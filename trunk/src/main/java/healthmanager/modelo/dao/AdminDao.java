/*
 * AdminDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Admin;

import java.util.List;
import java.util.Map;

public interface AdminDao {

	void crear(Admin admin); 

	int actualizar(Admin admin); 

	Admin consultar(Admin admin); 

	int eliminar(Admin admin); 

	List<Admin> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}