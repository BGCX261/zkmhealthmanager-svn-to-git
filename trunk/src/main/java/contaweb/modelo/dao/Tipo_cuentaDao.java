/*
 * Tipo_cuentaDao.java
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
import contaweb.modelo.bean.Tipo_cuenta;

public interface Tipo_cuentaDao {

	void crear(Tipo_cuenta tipo_cuenta); 

	int actualizar(Tipo_cuenta tipo_cuenta); 

	Tipo_cuenta consultar(Tipo_cuenta tipo_cuenta); 

	int eliminar(Tipo_cuenta tipo_cuenta); 

	List<Tipo_cuenta> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}