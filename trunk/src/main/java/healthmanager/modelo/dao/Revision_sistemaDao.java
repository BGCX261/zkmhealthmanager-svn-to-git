/*
 * Revision_sistemaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Revision_sistema;

public interface Revision_sistemaDao {

	void crear(Revision_sistema revision_sistema); 

	int actualizar(Revision_sistema revision_sistema); 

	Revision_sistema consultar(Revision_sistema revision_sistema); 

	int eliminar(Revision_sistema revision_sistema); 

	List<Revision_sistema> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}