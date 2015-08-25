/*
 * Copago_estratoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Copago_estrato;

public interface Copago_estratoDao {

	void crear(Copago_estrato Copago_estrato); 

	int actualizar(Copago_estrato Copago_estrato); 

	Copago_estrato consultar(Copago_estrato Copago_estrato); 

	int eliminar(Copago_estrato Copago_estrato); 

	List<Copago_estrato> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}