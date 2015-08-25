/*
 * His_contactos_tuberculosisDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_contactos_tuberculosis;

public interface His_contactos_tuberculosisDao {

	void crear(His_contactos_tuberculosis his_contactos_tuberculosis); 

	int actualizar(His_contactos_tuberculosis his_contactos_tuberculosis); 

	His_contactos_tuberculosis consultar(His_contactos_tuberculosis his_contactos_tuberculosis); 

	int eliminar(Map<String,Object> parameters); 

	List<His_contactos_tuberculosis> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}