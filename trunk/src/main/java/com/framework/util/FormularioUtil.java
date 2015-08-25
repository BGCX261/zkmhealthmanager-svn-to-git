package com.framework.util;

import healthmanager.controller.ZKWindow;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.NumberInputElement;
import org.zkoss.zul.impl.XulElement;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.res.RadioGroupL2H;

public class FormularioUtil {

	public static Logger log = Logger.getLogger(FormularioUtil.class);

	public static final String RESTRICCION_COMPONENTE_VACIO = "RESTRICCION_COMPONENTE_VACIO";

	public static void deshabilitarComponentes(Component component, boolean sw,
			String... ids) {

		Map<String, String> mapaExcluyentes = new HashMap<String, String>();

		for (String id : ids) {
			mapaExcluyentes.put(id, id);
		}

		List<Component> listado = component.getChildren();

		for (Component component2 : listado) {

			if (!mapaExcluyentes.containsKey(component2.getId())) {

				if (component2 instanceof Textbox) {
					Textbox textboxTemp = (Textbox) component2;
					if (textboxTemp instanceof Bandbox) {
						((Bandbox) textboxTemp).setReadonly(sw);
						((Bandbox) textboxTemp).setButtonVisible(!sw);
					} else {
						textboxTemp.setReadonly(sw);
					}
				}

				if (component2 instanceof Listbox) {
					((Listbox) component2).setDisabled(sw);
				}
				if (component2 instanceof Doublebox) {
					((Doublebox) component2).setDisabled(sw);
				}
				if (component2 instanceof Intbox) {
					((Intbox) component2).setReadonly(sw);
				}
				if (component2 instanceof Datebox) {
					((Datebox) component2).setReadonly(sw);
					((Datebox) component2).setButtonVisible(!sw);
				}

				if (component2 instanceof Timebox) {
					((Timebox) component2).setReadonly(sw);
					((Timebox) component2).setButtonVisible(!sw);
				}

				if (component2 instanceof Checkbox) {
					((Checkbox) component2).setDisabled(sw);
				}
				if (component2 instanceof Radio) {
					((Radio) component2).setDisabled(sw);
				}

				if (component2 instanceof Spinner) {
					((Spinner) component2).setDisabled(sw);
				}

				if (component2 instanceof Toolbarbutton) {
					((Toolbarbutton) component2).setVisible(!sw);
				}

				if (component2 instanceof RadioGroupL2H) {
					((RadioGroupL2H) component2).setDisabled(sw);
				}

				if (component2 instanceof Radiogroup) {
					for (Object abstractComponentTemp : ((Radiogroup) component2)
							.getChildren()) {
						if (abstractComponentTemp instanceof Radio)
							((Radio) abstractComponentTemp).setDisabled(sw);
					}
				}

				if (!component2.getChildren().isEmpty()) {
					deshabilitarComponentes(component2, sw, ids);
				}

			}
		}

	}

	public static void limpiarComponentes(Component component, String... ids) {

		limpiarComponentesRestricciones(component);

		Map<String, String> mapaExcluyentes = new HashMap<String, String>();

		for (String id : ids) {
			mapaExcluyentes.put(id, id);
		}

		List<Component> listado = component.getChildren();

		for (Component component2 : listado) {
			if (!mapaExcluyentes.containsKey(component2.getId())) {
				if (component2 instanceof Textbox) {
					if (component2 instanceof Bandbox) {
						if (component2 instanceof BandboxRegistrosMacro) {
							((BandboxRegistrosMacro) component2)
									.limpiarSeleccion(false);
						} else {
							((Bandbox) component2).setValue("");
						}
					} else {
						((Textbox) component2).setValue("");
					}
				}

				if (component2 instanceof Listbox) {
					if (((Listbox) component2).getItemCount() > 0) {
						((Listbox) component2).setSelectedIndex(0);
					}
				}
				if (component2 instanceof Doublebox) {
					// ((Doublebox) component2).setValue(null);
				}
				if (component2 instanceof Intbox) {
					((Intbox) component2).setText("");
				}
				if (component2 instanceof Datebox) {
					((Datebox) component2).setValue(new Date());
				}
				if (component2 instanceof Checkbox) {
					if (!(component2 instanceof Radio))
						((Checkbox) component2).setChecked(false);
				}

				if (!component2.getChildren().isEmpty()) {
					limpiarComponentes(component2, ids);
				}

			}
		}

	}

	public static void setUpperCase(Component component, String... diferent) {
		Collection<Component> listado = component.getFellows();
		for (Component component2 : listado) {
			if (component2 instanceof Textbox) {
				Textbox textbox = (Textbox) component2;
				if (textbox.isValid() && !existe(textbox, diferent)) {
					if (!(textbox instanceof Combobox)) {
						((Textbox) component2).setText(((Textbox) component2)
								.getText().trim().toUpperCase());
					}
				}
			}
		}
	}

	/**
	 * Este metodo me permite verificar si existe un ID
	 * */
	private static boolean existe(Component component, String[] strings) {
		if (strings != null) {
			for (String id : strings) {
				String id_components = component.getId();
				if (id_components != null && id.equals(id_components)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Método que permite memorizar los radios seleccionados por defecto (debe
	 * ser llamado al iniciar la vista).
	 * 
	 * @param component
	 *            Componente que contiene los radiogroups.
	 */
	public static void inicializarRadiogroupsDefecto(Component component) {
		List<Component> listado = component.getChildren();
		for (Component component2 : listado) {
			if (component2 instanceof Radiogroup) {
				Integer index = ((Radiogroup) component2).getSelectedIndex();
				component2.setAttribute("defaultSelect", index);
			}
			if (!component2.getChildren().isEmpty()) {
				inicializarRadiogroupsDefecto(component2);
			}
		}
	}

	/**
	 * Método para marcar los radios a su estado por defecto.
	 * 
	 * @param component
	 *            Componente que contiene los radiogroups.
	 */
	public static void cargarRadiogroupsDefecto(Component component) {
		List<Component> listado = component.getChildren();
		for (Component component2 : listado) {
			if (component2 instanceof Radiogroup) {
				if (component2.getAttribute("defaultSelect") != null) {
					Integer index = (Integer) component2
							.getAttribute("defaultSelect");
					((Radiogroup) component2).setSelectedIndex(index);
				}
			}
			if (!component2.getChildren().isEmpty()) {
				cargarRadiogroupsDefecto(component2);
			}
		}
	}

	/**
	 * Metodo para mostrar campos visibles al momento de seleccionar el
	 * radiogroup
	 * 
	 * @param form
	 *            Identificacion del formulario radiogroup Id del radiogroup
	 *            valor Contiene el value que hace visible los campos
	 *            abstractComponents Contiene todos los componentes que se les
	 *            ejecuta la accion
	 */

	public static void mostrarComponentes_conRadiogroup(ZKWindow form,
			Radiogroup radiogroup, String valor,
			AbstractComponent[] abstractComponents) {
		try {
			// log.info("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals(valor)) {
					abstractComponent.setVisible(true);

					if (abstractComponent instanceof Radiogroup) {
						((Radio) ((Radiogroup) abstractComponent)
								.getFirstChild()).setChecked(true);
					}

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());

					}

					if (abstractComponent instanceof Timebox) {
						((Timebox) abstractComponent).setValue(new Date());

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Doublebox) {
						((Doublebox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Listbox) {
						if (((Listbox) abstractComponent).getItemCount() > 0) {
							((Listbox) abstractComponent).setSelectedIndex(0);
						}
						UtilidadesComponentes.listarElementosListbox(true,
								false, form.getServiceLocator(),
								((Listbox) abstractComponent));

					}

				} else {

					if (abstractComponent instanceof Radiogroup) {
						((Radio) ((Radiogroup) abstractComponent)
								.getFirstChild()).setChecked(true);
					}

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);

					}

					if (abstractComponent instanceof Timebox) {
						((Timebox) abstractComponent).setValue(null);

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}
					if (abstractComponent instanceof Doublebox) {
						((Doublebox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Listbox) {
						((Listbox) abstractComponent).getChildren().clear();
						for (int i = 0; i < ((Listbox) abstractComponent)
								.getItemCount(); i++) {

							Listitem listitem = ((Listbox) abstractComponent)
									.getItemAtIndex(i);
							if (listitem.getValue().toString().equals("")) {
								listitem.setSelected(true);
								i = ((Listbox) abstractComponent)
										.getItemCount();
							}
						}

					}

					abstractComponent.setVisible(false);

				}
			}
		} catch (Exception e) {
			// block
			// log.info("Excepcion loaded");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para habilitar campos ocultos al momento de seleccionar el
	 * radiogroup
	 * 
	 * @param form
	 *            Identificacion del formulario radiogroup Id del radiogroup
	 *            valor Contiene el value que hace visible los campos
	 *            abstractComponents Contiene todos los componentes que se les
	 *            ejecuta la accion
	 */

	public static void deshabilitarComponentes_conRadiogroup(
			Radiogroup radiogroup, String valor,
			AbstractComponent[] abstractComponents) {
		try {
			// log.info("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals(valor)) {

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());
						((Datebox) abstractComponent).setReadonly(false);
						((Datebox) abstractComponent).setButtonVisible(true);

					}
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());
						((Datebox) abstractComponent).setReadonly(false);
						((Datebox) abstractComponent).setButtonVisible(true);

					}

					if (abstractComponent instanceof Checkbox) {
						if (abstractComponent instanceof Radio) {
							((Radio) abstractComponent).setSelected(true);
							((Radio) abstractComponent).setVisible(true);

						} else {
							((Checkbox) abstractComponent).setValue("");
							((Checkbox) abstractComponent).setChecked(false);
							((Checkbox) abstractComponent).setDisabled(false);
						}
					}

					if (abstractComponent instanceof Timebox) {
						((Timebox) abstractComponent).setValue(new Date());
						((Timebox) abstractComponent).setReadonly(false);
						((Timebox) abstractComponent).setButtonVisible(true);

					}
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
						((Intbox) abstractComponent).setReadonly(false);

					}

					if (abstractComponent instanceof Doublebox) {
						((Doublebox) abstractComponent).setText("");
						((Doublebox) abstractComponent).setReadonly(false);

					}

					if (abstractComponent instanceof Radio) {
						((Radio) abstractComponent).setSelected(true);
						((Radio) abstractComponent).setDisabled(false);
					}

				} else {

					if (abstractComponent instanceof Radio) {
						((Radio) abstractComponent).setSelected(true);
						((Radio) abstractComponent).setDisabled(true);
					}

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);
						((Datebox) abstractComponent).setReadonly(true);
						((Datebox) abstractComponent).setButtonVisible(false);

					}

					if (abstractComponent instanceof Timebox) {
						((Timebox) abstractComponent).setValue(null);
						((Timebox) abstractComponent).setReadonly(true);
						((Timebox) abstractComponent).setButtonVisible(false);

					}
					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setValue("");
						((Checkbox) abstractComponent).setChecked(false);
						((Checkbox) abstractComponent).setDisabled(true);
					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
						((Intbox) abstractComponent).setReadonly(true);

					}

					if (abstractComponent instanceof Doublebox) {
						((Doublebox) abstractComponent).setText("");
						((Doublebox) abstractComponent).setReadonly(true);

					}

				}
			}
		} catch (Exception e) {
			// block
			// log.info("Excepcion loaded");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para mostrar campos ocultos al momento de seleccionar el Checkbox
	 * 
	 * @param form
	 *            Identificacion del formulario checkbox Id del checkbox
	 *            abstractComponents Contiene todos los componentes que se les
	 *            ejecuta la accion
	 */

	public static void mostrarComponentes_conCheckbox(ZKWindow form,
			Checkbox checkbox, AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {
					abstractComponent.setVisible(true);

					if (abstractComponent instanceof Radiogroup) {
						((Radio) ((Radiogroup) abstractComponent)
								.getFirstChild()).setChecked(true);
					}

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());

					}

					if (abstractComponent instanceof Timebox) {
						((Timebox) abstractComponent).setValue(new Date());

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Doublebox) {
						((Doublebox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Listbox) {
						if (((Listbox) abstractComponent).getItemCount() > 0) {
							((Listbox) abstractComponent).setSelectedIndex(0);
						}
						UtilidadesComponentes.listarElementosListbox(true,
								false, form.getServiceLocator(),
								((Listbox) abstractComponent));

					}

				} else {

					if (abstractComponent instanceof Radiogroup) {
						((Radio) ((Radiogroup) abstractComponent)
								.getFirstChild()).setChecked(true);
					}

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);

					}

					if (abstractComponent instanceof Timebox) {
						((Timebox) abstractComponent).setValue(null);

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}
					if (abstractComponent instanceof Doublebox) {
						((Doublebox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Listbox) {
						((Listbox) abstractComponent).getChildren().clear();
						for (int i = 0; i < ((Listbox) abstractComponent)
								.getItemCount(); i++) {

							Listitem listitem = ((Listbox) abstractComponent)
									.getItemAtIndex(i);
							if (listitem.getValue().toString().equals("")) {
								listitem.setSelected(true);
								i = ((Listbox) abstractComponent)
										.getItemCount();
							}
						}

					}

					abstractComponent.setVisible(false);

				}
			}
		} catch (Exception e) {
			// block
			// log.info("Excepcion loaded");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para habilitar campos ocultos al momento de seleccionar el
	 * Checkbox
	 * 
	 * @param checkbox
	 *            Id del checkbox abstractComponents Contiene todos los
	 *            componentes que se les ejecuta la accion
	 */

	public static void deshabilitarComponentes_conCheckbox(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());
						((Datebox) abstractComponent).setReadonly(false);
						((Datebox) abstractComponent).setButtonVisible(true);

					}

					if (abstractComponent instanceof Timebox) {
						((Timebox) abstractComponent).setValue(new Date());
						((Timebox) abstractComponent).setReadonly(false);
						((Timebox) abstractComponent).setButtonVisible(true);

					}
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
						((Intbox) abstractComponent).setReadonly(false);

					}

					if (abstractComponent instanceof Doublebox) {
						((Doublebox) abstractComponent).setText("");
						((Doublebox) abstractComponent).setReadonly(false);

					}

				} else {

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);
						((Datebox) abstractComponent).setReadonly(true);
						((Datebox) abstractComponent).setButtonVisible(false);

					}

					if (abstractComponent instanceof Timebox) {
						((Timebox) abstractComponent).setValue(null);
						((Timebox) abstractComponent).setReadonly(true);
						((Timebox) abstractComponent).setButtonVisible(false);

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
						((Intbox) abstractComponent).setReadonly(true);

					}

					if (abstractComponent instanceof Doublebox) {
						((Doublebox) abstractComponent).setText("");
						((Doublebox) abstractComponent).setReadonly(true);

					}
				}
			}
		} catch (Exception e) {
			// block
			// log.info("Excepcion loaded");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para marcar los campos obligatorios con rojo sin necesidad de
	 * cambiarles el background-color, sino marcando el arrededor en rojo.
	 * 
	 * @param componentes
	 *            Componentes de tipo XulElement que se desean colocar como
	 *            obligatorios
	 * @return
	 */

	public static void validarCamposObligatorios(XulElement... componentes) {
		if (componentes != null) {
			for (XulElement xulElement : componentes) {
				limpiarComponentesRestricciones(xulElement);
				if (componenteVisible(xulElement)) {
					if (!xulElement.hasAttribute(RESTRICCION_COMPONENTE_VACIO)) {
						xulElement.setAttribute(RESTRICCION_COMPONENTE_VACIO,
								RESTRICCION_COMPONENTE_VACIO);
					}
				}
			}

			XulElement elemento = null;

			for (XulElement xulElement : componentes) {
				if (componenteVisible(xulElement)) {
					if (xulElement instanceof BandboxRegistrosMacro) {
						if (((BandboxRegistrosMacro) xulElement)
								.getRegistroSeleccionado() == null) {
							Clients.wrongValue(xulElement, "Campo obligatorio");
							if (elemento == null)
								elemento = xulElement;
						}
					} else if (xulElement instanceof InputElement) {
						if (xulElement
								.hasAttribute(RESTRICCION_COMPONENTE_VACIO)) {
							if (((InputElement) xulElement).getText() == null
									|| ((InputElement) xulElement).getText()
											.isEmpty()) {
								xulElement
										.setSclass("z-textbox z-textbox-text-invalid");
								if (elemento == null)
									elemento = xulElement;
							}
						}
					} else if (xulElement instanceof Listbox) {
						if (xulElement
								.hasAttribute(RESTRICCION_COMPONENTE_VACIO)) {
							if (!(((Listbox) xulElement).getSelectedItem() != null
									&& ((Listbox) xulElement).getSelectedItem()
											.getValue() != null && !((Listbox) xulElement)
									.getSelectedItem().getValue().toString()
									.isEmpty())) {
								if (elemento == null)
									elemento = xulElement;
							}
						}
					}
				}
			}

			final XulElement elemento_aux = elemento;

			if (elemento_aux != null) {
				// log.info("Mostrando alerta de que hay elementos vacios que deben ser diligenciados ===> "
				// + elemento_aux.getId());
				MensajesUtil
						.mensajeAlerta(
								"Campos vacios no diligenciados",
								"Los campos demarcados con rojo o seleccionados son obligatorios. Para continuar con el proceso estos campos deben estar diligenciados",
								new EventListener<Event>() {

									@Override
									public void onEvent(Event event)
											throws Exception {
										elemento_aux.setFocus(true);
									}
								});
				throw new WrongValueException(elemento_aux,
						"El componente con id " + elemento_aux.getId()
								+ " tiene un valor erroneo");
			} else {
				// log.info("Todos los elementos obligatorios estan previamente diligenciados");
			}
		}
	}

	public static void limpiarComponentesRestricciones(Component component) {
		if (component instanceof XulElement) {
			component.removeAttribute(RESTRICCION_COMPONENTE_VACIO);
			if (component instanceof Bandbox) {
				((XulElement) component).setSclass("z-bandbox");
			} else if (component instanceof Textbox) {
				((XulElement) component).setSclass("z-textbox");
			} else if (component instanceof Datebox) {
				((XulElement) component).setSclass("z-datebox");
			} else if (component instanceof Bandbox) {

			} else {
				Clients.clearWrongValue(component);
			}
		}

		List<Component> listado = component.getChildren();

		if (!listado.isEmpty()) {
			for (Component component2 : listado) {
				limpiarComponentesRestricciones(component2);
			}
		}

	}

	private static boolean componenteVisible(Component component) {
		if (component.isVisible()) {
			if (component.getParent() != null)
				return componenteVisible(component.getParent());
			else
				return true;
		} else {
			return false;
		}

	}

	/**
	 * Metodo para marcar los campos obligatorios con rojo sin necesidad de
	 * cambiarles el background-color, sino marcando el arrededor en rojo.
	 * 
	 * @param componentes
	 *            Componentes de tipo {@link InputElement} que se desean validar
	 *            como campos con caracteres minimos
	 * 
	 * @param minimo
	 *            Caracteres minimos de los campos a evaluar
	 * 
	 * @return
	 */

	public static void validarCaracteresMinimos(int minimo,
			InputElement... componentes) {
		if (componentes != null) {
			XulElement elemento = null;
			for (InputElement inputElement : componentes) {
				if (componenteVisible(inputElement)) {
					if (!(inputElement.getText() != null && inputElement
							.getText().length() >= minimo)) {
						Clients.wrongValue(inputElement, "");
						if (elemento == null)
							elemento = inputElement;
					}
				}
			}

			final XulElement elemento_aux = elemento;

			if (elemento_aux != null) {
				// log.info("Mostrando alerta de que hay elementos que no tienen el valor minimo de caracteres que deben ser diligenciados ===> "
				// + elemento_aux.getId());
				MensajesUtil
						.mensajeAlerta(
								"Valores no alcanzan caracteres minimos ("
										+ minimo + " caracteres)",
								"Los campos demarcados con rojo no alcanzan los caracteres minimos que son "
										+ minimo
										+ ". Para continuar con el proceso estos campos deben estar bien diligenciados",
								new EventListener<Event>() {

									@Override
									public void onEvent(Event event)
											throws Exception {
										elemento_aux.setFocus(true);
									}
								});
				throw new WrongValueException(
						elemento_aux,
						"El componente con id "
								+ elemento_aux.getId()
								+ " tiene un valor erroneo de caracteres minimos");
			} else {
				// log.info("Todos los elementos obligatorios estan previamente diligenciados");
			}
		}
	}

	/**
	 * Metodos para mostrar un row de observaciones de los radiogroup que se
	 * encuentran en el row superior
	 */

	public static void mostrarObservaciones(Radiogroup radiogroup_seleccionado,
			String marca, Textbox target, Row row_contenedor) {
		if (radiogroup_seleccionado != null
				&& radiogroup_seleccionado.getSelectedItem() != null) {
			Boolean respuesta = radiogroup_seleccionado.getSelectedItem()
					.getValue().equals(marca);
			target.setVisible(respuesta);
			if (row_contenedor != null) {
				verificarRow(row_contenedor);
			}
		}
	}

	/**
	 * Metodos para mostrar un row de observaciones de los radiogroup que se
	 * encuentran en el row superior
	 */

	public static void mostrarObservaciones(Radiogroup radiogroup_seleccionado,
			String marca, Label target, Row row_contenedor) {
		if (radiogroup_seleccionado != null
				&& radiogroup_seleccionado.getSelectedItem() != null) {
			Boolean respuesta = radiogroup_seleccionado.getSelectedItem()
					.getValue().equals(marca);
			target.setVisible(respuesta);
			if (row_contenedor != null) {
				verificarRow(row_contenedor);
			}
		}
	}

	public static void verificarRow(Row row_contenedor) {
		if (row_contenedor != null) {
			row_contenedor.setVisible(false);
			for (Component componente : row_contenedor.getChildren()) {
				if (componente instanceof Cell) {
					for (Component componente_1 : componente.getChildren()) {
						if (componente_1 instanceof Textbox) {
							if (componente_1.isVisible()) {
								row_contenedor.setVisible(true);
								return;
							}
						}
					}
				}

			}
		}
	}
	
	public static Label getLabelSize(String valor, String tamanio){
		Label label = new Label(valor);
		label.setStyle("font-size:"+tamanio);
		return label;
	}
	
	public static XulElement obtenerComponenteCelda(Object valor,
			Class<? extends XulElement> classs, boolean readonly,
			boolean inplace) {
		XulElement component = null;
		if (classs.getName().equals(Textbox.class.getName())) {
			component = new Textbox(valor != null ? valor.toString() : "");
		} else if (classs.getName().equals(Doublebox.class.getName())) {
			component = new Doublebox((Double) valor);
			((Doublebox) component).setFormat("#,##0.00");
		} else if (classs.getName().equals(Label.class.getName())) {
			component = new Label(valor.toString());
		} else if (classs.getName().equals(Intbox.class.getName())) {
			component = new Intbox(valor != null ? Integer.parseInt(valor
					.toString()) : 0);
		} else if (classs.getName().equals(Datebox.class.getName())) {
			if (valor instanceof Date) {
				component = new Datebox((Date) valor);
				((Datebox) component).setFormat("yyyy-MM-dd");
			} else {
				component = new Datebox();
				((Datebox) component).setFormat("yyyy-MM-dd");
				((Datebox) component).setText((String) valor);
			}
			((Datebox) component).setButtonVisible(!readonly);
		}
		component.setWidth("98%");
		if (component instanceof InputElement) {
			((InputElement) component).setReadonly(readonly);
			((InputElement) component).setInplace(inplace);

		}
		return component;
	}

	public static void validarCantidadMaxima(final Integer cantidad_maxima, 
			final Doublebox tbxCantidad, final String descripcion, final String tipo) {
		tbxCantidad.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
						onAccionValidarCantidadMaxima(cantidad_maxima,
								tbxCantidad, descripcion, tipo);
			}
		});
	}
	
	
	/**
	 * Este metodo me permite validar de manera directa, las cantidades
	 * maximas permitidas para un servicio
	 * */
	public static boolean onAccionValidarCantidadMaxima(final Integer cantidad_maxima, 
			final NumberInputElement tbxCantidad, final String descripcion, final String tipo){
		if(cantidad_maxima != null && cantidad_maxima > 0){
			Double cantidad_utilizada = tbxCantidad instanceof Doublebox ? ((Doublebox) tbxCantidad)
					.getValue() : ((Intbox) tbxCantidad).getValue();
			if (cantidad_utilizada != null
					&& cantidad_utilizada.intValue() > cantidad_maxima.intValue()) {
				if(tbxCantidad instanceof Doublebox){
					((Doublebox) tbxCantidad).setValue(cantidad_maxima);
				}else{
					((Intbox) tbxCantidad).setValue(cantidad_maxima.intValue()); 
				}
				MensajesUtil
						.mensajeAlerta(
								"Advertencia",
								"La cantidad "
										+ cantidad_utilizada
										+ " para el "
										+ tipo
										+ " "
										+ descripcion
										+ " no puede sobrepasar el valor maximo permitido que es "
										+ cantidad_maxima);
				return false;
			}
		}
		return true;
	}
	

}
