/*
 * Registro_detalleDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Registro_detalle;

public interface Registro_detalleDao {

	void crear(Registro_detalle registro_detalle); 

	int actualizar(Registro_detalle registro_detalle); 

	Registro_detalle consultar(Registro_detalle registro_detalle); 

	int eliminar(Registro_detalle registro_detalle); 

	List<Registro_detalle> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}