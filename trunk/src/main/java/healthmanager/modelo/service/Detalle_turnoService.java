/*
 * Detalle_turnoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_turno;
import healthmanager.modelo.dao.Detalle_turnoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_turnoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_turnoService implements Serializable{

	@Autowired
	private Detalle_turnoDao detalle_turnoDao;
	private String limit;

	public void crear(Detalle_turno detalle_turno) {
		try {
			if (consultar(detalle_turno) != null) {
				throw new HealthmanagerException(
						"Detalle_turno ya se encuentra en la base de datos");
			}
			detalle_turnoDao.crear(detalle_turno);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_turno detalle_turno) {
		try {
			return detalle_turnoDao.actualizar(detalle_turno);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_turno consultar(Detalle_turno detalle_turno) {
		try {
			return detalle_turnoDao.consultar(detalle_turno);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_turno detalle_turno) {
		try {
			return detalle_turnoDao.eliminar(detalle_turno);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_turno> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_turnoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_turnoDao.existe(parameters);
	}

}