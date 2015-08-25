/*
 * Imagen_diagnosticaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package imagen_diagnostica.modelo.dao;

import java.util.List;
import java.util.Map;

import imagen_diagnostica.modelo.bean.Imagen_diagnostica;

public interface Imagen_diagnosticaDao {

	void crear(Imagen_diagnostica imagen_diagnostica); 

	int actualizar(Imagen_diagnostica imagen_diagnostica); 

	Imagen_diagnostica consultar(Imagen_diagnostica imagen_diagnostica); 

	int eliminar(Imagen_diagnostica imagen_diagnostica); 

	List<Imagen_diagnostica> listar(Map<String,Object> parameters); 

	boolean existe(Map<String,Object> param);

	Integer consultarTotalImaenDiagnostica( 
			Map<String, Object> total_imagenes_diagnosticasMap); 

}