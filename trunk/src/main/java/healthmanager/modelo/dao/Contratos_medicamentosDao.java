/*
 * Contratos_medicamentosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Contratos_medicamentos;

public interface Contratos_medicamentosDao {

	void crear(Contratos_medicamentos contratos_medicamentos); 

	int actualizar(Contratos_medicamentos contratos_medicamentos); 

	Contratos_medicamentos consultar(Contratos_medicamentos contratos_medicamentos); 

	int eliminar(Contratos_medicamentos contratos_medicamentos); 

	List<Contratos_medicamentos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}