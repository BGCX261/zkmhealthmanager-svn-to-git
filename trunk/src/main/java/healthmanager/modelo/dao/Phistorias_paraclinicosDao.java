/*
 * Phistorias_paraclinicosDao.java
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
import healthmanager.modelo.bean.Phistorias_paraclinicos;

public interface Phistorias_paraclinicosDao {

	void crear(Phistorias_paraclinicos phistorias_paraclinicos); 

	int actualizar(Phistorias_paraclinicos phistorias_paraclinicos); 

	Phistorias_paraclinicos consultar(Phistorias_paraclinicos phistorias_paraclinicos); 

	int eliminar(Phistorias_paraclinicos phistorias_paraclinicos); 

	List<Phistorias_paraclinicos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}