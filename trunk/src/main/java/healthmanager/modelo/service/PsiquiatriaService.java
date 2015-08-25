/*
 * PsiquiatriaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Psiquiatria;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.PsiquiatriaDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.util.ServiciosDisponiblesUtils;

@Service("psiquiatriaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PsiquiatriaService implements Serializable{

	// private static Logger log = Logger.getLogger(PsiquiatriaAction.class);

	@Autowired
	private PsiquiatriaDao psiquiatriaDao;
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
	private Remision_internaService remision_internaService;
	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;

	@Autowired
	private GeneralExtraService generalExtraService;

	@Autowired
	private CitasDao citasDao;

	public Psiquiatria guardar(Map<String, Object> datos) {
		try {

			Psiquiatria psiquiatria = (Psiquiatria) datos
					.get("historia_clinica");
			Admision admision = (Admision) datos.get("admision");
			if (admision == null) {
				throw new Exception("No hay admision que actualizar");
			}
			String accion = (String) datos.get("accion");
			Map<String, Object> mapRecetas = (Map<String, Object>) datos
					.get("receta_medica");
			Map<String, Object> mapOrdenServicio = (Map<String, Object>) datos
					.get("orden_servicio");
			Citas cita = (Citas) datos.get("cita_seleccionada");
			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");

			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna.setCodigo_historia(psiquiatria
					.getCodigo_historia());
			remision_interna.setFecha_inicio(psiquiatria.getFecha_inicial());

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(psiquiatria.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(psiquiatria
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(psiquiatria.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(psiquiatria.getCreacion_user());

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				psiquiatria.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				psiquiatriaDao.crear(psiquiatria);

				remision_interna.setCodigo_historia(psiquiatria
						.getCodigo_historia());
			} else {

				if (generalExtraService.consultar(historia_clinica) != null) {
					if (consultar(psiquiatria) == null)
						throw new Exception(
								"El dato que desea actualizar no existe en la base de datos");
					psiquiatriaDao.actualizar(psiquiatria);
				}

			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
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
			if (psiquiatria.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				admision.setPrimera_vez("S");
			} else {
				admision.setPrimera_vez("N");
			}

			/* generamos la consulta */
			crearConsulta(psiquiatria, admision, impresion_diagnostica);

			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					psiquiatria.getCodigo_historia(), datos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					psiquiatria.getCreacion_user());

			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) datos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad
						.setNro_historia(psiquiatria.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							psiquiatria.getCodigo_empresa(),
							psiquiatria.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							psiquiatria.getCodigo_empresa(),
							psiquiatria.getCodigo_sucursal(),
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

			return psiquiatria;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Psiquiatria psiquiatria) {
		try {
			if (consultar(psiquiatria) != null) {
				throw new HealthmanagerException(
						"Psiquiatria ya se encuentra en la base de datos");
			}
			psiquiatriaDao.crear(psiquiatria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Psiquiatria psiquiatria) {
		try {
			return psiquiatriaDao.actualizar(psiquiatria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Psiquiatria consultar(Psiquiatria psiquiatria) {
		try {
			return psiquiatriaDao.consultar(psiquiatria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Psiquiatria consultar_historia(Psiquiatria psiquiatria) {
		try {
			return psiquiatriaDao.consultar_historia(psiquiatria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Psiquiatria psiquiatria) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(psiquiatria.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(psiquiatria
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(psiquiatria
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Psiquiatria> listar(Map<String, Object> parameter) {
		try {
			return psiquiatriaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parameters) {
		try {
			return psiquiatriaDao.total_registros(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.psiquiatriaDao.existe(parameters);
	}

	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

		Psiquiatria psiquiatria = (Psiquiatria) historiasConsultas;
		Map map = new HashMap();
		map.put("codigo_empresa", psiquiatria.getCodigo_empresa());
		map.put("codigo_sucursal", psiquiatria.getCodigo_sucursal());
		map.put("nro_identificacion", psiquiatria.getIdentificacion());
		map.put("nro_ingreso", psiquiatria.getNro_ingreso());
		map.put("codigo_prestador", psiquiatria.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", psiquiatria.getCreacion_date());
		map.put("ultimo_update", psiquiatria.getUltimo_update());
		map.put("creacion_user", psiquiatria.getCreacion_user());
		map.put("ultimo_user", psiquiatria.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}
}
