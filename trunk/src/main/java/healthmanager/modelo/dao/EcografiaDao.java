/*
 * EcografiaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Ecografia;

public interface EcografiaDao {

	void crear(Ecografia ecografia); 

	int actualizar(Ecografia ecografia); 

	Ecografia consultar(Ecografia ecografia); 

	int eliminar(Ecografia ecografia); 

	List<Ecografia> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param);

	Ecografia consultarPorFiltros(Ecografia ecografia);  

}