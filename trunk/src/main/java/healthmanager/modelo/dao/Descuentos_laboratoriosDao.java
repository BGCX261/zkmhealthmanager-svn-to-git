/*
 * Descuentos_laboratoriosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Descuentos_laboratorios;

public interface Descuentos_laboratoriosDao {

	void crear(Descuentos_laboratorios descuentos_laboratorios); 

	int actualizar(Descuentos_laboratorios descuentos_laboratorios); 

	Descuentos_laboratorios consultar(Descuentos_laboratorios descuentos_laboratorios); 

	int eliminar(Descuentos_laboratorios descuentos_laboratorios); 

	List<Descuentos_laboratorios> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}