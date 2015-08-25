/*
 * Control_prenatalDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Control_prenatal;

public interface Control_prenatalDao {

	void crear(Control_prenatal control_prenatal); 

	int actualizar(Control_prenatal control_prenatal); 

	Control_prenatal consultar(Control_prenatal control_prenatal); 

	int eliminar(Control_prenatal control_prenatal); 

	List<Control_prenatal> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}