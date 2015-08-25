package com.framework.util;

import healthmanager.controller.ZKWindow;
import healthmanager.controller.exception.ExceptionAction;
import healthmanager.exception.ValidacionRunTimeException;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.framework.res.Res;

public class MensajesUtil {

	private static Logger log = Logger.getLogger(MensajesUtil.class);

	public static final String POSICION_ALERTA = "start_center";
	public static Integer TIEMPO_ALERTA = 3000;

	/**
	 * Metodo para mostrar un mensaje de informacion al usuario. Implementa el
	 * Mensaje de informacion del {@link Messagebox }
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param mensaje
	 *            Contenido de la notificacion
	 */
	public static void mensajeInformacion(String titulo, String mensaje) {
		try {
			Messagebox.show(mensaje, titulo, Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {

		}
	}

	/**
	 * Metodo para mostrar un mensaje de informacion al usuario. Implementa el
	 * Mensaje de informacion del {@link Messagebox }
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param mensaje
	 *            Contenido de la notificacion
	 */
	public static void mensajeInformacion(String titulo, String mensaje,
			EventListener<Event> eventListener) {
		try {
			Messagebox.show(mensaje, titulo, Messagebox.OK,
					Messagebox.INFORMATION, eventListener);
		} catch (Exception e) {

		}
	}

	/**
	 * Metodo para mostrar un mensaje de alerta al usuario. Implementa el
	 * Mensaje de Alerta del {@link Messagebox }
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param mensaje
	 *            Contenido de la notificacion
	 */
	public static void mensajeAlerta(String titulo, String mensaje) {
		try {
			Messagebox.show(mensaje, titulo, Messagebox.OK,
					Messagebox.EXCLAMATION);
		} catch (Exception e) {

		}
	}

	/**
	 * Metodo para mostrar un mensaje de error al usuario. Implementa el Mensaje
	 * de Error del {@link Messagebox }
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param mensaje
	 *            Contenido de la notificacion
	 */
	public static void mensajeError(String titulo, String mensaje) {
		try {
			Messagebox.show(mensaje, titulo, Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {

		}
	}

	/**
	 * Metodo para mostrar un mensaje de alerta al usuario. Implementa el
	 * Mensaje de Alerta del {@link Messagebox }
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param mensaje
	 *            Contenido de la notificacion
	 */
	public static void mensajeAlerta(String titulo, String mensaje,
			EventListener<Event> eventListener) {
		try {
			Messagebox.show(mensaje, titulo, Messagebox.OK,
					Messagebox.EXCLAMATION, eventListener);
		} catch (Exception e) {

		}
	}

	/**
	 * Metodo para mostrar un mensaje de error al usuario. Implementa el Mensaje
	 * de Error del {@link Messagebox }
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param mensaje
	 *            Contenido de la notificacion
	 */
	public static void mensajeError(String titulo, String mensaje,
			EventListener<Event> eventListener) {
		try {
			Messagebox.show(mensaje, titulo, Messagebox.OK, Messagebox.ERROR,
					eventListener);
		} catch (Exception e) {

		}
	}

	/**
	 * Metodo para mostrar de forma amigable un error en tiempo de ejecucion,
	 * utilizando una vista dedicada para ese proceso
	 * 
	 * @param throwable
	 *            Excepcion que lanza el sistema
	 * @param msgAdicinal
	 *            Mensaje adicional para mostrar en la causa de la excepcion
	 * @param ventana
	 *            Ventana donde ocurre la excepcion
	 */

	public static void mensajeError(final Throwable throwable,
			String msgAdicinal, final Component ventana) {
		if (throwable instanceof ValidacionRunTimeException) {
			MensajesUtil.mensajeError("Advertencia", throwable.getMessage());
			log.error("Error de tipo ValidacionRunTimeException: ", throwable);
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("throwable", throwable);
			parametros.put("msgAdicinal", msgAdicinal);
			final ExceptionAction exceptionAction = (ExceptionAction) Executions
					.createComponents("/pages/exception/excepcion.zul", null,
							parametros);
			exceptionAction.getBtnEnviarError().addEventListener(
					Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							if (ventana instanceof ZKWindow) {
								ZKWindow window = (ZKWindow) ventana;
								Res.sendMsjError(
										throwable,
										window.codigo_empresa + " "
												+ window.empresa != null ? window.empresa
												.getNombre_empresa()
												: "La empresa no se pudo cargar",
										window.getClass().getName(), window
												.getUserInSessionError());
							}

							exceptionAction.detach();
						}
					});
			exceptionAction.setMode("modal");
			log.error(throwable.getMessage(), throwable);
		}
	}

	/**
	 * Mandar una notificacion de informacion (info) usando la Clase Clients
	 * 
	 * @param alerta
	 *            Mensaje o contenido de lo que se desea alertar
	 * @param componente_ref
	 *            referencia donde va a salir la notificacion
	 */
	public static void notificarInformacion(String alerta,
			Component componente_ref) {
		Clients.showNotification(alerta, Clients.NOTIFICATION_TYPE_INFO,
				componente_ref, POSICION_ALERTA, TIEMPO_ALERTA, true);
	}

	/**
	 * Mandar una notificacion de alerta (warning) usando la Clase Clients
	 * 
	 * @param alerta
	 *            Mensaje o contenido de lo que se desea alertar
	 * @param componente_ref
	 *            referencia donde va a salir la notificacion
	 */
	public static void notificarAlerta(String alerta, Component componente_ref) {
		Clients.showNotification(alerta, Clients.NOTIFICATION_TYPE_WARNING,
				componente_ref, POSICION_ALERTA, TIEMPO_ALERTA, true);
	}

	/**
	 * Mandar una notificacion de Error (error) usando la Clase Clients
	 * 
	 * @param alerta
	 *            Mensaje o contenido de lo que se desea alertar
	 * @param componente_ref
	 *            referencia donde va a salir la notificacion
	 */
	public static void notificarError(String alerta, Component componente_ref) {
		Clients.showNotification(alerta, Clients.NOTIFICATION_TYPE_ERROR,
				componente_ref, POSICION_ALERTA, TIEMPO_ALERTA, true);
	}

	/**
	 * Mandar una notificacion usando la Clase Clients
	 * 
	 * @param alerta
	 *            Mensaje o contenido de lo que se desea alertar
	 * @param tipo
	 *            Tipo de la notificacion que se desea mostrar
	 * @param componente_ref
	 *            referencia donde va a salir la notificacion
	 */
	public static void notificar(String alerta, String tipo,
			Component componente_ref) {
		Clients.showNotification(alerta, tipo, componente_ref, POSICION_ALERTA,
				TIEMPO_ALERTA, true);
	}

}
