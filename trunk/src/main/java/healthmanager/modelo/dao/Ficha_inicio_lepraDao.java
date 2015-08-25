/*
 * Ficha_inicio_lepraDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Ficha_inicio_lepra;

public interface Ficha_inicio_lepraDao {

	void crear(Ficha_inicio_lepra ficha_inicio_lepra); 

	int actualizar(Ficha_inicio_lepra ficha_inicio_lepra); 

	Ficha_inicio_lepra consultar(Ficha_inicio_lepra ficha_inicio_lepra); 

	int eliminar(Ficha_inicio_lepra ficha_inicio_lepra); 

	List<Ficha_inicio_lepra> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

	boolean existe_paciente_lepra(Map<String,Object> param); 

}