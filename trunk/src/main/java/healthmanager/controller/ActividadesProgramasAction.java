package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Pyp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Group;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * @author Luis miguel
 *
 */
public class ActividadesProgramasAction extends ZKWindow {

    public static final String ACTIVIDADES = "acts";
    public static final String PROGRAMA_PYP = "p_PyP";

    @View
    private Rows rowActividades;
    private List<Plantilla_pyp> plantilla_pyps;

    private Admision admision;

    @Override
    public void params(Map<String, Object> map) {
        plantilla_pyps = (List<Plantilla_pyp>) map.get(ACTIVIDADES);
        admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
        if (admision == null) {
            MensajesUtil.mensajeAlerta("Advertencia", "admision no encontrada");
            return;
        }
    }

    @Override
    public void init() throws Exception {
        cargarActividades();
    }

    private void cargarActividades() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            for (Plantilla_pyp plantilla_pyp : plantilla_pyps) {
                if (!map.containsKey(plantilla_pyp.getArea_intervencion() + "")) {
                    Pyp pyp = new Pyp();
                    pyp.setCodigo(plantilla_pyp.getArea_intervencion());
                    pyp = getServiceLocator().getServicio(GeneralExtraService.class).consultar(pyp);
                    rowActividades.appendChild(crearFilasPyp(pyp));
                    map.put(plantilla_pyp.getArea_intervencion() + "", "_Top_");
                }
                //log.info(map); 
                rowActividades.appendChild(crearFilas(plantilla_pyp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo me permite agrupar la lista
	 *
     */
    private Group crearFilasPyp(Pyp pyp) {
        Group group = new Group();

        Hbox hbox = new Hbox();
        hbox.setStyle("display:inline-block");

        Toolbarbutton toolbarbutton = new Toolbarbutton();
        toolbarbutton.setLabel("Asignar cita");
        toolbarbutton.setImage("/images/admision.png");
        toolbarbutton.setAttribute(PROGRAMA_PYP, pyp);

        toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event evt) throws Exception {
                Pyp pyp = (Pyp) evt.getTarget().getAttribute(PROGRAMA_PYP);
                abrirModuloCitas(pyp);
            }
        });

        if (parametros_empresa.getPermitir_apartar_citas_medico().equals("S")) {
            hbox.appendChild(toolbarbutton);
            Space space = new Space();
            space.setBar(true);
            hbox.appendChild(space);
        }
        hbox.appendChild(new Label("" + pyp.getNombre()));
        group.appendChild(hbox);

        return group;
    }

    public void abrirModuloCitas(Pyp pyp) {
        if (admision != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            Admision admision = Res.clonar(this.admision);
            admision.setVia_ingreso(ServiciosDisponiblesUtils.getViaIngreso(sucursal, pyp.getCodigo()));
            map.put("admision", admision);
            Citas_corregidoAction citas_corregidoAction = (Citas_corregidoAction) Executions.createComponents("/pages/cita_corregido.zul", this, map);
            citas_corregidoAction.setWidth("100%");
            citas_corregidoAction.setHeight("100%");
            citas_corregidoAction.doModal();
        } else {
            MensajesUtil.mensajeAlerta("Advertencia", "La admision a con la que envio la orden no encontrada");
        }
    }

    public Row crearFilas(Object objeto) throws Exception {
        Row fila = new Row();

        final Plantilla_pyp plantilla_pyp = (Plantilla_pyp) objeto;

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(plantilla_pyp.getCodigo_pdc() + ""));
        fila.appendChild(new Label(plantilla_pyp.getNombre_pcd() + ""));

        return fila;
    }

}
