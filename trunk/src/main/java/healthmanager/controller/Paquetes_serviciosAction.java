/*
 * paquetes_serviciosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Detalles_paquetes_serviciosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Paquetes_serviciosService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;

public class Paquetes_serviciosAction extends ZKWindow implements
		ISeleccionarComponente {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3845590989007096418L;

	private static Logger log = Logger
			.getLogger(Paquetes_serviciosAction.class);

	private Paquetes_serviciosService paquetes_serviciosService;

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
	private Longbox lgxId_paquete;

	@View
	private Textbox tbxId_procedimiento_principal;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxCups_principal;

	@View
	private Textbox tbxNombre_procedimiento;

	@View
	private Toolbarbutton btnLimpiarProcedimiento;

	@View
	private Doublebox dbxValor_paquete;

	@View
	private Listbox lbxTipo_servicio;

	@View
	private Listbox listboxDetalles_paquetes;

	@View
	private Listbox lbxVia_ingreso;

	@View
	private Textbox tbxNombre_paquete;

	private final String[] idsExcluyentes = {};

	private List<String> seleccionados_procedimientos = new ArrayList<String>();
	private List<String> seleccionados_articulos = new ArrayList<String>();

	@Override
	public void init() {
		listarCombos();
		listarTipoServicios();
		listarManuales();
		parametrizarBandboxProcedimientos();
		parametrizarBandboxServicios_disponibles();
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxVia_ingreso, true,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nombre_paquete");
		listitem.setLabel("Nombre de paquete");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void listarTipoServicios() {
		lbxTipo_servicio.getChildren().clear();
		lbxTipo_servicio.appendItem("Procedimiento",
				IConstantes.DETALLE_PAQUETE_PROCEDIMIENTO);
		lbxTipo_servicio.appendItem("Medicamento/insumos",
				IConstantes.DETALLE_PAQUETE_ARTICULO);
		lbxTipo_servicio.setSelectedIndex(0);
	}

	public void listarManuales() {
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
			bandboxCups_principal.setDisabled(true);
		} else {
			limpiarDatos();
			bandboxCups_principal.setDisabled(false);
			listboxDetalles_paquetes.getItems().clear();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;
		String mensaje = "";

		if (bandboxCups_principal.getRegistroSeleccionado() == null) {
			mensaje = "debe seleccionar el cups del procedimiento principal del paquete";
			valida = false;
		}

		FormularioUtil.validarCamposObligatorios(dbxValor_paquete,
				lbxVia_ingreso);

		if (valida && listboxDetalles_paquetes.getItemCount() == 0) {
			mensaje = "Para guardar el paquete debe agregar por lo menos un detalle de servicio";
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}

	private void parametrizarBandboxProcedimientos() {
		bandboxCups_principal.inicializar(tbxNombre_procedimiento,
				btnLimpiarProcedimiento);
		BandboxRegistrosIMG<Object> bandboxRegistrosIMG = new BandboxRegistrosIMG<Object>() {

			@Override
			public void renderListitem(Listitem listitem, Object registro) {
				String codigo_cups = "";
				String descripcion = "";

				codigo_cups = ((Procedimientos) registro).getCodigo_cups();
				descripcion = ((Procedimientos) registro).getDescripcion();

				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(codigo_cups));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(descripcion));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Object> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");

				List<Object> listado_procedimientos = new ArrayList<Object>();

				Paquetes_serviciosAction.this.getServiceLocator()
						.getProcedimientosService()
						.setLimit("limit 25 offset 0");
				List<Procedimientos> listado = getServiceLocator()
						.getProcedimientosService().listar(parametros);
				for (Procedimientos procedimiento : listado) {
					listado_procedimientos.add(procedimiento);
				}

				return listado_procedimientos;

			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Object registro) {
				boolean retorno = true;
				String codigo_cups = "";
				String descripcion = "";

				codigo_cups = ((Procedimientos) registro).getCodigo_cups();
				descripcion = ((Procedimientos) registro).getDescripcion();
				tbxId_procedimiento_principal
						.setValue(((Procedimientos) registro)
								.getId_procedimiento() + "");

				bandbox.setValue(codigo_cups);
				textboxInformacion.setValue(descripcion);

				return retorno;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				listboxDetalles_paquetes.getItems().clear();
			}

		};

		bandboxCups_principal.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	private void parametrizarBandboxServicios_disponibles() {

	}

	public void agregarDetalleServicio() throws Exception {
		String tipo = lbxTipo_servicio.getSelectedItem().getValue().toString();
		if (tipo.equals(IConstantes.DETALLE_PAQUETE_PROCEDIMIENTO)) {
			openPcd();
		} else if (tipo.equals(IConstantes.DETALLE_PAQUETE_ARTICULO)) {
			openArticulo();
		}
	}

	public void openPcd() throws Exception {
		if (bandboxCups_principal.getRegistroSeleccionado() != null) {
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
			parametros.put("excluir_cups", bandboxCups_principal.getValue());
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
			ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
			ventana.setMode("modal");
		}
	}

	public void openArticulo() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("grupo1_in", Utilidades.convertirALista("01", "02"));
		parametros.put("ocultaExist", "");
		parametros.put("ocultaValor", "");
		parametros.put("seleccionados", seleccionados_articulos);

		Component componente = Executions.createComponents(
				"/pages/openArticulo.zul", null, parametros);
		final OpenArticuloAction ventana = (OpenArticuloAction) componente;
		ventana.setSeleccionar_componente(this);
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR MEDICAMENTOS/INSUMOS ");
		ventana.setMode("modal");
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		// log.info("@onSeleccionar Ejecutando evento");
		if (pcd.containsKey("codigo_articulo")) {
			final String codigo_articulo = (String) pcd.get("codigo_articulo");

			seleccionados_articulos.add(codigo_articulo);

			String nombre1 = (String) pcd.get("nombre1");

			Detalles_paquetes_servicios detalles_paquetes_servicios = new Detalles_paquetes_servicios();
			detalles_paquetes_servicios.setCodigo_detalle(codigo_articulo);
			detalles_paquetes_servicios
					.setTipo_detalle(IConstantes.DETALLE_PAQUETE_ARTICULO);
			detalles_paquetes_servicios.setCreacion_date(new Timestamp(
					new Date().getTime()));
			detalles_paquetes_servicios.setCreacion_user(usuarios.getCodigo());
			detalles_paquetes_servicios.setCodigo_item(codigo_articulo);
			detalles_paquetes_servicios.setNombre_detalle(nombre1);

			final Listitem listitem = new Listitem();
			listitem.appendChild(Utilidades.obtenerListcell(codigo_articulo,
					Textbox.class, true, true));
			listitem.appendChild(Utilidades.obtenerListcell(codigo_articulo,
					Textbox.class, true, true));
			listitem.appendChild(Utilidades.obtenerListcell(nombre1.trim(),
					Textbox.class, true, true));
			listitem.appendChild(Utilidades.obtenerListcell(
					"Medicamento/Insumo", Textbox.class, true, true));

			final Image image = new Image("/images/borrar.gif");
			image.setStyle("cursor:pointer");
			image.setTooltiptext("eliminar este item");
			image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show(
							"Esta seguro que desea eliminar este registro? ",
							"Eliminar Registro",
							Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equals(event.getName())) {
										// do the thing
										seleccionados_articulos
												.remove(codigo_articulo);
										listitem.detach();
									}
								}
							});
				}

			});
			Listcell celda = new Listcell();
			celda.appendChild(image);
			listitem.appendChild(celda);

			listitem.setValue(detalles_paquetes_servicios);

			listboxDetalles_paquetes.appendChild(listitem);

		} else {

			final String codigo_procedimiento = pcd.get("id_procedimiento")
					.toString();

			seleccionados_procedimientos.add(codigo_procedimiento);

			String codigo_cups = (String) pcd.get("codigo_cups");
			String nombre_procedimiento = (String) pcd
					.get("nombre_procedimiento");

			Detalles_paquetes_servicios detalles_paquetes_servicios = new Detalles_paquetes_servicios();
			detalles_paquetes_servicios.setCodigo_detalle(codigo_procedimiento);
			detalles_paquetes_servicios
					.setTipo_detalle(IConstantes.DETALLE_PAQUETE_PROCEDIMIENTO);
			detalles_paquetes_servicios.setCreacion_date(new Timestamp(
					new Date().getTime()));
			detalles_paquetes_servicios.setCreacion_user(usuarios.getCodigo());
			detalles_paquetes_servicios.setCodigo_item(codigo_cups);
			detalles_paquetes_servicios.setNombre_detalle(nombre_procedimiento);

			final Listitem listitem = new Listitem();
			listitem.appendChild(Utilidades.obtenerListcell(
					codigo_procedimiento.trim(), Textbox.class, true, true));
			listitem.appendChild(Utilidades.obtenerListcell(codigo_cups,
					Textbox.class, true, true));
			listitem.appendChild(Utilidades.obtenerListcell(
					nombre_procedimiento, Textbox.class, true, true));
			listitem.appendChild(Utilidades.obtenerListcell("Procedimiento",
					Textbox.class, true, true));

			final Image image = new Image("/images/borrar.gif");
			image.setStyle("cursor:pointer");
			image.setTooltiptext("eliminar este item");
			image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show(
							"Esta seguro que desea eliminar este registro? ",
							"Eliminar Registro",
							Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equals(event.getName())) {
										// do the thing
										seleccionados_procedimientos
												.remove(codigo_procedimiento);
										listitem.detach();
									}
								}
							});
				}

			});

			Listcell celda = new Listcell();
			celda.appendChild(image);
			listitem.appendChild(celda);

			listitem.setValue(detalles_paquetes_servicios);

			listboxDetalles_paquetes.appendChild(listitem);
		}
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			
			if(!value.isEmpty()){
				parameters.put("nombre_paquete", value);
			}
			
			paquetes_serviciosService.setLimit("limit 25 offset 0");

			List<Paquetes_servicios> lista_datos = paquetes_serviciosService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Paquetes_servicios paquetes_servicios : lista_datos) {
				rowsResultado.appendChild(crearFilas(paquetes_servicios, this));
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
		final Paquetes_servicios paquetes_servicios = (Paquetes_servicios) objeto;
		Elemento elemento = new Elemento();
		elemento.setCodigo(paquetes_servicios.getVia_ingreso());
		elemento.setTipo("via_ingreso");
		elemento = getServiceLocator().getServicio(ElementoService.class)
				.consultar(elemento);
		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(paquetes_servicios
				.getId_procedimiento_principal()));
		procedimientos = getServiceLocator().getProcedimientosService()
				.consultar(procedimientos);
		fila.appendChild(Utilidades.obtenerCell(
				paquetes_servicios.getId() + "", Textbox.class, true, true));
		fila.appendChild(Utilidades.obtenerCell(
				procedimientos != null ? procedimientos.getCodigo_cups() : "",
				Textbox.class, true, true));
		fila.appendChild(Utilidades.obtenerCell(
				procedimientos != null ? procedimientos.getDescripcion() : "",
				Textbox.class, true, true));
		fila.appendChild(Utilidades.obtenerCell(
				elemento != null ? elemento.getDescripcion()
						: paquetes_servicios.getVia_ingreso() + "",
				Textbox.class, true, true));
		fila.appendChild(Utilidades.obtenerCell(paquetes_servicios.getValor(),
				Doublebox.class, true, true));

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
				mostrarDatos(paquetes_servicios);
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
									eliminarDatos(paquetes_servicios);
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
			if (validarForm()) {
				// Cargamos los componentes //
				Procedimientos procedimientos = (Procedimientos) bandboxCups_principal
						.getRegistroSeleccionado();
				FormularioUtil.setUpperCase(groupboxEditar);
				Paquetes_servicios paquetes_servicios = new Paquetes_servicios();
				paquetes_servicios.setCodigo_empresa(empresa
						.getCodigo_empresa());
				paquetes_servicios.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				paquetes_servicios.setId(lgxId_paquete.getValue());
				paquetes_servicios.setId_procedimiento_principal(procedimientos
						.getId_procedimiento());
				paquetes_servicios
						.setValor((dbxValor_paquete.getValue() != null ? dbxValor_paquete
								.getValue() : 0.00));
				paquetes_servicios.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				paquetes_servicios.setCreacion_user(usuarios.getCodigo()
						.toString());
				paquetes_servicios.setVia_ingreso(lbxVia_ingreso
						.getSelectedItem().getValue().toString());
				paquetes_servicios.setNombre_paquete(tbxNombre_paquete
						.getValue().trim());
				paquetes_servicios.setCodigo_cups(procedimientos
						.getCodigo_cups());

				List<Detalles_paquetes_servicios> listado_detalles = new ArrayList<Detalles_paquetes_servicios>();

				for (int i = 0; i < listboxDetalles_paquetes.getItemCount(); i++) {
					Listitem listitem = listboxDetalles_paquetes
							.getItemAtIndex(i);
					Detalles_paquetes_servicios detalles_paquetes_servicios = (Detalles_paquetes_servicios) listitem
							.getValue();
					// log.info("detalles_paquetes_servicios ===> "
					// + detalles_paquetes_servicios);
					listado_detalles.add(detalles_paquetes_servicios);
				}

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("accion", tbxAccion.getValue());
				mapa_datos.put("paquetes_servicios", paquetes_servicios);
				mapa_datos.put("listado_detalles", listado_detalles);

				getServiceLocator()
						.getServicio(Paquetes_serviciosService.class)
						.guardarDatos(mapa_datos);

				tbxAccion.setValue("modificar");
				lgxId_paquete.setValue(paquetes_servicios.getId());

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (WrongValueException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Paquetes_servicios paquetes_servicios = (Paquetes_servicios) obj;
		try {
			lgxId_paquete.setValue(paquetes_servicios.getId());
			dbxValor_paquete.setValue(paquetes_servicios.getValor());

			Procedimientos procedimiento_prin = new Procedimientos();
			procedimiento_prin.setId_procedimiento(new Long(paquetes_servicios
					.getId_procedimiento_principal()));
			procedimiento_prin = getServiceLocator().getProcedimientosService()
					.consultar(procedimiento_prin);

			if (procedimiento_prin != null) {
				bandboxCups_principal.seleccionarRegistro(procedimiento_prin,
						procedimiento_prin.getCodigo_cups(),
						procedimiento_prin.getDescripcion());
			}

			Utilidades.seleccionarListitem(lbxVia_ingreso,
					paquetes_servicios.getVia_ingreso());

			btnLimpiarProcedimiento.setVisible(false);

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("id_paquete", paquetes_servicios.getId());

			List<Detalles_paquetes_servicios> listado_detalles = getServiceLocator()
					.getServicio(Detalles_paquetes_serviciosService.class)
					.listar(parametros);

			listboxDetalles_paquetes.getItems().clear();

			for (final Detalles_paquetes_servicios detalles_paquetes_servicios : listado_detalles) {

				String codigo_procedimiento = "";
				String codigo_cups = "";
				String nombre_procedimiento = "";

				if (detalles_paquetes_servicios.getTipo_detalle().equals(
						IConstantes.DETALLE_PAQUETE_PROCEDIMIENTO)) {

					Procedimientos procedimiento = new Procedimientos();
					procedimiento.setId_procedimiento(new Long(
							detalles_paquetes_servicios.getCodigo_detalle()));
					procedimiento = getServiceLocator()
							.getProcedimientosService()
							.consultar(procedimiento);
					if (procedimiento != null) {
						codigo_procedimiento = procedimiento
								.getId_procedimiento() + "";
						codigo_cups = procedimiento.getCodigo_cups();
						nombre_procedimiento = procedimiento.getDescripcion();
					}

					seleccionados_procedimientos.add(codigo_procedimiento);

				} else {
					Articulo articulo = new Articulo();
					articulo.setCodigo_empresa(codigo_empresa);
					articulo.setCodigo_sucursal(codigo_sucursal);
					articulo.setCodigo_articulo(detalles_paquetes_servicios
							.getCodigo_detalle());

					articulo = getServiceLocator().getArticuloService()
							.consultar(articulo);

					if (articulo != null) {
						codigo_procedimiento = articulo.getCodigo_articulo();
						codigo_cups = articulo.getCodigo_articulo();
						nombre_procedimiento = articulo.getNombre1();
					}
				}

				final Listitem listitem = new Listitem();
				listitem.appendChild(Utilidades.obtenerListcell(
						codigo_procedimiento, Textbox.class, true, true));
				listitem.appendChild(Utilidades.obtenerListcell(codigo_cups,
						Textbox.class, true, true));
				listitem.appendChild(Utilidades.obtenerListcell(
						nombre_procedimiento, Textbox.class, true, true));
				listitem.appendChild(Utilidades
						.obtenerListcell(
								detalles_paquetes_servicios.getTipo_detalle()
										.equals("01") ? "Procedimiento"
										: "Medicamento/insumos", Textbox.class,
								true, true));

				final String codigo_procedimiento_aux = codigo_procedimiento;

				final Image image = new Image("/images/borrar.gif");
				image.setStyle("cursor:pointer");
				image.setTooltiptext("eliminar este item");
				image.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								Messagebox
										.show("Esta seguro que desea eliminar este registro? ",
												"Eliminar Registro",
												Messagebox.YES + Messagebox.NO,
												Messagebox.QUESTION,
												new org.zkoss.zk.ui.event.EventListener<Event>() {
													public void onEvent(
															Event event)
															throws Exception {
														if ("onYes".equals(event
																.getName())) {
															// do the thing
															if (detalles_paquetes_servicios
																	.getTipo_detalle()
																	.equals("01")) {
																seleccionados_procedimientos
																		.remove(codigo_procedimiento_aux);
															} else {
																seleccionados_articulos
																		.remove(codigo_procedimiento_aux);
															}
															listitem.detach();
														}
													}
												});
							}

						});

				Listcell celda = new Listcell();
				celda.appendChild(image);
				listitem.appendChild(celda);

				listitem.setValue(detalles_paquetes_servicios);

				seleccionados_articulos.add(codigo_procedimiento);

				listboxDetalles_paquetes.appendChild(listitem);

			}

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Paquetes_servicios paquetes_servicios = (Paquetes_servicios) obj;
		try {
			int result = paquetes_serviciosService.eliminar(paquetes_servicios);
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
}