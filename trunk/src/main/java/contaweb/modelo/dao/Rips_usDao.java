/*
 * Rips_usDao.java
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

import contaweb.modelo.bean.Rips_us;

public interface Rips_usDao {

	void crear(Rips_us rips_us); 

	int actualizar(Rips_us rips_us); 

	Rips_us consultar(Rips_us rips_us); 

	int eliminar(Rips_us rips_us); 

	List<Rips_us> listar(Map<String,Object> parameters); 

	boolean existe(Map<String,Object> param); 

}