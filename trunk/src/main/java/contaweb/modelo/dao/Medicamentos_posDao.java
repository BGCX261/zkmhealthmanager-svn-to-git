/*
 * Medicamentos_posDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Medicamentos_pos;

public interface Medicamentos_posDao {

	void crear(Medicamentos_pos medicamentos_pos); 

	int actualizar(Medicamentos_pos medicamentos_pos); 

	Medicamentos_pos consultar(Medicamentos_pos medicamentos_pos); 

	int eliminar(Medicamentos_pos medicamentos_pos); 

	List<Medicamentos_pos> listar(Map parameters); 

	void setLimit(String limit); 

}