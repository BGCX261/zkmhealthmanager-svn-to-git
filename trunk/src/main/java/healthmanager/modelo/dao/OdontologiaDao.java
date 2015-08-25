/*
 * OdontologiaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Odontologia;

public interface OdontologiaDao {

	void crear(Odontologia odontologia);

	int actualizar(Odontologia odontologia);

	Odontologia consultar(Odontologia odontologia);

	Odontologia consultar_historia(Odontologia odontologia);

	int eliminar(Odontologia odontologia);

	List<Odontologia> listar(Map<String, Object> parameters);
	
	Integer total_registros(Map<String,Object> parameters);

	void setLimit(String limit);

}