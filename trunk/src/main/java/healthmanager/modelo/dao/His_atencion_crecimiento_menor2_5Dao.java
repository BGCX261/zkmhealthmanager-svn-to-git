/*
 * His_atencion_crecimiento_menor2_5Dao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_atencion_crecimiento_menor2_5;

public interface His_atencion_crecimiento_menor2_5Dao {

	void crear(His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5); 

	int actualizar(His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5); 

	His_atencion_crecimiento_menor2_5 consultar(His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5); 

	int eliminar(His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5); 

	List<His_atencion_crecimiento_menor2_5> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	
	Integer total_registros(Map<String, Object> parameters);

}