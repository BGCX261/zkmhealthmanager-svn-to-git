package com.framework.res;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.service.CitasService;

import java.util.HashMap;
import java.util.Map;

import com.framework.locator.ServiceLocatorWeb;

public class VerificacionOnlyPyp {
	/**
	 * Verificamos si la admicion es de PYP
	 * 
	 * @author Luis Miguel
	 * */
	public static boolean onlyPyp(Admision admision,
			ServiceLocatorWeb serviceLocator) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", admision.getCodigo_empresa());
		map.put("codigo_sucursal", admision.getCodigo_sucursal());
		map.put("codigo_cita", admision.getCodigo_cita());
		map.put("tipo_consulta", "4");
		return serviceLocator.getCitasService().existe(map);
	}

	public static boolean onlyPyp(Admision admision, CitasService citasService) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", admision.getCodigo_empresa());
		map.put("codigo_sucursal", admision.getCodigo_sucursal());
		map.put("codigo_cita", admision.getCodigo_cita());
		map.put("tipo_consulta", "4");
		return citasService.existe(map);
	}

}
