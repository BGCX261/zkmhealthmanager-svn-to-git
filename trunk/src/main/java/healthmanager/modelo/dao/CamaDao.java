/*
 * CamaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Cama;

public interface CamaDao {

	void crear(Cama cama); 

	int actualizar(Cama cama); 

	Cama consultar(Cama cama); 

	int eliminar(Cama cama); 
	
	Integer nextConsecutivo(Cama cama);

	List<Cama> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}