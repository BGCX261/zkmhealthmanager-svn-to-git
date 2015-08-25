/*
 * Consultorio_prestadorServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Consultorio_prestador;
import healthmanager.modelo.dao.Consultorio_prestadorDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("consultorio_prestadorService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Consultorio_prestadorService implements Serializable{

	private String limit;

	@Autowired
	private Consultorio_prestadorDao consultorio_prestadorDao;

	public void crear(Consultorio_prestador consultorio_prestador) {
		try {
			if (consultar(consultorio_prestador) != null) {
				throw new HealthmanagerException(
						"Consultorio_prestador ya se encuentra en la base de datos");
			}
			consultorio_prestadorDao.crear(consultorio_prestador);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Consultorio_prestador consultorio_prestador) {
		try {
			return consultorio_prestadorDao.actualizar(consultorio_prestador);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Consultorio_prestador consultar(
			Consultorio_prestador consultorio_prestador) {
		try {
			return consultorio_prestadorDao.consultar(consultorio_prestador);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Consultorio_prestador consultorio_prestador) {
		try {
			return consultorio_prestadorDao.eliminar(consultorio_prestador);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Consultorio_prestador> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return consultorio_prestadorDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.consultorio_prestadorDao.existe(parameters);
	}

}
