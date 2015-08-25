/*
 * Escala_del_desarrolloDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Escala_del_desarrollo;

public interface Escala_del_desarrolloDao {

	void crear(Escala_del_desarrollo escala_del_desarrollo); 

	int actualizar(Escala_del_desarrollo escala_del_desarrollo); 

	Escala_del_desarrollo consultar(Escala_del_desarrollo escala_del_desarrollo); 

	int eliminar(Escala_del_desarrollo escala_del_desarrollo); 

	List<Escala_del_desarrollo> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}