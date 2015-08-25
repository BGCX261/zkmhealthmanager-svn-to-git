/*
 * Afiliaciones_meDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Afiliaciones_me;

public interface Afiliaciones_meDao {

	void crear(Afiliaciones_me afiliaciones_me); 

	int actualizar(Afiliaciones_me afiliaciones_me); 

	Afiliaciones_me consultar(Afiliaciones_me afiliaciones_me); 

	int eliminar(Afiliaciones_me afiliaciones_me); 

	List<Afiliaciones_me> listar(Map<String, Object> parameters); 

	void setLimit(String limit);
	
	List<Map<String, Object>> listarMap(Map<String, Object> param);
	
	byte[] getFoto(Afiliaciones_me afiliacionesMe);
	
	List<Map<String, Object>> listarMap054(Map<String, Object> params);
	Map<String, Object> getMap198(Map<String, Object> params);
	
	Map<String, Object> getUltimoAportes(Map<String, Object> params);
	
	boolean existe(Map<String, Object> params);
}