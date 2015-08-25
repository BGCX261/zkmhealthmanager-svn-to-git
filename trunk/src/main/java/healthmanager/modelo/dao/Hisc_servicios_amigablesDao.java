/*
 * Hisc_servicios_amigablesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Hisc_servicios_amigables;

public interface Hisc_servicios_amigablesDao {

	void crear(Hisc_servicios_amigables hisc_servicios_amigables);

	int actualizar(Hisc_servicios_amigables hisc_servicios_amigables);

	Hisc_servicios_amigables consultar(Hisc_servicios_amigables hisc_servicios_amigables);

	Hisc_servicios_amigables consultar_historia(Hisc_servicios_amigables hisc_servicios_amigables);

	int eliminar(Hisc_servicios_amigables hisc_servicios_amigables);

	List<Hisc_servicios_amigables> listar(Map<String, Object> parameters);
	
	void setLimit(String limit);

	boolean existe(Map<String,Object> param);
	
	Integer total_registros(Map<String, Object> parameters);

}