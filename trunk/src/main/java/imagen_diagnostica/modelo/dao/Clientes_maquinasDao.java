/*
 * Clientes_maquinasDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package imagen_diagnostica.modelo.dao;

import java.util.List;
import java.util.Map;
import imagen_diagnostica.modelo.bean.Clientes_maquinas;

public interface Clientes_maquinasDao {

	void crear(Clientes_maquinas clientes_maquinas); 

	int actualizar(Clientes_maquinas clientes_maquinas); 

	Clientes_maquinas consultar(Clientes_maquinas clientes_maquinas); 

	int eliminar(Clientes_maquinas clientes_maquinas); 

	List<Clientes_maquinas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}