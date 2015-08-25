package com.framework.util.secuencia_hora;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Luis Miguel
 * */
public class NHora {

	public Calendar calendarAcarreo;
	public String formato = "hh:mm a";

	native Calendar getCalendar();

	@Override
	public String toString() {
		return fomato(getCalendar());
	}

	public String fomato(Calendar calendar) {
		return new SimpleDateFormat(formato).format(calendar.getTime());
	}

	public Calendar clone() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCalendar().getTime());
		return calendar;
	}
	
	
	public Calendar getCalendarAcarreo(){
		return calendarAcarreo;
	}

}
