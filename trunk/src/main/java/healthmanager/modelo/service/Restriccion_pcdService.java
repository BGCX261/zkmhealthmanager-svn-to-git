/*
 * Restriccion_pcdServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Restriccion_pcd;
import healthmanager.modelo.dao.Restriccion_pcdDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("restriccion_pcdService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Restriccion_pcdService implements Serializable{

	private String limit;

	@Autowired
	private Restriccion_pcdDao Restriccion_pcdDao;

	public void crear(Restriccion_pcd Restriccion_pcd) {
		try {
			if (consultar(Restriccion_pcd) != null) {
				throw new HealthmanagerException(
						"Restriccion_pcd ya se encuentra en la base de datos");
			}
			Restriccion_pcdDao.crear(Restriccion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Restriccion_pcd Restriccion_pcd) {
		try {
			return Restriccion_pcdDao.actualizar(Restriccion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Restriccion_pcd consultar(Restriccion_pcd Restriccion_pcd) {
		try {
			return Restriccion_pcdDao.consultar(Restriccion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Restriccion_pcd Restriccion_pcd) {
		try {
			return Restriccion_pcdDao.eliminar(Restriccion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Restriccion_pcd> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return Restriccion_pcdDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
