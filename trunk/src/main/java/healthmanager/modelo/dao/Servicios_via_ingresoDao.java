/*
 * Servicios_via_ingresoDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Servicios_via_ingreso;

public interface Servicios_via_ingresoDao {

	void crear(Servicios_via_ingreso servicios_via_ingreso); 

	int actualizar(Servicios_via_ingreso servicios_via_ingreso); 

	Servicios_via_ingreso consultar(Servicios_via_ingreso servicios_via_ingreso); 

	int eliminar(Servicios_via_ingreso servicios_via_ingreso); 

	List<Servicios_via_ingreso> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}