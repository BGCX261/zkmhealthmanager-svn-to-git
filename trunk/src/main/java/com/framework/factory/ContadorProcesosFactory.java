package com.framework.factory;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import healthmanager.controller.proceso.ContadorProcesoAction;
import healthmanager.controller.proceso.ContadorProcesoAction.IOnEjecutar;

public class ContadorProcesosFactory {
    public static ContadorProcesoAction getContadorProcesoAction(Component component, IOnEjecutar onEjecutar){
    	ContadorProcesoAction contadorProcesoAction = (ContadorProcesoAction) Executions.createComponents("/pages/proceso/contador_proceso.zul", component, null);
    	contadorProcesoAction.setOnEjecutar(onEjecutar); 
    	contadorProcesoAction.setVisible(false);
    	return contadorProcesoAction;
    }
}
