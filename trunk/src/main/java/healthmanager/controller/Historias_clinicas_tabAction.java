package healthmanager.controller;

import com.framework.constantes.IRutas_historia;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.Util;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.service.GeneralExtraService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class Historias_clinicas_tabAction extends GeneralComposer {

    private static Logger log = Logger.getLogger(Historias_clinicas_tabAction.class);
    private static final long serialVersionUID = -9145887024839938515L;

    @WireVariable
    private GeneralExtraService generalExtraService;

    private String via_ingreso;
    private Long codigo_historia;

    @Override
    public void init() throws Exception {
        if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA)) {
            mostrarHistoriaConsultaExterna(codigo_historia);
        }
    }

    @Override
    public void params(Map<String, Object> map) {
        codigo_historia = Long.parseLong(map.get("codigo_historia").toString());
        via_ingreso = (String) map.get("via_ingreso");
    }

    private void mostrarHistoriaConsultaExterna(Long codigo_historia) {
        Historia_clinica historia_clinica = new Historia_clinica();
        historia_clinica.setCodigo_historia(codigo_historia);
        historia_clinica = generalExtraService.consultar(historia_clinica);

        Admision admision_aux = new Admision();
        admision_aux.setCodigo_empresa(historia_clinica.getCodigo_empresa());
        admision_aux.setCodigo_sucursal(historia_clinica.getCodigo_sucursal());
        admision_aux.setNro_ingreso(historia_clinica.getNro_ingreso());
        admision_aux.setNro_identificacion(historia_clinica.getNro_identificacion());

        admision_aux = generalExtraService.consultar(admision_aux);

        Citas citas = new Citas();
        citas.setCodigo_empresa(admision_aux
                .getCodigo_empresa());
        citas.setCodigo_sucursal(admision_aux
                .getCodigo_sucursal());
        citas.setCodigo_cita(admision_aux.getCodigo_cita());
        citas.setNro_identificacion(admision_aux
                .getNro_identificacion());

        citas = generalExtraService.consultar(citas);

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put(IVias_ingreso.ADMISION_PACIENTE,
                admision_aux);
        parametros.put(IVias_ingreso.VIA_INGRESO, via_ingreso);
        parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
                IVias_ingreso.Opciones_via_ingreso.REGISTRAR);
        parametros.put(IVias_ingreso.CITA_PACIENTE, citas);
        parametros.put("_MOSTRAR_BTN_CONSULTAR", false);

        String ruta_historia;

        if (parametros_empresa.getTipo_historia_clinica().equals("01")) {
            ruta_historia = IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_1;
        } else if (parametros_empresa.getTipo_historia_clinica()
                .equals("02")) {
            ruta_historia = IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_2;
        } else {
            ruta_historia = IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_3;
        }

        int edad = Util.getEdadYYYYMMDD(
                admision_aux.getPaciente()
                .getFecha_nacimiento(),
                admision_aux.getCreacion_date()).get("anios");

        if (edad == 0) {
            int edad_meses = Util.getEdadYYYYMMDD(
                    admision_aux.getPaciente()
                    .getFecha_nacimiento(),
                    admision_aux.getCreacion_date()).get(
                            "meses");

            if (edad_meses < 2) {
                ruta_historia = IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES;
            } else {
                ruta_historia = IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS;
            }

        } else if (edad < 5) {
            ruta_historia = IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS;
        }

        this.appendChild(Executions.createComponents(ruta_historia, this, parametros));
    }

}
