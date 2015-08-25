/*
 * Detalle_plantilla_pypDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_plantilla_pyp;

public interface Detalle_plantilla_pypDao {

	void crear(Detalle_plantilla_pyp detalle_plantilla_pyp); 

	int actualizar(Detalle_plantilla_pyp detalle_plantilla_pyp); 

	Detalle_plantilla_pyp consultar(Detalle_plantilla_pyp detalle_plantilla_pyp); 

	int eliminar(Detalle_plantilla_pyp detalle_plantilla_pyp); 

	List<Detalle_plantilla_pyp> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}