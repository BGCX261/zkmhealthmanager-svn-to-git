/*
 * Encuesta_esperaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Encuesta_espera;

public interface Encuesta_esperaDao {

	void crear(Encuesta_espera encuesta_espera); 

	int actualizar(Encuesta_espera encuesta_espera); 

	Encuesta_espera consultar(Encuesta_espera encuesta_espera); 

	int eliminar(Encuesta_espera encuesta_espera); 

	List<Encuesta_espera> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}