/*
 * Pexamenes_paraclinicosDao.java
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
import healthmanager.modelo.bean.Pexamenes_paraclinicos;

public interface Pexamenes_paraclinicosDao {

	void crear(Pexamenes_paraclinicos pexamenes_paraclinicos); 

	int actualizar(Pexamenes_paraclinicos pexamenes_paraclinicos); 

	Pexamenes_paraclinicos consultar(Pexamenes_paraclinicos pexamenes_paraclinicos); 

	int eliminar(Pexamenes_paraclinicos pexamenes_paraclinicos); 

	List<Pexamenes_paraclinicos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}