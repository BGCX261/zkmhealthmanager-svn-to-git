/*
 * maestro_manualAction.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_procedimientos;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Maestro_manualService;
import healthmanager.modelo.service.Manuales_procedimientosService;
import healthmanager.modelo.service.ProcedimientosService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.res.Res;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Maestro_manualAction extends ZKWindow implements
		ISeleccionarComponente {

//	private static Logger log = Logger.getLogger(Maestro_manualAction.class);

	private Maestro_manualService maestro_manualService;

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
	private Listbox listboxResultado;
	@View
	private Checkbox checkboxSeleccionar_todos;

	@View
	private Longbox lgxId_manual;
	@View
	private Textbox tbxManual;
	@View
	private Listbox lbxTipo_manual;
	@View
	private Listbox lbxTipo_moneda;
	@View
	private Div divPopups;

	@View
	private Row rowId_manual;

	@View
	private Listbox listboxProcedimientos;
	@View private Listheader lhrNroCuenta;

	private List<String> procedimientos_seleccionados = new ArrayList<String>();

	private Map<Long, Object> mapa_datos_procedimientos = new TreeMap<Long, Object>();

	@View
	private Bandbox bandboxBusqueda;

	private String parametro_current = "";

	private List<Integer> listado_resultados = new ArrayList<Integer>();
	private Integer index_current = -1;

	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
		validarTipoSucursal();
	}

	// Esto es para medicos y auditores
	private void validarTipoSucursal() {
		  if (getSucursal().getTipo().equals(
				IConstantes.TIPOS_SUCURSAL_MEDICO_AUDITORES)) {
			 lhrNroCuenta.setVisible(true); 
		 }
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxTipo_manual, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_moneda, true,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		// listitem.setValue("id_manual");
		// listitem.setLabel("Id_manual");
		// lbxParameter.appendChild(listitem);

		// listitem = new Listitem();
		listitem.setValue("manual");
		listitem.setLabel("Manual");
		lbxParameter.appendChild(listitem);

		// listitem = new Listitem();
		// listitem.setValue("tipo_manual");
		// listitem.setLabel("Tipo_manual");
		// lbxParameter.appendChild(listitem);
		//
		// listitem = new Listitem();
		// listitem.setValue("tipo_moneda");
		// listitem.setLabel("Tipo_moneda");
		// lbxParameter.appendChild(listitem);

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
			rowId_manual.setVisible(true);
			lbxTipo_manual.setDisabled(true);
			listboxProcedimientos.setVisible(true);
			listboxProcedimientos.invalidate();
		} else {
			// buscarDatos();
			limpiarDatos();
			rowId_manual.setVisible(false);
			lbxTipo_manual.setDisabled(false);
			listboxProcedimientos.setVisible(false);
			procedimientos_seleccionados.clear();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		mapa_datos_procedimientos.clear();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;
		XulElement xulElement = null;
		if (tbxManual.getValue().trim().isEmpty()) {
			MensajesUtil.notificarError("El campo Manual es obligatorio",
					tbxManual);
			valida = false;
			if (xulElement == null)
				xulElement = tbxManual;
		}
		if (lbxTipo_manual.getSelectedItem().getValue().toString().trim()
				.isEmpty()) {
			MensajesUtil.notificarError("El campo Tipo manual es obligatorio",
					lbxTipo_manual);
			valida = false;
			if (xulElement == null)
				xulElement = lbxTipo_manual;
		}
		if (lbxTipo_moneda.getSelectedItem().getValue().toString().trim()
				.isEmpty()) {
			MensajesUtil.notificarError("El campo Tipo moneda es obligatorio",
					lbxTipo_moneda);
			valida = false;
			if (xulElement == null)
				xulElement = lbxTipo_moneda;
		}

		if (xulElement != null) {
			Clients.scrollIntoView(xulElement);
			xulElement.setFocus(true);
		}
		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			String value = tbxValue.getValue().trim().toUpperCase();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();

			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			parameters.put("limite_paginado","limit 25 offset 0");

			List<Maestro_manual> lista_datos = maestro_manualService
					.listar(parameters);
			listboxResultado.getItems().clear();
			divPopups.getChildren().clear();

			for (Maestro_manual maestro_manual : lista_datos) {
				listboxResultado.appendChild(crearFilas(maestro_manual, this));
			}

			listboxResultado.applyProperties();
			listboxResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		Listitem fila = new Listitem();

		final Maestro_manual maestro_manual = (Maestro_manual) objeto;

		fila.appendChild(new Listcell(maestro_manual.getId_manual() + ""));
		fila.appendChild(new Listcell(maestro_manual.getManual() + ""));
		fila.appendChild(new Listcell(Utilidades.getNombreElemento(
				maestro_manual.getTipo_manual(), "tipo_manual",
				getServiceLocator())));
		fila.appendChild(new Listcell(Utilidades.getNombreElemento(
				maestro_manual.getTipo_moneda(), "tipo_moneda",
				getServiceLocator())));

		Hlayout hlayout = new Hlayout();
		fila.setStyle("text-align: justify;nowrap:nowrap");
		Toolbarbutton toolbarbutton = new Toolbarbutton("Editar");
		toolbarbutton.setImage("/images/editar.gif");
		toolbarbutton.setTooltiptext("Editar registro");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(maestro_manual);
					}
				});
		hlayout.appendChild(toolbarbutton);
		toolbarbutton = new Toolbarbutton("Eliminar");
		toolbarbutton.setImage("/images/borrar.gif");
		toolbarbutton.setTooltiptext("Eliminar registro");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que desea eliminar este registro? ",
										"Eliminar Registro",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													eliminarDatos(maestro_manual);
													buscarDatos();
												}
											}
										});
					}
				});
		hlayout.appendChild(toolbarbutton);
		Listcell listcell = new Listcell();
		listcell.appendChild(hlayout);
		fila.appendChild(listcell);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			//log.info("ejecutando metodo guardarDatos()");
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Maestro_manual maestro_manual = new Maestro_manual();
				maestro_manual
						.setId_manual((lgxId_manual.getValue() != null ? lgxId_manual
								.getValue() : 0L));
				maestro_manual.setCodigo_empresa(codigo_empresa);
				maestro_manual.setCodigo_sucursal(codigo_sucursal);
				maestro_manual.setManual(tbxManual.getValue());
				maestro_manual.setTipo_manual(lbxTipo_manual.getSelectedItem()
						.getValue().toString());
				maestro_manual.setTipo_moneda(lbxTipo_moneda.getSelectedItem()
						.getValue().toString());
				maestro_manual.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				maestro_manual
						.setCreacion_user(usuarios.getCodigo().toString());
				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("maestro_manual", maestro_manual);
				mapa_datos.put("accion", tbxAccion.getValue());
				mapa_datos.put("mapa_datos_procedimientos",
						mapa_datos_procedimientos);
				maestro_manualService.guardarDatos(mapa_datos);
				tbxAccion.setValue("modificar");
				mostrarDatos(maestro_manual);
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Maestro_manual maestro_manual = (Maestro_manual) obj;
		try {
			procedimientos_seleccionados.clear();
			mapa_datos_procedimientos.clear();
			lgxId_manual.setValue(maestro_manual.getId_manual());
			tbxManual.setValue(maestro_manual.getManual());
			Utilidades.seleccionarListitem(lbxTipo_manual,
					maestro_manual.getTipo_manual());
			Utilidades.seleccionarListitem(lbxTipo_moneda,
					maestro_manual.getTipo_moneda());

			listboxProcedimientos.getItems().clear();

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("id_manual", maestro_manual.getId_manual());

			List<Manuales_procedimientos> listado_procedimientos = getServiceLocator()
					.getServicio(Manuales_procedimientosService.class).listar(
							parametros);

			for (Manuales_procedimientos manuales_procedimientos : listado_procedimientos) {
				Long id_procedimiento = manuales_procedimientos
						.getId_procedimiento();
				Procedimientos procedimientos = new Procedimientos();
				procedimientos.setId_procedimiento(id_procedimiento);
				procedimientos = getServiceLocator().getProcedimientosService()
						.consultar(procedimientos);
				Map<String, Object> pcd = OpenProcedimientosAction
						.getProcedimientoMap(codigo_empresa, codigo_sucursal,
								procedimientos);
				mapa_datos_procedimientos.put(id_procedimiento, pcd);
				pcd.put("codigo_manual",
						manuales_procedimientos.getCodigo_manual());
				pcd.put("valor", manuales_procedimientos.getValor());
				pcd.put("grupo_uvr", manuales_procedimientos.getGrupo_uvr());
				pcd.put("nro_cuenta",
						manuales_procedimientos.getNro_cuenta_contable() != null ? manuales_procedimientos
								.getNro_cuenta_contable() : "");  
				adicionarProcedimiento(pcd);
				procedimientos_seleccionados.add(id_procedimiento + "");
			}

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Maestro_manual maestro_manual = (Maestro_manual) obj;
		try {
			int result = maestro_manualService.eliminar(maestro_manual);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se elimino satisfactoriamente !!",
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

	public void agregarTodosProcedimientos() {
		Messagebox.show(
				"¿Esta seguro que desea agregar todos los procedimientos?",
				"Eliminar Registro", Messagebox.YES + Messagebox.NO,
				Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						if ("onYes".equals(event.getName())) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							getServiceLocator().getServicio(
									ProcedimientosService.class).setLimit(null);
							List<Procedimientos> listado = getServiceLocator()
									.getServicio(ProcedimientosService.class)
									.listar(parametros);
							for (Procedimientos procedimientos : listado) {
								if (!mapa_datos_procedimientos
										.containsKey(procedimientos
												.getId_procedimiento())) {
									Map<String, Object> pcd = OpenProcedimientosAction
											.getProcedimientoMap(
													codigo_empresa,
													codigo_sucursal,
													procedimientos);
									mapa_datos_procedimientos.put(
											procedimientos
													.getId_procedimiento(), pcd);
									procedimientos_seleccionados
											.add(procedimientos
													.getId_procedimiento() + "");
								}
							}
							renderizarMapaDatos();
						}
					}
				});

	}

	public void openProcedimientos() {
		String pages = "";
		String anio = this.anio + "";

		pages = "/pages/openProcedimientos.zul";

		Map parametros = new HashMap();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("anio", anio);
		parametros.put("contratos", "");
		parametros.put("ocultar_filtro_procedimiento",
				"ocultar_filtro_procedimiento");
		parametros.put("seleccionados", procedimientos_seleccionados);

		OpenProcedimientosAction componente = (OpenProcedimientosAction) Executions
				.createComponents(pages, this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
		ventana.setMode("modal");
		componente.setSeleccionar_componente(this);
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		Long id_procedimiento = (Long) pcd.get("id_procedimiento");
		mapa_datos_procedimientos.put(id_procedimiento, pcd);
		adicionarProcedimiento(pcd);
		procedimientos_seleccionados.add(id_procedimiento + "");
	}

	public void adicionarProcedimiento(Map<String, Object> pcd) {
		String codigo_cups = (String) pcd.get("codigo_cups");
		String nombre_procedimiento = (String) pcd.get("nombre_procedimiento");

		String codigo_manual = (String) pcd.get("codigo_manual");

		Double valor_defecto = 0D;

		String tipo = lbxTipo_manual.getSelectedItem().getValue().toString();

		if (tipo.equals(IConstantes.TIPO_MANUAL_SOAT)) {
			valor_defecto = (Double) pcd.get("porcentaje_defecto");
		} else if (tipo.equals(IConstantes.TIPO_MANUAL_ISS01)) {
			valor_defecto = (Double) pcd.get("valoriss01_defecto");
		} else if (tipo.equals(IConstantes.TIPO_MANUAL_ISS04)) {
			valor_defecto = (Double) pcd.get("valoriss04_defecto");
		}

		Listitem listitem = new Listitem();
		listitem.setValue(pcd);

		listitem.appendChild(new Listcell());
		listitem.appendChild(new Listcell(codigo_cups));

		Textbox tbxCodigo_manual = new Textbox(
				codigo_manual != null ? codigo_manual : codigo_cups);
		tbxCodigo_manual.setHflex("1");
		Listcell listcell = new Listcell();
		listcell.appendChild(tbxCodigo_manual);
		listitem.appendChild(listcell);

		Res.cargarAutomatica(tbxCodigo_manual, pcd, "codigo_manual", null);
		pcd.put("codigo_manual", codigo_manual != null ? codigo_manual
				: codigo_cups);

		Textbox tbxNombre = new Textbox(nombre_procedimiento);
		tbxNombre.setHflex("1");
		tbxNombre.setReadonly(true);
		listcell = new Listcell();
		listcell.appendChild(tbxNombre);
		listitem.appendChild(listcell);

		Double valor = (Double) pcd.get("valor");

		Doublebox dbxValor = new Doublebox();
		dbxValor.setValue(valor != null ? valor : valor_defecto);
		dbxValor.setHflex("1");
		listcell = new Listcell();
		listcell.appendChild(dbxValor);
		listitem.appendChild(listcell);

		Res.cargarAutomatica(dbxValor, pcd, "valor", null);
		pcd.put("valor", valor != null ? valor : valor_defecto);

		String grupo_uvr = (String) pcd.get("grupo_uvr");

		Textbox tbxGrupoUvr = new Textbox(grupo_uvr != null ? grupo_uvr : "");
		tbxGrupoUvr.setHflex("1");
		listcell = new Listcell();
		listcell.appendChild(tbxGrupoUvr);
		listitem.appendChild(listcell);

		Res.cargarAutomatica(tbxGrupoUvr, pcd, "grupo_uvr", null);
		pcd.put("grupo_uvr", grupo_uvr != null ? grupo_uvr : "");
		
		
		
		String nro_cuenta = (String) pcd.get("nro_cuenta");

		Textbox tbxNro_cuenta = new Textbox(nro_cuenta != null ? nro_cuenta : "");
		tbxNro_cuenta.setHflex("1");
		listcell = new Listcell();
		listcell.appendChild(tbxNro_cuenta);
		listitem.appendChild(listcell);

		Res.cargarAutomatica(tbxNro_cuenta, pcd, "nro_cuenta", null);
		pcd.put("nro_cuenta", nro_cuenta != null ? nro_cuenta : "");

		listboxProcedimientos.appendChild(listitem);
	}

	public void seleccionarTodos() {
		List<Listitem> listado_items = listboxProcedimientos.getItems();
		for (Listitem listitem : listado_items) {
			listitem.setSelected(checkboxSeleccionar_todos.isChecked());
		}
	}

	public void verificarSeleccion() {
		List<Listitem> listado_items = listboxProcedimientos.getItems();
		if (!listado_items.isEmpty()) {
			boolean todos = true;
			for (Listitem listitem : listado_items) {
				if (!listitem.isSelected()) {
					todos = false;
					break;
				}
			}
			checkboxSeleccionar_todos.setChecked(todos);
		} else {
			checkboxSeleccionar_todos.setChecked(false);
		}
	}

	public void quitarItemsSeleccionados() {
		Set<Listitem> listado_items = listboxProcedimientos.getSelectedItems();
		for (Listitem listitem : listado_items) {
			Map<String, Object> pcd = (Map<String, Object>) listitem.getValue();
			Long id_procedimiento = (Long) pcd.get("id_procedimiento");
			mapa_datos_procedimientos.remove(id_procedimiento);
			procedimientos_seleccionados.remove(id_procedimiento + "");
		}
		renderizarMapaDatos();
		verificarSeleccion();
	}

	public void renderizarMapaDatos() {
		listboxProcedimientos.getItems().clear();
		for (Long key_map : mapa_datos_procedimientos.keySet()) {
			Map<String, Object> pcd = (Map<String, Object>) mapa_datos_procedimientos
					.get(key_map);
			adicionarProcedimiento(pcd);
		}
		listboxProcedimientos.invalidate();
	}

	public void buscarProcedimientos() {
		String parametro = bandboxBusqueda.getValue().trim();
		if (!parametro.isEmpty()) {
			List<Listitem> listado_items = listboxProcedimientos.getItems();
			if (parametro.toUpperCase().equals(parametro_current)) {
				index_current++;
				if (index_current >= listado_resultados.size()) {
					index_current = 0;
				}
			} else {
				parametro_current = parametro.toUpperCase();
				listado_resultados.clear();
				for (int i = 0; i < listado_items.size(); i++) {
					Listitem listitem = listado_items.get(i);
					Map<String, Object> pcd = (Map<String, Object>) listitem
							.getValue();
					String codigo_cups = (String) pcd.get("codigo_cups");
					String nombre_procedimiento = (String) pcd
							.get("nombre_procedimiento");
					if (codigo_cups.toUpperCase().contains(
							parametro.toUpperCase())
							|| nombre_procedimiento.toUpperCase().contains(
									parametro.toUpperCase())) {
						listado_resultados.add(i);
					}
				}

				if (!listado_resultados.isEmpty()) {
					index_current = 0;
				} else {
					index_current = -1;
				}
			}

			if (index_current != -1) {
				Listitem listitem = listado_items.get(listado_resultados
						.get(index_current));
				listboxProcedimientos.setActivePage(listitem);
				Clients.scrollIntoView(listitem);
				MensajesUtil.notificarInformacion("Resultado encontrado",
						listitem);
			} else {
				MensajesUtil.notificarAlerta("No se encontraron resultado",
						bandboxBusqueda);
			}

		}
	}

}