/*
 * Programas_actividades_pypServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Programas_actividades_pyp;
import healthmanager.modelo.dao.Programas_actividades_pypDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("programas_actividades_pypService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Programas_actividades_pypService implements Serializable{

	@Autowired
	private Programas_actividades_pypDao programas_actividades_pypDao;
	private String limit;

	public void crear(Programas_actividades_pyp programas_actividades_pyp) {
		try {
			if (consultar(programas_actividades_pyp) != null) {
				throw new HealthmanagerException(
						"Programas_actividades_pyp ya se encuentra en la base de datos");
			}
			programas_actividades_pypDao.crear(programas_actividades_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Programas_actividades_pyp programas_actividades_pyp) {
		try {
			return programas_actividades_pypDao
					.actualizar(programas_actividades_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Programas_actividades_pyp consultar(
			Programas_actividades_pyp programas_actividades_pyp) {
		try {
			return programas_actividades_pypDao
					.consultar(programas_actividades_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Programas_actividades_pyp programas_actividades_pyp) {
		try {
			return programas_actividades_pypDao
					.eliminar(programas_actividades_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Programas_actividades_pyp> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return programas_actividades_pypDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return programas_actividades_pypDao.existe(parameters);
	}

}