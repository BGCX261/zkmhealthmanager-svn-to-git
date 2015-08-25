/*
 * IpsDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Ips;

public interface IpsDao {

	void crear(Ips ips); 

	int actualizar(Ips ips); 

	Ips consultar(Ips ips); 

	int eliminar(Ips ips); 

	List<Ips> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}