/*
 * Cuadros_aiepi_descripcionesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cuadros_aiepi_descripciones;
import healthmanager.modelo.dao.Cuadros_aiepi_descripcionesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("cuadros_aiepi_descripcionesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cuadros_aiepi_descripcionesService implements Serializable{

	@Autowired
	private Cuadros_aiepi_descripcionesDao cuadros_aiepi_descripcionesDao;

	public void crear(Cuadros_aiepi_descripciones cuadros_aiepi_descripciones) {
		try {
			if (consultar(cuadros_aiepi_descripciones) != null) {
				throw new HealthmanagerException(
						"Cuadros_aiepi_descripciones ya se encuentra en la base de datos");
			}
			cuadros_aiepi_descripcionesDao.crear(cuadros_aiepi_descripciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Cuadros_aiepi_descripciones cuadros_aiepi_descripciones) {
		try {
			return cuadros_aiepi_descripcionesDao
					.actualizar(cuadros_aiepi_descripciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cuadros_aiepi_descripciones consultar(
			Cuadros_aiepi_descripciones cuadros_aiepi_descripciones) {
		try {
			return cuadros_aiepi_descripcionesDao
					.consultar(cuadros_aiepi_descripciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cuadros_aiepi_descripciones cuadros_aiepi_descripciones) {
		try {
			return cuadros_aiepi_descripcionesDao
					.eliminar(cuadros_aiepi_descripciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cuadros_aiepi_descripciones> listar(
			Map<String, Object> parameters) {
		try {
			return cuadros_aiepi_descripcionesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return cuadros_aiepi_descripcionesDao.existe(parameters);
	}

}