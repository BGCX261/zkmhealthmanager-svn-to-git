/*
 * Detalle_notaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Detalle_nota;
import contaweb.modelo.dao.Detalle_notaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_notaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_notaService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_notaDao detalle_notaDao;

	public void crear(Detalle_nota detalle_nota) {
		try {
			if (consultar(detalle_nota) != null) {
				throw new HealthmanagerException(
						"Detalle_nota ya se encuentra en la base de datos");
			}
			detalle_notaDao.crear(detalle_nota);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_nota detalle_nota) {
		try {
			return detalle_notaDao.actualizar(detalle_nota);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_nota consultar(Detalle_nota detalle_nota) {
		try {
			return detalle_notaDao.consultar(detalle_nota);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_nota detalle_nota) {
		try {
			return detalle_notaDao.eliminar(detalle_nota);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_nota> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_notaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public void setLimit(String limit) {
		this.limit = limit;
	}

}
