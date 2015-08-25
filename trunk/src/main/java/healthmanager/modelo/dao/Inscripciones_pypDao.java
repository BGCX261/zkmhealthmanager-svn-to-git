/*
 * Inscripciones_pypDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Inscripciones_pyp;

public interface Inscripciones_pypDao {

	void crear(Inscripciones_pyp inscripciones_pyp); 

	int actualizar(Inscripciones_pyp inscripciones_pyp); 

	Inscripciones_pyp consultar(Inscripciones_pyp inscripciones_pyp); 

	int eliminar(Inscripciones_pyp inscripciones_pyp); 

	List<Inscripciones_pyp> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}