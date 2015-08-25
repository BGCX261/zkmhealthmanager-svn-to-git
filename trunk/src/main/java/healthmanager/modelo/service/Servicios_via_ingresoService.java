/*
 * Servicios_via_ingresoServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Servicios_via_ingreso;
import healthmanager.modelo.dao.Servicios_via_ingresoDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("servicios_via_ingresoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Servicios_via_ingresoService implements Serializable{

	@Autowired
	private Servicios_via_ingresoDao servicios_via_ingresoDao;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String accion = (String) mapa_datos.get("accion");
			Servicios_via_ingreso servicios_via_ingreso = (Servicios_via_ingreso) mapa_datos
					.get("servicios_via_ingreso");
			if (accion.equalsIgnoreCase("registrar")) {
				servicios_via_ingresoDao.crear(servicios_via_ingreso);
			} else {
				servicios_via_ingresoDao.actualizar(servicios_via_ingreso);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Servicios_via_ingreso servicios_via_ingreso) {
		try {
			if (consultar(servicios_via_ingreso) != null) {
				throw new HealthmanagerException(
						"Servicios_via_ingreso ya se encuentra en la base de datos");
			}
			servicios_via_ingresoDao.crear(servicios_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Servicios_via_ingreso servicios_via_ingreso) {
		try {
			return servicios_via_ingresoDao.actualizar(servicios_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Servicios_via_ingreso consultar(
			Servicios_via_ingreso servicios_via_ingreso) {
		try {
			return servicios_via_ingresoDao.consultar(servicios_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Servicios_via_ingreso servicios_via_ingreso) {
		try {
			return servicios_via_ingresoDao.eliminar(servicios_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Servicios_via_ingreso> listar(Map<String, Object> parameters) {
		try {
			return servicios_via_ingresoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return servicios_via_ingresoDao.existe(parameters);
	}

}