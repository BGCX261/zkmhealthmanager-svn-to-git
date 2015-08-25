/*
 * Horarios_nuevoDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Horarios_nuevo;

public interface Horarios_nuevoDao {

	void crear(Horarios_nuevo horarios_nuevo); 

	int actualizar(Horarios_nuevo horarios_nuevo); 

	Horarios_nuevo consultar(Horarios_nuevo horarios_nuevo); 

	int eliminar(Horarios_nuevo horarios_nuevo); 

	List<Horarios_nuevo> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}