/*
 * His_recien_nacidoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_recien_nacido;

public interface His_recien_nacidoDao {

	void crear(His_recien_nacido his_recien_nacido); 

	int actualizar(His_recien_nacido his_recien_nacido); 

	His_recien_nacido consultar(His_recien_nacido his_recien_nacido); 

	int eliminar(His_recien_nacido his_recien_nacido); 

	List<His_recien_nacido> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}