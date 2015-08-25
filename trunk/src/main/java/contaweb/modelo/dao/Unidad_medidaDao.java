/*
 * Unidad_medidaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Unidad_medida;

public interface Unidad_medidaDao {

	void crear(Unidad_medida unidad_medida); 

	int actualizar(Unidad_medida unidad_medida); 

	Unidad_medida consultar(Unidad_medida unidad_medida); 

	int eliminar(Unidad_medida unidad_medida); 

	List<Unidad_medida> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}