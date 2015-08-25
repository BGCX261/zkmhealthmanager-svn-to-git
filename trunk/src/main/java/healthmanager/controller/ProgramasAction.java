/*
 * programasAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Programas;
import healthmanager.modelo.bean.Programas_actividades_pyp;
import healthmanager.modelo.service.ProgramasService;
import healthmanager.modelo.service.Programas_actividades_pypService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
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
import org.zkoss.zul.Window;

import com.framework.interfaces.ISeleccionarComponente;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class ProgramasAction extends ZKWindow implements ISeleccionarComponente {

	private static Logger log = Logger.getLogger(ProgramasAction.class);

	private ProgramasService programasService;

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
	private Textbox tbxCodigo_programa;
	@View
	private Textbox tbxNombre_programa;
	@View
	private Listbox lbxVia_ingreso;
	@View
	private Listbox lbxTipo_programa;
	@View
	private Checkbox chbFacturacion_recuperacion;

	@View
	private Label lbCodigo_programa;

	@View
	private Listbox lbxManual_tarifario;

	@View
	private Listbox listboxActividades;

	private Map<String, String> mapa_seleccionados = new TreeMap<String, String>();

	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxTipo_programa, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxVia_ingreso, true,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_programa");
		listitem.setLabel("Codigo de programa");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre_programa");
		listitem.setLabel("Nombre de programa");
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
			lbCodigo_programa.setVisible(true);
			tbxCodigo_programa.setVisible(true);
		} else {
			limpiarDatos();
			lbCodigo_programa.setVisible(false);
			tbxCodigo_programa.setVisible(false);
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

		FormularioUtil.validarCamposObligatorios(tbxCodigo_programa,
				tbxNombre_programa, lbxVia_ingreso, lbxTipo_programa);

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (parameter.equals("codigo_programa")) {
				parameters.put("codigo_programa", value.trim().toLowerCase());
			} else if (parameter.equals("nombre_programa")) {
				parameters.put("nombre_programa", value.trim().toLowerCase());
			}

			//log.info("parametros de busqueda ===>" + parameters);

			programasService.setLimit("limit 25 offset 0");

			List<Programas> lista_datos = programasService.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Programas programas : lista_datos) {
				rowsResultado.appendChild(crearFilas(programas, this));
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

		final Programas programas = (Programas) objeto;

		Elemento elemento = new Elemento();
		elemento.setCodigo(programas.getVia_ingreso());
		elemento.setTipo("via_ingreso");
		elemento = getServiceLocator().getElementoService().consultar(elemento);
		programas.setElemento_via_ingreso(elemento);

		elemento = new Elemento();
		elemento.setCodigo(programas.getTipo_programa());
		elemento.setTipo("tipo_programa");
		elemento = getServiceLocator().getElementoService().consultar(elemento);
		programas.setElemento_tipo_programa(elemento);

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(Utilidades.obtenerCell(programas.getCodigo_programa(),
				Textbox.class, true, true));
		fila.appendChild(Utilidades.obtenerCell(programas.getNombre_programa(),
				Textbox.class, true, true));
		fila.appendChild(Utilidades.obtenerCell(
				programas.getElemento_via_ingreso() != null ? programas
						.getElemento_via_ingreso().getDescripcion() : programas
						.getVia_ingreso(), Textbox.class, true, true));
		fila.appendChild(Utilidades.obtenerCell(
				programas.getElemento_tipo_programa() != null ? programas
						.getElemento_tipo_programa().getDescripcion()
						: programas.getTipo_programa(), Textbox.class, true,
				true));
		fila.appendChild(Utilidades.obtenerCell(programas
				.getFacturacion_recuperacion().equals("S") ? "Si" : "No",
				Textbox.class, true, true));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(programas);
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
									eliminarDatos(programas);
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
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //
				Programas programas = new Programas();
				programas.setCodigo_empresa(empresa.getCodigo_empresa());
				programas.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				programas.setCodigo_programa(tbxCodigo_programa.getValue());
				programas.setNombre_programa(tbxNombre_programa.getValue());
				programas.setVia_ingreso(lbxVia_ingreso.getSelectedItem()
						.getValue().toString());
				programas.setTipo_programa(lbxTipo_programa.getSelectedItem()
						.getValue().toString());
				programas
						.setFacturacion_recuperacion(chbFacturacion_recuperacion
								.isChecked() ? "S" : "N");

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("programas", programas);
				mapa_datos.put("mapa_seleccionados", mapa_seleccionados);
				mapa_datos.put("accion", tbxAccion.getValue());

				getServiceLocator().getServicio(ProgramasService.class)
						.guardarDatos(mapa_datos);

				tbxCodigo_programa.setVisible(true);
				tbxCodigo_programa.setValue(programas.getCodigo_programa());
				lbCodigo_programa.setVisible(true);
				tbxAccion.setValue("modificar");
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (WrongValueException e) {
			log.error("Error del componente : " + e.getComponent().getId(), e);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Programas programas = (Programas) obj;
		try {
			tbxCodigo_programa.setValue(programas.getCodigo_programa());
			tbxNombre_programa.setValue(programas.getNombre_programa());
			Utilidades.seleccionarListitem(lbxVia_ingreso,
					programas.getVia_ingreso());
			Utilidades.seleccionarListitem(lbxTipo_programa,
					programas.getTipo_programa());

			chbFacturacion_recuperacion.setChecked(programas
					.getFacturacion_recuperacion().equals("S") ? true : false);

			// Mostramos la vista //
			tbxAccion.setText("modificar");

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("codigo_programa", programas.getCodigo_programa());

			List<Programas_actividades_pyp> listado_actividades = getServiceLocator()
					.getServicio(Programas_actividades_pypService.class)
					.listar(parametros);

			for (Programas_actividades_pyp programas_actividades_pyp : listado_actividades) {
				final String codigo_cups = programas_actividades_pyp
						.getCodigo_cups();

				if (!mapa_seleccionados.containsKey(codigo_cups)) {
					String nombre_procedimiento = "";

					Map<String, Object> parametros2 = new HashMap<String, Object>();
					parametros.put("codigo_cups", codigo_cups);
					List<Procedimientos> listado = getServiceLocator()
							.getProcedimientosService().listar(parametros2);
					Procedimientos procedimiento = listado.isEmpty() ? null
							: listado.get(0);
					if (procedimiento != null)
						nombre_procedimiento = procedimiento.getDescripcion();

					final Listitem listitem = new Listitem();

					Listcell celda_codigo = Utilidades.obtenerListcell(
							codigo_cups, Textbox.class, true, true);

					listitem.appendChild(celda_codigo);

					final Listbox listbox_manual = new Listbox();
					listbox_manual.setHflex("1");
					listbox_manual.setMold("select");
					listarManuales(listbox_manual, lbxManual_tarifario
							.getSelectedItem().getValue().toString());

					Listcell celda_manual = new Listcell();
					celda_manual.appendChild(listbox_manual);

					listitem.appendChild(celda_manual);

					Listcell celda_nombre = Utilidades.obtenerListcell(
							nombre_procedimiento, Textbox.class, true, true);

					final Textbox tbxNombre = (Textbox) celda_nombre
							.getFirstChild();

					listitem.appendChild(celda_nombre);

					listbox_manual.addEventListener("onSelect",
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {

									Map<String, Object> parametros = new HashMap<String, Object>();
									parametros.put("codigo_cups", codigo_cups);
									List<Procedimientos> listado = getServiceLocator()
											.getProcedimientosService().listar(
													parametros);
									Procedimientos procedimiento = listado
											.isEmpty() ? null : listado.get(0);
									if (procedimiento != null)
										tbxNombre.setValue(procedimiento
												.getDescripcion());

								}

							});

					Listcell celda_eliminar = new Listcell();

					Image img = new Image("/images/borrar.gif");

					img.setTooltiptext("Quitar registro");
					img.setStyle("cursor:pointer");
					celda_eliminar.appendChild(img);

					img.addEventListener("onClick", new EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {

							Messagebox
									.show("Esta seguro que desea eliminar este registro? ",
											"Eliminar Registro",
											Messagebox.YES + Messagebox.NO,
											Messagebox.QUESTION,
											new org.zkoss.zk.ui.event.EventListener() {
												public void onEvent(Event event)
														throws Exception {
													if ("onYes".equals(event
															.getName())) {
														listitem.detach();
														mapa_seleccionados
																.remove(codigo_cups);
													}
												}
											});
						}
					});

					listitem.appendChild(celda_eliminar);

					listboxActividades.appendChild(listitem);
					mapa_seleccionados.put(codigo_cups, codigo_cups);
				}
			}

			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Programas programas = (Programas) obj;
		try {
			int result = programasService.eliminar(programas);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Informacion", Messagebox.OK, Messagebox.INFORMATION);
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

	public void openPcd() throws Exception {
		String pages = "";

		String manual = lbxManual_tarifario.getSelectedItem().getValue()
				.toString();

		pages = "/pages/openProcedimientos.zul";

		Map parametros = new HashMap();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("anio", anio != null ? anio.toString() : "");
		parametros.put("ocultar_filtro_procedimiento",
				"ocultar_filtro_procedimiento");
		parametros.put("pyp", "S");

		//log.info("Paginas: " + pages);

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

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		final String codigo_cups = (String) pcd.get("codigo_cups");
		String nombre_procedimiento = (String) pcd.get("nombre_procedimiento");

		if (!mapa_seleccionados.containsKey(codigo_cups)) {

			final Listitem listitem = new Listitem();

			Listcell celda_codigo = Utilidades.obtenerListcell(codigo_cups,
					Textbox.class, true, true);

			listitem.appendChild(celda_codigo);

			final Listbox listbox_manual = new Listbox();
			listbox_manual.setHflex("1");
			listbox_manual.setMold("select");
			listarManuales(listbox_manual, lbxManual_tarifario
					.getSelectedItem().getValue().toString());

			Listcell celda_manual = new Listcell();
			celda_manual.appendChild(listbox_manual);

			listitem.appendChild(celda_manual);

			Listcell celda_nombre = Utilidades.obtenerListcell(
					nombre_procedimiento, Textbox.class, true, true);

			final Textbox tbxNombre = (Textbox) celda_nombre.getFirstChild();

			listitem.appendChild(celda_nombre);

			listbox_manual.addEventListener("onSelect",
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {

							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_cups", codigo_cups);
							List<Procedimientos> listado = getServiceLocator()
									.getProcedimientosService().listar(
											parametros);
							Procedimientos procedimiento = listado.isEmpty() ? null
									: listado.get(0);
							if (procedimiento != null)
								tbxNombre.setValue(procedimiento
										.getDescripcion());
						}
					});

			Listcell celda_eliminar = new Listcell();

			Image img = new Image("/images/borrar.gif");

			img.setTooltiptext("Quitar registro");
			img.setStyle("cursor:pointer");
			celda_eliminar.appendChild(img);

			img.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {

					Messagebox.show(
							"Esta seguro que desea eliminar este registro? ",
							"Eliminar Registro",
							Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equals(event.getName())) {
										listitem.detach();
										mapa_seleccionados.remove(codigo_cups);
									}
								}
							});
				}
			});

			listitem.appendChild(celda_eliminar);

			listboxActividades.appendChild(listitem);
			mapa_seleccionados.put(codigo_cups, codigo_cups);
		}
	}

	public void listarManuales(Listbox listbox, String manual) {
		listbox.getChildren().clear();
		Listitem listitem = new Listitem("SOAT", "SOAT");
		if (manual.equals("SOAT"))
			listitem.setSelected(true);
		listbox.appendChild(listitem);

		listitem = new Listitem("ISS01", "ISS01");
		if (manual.equals("ISS01"))
			listitem.setSelected(true);
		listbox.appendChild(listitem);

		listitem = new Listitem("ISS04", "ISS04");
		if (manual.equals("ISS04"))
			listitem.setSelected(true);
		listbox.appendChild(listitem);

	}

}