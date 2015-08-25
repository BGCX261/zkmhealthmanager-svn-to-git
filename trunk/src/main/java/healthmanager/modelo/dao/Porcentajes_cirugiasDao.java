/*
 * Porcentajes_cirugiasDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Porcentajes_cirugias;

public interface Porcentajes_cirugiasDao {

	void crear(Porcentajes_cirugias porcentajes_cirugias); 

	int actualizar(Porcentajes_cirugias porcentajes_cirugias); 

	Porcentajes_cirugias consultar(Porcentajes_cirugias porcentajes_cirugias); 

	int eliminar(Porcentajes_cirugias porcentajes_cirugias); 

	List<Porcentajes_cirugias> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}