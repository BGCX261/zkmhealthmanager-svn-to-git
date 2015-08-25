/*
 * Solicitud_medicamentoDao.java
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
import healthmanager.modelo.bean.Solicitud_medicamento;

public interface Solicitud_medicamentoDao {

	void crear(Solicitud_medicamento solicitud_medicamento); 

	int actualizar(Solicitud_medicamento solicitud_medicamento); 

	Solicitud_medicamento consultar(Solicitud_medicamento solicitud_medicamento); 

	int eliminar(Solicitud_medicamento solicitud_medicamento); 

	List<Solicitud_medicamento> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}