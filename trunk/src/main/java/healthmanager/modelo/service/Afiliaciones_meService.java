/*
 * Afiliaciones_meServiceImpl.java
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import static com.framework.constantes.IConstantes.NE_REGIMEN_ESPECIAL;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.HistorialObservacionMacro;
import com.framework.res.ResCalculadorDeRangoCuota;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Anio_cuota_moderadora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Historial_observaciones;
import healthmanager.modelo.bean.Novedades_ne;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Salario_anual;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.dao.Afiliaciones_meDao;
import healthmanager.modelo.dao.Historial_observacionesDao;
import healthmanager.modelo.dao.Novedades_neDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.Pacientes_contratosDao;
import healthmanager.modelo.dao.Salario_anualDao;
import healthmanager.modelo.dao.Servicios_contratadosDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("afiliaciones_meService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Afiliaciones_meService implements Serializable{
	// private static Logger log =
	// Logger.getLogger(Afiliaciones_meService.class);

	private String limit;

	public static final String AFILIACIONES = "af_s";
	public static final String PACIENTES = "pac_s";
	public static final String LISTADO_CONTRATO = "l_cont";
	public static final String ADICIONAL_PACIENTES = "adspac_s";
	public static final String BENEFICIARIOS = "bnt_s";
	public static final String NIVEL = "NL";
	public static final String HISTORIAL_OBSERVACIONES = "hist_observ";

	public static final String AFILIACIONES_PACIENTE = "AFILIACIONES";

	public static final String PARAM_ANIO = "PA";
	public static final String PARAM_MES = "PM";
	public static final String PARAM_ULTIMO_APORTE = "PUA";
	public static final String PARAM_PACIENTES = "PP";
	public static final String PARAM_TODOS_LOS_PACIENTES = "PTLP";
	public static final String PARAM_SUCURSAL = "SCL";
	public static final String PARAM_SERVICE_LOCATOR = "SVL";

	public static final String PARAM_CUOTA_MODERADORA = "PCM";
	public static final String PARAM_PORCENTAJE_COPAGO = "PPC";
	public static final String PARAM_CUOTA_MODERADORA_ANTERIOR = "PCMA";
	public static final String PARAM_PORCENTAJE_COPAGO_ANTERIOR = "PPCA";
	public static final String PARAM_OBSERVACIONES = "POS";

	@Autowired
	private Afiliaciones_meDao afiliaciones_meDao;
	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private Novedades_neDao novedadesNeDao;
	@Autowired
	private GeneralExtraService generalExtraService;
	@Autowired
	private Pacientes_contratosDao pacientes_contratosDao;
	@Autowired
	private Historial_observacionesDao historial_observacionesDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Servicios_contratadosDao servicios_contratadosDao;
	@Autowired
	private Salario_anualDao salario_anualDao;

	public void crear(Afiliaciones_me afiliaciones_me) {
		try {
			String codigo_afiliado = consecutivoService
					.crearConsecutivo(afiliaciones_me.getCodigo_empresa(),
							afiliaciones_me.getCodigo_sucursal(),
							AFILIACIONES_PACIENTE);
			afiliaciones_me.setId(codigo_afiliado);
			Afiliaciones_me afiliacionesMeTemp = consultar(afiliaciones_me);
			if (afiliacionesMeTemp != null) {
				throw new HealthmanagerException(
						"Este Afiliado ya se encuentra en la base de datos.\n"
								+ "con el nombre de "
								+ afiliacionesMeTemp.getPaciente()
										.getNombreCompleto()
								+ " y con el consecutivo "
								+ codigo_afiliado
								+ ", por favor verifique los consecutivos en el item "
								+ AFILIACIONES_PACIENTE);
			}
			afiliaciones_meDao.crear(afiliaciones_me);
			consecutivoService.actualizarConsecutivo(
					afiliaciones_me.getCodigo_empresa(),
					afiliaciones_me.getCodigo_sucursal(),
					AFILIACIONES_PACIENTE, codigo_afiliado);
			// guardarBeneficiarios(afiliaciones_me.getList_beneficiarios(),
			// afiliaciones_me);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Map<String, Object> map) {
		try {
			Afiliaciones_me afiliaciones_me = (Afiliaciones_me) map
					.get(AFILIACIONES);
			Paciente paciente = (Paciente) map.get(PACIENTES);
			List<Contratos> list_Contratos = (List<Contratos>) map
					.get(LISTADO_CONTRATO);
			Adicional_paciente adicional_paciente = (Adicional_paciente) map
					.get(ADICIONAL_PACIENTES);
			List<Map<String, Object>> list_historial_obs = (List<Map<String, Object>>) map
					.get(HISTORIAL_OBSERVACIONES);

			afiliaciones_me.setPaciente(paciente);

			// validamos al paciente
			paciente.setDocumento(paciente.getNro_identificacion());
			Paciente pacienteAux = pacienteDao.consultar(paciente);
			if (pacienteAux != null) {
				if (pacienteAux.getTipo_identificacion().equals(
						paciente.getTipo_identificacion())
						&& pacienteAux.getDocumento().equals(
								paciente.getDocumento())) {
					throw new ValidacionRunTimeException("Paciente "
							+ paciente.getTipo_identificacion() + "-"
							+ paciente.getDocumento()
							+ " ya se encuentra en la base de datos");
				} else {
					paciente.setNro_identificacion(paciente
							.getTipo_identificacion()
							+ "_"
							+ paciente.getDocumento());
				}
			}
			pacienteDao.crear(paciente);

			crear(afiliaciones_me);
			guardarAdicional(adicional_paciente);
			guardarContrato(paciente, list_Contratos);
			guardarVerificacionIdIBC(afiliaciones_me);
			guardarBeneficiarios(map);
			guardarHistorialObs(list_historial_obs, afiliaciones_me);
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private void guardarHistorialObs(
			List<Map<String, Object>> list_historial_obs,
			Afiliaciones_me afiliaciones_me) {
		if (list_historial_obs != null && !list_historial_obs.isEmpty()) {
			for (Map<String, Object> map : list_historial_obs) {
				Historial_observaciones historial_observaciones = (Historial_observaciones) map
						.get(HistorialObservacionMacro.HISTORIAL);
				if (historial_observaciones != null) {
					if (historial_observaciones.getFecha_observacion() == null) {
						throw new ValidacionRunTimeException(
								"La fecha de la observacion de obligatoria");
					}

					if (historial_observaciones.getFecha_observacion()
							.compareTo(Calendar.getInstance().getTime()) > 0) {
						throw new ValidacionRunTimeException(
								"La fecha de la observacion no puede ser igual a la fecha actual");
					}

					if (historial_observaciones.getObservacion() == null
							|| historial_observaciones.getObservacion().trim()
									.isEmpty()) {
						throw new ValidacionRunTimeException(
								"El campo de la observacion no puede estar vacio");
					}

					historial_observaciones.setCodigo_empresa(afiliaciones_me
							.getCodigo_empresa());
					historial_observaciones.setCodigo_sucursal(afiliaciones_me
							.getCodigo_sucursal());
					historial_observaciones.setId_afiliado(afiliaciones_me
							.getId());
					historial_observaciones.setCreacion_user(afiliaciones_me
							.getUltimo_user());
					historial_observaciones.setUltimo_user(afiliaciones_me
							.getUltimo_user());
					historial_observaciones.setCreacion_date(new Timestamp(
							Calendar.getInstance().getTimeInMillis()));
					historial_observaciones.setUltimo_update(new Timestamp(
							Calendar.getInstance().getTimeInMillis()));
					historial_observacionesDao.crear(historial_observaciones);
				} else {
					throw new ValidacionRunTimeException(
							"Error al crear historial de observacion");
				}
			}
		}
	}

	public void guardarAdicional(Adicional_paciente adicional_paciente) {
		if (generalExtraService.consultar(adicional_paciente) != null) {
			generalExtraService.actualizar(adicional_paciente);
		} else {
			generalExtraService.crear(adicional_paciente);
		}
	}

	/**
	 * Este metodo me permite guardar y actualizar los contratos de un paciente
	 * */
	private void guardarContrato(Paciente paciente,
			List<Contratos> listado_contratos) {
		Pacientes_contratos pacientes_contratos_aux = new Pacientes_contratos();
		pacientes_contratos_aux.setCodigo_empresa(paciente.getCodigo_empresa());
		pacientes_contratos_aux.setCodigo_sucursal(paciente
				.getCodigo_sucursal());
		pacientes_contratos_aux.setNro_identificacion(paciente
				.getNro_identificacion());
		pacientes_contratos_aux.setCodigo_administradora(paciente
				.getCodigo_administradora());

		pacientes_contratosDao.eliminar_contratos(pacientes_contratos_aux);

		Map<String, Servicios_contratados> mapa_servicios = new HashMap<String, Servicios_contratados>();

		for (Contratos contratos : listado_contratos) {
			Map<String, Object> parametros_servicios = new HashMap<String, Object>();
			parametros_servicios.put("codigo_empresa",
					contratos.getCodigo_empresa());
			parametros_servicios.put("codigo_sucursal",
					contratos.getCodigo_sucursal());
			parametros_servicios.put("id_contrato", contratos.getId_plan());
			parametros_servicios.put("codigo_administradora",
					contratos.getCodigo_administradora());

			List<Servicios_contratados> listado_servicios = servicios_contratadosDao
					.listar(parametros_servicios);

			for (Servicios_contratados servicios_contratados : listado_servicios) {
				if (!mapa_servicios.containsKey(servicios_contratados
						.getCodigo_servicio())) {
					mapa_servicios.put(
							servicios_contratados.getCodigo_servicio(),
							servicios_contratados);
				} else {
					throw new ValidacionRunTimeException(
							"El servicio "
									+ servicios_contratados
											.getCodigo_servicio()
									+ " Se encuentra repetida en los contratos seleccionados. Revisar los servicios contratados para cada contrato seleccionado");
				}
			}

			Pacientes_contratos pacientes_contratos = new Pacientes_contratos();
			pacientes_contratos.setCodigo_empresa(paciente.getCodigo_empresa());
			pacientes_contratos.setCodigo_sucursal(paciente
					.getCodigo_sucursal());
			pacientes_contratos.setNro_identificacion(paciente
					.getNro_identificacion());
			pacientes_contratos.setCodigo_administradora(paciente
					.getCodigo_administradora());
			pacientes_contratos.setId_codigo(contratos.getId_plan());
			pacientes_contratos.setCreacion_date(new Timestamp((new Date())
					.getTime()));
			pacientes_contratos.setCreacion_user(paciente.getCreacion_user());
			pacientes_contratosDao.crear(pacientes_contratos);
		}
	}

	/**
	 * Verificacion de afiliado por IBC
	 * */
	private void guardarVerificacionIdIBC(Afiliaciones_me cotizante) {
		if (cotizante.getIdentificacion_ibc() != null) {
			if (!cotizante.getIdentificacion_ibc().trim().isEmpty()) {
				Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
				afiliacionesMe.setCodigo_empresa(cotizante.getCodigo_empresa());
				afiliacionesMe.setCodigo_sucursal(cotizante
						.getCodigo_sucursal());
				afiliacionesMe.setNro_identificacion_afiliado(cotizante
						.getIdentificacion_ibc());
				afiliacionesMe = afiliaciones_meDao.consultar(afiliacionesMe);
				if (afiliacionesMe != null) {
					afiliacionesMe.setIdentificacion_ibc(cotizante
							.getNro_identificacion_afiliado());
					afiliaciones_meDao.actualizar(afiliacionesMe);
				}
			}
		}
	}

	private void guardarVerificacionIdIBCAnterior(Afiliaciones_me cotizante) {
		if (cotizante.getIdentificacion_ibc() != null) {
			if (!cotizante.getIdentificacion_ibc().trim().isEmpty()) {
				Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
				afiliacionesMe.setCodigo_empresa(cotizante.getCodigo_empresa());
				afiliacionesMe.setCodigo_sucursal(cotizante
						.getCodigo_sucursal());
				afiliacionesMe.setNro_identificacion_afiliado(cotizante
						.getIdentificacion_ibc());
				afiliacionesMe = afiliaciones_meDao.consultar(afiliacionesMe);
				if (afiliacionesMe != null) {
					afiliacionesMe.setIdentificacion_ibc("");
					afiliaciones_meDao.actualizar(afiliacionesMe);
				}
			}
		}
	}

	private void guardarBeneficiarios(Map<String, Object> map) {
		Afiliaciones_me afiliaciones_me = (Afiliaciones_me) map
				.get(AFILIACIONES);
		Paciente paciente = (Paciente) map.get(PACIENTES);
		List<Contratos> list_Contratos = (List<Contratos>) map
				.get(LISTADO_CONTRATO);
		Adicional_paciente adicional_paciente = (Adicional_paciente) map
				.get(ADICIONAL_PACIENTES);
		List<Afiliaciones_me> listBeneficiarios = (List<Afiliaciones_me>) map
				.get(BENEFICIARIOS);

		if (listBeneficiarios != null) {
			for (Afiliaciones_me afiliacionesBeneficiario : listBeneficiarios) {
				Paciente pacienteBen = afiliacionesBeneficiario.getPaciente();
				// paciente.setId_plan(cotizante.getPaciente().getId_plan());
				pacienteBen.setUnidad_medidad(afiliaciones_me.getPaciente()
						.getUnidad_medidad());
				pacienteBen.setEstrato(afiliaciones_me.getPaciente()
						.getEstrato());
				pacienteBen.setTipo_usuario(afiliaciones_me.getPaciente()
						.getTipo_usuario());
				pacienteBen.setNivel_sisben(paciente.getNivel_sisben());
				pacienteBen.setCodigo_administradora(paciente
						.getCodigo_administradora());
				afiliacionesBeneficiario
						.setNro_identificacion_aportante2(afiliaciones_me
								.getNro_identificacion_aportante2());
				afiliacionesBeneficiario
						.setNro_identificacion_cotizante(afiliaciones_me
								.getNro_identificacion_afiliado());
				afiliacionesBeneficiario
						.setNro_identificacion_aportante(afiliaciones_me
								.getNro_identificacion_aportante());
				afiliacionesBeneficiario.setConvension(afiliaciones_me
						.getConvension());
				afiliacionesBeneficiario.setNombre_universidad(afiliaciones_me
						.getNombre_universidad());
				afiliacionesBeneficiario.setTipo_empleado(afiliaciones_me
						.getTipo_empleado());

				// Agregamos los adicionales del paciente
				Adicional_paciente adicional_pacienteBenef = new Adicional_paciente();
				adicional_pacienteBenef.setCodigo_empresa(pacienteBen
						.getCodigo_empresa());
				adicional_pacienteBenef.setCodigo_sucursal(pacienteBen
						.getCodigo_sucursal());
				adicional_pacienteBenef.setNro_identificacion(pacienteBen
						.getNro_identificacion());
				adicional_pacienteBenef.setCarnet("");
				adicional_pacienteBenef
						.setFecha_afiliacion(afiliacionesBeneficiario
								.getFecha_afiliacion());
				adicional_pacienteBenef.setTipo_poblacion("5");
				adicional_pacienteBenef.setFicha_sisben("0");
				adicional_pacienteBenef.setModalidad_subsidio("ST");
				adicional_pacienteBenef.setCodigo_barrio(adicional_paciente
						.getCodigo_barrio());

				pacienteBen.setCodigo_ocupacion(afiliaciones_me.getPaciente()
						.getCodigo_ocupacion());

				if (consultar(afiliacionesBeneficiario) != null) {
					pacienteDao.actualizar(pacienteBen);
					actualizar(afiliacionesBeneficiario);
				} else {
					pacienteBen.setDocumento(pacienteBen
							.getNro_identificacion());
					Paciente pacienteAux = pacienteDao.consultar(pacienteBen);
					if (pacienteAux != null) {
						if (pacienteAux.getTipo_identificacion().equals(
								paciente.getTipo_identificacion())
								&& pacienteAux.getDocumento().equals(
										pacienteBen.getDocumento())) {
							throw new ValidacionRunTimeException("Paciente "
									+ pacienteBen.getTipo_identificacion()
									+ "-" + pacienteBen.getDocumento()
									+ " ya se encuentra en la base de datos");
						} else {
							pacienteBen.setNro_identificacion(pacienteBen
									.getTipo_identificacion()
									+ "_"
									+ pacienteBen.getDocumento());
						}
					}
					pacienteDao.crear(pacienteBen);
					crear(afiliacionesBeneficiario);
				}
				// Relacionamos los contratos con los pacientes beneficiarios
				guardarContrato(pacienteBen, list_Contratos);
				guardarAdicional(adicional_pacienteBenef);
				if (afiliaciones_me.getHistorial_observaciones() != null) {
					guardarHistorialObs(
							afiliaciones_me.getHistorial_observaciones(),
							afiliaciones_me);
				}
			}
		}
	}

	public int actualizar(Map<String, Object> map) {
		try {
			Afiliaciones_me afiliaciones_me = (Afiliaciones_me) map
					.get(AFILIACIONES);
			Paciente paciente = (Paciente) map.get(PACIENTES);
			List<Contratos> list_Contratos = (List<Contratos>) map
					.get(LISTADO_CONTRATO);
			Adicional_paciente adicional_paciente = (Adicional_paciente) map
					.get(ADICIONAL_PACIENTES);
			String nivel = (String) map.get(NIVEL);
			List<Map<String, Object>> list_historial_obs = (List<Map<String, Object>>) map
					.get(HISTORIAL_OBSERVACIONES);
			if (nivel == null)
				nivel = "";

			afiliaciones_me.setPaciente(paciente);

			guardarVerificacionNE(afiliaciones_me);

			Paciente pacienteAux = pacienteDao.consultarPorDocumento(paciente);
			if (pacienteAux != null) {
				if (!pacienteAux.getNro_identificacion().equals(
						paciente.getNro_identificacion())) {
					throw new ValidacionRunTimeException("Paciente "
							+ paciente.getTipo_identificacion() + "-"
							+ paciente.getDocumento()
							+ " ya se encuentra en la base de datos");
				}

			}

			pacienteDao.actualizar(paciente);
			guardarBeneficiarios(map);

			// Creamos el adicional siempre y cuando no sea beneficiario
			if (!nivel.equals(BENEFICIARIOS)) {
				guardarAdicional(adicional_paciente);
				guardarContrato(paciente, list_Contratos);
			}

			Afiliaciones_me afiliacionesMeWhere = new Afiliaciones_me();
			afiliacionesMeWhere.setCodigo_empresa(afiliaciones_me
					.getCodigo_empresa());
			afiliacionesMeWhere.setCodigo_sucursal(afiliaciones_me
					.getCodigo_sucursal());
			afiliacionesMeWhere.setId(afiliaciones_me.getId());
			guardarVerificacionIdIBCAnterior(afiliaciones_meDao
					.consultar(afiliacionesMeWhere));
			guardarVerificacionIdIBC(afiliaciones_me);
			guardarHistorialObs(list_historial_obs, afiliaciones_me);
			return afiliaciones_meDao.actualizar(afiliaciones_me);
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Afiliaciones_me afiliaciones_me) {
		try {
			return afiliaciones_meDao.actualizar(afiliaciones_me);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Afiliaciones_me consultar(Afiliaciones_me afiliaciones_me) {
		try {
			return afiliaciones_meDao.consultar(afiliaciones_me);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Afiliaciones_me afiliaciones_me) {
		try {
			return afiliaciones_meDao.eliminar(afiliaciones_me);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Afiliaciones_me> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return afiliaciones_meDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private class ValorNodedad {
		Object nuevo_valor;
		// int posicion;
		Class type;

		public ValorNodedad(Object nuevoValor, int posicion, Class type) {
			super();
			nuevo_valor = nuevoValor;
			// this.posicion = posicion;
			this.type = type;
		}
	}

	private void guardarVerificacionNE(Afiliaciones_me afiliacionesMe) {
		Afiliaciones_me afiliacionesMeWhere = new Afiliaciones_me();
		afiliacionesMeWhere.setCodigo_empresa(afiliacionesMe
				.getCodigo_empresa());
		afiliacionesMeWhere.setCodigo_sucursal(afiliacionesMe
				.getCodigo_sucursal());
		afiliacionesMeWhere.setId(afiliacionesMe.getId());
		Afiliaciones_me afiliacionesMeAnterior = consultar(afiliacionesMeWhere);
		// log.info("AFA: " + afiliacionesMeAnterior);
		try {
			Map<String, List> map_novedades = new HashMap();
			/* afiliacion */
			// log.info("Cargando afiliaciones...");
			cargarNovedadesNE(map_novedades, afiliacionesMe,
					afiliacionesMeAnterior, Afiliaciones_me.class);
			// log.info("Cargando datos pacientes...");
			Paciente pacienteAnterior = pacienteDao
					.consultar(afiliacionesMeAnterior.getPaciente());
			cargarNovedadesNE(map_novedades, afiliacionesMe.getPaciente(),
					pacienteAnterior, Paciente.class);
			// recorremos el mapa
			// ordenamos las listas en los mapas
			// creamos una novedad
			Iterator it = map_novedades.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				// log.info(e.getKey() + " " + e.getValue());
				Novedades_ne novedadesNe = crearNovedad(afiliacionesMe,
						afiliacionesMeAnterior, e.getKey().toString());
				List list = (ArrayList) e.getValue();
				/* ordenamos lista */
				int i = 1;
				for (ValorNodedad valorNodedad : (List<ValorNodedad>) list) {
					Field field = Novedades_ne.class
							.getDeclaredField("nuevo_valor" + (i++));
					field.setAccessible(true);
					if (valorNodedad.type == String.class) {
						field.set(novedadesNe,
								valorNodedad.nuevo_valor.toString());
					} else if (valorNodedad.type == Timestamp.class) {
						field.set(novedadesNe,
								((Timestamp) valorNodedad.nuevo_valor)
										.toString());
					}
				}
				novedadesNeDao.crear(novedadesNe);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite saber si hubo una novedad en el cambio de
	 * informacion del paciente
	 * 
	 * @author Luis Miguel
	 * */
	private void cargarNovedadesNE(Map<String, List> map_novedades,
			Object objectNuevo, Object objectAnterior, Class class1)
			throws Exception {
		// log.info("Metod(loadNovedades) : objectAnterior: " + objectAnterior);
		Field[] fields = class1.getDeclaredFields();
		reOrdenarField(fields);
		for (Field field : fields) {
			field.setAccessible(true);
			NE_REGIMEN_ESPECIAL view = field
					.getAnnotation(NE_REGIMEN_ESPECIAL.class);
			if (view != null) {
				Object valueNuevo = field.get(objectNuevo);
				Object valueAnterior = field.get(objectAnterior);
				if (!compararDatos(valueNuevo, valueAnterior, field.getType())) {
					String codigo_novedad = view.codigo();
					List list = null;
					if (map_novedades.containsKey(codigo_novedad)) {
						list = map_novedades.get(codigo_novedad);
					} else {
						list = new ArrayList();
						map_novedades.put(codigo_novedad, list);
					}
					list.add(new ValorNodedad(valueNuevo, view.posicion(),
							field.getType()));
				}
			}
		}
	}

	private static Field[] reOrdenarField(Field[] fields) {
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields.length; j++) {
				NE_REGIMEN_ESPECIAL gtorViewPosition_i = fields[i]
						.getAnnotation(NE_REGIMEN_ESPECIAL.class);
				NE_REGIMEN_ESPECIAL gtorViewPosition_j = fields[j]
						.getAnnotation(NE_REGIMEN_ESPECIAL.class);
				if (gtorViewPosition_j != null && gtorViewPosition_i != null) {
					if (gtorViewPosition_i.posicion() < gtorViewPosition_j
							.posicion()) {
						Field field_temp = fields[i];
						fields[i] = fields[j];
						fields[j] = field_temp;
					}
				}
			}
		}
		// log.info("ORGANIZACION: " + fields);
		return fields;
	}

	private Novedades_ne crearNovedad(Afiliaciones_me afiliacionesMe,
			Afiliaciones_me afiliacionesMeAnterior, String codigo) {

		Novedades_ne novedades_ne = new Novedades_ne();
		novedades_ne.setCodigo_empresa(afiliacionesMe.getCodigo_empresa());
		novedades_ne.setCodigo_sucursal(afiliacionesMe.getCodigo_sucursal());
		novedades_ne = novedadesNeDao.consultar(novedades_ne);
		Map map = new HashMap();
		map.put("codigo_empresa", afiliacionesMe.getCodigo_empresa());
		map.put("codigo_sucursal", afiliacionesMe.getCodigo_sucursal());
		Integer cons = novedadesNeDao.getConsecutivo(map);

		int consecutivo = 1;
		if (cons != null) {
			consecutivo = cons.intValue();
		}

		novedades_ne = new Novedades_ne();
		novedades_ne.setConsecutivo(consecutivo);
		novedades_ne.setCodigo_empresa(afiliacionesMe.getCodigo_empresa());
		novedades_ne.setCodigo_sucursal(afiliacionesMe.getCodigo_sucursal());
		novedades_ne.setNro_id_afiliado(afiliacionesMe.getId());
		novedades_ne.setCodigo_novedad(codigo);
		// novedades_ne.setNuevo_valor1();
		// novedades_ne.setNuevo_valor2();
		// novedades_ne.setNuevo_valor3();
		// novedades_ne.setNuevo_valor4();
		// novedades_ne.setNuevo_valor5();
		// novedades_ne.setNuevo_valor6();
		// novedades_ne.setNuevo_valor7();
		novedades_ne.setFecha_novedad(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		novedades_ne.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		novedades_ne.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		novedades_ne.setCreacion_user(afiliacionesMe.getCreacion_user());
		novedades_ne.setUltimo_user(afiliacionesMe.getUltimo_user());
		return novedades_ne;
	}

	private boolean compararDatos(Object valueNuevo, Object valueAnterior,
			Class<?> type) {
		if (type == String.class) {
			// log.info("V1 " + valueNuevo);
			// log.info("V2 " + valueAnterior);
			if (valueNuevo != null && valueAnterior != null)
				return valueNuevo.toString().equalsIgnoreCase(
						valueAnterior.toString());
		} else if (type == Timestamp.class) {
			if (valueNuevo != null && valueAnterior != null)
				return ((Timestamp) valueNuevo).toString().equalsIgnoreCase(
						((Timestamp) valueAnterior).toString());
		}
		return true;
	}

	public List<Map<String, Object>> listarMap(Map param) {
		param.put("limit", limit);
		return afiliaciones_meDao.listarMap(param);
	}

	public List<Map<String, Object>> listarMap054(Map<String, Object> params) {
		return afiliaciones_meDao.listarMap054(params);
	}

	public Map<String, Object> getMap198(Map<String, Object> params) {
		return afiliaciones_meDao.getMap198(params);
	}

	public List<Map<String, Object>> actualizarCobros(Map<String, Object> params) {
		try {
			List<Map<String, Object>> resultado = new ArrayList<Map<String, Object>>();
			boolean ultimo_aporte = (Boolean) params.get(PARAM_ULTIMO_APORTE);
			boolean actualizar_todos_los_afiliados = (Boolean) params
					.get(PARAM_TODOS_LOS_PACIENTES);
			int anio = (Integer) params.get(PARAM_ANIO);
			int mes = (Integer) params.get(PARAM_MES);
			Sucursal sucursal = (Sucursal) params.get(PARAM_SUCURSAL);
			ServiceLocatorWeb service_locator = (ServiceLocatorWeb) params
					.get(PARAM_SERVICE_LOCATOR);

			List<Afiliaciones_me> afilados = (List<Afiliaciones_me>) params
					.get(PARAM_PACIENTES);

			if (actualizar_todos_los_afiliados) {
				Map<String, Object> params_afiliados = new HashMap<String, Object>();
				params_afiliados.put("codigo_empresa",
						sucursal.getCodigo_empresa());
				params_afiliados.put("codigo_sucursal",
						sucursal.getCodigo_sucursal());
				params_afiliados.put("limit", null);
				afilados = listar(params_afiliados);
			}

			if (!ultimo_aporte) {
				++mes;
			}

			if (afilados.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se enontraron afiliados");
			}

			Map<String, Salario_anual> map_cache_salario = new HashMap<String, Salario_anual>();
			for (Afiliaciones_me afiliaciones_me : afilados) {
				if (ultimo_aporte) {
					Map<String, Object> params_afiliados = new HashMap<String, Object>();
					params_afiliados.put("codigo_empresa",
							sucursal.getCodigo_empresa());
					params_afiliados.put("codigo_sucursal",
							sucursal.getCodigo_sucursal());
					params_afiliados.put("nro_identificacion",
							afiliaciones_me.getNro_identificacion_afiliado());

					Map<String, Object> ultimos_aportes = afiliaciones_meDao
							.getUltimoAportes(params);
					Integer anio_ultimo = (Integer) ultimos_aportes.get("anio");
					Integer mes_ultimo = (Integer) ultimos_aportes.get("mes");
					anio = anio_ultimo != null ? anio_ultimo : anio;
					mes = mes_ultimo != null ? mes_ultimo : mes;
				}

				Map<String, Object> map_historial = new HashMap<String, Object>();
				map_historial.put(AFILIACIONES, afiliaciones_me);
				map_historial.put(PARAM_CUOTA_MODERADORA_ANTERIOR,
						afiliaciones_me.getCuota_moderadora());
				map_historial.put(PARAM_PORCENTAJE_COPAGO_ANTERIOR,
						afiliaciones_me.getPorcentaje_copago());

				Salario_anual salario_anual = map_cache_salario.get(anio + "");
				if (salario_anual == null) {
					salario_anual = new Salario_anual();
					salario_anual.setCodigo_empresa(sucursal
							.getCodigo_empresa());
					salario_anual.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					salario_anual.setAnio(anio + "");
					salario_anual = salario_anualDao.consultar(salario_anual);
					map_cache_salario.put(anio + "", salario_anual);
				}

				if (salario_anual != null) {
					Anio_cuota_moderadora anio_cuota_moderadora = ResCalculadorDeRangoCuota
							.getGrupo(afiliaciones_me.getPaciente(),
									service_locator, anio + "", mes, sucursal);

					if (anio_cuota_moderadora != null) {
						boolean actualizo_porcentaje = true;
						boolean actualizo_cuota_moderadora = true;

						if (anio_cuota_moderadora.getPorcentaje_copago() != 0d) {
							afiliaciones_me
									.setPorcentaje_copago(anio_cuota_moderadora
											.getPorcentaje_copago());
						} else {
							actualizo_porcentaje = false;
						}

						if (anio_cuota_moderadora.getValor_cuota() != 0d) {
							afiliaciones_me
									.setCuota_moderadora(anio_cuota_moderadora
											.getValor_cuota());
						} else {
							actualizo_cuota_moderadora = false;
						}

						if (actualizo_cuota_moderadora || actualizo_porcentaje) {
							actualizar(afiliaciones_me);
						}

						// cargamos observacion
						if (actualizo_cuota_moderadora && actualizo_porcentaje) {
							map_historial.put(PARAM_OBSERVACIONES,
									"ACTUALIZACION EXITOSA");
						} else if (actualizo_cuota_moderadora) {
							map_historial.put(PARAM_OBSERVACIONES,
									"SOLO SE ACTUALIZO LA CUOTA MODERADORA");
						} else if (actualizo_porcentaje) {
							map_historial.put(PARAM_OBSERVACIONES,
									"SOLO SE ACTUALIZO EL PORCENTAJE COPAGO");
						} else {
							map_historial
									.put(PARAM_OBSERVACIONES,
											"NO SE ACTUALIZO POR QUE LOS VALORES NUEVOS ERAN CERO");
						}
					} else {
						map_historial.put(PARAM_OBSERVACIONES,
								"EL SISTEMA NO ENCONTRO APORTES " + anio
										+ " Y  MES " + (mes + 1));
					}
				} else {
					map_historial.put(PARAM_OBSERVACIONES,
							"NO ESTA CONFIGURADO EL COBRO PARA EL AÑO " + anio);
				}

				// Creamos mapa de historial
				map_historial.put(PARAM_CUOTA_MODERADORA,
						afiliaciones_me.getCuota_moderadora());
				map_historial.put(PARAM_PORCENTAJE_COPAGO,
						afiliaciones_me.getPorcentaje_copago());
				resultado.add(map_historial);
			}

			return resultado;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public void setLimit(String limit) {
		this.limit = limit;
	}
	
}
