/*
 * AdministradoraDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Administradora;

public interface AdministradoraDao {

	void crear(Administradora administradora); 

	int actualizar(Administradora administradora); 

	Administradora consultar(Administradora administradora); 

	int eliminar(Administradora administradora); 

	List<Administradora> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	List<Map> listarDesdeContratos(Map<String, Object> parameters); 
	
	List<Administradora> listarAdministradorasServicio(Map<String, Object> params);
	
	Administradora consultarNit(Administradora administradora); 
	
	String consultarTipoAseguradora(Administradora administradora);
}