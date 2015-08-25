/**
 * 
 */
package healthmanager.controller;

import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Ordenes_medicas;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.service.Ordenes_medicasService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

/**
 * @author ferney
 * 
 */
public class Ordenes_medicasAction extends ZKWindow {
	private static Logger log = Logger.getLogger(Ordenes_medicasAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	public SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd");

	private Admision admision_seleccionada;

	@View
	private Borderlayout borderlayoutEditar;

	@View
	private Toolbarbutton btGuardar;

	@View
	private Div divEditar;

	@View
	private Textbox tbxAccion;

	@View
	private Label labelCodigo_orden;

	@View
	private Textbox tbxCodigo_orden_m;

	@View
	private Datebox dtbxFecha_orden_m;

	// @View
	// private Textbox tbxObservacion_medica;

	@View
	private Div divModuloOrdenamiento;

	private Orden_servicio_internoAction orden_servicioAction;

	@View
	private Div divModuloFarmacologico;

	@View
	private Textbox tbxCodigo_prestador_om;

	@View
	private Auxheader auxhModulo;

	private Receta_rips_internoAction receta_ripsAction;

	private String codigo_orden;

	private String tipo;

	@View
	private Groupbox groupboxConsultar;

	@View
	private Listbox lbxAdmisiones;

	@View
	private Toolbarbutton toolbarbuttonNueva_orden_hospitalizacion;

	@View
	private Toolbarbutton toolbarbuttonNueva_orden_urgencia;

	@View
	private Toolbarbutton toolbarbuttonNueva_receta;

	@View
	private Listbox listboxOrdenes_medicas;

	@Override
	public void params(Map<String, Object> map) {
		admision_seleccionada = (Admision) map
				.get(IVias_ingreso.ADMISION_PACIENTE);
		if (admision_seleccionada != null) {
			tbxCodigo_prestador_om.setValue(admision_seleccionada
					.getCodigo_medico());
		} else {
			tbxCodigo_prestador_om.setValue(IConstantes.CODIGO_MEDICO_DEFECTO);
		}
	}

	public void initNota_enfermeria() throws Exception {

	}

	public void cargarPrestador() throws Exception {

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsultar.setVisible(!sw);
		borderlayoutEditar.setVisible(sw);
		if (accion.equalsIgnoreCase("registrar")) {
			tbxCodigo_orden_m.setVisible(false);
			labelCodigo_orden.setVisible(false);
			dtbxFecha_orden_m.setValue(new Date());
			btGuardar.setDisabled(false);
		} else {
			FormularioUtil.limpiarComponentes(divEditar,
					new String[] { "tbxCodigo_prestador" });
			tbxCodigo_orden_m.setVisible(true);
			labelCodigo_orden.setVisible(true);
		}
		recargarAdmisiones();
		tbxAccion.setValue(accion);
	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {

	}

	@Override
	public void init() throws Exception {
		listarAdmisiones();
		onSeleccionarAdmision();
		recargarAdmisiones();
	}

	public void onMostrarModuloFarmacologico(String codigo_receta,
			String nro_ingreso, String tipo) throws Exception {
		if (receta_ripsAction != null)
			receta_ripsAction.detach();
		// log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("codigo_receta", codigo_receta);
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso);
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
		parametros.put("opcion_formulario", tbxAccion.getValue());

		/* este parametros es para que oculte las vistas */

		parametros.put("mensajeBotonImprimir", "Imprimir");

		if (tipo.equals(IConstantes.TIPO_ORDEN_HOSPITALIZACION)
				|| tipo.equals(IConstantes.TIPO_ORDEN_AMBULATORIA)) {
			parametros.put("valorInicial", 0d);
			parametros.put("mostrarDosis", "mostrarDosis");
			parametros.put("mostrarDuracion", "mostrarDuracion");
			parametros.put("mostrarCantidad", "mostrarCantidad");
		} else {
			parametros.put("valorInicial", 1d);
			// parametros.put("ocultarDosis", "ocultarDosis");
			// parametros.put("ocultarDuracion", "ocultarDuracion");
			// parametros.put("ocultarCantidad", "ocultarCantidad");
		}

		parametros.put("ocultarInformacion", "_Ocultar");
		parametros.put("mostrarEstado", "mostrarEstado");
		// parametros.put("ocultarRecomendaciones", "ocultar");
		receta_ripsAction = (Receta_rips_internoAction) Executions
				.createComponents("/pages/receta_rips_interno.zul", null,
						parametros);
		receta_ripsAction.inicializar(this);
		divModuloFarmacologico.appendChild(receta_ripsAction);
	}

	public void onMostrarModuloOrdenamiento(String codigo_orden,
			String nro_ingreso, String tipo) throws Exception {
		if (orden_servicioAction != null)
			orden_servicioAction.detach();
		// if (!cargo_farmacologico) {
		// log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("codigo_orden", codigo_orden);
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso);
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
		parametros.put("tipo", tipo);
		if (tipo.equals(IConstantes.TIPO_ORDEN_HOSPITALIZACION)) {
			parametros.put("valorInicial", 1d);
			// parametros.put("ocultarValor", "ocultarValor");
			// parametros.put("ocultarCantidad", "ocultarCantidad");
		} else {
			parametros.put("valorInicial", 0d);
			parametros.put("mostarValor", "mostarValor");
			parametros.put("mostarCantidad", "mostarCantidad");
		}

		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");
		parametros.put("mostrarEstado", "mostrarEstado");
		// parametros.put("ocultarRecomendaciones", "ocultar");
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
	}

	public void mostrarDatos(Ordenes_medicas ordenes_medicas,
			Orden_servicio orden_servicio, Receta_rips receta_rips,
			Admision admision) throws Exception {
		try {
			accionForm(true, OpcionesFormulario.MOSTRAR.toString());

			if (ordenes_medicas != null) {
				tbxCodigo_orden_m.setValue(ordenes_medicas.getCodigo_orden());
				dtbxFecha_orden_m.setValue(ordenes_medicas.getFecha_orden());
				// tbxObservacion_medica.setValue(ordenes_medicas.getObservaciones_medico());
				tbxCodigo_prestador_om.setValue(ordenes_medicas
						.getCodigo_medico());

				// tipo = ordenes_medicas.getTipo();
				// if (tipo == null) {
				// tipo = IConstantes.TIPO_ORDEN_AMBULATORIA;
				// }

			} else {
				tbxCodigo_orden_m.setValue("Ambulatorio "
						+ admision.getNro_ingreso());
				dtbxFecha_orden_m.setValue(admision.getFecha_ingreso());
				// tbxObservacion_medica.setValue("Orden medica realizada desde una consulta ambulatoria");
				tbxCodigo_prestador_om.setValue(admision.getCodigo_medico());
				divModuloFarmacologico.setVisible(true);
				divModuloOrdenamiento.setVisible(true);
			}

			if (orden_servicio != null) {
				divModuloOrdenamiento.setVisible(true);
				onMostrarModuloOrdenamiento(orden_servicio.getCodigo_orden(),
						admision.getNro_ingreso(), tipo);
			} else {
				if (orden_servicioAction != null)
					orden_servicioAction.detach();
			}

			if (receta_rips != null) {
				divModuloFarmacologico.setVisible(true);
				onMostrarModuloFarmacologico(receta_rips.getCodigo_receta(),
						admision.getNro_ingreso(), tipo);

				// receta_ripsAction.getFootIndicacionRecomendaciones().setVisible(false);
			} else {
				if (receta_ripsAction != null)
					receta_ripsAction.detach();
			}

			FormularioUtil.deshabilitarComponentes(divEditar, true,
					new String[] {});

			if (orden_servicioAction != null)
				orden_servicioAction.validarParaImpresion();

			if (receta_ripsAction != null)
				receta_ripsAction.validarParaImpresion();

			btGuardar.setDisabled(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void eliminarDatos(final Ordenes_medicas ordenes_medicas,
			final Orden_servicio orden_servicio, final Receta_rips receta_rips,
			Admision admision) throws Exception {
		try {
			Messagebox.show(
					"¿Esta seguro que desea eliminar esta orden medica? ",
					"Informacion", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								if (orden_servicio != null)
									getServiceLocator()
											.getOrden_servicioService()
											.eliminar(orden_servicio);
								if (receta_rips != null)
									getServiceLocator().getReceta_ripsService()
											.eliminar(receta_rips);
								getServiceLocator().getServicio(
										Ordenes_medicasService.class).eliminar(
										ordenes_medicas);
								recargarAdmisiones();
								MensajesUtil
										.mensajeInformacion(
												"Orden medica eliminada !!!",
												"Orden medica eliminada satisfactoriamente !!!");
							}
						}
					});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public boolean validarForm() throws Exception {
		boolean validar = true;

		// FormularioUtil.validarCamposObligatorios(tbxObservacion_medica);

		if (validar && receta_ripsAction != null)
			validar = receta_ripsAction.validarFormExterno();

		if (validar && orden_servicioAction != null)
			validar = orden_servicioAction.validarFormExterno();

		if (validar) {
			int total_medicamentos = receta_ripsAction != null ? receta_ripsAction
					.getTotalDetalles() : 0;
			int total_ordenamientos = orden_servicioAction != null ? orden_servicioAction
					.getTotalDetalles() : 0;

			if (total_medicamentos == 0 && total_ordenamientos == 0) {
				validar = false;
			}

			if (!validar) {
				MensajesUtil
						.mensajeAlerta(
								"No hay datos para guardar la orden medica",
								"Para guardar una orden medica se necesita por lo menos ordenar un medicamente o un procedimiento");
			}

		}

		return validar;
	}

	public void guardarDatos() {
		try {
			if (validarForm()) {
				Ordenes_medicas ordenes_medicas = new Ordenes_medicas();
				ordenes_medicas.setCodigo_orden(codigo_orden);
				ordenes_medicas.setCodigo_empresa(codigo_empresa);
				ordenes_medicas.setCodigo_sucursal(codigo_sucursal);
				ordenes_medicas.setCodigo_enfermera(null);
				if (tbxCodigo_prestador_om.getValue().isEmpty()
						|| tbxCodigo_prestador_om.getValue().equals(
								IConstantes.CODIGO_MEDICO_DEFECTO)) {
					ordenes_medicas.setCodigo_medico(usuarios.getCodigo());
				} else {
					ordenes_medicas.setCodigo_medico(tbxCodigo_prestador_om
							.getValue());
				}

				// ordenes_medicas.setCodigo_orden_servicio();
				// ordenes_medicas.setCodigo_receta();
				ordenes_medicas.setEstado("");
				ordenes_medicas.setFecha_orden(new Timestamp(dtbxFecha_orden_m
						.getValue().getTime()));
				ordenes_medicas.setNro_documento(admision_seleccionada
						.getNro_identificacion());
				ordenes_medicas.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());
				ordenes_medicas.setObservaciones("");
				ordenes_medicas.setObservaciones_medico("");
				// ordenes_medicas.setObservaciones_medico(tbxObservacion_medica.getValue());
				ordenes_medicas.setVia_ingreso(admision_seleccionada
						.getVia_ingreso());
				ordenes_medicas.setTipo(tipo);

				Map<String, Object> datos = new HashMap<String, Object>();

				// log.info("");

				datos.put("accion", tbxAccion.getValue());

				datos.put("ordenes_medicas", ordenes_medicas);
				datos.put("admision", admision_seleccionada);
				Map<String, Object> mapReceta = receta_ripsAction
						.obtenerDatos();
				Map<String, Object> mapProcedimientos = null;
				if (orden_servicioAction != null) {
					mapProcedimientos = orden_servicioAction.obtenerDatos();
					datos.put("orden_servicio", mapProcedimientos);
				} else {
					mapProcedimientos = new HashMap<String, Object>();
					mapProcedimientos.put("orden_servicio",
							new Orden_servicio());
					mapProcedimientos.put("lista_detalle",
							new ArrayList<Detalle_orden>());
					datos.put("orden_servicio", mapProcedimientos);
				}
				datos.put("receta_medica", mapReceta);

				// log.info("accion ===> " + tbxAccion.getValue());

				getServiceLocator().getServicio(Ordenes_medicasService.class)
						.guardarDatos(datos);

				codigo_orden = ordenes_medicas.getCodigo_orden();

				Receta_rips receta_rips = (Receta_rips) mapReceta
						.get("receta_rips");
				if (receta_rips != null)
					receta_ripsAction.mostrarDatos(receta_rips, true);
				// hay que llamar este metodo para validar que salga el boton
				// para imprimir despues de guardar la receta
				receta_ripsAction.validarParaImpresion();

				if (orden_servicioAction != null) {
					Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
							.get("orden_servicio");
					if (orden_servicio != null)
						orden_servicioAction.mostrarDatos(orden_servicio, true);
					// hay que llamar este metodo para validar que salga el
					// boton
					// para imprimir despues de guardar la orden
					orden_servicioAction.validarParaImpresion();
				}

				tbxAccion.setValue("modificar");

				MensajesUtil
						.mensajeInformacion("Informacion...",
								"Los datos de la orden se guardaron satisfactoriamente");

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

	public void onSeleccionarTipos(Integer opcion) {
		groupboxConsultar.setVisible(false);
		tbxAccion.setValue("registrar");
		try {
			switch (opcion) {
			case 0:
				tipo = IConstantes.TIPO_ORDEN_URGENCIA;
				divModuloFarmacologico.setVisible(true);
				divModuloOrdenamiento.setVisible(true);
				auxhModulo.setLabel("MODULO DE ORDENES URGENCIA");
				onMostrarModuloFarmacologico(null, "", tipo);
				onMostrarModuloOrdenamiento(null, "", tipo);
				break;
			case 1:
				tipo = IConstantes.TIPO_ORDEN_HOSPITALIZACION;
				divModuloFarmacologico.setVisible(true);
				divModuloOrdenamiento.setVisible(true);
				auxhModulo.setLabel("MODULO DE ORDENES HOSPITALIZACION");
				onMostrarModuloFarmacologico(null, "", tipo);
				onMostrarModuloOrdenamiento(null, "", tipo);
				break;
			case 2:
				tipo = IConstantes.TIPO_ORDEN_AMBULATORIA;
				divModuloFarmacologico.setVisible(true);
				divModuloOrdenamiento.setVisible(false);
				auxhModulo.setLabel("MODULO DE RECETAS");
				onMostrarModuloFarmacologico(null, "", tipo);
				onMostrarModuloOrdenamiento(null, "", tipo);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MensajesUtil.mensajeError(e, "", this);
		}

		tbxCodigo_orden_m.setVisible(false);
		labelCodigo_orden.setVisible(false);
		// tbxObservacion_medica.setReadonly(false);
		dtbxFecha_orden_m.setValue(new Date());
		btGuardar.setDisabled(false);

		borderlayoutEditar.setVisible(true);
	}

	public void listarAdmisiones() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());

		List<Admision> listado_admisiones = getServiceLocator()
				.getAdmisionService().listarTabla(parametros);
		lbxAdmisiones.getChildren();

		for (Admision admision : listado_admisiones) {
			admision = getServiceLocator().getAdmisionService().consultar(
					admision);
			Elemento elemento_via = new Elemento();
			elemento_via.setCodigo(admision.getVia_ingreso());
			elemento_via.setTipo("via_ingreso");
			elemento_via = getServiceLocator().getElementoService().consultar(
					elemento_via);
			Listitem listitem = new Listitem(admision.getNro_ingreso()
					+ " - "
					+ formato_fecha.format(admision.getFecha_ingreso())
					+ " "
					+ (elemento_via != null ? elemento_via.getDescripcion()
							: admision.getVia_ingreso()), admision);
			if (admision.getNro_ingreso().equals(
					admision_seleccionada.getNro_ingreso())
					&& admision.getVia_ingreso().equals(
							admision_seleccionada.getVia_ingreso())) {
				listitem.setSelected(true);
			}
			lbxAdmisiones.appendChild(listitem);
		}
	}

	public void recargarAdmisiones() {
		Admision admision = (Admision) lbxAdmisiones.getSelectedItem()
				.getValue();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("nro_documento", admision.getNro_identificacion());

		List<Ordenes_medicas> listado_ordenes = getServiceLocator()
				.getServicio(Ordenes_medicasService.class).listar(parametros);

		listboxOrdenes_medicas.getItems().clear();

		for (Ordenes_medicas ordenes_medicas : listado_ordenes) {
			listboxOrdenes_medicas.appendChild(crearFila(ordenes_medicas,
					admision));
		}
	}

	public void onSeleccionarAdmision() {
		Admision admision = (Admision) lbxAdmisiones.getSelectedItem()
				.getValue();
		toolbarbuttonNueva_orden_hospitalizacion.setDisabled(true);
		toolbarbuttonNueva_orden_urgencia.setDisabled(true);
		toolbarbuttonNueva_receta.setDisabled(true);
		if (admision_seleccionada != null) {
			if (admision.getNro_ingreso().equals(
					admision_seleccionada.getNro_ingreso())
					&& admision.getVia_ingreso().equals(
							admision_seleccionada.getVia_ingreso())) {
				toolbarbuttonNueva_orden_hospitalizacion.setDisabled(false);
				toolbarbuttonNueva_orden_urgencia.setDisabled(false);
				toolbarbuttonNueva_receta.setDisabled(false);
			}
		}

		if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)) {
			toolbarbuttonNueva_orden_hospitalizacion.setVisible(false);
			tipo = IConstantes.TIPO_ORDEN_URGENCIA;
			onSeleccionarTipos(0);
		} else if (admision.getVia_ingreso().equals(
				IVias_ingreso.HOSPITALIZACIONES)) {
			toolbarbuttonNueva_orden_urgencia.setVisible(false);
			tipo = IConstantes.TIPO_ORDEN_HOSPITALIZACION;
			onSeleccionarTipos(1);
		} else {
			onSeleccionarTipos(2);
		}

		// parametros.put("codigo_paciente", admision.getNro_identificacion());
		// parametros.put("codigo_administradora",
		// admision.getCodigo_administradora());
		// parametros.put("tipo_orden", tipo);
		// parametros.put("tipo_receta", tipo);
		// parametros.put("nro_identificacion",
		// admision.getNro_identificacion());
		//
		// List<Orden_servicio> lista_orden = getServiceLocator()
		// .getOrden_servicioService().listar(parametros);
		//
		// Orden_servicio orden_servicio = lista_orden.isEmpty() ? null
		// : lista_orden.get(0);
		//
		// List<Receta_rips> lista_receta = getServiceLocator()
		// .getReceta_ripsService().listar(parametros);
		//
		// Receta_rips receta_rips = lista_receta.isEmpty() ? null :
		// lista_receta
		// .get(0);
		//
		// if (!(orden_servicio == null && receta_rips == null)) {
		// listboxOrdenes_medicas.appendChild(crearFila(orden_servicio,
		// receta_rips, admision));
		// }

	}

	public Listitem crearFila(final Ordenes_medicas ordenes_medicas,
			final Admision admision) {
		final Listitem listitem = new Listitem();

		listitem.appendChild(new Listcell(ordenes_medicas.getCodigo_orden()));

		listitem.appendChild(new Listcell(valor_constante(ordenes_medicas
				.getTipo())));

		listitem.appendChild(new Listcell(formato_fecha.format(ordenes_medicas
				.getFecha_orden())));

		Orden_servicio orden_servicio = null;
		Receta_rips receta_rips = null;

		if (ordenes_medicas.getCodigo_orden_servicio() != null) {
			orden_servicio = new Orden_servicio();
			orden_servicio.setId(ordenes_medicas.getCodigo_orden_servicio());
			orden_servicio = getServiceLocator().getOrden_servicioService()
					.consultar(orden_servicio);
			listitem.appendChild(new Listcell(
					orden_servicio != null ? orden_servicio.getLista_detalle()
							.size() + "" : ""));
		} else {
			listitem.appendChild(new Listcell(""));
		}

		if (ordenes_medicas.getCodigo_receta() != null
				&& !ordenes_medicas.getCodigo_receta().isEmpty()) {
			receta_rips = new Receta_rips();
			receta_rips.setCodigo_empresa(codigo_empresa);
			receta_rips.setCodigo_sucursal(codigo_sucursal);
			receta_rips.setCodigo_receta(ordenes_medicas.getCodigo_receta());
			receta_rips = getServiceLocator().getReceta_ripsService()
					.consultar(receta_rips);
			listitem.appendChild(new Listcell(receta_rips != null ? receta_rips
					.getLista_detalle().size() + "" : ""));
		} else {
			listitem.appendChild(new Listcell(""));
		}

		listitem.appendChild(new Listcell(admision.getNro_identificacion()
				+ " " + admision.getPaciente().getNombreCompleto()));

		Listcell listcell = new Listcell();
		Hlayout hlayout = new Hlayout();
		hlayout.setValign("middle");

		Toolbarbutton toolbarbutton_mostrar = new Toolbarbutton("Mostrar",
				"/images/mostrar_info.png");
		toolbarbutton_mostrar
				.setTooltiptext("Mostrar informacion de la orden medica");

		final Orden_servicio orden_servicio2 = orden_servicio;
		final Receta_rips receta_rips2 = receta_rips;

		toolbarbutton_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						mostrarDatos(ordenes_medicas, orden_servicio2,
								receta_rips2, admision);
					}

				});

		hlayout.appendChild(toolbarbutton_mostrar);

		Toolbarbutton toolbarbutton_eliminar = new Toolbarbutton("",
				"/images/eliminar.gif");
		toolbarbutton_mostrar
				.setTooltiptext("Mostrar informacion de la orden medica");

		toolbarbutton_eliminar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						eliminarDatos(ordenes_medicas, orden_servicio2,
								receta_rips2, admision);
					}

				});

		hlayout.appendChild(new Space());
		hlayout.appendChild(toolbarbutton_eliminar);

		listcell.appendChild(hlayout);

		listitem.appendChild(listcell);

		return listitem;
	}

	private String valor_constante(String constante) {
		String r = "";
		if (constante.equals(IConstantes.TIPO_ORDEN_HOSPITALIZACION)) {
			r = "Hospitalizacion";
		} else if (constante.equals(IConstantes.TIPO_ORDEN_URGENCIA)) {
			r = "Urgencia";
		} else {
			r = "Ambulatoria";
		}
		return r;
	}

	public Listitem crearFila(final Orden_servicio orden_servicio,
			final Receta_rips receta_rips, final Admision admision) {

		final Listitem listitem = new Listitem();
		listitem.appendChild(new Listcell(admision.getNro_ingreso()));
		listitem.appendChild(new Listcell("Ambulatorio"));
		listitem.appendChild(new Listcell(formato_fecha.format(admision
				.getFecha_ingreso())));

		if (orden_servicio != null) {
			listitem.appendChild(new Listcell(orden_servicio.getLista_detalle()
					.size() + ""));
		} else {
			listitem.appendChild(new Listcell(""));
		}

		if (receta_rips != null) {
			listitem.appendChild(new Listcell(receta_rips.getLista_detalle()
					.size() + ""));
		} else {
			listitem.appendChild(new Listcell(""));
		}

		listitem.appendChild(new Listcell(admision.getNro_identificacion()
				+ " " + admision.getPaciente().getNombreCompleto()));

		Listcell listcell = new Listcell();
		Hlayout hlayout = new Hlayout();
		hlayout.setValign("middle");

		Toolbarbutton toolbarbutton_mostrar = new Toolbarbutton("Mostrar",
				"/images/mostrar_info.png");
		toolbarbutton_mostrar
				.setTooltiptext("Mostrar informacion de la orden medica");

		toolbarbutton_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						mostrarDatos(null, orden_servicio, receta_rips,
								admision);
					}

				});

		hlayout.appendChild(toolbarbutton_mostrar);
		listcell.appendChild(hlayout);

		listitem.appendChild(listcell);

		return listitem;
	}

}
