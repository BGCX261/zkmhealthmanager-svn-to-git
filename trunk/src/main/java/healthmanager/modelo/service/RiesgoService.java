/*
 * RiesgoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Riesgo;
import healthmanager.modelo.dao.RiesgoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("riesgoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class RiesgoService implements Serializable{

	private String limit;

	@Autowired
	private RiesgoDao riesgoDao;

	public void crear(Riesgo riesgo) {
		try {
			if (consultar(riesgo) != null) {
				throw new HealthmanagerException(
						"Riesgo ya se encuentra en la base de datos");
			}
			riesgoDao.crear(riesgo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Riesgo riesgo) {
		try {
			return riesgoDao.actualizar(riesgo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Riesgo consultar(Riesgo riesgo) {
		try {
			return riesgoDao.consultar(riesgo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Riesgo riesgo) {
		try {
			return riesgoDao.eliminar(riesgo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Riesgo> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return riesgoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
