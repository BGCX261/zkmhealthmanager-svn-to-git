/*
 * contratosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Contratos;

public interface ContratosDao {

	void crear(Contratos contratos); 

	int actualizar(Contratos contratos); 

	Contratos consultar(Contratos contratos); 
	
	Contratos consultarPorNroContrato(Contratos contratos);

	int eliminar(Contratos contratos); 

	List<Contratos> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	List<Map<String,Object>> listarProcedimientos(Map<String,Object> param);
	
	boolean prestaServiciosPyp(Map<String, Object> params);
	
	Integer totalResultados(Map<String, Object> parameters);
	
}