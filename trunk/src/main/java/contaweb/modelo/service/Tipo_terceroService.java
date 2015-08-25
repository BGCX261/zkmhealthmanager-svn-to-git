/*
 * Tipo_terceroServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Tipo_tercero;
import contaweb.modelo.dao.Tipo_terceroDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("tipo_terceroService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Tipo_terceroService implements Serializable{

	private String limit;

	@Autowired
	private Tipo_terceroDao tipo_terceroDao;

	public void crear(Tipo_tercero tipo_tercero) {
		try {
			if (consultar(tipo_tercero) != null) {
				throw new HealthmanagerException(
						"Tipo_tercero ya se encuentra en la base de datos");
			}
			tipo_terceroDao.crear(tipo_tercero);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Tipo_tercero tipo_tercero) {
		try {
			return tipo_terceroDao.actualizar(tipo_tercero);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Tipo_tercero consultar(Tipo_tercero tipo_tercero) {
		try {
			return tipo_terceroDao.consultar(tipo_tercero);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Tipo_tercero tipo_tercero) {
		try {
			return tipo_terceroDao.eliminar(tipo_tercero);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Tipo_tercero> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return tipo_terceroDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
