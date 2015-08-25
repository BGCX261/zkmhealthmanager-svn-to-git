/*
 * Grupos_procedimientosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Grupos_procedimientos;

public interface Grupos_procedimientosDao {

	void crear(Grupos_procedimientos grupos_procedimientos); 

	int actualizar(Grupos_procedimientos grupos_procedimientos); 

	Grupos_procedimientos consultar(Grupos_procedimientos grupos_procedimientos); 

	int eliminar(Grupos_procedimientos grupos_procedimientos); 

	List<Grupos_procedimientos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}