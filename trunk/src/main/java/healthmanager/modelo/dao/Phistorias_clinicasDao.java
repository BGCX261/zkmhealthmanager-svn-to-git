/*
 * Phistorias_clinicasDao.java
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
import healthmanager.modelo.bean.Phistorias_clinicas;

public interface Phistorias_clinicasDao {

	void crear(Phistorias_clinicas phistorias_clinicas); 

	int actualizar(Phistorias_clinicas phistorias_clinicas); 

	Phistorias_clinicas consultar(Phistorias_clinicas phistorias_clinicas); 

	int eliminar(Phistorias_clinicas phistorias_clinicas); 

	List<Phistorias_clinicas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}