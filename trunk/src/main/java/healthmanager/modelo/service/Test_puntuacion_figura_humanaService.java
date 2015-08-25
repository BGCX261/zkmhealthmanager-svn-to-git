/*
 * Test_puntuacion_figura_humanaService.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Test_puntuacion_figura_humana;
import healthmanager.modelo.dao.Test_puntuacion_figura_humanaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("test_puntuacion_figura_humanaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Test_puntuacion_figura_humanaService implements Serializable{
	private String limit;

	@Autowired
	private Test_puntuacion_figura_humanaDao test_puntuacion_figura_humanaDao;

	public void crear(
			Test_puntuacion_figura_humana test_puntuacion_figura_humana) {
		try {
			if (consultar(test_puntuacion_figura_humana) != null) {
				throw new HealthmanagerException(
						"Test_puntuacion_figura_humana ya se encuentra en la base de datos");
			}
			test_puntuacion_figura_humanaDao
					.crear(test_puntuacion_figura_humana);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Test_puntuacion_figura_humana test_puntuacion_figura_humana) {
		try {
			return test_puntuacion_figura_humanaDao
					.actualizar(test_puntuacion_figura_humana);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Test_puntuacion_figura_humana consultar(
			Test_puntuacion_figura_humana test_puntuacion_figura_humana) {
		try {
			return test_puntuacion_figura_humanaDao
					.consultar(test_puntuacion_figura_humana);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Test_puntuacion_figura_humana test_puntuacion_figura_humana) {
		try {
			return test_puntuacion_figura_humanaDao
					.eliminar(test_puntuacion_figura_humana);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Test_puntuacion_figura_humana> listar(
			Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return test_puntuacion_figura_humanaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Test_puntuacion_figura_humana> listarHistorial(
			Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return test_puntuacion_figura_humanaDao.listarHistorial(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.test_puntuacion_figura_humanaDao.existe(parameters);
	}
}