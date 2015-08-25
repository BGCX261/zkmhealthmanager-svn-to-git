/*
 * CarteraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Cartera;
import contaweb.modelo.dao.CarteraDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("carteraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CarteraService implements Serializable{

	private String limit;

	@Autowired
	private CarteraDao carteraDao;

	public void crear(Cartera cartera) {
		try {
			if (consultar(cartera) != null) {
				throw new HealthmanagerException(
						"Cartera ya se encuentra en la base de datos");
			}
			carteraDao.crear(cartera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Cartera cartera) {
		try {
			return carteraDao.actualizar(cartera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cartera consultar(Cartera cartera) {
		try {
			return carteraDao.consultar(cartera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cartera cartera) {
		try {
			return carteraDao.eliminar(cartera);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cartera> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return carteraDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.carteraDao.existe(parameters);
	}

}
