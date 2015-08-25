/*
 * Detalles_paquetes_serviciosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;

public interface Detalles_paquetes_serviciosDao {

	void crear(Detalles_paquetes_servicios detalles_paquetes_servicios); 

	int actualizar(Detalles_paquetes_servicios detalles_paquetes_servicios); 

	Detalles_paquetes_servicios consultar(Detalles_paquetes_servicios detalles_paquetes_servicios); 

	int eliminar(Detalles_paquetes_servicios detalles_paquetes_servicios); 

	List<Detalles_paquetes_servicios> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}