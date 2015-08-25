/*
 * Consultorio_prestadorDao.java
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
import healthmanager.modelo.bean.Consultorio_prestador;

public interface Consultorio_prestadorDao {

	void crear(Consultorio_prestador consultorio_prestador); 

	int actualizar(Consultorio_prestador consultorio_prestador); 

	Consultorio_prestador consultar(Consultorio_prestador consultorio_prestador); 

	int eliminar(Consultorio_prestador consultorio_prestador); 

	List<Consultorio_prestador> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}