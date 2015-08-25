/*
 * Paquetes_serviciosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Paquetes_servicios;

public interface Paquetes_serviciosDao {

	void crear(Paquetes_servicios paquetes_servicios); 

	int actualizar(Paquetes_servicios paquetes_servicios); 

	Paquetes_servicios consultar(Paquetes_servicios paquetes_servicios); 

	int eliminar(Paquetes_servicios paquetes_servicios); 

	List<Paquetes_servicios> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}