/*
 * Control_convivientes_seguimientoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Control_convivientes_seguimiento;
import healthmanager.modelo.dao.Control_convivientes_seguimientoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("control_convivientes_seguimientoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Control_convivientes_seguimientoService implements Serializable{

	@Autowired
	private Control_convivientes_seguimientoDao control_convivientes_seguimientoDao;
	private String limit;

	public void crear(
			Control_convivientes_seguimiento control_convivientes_seguimiento) {
		try {
			if (consultar(control_convivientes_seguimiento) != null) {
				throw new HealthmanagerException(
						"Control_convivientes_seguimiento ya se encuentra en la base de datos");
			}
			control_convivientes_seguimientoDao
					.crear(control_convivientes_seguimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Control_convivientes_seguimiento control_convivientes_seguimiento) {
		try {
			return control_convivientes_seguimientoDao
					.actualizar(control_convivientes_seguimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Control_convivientes_seguimiento consultar(
			Control_convivientes_seguimiento control_convivientes_seguimiento) {
		try {
			return control_convivientes_seguimientoDao
					.consultar(control_convivientes_seguimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Control_convivientes_seguimiento control_convivientes_seguimiento) {
		try {
			return control_convivientes_seguimientoDao
					.eliminar(control_convivientes_seguimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Control_convivientes_seguimiento> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return control_convivientes_seguimientoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return control_convivientes_seguimientoDao.existe(parameters);
	}

}