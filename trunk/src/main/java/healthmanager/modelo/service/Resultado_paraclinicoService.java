/*
 * Resultado_paraclinicoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Resultado_paraclinico;
import healthmanager.modelo.dao.Resultado_paraclinicoDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("resultado_paraclinicoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Resultado_paraclinicoService implements Serializable{

	@Autowired
	private Resultado_paraclinicoDao resultado_paraclinicoDao;

	public void crear(Resultado_paraclinico resultado_paraclinico) {
		try {
			if (consultar(resultado_paraclinico) != null) {
				throw new HealthmanagerException(
						"Resultado_paraclinico ya se encuentra en la base de datos");
			}
			resultado_paraclinicoDao.crear(resultado_paraclinico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Resultado_paraclinico resultado_paraclinico) {
		try {
			return resultado_paraclinicoDao.actualizar(resultado_paraclinico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Resultado_paraclinico consultar(
			Resultado_paraclinico resultado_paraclinico) {
		try {
			return resultado_paraclinicoDao.consultar(resultado_paraclinico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Resultado_paraclinico resultado_paraclinico) {
		try {
			return resultado_paraclinicoDao.eliminar(resultado_paraclinico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Resultado_paraclinico> listar(Map<String, Object> parameter) {
		try {
			return resultado_paraclinicoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.resultado_paraclinicoDao.existe(parameters);
	}

	public int obtenerConsecutivo(Resultado_paraclinico resultado_paraclinico) {
		try {
			return resultado_paraclinicoDao
					.obtenerConsecutivo(resultado_paraclinico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
