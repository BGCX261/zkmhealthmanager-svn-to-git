/*
 * Escala_del_desarrolloServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Escala_del_desarrollo;
import healthmanager.modelo.dao.Escala_del_desarrolloDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("escala_del_desarrolloService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Escala_del_desarrolloService implements Serializable{

	@Autowired
	private Escala_del_desarrolloDao escala_del_desarrolloDao;

	public void crear(Escala_del_desarrollo escala_del_desarrollo) {
		try {
			if (consultar(escala_del_desarrollo) != null) {
				throw new HealthmanagerException(
						"Escala_del_desarrollo ya se encuentra en la base de datos");
			}
			escala_del_desarrolloDao.crear(escala_del_desarrollo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Escala_del_desarrollo escala_del_desarrollo) {
		try {
			return escala_del_desarrolloDao.actualizar(escala_del_desarrollo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Escala_del_desarrollo consultar(
			Escala_del_desarrollo escala_del_desarrollo) {
		try {
			return escala_del_desarrolloDao.consultar(escala_del_desarrollo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Escala_del_desarrollo escala_del_desarrollo) {
		try {
			return escala_del_desarrolloDao.eliminar(escala_del_desarrollo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Escala_del_desarrollo> listar(Map<String, Object> parameters) {
		try {
			return escala_del_desarrolloDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return escala_del_desarrolloDao.existe(parameters);
	}

}