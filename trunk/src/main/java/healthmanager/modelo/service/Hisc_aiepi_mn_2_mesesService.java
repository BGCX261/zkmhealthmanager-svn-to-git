/*
 * Hisc_aiepi_mn_2_mesesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Cuadros_aiepi_resultados;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Hisc_aiepi_mn_2_meses;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.Hisc_aiepi_mn_2_mesesDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.res.Res;
import com.framework.util.ServiciosDisponiblesUtils;

@Service("hisc_aiepi_mn_2_mesesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Hisc_aiepi_mn_2_mesesService implements Serializable{

	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Hisc_aiepi_mn_2_mesesDao hisc_aiepi_mn_2_mesesDao;
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
	private Cuadros_aiepi_resultadosService cuadros_aiepi_resultadosService;
	@Autowired
	private Remision_internaService remision_internaService;
	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;
	@Autowired
	private CitasDao citasDao;
	// @Autowired
	// private Agudeza_visualService agudeza_visualService;

	@Autowired
	private GeneralExtraService generalExtraService;

	public void crear(Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses) {
		try {
			if (consultar(hisc_aiepi_mn_2_meses) != null) {
				throw new HealthmanagerException(
						"La historia clinica ya se encuentra en la base de datos");
			}
			hisc_aiepi_mn_2_mesesDao.crear(hisc_aiepi_mn_2_meses);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarDatos(Map<String, Object> mapaDatos) {
		try {
			Admision admision = (Admision) mapaDatos.get("admision");
			String accion = (String) mapaDatos.get("accion");
			Map<String, Object> mapRecetas = (Map<String, Object>) mapaDatos
					.get("receta_medica");
			Map<String, Object> mapOrdenServicio = (Map<String, Object>) mapaDatos
					.get("orden_servicio");
			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) mapaDatos
					.get("impresion_diagnostica");
			List<Cuadros_aiepi_resultados> cuadro = (List<Cuadros_aiepi_resultados>) mapaDatos
					.get("resultado_cuadro");

			Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = (Hisc_aiepi_mn_2_meses) mapaDatos
					.get("historia_clinica");

			Citas cita = (Citas) mapaDatos.get("cita_Seleccionada");

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(hisc_aiepi_mn_2_meses
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_aiepi_mn_2_meses
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(hisc_aiepi_mn_2_meses
					.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(hisc_aiepi_mn_2_meses.getPrestador());

			Remision_interna remision_interna = (Remision_interna) mapaDatos
					.get("remision_interna");

			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna.setCodigo_historia(hisc_aiepi_mn_2_meses
					.getCodigo_historia());
			remision_interna.setFecha_inicio(hisc_aiepi_mn_2_meses
					.getFecha_inicial());

			/*
			 * Agudeza_visual agudeza_visual = (Agudeza_visual) mapaDatos
			 * .get("agudeza_visual");
			 * 
			 * agudeza_visual.setCodigo_empresa(hisc_aiepi_mn_2_meses
			 * .getCodigo_empresa());
			 * agudeza_visual.setCodigo_sucursal(hisc_aiepi_mn_2_meses
			 * .getCodigo_sucursal());
			 * agudeza_visual.setCodigo_historia(hisc_aiepi_mn_2_meses
			 * .getCodigo_historia());
			 */

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				hisc_aiepi_mn_2_meses.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				hisc_aiepi_mn_2_mesesDao.crear(hisc_aiepi_mn_2_meses);

				remision_interna.setCodigo_historia(hisc_aiepi_mn_2_meses
						.getCodigo_historia());
				// agudeza_visual.setCodigo_historia(hisc_aiepi_mn_2_meses.getCodigo_historia());

				if (cuadro != null) {
					for (Cuadros_aiepi_resultados res : cuadro) {
						res.setCodigo_empresa(hisc_aiepi_mn_2_meses
								.getCodigo_empresa());
						res.setCodigo_sucursal(hisc_aiepi_mn_2_meses
								.getCodigo_sucursal());
						res.setCodigo_historia(hisc_aiepi_mn_2_meses
								.getCodigo_historia());
						res.setVia_ingreso(IVias_ingreso.HC_AIEPI_2_MESES);
						res.setIdentificacion(admision.getPaciente()
								.getNro_identificacion());

						if (cuadros_aiepi_resultadosService.consultar(res) != null) {
							throw new HealthmanagerException(
									"Un dato del cuadro aiepi ya se encuentra en la base de datos");
						}
						cuadros_aiepi_resultadosService.crear(res);
					}
				}

			} else {
				if (cuadro != null) {
					for (Cuadros_aiepi_resultados res : cuadro) {
						if (cuadros_aiepi_resultadosService.actualizar(res) == 0) {
							throw new HealthmanagerException(
									"El dato que desea actualizar no existe en la base de datos");
						}
					}
				}

				if (consultar(hisc_aiepi_mn_2_meses) == null)
					throw new Exception(
							"El dato que desea actualizar no existe en la base de datos");

				hisc_aiepi_mn_2_mesesDao.actualizar(hisc_aiepi_mn_2_meses);

			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
			}
			/*
			 * if (agudeza_visualService.consultar(agudeza_visual) == null) {
			 * agudeza_visualService.crear(agudeza_visual); } else {
			 * agudeza_visualService.actualizar(agudeza_visual); }
			 */

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

			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) mapaDatos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad.setNro_historia(hisc_aiepi_mn_2_meses
						.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							hisc_aiepi_mn_2_meses.getCodigo_empresa(),
							hisc_aiepi_mn_2_meses.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							hisc_aiepi_mn_2_meses.getCodigo_empresa(),
							hisc_aiepi_mn_2_meses.getCodigo_sucursal(),
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

			if (cita != null) {
				cita.setEstado("2");
				citasDao.actualizar(cita);
			}
			admision.setCodigo_medico(historia_clinica.getPrestador());
			admision.setAtendida(true);
			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());
			admisionDao.actualizar(admision);
			// Creamos autorizacion
			Map<String, Object> mapDatos = (Map<String, Object>) mapaDatos
					.get("autorizacion");
			if (mapDatos != null) {
				orden_autorizacionService.guardarOrden(mapDatos,
						impresion_diagnostica, admision);
			}
			crearConsulta(hisc_aiepi_mn_2_meses, admision,
					impresion_diagnostica);
			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					hisc_aiepi_mn_2_meses.getCodigo_historia(), mapaDatos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					hisc_aiepi_mn_2_meses.getCreacion_user());
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses) {
		try {
			return hisc_aiepi_mn_2_mesesDao.actualizar(hisc_aiepi_mn_2_meses);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Hisc_aiepi_mn_2_meses consultar(
			Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses) {
		try {
			return hisc_aiepi_mn_2_mesesDao.consultar(hisc_aiepi_mn_2_meses);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(hisc_aiepi_mn_2_meses.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_aiepi_mn_2_meses
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(hisc_aiepi_mn_2_meses
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parameters) {
		try {
			return hisc_aiepi_mn_2_mesesDao.total_registros(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Hisc_aiepi_mn_2_meses> listar(Map<String, Object> parameters) {
		try {
			return hisc_aiepi_mn_2_mesesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


	public boolean existe(Map<String, Object> parameters) {
		return hisc_aiepi_mn_2_mesesDao.existe(parameters);
	}


	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

		Hisc_aiepi_mn_2_meses hisc_consulta_externa = (Hisc_aiepi_mn_2_meses) historiasConsultas;
		Admision admisionClonada = Res.clonar(admision);
		// este es el codgio de consulta externa
//		admisionClonada.setVia_ingreso("2");
		Map map = new HashMap();
		map.put("codigo_empresa", hisc_consulta_externa.getCodigo_empresa());
		map.put("codigo_sucursal", hisc_consulta_externa.getCodigo_sucursal());
		map.put("nro_identificacion", hisc_consulta_externa.getIdentificacion());
		map.put("nro_ingreso", hisc_consulta_externa.getNro_ingreso());
		map.put("codigo_prestador", hisc_consulta_externa.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", hisc_consulta_externa.getCreacion_date());
		map.put("ultimo_update", hisc_consulta_externa.getUltimo_update());
		map.put("creacion_user", hisc_consulta_externa.getCreacion_user());
		map.put("ultimo_user", hisc_consulta_externa.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admisionClonada);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}
}