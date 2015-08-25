/*
 * Notas_enfermeriaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Notas_enfermeria;

public interface Notas_enfermeriaDao {

	void crear(Notas_enfermeria notas_enfermeria); 

	int actualizar(Notas_enfermeria notas_enfermeria); 

	Notas_enfermeria consultar(Notas_enfermeria notas_enfermeria); 

	int eliminar(Notas_enfermeria notas_enfermeria); 

	List<Notas_enfermeria> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}