/*
 * Horarios_nuevoServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Horarios_nuevo;
import healthmanager.modelo.dao.Horarios_nuevoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("horarios_nuevoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Horarios_nuevoService implements Serializable{

	@Autowired
	private Horarios_nuevoDao horarios_nuevoDao;
	private String limit;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String accion = (String) mapa_datos.get("accion");
			Horarios_nuevo horarios_nuevo = (Horarios_nuevo) mapa_datos
					.get("horarios_nuevo");
			if (accion.equalsIgnoreCase("registrar")) {
				horarios_nuevoDao.crear(horarios_nuevo);
			} else {
				horarios_nuevoDao.actualizar(horarios_nuevo);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Horarios_nuevo horarios_nuevo) {
		try {
			if (consultar(horarios_nuevo) != null) {
				throw new HealthmanagerException(
						"Horarios_nuevo ya se encuentra en la base de datos");
			}
			horarios_nuevoDao.crear(horarios_nuevo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Horarios_nuevo horarios_nuevo) {
		try {
			return horarios_nuevoDao.actualizar(horarios_nuevo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Horarios_nuevo consultar(Horarios_nuevo horarios_nuevo) {
		try {
			return horarios_nuevoDao.consultar(horarios_nuevo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Horarios_nuevo horarios_nuevo) {
		try {
			return horarios_nuevoDao.eliminar(horarios_nuevo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Horarios_nuevo> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return horarios_nuevoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return horarios_nuevoDao.existe(parameters);
	}

}