/*
 * CarteraDao.java
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
import contaweb.modelo.bean.Cartera;

public interface CarteraDao {

	void crear(Cartera cartera); 

	int actualizar(Cartera cartera); 

	Cartera consultar(Cartera cartera); 

	int eliminar(Cartera cartera); 

	List<Cartera> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}