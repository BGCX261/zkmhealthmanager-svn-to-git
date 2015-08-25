/*
 * Copago_estratoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Copago_estrato;
import healthmanager.modelo.dao.Copago_estratoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("copago_estratoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Copago_estratoService implements Serializable{

	private String limit;

	@Autowired
	private Copago_estratoDao Copago_estratoDao;

	public void crear(Copago_estrato Copago_estrato) {
		try {
			if (consultar(Copago_estrato) != null) {
				throw new HealthmanagerException(
						"Copago_estrato ya se encuentra en la base de datos");
			}
			Copago_estratoDao.crear(Copago_estrato);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Copago_estrato Copago_estrato) {
		try {
			return Copago_estratoDao.actualizar(Copago_estrato);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Copago_estrato consultar(Copago_estrato Copago_estrato) {
		try {
			return Copago_estratoDao.consultar(Copago_estrato);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Copago_estrato Copago_estrato) {
		try {
			return Copago_estratoDao.eliminar(Copago_estrato);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Copago_estrato> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return Copago_estratoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
