/*
 * Hisc_deteccion_alt_embarazoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Hisc_deteccion_alt_embarazo;

import java.util.List;
import java.util.Map;

public interface Hisc_deteccion_alt_embarazoDao {

	void crear(Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo); 

	int actualizar(Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo); 

	Hisc_deteccion_alt_embarazo consultar(Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo); 

	int eliminar(Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo); 

	List<Hisc_deteccion_alt_embarazo> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Integer total_registros(Map<String, Object> parameters);
	
   //Hisc_deteccion_alt_embarazo cargarUltimaHistoria(Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo);

}