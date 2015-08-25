/*
 * CitasDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Oportunidad_citas;

public interface CitasDao {

	void crear(Citas citas); 

	int actualizar(Citas citas); 

	Citas consultar(Citas citas); 

	Citas consultar_citas(Map<String, Object> parameters); 
	
	boolean existe(Map<String, Object> map);  

	int eliminar(Citas citas); 

	List<Citas> listar(Map<String, Object> parameters); 

	List<Map<String, Object>> listar_oportunidad(Map<String, Object> parameters); 

	List<Map<String, Object>> listar_oportunidad_pacientes(Map<String, Object> parameters); 
	
	List<Oportunidad_citas> listar_oportunidad_consulta(Map<String, Object> parameters); 
	
	void setLimit(String limit); 
	
	int obtenerCitasAseguradora(Map<String,Object> params);
	
	public int getCitas(Map<String, Object> parameter);
}