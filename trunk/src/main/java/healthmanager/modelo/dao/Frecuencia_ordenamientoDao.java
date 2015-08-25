/*
 * Frecuencia_ordenamientoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Frecuencia_ordenamiento;

public interface Frecuencia_ordenamientoDao {

	void crear(Frecuencia_ordenamiento frecuencia_ordenamiento); 

	int actualizar(Frecuencia_ordenamiento frecuencia_ordenamiento); 

	Frecuencia_ordenamiento consultar(Frecuencia_ordenamiento frecuencia_ordenamiento); 
	
	Frecuencia_ordenamiento consultarActual(Frecuencia_ordenamiento frecuencia_ordenamiento);

	int eliminar(Frecuencia_ordenamiento frecuencia_ordenamiento); 

	List<Frecuencia_ordenamiento> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}