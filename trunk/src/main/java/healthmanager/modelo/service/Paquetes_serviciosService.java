/*
 * Paquetes_serviciosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.dao.Detalles_paquetes_serviciosDao;
import healthmanager.modelo.dao.Paquetes_serviciosDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("paquetes_serviciosService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Paquetes_serviciosService implements Serializable {

	@Autowired
	private Paquetes_serviciosDao paquetes_serviciosDao;
	private String limit;

	@Autowired
	private Detalles_paquetes_serviciosDao detalles_paquetes_serviciosDao;

	@SuppressWarnings("unchecked")
	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			Paquetes_servicios paquetes_servicios = (Paquetes_servicios) mapa_datos
					.get("paquetes_servicios");
			String accion = (String) mapa_datos.get("accion");
			List<Detalles_paquetes_servicios> listado_detalles = (List<Detalles_paquetes_servicios>) mapa_datos
					.get("listado_detalles");

			if (accion.equalsIgnoreCase("registrar")) {
				paquetes_serviciosDao.crear(paquetes_servicios);
			} else {
				paquetes_serviciosDao.actualizar(paquetes_servicios);
			}

			Detalles_paquetes_servicios detalles_paquetes_servicios_aux = new Detalles_paquetes_servicios();
			detalles_paquetes_servicios_aux.setId_paquete(paquetes_servicios
					.getId());

			detalles_paquetes_serviciosDao
					.eliminar(detalles_paquetes_servicios_aux);

			for (int i = 0; i < listado_detalles.size(); i++) {
				Detalles_paquetes_servicios detalles_paquetes_servicios = listado_detalles
						.get(i);
				detalles_paquetes_servicios.setId_paquete(paquetes_servicios.getId());
				detalles_paquetes_serviciosDao
						.crear(detalles_paquetes_servicios);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Paquetes_servicios paquetes_servicios) {
		try {
			if (consultar(paquetes_servicios) != null) {
				throw new HealthmanagerException(
						"Paquetes_servicios ya se encuentra en la base de datos");
			}
			paquetes_serviciosDao.crear(paquetes_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Paquetes_servicios paquetes_servicios) {
		try {
			return paquetes_serviciosDao.actualizar(paquetes_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Paquetes_servicios consultar(Paquetes_servicios paquetes_servicios) {
		try {
			return paquetes_serviciosDao.consultar(paquetes_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Paquetes_servicios paquetes_servicios) {
		try {
			return paquetes_serviciosDao.eliminar(paquetes_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Paquetes_servicios> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return paquetes_serviciosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return paquetes_serviciosDao.existe(parameters);
	}

}