/*
 * Modulo_firmas_reportesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Modulo_firmas_reportes;
import healthmanager.modelo.dao.Modulo_firmas_reportesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("modulo_firmas_reportesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Modulo_firmas_reportesService implements Serializable{

	@Autowired
	private Modulo_firmas_reportesDao modulo_firmas_reportesDao;
	private String limit;

	public void crear(Modulo_firmas_reportes modulo_firmas_reportes) {
		try {
			if (consultar(modulo_firmas_reportes) != null) {
				throw new HealthmanagerException(
						"Modulo_firmas_reportes ya se encuentra en la base de datos");
			}
			modulo_firmas_reportesDao.crear(modulo_firmas_reportes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Modulo_firmas_reportes modulo_firmas_reportes) {
		try {
			return modulo_firmas_reportesDao.actualizar(modulo_firmas_reportes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Modulo_firmas_reportes consultar(
			Modulo_firmas_reportes modulo_firmas_reportes) {
		try {
			return modulo_firmas_reportesDao.consultar(modulo_firmas_reportes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Modulo_firmas_reportes modulo_firmas_reportes) {
		try {
			return modulo_firmas_reportesDao.eliminar(modulo_firmas_reportes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Modulo_firmas_reportes> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return modulo_firmas_reportesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return modulo_firmas_reportesDao.existe(parameters);
	}

	public Modulo_firmas_reportes consultarID(
			Modulo_firmas_reportes modulo_firmas_reportes) {
		return modulo_firmas_reportesDao.consultarID(modulo_firmas_reportes);
	}
}