/*
 * Paciente_centroDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Paciente_centro;

public interface Paciente_centroDao {

	void crear(Paciente_centro paciente_centro); 

	int actualizar(Paciente_centro paciente_centro); 

	Paciente_centro consultar(Paciente_centro paciente_centro); 

	int eliminar(Paciente_centro paciente_centro); 

	List<Paciente_centro> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}