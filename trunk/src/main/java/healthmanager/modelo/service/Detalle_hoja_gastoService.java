/*
 * Detalle_hoja_gastoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_hoja_gasto;
import healthmanager.modelo.dao.Detalle_hoja_gastoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_hoja_gastoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_hoja_gastoService implements Serializable{

	@Autowired
	private Detalle_hoja_gastoDao detalle_hoja_gastoDao;
	private String limit;

	public void crear(Detalle_hoja_gasto detalle_hoja_gasto) {
		try {
			if (consultar(detalle_hoja_gasto) != null) {
				throw new HealthmanagerException(
						"Detalle_hoja_gasto ya se encuentra en la base de datos");
			}
			detalle_hoja_gastoDao.crear(detalle_hoja_gasto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_hoja_gasto detalle_hoja_gasto) {
		try {
			return detalle_hoja_gastoDao.actualizar(detalle_hoja_gasto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_hoja_gasto consultar(Detalle_hoja_gasto detalle_hoja_gasto) {
		try {
			return detalle_hoja_gastoDao.consultar(detalle_hoja_gasto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_hoja_gasto detalle_hoja_gasto) {
		try {
			return detalle_hoja_gastoDao.eliminar(detalle_hoja_gasto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_hoja_gasto> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_hoja_gastoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_hoja_gastoDao.existe(parameters);
	}

}