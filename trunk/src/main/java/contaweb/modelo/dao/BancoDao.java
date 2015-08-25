/*
 * BancoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Banco;

public interface BancoDao {

	void crear(Banco banco); 

	int actualizar(Banco banco); 

	Banco consultar(Banco banco); 

	int eliminar(Banco banco); 

	List<Banco> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	boolean existe(Map<String, Object> param);
}