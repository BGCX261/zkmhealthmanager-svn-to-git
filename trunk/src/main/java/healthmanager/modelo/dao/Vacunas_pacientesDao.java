/*
 * Vacunas_pacientesDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Vacunas_pacientes;

public interface Vacunas_pacientesDao {

	void crear(Vacunas_pacientes vacunas_pacientes); 

	int actualizar(Vacunas_pacientes vacunas_pacientes); 

	Vacunas_pacientes consultar(Vacunas_pacientes vacunas_pacientes); 

	int eliminar(Vacunas_pacientes vacunas_pacientes); 

	List<Vacunas_pacientes> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param);

	Integer consultarTotalVacunasPaciente(Map<String, Object> totalVacunas);  

}