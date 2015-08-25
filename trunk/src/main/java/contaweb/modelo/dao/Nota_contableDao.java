/*
 * Nota_contableDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Nota_contable;

public interface Nota_contableDao {

	void crear(Nota_contable nota_contable); 

	int actualizar(Nota_contable nota_contable); 

	Nota_contable consultar(Nota_contable nota_contable); 
	
	Nota_contable consultarNota_credito_glosa(Nota_contable nota_contable); 

	int eliminar(Nota_contable nota_contable); 

	List<Nota_contable> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}