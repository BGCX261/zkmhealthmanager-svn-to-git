package com.framework.interfaces;

import com.framework.res.ZKSimpleCalendarEvent;

public interface EventosHorarios {

	/**
	 * Este metodo me permite agregar eventos a desde el componente Secuencia de
	 * Horarios
	 * 
	 * @author Luis Miguel
	 * */
	public void addEvent(ZKSimpleCalendarEvent zkSimpleCalendarEvent)
			throws Exception;

	/**
	 * Este metodo me permite eliminar eventos a desde el componente Secuencia
	 * de Horarios
	 * 
	 * @author Dario Perez Campillo
	 * */
	public void eliminarEvent(ZKSimpleCalendarEvent zkSimpleCalendarEvent);

	public void renderizarHorario();

}
