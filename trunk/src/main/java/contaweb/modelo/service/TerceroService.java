/*
 * TerceroServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Tercero;
import contaweb.modelo.dao.TerceroDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("terceroService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class TerceroService implements Serializable{

	private String limit;

	@Autowired
	private TerceroDao terceroDao;

	public void crear(Tercero tercero) {
		try {
			if (consultar(tercero) != null) {
				throw new HealthmanagerException(
						"Tercero ya se encuentra en la base de datos");
			}
			terceroDao.crear(tercero);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Tercero tercero) {
		try {
			return terceroDao.actualizar(tercero);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Tercero consultar(Tercero tercero) {
		try {
			return terceroDao.consultar(tercero);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Tercero tercero) {
		try {
			return terceroDao.eliminar(tercero);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Tercero> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return terceroDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
