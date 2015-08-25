/*
 * Especificaciones_aportesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Especificaciones_aportes;
import healthmanager.modelo.dao.Especificaciones_aportesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("especificaciones_aportesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Especificaciones_aportesService implements Serializable{

	private String limit;

	@Autowired
	private Especificaciones_aportesDao especificaciones_aportesDao;

	public void crear(Especificaciones_aportes especificaciones_aportes) {
		try {
			if (consultar(especificaciones_aportes) != null) {
				throw new HealthmanagerException(
						"Especificaciones_aportes ya se encuentra en la base de datos");
			}
			especificaciones_aportesDao.crear(especificaciones_aportes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Especificaciones_aportes especificaciones_aportes) {
		try {
			return especificaciones_aportesDao
					.actualizar(especificaciones_aportes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Especificaciones_aportes consultar(
			Especificaciones_aportes especificaciones_aportes) {
		try {
			return especificaciones_aportesDao
					.consultar(especificaciones_aportes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Especificaciones_aportes especificaciones_aportes) {
		try {
			return especificaciones_aportesDao
					.eliminar(especificaciones_aportes);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Especificaciones_aportes> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return especificaciones_aportesDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
