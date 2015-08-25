/*
 * Solicitud_e1ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Solicitud_e1;
import healthmanager.modelo.dao.Solicitud_e1Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("solicitud_e1Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Solicitud_e1Service implements Serializable{

	private String limit;

	@Autowired
	private Solicitud_e1Dao solicitud_e1Dao;

	public void crear(Solicitud_e1 solicitud_e1) {
		try {
			if (consultar(solicitud_e1) != null) {
				throw new HealthmanagerException(
						"Esta Solicitud ya se encuentra en la base de datos");
			}
			solicitud_e1Dao.crear(solicitud_e1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Solicitud_e1 solicitud_e1) {
		try {
			return solicitud_e1Dao.actualizar(solicitud_e1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Solicitud_e1 consultar(Solicitud_e1 solicitud_e1) {
		try {
			return solicitud_e1Dao.consultar(solicitud_e1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Solicitud_e1 solicitud_e1) {
		try {
			return solicitud_e1Dao.eliminar(solicitud_e1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Solicitud_e1> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return solicitud_e1Dao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map> listarMap(Map param) {
		param.put("limit", limit);
		return solicitud_e1Dao.listarMap(param);
	}

}
