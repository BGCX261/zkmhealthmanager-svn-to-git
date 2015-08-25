/*
 * VacunasDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Vacunas;

public interface VacunasDao {

	void crear(Vacunas vacunas); 

	int actualizar(Vacunas vacunas); 

	Vacunas consultar(Vacunas vacunas); 

	int eliminar(Vacunas vacunas); 

	List<Vacunas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param);
 
	List<String> getCodigoProcedimientosVacunas(Map<String, Object> map); 

}