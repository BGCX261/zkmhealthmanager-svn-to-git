/*
 * Control_ficha_inicio_lepraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Control_ficha_inicio_lepra;
import healthmanager.modelo.dao.Control_ficha_inicio_lepraDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("control_ficha_inicio_lepraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Control_ficha_inicio_lepraService implements Serializable{

	@Autowired
	private Control_ficha_inicio_lepraDao control_ficha_inicio_lepraDao;
	private String limit;

	public void crear(Control_ficha_inicio_lepra control_ficha_inicio_lepra) {
		try {
			if (consultar(control_ficha_inicio_lepra) != null) {
				throw new HealthmanagerException(
						"Control_ficha_inicio_lepra ya se encuentra en la base de datos");
			}
			control_ficha_inicio_lepraDao.crear(control_ficha_inicio_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Control_ficha_inicio_lepra control_ficha_inicio_lepra) {
		try {
			return control_ficha_inicio_lepraDao
					.actualizar(control_ficha_inicio_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Control_ficha_inicio_lepra consultar(
			Control_ficha_inicio_lepra control_ficha_inicio_lepra) {
		try {
			return control_ficha_inicio_lepraDao
					.consultar(control_ficha_inicio_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Control_ficha_inicio_lepra control_ficha_inicio_lepra) {
		try {
			return control_ficha_inicio_lepraDao
					.eliminar(control_ficha_inicio_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Control_ficha_inicio_lepra> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return control_ficha_inicio_lepraDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listarAnio(Map<String, Object> parameters) {
		try {
			return control_ficha_inicio_lepraDao.listarAnio(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Map<String, Object> consultarDiaMes(Map<String, Object> parameters) {
		try {
			return control_ficha_inicio_lepraDao.consultarDiaMes(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return control_ficha_inicio_lepraDao.existe(parameters);
	}

}