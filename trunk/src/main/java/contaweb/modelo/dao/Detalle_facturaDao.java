/*
 * Detalle_facturaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Detalle_factura;

public interface Detalle_facturaDao {

	void crear(Detalle_factura detalle_factura); 

	int actualizar(Detalle_factura detalle_factura); 

	Detalle_factura consultar(Detalle_factura detalle_factura); 
	
	Detalle_factura consultar_facturable(Detalle_factura detalle_factura); 

	int eliminar(Detalle_factura detalle_factura); 

	int eliminar_factura(Detalle_factura detalle_factura); 
	
	List<Detalle_factura> listar(Map<String, Object> parameters);
	
	Double totalFacturaClinica(Map<String, Object> parameters);

	void setLimit(String limit); 
	
   List<Map<String, Object>>  getInformacionFactura(Map<String, Object> parametros);
}