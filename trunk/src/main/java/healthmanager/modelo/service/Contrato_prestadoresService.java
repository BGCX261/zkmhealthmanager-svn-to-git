/*
 * Contrato_prestadoresServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Contrato_prestadores;
import healthmanager.modelo.dao.Contrato_prestadoresDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("contrato_prestadoresService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Contrato_prestadoresService implements Serializable{

	private String limit;

	@Autowired
	private Contrato_prestadoresDao contrato_prestadoresDao;

	public void crear(Contrato_prestadores contrato_prestadores) {
		try {
			if (consultar(contrato_prestadores) != null) {
				throw new HealthmanagerException(
						"Contrato_prestadores ya se encuentra en la base de datos");
			}
			contrato_prestadoresDao.crear(contrato_prestadores);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Contrato_prestadores contrato_prestadores) {
		try {
			return contrato_prestadoresDao.actualizar(contrato_prestadores);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Contrato_prestadores consultar(
			Contrato_prestadores contrato_prestadores) {
		try {
			return contrato_prestadoresDao.consultar(contrato_prestadores);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Contrato_prestadores contrato_prestadores) {
		try {
			return contrato_prestadoresDao.eliminar(contrato_prestadores);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Contrato_prestadores> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return contrato_prestadoresDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
