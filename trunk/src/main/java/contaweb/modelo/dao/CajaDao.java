/*
 * CajaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Caja;

public interface CajaDao {

	void crear(Caja caja); 

	int actualizar(Caja caja); 

	Caja consultar(Caja caja); 

	int eliminar(Caja caja); 

	List<Caja> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	Double totalCopagos(Caja caja);

	int actualizarFecha(Caja caja);  

}