/*
 * Programas_actividades_pypDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Programas_actividades_pyp;

public interface Programas_actividades_pypDao {

	void crear(Programas_actividades_pyp programas_actividades_pyp); 

	int actualizar(Programas_actividades_pyp programas_actividades_pyp); 

	Programas_actividades_pyp consultar(Programas_actividades_pyp programas_actividades_pyp); 

	int eliminar(Programas_actividades_pyp programas_actividades_pyp); 

	List<Programas_actividades_pyp> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}