/*
 * Datos_servicioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.dao.Datos_servicioDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("datos_servicioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Datos_servicioService implements Serializable{

	private String limit;

	@Autowired
	private Datos_servicioDao datos_servicioDao;

	public void crear(Datos_servicio datos_servicio) {
		try {
			if (consultar(datos_servicio) != null) {
				throw new HealthmanagerException(
						"Datos_servicio ya se encuentra en la base de datos");
			}
			datos_servicioDao.crear(datos_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Datos_servicio datos_servicio) {
		try {
			return datos_servicioDao.actualizar(datos_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Datos_servicio consultar(Datos_servicio datos_servicio) {
		try {
			return datos_servicioDao.consultar(datos_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Datos_servicio datos_servicio) {
		try {
			return datos_servicioDao.eliminar(datos_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Datos_servicio> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return datos_servicioDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.datos_servicioDao.existe(parameters);
	}

	public List<Map<String, Object>> servicios_hoja_gastos(
			Map<String, Object> param) {
		try {
			param.put("limit", limit);
			return datos_servicioDao.servicios_hoja_gastos(param);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Map<String, Object> getFechaRealizacion(Map<String, Object> parametro) {
		return datos_servicioDao.getFechaRealizacion(parametro);
	}

}
