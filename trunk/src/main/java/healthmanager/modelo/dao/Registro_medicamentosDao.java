/*
 * Registro_medicamentosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Registro_medicamentos;

public interface Registro_medicamentosDao {

	void crear(Registro_medicamentos registro_medicamentos); 

	int actualizar(Registro_medicamentos registro_medicamentos); 

	Registro_medicamentos consultar(Registro_medicamentos registro_medicamentos); 

	int eliminar(Registro_medicamentos registro_medicamentos); 

	List<Registro_medicamentos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}