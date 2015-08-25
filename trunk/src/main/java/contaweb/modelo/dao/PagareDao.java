/*
 * PagareDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Pagare;

public interface PagareDao {

	void crear(Pagare pagare); 

	int actualizar(Pagare pagare); 

	Pagare consultar(Pagare pagare); 
	
	Pagare consultarAdmision(Pagare pagare); 

	int eliminar(Pagare pagare); 

	List<Pagare> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}