/*
 * Alteracion_menorDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Alteracion_menor;

public interface Alteracion_menorDao {

	void crear(Alteracion_menor alteracion_menor); 

	int actualizar(Alteracion_menor alteracion_menor); 

	Alteracion_menor consultar(Alteracion_menor alteracion_menor); 

	int eliminar(Alteracion_menor alteracion_menor); 

	List<Alteracion_menor> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}