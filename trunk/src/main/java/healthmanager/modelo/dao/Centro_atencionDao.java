/*
 * Centro_atencionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Centro_atencion;

public interface Centro_atencionDao {

	void crear(Centro_atencion centro_atencion); 

	int actualizar(Centro_atencion centro_atencion); 

	Centro_atencion consultar(Centro_atencion centro_atencion); 

	int eliminar(Centro_atencion centro_atencion); 

	List<Centro_atencion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 
	
	Integer totalResultados(Map<String, Object> parametros);

}