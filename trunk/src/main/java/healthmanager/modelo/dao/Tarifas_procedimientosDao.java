/*
 * Tarifas_procedimientosDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Tarifas_procedimientos;

public interface Tarifas_procedimientosDao {

	void crear(Tarifas_procedimientos tarifas_procedimientos); 

	int actualizar(Tarifas_procedimientos tarifas_procedimientos); 

	Tarifas_procedimientos consultar(Tarifas_procedimientos tarifas_procedimientos); 

	int eliminar(Tarifas_procedimientos tarifas_procedimientos); 

	List<Tarifas_procedimientos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}