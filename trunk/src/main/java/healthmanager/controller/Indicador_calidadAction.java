/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Urgencias;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Hisc_urgenciaService;

import com.framework.util.Util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.ContenedorPaginasMacro;
import com.framework.macros.impl.ModuloConsultaIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

/**
 * @author Manuel Aguilar
 * 
 */
public class Indicador_calidadAction extends ZKWindow implements
		ModuloConsultaIMG {
	private static final long serialVersionUID = -9145887024839938515L;

	// private Historia_clinicaService historia_clinicaService;

	@View
	private Timer timerConsultar;

	@View
	private Auxheader auxheaderAsistencial;
	@View
	private Listheader listheaderInfo;
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
	private ContenedorPaginasMacro tabboxContendor;

	@View
	private Groupbox gbxHistorias_clinicas;
	// @View
	// private Popup agudezaVisual;
	IRoles iroles;
	@View
	private A aVacunacionPai;

	private Admision admision_seleccionada;

	@Override
	public void init() throws Exception {
		timerConsultar.setDelay(480000);
		dtxFecha.setValue(new Date());
		if (tbxVia_ingreso.getValue().equals(IVias_ingreso.URGENCIA)) {
			auxheaderAsistencial.setColspan(10);
		}

		if (tbxVia_ingreso.getValue().equals(IVias_ingreso.URGENCIA)
				|| tbxVia_ingreso.getValue().equals(
						IVias_ingreso.FORMULARIO_TRIAGE)
				|| tbxVia_ingreso.getValue().equals(
						IVias_ingreso.HOSPITALIZACIONES)) {
			if (tbxVia_ingreso.getValue().equals(IVias_ingreso.URGENCIA)
					|| tbxVia_ingreso.getValue().equals(
							IVias_ingreso.FORMULARIO_TRIAGE)) {
				listheaderInfo.setVisible(true);
			}
			timerConsultar.setDelay(15000);
			timerConsultar.start();
		}

	}
	
	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		tbxVia_ingreso.setValue((String) request.getParameter(IVias_ingreso.VIA_INGRESO));
	}

	public void accionForm(String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			groupboxConsultar.setVisible(true);
			borderlayoutEditar.setVisible(false);
		} else {
			groupboxConsultar.setVisible(false);
			borderlayoutEditar.setVisible(true);
		}
	}

	public void buscarDatos() throws Exception {
		listboxAdmisiones.getItems().clear();
		divPopups_traige.getChildren().clear();
		try {

			String atendida = (String) lbxAtendidas.getSelectedItem()
					.getValue();
			// log.info("atendido>>>>>>>>>>>>>>>>>>>>" + atendida);
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

			if (!((String) lbxEstado_admision.getSelectedItem().getValue())
					.isEmpty())
				parameters.put("estado", (String) lbxEstado_admision
						.getSelectedItem().getValue());
			parameters.put("fecha", new SimpleDateFormat("yyyy-MM-dd")
					.format(dtxFecha.getValue()));
			boolean atendida_boolean = (atendida.equals("1") ? true : false);
			if (tbxVia_ingreso.getValue().equals(
					IVias_ingreso.FORMULARIO_TRIAGE)) {
				parameters.put("via_ingreso", IVias_ingreso.URGENCIA);
				parameters.put("aplica_triage", true);
				if (!atendida.equals("0")) {
					if (atendida_boolean) {
						parameters.put("realizo_triage", true);
					} else {
						parameters.put("realizo_triage", false);
					}
				}

			} else if (tbxVia_ingreso.getValue().equals(IVias_ingreso.URGENCIA)) {
				parameters.put("via_ingreso", IVias_ingreso.URGENCIA);
				if (!atendida.equals("0"))
					parameters.put("atendida", atendida_boolean);
				parameters.put("filtro_urgencias", "filtro_urgencias");
			} else {
				parameters.put("via_ingreso", tbxVia_ingreso.getValue());
				if (!atendida.equals("0")) {
					// parameters.put("atendida", atendida_boolean);
				}

			}

			parameters.put("paramTodo", "");
			parameters.put("value", "%"
					+ tbxValueAdmision.getValue().toUpperCase().trim() + "%");

			parameters.put("codigo_centro",
					centro_atencion_session.getCodigo_centro());

			// parametro para filtrar los pacientes por el medico que esta en la
			// sesion

			if (!rol_usuario.equals(IRoles.ADMINISTRADOR)) {
				parameters
						.put("codigo_medico_mod",
								rol_usuario
										.equals(IRoles.AUXILIAR_LABORATORIOS) ? IConstantes.CODIGO_MEDICO_DEFECTO
										: usuarios.getCodigo());
			}

			parameters
					.put("order",
							"fecha_ingreso asc,creacion_date,apellido1,apellido2,nombre1,nombre2");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Admision> listado = getServiceLocator().getAdmisionService()
					.listarResultados(parameters);

			// log.info("listado de admisiones ===> " + listado.size());

			for (Admision admision : listado) {
				listboxAdmisiones.appendChild(crearFila(admision));
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// manuel
	private Listcell crearCeldaEstadoUrgencia(Admision admision,
			Listitem listitem) {
		Toolbarbutton toolbarbutonEstado = new Toolbarbutton("", "");
		Listcell liscell = new Listcell();

		if (!admision.getAtendida() && !admision.getAtendiendo()) {
			liscell.appendChild(toolbarbutonEstado);
			liscell.setStyle("text-align:center;background-color:yellow");
			toolbarbutonEstado.setImage("/images/espera.png");
			toolbarbutonEstado.setLabel("Espera...");
			toolbarbutonEstado.setStyle("color:black;font-weight:bold");
			listitem.setDisabled(false);
			admision.setFecha_apertura(null);
			actualizarAdmisionSession();
		}

		if (!admision.getAtendida() && admision.getAtendiendo()) {
			liscell.appendChild(toolbarbutonEstado);
			liscell.setStyle("text-align:center;background-color:red");
			toolbarbutonEstado.setImage("/images/process.png");
			toolbarbutonEstado.setLabel("Proceso");
			toolbarbutonEstado.setStyle("color:black;font-weight:bold");
			listitem.setDisabled(true);

		}

		if (admision.getAtendida()) {
			liscell.appendChild(toolbarbutonEstado);
			liscell.setStyle("text-align:center;background-color:green");
			toolbarbutonEstado.setImage("/images/atendido.png");
			toolbarbutonEstado.setLabel("Atendido");
			toolbarbutonEstado.setStyle("color:white;font-weight:bold");
			listitem.setDisabled(false);
			toolbarbutonEstado.setTooltip("");
		}
		return liscell;

	}

	private Listcell crearCeldaEstadoTriage(Admision admision, Listitem listitem) {
		His_triage his_triage = null;
		Toolbarbutton toolbarbutonEstado = new Toolbarbutton("", "");
		Listcell liscell = new Listcell();

		his_triage = new His_triage();
		his_triage.setCodigo_empresa(codigo_empresa);
		his_triage.setCodigo_sucursal(codigo_sucursal);
		his_triage.setIdentificacion(admision.getNro_identificacion());
		his_triage.setNro_ingreso(admision.getNro_ingreso());

		his_triage = getServiceLocator().getHis_triageService().consultar(
				his_triage);

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

		if (his_triage != null) {
			liscell.appendChild(toolbarbutonEstado);
			liscell.setStyle("text-align:center;background-color:green");
			toolbarbutonEstado.setImage("/images/atendido.png");
			toolbarbutonEstado.setLabel("Atendido");
			toolbarbutonEstado.setStyle("color:white;font-weight:bold");
			listitem.setDisabled(false);
			toolbarbutonEstado.setTooltip("");
		}

		return liscell;
	}

	public static long restarFecha(Date fechaIni, Date fechaFin) {
		long diferencia, fin, inicio;
		fin = fechaFin.getTime();
		inicio = fechaIni.getTime();
		diferencia = (fin - inicio);

		return diferencia;
	}

	public static long restarFechaTemp(Date fechaIni) {
		Date factual = new Date();
		long diferencia, fin, inicio;
		fin = factual.getTime();
		inicio = fechaIni.getTime();
		diferencia = (fin - inicio);

		return diferencia;
	}

	public Listitem crearFila(final Admision admision) {
		long resultado;
		His_triage his_triage = null;
		Urgencias urgencia = null;
		final String via_ingreso = tbxVia_ingreso.getValue();
		if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
			urgencia = new Urgencias();
			urgencia.setCodigo_empresa(admision.getCodigo_empresa());
			urgencia.setCodigo_sucursal(admision.getCodigo_sucursal());
			urgencia.setNro_identificacion(admision.getNro_identificacion());
			urgencia.setNro_ingreso(admision.getNro_ingreso());

			urgencia = getServiceLocator().getUrgenciasService().consultar(
					urgencia);
		}
		if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
			his_triage = new His_triage();
			his_triage.setCodigo_empresa(codigo_empresa);
			his_triage.setCodigo_sucursal(codigo_sucursal);
			his_triage.setIdentificacion(admision.getNro_identificacion());
			his_triage.setNro_ingreso(admision.getNro_ingreso());

			his_triage = getServiceLocator().getHis_triageService().consultar(
					his_triage);

		}

		String ffh = "";
		String ffm = "";
		String ffs = "";
		if (his_triage != null) {
			if (admision.getFecha_apertura() != null) {
				resultado = restarFecha(his_triage.getUltimo_update(),
						admision.getFecha_apertura());
			} else {
				resultado = restarFechaTemp(his_triage.getFecha_inicial());
			}
			long hora = (resultado / 3600000);
			long minutos = ((resultado % 3600000) / 60000);
			long segundos = (((resultado % 3600000) % 60000) / 1000);
			ffh = String.valueOf(hora);
			ffm = String.valueOf(minutos);
			ffs = String.valueOf(segundos);
		}

		Paciente paciente = admision.getPaciente();
		Prestadores p = admision.getPrestadores();

		String prest = "";
		if (p != null) {
			prest = p.getNombres() + " " + p.getApellidos();
		}

		String apellidos = (paciente != null ? paciente.getApellido1() + " "
				+ paciente.getApellido2() : "");
		String nombres = (paciente != null ? paciente.getNombre1() + " "
				+ paciente.getNombre2() : "");

		final Listitem listitem = new Listitem();

		listitem.setStyle("cursor:pointer");
		listitem.setValue(admision);
		listitem.appendChild(new Listcell());

		if (via_ingreso.equals(IVias_ingreso.FORMULARIO_TRIAGE)
				&& !admision.getAtendida()) {
			listitem.appendChild(crearCeldaEstadoTriage(admision, listitem));
		} else if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
			listitem.appendChild(crearCeldaEstadoUrgencia(admision, listitem));
		} else {
			listitem.appendChild(new Listcell());
		}

		listitem.appendChild(Utilidades.obtenerListcell(apellidos + " "
				+ nombres, Label.class, true, true));
		listitem.appendChild(new Listcell(prest));
		listitem.appendChild(his_triage != null ? Utilidades.obtenerListcell(
				new SimpleDateFormat("yyyy-MM-dd HH:mm").format(his_triage
						.getUltimo_update()), Label.class, true, true)
				: new Listcell(" "));

		listitem.appendChild(admision.getFecha_apertura() != null ? Utilidades
				.obtenerListcell(new SimpleDateFormat("yyyy-MM-dd HH:mm")
						.format(admision.getFecha_apertura()), Label.class,
						true, true) : new Listcell(" "));

		/*
		 * listitem.appendChild(Utilidades.obtenerListcell(new SimpleDateFormat(
		 * "yyyy-MM-dd HH:mm").format(admision.getFecha_atencion()),
		 * Label.class, true, true));
		 */

		listitem.appendChild(Utilidades.obtenerListcell(ffh + "H" + " y " + ffm
				+ "m " + ffs + "s", Label.class, true, true));

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

		listitem.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

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
							His_triage his_triage = new His_triage();
							his_triage.setCodigo_empresa(codigo_empresa);
							his_triage.setCodigo_sucursal(codigo_sucursal);
							his_triage.setIdentificacion(admision
									.getNro_identificacion());
							his_triage.setNro_ingreso(admision.getNro_ingreso());

							his_triage = getServiceLocator()
									.getHis_triageService().consultar(
											his_triage);

							if (!admision.getAtendida()) {
								Timestamp f = new Timestamp(new Date()
										.getTime());
								admision.setFecha_apertura(f);
								// log.info("fecha de apertura>>>>>>>>>>>>>>" +
								// f);
							}
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
									window.setPage(Indicador_calidadAction.this
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

						Map<String, Object> map = new HashMap<String, Object>();
						map.put("accion", "registrar");

						onSeleccionarAdmision(listitem);
					} else {
						onSeleccionarAdmision(listitem);
					}
				}
			}
		});

		return listitem;
	}

	public void onSeleccionarAdmision(Listitem listitem) throws Exception {
		try {

			Admision admision = (Admision) listitem.getValue();
			admision = admisionService.consultar(admision);
			// log.info("atendiendo>>>>>" + true);
			admision.setAtendiendo(true);
			if (!admision.getAtendida()) {
				Timestamp f = new Timestamp(new Date().getTime());
				admision.setFecha_apertura(f);
				// log.info("fecha de apertura>>>>>>>>>>>>>>" + f);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("admision", admision);
			// map.put("parametros_empresa", parametros_empresa);
			admisionService.actualizar(map);
			admision_seleccionada = admision;
			accionForm("registrar");
			// cargarHistorialHistorias();
			borderlayoutEditar.invalidate();
			actualizarAdmisionSession();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	/**
	 * Este metodo me devuelve el mapa que contiene todos los componetes, que
	 * estan caragados en la vista Ejemplo: Autorizaciones, Remisiones, etc
	 * 
	 * @author Luis Miguel
	 * */
	public Map<String, Component> getMapa_componentes() {
		return tabboxContendor.getMapa_componentes();
	}

	public void actualizarAdmisionSession() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		if (admision_seleccionada != null) {
			request = (HttpServletRequest) Executions.getCurrent()
					.getNativeRequest();
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
		request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(IVias_ingreso.ADMISION_PACIENTE);
		session.removeAttribute(IVias_ingreso.SERVICE_LOCATOR);

	}

	// @Override
	// public void cargarRegClinicoHigiene(Odontologia odontologia,
	// boolean desde_historia, OpcionesFormulario opcion_formulario) {
	//
	//
	// }

	@Override
	public void cargarEpicrisis() {

	}

	@Override
	public void cargarEvolucionOdonotologia(Odontologia odontologia,
			OpcionesFormulario opcion_formulario,
			List<Map<String, Object>> lista_codigos_fac, boolean primera_vez) {

	}

	@Override
	public OpcionesFormulario opcion_formulario(List<String> lista_codigos_fac,
			boolean primera_vez) {

		return null;
	}

}
