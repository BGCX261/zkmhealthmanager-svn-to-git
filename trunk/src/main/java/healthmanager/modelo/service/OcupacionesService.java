/*
 * OcupacionesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.dao.OcupacionesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("ocupacionesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class OcupacionesService implements Serializable{

	private String limit;

	@Autowired
	private OcupacionesDao ocupacionesDao;

	public void crear(Ocupaciones ocupaciones) {
		try {
			if (consultar(ocupaciones) != null) {
				throw new HealthmanagerException(
						"Ocupaciones ya se encuentra en la base de datos");
			}
			ocupacionesDao.crear(ocupaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Ocupaciones ocupaciones) {
		try {
			return ocupacionesDao.actualizar(ocupaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Ocupaciones consultar(Ocupaciones ocupaciones) {
		try {
			return ocupacionesDao.consultar(ocupaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Ocupaciones ocupaciones) {
		try {
			return ocupacionesDao.eliminar(ocupaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Ocupaciones> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return ocupacionesDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
