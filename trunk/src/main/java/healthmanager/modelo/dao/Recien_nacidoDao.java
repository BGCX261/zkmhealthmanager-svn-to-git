/*
 * Recien_nacidoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Recien_nacido;

public interface Recien_nacidoDao {

	void crear(Recien_nacido recien_nacido); 

	int actualizar(Recien_nacido recien_nacido); 

	Recien_nacido consultar(Recien_nacido recien_nacido); 

	int eliminar(Recien_nacido recien_nacido); 

	List<Recien_nacido> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}