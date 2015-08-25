package com.framework.macros.revision_sistemas;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Elm_ihc;
import healthmanager.modelo.bean.Revision_sistema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

public class TreeItemDinamicRevisionSistemasMacro extends Treeitem {

	private Tree tree;
	private List<Elm_ihc> listado_padres;
	private ZKWindow zkWindow;
	private String tipo;

	private Map<String, Revision_sistema> elementos_seleccionados = new HashMap<String, Revision_sistema>();

//	private static Logger log = Logger
//			.getLogger(TreeItemDinamicRevisionSistemasMacro.class);

	public TreeItemDinamicRevisionSistemasMacro(Tree tree, ZKWindow zkWindow,
			String tipo) {
		this.tree = tree;
		this.tree.getTreechildren().getChildren().clear();
		this.zkWindow = zkWindow;
		this.tipo = tipo;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("TYPE", tipo);
		map.put("ROOT", "_look");
		listado_padres = zkWindow.getServiceLocator().getElm_ihcService()
				.listar(map);
	}

	private String generarLLaveMapa(Elm_ihc elm_ihc) {
		return elm_ihc.getCod_elm_ihc() + "_" + elm_ihc.getSub_elm_ihc() + "_"
				+ elm_ihc.getCsc_elm_ihc();
	}

	public List<Revision_sistema> getElementos_seleccionados() {
		List<Revision_sistema> listado = new ArrayList<Revision_sistema>();
		for (String key : elementos_seleccionados.keySet()) {
			listado.add(elementos_seleccionados.get(key));
		}
		return listado;
	}

	/**
	 * Metodo para cargar en un mapa la lista de elementos de revision por
	 * sistema que se necesitan que salgan seleccionados al momento de cargar el
	 * arbol por primera vez
	 * 
	 * @param elemementos_iniciales
	 */
	public void cargarElemementosIniciales(
			List<Revision_sistema> elemementos_iniciales) {
		//log.info("@cargarElemementosIniciales() ===> Cargando "
				//+ elemementos_iniciales.size() + " elementos");
		for (Revision_sistema revision_sistema : elemementos_iniciales) {
			elementos_seleccionados.put(revision_sistema.getCodigo_revision(),
					revision_sistema);
		}
	}

	/**
	 * @author Luis Miguel Hernandez Perez Este metodo es para cargar el listado
	 *         entregado
	 * 
	 * */
	public TreeItemDinamicRevisionSistemasMacro renderizarContenido() {
		render(tree.getTreechildren(), this, listado_padres);
		return this;
	}

	private void render(Treechildren treechildren, Treeitem padre,
			List<Elm_ihc> listado) {
		for (final Elm_ihc elm_ihc : listado) {
			final TreeTitle treeTitle = new TreeTitle(elm_ihc,
					elm_ihc.getTpo_elm_ihc());
			treechildren.appendChild(treeTitle);

			if (elm_ihc.getTpo_elm_ihc().equals("S_N")) {
				final Estate estate = new Estate();
				estate.estado = false;
				final Treechildren treechildrenTemp = new Treechildren();
				treeTitle.appendChild(treechildrenTemp);

				treeTitle.addEventListener("onOpen",
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								//log.info("Estado: " + estate.estado);
								open(estate, elm_ihc, treechildrenTemp,
										treeTitle);
								treeTitle.focus();
							}

						});

				if (elementos_seleccionados
						.containsKey(generarLLaveMapa(elm_ihc))) {
					open(estate, elm_ihc, treechildrenTemp, treeTitle);
					treeTitle.setOpen(true);
					if (treeTitle.componentValue instanceof Radiogroup) {
						((Radiogroup) treeTitle.componentValue)
								.setSelectedIndex(0);
					}
				}

				/* avilitamos la seleccion de los contenido */
				if (treeTitle.componentValue instanceof Radiogroup) {
					final Radiogroup radiogroup = (Radiogroup) treeTitle.componentValue;
					final Revision_sistema OBJETO_ELEMENTO = (Revision_sistema) radiogroup
							.getAttribute("OBJETO_ELEMENTO");
					radiogroup.addEventListener("onCheck",
							new EventListener<Event>() {
								@Override
								public void onEvent(Event event)
										throws Exception {
									Radio radio = radiogroup.getSelectedItem();
									if (radio != null) {
										if (radio.getValue().equals("S")) {
											elementos_seleccionados.put(
													OBJETO_ELEMENTO
															.getCodigo_revision(),
													OBJETO_ELEMENTO);
											open(estate, elm_ihc,
													treechildrenTemp, treeTitle);
										} else {
											elementos_seleccionados.remove(OBJETO_ELEMENTO
													.getCodigo_revision());
										}
										treeTitle.setOpen(radio.getValue()
												.equals("S"));
										List<Component> components = treechildrenTemp
												.getChildren();
										for (Component component : components) {
											if (component instanceof TreeTitle) {
												TreeTitle treeTitle = (TreeTitle) component;
												if (radio.getValue()
														.equals("N")) {
													if (treeTitle.tbxObservacion != null) {
														treeTitle.tbxObservacion
																.setValue("");
														treeTitle.tbxObservacion
																.setDisabled(true);
													}
												} else {
													if (treeTitle.tbxObservacion != null) {
														treeTitle.tbxObservacion
																.setDisabled(false);

													}
												}
												if (treeTitle.componentValue instanceof Checkbox) {
													Revision_sistema OBJETO_ELEMENTO_AUX = (Revision_sistema) treeTitle.componentValue
															.getAttribute("OBJETO_ELEMENTO");
													((Checkbox) treeTitle.componentValue)
															.setDisabled(radio
																	.getValue()
																	.equals("N"));
													if (radio.getValue()
															.equals("N")) {
														elementos_seleccionados
																.remove(OBJETO_ELEMENTO_AUX
																		.getCodigo_revision());
														((Checkbox) treeTitle.componentValue)
																.setChecked(false);
													}
												} else if (treeTitle.componentValue instanceof Radiogroup) {
													Revision_sistema OBJETO_ELEMENTO_AUX = (Revision_sistema) treeTitle.componentValue
															.getAttribute("OBJETO_ELEMENTO");
													if (radio.getValue()
															.equals("N")) {
														elementos_seleccionados
																.remove(OBJETO_ELEMENTO_AUX
																		.getCodigo_revision());
														((Radiogroup) treeTitle.componentValue)
																.setSelectedIndex(1);
													}
												}
											}
										}
									}
									radiogroup.setFocus(true);
								}
							});
				}
			} else if (elm_ihc.getTpo_elm_ihc().equals("CHK")
					|| elm_ihc.getTpo_elm_ihc().equals("CHO")) {
				if (treeTitle.componentValue instanceof Checkbox) {
					final Checkbox checkbox = (Checkbox) treeTitle.componentValue;
					final Textbox textbox = treeTitle.tbxObservacion;
					final Revision_sistema OBJETO_ELEMENTO = (Revision_sistema) checkbox
							.getAttribute("OBJETO_ELEMENTO");
					checkbox.addEventListener("onCheck",
							new EventListener<Event>() {
								@Override
								public void onEvent(Event event)
										throws Exception {
									if (checkbox.isChecked()) {
										elementos_seleccionados.put(
												OBJETO_ELEMENTO
														.getCodigo_revision(),
												OBJETO_ELEMENTO);
									} else {
										elementos_seleccionados
												.remove(OBJETO_ELEMENTO
														.getCodigo_revision());
									}
								}
							});
					if (elementos_seleccionados.containsKey(OBJETO_ELEMENTO
							.getCodigo_revision())) {
						checkbox.setChecked(true);
						textbox.setVisible(true);
						if (elm_ihc.getTpo_elm_ihc().equals("CHO")) {
							treeTitle.tbxObservacion
									.setValue(elementos_seleccionados.get(
											OBJETO_ELEMENTO
													.getCodigo_revision())
											.getObservaciones());
						}
					}
				}
			}
		}
	}

	public void open(Estate estate, Elm_ihc elm_ihc,
			Treechildren treechildrenTemp, TreeTitle treeTitle) {
		if (!estate.estado) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TYPE", tipo);
			map.put("child", (elm_ihc.getSub_elm_ihc() + elm_ihc
					.getCsc_elm_ihc()).trim().toUpperCase());
			List<Elm_ihc> list = zkWindow.getServiceLocator()
					.getElm_ihcService().listar(map);
			//log.info("Hijos: tipo: " + tipo + " child: "
					//+ elm_ihc.getSub_elm_ihc() + elm_ihc.getCsc_elm_ihc()
					//+ " size: " + list.size());
			if (!list.isEmpty()) {
				render(treechildrenTemp, treeTitle, list);
			}
			estate.estado = true;
		}
	}

	public void aplicarSoloLectura(boolean readonly) {
		for (Component component : tree.getChildren()) {
			deshabilitarComponentes(component, readonly);
		}
	}

	private void deshabilitarComponentes(Component component, boolean sw) {
		List<Component> listado = component.getChildren();

		for (Component component2 : listado) {

			if (component2 instanceof Textbox) {
				((Textbox) component2).setReadonly(sw);
			}

			if (component2 instanceof Checkbox) {
				((Checkbox) component2).setDisabled(sw);
			}
			if (component2 instanceof Radio) {
				((Radio) component2).setDisabled(sw);
			}
			
			if(component2 instanceof TreeTitle){
				Iterator<EventListener<? extends Event>> iterador = component2.getEventListeners(Events.ON_OPEN).iterator();
				while(iterador.hasNext()){
					EventListener<? extends Event> eventListener = iterador.next();
					component2.removeEventListener(Events.ON_OPEN, eventListener);
				}
			}

			if (component2 instanceof Radiogroup) {
				for (Object abstractComponentTemp : ((Radiogroup) component2)
						.getChildren()) {
					if (abstractComponentTemp instanceof Radio)
						((Radio) abstractComponentTemp).setDisabled(sw);
				}
			}

			if (!component2.getChildren().isEmpty()) {
				deshabilitarComponentes(component2, sw);
			}

		}

	}

	private class Estate {
		boolean estado;
	}

	/**
	 * Titulo definido con
	 * */

	private class TreeTitle extends Treeitem {
		Component componentValue;
		Textbox tbxObservacion;

		public TreeTitle(Elm_ihc elm_ihc, String mold) {
			aplicarTitulo(elm_ihc, mold);
		}

		private void aplicarTitulo(Elm_ihc elm_ihc, String mold) {

			final Revision_sistema revision_sistema = new Revision_sistema();
			revision_sistema.setCodigo_empresa(zkWindow.codigo_empresa);
			revision_sistema.setCodigo_sucursal(zkWindow.codigo_sucursal);
			revision_sistema.setRespuesta("S");
			revision_sistema.setCodigo_revision(generarLLaveMapa(elm_ihc));

			String title = elm_ihc.getDsp_elm_ihc();
			Treerow treerow = new Treerow();
			Treecell treecell = new Treecell();
			setOpen(false);
			if (mold.equals("CHK") || mold.equals("CHO")) {
				Checkbox checkbox = new Checkbox(title);
				checkbox.setDisabled(true);
				componentValue = checkbox;
				treecell.appendChild(componentValue);
				if (mold.equals("CHO")) {
					tbxObservacion = new Textbox();
					tbxObservacion.setHflex("1");
					tbxObservacion.setVisible(false);
					treecell.appendChild(new Space());
					treecell.appendChild(new Space());
					treecell.appendChild(tbxObservacion);

					checkbox.addEventListener("onCheck",
							new EventListener<Event>() {
								@Override
								public void onEvent(Event event)
										throws Exception {
									CheckEvent checkEvent = (CheckEvent) event;
									tbxObservacion.setVisible(checkEvent
											.isChecked());
									tbxObservacion.setFocus(true);
								}
							});
					tbxObservacion.addEventListener(Events.ON_CHANGING,
							new EventListener<InputEvent>() {

								@Override
								public void onEvent(InputEvent inputEvent)
										throws Exception {
									revision_sistema
											.setObservaciones(inputEvent
													.getValue());
								}

							});
				}
			} else {
				Radiogroup radiogroup = new Radiogroup();
				radiogroup.appendItem("SI", "S");
				Radio radioNo = radiogroup.appendItem("NO", "N");
				radioNo.setSelected(true);
				componentValue = radiogroup;
				treecell.appendChild(componentValue);
				treecell.appendChild(new Space());
				treecell.appendChild(new Space());
				treecell.appendChild(new Label("" + title));
			}

			componentValue.setAttribute("OBJETO_ELEMENTO", revision_sistema);

			treerow.appendChild(treecell);
			appendChild(treerow);
		}

	}
}
