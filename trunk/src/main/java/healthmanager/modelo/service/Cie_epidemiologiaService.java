/*
 * Cie_epidemiologiaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.dao.Cie_epidemiologiaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("cie_epidemiologiaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cie_epidemiologiaService implements Serializable{

	@Autowired
	private Cie_epidemiologiaDao cie_epidemiologiaDao;

	public void crear(Cie_epidemiologia cie_epidemiologia) {
		try {
			if (consultar(cie_epidemiologia) != null) {
				throw new HealthmanagerException(
						"Cie_epidemiologia ya se encuentra en la base de datos");
			}
			cie_epidemiologiaDao.crear(cie_epidemiologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Cie_epidemiologia cie_epidemiologia) {
		try {
			return cie_epidemiologiaDao.actualizar(cie_epidemiologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cie_epidemiologia consultar(Cie_epidemiologia cie_epidemiologia) {
		try {
			return cie_epidemiologiaDao.consultar(cie_epidemiologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cie_epidemiologia cie_epidemiologia) {
		try {
			return cie_epidemiologiaDao.eliminar(cie_epidemiologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cie_epidemiologia> listar(Map<String, Object> parameters) {
		try {
			return cie_epidemiologiaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cie_epidemiologia> listar_cie(Map<String, Object> parameters) {
		try {
			return cie_epidemiologiaDao.listar_cie(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


	public boolean existe(Map<String, Object> parameters) {
		return cie_epidemiologiaDao.existe(parameters);
	}

}