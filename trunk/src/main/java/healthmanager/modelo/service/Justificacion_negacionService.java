/*
 * Justificacion_negacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Justificacion_negacion;
import healthmanager.modelo.dao.Justificacion_negacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("justificacion_negacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Justificacion_negacionService implements Serializable{

	private String limit;

	@Autowired
	private Justificacion_negacionDao justificacion_negacionDao;

	public void crear(Justificacion_negacion justificacion_negacion) {
		try {
			if (consultar(justificacion_negacion) != null) {
				throw new HealthmanagerException(
						"Justificacion_negacion ya se encuentra en la base de datos");
			}
			justificacion_negacionDao.crear(justificacion_negacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Justificacion_negacion justificacion_negacion) {
		try {
			return justificacion_negacionDao.actualizar(justificacion_negacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Justificacion_negacion consultar(
			Justificacion_negacion justificacion_negacion) {
		try {
			return justificacion_negacionDao.consultar(justificacion_negacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Justificacion_negacion justificacion_negacion) {
		try {
			return justificacion_negacionDao.eliminar(justificacion_negacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Justificacion_negacion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return justificacion_negacionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
