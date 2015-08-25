/*
 * Diente_evolucionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Diente_evolucion;
import healthmanager.modelo.dao.Diente_evolucionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("diente_evolucionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Diente_evolucionService implements Serializable{

	private String limit;

	@Autowired
	private Diente_evolucionDao diente_evolucionDao;

	public void crear(Diente_evolucion diente_evolucion) {
		try {
			if (consultar(diente_evolucion) != null) {
				throw new HealthmanagerException(
						"Diente_evolucion ya se encuentra en la base de datos");
			}
			diente_evolucionDao.crear(diente_evolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Diente_evolucion diente_evolucion) {
		try {
			return diente_evolucionDao.actualizar(diente_evolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Diente_evolucion consultar(Diente_evolucion diente_evolucion) {
		try {
			return diente_evolucionDao.consultar(diente_evolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Diente_evolucion diente_evolucion) {
		try {
			return diente_evolucionDao.eliminar(diente_evolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Diente_evolucion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return diente_evolucionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.diente_evolucionDao.existe(parameters);
	}

}
