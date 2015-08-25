/*
 * Pacientes_contratosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Pacientes_contratos;

import java.util.List;
import java.util.Map;

public interface Pacientes_contratosDao {

	void crear(Pacientes_contratos pacientes_contratos); 

	int actualizar(Pacientes_contratos pacientes_contratos);

	Pacientes_contratos consultar(Pacientes_contratos pacientes_contratos); 

	int eliminar(Pacientes_contratos pacientes_contratos); 
	
	int eliminar_contratos_varios(Map<String, Object> mapa_contratos);

	List<Pacientes_contratos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	int eliminar_contratos(Pacientes_contratos paciente);
	
	Double getPoblacionReal(Map<String, Object> params);

}