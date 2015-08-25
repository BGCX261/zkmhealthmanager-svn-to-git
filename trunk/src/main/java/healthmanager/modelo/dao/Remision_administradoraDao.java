/*
 * Remision_administradoraDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Remision_administradora;

public interface Remision_administradoraDao {

	void crear(Remision_administradora remision_administradora); 

	int actualizar(Remision_administradora remision_administradora); 

	Remision_administradora consultar(Remision_administradora remision_administradora); 

	int eliminar(Remision_administradora remision_administradora); 

	List<Remision_administradora> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}