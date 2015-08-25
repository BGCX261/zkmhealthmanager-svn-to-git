/*
 * Bodega_articuloDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Bodega_articulo;

public interface Bodega_articuloDao {

	void crear(Bodega_articulo bodega_articulo); 

	int actualizar(Bodega_articulo bodega_articulo); 

	Bodega_articulo consultar(Bodega_articulo bodega_articulo); 

	int eliminar(Bodega_articulo bodega_articulo); 

	List<Bodega_articulo> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	boolean existe(Map<String, Object> parameters);

}