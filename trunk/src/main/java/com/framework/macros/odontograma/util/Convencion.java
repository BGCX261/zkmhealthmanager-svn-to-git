package com.framework.macros.odontograma.util;

import org.apache.log4j.Logger;

import com.framework.macros.odontograma.DienteMacro;

public class Convencion {
	
	public static Logger log = Logger.getLogger(Convencion.class);
	
	private DienteMacro dienteMacro;

	public DienteMacro getDienteMacro() {
		return dienteMacro;
	}

	public void setDienteMacro(DienteMacro dienteMacro) {
		this.dienteMacro = dienteMacro;
	}

}
