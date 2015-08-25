/*
 * BarrioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.dao.BarrioDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("barrioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class BarrioService implements Serializable{

	@Autowired
	private BarrioDao barrioDao;

	public void crear(Barrio barrio) {
		try {
			if (consultar(barrio) != null) {
				throw new HealthmanagerException(
						"Barrio ya se encuentra en la base de datos");
			}
			barrioDao.crear(barrio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Barrio barrio) {
		try {
			return barrioDao.actualizar(barrio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Barrio consultar(Barrio barrio) {
		try {
			return barrioDao.consultar(barrio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Barrio barrio) {
		try {
			return barrioDao.eliminar(barrio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Barrio> listar(Map<String, Object> parameter) {
		try {
			return barrioDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.barrioDao.existe(parameters);
	}
	
	public Integer totalResultados(Map<String, Object> parameters) {
		try {
			return barrioDao.totalResultados(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


}
