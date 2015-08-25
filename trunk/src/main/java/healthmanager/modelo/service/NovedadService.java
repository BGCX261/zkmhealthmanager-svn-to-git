/*
 * NovedadServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Novedad;
import healthmanager.modelo.dao.NovedadDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("novedadService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class NovedadService implements Serializable{

	private String limit;

	@Autowired
	private NovedadDao novedadDao;

	public void crear(Novedad novedad) {
		try {
			if (consultar(novedad) != null) {
				throw new HealthmanagerException(
						"Novedad ya se encuentra en la base de datos");
			}
			novedadDao.crear(novedad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Novedad novedad) {
		try {
			return novedadDao.actualizar(novedad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Novedad consultar(Novedad novedad) {
		try {
			return novedadDao.consultar(novedad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Novedad novedad) {
		try {
			return novedadDao.eliminar(novedad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Novedad> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return novedadDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
