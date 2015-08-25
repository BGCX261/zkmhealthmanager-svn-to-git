/*
 * Estancias_soatServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Estancias_soat;
import healthmanager.modelo.dao.Estancias_soatDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("estancias_soatService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Estancias_soatService implements Serializable{

	private String limit;

	@Autowired
	private Estancias_soatDao estancias_soatDao;

	public void actualizarEstancias(Map<String, Object> mapa_guardar) {
		try {
			List<Object> listado_datos = (List<Object>) mapa_guardar
					.get("listado_datos");
			List<Object> listado_eliminar = (List<Object>) mapa_guardar
					.get("listado_eliminar");
			for (Object objeto : listado_eliminar) {
				Estancias_soat estancias_soat = (Estancias_soat) objeto;
				eliminar(estancias_soat);
			}

			for (Object objeto : listado_datos) {
				Estancias_soat estancias_soat = (Estancias_soat) objeto;
				if (consultar(estancias_soat) != null) {
					actualizar(estancias_soat);
				} else {
					estancias_soatDao.crear(estancias_soat);
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Estancias_soat estancias_soat) {
		try {
			if (consultar(estancias_soat) != null) {
				throw new HealthmanagerException(
						"Estancias_soat ya se encuentra en la base de datos");
			}
			estancias_soatDao.crear(estancias_soat);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Estancias_soat estancias_soat) {
		try {
			return estancias_soatDao.actualizar(estancias_soat);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Estancias_soat consultar(Estancias_soat estancias_soat) {
		try {
			return estancias_soatDao.consultar(estancias_soat);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Estancias_soat consultarEstancia(Estancias_soat estancias_soat) {
		try {
			return estancias_soatDao.consultarEstancia(estancias_soat);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Estancias_soat estancias_soat) {
		try {
			return estancias_soatDao.eliminar(estancias_soat);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Estancias_soat> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return estancias_soatDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.estancias_soatDao.existe(parameters);
	}

}
