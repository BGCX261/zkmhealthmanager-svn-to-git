/*
 * Remision_administradoraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Remision_administradora;
import healthmanager.modelo.dao.Remision_administradoraDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("remision_administradoraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Remision_administradoraService implements Serializable{

	private String limit;

	@Autowired
	private Remision_administradoraDao remision_administradoraDao;

	public void crear(Remision_administradora remision_administradora) {
		try {
			if (consultar(remision_administradora) != null) {
				throw new HealthmanagerException(
						"Remision_administradora ya se encuentra en la base de datos");
			}
			remision_administradoraDao.crear(remision_administradora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Remision_administradora remision_administradora) {
		try {
			return remision_administradoraDao
					.actualizar(remision_administradora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Remision_administradora consultar(
			Remision_administradora remision_administradora) {
		try {
			return remision_administradoraDao
					.consultar(remision_administradora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Remision_administradora remision_administradora) {
		try {
			return remision_administradoraDao.eliminar(remision_administradora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Remision_administradora> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return remision_administradoraDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.remision_administradoraDao.existe(parameters);
	}

}
