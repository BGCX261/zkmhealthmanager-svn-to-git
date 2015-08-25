/*
 * Rips_anServiceImpl.java
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

import contaweb.modelo.bean.Rips_an;
import contaweb.modelo.dao.Rips_anDao;

@Service("rips_anService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Rips_anService implements Serializable{
	private String limit;
	@Autowired
	private Rips_anDao rips_anDao;

	public void crear(Rips_an rips_an) {
		try {
			if (consultar(rips_an) != null) {
				throw new HealthmanagerException(
						"Rips_an ya se encuentra en la base de datos");
			}
			rips_anDao.crear(rips_an);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Rips_an rips_an) {
		try {
			return rips_anDao.actualizar(rips_an);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Rips_an consultar(Rips_an rips_an) {
		try {
			return rips_anDao.consultar(rips_an);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Rips_an rips_an) {
		try {
			return rips_anDao.eliminar(rips_an);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Rips_an> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return rips_anDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.rips_anDao.existe(parameters);
	}

}