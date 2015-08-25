/*
 * Tarifa_medicamentoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Tarifa_medicamento;

public interface Tarifa_medicamentoDao {

	void crear(Tarifa_medicamento tarifa_medicamento); 

	int actualizar(Tarifa_medicamento tarifa_medicamento); 

	Tarifa_medicamento consultar(Tarifa_medicamento tarifa_medicamento); 

	int eliminar(Tarifa_medicamento tarifa_medicamento); 

	List<Tarifa_medicamento> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}