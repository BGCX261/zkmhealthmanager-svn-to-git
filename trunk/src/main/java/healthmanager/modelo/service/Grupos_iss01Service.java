/*
 * Grupos_iss01ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Grupos_iss01;
import healthmanager.modelo.dao.Grupos_iss01Dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("grupos_iss01Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Grupos_iss01Service implements Serializable{

	private String limit;

	@Autowired
	private Grupos_iss01Dao grupos_iss01Dao;

	public void crear(Grupos_iss01 grupos_iss01) {
		try {
			if (consultar(grupos_iss01) != null) {
				throw new HealthmanagerException(
						"Grupos_iss01 ya se encuentra en la base de datos");
			}
			grupos_iss01Dao.crear(grupos_iss01);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Grupos_iss01 grupos_iss01) {
		try {
			return grupos_iss01Dao.actualizar(grupos_iss01);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Grupos_iss01 consultar(Grupos_iss01 grupos_iss01) {
		try {
			return grupos_iss01Dao.consultar(grupos_iss01);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Grupos_iss01 grupos_iss01) {
		try {
			return grupos_iss01Dao.eliminar(grupos_iss01);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Grupos_iss01> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return grupos_iss01Dao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.grupos_iss01Dao.existe(parameters);
	}

}
