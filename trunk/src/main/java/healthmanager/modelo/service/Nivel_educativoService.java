/*
 * Nivel_educativoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.dao.Nivel_educativoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("nivel_educativoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Nivel_educativoService implements Serializable{

	private String limit;

	@Autowired
	private Nivel_educativoDao nivel_educativoDao;

	public void crear(Nivel_educativo nivel_educativo) {
		try {
			if (consultar(nivel_educativo) != null) {
				throw new HealthmanagerException(
						"Nivel_educativo ya se encuentra en la base de datos");
			}
			nivel_educativoDao.crear(nivel_educativo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Nivel_educativo nivel_educativo) {
		try {
			return nivel_educativoDao.actualizar(nivel_educativo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nivel_educativo consultar(Nivel_educativo nivel_educativo) {
		try {
			return nivel_educativoDao.consultar(nivel_educativo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Nivel_educativo nivel_educativo) {
		try {
			return nivel_educativoDao.eliminar(nivel_educativo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Nivel_educativo> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return nivel_educativoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
