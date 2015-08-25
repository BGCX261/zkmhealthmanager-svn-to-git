/*
 * Modulo_firmas_reportesDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Modulo_firmas_reportes;

public interface Modulo_firmas_reportesDao {

	void crear(Modulo_firmas_reportes modulo_firmas_reportes); 

	int actualizar(Modulo_firmas_reportes modulo_firmas_reportes); 

	Modulo_firmas_reportes consultar(Modulo_firmas_reportes modulo_firmas_reportes); 

	int eliminar(Modulo_firmas_reportes modulo_firmas_reportes); 

	List<Modulo_firmas_reportes> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Modulo_firmas_reportes consultarID(Modulo_firmas_reportes modulo_firmas_reportes); 
}