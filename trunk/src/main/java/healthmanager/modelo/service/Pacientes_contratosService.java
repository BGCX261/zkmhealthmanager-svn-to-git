/*
 * Pacientes_contratosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.dao.Pacientes_contratosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("pacientes_contratosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Pacientes_contratosService implements Serializable{

	@Autowired
	private Pacientes_contratosDao pacientes_contratosDao;

	public Pacientes_contratosDao getPacientes_contratosDao() {
		return pacientes_contratosDao;
	}

	public void crear(Pacientes_contratos pacientes_contratos) {
		try {
			if (consultar(pacientes_contratos) != null) {
				throw new HealthmanagerException(
						"Pacientes_contratos ya se encuentra en la base de datos");
			}
			pacientes_contratosDao.crear(pacientes_contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Pacientes_contratos pacientes_contratos) {
		try {
			return pacientes_contratosDao.actualizar(pacientes_contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Pacientes_contratos consultar(Pacientes_contratos pacientes_contratos) {
		try {
			return pacientes_contratosDao.consultar(pacientes_contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Pacientes_contratos pacientes_contratos) {
		try {
			return pacientes_contratosDao.eliminar(pacientes_contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Pacientes_contratos> listar(Map<String, Object> parameters) {
		try {
			return pacientes_contratosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public double getPoblacionReal(Map<String, Object> params) {
		Double poblacion_real = pacientes_contratosDao.getPoblacionReal(params);
		return poblacion_real != null ? poblacion_real : 0;
	}

	public boolean existe(Map<String, Object> parameters) {
		return pacientes_contratosDao.existe(parameters);
	}

	public int eliminar_contratos_varios(Map<String, Object> mapa_contratos) {
		try {
			return pacientes_contratosDao
					.eliminar_contratos_varios(mapa_contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
}