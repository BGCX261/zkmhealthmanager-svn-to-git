/*
 * LaboratoriosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Laboratorios;

public interface LaboratoriosDao {

	void crear(Laboratorios laboratorios); 

	int actualizar(Laboratorios laboratorios); 

	Laboratorios consultar(Laboratorios laboratorios); 

	int eliminar(Laboratorios laboratorios); 

	List<Laboratorios> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}