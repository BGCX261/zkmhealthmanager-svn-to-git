/*
 * Hisc_fisioterapiaDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Hisc_fisioterapia;

public interface Hisc_fisioterapiaDao {

	void crear(Hisc_fisioterapia hisc_fisioterapia); 

	int actualizar(Hisc_fisioterapia hisc_fisioterapia); 

	Hisc_fisioterapia consultar(Hisc_fisioterapia hisc_fisioterapia); 

	int eliminar(Hisc_fisioterapia hisc_fisioterapia); 

	List<Hisc_fisioterapia> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}