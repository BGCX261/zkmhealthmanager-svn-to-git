/*
 * Epicrisis_eseServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Epicrisis_ese;
import healthmanager.modelo.dao.Epicrisis_eseDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("epicrisis_eseService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Epicrisis_eseService implements Serializable{

	@Autowired
	private Epicrisis_eseDao epicrisis_eseDao;
	private String limit;

	public void crear(Epicrisis_ese epicrisis_ese) {
		try {
			if (consultar(epicrisis_ese) != null) {
				throw new HealthmanagerException(
						"Epicrisis_ese ya se encuentra en la base de datos");
			}
			epicrisis_eseDao.crear(epicrisis_ese);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Epicrisis_ese epicrisis_ese) {
		try {
			return epicrisis_eseDao.actualizar(epicrisis_ese);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Epicrisis_ese consultar(Epicrisis_ese epicrisis_ese) {
		try {
			return epicrisis_eseDao.consultar(epicrisis_ese);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Epicrisis_ese epicrisis_ese) {
		try {
			return epicrisis_eseDao.eliminar(epicrisis_ese);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Epicrisis_ese> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return epicrisis_eseDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return epicrisis_eseDao.existe(parameters);
	}

}