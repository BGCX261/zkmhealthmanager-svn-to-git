/*
 * Seguimiento_convivientes_lepraDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Seguimiento_convivientes_lepra;

public interface Seguimiento_convivientes_lepraDao {

	void crear(Seguimiento_convivientes_lepra seguimiento_convivientes_lepra); 

	int actualizar(Seguimiento_convivientes_lepra seguimiento_convivientes_lepra); 

	Seguimiento_convivientes_lepra consultar(Seguimiento_convivientes_lepra seguimiento_convivientes_lepra); 

	int eliminar(Map<String,Object> parameters); 

	List<Seguimiento_convivientes_lepra> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}