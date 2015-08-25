/*
 * Impresion_diagnosticaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.dao.Impresion_diagnosticaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("impresion_diagnosticaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Impresion_diagnosticaService implements Serializable{

	@Autowired
	private Impresion_diagnosticaDao impresion_diagnosticaDao;

	public void guardarDatos(Impresion_diagnostica impresion_diagnostica) {
		try {
			if (consultar(impresion_diagnostica) != null) {
				impresion_diagnosticaDao.actualizar(impresion_diagnostica);
			} else {
				impresion_diagnosticaDao.crear(impresion_diagnostica);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Impresion_diagnostica impresion_diagnostica) {
		try {
			if (consultar(impresion_diagnostica) != null) {
				throw new HealthmanagerException(
						"Impresion_diagnostica ya se encuentra en la base de datos");
			}
			impresion_diagnosticaDao.crear(impresion_diagnostica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Impresion_diagnostica impresion_diagnostica) {
		try {
			return impresion_diagnosticaDao.actualizar(impresion_diagnostica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Impresion_diagnostica consultar(
			Impresion_diagnostica impresion_diagnostica) {
		try {
			return impresion_diagnosticaDao.consultar(impresion_diagnostica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Impresion_diagnostica impresion_diagnostica) {
		try {
			return impresion_diagnosticaDao.eliminar(impresion_diagnostica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Impresion_diagnostica> listar(Map<String, Object> parameters) {
		try {
			return impresion_diagnosticaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Impresion_diagnostica> listar_paciente_contuberculosis_lepra(
			Map<String, Object> parameters) {
		try {
			return impresion_diagnosticaDao
					.listar_paciente_contuberculosis_lepra(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return impresion_diagnosticaDao.existe(parameters);
	}

}