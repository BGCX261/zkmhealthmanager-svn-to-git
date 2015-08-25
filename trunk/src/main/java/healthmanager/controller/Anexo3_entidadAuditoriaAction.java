package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Detalle_anexo3;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Negacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.service.Anexo3_entidadService;
import healthmanager.modelo.service.Configuracion_servicios_autorizacionService;
import healthmanager.modelo.service.Detalle_anexo3Service;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.NegacionService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.interfaces.IEventoNegacionAutorizaciones;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.L2HContraintDate;
import com.framework.res.L2HContraintDate.TypeInit;
import com.framework.res.LabelState;
import com.framework.res.Res;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Anexo3_entidadAuditoriaAction extends ZKWindow implements
		IEventoNegacionAutorizaciones {

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

	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;

	@View
	private Textbox tbxTipoId;
	@View
	private Textbox tbxIdentificacionPaciente;
	@View
	private Textbox tbxapellido1Paciente;
	@View
	private Textbox tbxapellido2paciente;
	@View
	private Textbox tbxnombre1Paciente;
	@View
	private Textbox tbxnombre2paciente;
	@View
	private Textbox tbxdirPaciente;
	@View
	private Textbox tbxtelpaciente;
	@View
	private Datebox tbxFechNacpaciente;
	@View
	private Textbox tbxNombre_solicita;
	@View
	private Textbox tbxTelefono;
	@View
	private Textbox tbxCargo;
	@View
	private Textbox tbxCel;
	@View
	private Label lbCantidad;

	@View
	private Rows rowServicios;

	@View
	private Foot footCantidad;
	@View
	private Toolbarbutton tbnGuardar;

	@View
	Listbox lbxSearh;
	@View
	private Timer timer;

	private Map<String, Object> params_negacion = new HashMap<String, Object>();
	private List<Map<String, Object>> listado_detalle = new ArrayList<Map<String, Object>>();

	private String cambio_prestador;

	private Anexo3_entidadService anexo3_entidadService;
	private Anexo3_entidad anexo3Entidad;
	private Paciente paciente_temp;

	@Override
	public void params(Map<String, Object> map) {
		cambio_prestador = (String) map.get("changePrestador");
	}

	public void listarCombos() {
		listarParameter();
		listarTipoBusqueda();
	}

	private void listarTipoBusqueda() {
		lbxSearh.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("Todos");
		lbxSearh.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("N");
		listitem.setLabel("No leidos");
		lbxSearh.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("S");
		listitem.setLabel("Leidos");
		lbxSearh.appendChild(listitem);

		if (lbxSearh.getItemCount() > 0) {
			lbxSearh.setSelectedIndex(0);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("anexo3.codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("anexo3.numero_solicitud");
		listitem.setLabel("Numero Solicitud");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("pac.nro_identificacion ||' '|| pac.nombre1||' '||pac.nombre2 ||' '|| pac.apellido1||' '||pac.apellido2");
		listitem.setLabel("Paciente");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
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

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		FormularioUtil.setUpperCase(groupboxEditar);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar);
		tbnGuardar.setDisabled(false);
		listado_detalle.clear();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		String msj = "";

		if (listado_detalle.isEmpty()) {
			valida = false;
			msj = "Para realizar esta accion debes de tener un servicio a autorizar";
		}

		if (valida) {
			int i = 1;
			for (Map<String, Object> detalle : listado_detalle) {
				Detalle_anexo3 anexo3 = (Detalle_anexo3) detalle
						.get(IConstantesAutorizaciones.PARAM_DETALLE_SERVICIO);
				if (anexo3.getEstado_autorizacion().trim().isEmpty()
						|| anexo3
								.getEstado_autorizacion()
								.equals(IConstantesAutorizaciones.ESTADO_AUTORIZACION_SOLICITUD_NO_AUTORIZADO)) {
					valida = false;
					msj = "Para realizar esta accion debe seleccionar el Estado Aut. en la fila "
							+ i;
					break;
				}
				i++;
			}
		}

		if (!valida) {
			Messagebox
					.show("" + msj, usuarios.getNombres() + " recuerde que...",
							Messagebox.OK, Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			String filtro = lbxSearh.getSelectedItem().getValue() + "";

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paciente", "_");
			parameters.put("fecha_inicio", new Timestamp(dtbxFecha_inicial
					.getValue().getTime()));
			parameters.put("fecha_final", new Timestamp(dtbxFecha_final
					.getValue().getTime()));
			// if(changePrestador == null)parameters.put("need_autorizacion",
			// "S");

			if (!filtro.isEmpty()) {
				parameters.put("leido", filtro);
			}

			getServiceLocator().getAnexo3EntidadService().setLimit(
					"limit 25 offset 0");

			List<Anexo3_entidad> lista_datos = getServiceLocator()
					.getAnexo3EntidadService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Anexo3_entidad anexo3_entidad : lista_datos) {
				rowsResultado.appendChild(crearFilas(anexo3_entidad, this));
			}
			// le quiete esto para cunaod se valla aconsultar no ponga problemas
			// de actualizacion de la tabla
			// gridResultado.setVisible(true);
			// gridResultado.setMold("paging");
			// gridResultado.setPageSize(20);
			//
			// gridResultado.applyProperties();
			// gridResultado.invalidate();
			// gridResultado.setVisible(true);

		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		final Row fila = new Row();

		final Anexo3_entidad anexo3Entidad = (Anexo3_entidad) objeto;

		// log.info("anexo3 entidad>>>>>>>>>>>>>>>" + anexo3Entidad);

		Hbox hbox = new Hbox();
		Space space = new Space();

		String codigo_paciente = anexo3Entidad != null ? anexo3Entidad
				.getCodigo_paciente() : "";

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(sucursal.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(codigo_paciente);
		final Paciente paciente_temp = getServiceLocator().getPacienteService()
				.consultar(paciente);

		// log.info("paciente>>>>>>>" + paciente_temp);

		String nombre = "";
		if (paciente_temp != null) {
			nombre = paciente_temp.getNombre1() + " "
					+ paciente_temp.getNombre2() + " "
					+ paciente_temp.getApellido1() + " "
					+ paciente_temp.getApellido2();
		}

		boolean leido = anexo3Entidad.getLeido().equalsIgnoreCase("N");

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new LabelState(anexo3Entidad.getNumero_solicitud(),
				leido));
		fila.appendChild(new LabelState(new SimpleDateFormat(
				"yyyy-MM-dd hh:mm a").format(anexo3Entidad.getFecha()), leido));
		fila.appendChild(new LabelState(anexo3Entidad.getCodigo_paciente()
				+ " " + nombre, leido));
		fila.appendChild(new LabelState(anexo3Entidad.getNombre_reporta() + "",
				leido));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setVisible(false);
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				imprimir(anexo3Entidad.getCodigo());
				cambiarEstado(anexo3Entidad);
			}
		});
		// hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/atendido.png");
		img.setTooltiptext("Autorizar servicios");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// if(anexo3Entidad.getTipo_anexo().equals("PRO")){
				mostrarDatos(anexo3Entidad, paciente_temp);
				// }else if(anexo3Entidad.getTipo_anexo().equals("MED")){
				// Receta_rips recetaRips = new Receta_rips();
				// recetaRips.setCodigo_empresa(empresa.getCodigo_empresa());
				// recetaRips.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				// recetaRips.setCodigo_receta(anexo3Entidad.getCodigo_receta());
				// recetaRips =
				// getServiceLocator().getReceta_ripsService().consultar(recetaRips);
				// cargarMedicamentos(recetaRips, anexo3Entidad);
				// }
				cambiarEstado(anexo3Entidad);
			}
		});

		if (cambio_prestador == null) {
			hbox.appendChild(space);
			hbox.appendChild(img);
		}

		img = new Image();
		img.setSrc("/images/devolucion.gif");
		img.setTooltiptext("Cambiar de prestador de servicio");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				changePrestadorServicio(anexo3Entidad);
			}
		});
		if ((cambio_prestador + "").equalsIgnoreCase("s")) {
			hbox.appendChild(space);
			hbox.appendChild(img);
		}

		fila.appendChild(hbox);

		return fila;
	}

	protected void mostrarDatos(Anexo3_entidad anexo3Entidad,
			Paciente paciente_temp) {
		this.anexo3Entidad = anexo3Entidad;
		this.paciente_temp = paciente_temp;
		try {
			// Cargamos datos del paciente
			cargarDatosPaciente(paciente_temp);

			// Cargamos datos de solicitante
			tbxNombre_solicita.setValue("" + anexo3Entidad.getNombre_reporta());
			tbxTelefono.setValue("" + anexo3Entidad.getTel_reporta());
			tbxCargo.setValue("" + anexo3Entidad.getCargo_reporta());
			tbxCel.setValue("" + anexo3Entidad.getCel_reporta());

			// Cargamos servicios
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", anexo3Entidad.getCodigo_empresa());
			parametros.put("codigo_sucursal",
					anexo3Entidad.getCodigo_sucursal());
			parametros.put("codigo_orden", anexo3Entidad.getCodigo());
			List<Detalle_anexo3> listado_servicios = getServiceLocator()
					.getServicio(Detalle_anexo3Service.class)
					.listar(parametros);
			cargarServicios(listado_servicios);

			// tbnGuardar.setDisabled(!anexo3Entidad.getEstado().equals(
			// IConstantesAutorizaciones.ESTADO_ANEXO3_ACTIVO));

			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void cargarServicios(List<Detalle_anexo3> listado_servicios) {
		try {
			rowServicios.getChildren().clear();
			for (Detalle_anexo3 detalle_anexo3 : listado_servicios) {
				rowServicios.appendChild(crearFilasServicios(detalle_anexo3));
			}

			if (!rowServicios.getChildren().isEmpty()) {
				footCantidad.setVisible(true);
				lbCantidad.setValue("Total servicios: "
						+ rowServicios.getChildren().size());
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilasServicios(final Detalle_anexo3 detalle_anexo3)
			throws Exception {
		final Row fila = new Row();
		fila.setStyle("text-align: justify;nowrap:nowrap");

		final Map<String, Object> mapContenedorServicio = new HashMap<String, Object>();
		mapContenedorServicio.put(
				IConstantesAutorizaciones.PARAM_DETALLE_SERVICIO,
				detalle_anexo3);
		listado_detalle.add(mapContenedorServicio);

		Configuracion_servicios_autorizacion config = new Configuracion_servicios_autorizacion();
		config.setId(detalle_anexo3.getTipo_servicio());
		config = getServiceLocator().getServicio(
				Configuracion_servicios_autorizacionService.class).consultar(
				config);

		String servicio = " * SERVICIO NO ENCONTRADO * ";
		if (config != null) {
			servicio = config.getNombre();
		}

		Elemento elemento = new Elemento();
		elemento.setCodigo(detalle_anexo3.getEstado_cobro());
		elemento.setTipo("estado_pago");
		elemento = getServiceLocator().getServicio(ElementoService.class)
				.consultar(elemento);

		final Cell cellEstado = new Cell();
		fila.appendChild(cellEstado);
		fila.appendChild(new Label(detalle_anexo3.getCodigo_procedimiento()
				+ ""));
		fila.appendChild(new LabelState(detalle_anexo3.getNombre_pcd() + "", 10));
		fila.appendChild(new LabelState(servicio, 10));
		fila.appendChild(new Label(detalle_anexo3.getUnidades() + ""));
		fila.appendChild(new LabelState(elemento != null ? elemento
				.getDescripcion() : "NO DEFINIDO", 10));

		final Listbox listbox = getListBoxEstado();
		Res.cargarAutomatica(listbox, detalle_anexo3, "estado_autorizacion");
		fila.appendChild(listbox);

		// final boolean activo =
		// anexo3Entidad.getEstado().equals(IConstantesAutorizaciones.ESTADO_ANEXO3_ACTIVO);

		if (detalle_anexo3.getEstado_autorizacion().equals(
				IConstantesAutorizaciones.ESTADO_DETALLE_ANEXO3_NEGADO)) {
			Negacion negacion = new Negacion();
			negacion.setCodigo_empresa(detalle_anexo3.getCodigo_empresa());
			negacion.setCodigo_sucursal(detalle_anexo3.getCodigo_sucursal());
			negacion.setCodigo_servicio(detalle_anexo3
					.getCodigo_procedimiento());
			negacion.setNro_solicitud(detalle_anexo3.getCodigo_orden());
			negacion = getServiceLocator().getServicio(NegacionService.class)
					.consultar(negacion);
			// Cargamos negacion para visualizacion
			mapContenedorServicio.put(IConstantesAutorizaciones.PARAM_NEGACION,
					negacion);
			cambiarEstadoNegacion(true, cellEstado, fila, mapContenedorServicio);
		}

		// listbox.setDisabled(!activo);
		/* eventos */
		cellEstado.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Negacion negacion = (Negacion) mapContenedorServicio
								.get(IConstantesAutorizaciones.PARAM_NEGACION);
						if (negacion != null) {
							params_negacion.clear();
							/*
							 * params_negacion
							 * .put(IConstantesAutorizaciones.PARAM_EDICION_NEGACION
							 * , activo);
							 */
							params_negacion.put(
									IConstantesAutorizaciones.PARAM_NEGACION,
									negacion);
							params_negacion
									.put(IConstantesAutorizaciones.PARAM_FILA_SERVICIO,
											fila);
							params_negacion
									.put(IConstantesAutorizaciones.PARAM_CELL_ESTADO_NEGACION,
											cellEstado);
							params_negacion
									.put(IConstantesAutorizaciones.PARAM_MAPA_DETALLE,
											mapContenedorServicio);
							params_negacion
									.put(IConstantesAutorizaciones.PARAM_LISTBOX_ESTADO,
											listbox);
							abrirNegacion(detalle_anexo3);
						} else {
							Notificaciones.mostrarNotificacionAlerta(
									"Adtervencia",
									"La negacion no a sido encontrada", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
						}
					}
				});

		listbox.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				String estado_seleccionado = listbox.getSelectedItem()
						.getValue();
				if (estado_seleccionado
						.equals(IConstantesAutorizaciones.ESTADO_AUTORIZACION_SOLICITUD_NEGACION)) {
					// solicitamos negacion
					params_negacion.clear();
					params_negacion
							.put(IConstantesAutorizaciones.PARAM_FILA_SERVICIO,
									fila);
					params_negacion
							.put(IConstantesAutorizaciones.PARAM_CELL_ESTADO_NEGACION,
									cellEstado);
					params_negacion.put(
							IConstantesAutorizaciones.PARAM_MAPA_DETALLE,
							mapContenedorServicio);
					params_negacion.put(
							IConstantesAutorizaciones.PARAM_LISTBOX_ESTADO,
							listbox);
					abrirNegacion(detalle_anexo3);
				} else {
					// Una verificacion
					cambiarEstadoNegacion(false, cellEstado, fila,
							mapContenedorServicio);
				}
			}
		});
		return fila;
	}

	public void cambiarEstadoNegacion(boolean estado_negacion, Cell cellEstado,
			Row fila, Map<String, Object> mapa_contenedor_servicio) {
		if (estado_negacion) {
			cellEstado
					.setStyle(IConstantesAutorizaciones.ESTILO_COLOR_NO_AUTORIZADO
							+ "cursor:pointer");
			fila.setTooltiptext("SERVICIO NEGADO");
			Clients.showNotification("seleccione aquí para ver negacion",
					cellEstado);
		} else {
			cellEstado.setStyle(null);
			cellEstado.setAttribute(
					IConstantesAutorizaciones.PARAM_CELL_ESTADO_NEGACION, null);
			fila.setTooltiptext("");
			mapa_contenedor_servicio.put(
					IConstantesAutorizaciones.PARAM_NEGACION, null);
		}
	}

	private void abrirNegacion(Detalle_anexo3 detalle_anexo3) {
		Component componente = Executions.createComponents(
				"/pages/negacion.zul", this, params_negacion);
		final Window ventana = (Window) componente;
		// ventana.setTitle(detalle_anexo3 != null ?
		// (detalle_anexo3.getNombre_pcd() + "") : "NEGACION DE SERVICIO");
		ventana.setTitle("");
		ventana.setBorder(false);
		ventana.setWidth("100%");
		ventana.setHeight("100%");
		ventana.doModal();
	}

	private Listbox getListBoxEstado() {
		Listbox listbox = new Listbox();
		listbox.setMold("select");
		listbox.setHflex("1");
		listbox.setName(IConstantesAutorizaciones.ESTADO_AUTORIZACION_ELEMENTO_TIPO);
		Utilidades.listarElementoListbox(listbox, true, getServiceLocator());
		return listbox;
	}

	private void cargarDatosPaciente(Paciente paciente) {
		if (paciente != null) {
			tbxnombre1Paciente.setValue("" + paciente.getNombre1());
			tbxnombre2paciente.setValue("" + paciente.getNombre2());
			tbxapellido1Paciente.setValue("" + paciente.getApellido1());
			tbxapellido2paciente.setValue("" + paciente.getApellido2());
			tbxTipoId.setValue("" + paciente.getTipo_identificacion());
			tbxIdentificacionPaciente.setValue(""
					+ paciente.getNro_identificacion());
		} else {
			tbxnombre1Paciente.setValue("");
			tbxnombre2paciente.setValue("");
			tbxapellido1Paciente.setValue("");
			tbxapellido2paciente.setValue("");
			tbxTipoId.setValue("");
			tbxIdentificacionPaciente.setValue("");
		}
	}

	protected void changePrestadorServicio(Anexo3_entidad anexo3Entidad)
			throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("Anexo3", anexo3Entidad);

		Component componente = Executions.createComponents(
				"/pages/cambio_prestador_anexo3.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setWidth("100%");
		window.setHeight("100%");
		window.doModal();
	}

	protected void cargarMedicamentos(Receta_rips recetaRips,
			Anexo3_entidad anexo3Entidad) throws Exception {

		Map params = new HashMap();
		params.put("receta", recetaRips);
		params.put("auditor", true);
		params.put("anexo", anexo3Entidad);

		Component componente = Executions.createComponents(
				"/pages/farmacia_recetas_rips_medicamentos.zul", this, params);
		final Window ventana = (Window) componente;
		ventana.setWidth("90%");
		ventana.setHeight("90%");
		ventana.setVflex("1");
		ventana.doModal();
	}

	private void cambiarEstado(Anexo3_entidad anexo3Entidad) {
		anexo3Entidad.setLeido("S");
		getServiceLocator().getAnexo3EntidadService().actualizar(anexo3Entidad);
	}

	protected void cargarProcedimeintos(Anexo3_entidad anexo3Entidad)
			throws Exception {

		Map params = new HashMap();
		params.put("anexo3", anexo3Entidad);

		Component componente = Executions.createComponents(
				"/pages/negacion_anexo3.zul", this, params);
		final Window ventana = (Window) componente;
		ventana.setWidth("90%");
		ventana.setHeight("90%");
		ventana.setVflex("1");
		ventana.doModal();
	}

	public void imprimir(String codigo) throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Anexo3");
		paramRequest.put("codigo", codigo);

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				Messagebox
						.show("Esta seguro que desea realizar esta guardar esta autorizacion",
								"Eliminar Registro",
								Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener<Event>() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											Map<String, Object> map = new HashMap<String, Object>();
											map.put(IConstantesAutorizaciones.PARAM_DETALLE_SERVICIO,
													listado_detalle);
											map.put(IConstantesAutorizaciones.PARAM_ANEXO_3,
													anexo3Entidad);
											map.put(IConstantesAutorizaciones.PARAM_PACIENTE,
													paciente_temp);
											map.put(IConstantesAutorizaciones.PARAM_USUARIO,
													usuarios);
											map.put(IConstantesAutorizaciones.PARAM_ROL,
													rol_usuario);
											anexo3_entidadService
													.guardarAutorizacion(map);
											MensajesUtil
													.mensajeInformacion(
															"Informacion",
															"Registro de autorizacion realizado satisfactoriamente");
											tbnGuardar.setDisabled(true);
										}
									}
								});
			}
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		// Anexo3_entidad anexo4_entidad = (Anexo3_entidad)obj;
		// try{
		//
		// //Mostramos la vista //
		// tbxAccion.setText("modificar");
		// accionForm(true, tbxAccion.getText());
		// }catch (Exception e) {
		//
		// log.error(e.getMessage(), e);
		// Messagebox.show("Este dato no se puede editar",
		// "Error !!", Messagebox.OK, Messagebox.ERROR);
		// }
	}

	public void eliminarDatos(Object obj) throws Exception {
		Anexo3_entidad anexo4_entidad = (Anexo3_entidad) obj;
		try {
			int result = getServiceLocator().getAnexo3EntidadService()
					.eliminar(anexo4_entidad);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {

			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@Override
	public void init() throws Exception {
		listarCombos();
		dtbxFecha_inicial.setValue(L2HContraintDate.initFechaInHHMMSS(Calendar
				.getInstance().getTime(), TypeInit.Init00_00_00));
		dtbxFecha_final.setValue(L2HContraintDate.initFechaInHHMMSS(Calendar
				.getInstance().getTime(), TypeInit.end23_59_59));
		try {
			buscarDatos();
		} catch (Exception e) {
		}
	}

	@Override
	public void OnAgregarNegacion(ZKWindow window, Negacion negacion,
			Map<String, Object> params) {
		Map<String, Object> mapa_contenedor_servicio = (Map<String, Object>) params
				.get(IConstantesAutorizaciones.PARAM_MAPA_DETALLE);
		Cell cellEstado = (Cell) params
				.get(IConstantesAutorizaciones.PARAM_CELL_ESTADO_NEGACION);
		Row row = (Row) params
				.get(IConstantesAutorizaciones.PARAM_FILA_SERVICIO);
		if (cellEstado != null && row != null
				&& mapa_contenedor_servicio != null) {
			mapa_contenedor_servicio.put(
					IConstantesAutorizaciones.PARAM_NEGACION, negacion);
			cambiarEstadoNegacion(true, cellEstado, row,
					mapa_contenedor_servicio);
			window.detach();
		} else {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"Faltan parametros para cargar la autorizacion", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		}
	}

	@Override
	public void OnCerrarNegacion(ZKWindow window, Map<String, Object> params) {
		// window.detach();
	}

	@Override
	public void OnCancelarNegacion(ZKWindow window, Map<String, Object> params) {
		Map<String, Object> mapa_contenedor_servicio = (Map<String, Object>) params
				.get(IConstantesAutorizaciones.PARAM_MAPA_DETALLE);
		Cell cellEstado = (Cell) params
				.get(IConstantesAutorizaciones.PARAM_CELL_ESTADO_NEGACION);
		Row row = (Row) params
				.get(IConstantesAutorizaciones.PARAM_FILA_SERVICIO);
		Listbox listboxEstado = (Listbox) params
				.get(IConstantesAutorizaciones.PARAM_LISTBOX_ESTADO);
		if (cellEstado != null && row != null
				&& mapa_contenedor_servicio != null) {
			cambiarEstadoNegacion(false, cellEstado, row,
					mapa_contenedor_servicio);
			listboxEstado.setSelectedIndex(0);
		}
		params.clear();
		window.detach();
	}
}
