/*
 * Roles_usuariosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Roles_usuarios;

public interface Roles_usuariosDao {

	void crear(Roles_usuarios roles_usuarios); 

	int actualizar(Roles_usuarios roles_usuarios); 

	Roles_usuarios consultar(Roles_usuarios roles_usuarios); 

	int eliminar(Roles_usuarios roles_usuarios); 

	List<Roles_usuarios> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}