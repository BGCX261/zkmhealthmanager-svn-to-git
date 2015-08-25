/*
 * Ficha_epidemiologia_historialServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.dao.Ficha_epidemiologia_historialDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("ficha_epidemiologia_historialService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Ficha_epidemiologia_historialService implements Serializable{

	@Autowired
	private Ficha_epidemiologia_historialDao ficha_epidemiologia_historialDao;
	private String limit;

	public void crear(
			Ficha_epidemiologia_historial ficha_epidemiologia_historial) {
		try {
			if (consultar(ficha_epidemiologia_historial) != null) {
				throw new HealthmanagerException(
						"Ficha_epidemiologia_historial ya se encuentra en la base de datos");
			}
			// log.info("prueba -- ");
			ficha_epidemiologia_historialDao
					.crear(ficha_epidemiologia_historial);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Ficha_epidemiologia_historial ficha_epidemiologia_historial) {
		try {
			return ficha_epidemiologia_historialDao
					.actualizar(ficha_epidemiologia_historial);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Ficha_epidemiologia_historial consultar(
			Ficha_epidemiologia_historial ficha_epidemiologia_historial) {
		try {
			return ficha_epidemiologia_historialDao
					.consultar(ficha_epidemiologia_historial);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Ficha_epidemiologia_historial ficha_epidemiologia_historial) {
		try {
			return ficha_epidemiologia_historialDao
					.eliminar(ficha_epidemiologia_historial);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Ficha_epidemiologia_historial> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return ficha_epidemiologia_historialDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return ficha_epidemiologia_historialDao.existe(parameters);
	}

}