/*
 * C_costo_via_ingresoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.C_costo_via_ingreso;

public interface C_costo_via_ingresoDao {

	void crear(C_costo_via_ingreso c_costo_via_ingreso); 

	int actualizar(C_costo_via_ingreso c_costo_via_ingreso); 

	C_costo_via_ingreso consultar(C_costo_via_ingreso c_costo_via_ingreso); 
	
	C_costo_via_ingreso consultarUnico(C_costo_via_ingreso c_costo_via_ingreso); 

	int eliminar(C_costo_via_ingreso c_costo_via_ingreso); 

	List<C_costo_via_ingreso> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}