/*
 * Resultado_paraclinicoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Resultado_paraclinico;

public interface Resultado_paraclinicoDao {

	void crear(Resultado_paraclinico resultado_paraclinico); 

	int actualizar(Resultado_paraclinico resultado_paraclinico); 

	Resultado_paraclinico consultar(Resultado_paraclinico resultado_paraclinico); 

	int eliminar(Resultado_paraclinico resultado_paraclinico); 

	List<Resultado_paraclinico> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	int obtenerConsecutivo(Resultado_paraclinico resultado_paraclinico); 

	boolean existe(Map<String,Object> param);

	 

}