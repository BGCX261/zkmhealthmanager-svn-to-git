/*
 * Maestro_manualDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Maestro_manual;

public interface Maestro_manualDao {

	void crear(Maestro_manual maestro_manual); 

	int actualizar(Maestro_manual maestro_manual); 

	Maestro_manual consultar(Maestro_manual maestro_manual); 

	int eliminar(Maestro_manual maestro_manual); 

	List<Maestro_manual> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}