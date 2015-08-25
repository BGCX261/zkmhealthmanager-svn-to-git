package com.framework.macros.graficas.respuesta;

/**
 * Esta es la respuesta de cada interceptor
 * 
 * @author Luis Miguel
 * */
public class RespuestaInt {
	private double valor;
	private double x;
	private double y;
	private boolean actual;

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	public boolean isActual() {
		return actual;
	}
	public void setActual(boolean actual) {
		this.actual = actual;
	}
}
