/*
 * Porcentajes_cirugiasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Porcentajes_cirugias;
import healthmanager.modelo.dao.Porcentajes_cirugiasDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("porcentajes_cirugiasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Porcentajes_cirugiasService implements Serializable{

	private String limit;

	@Autowired
	private Porcentajes_cirugiasDao porcentajes_cirugiasDao;

	public void crear(Porcentajes_cirugias porcentajes_cirugias) {
		try {
			if (consultar(porcentajes_cirugias) != null) {
				throw new HealthmanagerException(
						"Porcentajes_cirugias ya se encuentra en la base de datos");
			}
			porcentajes_cirugiasDao.crear(porcentajes_cirugias);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Porcentajes_cirugias porcentajes_cirugias) {
		try {
			return porcentajes_cirugiasDao.actualizar(porcentajes_cirugias);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Porcentajes_cirugias consultar(
			Porcentajes_cirugias porcentajes_cirugias) {
		try {
			return porcentajes_cirugiasDao.consultar(porcentajes_cirugias);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Porcentajes_cirugias porcentajes_cirugias) {
		try {
			return porcentajes_cirugiasDao.eliminar(porcentajes_cirugias);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Porcentajes_cirugias> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return porcentajes_cirugiasDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.porcentajes_cirugiasDao.existe(parameters);
	}

}
