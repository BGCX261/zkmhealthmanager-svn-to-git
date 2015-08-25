/*
 * Tipo_cuentaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Tipo_cuenta;
import contaweb.modelo.dao.Tipo_cuentaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("tipo_cuentaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Tipo_cuentaService implements Serializable{

	private String limit;

	@Autowired
	private Tipo_cuentaDao tipo_cuentaDao;

	public void crear(Tipo_cuenta tipo_cuenta) {
		try {
			if (consultar(tipo_cuenta) != null) {
				throw new HealthmanagerException(
						"Tipo_cuenta ya se encuentra en la base de datos");
			}
			tipo_cuentaDao.crear(tipo_cuenta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Tipo_cuenta tipo_cuenta) {
		try {
			return tipo_cuentaDao.actualizar(tipo_cuenta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Tipo_cuenta consultar(Tipo_cuenta tipo_cuenta) {
		try {
			return tipo_cuentaDao.consultar(tipo_cuenta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Tipo_cuenta tipo_cuenta) {
		try {
			return tipo_cuentaDao.eliminar(tipo_cuenta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Tipo_cuenta> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return tipo_cuentaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.tipo_cuentaDao.existe(parameters);
	}

}
