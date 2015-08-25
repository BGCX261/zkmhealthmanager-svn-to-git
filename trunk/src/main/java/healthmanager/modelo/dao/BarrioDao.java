/*
 * BarrioDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Barrio;

public interface BarrioDao {

	void crear(Barrio barrio); 

	int actualizar(Barrio barrio); 

	Barrio consultar(Barrio barrio); 

	int eliminar(Barrio barrio); 

	List<Barrio> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 
	
	Integer totalResultados(Map<String, Object> parameters); 

}