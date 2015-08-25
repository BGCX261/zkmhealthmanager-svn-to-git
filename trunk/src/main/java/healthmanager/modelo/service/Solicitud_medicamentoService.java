/*
 * Solicitud_medicamentoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Solicitud_medicamento;
import healthmanager.modelo.dao.Solicitud_medicamentoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("solicitud_medicamentoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Solicitud_medicamentoService implements Serializable{

	private String limit;

	@Autowired
	private Solicitud_medicamentoDao solicitud_medicamentoDao;

	public void crear(Solicitud_medicamento solicitud_medicamento) {
		try {
			if (consultar(solicitud_medicamento) != null) {
				throw new HealthmanagerException(
						"Solicitud_medicamento ya se encuentra en la base de datos");
			}
			solicitud_medicamentoDao.crear(solicitud_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Solicitud_medicamento solicitud_medicamento) {
		try {
			return solicitud_medicamentoDao.actualizar(solicitud_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Solicitud_medicamento consultar(
			Solicitud_medicamento solicitud_medicamento) {
		try {
			return solicitud_medicamentoDao.consultar(solicitud_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Solicitud_medicamento solicitud_medicamento) {
		try {
			return solicitud_medicamentoDao.eliminar(solicitud_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Solicitud_medicamento> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return solicitud_medicamentoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.solicitud_medicamentoDao.existe(parameters);
	}

}
