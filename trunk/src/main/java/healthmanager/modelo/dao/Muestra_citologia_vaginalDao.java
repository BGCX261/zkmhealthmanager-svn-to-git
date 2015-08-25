/*
 * Muestra_citologia_vaginalDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Muestra_citologia_vaginal;

import java.util.List;
import java.util.Map;

public interface Muestra_citologia_vaginalDao {

	void crear(Muestra_citologia_vaginal muestra_citologia_vaginal); 

	int actualizar(Muestra_citologia_vaginal muestra_citologia_vaginal); 

	Muestra_citologia_vaginal consultar(Muestra_citologia_vaginal muestra_citologia_vaginal); 

	int eliminar(Muestra_citologia_vaginal muestra_citologia_vaginal); 

	List<Muestra_citologia_vaginal> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Muestra_citologia_vaginal consultarPorFiltros(Muestra_citologia_vaginal muestra_citologia_vaginal);  
	
}