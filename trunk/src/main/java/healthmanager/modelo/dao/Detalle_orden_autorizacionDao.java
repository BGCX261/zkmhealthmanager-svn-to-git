/*
 * Detalle_orden_autorizacionDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_orden_autorizacion;

public interface Detalle_orden_autorizacionDao {

	void crear(Detalle_orden_autorizacion detalle_orden_autorizacion); 

	int actualizar(Detalle_orden_autorizacion detalle_orden_autorizacion); 

	Detalle_orden_autorizacion consultar(Detalle_orden_autorizacion detalle_orden_autorizacion); 

	int eliminar(Detalle_orden_autorizacion detalle_orden_autorizacion); 

	List<Detalle_orden_autorizacion> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}