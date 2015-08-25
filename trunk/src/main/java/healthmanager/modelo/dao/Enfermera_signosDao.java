/*
 * Enfermera_signosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Enfermera_signos;

public interface Enfermera_signosDao {

	void crear(Enfermera_signos enfermera_signos); 

	int actualizar(Enfermera_signos enfermera_signos); 

	Enfermera_signos consultar(Enfermera_signos enfermera_signos); 

	int eliminar(Enfermera_signos enfermera_signos); 

	List<Enfermera_signos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}