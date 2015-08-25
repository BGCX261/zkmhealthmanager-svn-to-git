/*
 * Orden_servicio2Dao.java
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
import healthmanager.modelo.bean.Orden_servicio2;

public interface Orden_servicio2Dao {

	void crear(Orden_servicio2 orden_servicio2); 

	int actualizar(Orden_servicio2 orden_servicio2); 

	Orden_servicio2 consultar(Orden_servicio2 orden_servicio2); 

	int eliminar(Orden_servicio2 orden_servicio2); 

	List<Orden_servicio2> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}