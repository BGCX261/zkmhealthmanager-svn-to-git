/*
 * Parametro_codigo_consultaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Parametro_codigo_consulta;
import healthmanager.modelo.dao.Parametro_codigo_consultaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("parametro_codigo_consultaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Parametro_codigo_consultaService implements Serializable{

	private String limit;

	@Autowired
	private Parametro_codigo_consultaDao parametro_codigo_consultaDao;

	public void crear(Parametro_codigo_consulta parametro_codigo_consulta) {
		try {
			if (consultar(parametro_codigo_consulta) != null) {
				throw new HealthmanagerException(
						"Parametro_codigo_consulta ya se encuentra en la base de datos");
			}
			parametro_codigo_consultaDao.crear(parametro_codigo_consulta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Parametro_codigo_consulta parametro_codigo_consulta) {
		try {
			return parametro_codigo_consultaDao
					.actualizar(parametro_codigo_consulta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Parametro_codigo_consulta consultar(
			Parametro_codigo_consulta parametro_codigo_consulta) {
		try {
			return parametro_codigo_consultaDao
					.consultar(parametro_codigo_consulta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Parametro_codigo_consulta parametro_codigo_consulta) {
		try {
			return parametro_codigo_consultaDao
					.eliminar(parametro_codigo_consulta);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Parametro_codigo_consulta> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return parametro_codigo_consultaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
