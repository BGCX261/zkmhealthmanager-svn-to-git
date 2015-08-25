/*
 * Rips_usServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing: 
	Luis Hernadez Perez
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

import contaweb.modelo.bean.Rips_us;
import contaweb.modelo.dao.Rips_usDao;

@Service("rips_usService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Rips_usService implements Serializable{
	private String limit;
	@Autowired
	private Rips_usDao rips_usDao;

	public void crear(Rips_us rips_us) {
		try {
			if (consultar(rips_us) != null) {
				throw new HealthmanagerException(
						"Rips_us ya se encuentra en la base de datos");
			}
			rips_usDao.crear(rips_us);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Rips_us rips_us) {
		try {
			return rips_usDao.actualizar(rips_us);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Rips_us consultar(Rips_us rips_us) {
		try {
			return rips_usDao.consultar(rips_us);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Rips_us rips_us) {
		try {
			return rips_usDao.eliminar(rips_us);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Rips_us> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return rips_usDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.rips_usDao.existe(parameters);
	}

}