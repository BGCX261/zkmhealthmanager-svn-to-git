/*
 * Cuadros_aiepi_tutorialesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Cuadros_aiepi_tutoriales;

public interface Cuadros_aiepi_tutorialesDao {

	void crear(Cuadros_aiepi_tutoriales cuadros_aiepi_tutoriales); 

	int actualizar(Cuadros_aiepi_tutoriales cuadros_aiepi_tutoriales); 

	Cuadros_aiepi_tutoriales consultar(Cuadros_aiepi_tutoriales cuadros_aiepi_tutoriales); 

	int eliminar(Cuadros_aiepi_tutoriales cuadros_aiepi_tutoriales); 

	List<Cuadros_aiepi_tutoriales> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}