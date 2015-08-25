/*
 * ConsultorioDao.java
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
import healthmanager.modelo.bean.Consultorio;

public interface ConsultorioDao {

	void crear(Consultorio consultorio); 

	int actualizar(Consultorio consultorio); 

	Consultorio consultar(Consultorio consultorio); 

	int eliminar(Consultorio consultorio); 

	List<Consultorio> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}