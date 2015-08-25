/*
 * Detalle_anexo3ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_anexo3;
import healthmanager.modelo.dao.Detalle_anexo3Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_anexo3Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_anexo3Service implements Serializable{

	@Autowired
	private Detalle_anexo3Dao detalle_anexo3Dao;
	private String limit;

	public void crear(Detalle_anexo3 detalle_anexo3) {
		try {
			if (consultar(detalle_anexo3) != null) {
				throw new HealthmanagerException(
						"Detalle_anexo3 ya se encuentra en la base de datos");
			}
			detalle_anexo3Dao.crear(detalle_anexo3);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_anexo3 detalle_anexo3) {
		try {
			return detalle_anexo3Dao.actualizar(detalle_anexo3);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_anexo3 consultar(Detalle_anexo3 detalle_anexo3) {
		try {
			return detalle_anexo3Dao.consultar(detalle_anexo3);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_anexo3 detalle_anexo3) {
		try {
			return detalle_anexo3Dao.eliminar(detalle_anexo3);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_anexo3> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_anexo3Dao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_anexo3Dao.existe(parameters);
	}

}