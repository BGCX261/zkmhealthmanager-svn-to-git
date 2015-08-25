/*
 * Detalle_evolucionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_evolucion;
import healthmanager.modelo.dao.Detalle_evolucionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_evolucionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_evolucionService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_evolucionDao detalle_evolucionDao;

	public void crear(Detalle_evolucion detalle_evolucion) {
		try {
			if (consultar(detalle_evolucion) != null) {
				throw new HealthmanagerException(
						"Detalle_evolucion ya se encuentra en la base de datos");
			}
			detalle_evolucionDao.crear(detalle_evolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_evolucion detalle_evolucion) {
		try {
			return detalle_evolucionDao.actualizar(detalle_evolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_evolucion consultar(Detalle_evolucion detalle_evolucion) {
		try {
			return detalle_evolucionDao.consultar(detalle_evolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_evolucion detalle_evolucion) {
		try {
			return detalle_evolucionDao.eliminar(detalle_evolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_evolucion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_evolucionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
