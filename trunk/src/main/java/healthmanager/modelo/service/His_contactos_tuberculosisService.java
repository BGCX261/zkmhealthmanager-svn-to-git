/*
 * His_contactos_tuberculosisServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_contactos_tuberculosis;
import healthmanager.modelo.dao.His_contactos_tuberculosisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_contactos_tuberculosisService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_contactos_tuberculosisService implements Serializable{

	@Autowired
	private His_contactos_tuberculosisDao his_contactos_tuberculosisDao;
	private String limit;

	public void crear(His_contactos_tuberculosis his_contactos_tuberculosis) {
		try {
			if (consultar(his_contactos_tuberculosis) != null) {
				throw new HealthmanagerException(
						"His_contactos_tuberculosis ya se encuentra en la base de datos");
			}
			his_contactos_tuberculosisDao.crear(his_contactos_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(His_contactos_tuberculosis his_contactos_tuberculosis) {
		try {
			return his_contactos_tuberculosisDao
					.actualizar(his_contactos_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_contactos_tuberculosis consultar(
			His_contactos_tuberculosis his_contactos_tuberculosis) {
		try {
			return his_contactos_tuberculosisDao
					.consultar(his_contactos_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Map<String, Object> parameters) {
		try {
			return his_contactos_tuberculosisDao.eliminar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_contactos_tuberculosis> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return his_contactos_tuberculosisDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return his_contactos_tuberculosisDao.existe(parameters);
	}

}