/*
 * Excepciones_pypServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Excepciones_pyp;
import healthmanager.modelo.dao.Excepciones_pypDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("excepciones_pypService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Excepciones_pypService implements Serializable{

	@Autowired
	private Excepciones_pypDao excepciones_pypDao;
	private String limit;

	public void crear(Excepciones_pyp excepciones_pyp) {
		try {
			if (consultar(excepciones_pyp) != null) {
				throw new HealthmanagerException(
						"Excepciones_pyp ya se encuentra en la base de datos");
			}
			excepciones_pypDao.crear(excepciones_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Excepciones_pyp excepciones_pyp) {
		try {
			return excepciones_pypDao.actualizar(excepciones_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Excepciones_pyp consultar(Excepciones_pyp excepciones_pyp) {
		try {
			return excepciones_pypDao.consultar(excepciones_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Excepciones_pyp excepciones_pyp) {
		try {
			return excepciones_pypDao.eliminar(excepciones_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Excepciones_pyp> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return excepciones_pypDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return excepciones_pypDao.existe(parameters);
	}

}