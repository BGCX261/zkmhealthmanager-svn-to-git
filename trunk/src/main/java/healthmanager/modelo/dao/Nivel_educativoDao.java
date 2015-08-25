/*
 * Nivel_educativoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Nivel_educativo;

public interface Nivel_educativoDao {

	void crear(Nivel_educativo nivel_educativo); 

	int actualizar(Nivel_educativo nivel_educativo); 

	Nivel_educativo consultar(Nivel_educativo nivel_educativo); 

	int eliminar(Nivel_educativo nivel_educativo); 

	List<Nivel_educativo> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}