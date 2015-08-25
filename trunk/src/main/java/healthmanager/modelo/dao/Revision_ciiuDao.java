/*
 * Revision_ciiuDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Revision_ciiu;

public interface Revision_ciiuDao {

	void crear(Revision_ciiu revision_ciiu); 

	int actualizar(Revision_ciiu revision_ciiu); 

	Revision_ciiu consultar(Revision_ciiu revision_ciiu); 

	int eliminar(Revision_ciiu revision_ciiu); 

	List<Revision_ciiu> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}