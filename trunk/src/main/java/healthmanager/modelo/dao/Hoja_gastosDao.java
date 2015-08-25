/*
 * Hoja_gastosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Hoja_gastos;

public interface Hoja_gastosDao {

	void crear(Hoja_gastos hoja_gastos); 

	int actualizar(Hoja_gastos hoja_gastos); 

	Hoja_gastos consultar(Hoja_gastos hoja_gastos); 

	int eliminar(Hoja_gastos hoja_gastos); 

	List<Hoja_gastos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}