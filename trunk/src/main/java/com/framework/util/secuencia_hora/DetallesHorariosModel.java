package com.framework.util.secuencia_hora;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DetallesHorariosModel<E> extends TreeMap<Long, E> {

	public static Calendar FECHA_CALENDAR_AUX = Calendar.getInstance();

	public List<E> getSublist(Date fecha_inicio, Date fecha_fin) {
		List<E> listado = new ArrayList<E>();
		for (Long key_mapa : keySet()) {
			if (key_mapa >= fecha_inicio.getTime()
					&& key_mapa <= fecha_fin.getTime())
				listado.add(get(key_mapa));
		}
		return listado;
	}

	public List<E> getSublist(Map<Long, E> mapa_informacion, Date fecha_inicio,
			Date fecha_fin) {
		FECHA_CALENDAR_AUX.setTime(fecha_fin);
		FECHA_CALENDAR_AUX.set(Calendar.HOUR, 23);
		FECHA_CALENDAR_AUX.set(Calendar.MINUTE, 59);
		FECHA_CALENDAR_AUX.set(Calendar.SECOND, 59);
		fecha_fin = FECHA_CALENDAR_AUX.getTime();
		List<E> listado = new ArrayList<E>();
		for (Long key_mapa : mapa_informacion.keySet()) {
			if (key_mapa >= fecha_inicio.getTime()
					&& key_mapa <= fecha_fin.getTime()) {
				listado.add(mapa_informacion.get(key_mapa));
			}
		}
		return listado;
	}

	public List<E> getSublist(Map<Long, E> mapa_informacion, Date fecha_inicio,
			Date fecha_fin, Comparador comparador) {
		FECHA_CALENDAR_AUX.setTime(fecha_fin);
		FECHA_CALENDAR_AUX.set(Calendar.HOUR, 23);
		FECHA_CALENDAR_AUX.set(Calendar.MINUTE, 59);
		FECHA_CALENDAR_AUX.set(Calendar.SECOND, 59);
		fecha_fin = FECHA_CALENDAR_AUX.getTime();
		List<E> listado = new ArrayList<E>();
		for (Long key_mapa : mapa_informacion.keySet()) {
			if (key_mapa >= fecha_inicio.getTime()
					&& key_mapa <= fecha_fin.getTime()) {
				if (comparador.comparacion(key_mapa))
					listado.add(mapa_informacion.get(key_mapa));
			}
		}
		return listado;
	}

	public Map<Long, E> getSubmap(Date fecha_inicio, Date fecha_fin) {
		FECHA_CALENDAR_AUX.setTime(fecha_fin);
		FECHA_CALENDAR_AUX.set(Calendar.HOUR, 23);
		FECHA_CALENDAR_AUX.set(Calendar.MINUTE, 59);
		FECHA_CALENDAR_AUX.set(Calendar.SECOND, 59);
		fecha_fin = FECHA_CALENDAR_AUX.getTime();
		Map<Long, E> sub_mapa = new TreeMap<Long, E>();
		for (Long key_mapa : keySet()) {
			if (key_mapa >= fecha_inicio.getTime()
					&& key_mapa <= fecha_fin.getTime())
				sub_mapa.put(key_mapa, get(key_mapa));
		}
		return sub_mapa;
	}

	public Map<Long, E> getSubmap(Date fecha_inicio, Date fecha_fin,
			Comparador comparador) {
		FECHA_CALENDAR_AUX.setTime(fecha_fin);
		FECHA_CALENDAR_AUX.set(Calendar.HOUR, 23);
		FECHA_CALENDAR_AUX.set(Calendar.MINUTE, 59);
		FECHA_CALENDAR_AUX.set(Calendar.SECOND, 59);
		fecha_fin = FECHA_CALENDAR_AUX.getTime();
		Map<Long, E> sub_mapa = new TreeMap<Long, E>();
		for (Long key_mapa : keySet()) {
			if (key_mapa >= fecha_inicio.getTime()
					&& key_mapa <= fecha_fin.getTime())
				if (comparador.comparacion(key_mapa))
					sub_mapa.put(key_mapa, get(key_mapa));
		}
		return sub_mapa;
	}

	public void agregar(Long key, E valor) {
		put(key, valor);
	}

	public interface Comparador {
		public boolean comparacion(Long fecha_long);
	}

}
