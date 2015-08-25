/*
 * Roles_usuarios_capsDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Roles_usuarios_caps;

public interface Roles_usuarios_capsDao {

	void crear(Roles_usuarios_caps roles_usuarios_caps); 

	int actualizar(Roles_usuarios_caps roles_usuarios_caps); 

	Roles_usuarios_caps consultar(Roles_usuarios_caps roles_usuarios_caps); 

	int eliminar(Roles_usuarios_caps roles_usuarios_caps); 

	List<Roles_usuarios_caps> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}