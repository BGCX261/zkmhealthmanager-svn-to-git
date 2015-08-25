/*
 * Aportes_cotizacionesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Aportes_cotizaciones;
import healthmanager.modelo.dao.Aportes_cotizacionesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("aportes_cotizacionesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Aportes_cotizacionesService implements Serializable{

	private String limit;

	@Autowired
	private Aportes_cotizacionesDao aportes_cotizacionesDao;

	public void crear(Aportes_cotizaciones aportes_cotizaciones) {
		try {
			Aportes_cotizaciones aportesCotizacionesTemp = consultar(aportes_cotizaciones);
			if (aportesCotizacionesTemp != null) {
				aportes_cotizaciones.setValor_cotiza(aportesCotizacionesTemp
						.getValor_cotiza()
						+ aportes_cotizaciones.getValor_cotiza());
				aportes_cotizaciones.setIbc(aportesCotizacionesTemp.getIbc()
						+ aportes_cotizaciones.getIbc());
				aportes_cotizacionesDao.actualizar(aportes_cotizaciones);
			} else
				aportes_cotizacionesDao.crear(aportes_cotizaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Aportes_cotizaciones aportes_cotizaciones) {
		try {
			return aportes_cotizacionesDao.actualizar(aportes_cotizaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Aportes_cotizaciones consultar(
			Aportes_cotizaciones aportes_cotizaciones) {
		try {
			return aportes_cotizacionesDao.consultar(aportes_cotizaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Aportes_cotizaciones aportes_cotizaciones) {
		try {
			return aportes_cotizacionesDao.eliminar(aportes_cotizaciones);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Aportes_cotizaciones> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return aportes_cotizacionesDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public Double getIbcOrCotizacion(Map<String, Object> parameters) {
		return this.aportes_cotizacionesDao.getIbcOrCotizacion(parameters);
	}
}
