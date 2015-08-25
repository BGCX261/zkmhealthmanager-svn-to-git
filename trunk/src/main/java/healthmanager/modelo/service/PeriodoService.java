/*
 * PeriodoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Periodo;
import healthmanager.modelo.dao.PeriodoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("periodoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PeriodoService implements Serializable{

	@Autowired
	private PeriodoDao periodoDao;
	private String limit;

	public void crear(Periodo periodo) {
		try {
			if (consultar(periodo) != null) {
				throw new HealthmanagerException(
						"Periodo ya se encuentra en la base de datos");
			}
			periodoDao.crear(periodo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Periodo periodo) {
		try {
			return periodoDao.actualizar(periodo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Periodo consultar(Periodo periodo) {
		try {
			return periodoDao.consultar(periodo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Periodo periodo) {
		try {
			return periodoDao.eliminar(periodo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Periodo> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return periodoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return periodoDao.existe(parameters);
	}

}