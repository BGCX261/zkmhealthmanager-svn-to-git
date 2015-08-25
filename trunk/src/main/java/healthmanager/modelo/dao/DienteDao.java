/*
 * DienteDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Diente;

public interface DienteDao {

	void crear(Diente diente); 

	int actualizar(Diente diente); 

	Diente consultar(Diente diente); 

	int eliminar(Diente diente); 

	List<Diente> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}