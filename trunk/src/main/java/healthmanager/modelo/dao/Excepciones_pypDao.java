/*
 * Excepciones_pypDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Excepciones_pyp;

public interface Excepciones_pypDao {

	void crear(Excepciones_pyp excepciones_pyp); 

	int actualizar(Excepciones_pyp excepciones_pyp); 

	Excepciones_pyp consultar(Excepciones_pyp excepciones_pyp); 

	int eliminar(Excepciones_pyp excepciones_pyp); 

	List<Excepciones_pyp> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}