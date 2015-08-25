/*
 * Servicios_procedimientosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Servicios_procedimientos;

import java.util.List;
import java.util.Map;

public interface Servicios_procedimientosDao {

	void crear(Servicios_procedimientos servicios_procedimientos);

	int actualizar(Servicios_procedimientos servicios_procedimientos);

	Servicios_procedimientos consultar(
			Servicios_procedimientos servicios_procedimientos);

	int eliminar(Servicios_procedimientos servicios_procedimientos);

	List<Servicios_procedimientos> listar(Map<String, Object> parameters);
	
	Integer cantidad_procedimientos(Map<String, Object> parametros);

	void setLimit(String limit);

	boolean existe(Map<String, Object> param);

}