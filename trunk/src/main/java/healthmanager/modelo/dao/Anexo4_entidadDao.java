/*
 * Anexo4_entidadDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Anexo4_entidad;

import java.util.List;
import java.util.Map;

public interface Anexo4_entidadDao {

	void crear(Anexo4_entidad anexo4_entidad); 

	int actualizar(Anexo4_entidad anexo4_entidad); 

	Anexo4_entidad consultar(Anexo4_entidad anexo4_entidad); 

	int eliminar(Anexo4_entidad anexo4_entidad); 

	List<Anexo4_entidad> listar(Map<String, Object> parameters); 

	void setLimit(String limit);

	int anularAutorizacion(Map<String, Object> map);

	void eliminarParametro(Map<String, Object> params);  
	
	List<Anexo4_entidad> listarResultados(Map<String, Object> parametros);
	
	Integer totalResultados(Map<String, Object> parameters);
	
	Anexo4_entidad consultarParametrizado(Anexo4_entidad anexo4_entidad);
}