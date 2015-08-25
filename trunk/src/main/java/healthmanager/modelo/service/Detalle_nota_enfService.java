/*
 * Detalle_nota_enfServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_nota_enf;
import healthmanager.modelo.dao.Detalle_nota_enfDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_nota_enfService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_nota_enfService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_nota_enfDao detalle_nota_enfDao;

	public void crear(Detalle_nota_enf detalle_nota_enf) {
		try {
			if (consultar(detalle_nota_enf) != null) {
				throw new HealthmanagerException(
						"Detalle_nota_enf ya se encuentra en la base de datos");
			}
			detalle_nota_enfDao.crear(detalle_nota_enf);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_nota_enf detalle_nota_enf) {
		try {
			return detalle_nota_enfDao.actualizar(detalle_nota_enf);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_nota_enf consultar(Detalle_nota_enf detalle_nota_enf) {
		try {
			return detalle_nota_enfDao.consultar(detalle_nota_enf);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_nota_enf detalle_nota_enf) {
		try {
			return detalle_nota_enfDao.eliminar(detalle_nota_enf);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_nota_enf> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_nota_enfDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
