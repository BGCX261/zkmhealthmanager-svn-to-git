/*
 * Nota_contableServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Nota_contable;
import contaweb.modelo.dao.Nota_contableDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("nota_contableService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Nota_contableService implements Serializable{

	private String limit;

	@Autowired
	private Nota_contableDao nota_contableDao;

	public void crear(Nota_contable nota_contable) {
		try {
			if (consultar(nota_contable) != null) {
				throw new HealthmanagerException(
						"Nota_contable ya se encuentra en la base de datos");
			}
			nota_contableDao.crear(nota_contable);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Nota_contable nota_contable) {
		try {
			return nota_contableDao.actualizar(nota_contable);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nota_contable consultar(Nota_contable nota_contable) {
		try {
			return nota_contableDao.consultar(nota_contable);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nota_contable consultarNota_credito_glosa(Nota_contable nota_contable) {
		try {
			return nota_contableDao.consultarNota_credito_glosa(nota_contable);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Nota_contable nota_contable) {
		try {
			return nota_contableDao.eliminar(nota_contable);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Nota_contable> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return nota_contableDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
