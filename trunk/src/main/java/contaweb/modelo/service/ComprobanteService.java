/*
 * ComprobanteServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Comprobante;
import contaweb.modelo.dao.ComprobanteDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("comprobanteService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ComprobanteService implements Serializable{

	private String limit;

	@Autowired
	private ComprobanteDao comprobanteDao;

	public void crear(Comprobante comprobante) {
		try {
			if (consultar(comprobante) != null) {
				throw new HealthmanagerException(
						"Comprobante ya se encuentra en la base de datos");
			}
			comprobanteDao.crear(comprobante);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Comprobante comprobante) {
		try {
			return comprobanteDao.actualizar(comprobante);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Comprobante consultar(Comprobante comprobante) {
		try {
			return comprobanteDao.consultar(comprobante);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Comprobante comprobante) {
		try {
			return comprobanteDao.eliminar(comprobante);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Comprobante> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return comprobanteDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
