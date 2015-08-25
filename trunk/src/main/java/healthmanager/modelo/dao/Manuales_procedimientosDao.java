/*
 * Manuales_procedimientosDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Manuales_procedimientos;

public interface Manuales_procedimientosDao {

	void crear(Manuales_procedimientos manuales_procedimientos); 

	int actualizar(Manuales_procedimientos manuales_procedimientos); 

	Manuales_procedimientos consultar(Manuales_procedimientos manuales_procedimientos); 

	int eliminar(Manuales_procedimientos manuales_procedimientos); 
	
	int eliminar_manual(Manuales_procedimientos manuales_procedimientos);

	List<Manuales_procedimientos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}