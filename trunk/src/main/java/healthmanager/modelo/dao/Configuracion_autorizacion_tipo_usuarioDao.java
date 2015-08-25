/*
 * Configuracion_autorizacion_tipo_usuarioDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Configuracion_autorizacion_tipo_usuario;

public interface Configuracion_autorizacion_tipo_usuarioDao {

	void crear(Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario); 

	int actualizar(Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario); 

	Configuracion_autorizacion_tipo_usuario consultar(Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario); 

	int eliminar(Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario); 

	List<Configuracion_autorizacion_tipo_usuario> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}