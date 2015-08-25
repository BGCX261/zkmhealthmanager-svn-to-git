/*
 * Detalle_facturaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.dao.Detalle_facturaDao;

@Service("detalle_facturaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_facturaService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_facturaDao detalle_facturaDao;

	public void crear(Detalle_factura detalle_factura) {
		try {
			if (consultar(detalle_factura) != null) {
				throw new HealthmanagerException(
						"Detalle_factura ya se encuentra en la base de datos");
			}
			detalle_facturaDao.crear(detalle_factura);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_factura detalle_factura) {
		try {
			return detalle_facturaDao.actualizar(detalle_factura);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_factura consultar(Detalle_factura detalle_factura) {
		try {
			return detalle_facturaDao.consultar(detalle_factura);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_factura detalle_factura) {
		try {
			return detalle_facturaDao.eliminar(detalle_factura);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_factura> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_facturaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public Double totalFacturaClinica(Map<String, Object> parameters){
		try {
			return detalle_facturaDao.totalFacturaClinica(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map<String, Object>> getInformacionFactura(
			Map<String, Object> parametros) {
		return detalle_facturaDao.getInformacionFactura(parametros);
	}

}
