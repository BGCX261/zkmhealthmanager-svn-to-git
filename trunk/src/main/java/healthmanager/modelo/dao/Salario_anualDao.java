/*
 * Salario_anualDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Salario_anual;

public interface Salario_anualDao {

	void crear(Salario_anual salario_anual); 

	int actualizar(Salario_anual salario_anual); 

	Salario_anual consultar(Salario_anual salario_anual); 

	int eliminar(Salario_anual salario_anual); 

	List<Salario_anual> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}