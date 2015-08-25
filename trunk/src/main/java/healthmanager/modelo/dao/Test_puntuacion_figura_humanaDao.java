/*
 * Test_puntuacion_figura_humanaDao.java
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
import healthmanager.modelo.bean.Test_puntuacion_figura_humana;

public interface Test_puntuacion_figura_humanaDao {

	void crear(Test_puntuacion_figura_humana test_puntuacion_figura_humana); 

	int actualizar(Test_puntuacion_figura_humana test_puntuacion_figura_humana); 

	Test_puntuacion_figura_humana consultar(Test_puntuacion_figura_humana test_puntuacion_figura_humana); 

	int eliminar(Test_puntuacion_figura_humana test_puntuacion_figura_humana); 

	List<Test_puntuacion_figura_humana> listar(Map<String,Object> parameters); 

	List<Test_puntuacion_figura_humana> listarHistorial(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}