/*
 * Detalle_inventarioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Detalle_inventario;
import contaweb.modelo.dao.Detalle_inventarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_inventarioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_inventarioService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_inventarioDao detalle_inventarioDao;

	public void crear(Detalle_inventario detalle_inventario) {
		try {
			if (consultar(detalle_inventario) != null) {
				throw new HealthmanagerException(
						"Detalle_inventario ya se encuentra en la base de datos");
			}
			detalle_inventarioDao.crear(detalle_inventario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_inventario detalle_inventario) {
		try {
			return detalle_inventarioDao.actualizar(detalle_inventario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_inventario consultar(Detalle_inventario detalle_inventario) {
		try {
			return detalle_inventarioDao.consultar(detalle_inventario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_inventario detalle_inventario) {
		try {
			return detalle_inventarioDao.eliminar(detalle_inventario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_inventario> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_inventarioDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
