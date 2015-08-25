/*
 * BodegaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Bodega;
import contaweb.modelo.dao.BodegaDao;

@Service("bodegaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class BodegaService implements Serializable{

	private String limit;

	@Autowired
	private BodegaDao bodegaDao;

	public void crear(Bodega bodega) {
		try {
			if (consultar(bodega) != null) {
				throw new HealthmanagerException(
						"Bodega ya se encuentra en la base de datos");
			}
			bodegaDao.crear(bodega);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Bodega bodega) {
		try {
			return bodegaDao.actualizar(bodega);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Bodega consultar(Bodega bodega) {
		try {
			return bodegaDao.consultar(bodega);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Bodega bodega) {
		try {
			return bodegaDao.eliminar(bodega);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Bodega> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return bodegaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.bodegaDao.existe(parameters);
	}

}
