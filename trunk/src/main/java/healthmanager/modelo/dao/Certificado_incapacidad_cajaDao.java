/*
 * Certificado_incapacidad_cajaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Certificado_incapacidad_caja;

public interface Certificado_incapacidad_cajaDao {

	void crear(Certificado_incapacidad_caja certificado_incapacidad_caja); 

	int actualizar(Certificado_incapacidad_caja certificado_incapacidad_caja); 

	Certificado_incapacidad_caja consultar(Certificado_incapacidad_caja certificado_incapacidad_caja); 

	int eliminar(Certificado_incapacidad_caja certificado_incapacidad_caja); 

	List<Certificado_incapacidad_caja> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean exist(Map<String,Object> param);

	Integer totalResultados(Map<String, Object> parametros);  

}