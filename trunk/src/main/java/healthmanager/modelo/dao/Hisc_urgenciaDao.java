/*
 * Hisc_partoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Hisc_urgencia;

public interface Hisc_urgenciaDao {

	void crear(Hisc_urgencia hisc_urgencia); 

	int actualizar(Hisc_urgencia hisc_urgencia); 

	Hisc_urgencia consultar(Hisc_urgencia hisc_urgencia); 

	int eliminar(Hisc_urgencia hisc_urgencia); 

	List<Hisc_urgencia> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}