/*
 * Cuadros_aiepi_estadoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Cuadros_aiepi_estado;

public interface Cuadros_aiepi_estadoDao {

	void crear(Cuadros_aiepi_estado cuadros_aiepi_estado); 

	int actualizar(Cuadros_aiepi_estado cuadros_aiepi_estado); 

	Cuadros_aiepi_estado consultar(Cuadros_aiepi_estado cuadros_aiepi_estado); 

	int eliminar(Cuadros_aiepi_estado cuadros_aiepi_estado); 

	List<Cuadros_aiepi_estado> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}