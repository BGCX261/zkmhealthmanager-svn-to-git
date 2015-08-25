/*
 * Cuadros_aiepi_tutorialesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cuadros_aiepi_tutoriales;
import healthmanager.modelo.dao.Cuadros_aiepi_tutorialesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("cuadros_aiepi_tutorialesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cuadros_aiepi_tutorialesService implements Serializable{

	@Autowired
	private Cuadros_aiepi_tutorialesDao cuadros_aiepi_tutorialesDao;

	public void crear(Cuadros_aiepi_tutoriales cuadros_aiepi_tutoriales) {
		try {
			if (consultar(cuadros_aiepi_tutoriales) != null) {
				throw new HealthmanagerException(
						"Cuadros_aiepi_tutoriales ya se encuentra en la base de datos");
			}
			cuadros_aiepi_tutorialesDao.crear(cuadros_aiepi_tutoriales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Cuadros_aiepi_tutoriales cuadros_aiepi_tutoriales) {
		try {
			return cuadros_aiepi_tutorialesDao
					.actualizar(cuadros_aiepi_tutoriales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cuadros_aiepi_tutoriales consultar(
			Cuadros_aiepi_tutoriales cuadros_aiepi_tutoriales) {
		try {
			return cuadros_aiepi_tutorialesDao
					.consultar(cuadros_aiepi_tutoriales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cuadros_aiepi_tutoriales cuadros_aiepi_tutoriales) {
		try {
			return cuadros_aiepi_tutorialesDao
					.eliminar(cuadros_aiepi_tutoriales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cuadros_aiepi_tutoriales> listar(Map<String, Object> parameters) {
		try {
			return cuadros_aiepi_tutorialesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return cuadros_aiepi_tutorialesDao.existe(parameters);
	}

}