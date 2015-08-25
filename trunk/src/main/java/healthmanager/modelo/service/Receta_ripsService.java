/*
 * Receta_ripsServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Detalle_solicitud_tecnico;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Solicitud_tecnico;
import healthmanager.modelo.dao.Detalle_receta_ripsDao;
import healthmanager.modelo.dao.Detalle_solicitud_tecnicoDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.Receta_ripsDao;
import healthmanager.modelo.dao.Solicitud_tecnicoDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConsecutivos;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.Main;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.service.ArticuloService;
import contaweb.modelo.service.Grupo1Service;
import contaweb.modelo.service.Tarifa_medicamentoService;

@Service("receta_ripsService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Receta_ripsService implements Serializable{

	@Autowired
	private Receta_ripsDao receta_ripsDao;
	@Autowired
	private Detalle_receta_ripsDao detalle_receta_ripsDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Solicitud_tecnicoDao solicitud_tecnicoDao;
	@Autowired
	private Detalle_solicitud_tecnicoDao detalle_solicitud_tecnicoDao;
	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private ArticuloService articuloService;
	@Autowired
	private Copago_estratoService copago_estratoService;
	@Autowired
	private Tarifa_medicamentoService tarifa_medicamentoService;
	@Autowired
	private Grupo1Service grupo1Service;

	public synchronized Receta_rips guardar(Map<String, Object> datos) {
		Receta_rips receta_rips = (Receta_rips) datos.get("receta_rips");
		try {
			List<Detalle_receta_rips> lista_detalle = (List<Detalle_receta_rips>) datos
					.get("lista_detalle");
			Solicitud_tecnico solicitud_tecnico = (Solicitud_tecnico) datos
					.get("solicitud_tecnico");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				// String codigo_receta = consecutivoService.obtenerConsecutivo(
				// receta_rips.getCodigo_empresa(),
				// receta_rips.getCodigo_sucursal(), "RECETA_RIPS");
				// receta_rips.setCodigo_receta(codigo_receta);
				// if (consultar(receta_rips) != null) {
				// throw new HealthmanagerException(
				// "Receta nro "
				// + codigo_receta
				// +
				// " ya se encuentra en la base de datos actualize el consecutivo de la receta");
				// }
				receta_ripsDao.crear(receta_rips);
				// consecutivoService.actualizarConsecutivo(
				// receta_rips.getCodigo_empresa(),
				// receta_rips.getCodigo_sucursal(), "RECETA_RIPS",
				// receta_rips.getCodigo_receta());
			} else {
				receta_ripsDao.actualizar(receta_rips);
			}

			Detalle_receta_rips detalle_receta_ripsAux = new Detalle_receta_rips();
			detalle_receta_ripsAux.setCodigo_empresa(receta_rips
					.getCodigo_empresa());
			detalle_receta_ripsAux.setCodigo_sucursal(receta_rips
					.getCodigo_sucursal());
			detalle_receta_ripsAux.setCodigo_receta(receta_rips
					.getCodigo_receta());
			detalle_receta_ripsDao.eliminar(detalle_receta_ripsAux);
			int i = 0;
			for (Detalle_receta_rips detalle_receta_rips : lista_detalle) {
				detalle_receta_rips.setCodigo_receta(receta_rips
						.getCodigo_receta());
				if (detalle_receta_ripsDao.consultar(detalle_receta_rips) != null) {
					throw new HealthmanagerException("Medicamento  de la fila "
							+ (i + 1) + " se encuentra repetido");
				}
				detalle_receta_ripsDao.crear(detalle_receta_rips);
				i++;
			}

			receta_rips.setLista_detalle(lista_detalle);

			// Verificamos si ahi una solcitud y la registramos
			if (solicitud_tecnico != null) {
				String codigo = consecutivoService.crearConsecutivo(
						solicitud_tecnico.getCodigo_empresa(),
						solicitud_tecnico.getCodigo_sucursal(),
						IConsecutivos.CONS_SOLICITUD_TECNICO);
				solicitud_tecnico.setCodigo(codigo);
				solicitud_tecnico.setCodigo_receta(receta_rips
						.getCodigo_receta());
				solicitud_tecnicoDao.crear(solicitud_tecnico);

				/* guardamos los detalles */
				int cons = 1;
				for (Detalle_solicitud_tecnico detalleSolicitudTecnico : solicitud_tecnico
						.getDetalleSolicitudTecnicos()) {
					detalleSolicitudTecnico.setConsecutivo(cons++ + "");
					detalleSolicitudTecnico.setCodigo_solicitud_tecnico(codigo);
					detalle_solicitud_tecnicoDao.crear(detalleSolicitudTecnico);
				}
				/* fin */
				consecutivoService.actualizarConsecutivo(
						solicitud_tecnico.getCodigo_empresa(),
						solicitud_tecnico.getCodigo_sucursal(),
						IConsecutivos.CONS_SOLICITUD_TECNICO, codigo);
			}

			return receta_rips;
		} catch (Exception e) {
			if (receta_rips != null) {
				receta_rips.setCodigo_receta("");
			}
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Receta_rips receta_rips) {
		try {
			if (consultar(receta_rips) != null) {
				throw new HealthmanagerException(
						"Receta_rips ya se encuentra en la base de datos");
			}
			receta_ripsDao.crear(receta_rips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Receta_rips receta_rips) {
		try {
			return receta_ripsDao.actualizar(receta_rips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Receta_rips consultar(Receta_rips receta_rips) {
		try {
			return receta_ripsDao.consultar(receta_rips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Receta_rips receta_rips) {
		try {
			return receta_ripsDao.eliminar(receta_rips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Receta_rips> listar(Map<String, Object> parameter) {
		try {
			return receta_ripsDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


	public Receta_rips crearWithSolicitudTecnica(ServiceLocatorWeb serviceLocator,
			Map map, Solicitud_tecnico solicitudTecnico) throws Exception {

		String codigo = Main.obtenerConsecutivo(serviceLocator,
				solicitudTecnico.getCodigo_empresa(),
				solicitudTecnico.getCodigo_sucursal(), "SOLICITUD_TECNICO");

		Receta_rips recetaRips = guardar(map);
		// solicitudTecnico.setCodigo_receta(recetaRips.getCodigo_receta());
		solicitudTecnico.setCodigo(codigo);
		serviceLocator.getSolicitudTecnicoService().crear(solicitudTecnico);

		/* guardamos los detalles */
		int cons = 0;
		for (Detalle_solicitud_tecnico detalleSolicitudTecnico : solicitudTecnico
				.getDetalleSolicitudTecnicos()) {
			detalleSolicitudTecnico.setConsecutivo(++cons + "");
			detalleSolicitudTecnico.setCodigo_solicitud_tecnico(codigo);
			serviceLocator.getDetalleSolicitudTecnicoService().crear(
					detalleSolicitudTecnico);
		}
		/* fin */

		/* actualizamos el consecutivo */
		Main.actualizarConsecutivo(serviceLocator,
				recetaRips.getCodigo_empresa(),
				recetaRips.getCodigo_sucursal(), "SOLICITUD_TECNICO", codigo);
		return recetaRips;
	}

	public void actualizar(Map<String, Object> map) {
		try {
			Receta_rips receta_rips = (Receta_rips) map.get("receta");
			List<Detalle_receta_rips> detalle_receta_rips = (List<Detalle_receta_rips>) map
					.get("dtt_receta");
			ServiceLocatorWeb serviceLocator = (ServiceLocatorWeb) map
					.get("serviceLocator");
			actualizar(receta_rips);
			// log.info("Actualizar receta: " + receta_rips);

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(receta_rips.getCodigo_empresa());
			paciente.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
			paciente.setNro_identificacion(receta_rips.getNro_identificacion());
			paciente = pacienteDao.consultar(paciente);

			if (paciente == null) {
				throw new ValidacionRunTimeException(
						"El paciente con el nro de identificacion: "
								+ receta_rips.getNro_identificacion()
								+ " no existe");
			}

			Map<String, Object> map_servicios = new HashMap<String, Object>();
			map_servicios.put("articuloService", articuloService);
			map_servicios.put("copago_estratoService", copago_estratoService);
			map_servicios.put("tarifa_medicamentoService",
					tarifa_medicamentoService);
			map_servicios.put("grupo1Service", grupo1Service);

			Servicios_contratados servicios_contratados = ServiciosDisponiblesUtils
					.getServiciosEspecificos(
							paciente,
							serviceLocator,
							ServiciosDisponiblesUtils.CODSER_FARMACIA_MEDICAMENTOS);
			if (servicios_contratados != null) {
				Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
						.getManuales_tarifarios(servicios_contratados);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("manual_tarifario", manuales_tarifarios);
				params.put("grupo", "01");// 01 de medicamentos
				for (Detalle_receta_rips dtt_temp : detalle_receta_rips) {
					params.put("codigo_articulo", dtt_temp.getCodigo_articulo());
					Map<String, Object> mapMedicamento = Utilidades
							.getMedicamentos(params, map_servicios);
					if (mapMedicamento != null) {
						double valor_unitario = (Double) mapMedicamento
								.get("valor_unitario");
						dtt_temp.setValor_unitario(valor_unitario);
						dtt_temp.setValor_total(valor_unitario
								* dtt_temp.getCantidad_recetada());
						detalle_receta_ripsDao.actualizar(dtt_temp);
					}
				}
			} else {
				throw new ValidacionRunTimeException(
						"El paciente con el nro de identificacion "
								+ receta_rips.getNro_identificacion()
								+ " no tiene el servicio de farmacia activo, verificar en los contratos");
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public Integer totalResultados(Map<String, Object> parameters) {
		try {
			return receta_ripsDao.totalResultados(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
