/*
 * Parametro_codigo_consultaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Parametro_codigo_consulta;

public interface Parametro_codigo_consultaDao {

	void crear(Parametro_codigo_consulta parametro_codigo_consulta); 

	int actualizar(Parametro_codigo_consulta parametro_codigo_consulta); 

	Parametro_codigo_consulta consultar(Parametro_codigo_consulta parametro_codigo_consulta); 

	int eliminar(Parametro_codigo_consulta parametro_codigo_consulta); 

	List<Parametro_codigo_consulta> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}