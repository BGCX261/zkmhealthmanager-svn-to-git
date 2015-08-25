/*
 * Laboratorios_respuestasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Laboratorios_respuestas;
import healthmanager.modelo.dao.Laboratorios_respuestasDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("laboratorios_respuestasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Laboratorios_respuestasService implements Serializable{

	@Autowired
	private Laboratorios_respuestasDao laboratorios_respuestasDao;
	private String limit;

	public void crear(Laboratorios_respuestas laboratorios_respuestas) {
		try {
			if (consultar(laboratorios_respuestas) != null) {
				throw new HealthmanagerException(
						"Laboratorios_respuestas ya se encuentra en la base de datos");
			}
			laboratorios_respuestasDao.crear(laboratorios_respuestas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Laboratorios_respuestas laboratorios_respuestas) {
		try {
			return laboratorios_respuestasDao
					.actualizar(laboratorios_respuestas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Laboratorios_respuestas consultar(
			Laboratorios_respuestas laboratorios_respuestas) {
		try {
			return laboratorios_respuestasDao
					.consultar(laboratorios_respuestas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Laboratorios_respuestas laboratorios_respuestas) {
		try {
			return laboratorios_respuestasDao.eliminar(laboratorios_respuestas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Laboratorios_respuestas> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return laboratorios_respuestasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return laboratorios_respuestasDao.existe(parameters);
	}

}