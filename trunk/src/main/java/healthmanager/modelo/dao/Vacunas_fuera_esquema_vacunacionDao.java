/*
 * Vacunas_fuera_esquema_vacunacionDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Vacunas_fuera_esquema_vacunacion;

public interface Vacunas_fuera_esquema_vacunacionDao {

	void crear(Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion); 

	int actualizar(Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion); 

	Vacunas_fuera_esquema_vacunacion consultar(Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion); 

	int eliminar(Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion); 

	List<Vacunas_fuera_esquema_vacunacion> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}