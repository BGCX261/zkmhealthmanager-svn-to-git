/*
 * PabellonServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Pabellon;
import healthmanager.modelo.dao.PabellonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("pabellonService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PabellonService implements Serializable{

	@Autowired
	private PabellonDao pabellonDao;

	public void crear(Pabellon pabellon) {
		try {
			Integer codigo = pabellonDao.nextConsecutivo(pabellon);
			pabellon.setCodigo((codigo != null ? codigo + 1 : 1));
			pabellonDao.crear(pabellon);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Pabellon pabellon) {
		try {
			return pabellonDao.actualizar(pabellon);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Pabellon consultar(Pabellon pabellon) {
		try {
			return pabellonDao.consultar(pabellon);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Pabellon pabellon) {
		try {
			return pabellonDao.eliminar(pabellon);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Pabellon> listar(Map<String, Object> parameter) {
		try {
			return pabellonDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
