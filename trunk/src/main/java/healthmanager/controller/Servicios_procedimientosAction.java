/*
 * servicios_procedimientosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Servicios_procedimientos;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Servicios_procedimientosService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
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

import com.framework.interfaces.ISeleccionarComponente;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Servicios_procedimientosAction extends ZKWindow implements
		ISeleccionarComponente {

//	private static Logger log = Logger
//			.getLogger(Servicios_procedimientosAction.class);

	private Servicios_procedimientosService servicios_procedimientosService;
	private ProcedimientosService procedimientoService;

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
	private BandboxRegistrosMacro bandboxUnidad_funcional;

	@View
	private Textbox tbxUnidad_funcional;

	@View
	private Toolbarbutton btnLimpiarUnidad;

	@View
	private Listbox listboxProcedimientos;
	@View
	private Listbox lbxManual_tarifario;

	private List<String> seleccionados_procedimientos = new ArrayList<String>();

	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {
		listarParameter();
		listarManuales();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("unidad_funcional");
		listitem.setLabel("Servicio");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_cups");
		listitem.setLabel("Codigo cups");
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
			bandboxUnidad_funcional.setButtonVisible(true);

		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		listboxProcedimientos.getItems().clear();
		seleccionados_procedimientos.clear();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		if (listboxProcedimientos.getItems().isEmpty()) {
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Debe agregar procedimientos para guardar el grupo");
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			rowsResultado.getChildren().clear();

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put(parameter, value);

//			List<String> listado_unidades = new ArrayList<String>();

			parametros.remove(parameter);

//			for (String codigo_unidad : listado_unidades) {
//				parametros.put("codigo_unidad", null);
//				List<Servicios_procedimientos> listado_procedimientos = servicios_procedimientosService
//						.listar(parametros);
//				rowsResultado.appendChild(crearFilas(new Object(),
//						listado_procedimientos, this));
//			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(final Object unidad_funcional,
			final List<Servicios_procedimientos> listado_procedimientos,
			Component componente) throws Exception {
		Row fila = new Row();

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(""));
		fila.appendChild(new Label(""));
		fila.appendChild(new Label(listado_procedimientos.size() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(unidad_funcional, listado_procedimientos);
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
									eliminarDatos(listado_procedimientos.get(0));
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

	public void listarManuales() {
		lbxManual_tarifario.getChildren().clear();
		lbxManual_tarifario.appendItem("SOAT", "SOAT");
		lbxManual_tarifario.appendItem("ISS01", "ISS01");
		lbxManual_tarifario.appendItem("ISS04", "ISS04");
		lbxManual_tarifario.setSelectedIndex(0);
	}

	public void agregarDetalleServicio() throws Exception {
		if (bandboxUnidad_funcional.getRegistroSeleccionado() != null) {
			String manual = lbxManual_tarifario.getSelectedItem().getValue()
					.toString();
			openPcd(manual);
		} else {
			MensajesUtil
					.mensajeAlerta("Seleccionar servicio",
							"Debe seleccionar el servicio con el que va a agrupar los procedimientos");
		}

	}

	public void eliminarDetallesServicio() throws Exception {
		List<Listitem> listado_seleccionados = listboxProcedimientos.getItems();
		if (!listado_seleccionados.isEmpty()) {
			for (Listitem listitem : listado_seleccionados) {
				Servicios_procedimientos servicios_procedimientos = (Servicios_procedimientos) listitem
						.getValue();
				seleccionados_procedimientos.remove(servicios_procedimientos
						.getCodigo_procedimiento());
				listitem.detach();
			}
		}
	}

	public void openPcd(String manual) throws Exception {
		if (bandboxUnidad_funcional.getRegistroSeleccionado() != null) {
			String pages = "";
			String anio = this.anio + "";

			pages = "/pages/openProcedimientos.zul";

			Map parametros = new HashMap();
			parametros.put("codigo_empresa", empresa.getCodigo_empresa());
			parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parametros.put("restringido", "");
			parametros.put("anio", anio);
			parametros.put("ocultar_filtro_procedimiento",
					"ocultar_filtro_procedimiento");
			parametros.put("seleccionados", seleccionados_procedimientos);

			// log.info("parametros: " + parametros);

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
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			// log.info("ejecutando metodo @guardarDatos()");
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				List<Listitem> listado_seleccionados = listboxProcedimientos
						.getItems();
				List<Servicios_procedimientos> listado_datos = new ArrayList<Servicios_procedimientos>();
				for (Listitem listitem : listado_seleccionados) {
					listado_datos.add((Servicios_procedimientos) listitem
							.getValue());
				}

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("listado_datos", listado_datos);

				servicios_procedimientosService.guardarDatos(mapa_datos);

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object unidad_funcional,
			List<Servicios_procedimientos> listado_procedimientos)
			throws Exception {
		try {
			bandboxUnidad_funcional.seleccionarRegistro(unidad_funcional,
					"", "");
			btnLimpiarUnidad.setVisible(false);
			bandboxUnidad_funcional.setButtonVisible(false);
			listboxProcedimientos.getItems().clear();

			for (Servicios_procedimientos servicios_procedimientos : listado_procedimientos) {
				seleccionados_procedimientos.add(servicios_procedimientos
						.getCodigo_procedimiento());
				Listitem listitem = new Listitem();
				listitem.setValue(servicios_procedimientos);
				listitem.appendChild(new Listcell(servicios_procedimientos
						.getCodigo_procedimiento()));
				listitem.appendChild(new Listcell(servicios_procedimientos
						.getCodigo_cups()));

				Procedimientos procedimiento = new Procedimientos();
				procedimiento.setId_procedimiento(new Long(
						servicios_procedimientos.getCodigo_procedimiento()));
				procedimiento = procedimientoService.consultar(procedimiento);
				if (procedimiento != null) {
					listitem.appendChild(new Listcell(procedimiento
							.getDescripcion()));
				} else {
					listitem.appendChild(new Listcell(servicios_procedimientos
							.getCodigo_procedimiento()));
				}

				listboxProcedimientos.appendChild(listitem);
			}

			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Servicios_procedimientos servicios_procedimientos)
			throws Exception {
		try {
			Servicios_procedimientos servicios_procedimientos2 = new Servicios_procedimientos();
			servicios_procedimientos2
					.setCodigo_empresa(servicios_procedimientos
							.getCodigo_empresa());
			servicios_procedimientos2
					.setCodigo_sucursal(servicios_procedimientos
							.getCodigo_sucursal());
			servicios_procedimientos2.setCodigo_unidad(servicios_procedimientos
					.getCodigo_unidad());
			int result = servicios_procedimientosService
					.eliminar(servicios_procedimientos2);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
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

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		// log.info("@onSeleccionar Ejecutando evento");

//		Object unidad_funcional = bandboxUnidad_funcional
//				.getRegistroSeleccionado();

		final String codigo_procedimiento = (String) pcd
				.get("codigo_procedimiento");

		seleccionados_procedimientos.add(codigo_procedimiento);

		String codigo_cups = (String) pcd.get("codigo_cups");
		String nombre_procedimiento = (String) pcd.get("nombre_procedimiento");
		String manual_tarifario = lbxManual_tarifario.getSelectedItem()
				.getValue().toString();

		Servicios_procedimientos servicios_procedimientos = new Servicios_procedimientos();
		servicios_procedimientos.setCodigo_procedimiento(codigo_procedimiento);
		servicios_procedimientos.setCodigo_cups(codigo_cups);
		servicios_procedimientos.setCodigo_empresa(codigo_empresa);
		servicios_procedimientos.setCodigo_sucursal(codigo_sucursal);
		servicios_procedimientos.setManual(manual_tarifario);
//		servicios_procedimientos.setCodigo_unidad(unidad_funcional.getCodigo());
		servicios_procedimientos.setCreacion_date(new Timestamp(new Date()
				.getTime()));
		servicios_procedimientos.setCreacion_user(usuarios.getCodigo());

		final Listitem listitem = new Listitem();
		listitem.appendChild(new Listcell(codigo_procedimiento.trim()));
		listitem.appendChild(Utilidades.obtenerListcell(codigo_cups,
				Textbox.class, true, true));
		listitem.appendChild(Utilidades.obtenerListcell(nombre_procedimiento,
				Textbox.class, true, true));
		listitem.appendChild(new Listcell(manual_tarifario));
		listitem.setValue(servicios_procedimientos);
		listboxProcedimientos.appendChild(listitem);

	}

}