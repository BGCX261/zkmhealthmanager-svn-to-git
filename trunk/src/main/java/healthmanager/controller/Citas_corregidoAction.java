package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Detalle_turno;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Pyp;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.BarrioService;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.ConsultorioService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.Detalle_turnoService;
import healthmanager.modelo.service.Detalles_horariosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pacientes_contratosService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import sios.modelo.service.Hisc_historialSiosService;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IOnRegistroEvent;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.macros.util.Parametros_busqueda;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.L2HContraintDate;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.SolicitadorActionUtil;
import com.framework.util.SolicitadorActionUtil.ISolicitadorAccion;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesSios;

public class Citas_corregidoAction extends GeneralComposer implements
		WindowRegistrosIMG {

	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private Detalle_turnoService detalle_turnoService;
	@WireVariable
	private CitasService citasService;
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private AdministradoraService administradoraService;
	@WireVariable
	private Pacientes_contratosService pacientes_contratosService;
	@WireVariable
	private ContratosService contratosService;
	@WireVariable
	private BarrioService barrioService;
	@WireVariable
	private Centro_atencionService centro_atencionService;
	@WireVariable
	private DepartamentosService departamentosService;
	@WireVariable
	private MunicipiosService municipiosService;
	@WireVariable
	private PrestadoresService prestadoresService;
	@WireVariable
	private GeneralExtraService generalExtraService;
	@WireVariable
	private Hisc_historialSiosService hisc_historialSiosService;
	@WireVariable
	private Detalles_horariosService detalles_horariosService;
	@WireVariable
	private ConsultorioService consultorioService;

	@View
	private Borderlayout groupboxEditar;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View
	private West westDatos_cita;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Toolbarbutton btnLimpiarPaciente;
	@View
	private Textbox tbxCentro_atencion;
	@View
	private Textbox tbxCodigo_centro;

	@View
	private Textbox tbxCodigoAseguradora;
	@View
	private Textbox tbxInfoAseguradora;

	@View
	private Textbox tbxDireccion;
	@View
	private Doublebox dbxTel;
	@View
	private Textbox tbxEdad;
	@View
	private Textbox tbxSexo;
	@View
	private Datebox dtbxFecha_solicitada;
	@View
	private Listbox lbxMedio_solicitud;
	@View
	private Listbox lbxTipo_cita;
	@View
	private Listbox lbxVia_ingreso;

	@View
	private Textbox tbxAcompaniante;
	@View
	private Listbox lbxRelacion;
	// tbxTel_acompaniante
	@View
	private Textbox tbxTel_acompaniante;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;
	@View
	private Textbox tbxEspecialidad;

	@View
	private Datebox dtbxFecha_busqueda;

	@View
	private Datebox dtbxFecha_final2;

	@View
	private Textbox tbxOtro_acompaniante;

	@View
	private Textbox tbxApellidos_acomp;
	@View
	private Textbox tbxCedula_acomp;

	@View
	private Toolbarbutton btAtras;

	@View
	private Toolbarbutton btNew;
	@View
	private Toolbarbutton btGenerar;
	@View
	private Toolbarbutton btAbrir;

	@View
	private Row rowPrestador;
	@View
	private Row rowEspecialidad;

	@View
	private Toolbarbutton btAgregarPaciente;

	@View
	private Listbox listboxCitas;

	@View
	private Listbox listboxLaboratorios;

	@View
	private Listbox listboxEstados;

	@View
	private Popup popupEstados;
	@View
	private Label labelProgramacion_citas;

	@View
	private Checkbox chkAplica_tel;
	@View
	private Textbox tbxContrato;
	@View
	private Row rowContratos;

	private final SimpleDateFormat formatoFechaHora = new SimpleDateFormat(
			"hh:mm a", IConstantes.locale);
	private final SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"EEEEE dd MMMMM yyyy", IConstantes.locale);

	private Admision admision;

	private Prestadores prestador;

	private final String[] idsExcluyentes = new String[] { "tbxAccion", "btNew" };

	private final Map<String, Consultorio> mapa_consultorios = new HashMap<String, Consultorio>();

	private final Map<String, Prestadores> mapa_prestadores = new HashMap<String, Prestadores>();

	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizarBandbox();
		limpiarDatos();
		dtbxFecha_solicitada.setText("");
		chkAplica_tel.setChecked(true);
		cargarInformacionPaciente();
	}

	private void cargarInformacionPaciente() {
		if (admision != null) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(admision.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);
			if (paciente != null) {
				tbxNro_identificacion.seleccionarRegistro(paciente,
						paciente.getNro_identificacion(),
						paciente.getNombreCompleto());
				seleccionarPaciente(paciente);
			}
			tbxNro_identificacion.setDisabled(true);
			Utilidades.setValueFrom(lbxVia_ingreso, admision.getVia_ingreso());

			if (prestador != null) {
				tbxCodigo_prestador
						.seleccionarRegistro(
								prestador,
								prestador.getNro_identificacion(),
								prestador.getApellidos() + " "
										+ prestador.getNombres());
				tbxCodigo_prestador.setDisabled(false);
				cargarCalendarioCitas();
			}
			btAbrir.setVisible(false);
			btGenerar.setVisible(false);
			btNew.setVisible(false);
			btAtras.setVisible(true);
			btAgregarPaciente.setVisible(false);
			lbxVia_ingreso.setDisabled(true);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		admision = (Admision) map.get("admision");
		prestador = (Prestadores) map.get("prestador");
	}

	private String getViaIngresoValida() {
		String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue();
		if (via_ingreso.equals(IVias_ingreso.LABORATORIOS_PYP)) {
			via_ingreso = IVias_ingreso.LABORATORIOS;
		}
		return via_ingreso;
	}

	public void seleccionarApartarCita(Map<String, Object> mapa_evento) {
		try {
			if (!isApoyoDiagnostico()) {
				guardarCita(mapa_evento);
			} else {
				final Date fecha_seleccionada = (Date) mapa_evento
						.get("fecha_seleccionada");
				final Centro_atencion centro_atencion = centro_atencion_session;
				if (centro_atencion != null) {
					// log.info("centro_atencion_session ===> "
					// + centro_atencion_session);
					final String fecha = new SimpleDateFormat("yyyy-MM-dd")
							.format(fecha_seleccionada);

					// Tomamos las citas que estan para hoy para esa area
					// funcional
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("codigo_empresa", codigo_empresa);
					parameter.put("codigo_sucursal", codigo_sucursal);
					parameter.put("tipo_consulta", getViaIngresoValida());
					parameter.put("fecha_cita", fecha);
					// log.info("" + parameter);
					// int citas_apartadas = getServiceLocator().getServicio(
					// CitasService.class).getCitas(parameter);
					// log.info("Citas apartadas el dia de hoy: "
					// + citas_apartadas);

					// Verificamos si tiene, cuales son los topes que tiene
					parameter = new HashMap<String, Object>();
					parameter.put("codigo_empresa", codigo_empresa);
					parameter.put("codigo_sucursal", codigo_sucursal);

					// Esto es para tome el servicio como cita de laboratorio
					parameter.put("via_ingreso", getViaIngresoValida());
					parameter.put("codigo_centro_salud",
							centro_atencion.getCodigo_centro());

					// log.info("parameter busqueda ==> " + parameter);
					final List<Detalle_turno> detalle_turnos = detalle_turnoService
							.listar(parameter);
					final Listbox listbox = new Listbox();

					Listhead listhead = new Listhead();

					Listheader listheader = new Listheader();
					listheader.setAlign("center");
					listhead.appendChild(listheader);

					listheader = new Listheader("Maximo citas");
					listheader.setAlign("center");
					listheader.setWidth("100px");
					listhead.appendChild(listheader);

					listheader = new Listheader("Citas disponibles");
					listheader.setAlign("center");
					listheader.setWidth("120px");
					listhead.appendChild(listheader);

					// Adicionamos
					listbox.appendChild(listhead);
					if (!detalle_turnos.isEmpty()) {
						for (Detalle_turno detalle_turno : detalle_turnos) {
							try {

								SimpleDateFormat dateFormat = new SimpleDateFormat(
										"yyyy-MM-dd hh:mm a");
								Date dateIncial = dateFormat.parse(fecha + " "
										+ detalle_turno.getHora_inicio());
								Date dateFinal = dateFormat.parse(fecha + " "
										+ detalle_turno.getHora_fin());

								// log.info("Hora incio: " + dateIncial);
								// log.info("Hora final: " + dateFinal);
								dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");

								parameter = new HashMap<String, Object>();
								parameter.put("codigo_empresa", codigo_empresa);
								parameter.put("codigo_sucursal",
										codigo_sucursal);
								parameter.put("codigo_centro",
										centro_atencion.getCodigo_centro());

								// tipo_consultas
								String via_ingreso = lbxVia_ingreso
										.getSelectedItem().getValue();
								if (via_ingreso
										.equals(IVias_ingreso.LABORATORIOS_PYP)
										|| via_ingreso
												.equals(IVias_ingreso.LABORATORIOS)) {
									parameter
											.put("tipo_consultas",
													new String[] {
															IVias_ingreso.LABORATORIOS_PYP,
															IVias_ingreso.LABORATORIOS });
								} else {
									parameter.put("tipo_consulta", via_ingreso);
								}
								parameter
										.put("fecha_incial", Timestamp
												.valueOf(dateFormat
														.format(dateIncial)));
								parameter.put("fecha_final", Timestamp
										.valueOf(dateFormat.format(dateFinal)));
								// log.info("Mapa: " + parameter);
								int citas_apartada = citasService
										.getCitas(parameter);

								Listitem listitem = new Listitem();
								listitem.setValue(detalle_turno);

								if (citas_apartada >= detalle_turno
										.getTope_maximo_citas()) {
									listitem.setDisabled(true);
									listitem.setTooltiptext("No hay citas disponibles");
								}

								Listcell listcell = new Listcell("De "
										+ detalle_turno.getHora_inicio()
										+ " a " + detalle_turno.getHora_fin());
								listitem.appendChild(listcell);

								listcell = new Listcell(""
										+ detalle_turno.getTope_maximo_citas());
								listitem.appendChild(listcell);

								listcell = new Listcell(
										""
												+ (detalle_turno
														.getTope_maximo_citas() - citas_apartada));
								listitem.appendChild(listcell);

								listbox.appendChild(listitem);

							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

						final SolicitadorActionUtil solicitadorActionUtil = new SolicitadorActionUtil(
								getPage(), new ISolicitadorAccion() {
									// En esta parte agregamos los
									// componentes
									@Override
									public Component getCuerpoComponente() {

										Vbox vbox = new Vbox();
										vbox.appendChild(listbox);

										return vbox;
									}

									@Override
									public void cancelar(
											SolicitadorActionUtil solicitadorActionUtil) {
										// log.info("Se cancelo");
										solicitadorActionUtil.detach();
									}

									@Override
									public boolean isVisibleButton() {
										return false;
									}

									@Override
									public void aceptar(
											SolicitadorActionUtil solicitadorActionUtil) {
										// log.info("Se acepto");
									}
								});

						// Cargamos evento
						listbox.addEventListener(Events.ON_CLICK,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
										try {
											// log.info("Selecciono");
											Detalle_turno detalle_turnoSeleccionada = listbox
													.getSelectedItem()
													.getValue();

											String hora = new SimpleDateFormat(
													"hh:mm a").format(Calendar
													.getInstance().getTime());

											// log.info("Hora: " + hora);
											boolean correspondeAlRango = correspondeAlRango(
													detalle_turnoSeleccionada,
													hora);
											if (!correspondeAlRango) {
												hora = detalle_turnoSeleccionada
														.getHora_inicio();
											}

											SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
													"yyyy-MM-dd hh:mm a");
											Date fecha_seleccionada_aux = simpleDateFormat.parse(new SimpleDateFormat(
													"yyyy-MM-dd")
													.format(fecha_seleccionada)
													+ " " + hora);

											Map<String, Object> mapa_evento = new HashMap<String, Object>();

											Detalles_horarios detalles_horarios = new Detalles_horarios();
											detalles_horarios
													.setCodigo_empresa(codigo_empresa);
											detalles_horarios
													.setCodigo_sucursal(codigo_sucursal);
											detalles_horarios
													.setFecha_inicial(new Timestamp(
															fecha_seleccionada_aux
																	.getTime()));
											detalles_horarios
													.setCodigo_medico(IConstantes.CODIGO_MEDICO_DEFECTO);
											detalles_horarios
													.setConsecutivo(-1);

											mapa_evento.put(
													"detalles_horarios",
													detalles_horarios);
											mapa_evento.put("codigo_estado",
													"0");

											guardarCita(mapa_evento);
											solicitadorActionUtil.detach();
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								});

						solicitadorActionUtil
								.setTitle("CITAS DISPONIBLES PARA "
										+ lbxVia_ingreso.getSelectedItem()
												.getLabel().toUpperCase());
						solicitadorActionUtil.setWidth("450px");
						solicitadorActionUtil.setClosable(true);
						solicitadorActionUtil.doModal();
					} else {
						MensajesUtil
								.mensajeAlerta(
										"Advertencia",
										"Para realizar esta accion debe terner configurar los turnos para el centro de salud de "
												+ centro_atencion
														.getNombre_centro());
					}
				} else {
					MensajesUtil
							.mensajeAlerta("Advertencia",
									"Para realizar esta accion debe terner un centro de atencion asignado");
				}

			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	/**
	 * Este metodo me permite cerificar los turnos diasponibles
	 *
	 * @param string
	 *
	 */
	protected Detalle_turno getTurnoDisponible(
			List<Detalle_turno> detalle_turnos, String hora) {
		for (Detalle_turno detalle_turno : detalle_turnos) {
			try {
				if (correspondeAlRango(detalle_turno, hora)) {
					return detalle_turno;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private boolean correspondeAlRango(Detalle_turno detalle_turno, String hora)
			throws Exception {
		Date dateInicial = new SimpleDateFormat("hh:mm a").parse(detalle_turno
				.getHora_inicio());
		Date dateFinal = new SimpleDateFormat("hh:mm a").parse(detalle_turno
				.getHora_fin());
		Date datehora = new SimpleDateFormat("hh:mm a").parse(hora);

		// //log.info("Comparacion de fechas");
		// //log.info("Fecha inicio:" + dateInicial);
		// //log.info("Fecha final:" + dateFinal);
		// //log.info("Fecha inicio:" + datehora);
		if (datehora.compareTo(dateInicial) >= 0
				&& datehora.compareTo(dateFinal) <= 0) {
			return true;
		}
		return false;
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		// agregamos la fecha del dia siguiente
		dtbxFecha_solicitada.setValue(null);
		dtbxFecha_final2.setText("");

		seleccionarTipo_consulta();
		cargarCalendarioCitas();
		tbxCodigo_prestador.setDisabled(true);
		tbxNro_identificacion.setReadonly(false);
		listboxCitas.invalidate();
		listboxLaboratorios.invalidate();
		tbxCodigoAseguradora.removeAttribute("ADMINISTRADORA");
		tbxContrato.removeAttribute("LISTADO_CONTRATOS");
		dbxTel.setText("");
	}

	private void parametrizarBandbox() {
		parametrizarBandboxPaciente();
		parametrizarBandboxPrestador();
	}

	private void parametrizarBandboxPaciente() {
		tbxNro_identificacion.setTrabajarConParametros(true);
		tbxNro_identificacion.inicializar(tbxNomPaciente, btnLimpiarPaciente);
		tbxNro_identificacion.setMostrarVacio(true);
		tbxNro_identificacion
				.setMensajeVacio("Este paciente no se encuentra en la base de datos..!!");
		tbxNro_identificacion
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado", "limit 25 offset 0");

						return pacienteService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						// Esta validacion es para la Caja
						if (sucursal.getTipo().equals(
								IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
							if (!registro.getEstado_afiliacion().equals(
									IConstantes.ESTADO_AFILIACION_ACTIVO)) {
								String descripcion = Utilidades.getDescripcionElemento(
										registro.getEstado_afiliacion(),
										"estado_afiliacion", elementoService);
								MensajesUtil.mensajeAlerta("Advertencia",
										"Este paciente no puede ser atendido tiene estado "
												+ descripcion);
								return false;
							}
						}

						boolean brindar_servicio = true;

						if (brindar_servicio) {
							bandbox.setValue(registro.getNro_identificacion());
							textboxInformacion.setValue(registro
									.getNombreCompleto());
							tbxNomPaciente.setHflex("0");
							textboxInformacion.setWidth("210px");
							seleccionarPaciente(registro);
							ServiciosDisponiblesUtils
									.validarInformacionPaciente(registro);
							return true;
						} else {
							return false;
						}
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						FormularioUtil.limpiarComponentes(westDatos_cita,
								new String[] {});
						tbxCodigo_prestador.setDisabled(true);
						lbxVia_ingreso.setDisabled(true);
						lbxTipo_cita.setDisabled(true);
						dtbxFecha_solicitada.setText("");
						tbxNomPaciente.setWidth(null);
						tbxNomPaciente.setHflex("1");
						tbxNro_identificacion.setAttribute("centro", null);
						listboxCitas.getItems().clear();
						dbxTel.setText("");
					}
				});
		List<Parametros_busqueda> listado_parametros = new ArrayList<Parametros_busqueda>();
		listado_parametros.add(new Parametros_busqueda("Documento aux",
				"documento"));
		listado_parametros.add(new Parametros_busqueda("Nro identificacion",
				"nro_identificacion"));
		listado_parametros.add(new Parametros_busqueda("Primer nombre",
				"nombre1"));
		listado_parametros.add(new Parametros_busqueda("Segundo nombre",
				"nombre2"));
		listado_parametros.add(new Parametros_busqueda("Primer apellido",
				"apellido1"));
		listado_parametros.add(new Parametros_busqueda("Segundo apellido",
				"apellido2"));
		tbxNro_identificacion.listarParametros(listado_parametros, true);
	}

	private void seleccionarPaciente(Paciente paciente) {
		List<Contratos> listado_contratos_pacientes = new ArrayList<Contratos>();
		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(paciente.getCodigo_empresa());
		administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
		administradora.setCodigo(paciente.getCodigo_administradora());
		administradora = administradoraService.consultar(administradora);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("nro_identificacion", paciente.getNro_identificacion());
		parametros.put("codigo_administradora",
				administradora != null ? administradora.getCodigo() : "");

		List<Pacientes_contratos> listado_contratos = pacientes_contratosService
				.listar(parametros);

		for (Pacientes_contratos pacientes_contratos : listado_contratos) {
			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(codigo_empresa);
			contratos.setCodigo_sucursal(codigo_sucursal);
			contratos.setCodigo_administradora(pacientes_contratos
					.getCodigo_administradora());
			contratos.setId_plan(pacientes_contratos.getId_codigo());
			contratos = contratosService.consultar(contratos);
			listado_contratos_pacientes.add(contratos);
		}

		tbxCodigoAseguradora.setValue(paciente.getCodigo_administradora());
		tbxInfoAseguradora.setValue((administradora != null ? administradora
				.getNombre() : ""));
		tbxCodigoAseguradora.setAttribute("ADMINISTRADORA", administradora);
		tbxContrato.setAttribute("LISTADO_CONTRATOS",
				listado_contratos_pacientes);
		tbxContrato.setValue("");

		cargarDatosPaciente(paciente);

		lbxVia_ingreso.setDisabled(false);
		/*
		 * este para validar los servicios que se les puede brindar al paciente
		 */
		ServiciosDisponiblesUtils.validarTipoViaIngresoAdmisionNuevo(
				lbxVia_ingreso, paciente, paciente.getCodigo_administradora(),
				true, listado_contratos_pacientes);
		Listitem listitem_aux = ServiciosDisponiblesUtils.getListitem(
				lbxVia_ingreso, IVias_ingreso.URGENCIA);
		if (listitem_aux != null) {
			listitem_aux.setVisible(false);
		}

		listitem_aux = ServiciosDisponiblesUtils.getListitem(lbxVia_ingreso,
				IVias_ingreso.URGENCIA_ODONTOLOGICO);
		if (listitem_aux != null) {
			listitem_aux.setVisible(false);
		}

		listitem_aux = ServiciosDisponiblesUtils.getListitem(lbxVia_ingreso,
				IVias_ingreso.SERVICIOS_AMBULATORIOS);
		if (listitem_aux != null) {
			listitem_aux.setVisible(false);
		}

		listitem_aux = ServiciosDisponiblesUtils.getListitem(lbxVia_ingreso,
				IVias_ingreso.MEDICAMENTOS_PYP);
		if (listitem_aux != null) {
			listitem_aux.setVisible(false);
		}

		listitem_aux = ServiciosDisponiblesUtils.getListitem(lbxVia_ingreso,
				IVias_ingreso.HOSPITALIZACIONES);
		if (listitem_aux != null) {
			listitem_aux.setVisible(false);
		}

		Adicional_paciente adicional_paciente = new Adicional_paciente();
		adicional_paciente.setCodigo_empresa(codigo_empresa);
		adicional_paciente.setCodigo_sucursal(codigo_sucursal);
		adicional_paciente.setNro_identificacion(paciente
				.getNro_identificacion());
		adicional_paciente = generalExtraService.consultar(adicional_paciente);

		if (adicional_paciente != null) {
			Barrio barrio = new Barrio();
			barrio.setCodigo_barrio(adicional_paciente.getCodigo_barrio());
			barrio = barrioService.consultar(barrio);
			// log.info("barrio: " + barrio);

			Centro_barrio centro_barrio = new Centro_barrio();
			centro_barrio.setCodigo_barrio(barrio != null ? barrio
					.getCodigo_barrio() : "");
			centro_barrio = generalExtraService.consultar(centro_barrio);

			Centro_atencion centro_atencion = new Centro_atencion();
			centro_atencion.setCodigo_empresa(paciente.getCodigo_empresa());
			centro_atencion.setCodigo_sucursal(paciente.getCodigo_sucursal());
			centro_atencion
					.setCodigo_centro((centro_barrio != null ? centro_barrio
							.getCodigo_centro() : ""));
			centro_atencion = centro_atencionService.consultar(centro_atencion);
			tbxCodigo_centro
					.setValue((centro_atencion != null ? centro_atencion
							.getCodigo_centro() : ""));
			tbxCentro_atencion
					.setValue((centro_atencion != null ? centro_atencion
							.getNombre_centro() : ""));
			tbxNro_identificacion.setAttribute("centro", centro_atencion);
		} else {
			tbxCodigo_centro.setValue("");
			tbxCentro_atencion.setValue("NO HAY CENTRO RELACIONADO");
		}
	}

	public void cargarDatosPaciente(Paciente paciente) {
		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo((paciente != null ? paciente.getCodigo_dpto()
				: ""));
		departamentos = departamentosService.consultar(departamentos);

		Municipios municipios = new Municipios();
		municipios
				.setCoddep((paciente != null ? paciente.getCodigo_dpto() : ""));
		municipios.setCodigo((paciente != null ? paciente.getCodigo_municipio()
				: ""));
		municipios = municipiosService.consultar(municipios);

		Elemento elementoTipo_af = new Elemento();
		elementoTipo_af.setTipo("tipo_afiliado");
		elementoTipo_af.setCodigo((paciente != null ? paciente
				.getTipo_afiliado() : ""));
		elementoTipo_af = elementoService.consultar(elementoTipo_af);

		tbxEdad.setValue(Util.getEdadPrsentacionSimple(
				paciente.getFecha_nacimiento(),
				new Timestamp(new Date().getTime())));
		tbxSexo.setValue((paciente != null ? Utilidades.getNombreElemento(
				paciente.getSexo(), "sexo", elementoService) : ""));

		tbxDireccion
				.setValue((paciente != null ? paciente.getDireccion() : ""));

		chkAplica_tel.setChecked(paciente.getTel_res() != null
				&& !paciente.getTel_res().isEmpty());

		String telefono = paciente.getTel_res() != null ? paciente.getTel_res()
				: "";

		try {
			dbxTel.setText(telefono);
		} catch (Exception e) {
			dbxTel.setText("");
		}

		tbxCodigo_prestador.setDisabled(false);

		tbxCodigo_prestador.setValue("");
		tbxNomPrestador.setValue("");

		listboxCitas.getItems().clear();

	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_prestador.inicializar(tbxNomPrestador, btnLimpiarPrestador);
		tbxCodigo_prestador
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {

					@Override
					public void renderListitem(Listitem listitem,
							Map<String, Object> registro) {

						// Extraemos valores
						String nro_identificacion = (String) registro
								.get("nro_identificacion");
						String nombres = (String) registro.get("nombres");
						String apellidos = (String) registro.get("apellidos");
						Integer citas_del_dia = (Integer) registro
								.get("citas_del_dia");
						Integer citas_asignadas = (Integer) registro
								.get("citas_asignadas");
						Integer citas_pendientes = (Integer) registro
								.get("citas_pendientes");
						String tipo_prestador = (String) registro
								.get("tipo_prestador");

						// Mostramos valores en vista
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(nro_identificacion));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(nombres + " "
								+ apellidos));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(tipo_prestador
								.equals("01") ? "MÉDICO" : "ENFERMERA"));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(
								citas_del_dia != null ? citas_del_dia + ""
										: "0"));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(
								citas_asignadas != null ? citas_asignadas + ""
										: "0"));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(
								citas_pendientes != null ? citas_pendientes
										+ "" : "0"));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Map<String, Object>> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("codigo_empresa",
								sucursal.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("fecha_solicitada", new Timestamp(
								dtbxFecha_solicitada.getValue().getTime()));
						parametros
								.put("codigo_centro_dh",
										centro_atencion_session != null ? centro_atencion_session
												.getCodigo_centro()
												: IConstantes.CENTRO_ATENCION_CUALQUIERA);

						parametros.put("rol_programa", getRolViaIngreso());
						parametros.put("codigo_programa_excepcion",
								lbxVia_ingreso.getSelectedItem().getValue()
										.toString());
						// String tipo_prestador = habilitarFiltroEnfermera();
						//
						// if (!tipo_prestador.equals("00")) {
						// parametros.put("tipo_prestador", tipo_prestador);
						// }

						// log.info("consulta de prestador citas" + parametros);
						parametros.put("limite_paginado", "limit 25 offset 0");

						return prestadoresService
								.listarPrestadoresPorRolCentro(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion,
							Map<String, Object> registro) {
						String nro_identificacion = (String) registro
								.get("nro_identificacion");
						String nombre = (registro.get("nombres") + " " + registro
								.get("apellidos"));

						bandbox.setValue(nro_identificacion);
						textboxInformacion.setValue(nombre);

						tbxCodigo_prestador.seleccionarRegistro(registro,
								tbxCodigo_prestador.getValue(),
								tbxNomPrestador.getValue());

						tbxNomPrestador.setHflex("0");
						tbxNomPrestador.setWidth("210px");

						onChangeFechaSolicitud();
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxEspecialidad.setValue("");
						tbxNomPrestador.setHflex("1");
						tbxNomPrestador.setWidth(null);

						tbxCodigo_prestador.seleccionarRegistro(null,
								tbxCodigo_prestador.getValue(),
								tbxNomPrestador.getValue());
						onChangeFechaSolicitud();
						cargarCalendarioCitas();
					}

				});
	}

	/*
	 * private void actualizarDatosHistoria() { try { cargarCalendarioCitas(); }
	 * catch (Exception e) { MensajesUtil.mensajeError(e, "", this); } }
	 */
	private List<Via_ingreso_rol> getRolViaIngreso() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo_via_ingreso", lbxVia_ingreso.getSelectedItem()
				.getValue().toString());
		param.put("codigo_empresa", codigo_empresa);
		// log.info("Map de vias ingreso: " + param);
		return generalExtraService.listar(Via_ingreso_rol.class, param);
	}

	public void listarCombos() {
		Utilidades.listarElementoListbox(lbxMedio_solicitud, false,
				elementoService);
		Utilidades.listarElementoListbox(lbxTipo_cita, true, elementoService);
		Utilidades.listarElementoListbox(lbxVia_ingreso, true, elementoService);
		Utilidades.listarElementoListbox(lbxRelacion, true, elementoService);
		listarEstados();

	}

	public void listarEstados() {
		listboxEstados.getItems().clear();

		Listitem listitem = new Listitem();
		listitem.setLabel("Libre");
		listitem.setValue("0");
		listitem.setSelected(true);
		listboxEstados.appendChild(listitem);

		listitem = new Listitem();
		listitem.setLabel("Asignada");
		listitem.setValue("1");
		listitem.setSelected(false);
		listboxEstados.appendChild(listitem);

		listitem = new Listitem();
		listitem.setLabel("Cumplida");
		listitem.setValue("2");
		listitem.setSelected(false);
		listboxEstados.appendChild(listitem);

		listitem = new Listitem();
		listitem.appendChild(new Listcell());
		listitem.setLabel("Cumplida Manual");
		listitem.setValue("3");
		listitem.setSelected(false);
		listboxEstados.appendChild(listitem);

		listitem = new Listitem();
		listitem.setLabel("Cancelada");
		listitem.setValue("5");
		listitem.setSelected(false);
		listboxEstados.appendChild(listitem);

		listitem = new Listitem();
		listitem.setLabel("Reemplazada");
		listitem.setValue("6");
		listitem.setSelected(false);
		listboxEstados.appendChild(listitem);

	}

	public void seleccionarTipo_consulta() {
		try {
			String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
					.toString();
			tbxContrato.setValue("");
			if (!via_ingreso.isEmpty()) {
				boolean apoyo_diagnostico = isApoyoDiagnostico();
				if (!via_ingreso.equals(IVias_ingreso.LABORATORIOS_PYP)) {
					verificarContratosNoCapitados(via_ingreso);
					rowContratos.setVisible(true);
				} else {
					rowContratos.setVisible(false);
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("codigo_empresa", codigo_empresa);
				parameters.put("codigo_sucursal", codigo_sucursal);
				parameters.put("nro_identificacion",
						tbxNro_identificacion.getValue());

				parameters.put("anio", anio);
				parameters.put("tipo_consulta", via_ingreso);
				parameters.put("via_ingreso", lbxVia_ingreso.getSelectedItem()
						.getValue().toString());

				Boolean previas_sios = false;
				if (lbxVia_ingreso.getSelectedIndex() != 0
						&& !tbxNro_identificacion.getValue().isEmpty()) {
					previas_sios = UtilidadesSios.pacienteTieneRegistro(
							hisc_historialSiosService, empresa,
							tbxNro_identificacion.getValue(),
							(String) lbxVia_ingreso.getSelectedItem()
									.getValue(), true);
				}
				if (apoyo_diagnostico) {
					List<Citas> lista_citas = citasService.listar(parameters);

					if (!lista_citas.isEmpty()) {
						boolean repetido = false;
						for (Citas citas : lista_citas) {
							if (citas.getEstado().equals("2")) {
								repetido = true;
								break;
							}
						}

						if (repetido) {
							lbxTipo_cita.setSelectedIndex(2);
						} else {
							if (!previas_sios) {
								lbxTipo_cita.setSelectedIndex(1);
							} else {
								lbxTipo_cita.setSelectedIndex(2);
							}

						}

					} else {
						lbxTipo_cita.setSelectedIndex(1);
					}
				} else {
					List<Historia_clinica> listado_historias = generalExtraService
							.listar(Historia_clinica.class,parameters);
					if (!listado_historias.isEmpty()) {
						lbxTipo_cita.setSelectedIndex(2);
					} else if (!previas_sios) {
						lbxTipo_cita.setSelectedIndex(1);
					} else {
						lbxTipo_cita.setSelectedIndex(2);
					}
				}

				if (isApoyoDiagnostico()) {
					rowEspecialidad.setVisible(false);
					rowPrestador.setVisible(false);
					tbxCodigo_prestador
							.setValue(IConstantes.CODIGO_MEDICO_DEFECTO);
					listboxCitas.setVisible(false);
					listboxLaboratorios.setVisible(true);
				} else {
					rowPrestador.setVisible(true);
					tbxCodigo_prestador.limpiarSeleccion(false);
					listboxCitas.setVisible(true);
					listboxLaboratorios.setVisible(false);
				}

				// vlidacion
				Centro_atencion centro_atencionPaciente = (Centro_atencion) tbxNro_identificacion
						.getAttribute("centro");
				String codigo_centro = centro_atencionPaciente != null ? centro_atencionPaciente
						.getCodigo_centro() : "";
				String codigo_centro_session = centro_atencion_session != null ? centro_atencion_session
						.getCodigo_centro() : "";

				Paciente paciente = tbxNro_identificacion
						.getRegistroSeleccionado();
				if (paciente != null) {
					boolean cargarHorario = true;
					if (!codigo_centro
							.equals(IConstantes.CENTRO_ATENCION_CUALQUIERA)
							&& !codigo_centro.trim().isEmpty()) {
						if ((!via_ingreso.equals(IVias_ingreso.URGENCIA)
								&& !via_ingreso
										.equals(IVias_ingreso.HOSPITALIZACIONES)
								&& !isApoyoDiagnostico() && !via_ingreso
									.equals(IVias_ingreso.PSICOLOGIA))
								&& !codigo_centro.equals(codigo_centro_session)) {
							String centro_salud_consultado = centro_atencionPaciente != null ? centro_atencionPaciente
									.getNombre_centro() : "";
							Messagebox
									.show("El paciente "
											+ paciente.getNombreCompleto()
											+ " no puede ser atendido en el CAP "
											+ centro_atencion_session
													.getCodigo_centro()
											+ "-"
											+ centro_atencion_session
													.getNombre_centro()
											+ ". Este paciente tiene que ser atendido en el centro de atencion "
											+ codigo_centro
											+ "-"
											+ centro_salud_consultado
											+ ". ¿De todas formas desea realizar la cita?",
											"Pertenece a otro centro...",
											Messagebox.YES + Messagebox.NO,
											Messagebox.QUESTION,
											new org.zkoss.zk.ui.event.EventListener<Event>() {
												public void onEvent(Event event)
														throws Exception {
													if ("onYes".equals(event
															.getName())) {
														cargarCalendarioCitas();
													} else {
														lbxVia_ingreso
																.setSelectedIndex(0);
													}
												}
											});
							cargarHorario = false;
						}
					}

					// El sistema va habilitar las citas dependiendo si
					// al paciente es permitido esa atencion
					if (cargarHorario) {
						cargarCalendarioCitas();
					}
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void verificarContratosNoCapitados(String via_ingreso) {
		Administradora administradora = (Administradora) tbxCodigoAseguradora
				.getAttribute("ADMINISTRADORA");
		List<Contratos> listado_contratos = (List<Contratos>) tbxContrato
				.getAttribute("LISTADO_CONTRATOS");
		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
		Admision admision_aux = new Admision();
		admision_aux.setCodigo_empresa(codigo_empresa);
		admision_aux.setCodigo_sucursal(codigo_sucursal);
		admision_aux.setNro_identificacion(paciente.getNro_identificacion());
		admision_aux.setCodigo_administradora(administradora.getCodigo());
		admision_aux.setVia_ingreso(via_ingreso);
		admision_aux
				.setParticular((administradora != null && administradora
						.getTipo_aseguradora()
						.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) ? "S"
						: "N");
		List<Via_ingreso_contratadas> listado_vias_ingreso = ServiciosDisponiblesUtils
				.getListado_via_ingreso_contratadas(admision_aux,
						listado_contratos);
		boolean existe_capitado = false;
		if (listado_vias_ingreso.size() == 1) {
			Via_ingreso_contratadas via_ingreso_contratadas = listado_vias_ingreso
					.get(0);
			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(codigo_empresa);
			contratos.setCodigo_sucursal(codigo_sucursal);
			contratos.setId_plan(via_ingreso_contratadas.getId_plan());
			contratos.setCodigo_administradora(via_ingreso_contratadas
					.getCodigo_administradora());
			contratos = contratosService.consultar(contratos);
			if (contratos != null) {
				if (contratos.getTipo_facturacion().equals("01")) {
					tbxContrato.setValue((contratos.getNro_contrato() + " "
							+ contratos.getNombre() + " (CAPITADO)").trim());
					existe_capitado = true;
				} else if (contratos.getTipo_facturacion().equals("02")) {
					tbxContrato.setValue((contratos.getNro_contrato() + " "
							+ contratos.getNombre() + " (EVENTO)").trim());
				} else {
					tbxContrato.setValue((contratos.getNro_contrato() + " "
							+ contratos.getNombre() + " (OTRO)").trim());
				}
			}
		} else if (!listado_vias_ingreso.isEmpty()) {
			Contratos contratos_aux = null;
			for (Via_ingreso_contratadas via_ingreso_contratadas : listado_vias_ingreso) {
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(codigo_empresa);
				contratos.setCodigo_sucursal(codigo_sucursal);
				contratos.setId_plan(via_ingreso_contratadas.getId_plan());
				contratos.setCodigo_administradora(via_ingreso_contratadas
						.getCodigo_administradora());
				contratos = contratosService.consultar(contratos);
				if (contratos != null) {
					contratos_aux = contratos;
					if (contratos.getTipo_facturacion().equals("01")) {
						existe_capitado = true;
						break;
					}
				}
			}

			if (contratos_aux != null) {
				if (contratos_aux.getTipo_facturacion().equals("01")) {
					tbxContrato
							.setValue((contratos_aux.getNro_contrato() + " "
									+ contratos_aux.getNombre() + " (CAPITADO)")
									.trim());
				} else if (contratos_aux.getTipo_facturacion().equals("02")) {
					tbxContrato.setValue((contratos_aux.getNro_contrato() + " "
							+ contratos_aux.getNombre() + " (EVENTO)").trim());
				} else {
					tbxContrato.setValue((contratos_aux.getNro_contrato() + " "
							+ contratos_aux.getNombre() + " (OTRO)").trim());
				}
			}
		}

		if (!existe_capitado) {
			MensajesUtil
					.mensajeError(
							"Contratos No Capitados",
							"Al parecer este paciente tiene asociado contratos no capitados. Verificar las autorizaciones.\n"
									+ tbxContrato.getValue().trim(),
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									MensajesUtil.notificarError(
											"Este contrato no es capitado",
											tbxContrato);
								}

							});
		}

	}

	private boolean isApoyoDiagnostico() {
		String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue();
		return ServiciosDisponiblesUtils.isApoyoDiagnostico(via_ingreso);
	}

	public void listarAreaIntervencion(Listbox listbox, boolean sw) {
		listbox.getItems().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("-- seleccionar --");
		listbox.appendChild(listitem);

		if (sw) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", codigo_empresa);
			map.put("codigo_sucursal", codigo_sucursal);
			List<Pyp> pyps = generalExtraService.listar(Pyp.class, map);

			for (Pyp pyp : pyps) {
				listitem = new Listitem();
				listitem.setValue(pyp.getCodigo());
				listitem.setLabel(pyp.getNombre());
				listbox.appendChild(listitem);
			}
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}

	}

	public boolean validarForm() throws Exception {

		XulElement[] componentes1 = { tbxNro_identificacion, tbxDireccion,
				lbxVia_ingreso, lbxTipo_cita, dtbxFecha_solicitada };

		XulElement[] componentes2 = { tbxAcompaniante, tbxApellidos_acomp,
				tbxCedula_acomp, tbxTel_acompaniante, lbxRelacion,
				tbxNro_identificacion, tbxDireccion, lbxVia_ingreso,
				lbxTipo_cita, dtbxFecha_solicitada };

		XulElement[] componentes3 = { tbxNro_identificacion, tbxDireccion,
				dbxTel, lbxVia_ingreso, lbxTipo_cita, dtbxFecha_solicitada };

		XulElement[] componentes4 = { tbxAcompaniante, tbxApellidos_acomp,
				tbxCedula_acomp, tbxTel_acompaniante, lbxRelacion,
				tbxNro_identificacion, tbxDireccion, dbxTel, lbxVia_ingreso,
				lbxTipo_cita, dtbxFecha_solicitada };

		if (tbxAcompaniante.isReadonly()) {
			if (!chkAplica_tel.isChecked()) {
				FormularioUtil.validarCamposObligatorios(componentes1);
			} else {
				FormularioUtil.validarCamposObligatorios(componentes3);
			}
		} else {
			if (!chkAplica_tel.isChecked()) {
				FormularioUtil.validarCamposObligatorios(componentes2);
			} else {
				FormularioUtil.validarCamposObligatorios(componentes4);
			}
		}

		// lbxAyuda_dx.setStyle("background-color:white");
		dtbxFecha_solicitada.setStyle("background-color:white");
		lbxVia_ingreso.setStyle("background-color:white");

		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";

		String fecha_sistema = formatoFecha.format(Calendar.getInstance()
				.getTime());
		String fecha_solicitada = formatoFecha.format(dtbxFecha_solicitada
				.getValue());

		if (!fecha_sistema.equals(fecha_solicitada)) {
			if (Calendar.getInstance().getTime()
					.compareTo(dtbxFecha_solicitada.getValue()) > 0) {
				valida = false;
				msj = "La fecha solicitada debe ser mayor que la fecha actual";
				dtbxFecha_solicitada.setStyle("background-color:#F6BBBE");
			}
		}

		if (!valida) {
			Messagebox.show(msj, usuarios.getNombres() + " recuerde que...",
					Messagebox.OK, Messagebox.EXCLAMATION);
			Listitem listitem_evento = listboxCitas.getSelectedItem();
			listitem_evento.setSelected(false);
		}

		return valida;
	}

	public void limpiarCalendarioCitas() {
		labelProgramacion_citas.setValue("LISTADO DE PROGRAMACIÓN DE CITAS ");
		listboxCitas.getItems().clear();
		listboxLaboratorios.getItems().clear();
	}

	public void cargarCalendarioCitas() {
		try {
			limpiarCalendarioCitas();
			if (lbxVia_ingreso.getSelectedItem() != null
					&& !lbxVia_ingreso.getSelectedItem().getValue().toString()
							.isEmpty()
					&& dtbxFecha_solicitada.getValue() != null) {

				if (!isApoyoDiagnostico()) {
					Date fecha_busqueda = dtbxFecha_busqueda.getValue();
					if (fecha_busqueda == null) {
						throw new ValidacionRunTimeException(
								"Debe seleccionar la fecha de busqueda para realizar la consulta");
					}
					if (fecha_busqueda.compareTo(dtbxFecha_solicitada
							.getValue()) < 0) {
						throw new ValidacionRunTimeException(
								"La fecha de busqueda debe ser mayor o igual a la fecha solicitada");
					}

					List<Via_ingreso_rol> codigo_rols = getRolViaIngreso();

					GregorianCalendar gregorianCalendar = new GregorianCalendar();

					boolean parametro_mayor = false;

					labelProgramacion_citas
							.setValue("LISTADO DE PROGRAMACIÓN DE CITAS "
									+ formatoFecha.format(fecha_busqueda)
											.toUpperCase());

					if (formatoFecha.format(gregorianCalendar.getTime())
							.equals(formatoFecha.format(fecha_busqueda))) {
						parametro_mayor = true;
					}
					gregorianCalendar.setTime(fecha_busqueda);

					Map<String, Object> parameters = new HashMap();
					parameters.put("codigo_empresa",
							sucursal.getCodigo_empresa());
					parameters.put("codigo_sucursal",
							sucursal.getCodigo_sucursal());
					parameters.put("fecha_final_busqueda",
							gregorianCalendar.getTime());

					if (!codigo_rols.isEmpty()) {
						parameters.put("codigo_rols", codigo_rols);
					}

					Map<String, Object> prestadores_map = tbxCodigo_prestador
							.getRegistroSeleccionado();

					String codigo_centro = centro_atencion_session
							.getCodigo_centro();
					parameters.put("codigo_centro", codigo_centro);

					if (prestadores_map != null) {
						parameters.put("codigo_medico", ""
								+ tbxCodigo_prestador.getValue());
					}
					// parameters.put("solo_disponibles", "_NotNulls");

					if (parametro_mayor) {
						parameters.put("fecha_solicitada", fecha_busqueda);
						// parameters.put("disponibles_desde_fecha",
						// "_NotNulls");
					} else {
						parameters.put("fecha_solicitada", fecha_busqueda);
					}

					Map<String, String> mapa_estados = new HashMap<String, String>();

					for (Listitem listitem : listboxEstados.getItems()) {
						if (listitem.isSelected()) {
							mapa_estados.put(listitem.getValue().toString(),
									listitem.getLabel());
						}
					}

					List<Detalles_horarios> listaDetalles_horarios = detalles_horariosService
							.listar_registros(parameters);

					for (Detalles_horarios detalles_horarios : listaDetalles_horarios) {
						Map<String, Object> mapa_citas = new HashMap<String, Object>();
						mapa_citas.put("codigo_empresa",
								detalles_horarios.getCodigo_empresa());
						mapa_citas.put("codigo_sucursal",
								detalles_horarios.getCodigo_sucursal());
						mapa_citas.put("codigo_prestador",
								detalles_horarios.getCodigo_medico());
						mapa_citas.put("codigo_detalle_horario",
								detalles_horarios.getConsecutivo());
						mapa_citas.put("estado_act", "-");

						Citas citas = citasService.consultar_citas(mapa_citas);
						// if (citas != null)
						String estado = "";
						String color = "";
						String codigo_estado = "";
						String background_color = "";

						if (citas == null) {
							estado = "Libre";
							color = "black";
							codigo_estado = "0";
						} else if (citas.getEstado().equals("1")) {
							estado = "Asignada";
							color = "blue";
							codigo_estado = "1";
							// background_color = ";background-color:gray";
						} else if (citas.getEstado().equals("2")) {
							estado = "Cumplida";
							color = "green";
							codigo_estado = "2";
						} else if (citas.getEstado().equals("5")) {
							estado = "Cancelada";
							color = "red";
							codigo_estado = "5";
						}

						if (mapa_estados.containsKey(codigo_estado)) {
							Listitem listitem = new Listitem();

							final Map<String, Object> mapa_evento = new HashMap<String, Object>();
							mapa_evento.put("detalles_horarios",
									detalles_horarios);
							mapa_evento.put("codigo_estado", codigo_estado);
							mapa_evento.put("citas", citas);

							listitem.setValue(mapa_evento);

							Listcell listcell = new Listcell();

							listcell.setLabel(estado);
							listcell.setStyle("color:" + color
									+ "; font-weight:bold" + background_color);
							listitem.appendChild(listcell);

							listcell = new Listcell(
									formatoFechaHora.format(detalles_horarios
											.getFecha_inicial())
											+ " - "
											+ formatoFechaHora
													.format(detalles_horarios
															.getFecha_final()));
							listcell.setStyle("color:" + color
									+ "; font-weight:bold" + background_color);
							listitem.appendChild(listcell);

							Prestadores prestadores;

							if (mapa_prestadores.containsKey(detalles_horarios
									.getCodigo_medico())) {
								prestadores = mapa_prestadores
										.get(detalles_horarios
												.getCodigo_medico());
							} else {
								prestadores = new Prestadores();
								prestadores.setCodigo_empresa(codigo_empresa);
								prestadores.setCodigo_sucursal(codigo_sucursal);
								prestadores
										.setNro_identificacion(detalles_horarios
												.getCodigo_medico());
								prestadores = prestadoresService
										.consultar(prestadores);
								mapa_prestadores.put(
										detalles_horarios.getCodigo_medico(),
										prestadores);
							}

							listcell = new Listcell(
									prestadores != null ? prestadores
											.getNombres()
											+ " "
											+ prestadores.getApellidos() : "");
							listcell.setStyle("color:" + color
									+ "; font-weight:bold" + background_color);
							listitem.appendChild(listcell);

							Consultorio consultorio;

							if (mapa_consultorios.containsKey(detalles_horarios
									.getCodigo_consultorio())) {
								consultorio = mapa_consultorios
										.get(detalles_horarios
												.getCodigo_consultorio());
							} else {
								consultorio = new Consultorio();
								consultorio.setCodigo_empresa(codigo_empresa);
								consultorio.setCodigo_sucursal(codigo_sucursal);
								consultorio
										.setCodigo_consultorio(detalles_horarios
												.getCodigo_consultorio());
								consultorio = consultorioService
										.consultar(consultorio);
								mapa_consultorios.put(detalles_horarios
										.getCodigo_consultorio(), consultorio);
							}

							listcell = new Listcell(
									consultorio != null ? consultorio
											.getCodigo_consultorio()
											+ " "
											+ consultorio.getNombre()
											: detalles_horarios
													.getCodigo_consultorio());
							listcell.setStyle("color:" + color
									+ "; font-weight:bold" + background_color);
							listitem.appendChild(listcell);

							listitem.addEventListener(Events.ON_CLICK,
									new EventListener<Event>() {

										@Override
										public void onEvent(Event arg0)
												throws Exception {
											seleccionarApartarCita(mapa_evento);
										}

									});

							listboxCitas.appendChild(listitem);
						}

					}

					if (listboxCitas.getItemCount() == 0) {
						if (mapa_estados.isEmpty()) {
							MensajesUtil.mensajeInformacion(
									"No se encontraron citas",
									"No se encontraron citas en el sistema");
						} else {
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append("Estados [");
							for (String key_map : mapa_estados.keySet()) {
								stringBuilder.append(mapa_estados.get(key_map))
										.append(" ");
							}
							stringBuilder.append("]");
							MensajesUtil.mensajeInformacion(
									"No se encontraron citas",
									"No se encontraron citas en el sistema para "
											+ stringBuilder.toString());
						}
					}

				} else {
					Date date_final = dtbxFecha_final2.getValue();
					if (date_final != null) {
						Calendar fecha_solicitada = Calendar.getInstance();
						fecha_solicitada.setTime(dtbxFecha_solicitada
								.getValue());
						while (fecha_solicitada.getTime().compareTo(date_final) <= 0) {
							final Map<String, Object> mapa_evento = new HashMap<String, Object>();
							mapa_evento.put("fecha_seleccionada",
									fecha_solicitada.getTime());
							Listitem listitem = new Listitem();
							listitem.setValue(fecha_solicitada.getTime());
							Listcell celda = new Listcell(
									(" " + formatoFecha.format(fecha_solicitada
											.getTime())).toUpperCase());
							celda.setStyle("font-weight:bold");
							listitem.appendChild(celda);
							listboxLaboratorios.appendChild(listitem);

							listitem.addEventListener(Events.ON_CLICK,
									new EventListener<Event>() {

										@Override
										public void onEvent(Event arg0)
												throws Exception {
											seleccionarApartarCita(mapa_evento);
										}

									});

							fecha_solicitada.set(Calendar.DATE,
									fecha_solicitada.get(Calendar.DATE) + 1);
						}
					}
				}
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	public String verificarRolMedico() {
		String rol_medico = "05";
		if (lbxVia_ingreso.getSelectedItem() != null) {
			String tipo_consulta = lbxVia_ingreso.getSelectedItem().getValue()
					.toString();
			if (tipo_consulta
					.equals(ServiciosDisponiblesUtils.CONSULTA_EXTERNA)) {
				rol_medico = "05";
			} else if (tipo_consulta
					.equals(ServiciosDisponiblesUtils.CONSULTA_ODONOTOLOGIA)) {
				rol_medico = "12";
			} else if (tipo_consulta
					.equals(ServiciosDisponiblesUtils.CONSULTA_PSICOLOGIA)
					|| tipo_consulta
							.equals(ServiciosDisponiblesUtils.CONSULTA_PSIQUIATRIA)) {
				rol_medico = "14";
			} else if (tipo_consulta
					.equals(ServiciosDisponiblesUtils.CONSULTA_PYP)) {
				rol_medico = "13";
			}
		}
		return rol_medico;

	}

	private void guardarCita(Map<String, Object> mapa_evento) throws Exception {
		Window ventana_cita = (Window) mapa_evento.get("ventana_cita");
		try {
			Boolean reemplazar = (Boolean) mapa_evento.get("reemplazar");
			String codigo_estado = (String) mapa_evento.get("codigo_estado");
			Detalles_horarios detalles_horarios = (Detalles_horarios) mapa_evento
					.get("detalles_horarios");

			if (codigo_estado.equals("0")) {
				if (validarForm()) {
					generarCita(detalles_horarios, ventana_cita);
				}
			} else if (codigo_estado.equals("1")) {
				if (reemplazar != null) {
					if (validarForm()) {
						Citas citas = (Citas) mapa_evento.get("citas");
						citas.setEstado("6");
						citasService.actualizar(citas);
						generarCita(detalles_horarios, ventana_cita);
					}
				} else {
					verCitaApartada(mapa_evento);
				}
			}
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				if (ventana_cita != null) {
					ventana_cita.detach();
					mapa_evento.remove("ventana_cita");
					mapa_evento.remove("reemplazar");
				}
				throw new Exception(e.getMessage(), e);
			} else {
				if (ventana_cita != null) {
					ventana_cita.detach();
					mapa_evento.remove("ventana_cita");
					mapa_evento.remove("reemplazar");
				}
				Listitem listitem_evento = listboxCitas.getSelectedItem();
				listitem_evento.setSelected(false);
			}
		}
	}

	public void verCitaApartada(final Map<String, Object> mapa_evento) {
		final Citas cita = (Citas) mapa_evento.get("citas");
		final Window window = (Window) Executions.createComponents(
				"/pages/cita_apartada.zul", this, null);
		final Textbox tbxCodigo_cita = (Textbox) window
				.getFellow("tbxCodigo_cita");
		tbxCodigo_cita.setValue(cita.getCodigo_cita());
		final Textbox tbxIdentificacion = (Textbox) window
				.getFellow("tbxIdentificacion");
		tbxIdentificacion.setValue(cita.getNro_identificacion());

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(cita.getNro_identificacion());
		paciente = pacienteService.consultar(paciente);
		if (paciente != null) {
			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(paciente.getCodigo_empresa());
			administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
			administradora.setCodigo(paciente.getCodigo_administradora());
			administradora = administradoraService.consultar(administradora);

			final Textbox tbxNomPaciente = (Textbox) window
					.getFellow("tbxNomPaciente");
			tbxNomPaciente.setValue(paciente.getNombreCompleto());

			final Textbox tbxCodigoAseguradora = (Textbox) window
					.getFellow("tbxCodigoAseguradora");
			tbxCodigoAseguradora.setValue(paciente.getCodigo_administradora());

			final Textbox tbxInfoAseguradora = (Textbox) window
					.getFellow("tbxInfoAseguradora");
			tbxInfoAseguradora
					.setValue((administradora != null ? administradora
							.getNombre() : ""));

			Adicional_paciente adicional_paciente = new Adicional_paciente();
			adicional_paciente.setCodigo_empresa(codigo_empresa);
			adicional_paciente.setCodigo_sucursal(codigo_sucursal);
			adicional_paciente.setNro_identificacion(paciente
					.getNro_identificacion());
			adicional_paciente = generalExtraService
					.consultar(adicional_paciente);

			if (adicional_paciente != null) {
				Barrio barrio = new Barrio();
				barrio.setCodigo_barrio(adicional_paciente.getCodigo_barrio());
				barrio = barrioService.consultar(barrio);

				Centro_barrio centro_barrio = new Centro_barrio();
				centro_barrio.setCodigo_barrio(barrio != null ? barrio
						.getCodigo_barrio() : "");
				centro_barrio = generalExtraService.consultar(centro_barrio);

				Centro_atencion centro_atencion = new Centro_atencion();
				centro_atencion.setCodigo_empresa(paciente.getCodigo_empresa());
				centro_atencion.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				centro_atencion
						.setCodigo_centro((centro_barrio != null ? centro_barrio
								.getCodigo_centro() : ""));
				centro_atencion = centro_atencionService
						.consultar(centro_atencion);

				final Textbox tbxCentro_atencion = (Textbox) window
						.getFellow("tbxCentro_atencion");
				tbxCentro_atencion
						.setValue((centro_atencion != null ? centro_atencion
								.getNombre_centro() : ""));
			}
			final Textbox tbxSexo = (Textbox) window.getFellow("tbxSexo");
			tbxSexo.setValue((paciente != null ? Utilidades.getNombreElemento(
					paciente.getSexo(), "sexo", elementoService) : ""));

			final Textbox tbxEdad = (Textbox) window.getFellow("tbxEdad");
			tbxEdad.setValue((paciente != null ? Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy")
							.format(paciente.getFecha_nacimiento()), "1", true)
					: ""));

			Detalles_horarios detalles_horarios = new Detalles_horarios();
			detalles_horarios.setConsecutivo(cita.getCodigo_detalle_horario());
			detalles_horarios = detalles_horariosService
					.consultar(detalles_horarios);

			Consultorio consultorio = new Consultorio();
			consultorio.setCodigo_empresa(codigo_empresa);
			consultorio.setCodigo_sucursal(codigo_sucursal);
			consultorio.setCodigo_consultorio(detalles_horarios
					.getCodigo_consultorio());
			consultorio = consultorioService.consultar(consultorio);

			final Textbox tbxConsultorio = (Textbox) window
					.getFellow("tbxConsultorio");
			tbxConsultorio.setValue(consultorio.getNombre());

			final Textbox tbxHora = (Textbox) window.getFellow("tbxHora");
			tbxHora.setValue(cita.getHora());

			Prestadores prestador = new Prestadores();
			prestador.setCodigo_empresa(codigo_empresa);
			prestador.setCodigo_sucursal(codigo_sucursal);
			prestador.setNro_identificacion(cita.getCodigo_prestador());
			prestador = prestadoresService.consultar(prestador);

			final Textbox tbxPrestador = (Textbox) window
					.getFellow("tbxPrestador");
			tbxPrestador.setValue(prestador.getNombres() + " "
					+ prestador.getApellidos());

			Elemento via_ingreso = new Elemento();
			via_ingreso.setTipo("via_ingreso");
			via_ingreso.setCodigo(cita.getTipo_consulta());
			via_ingreso = elementoService.consultar(via_ingreso);

			final Textbox tbxViaingreso = (Textbox) window
					.getFellow("tbxViaingreso");
			tbxViaingreso.setValue(via_ingreso.getDescripcion());

			Toolbarbutton toolbarbutton = (Toolbarbutton) window
					.getFellow("btnImprimir");

			toolbarbutton.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							imprimir(cita.getCodigo_cita());
							window.onClose();
						}
					});

			Toolbarbutton btnReemplazar = (Toolbarbutton) window
					.getFellow("btnReemplazar");

			final Paciente paciente_aux = paciente;
			final Paciente paciente_seleccionado = tbxNro_identificacion
					.getRegistroSeleccionado();

			if (paciente_seleccionado.getNro_identificacion().equals(
					paciente_aux.getNro_identificacion())) {
				btnReemplazar.setDisabled(true);
			}

			btnReemplazar.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {

							Messagebox.show(
									"¡Estas seguro que deseas reemplazar la cita aparatada de "
											+ paciente_aux
													.getNro_identificacion()
											+ " - "
											+ paciente_aux.getNombreCompleto()
											+ " por el paciente seleccionado "
											+ paciente_seleccionado
													.getNro_identificacion()
											+ " - "
											+ paciente_seleccionado
													.getNombreCompleto(),
									"Confirmacion de reemplazo de citas",
									Messagebox.YES + +Messagebox.NO,
									Messagebox.INFORMATION,
									new EventListener<Event>() {
										@Override
										public void onEvent(Event event)
												throws Exception {
											if ("onYes".equalsIgnoreCase(event
													.getName())) {
												mapa_evento.put("reemplazar",
														true);
												mapa_evento.put("ventana_cita",
														window);
												guardarCita(mapa_evento);
											}

										}
									});
						}

					});

			window.setTitle("Cita Asignada");
			window.setMode("modal");
		}
	}

	private void generarCita(Detalles_horarios detalles_horarios,
			final Window ventana_reemplazo) throws Exception {
		final Detalles_horarios detalles_horarios_aux = detalles_horarios;
		try {
			// validacion
			// if(valido){
			Messagebox
					.show("Usuario "
							+ usuarios.getNombres()
							+ " estas seguro que deseas apartar esta cita, para el dia "
							+ new SimpleDateFormat("yyyy/MM/dd")
									.format(detalles_horarios
											.getFecha_inicial()),
							"Informacion", Messagebox.OK + Messagebox.CANCEL,
							Messagebox.INFORMATION, new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									if ("onOK".equals(arg0.getName())) {
										try {
											Date today = Calendar.getInstance()
													.getTime();

											Map<String, Object> parametros = new HashMap<String, Object>();
											parametros.put("codigo_empresa",
													codigo_empresa);
											parametros.put("codigo_sucursal",
													codigo_sucursal);
											parametros.put(
													"nro_identificacion",
													tbxNro_identificacion
															.getValue());
											parametros.put("tipo_consulta",
													lbxVia_ingreso
															.getSelectedItem()
															.getValue()
															.toString());
											parametros.put("anio", anio);
											parametros.put("estado", "1");

											List<Citas> listado_citas = citasService
													.listar(parametros);
											if (!listado_citas.isEmpty()) {
												Citas citas_aux = listado_citas
														.get(0);
												throw new Exception(
														"No se puede hacer el registro de la cita porque este paciente tiene una cita activa "
																+ "en esta área '"
																+ lbxVia_ingreso
																		.getSelectedItem()
																		.getLabel()
																+ "' para la fecha "
																+ new SimpleDateFormat(
																		"yyyy/MM/dd")
																		.format(citas_aux
																				.getFecha_cita()));
											}

											final Citas citas = new Citas();
											citas.setCodigo_empresa(empresa
													.getCodigo_empresa());
											citas.setCodigo_sucursal(sucursal
													.getCodigo_sucursal());
											citas.setNro_identificacion(tbxNro_identificacion
													.getValue());
											citas.setCodigo_prestador(detalles_horarios_aux
													.getCodigo_medico());
											citas.setFecha_cita(new Timestamp(
													detalles_horarios_aux
															.getFecha_inicial()
															.getTime()));
											citas.setHora(formatHora
													.format(citas
															.getFecha_cita()));
											citas.setValor_cita(0);
											citas.setCopago_cita(0);
											citas.setEstado("1");
											citas.setTipo_consulta(lbxVia_ingreso
													.getSelectedItem()
													.getValue().toString());
											citas.setCreacion_date(new Timestamp(
													Calendar.getInstance()
															.getTimeInMillis()));
											citas.setUltimo_update(new Timestamp(
													Calendar.getInstance()
															.getTimeInMillis()));
											citas.setCreacion_user(getCreacionUser());
											citas.setUltimo_user(getCreacionUser());
											Centro_atencion centro_atencion = centro_atencion_session;
											citas.setCodigo_centro(centro_atencion != null ? centro_atencion
													.getCodigo_centro() : "");

											// citas.setArea_intervencion(lbxArea_intervencion
											// .getSelectedItem()
											// .getValue().toString());
											citas.setCodigo_plantilla("");
											citas.setCodigo_detalle_horario(detalles_horarios_aux
													.getConsecutivo());
											citas.setFecha_solicitada(new Timestamp(
													dtbxFecha_solicitada
															.getValue()
															.getTime()));
											/* calculamos la resolucion 1552 */
											citas.setDiferencia1(new Long(
													L2HContraintDate.getDiferentDay(
															citas.getFecha_solicitada(),
															today)).intValue());
											citas.setDiferencia2(new Long(
													L2HContraintDate.getDiferentDay(
															citas.getFecha_cita(),
															today)).intValue());
											/* fin de calculo de diferencias */
											citas.setAcompaniante(tbxAcompaniante
													.getValue());
											citas.setTel_acompaniante(tbxTel_acompaniante
													.getText());
											citas.setRelacion(lbxRelacion
													.getSelectedItem()
													.getValue().toString());
											citas.setOtro_acompaniante(tbxOtro_acompaniante
													.getValue());
											citas.setCedula_acomp(tbxCedula_acomp
													.getText());
											citas.setApellidos_acomp(tbxApellidos_acomp
													.getValue());

											citas.setCodigo_administradora(tbxCodigoAseguradora
													.getValue());

											citas.setMedio_solicitud(lbxMedio_solicitud
													.getSelectedItem()
													.getValue().toString());
											citas.setTipo_cita(lbxTipo_cita
													.getSelectedItem()
													.getValue().toString());
											final Citas auxCitas = citasService
													.guardarCita(
															citas,
															tbxDireccion
																	.getValue(),
															String.valueOf(dbxTel
																	.getValue()));

											if (!isApoyoDiagnostico()) {
												cargarCalendarioCitas();
											}

											Messagebox
													.show("Cita apartada exitosamente, Desea imprimir el recordatorio de citas.",
															"Informacion",
															Messagebox.YES
																	+ +Messagebox.NO,
															Messagebox.INFORMATION,
															new EventListener<Event>() {
																@Override
																public void onEvent(
																		Event event)
																		throws Exception {
																	if ("onYes"
																			.equalsIgnoreCase(event
																					.getName())) {
																		if (ventana_reemplazo != null) {
																			ventana_reemplazo
																					.detach();
																		}
																		imprimir(auxCitas
																				.getCodigo_cita());
																	} else {
																		if (ventana_reemplazo != null) {
																			ventana_reemplazo
																					.detach();
																		}
																	}

																}
															});
											limpiarDatos();
										} catch (Exception e) {
											MensajesUtil
													.mensajeAlerta(
															"No se puede apartar la cita medica",
															e.getMessage());
										}
									}
								}
							});
			// }
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void imprimir(String codigo_cita) throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "RecordatorioCitas");
		paramRequest.put("codigo_cita", codigo_cita + "");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void abrirWindowHistorial() {
		if (tbxNro_identificacion.getValue().equals("")) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"Para realizar esta accion debe seleccionar el paciente.",
					IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("nro_identificacion", tbxNro_identificacion.getValue());
		map.put("anio", anio);

		String columnas = "Codigo#100px|Fecha cita#150px|Nro id#100px|Paciente|Prestador|Via Ingreso#200px|Estado#100px";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"CONSULTAR HISTORIAL DE CITAS", Paquetes.HEALTHMANAGER,
				"CitasDao.listar", this, columnas, map);
		// windowRegistros.getToolbar().setVisible(false);
		windowRegistros.setClosable(true);
		windowRegistros.mostrarWindow(new ArrayList<String>());
		windowRegistros.onConsultarRegistros();
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {

	}

	/**
	 * Este metodo me permite, registrar un paciente y adicionarlo.
	 *
	 */
	public void registrarPaciente() {
		Map<String, Object> map = new HashMap<String, Object>();
		final PacienteAction pacienteAction = (PacienteAction) Executions
				.createComponents("/pages/paciente.zul", this, map);
		pacienteAction.setOnRegistroEvent(new IOnRegistroEvent<Paciente>() {
			@Override
			public void registroGuardado(Paciente t) {
				/**
				 * En esta parte agregamos el paciente
				 *
				 */
				tbxNro_identificacion.seleccionarRegistro(t,
						t.getNro_identificacion(), t.getNombreCompleto());
				seleccionarPaciente(t);
				pacienteAction.detach();
			}
		});
		pacienteAction.getFellow("btNuevo").setVisible(false);
		pacienteAction.getFellow("btCancelar").setVisible(false);
		pacienteAction.setTitle("Registrar paciente");
		pacienteAction.setClosable(true);
		pacienteAction.setWidth("985px");
		// pacienteAction.setHeight("97%");
		pacienteAction.doModal();
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		Citas citas = (Citas) registro;

		String fecha_cita = new SimpleDateFormat("yyyy-MM-dd hh:mm aa")
				.format(citas.getFecha_cita());

		Elemento elementoEstado = citas.getElementoEstado();

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(citas.getNro_identificacion());
		paciente = pacienteService.consultar(paciente);

		Prestadores prestador = new Prestadores();
		prestador.setCodigo_empresa(citas.getCodigo_empresa());
		prestador.setCodigo_sucursal(citas.getCodigo_sucursal());
		prestador.setNro_identificacion(citas.getCodigo_prestador());
		prestador = prestadoresService.consultar(prestador);

		Elemento elemento = new Elemento();
		elemento.setTipo("via_ingreso");
		elemento.setCodigo(citas.getTipo_consulta());
		elemento = elementoService.consultar(elemento);

		return new Object[] {
				citas.getCodigo_cita(),
				fecha_cita,
				citas.getNro_identificacion(),
				(paciente != null ? paciente.getNombreCompleto() : ""),
				(prestador != null ? prestador.getNombres() + " "
						+ prestador.getApellidos() : ""),
				(elemento != null ? elemento.getDescripcion() : ""),
				(elementoEstado != null ? elementoEstado.getDescripcion() : "") };
	}

	public void editarCitas_paciente() throws Exception {
		if (tbxNro_identificacion.getValue().equals("")) {
			MensajesUtil.mensajeAlerta("Alerta!!",
					"Debe seleccionar el paciente");
			return;
		}

		Map<String, Object> parameters = new HashMap();
		Component componente = Executions.createComponents(
				"/pages/control_citas_pacientes.zul", this, parameters);
		final Control_citas_pacientesAction window = (Control_citas_pacientesAction) componente;
		window.setWidth("970px");
		window.setHeight("360px");
		window.setClosable(true);
		window.setMode("modal");
		window.cargarCitaPaciente(tbxNro_identificacion.getValue(), anio);
	}

	public void onSeleccionarRelacion() {
		if (lbxRelacion.getSelectedItem().getValue().toString().equals("8")) {
			tbxOtro_acompaniante.setVisible(true);
		} else {
			tbxOtro_acompaniante.setVisible(false);

		}
	}

	public void onChangeFechaSolicitud() {
		Date fecha_solicitud = dtbxFecha_solicitada.getValue();
		if (fecha_solicitud != null) {
			Calendar calendar_fecha = Calendar.getInstance();
			calendar_fecha.setTime(fecha_solicitud);
			int dia_semana = calendar_fecha.get(Calendar.DAY_OF_WEEK);
			int dia_mes = calendar_fecha.get(Calendar.DAY_OF_MONTH);
			if (dia_semana == Calendar.SATURDAY) {
				calendar_fecha.set(Calendar.DATE, dia_mes + 7);
			} else if (dia_semana == Calendar.SUNDAY) {
				calendar_fecha.set(Calendar.DATE, dia_mes + 6);
			} else if (dia_semana == Calendar.MONDAY) {
				calendar_fecha.set(Calendar.DATE, dia_mes + 5);
			} else if (dia_semana == Calendar.TUESDAY) {
				calendar_fecha.set(Calendar.DATE, dia_mes + 4);
			} else if (dia_semana == Calendar.WEDNESDAY) {
				calendar_fecha.set(Calendar.DATE, dia_mes + 3);
			} else if (dia_semana == Calendar.THURSDAY) {
				calendar_fecha.set(Calendar.DATE, dia_mes + 2);
			} else if (dia_semana == Calendar.FRIDAY) {
				calendar_fecha.set(Calendar.DATE, dia_mes + 1);
			}
			dtbxFecha_final2.setValue(calendar_fecha.getTime());
			dtbxFecha_busqueda.setValue(fecha_solicitud);
			/*
			 * //log.info("10--------->"+tbxCodigo_prestador.getRegistroSeleccionado
			 * ()); if (tbxCodigo_prestador.getRegistroSeleccionado() != null) {
			 * cargarCalendarioCitas(); }
			 */
		} else {
			dtbxFecha_busqueda.setText("");
			dtbxFecha_final2.setText("");
			listboxCitas.getItems().clear();
			listboxLaboratorios.getItems().clear();
		}
	}

	// private String habilitarFiltroEnfermera() {
	// String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
	// .toString();
	// String tipo_cita = lbxTipo_cita.getSelectedItem().getValue().toString();
	// return Utilidades.habilitarFiltroEnfermera(via_ingreso, tipo_cita,
	// getParametros_empresa());
	// }
	public void onCheckAplicaTel(Doublebox dbxTel) {
		if (!chkAplica_tel.isChecked()) {
			dbxTel.setDisabled(true);
			dbxTel.setText("");
		} else {
			dbxTel.setDisabled(false);
		}

	}

}
