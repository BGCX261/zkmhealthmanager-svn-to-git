/*
 * Revision_comiteDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Revision_comite;

public interface Revision_comiteDao {

	void crear(Revision_comite revision_comite); 

	int actualizar(Revision_comite revision_comite); 

	Revision_comite consultar(Revision_comite revision_comite); 

	int eliminar(Revision_comite revision_comite); 

	List<Revision_comite> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}