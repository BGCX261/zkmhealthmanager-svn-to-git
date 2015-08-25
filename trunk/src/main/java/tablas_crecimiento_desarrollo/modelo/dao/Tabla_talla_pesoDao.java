/*
 * Tabla_talla_pesoDao.java
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
import tablas_crecimiento_desarrollo.modelo.bean.Tabla_talla_peso;

public interface Tabla_talla_pesoDao {

	void crear(Tabla_talla_peso tabla_talla_peso); 

	int actualizar(Tabla_talla_peso tabla_talla_peso); 

	Tabla_talla_peso consultar(Tabla_talla_peso tabla_talla_peso); 

	int eliminar(Tabla_talla_peso tabla_talla_peso); 

	List<Tabla_talla_peso> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}