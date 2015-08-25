/*
 * Sala_esperaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Sala_espera;
import healthmanager.modelo.dao.Sala_esperaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("sala_esperaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Sala_esperaService implements Serializable{

	@Autowired
	private Sala_esperaDao sala_esperaDao;
	private String limit;

	public void crear(Sala_espera sala_espera) {
		try {
			if (consultar(sala_espera) != null) {
				throw new HealthmanagerException(
						"Sala_espera ya se encuentra en la base de datos");
			}
			sala_esperaDao.crear(sala_espera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Sala_espera sala_espera) {
		try {
			return sala_esperaDao.actualizar(sala_espera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Sala_espera consultar(Sala_espera sala_espera) {
		try {
			return sala_esperaDao.consultar(sala_espera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Sala_espera sala_espera) {
		try {
			return sala_esperaDao.eliminar(sala_espera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Sala_espera> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return sala_esperaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return sala_esperaDao.existe(parameters);
	}

}