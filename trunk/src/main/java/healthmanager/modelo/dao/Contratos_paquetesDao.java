/*
 * Contratos_paquetesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Contratos_paquetes;

public interface Contratos_paquetesDao {

	void crear(Contratos_paquetes contratos_paquetes); 

	int actualizar(Contratos_paquetes contratos_paquetes); 

	Contratos_paquetes consultar(Contratos_paquetes contratos_paquetes); 

	int eliminar(Contratos_paquetes contratos_paquetes); 

	List<Contratos_paquetes> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}