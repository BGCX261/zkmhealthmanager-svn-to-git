/*
 * Laboratorios_respuestasDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Laboratorios_respuestas;

public interface Laboratorios_respuestasDao {

	void crear(Laboratorios_respuestas laboratorios_respuestas); 

	int actualizar(Laboratorios_respuestas laboratorios_respuestas); 

	Laboratorios_respuestas consultar(Laboratorios_respuestas laboratorios_respuestas); 

	int eliminar(Laboratorios_respuestas laboratorios_respuestas); 

	List<Laboratorios_respuestas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}