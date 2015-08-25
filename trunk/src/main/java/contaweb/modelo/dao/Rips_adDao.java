/*
 * Rips_adDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Rips_ad;

public interface Rips_adDao {

	void crear(Rips_ad rips_ad); 

	int actualizar(Rips_ad rips_ad); 

	Rips_ad consultar(Rips_ad rips_ad); 

	int eliminar(Rips_ad rips_ad); 

	List<Rips_ad> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}