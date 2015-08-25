/*
 * Rips_ahDao.java
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

import contaweb.modelo.bean.Rips_ah;

public interface Rips_ahDao {

	void crear(Rips_ah rips_ah); 

	int actualizar(Rips_ah rips_ah); 

	Rips_ah consultar(Rips_ah rips_ah); 

	int eliminar(Rips_ah rips_ah); 

	List<Rips_ah> listar(Map<String,Object> parameters); 

	boolean existe(Map<String,Object> param); 

}