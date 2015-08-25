/*
 * Anexo1Dao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Anexo1;

public interface Anexo1Dao {

	void crear(Anexo1 anexo1); 

	int actualizar(Anexo1 anexo1); 

	Anexo1 consultar(Anexo1 anexo1); 

	int eliminar(Anexo1 anexo1); 

	List<Anexo1> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}