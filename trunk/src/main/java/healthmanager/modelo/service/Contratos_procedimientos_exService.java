/*
 * Contratos_procedimientos_exServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Contratos_procedimientos_ex;
import healthmanager.modelo.dao.Contratos_procedimientos_exDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("contratos_procedimientos_exService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Contratos_procedimientos_exService implements Serializable{

	@Autowired
	private Contratos_procedimientos_exDao contratos_procedimientos_exDao;

	public void crear(Contratos_procedimientos_ex contratos_procedimientos_ex) {
		try {
			if (consultar(contratos_procedimientos_ex) != null) {
				throw new HealthmanagerException(
						"Contratos_procedimientos_ex ya se encuentra en la base de datos");
			}
			contratos_procedimientos_exDao.crear(contratos_procedimientos_ex);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Contratos_procedimientos_ex contratos_procedimientos_ex) {
		try {
			return contratos_procedimientos_exDao
					.actualizar(contratos_procedimientos_ex);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Contratos_procedimientos_ex consultar(
			Contratos_procedimientos_ex contratos_procedimientos_ex) {
		try {
			return contratos_procedimientos_exDao
					.consultar(contratos_procedimientos_ex);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Contratos_procedimientos_ex contratos_procedimientos_ex) {
		try {
			return contratos_procedimientos_exDao
					.eliminar(contratos_procedimientos_ex);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Contratos_procedimientos_ex> listar(
			Map<String, Object> parameters) {
		try {
			return contratos_procedimientos_exDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<String> listar_cups_ex(Map<String, Object> parametros) {
		try {
			return contratos_procedimientos_exDao.listar_cups_ex(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return contratos_procedimientos_exDao.existe(parameters);
	}

}