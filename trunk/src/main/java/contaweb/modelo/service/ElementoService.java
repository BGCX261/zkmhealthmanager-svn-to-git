/*
 * ElementoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Elemento;
import contaweb.modelo.dao.ElementoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("elementoContawebService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ElementoService implements Serializable{

	private String limit;

	@Autowired
	private ElementoDao elementoDao;

	public void crear(Elemento elemento) {
		try {
			if (consultar(elemento) != null) {
				throw new HealthmanagerException(
						"Elemento ya se encuentra en la base de datos");
			}
			elementoDao.crear(elemento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Elemento elemento) {
		try {
			return elementoDao.actualizar(elemento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Elemento consultar(Elemento elemento) {
		try {
			return elementoDao.consultar(elemento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Elemento elemento) {
		try {
			return elementoDao.eliminar(elemento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Elemento> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return elementoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Elemento> listar(String tipo) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("limit", limit);
			parametros.put("tipo", tipo);
			return elementoDao.listar(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
