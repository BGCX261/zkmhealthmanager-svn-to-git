package healthmanager.exception;

import healthmanager.modelo.bean.Empresa;

/**
 * 
 * */
public class BloqueoException extends RuntimeException {
	private Empresa empresa;

	public BloqueoException(String mensaje, Empresa empresa) {
		super(mensaje);
		this.empresa = empresa;
	}

	public BloqueoException(String mensaje, Throwable causa, Empresa empresa) {
		super(mensaje, causa);
		this.empresa = empresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}
}
