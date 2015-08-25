/*
 * Facturacion_medicamentoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Facturacion_medicamento;

public interface Facturacion_medicamentoDao {

	void crear(Facturacion_medicamento facturacion_medicamento); 

	int actualizar(Facturacion_medicamento facturacion_medicamento); 

	Facturacion_medicamento consultar(Facturacion_medicamento facturacion_medicamento); 

	int eliminar(Facturacion_medicamento facturacion_medicamento); 

	List<Facturacion_medicamento> listar(Map<String, Object> parameters); 
	
	boolean existe(Object object);

	void setLimit(String limit); 

}