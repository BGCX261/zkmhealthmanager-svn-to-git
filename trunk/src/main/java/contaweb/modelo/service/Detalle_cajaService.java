/*
 * Detalle_cajaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Detalle_caja;
import contaweb.modelo.dao.Detalle_cajaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_cajaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_cajaService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_cajaDao detalle_cajaDao;

	public void crear(Detalle_caja detalle_caja) {
		try {
			if (consultar(detalle_caja) != null) {
				throw new HealthmanagerException(
						"Detalle_caja ya se encuentra en la base de datos");
			}
			detalle_cajaDao.crear(detalle_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_caja detalle_caja) {
		try {
			return detalle_cajaDao.actualizar(detalle_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_caja consultar(Detalle_caja detalle_caja) {
		try {
			return detalle_cajaDao.consultar(detalle_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_caja detalle_caja) {
		try {
			return detalle_cajaDao.eliminar(detalle_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_caja> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_cajaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
