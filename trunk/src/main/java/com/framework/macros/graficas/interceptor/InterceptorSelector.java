package com.framework.macros.graficas.interceptor;

import healthmanager.controller.ZKWindow;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;

public abstract class InterceptorSelector {
	public static Logger log = Logger.getLogger(InterceptorSelector.class);
	
	protected double validateAproximacion0Apunto5(double valor) {
		DecimalFormat decimalFormat = new DecimalFormat("##.#");
		String valor_string = decimalFormat.format(valor);
		decimalFormat = new DecimalFormat("##");
		if (valor_string.matches("[0-9].+[0-3 7-9]")) {
			return Double.parseDouble(decimalFormat.format(valor));
		} else {
			return Double.parseDouble(decimalFormat.format(valor) + ".5");
		}
	}
	
	public void mostrarVentanaParaUsuario(Component component, ZKWindow zkWindow, String title){
		Window window = new Window(); 
		window.setTitle("" + title);
		window.setClosable(true);
		window.appendChild(component); 
		window.setPosition("left,top");
		window.setPage(zkWindow.getPage());
		window.doModal();
	}
	
	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

}
