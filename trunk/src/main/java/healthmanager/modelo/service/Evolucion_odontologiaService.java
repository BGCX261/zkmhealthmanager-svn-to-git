/*
 * Evolucion_odontologiaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Diente;
import healthmanager.modelo.bean.Evolucion_odontologia;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Plan_tratamiento;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Afiliaciones_meDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.Detalle_ordenDao;
import healthmanager.modelo.dao.DienteDao;
import healthmanager.modelo.dao.Evolucion_odontologiaDao;
import healthmanager.modelo.dao.Servicios_contratadosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.service.FacturacionService;

@Service("evolucion_odontologiaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Evolucion_odontologiaService implements Serializable{

	private static Logger log = Logger
			.getLogger(Evolucion_odontologiaService.class);

	@Autowired
	private Evolucion_odontologiaDao evolucion_odontologiaDao;
	@Autowired
	private DienteDao dienteDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private Orden_autorizacionService orden_autorizacionService;
	@Autowired
	private Datos_procedimientoService datos_procedimientoService;
	@Autowired
	private PacienteService pacienteService;
	@Autowired
	private Receta_ripsService receta_ripsService;
	@Autowired
	private Orden_servicioService orden_servicioService;
	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;
	@Autowired
	private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;
	@Autowired
	private Impresion_diagnosticaService impresion_diagnosticaService;
	@Autowired
	private Detalle_ordenDao detalle_ordenDao;
	@Autowired
	private Remision_internaService remision_internaService;

	@Autowired
	private GeneralExtraService generalExtraService;

	@Autowired
	private AdmisionService admisionService;

	@Autowired
	private Servicios_contratadosDao servicios_contratadosDao;
	@Autowired
	private Plan_tratamientoService plan_tratamientoService;
	@Autowired
	private Afiliaciones_meDao afiliaciones_meDao;
	@Autowired
	private FacturacionService facturacionService;

	public void crear(Evolucion_odontologia evolucion_odontologia) {
		try {
			if (consultar(evolucion_odontologia) != null) {
				throw new HealthmanagerException(
						"Evolucion_odontologia ya se encuentra en la base de datos");
			}
			evolucion_odontologiaDao.crear(evolucion_odontologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Evolucion_odontologia evolucion_odontologia) {
		try {
			return evolucion_odontologiaDao.actualizar(evolucion_odontologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Evolucion_odontologia consultar(
			Evolucion_odontologia evolucion_odontologia) {
		try {
			return evolucion_odontologiaDao.consultar(evolucion_odontologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Evolucion_odontologia evolucion_odontologia) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(evolucion_odontologia.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(evolucion_odontologia
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(evolucion_odontologia
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Evolucion_odontologia> listar(Map<String, Object> parameter) {
		try {
			return evolucion_odontologiaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parameters) {
		try {
			return evolucion_odontologiaDao.total_registros(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.evolucion_odontologiaDao.existe(parameters);
	}

	public Evolucion_odontologia consultarUltimaEvolucion(
			Evolucion_odontologia evolucion_odontologia) {
		try {
			return evolucion_odontologiaDao
					.consultarUltimaEvolucion(evolucion_odontologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Evolucion_odontologia guardar(Map<String, Object> map) {
		Evolucion_odontologia evolucion_odontologia = (Evolucion_odontologia) map
				.get("evolucion");
		try {
			List<Diente> dientes = (List<Diente>) map.get("dientes");
			boolean primera_vez = (Boolean) map.get("primera_vez");
			Admision admision = (Admision) map.get("admision");
			Sucursal sucursal = (Sucursal) map.get("sucursal");
			Usuarios usuarios = (Usuarios) map.get("usuarios");
			String rol_usuario = (String) map.get("rol_usuario");

			List<Map<String, Object>> plan_tratamientos_procedimiento_realizados = (List<Map<String, Object>>) map
					.get("procedimientos_realizados");
			Odontologia odontologia = (Odontologia) map.get("odontologia");
			Map<String, Object> mapRecetas = (Map<String, Object>) map
					.get("receta_medica");
			Map<String, Object> mapOrdenServicio = (Map<String, Object>) map
					.get("orden_servicio");
			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) map
					.get("impresion_diagnostica");

			Remision_interna remision_interna = (Remision_interna) map
					.get("remision_interna");

			if (consultar(evolucion_odontologia) != null) {
				throw new HealthmanagerException(
						"La historia ya se encuentra en la base de datos actualize el consecutivo del registro");
			}
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(evolucion_odontologia
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(evolucion_odontologia
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(evolucion_odontologia
					.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(evolucion_odontologia
					.getCreacion_user());
			generalExtraService.crear(historia_clinica);
			evolucion_odontologia.setCodigo_evolucion_historia(historia_clinica
					.getCodigo_historia());
			evolucion_odontologia.setCodigo_historia(odontologia
					.getCodigo_historia());
			evolucion_odontologiaDao.crear(evolucion_odontologia);
			guardarDintes(dientes, evolucion_odontologia);

			if (!admision.getAtendida()) {
				admision.setCodigo_medico(historia_clinica.getPrestador());
				admision.setAtendida(true);
				admisionDao.actualizar(admision);
			}

			// Creamos autorizacion
			Map<String, Object> mapDatos = (Map<String, Object>) map
					.get("autorizacion");
			if (mapDatos != null) {
				orden_autorizacionService.guardarOrden(mapDatos,
						impresion_diagnostica, admision);
			}

			/**
			 * En esta parte generamos los procedimientos
			 * */
			// boolean realizo_procedimientos = false;
			Manuales_tarifarios manuales_tarifarios = null;
			if (!plan_tratamientos_procedimiento_realizados.isEmpty()) {
				manuales_tarifarios = ServiciosDisponiblesUtils
						.getManuales_tarifarios(admision);
			}
			for (Map<String, Object> Mapplan_tratamiento : plan_tratamientos_procedimiento_realizados) {
				Plan_tratamiento plan_tratamientoTemp = (Plan_tratamiento) Mapplan_tratamiento
						.get("plan");
				int cantidades_realizadas = (Integer) Mapplan_tratamiento
						.get("cantidad");
				// Boolean nuevo = (Boolean) Mapplan_tratamiento.get("nuevo");

				Plan_tratamiento plan_tratamientoAux = plan_tratamientoService
						.consultar(plan_tratamientoTemp);
				// int cantidades_ordenadas = 0;
				if (plan_tratamientoAux != null) {
					plan_tratamientoAux
							.setUnidades_realizadas(plan_tratamientoAux
									.getUnidades_realizadas().intValue()
									+ cantidades_realizadas);
					plan_tratamientoService.actualizar(plan_tratamientoAux);
					plan_tratamientoTemp = plan_tratamientoAux;
				} else {
					plan_tratamientoTemp
							.setUnidades_realizadas(plan_tratamientoTemp
									.getUnidades_realizadas().intValue()
									+ cantidades_realizadas);
					plan_tratamientoTemp.setCreacion_date(new Timestamp(
							Calendar.getInstance().getTimeInMillis()));
					plan_tratamientoTemp.setCreacion_user(evolucion_odontologia
							.getCreacion_user());
					plan_tratamientoTemp.setUltimo_update(new Timestamp(
							Calendar.getInstance().getTimeInMillis()));
					plan_tratamientoTemp.setUltimo_user(evolucion_odontologia
							.getUltimo_user());
					plan_tratamientoTemp.setEstado("0"); // 0 NO REALIZADO
					plan_tratamientoService.crear(plan_tratamientoTemp);
				}
				guardarDatosProcedimientos(plan_tratamientoTemp, admision,
						odontologia, cantidades_realizadas, manuales_tarifarios);
				// realizo_procedimientos = true;
			}

			/*
			 * Este es para la caja para el cobro de los procedimientos
			 * realizados a beneficiarios
			 */
			if (sucursal != null
					&& sucursal.getTipo().equals(
							IConstantes.TIPOS_SUCURSAL_CAJA_PREV)
					&& sucursal.getId_configuracion_orden_odontologia() != null
					&& admision.getVia_ingreso().equals(
							IVias_ingreso.ODONTOLOGIA2)
					&& !plan_tratamientos_procedimiento_realizados.isEmpty()) {
				validarOrdenBeneficiario(admision,
						plan_tratamientos_procedimiento_realizados, sucursal,
						impresion_diagnostica, usuarios, rol_usuario);
			} else {
				log.debug("Hace falta la configuracion para odontologia");
			}

			// Esto me permite saber cuando es una segunda evolucion
			log.debug("La admision a la hora de guaradar es por primera vez? "
					+ primera_vez);

			// creamos complemento cuando es una segunda evolucion
			List<Detalle_receta_rips> detalle_receta_rips = (List<Detalle_receta_rips>) mapRecetas
					.get("lista_detalle");
			if (!detalle_receta_rips.isEmpty()) {
				// log.info("Se creo una receta en la evolucion");
				mapRecetas.put("accion", "registrar");
				mapRecetas.put("receta_rips",
						receta_ripsService.guardar(mapRecetas));
			} else {
				mapRecetas.put("receta_rips", null);
			}

			if (primera_vez) {
				// Esta opcion es cuando es por primera vez la atencion
				// para que la factura tome los valores
				if (!plan_tratamientos_procedimiento_realizados.isEmpty()) {
					ServiciosDisponiblesUtils.facturacionAutomatica(admision,
							false);
				}
			} else {
				if (admision.getVia_ingreso()
						.equals(IVias_ingreso.ODONTOLOGIA2)) {
					crearConsulta(evolucion_odontologia, admision,
							impresion_diagnostica);
				} else {
					admision.setFecha_atencion(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					admisionDao.actualizar(admision);
					ServiciosDisponiblesUtils.facturacionAutomatica(admision);
				}

				List<Detalle_orden> detalle_ordens = (List<Detalle_orden>) mapOrdenServicio
						.get("lista_detalle");
				if (!detalle_ordens.isEmpty()) {
					for (Detalle_orden dtt_orden : detalle_ordens) {
						Plan_tratamiento plan_tratamiento = Utilidades
								.convertirDetalle_ordenAPlanTratamiento(dtt_orden);
						plan_tratamiento.setNro_identificacion(admision
								.getNro_identificacion());

						// Este es si ya tiene un plan de tratamiento
						Plan_tratamiento plan_tratamientoAux = plan_tratamientoService
								.consultar(plan_tratamiento);
						if (plan_tratamientoAux != null) {
							plan_tratamientoAux.setUnidades(plan_tratamientoAux
									.getUnidades().intValue()
									+ dtt_orden.getUnidades().intValue());
							plan_tratamientoService
									.actualizar(plan_tratamientoAux);
							plan_tratamiento = plan_tratamientoAux;
						} else {
							plan_tratamiento
									.setCreacion_user(evolucion_odontologia
											.getCreacion_user());
							plan_tratamiento
									.setUltimo_user(evolucion_odontologia
											.getCreacion_user());
							plan_tratamiento.setEstado("0"); // 0 NO REALIZADO

							plan_tratamientoService.crear(plan_tratamiento);
						}
					}
				}

				remision_interna.setCodigo_historia(evolucion_odontologia
						.getCodigo_evolucion_historia());
				remision_interna.setCodigo_paciente(admision.getPaciente()
						.getNro_identificacion());
				remision_interna.setCodigo_historia(evolucion_odontologia
						.getCodigo_evolucion_historia());
				remision_interna.setFecha_inicio(evolucion_odontologia
						.getFecha_inicial());

				if (remision_internaService.consultar(remision_interna) == null) {
					remision_internaService.crear(remision_interna);
				} else {
					remision_internaService.actualizar(remision_interna);
				}

				mapOrdenServicio.put("orden_servicio", null);

				FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
						evolucion_odontologia.getCodigo_evolucion_historia(),
						map, ficha_epidemiologia_nnService,
						impresion_diagnosticaService,
						evolucion_odontologia.getCreacion_user());

				Anexo9_entidad anexo9_entidad = (Anexo9_entidad) map
						.get("anexo9");

				// remision
				if (anexo9_entidad != null) {
					anexo9_entidad.setNro_historia(evolucion_odontologia
							.getCodigo_evolucion_historia());
					anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
					Anexo9_entidad anexo9_aux = anexo9_entidadDao
							.consultar(anexo9_entidad);
					if (anexo9_aux != null) {
						anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
						anexo9_entidadDao.actualizar(anexo9_entidad);
					} else {
						String consecutivo = consecutivoService
								.crearConsecutivo(evolucion_odontologia
										.getCodigo_empresa(),
										evolucion_odontologia
												.getCodigo_sucursal(),
										IConstantes.CONS_ANEXO_9);
						String codigo_anexo = consecutivoService.getZeroFill(
								consecutivo, 10);
						anexo9_entidad.setCodigo(codigo_anexo);
						anexo9_entidadDao.crear(anexo9_entidad);
						consecutivoService.actualizarConsecutivo(
								evolucion_odontologia.getCodigo_empresa(),
								evolucion_odontologia.getCodigo_sucursal(),
								IConstantes.CONS_ANEXO_9, codigo_anexo);
					}
				} else {
					anexo9_entidad = new Anexo9_entidad();
					anexo9_entidad.setCodigo_empresa(admision
							.getCodigo_empresa());
					anexo9_entidad.setCodigo_sucursal(admision
							.getCodigo_sucursal());
					anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
					anexo9_entidad.setCodigo_paciente(admision
							.getNro_identificacion());
					anexo9_entidadDao.eliminar(anexo9_entidad);
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
		return evolucion_odontologia;
	}

	/**
	 * Este metodo me permite generar una orden de manera automatica cuando sea
	 * un beneficiario.
	 * 
	 * @author Luis Miguel
	 * */
	private void validarOrdenBeneficiario(
			Admision admision,
			List<Map<String, Object>> plan_tratamientos_procedimiento_realizados,
			Sucursal sucursal, Impresion_diagnostica impresion_diagnostica,
			Usuarios usuarios, String rol_usuario) {
		Map<String, Object> params_verificacion_veneficiario = new HashMap<String, Object>();
		params_verificacion_veneficiario.put("codigo_empresa",
				sucursal.getCodigo_empresa());
		params_verificacion_veneficiario.put("codigo_sucursal",
				sucursal.getCodigo_sucursal());
		params_verificacion_veneficiario.put("nro_identificacion_afiliado",
				admision.getNro_identificacion());
		params_verificacion_veneficiario.put("tipo_afiliado",
				IConstantesAutorizaciones.TIPO_AFILIADO_BENEFICIARIO);
		if (afiliaciones_meDao.existe(params_verificacion_veneficiario)) {
			Map<String, Object> parametros_guardado = new HashMap<String, Object>();
			parametros_guardado.put(
					Orden_autorizacionService.PARAM_PLAN_TRATAMIENTO,
					plan_tratamientos_procedimiento_realizados);
			parametros_guardado.put(Orden_autorizacionService.PARAM_ADMISION,
					admision);
			parametros_guardado.put(Orden_autorizacionService.PARAM_SUCURSAL,
					sucursal);
			parametros_guardado.put(
					Orden_autorizacionService.PARAM_IMPRESION_DIAGNOSTICA,
					impresion_diagnostica);
			parametros_guardado.put(Orden_autorizacionService.PARAM_USUARIO,
					usuarios);
			parametros_guardado.put(
					Orden_autorizacionService.PARAM_ROL_USUARIO, rol_usuario);
			orden_autorizacionService
					.guardarOrdenDesdePlanTratamiento(parametros_guardado);
		} else {
			log.debug("no apliza para la creacion de orden");
		}
	}

	// private void eliminarDetalle(List<Detalle_orden> pendientes_anteriores,
	// Detalle_orden detalle_orden) {
	// Detalle_orden detalle_ordenTemp = getDetalleOrden(
	// pendientes_anteriores, detalle_orden);
	// if (detalle_ordenTemp != null) {
	// detalle_ordenDao.eliminar(detalle_ordenTemp);
	// }
	// }

	// private Detalle_orden getDetalleOrden(
	// List<Detalle_orden> pendientes_anteriores,
	// Detalle_orden detalle_orden) {
	// for (Detalle_orden detalle_ordenTemo : pendientes_anteriores) {
	// if (detalle_ordenTemo.getCodigo_procedimiento().equals(
	// detalle_orden.getCodigo_procedimiento())) {
	// return detalle_ordenTemo;
	// }
	// }
	// return null;
	// }

	private void guardarDatosProcedimientos(
			Plan_tratamiento plan_tratamientoTemp, Admision admision,
			Odontologia odontologia, Integer procedimientos_realizados,
			Manuales_tarifarios manuales_tarifarios) {

		plan_tratamientoTemp.setRealizado("S");
		int actualizado = plan_tratamientoService
				.actualizar(plan_tratamientoTemp);
		if (actualizado == 0) {
			throw new ValidacionRunTimeException(
					"El procedimiento no se ha podido actualizar como realizado");
		}
		// log.info("Actualizo detalle de la orden " + actualizado);
		if (admision.getPaciente() == null)
			throw new ValidacionRunTimeException(
					"El paciente admisionado no existe en la base de datos.");

		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento.setCodigo_empresa(plan_tratamientoTemp
				.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(plan_tratamientoTemp
				.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);
		datos_procedimiento.setCodigo_cups(plan_tratamientoTemp
				.getCodigo_cups());

		datos_procedimiento.setTipo_identificacion(admision.getPaciente()
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(admision.getPaciente()
				.getNro_identificacion());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(plan_tratamientoTemp
				.getCodigo_procedimiento());
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento.setValor_procedimiento(plan_tratamientoTemp
				.getValor_procedimiento());
		datos_procedimiento
				.setUnidades(procedimientos_realizados != null ? procedimientos_realizados
						: plan_tratamientoTemp.getUnidades());
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento
				.setValor_neto(plan_tratamientoTemp.getValor_procedimiento()
						* (procedimientos_realizados != null ? procedimientos_realizados
								.intValue() : plan_tratamientoTemp
								.getUnidades().intValue()));

		datos_procedimiento
				.setAmbito_procedimiento(IDatosProcedimientos.AMBITO_REALIZACION);
		datos_procedimiento
				.setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
		datos_procedimiento
				.setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
		datos_procedimiento
				.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
		datos_procedimiento.setCodigo_diagnostico_principal(admision
				.getDiagnostico_ingreso());
		datos_procedimiento.setCodigo_diagnostico_relacionado("");
		datos_procedimiento.setComplicacion("");
		datos_procedimiento.setCancelo_copago("N");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(odontologia.getCreacion_user());
		datos_procedimiento.setUltimo_user(odontologia.getCreacion_user());
		datos_procedimiento.setValor_real(plan_tratamientoTemp.getValor_real());
		datos_procedimiento.setDescuento(plan_tratamientoTemp.getDescuento());
		datos_procedimiento.setIncremento(plan_tratamientoTemp.getIncremento());
		datos_procedimiento
				.setRealizado_en(IDatosProcedimientos.REALIZADO_EN_ODONOTOLOGIA);
		datos_procedimientoService.crear(datos_procedimiento,
				manuales_tarifarios, admision);
	}

	private void guardarDintes(List<Diente> dientes,
			Evolucion_odontologia odontologia) {
		Diente dienteDelete = new Diente();
		dienteDelete.setCodigo_empresa(odontologia.getCodigo_empresa());
		dienteDelete.setCodigo_sucursal(odontologia.getCodigo_sucursal());
		dienteDelete
				.setNro_historia(odontologia.getCodigo_evolucion_historia());
		dienteDelete.setEvolucion("S");
		dienteDao.eliminar(dienteDelete);
		for (Diente diente : dientes) {
			diente.setCodigo_empresa(odontologia.getCodigo_empresa());
			diente.setCodigo_sucursal(odontologia.getCodigo_sucursal());
			diente.setNro_historia(odontologia.getCodigo_evolucion_historia());
			diente.setCreacion_date(odontologia.getCreacion_date());
			diente.setCreacion_user(odontologia.getCreacion_user());
			diente.setUltimo_update(odontologia.getUltimo_update());
			diente.setUltimo_user(odontologia.getUltimo_user());
			diente.setEvolucion("S");
			dienteDao.crear(diente);
		}
	}

	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

		Evolucion_odontologia evolucion_odontologia = (Evolucion_odontologia) historiasConsultas;
		Map map = new HashMap();
		map.put("codigo_empresa", evolucion_odontologia.getCodigo_empresa());
		map.put("codigo_sucursal", evolucion_odontologia.getCodigo_sucursal());
		map.put("nro_identificacion", evolucion_odontologia.getIdentificacion());
		map.put("nro_ingreso", admision.getNro_ingreso());
		map.put("codigo_prestador", evolucion_odontologia.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", evolucion_odontologia.getCreacion_date());
		map.put("ultimo_update", evolucion_odontologia.getUltimo_update());
		map.put("creacion_user", evolucion_odontologia.getCreacion_user());
		map.put("ultimo_user", evolucion_odontologia.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}

	public Evolucion_odontologia consultarDesdeAdmision(
			Evolucion_odontologia evolucion_odontologia) {
		return evolucion_odontologiaDao
				.consultarDesdeAdmision(evolucion_odontologia);
	}
}
