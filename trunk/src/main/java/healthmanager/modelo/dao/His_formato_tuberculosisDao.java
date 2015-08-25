/*
 * His_formato_tuberculosisDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_formato_tuberculosis;

public interface His_formato_tuberculosisDao {

	void crear(His_formato_tuberculosis his_formato_tuberculosis); 

	int actualizar(His_formato_tuberculosis his_formato_tuberculosis); 

	His_formato_tuberculosis consultar(His_formato_tuberculosis his_formato_tuberculosis); 

	int eliminar(His_formato_tuberculosis his_formato_tuberculosis); 

	List<His_formato_tuberculosis> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}