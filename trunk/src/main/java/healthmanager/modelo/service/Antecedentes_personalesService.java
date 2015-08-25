/*
 * Antecedentes_personalesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Antecedentes_personales;
import healthmanager.modelo.dao.Antecedentes_personalesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("antecedentes_personalesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Antecedentes_personalesService implements Serializable{

	@Autowired
	private Antecedentes_personalesDao antecedentes_personalesDao;

	public void crear(Antecedentes_personales antecedentes_personales) {
		try {
			if (consultar(antecedentes_personales) != null) {
				throw new HealthmanagerException(
						"Antecedentes_personales ya se encuentra en la base de datos");
			}
			antecedentes_personalesDao.crear(antecedentes_personales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Antecedentes_personales antecedentes_personales) {
		try {
			return antecedentes_personalesDao
					.actualizar(antecedentes_personales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Antecedentes_personales consultar(
			Antecedentes_personales antecedentes_personales) {
		try {
			return antecedentes_personalesDao
					.consultar(antecedentes_personales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Antecedentes_personales antecedentes_personales) {
		try {
			return antecedentes_personalesDao.eliminar(antecedentes_personales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Antecedentes_personales> listar(Map<String, Object> parameter) {
		try {
			return antecedentes_personalesDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listar_reporte(
			Map<String, Object> parametros) {
		try {
			return antecedentes_personalesDao.listar_reporte(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


}
