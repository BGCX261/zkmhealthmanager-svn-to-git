/*
 * Rips_anDao.java
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

import contaweb.modelo.bean.Rips_an;

public interface Rips_anDao {

	void crear(Rips_an rips_an); 

	int actualizar(Rips_an rips_an); 

	Rips_an consultar(Rips_an rips_an); 

	int eliminar(Rips_an rips_an); 

	List<Rips_an> listar(Map<String,Object> parameters); 

	boolean existe(Map<String,Object> param); 

}