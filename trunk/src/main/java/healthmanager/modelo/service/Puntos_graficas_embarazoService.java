/*
 * Puntos_graficas_embarazoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Puntos_graficas_embarazo;
import healthmanager.modelo.dao.Puntos_graficas_embarazoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("puntos_graficas_embarazoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Puntos_graficas_embarazoService implements Serializable{

	private String limit;

	@Autowired
	private Puntos_graficas_embarazoDao puntos_graficas_embarazoDao;

	public void crear(Puntos_graficas_embarazo puntos_graficas_embarazo) {
		try {
			if (consultar(puntos_graficas_embarazo) != null) {
				throw new HealthmanagerException(
						"Puntos_graficas_embarazo ya se encuentra en la base de datos");
			}
			puntos_graficas_embarazoDao.crear(puntos_graficas_embarazo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Puntos_graficas_embarazo puntos_graficas_embarazo) {
		try {
			return puntos_graficas_embarazoDao
					.actualizar(puntos_graficas_embarazo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Puntos_graficas_embarazo consultar(
			Puntos_graficas_embarazo puntos_graficas_embarazo) {
		try {
			return puntos_graficas_embarazoDao
					.consultar(puntos_graficas_embarazo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Puntos_graficas_embarazo puntos_graficas_embarazo) {
		try {
			return puntos_graficas_embarazoDao
					.eliminar(puntos_graficas_embarazo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Puntos_graficas_embarazo> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return puntos_graficas_embarazoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.puntos_graficas_embarazoDao.existe(parameters);
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}