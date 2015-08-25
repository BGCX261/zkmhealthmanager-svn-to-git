/*
 * Grupo_cuentaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Grupo_cuenta;
import contaweb.modelo.dao.Grupo_cuentaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("grupo_cuentaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Grupo_cuentaService implements Serializable{

	private String limit;

	@Autowired
	private Grupo_cuentaDao grupo_cuentaDao;

	public void crear(Grupo_cuenta grupo_cuenta) {
		try {
			if (consultar(grupo_cuenta) != null) {
				throw new HealthmanagerException(
						"Grupo_cuenta ya se encuentra en la base de datos");
			}
			grupo_cuentaDao.crear(grupo_cuenta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Grupo_cuenta grupo_cuenta) {
		try {
			return grupo_cuentaDao.actualizar(grupo_cuenta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Grupo_cuenta consultar(Grupo_cuenta grupo_cuenta) {
		try {
			return grupo_cuentaDao.consultar(grupo_cuenta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Grupo_cuenta grupo_cuenta) {
		try {
			return grupo_cuentaDao.eliminar(grupo_cuenta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Grupo_cuenta> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return grupo_cuentaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.grupo_cuentaDao.existe(parameters);
	}

}
