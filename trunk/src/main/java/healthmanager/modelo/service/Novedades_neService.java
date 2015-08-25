/*
 * Novedades_neServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Novedades_ne;
import healthmanager.modelo.dao.Novedades_neDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("novedades_neService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Novedades_neService implements Serializable{

	private String limit;

	@Autowired
	private Novedades_neDao novedades_neDao;

	public void crear(Novedades_ne novedades_ne) {
		try {
			if (consultar(novedades_ne) != null) {
				throw new HealthmanagerException(
						"Novedades_ne ya se encuentra en la base de datos");
			}
			novedades_neDao.crear(novedades_ne);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Novedades_ne novedades_ne) {
		try {
			return novedades_neDao.actualizar(novedades_ne);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Novedades_ne consultar(Novedades_ne novedades_ne) {
		try {
			return novedades_neDao.consultar(novedades_ne);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Novedades_ne novedades_ne) {
		try {
			return novedades_neDao.eliminar(novedades_ne);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Novedades_ne> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return novedades_neDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map> listarMap(Map param) {
		param.put("limit", limit);
		return novedades_neDao.listarMap(param);
	}

}
