/*
 * Epicrisis_eseDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Epicrisis_ese;

public interface Epicrisis_eseDao {

	void crear(Epicrisis_ese epicrisis_ese); 

	int actualizar(Epicrisis_ese epicrisis_ese); 

	Epicrisis_ese consultar(Epicrisis_ese epicrisis_ese); 

	int eliminar(Epicrisis_ese epicrisis_ese); 

	List<Epicrisis_ese> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}