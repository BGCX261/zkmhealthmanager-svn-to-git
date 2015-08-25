/*
 * AdminServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.dao.Via_ingreso_contratadasDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("via_ingreso_contratadasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Via_ingreso_contratadasService implements Serializable{

	@Autowired
	private Via_ingreso_contratadasDao via_ingreso_contratadasDao;

	public void crear(Via_ingreso_contratadas via_ingreso_contratadas) {
		try {
			via_ingreso_contratadasDao.crear(via_ingreso_contratadas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Via_ingreso_contratadas via_ingreso_contratadas) {
		try {
			return via_ingreso_contratadasDao
					.actualizar(via_ingreso_contratadas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Via_ingreso_contratadas consultar(
			Via_ingreso_contratadas via_ingreso_contratadas) {
		try {
			return via_ingreso_contratadasDao
					.consultar(via_ingreso_contratadas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Via_ingreso_contratadas consultar_informacion(
			Via_ingreso_contratadas via_ingreso_contratadas) {
		try {
			return via_ingreso_contratadasDao
					.consultar_informacion(via_ingreso_contratadas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Via_ingreso_contratadas via_ingreso_contratadas) {
		try {
			return via_ingreso_contratadasDao.eliminar(via_ingreso_contratadas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Via_ingreso_contratadas> listar(Map<String, Object> parameter) {
		try {
			return via_ingreso_contratadasDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		try {
			return via_ingreso_contratadasDao.existe(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> verificarDuplicados() {
		try {
			return via_ingreso_contratadasDao.verificarDuplicados();
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}