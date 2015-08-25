/*
 * Enfermera_signosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.dao.Enfermera_signosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("enfermera_signosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Enfermera_signosService implements Serializable{

	@Autowired
	private Enfermera_signosDao enfermera_signosDao;
	private String limit;

	public void guardarDatos(Enfermera_signos enfermera_signos) {
		try {
			if (enfermera_signosDao.consultar(enfermera_signos) != null) {
				enfermera_signosDao.actualizar(enfermera_signos);
			} else {
				enfermera_signosDao.crear(enfermera_signos);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Enfermera_signos enfermera_signos) {
		try {
			if (consultar(enfermera_signos) != null) {
				throw new HealthmanagerException(
						"Enfermera_signos ya se encuentra en la base de datos");
			}
			enfermera_signosDao.crear(enfermera_signos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Enfermera_signos enfermera_signos) {
		try {
			return enfermera_signosDao.actualizar(enfermera_signos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Enfermera_signos consultar(Enfermera_signos enfermera_signos) {
		try {
			return enfermera_signosDao.consultar(enfermera_signos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Enfermera_signos enfermera_signos) {
		try {
			return enfermera_signosDao.eliminar(enfermera_signos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Enfermera_signos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return enfermera_signosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return enfermera_signosDao.existe(parameters);
	}

}