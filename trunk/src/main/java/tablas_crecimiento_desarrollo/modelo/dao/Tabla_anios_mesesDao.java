/*
 * Tabla_anios_mesesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package tablas_crecimiento_desarrollo.modelo.dao;

import java.util.List;
import java.util.Map;
import tablas_crecimiento_desarrollo.modelo.bean.Tabla_anios_meses;

public interface Tabla_anios_mesesDao {

	void crear(Tabla_anios_meses tabla_anios_meses); 

	int actualizar(Tabla_anios_meses tabla_anios_meses); 

	Tabla_anios_meses consultar(Tabla_anios_meses tabla_anios_meses); 

	int eliminar(Tabla_anios_meses tabla_anios_meses); 

	List<Tabla_anios_meses> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}