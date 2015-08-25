/*
 * ComprobanteDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Comprobante;

public interface ComprobanteDao {

	void crear(Comprobante comprobante); 

	int actualizar(Comprobante comprobante); 

	Comprobante consultar(Comprobante comprobante); 

	int eliminar(Comprobante comprobante); 

	List<Comprobante> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}