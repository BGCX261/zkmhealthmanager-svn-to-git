/*
 * Evolucion_menorServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Evolucion_menor;
import healthmanager.modelo.dao.Evolucion_menorDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("evolucion_menorService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Evolucion_menorService implements Serializable{

	private String limit;

	@Autowired
	private Evolucion_menorDao evolucion_menorDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Evolucion_menor guardar(Map<String, Object> datos) {
		try {

			Evolucion_menor menor = (Evolucion_menor) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						menor.getCodigo_empresa(), menor.getCodigo_sucursal(),
						"EVOLUCION_MENOR");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				menor.setCodigo_historia(codigo_historia);
				if (consultar(menor) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				evolucion_menorDao.crear(menor);
				consecutivoService.actualizarConsecutivo(
						menor.getCodigo_empresa(), menor.getCodigo_sucursal(),
						"EVOLUCION_MENOR", menor.getCodigo_historia());
			} else {
				evolucion_menorDao.actualizar(menor);
			}
			return menor;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Evolucion_menor evolucion_menor) {
		try {
			if (consultar(evolucion_menor) != null) {
				throw new HealthmanagerException(
						"Evolucion_menor ya se encuentra en la base de datos");
			}
			evolucion_menorDao.crear(evolucion_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Evolucion_menor evolucion_menor) {
		try {
			return evolucion_menorDao.actualizar(evolucion_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Evolucion_menor consultar(Evolucion_menor evolucion_menor) {
		try {
			return evolucion_menorDao.consultar(evolucion_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Evolucion_menor evolucion_menor) {
		try {
			return evolucion_menorDao.eliminar(evolucion_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Evolucion_menor> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return evolucion_menorDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
