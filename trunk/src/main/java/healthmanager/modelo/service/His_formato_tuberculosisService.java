/*
 * His_formato_tuberculosisServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_formato_tuberculosis;
import healthmanager.modelo.dao.His_formato_tuberculosisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_formato_tuberculosisService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_formato_tuberculosisService implements Serializable{

	@Autowired
	private His_formato_tuberculosisDao his_formato_tuberculosisDao;
	private String limit;

	public void crear(His_formato_tuberculosis his_formato_tuberculosis) {
		try {
			if (consultar(his_formato_tuberculosis) != null) {
				throw new HealthmanagerException(
						"His_formato_tuberculosis ya se encuentra en la base de datos");
			}
			his_formato_tuberculosisDao.crear(his_formato_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(His_formato_tuberculosis his_formato_tuberculosis) {
		try {
			return his_formato_tuberculosisDao
					.actualizar(his_formato_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_formato_tuberculosis consultar(
			His_formato_tuberculosis his_formato_tuberculosis) {
		try {
			return his_formato_tuberculosisDao
					.consultar(his_formato_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(His_formato_tuberculosis his_formato_tuberculosis) {
		try {
			return his_formato_tuberculosisDao
					.eliminar(his_formato_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_formato_tuberculosis> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return his_formato_tuberculosisDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return his_formato_tuberculosisDao.existe(parameters);
	}

}