/*
 * Detalle_autorizacion_pcdServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_autorizacion_pcd;
import healthmanager.modelo.dao.Detalle_autorizacion_pcdDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_autorizacion_pcdService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_autorizacion_pcdService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_autorizacion_pcdDao detalle_autorizacion_pcdDao;

	public void crear(Detalle_autorizacion_pcd detalle_autorizacion_pcd) {
		try {
			if (consultar(detalle_autorizacion_pcd) != null) {
				throw new HealthmanagerException(
						"Detalle_autorizacion_pcd ya se encuentra en la base de datos");
			}
			detalle_autorizacion_pcdDao.crear(detalle_autorizacion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_autorizacion_pcd detalle_autorizacion_pcd) {
		try {
			return detalle_autorizacion_pcdDao
					.actualizar(detalle_autorizacion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_autorizacion_pcd consultar(
			Detalle_autorizacion_pcd detalle_autorizacion_pcd) {
		try {
			return detalle_autorizacion_pcdDao
					.consultar(detalle_autorizacion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean consultarPcd(Map<String, Object> parameters) {
		try {
			return detalle_autorizacion_pcdDao.consultarPcd(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_autorizacion_pcd detalle_autorizacion_pcd) {
		try {
			return detalle_autorizacion_pcdDao
					.eliminar(detalle_autorizacion_pcd);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_autorizacion_pcd> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_autorizacion_pcdDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
