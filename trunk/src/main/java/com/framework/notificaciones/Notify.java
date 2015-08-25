package com.framework.notificaciones;

/**
 * 
 * @author CASEWAREDES05
 *
 */
public class Notify {
	private Tipos tipo;
	private String titulo;
	private String contenido;

	public Notify() {
		super();
	}

	public Notify(Tipos tipo, String titulo, String contenido) {
		super();
		this.tipo = tipo;
		this.titulo = titulo;
		this.contenido = contenido;
	}

	public Tipos getTipo() {
		return tipo;
	}

	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

}
