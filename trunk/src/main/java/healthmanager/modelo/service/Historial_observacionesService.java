/*
 * Historial_observacionesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Historial_observaciones;
import healthmanager.modelo.dao.Historial_observacionesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("historial_observacionesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Historial_observacionesService implements Serializable{

	@Autowired
	private Historial_observacionesDao historial_observacionesDao;
	private String limit;

	public void crear(Historial_observaciones historial_observaciones) {
		try {
			if (consultar(historial_observaciones) != null) {
				throw new HealthmanagerException(
						"Historial_observaciones ya se encuentra en la base de datos");
			}
			historial_observacionesDao.crear(historial_observaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Historial_observaciones historial_observaciones) {
		try {
			return historial_observacionesDao
					.actualizar(historial_observaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Historial_observaciones consultar(
			Historial_observaciones historial_observaciones) {
		try {
			return historial_observacionesDao
					.consultar(historial_observaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Historial_observaciones historial_observaciones) {
		try {
			return historial_observacionesDao.eliminar(historial_observaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Historial_observaciones> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return historial_observacionesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return historial_observacionesDao.existe(parameters);
	}

}