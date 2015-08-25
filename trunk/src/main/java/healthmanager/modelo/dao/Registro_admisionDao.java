/*
 * Registro_admisionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Registro_admision;

public interface Registro_admisionDao {

	void crear(Registro_admision registro_admision); 

	int actualizar(Registro_admision registro_admision); 

	Registro_admision consultar(Registro_admision registro_admision); 

	int eliminar(Registro_admision registro_admision); 

	List<Registro_admision> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}