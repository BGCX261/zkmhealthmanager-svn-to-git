/*
 * Indice_deanDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Indice_dean;

public interface Indice_deanDao {

	void crear(Indice_dean indice_dean); 

	int actualizar(Indice_dean indice_dean); 

	Indice_dean consultar(Indice_dean indice_dean); 

	int eliminar(Indice_dean indice_dean); 

	List<Indice_dean> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}