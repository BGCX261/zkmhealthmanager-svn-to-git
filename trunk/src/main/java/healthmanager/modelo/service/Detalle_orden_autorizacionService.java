/*
 * Detalle_orden_autorizacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_orden_autorizacion;
import healthmanager.modelo.dao.Detalle_orden_autorizacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_orden_autorizacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_orden_autorizacionService implements Serializable{

	@Autowired
	private Detalle_orden_autorizacionDao detalle_orden_autorizacionDao;
	private String limit;

	public void crear(Detalle_orden_autorizacion detalle_orden_autorizacion) {
		try {
			if (consultar(detalle_orden_autorizacion) != null) {
				throw new HealthmanagerException(
						"Detalle_orden_autorizacion ya se encuentra en la base de datos");
			}
			detalle_orden_autorizacionDao.crear(detalle_orden_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_orden_autorizacion detalle_orden_autorizacion) {
		try {
			return detalle_orden_autorizacionDao
					.actualizar(detalle_orden_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_orden_autorizacion consultar(
			Detalle_orden_autorizacion detalle_orden_autorizacion) {
		try {
			return detalle_orden_autorizacionDao
					.consultar(detalle_orden_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_orden_autorizacion detalle_orden_autorizacion) {
		try {
			return detalle_orden_autorizacionDao
					.eliminar(detalle_orden_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_orden_autorizacion> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_orden_autorizacionDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_orden_autorizacionDao.existe(parameters);
	}

}