/*
 * BancoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Banco;
import contaweb.modelo.dao.BancoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("bancoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class BancoService implements Serializable{

	private String limit;

	@Autowired
	private BancoDao bancoDao;

	public void crear(Banco banco) {
		try {
			if (consultar(banco) != null) {
				throw new HealthmanagerException(
						"Banco ya se encuentra en la base de datos");
			}
			bancoDao.crear(banco);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Banco banco) {
		try {
			return bancoDao.actualizar(banco);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Banco consultar(Banco banco) {
		try {
			return bancoDao.consultar(banco);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Banco banco) {
		try {
			return bancoDao.eliminar(banco);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Banco> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return bancoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
