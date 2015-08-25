/*
 * Parametros_signosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Parametros_signos;
import healthmanager.modelo.dao.Parametros_signosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("parametros_signosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Parametros_signosService implements Serializable{

	@Autowired
	private Parametros_signosDao parametros_signosDao;
	private String limit;

	public void guardarDatos(List<Parametros_signos> listado_parametros) {
		try {
			for (Parametros_signos parametros_signos : listado_parametros) {
				if (consultar(parametros_signos) != null) {
					parametros_signosDao.actualizar(parametros_signos);
				} else {
					parametros_signosDao.crear(parametros_signos);
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Parametros_signos parametros_signos) {
		try {
			if (consultar(parametros_signos) != null) {
				throw new HealthmanagerException(
						"Parametros_signos ya se encuentra en la base de datos");
			}
			parametros_signosDao.crear(parametros_signos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Parametros_signos parametros_signos) {
		try {
			return parametros_signosDao.actualizar(parametros_signos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Parametros_signos consultar(Parametros_signos parametros_signos) {
		try {
			return parametros_signosDao.consultar(parametros_signos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Parametros_signos parametros_signos) {
		try {
			return parametros_signosDao.eliminar(parametros_signos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Parametros_signos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return parametros_signosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return parametros_signosDao.existe(parameters);
	}

}