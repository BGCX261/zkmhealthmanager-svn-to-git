/*
 * Presultados_paraclinicosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Presultados_paraclinicos;

public interface Presultados_paraclinicosDao {

	void crear(Presultados_paraclinicos presultados_paraclinicos); 

	int actualizar(Presultados_paraclinicos presultados_paraclinicos); 

	Presultados_paraclinicos consultar(Presultados_paraclinicos presultados_paraclinicos); 

	Presultados_paraclinicos mostrar_ultimo(Presultados_paraclinicos presultados_paraclinicos); 

	int eliminar(Presultados_paraclinicos presultados_paraclinicos); 

	List<Presultados_paraclinicos> listar(Map<String,Object> parameters); 

	List<Map<String, Object>> listar_validacion_paciente(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}