/*
 * Control_convivientes_seguimientoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Control_convivientes_seguimiento;

public interface Control_convivientes_seguimientoDao {

	void crear(Control_convivientes_seguimiento control_convivientes_seguimiento); 

	int actualizar(Control_convivientes_seguimiento control_convivientes_seguimiento); 

	Control_convivientes_seguimiento consultar(Control_convivientes_seguimiento control_convivientes_seguimiento); 

	int eliminar(Control_convivientes_seguimiento control_convivientes_seguimiento); 

	List<Control_convivientes_seguimiento> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}