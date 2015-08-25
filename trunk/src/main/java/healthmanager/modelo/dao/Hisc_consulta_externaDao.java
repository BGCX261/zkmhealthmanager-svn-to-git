/*
 * Hisc_consulta_externaDao.java
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
import healthmanager.modelo.bean.Hisc_consulta_externa;

public interface Hisc_consulta_externaDao {

    void crear(Hisc_consulta_externa hisc_consulta_externa);  

	int actualizar(Hisc_consulta_externa hisc_consulta_externa); 

	Hisc_consulta_externa consultar(Hisc_consulta_externa hisc_consulta_externa); 

	int eliminar(Hisc_consulta_externa hisc_consulta_externa); 

	List<Hisc_consulta_externa> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Integer total_registros(Map<String, Object> parameters);

}