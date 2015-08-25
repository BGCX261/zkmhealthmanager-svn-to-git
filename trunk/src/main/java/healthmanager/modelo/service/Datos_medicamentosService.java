/*
 * Datos_medicamentosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.dao.Datos_medicamentosDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("datos_medicamentosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Datos_medicamentosService implements Serializable{

	private String limit;

	@Autowired
	private Datos_medicamentosDao datos_medicamentosDao;

	public void crear(Datos_medicamentos datos_medicamentos) {
		try {
			if (consultar(datos_medicamentos) != null) {
				throw new HealthmanagerException(
						"Datos_medicamentos ya se encuentra en la base de datos");
			}
			datos_medicamentosDao.crear(datos_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Datos_medicamentos datos_medicamentos) {
		try {
			return datos_medicamentosDao.actualizar(datos_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Datos_medicamentos consultar(Datos_medicamentos datos_medicamentos) {
		try {
			return datos_medicamentosDao.consultar(datos_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Datos_medicamentos datos_medicamentos) {
		try {
			return datos_medicamentosDao.eliminar(datos_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Datos_medicamentos> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return datos_medicamentosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map<String, Object>> medicamentos_hoja_gasto(
			Map<String, Object> param) {
		try {
			param.put("limit", limit);
			return datos_medicamentosDao.medicamentos_hoja_gastos(param);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Map<String, Object> getFechaRealizacion(Map<String, Object> parametro) {
		return datos_medicamentosDao.getFechaRealizacion(parametro);
	}
}
