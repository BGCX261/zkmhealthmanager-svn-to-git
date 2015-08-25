/*
 * Materiales_suturaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Materiales_sutura;
import healthmanager.modelo.dao.Materiales_suturaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("materiales_suturaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Materiales_suturaService implements Serializable{

	private String limit;

	@Autowired
	private Materiales_suturaDao materiales_suturaDao;

	public void crear(Materiales_sutura materiales_sutura) {
		try {
			if (consultar(materiales_sutura) != null) {
				throw new HealthmanagerException(
						"Materiales_sutura ya se encuentra en la base de datos");
			}
			materiales_suturaDao.crear(materiales_sutura);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Materiales_sutura materiales_sutura) {
		try {
			return materiales_suturaDao.actualizar(materiales_sutura);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Materiales_sutura consultar(Materiales_sutura materiales_sutura) {
		try {
			return materiales_suturaDao.consultar(materiales_sutura);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Materiales_sutura materiales_sutura) {
		try {
			return materiales_suturaDao.eliminar(materiales_sutura);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Materiales_sutura> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return materiales_suturaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.materiales_suturaDao.existe(parameters);
	}

}
