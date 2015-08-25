/*
 * MunicipiosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Municipios;

public interface MunicipiosDao {

	void crear(Municipios municipios); 

	int actualizar(Municipios municipios); 

	Municipios consultar(Municipios municipios); 

	int eliminar(Municipios municipios); 

	List<Municipios> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}