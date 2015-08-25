/*
 * Nota_enfermeriaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Nota_enfermeria;

public interface Nota_enfermeriaDao {

	void crear(Nota_enfermeria nota_enfermeria); 

	int actualizar(Nota_enfermeria nota_enfermeria); 

	Nota_enfermeria consultar(Nota_enfermeria nota_enfermeria); 

	int eliminar(Nota_enfermeria nota_enfermeria); 

	List<Nota_enfermeria> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}