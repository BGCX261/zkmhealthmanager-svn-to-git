/*
 * PeriodoDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Periodo;

public interface PeriodoDao {

	void crear(Periodo periodo); 

	int actualizar(Periodo periodo); 

	Periodo consultar(Periodo periodo); 

	int eliminar(Periodo periodo); 

	List<Periodo> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}