/*
 * Configuracion_autorizacion_via_ingresoDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Configuracion_autorizacion_via_ingreso;

public interface Configuracion_autorizacion_via_ingresoDao {

	void crear(Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso); 

	int actualizar(Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso); 

	Configuracion_autorizacion_via_ingreso consultar(Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso); 

	int eliminar(Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso); 

	List<Configuracion_autorizacion_via_ingreso> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}