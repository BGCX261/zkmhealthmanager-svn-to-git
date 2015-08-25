/*
 * Registro_admisionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Registro_admision;
import healthmanager.modelo.dao.Registro_admisionDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("registro_admisionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Registro_admisionService implements Serializable{

	@Autowired
	private Registro_admisionDao registro_admisionDao;

	public void crear(Registro_admision registro_admision) {
		try {
			if (consultar(registro_admision) != null) {
				throw new HealthmanagerException(
						"Registro_admision ya se encuentra en la base de datos");
			}
			registro_admisionDao.crear(registro_admision);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Registro_admision registro_admision) {
		try {
			return registro_admisionDao.actualizar(registro_admision);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Registro_admision consultar(Registro_admision registro_admision) {
		try {
			return registro_admisionDao.consultar(registro_admision);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Registro_admision registro_admision) {
		try {
			return registro_admisionDao.eliminar(registro_admision);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Registro_admision> listar(Map<String, Object> parameter) {
		try {
			return registro_admisionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.registro_admisionDao.existe(parameters);
	}

}
