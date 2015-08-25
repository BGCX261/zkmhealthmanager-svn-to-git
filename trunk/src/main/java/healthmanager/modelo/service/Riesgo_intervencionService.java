/*
 * Riesgo_intervencionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Riesgo_intervencion;
import healthmanager.modelo.dao.Riesgo_intervencionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("riesgo_intervencionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Riesgo_intervencionService implements Serializable{

	private String limit;

	@Autowired
	private Riesgo_intervencionDao riesgo_intervencionDao;

	public void crear(Riesgo_intervencion riesgo_intervencion) {
		try {
			if (consultar(riesgo_intervencion) != null) {
				throw new HealthmanagerException(
						"Riesgo_intervencion ya se encuentra en la base de datos");
			}
			riesgo_intervencionDao.crear(riesgo_intervencion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Riesgo_intervencion riesgo_intervencion) {
		try {
			return riesgo_intervencionDao.actualizar(riesgo_intervencion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Riesgo_intervencion consultar(Riesgo_intervencion riesgo_intervencion) {
		try {
			return riesgo_intervencionDao.consultar(riesgo_intervencion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Riesgo_intervencion riesgo_intervencion) {
		try {
			return riesgo_intervencionDao.eliminar(riesgo_intervencion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Riesgo_intervencion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return riesgo_intervencionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
