/*
 * MunicipiosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.dao.MunicipiosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("municipiosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class MunicipiosService implements Serializable{

	private String limit;

	@Autowired
	private MunicipiosDao municipiosDao;

	public void crear(Municipios municipios) {
		try {
			if (consultar(municipios) != null) {
				throw new HealthmanagerException(
						"Municipios ya se encuentra en la base de datos");
			}
			municipiosDao.crear(municipios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Municipios municipios) {
		try {
			return municipiosDao.actualizar(municipios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Municipios consultar(Municipios municipios) {
		try {
			return municipiosDao.consultar(municipios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Municipios municipios) {
		try {
			return municipiosDao.eliminar(municipios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Municipios> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return municipiosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
