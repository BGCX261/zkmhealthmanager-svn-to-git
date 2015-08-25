/*
 * HospitalizacionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Hospitalizacion;

public interface HospitalizacionDao {

	void crear(Hospitalizacion hospitalizacion); 

	int actualizar(Hospitalizacion hospitalizacion); 

	Hospitalizacion consultar(Hospitalizacion hospitalizacion); 

	int eliminar(Hospitalizacion hospitalizacion); 

	List<Hospitalizacion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}