/*
 * Detalle_orden2ServiceImpl.java
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
import healthmanager.modelo.bean.Detalle_orden2;
import healthmanager.modelo.dao.Detalle_orden2Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_orden2Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_orden2Service implements Serializable{

	private String limit;

	@Autowired
	private Detalle_orden2Dao detalle_orden2Dao;

	public void crear(Detalle_orden2 detalle_orden2) {
		try {
			if (consultar(detalle_orden2) != null) {
				throw new HealthmanagerException(
						"Detalle_orden2 ya se encuentra en la base de datos");
			}
			detalle_orden2Dao.crear(detalle_orden2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_orden2 detalle_orden2) {
		try {
			return detalle_orden2Dao.actualizar(detalle_orden2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_orden2 consultar(Detalle_orden2 detalle_orden2) {
		try {
			return detalle_orden2Dao.consultar(detalle_orden2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_orden2 detalle_orden2) {
		try {
			return detalle_orden2Dao.eliminar(detalle_orden2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_orden2> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_orden2Dao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.detalle_orden2Dao.existe(parameters);
	}

}
