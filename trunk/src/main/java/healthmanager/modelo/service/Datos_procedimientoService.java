/*
 * Datos_procedimientoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anio_soat;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;
import healthmanager.modelo.bean.Grupos_iss01;
import healthmanager.modelo.bean.Grupos_quirurgicos;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Materiales_sutura;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Porcentajes_cirugias;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Sala_cirugia;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.ContratosDao;
import healthmanager.modelo.dao.Datos_procedimientoDao;
import healthmanager.modelo.dao.Detalle_grupos_procedimientosDao;
import healthmanager.modelo.dao.Grupos_iss01Dao;
import healthmanager.modelo.dao.Grupos_procedimientosDao;
import healthmanager.modelo.dao.Grupos_quirurgicosDao;
import healthmanager.modelo.dao.Materiales_suturaDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.ResolucionDao;
import healthmanager.modelo.dao.Sala_cirugiaDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.res.LineStringToList;
import com.framework.res.Res;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.service.FacturacionService;

@Service("datos_procedimientoService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Datos_procedimientoService implements Serializable {

	@Autowired
	private Datos_procedimientoDao datos_procedimientoDao;
	@Autowired
	private GeneralExtraService generalExtraService;
	@Autowired
	private ContratosDao contratosDao;
	@Autowired
	private Grupos_quirurgicosDao grupos_quirurgicosDao;
	@Autowired
	private Grupos_iss01Dao grupos_iss01Dao;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private ResolucionDao resolucionDao;
	@Autowired
	private Sala_cirugiaDao sala_cirugiaDao;
	@Autowired
	private Materiales_suturaDao materiales_suturaDao;
	@Autowired
	private PacienteDao pacienteDao;

	@Autowired
	private FacturacionService facturacionService;
	@Autowired
	private Porcentajes_cirugiasService porcentajes_cirugiasService;
	@Autowired
	private Grupos_procedimientosDao grupos_procedimientosDao;
	@Autowired
	private Detalle_grupos_procedimientosDao detalle_grupos_procedimientosDao;

	public void guardar(Map<String, Object> datos) {

		List<Datos_procedimiento> lista_procedimiento = (List<Datos_procedimiento>) datos
				.get("lista_procedimiento");
		String accion = (String) datos.get("accion");

		for (Datos_procedimiento datos_procedimiento : lista_procedimiento) {
			if (accion.equalsIgnoreCase("registrar")) {
				crear(datos_procedimiento);
			} else {
				actualizarRegistro(datos_procedimiento);
			}
		}

		if (!lista_procedimiento.isEmpty()) {
			Datos_procedimiento datos_procedimiento = lista_procedimiento
					.get(0);
			facturacionService.actualizarFacturaAutomatico(
					datos_procedimiento.getCodigo_empresa(),
					datos_procedimiento.getCodigo_sucursal(),
					datos_procedimiento.getNro_ingreso(),
					datos_procedimiento.getNro_identificacion());
		}

	}

	public void crear(Datos_procedimiento datos_procedimiento) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", datos_procedimiento.getCodigo_empresa());
			param.put("codigo_sucursal",
					datos_procedimiento.getCodigo_sucursal());
			param.put(
					"id_codigo_grupo",
					datos_procedimiento.getCodigo_procedimiento() != null ? datos_procedimiento
							.getCodigo_procedimiento() : "");
			if (grupos_procedimientosDao.existe(param)) {
				Admision admision = new Admision();
				admision.setCodigo_empresa(datos_procedimiento
						.getCodigo_empresa());
				admision.setCodigo_sucursal(datos_procedimiento
						.getCodigo_sucursal());
				admision.setNro_identificacion(datos_procedimiento
						.getNro_identificacion());
				admision.setNro_ingreso(datos_procedimiento.getNro_ingreso());
				admision = admisionDao.consultar(admision);
				if (admision != null) {
					Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
							.getManuales_tarifarios(admision);
					if (manuales_tarifarios != null) {
						param.put("codigo_grupo",
								datos_procedimiento.getCodigo_procedimiento());
						List<Detalle_grupos_procedimientos> detalle_grupos_procedimientos = detalle_grupos_procedimientosDao
								.listar(param);
						for (Detalle_grupos_procedimientos dtt_grupo : detalle_grupos_procedimientos) {
							Datos_procedimiento datos_procedimientoTemp = Res
									.clonar(datos_procedimiento);
							// calculamos el valor del nuevo procedimiento
							Map<String, Object> pcd = facturacionService
									.obtenerValorPcd(manuales_tarifarios,
											dtt_grupo.getId_procedimiento());
							datos_procedimientoTemp
									.setCodigo_procedimiento(dtt_grupo
											.getId_procedimiento());
							datos_procedimientoTemp
									.setValor_procedimiento((Double) pcd
											.get("valor_pcd"));
							datos_procedimientoTemp
									.setValor_neto(datos_procedimiento
											.getUnidades()
											* datos_procedimientoTemp
													.getValor_procedimiento());
							datos_procedimientoTemp.setValor_real((Double) pcd
									.get("valor_real"));
							datos_procedimientoTemp.setDescuento((Double) pcd
									.get("descuento"));
							datos_procedimientoTemp.setIncremento((Double) pcd
									.get("incremento"));
							datos_procedimientoTemp.setCreacion_user(admision
									.getUltimo_user());
							datos_procedimientoTemp.setUltimo_user(admision
									.getUltimo_user());
							datos_procedimientoTemp.setCodigo_cups((String) pcd
									.get("codigo_cups"));
							crearInterno(datos_procedimientoTemp);
						}
					} else {
						throw new ValidacionRunTimeException(
								"Para realizar esta accion debe existir la admision: "
										+ datos_procedimiento.getNro_ingreso());
					}
				} else {
					throw new ValidacionRunTimeException(
							"Para realizar esta accion debe existir la admision: "
									+ datos_procedimiento.getNro_ingreso());
				}
			} else {
				crearInterno(datos_procedimiento);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite crear los procedimientos teniendo en cuenta los
	 * grupos de los procedimientos descomprimiendo el grupo
	 *
	 * @author Luis Miguel
	 *
	 */
	public void crear(Datos_procedimiento datos_procedimiento,
			Manuales_tarifarios manuales_tarifarios, Admision admision) {
		try {
			// log.info("datos_procedimiento ===> " + datos_procedimiento);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", datos_procedimiento.getCodigo_empresa());
			param.put("codigo_sucursal",
					datos_procedimiento.getCodigo_sucursal());
			param.put(
					"id_codigo_grupo",
					datos_procedimiento.getCodigo_procedimiento() != null ? datos_procedimiento
							.getCodigo_procedimiento() : "");
			if (grupos_procedimientosDao.existe(param)) {
				if (admision != null) {
					if (manuales_tarifarios != null) {
						param.put("codigo_grupo",
								datos_procedimiento.getCodigo_procedimiento());
						List<Detalle_grupos_procedimientos> detalle_grupos_procedimientos = detalle_grupos_procedimientosDao
								.listar(param);
						for (Detalle_grupos_procedimientos dtt_grupo : detalle_grupos_procedimientos) {
							Datos_procedimiento datos_procedimientoTemp = Res
									.clonar(datos_procedimiento);
							// calculamos el valor del nuevo procedimiento
							Map<String, Object> pcd = facturacionService
									.obtenerValorPcd(manuales_tarifarios,
											dtt_grupo.getId_procedimiento());
							datos_procedimientoTemp
									.setCodigo_procedimiento(dtt_grupo
											.getId_procedimiento());
							datos_procedimientoTemp
									.setValor_procedimiento((Double) pcd
											.get("valor_pcd"));
							datos_procedimientoTemp
									.setValor_neto(datos_procedimiento
											.getUnidades()
											* datos_procedimientoTemp
													.getValor_procedimiento());
							datos_procedimientoTemp.setValor_real((Double) pcd
									.get("valor_real"));
							datos_procedimientoTemp.setDescuento((Double) pcd
									.get("descuento"));
							datos_procedimientoTemp.setIncremento((Double) pcd
									.get("incremento"));
							datos_procedimientoTemp.setCreacion_user(admision
									.getUltimo_user());
							datos_procedimientoTemp.setUltimo_user(admision
									.getUltimo_user());
							datos_procedimientoTemp.setCodigo_cups((String) pcd
									.get("codigo_cups"));
							crearInterno(datos_procedimientoTemp);
						}
					} else {
						throw new ValidacionRunTimeException(
								"Para realizar esta accion debe existir la admision: "
										+ datos_procedimiento.getNro_ingreso());
					}
				} else {
					throw new ValidacionRunTimeException(
							"Para realizar esta accion debe existir la admision: "
									+ datos_procedimiento.getNro_ingreso());
				}
			} else {
				crearInterno(datos_procedimiento);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearInterno(Datos_procedimiento datos_procedimiento) {
		try {
			datos_procedimientoDao.crear(datos_procedimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearLote(List<Datos_procedimiento> listaDatos_procedimientos) {
		try {
			for (Datos_procedimiento datos_procedimiento : listaDatos_procedimientos) {
				crear(datos_procedimiento);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizarRegistro(Datos_procedimiento datos_procedimiento) {
		try {
			int result = datos_procedimientoDao.actualizar(datos_procedimiento);
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizarActualizarFactura(
			Datos_procedimiento datos_procedimiento) {
		try {
			int result = datos_procedimientoDao.actualizar(datos_procedimiento);
			facturacionService.actualizarFacturaAutomatico(
					datos_procedimiento.getCodigo_empresa(),
					datos_procedimiento.getCodigo_sucursal(),
					datos_procedimiento.getNro_ingreso(),
					datos_procedimiento.getNro_identificacion());
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Datos_procedimiento consultar(Datos_procedimiento datos_procedimiento) {
		try {
			return datos_procedimientoDao.consultar(datos_procedimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Datos_procedimiento consultar_auxiliar(
			Datos_procedimiento datos_procedimiento) {
		try {
			return datos_procedimientoDao
					.consultar_auxiliar(datos_procedimiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminarActualizarFactura(Datos_procedimiento datos_procedimiento) {
		try {
			int result = datos_procedimientoDao.eliminar(datos_procedimiento);
			facturacionService.actualizarFacturaAutomatico(
					datos_procedimiento.getCodigo_empresa(),
					datos_procedimiento.getCodigo_sucursal(),
					datos_procedimiento.getNro_ingreso(),
					datos_procedimiento.getNro_identificacion());
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminarRegistro(Datos_procedimiento datos_procedimiento) {
		try {
			int result = datos_procedimientoDao.eliminar(datos_procedimiento);
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Datos_procedimiento> listarTabla(Map<String, Object> parametros) {
		try {
			return datos_procedimientoDao.listarTabla(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Datos_procedimiento> listarResultados(
			Map<String, Object> parametros) {
		try {
			return datos_procedimientoDao.listarResultados(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Object datos_procedimiento) {
		return this.datos_procedimientoDao.existe(datos_procedimiento);
	}

	public List<Map> listar_quirurgicos(Map<String, Object> parameters) {
		try {
			return datos_procedimientoDao.listar_quirurgicos(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearCirugia(
			List<Datos_procedimiento> listaDatos_procedimientos,
			String codigo_empresa, String codigo_sucursal, Long codigo_registro) {
		try {

			int id = 1;
			String nro_identificacion = "";
			String nro_ingreso = "";
			for (Datos_procedimiento datos_procedimiento : listaDatos_procedimientos) {
				// datos_procedimiento.setNro_factura(consecutivoService
				// .getZeroFill(nro_factura, 10));
				datos_procedimiento.setConsecutivo_registro(id + "");
				double valor_procedimiento = datos_procedimiento
						.getValor_cirujano()
						+ datos_procedimiento.getValor_anestesiologo()
						+ datos_procedimiento.getValor_ayudante()
						+ datos_procedimiento.getValor_sala()
						+ datos_procedimiento.getValor_materiales()
						+ datos_procedimiento.getValor_perfusionista();
				double valor_real = datos_procedimiento.getValor_real();

				datos_procedimiento.setValor_procedimiento(valor_procedimiento);
				datos_procedimiento.setValor_neto(datos_procedimiento
						.getValor_procedimiento());
				datos_procedimiento.setValor_real(valor_real);
				datos_procedimientoDao.crear(datos_procedimiento);

				nro_identificacion = datos_procedimiento
						.getNro_identificacion();
				nro_ingreso = datos_procedimiento.getNro_ingreso();
				id++;
			}
			// if (actualizar_consecutivo) {
			// consecutivoService.actualizarConsecutivo(codigo_empresa,
			// codigo_sucursal, "PROCEDIMIENTOS", nro_factura);
			// }

			facturacionService.actualizarFacturaAutomatico(codigo_empresa,
					codigo_sucursal, nro_ingreso, nro_identificacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}

	}

	public double[] consultarValorCirugia(Map<String, Object> parameters) {
		try {
			String codigo_empresa = (String) parameters.get("codigo_empresa");
			String codigo_sucursal = (String) parameters.get("codigo_sucursal");
			String nro_identificacion = (String) parameters
					.get("nro_identificacion");
			String nro_ingreso = (String) parameters.get("nro_ingreso");
			String anio = (String) parameters.get("anio");
			String manual = (String) parameters.get("manual");
			String grupo = (String) parameters.get("grupo");
			String tipo_grupo = (String) parameters.get("tipo_grupo");
			String cobra = (String) parameters.get("cobra");
			double uvr = (Double) parameters.get("uvr");
			String tipo_sala = (String) parameters.get("tipo_sala");
			Admision admision = (Admision) parameters.get("admision");

			if (admision == null) {
				admision = new Admision();
				admision.setCodigo_empresa(codigo_empresa);
				admision.setCodigo_sucursal(codigo_sucursal);
				admision.setNro_identificacion(nro_identificacion);
				admision.setNro_ingreso(nro_ingreso);
				admision = admisionDao.consultar(admision);
			}

			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(codigo_empresa);
			contratos.setCodigo_sucursal(codigo_sucursal);
			contratos.setCodigo_administradora((admision != null ? admision
					.getCodigo_administradora() : ""));
			contratos
					.setId_plan((admision != null ? admision.getId_plan() : ""));
			contratos = contratosDao.consultar(contratos);

			double descuento = 0, incremento = 0;
			double valor = 0, valor_real = 0;
			if (cobra.equals("S")) {
				Anio_soat anio_soat = new Anio_soat();
				anio_soat.setAnio(anio);
				anio_soat = generalExtraService.consultar(anio_soat);

				if (manual.equals("SOAT")) {
					// Obtenemos el porcentaje del grupo quirurgico //
					// y hacemos el incremento o descuento si el
					// plan lo tiene//
					Grupos_quirurgicos grupos = new Grupos_quirurgicos();
					grupos.setCodigo_grupo(grupo);
					grupos.setTipo_grupo(tipo_grupo);
					grupos = grupos_quirurgicosDao.consultar(grupos);
					if (grupos != null && anio_soat != null) {
						valor = (cobra.equals("S") ? (grupos.getPorcentaje() * anio_soat
								.getValor_anio()) : 0.0);
						valor_real = (int) valor;
						if (contratos != null && manuales_tarifarios != null) {
							if (manuales_tarifarios.getTipo_general().equals(
									"01")) {
								descuento = (int) (valor * (manuales_tarifarios
										.getGeneral() / 100));
								valor -= descuento;
							} else if (manuales_tarifarios.getTipo_general()
									.equals("02")) {
								incremento = (int) (valor * (manuales_tarifarios
										.getGeneral() / 100));
								valor += incremento;
							}
						}
						valor = (int) valor;
					}
				} else if (manual.equals("ISS01") || manual.equals("ISS04")) {
					// Obtenemos la uvr del grupo quirurgico //
					// y hacemos el incremento o descuento si el
					// plan lo tiene//

					/**
					 * ****** Preguntamos si el tipo de grupo es diferente a
					 * sala o materiales
					 ***********
					 */
					if (!tipo_grupo.equals("SALA")
							&& !tipo_grupo.equals("MATERIALES")) {
						Grupos_iss01 grupos = new Grupos_iss01();
						grupos.setCodigo(tipo_grupo);
						grupos = grupos_iss01Dao.consultar(grupos);
						if (grupos != null) {
							valor = (cobra.equals("S") ? (grupos.getUvr() * uvr)
									: 0.0);
							valor_real = (int) valor;
							if (contratos != null
									&& manuales_tarifarios != null) {
								if (manuales_tarifarios.getTipo_general()
										.equals("01")) {
									descuento = (int) (valor * (manuales_tarifarios
											.getGeneral() / 100));
									valor -= descuento;
								} else if (manuales_tarifarios
										.getTipo_general().equals("02")) {
									incremento = (int) (valor * (manuales_tarifarios
											.getGeneral() / 100));
									valor += incremento;
								}
							}
							valor = (int) valor;
						}
					} else if (tipo_grupo.equals("SALA")) {// Si es de tipo sala
						String codigo_sala = Utilidades.getCodigo_sala(
								tipo_sala, uvr);

						Sala_cirugia sala = new Sala_cirugia();
						sala.setTipo((tipo_sala.equals("01") ? "QUIROFANO"
								: "ESPECIAL"));
						sala.setCodigo(codigo_sala);
						sala = sala_cirugiaDao.consultar(sala);
						if (sala != null) {
							valor = (cobra.equals("S") ? (sala.getValor())
									: 0.0);
						} else if (tipo_sala.equals("01")) {
							valor = uvr * 1410;
						}
						valor_real = (int) valor;
						if (contratos != null && manuales_tarifarios != null) {
							if (manuales_tarifarios.getTipo_general().equals(
									"01")) {
								descuento = (int) (valor * (manuales_tarifarios
										.getGeneral() / 100));
								valor -= descuento;
							} else if (manuales_tarifarios.getTipo_general()
									.equals("02")) {
								incremento = (int) (valor * (manuales_tarifarios
										.getGeneral() / 100));
								valor += incremento;
							}
						}
						valor = (int) valor;
					} else {// Sino entonces es material de sutura
						String codigo = Utilidades.getCodigo_material(uvr);
						Materiales_sutura materiales_sutura = new Materiales_sutura();
						materiales_sutura.setCodigo(codigo);
						materiales_sutura = materiales_suturaDao
								.consultar(materiales_sutura);
						if (materiales_sutura != null) {
							valor = (cobra.equals("S") ? (materiales_sutura
									.getValor()) : 0.0);
						} else {
							valor = 0;
							// valor = Double.parseDouble(uvr);
						}
						valor_real = (int) valor;
						if (contratos != null && manuales_tarifarios != null) {
							if (manuales_tarifarios.getTipo_general().equals(
									"01")) {
								descuento = (int) (valor * (manuales_tarifarios
										.getGeneral() / 100));
								valor -= descuento;
							} else if (manuales_tarifarios.getTipo_general()
									.equals("02")) {
								incremento = (int) (valor * (manuales_tarifarios
										.getGeneral() / 100));
								valor += incremento;
							}
						}
						// System.out.println("valor material: "+materiales_sutura.getValor()
						// + " - Uvr: "+Double.parseDouble(uvr));
						valor = (int) valor;
					}
				}
			}

			return new double[] { valor, valor_real, descuento, incremento };
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean contieneExcepciones(Map<String, Object> map) {
		return datos_procedimientoDao.contieneExcepciones(map);
	}

	public void crearDesdeRips(Map<String, Object> map) {
		try {
			List<String> listTXT = (List<String>) map.get("list_text");
			String codigo_empresa = (String) map.get("codigo_empresa");
			String codigo_sucursal = (String) map.get("codigo_sucursal");
			String codigo_usuario = (String) map.get("codigo_usuario");
			int i_t = 0;
			boolean registros = false;
			for (String fileTxt : listTXT) {
				StringTokenizer stringTokenizer = new StringTokenizer(fileTxt,
						"\n");
				int i = 0;
				++i_t;
				while (stringTokenizer.hasMoreTokens()) {
					String linea = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(linea);
					++i;
					if (in == null) {
						throw new ValidacionRunTimeException(
								"Error al crear la linea de " + i
										+ " del archivo nro " + i_t);
					}

					if (in.size() != 15) {
						throw new ValidacionRunTimeException(
								"campos no correctos (" + in.size()
										+ ") al crear la linea de " + i
										+ " del archivo nro " + i_t);
					}

					// obtenemos informacion de rips
					// String codigo_prestador = in.get(1);
					String tipo_id = in.get(2);
					String nro_identificacion = in.get(3);
					String fecha_documento = in.get(4);
					String nro_autorizacion = in.get(5);
					String codigo_procedimiento = in.get(6);
					String ambito_realizacion = in.get(7);
					String finalidad_procedimiento = in.get(8);
					String personal_atiende = in.get(9);
					String diagnostico_principal = in.get(10);
					String diagnostico_relacinado = in.get(11);
					String complicacion = in.get(12);
					String forma_realizacion = in.get(13);
					String valor_procedimiento = in.get(14);

					// valicamos tipo de identificacion
					validador(tipo_id, "Tipo de identificacion (" + tipo_id
							+ ") no valido para datos procedimiento nro " + i_t
							+ " linea " + i, "CC", "CE", "PA", "RC", "TI",
							"AS", "MS", "NU");

					// ambito realizacion
					validador(ambito_realizacion, "Ambito de realizacion ("
							+ ambito_realizacion + ") no valido nro " + i_t
							+ " linea " + i, "1", "2", "3");

					//
					validador(finalidad_procedimiento,
							"Finalidad de la consulta (" + ambito_realizacion
									+ ") no valido nro " + i_t + " linea " + i,
							"1", "2", "3", "4", "5");

					//
					validador(personal_atiende, "Personal atiende ("
							+ ambito_realizacion + ") no valido nro " + i_t
							+ " linea " + i, "1", "2", "3", "4", "5");

					double valor_proc = 0;
					if (!valor_procedimiento.trim().isEmpty()
							&& valor_procedimiento.matches("[0-9]*$")) {
						valor_proc = Double.parseDouble(valor_procedimiento);
					}

					Paciente paciente = new Paciente();
					paciente.setCodigo_empresa(codigo_empresa);
					paciente.setCodigo_sucursal(codigo_sucursal);
					paciente.setNro_identificacion(nro_identificacion);
					paciente = pacienteDao.consultar(paciente);

					if (paciente != null) {
						Timestamp fecha = LineStringToList
								.convertTo(fecha_documento);

						Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
						datos_procedimiento.setCodigo_empresa(codigo_empresa);
						datos_procedimiento.setCodigo_sucursal(codigo_sucursal);
						// importado estadistica IE
						datos_procedimiento.setCodigo_registro(null);
						datos_procedimiento
								.setNro_identificacion(nro_identificacion);
						datos_procedimiento
								.setCodigo_procedimiento(codigo_procedimiento);

						// si no existe el lo crea
						if (datos_procedimientoDao
								.consultar(datos_procedimiento) == null) {
							datos_procedimiento.setTipo_identificacion(tipo_id);
							datos_procedimiento
									.setCodigo_administradora(paciente
											.getCodigo_administradora());
							datos_procedimiento.setId_plan("NA");
							datos_procedimiento.setCodigo_prestador("NA");
							datos_procedimiento.setNro_ingreso("NA");
							datos_procedimiento.setFecha_procedimiento(fecha);
							datos_procedimiento
									.setNumero_autorizacion(nro_autorizacion);
							datos_procedimiento
									.setValor_procedimiento(valor_proc);
							datos_procedimiento.setUnidades(1);
							datos_procedimiento.setCopago(0.0);
							datos_procedimiento.setValor_neto(valor_proc);
							datos_procedimiento
									.setCodigo_cups(codigo_procedimiento);

							datos_procedimiento
									.setAmbito_procedimiento(ambito_realizacion);
							datos_procedimiento
									.setFinalidad_procedimiento(finalidad_procedimiento);
							datos_procedimiento
									.setPersonal_atiende(personal_atiende);
							datos_procedimiento
									.setForma_realizacion(forma_realizacion);
							datos_procedimiento
									.setCodigo_diagnostico_principal(diagnostico_principal);
							datos_procedimiento
									.setCodigo_diagnostico_relacionado(diagnostico_relacinado);
							datos_procedimiento.setComplicacion(complicacion);
							datos_procedimiento.setCancelo_copago("N");
							datos_procedimiento.setCodigo_programa("");

							datos_procedimiento.setCreacion_date(fecha);
							datos_procedimiento.setUltimo_update(fecha);
							datos_procedimiento
									.setCreacion_user(codigo_usuario);
							datos_procedimiento.setUltimo_user(codigo_usuario);
							datos_procedimiento.setValor_real(0);
							datos_procedimiento.setDescuento(0);
							datos_procedimiento.setIncremento(0);
							datos_procedimiento.setRealizado_en("");
							datos_procedimientoDao.crear(datos_procedimiento);
							registros = true;
						}
					}
				}
			}
			if (!registros) {
				throw new ValidacionRunTimeException(
						"No hubo registros puede ser que los pacientes no existan, o ya tienen esos procedimientos registrados como realizados.");
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private void validador(String valor, String msj, String... comparadores) {
		boolean existe = false;
		for (String comparador : comparadores) {
			if (comparador.equalsIgnoreCase(comparador)) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			throw new ValidacionRunTimeException(msj);
		}
	}

	public Timestamp getUltimoProcedimiento(Map<String, Object> param) {
		List<Timestamp> listado = datos_procedimientoDao
				.getUltimoProcedimiento(param);
		return listado.isEmpty() ? null : listado.get(0);
	}

	public void actualizarCirugias(String codigo_empresa,
			String codigo_sucursal, String nro_identificacion,
			String nro_ingreso) {
		try {
			Admision admision = new Admision();
			admision.setCodigo_empresa(codigo_empresa);
			admision.setCodigo_sucursal(codigo_sucursal);
			admision.setNro_identificacion(nro_identificacion);
			admision.setNro_ingreso(nro_ingreso);
			admision = admisionDao.consultar(admision);

			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);

			String anio = (manuales_tarifarios != null ? manuales_tarifarios
					.getAnio() : "");

			Maestro_manual maestro_manual = manuales_tarifarios
					.getMaestro_manual();

			String manual = (manuales_tarifarios != null ? maestro_manual
					.getTipo_manual() : "");

			Resolucion resolucion = new Resolucion();
			resolucion.setCodigo_empresa(codigo_empresa);
			resolucion = resolucionDao.consultar(resolucion);
			String cobra_material = (resolucion != null ? resolucion
					.getCobrar_materiales() : "N");
			String cobra_cirugia = (resolucion != null ? resolucion
					.getCobrar_cirugia() : "N");
			String cobrar_cirugia_soat = (resolucion != null ? resolucion
					.getCobrar_cirugia_soat() : "N");

			List<Map> lista_map = consultarCirugias(codigo_empresa,
					codigo_sucursal, nro_identificacion, nro_ingreso);
			for (int i = 0; i < lista_map.size(); i++) {
				Map data = lista_map.get(i);
				List lista_datos_pro = (List) data.get("detalle_cirugias");
				List<Map<String, Object>> lista_valores_auxiliares = new LinkedList<Map<String, Object>>();
				for (int j = 0; j < lista_datos_pro.size(); j++) {
					Datos_procedimiento dp = (Datos_procedimiento) lista_datos_pro
							.get(j);
					Map<String, Object> pcd = facturacionService
							.obtenerValorPcd(manuales_tarifarios,
									dp.getCodigo_prestador());

					String cobra[] = { dp.getCobra_cirujano(),
							dp.getCobra_anestesiologo(),
							dp.getCobra_ayudante(), dp.getCobra_sala(),
							dp.getCobra_materiales(),
							dp.getCobra_perfusionista() };

					String grupo = (String) pcd.get("grupo");
					double uvr = (Double) pcd.get("uvr");
					dp.setValor_real(0.0);
					dp.setValor_procedimiento(0.0);

					Map<String, Object> mapaValoresAuxiliares = new TreeMap<String, Object>();

					// Recorremos los tipos de cobros //
					for (int k = 1; k <= 6; k++) {
						String tipo_grupo = Utilidades.getTipo_grupo_cirugia(k
								+ "", manual);
						Map<String, Object> parameters = new HashMap();
						parameters
								.put("codigo_empresa", dp.getCodigo_empresa());
						parameters.put("codigo_sucursal",
								dp.getCodigo_sucursal());
						parameters.put("nro_identificacion",
								dp.getNro_identificacion());
						parameters.put("nro_ingreso", dp.getNro_ingreso());
						parameters.put("anio", anio);
						parameters.put("manual", manual);
						parameters.put("grupo", grupo);
						parameters.put("tipo_grupo", tipo_grupo);
						parameters.put("cobra", cobra[k]);
						parameters.put("uvr", uvr);
						parameters.put("tipo_sala", dp.getTipo_sala());
						double result[] = consultarValorCirugia(parameters);

						if (k == 1) {
							dp.setValor_cirujano(result[0]);
						} else if (k == 2) {
							dp.setValor_anestesiologo(result[0]);
						} else if (k == 3) {
							dp.setValor_ayudante(result[0]);
						} else if (k == 4) {
							dp.setValor_sala(result[0]);
						} else if (k == 5) {
							if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)
									&& (grupo.equals("20")
											|| grupo.equals("21")
											|| grupo.equals("22") || grupo
												.equals("22"))
									&& cobra_material.equals("S")) {
								result[0] = dp.getValor_materiales();
								result[1] = dp.getValor_materiales();
							} else if (manual
									.equals(IConstantes.TIPO_MANUAL_ISS01)
									&& uvr > 170 && cobra_material.equals("S")) {
								result[0] = dp.getValor_materiales();
								result[1] = dp.getValor_materiales();
							}
							dp.setValor_materiales(result[0]);
						} else if (k == 6) {
							dp.setValor_perfusionista(result[0]);
						}

						mapaValoresAuxiliares
								.put(k + "_valor_auxiliar", result);

						dp.setValor_real(dp.getValor_real() + result[1]);
						dp.setValor_procedimiento(dp.getValor_procedimiento()
								+ result[1]);
					}

					dp.setValor_neto(dp.getValor_procedimiento()
							* dp.getUnidades());
					datos_procedimientoDao.actualizar(dp);
					lista_datos_pro.set(j, dp);
					lista_valores_auxiliares.add(j, mapaValoresAuxiliares);
				}

				// Ahora calculamos el porcentaje segun //
				guardarLiquidacionCirugia(lista_datos_pro,
						lista_valores_auxiliares, manuales_tarifarios,
						cobra_cirugia, cobrar_cirugia_soat);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarLiquidacionCirugia(List lista_datos_pro,
			List<Map<String, Object>> lista_valores_auxiliares,
			Manuales_tarifarios manual, String cobra_cirugia,
			String cobrar_cirugia_soat) {
		Maestro_manual maestro_manual = manual.getMaestro_manual();
		try {
			List<Datos_procedimiento> lista_aux = new LinkedList<Datos_procedimiento>(
					lista_datos_pro);
			ServiciosDisponiblesUtils.ordenarProcedimientosQuirurgicos(
					lista_aux, manual);
			lista_datos_pro.clear();

			for (int i = 0; i < lista_aux.size(); i++) {
				Datos_procedimiento data = lista_aux.get(i);

				Map<String, Object> mapaValoresAuxiliares = lista_valores_auxiliares
						.get(i);
				double aux_valor_cirujano = (Double) mapaValoresAuxiliares
						.get("1_valor_auxiliar");
				double aux_valor_anestesiologo = (Double) mapaValoresAuxiliares
						.get("2_valor_auxiliar");
				double aux_valor_ayudante = (Double) mapaValoresAuxiliares
						.get("3_valor_auxiliar");
				double aux_valor_sala = (Double) mapaValoresAuxiliares
						.get("4_valor_auxiliar");
				double aux_valor_materiales = (Double) mapaValoresAuxiliares
						.get("5_valor_auxiliar");
				double aux_valor_perfusionista = (Double) mapaValoresAuxiliares
						.get("6_valor_auxiliar");

				Datos_procedimiento aux = new Datos_procedimiento(data);

				if (i == 0) {
					datos_procedimientoDao.actualizar(aux);
					lista_datos_pro.add(aux);
				} else {
					String aux_manual = (maestro_manual.getTipo_manual()
							.equals(IConstantes.TIPO_MANUAL_SOAT) ? IConstantes.TIPO_MANUAL_SOAT
							: IConstantes.TIPO_MANUAL_ISS01);
					Porcentajes_cirugias porcentajes_cirugias = new Porcentajes_cirugias();
					porcentajes_cirugias.setManual_tarifario(aux_manual);
					porcentajes_cirugias.setTipo_intervencion(data
							.getTipo_intervencion());
					// //log.info("porcentajes_cirugias antes: "+porcentajes_cirugias);
					porcentajes_cirugias = porcentajes_cirugiasService
							.consultar(porcentajes_cirugias);
					// //log.info("porcentajes_cirugias despues: "+porcentajes_cirugias);
					if (porcentajes_cirugias == null) {
						throw new Exception(
								"No se ha definido porcentaje de cirugías");
					}

					aux.setValor_cirujano(aux.getValor_cirujano()
							* porcentajes_cirugias.getPorcentaje_cirujano());
					aux.setValor_anestesiologo(aux.getValor_anestesiologo()
							* porcentajes_cirugias
									.getPorcentaje_anestesiologo());
					aux.setValor_ayudante(aux.getValor_ayudante()
							* porcentajes_cirugias.getPorcentaje_ayudante());
					aux.setValor_sala(aux.getValor_sala()
							* porcentajes_cirugias.getPorcentaje_sala());
					aux.setValor_materiales(aux.getValor_materiales()
							* porcentajes_cirugias.getPorcentaje_materiales());
					aux.setValor_perfusionista(aux.getValor_perfusionista()
							* porcentajes_cirugias
									.getPorcentaje_perfusionista());

					validarCantidad_cirugias(aux, cobrar_cirugia_soat,
							cobra_cirugia, data.getTipo_intervencion(),
							maestro_manual.getTipo_manual(), i);

					Datos_procedimiento aux2 = new Datos_procedimiento();
					aux2.setValor_cirujano(aux_valor_cirujano
							* porcentajes_cirugias.getPorcentaje_cirujano());
					aux2.setValor_anestesiologo(aux_valor_anestesiologo
							* porcentajes_cirugias
									.getPorcentaje_anestesiologo());
					aux2.setValor_ayudante(aux_valor_ayudante
							* porcentajes_cirugias.getPorcentaje_ayudante());
					aux2.setValor_sala(aux_valor_sala
							* porcentajes_cirugias.getPorcentaje_sala());
					aux2.setValor_materiales(aux_valor_materiales
							* porcentajes_cirugias.getPorcentaje_materiales());
					aux2.setValor_perfusionista(aux_valor_perfusionista
							* porcentajes_cirugias
									.getPorcentaje_perfusionista());

					validarCantidad_cirugias(aux2, cobrar_cirugia_soat,
							cobra_cirugia, data.getTipo_intervencion(),
							maestro_manual.getTipo_manual(), i);

					aux.setValor_neto(aux2.getValor_cirujano()
							+ aux2.getValor_anestesiologo()
							+ aux2.getValor_ayudante() + aux2.getValor_sala()
							+ aux2.getValor_materiales()
							+ aux_valor_perfusionista);

					datos_procedimientoDao.actualizar(aux);
					lista_datos_pro.add(aux);
				}

			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	// Este metodo es para validar cuano las cirugías se pasen de mas de dos
	// registros y sean intervencion multiple igual vía//
	public void validarCantidad_cirugias(Datos_procedimiento aux,
			String cobrar_cirugia_soat, String cobra_cirugia,
			String tipo_intervencion, String manual, int i) throws Exception {

		try {
			if (tipo_intervencion.equals("3")) {
				if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
					if (cobrar_cirugia_soat.equals("N")) {
						aux.setValor_sala(0.0);
						aux.setValor_materiales(0.0);
					}
				} else {
					if (i > 1) {
						if (cobra_cirugia.equals("N")) {
							aux.setValor_cirujano(0.0);
							aux.setValor_anestesiologo(0.0);
							aux.setValor_ayudante(0.0);
							aux.setValor_sala(0.0);
							aux.setValor_materiales(0.0);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}

	}

	public List<Map> consultarCirugias(String codigo_empresa,
			String codigo_sucursal, String nro_identificacion,
			String nro_ingreso) {
		try {
			List<Map> list = new LinkedList<Map>();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("nro_identificacion", nro_identificacion);
			parameters.put("nro_ingreso", nro_ingreso);
			parameters.put("es_quirurgico", "S");
			List<Map> lista_datos = listar_quirurgicos(parameters);
			for (Map map : lista_datos) {
				String nro_factura = (String) map.get("nro_factura");

				Map<String, Object> parameters2 = new HashMap();
				parameters2.put("codigo_empresa", codigo_empresa);
				parameters2.put("codigo_sucursal", codigo_sucursal);
				parameters2.put("nro_factura", nro_factura);
				parameters2.put("order_by",
						"grupo desc,valor_procedimiento desc");

				Map<String, Object> data = new TreeMap<String, Object>();
				data.put("codigo_empresa", codigo_empresa);
				data.put("codigo_sucursal", codigo_sucursal);
				data.put("nro_factura", nro_factura);
				data.put("detalle_cirugias", listarTabla(parameters2));
				list.add(data);
			}

			return list;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Map<String, Object> getFechaRealizacion(Map<String, Object> parametro) {
		return datos_procedimientoDao.getFechaRealizacion(parametro);
	}

}
