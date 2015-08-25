/*
 * Coordenadas_graficasServiceImpl.java
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
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.dao.Coordenadas_graficasDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("coordenadas_graficasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Coordenadas_graficasService implements Serializable{

	private String limit;

	@Autowired
	private Coordenadas_graficasDao coordenadas_graficasDao;

	public void crear(Coordenadas_graficas coordenadas_graficas) {
		try {
			if (consultar(coordenadas_graficas) != null) {
				throw new HealthmanagerException(
						"Coordenadas_graficas ya se encuentra en la base de datos");
			}
			coordenadas_graficasDao.crear(coordenadas_graficas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Coordenadas_graficas coordenadas_graficas) {
		try {
			return coordenadas_graficasDao.actualizar(coordenadas_graficas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Coordenadas_graficas consultar(
			Coordenadas_graficas coordenadas_graficas) {
		try {
			return coordenadas_graficasDao.consultar(coordenadas_graficas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Coordenadas_graficas coordenadas_graficas) {
		try {
			return coordenadas_graficasDao.eliminar(coordenadas_graficas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Coordenadas_graficas> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return coordenadas_graficasDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.coordenadas_graficasDao.existe(parameters);
	}

}
