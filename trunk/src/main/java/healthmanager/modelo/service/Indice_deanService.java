/*
 * Indice_deanServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Indice_dean;
import healthmanager.modelo.dao.Indice_deanDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("indice_deanService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Indice_deanService implements Serializable{

	@Autowired
	private Indice_deanDao indice_deanDao;

	public void crear(Indice_dean indice_dean) {
		try {
			if (consultar(indice_dean) != null) {
				throw new HealthmanagerException(
						"Indice_dean ya se encuentra en la base de datos");
			}
			indice_deanDao.crear(indice_dean);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Indice_dean indice_dean) {
		try {
			return indice_deanDao.actualizar(indice_dean);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Indice_dean consultar(Indice_dean indice_dean) {
		try {
			return indice_deanDao.consultar(indice_dean);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Indice_dean indice_dean) {
		try {
			return indice_deanDao.eliminar(indice_dean);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Indice_dean> listar(Map<String, Object> parameter) {
		try {
			return indice_deanDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setIndice_deanDao(Indice_deanDao indice_deanDao) {
		this.indice_deanDao = indice_deanDao;
	}

	public Indice_deanDao getIndice_deanDao() {
		return this.indice_deanDao;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.indice_deanDao.existe(parameters);
	}

}
