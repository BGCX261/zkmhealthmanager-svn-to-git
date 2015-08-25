/*
 * NegacionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Negacion;

public interface NegacionDao {

	void crear(Negacion negacion); 

	int actualizar(Negacion negacion); 

	Negacion consultar(Negacion negacion); 

	int eliminar(Negacion negacion); 

	List<Negacion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}