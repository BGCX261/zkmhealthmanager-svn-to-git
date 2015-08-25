/*
 * Configuracion_autorizacion_tipo_usuarioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Configuracion_autorizacion_tipo_usuario;
import healthmanager.modelo.dao.Configuracion_autorizacion_tipo_usuarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("configuracion_autorizacion_tipo_usuarioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Configuracion_autorizacion_tipo_usuarioService implements Serializable{

	@Autowired
	private Configuracion_autorizacion_tipo_usuarioDao configuracion_autorizacion_tipo_usuarioDao;
	private String limit;

	public void crear(
			Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario) {
		try {
			if (consultar(configuracion_autorizacion_tipo_usuario) != null) {
				throw new HealthmanagerException(
						"Configuracion_autorizacion_tipo_usuario ya se encuentra en la base de datos");
			}
			configuracion_autorizacion_tipo_usuarioDao
					.crear(configuracion_autorizacion_tipo_usuario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario) {
		try {
			return configuracion_autorizacion_tipo_usuarioDao
					.actualizar(configuracion_autorizacion_tipo_usuario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Configuracion_autorizacion_tipo_usuario consultar(
			Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario) {
		try {
			return configuracion_autorizacion_tipo_usuarioDao
					.consultar(configuracion_autorizacion_tipo_usuario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario) {
		try {
			return configuracion_autorizacion_tipo_usuarioDao
					.eliminar(configuracion_autorizacion_tipo_usuario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Configuracion_autorizacion_tipo_usuario> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return configuracion_autorizacion_tipo_usuarioDao
					.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return configuracion_autorizacion_tipo_usuarioDao.existe(parameters);
	}

}