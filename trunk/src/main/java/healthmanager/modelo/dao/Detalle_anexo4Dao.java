/*
 * Detalle_anexo4Dao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_anexo4;

public interface Detalle_anexo4Dao {

	void crear(Detalle_anexo4 detalle_anexo4); 

	int actualizar(Detalle_anexo4 detalle_anexo4); 

	Detalle_anexo4 consultar(Detalle_anexo4 detalle_anexo4); 
	
	Detalle_anexo4 consultarPcd(Detalle_anexo4 detalle_anexo4); 

	int eliminar(Detalle_anexo4 detalle_anexo4); 

	List<Detalle_anexo4> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

	int eliminarParametrizado(Detalle_anexo4 detalle_anexo4);
}