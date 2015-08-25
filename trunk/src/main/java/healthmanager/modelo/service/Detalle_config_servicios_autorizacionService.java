/*
 * Detalle_config_servicios_autorizacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_config_servicios_autorizacion;
import healthmanager.modelo.dao.Detalle_config_servicios_autorizacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_config_servicios_autorizacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_config_servicios_autorizacionService implements Serializable{

	@Autowired
	private Detalle_config_servicios_autorizacionDao detalle_config_servicios_autorizacionDao;
	private String limit;

	public void crear(
			Detalle_config_servicios_autorizacion detalle_config_servicios_autorizacion) {
		try {
			if (consultar(detalle_config_servicios_autorizacion) != null) {
				throw new HealthmanagerException(
						"Detalle_config_servicios_autorizacion ya se encuentra en la base de datos");
			}
			detalle_config_servicios_autorizacionDao
					.crear(detalle_config_servicios_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Detalle_config_servicios_autorizacion detalle_config_servicios_autorizacion) {
		try {
			return detalle_config_servicios_autorizacionDao
					.actualizar(detalle_config_servicios_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_config_servicios_autorizacion consultar(
			Detalle_config_servicios_autorizacion detalle_config_servicios_autorizacion) {
		try {
			return detalle_config_servicios_autorizacionDao
					.consultar(detalle_config_servicios_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Detalle_config_servicios_autorizacion detalle_config_servicios_autorizacion) {
		try {
			return detalle_config_servicios_autorizacionDao
					.eliminar(detalle_config_servicios_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_config_servicios_autorizacion> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_config_servicios_autorizacionDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_config_servicios_autorizacionDao.existe(parameters);
	}

}