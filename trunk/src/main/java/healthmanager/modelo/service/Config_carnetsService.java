/*
 * Config_carnetsServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Config_carnets;
import healthmanager.modelo.dao.Config_carnetsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("config_carnetsService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Config_carnetsService implements Serializable{

	private String limit;

	@Autowired
	private Config_carnetsDao config_carnetsDao;

	public void crear(Config_carnets config_carnets) {
		try {
			if (consultar(config_carnets) != null) {
				throw new HealthmanagerException(
						"Config_carnets ya se encuentra en la base de datos");
			}
			config_carnetsDao.crear(config_carnets);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Config_carnets config_carnets) {
		try {
			return config_carnetsDao.actualizar(config_carnets);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Config_carnets consultar(Config_carnets config_carnets) {
		try {
			return config_carnetsDao.consultar(config_carnets);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Config_carnets config_carnets) {
		try {
			return config_carnetsDao.eliminar(config_carnets);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Config_carnets> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return config_carnetsDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.config_carnetsDao.existe(parameters);
	}

}
