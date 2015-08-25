/*
 * Imagenes_diagnosticasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Imagenes_diagnosticas;
import healthmanager.modelo.dao.Imagenes_diagnosticasDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("imagenes_diagnosticasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Imagenes_diagnosticasService implements Serializable{

	@Autowired
	private Imagenes_diagnosticasDao imagenes_diagnosticasDao;
	private String limit;

	public void crear(Imagenes_diagnosticas imagenes_diagnosticas) {
		try {
			if (consultar(imagenes_diagnosticas) != null) {
				throw new HealthmanagerException(
						"Imagenes_diagnosticas ya se encuentra en la base de datos");
			}
			imagenes_diagnosticasDao.crear(imagenes_diagnosticas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Imagenes_diagnosticas imagenes_diagnosticas) {
		try {
			return imagenes_diagnosticasDao.actualizar(imagenes_diagnosticas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Imagenes_diagnosticas consultar(
			Imagenes_diagnosticas imagenes_diagnosticas) {
		try {
			return imagenes_diagnosticasDao.consultar(imagenes_diagnosticas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Imagenes_diagnosticas imagenes_diagnosticas) {
		try {
			return imagenes_diagnosticasDao.eliminar(imagenes_diagnosticas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Imagenes_diagnosticas> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return imagenes_diagnosticasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return imagenes_diagnosticasDao.existe(parameters);
	}

}