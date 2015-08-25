/*
 * Detalle_revision_comiteServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_revision_comite;
import healthmanager.modelo.dao.Detalle_revision_comiteDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_revision_comiteService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_revision_comiteService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_revision_comiteDao detalle_revision_comiteDao;

	public void crear(Detalle_revision_comite detalle_revision_comite) {
		try {
			if (consultar(detalle_revision_comite) != null) {
				throw new HealthmanagerException(
						"Detalle_revision_comite ya se encuentra en la base de datos");
			}
			detalle_revision_comiteDao.crear(detalle_revision_comite);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_revision_comite detalle_revision_comite) {
		try {
			return detalle_revision_comiteDao
					.actualizar(detalle_revision_comite);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_revision_comite consultar(
			Detalle_revision_comite detalle_revision_comite) {
		try {
			return detalle_revision_comiteDao
					.consultar(detalle_revision_comite);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_revision_comite detalle_revision_comite) {
		try {
			return detalle_revision_comiteDao.eliminar(detalle_revision_comite);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_revision_comite> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_revision_comiteDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
