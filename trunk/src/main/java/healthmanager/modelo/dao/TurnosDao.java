/*
 * TurnosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Turnos;

public interface TurnosDao {

	void crear(Turnos turnos); 

	int actualizar(Turnos turnos); 

	Turnos consultar(Turnos turnos); 

	int eliminar(Turnos turnos); 

	List<Turnos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}