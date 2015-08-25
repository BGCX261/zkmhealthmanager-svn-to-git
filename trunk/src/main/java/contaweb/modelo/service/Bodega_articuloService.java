/*
 * Bodega_articuloServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.dao.Bodega_articuloDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("bodega_articuloService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Bodega_articuloService implements Serializable{
	@Autowired
	private Bodega_articuloDao bodega_articuloDao;

	public void crear(Bodega_articulo bodega_articulo) {
		try {
			if (consultar(bodega_articulo) != null) {
				throw new HealthmanagerException(
						"Bodega_articulo ya se encuentra en la base de datos");
			}
			bodega_articuloDao.crear(bodega_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Bodega_articulo bodega_articulo) {
		try {
			return bodega_articuloDao.actualizar(bodega_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Bodega_articulo consultar(Bodega_articulo bodega_articulo) {
		try {
			return bodega_articuloDao.consultar(bodega_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Bodega_articulo bodega_articulo) {
		try {
			return bodega_articuloDao.eliminar(bodega_articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Bodega_articulo> listar(Map<String, Object> parameter) {
		try {
			return bodega_articuloDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


}
