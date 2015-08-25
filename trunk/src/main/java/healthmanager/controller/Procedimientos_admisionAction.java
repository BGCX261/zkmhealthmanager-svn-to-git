/*
 * usuariosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.Detalle_ordenService;
import healthmanager.modelo.service.Esquema_vacunacionService;
import healthmanager.modelo.service.ProcedimientosService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Procedimientos_admisionAction extends GeneralComposer implements
		ISeleccionarComponente {

	// private static Logger log = Logger
	// .getLogger(Procedimientos_admisionAction.class);

	private static final long serialVersionUID = 2341202112877183267L;
	@WireVariable
	private Detalle_ordenService detalle_ordenService;
	@WireVariable
	private ProcedimientosService procedimientosService;
	@WireVariable
	private Esquema_vacunacionService esquema_vacunacionService;
	@WireVariable
	private Configuracion_admision_ingresoService configuracion_admision_ingresoService;

	// Componentes //
	@View
	private Listbox lbxProcedimientoPorRealizar;
	@View
	private Toolbarbutton btnAgregar_procedimiento;
	@View
	private Toolbarbutton btnQuitar_procedimiento;
	@View
	private Listheader listheaderValor;
	@View
	private Hbox hboxTotal;
	@View
	private Label lbTotal;
	@View
	private Listheader listheaderDosis;

	private List<String> seleccionados_procedimientos = new ArrayList<String>();

	private Orden_servicio orden_servicio;

	private String manual = "SOAT";

	private static final String NO_ELIMINAR = "no_e";

	private String via_ingreso;

	private boolean laboratorios_pyp;

	private Manuales_tarifarios manuales_tarifarios;

	private boolean atencion_particular;

	private Admision admision_auxiliar;

	@Override
	public void init() {

	}

	@Override
	public void onClose() {
		setVisible(false);
	}

	/**
	 * Este metodo me carga los servicios que tenga pendientes por realizar este
	 * paciente
	 * 
	 * @param string
	 * */
	public void mostrarServicios(String via_ingreso, boolean laboratorios_pyp,
			Paciente paciente, boolean atencion_particular, Contratos contratos) {
		// Cargamos via ingreso
		this.via_ingreso = via_ingreso;
		this.laboratorios_pyp = laboratorios_pyp;
		this.atencion_particular = atencion_particular;
		listheaderValor.setVisible(atencion_particular);

		if (atencion_particular) {
			hboxTotal.setVisible(true);
			// lbxProcedimientoPorRealizar.setCheckmark(false);
		}

		// Verificamos se va a utilizar el servicio de vacinacion
		listheaderDosis.setVisible(via_ingreso
				.equals(IVias_ingreso.VIA_VACUNACION));

		// Consultamos el manual tariafario donde tenga contratado
		// el servicio el paciente
		Admision admision = new Admision();
		admision.setCodigo_empresa(paciente.getCodigo_empresa());
		admision.setCodigo_sucursal(paciente.getCodigo_sucursal());
		admision.setNro_identificacion(paciente.getNro_identificacion());
		admision.setCodigo_administradora(contratos != null ? contratos
				.getCodigo_administradora() : paciente
				.getCodigo_administradora());
		admision.setVia_ingreso(via_ingreso);
		admision.setId_plan(contratos != null ? contratos.getId_plan() : "");
		admision.setParticular((atencion_particular && contratos != null) ? "S"
				: "N");
		this.admision_auxiliar = admision;

		manuales_tarifarios = ServiciosDisponiblesUtils
				.getManuales_tarifarios(admision);

		if (manuales_tarifarios != null) {
			// Obtenemos la tarifa con la cual fue contratado el laboratorio

			List<String> listado_cups_contratados = ServiciosDisponiblesUtils
					.getFiltroProcedimientos(admision_auxiliar, true);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", paciente.getCodigo_empresa());
			params.put("codigo_sucursal", paciente.getCodigo_sucursal());
			params.put("codigo_paciente", paciente.getNro_identificacion());

			if (habilitarFiltro()) {
				if (via_ingreso.equals(IVias_ingreso.LABORATORIOS)) {
					params.put(
							"tipo_procedimiento_in",
							ServiciosDisponiblesUtils
									.getTiposServicios(new String[] { ServiciosDisponiblesUtils.CODSER_LABORATORIO_CLINICO }));
					params.put("no_vias_ingreso", getViasIngresoPYP());
				} else if (via_ingreso.equals(IVias_ingreso.LABORATORIOS_PYP)) {
					params.put(
							"tipo_procedimiento_in",
							ServiciosDisponiblesUtils
									.getTiposServicios(new String[] { ServiciosDisponiblesUtils.CODSER_LABORATORIO_CLINICO }));
					params.put("vias_ingreso", getViasIngresoPYP());
				} else {
					if (!listado_cups_contratados.isEmpty())
						params.put("listado_cups_contratados",
								listado_cups_contratados);
				}
			}

			params.put("tipo_orden", IConstantes.TIPO_ORDEN_AMBULATORIA);
			params.put("realizado", "N");

			// log.info("Listado de parametros ------");
			// for (String key : params.keySet()) {
			// //log.info(key + " ===>" + params.get(key));
			// }

			List<Detalle_orden> detalle_ordens = detalle_ordenService
					.listarParametrizado(params);
			boolean mostrar_msj = seleccionados_procedimientos.isEmpty();
			double valor_total = 0;
			if (!detalle_ordens.isEmpty() && !atencion_particular) {

				for (Detalle_orden detalle_orden : detalle_ordens) {
					if (!existeRegistro(detalle_orden.getCodigo_procedimiento())) {
						String codigo_cups = "";
						String nombre_procedimiento = "";

						Procedimientos procedimiento = new Procedimientos();
						procedimiento.setId_procedimiento(new Long(
								detalle_orden.getCodigo_procedimiento()));
						procedimiento = procedimientosService
								.consultar(procedimiento);
						if (procedimiento != null) {
							codigo_cups = procedimiento.getCodigo_cups();
							nombre_procedimiento = procedimiento
									.getDescripcion();
						}

						Listitem listitem = new Listitem();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("dtt_orden", detalle_orden);
						listitem.setValue(map);

						listitem.setAttribute(NO_ELIMINAR, "_ok");
						listitem.appendChild(new Listcell(codigo_cups));
						listitem.appendChild(new Listcell(nombre_procedimiento));
						listitem.appendChild(new Listcell(detalle_orden
								.getUnidades() + ""));
						listitem.appendChild(new Listcell(detalle_orden
								.getValor_procedimiento() + ""));
						valor_total = detalle_orden.getValor_procedimiento()
								* detalle_orden.getUnidades().intValue();
						lbxProcedimientoPorRealizar.appendChild(listitem);
						seleccionados_procedimientos.add(detalle_orden
								.getCodigo_procedimiento());
					}
				}

				btnAgregar_procedimiento.setVisible(true);
				btnQuitar_procedimiento.setVisible(true);
				lbxProcedimientoPorRealizar.setMultiple(true);
				lbxProcedimientoPorRealizar.setCheckmark(true);

				lbTotal.setValue(new DecimalFormat("#,##0.##")
						.format(valor_total));
				// Mostramos msj de seleccion
				if (mostrar_msj)
					MensajesUtil
							.mensajeInformacion("Informacion",
									"Por favor seleccion el servicio que va a realizar el paciente");
			}
			// else{ // Habilita cuando no tenga laboratorios
			// btnAgregar_procedimiento.setVisible(true);
			// btnQuitar_procedimiento.setVisible(true);
			// lbxProcedimientoPorRealizar.setMultiple(false);
			// lbxProcedimientoPorRealizar.setCheckmark(false);
			//
			// MensajesUtil.mensajeInformacion("Informacion",
			// "Por favor agregue el servicio que va a realizar el paciente");
			// }

			btnAgregar_procedimiento.setVisible(true);
			btnQuitar_procedimiento.setVisible(true);
			lbxProcedimientoPorRealizar.setMultiple(true);
			lbxProcedimientoPorRealizar.setCheckmark(true);

			lbxProcedimientoPorRealizar.invalidate();
		} else {
			onClose();
		}
	}

	public void limpiar() {
		lbxProcedimientoPorRealizar.getChildren().clear();
		seleccionados_procedimientos.clear();
	}

	private boolean existeRegistro(String codigo_procedimiento) {
		for (String codigo_pcd_agregado : seleccionados_procedimientos) {
			if (codigo_pcd_agregado.equals(codigo_procedimiento)) {
				return true;
			}
		}
		return false;
	}

	public void mostrarOrdenServicio(Orden_servicio orden_servicio) {
		this.setOrden_servicio(orden_servicio);
		Admision admision = new Admision();
		admision.setCodigo_empresa(codigo_empresa);
		admision.setCodigo_sucursal(codigo_sucursal);
		admision.setNro_identificacion(orden_servicio.getCodigo_paciente());
		admision.setCodigo_administradora(orden_servicio
				.getCodigo_administradora());
		admision.setCausa_externa("");
		admision.setId_plan(orden_servicio.getId_plan());

		List<Detalle_orden> listado = orden_servicio.getLista_detalle();
		for (Detalle_orden detalle_orden : listado) {
			String codigo_cups = "";
			String nombre_procedimiento = "";

			Procedimientos procedimiento = new Procedimientos();
			procedimiento.setId_procedimiento(new Long(detalle_orden
					.getCodigo_procedimiento()));
			procedimiento = procedimientosService.consultar(procedimiento);
			if (procedimiento != null) {
				codigo_cups = procedimiento.getCodigo_cups();
				nombre_procedimiento = procedimiento.getDescripcion();
			}

			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell(codigo_cups));
			listitem.appendChild(new Listcell(nombre_procedimiento));
			listitem.appendChild(new Listcell(detalle_orden.getUnidades() + ""));
			listitem.appendChild(new Listcell(detalle_orden
					.getValor_procedimiento() + ""));
			listitem.setValue(detalle_orden);
			listitem.setDisabled(true);
			lbxProcedimientoPorRealizar.appendChild(listitem);
		}

		btnAgregar_procedimiento.setDisabled(true);
		btnQuitar_procedimiento.setDisabled(true);
	}

	private boolean habilitarFiltro() {
		// return (ServiciosDisponiblesUtils.isApoyoDiagnostico(via_ingreso)
		// || via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
		// || via_ingreso.equals(IVias_ingreso.VIA_VACUNACION) || via_ingreso
		// .equals(IVias_ingreso.SERVICIOS_AMBULATORIOS));
		return true;
	}

	public void openPcd() throws Exception {
		String pages = "";

		String anio = this.anio + "";

		pages = "/pages/openProcedimientos.zul";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("restringido", "");
		parametros.put("anio", anio);
		parametros.put("ocultar_filtro_procedimiento",
				"ocultar_filtro_procedimiento");
		parametros.put("seleccionados", seleccionados_procedimientos);
		parametros.put("manual_tarifario", manuales_tarifarios);
		parametros.put("via_ingreso", via_ingreso);

		if (habilitarFiltro()) {
			if (admision_auxiliar != null) {
				// log.info("admision_auxiliar ===> "
				// + admision_auxiliar.getVia_ingreso());
				List<String> listado_cups_contratados = ServiciosDisponiblesUtils
						.getFiltroProcedimientos(admision_auxiliar, true);
				if (!listado_cups_contratados.isEmpty())
					parametros.put("listado_cups_contratados",
							listado_cups_contratados);
				if (laboratorios_pyp) {
					parametros.put("tipo_procedimiento_bus",
							ServiciosDisponiblesUtils.TIPO_LABORATORIO_CLINICO);
					parametros.put("pyp", "S");
				}
			}
		}

		if (via_ingreso.equals(IVias_ingreso.LABORATORIOS_PYP)) {
			parametros.put("pyp", "S");
		}

		Component componente = Executions.createComponents(pages, null,
				parametros);
		final Window ventana = (Window) componente;
		if (ventana instanceof OpenProcedimientosAction) {
			((OpenProcedimientosAction) ventana)
					.setSeleccionar_componente(this);
		}
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR PROCEDIMIENTOS " + manual);
		ventana.setMode("modal");
	}

	@SuppressWarnings("unchecked")
	public void quitarListitemSeleccionados() {
		Set<Listitem> listado_items = lbxProcedimientoPorRealizar
				.getSelectedItems();
		List<Listitem> listitems_eliminar = new ArrayList<Listitem>();
		if (listado_items.isEmpty()) {
			MensajesUtil.mensajeAlerta("Advertencia",
					"Para esta accion debe seleccionar por lo menos un item");
		} else {

			boolean servicions_ordenados_por_medico = false;
			for (Listitem listitem : listado_items) {
				if (listitem.getAttribute(NO_ELIMINAR) == null) {
					Map<String, Object> detalle_orden_map = (Map<String, Object>) listitem
							.getValue();
					Detalle_orden detalle_orden = (Detalle_orden) detalle_orden_map
							.get("dtt_orden");
					seleccionados_procedimientos.remove(detalle_orden
							.getCodigo_procedimiento());
					listitems_eliminar.add(listitem);
				} else {
					servicions_ordenados_por_medico = true;
				}
			}
			lbxProcedimientoPorRealizar.getItems()
					.removeAll(listitems_eliminar);

			if (servicions_ordenados_por_medico) {
				MensajesUtil.mensajeAlerta("Advertencia",
						"Solo puede eliminar los servicios que adicione");
			}
		}
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		// log.info("pcd ===> " + pcd);
		Long id_procedimiento = (Long) pcd.get("id_procedimiento");
		seleccionados_procedimientos.add(id_procedimiento + "");
		String codigo_cups = (String) pcd.get("codigo_cups");
		String nombre_procedimiento = (String) pcd.get("nombre_procedimiento");

		final Detalle_orden detalle_orden = new Detalle_orden();
		detalle_orden.setCodigo_empresa(codigo_empresa);
		detalle_orden.setCodigo_sucursal(codigo_sucursal);
		detalle_orden.setCodigo_cups(codigo_cups);
		detalle_orden.setCodigo_procedimiento(id_procedimiento + "");
		detalle_orden.setUnidades(1);
		detalle_orden.setRealizado("N");
		detalle_orden.setUnidades_realizadas(0);
		detalle_orden.setCodigo_orden(null);
		detalle_orden.setNombre_procedimiento(nombre_procedimiento);

		detalle_orden.setValor_procedimiento((Double) pcd
				.get("valor_procedimiento"));
		detalle_orden.setDescuento((Double) pcd.get("descuento"));
		detalle_orden.setIncremento((Double) pcd.get("incremento"));
		detalle_orden.setValor_real((Double) pcd.get("valor_real"));

		// log.info("Valor porcedimiento: "
		// + detalle_orden.getValor_procedimiento());

		final Listitem listitem = new Listitem();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dtt_orden", detalle_orden);

		listitem.setValue(map);
		listitem.appendChild(new Listcell(codigo_cups));
		listitem.appendChild(Utilidades.obtenerListcell(nombre_procedimiento,
				Textbox.class, true, true));
		Listcell listcell = Utilidades.obtenerListcell(1.0, Doublebox.class,
				false, true);
		Doublebox doublebox = (Doublebox) listcell.getFirstChild();
		doublebox.setId("doublebox_codigo_procedimiento_" + id_procedimiento
				+ "");
		doublebox.setFormat("#####0");
		doublebox.setValue(1);
		doublebox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Doublebox doublebox = (Doublebox) arg0.getTarget();
				detalle_orden.setUnidades(doublebox.getValue() != null ? doublebox
						.getValue().intValue() : null);
				calcularTotalProcedimiento();
			}
		});

		listitem.appendChild(listcell);
		listitem.appendChild(new Listcell(new DecimalFormat("#,##0.#")
				.format((detalle_orden.getUnidades().intValue() * detalle_orden
						.getValor_procedimiento()))));

		// Accion para vacunacion
		if (via_ingreso.equals(IVias_ingreso.VIA_VACUNACION)) {
			Component listboxEsquemasVacunacion = getListBoxEsquemaVacunacion(
					id_procedimiento + "", listitem, map);
			listcell = new Listcell();
			listcell.appendChild(listboxEsquemasVacunacion);
			listitem.appendChild(listcell);
			listitem.setSelected(true);
		} else {
			listcell = new Listcell();
			listcell.appendChild(new Label("* VALIDO PARA VACUNACION"));
			listitem.appendChild(listcell);
			listitem.setSelected(true);
		}

		lbxProcedimientoPorRealizar.appendChild(listitem);
		calcularTotalProcedimiento();
	}

	private Component getListBoxEsquemaVacunacion(String codigo_procedimiento,
			Listitem listitem, final Map<String, Object> mapa_contenedor) {
		// Cargamos esquemas de vacunacion
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("codigo_vacuna", codigo_procedimiento);
		List<Esquema_vacunacion> esquema_vacunacions = esquema_vacunacionService
				.listar(map);
		if (!esquema_vacunacions.isEmpty()) {
			Listbox listbox = new Listbox();
			listbox.setHflex("1");
			listbox.setMold("select");
			for (Esquema_vacunacion esquema_vacunacion : esquema_vacunacions) {
				listbox.appendChild(new Listitem(""
						+ esquema_vacunacion.getDescripcion().toUpperCase(),
						esquema_vacunacion));
			}
			listitem.setSelected(true);
			if (listbox.getItemCount() > 0) {
				listbox.setSelectedIndex(0);
				// Esquema de vacunacion
				Esquema_vacunacion esquema_vacunacion = listbox
						.getSelectedItem().getValue();
				mapa_contenedor.put("esquema", esquema_vacunacion);
			}
			listbox.addEventListener(Events.ON_SELECT,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							Listbox listbox = (Listbox) arg0.getTarget();
							Listitem listitem = listbox.getSelectedItem();
							if (listitem != null) {
								mapa_contenedor.put("esquema",
										listitem.getValue());
							} else {
								mapa_contenedor.put("esquema", null);
							}
						}
					});
			return listbox;
		} else {
			return new Label("* no contiene docificacion registrada");
		}
	}

	/**
	 * Este metodo me permite calcular el valor total de los procedimientos
	 * */
	private void calcularTotalProcedimiento() {
		List<Listitem> listitems = lbxProcedimientoPorRealizar.getItems();
		double valor_total = 0;
		for (Listitem listitem : listitems) {
			Map<String, Object> map = listitem.getValue();
			Detalle_orden detalle_orden = (Detalle_orden) map.get("dtt_orden");
			valor_total += detalle_orden.getValor_procedimiento()
					* detalle_orden.getUnidades().intValue();
		}
		lbTotal.setValue(new DecimalFormat("#,##0.##").format(valor_total));
	}

	public List<Map<String, Object>> getListadoProcedimientos() {
		List<Map<String, Object>> listado_ordenes = new ArrayList<Map<String, Object>>();
		Collection<Listitem> seleccionados = atencion_particular ? lbxProcedimientoPorRealizar
				.getItems() : lbxProcedimientoPorRealizar.getSelectedItems();
		for (Listitem listitem : seleccionados) {
			Map<String, Object> map = listitem.getValue();
			Detalle_orden detalle_orden = (Detalle_orden) map.get("dtt_orden");
			Doublebox doublebox = (Doublebox) listitem
					.getFellowIfAny("doublebox_codigo_procedimiento_"
							+ detalle_orden.getCodigo_procedimiento());
			if (doublebox != null && doublebox.getValue() != null
					&& doublebox.getValue().intValue() != 0) {
				detalle_orden.setUnidades(doublebox.getValue().intValue());
			}
			listado_ordenes.add(map);
		}
		return listado_ordenes;
	}

	public void limpiarDatos() {
		setOrden_servicio(null);
		lbxProcedimientoPorRealizar.getItems().clear();
		seleccionados_procedimientos.clear();
	}

	public List<String> getViasIngresoPYP() {
		List<String> listado = new ArrayList<String>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("es_pyp", true);
		List<Configuracion_admision_ingreso> listado_configuraciones = configuracion_admision_ingresoService
				.listar(parametros);
		for (Configuracion_admision_ingreso configuracion_admision_ingreso : listado_configuraciones) {
			listado.add(configuracion_admision_ingreso.getVia_ingreso());
		}
		return listado;
	}

	public String getCodigoOrden(List<Detalle_orden> detalle_ordens) {
		String codigo_orden_final = detalle_ordens.get(0).getCodigo_orden()
				+ "_";
		for (Detalle_orden detalle_orden : detalle_ordens) {
			codigo_orden_final += "_" + detalle_orden.getCodigo_procedimiento();
		}
		return codigo_orden_final;
	}

	public Orden_servicio getOrden_servicio() {
		return orden_servicio;
	}

	public void setOrden_servicio(Orden_servicio orden_servicio) {
		this.orden_servicio = orden_servicio;
	}

	public String getVia_ingreso() {
		return via_ingreso;
	}

	public void setVia_ingreso(String via_ingreso) {
		this.via_ingreso = via_ingreso;
	}

}