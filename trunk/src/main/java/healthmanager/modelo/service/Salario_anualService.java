/*
 * Salario_anualServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Salario_anual;
import healthmanager.modelo.dao.Salario_anualDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("salario_anualService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Salario_anualService implements Serializable{

	private String limit;

	@Autowired
	private Salario_anualDao salario_anualDao;

	public void crear(Salario_anual salario_anual) {
		try {
			if (consultar(salario_anual) != null) {
				throw new HealthmanagerException(
						"Salario_anual ya se encuentra en la base de datos");
			}
			salario_anualDao.crear(salario_anual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Salario_anual salario_anual) {
		try {
			return salario_anualDao.actualizar(salario_anual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Salario_anual consultar(Salario_anual salario_anual) {
		try {
			return salario_anualDao.consultar(salario_anual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Salario_anual salario_anual) {
		try {
			return salario_anualDao.eliminar(salario_anual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Salario_anual> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return salario_anualDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
