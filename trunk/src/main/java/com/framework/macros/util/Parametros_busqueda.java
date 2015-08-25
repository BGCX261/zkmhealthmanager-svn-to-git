package com.framework.macros.util;

public class Parametros_busqueda {
	private String etiqueta;
	private String valor;
	
	public Parametros_busqueda(String etiqueta, String valor){
		this.setEtiqueta(etiqueta);
		this.setValor(valor);
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
