package com.framework.validator;

import java.util.HashMap;
import java.util.Map;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.service.AdmisionService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.http.HttpSessionListener;

import com.framework.constantes.IVias_ingreso;
import com.framework.locator.ServiceLocatorWeb;

public class LimpiarSessionListener extends HttpSessionListener {

	private static Logger log = Logger.getLogger(LimpiarSessionListener.class);

	@Override
	public void sessionDestroyed(HttpSessionEvent evt) {
		log.info("ejecutando metodo destruir session ===> sessionDestroyed(evt)");
		HttpSession session = evt.getSession();
		if (session.getAttribute(IVias_ingreso.ADMISION_PACIENTE) != null) {
			Admision admision_seleccionada = (Admision) evt.getSession()
					.getAttribute(IVias_ingreso.ADMISION_PACIENTE);
			ServiceLocatorWeb serviceLocator = (ServiceLocatorWeb) evt
					.getSession().getAttribute(IVias_ingreso.SERVICE_LOCATOR);
			admision_seleccionada.setAtendiendo(false);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("admision", admision_seleccionada);
			// map.put("parametros_empresa", parametros_empresa);
			serviceLocator.getServicio(AdmisionService.class).actualizar(map);

		}
		super.sessionDestroyed(evt);
	}
}
