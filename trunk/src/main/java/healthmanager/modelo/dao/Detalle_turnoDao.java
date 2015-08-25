/*
 * Detalle_turnoDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_turno;

public interface Detalle_turnoDao {

	void crear(Detalle_turno detalle_turno); 

	int actualizar(Detalle_turno detalle_turno); 

	Detalle_turno consultar(Detalle_turno detalle_turno); 

	int eliminar(Detalle_turno detalle_turno); 

	List<Detalle_turno> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}