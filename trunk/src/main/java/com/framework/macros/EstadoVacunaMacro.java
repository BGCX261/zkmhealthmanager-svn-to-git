package com.framework.macros;

import healthmanager.controller.ZKWindow;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;

import com.framework.util.MensajesUtil;
import com.framework.util.UtilidadesComponentes;

public class EstadoVacunaMacro extends Toolbarbutton implements AfterCompose {

	private static final long serialVersionUID = 1L;
	// private static Logger log = Logger.getLogger(EstadoVacunaMacro.class);
	private Popup popupObservaciones = null;

	private ZKWindow window;
	private Textbox textbox;
	private Listbox listbox;

	public OnAccionEstadoVacunacion onAccionEstadoVacunacion;

	@Override
	public void afterCompose() {
		addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// log.info("@onEvent cargando el evento.");
				if (popupObservaciones == null)
					popupObservaciones = generarpopUp();
				abrirPopUp();
				// log.info("@onEvent despues de cargar el evento.");
			}
		});
	}

	private void abrirPopUp() {
		if (popupObservaciones != null) {
			popupObservaciones.open(EstadoVacunaMacro.this, "before_start");
			popupObservaciones.focus();
		}
	}

	public EstadoVacunaMacro(ZKWindow window, String src) {
		this.window = window;
		setImage(src);
		afterCompose();
	}

	public EstadoVacunaMacro() {

	}

	/**
	 * Este metodo se encarga de generar el popup
	 * 
	 * @author Luis Miguel Hernandez
	 * */
	protected Popup generarpopUp() {
		final Popup popup = new Popup();
		textbox = new Textbox();
		// textbox.setReadonly(isReadOnly());
		popup.addEventListener(Events.ON_OPEN, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				// textbox.setValue(EstadoVacunaMacro.this.getValue());
				// textbox.setFocus(true);
			}
		});

		// textbox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
		// @Override
		// public void onEvent(Event event) throws Exception {
		// if(onCambioTexto != null){
		// onCambioTexto.texto(textbox.getValue());
		// //log.info("@generarpopUp Texto seleccionado: " +
		// textbox.getValue());
		// }
		// }
		// });

		/* Agregamos titulo de */
		Vlayout vlayout = new Vlayout();

		Hbox hbox = new Hbox();
		hbox.appendChild(new Label("Motivo: "));

		listbox = new Listbox();
		listbox.setName("cancelacion_vacuna");
		listbox.setMold("select");
		listbox.setWidth("300px");
		UtilidadesComponentes.listarElementosListbox(true, false,
				window.getServiceLocator(), listbox);
		hbox.appendChild(listbox);

		// cargamos
		vlayout.appendChild(hbox);

		Label labelObservaciones = new Label("Observaciones");
		labelObservaciones.setStyle("font-weight:bold");

		vlayout.appendChild(labelObservaciones);

		/* Agregamos campos de textos */
		textbox.setWidth("350px");
		textbox.setRows(4);

		vlayout.appendChild(textbox);

		// eventos del popup

		hbox = new Hbox();

		Toolbarbutton toolbarbutton = new Toolbarbutton("Guardar");
		toolbarbutton.setImage("/images/Save16.gif");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						if (onAccionEstadoVacunacion != null) {
							if (validar()) {
								Messagebox
										.show("Esta seguro que desea realizar esta accion?",
												"Eliminar Registro",
												Messagebox.YES + Messagebox.NO,
												Messagebox.QUESTION,
												new org.zkoss.zk.ui.event.EventListener<Event>() {
													public void onEvent(
															Event event)
															throws Exception {
														if ("onYes".equals(event
																.getName())) {
															boolean guardo = onAccionEstadoVacunacion
																	.onGuardar(
																			listbox.getSelectedItem()
																					.getValue()
																					.toString(),
																			textbox.getValue());
															if (guardo) {
																popupObservaciones
																		.close();
															}
														}
													}
												});
							}
						}
					}
				});
		hbox.appendChild(toolbarbutton);

		toolbarbutton = new Toolbarbutton("Cancelar");
		toolbarbutton.setImage("/images/eliminar.gif");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						listbox.setSelectedIndex(0);
						textbox.setValue("");
						popupObservaciones.close();
					}
				});
		hbox.appendChild(toolbarbutton);
		vlayout.appendChild(hbox);
		// textbox.setValue(EstadoVacunaMacro.this.getValue());
		// textbox.addEventListener("onChanging", new EventListener<Event>() {
		// @Override
		// public void onEvent(Event event) throws Exception {
		// InputEvent inputevent = (InputEvent) event;
		// EstadoVacunaMacro.this.setValue(inputevent.getValue());
		// }
		// });
		popup.appendChild(vlayout);
		getPage().getFirstRoot().appendChild(popup);
		return popup;
	}

	protected boolean validar() {
		boolean validar = true;
		String msj = "Campos requeridos";
		try {
			if (listbox.getSelectedIndex() == 0) {
				validar = false;
				msj = "Para realizar esta accion debe seleccionar un estado";
			}

			if (textbox.getValue().trim().isEmpty() && validar) {
				msj = "Para realizar esta accion debe digitar la observacion";
				validar = false;
			}

		} catch (WrongValueException e) {
			validar = false;
		}
		if (!validar) {
			MensajesUtil.mensajeAlerta("Advertencia", "" + msj,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							abrirPopUp();
						}
					});
		}
		return validar;
	}

	public ZKWindow getWindow() {
		return window;
	}

	public void setWindow(ZKWindow window) {
		this.window = window;
	}

	public interface OnAccionEstadoVacunacion {
		boolean onGuardar(String estado, String observacion);
	}

	public OnAccionEstadoVacunacion getOnAccionEstadoVacunacion() {
		return onAccionEstadoVacunacion;
	}

	public void setOnAccionEstadoVacunacion(
			OnAccionEstadoVacunacion onAccionEstadoVacunacion) {
		this.onAccionEstadoVacunacion = onAccionEstadoVacunacion;
	}
}
