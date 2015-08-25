/*
 * Cuadros_aiepi_estadoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cuadros_aiepi_estado;
import healthmanager.modelo.dao.Cuadros_aiepi_estadoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("cuadros_aiepi_estadoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cuadros_aiepi_estadoService implements Serializable{

	@Autowired
	private Cuadros_aiepi_estadoDao cuadros_aiepi_estadoDao;

	public void crear(Cuadros_aiepi_estado cuadros_aiepi_estado) {
		try {
			if (consultar(cuadros_aiepi_estado) != null) {
				throw new HealthmanagerException(
						"Cuadros_aiepi_estado ya se encuentra en la base de datos");
			}
			cuadros_aiepi_estadoDao.crear(cuadros_aiepi_estado);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Cuadros_aiepi_estado cuadros_aiepi_estado) {
		try {
			return cuadros_aiepi_estadoDao.actualizar(cuadros_aiepi_estado);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cuadros_aiepi_estado consultar(
			Cuadros_aiepi_estado cuadros_aiepi_estado) {
		try {
			return cuadros_aiepi_estadoDao.consultar(cuadros_aiepi_estado);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cuadros_aiepi_estado cuadros_aiepi_estado) {
		try {
			return cuadros_aiepi_estadoDao.eliminar(cuadros_aiepi_estado);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cuadros_aiepi_estado> listar(Map<String, Object> parameters) {
		try {
			return cuadros_aiepi_estadoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return cuadros_aiepi_estadoDao.existe(parameters);
	}

}