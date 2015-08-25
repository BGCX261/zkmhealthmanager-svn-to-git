/*
 * Revision_ciiuServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Revision_ciiu;
import healthmanager.modelo.dao.Revision_ciiuDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("revision_ciiuService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Revision_ciiuService implements Serializable{

	private String limit;

	@Autowired
	private Revision_ciiuDao revision_ciiuDao;

	public void crear(Revision_ciiu revision_ciiu) {
		try {
			if (consultar(revision_ciiu) != null) {
				throw new HealthmanagerException(
						"Revision_ciiu ya se encuentra en la base de datos");
			}
			revision_ciiuDao.crear(revision_ciiu);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Revision_ciiu revision_ciiu) {
		try {
			return revision_ciiuDao.actualizar(revision_ciiu);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Revision_ciiu consultar(Revision_ciiu revision_ciiu) {
		try {
			return revision_ciiuDao.consultar(revision_ciiu);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Revision_ciiu revision_ciiu) {
		try {
			return revision_ciiuDao.eliminar(revision_ciiu);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Revision_ciiu> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return revision_ciiuDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
