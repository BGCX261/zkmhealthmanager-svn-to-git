/*
 * Esquema_vacunacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.dao.Esquema_vacunacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("esquema_vacunacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Esquema_vacunacionService implements Serializable{

	@Autowired
	private Esquema_vacunacionDao esquema_vacunacionDao;

	public void crear(Esquema_vacunacion esquema_vacunacion) {
		try {
			if (consultar(esquema_vacunacion) != null) {
				throw new HealthmanagerException(
						"Esquema_vacunacion ya se encuentra en la base de datos");
			}
			esquema_vacunacionDao.crear(esquema_vacunacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Esquema_vacunacion esquema_vacunacion) {
		try {
			return esquema_vacunacionDao.actualizar(esquema_vacunacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Esquema_vacunacion consultar(Esquema_vacunacion esquema_vacunacion) {
		try {
			return esquema_vacunacionDao.consultar(esquema_vacunacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Esquema_vacunacion esquema_vacunacion) {
		try {
			return esquema_vacunacionDao.eliminar(esquema_vacunacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Esquema_vacunacion> listar(Map<String, Object> parameters) {
		try {
			return esquema_vacunacionDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return esquema_vacunacionDao.existe(parameters);
	}

}