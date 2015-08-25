/*
 * Unidad_medidaServiceImpl.java
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

import contaweb.modelo.bean.Unidad_medida;
import contaweb.modelo.dao.Unidad_medidaDao;

@Service("unidad_medidaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Unidad_medidaService implements Serializable{

	private String limit;

	@Autowired
	private Unidad_medidaDao unidad_medidaDao;

	public void crear(Unidad_medida unidad_medida) {
		try {
			if (consultar(unidad_medida) != null) {
				throw new HealthmanagerException(
						"Unidad_medida ya se encuentra en la base de datos");
			}
			unidad_medidaDao.crear(unidad_medida);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Unidad_medida unidad_medida) {
		try {
			return unidad_medidaDao.actualizar(unidad_medida);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Unidad_medida consultar(Unidad_medida unidad_medida) {
		try {
			return unidad_medidaDao.consultar(unidad_medida);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Unidad_medida unidad_medida) {
		try {
			return unidad_medidaDao.eliminar(unidad_medida);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Unidad_medida> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return unidad_medidaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.unidad_medidaDao.existe(parameters);
	}

}
