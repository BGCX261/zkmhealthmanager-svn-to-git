/*
 * Datos_medicamentosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Datos_medicamentos;

import java.util.List;
import java.util.Map;

public interface Datos_medicamentosDao {

	void crear(Datos_medicamentos datos_medicamentos); 

	int actualizar(Datos_medicamentos datos_medicamentos); 

	Datos_medicamentos consultar(Datos_medicamentos datos_medicamentos); 

	int eliminar(Datos_medicamentos datos_medicamentos); 

	List<Datos_medicamentos> listar(Map<String, Object> parameters); 
	
	List<Datos_medicamentos> listar_factura(Map<String, Object> parameters); 

	void setLimit(String limit); 	

	List<Map<String,Object>> medicamentos_hoja_gastos(Map<String,Object> param);

	Map<String, Object> getFechaRealizacion(Map<String, Object> parametro);
	
	boolean existe(Map<String,Object> param); 
} 