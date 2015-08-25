/*
 * BodegaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Bodega;

public interface BodegaDao {

	void crear(Bodega bodega); 

	int actualizar(Bodega bodega); 

	Bodega consultar(Bodega bodega); 

	int eliminar(Bodega bodega); 

	List<Bodega> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}