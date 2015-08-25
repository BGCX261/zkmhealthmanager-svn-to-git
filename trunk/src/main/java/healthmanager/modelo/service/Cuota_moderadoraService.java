/*
 * Cuota_moderadoraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cuota_moderadora;
import healthmanager.modelo.dao.Cuota_moderadoraDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("cuota_moderadoraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cuota_moderadoraService implements Serializable{

	private String limit;

	@Autowired
	private Cuota_moderadoraDao cuota_moderadoraDao;

	public void guardar(List<Cuota_moderadora> lista_cuotas) {
		try {
			for (Cuota_moderadora cuota_moderadora : lista_cuotas) {
				if (cuota_moderadoraDao.consultar(cuota_moderadora) == null) {
					cuota_moderadoraDao.crear(cuota_moderadora);
				} else {
					cuota_moderadoraDao.actualizar(cuota_moderadora);
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Cuota_moderadora cuota_moderadora) {
		try {
			if (consultar(cuota_moderadora) != null) {
				throw new HealthmanagerException(
						"Cuota_moderadora ya se encuentra en la base de datos");
			}
			cuota_moderadoraDao.crear(cuota_moderadora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Cuota_moderadora cuota_moderadora) {
		try {
			return cuota_moderadoraDao.actualizar(cuota_moderadora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Cuota_moderadora consultar(Cuota_moderadora cuota_moderadora) {
		try {
			return cuota_moderadoraDao.consultar(cuota_moderadora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void eliminar(List<Cuota_moderadora> lista_cuotas) {
		try {
			for (Cuota_moderadora cuota_moderadora : lista_cuotas) {
				if (cuota_moderadoraDao.consultar(cuota_moderadora) != null) {
					cuota_moderadoraDao.eliminar(cuota_moderadora);
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Cuota_moderadora cuota_moderadora) {
		try {
			return cuota_moderadoraDao.eliminar(cuota_moderadora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Cuota_moderadora> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return cuota_moderadoraDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
