/*
 * servicios_disponiblesAction.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.service.Servicios_disponiblesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.framework.util.MensajesUtil;

public class Servicios_disponiblesMacro extends Div implements AfterCompose {

	private static Logger log = Logger
			.getLogger(Servicios_disponiblesMacro.class);

	private ZKWindow zkWindow;
	// Componentes //

	private Tree treeServicios_disponibles;

	private String manual = "SOAT";
	private String anio = "2014";

	private Bandbox bandboxBusqueda;

	private Map<String, Servicios_contratados> mapa_servicios_contratados = new HashMap<String, Servicios_contratados>();

	private List<Servicios_disponibles> listado_encontrado;
	private String valor_busqueda;
	private int indice_busqueda;

	@Override
	public void afterCompose() {
		treeServicios_disponibles = (Tree) this.getFirstChild();
		bandboxBusqueda = (Bandbox) treeServicios_disponibles.getTreecols()
				.getFirstChild().getFirstChild();
	}

	public void inicializar(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
		crearArbol();

		bandboxBusqueda.addEventListener(Events.ON_OK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						onBuscarServiciosDisponibles();
					}
				});
		bandboxBusqueda.addEventListener(Events.ON_OPEN,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						onBuscarServiciosDisponibles();
					}
				});
	}

	public void crearArbol() {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("solo_padres", "_solo_padres");
			List<Servicios_disponibles> listado_servicios = zkWindow
					.getServiceLocator()
					.getServicio(Servicios_disponiblesService.class)
					.listar(parametros);
			treeServicios_disponibles.getTreechildren().getChildren().clear();
			render(treeServicios_disponibles.getTreechildren(),
					listado_servicios);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onBuscarServiciosDisponibles() {
		String valor = bandboxBusqueda.getValue().trim().toUpperCase();
		//log.info("Ejecutando metodo @onBuscarServiciosDisponibles() Valor ===> "
			//	+ valor);
		if (!valor.isEmpty()) {
			if (valor_busqueda != null && valor.equals(valor_busqueda)) {
				if (listado_encontrado.size() > (indice_busqueda + 1)) {
					indice_busqueda++;
				} else {
					indice_busqueda = 0;
				}
			} else {
				valor_busqueda = valor;
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("paramTodo", "paramTodo");
				parametros.put("value", valor.toUpperCase());
				listado_encontrado = zkWindow.getServiceLocator()
						.getServicio(Servicios_disponiblesService.class)
						.listar(parametros);
				indice_busqueda = 0;
			}

			if (!listado_encontrado.isEmpty()) {
				Servicios_disponibles servicios_disponibles = listado_encontrado
						.get(indice_busqueda);
				String codigo_servicio = servicios_disponibles.getCodigo();
				StringTokenizer codigos_seperados = new StringTokenizer(
						codigo_servicio, ".");
				Treechildren treechildren = treeServicios_disponibles
						.getTreechildren();
				String codigo_aux = "";
				int i = 0;
				while (codigos_seperados.hasMoreTokens()) {
					if (i == 0)
						codigo_aux = codigos_seperados.nextToken();
					else
						codigo_aux = codigo_aux + "."
								+ codigos_seperados.nextToken();

					Servicios_disponibles servicios_disponibles2 = new Servicios_disponibles();
					servicios_disponibles2.setCodigo(codigo_aux);
					servicios_disponibles2 = zkWindow.getServiceLocator()
							.getServicio(Servicios_disponiblesService.class)
							.consultar(servicios_disponibles2);

					TreeitemCheckbox treeitemTemp = buscarTreeitemCheckbox(
							treechildren, servicios_disponibles2.getCodigo());

					abrirRamaBusqueda(servicios_disponibles2, treeitemTemp);
					treechildren = treeitemTemp.getTreechildren();
					i++;
					if (treechildren != null
							&& !treechildren.getChildren().isEmpty()) {
						treeitemTemp.setOpen(true);
						if (i == codigos_seperados.countTokens()) {
							Clients.scrollIntoView(treeitemTemp.getCheckbox());
							MensajesUtil.notificarAlerta(
									"Resultado encontrado",
									treeitemTemp.getCheckbox());
						}
					} else {
						Clients.scrollIntoView(treeitemTemp.getCheckbox());
						MensajesUtil.notificarInformacion(
								"Resultado encontrado",
								treeitemTemp.getCheckbox());
					}
				}
			}

		}
		bandboxBusqueda.setFocus(true);
	}

	private TreeitemCheckbox buscarTreeitemCheckbox(Treechildren treechildren,
			String codigo) {
		for (Component component : treechildren.getChildren()) {
			if (component instanceof TreeitemCheckbox) {
				Servicios_disponibles servicios_disponibles = (Servicios_disponibles) ((TreeitemCheckbox) component)
						.getValue();
				if (servicios_disponibles.getCodigo().equals(codigo)) {
					return (TreeitemCheckbox) component;
				}
			}
		}
		return null;
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
			if (servicios_disponibles.getVia_ingreso()) {
				treeitemCheckbox.getLabelVia().setVisible(true);
			}

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

	public void abrirRamaBusqueda(
			Servicios_disponibles servicios_disponiblesTemp,
			TreeitemCheckbox treeitemTemp) {
		Treechildren treechildrenTemp = treeitemTemp.getTreechildren();
		if (!treeitemTemp.hasAttribute("ELEMENTOS_CARGADOS")) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("id_padre",
					servicios_disponiblesTemp.getId_servicio());
			List<Servicios_disponibles> listado = zkWindow.getServiceLocator()
					.getServicio(Servicios_disponiblesService.class)
					.listar(parametros);
			if (!listado.isEmpty()) {
				if (treeitemTemp.getTreechildren() == null)
					treeitemTemp.appendChild(new Treechildren());
				render(treechildrenTemp, listado);
			}
			treeitemTemp.setAttribute("ELEMENTOS_CARGADOS",
					"ELEMENTOS_CARGADOS");
			if (treeitemTemp.getCheckbox().isChecked())
				treeitemTemp.seleccionarHijos(true, treeitemTemp);
		}
	}

	public List<Servicios_disponibles> getListadoSeleccionado() {
		List<Servicios_disponibles> listado = new ArrayList<Servicios_disponibles>();
		getSeleccionados(treeServicios_disponibles.getTreechildren(), listado);
		return listado;
	}

	public void mostrarDatosArbol(
			List<Servicios_contratados> listado_servicios_contratados) {

		mapa_servicios_contratados.clear();
		for (Servicios_contratados servicios_contratados : listado_servicios_contratados) {
			mapa_servicios_contratados.put(
					servicios_contratados.getCodigo_servicio(),
					servicios_contratados);
		}
		verificarDatosGuardados(treeServicios_disponibles.getTreechildren());
	}

	private void verificarDatosGuardados(Treechildren treechildren) {
		for (Component componente : treechildren.getChildren()) {
			if (componente instanceof TreeitemCheckbox) {
				TreeitemCheckbox treeitemCheckbox = (TreeitemCheckbox) componente;
				Servicios_disponibles servicios_disponibles = (Servicios_disponibles) treeitemCheckbox
						.getValue();

				if (servicios_disponibles.getVia_ingreso()) {
					treeitemCheckbox.getLabelVia().setVisible(true);
				}

				int valor = existenHijosGuardados(servicios_disponibles
						.getCodigo());
				if (valor == 2) {
					treeitemCheckbox.setSeleccionado(true);
					treeitemCheckbox.getCheckbox().setStyle("font-weight:bold");
				} else if (valor == 1) {
					abrirRama(servicios_disponibles, treeitemCheckbox);
					treeitemCheckbox.setOpen(true);
					verificarDatosGuardados(treeitemCheckbox.getTreechildren());
				}
				TreeitemCheckbox.verificarPadres(treeitemCheckbox);
			}
		}
	}

	private int existenHijosGuardados(String codigo_servicio) {
		int valor = 0;
		for (String key_mapa : mapa_servicios_contratados.keySet()) {
			if (key_mapa.startsWith(codigo_servicio)) {
				if (key_mapa.equals(codigo_servicio)) {
					return 2;
				}
				valor = 1;
			}
		}
		return valor;
	}

	private void getSeleccionados(Treechildren treechildren,
			List<Servicios_disponibles> listado) {
		for (Component componente : treechildren.getChildren()) {
			if (componente instanceof TreeitemCheckbox) {
				TreeitemCheckbox treeitemCheckbox = (TreeitemCheckbox) componente;
				if (treeitemCheckbox.getSeleccionado()) {
					Servicios_disponibles servicios_disponibles = (Servicios_disponibles) treeitemCheckbox
							.getValue();
					listado.add(servicios_disponibles);
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

	public String getManual() {
		return manual;
	}

	public void setManual(String manual) {
		this.manual = manual;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	private static class TreeitemCheckbox extends Treeitem {

		private Checkbox checkbox = new Checkbox();

		private Label labelVia = new Label("(Via Ingreso)");

		public TreeitemCheckbox(String codigo, String nombre) {
			checkbox.setStyle("cursor:pointer");
			checkbox.setLabel(codigo + ".  " + nombre);
			labelVia.setStyle("color:blue;font-weight:bold;margin-left:2px;cursor:pointer");
			labelVia.setTooltiptext("Relacion via de ingreso");
			labelVia.setVisible(false);
			Treerow treerow = new Treerow();
			Treecell treecell = new Treecell();
			treecell.appendChild(checkbox);
			treecell.appendChild(labelVia);
			treerow.appendChild(treecell);
			appendChild(treerow);

			checkbox.addEventListener(Events.ON_CHECK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							checkbox.setStyle(checkbox.isChecked() ? "font-weight:bold"
									: "font:weight:normal");
							seleccionarHijos(checkbox.isChecked(),
									TreeitemCheckbox.this);
							verificarPadres(TreeitemCheckbox.this);
						}
					});
		}

		private void seleccionarHijos(boolean seleccionar,
				TreeitemCheckbox treeitemCheckbox) {
			if (treeitemCheckbox.getTreechildren() != null) {
				for (Component componente : treeitemCheckbox.getTreechildren()
						.getChildren()) {
					TreeitemCheckbox treeitem_aux = (TreeitemCheckbox) componente;
					treeitem_aux.setSeleccionado(seleccionar);
					treeitem_aux
							.getCheckbox()
							.setStyle(
									treeitem_aux.getCheckbox().isChecked() ? "font-weight:bold"
											: "font:weight:normal");
					if (!treeitem_aux.getSeleccionado()) {
						treeitemCheckbox.removeAttribute("PARCIAL_SELECCION");
					}
					seleccionarHijos(seleccionar, treeitem_aux);
				}
			}
		}

		public static void verificarPadres(TreeitemCheckbox treeitemCheckbox) {
			Treechildren treechildren = (Treechildren) treeitemCheckbox
					.getParent();
			if (treechildren != null) {
				boolean todos = true;
				boolean alguno = false;
				for (Component componente : treechildren.getChildren()) {
					TreeitemCheckbox treeitemCheckbox_aux = (TreeitemCheckbox) componente;
					if (!treeitemCheckbox_aux.getCheckbox().isChecked()) {
						todos = false;
						if (treeitemCheckbox_aux
								.hasAttribute("PARCIAL_SELECCION")) {
							alguno = true;
						}
					} else {
						alguno = true;
					}
				}
				if (treechildren.getParent() != null) {
					if (treechildren.getParent() instanceof TreeitemCheckbox) {
						((TreeitemCheckbox) treechildren.getParent())
								.setSeleccionado(todos);
						if (!todos) {
							if (alguno) {
								((TreeitemCheckbox) treechildren.getParent())
										.getCheckbox().setStyle("color:red");
								((TreeitemCheckbox) treechildren.getParent())
										.setAttribute("PARCIAL_SELECCION",
												"PARCIAL_SELECCION");
							} else {
								((TreeitemCheckbox) treechildren.getParent())
										.getCheckbox().setStyle("color:black");
							}
						} else if (todos) {
							((TreeitemCheckbox) treechildren.getParent())
									.getCheckbox().setStyle(
											"color:black;font-weight:bold");
						}
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

		public Label getLabelVia() {
			return labelVia;
		}
	}

}