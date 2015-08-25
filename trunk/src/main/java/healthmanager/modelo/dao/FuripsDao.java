/*
 * FuripsDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Furips;

public interface FuripsDao {

	void crear(Furips furips); 

	int actualizar(Furips furips); 

	Furips consultar(Furips furips); 

	int eliminar(Furips furips); 

	List<Furips> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}