/*
 * Edad_vencimientoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Edad_vencimiento;
import healthmanager.modelo.dao.Edad_vencimientoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("edad_vencimientoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Edad_vencimientoService implements Serializable{

	private String limit;

	@Autowired
	private Edad_vencimientoDao edad_vencimientoDao;

	public void crear(Edad_vencimiento edad_vencimiento) {
		try {
			if (consultar(edad_vencimiento) != null) {
				throw new HealthmanagerException(
						"Edad_vencimiento ya se encuentra en la base de datos");
			}
			edad_vencimientoDao.crear(edad_vencimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Edad_vencimiento edad_vencimiento) {
		try {
			return edad_vencimientoDao.actualizar(edad_vencimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Edad_vencimiento consultar(Edad_vencimiento edad_vencimiento) {
		try {
			return edad_vencimientoDao.consultar(edad_vencimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Edad_vencimiento edad_vencimiento) {
		try {
			return edad_vencimientoDao.eliminar(edad_vencimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Edad_vencimiento> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return edad_vencimientoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
