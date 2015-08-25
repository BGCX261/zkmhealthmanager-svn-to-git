/*
 * FurtranDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Furtran;

public interface FurtranDao {

	void crear(Furtran furtran); 

	int actualizar(Furtran furtran); 

	Furtran consultar(Furtran furtran); 

	int eliminar(Furtran furtran); 

	List<Furtran> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Furtran consultarPorParametros(Furtran furtran); 

}