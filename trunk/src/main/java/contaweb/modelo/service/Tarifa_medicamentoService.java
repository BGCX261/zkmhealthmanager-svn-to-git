/*
 * Tarifa_medicamentoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Tarifa_medicamento;
import contaweb.modelo.dao.Tarifa_medicamentoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("tarifa_medicamentoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Tarifa_medicamentoService implements Serializable{

	private String limit;

	@Autowired
	private Tarifa_medicamentoDao tarifa_medicamentoDao;

	public void crear(Tarifa_medicamento tarifa_medicamento) {
		try {
			if (consultar(tarifa_medicamento) != null) {
				throw new HealthmanagerException(
						"Tarifa_medicamento ya se encuentra en la base de datos");
			}
			tarifa_medicamentoDao.crear(tarifa_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Tarifa_medicamento tarifa_medicamento) {
		try {
			return tarifa_medicamentoDao.actualizar(tarifa_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Tarifa_medicamento consultar(Tarifa_medicamento tarifa_medicamento) {
		try {
			return tarifa_medicamentoDao.consultar(tarifa_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Tarifa_medicamento tarifa_medicamento) {
		try {
			return tarifa_medicamentoDao.eliminar(tarifa_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Tarifa_medicamento> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return tarifa_medicamentoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
