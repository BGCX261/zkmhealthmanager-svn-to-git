/*
 * Cuota_moderadoraDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Cuota_moderadora;

public interface Cuota_moderadoraDao {

	void crear(Cuota_moderadora cuota_moderadora); 

	int actualizar(Cuota_moderadora cuota_moderadora); 

	Cuota_moderadora consultar(Cuota_moderadora cuota_moderadora); 

	int eliminar(Cuota_moderadora cuota_moderadora); 

	List<Cuota_moderadora> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}