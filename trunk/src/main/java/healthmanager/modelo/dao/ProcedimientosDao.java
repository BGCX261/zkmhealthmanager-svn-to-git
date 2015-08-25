/*
 * ProcedimientoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Procedimientos;

public interface ProcedimientosDao {

	void crear(Procedimientos procedimientos); 

	int actualizar(Procedimientos procedimientos); 

	Procedimientos consultar(Procedimientos procedimientos); 

	int eliminar(Procedimientos procedimientos); 

	List<Procedimientos> listar(Map<String, Object> parameters); 
	
	List<String> listar_cups(Map<String, Object> parametros);

	void setLimit(String limit);

	Long consultarIDPorCups(Procedimientos procedimientos); 
	
	int actualizarPorDmanda(Procedimientos procedimientos);

}