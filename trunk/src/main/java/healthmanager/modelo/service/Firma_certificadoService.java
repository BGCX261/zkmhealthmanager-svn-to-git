/*
 * Firma_certificadoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Firma_certificado;
import healthmanager.modelo.dao.Firma_certificadoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("firma_certificadoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Firma_certificadoService implements Serializable{

	private String limit;

	@Autowired
	private Firma_certificadoDao firma_certificadoDao;

	public void crear(Firma_certificado firma_certificado) {
		try {
			if (consultar(firma_certificado) != null) {
				throw new HealthmanagerException(
						"Firma_certificado ya se encuentra en la base de datos");
			}
			firma_certificadoDao.crear(firma_certificado);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Firma_certificado firma_certificado) {
		try {
			return firma_certificadoDao.actualizar(firma_certificado);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Firma_certificado consultar(Firma_certificado firma_certificado) {
		try {
			return firma_certificadoDao.consultar(firma_certificado);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Firma_certificado firma_certificado) {
		try {
			return firma_certificadoDao.eliminar(firma_certificado);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Firma_certificado> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return firma_certificadoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
