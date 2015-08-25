/*
 * HabitacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Habitacion;
import healthmanager.modelo.dao.HabitacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("habitacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class HabitacionService implements Serializable{

	@Autowired
	private HabitacionDao habitacionDao;

	public void crear(Habitacion habitacion) {
		try {
			Integer codigo = habitacionDao.nextConsecutivo(habitacion);
			habitacion.setCodigo((codigo != null ? codigo + 1 : 1));
			habitacionDao.crear(habitacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Habitacion habitacion) {
		try {
			return habitacionDao.actualizar(habitacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Habitacion consultar(Habitacion habitacion) {
		try {
			return habitacionDao.consultar(habitacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Habitacion habitacion) {
		try {
			return habitacionDao.eliminar(habitacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Habitacion> listar(Map<String, Object> parameter) {
		try {
			return habitacionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
