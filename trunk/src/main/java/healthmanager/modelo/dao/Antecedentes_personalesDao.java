/*
 * Antecedentes_personalesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Antecedentes_personales;

public interface Antecedentes_personalesDao {

	void crear(Antecedentes_personales antecedentes_personales); 

	int actualizar(Antecedentes_personales antecedentes_personales); 

	Antecedentes_personales consultar(Antecedentes_personales antecedentes_personales); 

	int eliminar(Antecedentes_personales antecedentes_personales); 

	List<Antecedentes_personales> listar(Map<String, Object> parameters); 
	
	List<Map<String, Object>> listar_reporte(Map<String, Object> parametros);

	void setLimit(String limit); 

}