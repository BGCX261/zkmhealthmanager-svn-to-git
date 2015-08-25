/*
 * Orden_servicioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Detalle_grupos_procedimientosDao;
import healthmanager.modelo.dao.Detalle_ordenDao;
import healthmanager.modelo.dao.Grupos_procedimientosDao;
import healthmanager.modelo.dao.Orden_servicioDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.util.ServiciosDisponiblesUtils;

@Service("orden_servicioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Orden_servicioService implements Serializable{

	private String limit;

	@Autowired
	private Orden_servicioDao orden_servicioDao;
	@Autowired
	private Detalle_ordenDao detalle_ordenDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Grupos_procedimientosDao grupos_procedimientosDao;
	@Autowired
	private Detalle_grupos_procedimientosDao detalle_grupos_procedimientosDao;
	@Autowired
	private AdmisionDao admisionDao;

	@Autowired
	private ProcedimientosService procedimientosService;

	public synchronized Orden_servicio guardar(Map<String, Object> datos) {
		Orden_servicio orden_servicio = (Orden_servicio) datos
				.get("orden_servicio");
		try {
			List<Detalle_orden> lista_detalle = (List<Detalle_orden>) datos
					.get("lista_detalle");

			// log.info("lista_detalle ===> " + lista_detalle);
			if (orden_servicio.getId() == null) {
				// String codigo_orden = consecutivoService.obtenerConsecutivo(
				// orden_servicio.getCodigo_empresa(),
				// orden_servicio.getCodigo_sucursal(), "ORDEN_SERVICIO");
				//
				// //
				// orden_servicio.setId_prestador(orden_servicio.getCreacion_user());
				// orden_servicio.setCodigo_orden(codigo_orden);
				// if (consultar(orden_servicio) != null) {
				// throw new HealthmanagerException(
				// "Orden de servicio nro "
				// + codigo_orden
				// +
				// " ya se encuentra en la base de datos actualize el consecutivo de la orden de servicio");
				// }

				// anulamos ordenes anteriores
				Map<String, Object> mapOrdenesAnular = new HashMap<String, Object>();
				mapOrdenesAnular.put("codigo_empresa",
						orden_servicio.getCodigo_empresa());
				mapOrdenesAnular.put("codigo_sucursal",
						orden_servicio.getCodigo_sucursal());
				mapOrdenesAnular.put("codigo_paciente",
						orden_servicio.getCodigo_paciente());
						guardarAnulacionOrdenesAnteriores(mapOrdenesAnular);
				// log.info("Ordenes anuladas: " + anuladas + " para: "
				// + mapOrdenesAnular);

				orden_servicioDao.crear(orden_servicio);
				// consecutivoService.actualizarConsecutivo(
				// orden_servicio.getCodigo_empresa(),
				// orden_servicio.getCodigo_sucursal(), "ORDEN_SERVICIO",
				// orden_servicio.getCodigo_orden());
			} else {
				orden_servicioDao.actualizar(orden_servicio);
			}

			Admision admision = new Admision();
			admision.setCodigo_empresa(orden_servicio.getCodigo_empresa());
			admision.setCodigo_sucursal(orden_servicio.getCodigo_sucursal());
			admision.setNro_identificacion(orden_servicio.getCodigo_paciente());
			admision.setNro_ingreso(orden_servicio.getNro_ingreso());
			admision = admisionDao.consultar(admision);

			if (admision == null) {
				throw new ValidacionRunTimeException(
						"El paciente no tiene una admision valida");
			}

			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);

			if (manuales_tarifarios == null) {
				throw new ValidacionRunTimeException(
						"El paciente no tiene este servicio contratado");
			}

			// Registramos los procedimientos //
			Detalle_orden detalle_ordenAux = new Detalle_orden();
			detalle_ordenAux.setCodigo_empresa(orden_servicio
					.getCodigo_empresa());
			detalle_ordenAux.setCodigo_sucursal(orden_servicio
					.getCodigo_sucursal());
			detalle_ordenAux.setCodigo_orden(orden_servicio.getId());

            detalle_ordenDao.eliminar(detalle_ordenAux);
			// log.info("Cantidad: " + cantidad);

			int i = 0;
			for (Detalle_orden detalle_orden : lista_detalle) {
				detalle_orden.setCodigo_orden(orden_servicio.getId());
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("codigo_empresa", detalle_orden.getCodigo_empresa());
				param.put("codigo_sucursal", detalle_orden.getCodigo_sucursal());
				param.put("id_codigo_grupo",
						detalle_orden.getCodigo_procedimiento());
				// Verificamos si el procedimiento es un grupo
				// Si es un grupo lo registramos
				if (grupos_procedimientosDao.existe(param)) {
					param.put("codigo_grupo",
							detalle_orden.getCodigo_procedimiento());
					List<Detalle_grupos_procedimientos> detalle_grupos_procedimientos = detalle_grupos_procedimientosDao
							.listar(param);
					for (Detalle_grupos_procedimientos dtt_grupo : detalle_grupos_procedimientos) {
						detalle_orden.setConsecutivo(i + "");
						detalle_ordenDao.crear(ServiciosDisponiblesUtils
								.convertirDttGrupoDttOrden(dtt_grupo,
										detalle_orden, manuales_tarifarios));
						i++;
					}
				} else {
					detalle_orden.setConsecutivo(i + "");
					if (detalle_ordenDao.consultar(detalle_orden) != null) {
						throw new HealthmanagerException(
								"Procedimiento  de la fila " + (i + 1)
										+ " se encuentra repetido");
					}
					detalle_ordenDao.crear(detalle_orden);
					i++;
				}
			}

			orden_servicio.setLista_detalle(lista_detalle);

			return orden_servicio;
		} catch (Exception e) {
			/**
			 * Esto es cuando se presente un error, y la referencia de memoria
			 * obtenga el codigo.
			 * */
			if (orden_servicio != null) {
				orden_servicio.setCodigo_orden("");
				orden_servicio.setId(null);
			}
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Orden_servicio orden_servicio) {
		try {
			if (consultar(orden_servicio) != null) {
				throw new HealthmanagerException(
						"Orden_servicio ya se encuentra en la base de datos");
			}
			orden_servicioDao.crear(orden_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Orden_servicio orden_servicio) {
		try {
			return orden_servicioDao.actualizar(orden_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Orden_servicio consultar(Orden_servicio orden_servicio) {
		try {
			return orden_servicioDao.consultar(orden_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Orden_servicio orden_servicio) {
		try {
			return orden_servicioDao.eliminar(orden_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Orden_servicio> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return orden_servicioDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	
	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return orden_servicioDao.existe(parameters);
	}

	public int guardarAnulacionOrdenesAnteriores(Map<String, Object> param) {
		try {
			return orden_servicioDao.anularOrdenesAnteriores(param);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
