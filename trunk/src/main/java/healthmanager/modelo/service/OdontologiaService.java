/*
 * OdontologiaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Consentimiento_inf_odon;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Diente;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Indice_dean;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Plan_tratamiento;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.DienteDao;
import healthmanager.modelo.dao.Indice_deanDao;
import healthmanager.modelo.dao.OdontologiaDao;
import healthmanager.modelo.dao.Servicios_contratadosDao;

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
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

@Service("odontologiaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class OdontologiaService implements Serializable{


	@Autowired
	private OdontologiaDao odontologiaDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private DienteDao dienteDao;
	@Autowired
	private Indice_deanDao indice_deanDao;
	@Autowired
	private Datos_consultaService datos_consultaService;
	@Autowired
	private Orden_servicioService orden_servicioService;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private Remision_internaService remision_internaService;
	@Autowired
	private Consentimiento_inf_odonService consentimiento_inf_odonService;
	@Autowired
	private Receta_ripsService receta_ripsService;
	@Autowired
	private Impresion_diagnosticaService impresion_diagnosticaService;
	@Autowired
	private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;
	@Autowired
	PacienteService pacienteService;

	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;

	@Autowired
	Datos_procedimientoService datos_procedimientoService;

	@Autowired
	private GeneralExtraService generalExtraService;

	@Autowired
	private Servicios_contratadosDao servicios_contratadosDao;
	@Autowired
	private AdmisionService admisionService;
	@Autowired
	private Plan_tratamientoService plan_tratamientoService;

	public Odontologia guardar(Map<String, Object> datos) {
		try {
			Admision admision = (Admision) datos.get("admision");
			String accion = (String) datos.get("accion");
			Map<String, Object> mapOrdenServicio = (Map<String, Object>) datos
					.get("orden_servicio");
			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) datos
					.get("impresion_diagnostica");
			List<Diente> dientes = (List<Diente>) datos.get("dientes");
			List<Indice_dean> indice_dean = (List<Indice_dean>) datos
					.get("indice_dean");

			Parametros_empresa parametros_empresa = (Parametros_empresa) datos
					.get("parametros_empresa");
			/*
			 * Map<String, Object> mapProcedimientos_ordenados = (Map<String,
			 * Object>) datos .get("procedimientos_ordenados");
			 */

			Odontologia odonto = (Odontologia) datos.get("codigo_historia");

			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");

			Consentimiento_inf_odon consentimiento_inf_odon = (Consentimiento_inf_odon) datos
					.get("consentimineto_inf_odon");

			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna.setCodigo_historia(odonto.getCodigo_historia());
			remision_interna.setFecha_inicio(odonto.getFecha_inicial());

			consentimiento_inf_odon.setIdentificacion(admision
					.getNro_identificacion());
			consentimiento_inf_odon.setFecha_inicio(odonto.getFecha_inicial());

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(odonto.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(odonto.getCodigo_sucursal());
			historia_clinica.setFecha_historia(odonto.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(odonto.getCreacion_user());

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				odonto.setCodigo_historia(historia_clinica.getCodigo_historia());
				odontologiaDao.crear(odonto);

				remision_interna
						.setCodigo_historia(odonto.getCodigo_historia());

				consentimiento_inf_odon.setCodigo_historia(odonto
						.getCodigo_historia());

			} else {
				if (consultar(odonto) == null)
					throw new Exception(
							"El dato que desea actualizar no existe en la base de datos");

				odontologiaDao.actualizar(odonto);
			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
			}

			if (consentimiento_inf_odonService
					.consultar(consentimiento_inf_odon) == null) {
				consentimiento_inf_odonService.crear(consentimiento_inf_odon);
			} else {
				consentimiento_inf_odonService
						.actualizar(consentimiento_inf_odon);
			}
			// generarConsulta(odonto);
			guardarDientes(dientes, odonto);
			guardarIndiceDean(indice_dean, odonto);

			List<Detalle_orden> detalle_ordens = (List<Detalle_orden>) mapOrdenServicio
					.get("lista_detalle");

			if (!detalle_ordens.isEmpty()) {
				// O de odontologia
				for (Detalle_orden dtt_orden : detalle_ordens) {
					Plan_tratamiento plan_tratamiento = Utilidades
							.convertirDetalle_ordenAPlanTratamiento(dtt_orden);
					plan_tratamiento.setNro_identificacion(admision
							.getNro_identificacion());

					// Este es si ya tiene un plan de tratamiento
					Plan_tratamiento plan_tratamiento_temp = plan_tratamientoService
							.consultar(plan_tratamiento);
					if (plan_tratamiento_temp != null) {
						plan_tratamiento_temp.setUnidades(plan_tratamiento_temp
								.getUnidades().intValue()
								+ dtt_orden.getUnidades().intValue());
						plan_tratamiento_temp.setUltimo_user(odonto
								.getCreacion_user());
						plan_tratamientoService
								.actualizar(plan_tratamiento_temp);
					} else {
						plan_tratamiento.setCreacion_user(odonto
								.getCreacion_user());
						plan_tratamiento.setUltimo_user(odonto
								.getCreacion_user());
						plan_tratamiento.setEstado("0"); // 0 NO REALIZADO

						plan_tratamientoService.crear(plan_tratamiento);
					}
				}
			} else {
				mapOrdenServicio.put("orden_servicio", null);
			}

			admision.setCodigo_medico(historia_clinica.getPrestador());
			admision.setAtendida(true);
			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());

			admisionDao.actualizar(admision);
			if (admision.getVia_ingreso().equals(IVias_ingreso.ODONTOLOGIA2)) {
				crearConsulta(odonto, admision, impresion_diagnostica);
			} else if (admision.getVia_ingreso().equals(
					IVias_ingreso.SALUD_ORAL)) {
				if (parametros_empresa != null
						&& parametros_empresa.getSalud_oral_recuperacion()
								.equals("S")) {
					
				}
			}

			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					odonto.getCodigo_historia(), datos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService, odonto.getCreacion_user());

			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) datos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad.setNro_historia(odonto.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							odonto.getCodigo_empresa(),
							odonto.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							odonto.getCodigo_empresa(),
							odonto.getCodigo_sucursal(),
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

			return odonto;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private void guardarIndiceDean(List<Indice_dean> indice_deans,
			Odontologia odonto) {
		Indice_dean indice_deanDelete = new Indice_dean();
		indice_deanDelete.setCodigo_empresa(odonto.getCodigo_empresa());
		indice_deanDelete.setCodigo_sucursal(odonto.getCodigo_sucursal());
		indice_deanDelete.setNro_historia(odonto.getCodigo_historia());
		indice_deanDao.eliminar(indice_deanDelete);
		for (Indice_dean indice_dean : indice_deans) {
			indice_dean.setNro_historia(odonto.getCodigo_historia());
			indice_dean.setCreacion_date(odonto.getCreacion_date());
			indice_dean.setCreacion_user(odonto.getCreacion_user());
			indice_dean.setUltimo_update(odonto.getUltimo_update());
			indice_dean.setUltimo_user(odonto.getUltimo_user());
			indice_deanDao.crear(indice_dean);
		}
	}

	private void guardarDientes(List<Diente> dientes, Odontologia odontologia) {
		Diente dienteDelete = new Diente();
		dienteDelete.setCodigo_empresa(odontologia.getCodigo_empresa());
		dienteDelete.setCodigo_sucursal(odontologia.getCodigo_sucursal());
		dienteDelete.setNro_historia(odontologia.getCodigo_historia());
		dienteDelete.setEvolucion("N");
		dienteDao.eliminar(dienteDelete);
		for (Diente diente : dientes) {
			diente.setCodigo_empresa(odontologia.getCodigo_empresa());
			diente.setCodigo_sucursal(odontologia.getCodigo_sucursal());
			diente.setNro_historia(odontologia.getCodigo_historia());
			diente.setCreacion_date(odontologia.getCreacion_date());
			diente.setCreacion_user(odontologia.getCreacion_user());
			diente.setUltimo_update(odontologia.getUltimo_update());
			diente.setUltimo_user(odontologia.getUltimo_user());
			diente.setEvolucion("N");
			dienteDao.crear(diente);
		}
	}

	public void crear(Odontologia odontologia) {
		try {
			if (consultar(odontologia) != null) {
				throw new HealthmanagerException(
						"Odontologia ya se encuentra en la base de datos");
			}
			odontologiaDao.crear(odontologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Odontologia odontologia) {
		try {
			return odontologiaDao.actualizar(odontologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Odontologia consultar(Odontologia odontologia) {
		try {
			return odontologiaDao.consultar(odontologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Odontologia consultar_historia(Odontologia odontologia) {
		try {
			return odontologiaDao.consultar_historia(odontologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Odontologia odontologia) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(odontologia.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(odontologia
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(odontologia
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Odontologia> listar(Map<String, Object> parameter) {
		try {
			return odontologiaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parameters) {
		try {
			return odontologiaDao.total_registros(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

		Odontologia Odontologia = (Odontologia) historiasConsultas;
		Map map = new HashMap();
		map.put("codigo_empresa", Odontologia.getCodigo_empresa());
		map.put("codigo_sucursal", Odontologia.getCodigo_sucursal());
		map.put("nro_identificacion", Odontologia.getIdentificacion());
		map.put("nro_ingreso", admision.getNro_ingreso());
		map.put("codigo_prestador", Odontologia.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", Odontologia.getCreacion_date());
		map.put("ultimo_update", Odontologia.getUltimo_update());
		map.put("creacion_user", Odontologia.getCreacion_user());
		map.put("ultimo_user", Odontologia.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}

}