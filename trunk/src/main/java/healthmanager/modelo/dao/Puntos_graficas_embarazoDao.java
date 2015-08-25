/*
 * Puntos_graficas_embarazoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Puntos_graficas_embarazo;

public interface Puntos_graficas_embarazoDao {

	void crear(Puntos_graficas_embarazo puntos_graficas_embarazo); 

	int actualizar(Puntos_graficas_embarazo puntos_graficas_embarazo); 

	Puntos_graficas_embarazo consultar(Puntos_graficas_embarazo puntos_graficas_embarazo); 

	int eliminar(Puntos_graficas_embarazo puntos_graficas_embarazo); 

	List<Puntos_graficas_embarazo> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}