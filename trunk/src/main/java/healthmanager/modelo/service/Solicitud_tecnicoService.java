/*
 * Solicitud_tecnicoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Solicitud_tecnico;
import healthmanager.modelo.dao.Solicitud_tecnicoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("solicitud_tecnicoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Solicitud_tecnicoService implements Serializable{

	private String limit;

	@Autowired
	private Solicitud_tecnicoDao solicitud_tecnicoDao;

	public void crear(Solicitud_tecnico solicitud_tecnico) {
		try {
			if (consultar(solicitud_tecnico) != null) {
				throw new HealthmanagerException(
						"Solicitud_tecnico ya se encuentra en la base de datos");
			}
			solicitud_tecnicoDao.crear(solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Solicitud_tecnico solicitud_tecnico) {
		try {
			return solicitud_tecnicoDao.actualizar(solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Solicitud_tecnico consultar(Solicitud_tecnico solicitud_tecnico) {
		try {
			return solicitud_tecnicoDao.consultar(solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Solicitud_tecnico solicitud_tecnico) {
		try {
			return solicitud_tecnicoDao.eliminar(solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Solicitud_tecnico> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return solicitud_tecnicoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setSolicitud_tecnicoDao(
			Solicitud_tecnicoDao solicitud_tecnicoDao) {
		this.solicitud_tecnicoDao = solicitud_tecnicoDao;
	}

	public Solicitud_tecnicoDao getSolicitud_tecnicoDao() {
		return this.solicitud_tecnicoDao;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public Solicitud_tecnico consultarP(Solicitud_tecnico solicitud_tecnico) {
		try {
			return solicitud_tecnicoDao.consultarP(solicitud_tecnico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map param) {
		
		return this.solicitud_tecnicoDao.existe(param);
	}

}
