/*
 * Metodos_planificacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Metodos_planificacion;
import healthmanager.modelo.dao.Metodos_planificacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("metodos_planificacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Metodos_planificacionService implements Serializable{

	private String limit;

	@Autowired
	private Metodos_planificacionDao metodos_planificacionDao;

	public void crear(Metodos_planificacion metodos_planificacion) {
		try {
			if (consultar(metodos_planificacion) != null) {
				throw new HealthmanagerException(
						"Metodos_planificacion ya se encuentra en la base de datos");
			}
			metodos_planificacionDao.crear(metodos_planificacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Metodos_planificacion metodos_planificacion) {
		try {
			return metodos_planificacionDao.actualizar(metodos_planificacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Metodos_planificacion consultar(
			Metodos_planificacion metodos_planificacion) {
		try {
			return metodos_planificacionDao.consultar(metodos_planificacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Metodos_planificacion metodos_planificacion) {
		try {
			return metodos_planificacionDao.eliminar(metodos_planificacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Metodos_planificacion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return metodos_planificacionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setMetodos_planificacionDao(
			Metodos_planificacionDao metodos_planificacionDao) {
		this.metodos_planificacionDao = metodos_planificacionDao;
	}

	public Metodos_planificacionDao getMetodos_planificacionDao() {
		return this.metodos_planificacionDao;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
