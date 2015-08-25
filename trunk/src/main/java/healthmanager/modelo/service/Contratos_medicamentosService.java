/*
 * Contratos_medicamentosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Contratos_medicamentos;
import healthmanager.modelo.dao.Contratos_medicamentosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("contratos_medicamentosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Contratos_medicamentosService implements Serializable{

	@Autowired
	private Contratos_medicamentosDao contratos_medicamentosDao;
	private String limit;

	public void crear(Contratos_medicamentos contratos_medicamentos) {
		try {
			if (consultar(contratos_medicamentos) != null) {
				throw new HealthmanagerException(
						"Contratos_medicamentos ya se encuentra en la base de datos");
			}
			contratos_medicamentosDao.crear(contratos_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Contratos_medicamentos contratos_medicamentos) {
		try {
			return contratos_medicamentosDao.actualizar(contratos_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Contratos_medicamentos consultar(
			Contratos_medicamentos contratos_medicamentos) {
		try {
			return contratos_medicamentosDao.consultar(contratos_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Contratos_medicamentos contratos_medicamentos) {
		try {
			return contratos_medicamentosDao.eliminar(contratos_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Contratos_medicamentos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return contratos_medicamentosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return contratos_medicamentosDao.existe(parameters);
	}

}