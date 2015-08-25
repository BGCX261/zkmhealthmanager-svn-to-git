/*
 * His_evaluacion_tuberculosisDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_evaluacion_tuberculosis;

public interface His_evaluacion_tuberculosisDao {

	void crear(His_evaluacion_tuberculosis his_evaluacion_tuberculosis); 

	int actualizar(His_evaluacion_tuberculosis his_evaluacion_tuberculosis); 

	His_evaluacion_tuberculosis consultar(His_evaluacion_tuberculosis his_evaluacion_tuberculosis); 

	int eliminar(Map<String,Object> parameters); 

	List<His_evaluacion_tuberculosis> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}