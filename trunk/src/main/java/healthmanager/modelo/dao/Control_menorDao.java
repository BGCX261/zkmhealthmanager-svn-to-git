/*
 * Control_menorDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Control_menor;

public interface Control_menorDao {

	void crear(Control_menor control_menor); 

	int actualizar(Control_menor control_menor); 

	Control_menor consultar(Control_menor control_menor); 

	int eliminar(Control_menor control_menor); 

	List<Control_menor> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}