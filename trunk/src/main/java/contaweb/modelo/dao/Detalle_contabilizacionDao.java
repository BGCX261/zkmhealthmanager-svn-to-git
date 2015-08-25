/*
 * Detalle_contabilizacionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Detalle_contabilizacion;

public interface Detalle_contabilizacionDao {

	void crear(Detalle_contabilizacion detalle_contabilizacion); 

	int actualizar(Detalle_contabilizacion detalle_contabilizacion); 

	Detalle_contabilizacion consultar(Detalle_contabilizacion detalle_contabilizacion); 

	int eliminar(Detalle_contabilizacion detalle_contabilizacion); 

	List<Detalle_contabilizacion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}