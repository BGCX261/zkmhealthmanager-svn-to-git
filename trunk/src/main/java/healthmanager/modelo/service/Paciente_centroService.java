/*
 * Paciente_centroServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Paciente_centro;
import healthmanager.modelo.dao.Paciente_centroDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("paciente_centroService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Paciente_centroService implements Serializable{

	private String limit;

	@Autowired
	private Paciente_centroDao paciente_centroDao;

	public void crear(Paciente_centro paciente_centro) {
		try {
			if (consultar(paciente_centro) != null) {
				throw new HealthmanagerException(
						"Paciente_centro ya se encuentra en la base de datos");
			}
			paciente_centroDao.crear(paciente_centro);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Paciente_centro paciente_centro) {
		try {
			return paciente_centroDao.actualizar(paciente_centro);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Paciente_centro consultar(Paciente_centro paciente_centro) {
		try {
			return paciente_centroDao.consultar(paciente_centro);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Paciente_centro paciente_centro) {
		try {
			return paciente_centroDao.eliminar(paciente_centro);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Paciente_centro> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return paciente_centroDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.paciente_centroDao.existe(parameters);
	}

}
