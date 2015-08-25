/*
 * Rips_auDao.java
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

import contaweb.modelo.bean.Rips_au;

public interface Rips_auDao {

	void crear(Rips_au rips_au); 

	int actualizar(Rips_au rips_au); 

	Rips_au consultar(Rips_au rips_au); 

	int eliminar(Rips_au rips_au); 

	List<Rips_au> listar(Map<String,Object> parameters); 

	boolean existe(Map<String,Object> param); 

}