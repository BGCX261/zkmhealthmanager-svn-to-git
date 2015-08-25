/*
 * ProgramasDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Programas;

public interface ProgramasDao {

	void crear(Programas programas); 

	int actualizar(Programas programas); 

	Programas consultar(Programas programas); 

	int eliminar(Programas programas); 

	List<Programas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}