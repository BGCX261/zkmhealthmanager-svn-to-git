/*
 * SigvitalesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.dao.SigvitalesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("sigvitalesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SigvitalesService implements Serializable{

	private String limit;

	@Autowired
	private SigvitalesDao sigvitalesDao;

	public void crear(Sigvitales sigvitales) {
		try {
			if (consultar(sigvitales) != null) {
				throw new HealthmanagerException(
						"Sigvitales ya se encuentra en la base de datos");
			}
			sigvitalesDao.crear(sigvitales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Sigvitales sigvitales) {
		try {
			return sigvitalesDao.actualizar(sigvitales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Sigvitales consultar(Sigvitales sigvitales) {
		try {
			return sigvitalesDao.consultar(sigvitales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Sigvitales sigvitales) {
		try {
			return sigvitalesDao.eliminar(sigvitales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Sigvitales> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return sigvitalesDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.sigvitalesDao.existe(parameters);
	}

}
