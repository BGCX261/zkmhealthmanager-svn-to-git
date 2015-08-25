package com.framework.notificaciones;

import fi.jawsy.jawwa.zk.gritter.Gritter;
import fi.jawsy.jawwa.zk.gritter.Gritter.NotificationBuilder;

/**
 * Clase que contiene los metodos para generar notificaciones( basadas en
 * gritter)
 * 
 * @author dperezc
 * 
 */
public class Notificaciones {

	private static final String img_informacion = "/images/gritter/information.png";
	private static final String img_alerta = "/images/gritter/alert.png";
	private static final String img_error = "/images/gritter/error.png";

	/**
	 * Genera una notificacion en un gritter personalizado con un estilo que se
	 * crea en la pagina inicio.zul (gritter-iceberg)
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param contenido
	 *            Contenido de la notificacion
	 * @param tiempo
	 *            Tiempo para que la notificacion se cierre
	 * @return NotificationBuilder
	 */
	private static NotificationBuilder getNotificacion(String titulo,
			String contenido, Integer tiempo) {
		if (tiempo != null) {
			return Gritter.notification().withSclass("gritter-project")
					.withTitle(titulo).withText(contenido).withTime(tiempo);
		} else {
			return Gritter.notification().withSclass("gritter-project")
					.withTitle(titulo).withText(contenido);
		}
	}

	/**
	 * Muestra una notificacion normal(simple) con un titulo , un contenido y un
	 * tiempo siempre y cuando se determine
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param contenido
	 *            Contenido de la notificacion
	 * @param tiempo
	 *            Tiempo para que la notificacion se cierre
	 */
	public static void mostrarNotificacion(String titulo, String contenido,
			Integer tiempo) {
		getNotificacion(titulo, contenido, tiempo).show();
	}

	/**
	 * Muestra una notificacion con la imagen definida para las notificaciones
	 * de informacion con un titulo , un contenido y un tiempo siempre y cuando
	 * se determine
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param contenido
	 *            Contenido de la notificacion
	 * @param tiempo
	 *            Tiempo para que la notificacion se cierre
	 */
	public static void mostrarNotificacionInformacion(String titulo,
			String contenido, Integer tiempo) {
		getNotificacion(titulo, contenido, tiempo).withImage(img_informacion)
				.show();
	}

	/**
	 * Muestra una notificacion con la imagen definida para las notificaciones
	 * de error con un titulo , un contenido y un tiempo siempre y cuando se
	 * determine
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param contenido
	 *            Contenido de la notificacion
	 * @param tiempo
	 *            Tiempo para que la notificacion se cierre
	 */
	public static void mostrarNotificacionError(String titulo,
			String contenido, Integer tiempo) {
		getNotificacion(titulo, contenido, tiempo).withImage(img_error).show();
	}

	/**
	 * Muestra una notificacion con la imagen definida para las notificaciones
	 * de alerta con un titulo , un contenido y un tiempo siempre y cuando se
	 * determine
	 * 
	 * @param titulo
	 *            Titulo de la notificacion
	 * @param contenido
	 *            Contenido de la notificacion
	 * @param tiempo
	 *            Tiempo para que la notificacion se cierre
	 */
	public static void mostrarNotificacionAlerta(String titulo,
			String contenido, Integer tiempo) {
		getNotificacion(titulo, contenido, tiempo).withImage(img_alerta).show();
	}
	
	/**
	 * Muestra una notificacion con la imagen definida para las notificaciones
	 * segun el tipo de notificacion , un contenido y un tiempo siempre y cuando se
	 * determine. El titulo, tipo y contenido vienen encapsulado en un objeto de tipo {@link Notify}
	 * 
	 * @param notify
	 * @param tiempo
	 */
	public static void mostrarNotificacion(Notify notify, Integer tiempo){
		switch (notify.getTipo()) {
		case INFORMACION:
			mostrarNotificacionInformacion(notify.getTitulo(), notify.getContenido(), tiempo);
			break;
			
		case ALERTA:
			mostrarNotificacionAlerta(notify.getTitulo(), notify.getContenido(), tiempo);
			break;
		
		case ERROR:
			mostrarNotificacionError(notify.getTitulo(), notify.getContenido(), tiempo);
			break;

		default:
			break;
		}
	}
	

}
