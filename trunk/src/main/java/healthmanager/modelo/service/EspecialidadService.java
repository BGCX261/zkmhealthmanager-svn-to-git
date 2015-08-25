/*
 * EspecialidadServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.dao.EspecialidadDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("especialidadService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class EspecialidadService implements Serializable{

	private String limit;

	@Autowired
	private EspecialidadDao especialidadDao;

	public void crear(Especialidad especialidad) {
		try {
			if (consultar(especialidad) != null) {
				throw new HealthmanagerException(
						"Especialidad ya se encuentra en la base de datos");
			}
			especialidadDao.crear(especialidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Especialidad especialidad) {
		try {
			return especialidadDao.actualizar(especialidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Especialidad consultar(Especialidad especialidad) {
		try {
			return especialidadDao.consultar(especialidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Especialidad especialidad) {
		try {
			return especialidadDao.eliminar(especialidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Especialidad> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return especialidadDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
