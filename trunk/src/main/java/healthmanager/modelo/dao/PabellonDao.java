/*
 * PabellonDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Pabellon;

public interface PabellonDao {

	void crear(Pabellon pabellon); 

	int actualizar(Pabellon pabellon); 

	Pabellon consultar(Pabellon pabellon); 

	int eliminar(Pabellon pabellon); 
	
	Integer nextConsecutivo(Pabellon pabellon);

	List<Pabellon> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}