/*
 * ContabilizacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Contabilizacion;
import contaweb.modelo.dao.ContabilizacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("contabilizacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ContabilizacionService implements Serializable{

	private String limit;

	@Autowired
	private ContabilizacionDao contabilizacionDao;

	public void crear(Contabilizacion contabilizacion) {
		try {
			if (consultar(contabilizacion) != null) {
				throw new HealthmanagerException(
						"Contabilizacion ya se encuentra en la base de datos");
			}
			contabilizacionDao.crear(contabilizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Contabilizacion contabilizacion) {
		try {
			return contabilizacionDao.actualizar(contabilizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Contabilizacion consultar(Contabilizacion contabilizacion) {
		try {
			return contabilizacionDao.consultar(contabilizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Contabilizacion contabilizacion) {
		try {
			return contabilizacionDao.eliminar(contabilizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Contabilizacion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return contabilizacionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Contabilizacion contabilizacion) {
		return this.contabilizacionDao.existe(contabilizacion);
	}

}
