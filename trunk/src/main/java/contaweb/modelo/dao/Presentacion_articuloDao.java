/*
 * Presentacion_articuloDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Presentacion_articulo;

public interface Presentacion_articuloDao {

	void crear(Presentacion_articulo presentacion_articulo); 

	int actualizar(Presentacion_articulo presentacion_articulo); 

	Presentacion_articulo consultar(Presentacion_articulo presentacion_articulo); 

	int eliminar(Presentacion_articulo presentacion_articulo); 

	List<Presentacion_articulo> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}