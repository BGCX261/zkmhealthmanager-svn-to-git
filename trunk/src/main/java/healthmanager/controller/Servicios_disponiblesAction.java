/*
 * servicios_disponiblesAction.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.service.Servicios_disponiblesService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ItemRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.interfaces.ISeleccionarComponente;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Servicios_disponiblesAction extends ZKWindow implements
		AfterCompose, ISeleccionarComponente {

	private static Logger log = Logger
			.getLogger(Servicios_disponiblesAction.class);

	private ZKWindow zkWindow;
	// Componentes //

	private Long id_servicio;
	private TreeitemCheckbox treeitemCurrent;

	private String accion;

	@View
	private Textbox tbxNombre;
	@View
	private Textbox tbxCodigo_procedimiento;
	@View
	private Longbox lgxId_padre;
	@View
	private Textbox tbxPadre_informacion;
	@View
	private Listbox lbxTipo;
	@View
	private Panel panelServicios;
	@View
	private Tree treeServicios_disponibles;
	@View
	private Textbox tbxCodigo;
	@View
	private Checkbox chkVia_ingreso;

	@View
	private Toolbarbutton btnNuevo;

	@View
	private Hlayout hlayoutInc_exc;

	@View
	private Listbox lbxTipo_inc_exc;

	private Chosenbox chosenbox;

	private ListModelList<Map<String, Object>> listado_datos;

	private Set<Map<String, Object>> items_seleccionados_current;

	private List<String> seleccionados = new ArrayList<String>();

	private Integer consecutivo;

	private boolean agrego = false;

	@Override
	public void init() {
		inicializar(this);
	}

	public void inicializar(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
		crearArbol();
	}

	public void crearArbol() {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("raiz", "_raiz");
			// parametros.put("solo_padres", "_solo_padres");
			List<Servicios_disponibles> listado_servicios = zkWindow
					.getServiceLocator()
					.getServicio(Servicios_disponiblesService.class)
					.listar(parametros);
			treeServicios_disponibles.getTreechildren().getChildren().clear();
			render(treeServicios_disponibles.getTreechildren(),
					listado_servicios);

			chosenbox = new Chosenbox();
			chosenbox.setHflex("1");

			listado_datos = new ListModelList<Map<String, Object>>();

			Map<String, Object> datos = new HashMap<String, Object>();
			datos.put("tipo", "EXCLUSION");
			datos.put("jerarquia", "NIVEL");
			datos.put("valor", "2");
			listado_datos.add(datos);
			datos = new HashMap<String, Object>();
			datos.put("tipo", "EXCLUSION");
			datos.put("jerarquia", "NIVEL");
			datos.put("valor", "3");
			listado_datos.add(datos);
			datos = new HashMap<String, Object>();
			datos.put("tipo", "EXCLUSION");
			datos.put("jerarquia", "NIVEL");
			datos.put("valor", "4");
			listado_datos.add(datos);

			chosenbox.setItemRenderer(new ItemRenderer<Map<String, Object>>() {

				@Override
				public String render(Component componente,
						Map<String, Object> datos, int arg2) throws Exception {

					String descripcion = "";
					if (datos.containsKey("descripcion")) {
						descripcion = datos.get("descripcion").toString();
					}
					return datos.get("tipo").toString() + " DE "
							+ datos.get("jerarquia").toString() + " "
							+ datos.get("valor").toString() + " " + descripcion;

				}

			});

			chosenbox.setModel(listado_datos);

			items_seleccionados_current = chosenbox.getSelectedObjects();

			for (Map<String, Object> datos_aux : listado_datos) {
				items_seleccionados_current.add(datos_aux);
			}

			hlayoutInc_exc.appendChild(chosenbox);

			chosenbox.setSelectedObjects(items_seleccionados_current);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void agregarProcedimientos(String manual, String anio) {
		String pages = "";

		pages = "/pages/openProcedimientos.zul";

		Map parametros = new HashMap();
		parametros.put("codigo_empresa", zkWindow.getEmpresa()
				.getCodigo_empresa());
		parametros.put("codigo_sucursal", zkWindow.getSucursal()
				.getCodigo_sucursal());

		parametros.put("restringido", "");
		parametros.put("anio", anio);
		parametros.put("seleccionados", seleccionados);
		parametros.put("ocultar_filtro_procedimiento", "_ok");

		items_seleccionados_current = chosenbox.getSelectedObjects();

		Component componente = Executions.createComponents(pages, this,
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
		//log.info("Seleccionado: " + pcd);
		String tipo = lbxTipo_inc_exc.getSelectedItem().getValue().toString()
				.equals("inclusion") ? "INCLUSIÓN" : "EXCLUSIÓN";
		String nombre = (String) pcd.get("nombre_procedimiento");
		String codigo_cups = (String) pcd.get("codigo_cups");
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put("tipo", tipo);
		datos.put("jerarquia", "CODIGO CUPS");
		datos.put("valor", codigo_cups);
		datos.put("descripcion", nombre);
		listado_datos.add(datos);
		items_seleccionados_current.add(datos);
		seleccionados.add((String) pcd.get("codigo_procedimiento"));
		chosenbox.setSelectedObjects(items_seleccionados_current);
		chosenbox.invalidate();
	}

	private void render(final Treechildren treechildren,
			List<Servicios_disponibles> listado_servicios) {
		for (final Servicios_disponibles servicios_disponibles : listado_servicios) {
			final TreeitemCheckbox treeitemCheckbox = new TreeitemCheckbox(
					servicios_disponibles.getCodigo(),
					(servicios_disponibles.getCodigo_procedimiento() != null ? servicios_disponibles
							.getCodigo_procedimiento() + " "
							: "")
							+ servicios_disponibles.getNombre());

			if (servicios_disponibles.getVia_ingreso())
				treeitemCheckbox.getLabelVia_ingreso().setVisible(true);

			treeitemCheckbox.setValue(servicios_disponibles);
			treeitemCheckbox.setOpen(false);
			treechildren.appendChild(treeitemCheckbox);
			Long cantidad_hijos = zkWindow
					.getServiceLocator()
					.getServicio(Servicios_disponiblesService.class)
					.cantidad_hijos(
							new Servicios_disponibles(servicios_disponibles
									.getId_servicio()));

			if (cantidad_hijos > 0) {
				treeitemCheckbox.appendChild(new Treechildren());
				treeitemCheckbox.addEventListener(Events.ON_OPEN,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								abrirRama(servicios_disponibles,
										treeitemCheckbox);
							}

						});
			}

			treeitemCheckbox.addEventListener(Events.ON_RIGHT_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							final Component component = arg0.getTarget();
							final Menupopup menupopup = new Menupopup();
							Menuitem menuitem_nuevo = new Menuitem(
									"Nuevo servicio", "/images/New16_blue.png");
							menupopup.appendChild(menuitem_nuevo);
							Menuitem menuitem_editar = new Menuitem(
									"Editar servicio", "/images/editar.gif");
							menupopup.appendChild(menuitem_editar);
							appendChild(menupopup);
							menupopup.open(component);

							menuitem_nuevo.addEventListener(Events.ON_CLICK,
									new EventListener<Event>() {

										@Override
										public void onEvent(Event arg0)
												throws Exception {
											if ((component instanceof Treeitem)
													&& component != null) {
												accion = "registrar";
												btnNuevo.setDisabled(true);
												treeitemCurrent = (TreeitemCheckbox) component;
												consecutivo = treeitemCurrent
														.getTreechildren() != null ? treeitemCurrent
														.getTreechildren()
														.getChildren().size()
														: 0;
												Servicios_disponibles servicios_disponibles = treeitemCurrent
														.getValue();
												lgxId_padre
														.setValue(servicios_disponibles
																.getId_servicio());
												tbxPadre_informacion.setValue((servicios_disponibles
														.getCodigo_procedimiento() != null ? servicios_disponibles
														.getCodigo_procedimiento()
														+ " "
														: "")
														+ servicios_disponibles
																.getNombre());
												panelServicios.setVisible(true);
											}
										}

									});

							menuitem_editar.addEventListener(Events.ON_CLICK,
									new EventListener<Event>() {

										@Override
										public void onEvent(Event arg0)
												throws Exception {
											if ((component instanceof Treeitem)
													&& component != null) {
												accion = "modificar";
												btnNuevo.setDisabled(true);
												treeitemCurrent = (TreeitemCheckbox) component;
												Servicios_disponibles servicios_disponibles = treeitemCurrent
														.getValue();
												id_servicio = servicios_disponibles
														.getId_servicio();
												tbxCodigo
														.setValue(servicios_disponibles
																.getCodigo());
												tbxNombre
														.setValue(servicios_disponibles
																.getNombre());
												tbxCodigo_procedimiento
														.setValue(servicios_disponibles
																.getCodigo_procedimiento());
												chkVia_ingreso
														.setChecked(servicios_disponibles
																.getVia_ingreso());

												Utilidades.seleccionarListitem(
														lbxTipo,
														servicios_disponibles
																.getTipo());

												lgxId_padre
														.setValue(servicios_disponibles
																.getId_padre());

												Servicios_disponibles sd_aux = new Servicios_disponibles();
												sd_aux.setId_servicio(servicios_disponibles
														.getId_padre());
												sd_aux = zkWindow
														.getServiceLocator()
														.getServicio(
																Servicios_disponiblesService.class)
														.consultar(sd_aux);

												tbxPadre_informacion.setValue((sd_aux
														.getCodigo_procedimiento() != null ? sd_aux
														.getCodigo_procedimiento()
														+ " "
														: "")
														+ sd_aux.getNombre());
												panelServicios.setVisible(true);
											}
										}

									});

						}

					});

		}
	}

	public void abrirRama(Servicios_disponibles servicios_disponiblesTemp,
			TreeitemCheckbox treeitemTemp) {
		if (treeitemTemp.getTreechildren() == null)
			treeitemTemp.appendChild(new Treechildren());
		Treechildren treechildrenTemp = treeitemTemp.getTreechildren();
		if (!treeitemTemp.hasAttribute("ELEMENTOS_CARGADOS")) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("id_padre",
					servicios_disponiblesTemp.getId_servicio());
			List<Servicios_disponibles> listado = zkWindow.getServiceLocator()
					.getServicio(Servicios_disponiblesService.class)
					.listar(parametros);
			if (!listado.isEmpty()) {
				render(treechildrenTemp, listado);
			}
			treeitemTemp.setAttribute("ELEMENTOS_CARGADOS",
					"ELEMENTOS_CARGADOS");
			if (treeitemTemp.getCheckbox().isChecked())
				treeitemTemp.seleccionarHijos(true, treeitemTemp);
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;
		XulElement xulElement = null;
		if (tbxNombre.getValue().trim().isEmpty()) {
			MensajesUtil.notificarError("El campo Nombre es obligatorio",
					tbxNombre);
			valida = false;
			if (xulElement == null)
				xulElement = tbxNombre;
		}

		if (xulElement != null) {
			Clients.scrollIntoView(xulElement);
			xulElement.setFocus(true);
		}
		return valida;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			//log.info("ejecutando metodo guardarDatos()");
			if (validarForm()) {
				Servicios_disponibles servicios_padre = (Servicios_disponibles) treeitemCurrent
						.getValue();
				if (accion.equals("registrar")) {
					tbxCodigo.setValue(servicios_padre.getCodigo() + "."
							+ (consecutivo + 1));
				}
				FormularioUtil.setUpperCase(this);
				Servicios_disponibles servicios_disponibles = new Servicios_disponibles();
				servicios_disponibles.setNombre(tbxNombre.getValue());
				if (!tbxCodigo_procedimiento.getValue().trim().isEmpty()) {
					servicios_disponibles
							.setCodigo_procedimiento(tbxCodigo_procedimiento
									.getValue());
				}
				servicios_disponibles.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				servicios_disponibles.setCreacion_user(zkWindow.getUsuarios()
						.getCodigo().toString());
				servicios_disponibles
						.setId_padre((lgxId_padre.getValue() != null ? lgxId_padre
								.getValue() : 0));
				servicios_disponibles.setId_servicio(id_servicio);
				servicios_disponibles.setTipo(lbxTipo.getSelectedItem()
						.getValue().toString());
				servicios_disponibles
						.setVia_ingreso(chkVia_ingreso.isChecked());
				servicios_disponibles.setCodigo(tbxCodigo.getValue());

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("servicios_disponibles", servicios_disponibles);
				mapa_datos.put("accion", accion);
				zkWindow.getServiceLocator()
						.getServicio(Servicios_disponiblesService.class)
						.guardarDatos(mapa_datos);

				if (accion.equals("registrar")) {
					agrego = true;
					btnNuevo.setDisabled(false);
				} else {
					if (!agrego) {
						treeitemCurrent
								.getCheckbox()
								.setLabel(
										servicios_disponibles.getCodigo()
												+ ". "
												+ (servicios_disponibles
														.getCodigo_procedimiento() != null ? servicios_disponibles
														.getCodigo_procedimiento()
														+ " "
														: "")
												+ servicios_disponibles
														.getNombre());
						if (servicios_disponibles.getVia_ingreso()) {
							treeitemCurrent.getLabelVia_ingreso().setVisible(
									true);
						} else {
							treeitemCurrent.getLabelVia_ingreso().setVisible(
									false);
						}

						treeitemCurrent.setValue(servicios_disponibles);
					}
				}
				accion = "modificar";
				id_servicio = servicios_disponibles.getId_servicio();
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", zkWindow);
		}

	}

	public void onClickNuevo() {
		btnNuevo.setDisabled(true);
		accion = "registrar";
		consecutivo++;
	}

	public void cerrarPanelRegistro() {
		panelServicios.setVisible(false);
		if (agrego) {
			if (treeitemCurrent != null) {
				treeitemCurrent.removeAttribute("ELEMENTOS_CARGADOS");
				if (treeitemCurrent.getTreechildren() != null) {
					treeitemCurrent.getTreechildren().getChildren().clear();
				}
				abrirRama((Servicios_disponibles) treeitemCurrent.getValue(),
						treeitemCurrent);
			}
		}
		agrego = false;
	}

	public List<String> getListadoSeleccionado() {
		List<String> listado = new ArrayList<String>();
		getSeleccionados(treeServicios_disponibles.getTreechildren(), listado);
		return listado;
	}

	private void getSeleccionados(Treechildren treechildren,
			List<String> listado) {
		for (Component componente : treechildren.getChildren()) {
			if (componente instanceof TreeitemCheckbox) {
				TreeitemCheckbox treeitemCheckbox = (TreeitemCheckbox) componente;
				if (treeitemCheckbox.getSeleccionado()) {
					Servicios_disponibles servicios_disponibles = (Servicios_disponibles) treeitemCheckbox
							.getValue();
					listado.add(servicios_disponibles.getCodigo());
				} else {
					if (treeitemCheckbox.getTreechildren() != null
							&& !treeitemCheckbox.getTreechildren()
									.getChildren().isEmpty()) {
						getSeleccionados(treeitemCheckbox.getTreechildren(),
								listado);
					}
				}
			}
		}
	}

	private class TreeitemCheckbox extends Treeitem {

		private Checkbox checkbox = new Checkbox();

		private Label labelVia_ingreso = new Label("Via ingreso");

		public TreeitemCheckbox(String codigo, String nombre) {
			checkbox.setStyle("cursor:pointer");
			checkbox.setLabel(codigo + ".  " + nombre);
			labelVia_ingreso.setStyle("font-weight:bold;color:blue");
			labelVia_ingreso.setVisible(false);
			Treerow treerow = new Treerow();
			Treecell treecell = new Treecell();
			treecell.appendChild(checkbox);
			treerow.appendChild(treecell);

			checkbox.addEventListener(Events.ON_CHECK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							seleccionarHijos(checkbox.isChecked(),
									TreeitemCheckbox.this);
							verificarPadres(TreeitemCheckbox.this);
						}
					});

			treecell = new Treecell();
			treecell.appendChild(labelVia_ingreso);
			treerow.appendChild(treecell);

			appendChild(treerow);

		}

		private void seleccionarHijos(boolean seleccionar,
				TreeitemCheckbox treeitemCheckbox) {
			if (treeitemCheckbox.getTreechildren() != null) {
				for (Component componente : treeitemCheckbox.getTreechildren()
						.getChildren()) {
					TreeitemCheckbox treeitem_aux = (TreeitemCheckbox) componente;
					treeitem_aux.setSeleccionado(seleccionar);
					seleccionarHijos(seleccionar, treeitem_aux);
				}
			}
		}

		private void verificarPadres(TreeitemCheckbox treeitemCheckbox) {
			Treechildren treechildren = (Treechildren) treeitemCheckbox
					.getParent();
			if (treechildren != null) {
				boolean todos = true;
				for (Component componente : treechildren.getChildren()) {
					TreeitemCheckbox treeitemCheckbox_aux = (TreeitemCheckbox) componente;
					if (!treeitemCheckbox_aux.getCheckbox().isChecked()) {
						todos = false;
						break;
					}
				}
				if (treechildren.getParent() != null) {
					if (treechildren.getParent() instanceof TreeitemCheckbox) {
						((TreeitemCheckbox) treechildren.getParent())
								.setSeleccionado(todos);
						verificarPadres(((TreeitemCheckbox) treechildren
								.getParent()));

					}
				}
			}

		}

		public void setSeleccionado(boolean seleccionar) {
			checkbox.setChecked(seleccionar);
		}

		public boolean getSeleccionado() {
			return checkbox.isChecked();
		}

		public Checkbox getCheckbox() {
			return checkbox;
		}

		public Label getLabelVia_ingreso() {
			return labelVia_ingreso;
		}

	}

}