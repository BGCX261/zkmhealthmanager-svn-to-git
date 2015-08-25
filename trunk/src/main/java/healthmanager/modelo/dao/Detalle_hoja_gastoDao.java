/*
 * Detalle_hoja_gastoDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_hoja_gasto;

public interface Detalle_hoja_gastoDao {

	void crear(Detalle_hoja_gasto detalle_hoja_gasto); 

	int actualizar(Detalle_hoja_gasto detalle_hoja_gasto); 

	Detalle_hoja_gasto consultar(Detalle_hoja_gasto detalle_hoja_gasto); 

	int eliminar(Detalle_hoja_gasto detalle_hoja_gasto); 

	List<Detalle_hoja_gasto> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}