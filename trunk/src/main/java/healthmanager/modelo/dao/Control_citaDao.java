/*
 * Control_citaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Control_cita;

public interface Control_citaDao {

	void crear(Control_cita control_cita); 

	int actualizar(Control_cita control_cita); 

	Control_cita consultar(Control_cita control_cita); 

	int eliminar(Control_cita control_cita); 

	List<Control_cita> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}