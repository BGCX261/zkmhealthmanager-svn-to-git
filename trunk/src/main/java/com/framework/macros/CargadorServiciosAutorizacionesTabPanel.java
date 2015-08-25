package com.framework.macros;

import healthmanager.controller.AutorizacionesAction;
import healthmanager.controller.AutorizacionesAction.MOLD_AUTORIZACION;
import healthmanager.controller.OpenArticuloAction;
import healthmanager.controller.OpenProcedimientosAction;
import healthmanager.controller.ZKWindow;
import healthmanager.controller.ZKWindow.View;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Detalle_orden_autorizacion;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Procedimientos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.res.CargardorDeDatos;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.ServiciosDisponiblesUtils.NIVEL_CONSULTA;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesComponentes;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.service.ArticuloService;

public class CargadorServiciosAutorizacionesTabPanel extends Div implements
		ISeleccionarComponente, AfterCompose {

	public static Logger log = Logger
			.getLogger(CargadorServiciosAutorizacionesTabPanel.class);

	@View
	private Listbox lbxContratos;
	@View
	private Listbox lbxContenedorServicios;
	@View
	private Label lbContrato;
	@View
	private Groupbox groupContenedorContrato;

	@View
	private Toolbarbutton btnAgregarMedico;

	@View
	private Listheader lhrValorUni;
	@View
	private Listheader lhrValorTotal;
	@View
	private Listbox lbxForma_realizacion;
	@View
	private Textbox tbxObservaciones;
	@View
	private Hbox hbox_tipo;

	private Configuracion_servicios_autorizacion configuracion_servicios_autorizacion;

	private List<Map> lista_datos;
	private boolean auditor;

	private List<String> seleccionados = new ArrayList<String>();

	private ZKWindow zkWindow;
	private String tipo_pcd;
	private List<Map<String, Object>> contratacion;
	private Tabbox tabbox;
	private Component componentPadreContenedor;
	private Admision admision;
	private Tabpanels tabPanelsContenedorServicios;

	private MOLD_AUTORIZACION _tipo;

	public CargadorServiciosAutorizacionesTabPanel() {
		lista_datos = new LinkedList<Map>();
	}

	public void limpiar() {
		try {
			lista_datos.clear();
			lbxContratos.getItems().clear();
			seleccionados.clear();
			crearFilas();
			lbxContratos.setDisabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		List<Manuales_tarifarios> manuales_tarifarios = null;
		if (get_tipo() == MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE) {
			manuales_tarifarios = (List<Manuales_tarifarios>) lbxContratos
					.getSelectedItem()
					.getAttribute(
							IConstantesAutorizaciones.PARAM_LISTADO_MANUAL_TARIFARIO);
		}
		if (manuales_tarifarios != null
				|| get_tipo() != MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE) {
			Detalle_orden_autorizacion detalle_orden = getDetalle(
					pcd,
					manuales_tarifarios != null ? getManual(pcd,
							manuales_tarifarios) : null);
			// Integer frecuencia = (Integer) pcd.get("frecuencia");
			seleccionados.add(detalle_orden.getCodigo_procedimiento());
			adicionarOrden(detalle_orden,
					detalle_orden.getNombre_procedimiento(), true);
			lbxContratos.setDisabled(true);
			invalidatePadres();
		} else {
			MensajesUtil
					.mensajeAlerta(
							"Advertencia",
							"Para realizar esta accion debe agregar el manual tarifario con el que se va a calcular el valor del servicio");
		}
	}

	/**
	 * TODO pendiente terminar cuando tengas mas de un manual tarifario
	 * */
	private Manuales_tarifarios getManual(Map<String, Object> pcd,
			List<Manuales_tarifarios> manuales_tarifarios) {
		if (manuales_tarifarios.size() == 1) {
			return manuales_tarifarios.get(0);
		} else {
			if (getConfiguracion_servicios_autorizacion().getTipo_servicio()
					.equals(IConstantes.TIPO_SERVICIO_AUTO_MEDICAMENTOS)) {
				// pendiente por definir
			} else {
				Contratos contratos = lbxContratos.getSelectedItem().getValue();
				String codigo_cups = (String) pcd.get("codigo_cups");
				for (Manuales_tarifarios manuales_tarifariosTemp : manuales_tarifarios) {
					List<String> listado_servicios = ServiciosDisponiblesUtils.getFiltroServiciosPermitidos(
							contratos.getCodigo_empresa(),
							contratos.getCodigo_sucursal(),
							contratos.getCodigo_administradora(),
							contratos.getId_plan(),
							null, manuales_tarifariosTemp.getConsecutivo(),
									NIVEL_CONSULTA.CONTRATO);
					for (String codigo_cups_temp : listado_servicios) {
						if (codigo_cups_temp.equalsIgnoreCase(codigo_cups)) {
							return manuales_tarifariosTemp;
						}
					}
				}
			}
			return manuales_tarifarios.get(0);
		}
	}

	private Detalle_orden_autorizacion getDetalle(Map<String, Object> pcd,
			Manuales_tarifarios manuales_tarifarios) throws Exception {
		Maestro_manual maestro_manual = manuales_tarifarios != null ? manuales_tarifarios
				.getMaestro_manual() : null;
		if (getConfiguracion_servicios_autorizacion().getTipo_servicio()
				.equals(IConstantes.TIPO_SERVICIO_AUTO_MEDICAMENTOS)) {
			double valor_unitario = (Double) pcd.get("valor_unitario");
			double descuento = (Double) pcd.get("descuento");
			double incremento = (Double) pcd.get("incremento");
			double valor_real = (Double) pcd.get("valor_real");

			if (manuales_tarifarios != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("codigo_articulo",
						(String) pcd.get("codigo_articulo"));
				params.put("manual_tarifario", manuales_tarifarios);
				Map<String, Object> map_articulo_respuesta = Utilidades
						.getMedicamentos(params, getZkWindow()
								.getServiceLocator());
				valor_unitario = (Double) map_articulo_respuesta
						.get("valor_unitario");
				descuento = (Double) map_articulo_respuesta.get("descuento");
				incremento = (Double) map_articulo_respuesta.get("incremento");
				valor_real = (Double) map_articulo_respuesta.get("valor_real");
			}

			Detalle_orden_autorizacion detalle_orden = new Detalle_orden_autorizacion();
			detalle_orden.setCodigo_empresa((String) pcd.get("codigo_empresa"));
			detalle_orden.setCodigo_sucursal((String) pcd
					.get("codigo_sucursal"));
			detalle_orden.setCodigo_procedimiento((String) pcd
					.get("codigo_articulo"));
			detalle_orden.setValor_procedimiento(valor_unitario);
			detalle_orden.setDescuento(descuento);
			detalle_orden.setIncremento(incremento);
			detalle_orden.setValor_real(valor_real);
			detalle_orden.setUnidades(1);
			detalle_orden.setCodigo_cups((String) pcd.get("codigo_articulo"));
			detalle_orden.setNombre_procedimiento((String) pcd.get("nombre1"));
			detalle_orden
					.setManual_tarifario(manuales_tarifarios != null ? manuales_tarifarios
							.getMaestro_manual().getTipo_manual() : "");
			detalle_orden.setAutorizado((Boolean) pcd.get("autorizado") ? "S"
					: "N");
			detalle_orden
					.setTipo_servicio(getConfiguracion_servicios_autorizacion()
							.getId());
			return detalle_orden;
		} else {
			double valor_procedimiento = (Double) pcd
					.get("valor_procedimiento");
			double descuento = (Double) pcd.get("descuento");
			double incremento = (Double) pcd.get("incremento");
			double valor_real = (Double) pcd.get("valor_real");

			if (manuales_tarifarios != null) {
				Map<String, Object> map_pcd_respuesta = Utilidades
						.getProcedimiento(manuales_tarifarios,
								(Long) pcd.get("id_procedimiento"),
								getZkWindow().getServiceLocator());
				valor_procedimiento = (Double) map_pcd_respuesta
						.get("valor_pcd");
				descuento = (Double) map_pcd_respuesta.get("descuento");
				incremento = (Double) map_pcd_respuesta.get("incremento");
				valor_real = (Double) map_pcd_respuesta.get("valor_real");
			}

			Detalle_orden_autorizacion detalle_orden = new Detalle_orden_autorizacion();
			detalle_orden.setCodigo_empresa((String) pcd.get("codigo_empresa"));
			detalle_orden.setCodigo_sucursal((String) pcd
					.get("codigo_sucursal"));
			detalle_orden.setCodigo_procedimiento(pcd.get("id_procedimiento")
					+ "");
			detalle_orden.setValor_procedimiento(valor_procedimiento);
			detalle_orden.setDescuento(descuento);
			detalle_orden.setIncremento(incremento);
			detalle_orden.setValor_real(valor_real);
			detalle_orden.setUnidades(1);
			detalle_orden.setCodigo_cups((String) pcd.get("codigo_cups"));
			detalle_orden.setNombre_procedimiento((String) pcd
					.get("nombre_procedimiento"));
			detalle_orden
					.setTipo_servicio(getConfiguracion_servicios_autorizacion()
							.getId());
			detalle_orden
					.setManual_tarifario(manuales_tarifarios != null ? maestro_manual
							.getTipo_manual() : "");

			String auto_medico_general = (String) pcd
					.get("auto_medico_general");
			String auto_medico_especialista = (String) pcd
					.get("auto_medico_especialista");

			detalle_orden.setAutorizado(getEstadoAutorizacion(
					auto_medico_general, auto_medico_especialista));

			if (detalle_orden.getAutorizado().equals("N")) {
				MensajesUtil
						.mensajeInformacion(
								"informacion",
								"El procedimiento "
										+ detalle_orden
												.getCodigo_procedimiento()
										+ " "
										+ detalle_orden
												.getNombre_procedimiento()
										+ ", debera ser autorizado por el auditor médico para su realizacion");
			}
			return detalle_orden;
		}
	}

	/**
	 * Este metodo permite verificar el estado de autorizacion de un
	 * procedimiento
	 * 
	 * @author Luis Miguel
	 * */
	private String getEstadoAutorizacion(String auto_medico_general,
			String auto_medico_especialista) {
		if (get_tipo() == MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE) {
			return (auto_medico_general.equals("S")
					|| auto_medico_especialista.equals("S") ? "S" : "N");
		} else if (admision != null) {
			if (admision.getVia_ingreso().equals(
					IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
				return auto_medico_especialista;
			} else {
				return auto_medico_general;
			}
		} else {
			return "N";
		}
	}

	private void invalidatePadres() {
		if (tabbox != null) {
			tabbox.invalidate();
		}
		invalidate();
	}

	/**
	 * Este metodo me permite cargar los detalle en la pestaña correspondiente
	 * 
	 * @author Luis Miguel
	 * */
	public void _cargarDetalles(
			List<Detalle_orden_autorizacion> detalle_orden_autorizacions) {
		try {
			seleccionados.clear();
			lista_datos.clear();
			for (Detalle_orden_autorizacion detalle_orden : detalle_orden_autorizacions) {
				seleccionados.add(detalle_orden.getCodigo_procedimiento());
				adicionarOrden(detalle_orden,
						detalle_orden.getNombre_procedimiento(), false);
			}
			lbxContratos.setDisabled(true);
			crearFilas();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, getZkWindow());
		}
	}

	public void adicionarOrden(Detalle_orden_autorizacion detalle,
			String nombre_procedimiento, boolean habilitar_crear_filas)
			throws Exception {
		try {
			Map bean = llenarBeanDetalle(detalle);
			bean.put("nombre_procedimiento", nombre_procedimiento);
			lista_datos.add(bean);
			// log.info("Listado servicios: " + lista_datos.size());
			if (habilitar_crear_filas)
				crearFilas();
			// repintarGridCuentas();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e,
					"Error al seleccionar el procedimiento", getZkWindow());
		}
	}

	public void crearFilas() throws Exception {
		lbxContenedorServicios.getItems().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Map bean = lista_datos.get(j);
			crearFilaOrden(bean, j);
		}
	}

	public void crearFilaOrden(Map bean, int j) throws Exception {
		final int index = j;

		final String codigo_procedimiento = (String) bean
				.get("codigo_procedimiento");
		String nombre_procedimiento = (String) bean.get("nombre_procedimiento");
		final double valor_procedimiento = (Double) bean
				.get("valor_procedimiento");
		int unidades = bean.get("unidades") != null ? ((Integer) bean
				.get("unidades")).intValue() : 0;
		Detalle_orden_autorizacion detalle_orden_autorizacion = (Detalle_orden_autorizacion) bean
				.get("detalle");
		String codigo_cups = (String) bean.get("codigo_cups");

		final Listitem fila = new Listitem();
		fila.setValue(bean);
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo //
		Listcell cell = new Listcell();
		cell.setStyle("background-color:"
				+ (detalle_orden_autorizacion.getAutorizado().equals("S") ? "#7CE4E6"
						: "#E87B8B"));
		fila.appendChild(cell);

		cell = new Listcell();
		final Textbox tbxCodigo_procedimiento = new Textbox(
				codigo_procedimiento);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_procedimiento_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setVisible(false);
		tbxCodigo_procedimiento.setAttribute("map_", bean);
		cell.appendChild(tbxCodigo_procedimiento);

		final Textbox tbxCodigo_cups = new Textbox(codigo_cups);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_cups.setId("codigo_cups_" + j);
		tbxCodigo_cups.setHflex("1");
		tbxCodigo_cups.setReadonly(true);
		tbxCodigo_cups.setInplace(true);
		cell.appendChild(tbxCodigo_cups);

		fila.appendChild(cell);

		// Columna nombre //
		cell = new Listcell();

		final Textbox tbxNombre_procedimiento = new Textbox(
				nombre_procedimiento);
		// tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_procedimiento_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		tbxNombre_procedimiento.setInplace(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna unidades //
		cell = new Listcell();

		final Intbox tbxUnidades = new Intbox(unidades);
		if (unidades == 0.0)
			tbxUnidades.setText("");
		// tbxUnidades.setInplace(true);
		tbxUnidades.setId("unidades_" + j);
		tbxUnidades.setFormat("#,##0");
		tbxUnidades.setHflex("1");
		tbxUnidades.setMaxlength(2);
		tbxUnidades.setReadonly(false);
		cell.appendChild(tbxUnidades);
		fila.appendChild(cell);
		Res.cargarAutomatica(tbxUnidades, detalle_orden_autorizacion,
				"unidades");

		// Columna valor pcd //
		cell = new Listcell();

		final Doublebox tbxValor_procedimiento = new Doublebox(
				valor_procedimiento);
		// tbxValor_procedimiento.setInplace(true);
		tbxValor_procedimiento.setId("valor_procedimiento_" + j);
		tbxValor_procedimiento.setFormat("#,##0.00");
		tbxValor_procedimiento.setHflex("1");
		tbxValor_procedimiento.setReadonly(true);
		cell.appendChild(tbxValor_procedimiento);
		fila.appendChild(cell);

		cell = new Listcell();
		final Doublebox tbxValor_procedimientoTotal = new Doublebox(
				valor_procedimiento * unidades);
		// tbxValor_procedimiento.setInplace(true);
		tbxValor_procedimientoTotal.setId("valor_procedimiento_total_" + j);
		tbxValor_procedimientoTotal.setFormat("#,##0.00");
		tbxValor_procedimientoTotal.setHflex("1");
		tbxValor_procedimientoTotal.setReadonly(true);
		cell.appendChild(tbxValor_procedimientoTotal);
		fila.appendChild(cell);

		tbxUnidades.addEventListener(Events.ON_BLUR,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						int unidaddes = tbxUnidades.getValue() != null ? tbxUnidades
								.getValue() : 0;
						if (unidaddes == 0) { // Para que no tenga valores en
												// cero
							tbxUnidades.setValue(1);
							unidaddes = 1;
						}
						tbxValor_procedimientoTotal
								.setValue(valor_procedimiento * unidaddes);
					}
				});

		// Cargamos tipo
		cell = new Listcell();
		final Listbox lbxTipo = new Listbox();
		lbxTipo.setName("estado_pago");
		lbxTipo.setMold("select");
		UtilidadesComponentes.listarElementosListbox(
				get_tipo() == MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE,
				false, getZkWindow().getServiceLocator(), lbxTipo);
		Res.cargarAutomatica(lbxTipo, detalle_orden_autorizacion,
				"estado_cobro");
		lbxTipo.setHflex("1");
		cell.appendChild(lbxTipo);
		fila.appendChild(cell);

		if (getAdmision() != null) {
			/**
			 * En esta parte verificamos el estado de cobro de la autorizacion
			 * */
			String estado_cobro = Utilidades.getEstadoCobro(getAdmision()
					.getVia_ingreso());
			Utilidades.setValueFrom(lbxTipo, estado_cobro);
			detalle_orden_autorizacion.setEstado_cobro(estado_cobro);
			// if
			// (!estado_cobro.equals(IConstantesAutorizaciones.ESTADO_COBRO_PYP))
			// {
			// lbxTipo.setDisabled(true);
			// }
		}

		// Columna borrar //
		cell = new Listcell();

		Image img = new Image("/images/borrar.gif");
		img.setId("img_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");
		cell.appendChild(img);
		fila.appendChild(cell);

		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									seleccionados.remove(codigo_procedimiento);
									actualizarPagina();
									lista_datos.remove(index);
									crearFilas();
									if (lista_datos.isEmpty()) {
										lbxContratos.setDisabled(false);
									}
								}
							}
						});
			}
		});

		fila.setId("fila_" + j);
		lbxContenedorServicios.appendChild(fila);
		lbxContenedorServicios.setVisible(true);
		lbxContenedorServicios.applyProperties();
		lbxContenedorServicios.invalidate();
	}

	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			Listitem fila = (Listitem) lbxContenedorServicios.getFellow("fila_"
					+ i);
			Intbox tbxUnidades = (Intbox) fila.getFellow("unidades_" + i);
			bean.put("unidades", tbxUnidades.getValue());
			lista_datos.set(i, bean);
		}
	}

	/**
	 * Este metodo nos mapea el detalle de la orden con los datos indicados
	 * 
	 * @author Luis Miguel
	 * */
	private Map llenarBeanDetalle(Detalle_orden_autorizacion detalle)
			throws Exception {
		String codigo_procedimiento = detalle.getCodigo_procedimiento();
		String nombre_procedimiento = "";
		String sexo_pcd = "A";
		String limite_inferior_pcd = "0";
		String limite_superior_pcd = "0";
		double valor_procedimiento = detalle.getValor_procedimiento();
		int unidades = detalle.getUnidades();
		double descuento = detalle.getDescuento();
		double incremento = detalle.getIncremento();
		double valor_real = detalle.getValor_real();
		String codigo_cups = detalle.getCodigo_cups();

		if (getConfiguracion_servicios_autorizacion().getTipo_servicio()
				.equals(IConstantes.TIPO_SERVICIO_AUTO_MEDICAMENTOS)) {
			Articulo articulo = new Articulo();
			articulo.setCodigo_empresa(detalle.getCodigo_empresa());
			articulo.setCodigo_sucursal(detalle.getCodigo_sucursal());
			articulo.setCodigo_articulo(detalle.getCodigo_procedimiento());
			articulo = getZkWindow().getServiceLocator()
					.getServicio(ArticuloService.class).consultar(articulo);
			if (articulo != null) {
				nombre_procedimiento = articulo.getNombre1();
			} else {
				nombre_procedimiento = detalle.getNombre_procedimiento();
			}
		} else {
			Procedimientos proc = new Procedimientos();
			proc.setId_procedimiento(new Long(detalle.getCodigo_procedimiento()));
			proc = getZkWindow().getServiceLocator().getProcedimientosService()
					.consultar(proc);
			nombre_procedimiento = (proc != null ? proc.getDescripcion() : "");
			sexo_pcd = (proc != null ? proc.getSexo() : "");
			limite_inferior_pcd = (proc != null ? proc.getLimite_inferior()
					: "");
			limite_superior_pcd = (proc != null ? proc.getLimite_superior()
					: "");
			codigo_cups = proc != null ? proc.getCodigo_cups() : detalle
					.getCodigo_procedimiento();
		}

		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put("codigo_procedimiento", codigo_procedimiento);
		bean.put("nombre_procedimiento", nombre_procedimiento);
		bean.put("unidades", unidades);
		bean.put("unidades_realizadas", 0);
		bean.put("valor_procedimiento", valor_procedimiento);
		bean.put("descuento", descuento);
		bean.put("incremento", incremento);
		bean.put("valor_real", valor_real);
		bean.put("sexo_pcd", sexo_pcd);
		bean.put("limite_inferior_pcd", limite_inferior_pcd);
		bean.put("limite_superior_pcd", limite_superior_pcd);
		bean.put("codigo_cups", codigo_cups);
		bean.put("detalle", detalle);

		return bean;
	}

	/**
	 * Este metodo me permite abrir la ventana para cargar los procedimientos
	 * */
	public void openPcd() {
		if (!lbxContratos.getItems().isEmpty()
				|| get_tipo() != MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE) {
			if (getConfiguracion_servicios_autorizacion().getTipo_servicio()
					.equals(IConstantes.TIPO_SERVICIO_AUTO_MEDICAMENTOS)) {
				solicitarMedicamentos();
			} else {
				solicitarProcedimiento();
			}
		} else {
			MensajesUtil
					.mensajeAlerta(
							"Advertencia",
							"Para realizar esta accion debe agregar el manual tarifario con el que se va a calcular el valor del servicio");
		}
	}

	private void solicitarProcedimiento() {
		List<String> servicios_contratados = getServiciosPermitidos();

		if (servicios_contratados.isEmpty()) {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"No se encontraron procedimientos referenciados para este tipo");
		} else {
			String pages = "/pages/openProcedimientos.zul";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", getZkWindow().getEmpresa()
					.getCodigo_empresa());
			parametros.put("codigo_sucursal", getZkWindow().getSucursal()
					.getCodigo_sucursal());
			parametros.put("restringido", "");
			parametros.put("seleccionados", seleccionados);
			parametros.put("ocultar_filtro_procedimiento", "_ok");
			parametros.put("listado_cups_contratados", servicios_contratados);

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
			ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
			ventana.setMode("modal");
		}
	}

	private List<String> getServiciosPermitidos() {
		if (get_tipo() == MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE) {
			Contratos contratos = lbxContratos.getSelectedItem().getValue();
			return ServiciosDisponiblesUtils.getFiltroServiciosPermitidos(
					contratos.getCodigo_empresa(),
					contratos.getCodigo_sucursal(),
					contratos.getCodigo_administradora(),
					contratos.getId_plan(),
					getConfiguracion_servicios_autorizacion().getId());
		} else {
			return ServiciosDisponiblesUtils
					.getFiltroServiciosPermitidosPorConfiguracion(getConfiguracion_servicios_autorizacion()
							.getId());
		}
	}

	private void solicitarMedicamentos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", getZkWindow().getEmpresa()
				.getCodigo_empresa());
		parametros.put("codigo_sucursal", getZkWindow().getSucursal()
				.getCodigo_sucursal());
		parametros.put("nro_ingreso", "");
		parametros.put("nro_identificacion", "");
		parametros.put("grupo", "01");
		parametros.put("ocultaExist", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", getAdmision());
		parametros.put("seleccionados", seleccionados);

		Component componente = Executions.createComponents(
				"/pages/openArticulo.zul", null, parametros);
		final OpenArticuloAction ventana = (OpenArticuloAction) componente;
		ventana.setSeleccionar_componente(this);
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR MEDICAMENTOS ");
		ventana.setMode("modal");
	}

	public String getTipo_pcd() {
		return tipo_pcd;
	}

	public void setTipo_pcd(String tipo_pcd) {
		this.tipo_pcd = tipo_pcd;
		if (tipo_pcd.equals(AutorizacionesAction.CIRUGIAS)) {
			UtilidadesComponentes.listarElementosListbox(false, false,
					getZkWindow().getServiceLocator(), lbxForma_realizacion);
			hbox_tipo.setVisible(true);
		}
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	public List<Map<String, Object>> getContratacion() {
		return contratacion;
	}

	public void setContratacion(List<Map<String, Object>> contratacion) {
		this.contratacion = contratacion;
		cargarContratos();
	}

	private void cargarContratos() {
		if (contratacion != null) {
			lbxContratos.getItems().clear();
			for (Map<String, Object> mapContracion : contratacion) {
				Contratos contratos = (Contratos) mapContracion
						.get(IConstantesAutorizaciones.PARAM_CONTRATO);
				List<Manuales_tarifarios> manuales_tarifarios = (List<Manuales_tarifarios>) mapContracion
						.get(IConstantesAutorizaciones.PARAM_LISTADO_MANUAL_TARIFARIO);
				Listitem listitem = new Listitem();
				listitem.setLabel(contratos.getNro_contrato() + " - "
						+ contratos.getNombre());
				listitem.setValue(contratos);
				listitem.setAttribute(
						IConstantesAutorizaciones.PARAM_LISTADO_MANUAL_TARIFARIO,
						manuales_tarifarios);
				log.info("Manuales: " + manuales_tarifarios);
				lbxContratos.appendChild(listitem);
			}
			if (lbxContratos.getItemCount() > 0) {
				lbxContratos.setSelectedIndex(0);
			}
		}
	}

	@Override
	public void afterCompose() {
		try {
			CargardorDeDatos.initComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Detalle_orden_autorizacion> getDetalles() {
		List<Detalle_orden_autorizacion> autorizacions = new ArrayList<Detalle_orden_autorizacion>();
		// log.info("Size listado orden: " + lbxContenedorServicios.getItems());
		for (Listitem listitem : lbxContenedorServicios.getItems()) {
			Detalle_orden_autorizacion detalle_orden_autorizacion = (Detalle_orden_autorizacion) ((Map<String, Object>) listitem
					.getValue()).get("detalle");
			if (getConfiguracion_servicios_autorizacion().isMostrar_tipo_pcd()) {
				detalle_orden_autorizacion
						.setTipo_quirurgico(getTipoQuirurgico());
			}
			detalle_orden_autorizacion.setObservaciones(getObservaciones());
			autorizacions.add(detalle_orden_autorizacion);
		}
		return autorizacions;
	}

	private String getObservaciones() {
		return tbxObservaciones.getValue();
	}

	private String getTipoQuirurgico() {
		if (lbxForma_realizacion.getSelectedItem() != null) {
			return lbxForma_realizacion.getSelectedItem().getValue().toString();
		} else {
			return "";
		}
	}

	public boolean isAuditor() {
		return auditor;
	}

	public void setAuditor(boolean auditor) {
		this.auditor = auditor;
	}

	public MOLD_AUTORIZACION get_tipo() {
		return _tipo;
	}

	public void set_tipo(MOLD_AUTORIZACION _tipo) {
		this._tipo = _tipo;
		if (get_tipo() == MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE) {
			// lbxContratos.setVisible(false);
			lhrValorTotal.setVisible(true);
			lhrValorUni.setVisible(true);
			// lbContrato.setVisible(false);
			groupContenedorContrato.setVisible(true);
			// tabbox.setHeight("400px");
			// lbxContenedorServicios.setHeight("200px");
			setHeight("300px");
		}
	}

	public Tabbox getTabbox() {
		return tabbox;
	}

	public void setTabbox(Tabbox tabbox) {
		this.tabbox = tabbox;
	}

	public Component getComponentPadreContenedor() {
		return componentPadreContenedor;
	}

	public void setComponentPadreContenedor(Component componentPadreContenedor) {
		this.componentPadreContenedor = componentPadreContenedor;
	}

	public Admision getAdmision() {
		return admision;
	}

	public void setAdmision(Admision admision) {
		this.admision = admision;
	}

	public Tabpanels getTabPanelsContenedorServicios() {
		return tabPanelsContenedorServicios;
	}

	public void setTabPanelsContenedorServicios(
			Tabpanels tabPanelsContenedorServicios) {
		this.tabPanelsContenedorServicios = tabPanelsContenedorServicios;
	}

	public Configuracion_servicios_autorizacion getConfiguracion_servicios_autorizacion() {
		return configuracion_servicios_autorizacion;
	}

	public void setConfiguracion_servicios_autorizacion(
			Configuracion_servicios_autorizacion configuracion_servicios_autorizacion) {
		this.configuracion_servicios_autorizacion = configuracion_servicios_autorizacion;
		if (configuracion_servicios_autorizacion != null
				&& configuracion_servicios_autorizacion.isMostrar_tipo_pcd()) {
			UtilidadesComponentes.listarElementosListbox(false,false,
					getZkWindow().getServiceLocator(),lbxForma_realizacion);
			hbox_tipo.setVisible(true);
		}
	}
}
