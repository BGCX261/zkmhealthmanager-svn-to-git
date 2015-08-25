package com.framework.interfaces;

import java.util.Map;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Negacion;

public interface IEventoNegacionAutorizaciones {
	public void OnAgregarNegacion(ZKWindow window, Negacion negacion, Map<String, Object> params);
	public void OnCerrarNegacion(ZKWindow window,Map<String, Object> params);
	public void OnCancelarNegacion(ZKWindow window,Map<String, Object> params);
}
