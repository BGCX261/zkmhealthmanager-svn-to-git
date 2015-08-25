/*
 * ficha_epidemiologia_n10Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Excepciones_pyp;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.Excepciones_pypService;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.VacunasService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Area;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.helper.SimpleCategoryModelHelper;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.ManejadorPoblacion;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;

public class Graficos_poblacionAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Graficos_poblacionAction.class);

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxAdministradora;

	@View
	private Toolbarbutton btnLimpiarAdministradora;

	@View
	private Chart barcharGrafico;

	private final int BUFFER = 2048;

	@View
	private Toolbarbutton tbnExportar;

	// poblacion real
	@View
	private Listcell listcellDetencion_cancer_seno;
	@View
	private Listcell listcellSalud_oral;
	@View
	private Listcell listcellAlteracion_menor_10_anios;
	@View
	private Listcell listcellAgudeza_visual;
	@View
	private Listcell listcellRecien_nacido;
	@View
	private Listcell listcellDesarrollo_joven;
	@View
	private Listcell listcellCancer_cuello_uterino;
	@View
	private Listcell listcellPlanificacion_familiar;
	@View
	private Listcell listcellAdultoMayor;
	@View
	private Listcell listcellVacunacion;

	// excepciones
	@View
	private Listcell listcellDetencion_cancer_senoExcepciones;
	@View
	private Listcell listcellSalud_oralExcepciones;
	@View
	private Listcell listcellAlteracion_menor_10_aniosExcepciones;
	@View
	private Listcell listcellAgudeza_visualExcepciones;
	@View
	private Listcell listcellRecien_nacidoExcepciones;
	@View
	private Listcell listcellDesarrollo_jovenExcepciones;
	@View
	private Listcell listcellCancer_cuello_uterinoExcepciones;
	@View
	private Listcell listcellPlanificacion_familiarExcepciones;
	@View
	private Listcell listcellAdultoMayorExcepciones;
	@View
	private Listcell listcellVacunacionExcepciones;

	// totales
	@View
	private Listcell listcellDetencion_cancer_senoTotal;
	@View
	private Listcell listcellSalud_oralTotal;
	@View
	private Listcell listcellAlteracion_menor_10_aniosTotal;
	@View
	private Listcell listcellAgudeza_visualTotal;
	@View
	private Listcell listcellRecien_nacidoTotal;
	@View
	private Listcell listcellDesarrollo_jovenTotal;
	@View
	private Listcell listcellCancer_cuello_uterinoTotal;
	@View
	private Listcell listcellPlanificacion_familiarTotal;
	@View
	private Listcell listcellAdultoMayorTotal;
	@View
	private Listcell listcellVacunacionTotal;

	@View
	private Listfooter listfooterTotales;
	@View
	private Listfooter listfooterExcepciones;

	/* poblacion real */
	private List<Map<String, Object>> LIST_TOTAL_DETENCION_CANCER_SENO = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_SALUD_ORAL = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_ALTERACION_MENOR_10_ANIOS = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_AGUDEZA_VISUAL = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_RECIEN_NACIDO = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_DESARROLLO_JOVEN = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_CANCER_CUELLO_UTERINO = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_PLANIFICACION_FAMILIAR = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_ADULTO_MAYOR = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_VACUNACION = new ArrayList<Map<String, Object>>();

	/* excepciones */
	private List<Map<String, Object>> LIST_TOTAL_DETENCION_CANCER_SENO_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_SALUD_ORAL_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_AGUDEZA_VISUAL_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_RECIEN_NACIDO_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_DESARROLLO_JOVEN_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_ADULTO_MAYOR_EXP = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> LIST_TOTAL_VACUNACION_EXP = new ArrayList<Map<String, Object>>();

	/* listado excepciones */
	@InyectarExcepciones(codigo_programa_pyp = ServiciosDisponiblesUtils.CODSER_PYP_CANCER_SENO)
	private List<Excepciones_pyp> LIST_DETENCION_CANCER_SENO_EXP = new ArrayList<Excepciones_pyp>();
	@InyectarExcepciones(codigo_programa_pyp = ServiciosDisponiblesUtils.CODSER_PYP_SALUD_ORAL)
	private List<Excepciones_pyp> LIST_SALUD_ORAL_EXP = new ArrayList<Excepciones_pyp>();
	@InyectarExcepciones(codigo_programa_pyp = ServiciosDisponiblesUtils.CODSER_PYP_CRECIMIENTO_DESARROLLO)
	private List<Excepciones_pyp> LIST_ALTERACION_MENOR_10_ANIOS_EXP = new ArrayList<Excepciones_pyp>();
	@InyectarExcepciones(codigo_programa_pyp = ServiciosDisponiblesUtils.CODSER_PYP_AGUDEZA_VISUAL)
	private List<Excepciones_pyp> LIST_AGUDEZA_VISUAL_EXP = new ArrayList<Excepciones_pyp>();
	@InyectarExcepciones(codigo_programa_pyp = ServiciosDisponiblesUtils.CODSER_PYP_DESARROLLO_JOVEN)
	private List<Excepciones_pyp> LIST_DESARROLLO_JOVEN_EXP = new ArrayList<Excepciones_pyp>();
	@InyectarExcepciones(codigo_programa_pyp = ServiciosDisponiblesUtils.CODSER_PYP_CUELLO_UTERINO)
	private List<Excepciones_pyp> LIST_CANCER_CUELLO_UTERINO_EXP = new ArrayList<Excepciones_pyp>();
	@InyectarExcepciones(codigo_programa_pyp = ServiciosDisponiblesUtils.CODSER_PYP_PLANIFICACION_MUJERES)
	private List<Excepciones_pyp> LIST_PLANIFICACION_FAMILIAR_EXP = new ArrayList<Excepciones_pyp>();
	@InyectarExcepciones(codigo_programa_pyp = ServiciosDisponiblesUtils.CODSER_PYP_ADULTO_MAYOR)
	private List<Excepciones_pyp> LIST_ADULTO_MAYOR_EXP = new ArrayList<Excepciones_pyp>();

	private SimpleCategoryModelHelper categoryModel;

	/**
	 * Este anotacion me permite identificar las listas a las cuales se les va
	 * ha inyectar las excepciones de dicho programa.
	 * 
	 * @author Luis Miguel
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface InyectarExcepciones {
		String codigo_programa_pyp();
	}

	@Override
	public void init() throws Exception {
		parametrizarBandboxAdministradora();
		inyectarExcepcionesCorrespondientesProgramas();
	}

	@Override
	public void params(Map<String, Object> map) {

	}

	private void parametrizarBandboxAdministradora() {
		bandboxAdministradora.inicializar(new Textbox(),
				btnLimpiarAdministradora);
		bandboxAdministradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getNit() + " "
								+ registro.getNombre());
						textboxInformacion.setValue(registro.getCodigo());
						((BandboxRegistrosMacro) bandbox).setObject(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						barcharGrafico.setModel(new SimpleCategoryModel());
						listcellDetencion_cancer_seno.setLabel("");
						listcellSalud_oral.setLabel("");
						listcellAlteracion_menor_10_anios.setLabel("");
						listcellAgudeza_visual.setLabel("");
						listcellRecien_nacido.setLabel("");
						listcellDesarrollo_joven.setLabel("");
						listcellCancer_cuello_uterino.setLabel("");
						listcellPlanificacion_familiar.setLabel("");
						listfooterTotales.setLabel("");
						tbnExportar.setVisible(false);
					}
				});
	}

	private Map<String, List<Plantilla_pyp>> getActividadesPendientes(
			Paciente paciente, String servicio) {
		return ManejadorPoblacion.getActividadesPendientes(paciente, servicio);
	}

	/**
	 * Este metodo devuelve si tiene excepciones
	 * */
	private boolean cargarDatosEnLista(Paciente paciente,
			List<Map<String, Object>> lista_total,
			List<Map<String, Object>> lista_total_excepcion, String servicio,
			List<Excepciones_pyp> lista_excepciones_programa) {
		boolean tieneExcepciones = false;
		if (lista_excepciones_programa != null)
			tieneExcepciones = ServiciosDisponiblesUtils
					.tieneExcepcionesPaciente(lista_excepciones_programa,
							paciente);
		// Cargamos las actividades realizadas y las pendientes con la fecha

		Map<String, List<Plantilla_pyp>> map = getActividadesPendientes(
				paciente, servicio);

		if (tieneExcepciones) {
			lista_total_excepcion.add(getMapa(paciente, map
					.get(ManejadorPoblacion.ACTIVIDADESA_PENDIENTES), map
					.get(ManejadorPoblacion.ACTIVIDADESA_REALIZADAS),
					ServiciosDisponiblesUtils.getExcepecionesPaciente(paciente,
							servicio)));
			return true;
		} else {
			lista_total.add(getMapa(paciente,
					map.get(ManejadorPoblacion.ACTIVIDADESA_PENDIENTES),
					map.get(ManejadorPoblacion.ACTIVIDADESA_REALIZADAS)));
		}
		return false;
	}

	public void generarGraficos() {
		try {
			// poblacion real
			LIST_TOTAL_AGUDEZA_VISUAL.clear();
			LIST_TOTAL_ALTERACION_MENOR_10_ANIOS.clear();
			LIST_TOTAL_CANCER_CUELLO_UTERINO.clear();
			LIST_TOTAL_DESARROLLO_JOVEN.clear();
			LIST_TOTAL_DETENCION_CANCER_SENO.clear();
			LIST_TOTAL_PLANIFICACION_FAMILIAR.clear();
			LIST_TOTAL_RECIEN_NACIDO.clear();
			LIST_TOTAL_SALUD_ORAL.clear();
			LIST_TOTAL_ADULTO_MAYOR.clear();
			LIST_TOTAL_VACUNACION.clear();

			// excepciones
			LIST_TOTAL_AGUDEZA_VISUAL_EXP.clear();
			LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP.clear();
			LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP.clear();
			LIST_TOTAL_DESARROLLO_JOVEN_EXP.clear();
			LIST_TOTAL_DETENCION_CANCER_SENO_EXP.clear();
			LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP.clear();
			LIST_TOTAL_RECIEN_NACIDO_EXP.clear();
			LIST_TOTAL_SALUD_ORAL_EXP.clear();
			LIST_TOTAL_ADULTO_MAYOR_EXP.clear();
			LIST_TOTAL_VACUNACION_EXP.clear();

			int contador_excepciones = 0;
			if (bandboxAdministradora.getRegistroSeleccionado() != null) {
				Administradora administradora = (Administradora) bandboxAdministradora
						.getRegistroSeleccionado();

				List<Esquema_vacunacion> list_Esquema_vacunacions = ManejadorPoblacion
						.getListEsquema_vacunacions(getSucursal(),
								getServiceLocator());

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", codigo_empresa);
				parametros.put("codigo_sucursal", codigo_sucursal);
				parametros.put("codigo_administradora",
						administradora.getCodigo());
				parametros.put("tiene_servicio_contratado",
						ServiciosDisponiblesUtils.CODSER_PYP);

				List<Paciente> listado_pacientes = getServiceLocator()
						.getPacienteService().listarGraficos(parametros);

				//log.info("listado_pacientes ===> " + listado_pacientes.size());

				for (Paciente paciente : listado_pacientes) {
					List<String> listado_programas = ManejadorPoblacion
							.obtenerListadoProgrmas(paciente);

					for (String programa : listado_programas) {
						if (programa.equals(ManejadorPoblacion.AGUDEZA_VISUAL)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_AGUDEZA_VISUAL,
									LIST_TOTAL_AGUDEZA_VISUAL_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_AGUDEZA_VISUAL,
									LIST_AGUDEZA_VISUAL_EXP)) {
								contador_excepciones++;
							}
						} else if (programa
								.equals(ManejadorPoblacion.ALTERACION_MENOR_10_ANIOS)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_ALTERACION_MENOR_10_ANIOS,
									LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_CRECIMIENTO_DESARROLLO,
									LIST_ALTERACION_MENOR_10_ANIOS_EXP)) {
								contador_excepciones++;
							}
						} else if (programa
								.equals(ManejadorPoblacion.CANCER_CUELLO_UTERINO)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_CANCER_CUELLO_UTERINO,
									LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_CANCER_SENO,
									LIST_CANCER_CUELLO_UTERINO_EXP)) {
								contador_excepciones++;
							}
						} else if (programa
								.equals(ManejadorPoblacion.DESARROLLO_JOVEN)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_DESARROLLO_JOVEN,
									LIST_TOTAL_DESARROLLO_JOVEN_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_DESARROLLO_JOVEN,
									LIST_DESARROLLO_JOVEN_EXP)) {
								contador_excepciones++;
							}
						} else if (programa
								.equals(ManejadorPoblacion.DETENCION_CANCER_SENO)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_DETENCION_CANCER_SENO,
									LIST_TOTAL_DETENCION_CANCER_SENO_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_CANCER_SENO,
									LIST_DETENCION_CANCER_SENO_EXP)) {
								contador_excepciones++;
							}
						} else if (programa
								.equals(ManejadorPoblacion.PLANIFICACION_FAMILIAR)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_PLANIFICACION_FAMILIAR,
									LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_PLANIFICACION_MUJERES,
									LIST_PLANIFICACION_FAMILIAR_EXP)) {
								contador_excepciones++;
							}
						} else if (programa
								.equals(ManejadorPoblacion.RECIEN_NACIDO)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_RECIEN_NACIDO,
									LIST_TOTAL_RECIEN_NACIDO_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_RECIEN_NACIDO,
									null)) {
								contador_excepciones++;
							}
						} else if (programa
								.equals(ManejadorPoblacion.SALUD_ORAL)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_SALUD_ORAL,
									LIST_TOTAL_SALUD_ORAL_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_SALUD_ORAL,
									LIST_SALUD_ORAL_EXP)) {
								contador_excepciones++;
							}
						} else if (programa
								.equals(ManejadorPoblacion.ADULTO_MAYOR)) {
							if (cargarDatosEnLista(
									paciente,
									LIST_TOTAL_ADULTO_MAYOR,
									LIST_TOTAL_ADULTO_MAYOR_EXP,
									ServiciosDisponiblesUtils.CODSER_PYP_ADULTO_MAYOR,
									LIST_ADULTO_MAYOR_EXP)) {
								contador_excepciones++;
							}
						}
					}
					List<Esquema_vacunacion> esquema_vacunacions = ManejadorPoblacion
							.getVacunasDisponibles(paciente,
									list_Esquema_vacunacions);
					if (!esquema_vacunacions.isEmpty()) {
						LIST_TOTAL_VACUNACION.add(getMapa(paciente,
								esquema_vacunacions));
					}
				}

				CategoryModel categoryModel = getModeloGrafico();

				// poblacion real
				listcellAgudeza_visual.setLabel(LIST_TOTAL_AGUDEZA_VISUAL
						.size() + "");
				listcellAlteracion_menor_10_anios
						.setLabel(LIST_TOTAL_ALTERACION_MENOR_10_ANIOS.size()
								+ "");
				listcellCancer_cuello_uterino
						.setLabel(LIST_TOTAL_CANCER_CUELLO_UTERINO.size() + "");
				listcellDesarrollo_joven.setLabel(LIST_TOTAL_DESARROLLO_JOVEN
						.size() + "");
				listcellDetencion_cancer_seno
						.setLabel(LIST_TOTAL_DETENCION_CANCER_SENO.size() + "");
				listcellPlanificacion_familiar
						.setLabel(LIST_TOTAL_PLANIFICACION_FAMILIAR.size() + "");
				listcellRecien_nacido.setLabel(LIST_TOTAL_RECIEN_NACIDO.size()
						+ "");
				listcellSalud_oral.setLabel(LIST_TOTAL_SALUD_ORAL.size() + "");
				listcellAdultoMayor.setLabel(LIST_TOTAL_ADULTO_MAYOR.size()
						+ "");
				listcellVacunacion.setLabel(LIST_TOTAL_VACUNACION.size() + "");

				// excepciones
				listcellAgudeza_visualExcepciones
						.setLabel(LIST_TOTAL_AGUDEZA_VISUAL_EXP.size() + "");
				listcellAlteracion_menor_10_aniosExcepciones
						.setLabel(LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP
								.size() + "");
				listcellCancer_cuello_uterinoExcepciones
						.setLabel(LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP.size()
								+ "");
				listcellDesarrollo_jovenExcepciones
						.setLabel(LIST_TOTAL_DESARROLLO_JOVEN_EXP.size() + "");
				listcellDetencion_cancer_senoExcepciones
						.setLabel(LIST_TOTAL_DETENCION_CANCER_SENO_EXP.size()
								+ "");
				listcellPlanificacion_familiarExcepciones
						.setLabel(LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP.size()
								+ "");
				listcellRecien_nacidoExcepciones
						.setLabel(LIST_TOTAL_RECIEN_NACIDO_EXP.size() + "");
				listcellSalud_oralExcepciones
						.setLabel(LIST_TOTAL_SALUD_ORAL_EXP.size() + "");
				listcellAdultoMayorExcepciones
						.setLabel(LIST_TOTAL_ADULTO_MAYOR_EXP.size() + "");
				listcellVacunacionExcepciones
						.setLabel(LIST_TOTAL_VACUNACION_EXP.size() + "");

				// totales
				listcellAgudeza_visualTotal.setLabel((LIST_TOTAL_AGUDEZA_VISUAL
						.size() + LIST_TOTAL_AGUDEZA_VISUAL_EXP.size()) + "");
				listcellAlteracion_menor_10_aniosTotal
						.setLabel((LIST_TOTAL_ALTERACION_MENOR_10_ANIOS.size() + LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP
								.size()) + "");
				listcellCancer_cuello_uterinoTotal
						.setLabel((LIST_TOTAL_CANCER_CUELLO_UTERINO.size() + LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP
								.size()) + "");
				listcellDesarrollo_jovenTotal
						.setLabel((LIST_TOTAL_DESARROLLO_JOVEN.size() + LIST_TOTAL_DESARROLLO_JOVEN_EXP
								.size()) + "");
				listcellDetencion_cancer_senoTotal
						.setLabel((LIST_TOTAL_DETENCION_CANCER_SENO.size() + LIST_TOTAL_DETENCION_CANCER_SENO_EXP
								.size()) + "");
				listcellPlanificacion_familiarTotal
						.setLabel((LIST_TOTAL_PLANIFICACION_FAMILIAR.size() + LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP
								.size()) + "");
				listcellRecien_nacidoTotal.setLabel((LIST_TOTAL_RECIEN_NACIDO
						.size() + LIST_TOTAL_RECIEN_NACIDO_EXP.size()) + "");
				listcellSalud_oralTotal
						.setLabel((LIST_TOTAL_SALUD_ORAL.size() + LIST_TOTAL_SALUD_ORAL_EXP
								.size()) + "");
				listcellAdultoMayorTotal.setLabel((LIST_TOTAL_ADULTO_MAYOR
						.size() + LIST_TOTAL_ADULTO_MAYOR_EXP.size()) + "");
				listcellVacunacionTotal
						.setLabel((LIST_TOTAL_VACUNACION.size() + LIST_TOTAL_VACUNACION_EXP
								.size()) + "");

				listfooterTotales.setLabel(listado_pacientes.size() + "");
				listfooterExcepciones.setLabel(contador_excepciones + "");

				barcharGrafico.setModel(categoryModel);
				barcharGrafico.setThreeD(true);
				// barcharGrafico.setEngine(new BarChartEngine());
				Clients.showNotification(
						"Para ver la poblacion, presione sobre la grafica",
						barcharGrafico);
				tbnExportar.setVisible(true);
			} else {
				throw new Exception(
						"Debe seleccionar la administradora (EPS) para generar el grafico");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeAlerta("Error al generar graficos",
					e.getMessage());
		}
	}

	/**
	 * Este metodo me permite crear un mapa, que contenga las excepciones y las
	 * vacunas
	 * 
	 * @author Luis Miguel
	 * */
	private Map<String, Object> getMapa(Paciente paciente,
			List<?>... informacion) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ListadoPoblacionAction.PACIENTES, paciente);
		map.put(ListadoPoblacionAction.LISTAS, informacion);
		return map;
	}

	public void mostrarPoblacion(MouseEvent event) {
		Area area = (Area) event.getAreaComponent();
		Administradora administradora = (Administradora) bandboxAdministradora
				.getObject();
		if (categoryModel != null && administradora != null) {
			String poblacion_id = area.getAttribute("series") + "";
			String descripcion = area.getAttribute("category") + "";
			List<Map<String, Object>> pacientes = categoryModel
					.getObjects(poblacion_id + descripcion);
			//log.info("Poblacion: " + pacientes);

			if (pacientes != null && !pacientes.isEmpty()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(ListadoPoblacionAction.PACIENTES, pacientes);

				map.put(ListadoPoblacionAction.TITULO,
						(!poblacion_id.contains("POBLACION") ? "POBLACION "
								: "")
								+ poblacion_id.replace("_", " ")
								+ " - "
								+ descripcion.replace("_", " ")
								+ " "
								+ administradora.getNombre().toUpperCase()
								+ " - Total: " + pacientes.size());
				ListadoPoblacionAction listadoPoblacionAction = (ListadoPoblacionAction) Executions
						.createComponents("/pages/listado_poblacion.zul", this,
								map);
				listadoPoblacionAction.setWidth("100%");
				listadoPoblacionAction.setHeight("100%");
				listadoPoblacionAction.doModal();
			}
		}
	}

	private CategoryModel getModeloGrafico() {
		categoryModel = new SimpleCategoryModelHelper();

		// deteccion de cancer de seno
		categoryModel.setValue("POBLACION REAL", "DETENCION_CANCER_SENO",
				LIST_TOTAL_DETENCION_CANCER_SENO);
		List<Map<String, Object>> pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_DETENCION_CANCER_SENO);
		pacientesTotalPaciente.addAll(LIST_TOTAL_DETENCION_CANCER_SENO_EXP);

		if (!LIST_TOTAL_DETENCION_CANCER_SENO_EXP.isEmpty()) {
			categoryModel.setValue("TOTAL", "DETENCION_CANCER_SENO",
					pacientesTotalPaciente);
			categoryModel.setValue("EXCEPCIONES", "DETENCION_CANCER_SENO",
					LIST_TOTAL_DETENCION_CANCER_SENO_EXP);
		}

		// deteccion salud oral

		categoryModel.setValue("POBLACION REAL", "SALUD_ORAL",
				LIST_TOTAL_SALUD_ORAL);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_SALUD_ORAL);
		pacientesTotalPaciente.addAll(LIST_TOTAL_SALUD_ORAL_EXP);
		if (!LIST_TOTAL_SALUD_ORAL_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "SALUD_ORAL",
					LIST_TOTAL_SALUD_ORAL_EXP);
			categoryModel.setValue("TOTAL", "SALUD_ORAL",
					pacientesTotalPaciente);
		}

		// alteracion menor de 10 años
		categoryModel.setValue("POBLACION REAL", "ALTERACION_MENOR_10_añoS",
				LIST_TOTAL_ALTERACION_MENOR_10_ANIOS);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_ALTERACION_MENOR_10_ANIOS);
		pacientesTotalPaciente.addAll(LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP);

		if (!LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "ALTERACION_MENOR_10_añoS",
					LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP);
			categoryModel.setValue("TOTAL", "ALTERACION_MENOR_10_añoS",
					pacientesTotalPaciente);
		}

		// agudeza visual
		categoryModel.setValue("POBLACION REAL", "AGUDEZA_VISUAL",
				LIST_TOTAL_AGUDEZA_VISUAL);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_AGUDEZA_VISUAL);
		pacientesTotalPaciente.addAll(LIST_TOTAL_AGUDEZA_VISUAL_EXP);
		if (!LIST_TOTAL_AGUDEZA_VISUAL_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "AGUDEZA_VISUAL",
					LIST_TOTAL_AGUDEZA_VISUAL_EXP);
			categoryModel.setValue("TOTAL", "AGUDEZA_VISUAL",
					pacientesTotalPaciente);
		}

		// recien nacido

		categoryModel.setValue("POBLACION REAL", "RECIEN_NACIDO",
				LIST_TOTAL_RECIEN_NACIDO);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_RECIEN_NACIDO);
		pacientesTotalPaciente.addAll(LIST_TOTAL_RECIEN_NACIDO_EXP);

		if (!LIST_TOTAL_RECIEN_NACIDO_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "RECIEN_NACIDO",
					LIST_TOTAL_RECIEN_NACIDO_EXP);
			categoryModel.setValue("TOTAL", "RECIEN_NACIDO",
					pacientesTotalPaciente);
		}

		// desarrollo joven
		categoryModel.setValue("POBLACION REAL", "DESARROLLO_JOVEN",
				LIST_TOTAL_DESARROLLO_JOVEN);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_DESARROLLO_JOVEN);
		pacientesTotalPaciente.addAll(LIST_TOTAL_DESARROLLO_JOVEN_EXP);

		if (!LIST_TOTAL_DESARROLLO_JOVEN_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "DESARROLLO_JOVEN",
					LIST_TOTAL_DESARROLLO_JOVEN_EXP);
			categoryModel.setValue("TOTAL", "DESARROLLO_JOVEN",
					pacientesTotalPaciente);
		}

		// cancer de cuello uterino

		categoryModel.setValue("POBLACION REAL", "CANCER_CUELLO_UTERINO",
				LIST_TOTAL_CANCER_CUELLO_UTERINO);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_CANCER_CUELLO_UTERINO);
		pacientesTotalPaciente.addAll(LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP);
		if (!LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "CANCER_CUELLO_UTERINO",
					LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP);
			categoryModel.setValue("TOTAL", "CANCER_CUELLO_UTERINO",
					pacientesTotalPaciente);
		}

		// planificacion familiar

		categoryModel.setValue("POBLACION REAL", "PLANIFICACION_FAMILIAR",
				LIST_TOTAL_PLANIFICACION_FAMILIAR);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_PLANIFICACION_FAMILIAR);
		pacientesTotalPaciente.addAll(LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP);
		if (!LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "PLANIFICACION_FAMILIAR",
					LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP);
			categoryModel.setValue("TOTAL", "PLANIFICACION_FAMILIAR",
					pacientesTotalPaciente);
		}

		// adulto mayor

		categoryModel.setValue("POBLACION REAL", "ADULTO_MAYOR",
				LIST_TOTAL_ADULTO_MAYOR);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_ADULTO_MAYOR);
		pacientesTotalPaciente.addAll(LIST_TOTAL_ADULTO_MAYOR_EXP);

		if (!LIST_TOTAL_ADULTO_MAYOR_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "ADULTO_MAYOR",
					LIST_TOTAL_ADULTO_MAYOR_EXP);
			categoryModel.setValue("TOTAL", "ADULTO_MAYOR",
					pacientesTotalPaciente);
		}

		// vacunacion
		categoryModel.setValue("POBLACION REAL", "VACUNACION",
				LIST_TOTAL_VACUNACION);
		pacientesTotalPaciente = new ArrayList<Map<String, Object>>();
		pacientesTotalPaciente.addAll(LIST_TOTAL_VACUNACION);
		pacientesTotalPaciente.addAll(LIST_TOTAL_VACUNACION_EXP);
		if (!LIST_TOTAL_VACUNACION_EXP.isEmpty()) {
			categoryModel.setValue("EXCEPCIONES", "VACUNACION",
					LIST_TOTAL_VACUNACION_EXP);
			categoryModel.setValue("TOTAL", "VACUNACION",
					pacientesTotalPaciente);
		}

		return categoryModel;
	}

	private List<Map<String, Object>> concatenar(
			List<Map<String, Object>>... lists) {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (List<Map<String, Object>> map : lists) {
			maps.addAll(map);
		}
		return maps;
	}

	// exportador de excel
	public void exportarExcelXLS() {
		try {
			Workbook libroPoblacionReal = new HSSFWorkbook();
			Workbook libroExcepciones = new HSSFWorkbook();
			Workbook libroPoblacionTotal = new HSSFWorkbook();

			boolean octuvo_datos_poblacion_real = false;
			boolean octuvo_datos_excepciones = false;
			boolean octuvo_datos_poblacion_total = false;

			// creamos libros por cada uno de los programas
			if (!LIST_TOTAL_AGUDEZA_VISUAL.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_AGUDEZA_VISUAL,
						libroPoblacionReal, "AGUDEZA VISUAL");
			}

			if (!LIST_TOTAL_ALTERACION_MENOR_10_ANIOS.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_ALTERACION_MENOR_10_ANIOS,
						libroPoblacionReal, "ALTERACION MENOR 10 ANIOS");
			}

			if (!LIST_TOTAL_CANCER_CUELLO_UTERINO.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_CANCER_CUELLO_UTERINO,
						libroPoblacionReal, "CANCER CUELLO UTERINO");
			}
			if (!LIST_TOTAL_DESARROLLO_JOVEN.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_DESARROLLO_JOVEN,
						libroPoblacionReal, "DESARROLLO JOVEN");
			}
			if (!LIST_TOTAL_DETENCION_CANCER_SENO.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_DETENCION_CANCER_SENO,
						libroPoblacionReal, "DETENCION CANCER SENO");
			}
			if (!LIST_TOTAL_PLANIFICACION_FAMILIAR.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_PLANIFICACION_FAMILIAR,
						libroPoblacionReal, "PLANIFICACION FAMILIAR");
			}
			if (!LIST_TOTAL_RECIEN_NACIDO.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_RECIEN_NACIDO,
						libroPoblacionReal, "RECIEN NACIDO");
			}
			if (!LIST_TOTAL_SALUD_ORAL.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_SALUD_ORAL, libroPoblacionReal,
						"SALUD ORAL");
			}
			if (!LIST_TOTAL_ADULTO_MAYOR.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_ADULTO_MAYOR,
						libroPoblacionReal, "ADULTO MAYOR");
			}
			if (!LIST_TOTAL_VACUNACION.isEmpty()) {
				octuvo_datos_poblacion_real = true;
				generarPestaniaExcel(LIST_TOTAL_VACUNACION, libroPoblacionReal,
						"VACUNACION");
			}

			// creamos libros por cada uno de los programas
			if (!LIST_TOTAL_AGUDEZA_VISUAL_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_AGUDEZA_VISUAL_EXP,
						libroExcepciones, "EXCEPCIONES AGUDEZA VISUAL");
			}
			if (!LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP,
						libroExcepciones,
						"EXCEPCIONES ALTERACION MENOR 10 ANIOS");
			}
			if (!LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP,
						libroExcepciones, "EXCEPCIONES CANCER CUELLO UTERINO");
			}
			if (!LIST_TOTAL_DESARROLLO_JOVEN_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_DESARROLLO_JOVEN_EXP,
						libroExcepciones, "EXCEPCIONES DESARROLLO JOVEN");
			}
			if (!LIST_TOTAL_DETENCION_CANCER_SENO_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_DETENCION_CANCER_SENO_EXP,
						libroExcepciones, "EXCEPCIONES DETENCION CANCER SENO");
			}
			if (!LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP,
						libroExcepciones, "EXCEPCIONES PLANIFICACION FAMILIAR");
			}
			if (!LIST_TOTAL_AGUDEZA_VISUAL_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_RECIEN_NACIDO_EXP,
						libroExcepciones, "EXCEPCIONES RECIEN NACIDO");
			}
			if (!LIST_TOTAL_SALUD_ORAL_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_SALUD_ORAL_EXP,
						libroExcepciones, "EXCEPCIONES SALUD ORAL");
			}
			if (!LIST_TOTAL_ADULTO_MAYOR_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_ADULTO_MAYOR_EXP,
						libroExcepciones, "EXCEPCIONES ADULTO MAYOR");
			}
			if (!LIST_TOTAL_VACUNACION_EXP.isEmpty()) {
				octuvo_datos_excepciones = true;
				generarPestaniaExcel(LIST_TOTAL_VACUNACION_EXP,
						libroExcepciones, "EXCEPCIONES VACUNACION");
			}

			// creamos libros por cada uno de los programas
			List<Map<String, Object>> TOTAL_AGUDEZA_VISUAL = concatenar(
					LIST_TOTAL_AGUDEZA_VISUAL, LIST_TOTAL_AGUDEZA_VISUAL_EXP);
			if (!LIST_TOTAL_AGUDEZA_VISUAL_EXP.isEmpty()
					&& !LIST_TOTAL_AGUDEZA_VISUAL.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_AGUDEZA_VISUAL, libroPoblacionTotal,
						"AGUDEZA VISUAL TOTAL");
			}
			List<Map<String, Object>> TOTAL_ALTERACION_MENOR_10_ANIOS = concatenar(
					LIST_TOTAL_ALTERACION_MENOR_10_ANIOS,
					LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP);
			if (!LIST_TOTAL_ALTERACION_MENOR_10_ANIOS_EXP.isEmpty()
					&& !LIST_TOTAL_ALTERACION_MENOR_10_ANIOS.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_ALTERACION_MENOR_10_ANIOS,
						libroPoblacionTotal, "ALTERACION MENOR 10 ANIOS TOTAL");
			}
			List<Map<String, Object>> TOTAL_CANCER_CUELLO_UTERINO = concatenar(
					LIST_TOTAL_CANCER_CUELLO_UTERINO,
					LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP);
			if (!LIST_TOTAL_CANCER_CUELLO_UTERINO_EXP.isEmpty()
					&& !LIST_TOTAL_CANCER_CUELLO_UTERINO.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_CANCER_CUELLO_UTERINO,
						libroPoblacionTotal, "CANCER CUELLO UTERINO TOTAL");
			}

			List<Map<String, Object>> TOTAL_DESARROLLO_JOVEN = concatenar(
					LIST_TOTAL_DESARROLLO_JOVEN,
					LIST_TOTAL_DESARROLLO_JOVEN_EXP);
			if (!LIST_TOTAL_DESARROLLO_JOVEN_EXP.isEmpty()
					&& !LIST_TOTAL_DESARROLLO_JOVEN.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_DESARROLLO_JOVEN,
						libroPoblacionTotal, "DESARROLLO JOVEN TOTAL");
			}

			List<Map<String, Object>> TOTAL_DETENCION_CANCER_SENO_EXP = concatenar(
					LIST_TOTAL_DETENCION_CANCER_SENO,
					LIST_TOTAL_DETENCION_CANCER_SENO_EXP);
			if (!LIST_TOTAL_DETENCION_CANCER_SENO_EXP.isEmpty()
					&& !LIST_TOTAL_DETENCION_CANCER_SENO.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_DETENCION_CANCER_SENO_EXP,
						libroPoblacionTotal, "DETENCION CANCER SENO TOTAL");
			}
			List<Map<String, Object>> TOTAL_PLANIFICACION_FAMILIAR = concatenar(
					LIST_TOTAL_PLANIFICACION_FAMILIAR,
					LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP);
			if (!LIST_TOTAL_PLANIFICACION_FAMILIAR_EXP.isEmpty()
					&& !LIST_TOTAL_PLANIFICACION_FAMILIAR.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_PLANIFICACION_FAMILIAR,
						libroPoblacionTotal, "PLANIFICACION FAMILIAR TOTAL");
			}

			List<Map<String, Object>> TOTAL_RECIEN_NACIDO = concatenar(
					LIST_TOTAL_RECIEN_NACIDO, LIST_TOTAL_RECIEN_NACIDO_EXP);
			if (!LIST_TOTAL_RECIEN_NACIDO_EXP.isEmpty()
					&& !LIST_TOTAL_RECIEN_NACIDO.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_RECIEN_NACIDO, libroPoblacionTotal,
						"RECIEN NACIDO TOTAL");
			}

			List<Map<String, Object>> TOTAL_SALUD_ORAL = concatenar(
					LIST_TOTAL_SALUD_ORAL, LIST_TOTAL_SALUD_ORAL_EXP);
			if (!LIST_TOTAL_SALUD_ORAL_EXP.isEmpty()
					&& !LIST_TOTAL_SALUD_ORAL.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_SALUD_ORAL, libroPoblacionTotal,
						"SALUD ORAL TOTAL");
			}

			List<Map<String, Object>> TOTAL_ADULTO_MAYOR = concatenar(
					LIST_TOTAL_ADULTO_MAYOR, LIST_TOTAL_ADULTO_MAYOR_EXP);
			if (!LIST_TOTAL_ADULTO_MAYOR_EXP.isEmpty()
					&& !LIST_TOTAL_ADULTO_MAYOR.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_ADULTO_MAYOR, libroPoblacionTotal,
						"ADULTO MAYOR TOTAL");
			}

			List<Map<String, Object>> TOTAL_VACUNACION = concatenar(
					LIST_TOTAL_VACUNACION, LIST_TOTAL_VACUNACION_EXP);
			if (!LIST_TOTAL_VACUNACION_EXP.isEmpty()
					&& !LIST_TOTAL_VACUNACION.isEmpty()) {
				octuvo_datos_poblacion_total = true;
				generarPestaniaExcel(TOTAL_VACUNACION, libroPoblacionTotal,
						"VACUNACION TOTAL");
			}

			// descargamos archivo
			File fileDirectorio = getDirectorio();
			File fileZip = new File(fileDirectorio.getAbsolutePath()
					+ File.separator + "ZIP");
			if (!fileZip.exists()) {
				fileZip.mkdir();
			}

			File file = new File(fileDirectorio.getAbsolutePath()
					+ File.separator + "xlsTemp");
			if (!file.exists()) {
				file.mkdir();
			}

			// creamos los archivos
			if (octuvo_datos_poblacion_real)
				crearArchivo(file, "poblacion_real.xls", libroPoblacionReal);
			if (octuvo_datos_excepciones)
				crearArchivo(file, "poblacion_excepciones.xls",
						libroExcepciones);
			if (octuvo_datos_poblacion_total)
				crearArchivo(file, "poblacion_total.xls", libroPoblacionTotal);

			// generar zips
			String nameOfFileZip = fileZip.getAbsolutePath() + File.separator
					+ "poblacion.zip";
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(nameOfFileZip);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));
			byte data[] = new byte[BUFFER];
			String files[] = file.list();
			for (int i = 0; i < files.length; i++) {
				FileInputStream fi = new FileInputStream(file.getAbsolutePath()
						+ File.separator + files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				// creamos una entrada zip
				ZipEntry entry = new ZipEntry(files[i]);
				// agregamos entradas zip al archivo
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
				File aux = new File(file.getAbsolutePath() + File.separator
						+ files[i]);
				if (aux.exists()) {
					aux.delete();
				}
			}
			out.close();

			FileInputStream archivo = new FileInputStream(nameOfFileZip);
			int longitud = archivo.available();
			byte[] datos = new byte[longitud];
			archivo.read(datos);
			archivo.close();
			File del = new File(nameOfFileZip);
			Filedownload.save(del, "application/zip");
			try {
				fileDirectorio.deleteOnExit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearArchivo(File file, String title, Workbook libro)
			throws Exception {
		File archivo_excel = new File(file.getAbsolutePath() + File.separator
				+ title);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(archivo_excel);
			libro.write(fos);
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private File getDirectorio() {
		File file = new File(Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("")
				+ "/Files");
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(file.getAbsolutePath() + "/Temp");
		if (!file.exists()) {
			file.mkdir();
		}

		String fecha = new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar
				.getInstance().getTime());
		file = new File(file.getAbsolutePath() + "/"
				+ empresa.getCodigo_empresa() + ""
				+ sucursal.getCodigo_sucursal() + "" + fecha);
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	/* Este metodo me permite generar una pestaña en excel */
	private void generarPestaniaExcel(List<Map<String, Object>> LISTADO,
			Workbook libro, String titulo) {
		Sheet hoja1 = libro.createSheet(titulo);

		// creamos frozen
		hoja1.createFreezePane(0, 6);

		// cargamos formato del documento
		HSSFFont fontBold = (HSSFFont) libro.createFont();
		fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontBold.setFontName(HSSFFont.FONT_ARIAL);
		fontBold.setFontHeightInPoints((short) 9);

		HSSFFont headerFont = (HSSFFont) libro.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// headerFont.setColor(IndexedColors.WHITE.index);
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontName(HSSFFont.FONT_ARIAL);

		HSSFFont fontNormal = (HSSFFont) libro.createFont();
		fontNormal.setFontName(HSSFFont.FONT_ARIAL);
		fontNormal.setFontHeightInPoints((short) 9);

		DataFormat format = libro.createDataFormat();

		CellStyle styleInforme = libro.createCellStyle();
		styleInforme.setFont(fontNormal);
		styleInforme.setAlignment(CellStyle.ALIGN_CENTER);

		HSSFCellStyle style = (HSSFCellStyle) libro.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle styleNomFecha = libro.createCellStyle();
		styleNomFecha.setFont(fontBold);
		styleNomFecha.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle styleFecha = libro.createCellStyle();
		styleFecha.setFont(fontBold);
		styleFecha.setDataFormat(format.getFormat("dd/mm/yyyy"));

		CellStyle styleHeader = libro.createCellStyle();
		styleHeader.setFont(headerFont);
		styleHeader.setBorderTop(CellStyle.BORDER_THIN);
		styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
		styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
		styleHeader.setBorderRight(CellStyle.BORDER_THIN);

		CellStyle styleTexto = libro.createCellStyle();
		styleTexto.setFont(fontNormal);
		styleTexto.setDataFormat((short) BuiltinFormats
				.getBuiltinFormat("text"));
		styleTexto.setBorderTop(CellStyle.BORDER_THIN);
		styleTexto.setBorderBottom(CellStyle.BORDER_THIN);
		styleTexto.setBorderLeft(CellStyle.BORDER_THIN);
		styleTexto.setBorderRight(CellStyle.BORDER_THIN);

		CellStyle styleFecha2 = libro.createCellStyle();
		styleFecha2.setFont(fontNormal);
		styleFecha2.setDataFormat(format.getFormat("yyyy-MM-dd"));
		styleFecha2.setBorderTop(CellStyle.BORDER_THIN);
		styleFecha2.setBorderBottom(CellStyle.BORDER_THIN);
		styleFecha2.setBorderLeft(CellStyle.BORDER_THIN);
		styleFecha2.setBorderRight(CellStyle.BORDER_THIN);

		// agregamos cabecera
		CellStyle styleNombre_empresa = libro.createCellStyle();
		styleNombre_empresa.setFont(fontBold);
		styleNombre_empresa.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle stylnombreDetalles = libro.createCellStyle();
		stylnombreDetalles.setFont(fontBold);

		int row = 0;
		org.apache.poi.ss.usermodel.Row filaEmpresa = hoja1.createRow(row++);
		hoja1.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		Cell celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
		celda.setCellValue(titulo);
		celda.setCellStyle(styleNombre_empresa);

		filaEmpresa = hoja1.createRow(row++);
		hoja1.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
		celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
		celda.setCellValue("" + empresa.getNombre_empresa().toUpperCase());
		celda.setCellStyle(styleNombre_empresa);

		// nit de la empresa
		filaEmpresa = hoja1.createRow(row++);
		hoja1.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));
		celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
		celda.setCellValue("NIT: " + empresa.getNro_identificacion());
		celda.setCellStyle(styleNombre_empresa);

		// direccion
		String direccion = "";
		Municipios municipios = new Municipios();
		municipios.setCoddep(empresa.getCodigo_dpto());
		municipios.setCodigo(empresa.getCodigo_municipio());
		municipios = getServiceLocator().getMunicipiosService().consultar(
				municipios);
		if (municipios != null) {
			direccion = municipios.getNombre();
		}

		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo(empresa.getCodigo_dpto());
		departamentos = getServiceLocator().getServicio(
				DepartamentosService.class).consultar(departamentos);
		if (departamentos != null) {
			direccion += " " + departamentos.getNombre();
		}

		filaEmpresa = hoja1.createRow(row++);
		hoja1.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));
		celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
		celda.setCellValue(direccion.toUpperCase());
		celda.setCellStyle(styleNombre_empresa);

		filaEmpresa = hoja1.createRow(row++);
		hoja1.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));
		celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
		celda.setCellValue("EPS: " + bandboxAdministradora.getValue());
		celda.setCellStyle(styleNombre_empresa);

		org.apache.poi.ss.usermodel.Row filaCol = hoja1.createRow(row++);
		int initCol = 0;

		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("TIPO ID");
		celda.setCellStyle(style);
		initCol++;
		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("IDENTIFICAcion");
		celda.setCellStyle(style);
		initCol++;
		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("NOMBRES");
		celda.setCellStyle(style);
		initCol++;
		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("FECHA NAC");
		celda.setCellStyle(style);
		initCol++;
		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("SEXO");
		celda.setCellStyle(style);
		initCol++;
		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("EDAD");
		celda.setCellStyle(style);
		initCol++;
		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("TELÉFONO");
		celda.setCellStyle(style);
		initCol++;
		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("DIRECcion");
		celda.setCellStyle(style);
		initCol++;
		celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);
		celda.setCellValue("ZONA");
		celda.setCellStyle(style);
		initCol++;

		for (Map<String, Object> mapPaciente : LISTADO) {
			org.apache.poi.ss.usermodel.Row filaCart = hoja1.createRow(row);
			initCol = 0;

			Paciente paciente = (Paciente) mapPaciente
					.get(ListadoPoblacionAction.PACIENTES);

			String edad = Util.getEdadPrsentacion(paciente
					.getFecha_nacimiento());

			departamentos = new Departamentos();
			departamentos.setCodigo(paciente.getCodigo_dpto());
			departamentos = getServiceLocator().getServicio(
					DepartamentosService.class).consultar(departamentos);

			municipios = new Municipios();
			municipios.setCoddep(paciente.getCodigo_dpto());
			municipios.setCodigo(paciente.getCodigo_municipio());
			municipios = getServiceLocator().getServicio(
					MunicipiosService.class).consultar(municipios);

			String complementoDireccion = "";

			if (municipios != null) {
				complementoDireccion += (complementoDireccion.isEmpty() ? ""
						: ", ") + municipios.getNombre();
			}

			if (departamentos != null) {
				complementoDireccion += (complementoDireccion.isEmpty() ? ""
						: ", ") + departamentos.getNombre();
			}

			celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);
			celda.setCellValue(paciente.getTipo_identificacion());
			celda.setCellStyle(styleTexto);
			initCol++;
			celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);
			celda.setCellValue(paciente.getNro_identificacion());
			celda.setCellStyle(styleTexto);
			initCol++;
			celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);
			celda.setCellValue(paciente.getNombreCompleto());
			celda.setCellStyle(styleTexto);
			initCol++;
			celda = filaCart.createCell(initCol);
			celda.setCellValue(paciente.getFecha_nacimiento());
			celda.setCellStyle(styleFecha2);
			initCol++;
			celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);
			celda.setCellValue(paciente.getSexo().equals("M") ? "MASCULINO"
					: "FEMENINO");
			celda.setCellStyle(styleTexto);
			initCol++;
			celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);
			celda.setCellValue(edad);
			celda.setCellStyle(styleTexto);
			initCol++;
			celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);
			celda.setCellValue(paciente.getTel_res());
			celda.setCellStyle(styleTexto);
			initCol++;
			celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);
			celda.setCellValue(((paciente.getDireccion() + "") + " " + complementoDireccion)
					.toUpperCase());
			celda.setCellStyle(styleTexto);
			initCol++;
			celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);
			celda.setCellValue(((paciente.getZona() + "").equals("U") ? "URBANA"
					: "RURAL"));
			celda.setCellStyle(styleTexto);
			initCol++;
			row++;

			// Ingresamos la actividades pendientes, vacunas y excepciones
			List<Object>[] lists = (List<Object>[]) mapPaciente
					.get(ListadoPoblacionAction.LISTAS);
			boolean existe_actividades = false;
			boolean existe_actividades_realizadas = false;
			boolean existe_excepciones = false;
			boolean existe_vacunas = false;
			for (List<Object> list : lists) {
				for (Object object : list) {
					if (object instanceof Esquema_vacunacion) {
						if (!existe_vacunas) {
							existe_vacunas = true;
							int pos = row++;
							filaEmpresa = hoja1.createRow(pos);
							hoja1.addMergedRegion(new CellRangeAddress(pos,
									pos, 1, 8));
							celda = filaEmpresa.createCell(1,
									Cell.CELL_TYPE_STRING);
							celda.setCellValue("VACUNAS");
							celda.setCellStyle(style);
						}
						// ADICIONAMOS INFORMACION
						Esquema_vacunacion esquema_vacunacion = (Esquema_vacunacion) object;
						Vacunas vacunas = new Vacunas();
						vacunas.setCodigo_empresa(esquema_vacunacion
								.getCodigo_empresa());
						vacunas.setCodigo_sucursal(esquema_vacunacion
								.getCodigo_sucursal());
						vacunas.setCodigo_procedimiento(esquema_vacunacion
								.getCodigo_vacuna());
						vacunas = getServiceLocator().getServicio(
								VacunasService.class).consultar(vacunas);
						String vacuna = (esquema_vacunacion.getCodigo_vacuna()
								+ " " + (vacunas != null ? vacunas
								.getDescripcion() : ""));
						int pos = row++;
						filaEmpresa = hoja1.createRow(pos);
						hoja1.addMergedRegion(new CellRangeAddress(pos, pos, 1,
								8));
						celda = filaEmpresa
								.createCell(1, Cell.CELL_TYPE_STRING);
						celda.setCellValue("" + vacuna);
						celda.setCellStyle(stylnombreDetalles);
					} else if (object instanceof Excepciones_pyp) {
						if (!existe_excepciones) {
							existe_excepciones = true;
							int pos = row++;
							filaEmpresa = hoja1.createRow(pos);
							hoja1.addMergedRegion(new CellRangeAddress(pos,
									pos, 1, 8));
							celda = filaEmpresa.createCell(1,
									Cell.CELL_TYPE_STRING);
							celda.setCellValue("EXCEPCIONES");
							celda.setCellStyle(style);
						}
						// ADICIONAMOS INFORMACION
						Excepciones_pyp excepciones_pyp = (Excepciones_pyp) object;
						Procedimientos procedimientos = new Procedimientos();
						procedimientos.setId_procedimiento(new Long(
								excepciones_pyp.getCodigo_procedimiento()));
						procedimientos = getServiceLocator()
								.getProcedimientosService().consultar(
										procedimientos);
						String procedimiento = (excepciones_pyp
								.getCodigo_procedimiento() + " " + (procedimientos != null ? procedimientos
								.getDescripcion() : ""));

						int pos = row++;
						filaEmpresa = hoja1.createRow(pos);
						hoja1.addMergedRegion(new CellRangeAddress(pos, pos, 1,
								8));
						celda = filaEmpresa
								.createCell(1, Cell.CELL_TYPE_STRING);
						celda.setCellValue("" + procedimiento);
						celda.setCellStyle(stylnombreDetalles);
					} else if (object instanceof Plantilla_pyp
							&& ((Plantilla_pyp) object).isPendiente()) {
						if (!existe_actividades) {
							existe_actividades = true;
							int pos = row++;
							filaEmpresa = hoja1.createRow(pos);
							hoja1.addMergedRegion(new CellRangeAddress(pos,
									pos, 1, 8));
							celda = filaEmpresa.createCell(1,
									Cell.CELL_TYPE_STRING);
							celda.setCellValue("ACTIVIDADES PENDIENTES");
							celda.setCellStyle(style);
						}
						// ADICIONAMOS INFORMACION
						Plantilla_pyp ddt_pnatilla = (Plantilla_pyp) object;
						String actividad = (ddt_pnatilla != null ? (ddt_pnatilla
								.getCodigo_pdc() + " - " + ddt_pnatilla
								.getNombre_pcd()) : "ACTIVIDAD NO ENCONTRADA");
						int pos = row++;
						filaEmpresa = hoja1.createRow(pos);
						hoja1.addMergedRegion(new CellRangeAddress(pos, pos, 1,
								8));
						celda = filaEmpresa
								.createCell(1, Cell.CELL_TYPE_STRING);
						celda.setCellValue("" + actividad);
						celda.setCellStyle(stylnombreDetalles);
					} else if (object instanceof Plantilla_pyp
							&& !((Plantilla_pyp) object).isPendiente()) {
						if (!existe_actividades_realizadas) {
							existe_actividades_realizadas = true;
							int pos = row++;
							filaEmpresa = hoja1.createRow(pos);
							hoja1.addMergedRegion(new CellRangeAddress(pos,
									pos, 1, 8));
							celda = filaEmpresa.createCell(1,
									Cell.CELL_TYPE_STRING);
							celda.setCellValue("ACTIVIDADES REALIZADAS");
							celda.setCellStyle(style);
						}
						// ADICIONAMOS INFORMACION
						Plantilla_pyp ddt_pnatilla = (Plantilla_pyp) object;
						String actividad = (ddt_pnatilla != null ? (ddt_pnatilla
								.getCodigo_pdc() + " - " + ddt_pnatilla
								.getNombre_pcd()) : "ACTIVIDAD NO ENCONTRADA");
						int pos = row++;
						filaEmpresa = hoja1.createRow(pos);
						hoja1.addMergedRegion(new CellRangeAddress(pos, pos, 1,
								8));
						celda = filaEmpresa
								.createCell(1, Cell.CELL_TYPE_STRING);
						celda.setCellValue("" + actividad);
						celda.setCellStyle(stylnombreDetalles);
					}
				}
			}

		}

		for (int i = 0; i < 8; i++) {
			hoja1.autoSizeColumn((short) i);
		}
	}

	// importar rips
	public void importarRips() {
		try {
			Media[] medias = Fileupload.get(
					"Porfavor seleccione los rips (AP)", "Carga de Rips", -1);
			List<String> listTXT = new ArrayList<String>();
			if (medias != null)
				for (Media media : medias) {
					if (media.getName().toUpperCase().startsWith("AP")
							&& media.getName().toLowerCase().endsWith(".txt")) {
						listTXT.add(media.getStringData());
					} else {
						throw new ValidacionRunTimeException("El archivo "
								+ media.getName()
								+ " no valido de ser (AP)*(.TXT)");
					}
				}

			if (!listTXT.isEmpty()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list_text", listTXT);
				map.put("codigo_empresa", codigo_empresa);
				map.put("codigo_sucursal", codigo_sucursal);
				map.put("codigo_usuario", usuarios.getCodigo());
				getServiceLocator().getDatos_procedimientoService()
						.crearDesdeRips(map);
				MensajesUtil.mensajeInformacion("Informacion",
						"Datos guardados exitosamente");
			} else {
				throw new ValidacionRunTimeException(
						"No ha cargado ningun archivo");
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	/**
	 * Este metodo me permite inyectar las excepciones de los programas de pyp
	 * */
	private void inyectarExcepcionesCorrespondientesProgramas()
			throws Exception {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			InyectarExcepciones inyectarExcepciones = field
					.getAnnotation(InyectarExcepciones.class);
			if (inyectarExcepciones != null) {
				field.setAccessible(true);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo", inyectarExcepciones.codigo_programa_pyp());
				List<Excepciones_pyp> excepciones_pyps = getServiceLocator()
						.getServicio(Excepciones_pypService.class).listar(map);
				field.set(this, excepciones_pyps);
			}
		}
	}
}