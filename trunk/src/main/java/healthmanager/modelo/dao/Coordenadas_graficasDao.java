/*
 * Coordenadas_graficasDao.java
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
import healthmanager.modelo.bean.Coordenadas_graficas;

public interface Coordenadas_graficasDao {

	void crear(Coordenadas_graficas coordenadas_graficas); 

	int actualizar(Coordenadas_graficas coordenadas_graficas); 

	Coordenadas_graficas consultar(Coordenadas_graficas coordenadas_graficas); 

	int eliminar(Coordenadas_graficas coordenadas_graficas); 

	List<Coordenadas_graficas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}