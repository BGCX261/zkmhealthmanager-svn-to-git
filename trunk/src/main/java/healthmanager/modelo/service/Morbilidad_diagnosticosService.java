/*
 * Morbilidad_diagnosticosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Morbilidad_diagnosticos;
import healthmanager.modelo.dao.Morbilidad_diagnosticosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("morbilidad_diagnosticosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Morbilidad_diagnosticosService implements Serializable{

	@Autowired
	private Morbilidad_diagnosticosDao morbilidad_diagnosticosDao;
	private String limit;

	public void crear(Morbilidad_diagnosticos morbilidad_diagnosticos) {
		try {
			if (consultar(morbilidad_diagnosticos) != null) {
				throw new HealthmanagerException(
						"Morbilidad_diagnosticos ya se encuentra en la base de datos");
			}
			morbilidad_diagnosticosDao.crear(morbilidad_diagnosticos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Morbilidad_diagnosticos morbilidad_diagnosticos) {
		try {
			return morbilidad_diagnosticosDao
					.actualizar(morbilidad_diagnosticos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Morbilidad_diagnosticos consultar(
			Morbilidad_diagnosticos morbilidad_diagnosticos) {
		try {
			return morbilidad_diagnosticosDao
					.consultar(morbilidad_diagnosticos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Morbilidad_diagnosticos morbilidad_diagnosticos) {
		try {
			return morbilidad_diagnosticosDao.eliminar(morbilidad_diagnosticos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Morbilidad_diagnosticos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return morbilidad_diagnosticosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return morbilidad_diagnosticosDao.existe(parameters);
	}

}