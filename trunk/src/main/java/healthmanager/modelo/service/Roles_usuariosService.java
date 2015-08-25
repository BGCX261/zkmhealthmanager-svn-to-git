/*
 * Roles_usuariosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.dao.Roles_usuariosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("roles_usuariosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Roles_usuariosService implements Serializable{

	@Autowired
	private Roles_usuariosDao roles_usuariosDao;

	public void crear(Roles_usuarios roles_usuarios) {
		try {
			if (consultar(roles_usuarios) != null) {
				throw new HealthmanagerException(
						"Roles_usuarios ya se encuentra en la base de datos");
			}
			roles_usuariosDao.crear(roles_usuarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Roles_usuarios roles_usuarios) {
		try {
			return roles_usuariosDao.actualizar(roles_usuarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Roles_usuarios consultar(Roles_usuarios roles_usuarios) {
		try {
			return roles_usuariosDao.consultar(roles_usuarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Roles_usuarios roles_usuarios) {
		try {
			return roles_usuariosDao.eliminar(roles_usuarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Roles_usuarios> listar(Map<String, Object> parameter) {
		try {
			return roles_usuariosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
}
