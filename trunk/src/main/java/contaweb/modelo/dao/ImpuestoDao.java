/*
 * ImpuestoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Impuesto;

public interface ImpuestoDao {

	void crear(Impuesto impuesto); 

	int actualizar(Impuesto impuesto); 

	Impuesto consultar(Impuesto impuesto); 

	int eliminar(Impuesto impuesto); 

	List<Impuesto> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}