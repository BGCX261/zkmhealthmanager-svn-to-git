/*
 * Grupo_cuentaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Grupo_cuenta;

public interface Grupo_cuentaDao {

	void crear(Grupo_cuenta grupo_cuenta); 

	int actualizar(Grupo_cuenta grupo_cuenta); 

	Grupo_cuenta consultar(Grupo_cuenta grupo_cuenta); 

	int eliminar(Grupo_cuenta grupo_cuenta); 

	List<Grupo_cuenta> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}