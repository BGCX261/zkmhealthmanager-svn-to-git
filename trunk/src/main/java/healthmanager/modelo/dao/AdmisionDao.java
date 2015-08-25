/*
 * AdmisionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Admision;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface AdmisionDao {

	void crear(Admision admision); 

	int actualizar(Admision admision);
	
	int actualizar_estado(Admision admision);

	Admision consultar(Admision admision); 

	int eliminar(Admision admision); 

	List<Admision> listarTabla(Map<String, Object> parameters); 
	
	List<Admision> listarResultados(Map<String, Object> parameters); 
	
	Integer totalResultados(Map<String, Object> parameters); 
	
	List<Admision> listar_oportunidad(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	Admision consultarAdmisionDiasAnteriores(Map<String, Object> map);
	
	Boolean existe(Map<String, Object> parametros);
 
	Long getIdHistoria(Admision admision);

	boolean consultarContieneServicios(Admision admision);  
	
	Map<String, Object> getUltimaAutorizacion(Map<String, Object> parametros);
 
	Timestamp getFechaUltimoServicio(Admision admision);

}