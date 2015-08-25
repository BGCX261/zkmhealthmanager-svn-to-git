/*
 * PsiquiatriaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Psiquiatria;

public interface PsiquiatriaDao {

	void crear(Psiquiatria psiquiatria); 

	int actualizar(Psiquiatria psiquiatria); 

	Psiquiatria consultar(Psiquiatria psiquiatria); 

	Psiquiatria consultar_historia(Psiquiatria psiquiatria); 

	int eliminar(Psiquiatria psiquiatria); 

	List<Psiquiatria> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

	Integer total_registros(Map<String, Object> parameters);
}