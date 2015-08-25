/*
 * Cuadros_aiepi_sugerenciasDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Cuadros_aiepi_sugerencias;

public interface Cuadros_aiepi_sugerenciasDao {

	void crear(Cuadros_aiepi_sugerencias cuadros_aiepi_sugerencias); 

	int actualizar(Cuadros_aiepi_sugerencias cuadros_aiepi_sugerencias); 

	Cuadros_aiepi_sugerencias consultar(Cuadros_aiepi_sugerencias cuadros_aiepi_sugerencias); 

	int eliminar(Cuadros_aiepi_sugerencias cuadros_aiepi_sugerencias); 

	List<Cuadros_aiepi_sugerencias> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}