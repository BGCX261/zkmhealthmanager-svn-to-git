/*
 * DienteServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Diente;
import healthmanager.modelo.dao.DienteDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("dienteService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class DienteService implements Serializable{

	@Autowired
	private DienteDao dienteDao;

	public void crear(Diente diente) {
		try {
			if (consultar(diente) != null) {
				throw new HealthmanagerException(
						"Diente ya se encuentra en la base de datos");
			}
			dienteDao.crear(diente);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Diente diente) {
		try {
			return dienteDao.actualizar(diente);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Diente consultar(Diente diente) {
		try {
			return dienteDao.consultar(diente);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Diente diente) {
		try {
			return dienteDao.eliminar(diente);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Diente> listar(Map<String, Object> parameter) {
		try {
			return dienteDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.dienteDao.existe(parameters);
	}

}
