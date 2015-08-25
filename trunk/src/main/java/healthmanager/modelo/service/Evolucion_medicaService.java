/*
 * Evolucion_medicaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_evolucion;
import healthmanager.modelo.bean.Evolucion_medica;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Detalle_evolucionDao;
import healthmanager.modelo.dao.Evolucion_medicaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("evolucion_medicaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Evolucion_medicaService implements Serializable{

	private String limit;

	@Autowired
	private Evolucion_medicaDao evolucion_medicaDao;
	@Autowired
	private Detalle_evolucionDao detalle_evolucionDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Evolucion_medica guardar(Map<String, Object> datos) {
		try {
			Evolucion_medica evolucion_medica = (Evolucion_medica) datos
					.get("evolucion_medica");
			List<Detalle_evolucion> lista_detalle = (List<Detalle_evolucion>) datos
					.get("lista_detalle");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_evolucion = consecutivoService
						.crearConsecutivo(
								evolucion_medica.getCodigo_empresa(),
								evolucion_medica.getCodigo_sucursal(),
								"EVOLUCION_MEDICA");
				evolucion_medica.setCodigo_evolucion(codigo_evolucion);
				if (consultar(evolucion_medica) != null) {
					throw new HealthmanagerException(
							"Evolucion medica nro "
									+ codigo_evolucion
									+ " ya se encuentra en la base de datos actualize el consecutivo de la evolucion");
				}
				evolucion_medicaDao.crear(evolucion_medica);
				consecutivoService.actualizarConsecutivo(
						evolucion_medica.getCodigo_empresa(),
						evolucion_medica.getCodigo_sucursal(),
						"EVOLUCION_MEDICA",
						evolucion_medica.getCodigo_evolucion());
			} else {
				evolucion_medicaDao.actualizar(evolucion_medica);
			}

			// Registramos las evoluciones //
			Detalle_evolucion detalle_evolucionAux = new Detalle_evolucion();
			detalle_evolucionAux.setCodigo_empresa(evolucion_medica
					.getCodigo_empresa());
			detalle_evolucionAux.setCodigo_sucursal(evolucion_medica
					.getCodigo_sucursal());
			detalle_evolucionAux.setCodigo_evolucion(evolucion_medica
					.getCodigo_evolucion());
			detalle_evolucionDao.eliminar(detalle_evolucionAux);
			int i = 0;
			for (Detalle_evolucion detalle_evolucion : lista_detalle) {
				detalle_evolucion.setCodigo_evolucion(evolucion_medica
						.getCodigo_evolucion());
				if (detalle_evolucionDao.consultar(detalle_evolucion) != null) {
					throw new HealthmanagerException("Evolucion  de la fila "
							+ (i + 1) + " se encuentra repetido");
				}
				detalle_evolucionDao.crear(detalle_evolucion);
				i++;
			}
			evolucion_medica.setLista_detalle(lista_detalle);

			return evolucion_medica;

		} catch (Exception e) {
			
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Evolucion_medica evolucion_medica) {
		try {
			if (consultar(evolucion_medica) != null) {
				throw new HealthmanagerException(
						"Evolucion_medica ya se encuentra en la base de datos");
			}
			evolucion_medicaDao.crear(evolucion_medica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Evolucion_medica evolucion_medica) {
		try {
			return evolucion_medicaDao.actualizar(evolucion_medica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Evolucion_medica consultar(Evolucion_medica evolucion_medica) {
		try {
			return evolucion_medicaDao.consultar(evolucion_medica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Evolucion_medica evolucion_medica) {
		try {
			return evolucion_medicaDao.eliminar(evolucion_medica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Evolucion_medica> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return evolucion_medicaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
