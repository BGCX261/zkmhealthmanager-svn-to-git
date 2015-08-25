package com.framework.macros.manuales_tarifarios.interfaces;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Sucursal;


public interface IManualesAction {
   String getCodigoAdministradora();
   Empresa getEmpresa();
   Sucursal getSucursal();
   String getIdContrato();
   String getTipoFactura();
   String getNivel();
   ZKWindow getZkWindow();
}
