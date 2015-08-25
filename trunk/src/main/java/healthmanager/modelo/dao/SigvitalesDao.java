/*
 * SigvitalesDao.java
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
import healthmanager.modelo.bean.Sigvitales;

public interface SigvitalesDao {

	void crear(Sigvitales sigvitales); 

	int actualizar(Sigvitales sigvitales); 

	Sigvitales consultar(Sigvitales sigvitales); 

	int eliminar(Sigvitales sigvitales); 

	List<Sigvitales> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}