/*
 * Vacunas_pacientesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Vacunas_pacientes;
import healthmanager.modelo.dao.Vacunas_pacientesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("vacunas_pacientesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Vacunas_pacientesService implements Serializable{

	@Autowired
	private Vacunas_pacientesDao vacunas_pacientesDao;
	public void crear(Vacunas_pacientes vacunas_pacientes) {
		try {
			if (consultar(vacunas_pacientes) != null) {
				throw new HealthmanagerException(
						"Vacunas_pacientes ya se encuentra en la base de datos");
			}
			vacunas_pacientesDao.crear(vacunas_pacientes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Vacunas_pacientes vacunas_pacientes) {
		try {
			return vacunas_pacientesDao.actualizar(vacunas_pacientes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Vacunas_pacientes consultar(Vacunas_pacientes vacunas_pacientes) {
		try {
			return vacunas_pacientesDao.consultar(vacunas_pacientes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Vacunas_pacientes vacunas_pacientes) {
		try {
			return vacunas_pacientesDao.eliminar(vacunas_pacientes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Vacunas_pacientes> listar(Map<String, Object> parameters) {
		try {
			return vacunas_pacientesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return vacunas_pacientesDao.existe(parameters);
	}

	public Integer consultarTotalVacunasPaciente(
			Map<String, Object> totalVacunas) {
		return vacunas_pacientesDao.consultarTotalVacunasPaciente(totalVacunas);
	}

}