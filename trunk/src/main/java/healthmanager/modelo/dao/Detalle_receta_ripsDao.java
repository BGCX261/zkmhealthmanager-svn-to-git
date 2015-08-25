/*
 * Detalle_receta_ripsDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Detalle_receta_rips;

public interface Detalle_receta_ripsDao {

	void crear(Detalle_receta_rips detalle_receta_rips); 

	int actualizar(Detalle_receta_rips detalle_receta_rips); 

	Detalle_receta_rips consultar(Detalle_receta_rips detalle_receta_rips); 

	int eliminar(Detalle_receta_rips detalle_receta_rips); 

	List<Detalle_receta_rips> listar(Map<String, Object> parameters); 

	void setLimit(String limit);

	int autorizarEntrega(Map<String, Object> map);  

}