/*
 * Autorizacion_pcdServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Autorizacion_pcd;
import healthmanager.modelo.dao.Autorizacion_pcdDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("autorizacion_pcdService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Autorizacion_pcdService implements Serializable{

	private String limit;

	@Autowired
	private Autorizacion_pcdDao autorizacion_pcdDao;

	public void crear(Autorizacion_pcd autorizacion_pcd) {
		try {
			if (consultar(autorizacion_pcd) != null) {
				throw new HealthmanagerException(
						"Autorizacion_pcd ya se encuentra en la base de datos");
			}
			autorizacion_pcdDao.crear(autorizacion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Autorizacion_pcd autorizacion_pcd) {
		try {
			return autorizacion_pcdDao.actualizar(autorizacion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Autorizacion_pcd consultar(Autorizacion_pcd autorizacion_pcd) {
		try {
			return autorizacion_pcdDao.consultar(autorizacion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Autorizacion_pcd autorizacion_pcd) {
		try {
			return autorizacion_pcdDao.eliminar(autorizacion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Autorizacion_pcd> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return autorizacion_pcdDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
