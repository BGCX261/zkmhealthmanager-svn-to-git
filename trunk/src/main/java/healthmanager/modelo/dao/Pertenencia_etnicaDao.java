/*
 * Pertenencia_etnicaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Pertenencia_etnica;

public interface Pertenencia_etnicaDao {

	void crear(Pertenencia_etnica pertenencia_etnica); 

	int actualizar(Pertenencia_etnica pertenencia_etnica); 

	Pertenencia_etnica consultar(Pertenencia_etnica pertenencia_etnica); 

	int eliminar(Pertenencia_etnica pertenencia_etnica); 

	List<Pertenencia_etnica> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}