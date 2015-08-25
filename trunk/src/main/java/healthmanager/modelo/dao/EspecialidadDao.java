/*
 * EspecialidadDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Especialidad;

public interface EspecialidadDao {

	void crear(Especialidad especialidad); 

	int actualizar(Especialidad especialidad); 

	Especialidad consultar(Especialidad especialidad); 

	int eliminar(Especialidad especialidad); 

	List<Especialidad> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}