/*
 * centro_serviciosAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.controller.HistorialVacunasAction.IVacunacionWindowReceptor;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;
import healthmanager.modelo.service.Manuales_tarifariosService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.VacunasService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IControlesConvinacionesTeclas;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CampoObservacionesPopupMacro;
import com.framework.macros.CampoObservacionesPopupMacro.OnCambioTexto;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.macros.macro_row.BandBoxRowMacro.IConfiguracionBandbox;
import com.framework.macros.manuales_tarifarios.constantes.IManualesConstantes;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;

public class VacunasPaiAction extends ZKWindow implements WindowRegistrosIMG,
		IVacunacionWindowReceptor {

//	private static Logger log = Logger.getLogger(Centro_serviciosAction.class);

	private VacunasService vacunasService;
	private PacienteService pacienteService;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View
	private Textbox tbxNombrePaciente;
	@View
	private Toolbarbutton btnLimpiarPaciente;
	@View
	private Rows rowPacienteGrupalmente;
	@View
	private Rows rowVacunas;
	private final String[] idsExcluyentes = {};

	@View
	Toolbarbutton tbnHistorialVacunacion;

	@View
	Listbox lbxModoEstadistica;

	private List<Articulo> lista_ArticulosVacunas = new ArrayList<Articulo>();

	/**
	 * Este es el modo de adicion de servicios
	 * */
	@View
	Listbox lbxModo;
	@View
	Groupbox grbIndividual;
	@View
	Groupbox grbAgrupado;

	/* listado de guardado */
	private List<Map<String, Object>> lista_datos_paciente;
	private List<String> lista_seleccionados_pacientes = new ArrayList<String>();
	public static final String PACIENTE_VACUNADO = "cent_s";
	public static final String MANUAL_TARIFARIO = "manu_taf";

	private List<Map<String, Object>> lista_datos_vacunas;
	private List<String> lista_seleccionados_vacunas = new ArrayList<String>();

	private List<Esquema_vacunacion> dtt_vacunas_pendiente;
	public static final String VACUNAS = "Uni_f";
	public static final String JERINGA = "Jga";
	public static final String OBSERVACION = "Obs";

	public static final String ESQUEMA_VACUNACION = "esqueVA";

	@Override
	public void init() {
		tbxNro_identificacion
				.inicializar(tbxNombrePaciente, btnLimpiarPaciente);
		lista_datos_paciente = new ArrayList<Map<String, Object>>();
		lista_datos_vacunas = new ArrayList<Map<String, Object>>();
		listarCombos();
		parametrizarBandbox();
		cargarEventoConvinacionTeclas();
		cargarEventoModoAdicion();
		cargarEventoCargarHistorial();
		inicializarListadoJeringas();
	}

	private void inicializarListadoJeringas() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("grupo1", "02");
		map.put("paramTodo", "_");
		map.put("value", "JERINGA");
		lista_ArticulosVacunas = getServiceLocator().getArticuloService()
				.listar(map);
	}

	private void cargarEventoModoAdicion() {
		lbxModo.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws Exception {
				accionCambioModo();
			}
		});
	}

	private void accionCambioModo() {
		boolean estado = (lbxModo.getSelectedIndex() == 0);
		grbIndividual.setVisible(estado);
		grbAgrupado.setVisible(!estado);
	}

	/**
	 * Este metodo me permite realizar un evento dependiedo de la convinacion de
	 * tecla que realice
	 * 
	 * @author Luis Miguel
	 * */
	private void cargarEventoConvinacionTeclas() {
		addEventListener(Events.ON_CTRL_KEY, new EventListener<KeyEvent>() {
			@Override
			public void onEvent(KeyEvent evt) throws Exception {
				//log.info("Key code: " + evt.getKeyCode());
				if (evt.getKeyCode() == IControlesConvinacionesTeclas.ALT_u) {
					if (grbIndividual.isVisible())
						tbxNro_identificacion.setOpen(true);
					else
						abrirWindowPaciente();
				} else if (evt.getKeyCode() == IControlesConvinacionesTeclas.ALT_j) {
					abrirWindowVacunas();
				}
			}
		});
	}

	private void parametrizarBandbox() {
		inicializarBandboxPaciente();
	}

	private void inicializarBandboxPaciente() {
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
								+ " " + registro.getNombre2() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2() + ""));
						listitem.appendChild(listcell);

						String edad = Util.getEdadPrsentacion(registro
								.getFecha_nacimiento());
						listcell = new Listcell();
						listcell.appendChild(new Label(edad + ""));

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
						parametros.put("limite_paginado",
								"limit 25 offset 0");
						// centro_atencionService.setLimit("limit 25 offset 0");

						return pacienteService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						Manuales_tarifarios manuales_tarifarios = getManuales_tarifarios(registro);
						if (manuales_tarifarios == null) {
							MensajesUtil
									.mensajeAlerta("Advertencia",
											"Este paciente no tiene contrado el programa ampliado de inmunizacion");
							return false;
						} else {
							((BandboxRegistrosMacro) bandbox)
									.setObject(cargarPaciente(registro,
											manuales_tarifarios));
							bandbox.setValue(registro.getNro_identificacion());
							textboxInformacion.setValue(registro
									.getNombreCompleto());
							tbnHistorialVacunacion.setVisible(true);
							dtt_vacunas_pendiente = Utilidades
									.getVacunasPendientes(registro,
											getServiceLocator());
							Clients.showNotification(
									"Presione aqui para ver el Historial de vacunacion",
									tbnHistorialVacunacion);
							return true;
						}
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbnHistorialVacunacion.setVisible(false);
					}
				});
	}

	public void cargarEventoCargarHistorial() {
		tbnHistorialVacunacion.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostarHistorialPaciente((Paciente) ((Map<String, Object>) tbxNro_identificacion
								.getObject()).get(PACIENTE_VACUNADO));
					}
				});
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_centro");
		listitem.setLabel("Codigo_centro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_servicios");
		listitem.setLabel("Codigo_servicios");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		lista_datos_paciente.clear();
		lista_datos_vacunas.clear();
		lista_seleccionados_pacientes.clear();
		lista_seleccionados_vacunas.clear();
		rowPacienteGrupalmente.getChildren().clear();
		rowVacunas.getChildren().clear();
		accionCambioModo();
		tbnHistorialVacunacion.setVisible(false);
		lista_ArticulosVacunas.clear();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";

		if (lbxModo.getSelectedIndex() == 0) {
			if (tbxNro_identificacion.getValue().trim().isEmpty()) {
				valida = false;
			}
		} else {
			if (lista_datos_paciente.isEmpty()) {
				valida = false;
				msj = "Para realizar esta accion debe agregar por lo menos 1 centro de salud";
			}
		}

		if (lista_datos_vacunas.isEmpty() && valida) {
			valida = false;
			msj = "Para realizar esta accion debe agregar por lo menos 1 servicio";
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", msj);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			parameters.put("limite_paginado","limit 25 offset 0");

			List<Vacunas> lista_datos = vacunasService.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Vacunas centro_servicios : lista_datos) {
				rowsResultado.appendChild(crearFilas(centro_servicios, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Vacunas centro_servicios = (Vacunas) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(centro_servicios);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(centro_servicios);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //
				cargarmosCentrosValidos();

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("pacientes", lista_datos_paciente);
				params.put("vacunas", lista_datos_vacunas);
				params.put("usuario", usuarios);
				params.put("sucursal", sucursal);
				params.put("empresa", empresa);
				params.put("serviceLocator", getServiceLocator());
				params.put("centro_atencion_session", centro_atencion_session);
				vacunasService.guardar(params);
				Notificaciones.mostrarNotificacionInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/**
	 * Este metodo me permite cargar en la lista cuando se esta utilizando un
	 * solo centro de salud
	 * 
	 * @author Luis Miguel
	 * */
	private void cargarmosCentrosValidos() {
		if (lbxModo.getSelectedIndex() == 0) {
			lista_datos_paciente.clear();
			lista_datos_paciente
					.add((Map<String, Object>) tbxNro_identificacion
							.getObject());
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Vacunas vacunas = (Vacunas) obj;
		try {
			tbxNro_identificacion.setValue(vacunas.getCodigo_procedimiento());
			// tbxCodigo_servicios.setValue(centro_servicios.getCodigo_servicios());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Vacunas centro_servicios = (Vacunas) obj;
		try {
			int result = vacunasService.eliminar(centro_servicios);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminór satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	/* esta parte es de cargado de servicios y de centros de salud */
	public void abrirWindowPaciente() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		// parametros.put("no_rol_medico", "");
		String columnas = "Tipo Ident#65px|Identificacion|Nombres|Apellidos|Edad";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Pacientes", Paquetes.HEALTHMANAGER, "PacienteDao.listar",
				this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_pacientes);
	}

	public void abrirWindowVacunas() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("PARAMETRO_PADRE", IManualesConstantes.CANTIDAD_PADRE);
		String columnas = "Codigo Cups#100px|Descripcion";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Vacunas", Paquetes.HEALTHMANAGER, "VacunasDao.listar", this,
				columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_vacunas);
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			if (registro instanceof Paciente) {
				onSeleccionarPaciente((Paciente) registro);
			} else if (registro instanceof Vacunas) {
				onSeleccionarRegistroVacunas((Vacunas) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if (registro instanceof Paciente) {
			return celdasListitemPacientes((Paciente) registro);
		} else if (registro instanceof Vacunas) {
			return celdasListitemVacunas((Vacunas) registro);
		}
		return null;
	}

	private Object[] celdasListitemVacunas(Vacunas registro) {
		return new Object[] { registro.getCodigo_procedimiento(),
				registro.getDescripcion() };
	}

	public Object[] celdasListitemPacientes(Paciente paciente) {
		String edad = Util.getEdadPrsentacion(paciente.getFecha_nacimiento());
		return new Object[] { paciente.getTipo_identificacion(),
				paciente.getNro_identificacion(),
				paciente.getNombre1() + " " + paciente.getNombre2(),
				paciente.getApellido1() + " " + paciente.getApellido2(), edad };
	}

	// Metodos de seleccion de registros

	public void onSeleccionarPaciente(Paciente paciente) {
		Manuales_tarifarios manuales_tarifarios = getManuales_tarifarios(paciente);
		if (manuales_tarifarios == null) {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"Este paciente no tiene contrado el programa ampliado de inmunizacion");
		} else {
			adicionarDetalleListaPacientes(cargarPaciente(paciente,
					manuales_tarifarios));
			dtt_vacunas_pendiente = Utilidades.getVacunasPendientes(paciente,
					getServiceLocator());
		}
	}

	private Manuales_tarifarios getManuales_tarifarios(Paciente paciente) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", paciente.getCodigo_empresa());
		map.put("codigo_sucursal", paciente.getCodigo_sucursal());
		map.put("codigo_administradora", paciente.getCodigo_administradora());
		map.put("nro_identificacion", paciente.getNro_identificacion());
		map.put("codigo_servicio",
				ServiciosDisponiblesUtils.CODSER_PYP_VACUNACION_PAI);
		return getServiceLocator()
				.getServicio(Manuales_tarifariosService.class)
				.consultar_parametrizado(map);
	}

	private void adicionarDetalleListaPacientes(Map<String, Object> bean) {
		try {
			crearFilasPaciente(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasPaciente(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_paciente.add(bean);
		final Paciente paciente = (Paciente) bean.get(PACIENTE_VACUNADO);

		row.setValue(paciente);

		Cell cell = new Cell();
		Label label = new Label("" + paciente.getTipo_identificacion());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + paciente.getNro_identificacion());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + paciente.getNombre1() + " "
				+ paciente.getNombre2());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + paciente.getApellido1() + " "
				+ paciente.getApellido2());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label(""
				+ new SimpleDateFormat("yyyy-MM-dd").format(paciente
						.getFecha_nacimiento()));
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label(Util.getEdadPrsentacion(paciente
				.getFecha_nacimiento()));
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton("Historial",
				"/images/generar.png");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostarHistorialPaciente(paciente);
					}
				});
		row.appendChild(toolbarbutton);

		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_paciente
															.remove(bean);
													rowPacienteGrupalmente
															.removeChild(row);
												}
											}
										});
					}
				});
		rowPacienteGrupalmente.appendChild(row);
	}

	protected void mostarHistorialPaciente(Paciente paciente) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(HistorialVacunasAction.PARAM_PACIENTE, paciente);
		Window window = (Window) Executions.createComponents(
				"/pages/historial_vacunas.zul", this, param);

		actualizarTamanioComponente(window, "grVacunasPendientes");
		actualizarTamanioComponente(window, "grHistorial");

		window.setWidth("97%");
		// window.setHeight("570px");
		window.setTitle("Historial de vacunacion de "
				+ paciente.getNombreCompleto());
		window.doModal();
	}

	private void actualizarTamanioComponente(Window window,
			String nombre_componente) {
		Component componentHistorial = window.getFellowIfAny(nombre_componente);
		if (componentHistorial != null
				&& componentHistorial instanceof Groupbox) {
			((Groupbox) componentHistorial).setWidth("100%");
		}
	}

	private Map<String, Object> cargarPaciente(Paciente paciente,
			Manuales_tarifarios manuales_tarifarios) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(PACIENTE_VACUNADO, paciente);
		bean.put(MANUAL_TARIFARIO, manuales_tarifarios);
		return bean;
	}

	public void onSeleccionarRegistroVacunas(Vacunas vacunas) {
		boolean existe = false;
		if (dtt_vacunas_pendiente != null)
			for (Esquema_vacunacion esquema_vacunacion : dtt_vacunas_pendiente) {
				if (vacunas.getCodigo_procedimiento().equals(
						esquema_vacunacion.getCodigo_vacuna())) {
					existe = true;
					break;
				}
			}
		if (!existe) {
			adicionarDetalleListaVacunas(cargarVacunas(vacunas, null));
		} else {
			MensajesUtil
					.mensajeAlerta(
							"Advertencia",
							"Esta vacuna esta habilitada por el esquema de vacunacion para este paciente, por favor verifique el esquema de vacunacion");
		}
	}

	private void adicionarDetalleListaVacunas(Map<String, Object> bean) {
		try {
			crearFilasVacunas(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasVacunas(final Map<String, Object> bean) {
		lista_datos_vacunas.add(bean);
		Vacunas vacunas = (Vacunas) bean.get(VACUNAS);

		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(vacunas
				.getCodigo_procedimiento()));
		procedimientos = getServiceLocator().getProcedimientosService()
				.consultar(procedimientos);

		Esquema_vacunacion esquema_vacunacion = (Esquema_vacunacion) bean
				.get(ESQUEMA_VACUNACION);
		final Row row = new Row();

		row.setValue(bean);

		Cell cell = new Cell();
		Label label = new Label("" + procedimientos.getCodigo_cups());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + vacunas.getDescripcion());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		Textbox textbox = new Textbox();
		textbox.setInplace(true);
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");

		if (esquema_vacunacion != null) {
			BandBoxRowMacro bandBoxRowMacro = getBanbBoxReringa(textbox,
					toolbarbutton, bean);
			bandBoxRowMacro.setHflex("1");

			cell = new Cell();
			cell.appendChild(bandBoxRowMacro);
			row.appendChild(cell);
			// accionSeleccionAutomatica(listbox, bean);
		} else {
			bean.put(OBSERVACION, "");
			CampoObservacionesPopupMacro campoObservacionesPopupMacro = new CampoObservacionesPopupMacro(
					this);
			campoObservacionesPopupMacro.setOnCambioTexto(new OnCambioTexto() {
				@Override
				public void texto(String texto) {
					bean.put(OBSERVACION, texto);
				}
			});
			campoObservacionesPopupMacro.setHflex("1");
			cell = new Cell();
			cell.appendChild(campoObservacionesPopupMacro);
			row.appendChild(cell);
		}

		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_vacunas
															.remove(bean);
													rowVacunas.removeChild(row);
												}
											}
										});
					}
				});

		rowVacunas.appendChild(row);
	}

	// private void accionSeleccionAutomatica(Listbox listbox, Map<String,
	// Object> map){
	// listbox.setAttribute(JERINGA, map);
	// listbox.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
	// @Override
	// public void onEvent(Event arg0) throws Exception {
	// Listbox listbox = (Listbox) arg0.getTarget();
	// Listitem listitem = listbox.getSelectedItem();
	// if(listitem != null){
	// Articulo articuloJeringa = listitem.getValue();
	// ((Map<String, Object>)listbox.getAttribute(JERINGA)).put(JERINGA,
	// articuloJeringa);
	// }
	// }
	// });
	// Listitem listitem = listbox.getSelectedItem();
	// if(listitem != null){
	// Articulo articuloJeringa = listitem.getValue();
	// map.put(JERINGA, articuloJeringa)
	// }
	// }

	private BandBoxRowMacro getBanbBoxReringa(Textbox textbox,
			Toolbarbutton toolbarbutton, final Map<String, Object> bean) {
		BandBoxRowMacro bandboxJeringa = new BandBoxRowMacro();
		bandboxJeringa.configurar(new IConfiguracionBandbox<Articulo>() {

			@Override
			public void onSeleccionar(Articulo t, Bandbox bandbox) {
				bean.put(JERINGA, t);
			}

			@Override
			public String getCabecera(Bandbox bandbox) {
				return "código#100px|Nombre";
			}

			@Override
			public String getWidthListBox() {
				return "500px";
			}

			@Override
			public String getHeightListBox() {
				return "300px";
			}

			@Override
			public void renderListitem(Listitem listitem, Articulo registro,
					Bandbox bandbox) {
				listitem.appendChild(new Listcell(registro.getCodigo_articulo()));
				listitem.appendChild(new Listcell(registro.getNombre1()));
			}

			@Override
			public List<Articulo> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros, Bandbox bandbox) {
				parametros.put("grupo1", "02");
				parametros.put("grupo2", "03");
				parametros.put("paramTodo", "_");
				parametros.put("value", "" + valorBusqueda.toUpperCase());
				return getServiceLocator().getArticuloService().listar(
						parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Articulo registro) {
				bean.put(JERINGA, registro);
				bandbox.setValue("" + registro.getCodigo_articulo() + " "
						+ registro.getNombre1());
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				bean.put(JERINGA, null);
			}
		});
		return bandboxJeringa;
	}

	private Map<String, Object> cargarVacunas(Vacunas vacunas,
			Esquema_vacunacion esquema_vacunacion) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(VACUNAS, vacunas);
		bean.put(ESQUEMA_VACUNACION, esquema_vacunacion);
		return bean;
	}

	@Override
	public void vacunasSeleccionadas(
			List<Esquema_vacunacion> esquema_vacunacions) {
		for (Esquema_vacunacion esquema_vacunacionTemp : esquema_vacunacions) {
			if (!isAgregadaVacuna(esquema_vacunacionTemp.getVacunas()))
				adicionarDetalleListaVacunas(cargarVacunas(
						esquema_vacunacionTemp.getVacunas(),
						esquema_vacunacionTemp));
		}
	}

	public boolean isAgregadaVacuna(Vacunas vacunas) {
		for (Map<String, Object> mapVacunas : lista_datos_vacunas) {
			Vacunas vacunasTemp = (Vacunas) mapVacunas.get(VACUNAS);
			if (vacunasTemp.getCodigo_procedimiento().equals(vacunas.getCodigo_procedimiento())) {
				return true;
			}
		}
		return false;
	}
}