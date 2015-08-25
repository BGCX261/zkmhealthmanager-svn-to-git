/*
 * IpsServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Ips;
import healthmanager.modelo.dao.IpsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("ipsService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class IpsService implements Serializable{

	private String limit;

	@Autowired
	private IpsDao ipsDao;

	public void crear(Ips ips) {
		try {
			if (consultar(ips) != null) {
				throw new HealthmanagerException(
						"Ips ya se encuentra en la base de datos");
			}
			ipsDao.crear(ips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Ips ips) {
		try {
			return ipsDao.actualizar(ips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Ips consultar(Ips ips) {
		try {
			return ipsDao.consultar(ips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Ips ips) {
		try {
			return ipsDao.eliminar(ips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Ips> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return ipsDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
