/*
 * Roles_usuarios_capsServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.dao.Roles_usuarios_capsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("roles_usuarios_capsService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Roles_usuarios_capsService implements Serializable{

	@Autowired
	private Roles_usuarios_capsDao roles_usuarios_capsDao;

	public void crear(Roles_usuarios_caps roles_usuarios_caps) {
		try {
			if (consultar(roles_usuarios_caps) != null) {
				throw new HealthmanagerException(
						"Roles_usuarios_caps ya se encuentra en la base de datos");
			}
			roles_usuarios_capsDao.crear(roles_usuarios_caps);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Roles_usuarios_caps roles_usuarios_caps) {
		try {
			return roles_usuarios_capsDao.actualizar(roles_usuarios_caps);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Roles_usuarios_caps consultar(Roles_usuarios_caps roles_usuarios_caps) {
		try {
			return roles_usuarios_capsDao.consultar(roles_usuarios_caps);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Roles_usuarios_caps roles_usuarios_caps) {
		try {
			return roles_usuarios_capsDao.eliminar(roles_usuarios_caps);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Roles_usuarios_caps> listar(Map<String, Object> parameters) {
		try {
			return roles_usuarios_capsDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return roles_usuarios_capsDao.existe(parameters);
	}

}