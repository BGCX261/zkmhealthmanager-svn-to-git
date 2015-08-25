/*
 * Servicios_disponiblesServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.dao.Servicios_disponiblesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("servicios_disponiblesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Servicios_disponiblesService implements Serializable{

	@Autowired
	private Servicios_disponiblesDao servicios_disponiblesDao;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String accion = (String) mapa_datos.get("accion");
			Servicios_disponibles servicios_disponibles = (Servicios_disponibles) mapa_datos
					.get("servicios_disponibles");
			if (accion.equalsIgnoreCase("registrar")) {
				servicios_disponiblesDao.crear(servicios_disponibles);
			} else {
				servicios_disponiblesDao.actualizar(servicios_disponibles);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Servicios_disponibles servicios_disponibles) {
		try {
			if (consultar(servicios_disponibles) != null) {
				throw new HealthmanagerException(
						"Servicios_disponibles ya se encuentra en la base de datos");
			}
			servicios_disponiblesDao.crear(servicios_disponibles);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Servicios_disponibles servicios_disponibles) {
		try {
			return servicios_disponiblesDao.actualizar(servicios_disponibles);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Servicios_disponibles consultar(
			Servicios_disponibles servicios_disponibles) {
		try {
			return servicios_disponiblesDao.consultar(servicios_disponibles);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Servicios_disponibles servicios_disponibles) {
		try {
			return servicios_disponiblesDao.eliminar(servicios_disponibles);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Servicios_disponibles> listar(Map<String, Object> parameters) {
		try {
			return servicios_disponiblesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<String> listar_codigos(Map<String, Object> parametros) {
		try {
			return servicios_disponiblesDao.listar_codigos(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Long cantidad_hijos(Servicios_disponibles servicios_disponibles) {
		try {
			return servicios_disponiblesDao
					.cantidad_hijos(servicios_disponibles);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return servicios_disponiblesDao.existe(parameters);
	}

}