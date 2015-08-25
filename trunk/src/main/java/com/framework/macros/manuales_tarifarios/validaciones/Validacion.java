package com.framework.macros.manuales_tarifarios.validaciones;

/**
 * Esta clase es un bean, el cual se va ha utilizar 
 * para realizar las validaciones internas de un macrocompoenete
 * @author Luis Miguel
 * */
public class Validacion {

	private boolean valida;
	private String msj;

	public Validacion(boolean valida, String msj) {
		super();
		this.valida = valida;
		this.msj = msj;
	}

	public boolean isValida() {
		return valida;
	}

	public void setValida(boolean valida) {
		this.valida = valida;
	}

	public String getMsj() {
		return msj;
	}

	public void setMsj(String msj) {
		this.msj = msj;
	}

}
