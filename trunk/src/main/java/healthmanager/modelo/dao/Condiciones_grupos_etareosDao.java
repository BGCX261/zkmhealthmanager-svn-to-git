/*
 * Condiciones_grupos_etareosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Condiciones_grupos_etareos;

public interface Condiciones_grupos_etareosDao {

	void crear(Condiciones_grupos_etareos condiciones_grupos_etareos); 

	int actualizar(Condiciones_grupos_etareos condiciones_grupos_etareos); 

	Condiciones_grupos_etareos consultar(Condiciones_grupos_etareos condiciones_grupos_etareos); 

	int eliminar(Condiciones_grupos_etareos condiciones_grupos_etareos); 

	List<Condiciones_grupos_etareos> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}