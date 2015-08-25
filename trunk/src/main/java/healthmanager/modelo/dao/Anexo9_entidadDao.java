/*
 * Anexo9_entidadDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Anexo9_entidad;

public interface Anexo9_entidadDao {

	void crear(Anexo9_entidad anexo9_entidad); 

	int actualizar(Anexo9_entidad anexo9_entidad); 

	Anexo9_entidad consultar(Anexo9_entidad anexo9_entidad); 

	int eliminar(Anexo9_entidad anexo9_entidad); 

	List<Anexo9_entidad> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}