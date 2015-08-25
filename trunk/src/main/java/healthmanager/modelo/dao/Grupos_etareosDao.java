/*
 * Grupos_etareosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Grupos_etareos;

public interface Grupos_etareosDao {

	void crear(Grupos_etareos grupos_etareos); 

	int actualizar(Grupos_etareos grupos_etareos); 

	Grupos_etareos consultar(Grupos_etareos grupos_etareos); 

	int eliminar(Grupos_etareos grupos_etareos); 

	List<Grupos_etareos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}