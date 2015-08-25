/*
 * Descuentos_laboratoriosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Descuentos_laboratorios;
import healthmanager.modelo.dao.Descuentos_laboratoriosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("descuentos_laboratoriosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Descuentos_laboratoriosService implements Serializable{

	@Autowired
	private Descuentos_laboratoriosDao descuentos_laboratoriosDao;
	private String limit;

	public void crear(Descuentos_laboratorios descuentos_laboratorios) {
		try {
			if (consultar(descuentos_laboratorios) != null) {
				throw new HealthmanagerException(
						"Descuentos_laboratorios ya se encuentra en la base de datos");
			}
			descuentos_laboratoriosDao.crear(descuentos_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Descuentos_laboratorios descuentos_laboratorios) {
		try {
			return descuentos_laboratoriosDao
					.actualizar(descuentos_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Descuentos_laboratorios consultar(
			Descuentos_laboratorios descuentos_laboratorios) {
		try {
			return descuentos_laboratoriosDao
					.consultar(descuentos_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Descuentos_laboratorios descuentos_laboratorios) {
		try {
			return descuentos_laboratoriosDao.eliminar(descuentos_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Descuentos_laboratorios> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return descuentos_laboratoriosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return descuentos_laboratoriosDao.existe(parameters);
	}

}