/*
 * Visita_domiciliariaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Visita_domiciliaria;

public interface Visita_domiciliariaDao {

	void crear(Visita_domiciliaria visita_domiciliaria); 

	int actualizar(Visita_domiciliaria visita_domiciliaria); 

	Visita_domiciliaria consultar(Visita_domiciliaria visita_domiciliaria); 

	Visita_domiciliaria consultar_historia(Visita_domiciliaria visita_domiciliaria); 

	int eliminar(Visita_domiciliaria visita_domiciliaria); 

	List<Visita_domiciliaria> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	

}