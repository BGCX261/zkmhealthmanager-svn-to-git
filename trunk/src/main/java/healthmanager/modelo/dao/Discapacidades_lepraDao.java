/*
 * Discapacidades_lepraDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Discapacidades_lepra;

public interface Discapacidades_lepraDao {

	void crear(Discapacidades_lepra discapacidades_lepra); 

	int actualizar(Discapacidades_lepra discapacidades_lepra); 

	Discapacidades_lepra consultar(Discapacidades_lepra discapacidades_lepra); 

	int eliminar(Map<String,Object> parameters); 

	List<Discapacidades_lepra> listar(Map<String,Object> parameters); 

	List<Discapacidades_lepra> listar_pacientes_lepra(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}