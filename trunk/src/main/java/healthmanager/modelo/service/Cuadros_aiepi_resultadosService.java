/*
 * Cuadros_aiepi_resultadosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cuadros_aiepi_resultados;
import healthmanager.modelo.dao.Cuadros_aiepi_resultadosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("cuadros_aiepi_resultadosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cuadros_aiepi_resultadosService implements Serializable{

	@Autowired
	private Cuadros_aiepi_resultadosDao cuadros_aiepi_resultadosDao;

	public void crear(Cuadros_aiepi_resultados cuadros_aiepi_resultados) {
		try {
			if (consultar(cuadros_aiepi_resultados) != null) {
				throw new HealthmanagerException(
						"Cuadros_aiepi_resultados ya se encuentra en la base de datos");
			}
			cuadros_aiepi_resultadosDao.crear(cuadros_aiepi_resultados);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Cuadros_aiepi_resultados cuadros_aiepi_resultados) {
		try {
			return cuadros_aiepi_resultadosDao
					.actualizar(cuadros_aiepi_resultados);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cuadros_aiepi_resultados consultar(
			Cuadros_aiepi_resultados cuadros_aiepi_resultados) {
		try {
			return cuadros_aiepi_resultadosDao
					.consultar(cuadros_aiepi_resultados);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cuadros_aiepi_resultados cuadros_aiepi_resultados) {
		try {
			return cuadros_aiepi_resultadosDao
					.eliminar(cuadros_aiepi_resultados);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cuadros_aiepi_resultados> listar(Map<String, Object> parameters) {
		try {
			return cuadros_aiepi_resultadosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return cuadros_aiepi_resultadosDao.existe(parameters);
	}

}