package healthmanager.controller.complemento;

import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;

import java.util.Map;

import org.zkoss.zul.Datebox;

import com.framework.constantes.ITiposServicio;
import com.framework.macros.facturacion.ServiciosFacturacionMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro.ETIPO_SERVICIO;
import com.framework.res.Res;
import com.framework.util.Utilidades;

public class EstanciasAction extends ZKWindow {

	private String via_ingreso;
	// private Prestadores prestadores;
	// private Administradora administradora;
	private Object admision_cama;

	public static final String PYP_DETECCION_TEMPRANA_ALTERACIONES_ADULTO = "431220_02";
	public static final String PYP_ATENCION_PREVENTIVA_SALUD_BUCAL = "431220_01";
	public static final String PYP_DETECCION_TEMPRANA_CRECIMIENTO_MENOR_DIEZ_ANIOS = "431220_03";
	public static final String PYP_DETECCION_TEMPRANA_ALTERACIONES_DESARROLLO_JOVEN = "431220_04";
	public static final String PYP_DETECCION_ALTERACIONES_AGUDEZA_VISUAL = "431220_05";
	public static final String PYP_PLANIFICACION_FAMILIAR = "431220_06";
	public static final String PYP_DETECCION_TEMPRANA_ALTERACIONES_EMBARAZO = "431220_07";
	public static final String PYP_PROGRAMA_AMPLIADO_INMUNIZACION = "431220_08";
	public static final String PYP_DETECCION_TEMPRANA_CANCER_CUELLO_UTERINO = "431220_09";
	public static final String PYP_ATENCION_RECIEN_NACIDO = "431220_11";
	public static final String PYP_DETECCION_TEMPRANA_CANCER_SENO = "431220_12";
	public static final String PYP_ATENCION_PARTO = "431220_10";

	@View
	private Datebox dtbxFecha_ingreso;

	@View
	private Datebox dtbxFecha_egreso;

	private ServiciosFacturacionMacro COMPONENTE_MACRO;

	@Override
	public void init() throws Exception {
		listarCombos();
		cargarDatos();
		Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
		configuracion_admision_ingreso.setCodigo_empresa(codigo_empresa);
		configuracion_admision_ingreso.setCodigo_sucursal(codigo_sucursal);
		configuracion_admision_ingreso.setVia_ingreso(via_ingreso);

		configuracion_admision_ingreso = getServiceLocator().getServicio(
				Configuracion_admision_ingresoService.class).consultar(
				configuracion_admision_ingreso);

		if (configuracion_admision_ingreso == null) {
			throw new ValidacionRunTimeException(
					"La via de ingreso "
							+ via_ingreso
							+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
		}
	}

	private void cargarDatos() {
		Utilidades.aplicarOnOk(dtbxFecha_ingreso, dtbxFecha_egreso);
	}

	@Override
	public void _despuesIniciar() {
		Res.cargarAutomatica(dtbxFecha_ingreso, admision_cama, "fecha_ingreso");
		Res.cargarAutomatica(dtbxFecha_egreso, admision_cama, "fecha_egreso");

	}

	private void listarCombos() {

	}

	@Override
	public void params(Map<String, Object> map) {
		via_ingreso = (String) map.get(ITiposServicio.PARAM_VIA_INGRESO);
		// prestadores = (Prestadores) map.get(ITiposServicio.PARAM_PRESTADOR);
		// administradora = (Administradora)
		// map.get(ITiposServicio.PARAM_ADMINISTRADORA);
		admision_cama = map.get(ITiposServicio.PARAM_RIPS);
		COMPONENTE_MACRO = (ServiciosFacturacionMacro) map
				.get("COMPONENTE_MACRO");
	}

	public void onClickAgregarOtroServicio() {
		if (COMPONENTE_MACRO != null) {
			//log.info("ejecutando metodo @onClickAgregarOtroServicio()");
			COMPONENTE_MACRO.agregarServicio(ETIPO_SERVICIO.SERVICIO, true);
			this.onClose();
		}
	}

}
