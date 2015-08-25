/*
 * Esquema_vacunacionDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Esquema_vacunacion;

public interface Esquema_vacunacionDao {

	void crear(Esquema_vacunacion esquema_vacunacion); 

	int actualizar(Esquema_vacunacion esquema_vacunacion); 

	Esquema_vacunacion consultar(Esquema_vacunacion esquema_vacunacion); 

	int eliminar(Esquema_vacunacion esquema_vacunacion); 

	List<Esquema_vacunacion> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}