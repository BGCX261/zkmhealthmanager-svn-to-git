/*
 * C_costo_via_ingresoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.C_costo_via_ingreso;
import contaweb.modelo.dao.C_costo_via_ingresoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("c_costo_via_ingresoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class C_costo_via_ingresoService implements Serializable{

	@Autowired
	private C_costo_via_ingresoDao c_costo_via_ingresoDao;
	private String limit;

	public void crear(C_costo_via_ingreso c_costo_via_ingreso) {
		try {
			if (consultar(c_costo_via_ingreso) != null) {
				throw new HealthmanagerException(
						"C_costo_via_ingreso ya se encuentra en la base de datos");
			}
			c_costo_via_ingresoDao.crear(c_costo_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(C_costo_via_ingreso c_costo_via_ingreso) {
		try {
			return c_costo_via_ingresoDao.actualizar(c_costo_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public C_costo_via_ingreso consultar(C_costo_via_ingreso c_costo_via_ingreso) {
		try {
			return c_costo_via_ingresoDao.consultar(c_costo_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(C_costo_via_ingreso c_costo_via_ingreso) {
		try {
			return c_costo_via_ingresoDao.eliminar(c_costo_via_ingreso);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<C_costo_via_ingreso> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return c_costo_via_ingresoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return c_costo_via_ingresoDao.existe(parameters);
	}

}