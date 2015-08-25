/*
 * Rips_ahServiceImpl.java
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

import contaweb.modelo.bean.Rips_ah;
import contaweb.modelo.dao.Rips_ahDao;

@Service("rips_ahService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Rips_ahService implements Serializable{
	private String limit;

	@Autowired
	private Rips_ahDao rips_ahDao;

	public void crear(Rips_ah rips_ah) {
		try {
			if (consultar(rips_ah) != null) {
				throw new HealthmanagerException(
						"Rips_ah ya se encuentra en la base de datos");
			}
			rips_ahDao.crear(rips_ah);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Rips_ah rips_ah) {
		try {
			return rips_ahDao.actualizar(rips_ah);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Rips_ah consultar(Rips_ah rips_ah) {
		try {
			return rips_ahDao.consultar(rips_ah);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Rips_ah rips_ah) {
		try {
			return rips_ahDao.eliminar(rips_ah);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Rips_ah> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return rips_ahDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.rips_ahDao.existe(parameters);
	}

}