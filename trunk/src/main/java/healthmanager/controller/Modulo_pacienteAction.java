/**
 *
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Hisc_historial;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Ficha_inicio_lepraService;
import healthmanager.modelo.service.Hisc_urgenciaService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Roles_usuariosService;
import healthmanager.modelo.service.Seguimiento_control_pqtService;
import healthmanager.modelo.service.Vacunas_pacientesService;

import com.framework.util.Util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import sios.modelo.service.Hisc_historialSiosService;

import com.framework.constantes.IConstantes;
import com.framework.constantes.INotas;
import com.framework.constantes.IRoles;
import com.framework.constantes.IRutas_historia;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.macros.ContenedorPaginasMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.ManejadorPoblacion;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * @author Manuel Aguilar
 *
 */
public class Modulo_pacienteAction extends ZKWindow {

	private static Logger log = Logger.getLogger(Modulo_pacienteAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private PacienteService pacienteService;

	private Hisc_historialSiosService hisc_historialSiosService;
	private Roles_usuariosService roles_usuariosService;
	private GeneralExtraService generalExtraService;

	// private Historia_clinicaService historia_clinicaService;
	@View
	private Timer timerConsultar;
	@View
	private Caption captionEncabezado;
	@View
	private Auxheader auxheaderAsistencial;
	@View
	private Div divPopups_traige;
	private AdmisionService admisionService;
	@View
	private Listbox listboxAdmisiones;
	@View
	private Datebox dtxFecha;
	@View
	private Listbox lbxAtendidas;
	@View
	private Listbox lbxBuscar_paciente;
	@View
	private Listbox lbxEstado_admision;
	@View
	private Textbox tbxValueAdmision;
	@View
	private Textbox tbxValuePaciente;
	@View
	private Textbox tbxVia_ingreso;

	private Textbox tbxTipo_urgencia = new Textbox();

	@View
	private Groupbox groupboxConsultar;
	@View
	private Borderlayout borderlayoutEditar;

	@View
	private Toolbarbutton toolbarbuttonInformacion_paciente;
	@View
	private Textbox tbxIngreso;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxTipo_identificacion;
	@View
	private Textbox tbxFecha_ingreso;
	@View
	private Textbox tbxAseguradora;
	@View
	private Textbox tbxContrato;
	@View
	private Textbox tbxNivel_educativo;
	@View
	private Textbox tbxPertencia_etnica;
	@View
	private Textbox tbxEdad;

	@View
	private Textbox tbxSexo;

	@View
	private ContenedorPaginasMacro tabboxContendor;

	@View
	private Groupbox gbxHistorias_clinicas;

	@View
	private A aConsulta_externa;

	@View
	private A aSios;

	@View
	private A aPyp;
	@View
	private Popup popupPYP;
	@View
	private A aAiepi;
	@View
	private A aAiepiMenor2mA;
	@View
	private A aAiepiMenor2m5aA;
	// @View
	// private Popup agudezaVisual;
	@View
	private Popup popupAiepi;
	@View
	private Popup popupOdontologia;
	@View
	private Popup popupSaludMental;
	@View
	private A aPYP_adulto_mayor;
	@View
	private A aPYP_alteracion_embarazo;
	@View
	private A aPYP_alteracion_joven;
	@View
	private A aPYP_hipertenso_diabetico;
	@View
	private A aPYP_planificacion_familiar;
	@View
	private A aPYP_agudezVisual;
	@View
	private A aSalud_mental;
	@View
	private A aPsicologia;
	@View
	private A aPsiquiatria;
	@View
	private A aVisita_domiciliaria;

	// actividades pendietes de PyP
	@View
	private A aPYP_actividades_pendientes;

	@View
	private A aPYP_menor_2mes;
	@View
	private A aPYP_menor_2mes_2anos;
	@View
	private A aPYP_menor_2anos_5anos;
	@View
	private A aPYP_menor_5anos_10anos;
	@View
	private A aOdontologia;
	@View
	private A aOdontologia_1ra;
	@View
	private A aOdontologia_evolucion;
	@View
	private A aOdontologia_rcho;
	@View
	private A aImagenesDiagnosticas;
	IRoles iroles;
	@View
	private A aVacunacionPai;

	private Ficha_inicio_lepraService ficha_inicio_lepraService;
	private Seguimiento_control_pqtService seguimiento_control_pqtService;
	// ------------------ FIN LEPRA

	private Admision admision_seleccionada;

	@View
	private Div divAsistencial;
	@View
	private Div divVisitaDomiciliaria;
	@View
	private Div divTuberculosis_lepra;
	@View
	private Listbox lbxBuscar_paciente_tuberculosis;
	@View
	private Textbox tbxValuePaciente_tuberculosis;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		dtxFecha.setValue(new Date());
		parametrizarResultadoPaginado();

		if (tbxVia_ingreso.getValue().equals(IVias_ingreso.URGENCIA)) {
			auxheaderAsistencial.setColspan(10);

		}

		if (tbxVia_ingreso.getValue().equals(IVias_ingreso.VISITA_DOMICILIARIA)) {
			// buscarDatos_VisitaDomiciliaria();
			divVisitaDomiciliaria.setVisible(true);
			divAsistencial.setVisible(false);
			divTuberculosis_lepra.setVisible(false);
		} else if ((tbxVia_ingreso.getValue()
				.equals(IVias_ingreso.CONTROL_TUBERCULOSIS))
				|| (tbxVia_ingreso.getValue()
						.equals(IVias_ingreso.CONTROL_LEPRA))) {
			buscarDatos_Tuberculosis_lepra();
			divTuberculosis_lepra.setVisible(true);
			divVisitaDomiciliaria.setVisible(false);
			divAsistencial.setVisible(false);
		} else {
			buscarDatos();
			divAsistencial.setVisible(true);
			divVisitaDomiciliaria.setVisible(false);
			divTuberculosis_lepra.setVisible(false);
		}

		if (!empresa.getUtiliza_info_sio()) {
			aSios.setVisible(false);
		}

		if (tbxVia_ingreso.getValue().equals(IVias_ingreso.URGENCIA)
				|| tbxVia_ingreso.getValue().equals(
						IVias_ingreso.FORMULARIO_TRIAGE)) {
			timerConsultar.start();
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		tbxVia_ingreso.setValue((String) map.get(IVias_ingreso.VIA_INGRESO));
	}

	public void accionForm(String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			if (tbxVia_ingreso.getValue().equals(
					IVias_ingreso.VISITA_DOMICILIARIA)) {
				buscarDatos_VisitaDomiciliaria();
				divVisitaDomiciliaria.setVisible(true);
				divAsistencial.setVisible(false);
				divTuberculosis_lepra.setVisible(false);
				log.info("visita");
			} else if ((tbxVia_ingreso.getValue()
					.equals(IVias_ingreso.CONTROL_TUBERCULOSIS))
					|| (tbxVia_ingreso.getValue()
							.equals(IVias_ingreso.CONTROL_LEPRA))) {
				buscarDatos_Tuberculosis_lepra();
				divTuberculosis_lepra.setVisible(true);
				divVisitaDomiciliaria.setVisible(false);
				divAsistencial.setVisible(false);
				// log.info("tuberculosis");
			} else {
				buscarDatos();
				divAsistencial.setVisible(true);
				divVisitaDomiciliaria.setVisible(false);
				divTuberculosis_lepra.setVisible(false);
				// log.info("asistencial");
			}

			groupboxConsultar.setVisible(true);
			borderlayoutEditar.setVisible(false);
		} else {
			groupboxConsultar.setVisible(false);
			borderlayoutEditar.setVisible(true);
		}
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Admision> listarResultados(
					Map<String, Object> parametros) {
				List<Admision> listado = getServiceLocator()
						.getAdmisionService().listarResultados(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getAdmisionService()
						.totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFila((Admision) dato);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				listboxAdmisiones, 6);
	}

	public void accionForm2(final String accion) throws Exception {

		if (!admision_seleccionada.getAtendida()) {
			Messagebox.show(usuarios.getNombres()
					+ " estas seguro de realizar esta accion ? "
					+ "Si no ha guardado la historia se perderán los cambios!",
					"Informacion ..", Messagebox.YES + Messagebox.NO,
					Messagebox.INFORMATION, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {

							if (event.getName().equals("onYes")) {

								if (!accion.equalsIgnoreCase("registrar")) {
									admision_seleccionada.setAtendiendo(false);
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("admision", admision_seleccionada);
									// map.put("parametros_empresa",
									// parametros_empresa);
									admisionService.actualizar(map);
									eliminarAdmisionSession();

									if (tbxVia_ingreso.getValue().equals(
											IVias_ingreso.VISITA_DOMICILIARIA)) {
										buscarDatos_VisitaDomiciliaria();
										divVisitaDomiciliaria.setVisible(true);
										divAsistencial.setVisible(false);
										divTuberculosis_lepra.setVisible(false);
										// log.info("visita");
									} else if ((tbxVia_ingreso.getValue()
											.equals(IVias_ingreso.CONTROL_TUBERCULOSIS))
											|| (tbxVia_ingreso.getValue()
													.equals(IVias_ingreso.CONTROL_LEPRA))) {
										buscarDatos_Tuberculosis_lepra();
										divTuberculosis_lepra.setVisible(true);
										divVisitaDomiciliaria.setVisible(false);
										divAsistencial.setVisible(false);
										// log.info("tuberculosis");
									} else {
										buscarDatos();
										divAsistencial.setVisible(true);
										divVisitaDomiciliaria.setVisible(false);
										divTuberculosis_lepra.setVisible(false);
										// log.info("asistencial");
										if (tbxVia_ingreso
												.getValue()
												.equals(IVias_ingreso.FORMULARIO_TRIAGE)
												|| tbxVia_ingreso
														.getValue()
														.equals(IVias_ingreso.URGENCIA)) {
											timerConsultar.start();
										}
									}

									groupboxConsultar.setVisible(true);
									borderlayoutEditar.setVisible(false);

								} else {
									groupboxConsultar.setVisible(false);
									borderlayoutEditar.setVisible(true);
								}
							}
						}
					});
		} else {
			if (!accion.equalsIgnoreCase("registrar")) {
				if (tbxVia_ingreso.getValue().equals(
						IVias_ingreso.VISITA_DOMICILIARIA)) {
					buscarDatos_VisitaDomiciliaria();
					divVisitaDomiciliaria.setVisible(true);
					divAsistencial.setVisible(false);
					divTuberculosis_lepra.setVisible(false);
					// log.info("visita");
				} else if ((tbxVia_ingreso.getValue()
						.equals(IVias_ingreso.CONTROL_TUBERCULOSIS))
						|| (tbxVia_ingreso.getValue()
								.equals(IVias_ingreso.CONTROL_LEPRA))) {
					buscarDatos_Tuberculosis_lepra();
					divTuberculosis_lepra.setVisible(true);
					divVisitaDomiciliaria.setVisible(false);
					divAsistencial.setVisible(false);
					// log.info("tuberculosis");
				} else {
					buscarDatos();
					divAsistencial.setVisible(true);
					divVisitaDomiciliaria.setVisible(false);
					divTuberculosis_lepra.setVisible(false);
					// log.info("asistencial");
				}

				groupboxConsultar.setVisible(true);
				borderlayoutEditar.setVisible(false);
			} else {
				groupboxConsultar.setVisible(false);
				borderlayoutEditar.setVisible(true);
			}
		}
	}

	Integer cont = 0;

	public void buscarDatos() throws Exception {
		try {
			if (cont.equals(5)) {
				cont = 0;
			}
			cont = cont + 1;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			if (filtrarPorViaIngreso(cont) != null) {
				parameters.put("vias_ingreso", filtrarPorViaIngreso(cont));
			}

			if (!((String) lbxEstado_admision.getSelectedItem().getValue())
					.isEmpty()) {
				parameters.put("estado", (String) lbxEstado_admision
						.getSelectedItem().getValue());
			}
			parameters.put("fecha", new SimpleDateFormat("yyyy-MM-dd")
					.format(dtxFecha.getValue()));

			List<String> vias_ingreso_excluyentes = new ArrayList<String>();
			vias_ingreso_excluyentes.add(IVias_ingreso.URGENCIA);
			vias_ingreso_excluyentes.add(IVias_ingreso.HOSPITALIZACIONES);

			parameters
					.put("vias_ingreso_excluyentes", vias_ingreso_excluyentes);
			parameters.put("paramTodo", "");
			parameters.put("value", "%"
					+ tbxValueAdmision.getValue().toUpperCase().trim() + "%");

			parameters.put("codigo_centro",
					centro_atencion_session.getCodigo_centro());
			boolean atendida_boolean = false;
			parameters.put("atendida", atendida_boolean);

			// parametro para filtrar los pacientes por el medico que esta en la
			// sesion
			// log.info("rol>>>>" + rol_usuario);
			if (!rol_usuario.equals(IRoles.ADMINISTRADOR)) {
				parameters.put("codigo_medico_mod", usuarios.getCodigo());
			}

			parameters
					.put("order",
							"fecha_ingreso asc,creacion_date,apellido1,apellido2,nombre1,nombre2");

			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	public void buscarDatos_VisitaDomiciliaria() throws Exception {
		// log.info("ejecutando metodo @buscarDatos() ===> VIA_INGRESO="
		// + tbxVia_ingreso.getValue());

		listboxAdmisiones.getItems().clear();
		try {
			@SuppressWarnings("unused")
			String atendida = "2";
			String buscar_paciente = (String) lbxBuscar_paciente
					.getSelectedItem().getValue();

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", empresa.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param.put("codigo_servicio",
					ServiciosDisponiblesUtils.CODSER_PSICOLOGIA);

			param.put("paramTodo", buscar_paciente);
			param.put("value", "%"
					+ tbxValueAdmision.getValue().toUpperCase().trim() + "%");

			List<Paciente> servicios = pacienteService
					.listarPacienteSaludMental(param);

			// log.info("---------->" + servicios);
			for (Paciente paciente : servicios) {

				Admision a = new Admision();
				a.setCodigo_empresa(empresa.getCodigo_empresa());
				a.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				a.setVia_ingreso(tbxVia_ingreso.getValue());
				a.setEstado("1");
				a.setAtendida(false);
				a.setFecha_ingreso(new Timestamp(dtxFecha.getValue().getTime()));
				a.setPaciente(paciente);
				a.setNro_ingreso("0001");
				a.setNro_identificacion(paciente.getNro_identificacion());

				listboxAdmisiones.appendChild(crearFila(a));

			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void buscarDatos_Tuberculosis_lepra() throws Exception {

		// log.info("ejecutando metodo @buscarDatos() ===> VIA_INGRESO="
		// + tbxVia_ingreso.getValue());
		listboxAdmisiones.getItems().clear();
		try {

			String atendida = "2";
			String buscar_paciente = (String) lbxBuscar_paciente_tuberculosis
					.getSelectedItem().getValue();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("buscar_paciente", buscar_paciente);

			if (tbxVia_ingreso.getValue().equals(
					IVias_ingreso.CONTROL_TUBERCULOSIS)) {
				parameters.put("parameter",
						IConstantes.CODIGOS_CIE_TUBERCULOSIS);

			} else if (tbxVia_ingreso.getValue().equals(
					IVias_ingreso.CONTROL_LEPRA)) {
				parameters.put("parameter", IConstantes.CODIGOS_CIE_LEPRA);

			}
			parameters.put("paramTodo", "");
			parameters.put("value", "%"
					+ tbxValuePaciente.getValue().toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Paciente> listado = getServiceLocator().getPacienteService()
					.listar_conTuberculosis_lepra(parameters);

			// log.info("1" + listado);
			for (Paciente paciente : listado) {
				Admision a = new Admision();
				// a.setCodigo_empresa(empresa.getCodigo_empresa());
				// a.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				a.setVia_ingreso(tbxVia_ingreso.getValue());
				a.setEstado("1");
				a.setAtendida((atendida.equals("1") ? true : false));
				a.setFecha_ingreso(new Timestamp(dtxFecha.getValue().getTime()));
				a.setPaciente(paciente);
				a.setNro_ingreso("0000");
				a.setNro_identificacion(paciente.getNro_identificacion());

				listboxAdmisiones.appendChild(crearFila(a));
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public List<String> filtrarPorViaIngreso(Integer numero) {
		List<String> vias_ingreso_aplican = new ArrayList<String>();

		switch (numero) {
		case 1:
			vias_ingreso_aplican.add(IVias_ingreso.ALTERACION_JOVEN);
			vias_ingreso_aplican.add(IVias_ingreso.PLANIFICACION_FAMILIAR);
			vias_ingreso_aplican.add(IVias_ingreso.HIPERTENSO_DIABETICO);
			vias_ingreso_aplican.add(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);
			vias_ingreso_aplican.add(IVias_ingreso.ADULTO_MAYOR);

			vias_ingreso_aplican.add(IVias_ingreso.HC_MENOR_2_MESES);
			vias_ingreso_aplican.add(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);
			vias_ingreso_aplican.add(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);
			vias_ingreso_aplican
					.add(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A);
			break;
		case 2:
			vias_ingreso_aplican.add(IVias_ingreso.CONSULTA_EXTERNA);
			vias_ingreso_aplican.add(IVias_ingreso.CONSULTA_ESPECIALIZADA);
			/*
			 * vias_ingreso_aplican.add(IVias_ingreso.HC_AIEPI_2_MESES);
			 * vias_ingreso_aplican.add(IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
			 */
			break;
		case 3:
			vias_ingreso_aplican.add(IVias_ingreso.ODONTOLOGIA);
			vias_ingreso_aplican.add(IVias_ingreso.ODONTOLOGIA2);
			vias_ingreso_aplican.add(IVias_ingreso.URGENCIA_ODONTOLOGICO);
			break;
		case 4:
			vias_ingreso_aplican.add(IVias_ingreso.PSICOLOGIA);
			vias_ingreso_aplican.add(IVias_ingreso.PSIQUIATRIA);
			break;
		case 5:
			vias_ingreso_aplican.add(IVias_ingreso.LABORATORIOS);
			vias_ingreso_aplican.add(IVias_ingreso.ECOGRAFIA);
			vias_ingreso_aplican.add(IVias_ingreso.ECOGRAFIA_GINECOSTETRICA);
			break;

		default:
			break;
		}
		return vias_ingreso_aplican;
	}

	private Listcell crearCeldaEstado(Admision admision, Listitem listitem) {
		Toolbarbutton toolbarbutonEstado = new Toolbarbutton("", "");
		Listcell liscell = new Listcell();

		if (!admision.getAtendida() && !admision.getAtendiendo()) {
			liscell.appendChild(toolbarbutonEstado);
			liscell.setStyle("text-align:center;background-color:yellow");
			toolbarbutonEstado.setImage("/images/espera.png");
			toolbarbutonEstado.setLabel("Espera...");
			toolbarbutonEstado.setStyle("color:black;font-weight:bold");
			listitem.setDisabled(false);
		}

		if (!admision.getAtendida() && admision.getAtendiendo()) {
			liscell.appendChild(toolbarbutonEstado);
			liscell.setStyle("text-align:center;background-color:red");
			toolbarbutonEstado.setImage("/images/process.png");
			toolbarbutonEstado.setLabel("Proceso");
			toolbarbutonEstado.setStyle("color:black;font-weight:bold");
			listitem.setDisabled(true);

		}

		return liscell;
	}

	public Listitem crearFila(final Admision admision) {
		String apellidosPrestador = "";
		String nombrePrestador = "";
		String servicio = Utilidades.getNombreElemento(
				admision.getVia_ingreso(), "via_ingreso", getServiceLocator());

		Paciente paciente = admision.getPaciente();
		Usuarios u = new Usuarios();
		u.setCodigo(admision.getCodigo_medico());
		u.setCodigo_empresa(admision.getCodigo_empresa());
		u.setCodigo_sucursal(admision.getCodigo_sucursal());

		u = getServiceLocator().getUsuariosService().consultar(u);

		if (u != null) {
			apellidosPrestador = u.getApellidos();
			nombrePrestador = u.getNombres();

		}
		final String via_ingreso = tbxVia_ingreso.getValue();

		String apellidos = (paciente != null ? paciente.getApellido1() + " "
				+ paciente.getApellido2() : "");
		String nombres = (paciente != null ? paciente.getNombre1() + " "
				+ paciente.getNombre2() : "");

		final Listitem listitem = new Listitem();

		listitem.setStyle("cursor:pointer");
		listitem.setValue(admision);
		listitem.appendChild(new Listcell());

		listitem.appendChild(crearCeldaEstado(admision, listitem));

		listitem.appendChild(Utilidades.obtenerListcell(
				admision != null ? admision.getNro_ingreso() : "", Label.class,
				true, true));
		// servicio
		listitem.appendChild(Utilidades.obtenerListcell(servicio, Label.class,
				true, true));

		if (servicio.equals("Alteracion Joven")
				|| servicio.equals("Planificacion Familiar")
				|| servicio.equals("Hipertenso y Diabetico")) {
			servicio = "PROGRAMADA PYP";

		}
		captionEncabezado
				.setLabel("LISTADO DE PACIENTES ADMITIDOS PARA EL SERVICIO DE  "
						+ servicio.toUpperCase());
		// datos del paciente
		listitem.appendChild(Utilidades.obtenerListcell(apellidos + " "
				+ nombres, Label.class, true, true));
		// Datos del prestador
		listitem.appendChild(Utilidades.obtenerListcell(apellidosPrestador
				+ " " + nombrePrestador, Label.class, true, true));
		// ---------------------------------------------------------

		His_triage his_triage = null;

		if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
			Listcell listcell_traige = new Listcell();
			his_triage = new His_triage();
			his_triage.setCodigo_empresa(codigo_empresa);
			his_triage.setCodigo_sucursal(codigo_sucursal);
			his_triage.setIdentificacion(admision.getNro_identificacion());
			his_triage.setNro_ingreso(admision.getNro_ingreso());

			his_triage = getServiceLocator().getHis_triageService().consultar(
					his_triage);

			Popup popup_traige = new Popup();
			Html html = new Html();
			popup_traige.appendChild(html);
			divPopups_traige.appendChild(popup_traige);

			if (his_triage != null) {
				String style = his_triage.getNivel_triage().equals("1") ? "red"
						: his_triage.getNivel_triage().equals("2") ? "orange"
								: his_triage.getNivel_triage().equals("3") ? "yellow"
										: "green";
				listcell_traige.setStyle("text-align:center;background-color:"
						+ style
						+ ";font-weight:bold;border-style:solid;cursor : help");
				listcell_traige
						.setLabel(his_triage.getNivel_triage().equals("1") ? "I"
								: his_triage.getNivel_triage().equals("2") ? "II"
										: his_triage.getNivel_triage().equals(
												"3") ? "III" : "IV");
				html.setContent("Paciente clasificado con Triage Nivel "
						+ listcell_traige.getLabel()
						+ ". <br/>"
						+ (his_triage.getAdmitido_si().equals("1") ? "Urgencia General"
								: his_triage.getAdmitido_si().equals("2") ? "Urgencia Obstétrica"
										: ""));
			} else {
				listcell_traige
						.setStyle("text-align:center;font-weight:bold;border-style:solid;cursor : help");
				listcell_traige.setImage("/images/interrogracion.png");
				html.setContent("Paciente admitido omitiendo clasificacion de triage");
			}
			listcell_traige.setTooltip(popup_traige);
			listitem.appendChild(listcell_traige);

		}

		final His_triage his_triage_aux = his_triage;

		listitem.addEventListener(Events.ON_FOCUS, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (!listitem.isDisabled()) {
					listitem.setSelected(true);
					if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
						if (admision.getAtendida()) {
							Hisc_urgencia hisc_urgencia = new Hisc_urgencia();
							hisc_urgencia.setCodigo_empresa(codigo_empresa);
							hisc_urgencia.setCodigo_sucursal(codigo_sucursal);
							hisc_urgencia.setNro_identificacion(admision
									.getNro_identificacion());
							// manuel
							admision.setAtendiendo(true);
							hisc_urgencia.setNro_ingreso(admision
									.getNro_ingreso());
							hisc_urgencia
									.setVia_ingreso(IVias_ingreso.URGENCIA);
							hisc_urgencia = getServiceLocator().getServicio(
									Hisc_urgenciaService.class).consultar(
									hisc_urgencia);

							if (hisc_urgencia != null) {
								tbxTipo_urgencia.setValue(hisc_urgencia
										.getTipo_urgencia());
								onSeleccionarAdmision(listitem);
							} else {
								throw new Exception(
										"No hay una historia clinica de urgencias relacionada con esta admision");
							}

						} else {

							int edad = Integer.parseInt(Util.getEdad(
									new java.text.SimpleDateFormat("dd/MM/yyyy")
											.format(admision.getPaciente()
													.getFecha_nacimiento()),
									"1", false));

							if (admision.getPaciente().getSexo().equals("M")) {
								tbxTipo_urgencia
										.setValue(IVias_ingreso.TIPO_URGENCIA_GENERAL);
								// onSeleccionarAdmision(listitem);
							} else if (admision.getPaciente().getSexo()
									.equals("F")
									&& edad < 7) {
								tbxTipo_urgencia
										.setValue(IVias_ingreso.TIPO_URGENCIA_GENERAL);
								// onSeleccionarAdmision(listitem);
							}
							if (admision.getPaciente().getSexo().equals("F")
									&& edad > 7) {

								if (his_triage_aux != null) {
									tbxTipo_urgencia
											.setValue(his_triage_aux
													.getAdmitido_si().equals(
															"1") ? IVias_ingreso.TIPO_URGENCIA_GENERAL
													: his_triage_aux
															.getAdmitido_si()
															.equals("2") ? IVias_ingreso.TIPO_URGENCIA_OBSTETRICA
															: "");
									onSeleccionarAdmision(listitem);
								} else {

									final Window window = new Window(
											"Seleccinar Historia Clinica de Urgencias",
											"normal", true);
									window.setPage(Modulo_pacienteAction.this
											.getPage());
									Hlayout hlayoutContenedor = new Hlayout();

									Button btnUrgencia = new Button(
											"Historia Clinica de Urgencia");
									btnUrgencia.setMold("trendy");
									btnUrgencia.addEventListener(
											Events.ON_CLICK,
											new EventListener<Event>() {

												@Override
												public void onEvent(Event arg0)
														throws Exception {
													tbxTipo_urgencia
															.setValue(IVias_ingreso.TIPO_URGENCIA_GENERAL);
													admision.setAdmision_parto("N");
													onSeleccionarAdmision(listitem);
													window.setVisible(false);
												}
											});
									hlayoutContenedor.appendChild(btnUrgencia);
									hlayoutContenedor.appendChild(new Space());

									Button btnParto = new Button(
											"Historia Clinica Obstetrica");

									btnParto.addEventListener(Events.ON_CLICK,
											new EventListener<Event>() {

												@Override
												public void onEvent(Event arg0)
														throws Exception {
													tbxTipo_urgencia
															.setValue(IVias_ingreso.TIPO_URGENCIA_OBSTETRICA);
													admision.setAdmision_parto("S");
													onSeleccionarAdmision(listitem);
													window.setVisible(false);
												}
											});
									btnParto.setMold("trendy");
									hlayoutContenedor.appendChild(btnParto);
									window.appendChild(hlayoutContenedor);
									window.doModal();

									window.addEventListener(Events.ON_CLOSE,
											new EventListener<Event>() {

												@Override
												public void onEvent(Event arg0)
														throws Exception {
													listitem.setSelected(false);
												}

											});
								}

							} else {
								tbxTipo_urgencia
										.setValue(IVias_ingreso.TIPO_URGENCIA_GENERAL);
								onSeleccionarAdmision(listitem);
							}
						}
					} else if (via_ingreso.equals(IVias_ingreso.LABORATORIOS)) {
						// log.info(":: seleccionados laboratorios");
						onSeleccionarAdmisionLaboratorio(listitem);

					} else if (via_ingreso.equals(IVias_ingreso.ENFERMERIA_PYP)) {
						// log.info(":: enfermeria pyp");
						/*
						 * String programa_pyp = admision.getVia_ingreso();
						 * 
						 * //log.info("programa_pyp"+programa_pyp);
						 * 
						 * admision.setVia_ingreso(programa_pyp);
						 */
						onSeleccionarAdmision(listitem);

					} else if (via_ingreso
							.equals(IVias_ingreso.VISITA_DOMICILIARIA)) {
						// log.info(":: seleccionada visita domiciliaria");

						Admision admisionAux = new Admision();
						admisionAux.setCodigo_empresa(empresa
								.getCodigo_empresa());
						admisionAux.setCodigo_sucursal(sucursal
								.getCodigo_sucursal());
						admisionAux.setNro_identificacion(admision
								.getNro_identificacion());
						admisionAux.setCodigo_administradora(admision
								.getPaciente().getCodigo_administradora());
						admisionAux
								.setVia_ingreso(IVias_ingreso.VISITA_DOMICILIARIA);
						admisionAux.setEstado("1");
						admisionAux.setCodigo_medico("000000000");
						admisionAux.setNro_autorizacion("");
						admisionAux.setTipo_atencion("");
						admisionAux.setCodigo_especialidad("");
						admisionAux.setIngreso_programa("N");
						admisionAux.setPrimera_vez("N");
						admisionAux.setCondicion_usuaria("");
						admisionAux.setCausa_externa("13");
						admisionAux.setTipo_diagnostico("1");
						admisionAux.setDiagnostico_ingreso("Z000");
						admisionAux.setTipo_discapacidad("");
						admisionAux.setGrado_discapacidad("");
						admisionAux.setFecha_ingreso(admision
								.getFecha_ingreso());
						admisionAux.setCreacion_date(admision
								.getFecha_ingreso());
						admisionAux.setUltimo_update(admision
								.getFecha_ingreso());
						admisionAux.setCreacion_user(usuarios.getCodigo()
								.toString());
						admisionAux.setUltimo_user(usuarios.getCodigo()
								.toString());
						admisionAux.setUrgencias("N");
						admisionAux.setHospitalizacion("N");
						admisionAux.setRecien_nacido("N");
						admisionAux.setAtendida(false);
						admisionAux.setCodigo_cita("");
						admisionAux.setCama("");
						admisionAux.setPaciente(admision.getPaciente());

						admisionAux.setAplica_triage(false);
						admisionAux.setRealizo_triage(false);
						admisionAux.setAplica_tuberculosis(false);
						admisionAux.setAplica_lepra(false);
						admisionAux.setTipo_consulta("1");
						admisionAux.setTipo_adicional_via_ingreso("");
						admisionAux.setFecha_atencion(admision
								.getFecha_ingreso());
						admisionAux.setAdmision_parto("N");
						admisionAux.setCodigo_centro(centro_atencion_session
								.getCodigo_centro());

						listitem.setValue(admisionAux);

						onSeleccionarAdmision(listitem);
					} else {
						onSeleccionarAdmision(listitem);
					}
				}
			}
		});

		return listitem;
	}

	protected void onSeleccionarAdmisionLaboratorio(Listitem listitem) {
		if (!Utilidades.isAdministrador(usuarios.getRol())
				&& (Utilidades.isMedicoPyP(usuarios.getRol()) || Utilidades
						.isMedico(usuarios.getRol()))
				|| Utilidades.isEnfermeraJefe(usuarios.getRol())
				|| Utilidades.isAuxiliarOEnfermeraPyP(usuarios.getRol())) {
			Admision admision = (Admision) listitem.getValue();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(IVias_ingreso.ADMISION_PACIENTE, admision);
			TomaMuestraAction tomaMuestraAction = (TomaMuestraAction) Executions
					.createComponents("/pages/toma_muestra.zul", this, map);
			tomaMuestraAction.setWidth("100%");
			tomaMuestraAction.setHeight("100%");
			tomaMuestraAction.doModal();
		} else {
			MensajesUtil
					.mensajeAlerta("Administrador",
							"¿Debe seleccionar un rol de prestador para poder crear una historia clinica!");
		}
	}

	private boolean puedeCrearHistoria() {
		Integer c_prestador = 0;
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_usuario", usuarios.getCodigo());

		List<Roles_usuarios> listado_roles = roles_usuariosService
				.listar(parametros);

		for (Roles_usuarios roles_usuarios : listado_roles) {
			if (Utilidades.isMedico(roles_usuarios.getRol())
					|| Utilidades.isEnfermeraJefe(roles_usuarios.getRol())) {
				c_prestador++;
			}
		}

		return (c_prestador > 0);
	}

	public void onSeleccionarAdmision(Listitem listitem) throws Exception {
		try {
			if (puedeCrearHistoria()) {
				Admision admision = (Admision) listitem.getValue();
				admision = getServiceLocator().getAdmisionService().consultar(
						admision);
				admision.setAtendiendo(true);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("admision", admision);
				// map.put("parametros_empresa", parametros_empresa);
				admisionService.actualizar(map);
				admision_seleccionada = admision;
				accionForm("registrar");
				mostrarDatos();
				inicializarContenedorPaginas();
				cargarHistorialHistorias();
				borderlayoutEditar.invalidate();
				actualizarAdmisionSession();
			} else {
				MensajesUtil
						.mensajeAlerta("Administrador",
								"¿Debe seleccionar un rol de prestador para poder crear una historia clinica!");
			}
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null) {
				listitem.setDisabled(false);
			}
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null) {
				listitem.setDisabled(false);
			}
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	public void mostrarDatos() throws Exception {
		if (admision_seleccionada != null) {
			toolbarbuttonInformacion_paciente.setLabel(admision_seleccionada
					.getPaciente().getNombreCompleto());
			tbxIngreso.setValue(admision_seleccionada.getNro_ingreso());
			tbxNro_identificacion.setValue(admision_seleccionada
					.getNro_identificacion());

			// Agregado por jhonny
			Map<String, Integer> mapa_edades = Util
					.getEdadYYYYMMDD(admision_seleccionada.getPaciente()
							.getFecha_nacimiento());

			Integer anios = mapa_edades.get("anios");
			Integer meses = mapa_edades.get("meses");
			Integer dias = mapa_edades.get("dias");

			if (anios.intValue() == 0 && meses.intValue() == 0) {
				tbxEdad.setValue(dias + (dias == 1 ? " día" : " días"));
			} else if (anios.intValue() == 0) {
				tbxEdad.setValue(meses + (meses == 1 ? " mes (" : " meses (")
						+ (dias + (dias == 1 ? " día" : " días")) + ")");
			} else {
				int current_meses = meses.intValue() - (anios.intValue() * 12);
				tbxEdad.setValue(anios
						+ (anios == 1 ? " año" : " años")
						+ (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ? " mes "
								: " meses"))
								: ""));
			}

			if (admision_seleccionada.getPaciente().getSexo() != null) {
				if (admision_seleccionada.getPaciente().getSexo().equals("M")) {
					tbxSexo.setValue("MASCULINO");
				} else if (admision_seleccionada.getPaciente().getSexo()
						.equals("F")) {
					tbxSexo.setValue("FEMENINO");
				}
			}

			// Fin Jhonny
			tbxTipo_identificacion.setValue(Utilidades.getNombreElemento(
					admision_seleccionada.getPaciente()
							.getTipo_identificacion(), "tipo_id", this));
			tbxFecha_ingreso.setValue(new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss").format(admision_seleccionada
					.getFecha_ingreso()));
			tbxAseguradora
					.setValue(admision_seleccionada.getAdministradora() != null ? admision_seleccionada
							.getAdministradora().getCodigo()
							+ " "
							+ admision_seleccionada.getAdministradora()
									.getNombre() : admision_seleccionada
							.getCodigo_administradora());
			tbxContrato
					.setValue(admision_seleccionada.getContratos() != null ? admision_seleccionada
							.getContratos().getNombre() : admision_seleccionada
							.getId_plan());

			Nivel_educativo educacion = new Nivel_educativo();
			educacion.setId(admision_seleccionada.getPaciente()
					.getCodigo_educativo());
			educacion = getServiceLocator().getNivel_educativoService()
					.consultar(educacion);

			Pertenencia_etnica etnica = new Pertenencia_etnica();
			etnica.setId(admision_seleccionada.getPaciente()
					.getPertenencia_etnica());
			etnica = getServiceLocator().getPertenencia_etnicaService()
					.consultar(etnica);

			tbxNivel_educativo.setValue(educacion != null ? educacion
					.getNombre() : "");
			tbxPertencia_etnica.setValue("("
					+ admision_seleccionada.getPaciente()
							.getPertenencia_etnica() + ") "
					+ (etnica != null ? etnica.getNombre() : ""));
		} else {
			throw new Exception("No hay una admision seleccionada");
		}

	}

	public void inicializarContenedorPaginas() throws Exception {
		tabboxContendor.cerrarTabs();
		String via_ingreso = tbxVia_ingreso.getValue();
		// log.info("ejecutando metodo @inicializarContenedorPaginas() ===> VIA_INGRESO="
		// + via_ingreso);

		// CAMBIAR AQUI
		if (via_ingreso.equals(IVias_ingreso.ENFERMERIA_PYP)) {
			if (admision_seleccionada != null) {
				via_ingreso = admision_seleccionada.getVia_ingreso();
			}
		}

		if (admision_seleccionada != null) {
			boolean mostrar_prescripcion = true, mostrar_remision = true, mostrar_autorizaciones = true, mostrar_notas_odontologia = false;
			boolean mostrar_notasaclaratorias = false, mostrar_evolucion = false, mostrar_tuberculosis = false;
			boolean mostrar_epicrisis = false, mostrar_contrarreferencia = false;
			boolean mostrar_lepra = false, mostrar_ordenes_medicas = false, mostrar_notas_enfermeria = false, mostrar_registro_med = false;
			/*
			 * mostrar_seguimiento_lepra = false; boolean
			 * mostrar_discapacidades_lepra = false,mostrar_conviviente_lepra =
			 * false;
			 */

			Odontologia odontologiaTemp = null;

			tabboxContendor.cerrarTabs();

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.VIA_INGRESO, via_ingreso);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			parametros.put(IVias_ingreso.PADRE, this);
			parametros.put(IVias_ingreso.TIPO_URGENCIA,
					tbxTipo_urgencia.getValue());
			tabboxContendor.getTabs().setStyle(
					"background-image:url(../images/bar01.gif)");

			if (via_ingreso.equals(IVias_ingreso.FORMULARIO_TRIAGE)) {
				// log.info("ha entrado a la validacionn de triage ");
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_FORMULARIO_TRIAGE);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_FORMULARIO_TRIAGE);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				timerConsultar.stop();

			} else if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
				// log.info("via_ingreso.equals(IVias_ingreso.URGENCIA)");
				if (tbxTipo_urgencia.getValue().equals(
						IVias_ingreso.TIPO_URGENCIA_OBSTETRICA)) {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_HC_OBSTETRICA);
					tabboxContendor
							.setLabelTabInicio(IRutas_historia.LABEL_HC_OBSTETRICA);
				} else if (tbxTipo_urgencia.getValue().equals(
						IVias_ingreso.TIPO_URGENCIA_GENERAL)) {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_HC_URGENCIA);
					tabboxContendor
							.setLabelTabInicio(IRutas_historia.LABEL_HC_URGENCIA);
				}
				mostrar_ordenes_medicas = true;
				mostrar_notas_enfermeria = true;
				mostrar_registro_med = true;
				mostrar_epicrisis = true;
				mostrar_notasaclaratorias = false;
				mostrar_contrarreferencia = true;
				mostrar_autorizaciones = false;
				mostrar_evolucion = true;
				mostrar_prescripcion = false;
				mostrar_remision = false;
				timerConsultar.stop();
			} else if (via_ingreso
					.equals(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_RECIEN_NACIDO);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_RECIEN_NACIDO);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_notasaclaratorias = false;
				mostrar_epicrisis = true;
				mostrar_ordenes_medicas = true;
				mostrar_notas_enfermeria = true;
			} else if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA)
					|| via_ingreso.equals(IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
				Citas citas = new Citas();
				citas.setCodigo_empresa(admision_seleccionada
						.getCodigo_empresa());
				citas.setCodigo_sucursal(admision_seleccionada
						.getCodigo_sucursal());
				citas.setCodigo_cita(admision_seleccionada.getCodigo_cita());
				citas.setNro_identificacion(admision_seleccionada
						.getNro_identificacion());

				citas = getServiceLocator().getCitasService().consultar(citas);

				parametros.put(IVias_ingreso.CITA_PACIENTE, citas);

				if (parametros_empresa.getTipo_historia_clinica().equals("01")) {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_1);
				} else if (parametros_empresa.getTipo_historia_clinica()
						.equals("02")) {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_2);
				} else {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_3);
					if (admision_seleccionada.isAplica_tuberculosis()) {
						mostrar_tuberculosis = true;
					} else {
						mostrar_tuberculosis = false;
					}
					if (admision_seleccionada.isAplica_lepra()) {
						mostrar_lepra = true;
					} else {
						mostrar_lepra = false;
					}
					mostrar_prescripcion = false;
					mostrar_remision = false;
					mostrar_autorizaciones = false;
				}

				if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA)) {
					tabboxContendor
							.setLabelTabInicio(IRutas_historia.LABEL_CONSULTA_EXTERNA);
				}

				int edad = Integer.parseInt(Util.getEdad(
						new java.text.SimpleDateFormat("dd/MM/yyyy")
								.format(admision_seleccionada.getPaciente()
										.getFecha_nacimiento()), "1", false));

				if (edad == 0) {
					int edad_meses = Integer
							.parseInt(Util
									.getEdad(new java.text.SimpleDateFormat(
											"dd/MM/yyyy")
											.format(admision_seleccionada
													.getPaciente()
													.getFecha_nacimiento()),
											"2", false));

					if (edad_meses < 2) {
						tabboxContendor
								.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES);
						tabboxContendor
								.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES);
					} else {
						tabboxContendor
								.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES_5_ANOS);
						tabboxContendor
								.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS);
					}

				} else if (edad < 5) {
					tabboxContendor
							.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES_5_ANOS);
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS);
				}

			} else if (via_ingreso.equals(IVias_ingreso.HIPERTENSO_DIABETICO)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_HIPETENSO_DIABETICO);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_HIPERTENSO_DIABETICO);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_MENOR_2_MESES);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_MENOR_2_MESES);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;

			} else if (via_ingreso
					.equals(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_MENOR_2_MESES_2_ANIOS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_MENOR_2_MESES_2_ANIOS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_MENOR_2_ANIOS_5_ANIOS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_MENOR_2_ANIOS_5_ANIOS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso
					.equals(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_MENOR_5_ANIOS_10_ANIOS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_MENOR_5_ANIOS_10_ANIOS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso
					.equals(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ALTERACION_EMBARAZO);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ALTERACION_EMBARAZO);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.ADULTO_MAYOR)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ADULTO_MAYOR);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ADULTO_MAYOR);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.ALTERACION_JOVEN)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ALTERACION_JOVEN);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ALTERACION_JOVEN);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.PLANIFICACION_FAMILIAR)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_PLANIFICACION_FAMILIAR);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_PLANIFICACION_FAMILIAR);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.PSICOLOGIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_PSICOLOGIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_PSICOLOGIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.PSIQUIATRIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_PSIQUIATRIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_PSIQUIATRIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ODONTOLOGIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ODONTOLOGIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ODONTOLOGIA2);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ODONTOLOGIA2);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_notas_odontologia = true;

				Map<String, Object> parametros_odontologia = new HashMap<String, Object>();
				parametros_odontologia.put("codigo_empresa", codigo_empresa);
				parametros_odontologia.put("codigo_sucursal", codigo_sucursal);
				parametros_odontologia.put("identificacion",
						admision_seleccionada.getNro_identificacion());

				List<Odontologia> listado_odontologia = getServiceLocator()
						.getOdontologiaService().listar(parametros_odontologia);
				admision_seleccionada.setPrimera_vez("S");
				if (!listado_odontologia.isEmpty()) {
					admision_seleccionada.setPrimera_vez("N");
					odontologiaTemp = listado_odontologia.get(0);
					parametros.put("odontologiaTemp", odontologiaTemp);
				}

			} else if (via_ingreso.equals(IVias_ingreso.URGENCIA_ODONTOLOGICO)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_URGENCIA_ODONTOLOGICO);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_URGENCIA_ODONTOLOGICO);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.VISITA_DOMICILIARIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_VISITA_DOMICILIARIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_VISITA_DOMICILIARIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.HC_AIEPI_2_MESES)) {
				Citas citas = new Citas();
				citas.setCodigo_empresa(admision_seleccionada
						.getCodigo_empresa());
				citas.setCodigo_sucursal(admision_seleccionada
						.getCodigo_sucursal());
				citas.setCodigo_cita(admision_seleccionada.getCodigo_cita());
				citas.setNro_identificacion(admision_seleccionada
						.getNro_identificacion());
				citas = getServiceLocator().getCitasService().consultar(citas);
				parametros.put(IVias_ingreso.CITA_PACIENTE,
						citas != null ? citas : new Citas());
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso
					.equals(IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS)) {
				Citas citas = new Citas();
				citas.setCodigo_empresa(admision_seleccionada
						.getCodigo_empresa());
				citas.setCodigo_sucursal(admision_seleccionada
						.getCodigo_sucursal());
				citas.setCodigo_cita(admision_seleccionada.getCodigo_cita());
				citas.setNro_identificacion(admision_seleccionada
						.getNro_identificacion());
				citas = getServiceLocator().getCitasService().consultar(citas);
				parametros.put(IVias_ingreso.CITA_PACIENTE,
						citas != null ? citas : new Citas());
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES_5_ANOS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_HC_HOSPITALIZACIONES);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_HC_HOSPITALIZACIONES);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_evolucion = true;
				mostrar_epicrisis = true;
				mostrar_contrarreferencia = true;
				mostrar_ordenes_medicas = true;
				mostrar_registro_med = true;
				mostrar_notas_enfermeria = true;
			} else if (via_ingreso.equals(IVias_ingreso.CONTROL_TUBERCULOSIS)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_HC_TUBERCULOSIS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_HC_TUBERCULOSIS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.CONTROL_LEPRA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_INICIO_TRATAMIENTO_LEPRA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_INICIO_TRATAMIENTO_LEPRA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_lepra = true;
			} else if (via_ingreso.equals(IVias_ingreso.ECOGRAFIA)
					&& admision_seleccionada.getTipo_adicional_via_ingreso()
							.equals(IVias_ingreso.ECOGRAFIA_GINECOSTETRICA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ECOGRAFIA_GINECOSTETRICA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ECOGRAFIA_GINECOSTETRICA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_evolucion = false;
				mostrar_contrarreferencia = false;
				mostrar_notasaclaratorias = false;
				mostrar_epicrisis = false;
			}

			tabboxContendor.inicializarInicio(false, parametros);

			if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)) {
				if (admision_seleccionada.getAtendida()) {
					if (odontologiaTemp != null) {
						cargarEvolucionOdonotologia(odontologiaTemp,
								OpcionesFormulario.REGISTRAR, false);
						// cargarRegClinicoHigiene(odontologiaTemp, false,
						// OpcionesFormulario.REGISTRAR);
					} else {
						throw new Exception(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}
				}
			}

			/*
			 * Permitimos cargar la preescripcion medica para todas las
			 * historias
			 */
			if (mostrar_ordenes_medicas) {
				cargarOrdenesMedicas();
			}

			if (mostrar_notas_enfermeria) {
				cargarNotasEnfermeria();
			}

			if (mostrar_registro_med) {
				cargarRegistroMedicamentos();
			}

			if (mostrar_tuberculosis) {
				cargarTarjetaTuberculosis();
			}

			if (mostrar_prescripcion) {
				cargarPreescripcionMedica();
			}

			if (mostrar_autorizaciones) {
				cargarAutorizaciones();
			}

			if (mostrar_remision) {
				cargarRemisiones();
			}

			if (mostrar_notasaclaratorias) {
				cargarNotasAclaratorias();
			}

			if (mostrar_notas_odontologia) {
				cargarNota_odontologia();
			}

			if (mostrar_evolucion) {
				cargarEvoluciones();
			}

			if (mostrar_epicrisis) {
				cargarEpicrisis();
			}

			if (mostrar_contrarreferencia) {
				cargarContrarreferencia();
			}

			if (mostrar_lepra) {
				cargarFichaLepra_inicio();
				cargarFichaLepra_control();
				cargarFichaLepra_discapacidades();
				cargarFichaLepra_convivientes();
			}

		} else {
			throw new Exception("No hay una admision seleccionada");
		}
	}

	/**
	 * Este metodo permite cargar la tarjeta de tuberculosis
	 *
	 */
	private void cargarTarjetaTuberculosis() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);
		parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
				Opciones_via_ingreso.REGISTRAR);
		tabboxContendor.abrirPaginaTabDemanda(false,
				IRutas_historia.PAGINA_HC_TUBERCULOSIS,
				IRutas_historia.LABEL_HC_TUBERCULOSIS, parametros);
	}

	/**
	 * Este metodo permite cargar la ficha de inicio de tratamiento lepra
	 *
	 */
	private void cargarFichaLepra_inicio() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);
		parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
				Opciones_via_ingreso.REGISTRAR);
		tabboxContendor.abrirPaginaTabDemanda(false,
				IRutas_historia.PAGINA_INICIO_TRATAMIENTO_LEPRA,
				IRutas_historia.LABEL_INICIO_TRATAMIENTO_LEPRA, parametros);
	}

	/**
	 * Este metodo permite cargar la ficha de seguimiento y control de
	 * tratamiento lepra
	 *
	 */
	private void cargarFichaLepra_control() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", tbxNro_identificacion.getValue());

		ficha_inicio_lepraService.setLimit("limit 25 offset 0");

		// log.info("parameters>>>>" + parameters);
		Boolean ficha_inicio = ficha_inicio_lepraService
				.existe_paciente_lepra(parameters);
		// log.info("ficha_inicio>>>>" + ficha_inicio);

		if (ficha_inicio) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_SEGUIMIENTO_TRATAMIENTO_LEPRA,
					IRutas_historia.LABEL_SEGUIMIENTO_TRATAMIENTO_LEPRA,
					parametros);
		}
	}

	/**
	 * Este metodo permite cargar la ficha de valorizacion de discapacidades
	 *
	 */
	private void cargarFichaLepra_discapacidades() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", tbxNro_identificacion.getValue());
		parameters.put("fecha_actual", new Date());

		// log.info("parameters" + parameters);
		seguimiento_control_pqtService.setLimit("limit 25 offset 0");

		// log.info("parameters>>>>" + parameters);
		Boolean fecha_tratamiento = seguimiento_control_pqtService
				.existe_fecha_fin_tratamiento(parameters);
		// log.info("fecha_tratamiento>>>>" + fecha_tratamiento);

		if (fecha_tratamiento) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_VALORACION_DISCAPACIDADES_LEPRA,
					IRutas_historia.LABEL_VALORACION_DISCAPACIDADES_LEPRA,
					parametros);
		}
	}

	/**
	 * Este metodo permite cargar la ficha de control de convivientes
	 *
	 */
	private void cargarFichaLepra_convivientes() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", tbxNro_identificacion.getValue());
		parameters.put("fecha_actual", new Date());

		// log.info("parameters" + parameters);
		seguimiento_control_pqtService.setLimit("limit 25 offset 0");

		// log.info("parameters>>>>" + parameters);
		Boolean fecha_tratamiento = seguimiento_control_pqtService
				.existe_fecha_fin_tratamiento(parameters);
		// log.info("fecha_tratamiento>>>>" + fecha_tratamiento);

		if (fecha_tratamiento) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_CONTROL_CONVIVIENTES_LEPRA,
					IRutas_historia.LABEL_CONTROL_CONVIVIENTES_LEPRA,
					parametros);
		}
	}

	/**
	 * Este metodo permite cargar las notas aclaratorias
	 *
	 */
	public void cargarEpicrisis() {
		if (admision_seleccionada != null
				&& admision_seleccionada.getAtendida()) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_HC_EPICRISIS,
					IRutas_historia.LABEL_HC_EPICRISIS, parametros);
		}

	}

	/**
	 * Este metodo permite cargar las notas aclaratorias
	 *
	 */
	private void cargarContrarreferencia() {
		if (admision_seleccionada != null
				&& !admision_seleccionada.getAtendida()) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_CONTRAREFERENCIA,
					IRutas_historia.LABEL_CONTRAREFERENCIA, parametros);
		}

	}

	/**
	 * Este metodo se encarga de llamar las recetas medicas
	 *
	 */
	private void cargarPreescripcionMedica() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
		tabboxContendor.abrirPaginaTabDemanda(false, "/pages/receta_rips.zul",
				"PRESCRIPCION MEDICA", parametros);
	}

	/**
	 * Este metodo permite cargar las Autorizaciones
	 *
	 */
	private void cargarAutorizaciones() {
		if (parametros_empresa != null) {
			if (parametros_empresa.getTrabaja_autorizacion()) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("nro_identificacion",
						admision_seleccionada.getNro_identificacion());
				parametros.put("nro_ingreso",
						admision_seleccionada.getNro_ingreso());
				parametros.put("estado", admision_seleccionada.getEstado());
				parametros.put("tipo_hc", "");
				parametros.put(IVias_ingreso.ADMISION_PACIENTE,
						admision_seleccionada);

				tabboxContendor.abrirPaginaTabDemanda(false,
						IRutas_historia.PAGINA_AUTORIZACIONES,
						"AUTORIZACIONES", parametros);
			}
		}
	}

	public void cargarAgudeza() {
		if (parametros_empresa != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);

			// tabboxContendor.abrirPaginaTabDemanda(false,
			// IRutas_historia.PAGINA_AGUDEZA_VISUAL,"AGUDEZA VISUAL",
			// parametros);
		}
	}

	public void cargarOrdenesMedicas() {
		if (parametros_empresa != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_ORDENES_MEDICAS,
					IRutas_historia.LABEL_ORDENES_MEDICAS, parametros);
		}
	}

	/**
	 * Este metodo permite cargar las notas de enfermeria
	 *
	 */
	private void cargarNotasEnfermeria() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("admision_seleccionada", admision_seleccionada);
		parametros.put("rol_medico", "S");
		tabboxContendor.abrirPaginaTabDemanda(false,
				"/pages/notas_enfermeria.zul", "NOTAS DE ENFERMERIA",
				parametros);
	}

	/**
	 * Este metodo permite cargar las notas de enfermeria
	 *
	 */
	private void cargarRegistroMedicamentos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("admision_seleccionada", admision_seleccionada);
		parametros.put("rol_medico", "S");
		tabboxContendor.abrirPaginaTabDemanda(false,
				"/pages/registro_medicamentos.zul", "REGISTRO DE MEDICAMENTOS",
				parametros);
	}

	/**
	 * Este metodo permite cargar las notas aclaratorias
	 *
	 */
	private void cargarNotasAclaratorias() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("tipo", INotas.NOTAS_ACLARATORIAS);
		tabboxContendor
				.abrirPaginaTabDemanda(false, "/pages/nota_aclaratoria.zul",
						"NOTAS ACLARATORIAS", parametros);
	}

	/**
	 * Metodo para cargar remisiones Remisiones
	 *
	 *
	 */
	private void cargarRemisiones() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("tipo_hc", "");
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);

		tabboxContendor.abrirPaginaTabDemanda(false,
				IRutas_historia.PAGINA_REMISIONES, "REMISIONES", parametros);
	}

	/**
	 * Para odontologia
	 *
	 */
	public void cargarNota_odontologia() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("tipo", INotas.NOTAS_ODONTOLOGIA);

		tabboxContendor.abrirPaginaTabDemanda(false,
				"/pages/nota_aclaratoria.zul", "NOTAS ODONTOLOGIA", parametros);
	}

	public void cargarEvolucionOdonotologia(Odontologia odontologia,
			OpcionesFormulario opcion_formulario, boolean primera_vez) {
		/* verificamos si existe una historia odonotologica */
		if (odontologia != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("odontologiaTemp", odontologia);
			parametros.put("admision", admision_seleccionada);
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("primera_vez", primera_vez);
			parametros.put("opcion_formulario", opcion_formulario);
			tabboxContendor.abrirPaginaTabDemanda(false,
					"/pages/evolucion_odontologia.zul",
					"EVOLUCION ODONTOLOGIA", parametros);
		}
	}

	private void cargarEvoluciones() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("tipo_hc", tbxVia_ingreso.getValue() + "-"
				+ tbxTipo_urgencia.getValue());
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);
		parametros.put("ocultar_prestador", "ocultar_prestador");

		tabboxContendor.abrirPaginaTabDemanda(false,
				IRutas_historia.PAGINA_EVOLUCIONES, "EVOLUCIONES CLINICAS",
				parametros);
	}

	/**
	 * fin cargar pestañas odontologia
	 */
	private void agregarTabPagina(String label, String pagina, Admision admision) {
		String via_ingreso = tbxVia_ingreso.getValue();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision);
		parametros.put(IVias_ingreso.VIA_INGRESO, via_ingreso);
		parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
				Opciones_via_ingreso.MOSTRAR);
		tabboxContendor.abrirPaginaTab(true, pagina, label, parametros);
	}

	private void agregarTabPaginaMapeado(String label, String pagina,
			Map<String, Object> parametros, Admision admision) {
		String via_ingreso = tbxVia_ingreso.getValue();
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision);
		parametros.put(IVias_ingreso.VIA_INGRESO, via_ingreso);
		parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
				Opciones_via_ingreso.MOSTRAR);
		tabboxContendor.abrirPaginaTab(true, pagina, label, parametros);
	}

	private void cargarHistorialHistorias() throws Exception {
		if (admision_seleccionada != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());

			Hisc_historial hisc_historial = getServiceLocator()
					.getHisc_historialService().consultar_historial(parametros);

			Integer total_consulta_externa = hisc_historial
					.getTotal_consulta_externa();

			aConsulta_externa.setLabel("Consulta Externa ("
					+ total_consulta_externa + ")");

			if (total_consulta_externa != 0) {
				borrarEventosAnteriores(aConsulta_externa, Events.ON_CLICK);
				aConsulta_externa.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_CONSULTA_EXTERNA
												+ "(C)",
										IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_3
												+ "?", admision_seleccionada);
							}

						});
			}

			// Agregado por Ferney
			if (empresa.getUtiliza_info_sio()) {
				int total_historias_sios = hisc_historialSiosService
						.consultar_historial(parametros);
				aSios.setLabel("Historias verSIÓN anterior ("
						+ total_historias_sios + ")");
				if (total_historias_sios > 0) {
					borrarEventosAnteriores(aSios, Events.ON_CLICK);
					aSios.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {

								@Override
								public void onEvent(Event event)
										throws Exception {
									agregarTabPagina(IRutas_historia.LABEL_SIOS
											+ "", IRutas_historia.PAGINA_SIOS
											+ "?", admision_seleccionada);
								}

							});
				}
			}

			// Agregado Jhonny gomez
			Integer total_aiepi = 0;
			Integer total_aiepi_mn_2_meses = hisc_historial
					.getTotal_aiepi_menor_2_meses();
			Integer total_aiepi_mn_2_meses_5_anos = hisc_historial
					.getTotal_aiepi_menor_2_meses_5_anos();

			aAiepiMenor2mA.setLabel("AIEPI Menor 2 Meses ("
					+ total_aiepi_mn_2_meses + ")");
			aAiepiMenor2m5aA.setLabel("AIEPI Menor 2 Meses a 5 años ("
					+ total_aiepi_mn_2_meses_5_anos + ")");

			if (total_aiepi_mn_2_meses != 0) {
				borrarEventosAnteriores(aAiepiMenor2mA, Events.ON_CLICK);
				aAiepiMenor2mA.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_AIEPI_MENOR_2_MESES
												+ "(C)",
										IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES
												+ "?", admision_seleccionada);
								popupAiepi.close();
							}
						});
			}
			total_aiepi += total_aiepi_mn_2_meses;

			if (total_aiepi_mn_2_meses_5_anos != 0) {
				borrarEventosAnteriores(aAiepiMenor2m5aA, Events.ON_CLICK);
				aAiepiMenor2m5aA.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_AIEPI_MENOR_2_MESES_5_ANOS
												+ "(C)",
										IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS
												+ "?", admision_seleccionada);
								popupAiepi.close();
							}
						});
			}
			total_aiepi += total_aiepi_mn_2_meses_5_anos;

			aAiepi.setLabel("AIEPI (" + total_aiepi + ")");

			// Agregado Jose Ojeda
			Integer total_salud_mental = 0;

			Integer total_psicologia = hisc_historial.getTotal_psicologia();

			aPsicologia.setLabel("psicología (" + total_psicologia + ")");

			if (total_psicologia != 0) {
				borrarEventosAnteriores(aPsicologia, Events.ON_CLICK);
				aPsicologia.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_PSICOLOGIA
												+ "(C)",
										IRutas_historia.PAGINA_PSICOLOGIA + "?",
										admision_seleccionada);
								popupSaludMental.close();
							}
						});
			}

			total_salud_mental = total_salud_mental + total_psicologia;

			Integer total_psiquiatria = hisc_historial.getTotal_psiquiatria();

			aPsiquiatria.setLabel("Psiquiatría (" + total_psiquiatria + ")");

			if (total_psiquiatria != 0) {
				borrarEventosAnteriores(aPsiquiatria, Events.ON_CLICK);
				aPsiquiatria.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_PSIQUIATRIA
												+ "(C)",
										IRutas_historia.PAGINA_PSIQUIATRIA
												+ "?", admision_seleccionada);
								popupSaludMental.close();
							}

						});
			}

			total_salud_mental = total_salud_mental + total_psiquiatria;

			Integer total_visita = hisc_historial
					.getTotal_visita_domiciliaria();

			aVisita_domiciliaria.setLabel("Visita Domiciliaria ("
					+ total_visita + ")");

			if (total_visita != 0) {
				borrarEventosAnteriores(aVisita_domiciliaria, Events.ON_CLICK);
				aVisita_domiciliaria.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_VISITA_DOMICILIARIA
												+ "(C)",
										IRutas_historia.PAGINA_VISITA_DOMICILIARIA
												+ "?", admision_seleccionada);
								popupSaludMental.close();
							}

						});
			}

			total_salud_mental = total_salud_mental + total_visita;

			aSalud_mental.setLabel("Salud Mental (" + total_salud_mental + ")");

			// ------
			Integer total_pyp = 0;

			Integer total_adulto_mayor = hisc_historial.getTotal_adulto_mayor();

			aPYP_adulto_mayor.setLabel("Adulto Mayor (" + total_adulto_mayor
					+ ")");
			if (total_adulto_mayor != 0) {
				borrarEventosAnteriores(aPYP_adulto_mayor, Events.ON_CLICK);
				aPYP_adulto_mayor.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_ADULTO_MAYOR
												+ "(C)",
										IRutas_historia.PAGINA_ADULTO_MAYOR
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_adulto_mayor;

			Integer total_alteracion_embarazo = hisc_historial
					.getTotal_alteracion_embarazo();

			aPYP_alteracion_embarazo.setLabel("Alteracion Embarazo ("
					+ total_alteracion_embarazo + ")");
			if (total_alteracion_embarazo != 0) {
				borrarEventosAnteriores(aPYP_alteracion_embarazo,
						Events.ON_CLICK);
				aPYP_alteracion_embarazo.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_ALTERACION_EMBARAZO
												+ "(C)",
										IRutas_historia.PAGINA_ALTERACION_EMBARAZO
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_alteracion_embarazo;

			Integer total_alteracion_joven = hisc_historial
					.getTotal_alteracion_joven();

			aPYP_alteracion_joven.setLabel("Alteracion Joven ("
					+ total_alteracion_joven + ")");
			if (total_alteracion_joven != 0) {
				borrarEventosAnteriores(aPYP_alteracion_joven, Events.ON_CLICK);
				aPYP_alteracion_joven.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_ALTERACION_JOVEN
												+ "(C)",
										IRutas_historia.PAGINA_ALTERACION_JOVEN
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_alteracion_joven;

			Integer total_hipertenso_diabetico = hisc_historial
					.getTotal_hipertenso_diabetico();

			aPYP_hipertenso_diabetico.setLabel("Hipertenso-Diabético ("
					+ total_hipertenso_diabetico + ")");
			if (total_hipertenso_diabetico != 0) {
				borrarEventosAnteriores(aPYP_hipertenso_diabetico,
						Events.ON_CLICK);
				aPYP_hipertenso_diabetico.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_HIPERTENSO_DIABETICO
												+ "(C)",
										IRutas_historia.PAGINA_HIPETENSO_DIABETICO
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_hipertenso_diabetico;

			Integer total_planificacion_familiar = hisc_historial
					.getTotal_planificacion_familiar();

			aPYP_planificacion_familiar.setLabel("Planificacion Familiar ("
					+ total_planificacion_familiar + ")");
			if (total_planificacion_familiar != 0) {
				borrarEventosAnteriores(aPYP_planificacion_familiar,
						Events.ON_CLICK);
				aPYP_planificacion_familiar.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_PLANIFICACION_FAMILIAR
												+ "(C)",
										IRutas_historia.PAGINA_PLANIFICACION_FAMILIAR
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_planificacion_familiar;
			//
			// // Agregado Manuel Aguilar
			// Integer total_agudeza_visual = hisc_historial
			// .getTotal_agudeza_visual();
			// aPYP_agudezVisual.setLabel("Agudeza visual ("
			// + total_agudeza_visual + ")");
			//
			// if (total_agudeza_visual != 0) {
			// borrarEventosAnteriores(aPYP_agudezVisual, Events.ON_CLICK);
			// aPYP_agudezVisual.addEventListener(Events.ON_CLICK,
			// new EventListener<Event>() {
			// @Override
			// public void onEvent(Event event) throws Exception {
			// agregarTabPagina(IRutas_historia.LABEL_AGUDEZA_VISUAL + "(C)",
			// IRutas_historia.PAGINA_AGUDEZA_VISUAL + "?",
			// admision_seleccionada);
			// popupPYP.close();
			// }
			// });
			// }
			//
			// total_pyp = total_pyp + total_agudeza_visual;

			// ------- Nuevo
			Integer total_menor_2mes = hisc_historial.getTotal_menor_2mes();

			aPYP_menor_2mes.setLabel("Menor de 2 meses (" + total_menor_2mes
					+ ")");
			if (total_menor_2mes != 0) {
				borrarEventosAnteriores(aPYP_menor_2mes, Events.ON_CLICK);
				aPYP_menor_2mes.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_MENOR_2_MESES
												+ "(C)",
										IRutas_historia.PAGINA_MENOR_2_MESES
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_menor_2mes;

			Integer total_menor_2mes_2anos = hisc_historial
					.getTotal_menor_2mes_2anos();

			aPYP_menor_2mes_2anos.setLabel("Menor de 2 meses a 2 años ("
					+ total_menor_2mes_2anos + ")");
			if (total_menor_2mes_2anos != 0) {
				borrarEventosAnteriores(aPYP_menor_2mes_2anos, Events.ON_CLICK);
				aPYP_menor_2mes_2anos.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_MENOR_2_MESES_2_ANIOS
												+ "(C)",
										IRutas_historia.PAGINA_MENOR_2_MESES_2_ANIOS
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_menor_2mes_2anos;

			Integer total_menor_2anos_5anos = hisc_historial
					.getTotal_menor_2anos_5anos();

			aPYP_menor_2anos_5anos.setLabel("Menor de 2 años a 5 años ("
					+ total_menor_2anos_5anos + ")");
			if (total_menor_2anos_5anos != 0) {
				borrarEventosAnteriores(aPYP_menor_2anos_5anos, Events.ON_CLICK);
				aPYP_menor_2anos_5anos.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_MENOR_2_ANIOS_5_ANIOS
												+ "(C)",
										IRutas_historia.PAGINA_MENOR_2_ANIOS_5_ANIOS
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_menor_2anos_5anos;

			Integer total_menor_5anos_10anos = hisc_historial
					.getTotal_menor_5anos_10anos();

			aPYP_menor_5anos_10anos.setLabel("Menor de 5 años a 10 años ("
					+ total_menor_5anos_10anos + ")");
			if (total_menor_5anos_10anos != 0) {
				borrarEventosAnteriores(aPYP_menor_5anos_10anos,
						Events.ON_CLICK);
				aPYP_menor_5anos_10anos.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_MENOR_5_ANIOS_10_ANIOS
												+ "(C)",
										IRutas_historia.PAGINA_MENOR_5_ANIOS_10_ANIOS
												+ "?", admision_seleccionada);
								popupPYP.close();
							}

						});
			}

			total_pyp = total_pyp + total_menor_5anos_10anos;

			aPyp.setLabel("PyP (" + total_pyp + ")");

			Integer total_odontologia = 0;

			Integer total_odontologia1ra = hisc_historial
					.getTotal_odontologia1ra();

			aOdontologia_1ra.setLabel("HC. Primera Vez ("
					+ total_odontologia1ra + ")");

			if (total_odontologia1ra != 0) {
				borrarEventosAnteriores(aOdontologia_1ra, Events.ON_CLICK);
				aOdontologia_1ra.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								agregarTabPagina(
										IRutas_historia.LABEL_ODONTOLOGIA2
												+ "(C)",
										IRutas_historia.PAGINA_ODONTOLOGIA2
												+ "?", admision_seleccionada);
								popupOdontologia.close();
							}

						});
			}

			total_odontologia = total_odontologia + total_odontologia1ra;

			Integer total_odontologia_evolucion = hisc_historial
					.getTotal_odontologia_evolucion();

			aOdontologia_evolucion.setLabel("HC. de Evolucion ("
					+ total_odontologia_evolucion + ")");

			if (total_odontologia_evolucion != 0) {
				borrarEventosAnteriores(aOdontologia_evolucion, Events.ON_CLICK);
				aOdontologia_evolucion.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								Map<String, Object> parametros = new HashMap<String, Object>();
								parametros.put("nro_ingreso",
										admision_seleccionada.getNro_ingreso());
								parametros.put("primera_vez",
										admision_seleccionada.getPrimera_vez()
												.equals("S"));
								parametros.put("opcion_formulario",
										OpcionesFormulario.MOSTRAR);
								parametros.put("admision",
										admision_seleccionada);
								agregarTabPaginaMapeado(
										IRutas_historia.LABEL_EVOLUCION_ODONTOLOGICA
												+ "(C)",
										IRutas_historia.PAGINA_EVOLUCION_ODONTOLOGICA
												+ "?", parametros,
										admision_seleccionada);
								popupOdontologia.close();
							}

						});
			}

			total_odontologia = total_odontologia + total_odontologia_evolucion;

			// Integer total_odontologia_rchigiene = hisc_historial
			// .getTotal_odontologia_rchigiene();
			//
			// aOdontologia_rcho.setLabel("Registros Clinico de Higiene Oral ("
			// + total_odontologia_rchigiene + ")");
			//
			// if (total_odontologia_rchigiene != 0) {
			// borrarEventosAnteriores(aOdontologia_rcho, Events.ON_CLICK);
			// aOdontologia_rcho.addEventListener(Events.ON_CLICK,
			// new EventListener<Event>() {
			//
			// @Override
			// public void onEvent(Event event) throws Exception {
			// agregarTabPagina(
			// IRutas_historia.LABEL_RCHO_ODONTOLOGIA
			// + "(C)",
			// IRutas_historia.PAGINA_RCHO_ODONTOLOGIA
			// + "?", admision_seleccionada);
			// popupOdontologia.close();
			// }
			//
			// });
			// }
			//
			// total_odontologia = total_odontologia +
			// total_odontologia_rchigiene;
			aOdontologia.setLabel("Odontología (" + total_odontologia + ")");

			// imagen diagnostica
			Map<String, Object> total_imagenes_diagnosticasMap = new HashMap<String, Object>();
			total_imagenes_diagnosticasMap
					.put("codigo_empresa", codigo_empresa);
			total_imagenes_diagnosticasMap.put("codigo_sucursal",
					codigo_sucursal);
			total_imagenes_diagnosticasMap.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());

			// Verificamos que tengan resultados fuera del rango de lo normal
			boolean mostrar_alerta = ServiciosDisponiblesUtils
					.validarResultadosFueraRango(admision_seleccionada);
			borrarEventosAnteriores(aImagenesDiagnosticas, Events.ON_CLICK);
			aImagenesDiagnosticas.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							agregarTabPagina(
									IRutas_historia.LABEL_IMAGENIOLOGIA_RESULTADOS
											+ "(C)",
									IRutas_historia.PAGINA_IMAGENIOLOGIA_RESULTADOS
											+ "?", admision_seleccionada);
						}

					});

			if (mostrar_alerta) {
				Notificaciones
						.mostrarNotificacionAlerta(
								"Advertencia",
								"Este paciente tiene resultados de laboratorios que estan fuera del rango normal o positivos",
								10000);
				// Clients.showNotification(, aImagenesDiagnosticas, true);
				Clients.showNotification("Resultados", "warning",
						aImagenesDiagnosticas, "end_center", 5000);
			}

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(admision_seleccionada
					.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision_seleccionada
					.getCodigo_sucursal());
			paciente.setNro_identificacion(admision_seleccionada
					.getNro_identificacion());
			paciente = getServiceLocator().getServicio(PacienteService.class)
					.consultar(paciente);
			borrarEventosAnteriores(aPYP_actividades_pendientes,
					Events.ON_CLICK);
			if (paciente != null) {
				final List<Plantilla_pyp> plantilla_pyps = ManejadorPoblacion
						.getActividadesPendientesPorRealizar(paciente);

				// log.info("Actividades por realizar: " +
				// plantilla_pyps.size());
				if (!plantilla_pyps.isEmpty()) {
					Notificaciones
							.mostrarNotificacionAlerta(
									"Advertencia",
									"El paciente tiene pendiente unas actividades por realizar en PyP.",
									4000);
					aPYP_actividades_pendientes
							.setLabel("Actividades pendientes ("
									+ plantilla_pyps.size() + ")");
					aPYP_actividades_pendientes.addEventListener(
							Events.ON_CLICK, new EventListener<Event>() {
								@Override
								public void onEvent(Event event)
										throws Exception {
									Map<String, Object> map = new HashMap<String, Object>();
									map.put(ActividadesProgramasAction.ACTIVIDADES,
											plantilla_pyps);
									agregarTabPaginaMapeado(
											IRutas_historia.LABEL_ACTIVIDADES_PYP,
											IRutas_historia.PAGINA_ACTIVIDADES_PYP
													+ "?", map,
											admision_seleccionada);
								}

							});
				} else {
					aPYP_actividades_pendientes
							.setLabel("Actividades pendientes");
				}
				// aPYP_actividades_pendientes
			} else {
				aPYP_actividades_pendientes.setLabel("Actividades pendientes");
			}

			/* vacunacion */
			Map<String, Object> total_vacunasMap = new HashMap<String, Object>();
			total_vacunasMap.put("codigo_empresa", codigo_empresa);
			total_vacunasMap.put("codigo_sucursal", codigo_sucursal);
			total_vacunasMap.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			Integer totalvacunacion = getServiceLocator().getServicio(
					Vacunas_pacientesService.class)
					.consultarTotalVacunasPaciente(total_vacunasMap);
			Integer total_pendientes = ManejadorPoblacion
					.getVacunasPendientesNoRealizadas(paciente,
							getServiceLocator()).size();
			int total = (total_pendientes != null ? total_pendientes : 0);
			aVacunacionPai.setLabel("Vacunacion ("
					+ (totalvacunacion != null ? totalvacunacion : 0) + ")");
			if (total > 0) {
				Notificaciones.mostrarNotificacionAlerta("Advertencia",
						"Este paciente tiene vacunas pendientes por colocar",
						IConstantes.TIEMPO_NOTIFICACIONES_MEDIO);
			}
			aVacunacionPai.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							agregarTabPagina(IRutas_historia.LABEL_VACUNACION,
									IRutas_historia.PAGINA_VACUNACION + "?",
									admision_seleccionada);
						}

					});

			/* Alertas epidemiologia */
			consultarAlerta();

		} else {
			throw new Exception("No hay una admision seleccionada");
		}
	}

	public void consultarAlerta() {
		Map<String, Object> diagnosticos_cie = new HashMap<String, Object>();
		diagnosticos_cie.put("codigo_empresa", codigo_empresa);
		diagnosticos_cie.put("codigo_sucursal", codigo_sucursal);
		diagnosticos_cie.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());

		diagnosticos_cie.put("limite_paginado", "limit 25 offset 0");

		// log.info("parameters>>>>" + diagnosticos_cie);
		List<Map<String, Object>> lista_diagnosticos = generalExtraService
				.listar(Cie.class, "listar_codigo_ficha", diagnosticos_cie);

		String enfermedad = "";

		for (int j = 0; j < lista_diagnosticos.size(); j++) {

			Map<String, Object> mapCie = (Map<String, Object>) lista_diagnosticos
					.get(j);

			enfermedad += (String) mapCie.get("titulo") + "\n";

		}

		if (!enfermedad.equals("")) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"Este paciente le han diagnosticado:" + enfermedad + ".",
					IConstantes.TIEMPO_NOTIFICACIONES_LARGO);
		}

	}

	public void abrirMenuPYP() {
		popupPYP.open(gbxHistorias_clinicas, "overlap");
	}

	public void abrirMenuOdontologia() {
		popupOdontologia.open(gbxHistorias_clinicas, "overlap");
	}

	public void abrirMenuSaludMental() {
		popupSaludMental.open(gbxHistorias_clinicas, "overlap");
	}

	public void abrirMenuAiepi() {
		popupAiepi.open(gbxHistorias_clinicas, "overlap");
	}

	private void borrarEventosAnteriores(Component component, String evtnm) {
		Iterator<EventListener<? extends Event>> iterator = component
				.getEventListeners(evtnm).iterator();
		while (iterator.hasNext()) {
			component.removeEventListener(evtnm, iterator.next());
		}
	}

	/**
	 * Este metodo me devuelve el mapa que contiene todos los componetes, que
	 * estan caragados en la vista Ejemplo: Autorizaciones, Remisiones, etc
	 *
	 * @author Luis Miguel
	 *
	 */
	public Map<String, Component> getMapa_componentes() {
		return tabboxContendor.getMapa_componentes();
	}

	public void actualizarAdmisionSession() {
		if (admision_seleccionada != null) {
			HttpServletRequest request = (HttpServletRequest) Executions
					.getCurrent().getNativeRequest();
			HttpSession session = request.getSession();
			session.setAttribute(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			session.setAttribute(IVias_ingreso.SERVICE_LOCATOR,
					getServiceLocator());
		}
	}

	public void eliminarAdmisionSession() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(IVias_ingreso.ADMISION_PACIENTE);
		session.removeAttribute(IVias_ingreso.SERVICE_LOCATOR);

	}

}
