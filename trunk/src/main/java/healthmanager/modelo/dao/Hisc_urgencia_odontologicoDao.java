/*
 * Hisc_urgencia_odontologicoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Hisc_urgencia_odontologico;

public interface Hisc_urgencia_odontologicoDao {

	void crear(Hisc_urgencia_odontologico hisc_urgencia_odontologico); 

	int actualizar(Hisc_urgencia_odontologico hisc_urgencia_odontologico); 

	Hisc_urgencia_odontologico consultar(Hisc_urgencia_odontologico hisc_urgencia_odontologico); 

	int eliminar(Hisc_urgencia_odontologico hisc_urgencia_odontologico); 

	List<Hisc_urgencia_odontologico> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}