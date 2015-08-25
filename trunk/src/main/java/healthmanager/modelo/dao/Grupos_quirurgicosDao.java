/*
 * Grupos_quirurgicosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Grupos_quirurgicos;

public interface Grupos_quirurgicosDao {

	void crear(Grupos_quirurgicos grupos_quirurgicos); 

	int actualizar(Grupos_quirurgicos grupos_quirurgicos); 

	Grupos_quirurgicos consultar(Grupos_quirurgicos grupos_quirurgicos); 

	int eliminar(Grupos_quirurgicos grupos_quirurgicos); 

	List<Grupos_quirurgicos> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}