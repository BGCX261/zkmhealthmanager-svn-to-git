/*
 * Justificacion_negacionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Justificacion_negacion;

public interface Justificacion_negacionDao {

	void crear(Justificacion_negacion justificacion_negacion); 

	int actualizar(Justificacion_negacion justificacion_negacion); 

	Justificacion_negacion consultar(Justificacion_negacion justificacion_negacion); 

	int eliminar(Justificacion_negacion justificacion_negacion); 

	List<Justificacion_negacion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}