/*
 * Edad_vencimientoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Edad_vencimiento;

public interface Edad_vencimientoDao {

	void crear(Edad_vencimiento edad_vencimiento); 

	int actualizar(Edad_vencimiento edad_vencimiento); 

	Edad_vencimiento consultar(Edad_vencimiento edad_vencimiento); 

	int eliminar(Edad_vencimiento edad_vencimiento); 

	List<Edad_vencimiento> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}