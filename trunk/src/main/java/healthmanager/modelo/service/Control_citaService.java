/*
 * Control_citaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Control_cita;
import healthmanager.modelo.dao.Control_citaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("control_citaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Control_citaService implements Serializable{

	private String limit;

	@Autowired
	private Control_citaDao control_citaDao;

	public void crear(Control_cita control_cita) {
		try {
			if (consultar(control_cita) != null) {
				throw new HealthmanagerException(
						"Control_cita ya se encuentra en la base de datos");
			}
			control_citaDao.crear(control_cita);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Control_cita control_cita) {
		try {
			return control_citaDao.actualizar(control_cita);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Control_cita consultar(Control_cita control_cita) {
		try {
			return control_citaDao.consultar(control_cita);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Control_cita control_cita) {
		try {
			return control_citaDao.eliminar(control_cita);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Control_cita> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return control_citaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
