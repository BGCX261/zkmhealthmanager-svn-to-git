/*
 * Frecuencia_ordenamientoServiceImpl.java
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
import healthmanager.modelo.bean.Frecuencia_ordenamiento;
import healthmanager.modelo.dao.Frecuencia_ordenamientoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("frecuencia_ordenamientoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Frecuencia_ordenamientoService implements Serializable{

	private String limit;

	@Autowired
	private Frecuencia_ordenamientoDao frecuencia_ordenamientoDao;

	public void crear(Frecuencia_ordenamiento frecuencia_ordenamiento) {
		try {
			if (consultar(frecuencia_ordenamiento) != null) {
				throw new HealthmanagerException(
						"Frecuencia_ordenamiento ya se encuentra en la base de datos");
			}
			frecuencia_ordenamientoDao.crear(frecuencia_ordenamiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Frecuencia_ordenamiento frecuencia_ordenamiento) {
		try {
			return frecuencia_ordenamientoDao
					.actualizar(frecuencia_ordenamiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Frecuencia_ordenamiento consultar(
			Frecuencia_ordenamiento frecuencia_ordenamiento) {
		try {
			return frecuencia_ordenamientoDao
					.consultar(frecuencia_ordenamiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Frecuencia_ordenamiento frecuencia_ordenamiento) {
		try {
			return frecuencia_ordenamientoDao.eliminar(frecuencia_ordenamiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Frecuencia_ordenamiento> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return frecuencia_ordenamientoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.frecuencia_ordenamientoDao.existe(parameters);
	}

	public Frecuencia_ordenamiento consultarActual(
			Frecuencia_ordenamiento frecuencia_ordenamiento) {
		try {
			return frecuencia_ordenamientoDao
					.consultarActual(frecuencia_ordenamiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
