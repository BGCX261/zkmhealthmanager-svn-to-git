/*
 * Anexo10_entidadServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anexo10_entidad;
import healthmanager.modelo.dao.Anexo10_entidadDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("anexo10_entidadService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Anexo10_entidadService implements Serializable{

	private String limit;

	@Autowired
	private Anexo10_entidadDao anexo10_entidadDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	
	public void crear(Anexo10_entidad anexo10_entidad) {
		//log.info(">>>> crearservice");
		try {
			if (consultar(anexo10_entidad) != null) {
				throw new HealthmanagerException(
						"Anexo10_entidad ya se encuentra en la base de datos");
			}
			anexo10_entidadDao.crear(anexo10_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarDatos(Map<String, Object> datos) {
		//log.info(">>>> guardarservice");
		try {
			// Admision admision = (Admision) datos.get("admision");
			Anexo10_entidad anexo10_entidad = (Anexo10_entidad) datos
					.get("historia_clinica");
			//log.info(">>>>>mapa>>" + datos.get("datos"));
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				if (consultar(anexo10_entidad) != null) {
					throw new HealthmanagerException(
							"La historia clinica ya se encuentra en la base de datos");
				}
				anexo10_entidadDao.crear(anexo10_entidad);
			} else {
				if (consultar(anexo10_entidad) == null)
					throw new Exception(
							"El dato que desea actualizar no existe en la base de datos");
				anexo10_entidadDao.actualizar(anexo10_entidad);

			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}

	}

	public int actualizar(Anexo10_entidad anexo10_entidad) {
		try {
			return anexo10_entidadDao.actualizar(anexo10_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Anexo10_entidad consultar(Anexo10_entidad anexo10_entidad) {
		try {
			return anexo10_entidadDao.consultar(anexo10_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Anexo10_entidad anexo10_entidad) {
		try {
			return anexo10_entidadDao.eliminar(anexo10_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Anexo10_entidad> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return anexo10_entidadDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return anexo10_entidadDao.existe(parameters);
	}

}
