/*
 * Detalle_grupos_procedimientosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;

public interface Detalle_grupos_procedimientosDao {

	void crear(Detalle_grupos_procedimientos detalle_grupos_procedimientos); 

	int actualizar(Detalle_grupos_procedimientos detalle_grupos_procedimientos); 

	Detalle_grupos_procedimientos consultar(Detalle_grupos_procedimientos detalle_grupos_procedimientos); 

	int eliminar(Detalle_grupos_procedimientos detalle_grupos_procedimientos); 

	List<Detalle_grupos_procedimientos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}