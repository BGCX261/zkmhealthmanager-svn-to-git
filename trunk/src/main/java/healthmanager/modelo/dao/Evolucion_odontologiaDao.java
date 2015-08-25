/*
 * Evolucion_odontologiaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Evolucion_odontologia;

public interface Evolucion_odontologiaDao {

	void crear(Evolucion_odontologia evolucion_odontologia); 

	int actualizar(Evolucion_odontologia evolucion_odontologia); 

	Evolucion_odontologia consultar(Evolucion_odontologia evolucion_odontologia);
	
	Evolucion_odontologia consultarUltimaEvolucion(Evolucion_odontologia evolucion_odontologia);

	int eliminar(Evolucion_odontologia evolucion_odontologia); 

	List<Evolucion_odontologia> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Integer total_registros(Map<String,Object> parameters);
	
	Evolucion_odontologia consultarDesdeAdmision(Evolucion_odontologia evolucion_odontologia);

}