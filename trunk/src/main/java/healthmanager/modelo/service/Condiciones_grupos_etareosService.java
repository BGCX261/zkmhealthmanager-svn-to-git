/*
 * Condiciones_grupos_etareosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Condiciones_grupos_etareos;
import healthmanager.modelo.dao.Condiciones_grupos_etareosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("condiciones_grupos_etareosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Condiciones_grupos_etareosService implements Serializable{

	@Autowired
	private Condiciones_grupos_etareosDao condiciones_grupos_etareosDao;
	private String limit;

	public void crear(Condiciones_grupos_etareos condiciones_grupos_etareos) {
		try {
			if (consultar(condiciones_grupos_etareos) != null) {
				throw new HealthmanagerException(
						"Condiciones_grupos_etareos ya se encuentra en la base de datos");
			}
			condiciones_grupos_etareosDao.crear(condiciones_grupos_etareos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Condiciones_grupos_etareos condiciones_grupos_etareos) {
		try {
			return condiciones_grupos_etareosDao
					.actualizar(condiciones_grupos_etareos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Condiciones_grupos_etareos consultar(
			Condiciones_grupos_etareos condiciones_grupos_etareos) {
		try {
			return condiciones_grupos_etareosDao
					.consultar(condiciones_grupos_etareos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Condiciones_grupos_etareos condiciones_grupos_etareos) {
		try {
			return condiciones_grupos_etareosDao
					.eliminar(condiciones_grupos_etareos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Condiciones_grupos_etareos> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return condiciones_grupos_etareosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return condiciones_grupos_etareosDao.existe(parameters);
	}

}