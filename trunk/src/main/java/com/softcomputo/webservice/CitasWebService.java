package com.softcomputo.webservice;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Servicios_via_ingreso;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.service.Detalles_horariosService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Servicios_contratadosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.L2HContraintDate;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "CitasWebService")
public class CitasWebService {

	private static Calendar calendar = Calendar.getInstance();

	private static Logger log = Logger.getLogger(CitasWebService.class);

	private String separador = "|";

	private String separador_linea = "&";

	private String formato_fecha = "yyyy/MM/dd";

	private final String codigo_empresa = "1300102067";
	private final String codigo_sucursal = "130010206701";

	private ServiceLocatorWeb serviceLocator;

	public static final String VERIFICAR_SERVICIOS = "VERIFICAR_SERVICIOS";

	public static final String APARTAR_CEX = "APARTAR_CEX";

	public static final String APARTAR_ODO = "APARTAR_ODO";

	public static final String CANCELAR_CEX = "CANCELAR_CEX";

	public static final String CANCELAR_ODO = "CANCELAR_ODO";

	public static final String CONSULTAR_CITAS = "CONSULTAR_CITAS";

	public static final String LISTADO_CITAS = "LISTADO_CITAS";

	public static final SimpleDateFormat formatFecha = new SimpleDateFormat(
			"yyyy/MM/dd");
	public static final SimpleDateFormat formatHora = new SimpleDateFormat(
			"hh:mm a");

	@WebMethod(operationName = "ejecutarProceso")
	public String ejecutarProceso(@WebParam(name = "datos") String datos) {
		String resultado = "";
		try {
			Map<String, String> mapa_datos = obtenerDatos(datos);
			// log.info("mapa_datos ===> " + mapa_datos);
			if (!mapa_datos.isEmpty()) {
				serviceLocator = ServiceLocatorWeb.getInstance();
				String proceso = mapa_datos.get("PROCESO");
				if (proceso.equals(VERIFICAR_SERVICIOS)) {
					String nro_identificacion = mapa_datos
							.get("NRO_IDENTIFICACION");
					Paciente paciente = new Paciente();
					paciente.setCodigo_empresa(codigo_empresa);
					paciente.setCodigo_sucursal(codigo_sucursal);
					paciente.setNro_identificacion(nro_identificacion);

					paciente = serviceLocator.getPacienteService().consultar(
							paciente);

					if (paciente != null) {
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("OK=")
								.append(paciente.getNro_identificacion())
								.append(" ")
								.append(paciente.getNombreCompleto());

						if (mapa_datos.containsKey("VER_DERECHOS")) {
							Map<String, Object> mapa_vias_ingreso = ServiciosDisponiblesUtils
									.validarTipoViaIngresoAdmision(
											paciente,
											paciente.getCodigo_administradora(),
											null, false);
							if (mapa_vias_ingreso.isEmpty()) {
								stringBuilder.append(separador).append(
										"PROGRAMAS=NO TIENE DERECHOS");
							} else {
								stringBuilder.append(separador).append(
										"PROGRAMAS= ");
								for (String key_mapa : mapa_vias_ingreso
										.keySet()) {
									Map<String, Object> datos_aux = (Map<String, Object>) mapa_vias_ingreso
											.get(key_mapa);
									Servicios_via_ingreso servicios_via_ingreso = (Servicios_via_ingreso) datos_aux
											.get("servicios_via_ingreso");
									if (servicios_via_ingreso
											.getCodigo_registro() != null) {
										stringBuilder
												.append(servicios_via_ingreso
														.getElemento_via_ingreso()
														.getDescripcion())
												.append(", ");
									}
								}
							}
						}
						resultado = stringBuilder.toString();
					} else {
						resultado = "ERROR=PACIENTE NO EXISTE";
					}
				} else if (proceso.equals(APARTAR_CEX)
						|| proceso.equals(APARTAR_ODO)) {
					try {
						String nro_identificacion = mapa_datos
								.get("NRO_IDENTIFICACION");
						Paciente paciente = new Paciente();
						paciente.setCodigo_empresa(codigo_empresa);
						paciente.setCodigo_sucursal(codigo_sucursal);
						paciente.setNro_identificacion(nro_identificacion);
						paciente = serviceLocator.getPacienteService()
								.consultar(paciente);

						if (paciente != null) {
							String codigo_servicio = "";
							String nombre_servicio = "";
							String tipo_consulta = "";
							if (proceso.equals(APARTAR_CEX)) {
								codigo_servicio = ServiciosDisponiblesUtils.CODSER_CONSULTA_EXTERNA;
								nombre_servicio = "CONSULTA EXTERNA";
								tipo_consulta = IVias_ingreso.CONSULTA_EXTERNA;
							} else {
								codigo_servicio = ServiciosDisponiblesUtils.CODSER_ODONTOLOGIA_GENERAL;
								nombre_servicio = "ODONTOLOGIA";
								tipo_consulta = IVias_ingreso.ODONTOLOGIA2;
							}
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("codigo_administradora",
									paciente.getCodigo_administradora());
							parametros.put("nro_identificacion",
									paciente.getNro_identificacion());
							parametros.put("codigo_servicio_especifico",
									codigo_servicio);
							if (serviceLocator.getServicio(
									Servicios_contratadosService.class)
									.getTieneContratoServicio(parametros)) {

								String codigo_centro = IConstantes.CENTRO_ATENCION_CUALQUIERA;
								Adicional_paciente adicional_paciente = new Adicional_paciente();
								adicional_paciente.setCodigo_empresa(paciente
										.getCodigo_empresa());
								adicional_paciente.setCodigo_sucursal(paciente
										.getCodigo_sucursal());
								adicional_paciente
										.setNro_identificacion(paciente
												.getNro_identificacion());
								adicional_paciente = serviceLocator
										.getServicio(GeneralExtraService.class)
										.consultar(adicional_paciente);
								if (adicional_paciente != null
										&& adicional_paciente
												.getCodigo_barrio() != null
										&& !adicional_paciente
												.getCodigo_barrio().isEmpty()) {
									Centro_barrio centro_barrio = new Centro_barrio();
									centro_barrio
											.setCodigo_barrio(adicional_paciente
													.getCodigo_barrio());
									centro_barrio
											.setCodigo_empresa(adicional_paciente
													.getCodigo_empresa());
									centro_barrio
											.setCodigo_sucursal(adicional_paciente
													.getCodigo_sucursal());
									centro_barrio = serviceLocator.getServicio(
											GeneralExtraService.class)
											.consultar(centro_barrio);
									if (centro_barrio != null) {
										codigo_centro = centro_barrio
												.getCodigo_centro();
									}
								}
								parametros.put("codigo_centro", codigo_centro);
								parametros.put("codigo_via_ingreso",
										tipo_consulta);
								List<Via_ingreso_rol> listado_vias = serviceLocator
										.getServicio(GeneralExtraService.class)
										.listar(Via_ingreso_rol.class,
												parametros);

								List<String> codigos_roles = new ArrayList<String>();

								for (Via_ingreso_rol via_ingreso_rol : listado_vias) {
									codigos_roles.add(via_ingreso_rol
											.getCodigo_rol());
								}

								parametros.put("codigos_roles", codigos_roles);
								calendar.setTime(new Date());
								Integer anio = calendar.get(Calendar.YEAR);
								calendar.set(Calendar.DATE,
										calendar.get(Calendar.DATE) + 1);
								Date fecha_busqueda = calendar.getTime();
								parametros
										.put("fecha_busqueda", fecha_busqueda);
								parametros.put("estado_cita", "estado_cita");
								// log.info("fecha_busqueda ==> " +
								// fecha_busqueda);
								Detalles_horarios detalles_horarios = serviceLocator
										.getServicio(
												Detalles_horariosService.class)
										.consultarUltimoDisponible(parametros);

								if (detalles_horarios != null) {
									Date today = detalles_horarios
											.getFecha_inicial();

									parametros = new HashMap<String, Object>();
									parametros.put("codigo_empresa",
											codigo_empresa);
									parametros.put("codigo_sucursal",
											codigo_sucursal);
									parametros.put("nro_identificacion",
											paciente.getNro_identificacion());
									parametros.put("tipo_consulta",
											tipo_consulta);
									parametros.put("anio", anio);
									parametros.put("estado", "1");

									List<Citas> listado_citas = serviceLocator
											.getCitasService().listar(
													parametros);
									if (!listado_citas.isEmpty()) {
										Citas citas_aux = listado_citas.get(0);
										throw new ValidacionRunTimeException(
												"No se puede hacer el registro de la cita porque este paciente tiene una cita activa "
														+ " en '"
														+ nombre_servicio
														+ "' para la fecha "
														+ new SimpleDateFormat(
																"yyyy/MM/dd")
																.format(citas_aux
																		.getFecha_cita()));
									}

									final Citas citas = new Citas();
									citas.setCodigo_empresa(paciente
											.getCodigo_empresa());
									citas.setCodigo_sucursal(paciente
											.getCodigo_sucursal());
									citas.setNro_identificacion(paciente
											.getNro_identificacion());
									citas.setCodigo_prestador(detalles_horarios
											.getCodigo_medico());
									citas.setFecha_cita(new Timestamp(
											detalles_horarios
													.getFecha_inicial()
													.getTime()));
									citas.setHora(formatHora.format(citas
											.getFecha_cita()));
									citas.setValor_cita(0);
									citas.setCopago_cita(0);
									citas.setEstado("1");
									citas.setTipo_consulta(tipo_consulta);
									citas.setCreacion_date(new Timestamp(
											Calendar.getInstance()
													.getTimeInMillis()));
									citas.setUltimo_update(new Timestamp(
											Calendar.getInstance()
													.getTimeInMillis()));
									citas.setCreacion_user("AU");
									citas.setUltimo_user("AU");

									citas.setCodigo_centro(detalles_horarios
											.getCodigo_centro());

									// citas.setArea_intervencion(lbxArea_intervencion
									// .getSelectedItem()
									// .getValue().toString());
									citas.setCodigo_plantilla("");
									citas.setCodigo_detalle_horario(detalles_horarios
											.getConsecutivo());
									citas.setFecha_solicitada(detalles_horarios
											.getFecha_inicial());
									/* calculamos la resolucion 1552 */
									citas.setDiferencia1(new Long(
											L2HContraintDate.getDiferentDay(
													citas.getFecha_solicitada(),
													today)).intValue());
									citas.setDiferencia2(new Long(
											L2HContraintDate.getDiferentDay(
													citas.getFecha_cita(),
													today)).intValue());
									/* fin de calculo de diferencias */
									citas.setAcompaniante("AU");
									citas.setTel_acompaniante("AU");
									citas.setRelacion("AU");
									citas.setOtro_acompaniante("AU");
									citas.setCedula_acomp("AU");
									citas.setApellidos_acomp("AU");

									citas.setCodigo_administradora(paciente
											.getCodigo_administradora());

									citas.setMedio_solicitud("3");
									citas.setTipo_cita(tipo_consulta);
									Citas auxCitas = serviceLocator
											.getCitasService().guardarCita(
													citas, "AU", "AU");
									resultado = "OK=CITA GUARDADA"
											+ separador
											+ "CODIGO_CITA="
											+ auxCitas.getCodigo_cita()
											+ separador
											+ "FECHA="
											+ formatFecha.format(auxCitas
													.getFecha_cita())
											+ separador + "HORA="
											+ auxCitas.getHora();
								} else {
									resultado = "ERROR=NO HAY DISPONIBLIDAD CITA";
								}

							} else {
								resultado = "ERROR=NO TIENE SERVICIO CONTRATADO";
							}
						} else {
							resultado = "ERROR=PACIENTE NO EXISTE";
						}

					} catch (ValidacionRunTimeException e) {
						resultado = "ERROR=" + e.getMessage();
					}
				} else if (proceso.equals(CANCELAR_CEX)
						|| proceso.equals(CANCELAR_ODO)) {
					try {
						String nro_identificacion = mapa_datos
								.get("NRO_IDENTIFICACION");
						Paciente paciente = new Paciente();
						paciente.setCodigo_empresa(codigo_empresa);
						paciente.setCodigo_sucursal(codigo_sucursal);
						paciente.setNro_identificacion(nro_identificacion);
						paciente = serviceLocator.getPacienteService()
								.consultar(paciente);

						if (paciente != null) {
							String tipo_consulta = "";
							if (proceso.equals(CANCELAR_CEX)) {
								tipo_consulta = IVias_ingreso.CONSULTA_EXTERNA;
							} else {
								tipo_consulta = IVias_ingreso.ODONTOLOGIA2;
							}
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("codigo_administradora",
									paciente.getCodigo_administradora());
							parametros.put("nro_identificacion",
									paciente.getNro_identificacion());
							parametros.put("estado", "1");
							parametros.put("tipo_consulta", tipo_consulta);

							List<Citas> lista_citas = serviceLocator
									.getCitasService().listar(parametros);

							if (!lista_citas.isEmpty()) {
								Citas citas = lista_citas.get(0);
								citas.setEstado("5");
								serviceLocator.getCitasService().actualizar(
										citas);
								resultado = "OK=CITA_CANCELADA"
										+ separador
										+ "CODIGO_CITA="
										+ citas.getCodigo_cita()
										+ separador
										+ "FECHA_CITA="
										+ formatFecha.format(citas
												.getFecha_cita()) + separador
										+ "HORA=" + citas.getHora();
							} else {
								resultado = "ERROR=NO TIENE CITAS APARTADAS";
							}
						} else {
							resultado = "ERROR=PACIENTE NO EXISTE";
						}

					} catch (ValidacionRunTimeException e) {
						resultado = "ERROR=" + e.getMessage();
					}

				} else if (proceso.equals(CONSULTAR_CITAS)) {
					try {
						String nro_identificacion = mapa_datos
								.get("NRO_IDENTIFICACION");

						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);

						parametros
								.put("nro_identificacion", nro_identificacion);
						parametros.put("vias_ingreso", Utilidades
								.convertirALista(
										IVias_ingreso.CONSULTA_EXTERNA,
										IVias_ingreso.ODONTOLOGIA2));
						parametros.put("estado", "1");
						parametros.put("fecha_inicio", new Date());

						List<Citas> lista_citas = serviceLocator
								.getCitasService().listar(parametros);

						if (!lista_citas.isEmpty()) {
							for (int i = 0; i < lista_citas.size(); i++) {
								Citas citas = lista_citas.get(i);
								if (i != 0) {
									resultado = resultado + separador_linea;
								}
								resultado = "PROGRAMA="
										+ (citas.getTipo_consulta().equals(
												IVias_ingreso.CONSULTA_EXTERNA) ? "CONSULTA EXTERNA"
												: "ODONTOLOGIA")
										+ separador
										+ "FECHA_CITA="
										+ formatFecha.format(citas
												.getFecha_cita())+ separador
												+ "HORA=" + citas.getHora();
							}
						} else {
							resultado = "OK=NO TIENE CITAS APARTADAS";
						}

					} catch (ValidacionRunTimeException e) {
						resultado = "ERROR=" + e.getMessage();
					}
				} else if (proceso.equals(LISTADO_CITAS)) {
					try {
						String fecha_inicio = mapa_datos.get("FECHA_INICIO");

						String fecha_final = mapa_datos.get("FECHA_FINAL");

						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);

						parametros.put("vias_ingreso", Utilidades
								.convertirALista(
										IVias_ingreso.CONSULTA_EXTERNA,
										IVias_ingreso.ODONTOLOGIA2));
						parametros.put("estado", "1");
						parametros.put("fecha_inicio",
								formatFecha.parse(fecha_inicio));
						parametros.put("fecha_final",
								formatFecha.parse(fecha_final));

						List<Citas> lista_citas = serviceLocator
								.getCitasService().listar(parametros);

						if (!lista_citas.isEmpty()) {
							int contador = 0;
							StringBuilder stringBuilder = new StringBuilder();
							for (Citas citas : lista_citas) {
								Paciente paciente = new Paciente();
								paciente.setCodigo_empresa(codigo_empresa);
								paciente.setCodigo_sucursal(codigo_sucursal);
								paciente.setNro_identificacion(citas
										.getNro_identificacion());
								paciente = serviceLocator.getPacienteService()
										.consultar(paciente);
								if (paciente.getTel_res() != null
										&& paciente.getTel_res().length() >= 10) {
									if (contador != 0) {
										stringBuilder.append(separador_linea);
									}
									stringBuilder
											.append("TELEFONO=")
											.append(paciente.getTel_res())
											.append(separador)
											.append("PROGRAMA=")
											.append(citas
													.getTipo_consulta()
													.equals(IVias_ingreso.CONSULTA_EXTERNA) ? "CONSULTA_EXTERNA"
													: "ODONTOLOGIA")
											.append(separador)
											.append("FECHA_CITA=")
											.append(formatFecha.format(citas
													.getFecha_cita())).append(separador).append("HORA="+citas.getHora());
									contador++;
								}
							}
						} else {
							resultado = "OK=NO HAY CITAS APARTADAS";
						}
					} catch (ValidacionRunTimeException e) {
						resultado = "ERROR=" + e.getMessage();
					}
				} else {
					resultado = "ERROR=PROCESO NO ENCONTRADO";
				}
			} else {
				resultado = "ERROR=DATOS DE ENTRADA VACIOS";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultado = "ERROR=" + e.getMessage();
		}

		return resultado;
	}

	private Map<String, String> obtenerDatos(String datos) {
		// log.info("datos ===> " + datos);
		Map<String, String> mapa_datos = new HashMap<String, String>();
		if (!datos.isEmpty()) {
			StringTokenizer stringTokenizer = new StringTokenizer(datos,
					separador);

			while (stringTokenizer.hasMoreTokens()) {
				// Se agrega la coincidencia al ArrayList
				// La funcion group regresa el string encontrado
				String match = stringTokenizer.nextToken();
				String[] valores = match.split("=");
				mapa_datos.put(valores[0], valores[1]);
			}

		}
		return mapa_datos;
	}

	@WebMethod(operationName = "getSeparador")
	public String getSeparador() {
		return separador;
	}

	@WebMethod(operationName = "getSeparadorLinea")
	public String getSeparadorLinea() {
		return separador_linea;
	}

	@WebMethod(operationName = "getFormatoFecha")
	public String getFormatoFecha() {
		return formato_fecha;
	}

	@WebMethod(operationName = "getProcesos")
	public String getProcesos() {
		return VERIFICAR_SERVICIOS + separador + APARTAR_CEX + separador
				+ APARTAR_ODO + separador + CANCELAR_CEX + separador
				+ CANCELAR_ODO + separador + CONSULTAR_CITAS + separador
				+ LISTADO_CITAS;
	}

}
