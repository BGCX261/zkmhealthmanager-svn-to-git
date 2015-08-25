/*
 * Detalle_revision_comiteDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_revision_comite;

public interface Detalle_revision_comiteDao {

	void crear(Detalle_revision_comite detalle_revision_comite); 

	int actualizar(Detalle_revision_comite detalle_revision_comite); 

	Detalle_revision_comite consultar(Detalle_revision_comite detalle_revision_comite); 

	int eliminar(Detalle_revision_comite detalle_revision_comite); 

	List<Detalle_revision_comite> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}