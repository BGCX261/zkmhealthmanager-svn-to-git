/*
 * PsicologiaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Psicologia;

public interface PsicologiaDao {

	void crear(Psicologia psicologia); 

	int actualizar(Psicologia psicologia); 

	Psicologia consultar(Psicologia psicologia); 

	Psicologia consultar_historia(Psicologia psicologia); 

	int eliminar(Psicologia psicologia); 

	List<Psicologia> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	Integer total_registros(Map<String, Object> parameters);

}