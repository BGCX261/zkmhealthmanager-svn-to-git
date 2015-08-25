/*
 * Encuesta_esperaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Encuesta_espera;
import healthmanager.modelo.dao.Encuesta_esperaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("encuesta_esperaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Encuesta_esperaService implements Serializable{

	@Autowired
	private Encuesta_esperaDao encuesta_esperaDao;
	private String limit;

	public void crear(Encuesta_espera encuesta_espera) {
		try {
			if (consultar(encuesta_espera) != null) {
				throw new HealthmanagerException(
						"Encuesta_espera ya se encuentra en la base de datos");
			}
			encuesta_esperaDao.crear(encuesta_espera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Encuesta_espera encuesta_espera) {
		try {
			return encuesta_esperaDao.actualizar(encuesta_espera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Encuesta_espera consultar(Encuesta_espera encuesta_espera) {
		try {
			return encuesta_esperaDao.consultar(encuesta_espera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Encuesta_espera encuesta_espera) {
		try {
			return encuesta_esperaDao.eliminar(encuesta_espera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Encuesta_espera> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return encuesta_esperaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return encuesta_esperaDao.existe(parameters);
	}

}