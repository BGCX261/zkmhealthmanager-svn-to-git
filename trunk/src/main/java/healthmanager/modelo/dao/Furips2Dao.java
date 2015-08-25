/*
 * Furips2Dao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Furips2;

public interface Furips2Dao {

	void crear(Furips2 furips2); 

	int actualizar(Furips2 furips2); 

	Furips2 consultar(Furips2 furips2); 

	int eliminar(Furips2 furips2); 

	List<Furips2> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Furips2 consultarPorParametros(Furips2 furips2); 

}