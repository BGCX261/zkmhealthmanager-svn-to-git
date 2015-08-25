/*
 * Anexo3_entidadServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.dao.Anexo3_entidad2Dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("anexo3_entidad2Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Anexo3_entidad2Service implements Serializable{

	@Autowired
	private Anexo3_entidad2Dao anexo3_entidad2Dao;
	private String limit;

	public void crear(Anexo3_entidad anexo3_entidad) {
		try {
			if (consultar(anexo3_entidad) != null) {
				throw new HealthmanagerException(
						"Anexo3_entidad ya se encuentra en la base de datos");
			}
			anexo3_entidad2Dao.crear(anexo3_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Anexo3_entidad anexo3_entidad) {
		try {
			return anexo3_entidad2Dao.actualizar(anexo3_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Anexo3_entidad consultar(Anexo3_entidad anexo3_entidad) {
		try {
			return anexo3_entidad2Dao.consultar(anexo3_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Anexo3_entidad anexo3_entidad) {
		try {
			return anexo3_entidad2Dao.eliminar(anexo3_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Anexo3_entidad> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return anexo3_entidad2Dao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return anexo3_entidad2Dao.existe(parameters);
	}

}