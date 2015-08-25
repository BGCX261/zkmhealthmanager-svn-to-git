/*
 * Configuracion_autorizacion_via_ingresoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Configuracion_autorizacion_via_ingreso;
import healthmanager.modelo.dao.Configuracion_autorizacion_via_ingresoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("configuracion_autorizacion_via_ingresoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Configuracion_autorizacion_via_ingresoService implements Serializable{

	@Autowired
	private Configuracion_autorizacion_via_ingresoDao configuracion_autorizacion_via_ingresoDao;
	private String limit;

	public void crear(
			Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso) {
		try {
			if (consultar(configuracion_autorizacion_via_ingreso) != null) {
				throw new HealthmanagerException(
						"Configuracion_autorizacion_via_ingreso ya se encuentra en la base de datos");
			}
			configuracion_autorizacion_via_ingresoDao
					.crear(configuracion_autorizacion_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso) {
		try {
			return configuracion_autorizacion_via_ingresoDao
					.actualizar(configuracion_autorizacion_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Configuracion_autorizacion_via_ingreso consultar(
			Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso) {
		try {
			return configuracion_autorizacion_via_ingresoDao
					.consultar(configuracion_autorizacion_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso) {
		try {
			return configuracion_autorizacion_via_ingresoDao
					.eliminar(configuracion_autorizacion_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Configuracion_autorizacion_via_ingreso> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return configuracion_autorizacion_via_ingresoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return configuracion_autorizacion_via_ingresoDao.existe(parameters);
	}

}