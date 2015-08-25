/*
 * Parametros_signosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Parametros_signos;

public interface Parametros_signosDao {

	void crear(Parametros_signos parametros_signos); 

	int actualizar(Parametros_signos parametros_signos); 

	Parametros_signos consultar(Parametros_signos parametros_signos); 

	int eliminar(Parametros_signos parametros_signos); 

	List<Parametros_signos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}