/*
 * Anexo10_entidadDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Anexo10_entidad;

public interface Anexo10_entidadDao {

	void crear(Anexo10_entidad anexo10_entidad); 

	int actualizar(Anexo10_entidad anexo10_entidad); 

	Anexo10_entidad consultar(Anexo10_entidad anexo10_entidad); 

	int eliminar(Anexo10_entidad anexo10_entidad); 

	List<Anexo10_entidad> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	boolean existe(Map<String,Object> param); 

}