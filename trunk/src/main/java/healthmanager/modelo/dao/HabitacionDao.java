/*
 * HabitacionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Habitacion;

public interface HabitacionDao {

	void crear(Habitacion habitacion); 

	int actualizar(Habitacion habitacion); 

	Habitacion consultar(Habitacion habitacion); 

	int eliminar(Habitacion habitacion); 
	
	Integer nextConsecutivo(Habitacion habitacion);

	List<Habitacion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}