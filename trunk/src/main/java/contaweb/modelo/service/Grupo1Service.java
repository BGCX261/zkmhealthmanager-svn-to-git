/*
 * Grupo1ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Grupo1;
import contaweb.modelo.dao.Grupo1Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("grupo1Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Grupo1Service implements Serializable{

	@Autowired
	private Grupo1Dao grupo1Dao;

	public void crear(Grupo1 grupo1) {
		try {
			if (consultar(grupo1) != null) {
				throw new HealthmanagerException(
						"Grupo1 ya se encuentra en la base de datos");
			}
			grupo1Dao.crear(grupo1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Grupo1 grupo1) {
		try {
			return grupo1Dao.actualizar(grupo1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Grupo1 consultar(Grupo1 grupo1) {
		try {
			return grupo1Dao.consultar(grupo1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Grupo1 grupo1) {
		try {
			return grupo1Dao.eliminar(grupo1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Grupo1> listar(Map<String, Object> parameter) {
		try {
			return grupo1Dao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
