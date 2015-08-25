/*
 * Detalle_contabilizacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Detalle_contabilizacion;
import contaweb.modelo.dao.Detalle_contabilizacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_contabilizacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_contabilizacionService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_contabilizacionDao detalle_contabilizacionDao;

	public void crear(Detalle_contabilizacion detalle_contabilizacion) {
		try {
			if (consultar(detalle_contabilizacion) != null) {
				throw new HealthmanagerException(
						"Detalle_contabilizacion ya se encuentra en la base de datos");
			}
			detalle_contabilizacionDao.crear(detalle_contabilizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_contabilizacion detalle_contabilizacion) {
		try {
			return detalle_contabilizacionDao
					.actualizar(detalle_contabilizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_contabilizacion consultar(
			Detalle_contabilizacion detalle_contabilizacion) {
		try {
			return detalle_contabilizacionDao
					.consultar(detalle_contabilizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_contabilizacion detalle_contabilizacion) {
		try {
			return detalle_contabilizacionDao.eliminar(detalle_contabilizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_contabilizacion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_contabilizacionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
