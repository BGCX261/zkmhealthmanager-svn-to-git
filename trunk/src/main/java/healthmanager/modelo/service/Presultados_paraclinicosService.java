/*
 * Presultados_paraclinicosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Presultados_paraclinicos;
import healthmanager.modelo.dao.Presultados_paraclinicosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("presultados_paraclinicosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Presultados_paraclinicosService implements Serializable{

	private String limit;

	@Autowired
	private Presultados_paraclinicosDao presultados_paraclinicosDao;

	public void crear(Presultados_paraclinicos presultados_paraclinicos) {
		try {
			if (consultar(presultados_paraclinicos) != null) {
				throw new HealthmanagerException(
						"Presultados_paraclinicos ya se encuentra en la base de datos");
			}
			presultados_paraclinicosDao.crear(presultados_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Presultados_paraclinicos presultados_paraclinicos) {
		try {
			return presultados_paraclinicosDao
					.actualizar(presultados_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Presultados_paraclinicos consultar(
			Presultados_paraclinicos presultados_paraclinicos) {
		try {
			return presultados_paraclinicosDao
					.consultar(presultados_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Presultados_paraclinicos mostrar_ultimo(
			Presultados_paraclinicos presultados_paraclinicos) {
		try {
			return presultados_paraclinicosDao
					.mostrar_ultimo(presultados_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Presultados_paraclinicos presultados_paraclinicos) {
		try {
			return presultados_paraclinicosDao
					.eliminar(presultados_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Presultados_paraclinicos> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return presultados_paraclinicosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listar_validacion_paciente(
			Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return presultados_paraclinicosDao
					.listar_validacion_paciente(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.presultados_paraclinicosDao.existe(parameters);
	}

}
