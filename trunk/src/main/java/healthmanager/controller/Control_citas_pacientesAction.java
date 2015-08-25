package healthmanager.controller;

import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.ElementoService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;

public class Control_citas_pacientesAction  extends ZKWindow{
	
//	private static Logger log = Logger.getLogger(Control_citas_pacientesAction.class);
	
	@View private Textbox tbxNro_identificacion;
	@View private Intbox tbxAnio;
	@View private Grid gridResultado;
	@View private Rows rowsResultado;

	private Map<String, String> vias;
	
	@Override
	public void init() throws Exception {
		
		List<Elemento> listado_vias = getServiceLocator().getServicio(
				ElementoService.class).listar("via_ingreso");
		vias = new HashMap<String, String>();
		for (Elemento elemento : listado_vias) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}
	}
	
	public void cargarCitaPaciente(String nro_identificacion,int anio)throws Exception{
		tbxNro_identificacion.setValue(nro_identificacion);
		tbxAnio.setValue(anio);
		listarCitas();
	}
	
	public void listarCitas()throws Exception{
		try {
			Map map = new HashMap();
			map.put("codigo_empresa", codigo_empresa);
			map.put("codigo_sucursal", codigo_sucursal);
			map.put("nro_identificacion", tbxNro_identificacion.getValue());
			map.put("anio",tbxAnio.getValue());
			map.put("estado","1");
			
			List<Citas> listaCitas = getServiceLocator().getCitasService().listar(map);
			rowsResultado.getChildren().clear();
			for (Citas citas : listaCitas) {
				rowsResultado.appendChild(crearFilas(citas, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		final Citas citas = (Citas) objeto;
		
		String fecha_cita = new SimpleDateFormat("yyyy-MM-dd").
		format(citas.getFecha_cita());
		
		Elemento elementoEstado = citas.getElementoEstado();
	
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(citas.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		
		Prestadores prestador = new Prestadores();
		prestador.setCodigo_empresa(citas.getCodigo_empresa());
		prestador.setCodigo_sucursal(citas.getCodigo_sucursal());
		prestador.setNro_identificacion(citas.getCodigo_prestador());
		prestador = getServiceLocator().getPrestadoresService().consultar(prestador);
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(citas.getCodigo_cita()));
		fila.appendChild(new Label(fecha_cita));
		fila.appendChild(new Label(citas.getHora()));
		fila.appendChild(new Label(citas.getNro_identificacion()));
		fila.appendChild(new Label((paciente!=null?paciente.getNombreCompleto():"")));
		fila.appendChild(new Label((prestador!=null?prestador.getNombres()+" "+prestador.getApellidos():"")));
		fila.appendChild(new Label(vias!=null? vias.get(citas.getTipo_consulta()):""));
		fila.appendChild(new Label((elementoEstado!=null?elementoEstado.getDescripcion():"")));
		
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				crearDetalle_cita(citas);
			}
		});
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		
		return fila;
		
	}

	protected void crearDetalle_cita(Citas citas) {
		Map<String, Object> parameters = new HashMap();
		Component componente = Executions.createComponents(
				"/pages/detalleCita.zul", this, parameters);
		final Detalle_citaAction window = (Detalle_citaAction) componente;
		window.setMode("modal");
		window.cargarCita(citas.getCodigo_cita());
		
	}
	
	public void cerrarWindow(){
		if(this.getParent() instanceof Citas_corregidoAction){
			Citas_corregidoAction action = (Citas_corregidoAction) this
			.getParent();
			action.cargarCalendarioCitas();
			this.detach();
		}
	}
	
}
