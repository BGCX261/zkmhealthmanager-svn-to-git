/*
 * PucServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Puc;
import contaweb.modelo.dao.PucDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("pucService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PucService implements Serializable{

	private String limit;

	@Autowired
	private PucDao pucDao;

	public void crear(Puc puc) {
		try {
			if (consultar(puc) != null) {
				throw new HealthmanagerException(
						"Puc ya se encuentra en la base de datos");
			}
			pucDao.crear(puc);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Puc puc) {
		try {
			return pucDao.actualizar(puc);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Puc consultar(Puc puc) {
		try {
			return pucDao.consultar(puc);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Puc puc) {
		try {
			return pucDao.eliminar(puc);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Puc> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return pucDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
