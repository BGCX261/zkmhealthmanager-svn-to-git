/*
 * Tipo_procedimientoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Tipo_procedimiento;
import healthmanager.modelo.dao.Tipo_procedimientoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("tipo_procedimientoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Tipo_procedimientoService implements Serializable{

	private String limit;

	@Autowired
	private Tipo_procedimientoDao tipo_procedimientoDao;

	public void crear(Tipo_procedimiento tipo_procedimiento) {
		try {
			if (consultar(tipo_procedimiento) != null) {
				throw new HealthmanagerException(
						"Tipo_procedimiento ya se encuentra en la base de datos");
			}
			tipo_procedimientoDao.crear(tipo_procedimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Tipo_procedimiento tipo_procedimiento) {
		try {
			return tipo_procedimientoDao.actualizar(tipo_procedimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Tipo_procedimiento consultar(Tipo_procedimiento tipo_procedimiento) {
		try {
			return tipo_procedimientoDao.consultar(tipo_procedimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Tipo_procedimiento tipo_procedimiento) {
		try {
			return tipo_procedimientoDao.eliminar(tipo_procedimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Tipo_procedimiento> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return tipo_procedimientoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Tipo_procedimiento tipo_procedimiento) {
		return this.tipo_procedimientoDao.existe(tipo_procedimiento);
	}

}
