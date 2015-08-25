/*
 * Estancias_issServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Estancias_iss;
import healthmanager.modelo.dao.Estancias_issDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("estancias_issService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Estancias_issService implements Serializable{

	private String limit;

	@Autowired
	private Estancias_issDao estancias_issDao;

	public void actualizarEstancias(Map<String, Object> mapa_guardar) {
		List<Object> listado_datos = (List<Object>) mapa_guardar
				.get("listado_datos");
		List<Object> listado_eliminar = (List<Object>) mapa_guardar
				.get("listado_eliminar");
		try {

			for (Object objeto : listado_eliminar) {
				Estancias_iss estancias_iss = (Estancias_iss) objeto;
				eliminar(estancias_iss);
			}

			for (Object objeto : listado_datos) {
				Estancias_iss estancias_iss = (Estancias_iss) objeto;
				if (consultar(estancias_iss) != null) {
					actualizar(estancias_iss);
				} else {
					estancias_issDao.crear(estancias_iss);
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Estancias_iss estancias_iss) {
		try {
			if (consultar(estancias_iss) != null) {
				throw new HealthmanagerException(
						"Estancias_iss ya se encuentra en la base de datos");
			}
			estancias_issDao.crear(estancias_iss);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Estancias_iss estancias_iss) {
		try {
			return estancias_issDao.actualizar(estancias_iss);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Estancias_iss consultar(Estancias_iss estancias_iss) {
		try {
			return estancias_issDao.consultar(estancias_iss);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Estancias_iss consultarEstancia(Estancias_iss estancias_iss) {
		try {
			return estancias_issDao.consultarEstancia(estancias_iss);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Estancias_iss estancias_iss) {
		try {
			return estancias_issDao.eliminar(estancias_iss);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Estancias_iss> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return estancias_issDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.estancias_issDao.existe(parameters);
	}

}
