/*
 * ConsecutivoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import contaweb.modelo.bean.Consecutivo;

@Repository("consecutivoContawebDao")
public interface ConsecutivoDao {

	void crear(Consecutivo consecutivo); 

	int actualizar(Consecutivo consecutivo); 

	Consecutivo consultar(Consecutivo consecutivo); 

	int eliminar(Consecutivo consecutivo); 

	List<Consecutivo> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}