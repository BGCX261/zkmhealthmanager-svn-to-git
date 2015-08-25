/*
 * Lote_articuloServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Lote_articulo;
import contaweb.modelo.dao.Lote_articuloDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("lote_articuloService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Lote_articuloService implements Serializable{

	private String limit;

	@Autowired
	private Lote_articuloDao lote_articuloDao;

	public void crear(Lote_articulo lote_articulo) {
		try {
			if (consultar(lote_articulo) != null) {
				throw new HealthmanagerException(
						"Lote_articulo ya se encuentra en la base de datos");
			}
			lote_articuloDao.crear(lote_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Lote_articulo lote_articulo) {
		try {
			return lote_articuloDao.actualizar(lote_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Lote_articulo consultar(Lote_articulo lote_articulo) {
		try {
			return lote_articuloDao.consultar(lote_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Lote_articulo lote_articulo) {
		try {
			return lote_articuloDao.eliminar(lote_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Lote_articulo> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return lote_articuloDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
