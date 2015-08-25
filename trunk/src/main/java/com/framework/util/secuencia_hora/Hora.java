package com.framework.util.secuencia_hora;

import java.util.Calendar;
import java.util.Date;

/**
 * Clase Hora
 * <br/>
 * Esta clase simplifica el proceso, para visualizar las horas
 * en una hora inicial y una hora final.
 * @author Luis Miguel
 * */
public class Hora extends NHora{
	
	private Calendar calendar;
	
	private void inyectarFecha(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date); 
		calendar.set(Calendar.SECOND, 0); 
	}

	public Hora(Date date) {
		inyectarFecha(date);
	}
	
	@Override
	Calendar getCalendar() {
		return calendar;
	}
	
	public int compareTo(Hora hora){
		return compareTo(hora.getCalendar()); 
	}
	
	public int compareTo(Calendar calendar){
		return this.calendar.compareTo(calendar); 
	}
	
	public String incrementar(int secuencia){
		calendarAcarreo = clone();
		calendar.set(Calendar.MINUTE,  calendar.get(Calendar.MINUTE) + secuencia); 
		return  fomato(calendarAcarreo) + " - " + toString(); 
	}

}
