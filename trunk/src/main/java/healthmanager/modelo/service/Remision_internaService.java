/*
 * Remision_internaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.Remision_internaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("remision_internaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Remision_internaService implements Serializable{

	@Autowired
	private Remision_internaDao remision_internaDao;

	public void crear(Remision_interna remision_interna) {
		try {
			if (consultar(remision_interna) != null) {
				throw new HealthmanagerException(
						"Remision_interna ya se encuentra en la base de datos");
			}
			remision_internaDao.crear(remision_interna);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Remision_interna remision_interna) {
		try {
			return remision_internaDao.actualizar(remision_interna);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Remision_interna consultar(Remision_interna remision_interna) {
		try {
			return remision_internaDao.consultar(remision_interna);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Remision_interna remision_interna) {
		try {
			return remision_internaDao.eliminar(remision_interna);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Remision_interna> listar(Map<String, Object> parameters) {
		try {
			return remision_internaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


	public boolean existe(Map<String, Object> parameters) {
		return remision_internaDao.existe(parameters);
	}

}