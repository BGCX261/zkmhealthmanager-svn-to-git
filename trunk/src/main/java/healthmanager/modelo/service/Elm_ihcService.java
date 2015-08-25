package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elm_ihc;
import healthmanager.modelo.dao.Elm_ihcDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("elm_ihcService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Elm_ihcService implements Serializable{

	private String limit;

	@Autowired
	private Elm_ihcDao elm_ihcDao;

	public void crear(Elm_ihc elm_ihc) {
		try {
			if (consultar(elm_ihc) != null) {
				throw new HealthmanagerException(
						"Elm_ihc ya se encuentra en la base de datos");
			}
			elm_ihcDao.crear(elm_ihc);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Elm_ihc elm_ihc) {
		try {
			return elm_ihcDao.actualizar(elm_ihc);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Elm_ihc consultar(Elm_ihc elm_ihc) {
		try {
			return elm_ihcDao.consultar(elm_ihc);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Elm_ihc elm_ihc) {
		try {
			return elm_ihcDao.eliminar(elm_ihc);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Elm_ihc> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return elm_ihcDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.elm_ihcDao.existe(parameters);
	}

}
