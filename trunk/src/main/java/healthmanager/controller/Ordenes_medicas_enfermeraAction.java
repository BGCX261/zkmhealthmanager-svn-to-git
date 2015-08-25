/**
 * 
 */
package healthmanager.controller;

import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Hoja_gastos;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Ordenes_medicas;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.service.Hoja_gastosService;
import healthmanager.modelo.service.Ordenes_medicasService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

/**
 * @author ferney
 * 
 */
public class Ordenes_medicas_enfermeraAction extends ZKWindow {
	private static Logger log = Logger
			.getLogger(Ordenes_medicas_enfermeraAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private static final String ENTREGADOS_TOTALMENTE = "1";
	private static final String ENTREGADOS_PARCIALMENTE = "2";
	private static final String NO_ENTREGADOS = "3";

	public SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd");

	@View
	private Groupbox groupboxConsultar;

	@View
	private Borderlayout borderlayoutEditar;

	@View
	private Div divEditar;

	@View
	private Textbox tbxAccion;

	@View
	private Listbox listboxOrdenes_medicas;

	@View
	private Datebox dtbxFecha_orden_b;

	@View
	private Textbox tbxValue;

	@View
	private Label labelCodigo_orden;

	@View
	private Textbox tbxCodigo_orden_m;

	@View
	private Datebox dtbxFecha_orden_m;

	@View
	private Textbox tbxInformacion_paciente;

	@View
	private Textbox tbxObservacion_medica;

	@View
	private Div divModuloOrdenamiento;

	private Orden_servicio_internoAction orden_servicioAction;

	@View
	private Div divModuloFarmacologico;

	private Receta_rips_internoAction receta_ripsAction;

	@View
	private Toolbarbutton toolbarbuttonHoja_gasto;

	@View
	private Toolbarbutton toolbarbuttonRealizacion_procedimientos;
	@View
	private Toolbarbutton btimprimir;

	@View
	private Toolbarbutton toolbarbuttonEntrega_medicamento;

	@View
	private Listbox lbxEstados_ordenamientos;

	@View
	private Listbox lbxEstados_medicamentos;

	@SuppressWarnings("unused")
	private String codigo_orden;

	private Admision admision_seleccionada;
	private Ordenes_medicas ordenes_medicas;

	private Receta_rips receta_rips_current;

	// private Orden_servicio orden_servicio_current;

	@Override
	public void params(Map<String, Object> map) {

	}

	public void initNota_enfermeria() throws Exception {

	}

	public void cargarPrestador() throws Exception {

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsultar.setVisible(!sw);
		borderlayoutEditar.setVisible(sw);
		if (!accion.equalsIgnoreCase("registrar")) {
			FormularioUtil.limpiarComponentes(divEditar,
					new String[] { "dtbxFecha_orden_b" });
			tbxCodigo_orden_m.setVisible(true);
			labelCodigo_orden.setVisible(true);
		} else {
			tbxCodigo_orden_m.setVisible(false);
			labelCodigo_orden.setVisible(false);
			onMostrarModuloFarmacologico(null, null);
			onMostrarModuloOrdenamiento(null, null);
			dtbxFecha_orden_m.setValue(new Date());
		}

		tbxAccion.setValue(accion);

	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {

	}

	@Override
	public void init() throws Exception {

	}

	public void onMostrarModuloFarmacologico(String codigo_receta,
			Admision admision) throws Exception {
		if (receta_ripsAction != null)
			receta_ripsAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_receta", codigo_receta);
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("estado", admision.getEstado());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("id_plan", admision.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");
		parametros.put("mostrarEstado", "mostrarEstado");

		parametros.put("ocultarRecomendaciones", "_Ocultar");

		receta_ripsAction = (Receta_rips_internoAction) Executions
				.createComponents("/pages/receta_rips_interno.zul", null,
						parametros);
		receta_ripsAction.inicializar(this);
		divModuloFarmacologico.appendChild(receta_ripsAction);

	}

	public void onMostrarModuloOrdenamiento(String codigo_orden,
			Admision admision) throws Exception {
		if (orden_servicioAction != null)
			orden_servicioAction.detach();
		// if (!cargo_farmacologico) {
		//log.info("creando la ventana orden_servicioAction");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_orden", codigo_orden);
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("estado", admision.getEstado());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("id_plan", admision.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");
		parametros.put("mostrarEstado", "mostrarEstado");
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		// cargo_farmacologico = true;
		// }

	}

	public void buscarDatos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_centro",
				centro_atencion_session.getCodigo_centro());
		if (dtbxFecha_orden_b.getValue() != null)
			parametros.put("fecha_orden", dtbxFecha_orden_b.getValue());

		if (!tbxValue.getValue().trim().isEmpty()) {
			parametros.put("parametro_todo", tbxValue.getValue().trim()
					.toUpperCase());
		}

		List<String> tipos_ordenes = new ArrayList<String>();
		tipos_ordenes.add(IConstantes.TIPO_ORDEN_URGENCIA);
		tipos_ordenes.add(IConstantes.TIPO_ORDEN_HOSPITALIZACION);

		parametros.put("tipos_ordenes", tipos_ordenes);

		String estado_ordenamientos = lbxEstados_ordenamientos
				.getSelectedItem().getValue().toString();
		String estado_medicamentos = lbxEstados_medicamentos.getSelectedItem()
				.getValue().toString();

		List<Ordenes_medicas> listado_ordenes = getServiceLocator()
				.getServicio(Ordenes_medicasService.class).listar(parametros);

		listboxOrdenes_medicas.getItems().clear();

		for (Ordenes_medicas ordenes_medicas : listado_ordenes) {
			Orden_servicio orden_servicio = null;
			Receta_rips receta_rips = null;

			if (ordenes_medicas.getCodigo_orden_servicio() != null) {
				orden_servicio = new Orden_servicio();
				orden_servicio.setCodigo_empresa(codigo_empresa);
				orden_servicio.setCodigo_sucursal(codigo_sucursal);
				orden_servicio
						.setId(ordenes_medicas.getCodigo_orden_servicio());
				orden_servicio = getServiceLocator().getOrden_servicioService()
						.consultar(orden_servicio);
			}

			if (ordenes_medicas.getCodigo_receta() != null
					&& !ordenes_medicas.getCodigo_receta().isEmpty()) {
				receta_rips = new Receta_rips();
				receta_rips.setCodigo_empresa(codigo_empresa);
				receta_rips.setCodigo_sucursal(codigo_sucursal);
				receta_rips
						.setCodigo_receta(ordenes_medicas.getCodigo_receta());
				receta_rips = getServiceLocator().getReceta_ripsService()
						.consultar(receta_rips);
			}

			boolean crear_fila = true;

			if (!estado_ordenamientos.isEmpty()) {
				if (orden_servicio != null) {
					List<Detalle_orden> listado_detalles = orden_servicio
							.getLista_detalle();
					if (estado_ordenamientos.equals(ENTREGADOS_TOTALMENTE)) {
						for (Detalle_orden detalle_orden : listado_detalles) {
							if (detalle_orden.getUnidades().compareTo(
									detalle_orden.getUnidades_realizadas()) != 0) {
								crear_fila = false;
								break;
							}
						}
					} else if (estado_ordenamientos
							.equals(ENTREGADOS_PARCIALMENTE)) {
						for (Detalle_orden detalle_orden : listado_detalles) {
							if (!(detalle_orden.getUnidades().compareTo(
									detalle_orden.getUnidades_realizadas()) != 0 && detalle_orden
									.getUnidades_realizadas() > 0)) {
								crear_fila = false;
								break;
							}
						}
					} else if (estado_ordenamientos.equals(NO_ENTREGADOS)) {
						for (Detalle_orden detalle_orden : listado_detalles) {
							if (detalle_orden.getUnidades_realizadas() != 0) {
								crear_fila = false;
								break;
							}
						}
					}
				}
			}

			if (!estado_medicamentos.isEmpty() && crear_fila) {
				if (receta_rips != null) {
					List<Detalle_receta_rips> listado_detalles = receta_rips
							.getLista_detalle();
					if (estado_medicamentos.equals(ENTREGADOS_TOTALMENTE)) {
						for (Detalle_receta_rips detalle_receta_rips : listado_detalles) {
							if (detalle_receta_rips.getCantidad_recetada() != detalle_receta_rips
									.getCantidad_entregada()) {
								crear_fila = false;
								break;
							}
						}
					} else if (estado_medicamentos
							.equals(ENTREGADOS_PARCIALMENTE)) {
						for (Detalle_receta_rips detalle_receta_rips : listado_detalles) {
							if (!(detalle_receta_rips.getCantidad_recetada() != detalle_receta_rips
									.getCantidad_entregada() && detalle_receta_rips
									.getCantidad_entregada() > 0)) {
								crear_fila = false;
								break;
							}
						}
					} else if (estado_medicamentos.equals(NO_ENTREGADOS)) {
						for (Detalle_receta_rips detalle_receta_rips : listado_detalles) {
							if (detalle_receta_rips.getCantidad_entregada() != 0) {
								crear_fila = false;
								break;
							}
						}
					}
				}
			}

			if (crear_fila) {
				listboxOrdenes_medicas.appendChild(crearFila(ordenes_medicas,
						orden_servicio, receta_rips));
			}
		}

	}

	public Listitem crearFila(final Ordenes_medicas ordenes_medicas,
			Orden_servicio orden_servicio, Receta_rips receta_rips) {
		Admision admision = new Admision();
		admision.setCodigo_empresa(codigo_empresa);
		admision.setCodigo_sucursal(codigo_sucursal);
		admision.setNro_ingreso(ordenes_medicas.getNro_ingreso());
		admision.setNro_identificacion(ordenes_medicas.getNro_documento());
		admision = getServiceLocator().getAdmisionService().consultar(admision);

		// //log.info("admision ===> " + admision);

		final Listitem listitem = new Listitem();
		listitem.appendChild(new Listcell(ordenes_medicas.getCodigo_orden()));
		String tipo_orden = "";
		if (ordenes_medicas.getTipo().equals(IConstantes.TIPO_ORDEN_URGENCIA)) {
			tipo_orden = "URGENCIAS";
		} else if (ordenes_medicas.getTipo().equals(
				IConstantes.TIPO_ORDEN_HOSPITALIZACION)) {
			tipo_orden = "HOSPITALIZACION";
		}
		listitem.appendChild(new Listcell(tipo_orden));
		listitem.appendChild(new Listcell(formato_fecha.format(ordenes_medicas
				.getFecha_orden())));

		if (orden_servicio != null) {
			listitem.appendChild(new Listcell(
					orden_servicio != null ? orden_servicio.getLista_detalle()
							.size() + "" : ""));
		} else {
			listitem.appendChild(new Listcell(""));
		}

		if (receta_rips != null) {
			listitem.appendChild(new Listcell(receta_rips != null ? receta_rips
					.getLista_detalle().size() + "" : ""));
		} else {
			listitem.appendChild(new Listcell(""));
		}

		listitem.appendChild(new Listcell(admision.getPaciente()
				.getNro_identificacion()
				+ " "
				+ admision.getPaciente().getNombreCompleto()));

		Listcell listcell = new Listcell();
		Hlayout hlayout = new Hlayout();
		hlayout.setValign("middle");

		Toolbarbutton toolbarbutton_mostrar = new Toolbarbutton("Mostrar",
				"/images/mostrar_info.png");
		toolbarbutton_mostrar
				.setTooltiptext("Mostrar informacion de la orden medica");

		final Orden_servicio orden_servicio2 = orden_servicio;
		final Receta_rips receta_rips2 = receta_rips;
		final Admision admision2 = admision;

		toolbarbutton_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						mostrarDatos(ordenes_medicas, orden_servicio2,
								receta_rips2, admision2);
					}

				});

		hlayout.appendChild(toolbarbutton_mostrar);
		listcell.appendChild(hlayout);

		listitem.appendChild(listcell);

		return listitem;
	}

	public void mostrarDatos(Ordenes_medicas ordenes_medicas,
			Orden_servicio orden_servicio, Receta_rips receta_rips,
			Admision admision) throws Exception {
		accionForm(true, OpcionesFormulario.MOSTRAR.toString());
		toolbarbuttonRealizacion_procedimientos.setVisible(false);
		btimprimir.setVisible(true);
		toolbarbuttonHoja_gasto.setVisible(false);
		toolbarbuttonEntrega_medicamento.setVisible(false);
		tbxInformacion_paciente.setValue(admision.getNro_identificacion()
				+ " - " + admision.getPaciente().getNombreCompleto());
		tbxCodigo_orden_m.setValue(ordenes_medicas.getCodigo_orden());
		dtbxFecha_orden_m.setValue(ordenes_medicas.getFecha_orden());
		tbxObservacion_medica.setValue(ordenes_medicas
				.getObservaciones_medico());

		admision_seleccionada = admision;

		// orden_servicio_current = orden_servicio;

		receta_rips_current = receta_rips;

		mostrarInformacionRecetaRips(receta_rips, false);

		mostrarInformacionOrdenServicio(orden_servicio, false);

		toolbarbuttonHoja_gasto.setVisible(true);
		this.ordenes_medicas = ordenes_medicas;

	}

	public void mostrarInformacionRecetaRips(Receta_rips receta_rips,
			boolean consultar) throws Exception {
		if (consultar) {
			receta_rips = getServiceLocator().getReceta_ripsService()
					.consultar(receta_rips);
		}
		if (receta_rips != null) {
			onMostrarModuloFarmacologico(receta_rips.getCodigo_receta(),
					admision_seleccionada);
			// receta_ripsAction.getFootIndicacionRecomendaciones().setVisible(false);
			toolbarbuttonEntrega_medicamento.setVisible(true);
		} else {
			if (receta_ripsAction != null)
				receta_ripsAction.detach();
		}

		FormularioUtil.deshabilitarComponentes(divEditar, true, new String[] {
				"toolbarbuttonEntrega_medicamento",
				"toolbarbuttonRealizacion_procedimientos",
				"toolbarbuttonHoja_gasto", "btimprimir" });
	}

	public void mostrarInformacionOrdenServicio(Orden_servicio orden_servicio,
			boolean consultar) throws Exception {
		if (consultar) {
			orden_servicio = getServiceLocator().getOrden_servicioService()
					.consultar(orden_servicio);
		}
		if (orden_servicio != null) {
			onMostrarModuloOrdenamiento(orden_servicio.getCodigo_orden(),
					admision_seleccionada);
			toolbarbuttonRealizacion_procedimientos.setVisible(true);
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);
		} else {
			if (orden_servicioAction != null)
				orden_servicioAction.detach();
		}
	}

	public void mostrarHojaGastos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("admision_seleccionada", admision_seleccionada);
		Hoja_gastosAction hoja_gastosAction = (Hoja_gastosAction) Executions
				.createComponents("/pages/hoja_gastos.zul", this, parametros);
		hoja_gastosAction.setWidth("750px");
		hoja_gastosAction.setHeight("420px");
		hoja_gastosAction.setTitle("HOJA DE GASTOS DE "
				+ admision_seleccionada.getPaciente().getNombreCompleto());
		hoja_gastosAction.setClosable(true);
		hoja_gastosAction.doModal();
	}

	public void imprimir() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("admision_seleccionada", admision_seleccionada);

		Hoja_gastos hoja_gastos = new Hoja_gastos();
		hoja_gastos.setCodigo_empresa(codigo_empresa);
		hoja_gastos.setCodigo_sucursal(codigo_sucursal);
		hoja_gastos.setNro_identificacion(admision_seleccionada
				.getNro_identificacion());
		hoja_gastos.setNro_ingreso(admision_seleccionada.getNro_ingreso());

		hoja_gastos = getServiceLocator().getServicio(Hoja_gastosService.class)
				.consultar(hoja_gastos);

		if (hoja_gastos != null) {
			btimprimir.setVisible(true);
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "Hoja_gastos");
			paramRequest.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			paramRequest.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			paramRequest.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			paramRequest.put("codigo_empresa", codigo_empresa);
			paramRequest.put("codigo_sucursal", codigo_sucursal);

			//log.info("===> parametros" + paramRequest);

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			MensajesUtil
					.mensajeAlerta("Hoja de gasto",
							"No se ha generado la hoja de gasto por que no se ha guardado");
		}
	}

	public void mostrarEntregaMedicamentos() {
		if (receta_rips_current != null) {
			Map params = new HashMap();
			params.put("receta", receta_rips_current);
			params.put("auditor", false);
			params.put("paga_copago", false);

			Component componente = Executions.createComponents(
					"/pages/farmacia_recetas_rips_medicamentos.zul", this,
					params);
			final Window ventana = (Window) componente;
			ventana.setWidth("920px");
			ventana.setHeight("420px");
			ventana.setTitle("Entrega de medicamentos de "
					+ admision_seleccionada.getPaciente().getNombreCompleto());
			ventana.setClosable(true);
			ventana.doModal();
		}
	}

	public void mostrarRealizacionProcedimientos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("admision_seleccionada", admision_seleccionada);
		parametros.put("codigo_orden_servicio",
				ordenes_medicas.getCodigo_orden_servicio());
		Realizacion_procedimientosAction realizacion_procedimientosAction = (Realizacion_procedimientosAction) Executions
				.createComponents("/pages/realizacion_procedimientos.zul",
						this, parametros);
		realizacion_procedimientosAction.setWidth("750px");
		realizacion_procedimientosAction.setHeight("420px");
		realizacion_procedimientosAction
				.setTitle("Procedimientos Realizados de "
						+ admision_seleccionada.getPaciente()
								.getNombreCompleto());
		realizacion_procedimientosAction.setClosable(true);
		realizacion_procedimientosAction.doModal();
	}

	public boolean validarForm() throws Exception {
		boolean validar = true;
		FormularioUtil.validarCamposObligatorios(tbxObservacion_medica);
		validar = receta_ripsAction.validarFormExterno();
		if (validar)
			validar = orden_servicioAction.validarFormExterno();

		if (validar) {
			int total_medicamentos = receta_ripsAction.getTotalDetalles();
			int total_ordenamientos = orden_servicioAction.getTotalDetalles();

			if (total_medicamentos == 0 && total_ordenamientos == 0) {
				validar = false;
			}
		}

		if (!validar) {
			MensajesUtil
					.mensajeAlerta(
							"No hay datos para guardar la orden medica",
							"Para guardar una orden medica se necesita por lo menos ordenar un medicamente o un procedimiento");
		}

		return validar;
	}

	public void guardarDatos() {
		try {
			if (validarForm()) {

			}
		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			} else {
				log.error(((WrongValueException) e).getComponent().getId()
						+ " esta presentando error", e);
			}
		}
	}

}
