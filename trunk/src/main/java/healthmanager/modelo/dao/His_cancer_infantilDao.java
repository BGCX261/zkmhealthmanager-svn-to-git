/*
 * His_cancer_infantilDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_cancer_infantil;

public interface His_cancer_infantilDao {

	void crear(His_cancer_infantil his_cancer_infantil); 

	int actualizar(His_cancer_infantil his_cancer_infantil); 

	His_cancer_infantil consultar(His_cancer_infantil his_cancer_infantil); 

	int eliminar(His_cancer_infantil his_cancer_infantil); 

	List<His_cancer_infantil> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}