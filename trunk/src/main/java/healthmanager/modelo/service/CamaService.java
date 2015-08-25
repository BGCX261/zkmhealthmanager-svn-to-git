/*
 * CamaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cama;
import healthmanager.modelo.dao.CamaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("camaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CamaService implements Serializable{

	@Autowired
	private CamaDao camaDao;

	public void crear(Cama cama) {
		try {
			Integer codigo = camaDao.nextConsecutivo(cama);
			cama.setCodigo((codigo != null ? codigo + 1 : 1));
			camaDao.crear(cama);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Cama cama) {
		try {
			return camaDao.actualizar(cama);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cama consultar(Cama cama) {
		try {
			return camaDao.consultar(cama);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cama cama) {
		try {
			return camaDao.eliminar(cama);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cama> listar(Map<String, Object> parameter) {
		try {
			return camaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
