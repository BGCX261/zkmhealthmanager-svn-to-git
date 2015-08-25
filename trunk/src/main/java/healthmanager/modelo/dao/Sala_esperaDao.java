/*
 * Sala_esperaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Sala_espera;

public interface Sala_esperaDao {

	void crear(Sala_espera sala_espera); 

	int actualizar(Sala_espera sala_espera); 

	Sala_espera consultar(Sala_espera sala_espera); 

	int eliminar(Sala_espera sala_espera); 

	List<Sala_espera> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}