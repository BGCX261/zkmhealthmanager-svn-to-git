/*
 * Control_ficha_inicio_lepraDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Control_ficha_inicio_lepra;

public interface Control_ficha_inicio_lepraDao {

	void crear(Control_ficha_inicio_lepra control_ficha_inicio_lepra); 

	int actualizar(Control_ficha_inicio_lepra control_ficha_inicio_lepra); 

	Control_ficha_inicio_lepra consultar(Control_ficha_inicio_lepra control_ficha_inicio_lepra); 

	int eliminar(Control_ficha_inicio_lepra control_ficha_inicio_lepra); 

	List<Control_ficha_inicio_lepra> listar(Map<String,Object> parameters); 
	
	List<Map<String,Object>> listarAnio(Map<String,Object> parameters); 
	
	Map<String,Object> consultarDiaMes(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}