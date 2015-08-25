/*
 * Laboratorios_resultadosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package imagen_diagnostica.modelo.dao;

import java.util.List;
import java.util.Map;
import imagen_diagnostica.modelo.bean.Laboratorios_resultados;

public interface Laboratorios_resultadosDao {

	void crear(Laboratorios_resultados laboratorios_resultados); 

	int actualizar(Laboratorios_resultados laboratorios_resultados); 

	Laboratorios_resultados consultar(Laboratorios_resultados laboratorios_resultados); 

	int eliminar(Laboratorios_resultados laboratorios_resultados); 

	List<Laboratorios_resultados> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}