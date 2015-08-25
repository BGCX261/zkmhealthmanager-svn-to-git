/*
 * Contrato_prestadoresDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Contrato_prestadores;

public interface Contrato_prestadoresDao {

	void crear(Contrato_prestadores contrato_prestadores); 

	int actualizar(Contrato_prestadores contrato_prestadores); 

	Contrato_prestadores consultar(Contrato_prestadores contrato_prestadores); 

	int eliminar(Contrato_prestadores contrato_prestadores); 

	List<Contrato_prestadores> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}