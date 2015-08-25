/*
 * Control_signos_vitalesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Control_signos_vitales;

public interface Control_signos_vitalesDao {

	void crear(Control_signos_vitales control_signos_vitales); 

	int actualizar(Control_signos_vitales control_signos_vitales); 

	Control_signos_vitales consultar(Control_signos_vitales control_signos_vitales); 

	int eliminar(Map<String,Object> parameters); 

	List<Control_signos_vitales> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}