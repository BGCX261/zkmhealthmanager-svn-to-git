/*
 * Phistorias_paraclinicosServiceImpl.java
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
import healthmanager.modelo.bean.Phistorias_paraclinicos;
import healthmanager.modelo.dao.Phistorias_paraclinicosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("phistorias_paraclinicosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Phistorias_paraclinicosService implements Serializable{

	private String limit;

	@Autowired
	private Phistorias_paraclinicosDao phistorias_paraclinicosDao;

	public void crear(Phistorias_paraclinicos phistorias_paraclinicos) {
		try {
			if (consultar(phistorias_paraclinicos) != null) {
				throw new HealthmanagerException(
						"Phistorias_paraclinicos ya se encuentra en la base de datos");
			}
			phistorias_paraclinicosDao.crear(phistorias_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Phistorias_paraclinicos phistorias_paraclinicos) {
		try {
			return phistorias_paraclinicosDao
					.actualizar(phistorias_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Phistorias_paraclinicos consultar(
			Phistorias_paraclinicos phistorias_paraclinicos) {
		try {
			return phistorias_paraclinicosDao
					.consultar(phistorias_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Phistorias_paraclinicos phistorias_paraclinicos) {
		try {
			return phistorias_paraclinicosDao.eliminar(phistorias_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Phistorias_paraclinicos> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return phistorias_paraclinicosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.phistorias_paraclinicosDao.existe(parameters);
	}

}
