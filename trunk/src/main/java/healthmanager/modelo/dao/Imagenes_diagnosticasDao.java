/*
 * Imagenes_diagnosticasDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Imagenes_diagnosticas;

public interface Imagenes_diagnosticasDao {

	void crear(Imagenes_diagnosticas imagenes_diagnosticas); 

	int actualizar(Imagenes_diagnosticas imagenes_diagnosticas); 

	Imagenes_diagnosticas consultar(Imagenes_diagnosticas imagenes_diagnosticas); 

	int eliminar(Imagenes_diagnosticas imagenes_diagnosticas); 

	List<Imagenes_diagnosticas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}