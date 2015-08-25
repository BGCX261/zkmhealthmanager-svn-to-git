/*
 * His_fases_tuberculosisDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_fases_tuberculosis;

public interface His_fases_tuberculosisDao {

	void crear(His_fases_tuberculosis his_fases_tuberculosis); 

	int actualizar(His_fases_tuberculosis his_fases_tuberculosis); 

	His_fases_tuberculosis consultar(His_fases_tuberculosis his_fases_tuberculosis); 

	int eliminar(His_fases_tuberculosis his_fases_tuberculosis); 

	List<His_fases_tuberculosis> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}