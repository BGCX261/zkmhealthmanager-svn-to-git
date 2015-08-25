/*
 * NegacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Negacion;
import healthmanager.modelo.dao.NegacionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("negacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class NegacionService implements Serializable{

	private String limit;

	@Autowired
	private NegacionDao negacionDao;

	public void crear(Negacion negacion) {
		try {
			if (consultar(negacion) != null) {
				throw new HealthmanagerException(
						"Negacion ya se encuentra en la base de datos");
			}
			negacionDao.crear(negacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Negacion negacion) {
		try {
			return negacionDao.actualizar(negacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Negacion consultar(Negacion negacion) {
		try {
			return negacionDao.consultar(negacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Negacion negacion) {
		try {
			return negacionDao.eliminar(negacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Negacion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return negacionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
