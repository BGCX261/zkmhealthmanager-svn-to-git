/*
 * His_atencion_mesesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_atencion_meses;

public interface His_atencion_mesesDao {

	void crear(His_atencion_meses his_atencion_meses); 

	int actualizar(His_atencion_meses his_atencion_meses); 

	His_atencion_meses consultar(His_atencion_meses his_atencion_meses); 

	int eliminar(His_atencion_meses his_atencion_meses); 

	List<His_atencion_meses> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}