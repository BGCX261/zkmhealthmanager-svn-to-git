/*
 * Metas_pypDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Metas_pyp;

public interface Metas_pypDao {

	void crear(Metas_pyp metas_pyp); 

	int actualizar(Metas_pyp metas_pyp); 

	Metas_pyp consultar(Metas_pyp metas_pyp); 

	int eliminar(Metas_pyp metas_pyp); 
	
	int eliminarDesdeContrato(Metas_pyp metas_pyp); 

	List<Metas_pyp> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Integer getCantidadMetasProcedimiento(Map<String, Object> param);
	
	Integer getCantidadMetasArticulos(Map<String, Object> param);
	
	void eliminarPorContrato(Metas_pyp metas_pyp);
	
	Timestamp getUltimaFechaProcedimiento(Map<String, Object> map);
	
	Timestamp getUltimaFechaArticulos(Map<String, Object> map);
}