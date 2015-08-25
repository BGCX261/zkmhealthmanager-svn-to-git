/*
 * PrestadoresDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Prestadores;

public interface PrestadoresDao {

	void crear(Prestadores prestadores); 

	int actualizar(Prestadores prestadores); 

	Prestadores consultar(Prestadores prestadores); 

	int eliminar(Prestadores prestadores); 

	List<Prestadores> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	/**
	 * Este metodo me permite listar los prestadores, por rol y centro de salud
	 * y demas me devuelve las citas disponibles y las realizadas.
	 * @author Luis Miguel Hernandez
	 * */
	List<Map<String, Object>> listarPrestadoresPorRolCentro(Map<String, Object> param);
	
	List<Prestadores> listarPorCentro(Map<String, Object> parametros);
	
}