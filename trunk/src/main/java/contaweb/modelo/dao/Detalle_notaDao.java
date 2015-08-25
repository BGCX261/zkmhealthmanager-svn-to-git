/*
 * Detalle_notaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Detalle_nota;

public interface Detalle_notaDao {

	void crear(Detalle_nota detalle_nota); 

	int actualizar(Detalle_nota detalle_nota); 

	Detalle_nota consultar(Detalle_nota detalle_nota); 

	int eliminar(Detalle_nota detalle_nota); 

	List<Detalle_nota> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}