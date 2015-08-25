/*
 * Contratos_paquetesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Contratos_paquetes;
import healthmanager.modelo.dao.Contratos_paquetesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("contratos_paquetesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Contratos_paquetesService implements Serializable{

	@Autowired
	private Contratos_paquetesDao contratos_paquetesDao;
	private String limit;

	public void crear(Contratos_paquetes contratos_paquetes) {
		try {
			if (consultar(contratos_paquetes) != null) {
				throw new HealthmanagerException(
						"Contratos_paquetes ya se encuentra en la base de datos");
			}
			contratos_paquetesDao.crear(contratos_paquetes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Contratos_paquetes contratos_paquetes) {
		try {
			return contratos_paquetesDao.actualizar(contratos_paquetes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Contratos_paquetes consultar(Contratos_paquetes contratos_paquetes) {
		try {
			return contratos_paquetesDao.consultar(contratos_paquetes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Contratos_paquetes contratos_paquetes) {
		try {
			return contratos_paquetesDao.eliminar(contratos_paquetes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Contratos_paquetes> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return contratos_paquetesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return contratos_paquetesDao.existe(parameters);
	}

}