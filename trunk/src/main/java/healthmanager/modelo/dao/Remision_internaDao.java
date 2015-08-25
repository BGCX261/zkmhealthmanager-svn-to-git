/*
 * Remision_internaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Remision_interna;

public interface Remision_internaDao {

	void crear(Remision_interna remision_interna); 

	int actualizar(Remision_interna remision_interna); 

	Remision_interna consultar(Remision_interna remision_interna); 

	int eliminar(Remision_interna remision_interna); 

	List<Remision_interna> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}