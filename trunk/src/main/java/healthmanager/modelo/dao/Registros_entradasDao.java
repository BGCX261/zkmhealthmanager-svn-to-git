/*
 * Nota_pypDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Registros_entradas;

import java.util.List;
import java.util.Map;

public interface Registros_entradasDao {

	void crear(Registros_entradas registros_entradas); 

	int actualizar(Registros_entradas registros_entradas); 

	Registros_entradas consultar(Registros_entradas registros_entradas); 

	int eliminar(Registros_entradas registros_entradas); 

	List<Registros_entradas> listar(Map<String, Object> parameters); 
	
	List<Long> listarIds(Map<String, Object> parameters);
	
	long cantidad_entradas(Map<String, Object> parametros);

	void setLimit(String limit); 

}