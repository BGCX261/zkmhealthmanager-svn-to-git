/*
 * Manuales_tarifariosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.dao.Manuales_tarifariosDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("manuales_tarifariosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Manuales_tarifariosService implements Serializable{

	@Autowired
	private Manuales_tarifariosDao manuales_tarifariosDao;

	public void crear(Manuales_tarifarios manuales_tarifarios) {
		try {
			if (consultar(manuales_tarifarios) != null) {
				throw new HealthmanagerException(
						"Manuales_tarifarios ya se encuentra en la base de datos");
			}
			manuales_tarifariosDao.crear(manuales_tarifarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Manuales_tarifarios manuales_tarifarios) {
		try {
			return manuales_tarifariosDao.actualizar(manuales_tarifarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Manuales_tarifarios consultar(Manuales_tarifarios manuales_tarifarios) {
		try {
			return manuales_tarifariosDao.consultar(manuales_tarifarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Manuales_tarifarios consultarDesdeServicio(
			Servicios_contratados servicios_contratados) {
		return manuales_tarifariosDao
				.consultarDesdeServicio(servicios_contratados);
	}

	// public Manuales_tarifarios consultarParametrizado(Map<String, Object>
	// map){
	// return manuales_tarifariosDao.consultarParametrizado(map);
	// }

	public Manuales_tarifarios consultar_parametrizado(Map<String, Object> map) {
		return manuales_tarifariosDao.consultar_parametrizado(map);
	}

	public List<Manuales_tarifarios> listar_parametrizado(
			Map<String, Object> map) {
		return manuales_tarifariosDao.listar_parametrizado(map);
	}

	public int eliminar(Manuales_tarifarios manuales_tarifarios) {
		try {
			return manuales_tarifariosDao.eliminar(manuales_tarifarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Manuales_tarifarios> listar(Map<String, Object> parameters) {
		try {
			return manuales_tarifariosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return manuales_tarifariosDao.existe(parameters);
	}

}