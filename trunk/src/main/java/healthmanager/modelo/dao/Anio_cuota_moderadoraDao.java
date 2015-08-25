/*
 * Anio_cuota_moderadoraDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Anio_cuota_moderadora;

public interface Anio_cuota_moderadoraDao {

	void crear(Anio_cuota_moderadora anio_cuota_moderadora); 

	int actualizar(Anio_cuota_moderadora anio_cuota_moderadora); 

	Anio_cuota_moderadora consultar(Anio_cuota_moderadora anio_cuota_moderadora); 

	int eliminar(Anio_cuota_moderadora anio_cuota_moderadora); 

	List<Anio_cuota_moderadora> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}