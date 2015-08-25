package com.framework.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.zkoss.calendar.api.DateFormatter;

import com.framework.res.ZKSimpleCalendarEvent;

/**
 * La clase <b>DateFormatterHelper</b> es la interceptora entre la fecha y el
 * calendario por la cual le va ha dar formato a las fechas
 * 
 * @author Luis Miguel Hernández Pérez
 * */
public class DateFormatterHelper implements DateFormatter {
	
	@Override
	public String getCaptionByDate(Date date, Locale locale, TimeZone arg2) {
		return formato(date, locale, "yyyy") + "- 1";
	}

	/**
	 * dias del mes
	 * */
	@Override
	public String getCaptionByDateOfMonth(Date date, Locale locale,
			TimeZone arg2) {
		return formato(date, locale, "dd");
	}

	/**
	 * Dias de la semana
	 * */
	@Override
	public String getCaptionByDayOfWeek(Date date, Locale locale, TimeZone arg2) {
		return formato(date, locale, "EEEEEEE");
	}

	/**
	 * Visualizador completo del dia
	 * */
	@Override
	public String getCaptionByPopup(Date date, Locale locale, TimeZone arg2) {
		return formato(date, locale, "EEEEEEE  MMMMMMMMMMM dd - yyyy");
	}

	/**
	 * formato en el horario este es el formato de los eventos
	 * */
	@Override
	public String getCaptionByTimeOfDay(Date date, Locale locale, TimeZone arg2) {
		return "";
	}

	@Override
	public String getCaptionByWeekOfYear(Date date, Locale locale, TimeZone arg2) {
		return formato(date, locale, "yyyy") + "- 6";
	}

	private String formato(Date date, Locale locale, String formato) {
		String respuesta_formato = new SimpleDateFormat(formato, locale)
				.format(date);
		return respuesta_formato;
	}

	private static String formato(Date date, String formato) {
		String respuesta_formato = new SimpleDateFormat(formato).format(date);
		return respuesta_formato;
	}

	/**
	 * Aplicamos el formato estandar
	 * 
	 * @author Luis Miguel
	 * */
	public static void aplicarFormatoValidoPara(
			ZKSimpleCalendarEvent zkSimpleCalendarEvent) {
		if (zkSimpleCalendarEvent.getDescripcion() == null) {
			zkSimpleCalendarEvent.setDescripcion("");
		}
		String horaIncio = formato(zkSimpleCalendarEvent.getBeginDate(),
				"hh:mm a");
		String horaFinal = formato(zkSimpleCalendarEvent.getEndDate(),
				"hh:mm a");
		zkSimpleCalendarEvent.setContent(horaIncio + " - " + horaFinal + " : "
				+ zkSimpleCalendarEvent.getDescripcion());
	}

}
