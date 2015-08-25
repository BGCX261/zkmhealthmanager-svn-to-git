/*
 * Detalle_cajaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Detalle_caja;

public interface Detalle_cajaDao {

	void crear(Detalle_caja detalle_caja); 

	int actualizar(Detalle_caja detalle_caja); 

	Detalle_caja consultar(Detalle_caja detalle_caja); 

	int eliminar(Detalle_caja detalle_caja); 

	List<Detalle_caja> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}