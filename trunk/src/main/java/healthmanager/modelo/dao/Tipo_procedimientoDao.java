/*
 * Tipo_procedimientoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Tipo_procedimiento;

public interface Tipo_procedimientoDao {

	void crear(Tipo_procedimiento tipo_procedimiento); 

	int actualizar(Tipo_procedimiento tipo_procedimiento); 

	Tipo_procedimiento consultar(Tipo_procedimiento tipo_procedimiento); 

	int eliminar(Tipo_procedimiento tipo_procedimiento); 

	List<Tipo_procedimiento> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Tipo_procedimiento tipo_procedimiento); 

}