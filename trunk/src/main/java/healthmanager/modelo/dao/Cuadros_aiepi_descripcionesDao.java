/*
 * Cuadros_aiepi_descripcionesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Cuadros_aiepi_descripciones;

public interface Cuadros_aiepi_descripcionesDao {

	void crear(Cuadros_aiepi_descripciones cuadros_aiepi_descripciones); 

	int actualizar(Cuadros_aiepi_descripciones cuadros_aiepi_descripciones); 

	Cuadros_aiepi_descripciones consultar(Cuadros_aiepi_descripciones cuadros_aiepi_descripciones); 

	int eliminar(Cuadros_aiepi_descripciones cuadros_aiepi_descripciones); 

	List<Cuadros_aiepi_descripciones> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}