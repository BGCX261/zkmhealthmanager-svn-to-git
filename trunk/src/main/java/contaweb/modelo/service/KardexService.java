/*
 * KardexServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Kardex;
import contaweb.modelo.dao.KardexDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("kardexService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class KardexService implements Serializable{

	private String limit;

	@Autowired
	private KardexDao kardexDao;

	public void crear(Kardex kardex) {
		try {
			if (consultar(kardex) != null) {
				throw new HealthmanagerException(
						"Kardex ya se encuentra en la base de datos");
			}
			kardexDao.crear(kardex);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Kardex kardex) {
		try {
			return kardexDao.actualizar(kardex);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Kardex consultar(Kardex kardex) {
		try {
			return kardexDao.consultar(kardex);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Kardex kardex) {
		try {
			return kardexDao.eliminar(kardex);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Kardex> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return kardexDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
