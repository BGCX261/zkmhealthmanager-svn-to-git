/*
 * Detalles_paquetes_serviciosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.dao.Detalles_paquetes_serviciosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalles_paquetes_serviciosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalles_paquetes_serviciosService implements Serializable{

	@Autowired
	private Detalles_paquetes_serviciosDao detalles_paquetes_serviciosDao;
	private String limit;

	public void crear(Detalles_paquetes_servicios detalles_paquetes_servicios) {
		try {
			if (consultar(detalles_paquetes_servicios) != null) {
				throw new HealthmanagerException(
						"Detalles_paquetes_servicios ya se encuentra en la base de datos");
			}
			detalles_paquetes_serviciosDao.crear(detalles_paquetes_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Detalles_paquetes_servicios detalles_paquetes_servicios) {
		try {
			return detalles_paquetes_serviciosDao
					.actualizar(detalles_paquetes_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalles_paquetes_servicios consultar(
			Detalles_paquetes_servicios detalles_paquetes_servicios) {
		try {
			return detalles_paquetes_serviciosDao
					.consultar(detalles_paquetes_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalles_paquetes_servicios detalles_paquetes_servicios) {
		try {
			return detalles_paquetes_serviciosDao
					.eliminar(detalles_paquetes_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalles_paquetes_servicios> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalles_paquetes_serviciosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalles_paquetes_serviciosDao.existe(parameters);
	}

}