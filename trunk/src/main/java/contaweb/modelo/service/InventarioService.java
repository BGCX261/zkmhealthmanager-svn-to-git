/*
 * InventarioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Inventario;
import contaweb.modelo.dao.InventarioDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("inventarioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class InventarioService implements Serializable{

	private String limit;

	@Autowired
	private InventarioDao inventarioDao;

	public void crear(Inventario inventario) {
		try {
			if (consultar(inventario) != null) {
				throw new HealthmanagerException(
						"Inventario ya se encuentra en la base de datos");
			}
			inventarioDao.crear(inventario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Inventario inventario) {
		try {
			return inventarioDao.actualizar(inventario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Inventario consultar(Inventario inventario) {
		try {
			return inventarioDao.consultar(inventario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Inventario inventario) {
		try {
			return inventarioDao.eliminar(inventario);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Inventario> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return inventarioDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
