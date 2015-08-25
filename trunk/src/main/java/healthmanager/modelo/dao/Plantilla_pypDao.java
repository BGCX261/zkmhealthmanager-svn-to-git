/*
 * Plantilla_pypDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Plantilla_pyp;

public interface Plantilla_pypDao {

	void crear(Plantilla_pyp plantilla_pyp); 

	int actualizar(Plantilla_pyp plantilla_pyp); 

	Plantilla_pyp consultar(Plantilla_pyp plantilla_pyp); 

	int eliminar(Plantilla_pyp plantilla_pyp); 

	List<Plantilla_pyp> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	List<Map<String, Object>> listarMetasPyp(Map<String, Object> map);
	
	
	boolean existe(Map<String, Object> param);

}