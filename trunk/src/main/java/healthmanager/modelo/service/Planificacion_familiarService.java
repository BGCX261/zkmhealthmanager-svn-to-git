/*
 * Planificacion_familiarServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Planificacion_familiar;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.Planificacion_familiarDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

@Service("planificacion_familiarService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Planificacion_familiarService implements Serializable{

	@Autowired
	private Planificacion_familiarDao planificacion_familiarDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private Orden_autorizacionService orden_autorizacionService;
	@Autowired
	private Receta_ripsService receta_ripsService;
	@Autowired
	private Orden_servicioService orden_servicioService;
	@Autowired
	private Impresion_diagnosticaService impresion_diagnosticaService;
	@Autowired
	private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;
	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;
	@Autowired
	private Remision_internaService remision_internaService;
	@Autowired
	private Agudeza_visualService agudeza_visualService;

	@Autowired
	private GeneralExtraService generalExtraService;
	// manuel
	@Autowired
	private CitasDao citasDao;

	public void guardarDatos(Map<String, Object> datos) {
		try {
			Planificacion_familiar planificacion_familiar = (Planificacion_familiar) datos
					.get("historia_clinica");
			Admision admision = (Admision) datos.get("admision");
			if (admision == null) {
				throw new Exception("No hay admision que actualizar");
			}

			String anio_mapa = (String) datos.get("anio");
			Integer anio = ConvertidorDatosUtil.convertirDatot(anio_mapa);
			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);

			String accion = (String) datos.get("accion");
			Citas cita = (Citas) datos.get("cita_seleccionada");
			Map<String, Object> mapRecetas = (Map<String, Object>) datos
					.get("receta_medica");
			Map<String, Object> mapOrdenServicio = (Map<String, Object>) datos
					.get("orden_servicio");

			boolean cobrar_agudeza = (Boolean) datos.get("cobrar_agudeza");

			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");

			Agudeza_visual agudeza_visual = (Agudeza_visual) datos
					.get("agudeza_visual");

			agudeza_visual.setCodigo_historia(planificacion_familiar
					.getCodigo_historia());
			agudeza_visual.setIdentificacion(admision.getPaciente()
					.getNro_identificacion());

			if (cobrar_agudeza) {
				Map<String, Object> mapServicios = Utilidades.getProcedimiento(
						manuales_tarifarios, new Long(
								IConstantes.PROCEDIMIENTO_AGUDEZA_VISUAL),
						ServiciosDisponiblesUtils.getServiceLocator());
				ServiciosDisponiblesUtils.generarDatosProcedimientos(admision,
						IConstantes.PROCEDIMIENTO_AGUDEZA_VISUAL, mapServicios,
						manuales_tarifarios);
			}

			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna.setCodigo_historia(planificacion_familiar
					.getCodigo_historia());
			remision_interna.setFecha_inicio(planificacion_familiar
					.getFecha_inicial());

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(planificacion_familiar
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(planificacion_familiar
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(planificacion_familiar
					.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(planificacion_familiar
					.getCreacion_user());

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);

				planificacion_familiar.setCodigo_historia(historia_clinica
						.getCodigo_historia());

				planificacion_familiarDao.crear(planificacion_familiar);

				remision_interna.setCodigo_historia(planificacion_familiar
						.getCodigo_historia());
				agudeza_visual.setCodigo_historia(planificacion_familiar
						.getCodigo_historia());

			} else {

				if (generalExtraService.consultar(historia_clinica) != null) {
					if (consultar(planificacion_familiar) == null)
						throw new Exception(
								"El dato que desea actualizar no existe en la base de datos");
					planificacion_familiarDao
							.actualizar(planificacion_familiar);
				}

			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
			}

			if (cobrar_agudeza) {
				if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
					if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
						if (agudeza_visualService.consultar(agudeza_visual) == null) {
							agudeza_visualService.crear(agudeza_visual);
						} else {
							agudeza_visualService.actualizar(agudeza_visual);
						}
					}
				}
			}

			Orden_servicio orden_servicio = (Orden_servicio) ((Map<String, Object>) datos
					.get("orden_servicio")).get("orden_servicio");

			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) datos
					.get("impresion_diagnostica");

			orden_servicio.setCodigo_dx(impresion_diagnostica
					.getCie_principal());

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
				mapOrdenServicio.put("orden_servicio",
						orden_servicioService.guardar(mapOrdenServicio));
			} else {
				mapOrdenServicio.put("orden_servicio", null);
			}
			admision.setCodigo_medico(historia_clinica.getPrestador());
			admision.setAtendida(true);

			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());
			admisionDao.actualizar(admision);
			// Creamos autorizacion
			Map<String, Object> mapDatos = (Map<String, Object>) datos
					.get("autorizacion");
			if (mapDatos != null) {
				orden_autorizacionService.guardarOrden(mapDatos,
						impresion_diagnostica, admision);
			}

			if (cita != null) {
				cita.setEstado("2");
				citasDao.actualizar(cita);
			}

			if (planificacion_familiar.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				admision.setPrimera_vez("S");
			} else {
				admision.setPrimera_vez("N");
			}

			/* generamos la consulta */
			crearConsulta(planificacion_familiar, admision,
					impresion_diagnostica);

			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					planificacion_familiar.getCodigo_historia(), datos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					planificacion_familiar.getCreacion_user());

			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) datos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad.setNro_historia(planificacion_familiar
						.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							planificacion_familiar.getCodigo_empresa(),
							planificacion_familiar.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							planificacion_familiar.getCodigo_empresa(),
							planificacion_familiar.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9, codigo_anexo);
				}
			} else {
				anexo9_entidad = new Anexo9_entidad();
				anexo9_entidad.setCodigo_empresa(admision.getCodigo_empresa());
				anexo9_entidad
						.setCodigo_sucursal(admision.getCodigo_sucursal());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				anexo9_entidad.setCodigo_paciente(admision
						.getNro_identificacion());
				anexo9_entidadDao.eliminar(anexo9_entidad);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

		Planificacion_familiar planificacion_familiar = (Planificacion_familiar) historiasConsultas;
		Map map = new HashMap();
		map.put("codigo_empresa", planificacion_familiar.getCodigo_empresa());
		map.put("codigo_sucursal", planificacion_familiar.getCodigo_sucursal());
		map.put("nro_identificacion",
				planificacion_familiar.getIdentificacion());
		map.put("nro_ingreso", planificacion_familiar.getNro_ingreso());
		map.put("codigo_prestador", admision.getCodigo_medico());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", planificacion_familiar.getUltimo_update());
		map.put("ultimo_update", planificacion_familiar.getUltimo_update());
		map.put("creacion_user", admision.getCreacion_user());
		map.put("ultimo_user", admision.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}

	public void crear(Planificacion_familiar planificacion_familiar) {
		try {
			if (consultar(planificacion_familiar) != null) {
				throw new HealthmanagerException(
						"Planificacion_familiar ya se encuentra en la base de datos");
			}
			planificacion_familiarDao.crear(planificacion_familiar);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Planificacion_familiar planificacion_familiar) {
		try {
			return planificacion_familiarDao.actualizar(planificacion_familiar);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Planificacion_familiar consultar(
			Planificacion_familiar planificacion_familiar) {
		try {
			return planificacion_familiarDao.consultar(planificacion_familiar);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Planificacion_familiar consultar_historia(
			Planificacion_familiar planificacion_familiar) {
		try {
			return planificacion_familiarDao.consultar(planificacion_familiar);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Planificacion_familiar planificacion_familiar) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(planificacion_familiar.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(planificacion_familiar
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(planificacion_familiar
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Planificacion_familiar> listar(Map<String, Object> parametros) {
		try {
			return planificacion_familiarDao.listar(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parametros) {
		try {
			return planificacion_familiarDao.total_registros(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parametros) {
		return this.planificacion_familiarDao.existe(parametros);
	}

}
