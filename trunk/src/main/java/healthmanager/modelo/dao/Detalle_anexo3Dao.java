/*
 * Detalle_anexo3Dao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_anexo3;

public interface Detalle_anexo3Dao {

	void crear(Detalle_anexo3 detalle_anexo3); 

	int actualizar(Detalle_anexo3 detalle_anexo3); 

	Detalle_anexo3 consultar(Detalle_anexo3 detalle_anexo3); 

	int eliminar(Detalle_anexo3 detalle_anexo3); 

	List<Detalle_anexo3> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}