/*
 * ResolucionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Resolucion;
import contaweb.modelo.dao.ResolucionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("resolucionContawebService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ResolucionService implements Serializable{

	private String limit;

	@Autowired
	private ResolucionDao resolucionDao;

	public void crear(Resolucion resolucion) {
		try {
			if (consultar(resolucion) != null) {
				throw new HealthmanagerException(
						"Resolucion ya se encuentra en la base de datos");
			}
			resolucionDao.crear(resolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Resolucion resolucion) {
		try {
			return resolucionDao.actualizar(resolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Resolucion consultar(Resolucion resolucion) {
		try {
			return resolucionDao.consultar(resolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Resolucion resolucion) {
		try {
			return resolucionDao.eliminar(resolucion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Resolucion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return resolucionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
