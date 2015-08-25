/*
 * FuripsServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Furips;
import healthmanager.modelo.dao.FuripsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("furipsService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class FuripsService implements Serializable{

	private String limit;

	@Autowired
	private FuripsDao furipsDao;

	public void crear(Furips furips) {
		try {
			if (consultar(furips) != null) {
				throw new HealthmanagerException(
						"Furips ya se encuentra en la base de datos");
			}
			furipsDao.crear(furips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Furips furips) {
		try {
			return furipsDao.actualizar(furips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Furips consultar(Furips furips) {
		try {
			return furipsDao.consultar(furips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Furips furips) {
		try {
			return furipsDao.eliminar(furips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Furips> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return furipsDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.furipsDao.existe(parameters);
	}

}
