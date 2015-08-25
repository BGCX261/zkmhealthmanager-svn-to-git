/*
 * Detalle_inventarioDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Detalle_inventario;

public interface Detalle_inventarioDao {

	void crear(Detalle_inventario detalle_inventario); 

	int actualizar(Detalle_inventario detalle_inventario); 

	Detalle_inventario consultar(Detalle_inventario detalle_inventario); 

	int eliminar(Detalle_inventario detalle_inventario); 

	List<Detalle_inventario> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}