/*
 * Ficha_epidemiologiaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Ficha_epidemiologia;

public interface Ficha_epidemiologiaDao {

	void crear(Ficha_epidemiologia ficha_epidemiologia); 

	int actualizar(Ficha_epidemiologia ficha_epidemiologia); 

	Ficha_epidemiologia consultar(Ficha_epidemiologia ficha_epidemiologia); 

	int eliminar(Ficha_epidemiologia ficha_epidemiologia); 

	List<Ficha_epidemiologia> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}