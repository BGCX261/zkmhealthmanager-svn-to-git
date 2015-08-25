/*
 * PaisDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos softcomputo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Pais;

public interface PaisDao {

	long crear(Pais pais); 

	int actualizar(Pais pais); 

	Pais consultar(Pais pais);
	
	Pais consultarID(Pais pais);

	int eliminar(Pais pais); 

	List<Pais> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean exist(Map<String,Object> param); 

}