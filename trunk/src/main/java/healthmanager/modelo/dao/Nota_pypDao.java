/*
 * Nota_pypDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Nota_pyp;

public interface Nota_pypDao {

	void crear(Nota_pyp nota_pyp); 

	int actualizar(Nota_pyp nota_pyp); 

	Nota_pyp consultar(Nota_pyp nota_pyp); 

	int eliminar(Nota_pyp nota_pyp); 

	List<Nota_pyp> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}