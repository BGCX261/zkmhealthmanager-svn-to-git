/*
 * Cuadros_aiepi_resultadosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Cuadros_aiepi_resultados;

public interface Cuadros_aiepi_resultadosDao {

	void crear(Cuadros_aiepi_resultados cuadros_aiepi_resultados); 

	int actualizar(Cuadros_aiepi_resultados cuadros_aiepi_resultados); 

	Cuadros_aiepi_resultados consultar(Cuadros_aiepi_resultados cuadros_aiepi_resultados); 

	int eliminar(Cuadros_aiepi_resultados cuadros_aiepi_resultados); 

	List<Cuadros_aiepi_resultados> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}