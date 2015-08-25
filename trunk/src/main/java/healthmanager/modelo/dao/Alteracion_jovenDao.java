/*
 * Alteracion_jovenDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Alteracion_joven;

public interface Alteracion_jovenDao {

	void crear(Alteracion_joven alteracion_joven);

	int actualizar(Alteracion_joven alteracion_joven);

	Alteracion_joven consultar(Alteracion_joven alteracion_joven);

	Alteracion_joven consultar_historia(Alteracion_joven alteracion_joven);

	int eliminar(Alteracion_joven alteracion_joven);

	List<Alteracion_joven> listar(Map<String, Object> parameters);
	
	void setLimit(String limit);

	boolean existe(Map<String, Object> param);
	
	Integer total_registros(Map<String, Object> parameters);

}