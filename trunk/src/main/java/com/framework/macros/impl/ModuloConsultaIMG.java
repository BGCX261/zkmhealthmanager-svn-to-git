package com.framework.macros.impl;

import java.util.List;
import java.util.Map;

import healthmanager.controller.ZKWindow.OpcionesFormulario;
import healthmanager.modelo.bean.Odontologia;

public interface ModuloConsultaIMG {
	public void cargarEvolucionOdonotologia(Odontologia odontologia,
			OpcionesFormulario opcion_formulario,
			List<Map<String, Object>> lista_codigos_fac, boolean primera_vez);


			OpcionesFormulario opcion_formulario(List<String> lista_codigos_fac, boolean primera_vez);
	public void cargarEpicrisis();

}
