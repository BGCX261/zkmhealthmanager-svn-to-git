package com.framework.res;

import healthmanager.modelo.bean.Consultorio;

import org.zkoss.calendar.impl.SimpleCalendarEvent;

public class ZKSimpleCalendarEvent extends SimpleCalendarEvent {

	public static java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
			"yyyy-MM-dd hh:mm a");

	private boolean allDay;
	private boolean isCita;
	private String ubicacion;
	private String colorHeader;
	private String colorContent;
	private Integer consecutivo;
	private String estado_cita;
	private String codigo_cita;
	private String descripcion;
	private String codigo_centro_salud;
	private String codigo_consulorio;
	private String codigo_rol;
	private String codigo_programa_excepcion;
	private String evento_calendar;
	
	private Consultorio consultorio;

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public boolean isCita() {
		return isCita;
	}

	public void setCita(boolean isCita) {
		this.isCita = isCita;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getColorHeader() {
		return colorHeader;
	}

	public void setColorHeader(String colorHeader) {
		this.colorHeader = colorHeader;
	}

	public String getColorContent() {
		return colorContent;
	}

	public void setColorContent(String colorContent) {
		this.colorContent = colorContent;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	public void setEstado_cita(String estado_cita) {
		this.estado_cita = estado_cita;
	}

	public String getEstado_cita() {
		return estado_cita;
	}

	public void setCodigo_cita(String codigo_cita) {
		this.codigo_cita = codigo_cita;
	}

	public String getCodigo_cita() {
		return codigo_cita;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo_centro_salud() {
		return codigo_centro_salud;
	}

	public void setCodigo_centro_salud(String codigo_centro_salud) {
		this.codigo_centro_salud = codigo_centro_salud;
	}

	public String getCodigo_consulorio() {
		return codigo_consulorio;
	}

	public void setCodigo_consulorio(String codigo_consulorio) {
		this.codigo_consulorio = codigo_consulorio;
	}

	public String getEvento_calendar() {
		return evento_calendar;
	}

	public void setEvento_calendar(String evento_calendar) {
		this.evento_calendar = evento_calendar;
	}

	public String getCodigo_rol() {
		return codigo_rol;
	}

	public void setCodigo_rol(String codigo_rol) {
		this.codigo_rol = codigo_rol;
	}

	public String getCodigo_programa_excepcion() {
		return codigo_programa_excepcion;
	}

	public void setCodigo_programa_excepcion(String codigo_programa_excepcion) {
		this.codigo_programa_excepcion = codigo_programa_excepcion;
	}

	@Override
	public String toString() {

		return simpleDateFormat.format(getBeginDate()) + " || " + simpleDateFormat.format(getEndDate());
	}

	public Consultorio getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(Consultorio consultorio) {
		this.consultorio = consultorio;
	}
}
