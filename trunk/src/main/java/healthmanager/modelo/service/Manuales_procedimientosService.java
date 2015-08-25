/*
 * Manuales_procedimientosServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Manuales_procedimientos;
import healthmanager.modelo.dao.Manuales_procedimientosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("manuales_procedimientosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Manuales_procedimientosService implements Serializable{

	@Autowired
	private Manuales_procedimientosDao manuales_procedimientosDao;
	private String limit;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String accion = (String) mapa_datos.get("accion");
			Manuales_procedimientos manuales_procedimientos = (Manuales_procedimientos) mapa_datos
					.get("manuales_procedimientos");
			if (accion.equalsIgnoreCase("registrar")) {
				manuales_procedimientosDao.crear(manuales_procedimientos);
			} else {
				manuales_procedimientosDao.actualizar(manuales_procedimientos);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Manuales_procedimientos manuales_procedimientos) {
		try {
			if (consultar(manuales_procedimientos) != null) {
				throw new HealthmanagerException(
						"Manuales_procedimientos ya se encuentra en la base de datos");
			}
			manuales_procedimientosDao.crear(manuales_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Manuales_procedimientos manuales_procedimientos) {
		try {
			return manuales_procedimientosDao
					.actualizar(manuales_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Manuales_procedimientos consultar(
			Manuales_procedimientos manuales_procedimientos) {
		try {
			return manuales_procedimientosDao
					.consultar(manuales_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Manuales_procedimientos manuales_procedimientos) {
		try {
			return manuales_procedimientosDao.eliminar(manuales_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Manuales_procedimientos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return manuales_procedimientosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return manuales_procedimientosDao.existe(parameters);
	}

}