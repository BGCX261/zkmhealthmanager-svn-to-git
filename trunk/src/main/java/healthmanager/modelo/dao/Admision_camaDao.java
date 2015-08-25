/*
 * Admision_camaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Admision_cama;

public interface Admision_camaDao {

	void crear(Admision_cama admision_cama); 

	int actualizar(Admision_cama admision_cama); 

	Admision_cama consultar(Admision_cama admision_cama); 

	int eliminar(Admision_cama admision_cama); 

	List<Admision_cama> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Object param); 

}