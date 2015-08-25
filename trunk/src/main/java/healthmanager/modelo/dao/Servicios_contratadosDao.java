/*
 * Servicios_contratadosDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Servicios_contratados;

public interface Servicios_contratadosDao {

	void crear(Servicios_contratados servicios_contratados); 

	int actualizar(Servicios_contratados servicios_contratados); 

	Servicios_contratados consultar(Servicios_contratados servicios_contratados); 

	int eliminar(Servicios_contratados servicios_contratados); 

	List<Servicios_contratados> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	boolean getTieneContratoServicio(Map<String, Object> parametros);
	
	boolean getTieneContratoServicioParticular(Map<String, Object> parametros);
	
	List<Servicios_contratados> listarServiciosPaciente(Map<String, Object> parametros);
	
	List<Servicios_contratados> listarServiciosParticular(Map<String, Object> parametros);
	
	List<Map<String, Object>> getServiciosContratadosRepetidos(Map<String, Object> parametros);
	
	List<Servicios_contratados> listarServiciosPermitidos(Map<String, Object> parametros);
	
	List<String> listar_codigos(Map<String, Object> parametros);
	
	List<Map<String, Object>> getContratosValidos(Map<String, Object> params);
	
	List<String> listarServiciosPermitidosPorConfiguracion(Map<String, Object> params);
}