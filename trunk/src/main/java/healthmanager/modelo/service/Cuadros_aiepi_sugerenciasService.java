/*
 * Cuadros_aiepi_sugerenciasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cuadros_aiepi_sugerencias;
import healthmanager.modelo.dao.Cuadros_aiepi_sugerenciasDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("cuadros_aiepi_sugerenciasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cuadros_aiepi_sugerenciasService implements Serializable{

	@Autowired
	private Cuadros_aiepi_sugerenciasDao cuadros_aiepi_sugerenciasDao;

	public void crear(Cuadros_aiepi_sugerencias cuadros_aiepi_sugerencias) {
		try {
			if (consultar(cuadros_aiepi_sugerencias) != null) {
				throw new HealthmanagerException(
						"Cuadros_aiepi_sugerencias ya se encuentra en la base de datos");
			}
			cuadros_aiepi_sugerenciasDao.crear(cuadros_aiepi_sugerencias);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Cuadros_aiepi_sugerencias cuadros_aiepi_sugerencias) {
		try {
			return cuadros_aiepi_sugerenciasDao
					.actualizar(cuadros_aiepi_sugerencias);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cuadros_aiepi_sugerencias consultar(
			Cuadros_aiepi_sugerencias cuadros_aiepi_sugerencias) {
		try {
			return cuadros_aiepi_sugerenciasDao
					.consultar(cuadros_aiepi_sugerencias);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cuadros_aiepi_sugerencias cuadros_aiepi_sugerencias) {
		try {
			return cuadros_aiepi_sugerenciasDao
					.eliminar(cuadros_aiepi_sugerencias);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cuadros_aiepi_sugerencias> listar(Map<String, Object> parameters) {
		try {
			return cuadros_aiepi_sugerenciasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return cuadros_aiepi_sugerenciasDao.existe(parameters);
	}

}