/*
 * ContabilizacionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Contabilizacion;

public interface ContabilizacionDao {

	void crear(Contabilizacion contabilizacion); 

	int actualizar(Contabilizacion contabilizacion); 

	Contabilizacion consultar(Contabilizacion contabilizacion); 

	int eliminar(Contabilizacion contabilizacion); 

	List<Contabilizacion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Contabilizacion contabilizacion); 

}