/*
 * DepartamentosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.dao.DepartamentosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("departamentosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class DepartamentosService implements Serializable{

	private String limit;

	@Autowired
	private DepartamentosDao departamentosDao;

	public void crear(Departamentos departamentos) {
		try {
			if (consultar(departamentos) != null) {
				throw new HealthmanagerException(
						"Departamentos ya se encuentra en la base de datos");
			}
			departamentosDao.crear(departamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Departamentos departamentos) {
		try {
			return departamentosDao.actualizar(departamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Departamentos consultar(Departamentos departamentos) {
		try {
			return departamentosDao.consultar(departamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Departamentos departamentos) {
		try {
			return departamentosDao.eliminar(departamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Departamentos> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return departamentosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
