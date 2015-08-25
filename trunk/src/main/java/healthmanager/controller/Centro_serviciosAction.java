/*
 * centro_serviciosAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_servicios;
import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.bean.Servicios_via_ingreso;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.Centro_serviciosService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;
import healthmanager.modelo.service.Servicios_disponiblesService;
import healthmanager.modelo.service.Servicios_via_ingresoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zul.Bandbox;
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

import com.framework.constantes.IControlesConvinacionesTeclas;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Centro_serviciosAction extends ZKWindow implements
		WindowRegistrosIMG {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(Centro_serviciosAction.class);

	private Centro_serviciosService centro_serviciosService;
	private Servicios_via_ingresoService servicios_via_ingresoService;
	private Centro_atencionService centro_atencionService;

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
	private BandboxRegistrosMacro tbxCodigo_centro;
	@View
	private Textbox tbxNombreCentro;
	@View
	private Toolbarbutton btnLimpiarCentroAtencion;
	@View
	private Listbox listboxCentros;
	@View
	private Listbox listboxServicios;
	private final String[] idsExcluyentes = {};

	@View
	private Toolbarbutton tbbuttonEliminar;
	@View
	private Toolbarbutton tbbuttonServiciosEliminar;

	/**
	 * Este es el modo de adicion de servicios
	 * */
	@View
	private Listbox lbxModo;
	@View
	private Groupbox grbIndividual;
	@View
	private Groupbox grbAgrupado;

	/* listado de guardado */
	private Map<String, Centro_atencion> mapa_centros;
	private List<String> listado_centros_seleccionados = new ArrayList<String>();
	public static final String CENTROS_ATENCION = "cent_s";

	private Map<String, Servicios_via_ingreso> mapa_servicios;
	private List<String> listado_servicios_seleccionados = new ArrayList<String>();
	public static final String SERVICIO_VIA_INGRESO = "SERVICIO_VIA_INGRESO";

	@Override
	public void init() {
		tbxCodigo_centro.inicializar(tbxNombreCentro, btnLimpiarCentroAtencion);
		mapa_centros = new HashMap<String, Centro_atencion>();
		mapa_servicios = new HashMap<String, Servicios_via_ingreso>();
		listarCombos();
		parametrizarBandbox();
		cargarEventoConvinacionTeclas();
		cargarEventoModoAdicion();
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
				if (evt.getKeyCode() == IControlesConvinacionesTeclas.ALT_A) {
					abrirWindowCentrosSalud();
				} else if (evt.getKeyCode() == IControlesConvinacionesTeclas.ALT_S) {
					abrirWindowServiciosDisponibles();
				}
			}
		});
	}

	private void parametrizarBandbox() {
		inicializarBandboxCentroServicio();
	}

	private void inicializarBandboxCentroServicio() {
		tbxCodigo_centro
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getCodigo_centro() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNombre_centro() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getDireccion()
								+ ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getTelefonos()
								+ ""));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", valorBusqueda.toLowerCase()
								.trim());

						// centro_atencionService.setLimit("limit 25 offset 0");
						return centro_atencionService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {
						bandbox.setValue(registro.getCodigo_centro());
						textboxInformacion.setValue(registro.getNombre_centro());
						cargarServiciosCentroSalud(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						validarAccionEliminarServicios();
					}
				});
	}

	/**
	 * Este metodo me permite cargar los servicios de ese centro de salub
	 * 
	 * @author Luis Miguel
	 * */
	protected void cargarServiciosCentroSalud(Centro_atencion centro_atencion) {
		mapa_servicios.clear();
		mapa_centros.clear();
		listado_centros_seleccionados.clear();
		listado_servicios_seleccionados.clear();
		listboxServicios.getItems().clear();
		listboxCentros.getItems().clear();

		mapa_centros.put(centro_atencion.getCodigo_centro(), centro_atencion);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", centro_atencion.getCodigo_empresa());
		params.put("codigo_sucursal", centro_atencion.getCodigo_sucursal());
		params.put("codigo_centro", centro_atencion.getCodigo_centro());
		List<Centro_servicios> servicios = centro_serviciosService
				.listar(params);
		for (Centro_servicios centro_servicios : servicios) {
			Servicios_via_ingreso servicios_via_ingreso = new Servicios_via_ingreso();
			servicios_via_ingreso.setCodigo_registro(centro_servicios
					.getServicio_via_ingreso());
			servicios_via_ingreso = servicios_via_ingresoService
					.consultar(servicios_via_ingreso);
			if (servicios_via_ingreso != null) {
				onSeleccionarRegistroServicios(servicios_via_ingreso);
			}
		}
		validarAccionEliminarServicios();
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
		mapa_centros.clear();
		mapa_servicios.clear();
		listado_centros_seleccionados.clear();
		listado_servicios_seleccionados.clear();
		listboxCentros.getItems().clear();
		listboxServicios.getItems().clear();
		accionCambioModo();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";

		if (lbxModo.getSelectedIndex() == 0) {
			if (tbxCodigo_centro.getValue().trim().isEmpty()) {
				valida = false;
			}
		} else {
			if (mapa_centros.isEmpty()) {
				valida = false;
				msj = "Para realizar esta accion debe agregar por lo menos 1 centro de salud";
			}
		}

		if (mapa_servicios.isEmpty() && valida) {
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

			centro_serviciosService.setLimit("limit 25 offset 0");

			List<Centro_servicios> lista_datos = centro_serviciosService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Centro_servicios centro_servicios : lista_datos) {
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

		final Centro_servicios centro_servicios = (Centro_servicios) objeto;

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
				params.put("centros_salud", mapa_centros);
				params.put("servicios", mapa_servicios);
				params.put("codigo_usuario", usuarios.getCodigo());
				centro_serviciosService.guardar(params);
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
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
			Centro_atencion centro_atencion = (Centro_atencion) tbxCodigo_centro
					.getRegistroSeleccionado();
			mapa_centros.put(centro_atencion.getCodigo_centro(),
					centro_atencion);
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Centro_servicios centro_servicios = (Centro_servicios) obj;
		try {
			tbxCodigo_centro.setValue(centro_servicios.getCodigo_centro());
			// tbxCodigo_servicios.setValue(centro_servicios.getCodigo_servicios());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Centro_servicios centro_servicios = (Centro_servicios) obj;
		try {
			int result = centro_serviciosService.eliminar(centro_servicios);
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
	public void abrirWindowCentrosSalud() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		// parametros.put("no_rol_medico", "");
		String columnas = "Codigo#100px|Nombre|Direccion#300px|Telefono#100px";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Consultar centros de salud", Paquetes.HEALTHMANAGER,
				"Centro_atencionDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(listado_centros_seleccionados);
	}

	public void abrirWindowServiciosDisponibles() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("via_ingreso_servicio", true);
		String columnas = "Servicio|Via de ingreso";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Consultar servicios disponibles", Paquetes.HEALTHMANAGER,
				"Servicios_via_ingresoDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(listado_servicios_seleccionados);
	}

	public void eliminarCentrosSaludSeleccionados() {
		final Set<Listitem> listitems = listboxCentros.getSelectedItems();
		if (listitems.isEmpty()) {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"Para eliminar los registros debe haber seleccionado por lo menos 1");
		} else {
			Messagebox.show(
					"¿Estas seguro que deseas eliminar estos registros?",
					"Advertencia", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								for (Listitem listitem : listitems) {
									Centro_atencion centro_atencion = listitem
											.getValue();
									mapa_centros.remove(centro_atencion
											.getCodigo_centro());
								}
								listboxCentros.getItems().removeAll(listitems);
								validarAccionEliminarCentrosSalud();
							}
						}
					});
		}
	}

	public void validarAccionEliminarCentrosSalud() {
		tbbuttonEliminar.setDisabled(mapa_centros.isEmpty());
	}

	public void eliminarCentrosServicios() {
		final Set<Listitem> listitems = listboxServicios.getSelectedItems();
		if (listitems.isEmpty()) {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"Para eliminar los registros debe haber seleccionado por lo menos 1");
		} else {
			Messagebox.show(
					"¿Estas seguro que deseas eliminar estos registros?",
					"Advertencia", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event evt) throws Exception {
							if ("onYes".equals(evt.getName())) {
								for (Listitem listitem : listitems) {
									Servicios_via_ingreso servicios_via_ingreso = listitem
											.getValue();
									mapa_servicios.remove(servicios_via_ingreso
											.getCodigo_registro() + "");
								}
								listboxServicios.getItems()
										.removeAll(listitems);
								validarAccionEliminarServicios();
							}
						}
					});
		}
	}

	public void validarAccionEliminarServicios() {
		tbbuttonServiciosEliminar.setDisabled(mapa_servicios.isEmpty());
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			if (registro instanceof Centro_atencion) {
				onSeleccionarCentroAtencion((Centro_atencion) registro);
			} else if (registro instanceof Servicios_via_ingreso) {
				onSeleccionarRegistroServicios((Servicios_via_ingreso) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if (registro instanceof Centro_atencion) {
			return celdasListitemCentroAtencion((Centro_atencion) registro);
		} else if (registro instanceof Servicios_via_ingreso) {
			Servicios_disponibles servicios_disponibles = new Servicios_disponibles();
			servicios_disponibles.setCodigo(((Servicios_via_ingreso) registro)
					.getCodigo_servicio());
			servicios_disponibles = getServiceLocator().getServicio(
					Servicios_disponiblesService.class).consultar(
					servicios_disponibles);
			if (servicios_disponibles != null)
				return new Object[] {
						servicios_disponibles.getNombre(),
						((Servicios_via_ingreso) registro)
								.getElemento_via_ingreso().getDescripcion() };
			else
				return new Object[] {
						((Servicios_via_ingreso) registro).getCodigo_servicio(),
						((Servicios_via_ingreso) registro).getVia_ingreso() };
		}
		return null;
	}

	public Object[] celdasListitemCentroAtencion(Centro_atencion centro_atencion) {
		return new Object[] { centro_atencion.getCodigo_centro(),
				centro_atencion.getNombre_centro(),
				centro_atencion.getDireccion(), centro_atencion.getTelefonos() };
	}

	// Metodos de seleccion de registros

	public void onSeleccionarCentroAtencion(Centro_atencion centro_atencion) {
		crearFilasCentrosSalud(centro_atencion);
		validarAccionEliminarCentrosSalud();
	}

	private void crearFilasCentrosSalud(Centro_atencion centro_atencion) {
		Listitem listitem = new Listitem();
		listitem.setValue(centro_atencion);

		// nombre
		Listcell listcell = new Listcell(""
				+ centro_atencion.getNombre_centro());
		listitem.appendChild(listcell);

		// direccion
		listcell = new Listcell("" + centro_atencion.getDireccion());
		listitem.appendChild(listcell);

		mapa_centros.put(centro_atencion.getCodigo_centro(), centro_atencion);
		listado_centros_seleccionados.add(centro_atencion.getCodigo_centro());
		listboxCentros.appendChild(listitem);
	}

	public void onSeleccionarRegistroServicios(
			Servicios_via_ingreso servicios_via_ingreso) {
		Listitem listitem = new Listitem();
		listitem.setValue(servicios_via_ingreso);
		Servicios_disponibles servicios_disponibles = new Servicios_disponibles();
		servicios_disponibles.setCodigo(servicios_via_ingreso
				.getCodigo_servicio());
		servicios_disponibles = getServiceLocator().getServicio(
				Servicios_disponiblesService.class).consultar(
				servicios_disponibles);
		if (servicios_disponibles != null) {
			listitem.appendChild(new Listcell(servicios_disponibles.getNombre()));
			listitem.appendChild(new Listcell(servicios_via_ingreso
					.getElemento_via_ingreso().getDescripcion()));
		} else {
			listitem.appendChild(new Listcell(servicios_via_ingreso
					.getCodigo_servicio()));
			listitem.appendChild(new Listcell(servicios_via_ingreso
					.getElemento_via_ingreso().getDescripcion()));
		}
		mapa_servicios.put(servicios_via_ingreso.getCodigo_registro() + "",
				servicios_via_ingreso);
		listboxServicios.appendChild(listitem);
	}

}