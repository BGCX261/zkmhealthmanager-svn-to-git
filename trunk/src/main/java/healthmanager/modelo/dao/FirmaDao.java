/*
 * FirmaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Firma;

public interface FirmaDao {

	void crear(Firma firma); 

	int actualizar(Firma firma); 

	Firma consultar(Firma firma); 

	int eliminar(Firma firma); 

	List<Firma> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}