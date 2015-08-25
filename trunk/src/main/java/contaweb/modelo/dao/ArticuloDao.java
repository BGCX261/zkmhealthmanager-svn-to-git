/*
 * ArticuloDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Articulo;

public interface ArticuloDao {

	void crear(Articulo articulo); 

	int actualizar(Articulo articulo); 

	Articulo consultar(Articulo articulo); 

	int eliminar(Articulo articulo); 

	List<Articulo> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	boolean consultarPorReferencia(Articulo articulo);
	
	boolean existe(Map<String, Object> param);
	
	int actualizarPorDmanda(Articulo articulo);
}