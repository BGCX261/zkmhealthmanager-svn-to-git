/*
 * Lote_articuloDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Lote_articulo;

public interface Lote_articuloDao {

	void crear(Lote_articulo lote_articulo); 

	int actualizar(Lote_articulo lote_articulo); 

	Lote_articulo consultar(Lote_articulo lote_articulo); 

	int eliminar(Lote_articulo lote_articulo); 

	List<Lote_articulo> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}