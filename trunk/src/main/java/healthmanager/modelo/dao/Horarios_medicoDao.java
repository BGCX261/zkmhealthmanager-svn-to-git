/*
 * Horarios_medicoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Horarios_medico;

public interface Horarios_medicoDao {

	void crear(Horarios_medico horarios_medico); 

	int actualizar(Horarios_medico horarios_medico); 

	Horarios_medico consultar(Horarios_medico horarios_medico); 

	int eliminar(Horarios_medico horarios_medico); 

	List<Horarios_medico> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}