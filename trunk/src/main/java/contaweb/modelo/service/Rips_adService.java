/*
 * Rips_adServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
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

import contaweb.modelo.bean.Rips_ad;
import contaweb.modelo.dao.Rips_adDao;

@Service("rips_adService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Rips_adService implements Serializable{

	@Autowired
	private Rips_adDao rips_adDao;
	private String limit;

	public void crear(Rips_ad rips_ad) {
		try {
			if (consultar(rips_ad) != null) {
				throw new HealthmanagerException(
						"Rips_ad ya se encuentra en la base de datos");
			}
			rips_adDao.crear(rips_ad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Rips_ad rips_ad) {
		try {
			return rips_adDao.actualizar(rips_ad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Rips_ad consultar(Rips_ad rips_ad) {
		try {
			return rips_adDao.consultar(rips_ad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Rips_ad rips_ad) {
		try {
			return rips_adDao.eliminar(rips_ad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Rips_ad> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return rips_adDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return rips_adDao.existe(parameters);
	}

}