/*
 * Orden_autorizacionDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Orden_autorizacion;

public interface Orden_autorizacionDao {

	void crear(Orden_autorizacion orden_autorizacion); 

	int actualizar(Orden_autorizacion orden_autorizacion); 

	Orden_autorizacion consultar(Orden_autorizacion orden_autorizacion); 

	int eliminar(Orden_autorizacion orden_autorizacion); 

	List<Orden_autorizacion> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}