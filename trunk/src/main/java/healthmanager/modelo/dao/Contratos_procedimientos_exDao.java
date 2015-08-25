/*
 * Contratos_procedimientos_exDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Contratos_procedimientos_ex;

public interface Contratos_procedimientos_exDao {

	void crear(Contratos_procedimientos_ex contratos_procedimientos_ex); 

	int actualizar(Contratos_procedimientos_ex contratos_procedimientos_ex); 

	Contratos_procedimientos_ex consultar(Contratos_procedimientos_ex contratos_procedimientos_ex); 

	int eliminar(Contratos_procedimientos_ex contratos_procedimientos_ex); 

	List<Contratos_procedimientos_ex> listar(Map<String,Object> parameters);
	
	List<String> listar_cups_ex(Map<String, Object> parametros);

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}