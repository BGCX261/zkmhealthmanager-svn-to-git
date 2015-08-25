/*
 * UsuariosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Usuarios;

public interface UsuariosDao {

	void crear(Usuarios usuarios); 

	int actualizar(Usuarios usuarios); 

	Usuarios consultar(Usuarios usuarios); 

	int eliminar(Usuarios usuarios); 

	List<Usuarios> listar(Map<String, Object> parameters); 
	
	Integer totalResultados(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	List<Map<String,Object>> getUsuarioByRol(Map<String,Object> param);
}