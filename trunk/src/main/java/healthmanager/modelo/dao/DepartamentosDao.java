/*
 * DepartamentosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Departamentos;

public interface DepartamentosDao {

	void crear(Departamentos departamentos); 

	int actualizar(Departamentos departamentos); 

	Departamentos consultar(Departamentos departamentos); 

	int eliminar(Departamentos departamentos); 

	List<Departamentos> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}