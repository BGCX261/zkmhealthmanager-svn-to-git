/*
 * Grupos_iss01Dao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Grupos_iss01;

public interface Grupos_iss01Dao {

	void crear(Grupos_iss01 grupos_iss01); 

	int actualizar(Grupos_iss01 grupos_iss01); 

	Grupos_iss01 consultar(Grupos_iss01 grupos_iss01); 

	int eliminar(Grupos_iss01 grupos_iss01); 

	List<Grupos_iss01> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}