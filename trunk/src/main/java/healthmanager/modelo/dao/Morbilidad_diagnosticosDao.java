/*
 * Morbilidad_diagnosticosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Morbilidad_diagnosticos;

public interface Morbilidad_diagnosticosDao {

	void crear(Morbilidad_diagnosticos morbilidad_diagnosticos); 

	int actualizar(Morbilidad_diagnosticos morbilidad_diagnosticos); 

	Morbilidad_diagnosticos consultar(Morbilidad_diagnosticos morbilidad_diagnosticos); 

	int eliminar(Morbilidad_diagnosticos morbilidad_diagnosticos); 

	List<Morbilidad_diagnosticos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}