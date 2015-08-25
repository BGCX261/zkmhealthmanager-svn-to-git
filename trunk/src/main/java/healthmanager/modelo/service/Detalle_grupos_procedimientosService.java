/*
 * Detalle_grupos_procedimientosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;
import healthmanager.modelo.dao.Detalle_grupos_procedimientosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_grupos_procedimientosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_grupos_procedimientosService implements Serializable{

	@Autowired
	private Detalle_grupos_procedimientosDao detalle_grupos_procedimientosDao;
	private String limit;

	public void crear(
			Detalle_grupos_procedimientos detalle_grupos_procedimientos) {
		try {
			if (consultar(detalle_grupos_procedimientos) != null) {
				throw new HealthmanagerException(
						"Detalle_grupos_procedimientos ya se encuentra en la base de datos");
			}
			detalle_grupos_procedimientosDao
					.crear(detalle_grupos_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Detalle_grupos_procedimientos detalle_grupos_procedimientos) {
		try {
			return detalle_grupos_procedimientosDao
					.actualizar(detalle_grupos_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_grupos_procedimientos consultar(
			Detalle_grupos_procedimientos detalle_grupos_procedimientos) {
		try {
			return detalle_grupos_procedimientosDao
					.consultar(detalle_grupos_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Detalle_grupos_procedimientos detalle_grupos_procedimientos) {
		try {
			return detalle_grupos_procedimientosDao
					.eliminar(detalle_grupos_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_grupos_procedimientos> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_grupos_procedimientosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_grupos_procedimientosDao.existe(parameters);
	}

}