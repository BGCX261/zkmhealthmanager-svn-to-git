/*
 * Ficha_epidemiologia_historialDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;

public interface Ficha_epidemiologia_historialDao {

	void crear(Ficha_epidemiologia_historial ficha_epidemiologia_historial); 

	int actualizar(Ficha_epidemiologia_historial ficha_epidemiologia_historial); 

	Ficha_epidemiologia_historial consultar(Ficha_epidemiologia_historial ficha_epidemiologia_historial); 

	int eliminar(Ficha_epidemiologia_historial ficha_epidemiologia_historial); 

	List<Ficha_epidemiologia_historial> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}