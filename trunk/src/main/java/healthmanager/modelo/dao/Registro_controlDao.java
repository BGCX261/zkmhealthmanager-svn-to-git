/*
 * Registro_controlDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Registro_control;

public interface Registro_controlDao {

	void crear(Registro_control registro_control); 

	int actualizar(Registro_control registro_control); 

	Registro_control consultar(Registro_control registro_control); 

	int eliminar(Registro_control registro_control); 

	List<Registro_control> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}