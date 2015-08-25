/*
 * Anexo3_entidadDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Anexo3_entidad;

public interface Anexo3_entidadDao {

	void crear(Anexo3_entidad anexo3_entidad); 

	int actualizar(Anexo3_entidad anexo3_entidad); 

	Anexo3_entidad consultar(Anexo3_entidad anexo3_entidad); 

	int eliminar(Anexo3_entidad anexo3_entidad); 

	List<Anexo3_entidad> listar(Map<String, Object> parameters); 

	void setLimit(String limit);

	int anularAutorizacion(Map<String, Object> map);

	void eliminarParametro(Anexo3_entidad anexo_3entidad);   

}