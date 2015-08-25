/*
 * Rips_auServiceImpl.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Rips_au;
import contaweb.modelo.dao.Rips_auDao;

@Service("rips_auService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Rips_auService implements Serializable{
	private String limit;
	@Autowired
	private Rips_auDao rips_auDao;

	public void crear(Rips_au rips_au) {
		try {
			if (consultar(rips_au) != null) {
				throw new HealthmanagerException(
						"Rips_au ya se encuentra en la base de datos");
			}
			rips_auDao.crear(rips_au);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Rips_au rips_au) {
		try {
			return rips_auDao.actualizar(rips_au);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Rips_au consultar(Rips_au rips_au) {
		try {
			return rips_auDao.consultar(rips_au);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Rips_au rips_au) {
		try {
			return rips_auDao.eliminar(rips_au);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Rips_au> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return rips_auDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.rips_auDao.existe(parameters);
	}

}