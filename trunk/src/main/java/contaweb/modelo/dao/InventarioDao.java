/*
 * InventarioDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Inventario;

public interface InventarioDao {

	void crear(Inventario inventario); 

	int actualizar(Inventario inventario); 

	Inventario consultar(Inventario inventario); 

	int eliminar(Inventario inventario); 

	List<Inventario> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}