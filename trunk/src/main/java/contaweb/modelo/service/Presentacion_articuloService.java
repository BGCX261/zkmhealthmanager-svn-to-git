/*
 * Presentacion_articuloServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
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

import contaweb.modelo.bean.Presentacion_articulo;
import contaweb.modelo.dao.Presentacion_articuloDao;

@Service("presentacion_articuloService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Presentacion_articuloService implements Serializable{

	@Autowired
	private Presentacion_articuloDao presentacion_articuloDao;
	private String limit;

	public void crear(Presentacion_articulo presentacion_articulo) {
		try {
			if (consultar(presentacion_articulo) != null) {
				throw new HealthmanagerException(
						"Presentacion_articulo ya se encuentra en la base de datos");
			}
			presentacion_articuloDao.crear(presentacion_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Presentacion_articulo presentacion_articulo) {
		try {
			return presentacion_articuloDao.actualizar(presentacion_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Presentacion_articulo consultar(
			Presentacion_articulo presentacion_articulo) {
		try {
			return presentacion_articuloDao.consultar(presentacion_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Presentacion_articulo presentacion_articulo) {
		try {
			return presentacion_articuloDao.eliminar(presentacion_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Presentacion_articulo> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return presentacion_articuloDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return presentacion_articuloDao.existe(parameters);
	}

}