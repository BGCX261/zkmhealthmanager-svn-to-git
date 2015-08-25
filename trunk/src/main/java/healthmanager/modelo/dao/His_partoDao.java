/*
 * His_partoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_parto;

public interface His_partoDao {

	void crear(His_parto his_parto); 

	int actualizar(His_parto his_parto); 

	His_parto consultar(His_parto his_parto); 

	int eliminar(His_parto his_parto); 

	List<His_parto> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}