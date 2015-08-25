/*
 * Config_carnetsDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Config_carnets;

public interface Config_carnetsDao {

	void crear(Config_carnets config_carnets); 

	int actualizar(Config_carnets config_carnets); 

	Config_carnets consultar(Config_carnets config_carnets); 

	int eliminar(Config_carnets config_carnets); 

	List<Config_carnets> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}