/*
 * Materiales_suturaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Materiales_sutura;

public interface Materiales_suturaDao {

	void crear(Materiales_sutura materiales_sutura); 

	int actualizar(Materiales_sutura materiales_sutura); 

	Materiales_sutura consultar(Materiales_sutura materiales_sutura); 

	int eliminar(Materiales_sutura materiales_sutura); 

	List<Materiales_sutura> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}