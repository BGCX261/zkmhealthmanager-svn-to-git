/*
 * Detalle_anexo4ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_anexo4;
import healthmanager.modelo.dao.Detalle_anexo4Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_anexo4Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_anexo4Service implements Serializable{

	@Autowired
	private Detalle_anexo4Dao detalle_anexo4Dao;
	private String limit;

	public void crear(Detalle_anexo4 detalle_anexo4) {
		try {
			if (consultar(detalle_anexo4) != null) {
				throw new HealthmanagerException(
						"Detalle_anexo4 ya se encuentra en la base de datos");
			}
			detalle_anexo4Dao.crear(detalle_anexo4);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_anexo4 detalle_anexo4) {
		try {
			return detalle_anexo4Dao.actualizar(detalle_anexo4);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_anexo4 consultar(Detalle_anexo4 detalle_anexo4) {
		try {
			return detalle_anexo4Dao.consultar(detalle_anexo4);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_anexo4 detalle_anexo4) {
		try {
			return detalle_anexo4Dao.eliminar(detalle_anexo4);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_anexo4> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_anexo4Dao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_anexo4Dao.existe(parameters);
	}

}