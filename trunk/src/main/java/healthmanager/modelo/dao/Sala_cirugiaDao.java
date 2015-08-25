/*
 * Sala_cirugiaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Sala_cirugia;

public interface Sala_cirugiaDao {

	void crear(Sala_cirugia sala_cirugia); 

	int actualizar(Sala_cirugia sala_cirugia); 

	Sala_cirugia consultar(Sala_cirugia sala_cirugia); 

	int eliminar(Sala_cirugia sala_cirugia); 

	List<Sala_cirugia> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}