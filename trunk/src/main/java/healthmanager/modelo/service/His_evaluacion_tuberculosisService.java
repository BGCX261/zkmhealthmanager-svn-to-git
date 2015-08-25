/*
 * His_evaluacion_tuberculosisServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_evaluacion_tuberculosis;
import healthmanager.modelo.dao.His_evaluacion_tuberculosisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_evaluacion_tuberculosisService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_evaluacion_tuberculosisService implements Serializable{

	@Autowired
	private His_evaluacion_tuberculosisDao his_evaluacion_tuberculosisDao;
	private String limit;

	public void crear(His_evaluacion_tuberculosis his_evaluacion_tuberculosis) {
		try {
			if (consultar(his_evaluacion_tuberculosis) != null) {
				throw new HealthmanagerException(
						"His_evaluacion_tuberculosis ya se encuentra en la base de datos");
			}
			his_evaluacion_tuberculosisDao.crear(his_evaluacion_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			His_evaluacion_tuberculosis his_evaluacion_tuberculosis) {
		try {
			return his_evaluacion_tuberculosisDao
					.actualizar(his_evaluacion_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_evaluacion_tuberculosis consultar(
			His_evaluacion_tuberculosis his_evaluacion_tuberculosis) {
		try {
			return his_evaluacion_tuberculosisDao
					.consultar(his_evaluacion_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Map<String, Object> parameters) {
		try {
			return his_evaluacion_tuberculosisDao.eliminar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_evaluacion_tuberculosis> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return his_evaluacion_tuberculosisDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return his_evaluacion_tuberculosisDao.existe(parameters);
	}

}