/*
 * Detalle_orden2Dao.java
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
import healthmanager.modelo.bean.Detalle_orden2;

public interface Detalle_orden2Dao {

	void crear(Detalle_orden2 detalle_orden2); 

	int actualizar(Detalle_orden2 detalle_orden2); 

	Detalle_orden2 consultar(Detalle_orden2 detalle_orden2); 

	int eliminar(Detalle_orden2 detalle_orden2); 

	List<Detalle_orden2> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}