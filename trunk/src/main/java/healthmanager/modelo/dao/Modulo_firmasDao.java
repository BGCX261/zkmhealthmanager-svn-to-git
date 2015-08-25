/*
 * Modulo_firmasDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Modulo_firmas;

public interface Modulo_firmasDao {

	void crear(Modulo_firmas modulo_firmas); 

	int actualizar(Modulo_firmas modulo_firmas); 

	Modulo_firmas consultar(Modulo_firmas modulo_firmas); 

	int eliminar(Modulo_firmas modulo_firmas); 

	List<Modulo_firmas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Modulo_firmas consultarID(Modulo_firmas modulo_firmas);

	Modulo_firmas consultarFirma(Map<String, Object> map);  
}