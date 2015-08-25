/*
 * His_tarjeta_tuberculosisDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_tarjeta_tuberculosis;

public interface His_tarjeta_tuberculosisDao {

	void crear(His_tarjeta_tuberculosis his_tarjeta_tuberculosis); 

	int actualizar(His_tarjeta_tuberculosis his_tarjeta_tuberculosis); 

	His_tarjeta_tuberculosis consultar(His_tarjeta_tuberculosis his_tarjeta_tuberculosis); 

	His_tarjeta_tuberculosis consultar_historia(His_tarjeta_tuberculosis his_tarjeta_tuberculosis); 

	int eliminar(His_tarjeta_tuberculosis his_tarjeta_tuberculosis); 

	List<His_tarjeta_tuberculosis> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}