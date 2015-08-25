/*
 * PrestadoresServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.dao.PrestadoresDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("prestadoresService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PrestadoresService implements Serializable{

	@Autowired
	private PrestadoresDao prestadoresDao;

	public void crear(Prestadores prestadores) {
		try {
			if (consultar(prestadores) != null) {
				throw new HealthmanagerException(
						"Prestadores ya se encuentra en la base de datos");
			}
			prestadoresDao.crear(prestadores);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Prestadores prestadores) {
		try {
			return prestadoresDao.actualizar(prestadores);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Prestadores consultar(Prestadores prestadores) {
		try {
			return prestadoresDao.consultar(prestadores);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Prestadores prestadores) {
		try {
			return prestadoresDao.eliminar(prestadores);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Prestadores> listar(Map<String, Object> parameter) {
		try {
			return prestadoresDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listarPrestadoresPorRolCentro(
			Map<String, Object> param) {
		return prestadoresDao.listarPrestadoresPorRolCentro(param);
	}

	public List<Prestadores> listarPorCentro(Map<String, Object> parametros) {
		try {
			return prestadoresDao.listarPorCentro(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
