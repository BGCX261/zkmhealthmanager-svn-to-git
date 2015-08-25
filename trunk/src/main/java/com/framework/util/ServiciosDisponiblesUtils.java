package com.framework.util;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Alteracion_joven;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Excepciones_pyp;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.bean.Servicios_via_ingreso;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Via_ingreso_consultas;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Alteracion_jovenService;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.Configuracion_servicios_autorizacionService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Datos_consultaService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Excepciones_pypService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Manuales_tarifariosService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pacientes_contratosService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.Resultado_laboratoriosService;
import healthmanager.modelo.service.Servicios_contratadosService;
import healthmanager.modelo.service.Servicios_disponiblesService;
import healthmanager.modelo.service.Servicios_via_ingresoService;
import healthmanager.modelo.service.Via_ingreso_consultasService;
import healthmanager.modelo.service.Via_ingreso_contratadasService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.IVias_ingreso;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.Res;

import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.FacturacionService;

public class ServiciosDisponiblesUtils {

	private static Logger log = Logger
			.getLogger(ServiciosDisponiblesUtils.class);

	public static final String CODIGO_LABORATORIO = "(LAB)";
	public static final String CODIGO_LABORATORIO1 = "(LAB1)";
	public static final String CODIGO_LABORATORIO2 = "(LAB2)";
	public static final String CODIGO_LABORATORIO3 = "(LAB3)";
	public static final String CODIGO_LABORATORIO4 = "(LAB4)";

	/* parametros de consulta */
	public static final String NRO_IDENTIFICACION = "nro_identificacion";
	public static final String CODIGO_EMPRESA = "codigo_empresa";
	public static final String CODIGO_SUCURSAL = "codigo_sucursal";

	public static final String CODIGO_CUPS_ASESORIA_PSICOLOGIA = "990106";

	/* estos son los codigos en los tipos consulta que estan en las citas */
	public static final String CONSULTA_EXTERNA = "1";
	public static final String CONSULTA_ODONOTOLOGIA = "3";
	public static final String CONSULTA_PYP = "4";
	public static final String CONSULTA_TERAPIA_FISICA = "10";
	public static final String CONSULTA_PSIQUIATRIA = "19";
	public static final String CONSULTA_PSICOLOGIA = "20";

	public static final String CAUSA_EXTERNA_ACCIDENTE_TRABAJO = "01";
	public static final String CAUSA_EXTERNA_ACCIDENTE_TRANSITO = "02";
	public static final String CAUSA_EXTERNA_EVENTO_CATASTROFICO = "06";

	/* Codigos de los servicios en el arbol de los contratos */
	public static final String CODSER_CONSULTA_ESPECIALIZADA = "11";
	public static final String CODSER_URGENCIAS = "3";
	public static final String CODSER_CONSULTA_EXTERNA = "1.1";
	public static final String CODSER_ODONTOLOGIA_GENERAL = "1.2";
	public static final String CODSER_HOSPITALIZACION = "6";

	public static final String CODSER_PYP = "4";
	public static final String CODSER_PYP_HIPERTENSO_DIABETICO = "4.3.3";
	public static final String CODSER_PYP_CRECIMIENTO_DESARROLLO = "4.2.1.1";
	public static final String CODSER_PYP_DESARROLLO_JOVEN = "4.2.1.2";
	public static final String CODSER_PYP_DET_ALT_EMBARAZO = "4.2.1.3";
	public static final String CODSER_PYP_ADULTO_MAYOR = "4.2.1.4";
	public static final String CODSER_PYP_CITOLOGIA = "4.2.1.5";
	public static final String CODSER_PYP_AGUDEZA_VISUAL = "4.2.1.6";
	public static final String CODSER_PYP_VACUNACION_PAI = "4.2.2.1";
	public static final String CODSER_PYP_PLANIFICACION_HOMBRES = "4.2.2.5";
	public static final String CODSER_PYP_PLANIFICACION_MUJERES = "4.2.2.6";

	public static final String CODSER_PYP_ATENCION_PARTO = "4.2.2.3";
	public static final String CODSER_PYP_RECIEN_NACIDO = "4.2.2.4";
	public static final String CODSER_PYP_SALUD_ORAL = "4.2.2.2";
	public static final String CODSER_PYP_CUELLO_UTERINO = "4.2.1.5";
	public static final String CODSER_PYP_ALTERACION_EMBARAZO = "4.2.1.3";
	public static final String CODSER_PYP_CANCER_SENO = "4.2.1.7";

	public static final String CODSER_PSICOLOGIA = "5.1";
	public static final String CODSER_LABORATORIO_CLINICO = "2.3";
	public static final String CODSER_LABORATORIO_CLINICO_URG = "3.4.3";
	public static final String CODSER_LABORATORIO_CLINICO_HOS = "6.4.3";

	public static final String CODSER_FARMACIA_MEDICAMENTOS = "7";
	public static final String CODSER_SERVICIOS_MEDICAMENTOS = "7";

	public static final String CODSER_SERVICIOS_CIRUGIA = "8";

	@ServiciosTipos(servicios = { CODSER_ODONTOLOGIA_GENERAL })
	public static final String TIPO_ODONTOLOGIA_GENERAL = "431217_02";
	@ServiciosTipos(servicios = { CODSER_PYP_VACUNACION_PAI })
	public static final String TIPO_VACUNACION_PAI = "431220_08";

	public static final String TIPO_CURACIONES = "431228";
	public static final String TIPO_QUIROFANO = "431236";
	@ServiciosTipos(servicios = { CODSER_LABORATORIO_CLINICO })
	public static final String TIPO_LABORATORIO_CLINICO = "431246";
	public static final String TIPO_ELECTROENCEFALOGRAMA = "431247_01";
	public static final String TIPO_ECOGRAFIA = "431247_02";
	public static final String TIPO_RAYOS_X = "431247_03";
	public static final String TIPO_TERAPIA_FISICA = "431256_01";
	public static final String TIPO_TERAPIA_RESPITARORIA = "431256_02";
	public static final String TIPO_BANCO_SANGRE = "431257";
	public static final String TIPO_OTROS_SERVICIOS = "431295_01";
	public static final String TIPO_SERVICIOS_AMBULATORIOS = "431296";
	public static final String TIPO_CITOLOGIA = "431246_01";
	public static final String TIPO_MEDICINA_GENERAL = "431217_01";
	public static final String TIPO_IMAGENEOLOGIA = "431247";

	public static final String TIPO_ASEGURADORA_ARL = "07";
	public static final String TIPO_ASEGURADORA_SOAT = "08";

	public enum NIVEL_CONSULTA {

		CONTRATO, CONFIGURACION, CONFIGURACION_CONTRATADA
	};

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface ServiciosTipos {

		String[] servicios();
	}

	public static List<String> getTiposServicios(String[] servicios) {
		try {
			Field[] fields = ServiciosDisponiblesUtils.class
					.getDeclaredFields();
			List<String> listado_tipos = new ArrayList<String>();
			for (Field field : fields) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}

				ServiciosTipos serviciosTipos = field
						.getAnnotation(ServiciosTipos.class);
				if (serviciosTipos != null && servicios != null) {
					String tipo = (String) field.get(null);
					for (String servicios_1 : serviciosTipos.servicios()) {
						for (String servicios_2 : servicios) {
							if (servicios_1.equals(servicios_2)) {
								listado_tipos.add(tipo);
							}
						}
					}
				}
			}

			return listado_tipos.isEmpty() ? null : listado_tipos;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		throw new ValidacionRunTimeException(
				"Este servicio no esta disponible para este paciente, por favor verifique los contratos");
	}

	/**
	 * Este metodo me permite validar, los tipos de ingreso en la admision que
	 * tiene permitido ese paciente
	 *
	 * @param listbox
	 * @param paciente
	 * @param serviceLocator
	 * @author Luis Miguel
	 *
	 */
	@SuppressWarnings("unchecked")
	public static boolean validarTipoViaIngresoAdmision(Listbox listbox,
			Paciente paciente, String codigo_administradora,
			boolean seleccione, boolean isCita, String id_contrato,
			boolean particular) {
		listbox.getChildren().clear();

		UtilidadesComponentes.listarElementosListbox(true, true,
				getServiceLocator(), listbox);

		for (Listitem listitem : listbox.getItems()) {
			listitem.setVisible(false);
		}

		if (seleccione) {
			Listitem listitem = getListitem(listbox, "");
			if (listitem != null) {
				listitem.setVisible(true);
				listbox.setSelectedIndex(0);
			}
		}
		listbox.setDisabled(false);

		Map<String, Object> mapa_vias_ingreso = obtenerMapaViasIngreso(
				paciente, codigo_administradora, id_contrato, particular);

		if (mapa_vias_ingreso.isEmpty()) {
			Notificaciones.mostrarNotificacionAlerta("Informacion",
					"Este paciente no tiene tipo de servicios disponibles",
					IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			listbox.setDisabled(true);
			return false;
		} else {
			for (String key_map : mapa_vias_ingreso.keySet()) {
				Map<String, Object> datos = (Map<String, Object>) mapa_vias_ingreso
						.get(key_map);
				Servicios_via_ingreso servicios_via_ingreso = (Servicios_via_ingreso) datos
						.get("servicios_via_ingreso");

				Listitem listitem = getListitem(listbox,
						servicios_via_ingreso.getVia_ingreso());
				if (listitem != null) {
					listitem.setVisible(true);
					listitem.setAttribute("MAPA_SERVICIO_CONSECUTIVO", datos);

				}
			}

			int edad = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"1", false));

			int meses_edad = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"2", false));

			/* obtenemos los programas para los cuales es apto ese paciente */
			List<String> programas = ManejadorPoblacion
					.obtenerListadoProgrmas(paciente);

			// Verfiicamos si los pacientes aplican para este programa
			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.SALUD_ORAL)) {
				ocultarListitem(listbox, IVias_ingreso.SALUD_ORAL);
			}

			if (!(meses_edad < 2)) {
				ocultarListitem(listbox, IVias_ingreso.HC_MENOR_2_MESES);
			}

			if (!(meses_edad >= 2 && edad < 2)) {
				ocultarListitem(listbox, IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);
			}

			if (!(edad >= 2 && edad < 5)) {
				ocultarListitem(listbox, IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);
			}

			if (!(edad >= 5 && edad <= 10)) {
				ocultarListitem(listbox,
						IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A);

			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.DESARROLLO_JOVEN)) {
				ocultarListitem(listbox, IVias_ingreso.ALTERACION_JOVEN);
			}

			if (!aplicaServAmigables(paciente)) {
				ocultarListitem(listbox, IVias_ingreso.SERVICIOS_AMIGABLES);

			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.PLANIFICACION_FAMILIAR)) {
				ocultarListitem(listbox, IVias_ingreso.PLANIFICACION_FAMILIAR);

				ocultarListitem(listbox,
						IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);

			} else {
				if (!paciente.getSexo().equals("F")) {
					ocultarListitem(listbox,
							IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);

				}
			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.ADULTO_MAYOR)) {
				ocultarListitem(listbox, IVias_ingreso.ADULTO_MAYOR);

			}

			if (paciente.getSexo().equals("F")) {
				if (!(edad >= 9)) {
					ocultarListitem(listbox, IVias_ingreso.CITOLOGIA);
				}
			} else {
				ocultarListitem(listbox, IVias_ingreso.CITOLOGIA);
				ocultarListitem(listbox, IVias_ingreso.ECOGRAFIA);
			}

			if (!aplicaTuberculosis(paciente)) {
				ocultarListitem(listbox, IVias_ingreso.CONTROL_TUBERCULOSIS);

			}

			if (!aplicaLepra(paciente)) {
				ocultarListitem(listbox, IVias_ingreso.CONTROL_LEPRA);

			}

			if (isCita) {
				ocultarListitem(listbox, IVias_ingreso.URGENCIA);

				ocultarListitem(listbox, IVias_ingreso.HOSPITALIZACIONES);

				ocultarListitem(listbox, IVias_ingreso.ELECTROENCEFALOGRAMA);

				ocultarListitem(listbox, IVias_ingreso.ENDOSCOPIA);

				ocultarListitem(listbox, IVias_ingreso.TAC);

				ocultarListitem(listbox, IVias_ingreso.RESONANCIA_MAGNETICA);

				ocultarListitem(listbox, IVias_ingreso.CONTROL_LEPRA);

			}

		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static boolean validarTipoViaIngresoAdmisionNuevo(Listbox listbox,
			Paciente paciente, String codigo_administradora, boolean esCita,
			List<Contratos> listado_contratos) {
		listbox.getChildren().clear();

		UtilidadesComponentes.listarElementosListbox(true, true,
				getServiceLocator(), listbox);

		for (Listitem listitem : listbox.getItems()) {
			listitem.setVisible(false);
		}

		Listitem listitem = getListitem(listbox, "");
		if (listitem != null) {
			listitem.setVisible(true);
			listbox.setSelectedIndex(0);
		}

		listbox.setDisabled(false);

		Map<String, Object> mapa_vias_ingreso = obtenerMapaViasIngresoNuevo(
				paciente, codigo_administradora, listado_contratos);

		if (mapa_vias_ingreso.isEmpty()) {
			Notificaciones.mostrarNotificacionAlerta("Informacion",
					"Este paciente no tiene tipo de servicios disponibles",
					IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			listbox.setDisabled(true);
			return false;
		} else {
			for (String key_map : mapa_vias_ingreso.keySet()) {
				Map<String, Object> datos = (Map<String, Object>) mapa_vias_ingreso
						.get(key_map);
				Via_ingreso_contratadas via_ingreso_contratadas = (Via_ingreso_contratadas) datos
						.get("via_ingreso_contratadas");

				listitem = getListitem(listbox,
						via_ingreso_contratadas.getVia_ingreso());
				if (listitem != null) {
					listitem.setVisible(true);
					listitem.setAttribute("MAPA_SERVICIO_CONSECUTIVO", datos);
				}
			}

			int edad = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"1", false));

			int meses_edad = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"2", false));

			/* obtenemos los programas para los cuales es apto ese paciente */
			List<String> programas = ManejadorPoblacion
					.obtenerListadoProgrmas(paciente);

			// Verfiicamos si los pacientes aplican para este programa
			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.SALUD_ORAL)) {
				ocultarListitem(listbox, IVias_ingreso.SALUD_ORAL);
			}

			if (!(meses_edad < 2)) {
				ocultarListitem(listbox, IVias_ingreso.HC_MENOR_2_MESES);
			}

			if (!(meses_edad >= 2 && edad < 2)) {
				ocultarListitem(listbox, IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);
			}

			if (!(edad >= 2 && edad < 5)) {
				ocultarListitem(listbox, IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);
			}

			if (!(edad >= 5 && edad <= 10)) {
				ocultarListitem(listbox,
						IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A);

			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.DESARROLLO_JOVEN)) {
				ocultarListitem(listbox, IVias_ingreso.ALTERACION_JOVEN);
			}

			if (!aplicaServAmigables(paciente)) {
				ocultarListitem(listbox, IVias_ingreso.SERVICIOS_AMIGABLES);

			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.PLANIFICACION_FAMILIAR)) {
				ocultarListitem(listbox, IVias_ingreso.PLANIFICACION_FAMILIAR);

				ocultarListitem(listbox,
						IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);

			} else {
				if (!paciente.getSexo().equals("F")) {
					ocultarListitem(listbox,
							IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);

				}
			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.ADULTO_MAYOR)) {
				ocultarListitem(listbox, IVias_ingreso.ADULTO_MAYOR);

			}

			if (paciente.getSexo().equals("F")) {
				if (!(edad >= 9)) {
					ocultarListitem(listbox, IVias_ingreso.CITOLOGIA);
				}
			} else {
				ocultarListitem(listbox, IVias_ingreso.CITOLOGIA);
				ocultarListitem(listbox, IVias_ingreso.ECOGRAFIA);
			}

			if (!aplicaTuberculosis(paciente)) {
				ocultarListitem(listbox, IVias_ingreso.CONTROL_TUBERCULOSIS);

			}

			if (!aplicaLepra(paciente)) {
				ocultarListitem(listbox, IVias_ingreso.CONTROL_LEPRA);

			}

			if (esCita) {
				ocultarListitem(listbox, IVias_ingreso.URGENCIA);

				ocultarListitem(listbox, IVias_ingreso.HOSPITALIZACIONES);

				ocultarListitem(listbox, IVias_ingreso.ELECTROENCEFALOGRAMA);

				ocultarListitem(listbox, IVias_ingreso.ENDOSCOPIA);

				ocultarListitem(listbox, IVias_ingreso.TAC);

				ocultarListitem(listbox, IVias_ingreso.RESONANCIA_MAGNETICA);

				ocultarListitem(listbox, IVias_ingreso.CONTROL_LEPRA);

			}

		}
		return true;
	}

	/**
	 * Este metodo me permite validar, los tipos de ingreso en la admision que
	 * tiene permitido ese paciente
	 *
	 * @param codigo_administradora
	 * @param paciente
	 * @param id_contrato
	 * @param particular
	 * @author Luis Miguel
	 *
	 */
	public static Map<String, Object> validarTipoViaIngresoAdmision(
			Paciente paciente, String codigo_administradora,
			String id_contrato, boolean particular) {

		Map<String, Object> mapa_vias_ingreso = obtenerMapaViasIngreso(
				paciente, codigo_administradora, id_contrato, particular);

		if (mapa_vias_ingreso.isEmpty()) {
			return null;
		} else {
			int edad = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"1", false));

			int meses_edad = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"2", false));

			/* obtenemos los programas para los cuales es apto ese paciente */
			List<String> programas = ManejadorPoblacion
					.obtenerListadoProgrmas(paciente);

			// Verfiicamos si los pacientes aplican para este programa
			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.SALUD_ORAL)) {
				mapa_vias_ingreso.remove(IVias_ingreso.SALUD_ORAL);
			}

			if (!(meses_edad < 2)) {
				mapa_vias_ingreso.remove(IVias_ingreso.HC_MENOR_2_MESES);
			}

			if (!(meses_edad >= 2 && edad < 2)) {
				mapa_vias_ingreso.remove(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);
			}

			if (!(edad >= 2 && edad < 5)) {
				mapa_vias_ingreso.remove(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);
			}

			if (!(edad >= 5 && edad <= 10)) {
				mapa_vias_ingreso
						.remove(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A);
			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.DESARROLLO_JOVEN)) {
				mapa_vias_ingreso.remove(IVias_ingreso.ALTERACION_JOVEN);
			}

			if (!aplicaServAmigables(paciente)) {
				mapa_vias_ingreso.remove(IVias_ingreso.SERVICIOS_AMIGABLES);
			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.PLANIFICACION_FAMILIAR)) {
				mapa_vias_ingreso.remove(IVias_ingreso.PLANIFICACION_FAMILIAR);

				mapa_vias_ingreso
						.remove(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);

			} else {
				if (!paciente.getSexo().equals("F")) {
					mapa_vias_ingreso
							.remove(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);
				}
			}

			if (!aplicaParaEstePrograma(programas,
					ManejadorPoblacion.ADULTO_MAYOR)) {
				mapa_vias_ingreso.remove(IVias_ingreso.ADULTO_MAYOR);

			}

			if (paciente.getSexo().equals("F")) {
				if (!(edad >= 9)) {
					mapa_vias_ingreso.remove(IVias_ingreso.CITOLOGIA);
				}
			} else {
				mapa_vias_ingreso.remove(IVias_ingreso.CITOLOGIA);
				mapa_vias_ingreso.remove(IVias_ingreso.ECOGRAFIA);
			}

			if (!aplicaTuberculosis(paciente)) {
				mapa_vias_ingreso.remove(IVias_ingreso.CONTROL_TUBERCULOSIS);
			}

			if (!aplicaLepra(paciente)) {
				mapa_vias_ingreso.remove(IVias_ingreso.CONTROL_LEPRA);
			}

		}
		return mapa_vias_ingreso;
	}

	private static boolean aplicaParaEstePrograma(List<String> programas,
			String programa) {
		for (String programaTemp : programas) {
			if (programaTemp.equals(programa)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Este metodo me devuelve el item, que tenga el valor enviado por parametro
	 *
	 * @param listbox
	 *            - La lista desplegable que contiene los items
	 * @param item
	 *            - el valor del item que va ha buscar.
	 * @return Listitem o nulo si no lo encuentra
	 * @author Luis Miguel
	 *
	 */
	public static Listitem getListitem(Listbox listbox, String item) {
		for (Listitem listitem : listbox.getItems()) {
			if (listitem.getValue() != null && listitem.getValue().equals(item)) {
				return listitem;
			}
		}
		return null;
	}

	private static void ocultarListitem(Listbox listbox, String via_ingreso) {
		Listitem listitem = getListitem(listbox, via_ingreso);
		if (listitem != null) {
			listitem.setVisible(false);
		}
	}

	public static Map<String, Object> obtenerMapaViasIngreso(Paciente paciente,
			String codigo_administradora, String id_contrato, boolean particular) {
		log.info("ejecutando metodo @obtenerMapaViasIngreso()");
		Map<String, Object> mapa_vias_ingreso = new HashMap<String, Object>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", paciente.getCodigo_empresa());
		parametros.put("codigo_sucursal", paciente.getCodigo_sucursal());
		parametros.put("codigo_administradora", codigo_administradora);
		parametros.put("nro_identificacion", paciente.getNro_identificacion());

		List<Servicios_contratados> listado_servicios_contratados = getServiciosContratados(
				paciente, codigo_administradora, id_contrato, particular);

		List<Servicios_via_ingreso> listado_servicios_vias = getServiceLocator()
				.getServicio(Servicios_via_ingresoService.class).listar(
						parametros);

		boolean hay_pyp = false;
		for (Servicios_via_ingreso servicios_via_ingreso : listado_servicios_vias) {
			Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
			configuracion_admision_ingreso
					.setCodigo_empresa(servicios_via_ingreso
							.getCodigo_empresa());
			configuracion_admision_ingreso
					.setCodigo_sucursal(servicios_via_ingreso
							.getCodigo_sucursal());
			configuracion_admision_ingreso.setVia_ingreso(servicios_via_ingreso
					.getVia_ingreso());

			configuracion_admision_ingreso = getServiceLocator().getServicio(
					Configuracion_admision_ingresoService.class).consultar(
					configuracion_admision_ingreso);

			Object[] datos_verificacion = verificarServicio(
					servicios_via_ingreso, listado_servicios_contratados);
			if (datos_verificacion != null) {
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("servicios_via_ingreso", servicios_via_ingreso);
				datos.put("consecutivo", datos_verificacion[0]);
				datos.put("id_contrato", datos_verificacion[1]);
				datos.put("CONFIGURACION_ADMISION",
						configuracion_admision_ingreso);
				mapa_vias_ingreso.put(servicios_via_ingreso.getVia_ingreso(),
						datos);
				if (configuracion_admision_ingreso != null
						&& configuracion_admision_ingreso.getEs_pyp()
						&& configuracion_admision_ingreso.getPrograma_lab_pyp()) {
					hay_pyp = true;
				}
			}
		}

		if (hay_pyp) {
			Map<String, Object> mapa_lab_pyp = new HashMap<String, Object>();
			mapa_lab_pyp.put("codigo_empresa", paciente.getCodigo_empresa());
			mapa_lab_pyp.put("codigo_sucursal", paciente.getCodigo_sucursal());
			mapa_lab_pyp.put("laboratorio_pyp", true);

			List<Configuracion_admision_ingreso> listado_configuraciones = getServiceLocator()
					.getServicio(Configuracion_admision_ingresoService.class)
					.listar(mapa_lab_pyp);
			if (!listado_configuraciones.isEmpty()) {
				Map<String, Object> datos = new HashMap<String, Object>();
				Servicios_via_ingreso servicios_via_ingreso = new Servicios_via_ingreso();
				servicios_via_ingreso.setCodigo_empresa(paciente
						.getCodigo_empresa());
				servicios_via_ingreso.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				servicios_via_ingreso.setVia_ingreso(listado_configuraciones
						.get(0).getVia_ingreso());
				datos.put("servicios_via_ingreso", servicios_via_ingreso);
				datos.put("CONFIGURACION_ADMISION",
						listado_configuraciones.get(0));
				mapa_vias_ingreso.put(listado_configuraciones.get(0)
						.getVia_ingreso(), datos);
			}

		}

		return mapa_vias_ingreso;
	}

	public static Map<String, Object> obtenerMapaViasIngresoNuevo(
			Paciente paciente, String codigo_administradora,
			List<Contratos> listado_contratos) {
		Map<String, Object> mapa_vias_ingreso = new HashMap<String, Object>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", paciente.getCodigo_empresa());
		parametros.put("codigo_sucursal", paciente.getCodigo_sucursal());
		parametros.put("codigo_administradora", codigo_administradora);
		parametros.put("nro_identificacion", paciente.getNro_identificacion());

		List<Via_ingreso_contratadas> listado_vias_contratadas = new ArrayList<Via_ingreso_contratadas>();

		for (Contratos contratos : listado_contratos) {
			parametros.put("id_plan", contratos.getId_plan());
			listado_vias_contratadas.addAll(getServiceLocator().getServicio(
					Via_ingreso_contratadasService.class).listar(parametros));
		}
		boolean hay_pyp = false;
		for (Via_ingreso_contratadas via_ingreso_contratadas : listado_vias_contratadas) {
			Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
			configuracion_admision_ingreso
					.setCodigo_empresa(via_ingreso_contratadas
							.getCodigo_empresa());
			configuracion_admision_ingreso
					.setCodigo_sucursal(via_ingreso_contratadas
							.getCodigo_sucursal());
			configuracion_admision_ingreso
					.setVia_ingreso(via_ingreso_contratadas.getVia_ingreso());

			configuracion_admision_ingreso = getServiceLocator().getServicio(
					Configuracion_admision_ingresoService.class).consultar(
					configuracion_admision_ingreso);

			Map<String, Object> datos = new HashMap<String, Object>();
			datos.put("via_ingreso_contratadas", via_ingreso_contratadas);
			datos.put("id_contrato", via_ingreso_contratadas.getId_plan());
			datos.put("CONFIGURACION_ADMISION", configuracion_admision_ingreso);
			mapa_vias_ingreso.put(via_ingreso_contratadas.getVia_ingreso(),
					datos);
			if (configuracion_admision_ingreso != null
					&& configuracion_admision_ingreso.getEs_pyp()
					&& configuracion_admision_ingreso.getPrograma_lab_pyp()) {
				hay_pyp = true;
			}

		}

		if (hay_pyp) {
			Map<String, Object> mapa_lab_pyp = new HashMap<String, Object>();
			mapa_lab_pyp.put("codigo_empresa", paciente.getCodigo_empresa());
			mapa_lab_pyp.put("codigo_sucursal", paciente.getCodigo_sucursal());
			mapa_lab_pyp.put("laboratorio_pyp", true);

			List<Configuracion_admision_ingreso> listado_configuraciones = getServiceLocator()
					.getServicio(Configuracion_admision_ingresoService.class)
					.listar(mapa_lab_pyp);
			if (!listado_configuraciones.isEmpty()) {
				Map<String, Object> datos = new HashMap<String, Object>();
				Via_ingreso_contratadas via_ingreso_contratadas = new Via_ingreso_contratadas();
				via_ingreso_contratadas.setCodigo_empresa(paciente
						.getCodigo_empresa());
				via_ingreso_contratadas.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				via_ingreso_contratadas.setVia_ingreso(listado_configuraciones
						.get(0).getVia_ingreso());
				datos.put("via_ingreso_contratadas", via_ingreso_contratadas);
				datos.put("CONFIGURACION_ADMISION",
						listado_configuraciones.get(0));
				datos.put("laboratorio_pyp", true);
				mapa_vias_ingreso.put(listado_configuraciones.get(0)
						.getVia_ingreso(), datos);
			}

		}

		return mapa_vias_ingreso;
	}

	public static List<Servicios_contratados> getServiciosContratados(
			Paciente paciente, String codigo_administradora,
			String id_contrato, boolean particular) {
		log.info("ejecutando metodo @getServiciosContratados()");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", paciente.getCodigo_empresa());
		parametros.put("codigo_sucursal", paciente.getCodigo_sucursal());
		parametros.put("codigo_administradora", codigo_administradora);
		parametros.put("nro_identificacion", paciente.getNro_identificacion());
		if (id_contrato != null) {
			parametros.put("id_contrato", id_contrato);
		}

		List<Servicios_contratados> listado_servicios_contratados = new ArrayList<Servicios_contratados>();

		if (!particular) {
			List<Pacientes_contratos> listado_contratos = getServiceLocator()
					.getServicio(Pacientes_contratosService.class).listar(
							parametros);
			for (Pacientes_contratos pacientes_contratos : listado_contratos) {
				parametros.put("id_contrato",
						pacientes_contratos.getId_codigo());
				listado_servicios_contratados.addAll(getServiceLocator()
						.getServicio(Servicios_contratadosService.class)
						.listar(parametros));
			}
		} else {
			listado_servicios_contratados = getServiceLocator().getServicio(
					Servicios_contratadosService.class).listar(parametros);
		}
		return listado_servicios_contratados;
	}

	public static Object[] verificarServicio(
			Servicios_via_ingreso servicios_via_ingreso,
			List<Servicios_contratados> listado_servicios_contratados) {
		for (Servicios_contratados servicios_contratados : listado_servicios_contratados) {
			if (servicios_contratados.getCodigo_servicio().startsWith(
					servicios_via_ingreso.getCodigo_servicio())
					|| servicios_via_ingreso.getCodigo_servicio().startsWith(
							servicios_contratados.getCodigo_servicio())) {
				return new Object[] {
						servicios_contratados.getConsecutivo_mt(),
						servicios_contratados.getId_contrato() };
			}
		}
		return null;
	}

	public static Object[] verificarServicio_via_ingreso(
			Servicios_via_ingreso servicios_via_ingreso,
			List<Map<String, Object>> listado_servicios_contratados) {
		for (Map<String, Object> mapa : listado_servicios_contratados) {
			Servicios_contratados servicios_contratados = (Servicios_contratados) mapa
					.get("servicios_contratados");
			Long consecutivo_manual = (Long) mapa.get("consecutivo_manual");
			if (servicios_contratados.getCodigo_servicio().startsWith(
					servicios_via_ingreso.getCodigo_servicio())
					|| servicios_via_ingreso.getCodigo_servicio().startsWith(
							servicios_contratados.getCodigo_servicio())) {
				return new Object[] {
						servicios_via_ingreso.getElemento_via_ingreso(),
						consecutivo_manual };
			}
		}
		return null;
	}

	public static boolean aplicaServAmigables(Paciente paciente) {
		boolean aplica = true;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", paciente.getCodigo_empresa());
		map.put("codigo_sucursal", paciente.getCodigo_sucursal());
		map.put("identificacion", paciente.getNro_identificacion());
		map.put("order_desc_fecha", "order_desc_fecha");
		List<Alteracion_joven> lista_historias = getServiceLocator()
				.getServicio(Alteracion_jovenService.class).listar(map);

		Integer edad = Integer.parseInt(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
						.getFecha_nacimiento()), "1", false));

		// TODO: mejorar la validacion
		if (lista_historias.size() > 0) {
			Timestamp fecha1 = lista_historias.get(0).getFecha_inicial();
			Calendar nacimiento = Calendar.getInstance();
			Calendar hoy = Calendar.getInstance();

			nacimiento
					.setTimeInMillis(paciente.getFecha_nacimiento().getTime());
			nacimiento.set(Calendar.YEAR, hoy.get(Calendar.YEAR));

			Integer anos_dif = Util.getEdadYYYYMMDD(new Date(fecha1.getTime()),
					hoy.getTime()).get("anios");
			if (edad >= 10 && edad <= 13) {
				Integer lim = 10;
				aplica = (lim - anos_dif) >= lim;
			}
			if (edad >= 14 && edad <= 16) {
				Integer lim = 14;
				aplica = (lim - anos_dif) >= lim;
			}
			if (edad == 17) {
				Integer lim = 17;
				aplica = (lim - anos_dif) >= lim;
			}
		} else {
			aplica = false;
		}
		return aplica;
	}

	public static boolean aplicaTuberculosis(Paciente paciente) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", paciente.getCodigo_empresa());
		parameters.put("codigo_sucursal", paciente.getCodigo_sucursal());
		parameters.put("nro_identificacion", paciente.getNro_identificacion());
		parameters.put("parameters", IConstantes.CODIGOS_CIE_TUBERCULOSIS);
		parameters.put("limite_paginado", "limit 25 offset 0");
		List<Impresion_diagnostica> lista_datos = getServiceLocator()
				.getServicio(Impresion_diagnosticaService.class)
				.listar_paciente_contuberculosis_lepra(parameters);
		return (lista_datos != null);
	}

	public static boolean aplicaLepra(Paciente paciente) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", paciente.getCodigo_empresa());
		parameters.put("codigo_sucursal", paciente.getCodigo_sucursal());
		parameters.put("nro_identificacion", paciente.getNro_identificacion());
		parameters.put("parameters", IConstantes.CODIGOS_CIE_LEPRA);
		parameters.put("limite_paginado", "limit 25 offset 0");
		List<Impresion_diagnostica> lista_datos = getServiceLocator()
				.getServicio(Impresion_diagnosticaService.class)
				.listar_paciente_contuberculosis_lepra(parameters);
		return (lista_datos != null);
	}

	/**
	 * Este metodo me devuelve cuando un paciente tiene correcto los datos tanto
	 * la fecha como el numero de identificacion
	 *
	 * @author Luis Miguel
	 *
	 */
	public static void validarInformacionPaciente(Paciente paciente) {
		if (paciente != null) {
			boolean valido = true;
			Integer tiempo_msj = IConstantes.TIEMPO_NOTIFICACIONES_GENERALES;
			if (paciente.getFecha_nacimiento() != null) {
				if (paciente.getFecha_nacimiento().compareTo(
						Calendar.getInstance().getTime()) > 0) {
					valido = false;
					Notificaciones.mostrarNotificacionAlerta(
							"Advertencia",
							"Este paciente tiene la fecha de nacimiento incorrecta"
									+ new SimpleDateFormat("yyyy-MM-dd")
											.format(paciente
													.getFecha_nacimiento()),
							tiempo_msj);
				}
			}
			if (valido
					&& paciente.getTipo_identificacion().equals(
							IConstantes.TIPO_ID_TARJETA_IDENTIDAD)
					&& Utilidades.getAnios(paciente.getFecha_nacimiento()) >= 18) {
				Notificaciones
						.mostrarNotificacionAlerta(
								"Advertencia",
								"Este paciente es mayor de edad y presenta tarjeta de identidad",
								tiempo_msj);
			}
		}
	}

	public static boolean cobraCuotaModeradoraCita(String via_ingreso) {
		return (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA)
				|| via_ingreso.equals(IVias_ingreso.CONSULTA_ESPECIALIZADA)
				|| via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2) || via_ingreso
					.equals(IVias_ingreso.ODONTOLOGIA));
	}

	public static boolean isViaIngresoImagengeneologia(String via_ingreso) {
		return (via_ingreso.equals(IVias_ingreso.ECOGRAFIA)
				|| via_ingreso.equals(IVias_ingreso.RAYOS_X)
				|| via_ingreso.equals(IVias_ingreso.ELECTROENCEFALOGRAMA)
				|| via_ingreso.equals(IVias_ingreso.ENDOSCOPIA)
				|| via_ingreso.equals(IVias_ingreso.TAC) || via_ingreso
					.equals(IVias_ingreso.RESONANCIA_MAGNETICA));
	}

	/**
	 * Este metodo me devuelve los servicios que se pueden facturar automatico
	 * para los terceros
	 *
	 * @author Luis Miguel
	 *
	 */
	public static boolean validarfactAutomaticaParticular(String via_ingreso) {
		return via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)
				|| via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
				|| via_ingreso.equals(IVias_ingreso.URGENCIA)
				|| via_ingreso.equals(IVias_ingreso.URGENCIA_ODONTOLOGICO)
				|| via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES);
	}

	public static boolean isApoyoDiagnostico(String via_ingreso) {
		return (IVias_ingreso.LABORATORIOS.equals(via_ingreso)
				|| IVias_ingreso.LABORATORIOS_PYP.equals(via_ingreso)
				|| // "28".equals(via_ingreso) || // Ecografia solicita la
					// seleccion del medico
				IVias_ingreso.RAYOS_X.equals(via_ingreso)
				|| IVias_ingreso.ELECTROENCEFALOGRAMA.equals(via_ingreso)
				|| IVias_ingreso.RESONANCIA_MAGNETICA.equals(via_ingreso)
				|| IVias_ingreso.ENDOSCOPIA.equals(via_ingreso) || IVias_ingreso.TAC
					.equals(via_ingreso));
	}

	public static boolean isEcografia(String via_ingreso) {
		return IVias_ingreso.ECOGRAFIA.equals(via_ingreso);
	}

	public static boolean pagaCopago(Paciente paciente, String via_ingreso) {
		if (via_ingreso.equals(IVias_ingreso.LABORATORIOS)
				|| isViaIngresoImagengeneologia(via_ingreso)) {
			return (paciente != null && paciente.getTipo_usuario().equals(
					IConstantes.REGIMEN_CONTRIBUTIVO));
		}
		return false;
	}

	public static boolean pagaCopago(Admision admision) {
		return pagaCopago(admision.getPaciente(), admision.getVia_ingreso());
	}

	/**
	 * Este metodo permite consultar un manual tarifario dependiendo de un
	 * manual
	 *
	 * @param servicios_manuales
	 *            - este es un servicio, el cual se quiere sacar el manual
	 * @return Manuales_tarifarios
	 * @author Luis Miguel
	 *
	 */
	public static Manuales_tarifarios getManuales_tarifarios(
			Servicios_contratados servicios_contratados) {
		return getServiceLocator()
				.getServicio(Manuales_tarifariosService.class)
				.consultarDesdeServicio(servicios_contratados);
	}

	/**
	 * Este metodo devuelve el manual tarifario de la admision si el paciente
	 * tiene el servicio contratado
	 *
	 * @author Luis Miguel
	 *
	 */
	public static Manuales_tarifarios getManuales_tarifariosPorPaciente(
			Admision admision) {
		try {
			Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
			configuracion_admision_ingreso.setCodigo_empresa(admision
					.getCodigo_empresa());
			configuracion_admision_ingreso.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			configuracion_admision_ingreso.setVia_ingreso(admision
					.getVia_ingreso());

			configuracion_admision_ingreso = getServiceLocator().getServicio(
					Configuracion_admision_ingresoService.class).consultar(
					configuracion_admision_ingreso);

			String via_ingreso = "";

			if (configuracion_admision_ingreso != null) {
				if (configuracion_admision_ingreso.getLaboratorio_pyp()) {
					via_ingreso = admision.getPrograma_lab_pyp();
				} else {
					via_ingreso = admision.getVia_ingreso();
				}
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", admision.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", admision.getCodigo_sucursal());
				parametros.put("via_ingreso", via_ingreso);

				List<Servicios_via_ingreso> listado_servicios = getServiceLocator()
						.getServicio(Servicios_via_ingresoService.class)
						.listar(parametros);

				if (!listado_servicios.isEmpty()) {
					Servicios_via_ingreso servicios_via_ingreso = listado_servicios
							.get(0);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("codigo_empresa", admision.getCodigo_empresa());
					map.put("codigo_sucursal", admision.getCodigo_sucursal());
					map.put("codigo_administradora",
							admision.getCodigo_administradora());
					if (admision.getId_plan() != null
							&& !admision.getId_plan().isEmpty()) {
						map.put("id_contrato", admision.getId_plan());
					}

					if (admision.getCausa_externa() == null) {
						admision.setCausa_externa("");
					}

					if (admision.getParticular() == null) {
						admision.setParticular("N");
						// log.info("@getManuales_tarifarios va nulo");
					}

					// verificamos si fue admisionado por accidente de
					// trabajo o por accidente de transito..
					if (!admision.getCausa_externa().equals(
							CAUSA_EXTERNA_ACCIDENTE_TRABAJO)
							&& !admision.getCausa_externa().equals(
									CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
							&& !admision.getCausa_externa().equals(
									CAUSA_EXTERNA_EVENTO_CATASTROFICO)
							&& !admision.getParticular().equalsIgnoreCase("S")) {
						map.put("nro_identificacion",
								admision.getNro_identificacion());
					} else {
						map.put("id_contrato", admision.getId_plan());
					}

					map.put("codigo_servicio",
							servicios_via_ingreso.getCodigo_servicio());
					// map.put("id_contrato", admision.getId_plan());
					// //log.info("Va a ejecutar el metodo consultarParametrizado() ===> "
					// + map);
					return getServiceLocator().getServicio(
							Manuales_tarifariosService.class)
							.consultar_parametrizado(map);
				}

				Elemento elemento = new Elemento();
				elemento.setTipo("via_ingreso");
				elemento.setCodigo(admision.getVia_ingreso());
				elemento = getServiceLocator().getServicio(
						ElementoService.class).consultar(elemento);
				throw new ValidacionRunTimeException("El paciente "
						+ admision.getNro_identificacion()
						+ " no tiene contratado el servicio "
						+ admision.getVia_ingreso()
						+ "-"
						+ (elemento != null ? elemento.getDescripcion()
								: "\"descripcion de servicio no encontrada\""));
			} else {
				throw new ValidacionRunTimeException("La via de ingreso "
						+ admision.getVia_ingreso()
						+ "- no tiene una configuracion creada");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Este metodo devuelve el manual tarifario de la admision si el paciente
	 * tiene el servicio contratado
	 *
	 * @author Luis Miguel
	 *
	 */
	public static List<Manuales_tarifarios> getListado_manuales_tarifariosPorPaciente(
			Admision admision) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", admision.getCodigo_empresa());
			parametros.put("codigo_sucursal", admision.getCodigo_sucursal());
			parametros.put("via_ingreso", admision.getVia_ingreso());

			if (admision.getId_plan() != null
					&& !admision.getId_plan().isEmpty()) {
				parametros.put("id_contrato", admision.getId_plan());
			}

			List<Servicios_via_ingreso> listado_servicios = getServiceLocator()
					.getServicio(Servicios_via_ingresoService.class).listar(
							parametros);

			if (!listado_servicios.isEmpty()) {
				Servicios_via_ingreso servicios_via_ingreso = listado_servicios
						.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", admision.getCodigo_empresa());
				map.put("codigo_sucursal", admision.getCodigo_sucursal());
				map.put("codigo_administradora",
						admision.getCodigo_administradora());

				if (admision.getCausa_externa() == null) {
					admision.setCausa_externa("");
				}

				if (admision.getParticular() == null) {
					admision.setParticular("N");
					// log.info("@getManuales_tarifarios va nulo");
				}

				// verificamos si fue admisionado por accidente de
				// trabajo o por accidente de transito..
				if (!admision.getCausa_externa().equals(
						CAUSA_EXTERNA_ACCIDENTE_TRABAJO)
						&& !admision.getCausa_externa().equals(
								CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
						&& !admision.getCausa_externa().equals(
								CAUSA_EXTERNA_EVENTO_CATASTROFICO)
						&& !admision.getParticular().equalsIgnoreCase("S")) {
					map.put("nro_identificacion",
							admision.getNro_identificacion());
				} else {
					map.put("id_contrato", admision.getId_plan());
				}

				if (admision.getId_plan() != null
						&& !admision.getId_plan().isEmpty()) {
					map.put("id_contrato", admision.getId_plan());
				}

				map.put("codigo_servicio",
						servicios_via_ingreso.getCodigo_servicio());

				// log.info("Va a ejecutar el metodo listar_parametrizado() ===> "
				// + map);
				return getServiceLocator().getServicio(
						Manuales_tarifariosService.class).listar_parametrizado(
						map);
			}

			Elemento elemento = new Elemento();
			elemento.setTipo("via_ingreso");
			elemento.setCodigo(admision.getVia_ingreso());
			elemento = getServiceLocator().getServicio(ElementoService.class)
					.consultar(elemento);
			throw new ValidacionRunTimeException("El paciente "
					+ admision.getNro_identificacion()
					+ " no tiene contratado el servicio "
					+ admision.getVia_ingreso()
					+ "-"
					+ (elemento != null ? elemento.getDescripcion()
							: "\"descripcion de servicio no encontrada\""));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static Manuales_tarifarios getManuales_tarifariosDefecto(
			Admision admision) {
		Manuales_tarifarios manuales_tarifariosTemp = new Manuales_tarifarios();
		manuales_tarifariosTemp.setCodigo_empresa(admision.getCodigo_empresa());
		manuales_tarifariosTemp.setCodigo_sucursal(admision
				.getCodigo_sucursal());
		manuales_tarifariosTemp.setCodigo_administradora(admision
				.getCodigo_administradora());
		manuales_tarifariosTemp.setId_contrato(admision.getId_plan());
		manuales_tarifariosTemp.setConsecutivo(1L);
		manuales_tarifariosTemp = getServiceLocator().getServicio(
				Manuales_tarifariosService.class).consultar(
				manuales_tarifariosTemp);
		return manuales_tarifariosTemp;
	}

	/**
	 * Este metodo me permite, obtener el manual tarifario sin que el paciente
	 * tenga contratado el servico. Ejemplo: en el caso de que el paciente le
	 * facturaron un servicio por siente contrato y despues en una actualizacion
	 * lo inactiven la factura se podra visualizar por que ya no lo tomara por
	 * el paciente sino por el contrato y el servicio contratado
	 *
	 * @author Luis Miguel
	 * @param admision
	 * @return Manuales_tarifarios
	 *
	 */
	public static Manuales_tarifarios getManuales_tarifarios(Admision admision) {
		Admision admision_clon = Res.clonar(admision);
		// admision_clon.setParticular("S");
		Manuales_tarifarios manuales_tarifarios = getManuales_tarifariosPorPaciente(admision_clon);

		// si no se encuetra por servicio, se consulta por el manual
		if (manuales_tarifarios == null) {
			manuales_tarifarios = getManuales_tarifariosDefecto(admision_clon);
		}
		return manuales_tarifarios;
	}

	/**
	 * Este metodo me permite, obtener el manual tarifario sin que el paciente
	 * tenga contratado el servico. Ejemplo: en el caso de que el paciente le
	 * facturaron un servicio por siente contrato y despues en una actualizacion
	 * lo inactiven la factura se podra visualizar por que ya no lo tomara por
	 * el paciente sino por el contrato y el servicio contratado
	 *
	 * @author Luis Miguel
	 * @param admision
	 * @return Manuales_tarifarios
	 *
	 */
	public static List<Manuales_tarifarios> getListado_manuales_tarifarios(
			Admision admision) {
		Admision admision_clon = Res.clonar(admision);
		// admision_clon.setParticular("S");
		List<Manuales_tarifarios> listado_manuales_tarifarios = getListado_manuales_tarifariosPorPaciente(admision_clon);

		// si no se encuetra por servicio, se consulta por el manual
		if (listado_manuales_tarifarios == null
				|| listado_manuales_tarifarios.isEmpty()) {
			listado_manuales_tarifarios = new ArrayList<Manuales_tarifarios>();
			listado_manuales_tarifarios
					.add(getManuales_tarifariosDefecto(admision_clon));
		}
		return listado_manuales_tarifarios;
	}

	/**
	 *
	 * @author Luis Miguel
	 * @param admision
	 * @return Via_ingreso_contratadas
	 *
	 */
	public static List<Via_ingreso_contratadas> getListado_via_ingreso_contratadas(
			Admision admision) {
		log.info("ejecutando metodo @getListado_via_ingreso_contratadas()");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", admision.getCodigo_empresa());
		parametros.put("codigo_sucursal", admision.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("via_ingreso", admision.getVia_ingreso());
		if (admision.getId_plan() != null) {
			parametros.put("id_contrato", admision.getId_plan());
		}

		Util.mostrarParametros("Parametros via_ingreso_consultas", parametros);

		return getServiceLocator().getServicio(
				Via_ingreso_contratadasService.class).listar(parametros);
	}

	/**
	 *
	 * @author Luis Miguel
	 * @param admision
	 * @return Via_ingreso_contratadas
	 *
	 */
	public static List<Via_ingreso_contratadas> getListado_via_ingreso_contratadas(
			Admision admision, List<Contratos> listado_contratos) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", admision.getCodigo_empresa());
		parametros.put("codigo_sucursal", admision.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("via_ingreso", admision.getVia_ingreso());
		if (!listado_contratos.isEmpty()) {
			List<String> listado = new ArrayList<String>();
			for (Contratos contratos : listado_contratos) {
				listado.add(contratos.getId_plan());
			}
			parametros.put("listado_contratos", listado);
		}

		return getServiceLocator().getServicio(
				Via_ingreso_contratadasService.class).listar(parametros);
	}

	/**
	 * Este metodo me permite, obtener el manual tarifario sin que el paciente
	 * tenga contratado el servico. Ejemplo: en el caso de que el paciente le
	 * facturaron un servicio por siente contrato y despues en una actualizacion
	 * lo inactiven la factura se podra visualizar por que ya no lo tomara por
	 * el paciente sino por el contrato y el servicio contratado
	 *
	 * @author Luis Miguel
	 * @param admision
	 * @return Manuales_tarifarios
	 *
	 */
	public static Via_ingreso_contratadas getVia_ingreso_contratadas(
			Admision admision) {
		Via_ingreso_contratadas via_ingreso_contratadas = new Via_ingreso_contratadas();
		via_ingreso_contratadas.setCodigo_empresa(admision.getCodigo_empresa());
		via_ingreso_contratadas.setCodigo_sucursal(admision
				.getCodigo_sucursal());
		via_ingreso_contratadas.setCodigo_administradora(admision
				.getCodigo_administradora());
		if (admision.getPrograma_lab_pyp() != null
				&& !admision.getPrograma_lab_pyp().isEmpty()) {
			via_ingreso_contratadas.setVia_ingreso(admision
					.getPrograma_lab_pyp());
		} else {
			via_ingreso_contratadas.setVia_ingreso(admision.getVia_ingreso());
		}

		via_ingreso_contratadas.setId_plan(admision.getId_plan());
		via_ingreso_contratadas = getServiceLocator().getServicio(
				Via_ingreso_contratadasService.class).consultar_informacion(
				via_ingreso_contratadas);
		return via_ingreso_contratadas;
	}

	public static Contratos getContratosAdmision(Admision admision) {
		Contratos contratos = new Contratos();
		contratos.setCodigo_empresa(admision.getCodigo_empresa());
		contratos.setCodigo_sucursal(admision.getCodigo_sucursal());
		contratos.setCodigo_administradora(admision.getCodigo_administradora());
		contratos.setId_plan(admision.getId_plan());
		contratos = getServiceLocator().getServicio(ContratosService.class)
				.consultar(contratos);
		return contratos;
	}

	public static Manuales_tarifarios getManuales_tarifarios(
			Via_ingreso_contratadas via_ingreso_contratadas) {
		Manuales_tarifarios manuales_tarifarios = new Manuales_tarifarios();
		manuales_tarifarios.setCodigo_empresa(via_ingreso_contratadas
				.getCodigo_empresa());
		manuales_tarifarios.setCodigo_sucursal(via_ingreso_contratadas
				.getCodigo_sucursal());
		manuales_tarifarios.setCodigo_administradora(via_ingreso_contratadas
				.getCodigo_administradora());
		manuales_tarifarios
				.setId_contrato(via_ingreso_contratadas.getId_plan());
		manuales_tarifarios.setConsecutivo(via_ingreso_contratadas
				.getConsecutivo_manual());
		manuales_tarifarios = getServiceLocator().getServicio(
				Manuales_tarifariosService.class)
				.consultar(manuales_tarifarios);
		return manuales_tarifarios;
	}

	/**
	 * Este metodo me permite facturar automaticamente las admisiones del mismo
	 * dia.
	 *
	 */
	public static void facturacionAutomatica(Admision admision) {
		facturacionAutomatica(admision, false);
	}

	/**
	 * Este metodo me permite generar un factura
	 *
	 * @param recalculo
	 *            - Este parametro se utiliza para colocar la fecha de la
	 *            admision
	 *
	 */
	@SuppressWarnings("unchecked")
	public static void facturacionAutomatica(Admision admision,
			boolean recalculo) {
		Parametros_empresa parametros_empresa = new Parametros_empresa();
		parametros_empresa.setCodigo_empresa(admision.getCodigo_empresa());
		parametros_empresa = getServiceLocator().getServicio(
				GeneralExtraService.class).consultar(parametros_empresa);

		if (!admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)
				&& !admision.getVia_ingreso().equals(
						IVias_ingreso.HOSPITALIZACIONES)) {
			if ((parametros_empresa != null && parametros_empresa
					.getFacturar_auto_asistencial().equals("S"))
					|| (admision.getParticular().equals("S"))
					|| admision.getVia_ingreso().equals(
							IVias_ingreso.VIA_VACUNACION)) {

				Map<String, Object> param_factura = new HashMap<String, Object>();
				param_factura.put("codigo_empresa",
						admision.getCodigo_empresa());
				param_factura.put("codigo_sucursal",
						admision.getCodigo_sucursal());
				param_factura.put("codigo_tercero",
						admision.getNro_identificacion());
				param_factura.put("nro_ingreso", admision.getNro_ingreso());
				if (!getServiceLocator().getServicio(FacturacionService.class)
						.existePorParametro(param_factura)) {
					GregorianCalendar vence = new GregorianCalendar();
					vence.setTimeInMillis(admision.getFecha_ingreso().getTime());
					vence.set(Calendar.DATE, vence.get(Calendar.DATE) + 30);

					Paciente paciente = admision.getPaciente();
					if (paciente == null) {
						paciente = new Paciente();
						paciente.setCodigo_empresa(admision.getCodigo_empresa());
						paciente.setCodigo_sucursal(admision
								.getCodigo_sucursal());
						paciente.setNro_identificacion(admision
								.getNro_identificacion());
						paciente = getServiceLocator().getServicio(
								PacienteService.class).consultar(paciente);
					}

					if (paciente == null) {
						throw new ValidacionRunTimeException(
								"El paciente admisionado no existe en la base de datos.");
					}

					List<Detalle_factura> lista_detalle = new ArrayList<Detalle_factura>();
					Via_ingreso_contratadas via_ingreso_contratadas = getVia_ingreso_contratadas(admision);
					Map<String, Object> serviciosCargados = getServiceLocator()
							.getServicio(FacturacionService.class)
							.consultarAdmisionFactura(admision,
									admision.getFecha_ingreso(),/*
																 * new ArrayList
																 * <
																 * Detalle_factura
																 * >()
																 */
									lista_detalle, via_ingreso_contratadas,
									"registrar");

					lista_detalle = (List<Detalle_factura>) serviciosCargados
							.get("lista_detalle");
					Facturacion facturacion_totales = (Facturacion) serviciosCargados
							.get("facturacion_totales");

					boolean paga_copago = pagaCopago(paciente,
							admision.getVia_ingreso());

					Facturacion facturacion = new Facturacion();
					facturacion.setCodigo_empresa(admision.getCodigo_empresa());
					facturacion.setCodigo_sucursal(admision
							.getCodigo_sucursal());
					facturacion.setCodigo_comprobante("15");
					facturacion.setId_factura(null);

					facturacion.setTipo_identificacion(paciente
							.getTipo_identificacion());
					facturacion.setCodigo_tercero(paciente
							.getNro_identificacion());
					facturacion.setNro_ingreso(admision.getNro_ingreso());
					facturacion.setDocumento_externo("");
					facturacion.setCodigo_empleado("");
					facturacion.setFecha(admision.getFecha_ingreso());
					facturacion.setFecha_vencimiento(new Timestamp(vence
							.getTimeInMillis()));
					facturacion.setFecha_inicio(admision.getFecha_ingreso());

					Timestamp fecha_final_atencion = new Timestamp(Calendar
							.getInstance().getTimeInMillis());

					// En esta parte verificamos si es un recalculo
					// de esta manera, pordemos colocar una fecha acorde con la
					// fecha de atencion.
					if (recalculo) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(admision.getFecha_ingreso());
						calendar.set(Calendar.MINUTE,
								calendar.get(Calendar.MINUTE) + 10);
						fecha_final_atencion = new Timestamp(
								calendar.getTimeInMillis());
					}

					facturacion.setFecha_final(fecha_final_atencion);
					facturacion.setCodigo_administradora(admision
							.getCodigo_administradora());
					facturacion.setId_plan(admision.getId_plan());
					facturacion.setNro_contrato("");
					facturacion.setNro_poliza("");
					facturacion.setTotal_cuotas(1);
					facturacion.setPlazo(30);
					facturacion.setForma_pago("02");
					facturacion
							.setDescripcion("FACTURACION DE PROCEDIMIENTOS Y CONSULTAS");
					facturacion.setObservacion("");
					facturacion.setValor_total(facturacion_totales
							.getValor_total());
					facturacion.setValor_cuota(facturacion_totales
							.getValor_cuota());
					facturacion
							.setValor_copago(paga_copago ? facturacion_totales
									.getValor_copago() : 0d);
					facturacion.setNocopago(paga_copago ? "N" : "S");
					facturacion.setDto_factura(facturacion_totales
							.getDto_factura());
					facturacion.setDto_copago(paga_copago ? facturacion_totales
							.getDto_copago() : 0d);
					facturacion.setCop_canc(0);
					// facturacion.setTipo("IND");
					facturacion.setEstado("PEND");
					// facturacion.setFactura(factura);
					facturacion.setPost("N");
					facturacion.setUltimo_update(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					facturacion.setCreacion_user(admision.getCreacion_user());
					facturacion.setUltimo_user(admision.getUltimo_user());
					facturacion.setCreacion_date(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					facturacion.setCodigo_credito("");
					facturacion.setRemision("");
					facturacion.setPrefijo("");
					facturacion.setAnio(new java.text.SimpleDateFormat("yyyy")
							.format(facturacion.getFecha()));
					facturacion.setMes(new java.text.SimpleDateFormat("MM")
							.format(facturacion.getFecha()));
					facturacion.setClasificacion("");
					facturacion.setVerificado("N");
					facturacion.setRetencion(0.0);
					facturacion.setFactura("");
					facturacion.setRadicado("N");
					facturacion.setAuditado("N");
					facturacion.setValor_pagar_factura(facturacion
							.getValor_total());
					facturacion.setFact_glosada("N");

					Map<String, Object> datos = new HashMap<String, Object>();
					datos.put("facturacion", facturacion);
					datos.put("lista_detalle", lista_detalle);
					datos.put("accion", "registrar");
					datos.put("admision_aux", admision);
					datos.put("contratos", getContratosAdmision(admision));
					getServiceLocator().getServicio(FacturacionService.class)
							.guardarDatos(datos);
				}
			}
		}
		// Actualizamos la admision que esta en la vista para que coja el estado
		// que en realidad se debe tener
		Admision admision_aux = getServiceLocator().getServicio(
				AdmisionService.class).consultar(admision);
		admision.setEstado(admision_aux.getEstado());
	}

	public static void generarConsulta(Map<String, Object> map)
			throws Exception {
		log.info("ejecutando metodo generarConsulta()");
		String codigo_empresa = (String) map.get("codigo_empresa");
		String codigo_sucursal = (String) map.get("codigo_sucursal");
		String nro_identificacion = (String) map.get("nro_identificacion");
		String nro_ingreso = (String) map.get("nro_ingreso");
		// Timestamp fecha_consulta = (Timestamp) map.get("fecha_ingreso");
		Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) map
				.get("impresion_diagnostica");
		Admision admision = (Admision) map.get("admision");
		String recalculo_factura = (String) map.get("recalculo_factura");
		String codigo_prestador = (String) map.get("codigo_prestador");

		Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
		configuracion_admision_ingreso.setCodigo_empresa(codigo_empresa);
		configuracion_admision_ingreso.setCodigo_sucursal(codigo_sucursal);
		configuracion_admision_ingreso
				.setVia_ingreso(admision.getVia_ingreso());

		configuracion_admision_ingreso = getServiceLocator().getServicio(
				Configuracion_admision_ingresoService.class).consultar(
				configuracion_admision_ingreso);

		if (configuracion_admision_ingreso == null) {
			throw new ValidacionRunTimeException(
					"La via de ingreso "
							+ admision.getVia_ingreso()
							+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
		}

		// Validamos si se puede facturar automatico
		if ((!admision.getParticular().equals("S")
				|| configuracion_admision_ingreso
						.getFac_automatica_particular()
				|| recalculo_factura != null || validarfactAutomaticaParticular(admision
					.getVia_ingreso()))) {
			Double valor_consulta_paquete = (Double) map
					.get("valor_consulta_paquete");

			// boolean particular_aux = admision.getParticular() == null ||
			// admision.getParticular().equals("N");
			Manuales_tarifarios manuales_tarifarios = getManuales_tarifarios(admision);
			Datos_consultaService datos_consultaService = getServiceLocator()
					.getServicio(Datos_consultaService.class);
			PrestadoresService prestadoresService = getServiceLocator()
					.getServicio(PrestadoresService.class);
			AdmisionService admisionService = getServiceLocator().getServicio(
					AdmisionService.class);

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(codigo_prestador);
			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores == null) {
				throw new ValidacionRunTimeException(
						"EL usuario que esta en la session no es un prestador apto para realizar historias clinicas");
			}

			String codigo_consulta = datos_consultaService.getCodigoConsulta(
					admision, prestadores.getTipo_prestador());

			Datos_consulta datos_consulta = new Datos_consulta(false);
			datos_consulta.setCodigo_empresa(codigo_empresa);
			datos_consulta.setCodigo_sucursal(codigo_sucursal);
			datos_consulta.setNro_identificacion(nro_identificacion);
			datos_consulta.setNro_ingreso(nro_ingreso);
			datos_consulta.setCodigo_consulta(codigo_consulta);
			datos_consulta = getServiceLocator().getServicio(
					Datos_consultaService.class).consultar(datos_consulta);
			if (datos_consulta == null) {
				String nro_factura = "";
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTimeInMillis(admision.getFecha_ingreso().getTime());
				gc.set(Calendar.HOUR, 0);
				gc.set(Calendar.MINUTE, 0);
				gc.set(Calendar.SECOND, 0);
				gc.set(Calendar.MILLISECOND, 0);
				// Date fecha_consulta = new Timestamp(gc.getTimeInMillis());
				// if (datos_consulta != null) {
				// nro_factura = datos_consulta.getNro_factura();
				// getServiceLocator().getServicio(Datos_consultaService.class)
				// .eliminar(datos_consulta);
				// }
				map.put("nro_factura", nro_factura);
				Timestamp fecha_final_atencion = new Timestamp(Calendar
						.getInstance().getTimeInMillis());
				if (recalculo_factura != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(admision.getFecha_ingreso());
					calendar.set(Calendar.MINUTE,
							calendar.get(Calendar.MINUTE) + 9);
					fecha_final_atencion = new Timestamp(
							calendar.getTimeInMillis());
				}
				map.put("fecha_consulta", fecha_final_atencion);
				map.put("finalidad_consulta",
						impresion_diagnostica.getFinalidad_consulta());
				map.put("tipo_diasnostico",
						impresion_diagnostica.getTipo_principal());
				map.put("codigo_pyp", "");
				map.put("codigo_programa", "");
				map.put("causa_externa",
						impresion_diagnostica.getCausas_externas());
				map.put("manuales_tarifarios", manuales_tarifarios);
				map.put("valor_consulta_paquete", valor_consulta_paquete);
				map.put("codigo_consulta", codigo_consulta);
				map.put("prestadores", prestadores);
				// log.info("Guardar consulta automatica ");
				datos_consultaService.guardarDatosConsultasAut(map);
				// Actualizamos la fecha de atencion del paciente
				admision.setFecha_atencion(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				Map<String, Object> parametro = new HashMap<String, Object>();
				parametro.put("codigo_empresa", admision.getCodigo_empresa());
				parametro.put("codigo_sucursal", admision.getCodigo_sucursal());
				parametro.put("nro_ingreso", admision.getNro_ingreso());
				parametro.put("nro_identificacion",
						admision.getNro_identificacion());
				if (!datos_consultaService.existe(parametro)) {
					admision.setCausa_externa(impresion_diagnostica
							.getCausas_externas());
					Map<String, Object> map_aux = new HashMap<String, Object>();
					map_aux.put("admision", admision);
					// map.put("parametros_empresa", parametros_empresa);
					admisionService.actualizar(map_aux);
				}
				// Este metodo me indica si va ha facturar automaticamente
				facturacionAutomatica(admision, recalculo_factura != null);
			} else {
				// En el caso que ya exista la consulta solo vamos a cambiar los
				// datos
				datos_consulta
						.setCodigo_diagnostico_principal(impresion_diagnostica
								.getCie_principal());
				datos_consulta.setCodigo_diagnostico1(impresion_diagnostica
						.getCie_relacionado1());
				datos_consulta.setCodigo_diagnostico2(impresion_diagnostica
						.getCie_relacionado2());
				datos_consulta.setCodigo_diagnostico3(impresion_diagnostica
						.getCie_relacionado3());
				datos_consulta.setCausa_externa(impresion_diagnostica
						.getCausas_externas());
				datos_consulta.setFinalidad_consulta(impresion_diagnostica
						.getFinalidad_consulta());
				datos_consultaService.actualizarRegistro(datos_consulta);
			}
		}
	}

	public static Via_ingreso_consultas getVia_ingreso_consultas(
			Admision admision) {
		try {
			Via_ingreso_consultas via_ingreso_consultas = new Via_ingreso_consultas();
			via_ingreso_consultas.setCodigo_empresa(admision
					.getCodigo_empresa());
			via_ingreso_consultas.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			via_ingreso_consultas.setVia_ingreso(admision.getVia_ingreso());

			via_ingreso_consultas = getServiceLocator().getServicio(
					Via_ingreso_consultasService.class).consultar(
					via_ingreso_consultas);

			if (via_ingreso_consultas == null) {
				throw new ValidacionRunTimeException(
						"Este servicio no esta disponible para este paciente, por favor verifique los contratos");
			}
			return via_ingreso_consultas;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public static Detalle_orden convertirDttGrupoDttOrden(
			Detalle_grupos_procedimientos dtt_grupo,
			Detalle_orden detalle_orden, Manuales_tarifarios manuales_tarifarios) {
		try {
			Detalle_orden detalle = Res.clonar(detalle_orden);
			Map<String, Object> mapProcedimieto = Utilidades.getProcedimiento(
					manuales_tarifarios,
					new Long(dtt_grupo.getId_procedimiento()),
					getServiceLocator());

			Double valor_procedimiento = (Double) mapProcedimieto
					.get("valor_pcd");
			Double descuento = (Double) mapProcedimieto.get("descuento");
			Double incremento = (Double) mapProcedimieto.get("incremento");
			Double valor_real = (Double) mapProcedimieto.get("valor_real");

			detalle.setCodigo_procedimiento(dtt_grupo.getId_procedimiento());
			detalle.setValor_procedimiento(valor_procedimiento);
			detalle.setDescuento(descuento);
			detalle.setIncremento(incremento);
			detalle.setValor_real(valor_real);
			detalle.setCodigo_cups((String) mapProcedimieto.get("codigo_cups"));
			detalle.setRealizado("N");
			detalle.setUnidades_realizadas(0);
			return detalle;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public static Map<String, Object> getProcedimiento(
			Manuales_tarifarios manual, String id_procedimiento)
			throws Exception {
		return Utilidades.getProcedimiento(manual, new Long(id_procedimiento),
				getServiceLocator());
	}

	public static boolean getCupsContratado(String codigo_cups,
			String via_ingreso, Manuales_tarifarios manuales_tarifarios) {
		boolean retorno = false;
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa",
				manuales_tarifarios.getCodigo_empresa());
		parametros.put("codigo_sucursal",
				manuales_tarifarios.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				manuales_tarifarios.getCodigo_administradora());
		parametros.put("id_contrato", manuales_tarifarios.getId_contrato());
		if (via_ingreso.equals(IVias_ingreso.LABORATORIOS)) {

		} else {
			List<Servicios_contratados> listado_servicios_contratados = getServiceLocator()
					.getServicio(Servicios_contratadosService.class).listar(
							parametros);
			for (Servicios_contratados servicios_contratados : listado_servicios_contratados) {
				parametros.put("codigo_procedimiento", codigo_cups);
				parametros.put("codigo_padre",
						servicios_contratados.getCodigo_servicio());
				parametros.put("tipo", "02");
				retorno = getServiceLocator().getServicio(
						Servicios_disponiblesService.class).existe(parametros);
				if (retorno) {
					break;
				}
			}
		}

		return retorno;

	}

	public static boolean getServicioContratado(String codigo_servicio,
			Paciente paciente) {
		boolean retorno = false;
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", paciente.getCodigo_empresa());
		parametros.put("codigo_sucursal", paciente.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				paciente.getCodigo_administradora());
		parametros.put("codigo_servicio_especifico", codigo_servicio);

		retorno = getServiceLocator().getServicio(
				Servicios_contratadosService.class).existe(parametros);
		return retorno;
	}

	/**
	 * Este metodo me permite generar los datos procedimientos para la agudeza
	 * visual
	 *
	 * @param admision
	 * @param Map
	 *            procedimiento
	 * @param codigo
	 *            _procedimiento
	 * @param Manuales_tarifarios
	 * @author Manuel Aguialar
	 *
	 */
	public static void generarDatosProcedimientos(Admision admision,
			String codigo_procedimiento, Map<String, Object> procedimiento,
			Manuales_tarifarios manual) {

		double valor_pcd = 0;
		double valor_real = 0;
		double descuento = 0, incremento = 0;
		String codigo_cups = "";

		valor_pcd = (Double) procedimiento.get("valor_pcd");

		valor_real = (Double) procedimiento.get("valor_real");
		descuento = (Double) procedimiento.get("descuento");
		incremento = (Double) procedimiento.get("incremento");
		codigo_cups = (String) procedimiento.get("codigo_cups");

		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento.setCodigo_empresa(admision.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(admision.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);
		datos_procedimiento.setTipo_identificacion(admision.getPaciente()
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(admision
				.getNro_identificacion());
		datos_procedimiento.setCodigo_cups(codigo_cups);
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(codigo_procedimiento);
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento.setValor_procedimiento(valor_pcd);
		datos_procedimiento.setUnidades(1);
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento.setValor_neto(valor_pcd);
		datos_procedimiento
				.setAmbito_procedimiento(IDatosProcedimientos.AMBITO_REALIZACION);
		datos_procedimiento
				.setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
		datos_procedimiento
				.setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
		datos_procedimiento
				.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
		datos_procedimiento.setCodigo_diagnostico_principal(admision
				.getDiagnostico_ingreso());
		datos_procedimiento.setCodigo_diagnostico_relacionado("");
		datos_procedimiento.setComplicacion("");
		datos_procedimiento.setCancelo_copago("N");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(admision.getCreacion_user());
		datos_procedimiento.setUltimo_user(admision.getCreacion_user());
		datos_procedimiento.setValor_real(valor_real);
		datos_procedimiento.setDescuento(descuento);
		datos_procedimiento.setIncremento(incremento);
		datos_procedimiento
				.setRealizado_en(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
		getServiceLocator().getDatos_procedimientoService().crear(
				datos_procedimiento);
	}

	/**
	 * Este metodo me permite crear un paciente que signifique que no existe
	 *
	 */
	public static Paciente getPacienteNoExistente(String codigo_empresa,
			String codigo_sucursal) {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(IConstantes._NRO_IDENTIFICACION_NO_EXISTENTE);
		Paciente pacienteTemp = getServiceLocator().getServicio(
				PacienteService.class).consultar(paciente);

		if (pacienteTemp == null) {
			paciente.setTipo_identificacion(IConstantes._TIPO_IDENTIFICACION_NO_EXISTENTE);
			paciente.setCodigo_administradora(IConstantes._TIPO_ADMINISTRADORA_NO_EXISTENTE);
			paciente.setTipo_usuario("");
			paciente.setApellido1("");
			paciente.setApellido2("");
			paciente.setNombre1("PACIENTE NO EXISTE");
			paciente.setNombre2("");
			paciente.setFecha_nacimiento(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			paciente.setEdad("");
			paciente.setUnidad_medidad("");
			paciente.setSexo("");
			paciente.setCodigo_dpto("");
			paciente.setCodigo_municipio("");
			paciente.setZona("");
			paciente.setLugar_exp("");
			paciente.setProfesion("");
			// paciente.setTel_oficina(String.valueOf(dbxTel_oficina
			// .getValue()));
			paciente.setTel_oficina("");
			// paciente.setTel_res(String.valueOf(dbxTel_res.getValue()));
			paciente.setTel_res("");
			paciente.setDireccion("");
			paciente.setEstado_civil("");
			paciente.setPaciente_particula("N");
			paciente.setEstrato("");
			paciente.setTipo_afiliado("");
			paciente.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			paciente.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			paciente.setCreacion_user("TEMP");
			paciente.setUltimo_user("TEMP");
			paciente.setIngresos(0);
			paciente.setLogin("");
			paciente.setPassword("");
			paciente.setNivel_sisben("");

			paciente.setPertenencia_etnica("");
			paciente.setCodigo_educativo("");
			paciente.setCodigo_ocupacion("");
			paciente.setArea_paciente("");
			paciente.setNivel_sisben("");
			// creamos paciente si no existe
			getServiceLocator().getServicio(PacienteService.class).crear(
					paciente, false);
			return paciente;
		} else {
			return pacienteTemp;
		}
	}

	public static String[] getServiciosViaIngresoAux(Sucursal sucursal,
			String via_ingreso) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", sucursal.getCodigo_empresa());
			parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parametros.put("via_ingreso", via_ingreso);

			List<Servicios_via_ingreso> listado = getServiceLocator()
					.getServicio(Servicios_via_ingresoService.class).listar(
							parametros);

			// log.info("listado Servicios_via_ingreso ==> " + listado);
			if (!listado.isEmpty()) {
				String[] retorno = new String[listado.size()];
				int i = 0;
				for (Servicios_via_ingreso servicios_via_ingreso : listado) {
					retorno[i] = servicios_via_ingreso.getCodigo_servicio();
					i++;
				}
				return retorno;
			}

			throw new ValidacionRunTimeException(
					"El servicio con la via ingreso " + via_ingreso
							+ " no existe");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ValidacionRunTimeException(
					"El servicio con la via ingreso " + via_ingreso
							+ " no existe");
		}
	}

	public static String getViaIngreso(Sucursal sucursal, String codigo_servicio) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", sucursal.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("codigo_servicio", codigo_servicio);
		List<Servicios_via_ingreso> listado = getServiceLocator().getServicio(
				Servicios_via_ingresoService.class).listar(parametros);
		if (!listado.isEmpty()) {
			return listado.get(0).getVia_ingreso();
		}
		return "";
	}

	public static String[] getServicios(Admision admision) {
		try {
			Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
			configuracion_admision_ingreso.setCodigo_empresa(admision
					.getCodigo_empresa());
			configuracion_admision_ingreso.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			configuracion_admision_ingreso.setVia_ingreso(admision
					.getVia_ingreso());

			configuracion_admision_ingreso = getServiceLocator().getServicio(
					Configuracion_admision_ingresoService.class).consultar(
					configuracion_admision_ingreso);

			if (configuracion_admision_ingreso != null) {
				if (configuracion_admision_ingreso.getLaboratorio_pyp()) {
					if (admision.getPrograma_lab_pyp() != null
							&& admision.getPrograma_lab_pyp().trim().isEmpty()) {
						throw new ValidacionRunTimeException(
								"La via ingreso "
										+ admision.getVia_ingreso()
										+ " no presenta programas relacionados por favor verifique el programa");
					} else {
						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa",
								admision.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								admision.getCodigo_sucursal());
						parametros.put("via_ingreso",
								admision.getPrograma_lab_pyp());

						List<Servicios_via_ingreso> listado = getServiceLocator()
								.getServicio(Servicios_via_ingresoService.class)
								.listar(parametros);

						// //log.info("listado de servicios_via_ingreso ===> " +
						// listado);
						if (!listado.isEmpty()) {
							String[] retorno = new String[listado.size()];
							int i = 0;
							for (Servicios_via_ingreso servicios_via_ingreso : listado) {
								retorno[i] = servicios_via_ingreso
										.getCodigo_servicio();
								i++;
							}
							return retorno;
						}
						throw new ValidacionRunTimeException(
								"El servicio con la via ingreso "
										+ admision.getPrograma_lab_pyp()
										+ " no existe");
					}
				} else {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa",
							admision.getCodigo_empresa());
					parametros.put("codigo_sucursal",
							admision.getCodigo_sucursal());
					parametros.put("via_ingreso", admision.getVia_ingreso());

					List<Servicios_via_ingreso> listado = getServiceLocator()
							.getServicio(Servicios_via_ingresoService.class)
							.listar(parametros);

					// //log.info("listado de servicios_via_ingreso ===> " +
					// listado);
					if (!listado.isEmpty()) {
						String[] retorno = new String[listado.size()];
						int i = 0;
						for (Servicios_via_ingreso servicios_via_ingreso : listado) {
							retorno[i] = servicios_via_ingreso
									.getCodigo_servicio();
							i++;
						}
						return retorno;
					}
					throw new ValidacionRunTimeException(
							"El servicio con la via ingreso "
									+ admision.getVia_ingreso() + " no existe");
				}
			} else {
				throw new ValidacionRunTimeException(
						"La configuracion para la via de ingreso "
								+ admision.getVia_ingreso() + " no existe");
			}

		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public static List<Servicios_via_ingreso> getListadoServiciosViaIngreso(
			Admision admision) {
		try {
			Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
			configuracion_admision_ingreso.setCodigo_empresa(admision
					.getCodigo_empresa());
			configuracion_admision_ingreso.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			configuracion_admision_ingreso.setVia_ingreso(admision
					.getVia_ingreso());

			configuracion_admision_ingreso = getServiceLocator().getServicio(
					Configuracion_admision_ingresoService.class).consultar(
					configuracion_admision_ingreso);

			if (configuracion_admision_ingreso != null) {
				if (configuracion_admision_ingreso.getLaboratorio_pyp()) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa",
							admision.getCodigo_empresa());
					parametros.put("codigo_sucursal",
							admision.getCodigo_sucursal());
					parametros.put("via_ingreso",
							admision.getPrograma_lab_pyp());

					List<Servicios_via_ingreso> listado = getServiceLocator()
							.getServicio(Servicios_via_ingresoService.class)
							.listar(parametros);
					if (!listado.isEmpty()) {
						return listado;
					}

					throw new ValidacionRunTimeException(
							"El servicio con la via ingreso "
									+ admision.getPrograma_lab_pyp()
									+ " no existe");
				} else {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa",
							admision.getCodigo_empresa());
					parametros.put("codigo_sucursal",
							admision.getCodigo_sucursal());
					parametros.put("via_ingreso", admision.getVia_ingreso());

					List<Servicios_via_ingreso> listado = getServiceLocator()
							.getServicio(Servicios_via_ingresoService.class)
							.listar(parametros);
					if (!listado.isEmpty()) {
						return listado;
					}

					throw new ValidacionRunTimeException(
							"El servicio con la via ingreso "
									+ admision.getVia_ingreso() + " no existe");
				}
			} else {
				throw new ValidacionRunTimeException(
						"La configuracion para la via de ingreso "
								+ admision.getVia_ingreso() + " no existe");
			}

		} catch (Exception e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		}
	}

	public static Servicios_contratados getServiciosEspecificos(
			Paciente paciente, ServiceLocatorWeb serviceLocator,
			String codigo_servicio) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(NRO_IDENTIFICACION, paciente.getNro_identificacion());
		params.put(CODIGO_EMPRESA, paciente.getCodigo_empresa());
		params.put(CODIGO_SUCURSAL, paciente.getCodigo_sucursal());
		params.put("codigo_servicio_especifico", codigo_servicio);
		List<Servicios_contratados> listado_servicios = serviceLocator
				.getServicio(Servicios_contratadosService.class)
				.listarServiciosPaciente(params);
		if (listado_servicios.isEmpty()) {
			return null;
		} else {
			return listado_servicios.get(0);
		}
	}

	public static boolean isServicioPyp(String[] codigos_servicios) {
		if (codigos_servicios != null) {
			for (String codigo : codigos_servicios) {
				if (codigo.startsWith(CODSER_PYP)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isServicioPyp(
			Servicios_contratados servicios_contratados) {
		return servicios_contratados.getCodigo_servicio()
				.startsWith(CODSER_PYP);
	}

	/**
	 * Este metodo me permite verificar si el paciente ya se realizó la agudeza
	 * visual en el año especificado
	 *
	 * @param admision
	 * @param a
	 * @author Manuel Aguialar
	 *
	 */
	public static boolean realizarAgudeza(Admision admision, Integer anio) {
		boolean aplica = true;
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_empresa(admision.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(admision.getCodigo_sucursal());
		agudeza_visual.setIdentificacion(admision.getPaciente()
				.getNro_identificacion());
		agudeza_visual.setAnio(anio);

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultarAnio(agudeza_visual);
		if (agudeza_visual != null) {
			aplica = false;

		}
		return aplica;
	}

	public static boolean pagaCuotaModeradora(Paciente paciente) {
		if (paciente.getTipo_usuario().equals(IConstantes.REGIMEN_SUBCIDIADO)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Este metodo te identifica si el contrato contiene servicios de PYP
	 *
	 * @author Luis Miguel
	 * @param contratos
	 *            healthmanager.modelo.bean.Contratos
	 * @return Boolean
	 *
	 */
	public static boolean isContratoPyp(Contratos contratos) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", contratos.getCodigo_empresa());
		params.put("codigo_sucursal", contratos.getCodigo_sucursal());
		params.put("codigo_administradora",
				contratos.getCodigo_administradora());
		params.put("nro_contrato", contratos.getNro_contrato());
		params.put("id_servicio", CODSER_PYP);
		return getServiceLocator().getServicio(ContratosService.class)
				.prestaServiciosPyp(params);
	}

	public static boolean validarResultadosFueraRango(Admision admision) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", admision.getCodigo_empresa());
		map.put("codigo_sucursal", admision.getCodigo_sucursal());
		map.put("nro_identificacion", admision.getNro_identificacion());
		return getServiceLocator().getServicio(
				Resultado_laboratoriosService.class).resultadosFueraRango(map);
	}

	public static boolean tieneExcepcionesPaciente(
			List<Excepciones_pyp> excepciones_pyps, Paciente paciente) {
		if (excepciones_pyps.isEmpty()) {
			return false;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("excp", excepciones_pyps);
			map.put("codigo_empresa", paciente.getCodigo_empresa());
			map.put("codigo_sucursal", paciente.getCodigo_sucursal());
			map.put("nro_identificacion", paciente.getNro_identificacion());
			return getServiceLocator().getDatos_procedimientoService()
					.contieneExcepciones(map);
		}
	}

	public static List<Excepciones_pyp> getExcepecionesPaciente(
			Paciente paciente, String codigo_pyp) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo", codigo_pyp);
		map.put("codigo_empresa", paciente.getCodigo_empresa());
		map.put("codigo_sucursal", paciente.getCodigo_sucursal());
		map.put("nro_identificacion", paciente.getNro_identificacion());
		return getServiceLocator().getServicio(Excepciones_pypService.class)
				.listar(map);
	}

	public static void ordenarProcedimientosQuirurgicos(
			List<Datos_procedimiento> lista_pro, Manuales_tarifarios manual)
			throws Exception {

		Utilidades.ordenarProcedimientosQuirurgicos(lista_pro, manual,
				getServiceLocator());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getFiltroProcedimientos(Admision admision,
			boolean via_ingreso) {
		List<String> listado = new ArrayList<String>();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", admision.getCodigo_empresa());
		parametros.put("codigo_sucursal", admision.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("nro_identificacion", admision.getNro_identificacion());

		List listado_servicios = new ArrayList();

		if (via_ingreso) {
			List<Servicios_via_ingreso> codigos_servicios = getListadoServiciosViaIngreso(admision);

			for (Servicios_via_ingreso servicios_via_ingreso : codigos_servicios) {
				parametros.put("codigo_servicio_especifico",
						servicios_via_ingreso.getCodigo_servicio());
				if (admision.getParticular().equalsIgnoreCase("S")) {
					parametros.put("id_contrato", admision.getId_plan());
					if (getServiceLocator().getServicio(
							Servicios_contratadosService.class)
							.getTieneContratoServicioParticular(parametros)) {
						listado_servicios.add(servicios_via_ingreso
								.getCodigo_servicio());
					}
				} else {
					if (getServiceLocator().getServicio(
							Servicios_contratadosService.class)
							.getTieneContratoServicio(parametros)) {
						listado_servicios.add(servicios_via_ingreso
								.getCodigo_servicio());
					}
				}

			}
		} else {
			if (admision.getParticular().equalsIgnoreCase("S")) {
				parametros.put("id_contrato", admision.getId_plan());
				listado_servicios = getServiceLocator().getServicio(
						Servicios_contratadosService.class)
						.listarServiciosParticular(parametros);
			} else {
				listado_servicios = getServiceLocator().getServicio(
						Servicios_contratadosService.class)
						.listarServiciosPaciente(parametros);
			}
		}

		List<Object> listado_servicios_verificados = new ArrayList<Object>();
		parametros.clear();
		parametros.put("codigo_empresa", admision.getCodigo_empresa());
		parametros.put("codigo_sucursal", admision.getCodigo_sucursal());
		parametros.put("id_contrato", admision.getId_plan());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());

		// //log.info("listado_servicios ==> "+listado_servicios);
		for (Object object : listado_servicios) {
			if (object instanceof Servicios_contratados) {
				listado_servicios_verificados.add(object);
			} else {
				parametros.put("codigo_servicio_especifico", object.toString());
				List<String> listado_aux = getServiceLocator().getServicio(
						Servicios_contratadosService.class).listar_codigos(
						parametros);
				// log.info("parametros de busqueda ===> " + parametros);
				if (listado_aux.isEmpty()) {
					listado_servicios_verificados.add(object.toString());
				} else {
					listado_servicios_verificados.addAll(listado_aux);
				}
			}
		}

		// //log.info("listado_servicios_verificados ===> "
		// + listado_servicios_verificados.size());
		Map<String, String> niveles_labotarorios = new HashMap<String, String>();

		for (Object object : listado_servicios_verificados) {
			String codigo_bus = "";
			if (object instanceof Servicios_contratados) {
				codigo_bus = ((Servicios_contratados) object)
						.getCodigo_servicio();
			} else {
				codigo_bus = object.toString();
			}
			parametros.put("codigo_bus", codigo_bus);
			parametros.put("tipo", "02");
			parametros.put("not_null_procedimiento", "not_null_procedimiento");
			List<String> listado_disponibles = getServiceLocator().getServicio(
					Servicios_disponiblesService.class).listar_codigos(
					parametros);
			listado.addAll(listado_disponibles);

			for (int i = 1; i <= 4; i++) {
				if (codigo_bus.startsWith(CODSER_LABORATORIO_CLINICO + "." + i)
						|| (CODSER_LABORATORIO_CLINICO + "." + i)
								.startsWith(codigo_bus)) {
					niveles_labotarorios.put("" + i, "" + i);
				}

				if (codigo_bus.startsWith(CODSER_LABORATORIO_CLINICO_HOS + "."
						+ i)
						|| (CODSER_LABORATORIO_CLINICO_HOS + "." + i)
								.startsWith(codigo_bus)) {
					niveles_labotarorios.put("" + i, "" + i);
				}

				if (codigo_bus.startsWith(CODSER_LABORATORIO_CLINICO_URG + "."
						+ i)
						|| (CODSER_LABORATORIO_CLINICO_URG + "." + i)
								.startsWith(codigo_bus)) {
					niveles_labotarorios.put("" + i, "" + i);
				}

			}
		}

		if (!niveles_labotarorios.isEmpty()) {
			parametros.put("niveles_filtro", niveles_labotarorios.values());
			parametros.put("tipo_procedimiento_bus", TIPO_LABORATORIO_CLINICO);
			List<String> listado_laboratorios = getServiceLocator()
					.getProcedimientosService().listar_cups(parametros);
			listado.addAll(listado_laboratorios);
		}

		// log.info("listado cups filtros ==> " + listado);
		return listado;
	}

	/**
	 * Este metodo me crea un bean el cual relaciona un manual tarifario con
	 * servicio_contratado o servicio.
	 *
	 * @author Luis Miguel
	 *
	 */
	public static Servicios_contratados generarServiciosContratadosPara(
			Manuales_tarifarios manuales_tarifarios,
			Servicios_disponibles servicios_disponibles) {
		Servicios_contratados servicios_contratados = new Servicios_contratados();
		servicios_contratados.setCodigo_empresa(manuales_tarifarios
				.getCodigo_empresa());
		servicios_contratados.setCodigo_sucursal(manuales_tarifarios
				.getCodigo_sucursal());
		servicios_contratados.setCodigo_administradora(manuales_tarifarios
				.getCodigo_administradora());
		servicios_contratados.setConsecutivo_mt(manuales_tarifarios
				.getConsecutivo());
		servicios_contratados.setCreacion_date(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		servicios_contratados.setCreacion_user(manuales_tarifarios
				.getCreacion_user());
		servicios_contratados.setId_contrato(manuales_tarifarios
				.getId_contrato());
		servicios_contratados.setCodigo_servicio(servicios_disponibles
				.getCodigo());
		return servicios_contratados;
	}

	/**
	 * Este metodo me permite filtrar los servicios que tiene disponible un
	 * contrato
	 *
	 * @author Luis Miguel Hernandez Perez
	 *
	 */
	public static List<String> getFiltroServiciosPermitidosPorContrato(
			String codigo_empresa, String codigo_sucursal,
			String codigo_administradora, String id_plan) {
		return getFiltroServiciosPermitidos(codigo_empresa, codigo_sucursal,
				codigo_administradora, id_plan, null);
	}

	/**
	 * Este metodo me permite filtrar los servicios que tiene disponible por
	 * configuracion para la CAJA DE PREVISION
	 *
	 * @author Luis Miguel Hernandez Perez
	 *
	 */
	public static List<String> getFiltroServiciosPermitidosPorConfiguracion(
			Long id_configuracion) {
		return getFiltroServiciosPermitidos(null, null, null, null,
				id_configuracion, null, NIVEL_CONSULTA.CONFIGURACION);
	}

	public static List<String> getFiltroServiciosPermitidos(
			String codigo_empresa, String codigo_sucursal,
			String codigo_administradora, String id_plan, Long id_configuracion) {
		return getFiltroServiciosPermitidos(codigo_empresa, codigo_sucursal,
				codigo_administradora, id_plan, id_configuracion, null,
				NIVEL_CONSULTA.CONFIGURACION_CONTRATADA);
	}

	/**
	 * Este metodo me permite filtrar los servicios que tiene disponible un
	 * contrato
	 *
	 * @author Luis Miguel Hernandez Perez
	 * @param solamenteConfiguracion
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getFiltroServiciosPermitidos(
			String codigo_empresa, String codigo_sucursal,
			String codigo_administradora, String id_plan,
			Long id_configuracion, Long id_manual, NIVEL_CONSULTA nivel_consulta) {
		List<String> listado = new ArrayList<String>();

		// parametros
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_administradora", codigo_administradora);
		parametros.put("id_plan", id_plan);
		parametros.put("id_configuracion", id_configuracion);
		parametros.put("consecutivo_mt", id_manual);

		// consultamos servicios
		List listado_servicios_verificados = null;
		if (nivel_consulta == NIVEL_CONSULTA.CONFIGURACION) {
			listado_servicios_verificados = getServiceLocator().getServicio(
					Configuracion_servicios_autorizacionService.class)
					.listarServiciosPermitidos(parametros);
		} else if (nivel_consulta == NIVEL_CONSULTA.CONFIGURACION_CONTRATADA) {
			listado_servicios_verificados = getServiceLocator().getServicio(
					Servicios_contratadosService.class)
					.listarServiciosPermitidosPorConfiguracion(parametros);
		} else {
			listado_servicios_verificados = getServiceLocator().getServicio(
					Servicios_contratadosService.class)
					.listarServiciosPermitidos(parametros);
		}

		// Validamos en los servicios de laboratorio
		Map<String, String> niveles_labotarorios = new HashMap<String, String>();
		boolean procedimientos_quirurgicos = false;
		for (Object object : listado_servicios_verificados) {
			String codigo_bus = "";
			if (object instanceof Servicios_contratados) {
				codigo_bus = ((Servicios_contratados) object)
						.getCodigo_servicio();
			} else {
				codigo_bus = object.toString();
			}
			parametros.put("codigo_bus", codigo_bus);
			parametros.put("tipo", "02");
			parametros.put("not_null_procedimiento", "not_null_procedimiento");
			List<String> listado_disponibles = getServiceLocator().getServicio(
					Servicios_disponiblesService.class).listar_codigos(
					parametros);
			listado.addAll(listado_disponibles);

			// consultar los procedimientos quirurgicos de la caja
			if (codigo_bus.equalsIgnoreCase(CODSER_SERVICIOS_CIRUGIA)) {
				procedimientos_quirurgicos = true;
			}

			for (int i = 1; i <= 4; i++) {
				if (codigo_bus.startsWith(CODSER_LABORATORIO_CLINICO + "." + i)
						|| (CODSER_LABORATORIO_CLINICO + "." + i)
								.startsWith(codigo_bus)) {
					niveles_labotarorios.put("" + i, "" + i);
				}

				if (codigo_bus.startsWith(CODSER_LABORATORIO_CLINICO_HOS + "."
						+ i)
						|| (CODSER_LABORATORIO_CLINICO_HOS + "." + i)
								.startsWith(codigo_bus)) {
					niveles_labotarorios.put("" + i, "" + i);
				}

				if (codigo_bus.startsWith(CODSER_LABORATORIO_CLINICO_URG + "."
						+ i)
						|| (CODSER_LABORATORIO_CLINICO_URG + "." + i)
								.startsWith(codigo_bus)) {
					niveles_labotarorios.put("" + i, "" + i);
				}

			}
		}

		if (!niveles_labotarorios.isEmpty()) {
			parametros.put("niveles_filtro", niveles_labotarorios.values());
			parametros.put("tipo_procedimiento_bus", TIPO_LABORATORIO_CLINICO);
			List<String> listado_laboratorios = getServiceLocator()
					.getProcedimientosService().listar_cups(parametros);
			listado.addAll(listado_laboratorios);
		}

		// para consultar los procedimientos quirurgicos de la caja
		if (procedimientos_quirurgicos) {
			parametros.put("quirurgico_bus", "S");
			List<String> listado_procedimientos_quirurgicos = getServiceLocator()
					.getProcedimientosService().listar_cups(parametros);
			listado.addAll(listado_procedimientos_quirurgicos);
		}

		return listado;
	}

	public static String getAmbitoRealizacion(Admision admision) {
		if (admision != null) {
			if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)
					|| admision.getVia_ingreso().equals(
							IVias_ingreso.URGENCIA_ODONTOLOGICO)) {
				return IConstantes.AMBITO_REALIZACION_PROCEDIMIENTO_URGENCIAS;
			} else if (admision.getVia_ingreso().equals(
					IVias_ingreso.HOSPITALIZACIONES)) {
				return IConstantes.AMBITO_REALIZACION_PROCEDIMIENTO_HOSPITALARIO;
			} else {
				return IConstantes.AMBITO_REALIZACION_PROCEDIMIENTO_AMBULATORIO;
			}
		} else {
			throw new ValidacionRunTimeException(
					"Para registrar el procedimiento debe existir una admision");
		}
	}

	public static ServiceLocatorWeb getServiceLocator() {
		if (Executions.getCurrent() != null) {
			return ServiceLocatorWeb.getInstance();
		} else {
			return ServiceLocatorWeb.getInstance();
		}
	}

}
