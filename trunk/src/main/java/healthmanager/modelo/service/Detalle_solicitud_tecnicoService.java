/*
 * Detalle_solicitud_tecnicoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_solicitud_tecnico;
import healthmanager.modelo.dao.Detalle_solicitud_tecnicoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_solicitud_tecnicoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_solicitud_tecnicoService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_solicitud_tecnicoDao detalle_solicitud_tecnicoDao;

	public void crear(Detalle_solicitud_tecnico detalle_solicitud_tecnico) {
		try {
			if (consultar(detalle_solicitud_tecnico) != null) {
				throw new HealthmanagerException(
						"Detalle_solicitud_tecnico ya se encuentra en la base de datos");
			}
			detalle_solicitud_tecnicoDao.crear(detalle_solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_solicitud_tecnico detalle_solicitud_tecnico) {
		try {
			return detalle_solicitud_tecnicoDao
					.actualizar(detalle_solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_solicitud_tecnico consultar(
			Detalle_solicitud_tecnico detalle_solicitud_tecnico) {
		try {
			return detalle_solicitud_tecnicoDao
					.consultar(detalle_solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_solicitud_tecnico detalle_solicitud_tecnico) {
		try {
			return detalle_solicitud_tecnicoDao
					.eliminar(detalle_solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_solicitud_tecnico> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_solicitud_tecnicoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
