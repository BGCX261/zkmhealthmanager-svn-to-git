/*
 * Seguimiento_control_pqtDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Seguimiento_control_pqt;

public interface Seguimiento_control_pqtDao {

	void crear(Seguimiento_control_pqt seguimiento_control_pqt); 

	int actualizar(Seguimiento_control_pqt seguimiento_control_pqt); 

	Seguimiento_control_pqt consultar(Seguimiento_control_pqt seguimiento_control_pqt); 

	int eliminar(Seguimiento_control_pqt seguimiento_control_pqt); 

	List<Seguimiento_control_pqt> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	boolean existe_fecha_fin_tratamiento(Map<String,Object> param); 
	

}