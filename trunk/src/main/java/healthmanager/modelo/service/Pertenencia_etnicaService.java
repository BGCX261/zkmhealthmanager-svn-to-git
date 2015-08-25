/*
 * Pertenencia_etnicaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.dao.Pertenencia_etnicaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("pertenencia_etnicaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Pertenencia_etnicaService implements Serializable{

	private String limit;

	@Autowired
	private Pertenencia_etnicaDao pertenencia_etnicaDao;

	public void crear(Pertenencia_etnica pertenencia_etnica) {
		try {
			if (consultar(pertenencia_etnica) != null) {
				throw new HealthmanagerException(
						"Pertenencia_etnica ya se encuentra en la base de datos");
			}
			pertenencia_etnicaDao.crear(pertenencia_etnica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Pertenencia_etnica pertenencia_etnica) {
		try {
			return pertenencia_etnicaDao.actualizar(pertenencia_etnica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Pertenencia_etnica consultar(Pertenencia_etnica pertenencia_etnica) {
		try {
			return pertenencia_etnicaDao.consultar(pertenencia_etnica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Pertenencia_etnica pertenencia_etnica) {
		try {
			return pertenencia_etnicaDao.eliminar(pertenencia_etnica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Pertenencia_etnica> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return pertenencia_etnicaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
