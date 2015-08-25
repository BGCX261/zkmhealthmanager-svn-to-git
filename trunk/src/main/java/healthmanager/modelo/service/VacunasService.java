/*
 * VacunasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.controller.VacunasPaiAction;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.bean.Vacunas_fuera_esquema_vacunacion;
import healthmanager.modelo.dao.Datos_procedimientoDao;
import healthmanager.modelo.dao.Esquema_vacunacionDao;
import healthmanager.modelo.dao.Manuales_tarifariosDao;
import healthmanager.modelo.dao.VacunasDao;
import healthmanager.modelo.dao.Vacunas_fuera_esquema_vacunacionDao;
import healthmanager.modelo.dao.Vacunas_pacientesDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConsecutivos;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

@Service("vacunasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class VacunasService implements Serializable{

	@Autowired
	private VacunasDao vacunasDao;
	@Autowired
	private Vacunas_pacientesDao vacunas_pacientesDao;

	@Autowired
	private Esquema_vacunacionDao esquema_vacunacionDao;

	@Autowired
	private Datos_procedimientoDao datos_procedimientoDao;

	@Autowired
	private Manuales_tarifariosDao manuales_tarifariosDao;

	@Autowired
	private Datos_procedimientoService datos_procedimientoService;
	@Autowired
	private AdmisionService admisionService;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Vacunas_fuera_esquema_vacunacionDao vacunas_fuera_esquema_vacunacionDao;


	public void crear(Vacunas vacunas) {
		try {
			if (consultar(vacunas) != null) {
				throw new HealthmanagerException(
						"Vacunas ya se encuentra en la base de datos");
			}
			vacunasDao.crear(vacunas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Vacunas vacunas) {
		try {
			return vacunasDao.actualizar(vacunas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Vacunas consultar(Vacunas vacunas) {
		try {
			return vacunasDao.consultar(vacunas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Vacunas vacunas) {
		try {
			return vacunasDao.eliminar(vacunas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Vacunas> listar(Map<String, Object> parameters) {
		try {
			return vacunasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return vacunasDao.existe(parameters);
	}

	public void guardarEsquema(Map<String, Object> map) {
		try {
			Vacunas vacunas = (Vacunas) map.get("vacunas");
			List<Esquema_vacunacion> list_esq = (List<Esquema_vacunacion>) map
					.get("esquema_vacunacion");

			if (vacunasDao.consultar(vacunas) != null) {
				vacunasDao.actualizar(vacunas);
			} else {
				vacunasDao.crear(vacunas);
			}
			Esquema_vacunacion esquema_vacunacion = new Esquema_vacunacion();
			esquema_vacunacion.setCodigo_empresa(vacunas.getCodigo_empresa());
			esquema_vacunacion.setCodigo_sucursal(vacunas.getCodigo_sucursal());
			esquema_vacunacion.setCodigo_vacuna(vacunas.getCodigo_procedimiento());
			esquema_vacunacionDao.eliminar(esquema_vacunacion);

			int i = 0;
			for (Esquema_vacunacion esquema_vacunacionTemp : list_esq) {
				esquema_vacunacionTemp.setCodigo_vacuna(vacunas
						.getCodigo_procedimiento());
				esquema_vacunacionTemp.setCodigo_empresa(vacunas
						.getCodigo_empresa());
				esquema_vacunacionTemp.setCodigo_sucursal(vacunas
						.getCodigo_sucursal());
				esquema_vacunacionTemp.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				esquema_vacunacionTemp.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				esquema_vacunacionTemp.setCreacion_user(vacunas
						.getCreacion_user());
				esquema_vacunacionTemp.setUltimo_user(vacunas.getUltimo_user());
				esquema_vacunacionTemp.setConsecutivo(++i);

				if (esquema_vacunacionTemp.getDescripcion().trim().isEmpty()) {
					throw new ValidacionRunTimeException(
							"El campo descripcion en la fila " + i
									+ " no puede ser vacio.");
				}

				if (esquema_vacunacionDao.consultar(esquema_vacunacionTemp) != null) {
					esquema_vacunacionDao.actualizar(esquema_vacunacionTemp);
				} else {
					esquema_vacunacionDao.crear(esquema_vacunacionTemp);
				}
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardar(Map<String, Object> params) {
		try {
			List<Map<String, Object>> listado_pacientes = (List<Map<String, Object>>) params
					.get("pacientes");
			List<Map<String, Object>> listado_vacunas = (List<Map<String, Object>>) params
					.get("vacunas");
			Usuarios usuarios = (Usuarios) params.get("usuario");
			Usuarios vacunador = (Usuarios) params.get("vacunador");
			Timestamp fecha = (Timestamp) params.get("fecha_vacunacion");
			// Contratos contrato = (Contratos) params.get("contrato");
			// Administradora administradora = (Administradora)
			// params.get("administradora");
			// ServiceLocator serviceLocator = (ServiceLocator)
			// params.get("serviceLocator");

			List<Map<String, Object>> dtt_servicios = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> mapTempPacientes : listado_pacientes) {
				Paciente paciente = (Paciente) mapTempPacientes
						.get(VacunasPaiAction.PACIENTE_VACUNADO);

				// Manuales_tarifarios manuales_tarifarios =
				// (Manuales_tarifarios)
				// mapTempPacientes.get(VacunasPaiAction.MANUAL_TARIFARIO);
				int i = 0;
				for (Map<String, Object> map : listado_vacunas) {
					//log.info(">>>>>>>" + map);
					Vacunas vacunas = (Vacunas) map
							.get(VacunasPaiAction.VACUNAS);
					Esquema_vacunacion esquema_vacunacion = (Esquema_vacunacion) map
							.get(VacunasPaiAction.ESQUEMA_VACUNACION);
					// Articulo articulo = (Articulo)
					// map.get(VacunasPaiAction.JERINGA);
					//
					//
					// int anio =
					// L2HContraintDate.getAnio(paciente.getFecha_nacimiento());
					// int mes =
					// L2HContraintDate.getMes(paciente.getFecha_nacimiento());

					++i;
					/* registramos vacunas */
					Map<String, Object> map_vacunacion = new HashMap<String, Object>();
					if (esquema_vacunacion != null) {
						// if(articulo == null)throw new
						// ValidacionRunTimeException("La vacuna en la línea #"
						// + i + " debe seleccionar la Jeringa");
						map_vacunacion.put("esquema", esquema_vacunacion);
						// Vacunas_pacientes vacunas_pacientes = new
						// Vacunas_pacientes();
						// vacunas_pacientes.setCodigo_empresa(paciente.getCodigo_empresa());
						// vacunas_pacientes.setCodigo_sucursal(paciente.getCodigo_sucursal());
						// vacunas_pacientes.setCodigo_cups_vacuna(vacunas.getCodigo_cups());
						// vacunas_pacientes.setNro_identificacion(paciente.getNro_identificacion());
						// vacunas_pacientes.setId_esquema_vacunacion(esquema_vacunacion.getConsecutivo());
						// vacunas_pacientes.setDosis(esquema_vacunacion.getDosis());
						// vacunas_pacientes.setCreacion_date(new Timestamp(new
						// GregorianCalendar().getTimeInMillis()));
						// vacunas_pacientes.setUltimo_update(new Timestamp(new
						// GregorianCalendar().getTimeInMillis()));
						// vacunas_pacientes.setCreacion_user(usuarios.getCodigo().toString());
						// vacunas_pacientes.setUltimo_user(usuarios.getCodigo().toString());
						// vacunas_pacientes.setCodigo_jeringa(articulo.getCodigo_articulo());
						// vacunas_pacientes.setValor_jeringa(0);
						// vacunas_pacientes.setAnio(anio);
						// vacunas_pacientes.setMeses(mes + 1);
						// vacunas_pacientes.setVia_administracion(esquema_vacunacion
						// != null ? esquema_vacunacion.getVia_administracion()
						// : "");
						// vacunas_pacientes.setDescripcion_edad(vacunas.getDescripcion());
						// vacunas_pacientes.setFecha_vacunacion(new
						// Timestamp(Calendar.getInstance().getTimeInMillis()));
						// vacunas_pacientes.setRespuesta_4505(esquema_vacunacion
						// != null ? esquema_vacunacion.getRespuesta_4505() + ""
						// : "");
						// vacunas_pacientes.setEstado(IConstantes.ESTADO_VACUNA_NUEVA);
						// vacunas_pacientes.setObservacion_estado("");
						// vacunas_pacientes.setNro_ingreso("" +
						// admision.getNro_ingreso());
						// vacunas_pacientesDao.crear(vacunas_pacientes);
					} else {
						// creamos un registro de que colocaron una vacuna que
						// no aplica para ese paciente en el
						// esquema de vacunacion
						String observacion = (String) map
								.get(VacunasPaiAction.OBSERVACION);

						if (observacion == null || observacion.trim().isEmpty()) {
							throw new ValidacionRunTimeException(
									"La vacuna en la línea #"
											+ i
											+ " necesita una observacion del porque se coloca esta vacuna");
						}

						String codigo = consecutivoService.crearConsecutivo(
								paciente.getCodigo_empresa(),
								paciente.getCodigo_sucursal(),
								IConsecutivos.VACUNAS_FUERA_ESQUEMA);
						Vacunas_fuera_esquema_vacunacion vacunas_fuera_esquema_vacunacion = new Vacunas_fuera_esquema_vacunacion();
						vacunas_fuera_esquema_vacunacion
								.setCodigo_empresa(paciente.getCodigo_empresa());
						vacunas_fuera_esquema_vacunacion
								.setCodigo_sucursal(paciente
										.getCodigo_sucursal());
						vacunas_fuera_esquema_vacunacion.setConsecutivo(codigo);
						vacunas_fuera_esquema_vacunacion
								.setId_procedimiento_vac(vacunas
										.getCodigo_procedimiento());
						vacunas_fuera_esquema_vacunacion
								.setCodigo_usuario_coloco_vacuna(usuarios
										.getCodigo());
						vacunas_fuera_esquema_vacunacion
								.setNro_identificacion_paciente(paciente
										.getNro_identificacion());
						vacunas_fuera_esquema_vacunacion
								.setCreacion_date(new Timestamp(
										new GregorianCalendar()
												.getTimeInMillis()));
						vacunas_fuera_esquema_vacunacion
								.setUltimo_update(new Timestamp(
										new GregorianCalendar()
												.getTimeInMillis()));
						vacunas_fuera_esquema_vacunacion
								.setCreacion_user(usuarios.getCodigo()
										.toString());
						vacunas_fuera_esquema_vacunacion
								.setUltimo_user(usuarios.getCodigo().toString());
						vacunas_fuera_esquema_vacunacion
								.setObservacion(observacion);
						vacunas_fuera_esquema_vacunacionDao
								.crear(vacunas_fuera_esquema_vacunacion);
						consecutivoService.actualizarConsecutivo(
								paciente.getCodigo_empresa(),
								paciente.getCodigo_sucursal(),
								IConsecutivos.VACUNAS_FUERA_ESQUEMA, codigo);
					}
					/* alimentamos el rips de procedimientos */

					Detalle_orden detalle_orden = Utilidades
							.convertirVacunasPacienteADetalleOrden(vacunas,
									ServiciosDisponiblesUtils
											.getServiceLocator());
					//log.info(">>>>>>>" + detalle_orden);
					map_vacunacion.put("dtt_orden", detalle_orden);
					map_vacunacion.put("fecha_vacunacion", fecha);
					dtt_servicios.add(map_vacunacion);

					// generarDatosProcedimientos(vacunas, paciente,
					// manuales_tarifarios, serviceLocator, usuarios,
					// esquema_vacunacion);
					/* afectamos el inventario */

					// generarKardexMedicamentoVacuna(articulo);

				}
				Map<String, Object> parametros_vacunas = new HashMap<String, Object>();
				parametros_vacunas.put("paciente", paciente);
				parametros_vacunas.put("vacunador", vacunador);
				parametros_vacunas.putAll(params);

				parametros_vacunas = Utilidades
						.getAdmisionFantasma(parametros_vacunas);

				// Admision admision = (Admision)
				// parametros_vacunas.get("admision");
				// admision.setParticular("S");
				// admision.setCodigo_administradora(administradora.getCodigo());
				// admision.setContratos(contrato);
				// parametros_vacunas.put("admision",admision);

				parametros_vacunas.put("dtt_servicios", dtt_servicios);
				// facturar las vacunas seleccionadas
				// y creamos la admision

				admisionService.guardar(parametros_vacunas);
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<String> getCodigoProcedimientosVacunas(Map<String, Object> map) {
		return vacunasDao.getCodigoProcedimientosVacunas(map);
	}

	// private void generarKardexMedicamentoVacuna(Articulo articulo, Vacunas
	// vacunas) {
	// Kardex kardex = new Kardex();
	// kardex.setCodigo_empresa(articulo.getCodigo_empresa());
	// kardex.setCodigo_sucursal(articulo
	// .getCodigo_sucursal());
	// kardex.setCodigo_fuente("15");
	// kardex.setDetalle("Salida Vacunacion # 15 -"
	// + vacunas.getCodigo_cups() + " " + vacunas.getDescripcion());
	// kardex.setCodigo_documento("");
	// kardex.setCreacion_date(articulo.getCreacion_date());
	// kardex.setUltimo_update(articulo.getUltimo_update());
	// kardex.setCreacion_user(articulo.getCreacion_user());
	// kardex.setUltimo_user(articulo.getUltimo_user());
	// }

	// private void generarDatosProcedimientos(Vacunas vacunas, Paciente
	// paciente, Manuales_tarifarios manuales_tarifarios, ServiceLocator
	// serviceLocator, Usuarios usuarios, Esquema_vacunacion
	// esquema_vacunacion){
	// try {
	// Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
	// datos_procedimiento.setCodigo_empresa(vacunas.getCodigo_empresa());
	// datos_procedimiento.setCodigo_sucursal(vacunas.getCodigo_sucursal());
	// datos_procedimiento.setNro_factura("");
	//
	// Map<String, Object> procedimiento =
	// Utilidades.getProcedimiento(manuales_tarifarios,
	// vacunas.getCodigo_cups(), serviceLocator);
	//
	// double valor_pcd = (Double) procedimiento.get("valor_pcd");
	// String codigo_cups = (String) procedimiento.get("codigo_cups");
	//
	// datos_procedimiento.setTipo_identificacion(paciente.getTipo_identificacion());
	// datos_procedimiento.setNro_identificacion(paciente.getNro_identificacion());
	// datos_procedimiento.setCodigo_administradora(paciente.getCodigo_administradora());
	// datos_procedimiento.setId_plan(manuales_tarifarios.getId_contrato());
	// datos_procedimiento.setCodigo_prestador(usuarios.getCodigo());
	// datos_procedimiento.setNro_ingreso("");
	// datos_procedimiento.setCodigo_procedimiento(vacunas.getCodigo_cups());
	// datos_procedimiento.setFecha_procedimiento(new
	// Timestamp(Calendar.getInstance().getTimeInMillis()));
	// datos_procedimiento.setNumero_autorizacion("");
	// datos_procedimiento
	// .setValor_procedimiento(valor_pcd);
	// datos_procedimiento.setUnidades(esquema_vacunacion.getDosis());
	// datos_procedimiento.setCopago(0.0);
	// datos_procedimiento.setCodigo_cups(codigo_cups);
	//
	// double decuento = 0;
	// double incremento = 0;
	// double porcentaje = valor_pcd * (manuales_tarifarios.getServicios() /
	// 100);
	// if(manuales_tarifarios.getTipo_servicio().equals("01")){
	// decuento = valor_pcd - porcentaje;
	// }else{
	// incremento = valor_pcd + porcentaje;
	// }
	//
	// datos_procedimiento
	// .setValor_neto(valor_pcd * esquema_vacunacion.getDosis());
	//
	//
	// datos_procedimiento.setAmbito_procedimiento(IDatosProcedimientos.AMBITO_REALIZACION);
	// datos_procedimiento
	// .setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO_VACUNACION);
	// datos_procedimiento.setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
	// datos_procedimiento.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
	// datos_procedimiento
	// .setCodigo_diagnostico_principal(vacunas.getCodigo_cie());
	// datos_procedimiento
	// .setCodigo_diagnostico_relacionado("");
	// datos_procedimiento.setComplicacion("");
	// datos_procedimiento.setCancelo_copago("N");
	// datos_procedimiento.setCodigo_programa("");
	//
	// datos_procedimiento.setCreacion_date(new Timestamp(
	// new GregorianCalendar().getTimeInMillis()));
	// datos_procedimiento.setUltimo_update(new Timestamp(
	// new GregorianCalendar().getTimeInMillis()));
	// datos_procedimiento.setCreacion_user(usuarios.getCodigo());
	// datos_procedimiento.setUltimo_user(usuarios.getCodigo());
	// datos_procedimiento
	// .setValor_real(datos_procedimiento.getValor_procedimiento());
	// datos_procedimiento
	// .setDescuento(decuento * datos_procedimiento.getUnidades());
	// datos_procedimiento
	// .setIncremento(incremento * datos_procedimiento.getUnidades());
	// datos_procedimiento.setRealizado_en(IDatosProcedimientos.REALIZADO_EN_ODONOTOLOGIA);
	//
	// datos_procedimientoService.crear(datos_procedimiento);
	// } catch (Exception e) {
	// throw new ValidacionRunTimeException(e.getMessage(), e);
	// }
	// }

}