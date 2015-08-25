/*
 * Vacunas_fuera_esquema_vacunacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Vacunas_fuera_esquema_vacunacion;
import healthmanager.modelo.dao.Vacunas_fuera_esquema_vacunacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("vacunas_fuera_esquema_vacunacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Vacunas_fuera_esquema_vacunacionService implements Serializable{

	@Autowired
	private Vacunas_fuera_esquema_vacunacionDao vacunas_fuera_esquema_vacunacionDao;
	public void crear(
			Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion) {
		try {
			if (consultar(vacunas_fuera_esquema_vacunacion) != null) {
				throw new HealthmanagerException(
						"Vacunas_fuera_esquema_vacunacion ya se encuentra en la base de datos");
			}
			vacunas_fuera_esquema_vacunacionDao
					.crear(vacunas_fuera_esquema_vacunacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion) {
		try {
			return vacunas_fuera_esquema_vacunacionDao
					.actualizar(vacunas_fuera_esquema_vacunacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Vacunas_fuera_esquema_vacunacion consultar(
			Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion) {
		try {
			return vacunas_fuera_esquema_vacunacionDao
					.consultar(vacunas_fuera_esquema_vacunacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion) {
		try {
			return vacunas_fuera_esquema_vacunacionDao
					.eliminar(vacunas_fuera_esquema_vacunacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Vacunas_fuera_esquema_vacunacion> listar(
			Map<String, Object> parameters) {
		try {
			return vacunas_fuera_esquema_vacunacionDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return vacunas_fuera_esquema_vacunacionDao.existe(parameters);
	}

}