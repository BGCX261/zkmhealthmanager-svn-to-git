/*
 * Grupo2ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Grupo2;
import contaweb.modelo.dao.Grupo2Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("grupo2Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Grupo2Service implements Serializable{

	private String limit;

	@Autowired
	private Grupo2Dao grupo2Dao;

	public void crear(Grupo2 grupo2) {
		try {
			if (consultar(grupo2) != null) {
				throw new HealthmanagerException(
						"Grupo2 ya se encuentra en la base de datos");
			}
			grupo2Dao.crear(grupo2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Grupo2 grupo2) {
		try {
			return grupo2Dao.actualizar(grupo2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Grupo2 consultar(Grupo2 grupo2) {
		try {
			return grupo2Dao.consultar(grupo2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Grupo2 grupo2) {
		try {
			return grupo2Dao.eliminar(grupo2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Grupo2> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return grupo2Dao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
