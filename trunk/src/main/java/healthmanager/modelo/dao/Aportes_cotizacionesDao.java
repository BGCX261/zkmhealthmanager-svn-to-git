/*
 * Aportes_cotizacionesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Aportes_cotizaciones;

public interface Aportes_cotizacionesDao {

	void crear(Aportes_cotizaciones aportes_cotizaciones); 

	int actualizar(Aportes_cotizaciones aportes_cotizaciones); 

	Aportes_cotizaciones consultar(Aportes_cotizaciones aportes_cotizaciones); 

	int eliminar(Aportes_cotizaciones aportes_cotizaciones); 

	List<Aportes_cotizaciones> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	Double getIbcOrCotizacion(Map<String, Object> parameters); 
	
	
	int eliminarParametrizado(Map<String, Object> parameters);

}