/*
 * Ordenes_medicasDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Ordenes_medicas;

public interface Ordenes_medicasDao {

	void crear(Ordenes_medicas ordenes_medicas); 

	int actualizar(Ordenes_medicas ordenes_medicas); 

	Ordenes_medicas consultar(Ordenes_medicas ordenes_medicas); 

	int eliminar(Ordenes_medicas ordenes_medicas); 

	List<Ordenes_medicas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}