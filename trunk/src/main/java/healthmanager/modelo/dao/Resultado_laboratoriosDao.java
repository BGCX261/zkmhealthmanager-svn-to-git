/*
 * Resultado_laboratoriosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Resultado_laboratorios;

public interface Resultado_laboratoriosDao {

	void crear(Resultado_laboratorios resultado_laboratorios); 

	int actualizar(Resultado_laboratorios resultado_laboratorios); 

	Resultado_laboratorios consultar(Resultado_laboratorios resultado_laboratorios); 

	int eliminar(Resultado_laboratorios resultado_laboratorios); 

	List<Resultado_laboratorios> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param);

	boolean resultadosFueraRango(Map<String, Object> map);  
	
	boolean consultarExiste(Resultado_laboratorios resultado_laboratorios);

}