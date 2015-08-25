/*
 * Porcentaje_adicional_medicamentoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Porcentaje_adicional_medicamento;

public interface Porcentaje_adicional_medicamentoDao {

	void crear(Porcentaje_adicional_medicamento porcentaje_adicional_medicamento); 

	int actualizar(Porcentaje_adicional_medicamento porcentaje_adicional_medicamento); 

	Porcentaje_adicional_medicamento consultar(Porcentaje_adicional_medicamento porcentaje_adicional_medicamento); 

	int eliminar(Porcentaje_adicional_medicamento porcentaje_adicional_medicamento); 

	List<Porcentaje_adicional_medicamento> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}