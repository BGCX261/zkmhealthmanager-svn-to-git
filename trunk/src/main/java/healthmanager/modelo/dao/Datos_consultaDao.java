/*
 * Datos_consultaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Datos_consulta;

import java.util.List;
import java.util.Map;

public interface Datos_consultaDao {

	void crear(Datos_consulta datos_consulta);

	int actualizar(Datos_consulta datos_consulta);

	Datos_consulta consultar(Datos_consulta datos_consulta);

	int eliminar(Datos_consulta datos_consulta);

	List<Datos_consulta> listarTabla(Map<String, Object> parameters);

	List<Datos_consulta> listarResultados(Map<String, Object> parameters);

	List<Datos_consulta> listarResultadosRips(Map<String, Object> parameters);

	Integer totalResultados(Map<String, Object> parametros);

	boolean existe(Object object);

	Map<String, Object> getFechaRealizacion(Map<String, Object> map);
}