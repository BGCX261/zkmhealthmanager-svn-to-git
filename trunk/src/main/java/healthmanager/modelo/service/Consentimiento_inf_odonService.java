/*
 * Consentimineto_inf_odonServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Consentimiento_inf_odon;
import healthmanager.modelo.dao.Consentimiento_inf_odonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("consentimiento_inf_odonService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Consentimiento_inf_odonService implements Serializable{

	@Autowired
	private Consentimiento_inf_odonDao consentimiento_inf_odonDao;
	private String limit;

	public void crear(Consentimiento_inf_odon consentimineto_inf_odon) {
		try {
			if (consultar(consentimineto_inf_odon) != null) {
				throw new HealthmanagerException(
						"Consentimineto_inf_odon ya se encuentra en la base de datos");
			}
			consentimiento_inf_odonDao.crear(consentimineto_inf_odon);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Consentimiento_inf_odon consentimineto_inf_odon) {
		try {
			return consentimiento_inf_odonDao
					.actualizar(consentimineto_inf_odon);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Consentimiento_inf_odon consultar(
			Consentimiento_inf_odon consentimineto_inf_odon) {
		try {
			return consentimiento_inf_odonDao
					.consultar(consentimineto_inf_odon);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Consentimiento_inf_odon consentimineto_inf_odon) {
		try {
			return consentimiento_inf_odonDao.eliminar(consentimineto_inf_odon);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Consentimiento_inf_odon> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return consentimiento_inf_odonDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return consentimiento_inf_odonDao.existe(parameters);
	}

	public Consentimiento_inf_odon consultarPorParametros(
			Consentimiento_inf_odon consentimineto_inf_odon) {
		return consentimiento_inf_odonDao
				.consultarPorParametros(consentimineto_inf_odon);
	}
}