/*
 * Datos_procedimientoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Datos_procedimiento;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface Datos_procedimientoDao {

	void crear(Datos_procedimiento datos_procedimiento);

	int actualizar(Datos_procedimiento datos_procedimiento);

	Datos_procedimiento consultar(Datos_procedimiento datos_procedimiento);

	Datos_procedimiento consultar_auxiliar(
			Datos_procedimiento datos_procedimiento);

	int eliminar(Datos_procedimiento datos_procedimiento);

	List<Datos_procedimiento> listarTabla(Map<String, Object> parameters);

	List<Datos_procedimiento> listarResultados(Map<String, Object> parameters);

	List<Datos_procedimiento> listarResultadosRips(
			Map<String, Object> parameters);

	List<Map> listar_quirurgicos(Map<String, Object> parameters);

	void setLimit(String limit);

	boolean existe(Object datos_procedimiento);

	boolean contieneExcepciones(Map<String, Object> map);

	List<Timestamp> getUltimoProcedimiento(Map<String, Object> param);

	Map<String, Object> getFechaRealizacion(Map<String, Object> parametro);

}