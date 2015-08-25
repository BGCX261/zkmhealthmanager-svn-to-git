/*
 * Ordenes_medicasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Ordenes_medicas;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.dao.Ordenes_medicasDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("ordenes_medicasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Ordenes_medicasService implements Serializable{

	@Autowired
	private Ordenes_medicasDao ordenes_medicasDao;

	@Autowired
	private Receta_ripsService receta_ripsService;

	@Autowired
	private Orden_servicioService orden_servicioService;

	@Autowired
	private ConsecutivoService consecutivoService;

	private String limit;

	public void crear(Ordenes_medicas ordenes_medicas) {
		try {
			if (consultar(ordenes_medicas) != null) {
				throw new HealthmanagerException(
						"Ordenes_medicas ya se encuentra en la base de datos");
			}
			ordenes_medicasDao.crear(ordenes_medicas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarDatos(Map<String, Object> datos) {
		try {

			String accion = (String) datos.get("accion");
			Ordenes_medicas ordenes_medicas = (Ordenes_medicas) datos
					.get("ordenes_medicas");

			Map<String, Object> mapRecetas = (Map<String, Object>) datos
					.get("receta_medica");
			Map<String, Object> mapOrdenServicio = (Map<String, Object>) datos
					.get("orden_servicio");

			Orden_servicio orden_servicio = (Orden_servicio) mapOrdenServicio
					.get("orden_servicio");

			Admision admision_seleccionada = (Admision) datos.get("admision");

			orden_servicio.setCodigo_ordenador(ordenes_medicas
					.getCodigo_medico());

			orden_servicio.setTipo_orden(ordenes_medicas.getTipo());
			orden_servicio.setNro_ingreso(ordenes_medicas.getNro_ingreso());

			Receta_rips receta_rips = (Receta_rips) mapRecetas
					.get("receta_rips");
			receta_rips.setCodigo_prestador(ordenes_medicas.getCodigo_medico());
			receta_rips.setTipo_receta(ordenes_medicas.getTipo());

			if (admision_seleccionada != null) {
				orden_servicio.setCodigo_dx(admision_seleccionada
						.getDiagnostico_ingreso());
				receta_rips.setCodigo_dx(admision_seleccionada
						.getDiagnostico_ingreso());
			} else {
				orden_servicio.setCodigo_dx("Z000");
				receta_rips.setCodigo_dx("Z000");
			}

			/* guardamos receta */
			List<Detalle_orden> detalle_ordens = (List<Detalle_orden>) mapOrdenServicio
					.get("lista_detalle");
			List<Detalle_receta_rips> detalle_receta_rips = (List<Detalle_receta_rips>) mapRecetas
					.get("lista_detalle");

			if (!detalle_receta_rips.isEmpty()) {
				mapRecetas.put("receta_rips",
						receta_ripsService.guardar(mapRecetas));
			} else {
				mapRecetas.put("receta_rips", null);
			}

			if (!detalle_ordens.isEmpty()) {
				orden_servicio = orden_servicioService
						.guardar(mapOrdenServicio);
				// log.info("orden_servicio_aux ===> " + orden_servicio);
				mapOrdenServicio.put("orden_servicio", orden_servicio);
			} else {
				mapOrdenServicio.put("orden_servicio", null);
			}

			if (orden_servicio != null)
				ordenes_medicas
						.setCodigo_orden_servicio(orden_servicio.getId());

			if (receta_rips != null)
				ordenes_medicas
						.setCodigo_receta(receta_rips.getCodigo_receta());

			if (accion.equalsIgnoreCase("registrar")) {
				String consecutivo = consecutivoService.crearConsecutivo(
						ordenes_medicas.getCodigo_empresa(),
						ordenes_medicas.getCodigo_sucursal(),
						IConstantes.CONS_ORDENES_MEDICAS);
				String codigo_orden = consecutivoService.getZeroFill(
						consecutivo, 10);

				ordenes_medicas.setCodigo_orden(codigo_orden);

				if (consultar(ordenes_medicas) != null) {
					throw new HealthmanagerException(
							"La orden medica ya se encuentra en la base de datos");
				}

				ordenes_medicasDao.crear(ordenes_medicas);

				consecutivoService.actualizarConsecutivo(
						ordenes_medicas.getCodigo_empresa(),
						ordenes_medicas.getCodigo_sucursal(),
						IConstantes.CONS_ORDENES_MEDICAS, codigo_orden);
			} else {
				if (consultar(ordenes_medicas) == null)
					throw new Exception(
							"La orden medica que desea actualizar no existe en la base de datos");
				actualizar(ordenes_medicas);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Ordenes_medicas ordenes_medicas) {
		try {
			return ordenes_medicasDao.actualizar(ordenes_medicas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Ordenes_medicas consultar(Ordenes_medicas ordenes_medicas) {
		try {
			return ordenes_medicasDao.consultar(ordenes_medicas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Ordenes_medicas ordenes_medicas) {
		try {
			return ordenes_medicasDao.eliminar(ordenes_medicas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Ordenes_medicas> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return ordenes_medicasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return ordenes_medicasDao.existe(parameters);
	}

}