/*
 * Tarifas_procedimientosServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Tarifas_procedimientos;
import healthmanager.modelo.dao.Tarifas_procedimientosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("tarifas_procedimientosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Tarifas_procedimientosService implements Serializable{

	@Autowired
	private Tarifas_procedimientosDao tarifas_procedimientosDao;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String accion = (String) mapa_datos.get("accion");
			Tarifas_procedimientos tarifas_procedimientos = (Tarifas_procedimientos) mapa_datos
					.get("tarifas_procedimientos");
			if (accion.equalsIgnoreCase("registrar")) {
				tarifas_procedimientosDao.crear(tarifas_procedimientos);
			} else {
				tarifas_procedimientosDao.actualizar(tarifas_procedimientos);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Tarifas_procedimientos tarifas_procedimientos) {
		try {
			if (consultar(tarifas_procedimientos) != null) {
				throw new HealthmanagerException(
						"Tarifas_procedimientos ya se encuentra en la base de datos");
			}
			tarifas_procedimientosDao.crear(tarifas_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Tarifas_procedimientos tarifas_procedimientos) {
		try {
			return tarifas_procedimientosDao.actualizar(tarifas_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Tarifas_procedimientos consultar(
			Tarifas_procedimientos tarifas_procedimientos) {
		try {
			return tarifas_procedimientosDao.consultar(tarifas_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Tarifas_procedimientos tarifas_procedimientos) {
		try {
			return tarifas_procedimientosDao.eliminar(tarifas_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Tarifas_procedimientos> listar(Map<String, Object> parameters) {
		try {
			return tarifas_procedimientosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return tarifas_procedimientosDao.existe(parameters);
	}

}