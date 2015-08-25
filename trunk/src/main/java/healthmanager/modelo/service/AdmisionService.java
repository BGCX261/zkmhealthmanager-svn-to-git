/*
 * AdmisionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Admision_cama;
import healthmanager.modelo.bean.Anio_soat;
import healthmanager.modelo.bean.Cama;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Contratos_paquetes;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Estancias_iss;
import healthmanager.modelo.bean.Estancias_soat;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Furips2;
import healthmanager.modelo.bean.Habitacion;
import healthmanager.modelo.bean.Hospitalizacion;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Pabellon;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Recien_nacido;
import healthmanager.modelo.bean.Registro_admision;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Solicitud_medicamento;
import healthmanager.modelo.bean.Urgencias;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.bean.Vacunas_pacientes;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Admision_camaDao;
import healthmanager.modelo.dao.CamaDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.ContratosDao;
import healthmanager.modelo.dao.Contratos_paquetesDao;
import healthmanager.modelo.dao.Datos_consultaDao;
import healthmanager.modelo.dao.Datos_medicamentosDao;
import healthmanager.modelo.dao.Detalle_grupos_procedimientosDao;
import healthmanager.modelo.dao.Detalle_ordenDao;
import healthmanager.modelo.dao.Detalles_paquetes_serviciosDao;
import healthmanager.modelo.dao.Estancias_issDao;
import healthmanager.modelo.dao.Estancias_soatDao;
import healthmanager.modelo.dao.Facturacion_medicamentoDao;
import healthmanager.modelo.dao.Facturacion_servicioDao;
import healthmanager.modelo.dao.Grupos_procedimientosDao;
import healthmanager.modelo.dao.HabitacionDao;
import healthmanager.modelo.dao.HospitalizacionDao;
import healthmanager.modelo.dao.Manuales_tarifariosDao;
import healthmanager.modelo.dao.Orden_servicioDao;
import healthmanager.modelo.dao.PabellonDao;
import healthmanager.modelo.dao.Pacientes_contratosDao;
import healthmanager.modelo.dao.Receta_ripsDao;
import healthmanager.modelo.dao.Recien_nacidoDao;
import healthmanager.modelo.dao.Registro_admisionDao;
import healthmanager.modelo.dao.Solicitud_medicamentoDao;
import healthmanager.modelo.dao.UrgenciasDao;
import healthmanager.modelo.dao.VacunasDao;
import healthmanager.modelo.dao.Vacunas_pacientesDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.IVias_ingreso;
import com.framework.res.L2HContraintDate;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Tarifa_medicamento;
import contaweb.modelo.dao.Bodega_articuloDao;
import contaweb.modelo.dao.Tarifa_medicamentoDao;
import contaweb.modelo.service.FacturacionService;

@Service("admisionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdmisionService implements Serializable {

	private static Logger log = Logger.getLogger(AdmisionService.class);

	@Autowired
	private Configuracion_admision_ingresoService configuracion_admision_ingresoService;

	@Autowired
	private Bodega_articuloDao bodega_articuloDao;
	@Autowired
	private Tarifa_medicamentoDao tarifa_medicamentoDao;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private Registro_admisionDao registro_admisionDao;
	@Autowired
	private CitasDao citasDao;
	@Autowired
	private ContratosDao contratosDao;
	@Autowired
	private PabellonDao pabellonDao;
	@Autowired
	private HabitacionDao habitacionDao;
	@Autowired
	private CamaDao camaDao;
	@Autowired
	private Estancias_issDao estancias_issDao;
	@Autowired
	private Estancias_soatDao estancias_soatDao;
	@Autowired
	private Admision_camaDao admision_camaDao;
	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private AdministradoraService administradoraService;

	@Autowired
	private Datos_consultaDao datos_consultaDao;
	@Autowired
	private Datos_procedimientoService datos_procedimientoService;
	@Autowired
	private Facturacion_medicamentoDao facturacion_medicamentoDao;
	@Autowired
	private Facturacion_servicioDao facturacion_servicioDao;
	@Autowired
	private Datos_medicamentosDao datos_medicamentosDao;
	@Autowired
	private UrgenciasDao urgenciasDao;
	@Autowired
	private HospitalizacionDao hospitalizacionDao;
	@Autowired
	private Recien_nacidoDao recien_nacidoDao;

	@Autowired
	private Solicitud_medicamentoDao solicitud_medicamentoDao;
	@Autowired
	private Receta_ripsDao receta_ripsDao;
	@Autowired
	private Orden_servicioDao orden_servicioDao;

	@Autowired
	private FacturacionService facturacionService;

	@Autowired
	private Detalle_ordenDao detalle_ordenDao;

	@Autowired
	private Pacientes_contratosDao pacientes_contratosDao;

	@Autowired
	private Contratos_paquetesDao contratos_paquetesDao;

	@Autowired
	private Detalles_paquetes_serviciosDao detalles_paquetes_serviciosDao;

	@Autowired
	private Furips2Service furips2Service;
	@Autowired
	private PrestadoresService prestadoresService;

	@Autowired
	private Grupos_procedimientosDao grupos_procedimientosDao;
	@Autowired
	private Detalle_grupos_procedimientosDao detalle_grupos_procedimientosDao;

	@Autowired
	private ProcedimientosService procedimientoService;

	@Autowired
	private VacunasDao vacunasDao;
	@Autowired
	private Vacunas_pacientesDao vacunas_pacientesDao;
	@Autowired
	private Servicios_contratadosService servicios_contratadosService;
	@Autowired
	private Servicios_via_ingresoService servicios_via_ingresoService;
	@Autowired
	private Manuales_tarifariosDao manuales_tarifariosDao;
	@Autowired
	private GeneralExtraService generalExtraService;

	public void crear(Admision admision) {
		try {
			if (consultar(admision) != null) {
				throw new HealthmanagerException(
						"Admision ya se encuentra en la base de datos");
			}
			admisionDao.crear(admision);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Map<String, Object> map) {
		try {
			Admision admision = (Admision) map.get("admision");
			Parametros_empresa parametros_empresa = (Parametros_empresa) map
					.get("parametros_empresa");
			if (parametros_empresa != null
					&& parametros_empresa
							.getHabilitar_restriccion_autorizacion()
					&& !admision.getNro_autorizacion().trim().isEmpty()) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", admision.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", admision.getCodigo_sucursal());
				parametros.put("nro_autorizacion",
						admision.getNro_autorizacion());
				parametros.put("codigo_administradora",
						admision.getCodigo_administradora());
				parametros.put("no_estado", "3");

				Map<String, Object> ultima_autorizacion = getUltimaAutorizacion(parametros);

				if (ultima_autorizacion != null) {
					String nro_ingreso = (String) ultima_autorizacion
							.get("nro_ingreso");
					String nro_identificacion = (String) ultima_autorizacion
							.get("nro_identificacion");
					if (!(nro_ingreso.equals(admision.getNro_ingreso()) && nro_identificacion
							.equals(admision.getNro_identificacion()))) {
						throw new ValidacionRunTimeException(
								"No se pueden guardar estos servicios con esta autorizacion "
										+ admision.getNro_autorizacion()
										+ " porque esta siendo usada en otra admision. Nro-ingreso: "
										+ nro_ingreso + " Identificacion: "
										+ nro_identificacion);
					}
				}
			}
			return admisionDao.actualizar(admision);
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Admision consultar(Admision admision) {
		try {
			return admisionDao.consultar(admision);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Admision admision) {
		try {
			Admision aux2 = admision;
			// Actualizamos los contratos de las consultas de ese paciente //
			Map<String, Object> mapDatos = new HashMap<String, Object>();
			mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
			mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
			mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
			mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
			List<Datos_consulta> lista_datos_cons = datos_consultaDao
					.listarTabla(mapDatos);
			if (!lista_datos_cons.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le han cargados consultas");
			}

			// Actualizamos los contratos de los procedimientos de ese paciente
			// //
			mapDatos = new HashMap<String, Object>();
			mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
			mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
			mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
			mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
			List<Datos_procedimiento> lista_datos_pro = datos_procedimientoService
					.listarTabla(mapDatos);
			if (!lista_datos_pro.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le han cargados procedimientos");
			}
			// Actualizamos los contratos de las entregas de medicamentos //
			mapDatos = new HashMap<String, Object>();
			mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
			mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
			mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
			mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
			// mapDatos.put("tipo", "");
			List<Facturacion_medicamento> lista_datos_med = facturacion_medicamentoDao
					.listar(mapDatos);
			if (!lista_datos_med.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le han cargados medicamentos o insumos");
			}

			// Actualizamos los contratos de solicitud de medicamento //
			mapDatos = new HashMap<String, Object>();
			mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
			mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
			mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
			mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
			List<Solicitud_medicamento> lista_sol = solicitud_medicamentoDao
					.listar(mapDatos);
			if (!lista_sol.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le ha hecho solicitud de medicamento");
			}

			// Actualizamos los contratos de receta rips //
			mapDatos = new HashMap<String, Object>();
			mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
			mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
			mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
			mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
			List<Receta_rips> lista_receta = receta_ripsDao.listar(mapDatos);
			if (!lista_receta.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le ha hecho preescripcion médica");
			}

			// Actualizamos los contratos de los servicios de ese paciente //
			mapDatos = new HashMap<String, Object>();
			mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
			mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
			mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
			mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
			List<Facturacion_servicio> lista_datos_serv = facturacion_servicioDao
					.listar(mapDatos);
			if (!lista_datos_serv.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le han cargados servicios");
			}

			// Actualizamos los contratos de las urgencias //
			Urgencias aux_urgencias = new Urgencias();
			aux_urgencias.setCodigo_empresa(aux2.getCodigo_empresa());
			aux_urgencias.setCodigo_sucursal(aux2.getCodigo_sucursal());
			aux_urgencias.setNro_identificacion(aux2.getNro_identificacion());
			aux_urgencias.setNro_ingreso(aux2.getNro_ingreso());
			aux_urgencias = urgenciasDao.consultar(aux_urgencias);
			if (aux_urgencias != null) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le ha hecho rips de urgencias");
			}
			// Actualizamos los contratos de la hospitalizacion //
			Hospitalizacion aux_hospitalizacion = new Hospitalizacion();
			aux_hospitalizacion.setCodigo_empresa(aux2.getCodigo_empresa());
			aux_hospitalizacion.setCodigo_sucursal(aux2.getCodigo_sucursal());
			aux_hospitalizacion.setNro_identificacion(aux2
					.getNro_identificacion());
			aux_hospitalizacion.setNro_ingreso(aux2.getNro_ingreso());
			aux_hospitalizacion = (Hospitalizacion) hospitalizacionDao
					.consultar(aux_hospitalizacion);
			if (aux_hospitalizacion != null) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le ha hecho rips de hospitalizacion");
			}
			// Actualizamos los contratos de los recien nacidos //
			Recien_nacido aux_recien_nacido = new Recien_nacido();
			aux_recien_nacido.setCodigo_empresa(aux2.getCodigo_empresa());
			aux_recien_nacido.setCodigo_sucursal(aux2.getCodigo_sucursal());
			aux_recien_nacido.setNro_identificacion(aux2
					.getNro_identificacion());
			aux_recien_nacido.setNro_ingreso(aux2.getNro_ingreso());
			aux_recien_nacido = recien_nacidoDao.consultar(aux_recien_nacido);
			if (aux_recien_nacido != null) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le ha hecho rips de recion nacido");
			}

			// Actualizamos los contratos de receta rips //
			mapDatos = new HashMap<String, Object>();
			mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
			mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
			mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
			mapDatos.put("codigo_paciente", aux2.getNro_identificacion());
			List<Orden_servicio> lista_orden = orden_servicioDao
					.listar(mapDatos);
			if (!lista_orden.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se pudo eliminar la admision porque ya se le ha hecho orden de servicio");
			}

			if (admision.getCodigo_cita() != null) {
				Citas citas = new Citas();
				citas.setCodigo_empresa(admision.getCodigo_empresa());
				citas.setCodigo_sucursal(admision.getCodigo_sucursal());
				citas.setCodigo_cita(admision.getCodigo_cita());
				citas = citasDao.consultar(citas);
				if (citas != null) {
					citas.setEstado("1");
					citasDao.actualizar(citas);
				}
			}
			return admisionDao.eliminar(admision);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarFacturacionFaltante(Map<String, Object> mapa_datos) {
		try {
			List<Admision> listado_admisiones = (List<Admision>) mapa_datos
					.get("listado_admisiones");
			List<Map<String, Object>> listado_cups = (List<Map<String, Object>>) mapa_datos
					.get("listado_cups");
			for (Admision admision : listado_admisiones) {
				Paciente paciente = admision.getPaciente();
				Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
						.getManuales_tarifarios(admision);
				if (manuales_tarifarios != null) {
					for (Map<String, Object> map : listado_cups) {
						guardarDatos_procedimiento(manuales_tarifarios, map,
								admision, paciente);
					}
					Facturacion facturacion = new Facturacion();
					facturacion.setCodigo_empresa(admision.getCodigo_empresa());
					facturacion.setCodigo_sucursal(admision
							.getCodigo_sucursal());
					facturacion.setCodigo_tercero(admision
							.getNro_identificacion());
					facturacion.setNro_ingreso(admision.getNro_ingreso());
					facturacion = facturacionService.consultar(facturacion);
					if (facturacion != null) {
						facturacion.setAdmision(admision);
						facturacionService.guardarRecalculoFacturas(
								facturacion, false);
					}

				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Admision> listarTabla(Map<String, Object> parameter) {
		try {
			return admisionDao.listarTabla(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Admision> listarResultados(Map<String, Object> parameter) {
		try {
			return admisionDao.listarResultados(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer totalResultados(Map<String, Object> parameters) {
		try {
			return admisionDao.totalResultados(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public Admision guardar(Map<String, Object> map) {
		try {
			Parametros_empresa parametros_empresa = (Parametros_empresa) map
					.get("parametros_empresa");
			Admision admision = (Admision) map.get("admision");
			Administradora administradora = (Administradora) map
					.get("administradora");
			List<Contratos> listado_contratos = (List<Contratos>) map
					.get("listado_contratos");
			if (admision.getCodigo_medico().toString().isEmpty()) {
				admision.setCodigo_medico(IConstantes.CODIGO_MEDICO_DEFECTO);
			}
			String accion = (String) map.get("accion");
			List<Map<String, Object>> detalle_ordens = (List<Map<String, Object>>) map
					.get("dtt_servicios");

			List<Map> dtt_medicamentos = (List<Map>) map
					.get("dtt_medicamentos");

			Furips2 furips2 = (Furips2) map.get(IConstantes.LLAVE_FURIPS);

			Map<String, Object> mapa_vias_ingreso = ServiciosDisponiblesUtils
					.obtenerMapaViasIngresoNuevo(admision.getPaciente(),
							admision.getCodigo_administradora(),
							listado_contratos);

			Via_ingreso_contratadas via_ingreso_contratadas = null;
			Boolean laboratorio_pyp = false;

			if (!mapa_vias_ingreso.containsKey(admision.getVia_ingreso())) {
				throw new ValidacionRunTimeException(
						"No se encontraron manuales tarifarios para este contrato");
			} else {
				Map<String, Object> datos_verificacion = (Map<String, Object>) mapa_vias_ingreso
						.get(admision.getVia_ingreso());
				via_ingreso_contratadas = (Via_ingreso_contratadas) datos_verificacion
						.get("via_ingreso_contratadas");
				laboratorio_pyp = datos_verificacion
						.containsKey("laboratorio_pyp");
			}

			if (via_ingreso_contratadas == null) {
				throw new ValidacionRunTimeException(
						"No se encontraron manuales tarifarios para este contrato");
			}

			admision.setCodigo_orden("");

			if (accion.equalsIgnoreCase("registrar")) {
				Registro_admision ra = new Registro_admision();
				Registro_admisionDao raDao = registro_admisionDao;
				ra.setCodigo_empresa(admision.getCodigo_empresa());
				ra.setCodigo_sucursal(admision.getCodigo_sucursal());
				ra.setCodigo_paciente(admision.getNro_identificacion());
				if (raDao.consultar(ra) != null) {
					ra = (Registro_admision) raDao.consultar(ra);
				} else {
					ra.setNro_ingreso("1");
					ra.setCreacion_date(new Timestamp(new GregorianCalendar()
							.getTimeInMillis()));
					ra.setUltimo_update(new Timestamp(new GregorianCalendar()
							.getTimeInMillis()));
					ra.setCreacion_user(admision.getCreacion_user());
					ra.setUltimo_user(admision.getUltimo_user());
					raDao.crear(ra);
					ra = (Registro_admision) raDao.consultar(ra);
				}
				int nro_ing = Integer.parseInt(ra.getNro_ingreso());

				DecimalFormat decimalFormat = new DecimalFormat("0000");
				admision.setNro_ingreso(decimalFormat.format(nro_ing));
				admisionDao.crear(admision);
				ra.setNro_ingreso((nro_ing + 1) + "");
				raDao.actualizar(ra);

				if (admision.getCodigo_cita() != null
						&& !admision.getCodigo_cita().isEmpty()) {
					Citas citas = new Citas();
					citas.setCodigo_empresa(admision.getCodigo_empresa());
					citas.setCodigo_sucursal(admision.getCodigo_sucursal());
					citas.setCodigo_cita(admision.getCodigo_cita());
					citas = (Citas) citasDao.consultar(citas);
					if (citas != null) {
						citas.setEstado("2");
						citasDao.actualizar(citas);
					}
				}

			} else {
				Admision admisionAux = new Admision();
				admisionAux.setCodigo_empresa(admision.getCodigo_empresa());
				admisionAux.setCodigo_sucursal(admision.getCodigo_sucursal());
				admisionAux.setNro_ingreso(admision.getNro_ingreso());
				admisionAux.setNro_identificacion(admision
						.getNro_identificacion());
				admisionAux = admisionDao.consultar(admisionAux);

				admisionDao.actualizar(admision);
				eliminarEstancia(map);

				eliminarEgresos(admision);
				actualizarRips(admision);
			}

			if (parametros_empresa != null
					&& parametros_empresa
							.getHabilitar_restriccion_autorizacion()
					&& !admision.getNro_autorizacion().trim().isEmpty()) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", admision.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", admision.getCodigo_sucursal());
				parametros.put("nro_autorizacion",
						admision.getNro_autorizacion());
				parametros.put("codigo_administradora",
						admision.getCodigo_administradora());
				parametros.put("no_estado", "3");

				Map<String, Object> ultima_autorizacion = getUltimaAutorizacion(parametros);

				if (ultima_autorizacion != null) {
					String nro_ingreso = (String) ultima_autorizacion
							.get("nro_ingreso");
					String nro_identificacion = (String) ultima_autorizacion
							.get("nro_identificacion");
					if (!(nro_ingreso.equals(admision.getNro_ingreso()) && nro_identificacion
							.equals(admision.getNro_identificacion()))) {
						throw new ValidacionRunTimeException(
								"No se pueden guardar estos servicios con esta autorizacion "
										+ admision.getNro_autorizacion()
										+ " porque esta siendo usada en otra admision. Nro-ingreso: "
										+ nro_ingreso + " Identificacion: "
										+ nro_identificacion);
					}
				}
			}

			map.put("via_ingreso_contratadas", via_ingreso_contratadas);
			// Generamos la estancia //
			guardarEstancia(map);
			// Generamos el tercero de paciente en contaweb //
			guardarTerceroPaciente(admision);
			// Generamos el tercero de administradora en contaweb //
			guardarTerceroAdministradora(admision, administradora);

			// Creamos el registro del furips
			if (furips2 != null) {
				furips2.setNro_ingreso(admision.getNro_ingreso());
				if (furips2Service.consultar(furips2) == null) {
					furips2 = furips2Service.crear(furips2);
				}
				actualizarDatosFurips(furips2, admision);
			}

			if ((detalle_ordens != null && !detalle_ordens.isEmpty())
					|| (dtt_medicamentos != null && !dtt_medicamentos.isEmpty())) {
				Manuales_tarifarios manuales_tarifarios = null;
				if (laboratorio_pyp) {
					manuales_tarifarios = ServiciosDisponiblesUtils
							.getManuales_tarifarios(admision);
				} else {
					manuales_tarifarios = ServiciosDisponiblesUtils
							.getManuales_tarifarios(via_ingreso_contratadas);
				}

				// facturamos la orden cuando es laboratorio
				if (detalle_ordens != null && !detalle_ordens.isEmpty()) {
					guardarFacturacionServiciosOrdenados(
							detalle_ordens,
							admision,
							manuales_tarifarios,
							(admision.getVia_ingreso().equals(
									IVias_ingreso.LABORATORIOS)
									|| admision.getVia_ingreso().equals(
											IVias_ingreso.LABORATORIOS_PYP)
									|| admision.getVia_ingreso().equals(
											IVias_ingreso.VIA_VACUNACION)
									|| admision
											.getVia_ingreso()
											.equals(IVias_ingreso.SERVICIOS_AMBULATORIOS)
									|| ServiciosDisponiblesUtils
											.isViaIngresoImagengeneologia(admision
													.getVia_ingreso()) || admision
									.getParticular().equalsIgnoreCase("S")));
				}

				if (dtt_medicamentos != null && !dtt_medicamentos.isEmpty()) {
					guardarFacturacionServiciosMedicamentos(
							dtt_medicamentos,
							admision,
							manuales_tarifarios,
							admision.getVia_ingreso().equals(
									IVias_ingreso.MEDICAMENTOS_PYP)
									|| admision.getParticular()
											.equalsIgnoreCase("S"));
				}
			}

			return admision;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}

	}

	public Admision guardarAdmisionClon(Map<String, Object> datos) {
		Admision admision = (Admision) datos.get("admision");
		String accion = (String) datos.get("accion");
		if (accion.equalsIgnoreCase("registrar")) {
			Registro_admision ra = new Registro_admision();
			Registro_admisionDao raDao = registro_admisionDao;
			ra.setCodigo_empresa(admision.getCodigo_empresa());
			ra.setCodigo_sucursal(admision.getCodigo_sucursal());
			ra.setCodigo_paciente(admision.getNro_identificacion());
			if (raDao.consultar(ra) != null) {
				ra = (Registro_admision) raDao.consultar(ra);
			} else {
				ra.setNro_ingreso("1");
				ra.setCreacion_date(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				ra.setUltimo_update(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				ra.setCreacion_user(admision.getCreacion_user());
				ra.setUltimo_user(admision.getUltimo_user());
				raDao.crear(ra);
				ra = (Registro_admision) raDao.consultar(ra);
			}
			int nro_ing = Integer.parseInt(ra.getNro_ingreso());

			DecimalFormat decimalFormat = new DecimalFormat("0000");
			admision.setNro_ingreso(decimalFormat.format(nro_ing));
			admisionDao.crear(admision);
			ra.setNro_ingreso((nro_ing + 1) + "");
			raDao.actualizar(ra);
		} else {
			Admision admisionAux = new Admision();
			admisionAux.setCodigo_empresa(admision.getCodigo_empresa());
			admisionAux.setCodigo_sucursal(admision.getCodigo_sucursal());
			admisionAux.setNro_ingreso(admision.getNro_ingreso());
			admisionAux.setNro_identificacion(admision.getNro_identificacion());
			admisionAux = admisionDao.consultar(admisionAux);
			admisionDao.actualizar(admision);
		}
		return admision;
	}

	/**
	 * Este metodo me va ha permitir actualizar los datos del medico en el
	 * furips
	 *
	 * @author Luis Miguel
	 *
	 */
	private void actualizarDatosFurips(Furips2 furips2, Admision admision) {
		Prestadores prestadores = new Prestadores();
		prestadores.setCodigo_empresa(admision.getCodigo_empresa());
		prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
		prestadores.setNro_identificacion(admision.getCodigo_medico());
		prestadores = prestadoresService.consultar(prestadores);
		if (prestadores != null) {
			furips2.setIx_nro_documento_medico(prestadores
					.getNro_identificacion());
			int actualizado = furips2Service.actualizar(furips2);
			if (actualizado == 0) {
				furips2Service.crear(furips2);
			}
		} else {
			throw new ValidacionRunTimeException(
					"El medico con el nro de identificacion "
							+ admision.getCodigo_medico() + " no existe");
		}
	}

	private void guardarTerceroPaciente(Admision admision) throws Exception {
		Paciente paciente = admision.getPaciente();
		if (paciente == null) {
			paciente = new Paciente();
			paciente.setCodigo_empresa(admision.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);
		}
		if (paciente != null) {
			pacienteService.guardarTercero(paciente);
		}
	}

	private void guardarTerceroAdministradora(Admision admision,
			Administradora administradora) throws Exception {
		if (administradora == null) {
			administradora = new Administradora();
			administradora.setCodigo_empresa(admision.getCodigo_empresa());
			administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
			administradora.setCodigo(admision.getCodigo_administradora());
			administradora = administradoraService.consultar(administradora);
		}
		if (administradora != null) {
			administradoraService.guardarTerceroAdministradora(administradora);
		}
	}

	private void guardarEstancia(Map<String, Object> map) throws Exception {
		Admision admision = (Admision) map.get("admision");
		if (!admision.getCama().trim().equals("")) {
			Via_ingreso_contratadas via_ingreso_contratadas = (Via_ingreso_contratadas) map
					.get("via_ingreso_contratadas");
			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(via_ingreso_contratadas);
			String codigo_empresa = admision.getCodigo_empresa();
			String codigo_sucursal = admision.getCodigo_sucursal();
			String codigo_atencion = (String) map.get("codigo_atencion");
			String codigo_pabellon = (String) map.get("codigo_pabellon");
			String codigo_habitacion = (String) map.get("codigo_habitacion");
			String codigo_centro = admision.getCodigo_centro();
			Empresa empresa = (Empresa) map.get("empresa");
			// insertamos las camas si hay //
			// log.info("cama: " + admision.getCama().trim());

			if (manuales_tarifarios == null) {
				throw new HealthmanagerException(
						"Esta plan no existe en la tabla de contratos");
			}
			Pabellon pabellon = new Pabellon();
			pabellon.setCodigo_empresa(codigo_empresa);
			pabellon.setCodigo_sucursal(codigo_sucursal);
			pabellon.setTipo_atencion(codigo_atencion);
			pabellon.setCodigo(Integer.parseInt(codigo_pabellon));
			pabellon.setCodigo_centro(codigo_centro);
			pabellon = pabellonDao.consultar(pabellon);

			Habitacion habitacion = new Habitacion();
			habitacion.setCodigo_empresa(codigo_empresa);
			habitacion.setCodigo_sucursal(codigo_sucursal);
			habitacion.setTipo_atencion(codigo_atencion);
			habitacion.setCodigo_pabellon(Integer.parseInt(codigo_pabellon));
			habitacion.setCodigo(Integer.parseInt(codigo_habitacion));
			habitacion.setCodigo_centro(codigo_centro);
			habitacion = habitacionDao.consultar(habitacion);

			Cama cama = new Cama();
			cama.setCodigo_empresa(codigo_empresa);
			cama.setCodigo_sucursal(codigo_sucursal);
			cama.setTipo_atencion(codigo_atencion);
			cama.setCodigo_pabellon(Integer.parseInt(codigo_pabellon));
			cama.setCodigo_habitacion(Integer.parseInt(codigo_habitacion));
			cama.setCodigo(Integer.parseInt(admision.getCama()));
			cama.setCodigo_centro(codigo_centro);
			cama = (Cama) camaDao.consultar(cama);

			String codigo_procedimiento = "";
			double valor_dia = 0.0;
			Maestro_manual maestro_manual = manuales_tarifarios
					.getMaestro_manual();
			if (maestro_manual.getTipo_manual().equals(
					IConstantes.TIPO_MANUAL_SOAT)) {
				Map<String, Object> paramCama = new HashMap<String, Object>();
				paramCama.put("codigo_empresa", empresa.getCodigo_empresa());
				paramCama.put("codigo_sucursal",
						habitacion.getCodigo_sucursal());
				paramCama.put("tipo_atencion", habitacion.getTipo_atencion());
				paramCama.put("codigo_pabellon",
						habitacion.getCodigo_pabellon());
				paramCama.put("codigo_habitacion", habitacion.getCodigo());
				paramCama.put("codigo_centro", habitacion.getCodigo_centro());
				List<Cama> lista_aux = camaDao.listar(paramCama);

				Anio_soat anio_soat = new Anio_soat();
				anio_soat.setAnio(manuales_tarifarios.getAnio());
				anio_soat = generalExtraService.consultar(anio_soat);
				Estancias_soat estancias = new Estancias_soat();
				estancias.setEstancia((pabellon != null ? pabellon
						.getEstancias() : ""));
				estancias.setNivel(empresa.getNivel().equals("") ? -1 : Integer
						.parseInt(empresa.getNivel()));
				estancias.setNro_camas((habitacion != null ? lista_aux.size()
						: -1));
				// log.info("estancia: " + estancias);
				estancias = estancias_soatDao.consultarEstancia(estancias);
				codigo_procedimiento = (estancias != null ? estancias
						.getCodigo() : "");

				if (anio_soat == null) {
					throw new HealthmanagerException(
							"Esta plan no existe año soat");
				}
				valor_dia = (int) (estancias != null ? (anio_soat
						.getValor_anio() * estancias.getPorcentaje()) : 0.0);
			} else if (maestro_manual.getTipo_manual().equals(
					IConstantes.TIPO_MANUAL_ISS01)
					|| maestro_manual.getTipo_manual().equals(
							IConstantes.TIPO_MANUAL_ISS04)) {

				Map<String, Object> paramCama = new HashMap<String, Object>();
				paramCama.put("codigo_empresa", empresa.getCodigo_empresa());
				paramCama.put("codigo_sucursal",
						habitacion.getCodigo_sucursal());
				paramCama.put("tipo_atencion", habitacion.getTipo_atencion());
				paramCama.put("codigo_pabellon",
						habitacion.getCodigo_pabellon());
				paramCama.put("codigo_habitacion", habitacion.getCodigo());
				paramCama.put("codigo_centro", habitacion.getCodigo_centro());
				List<Cama> lista_aux = camaDao.listar(paramCama);

				Estancias_iss estancias = new Estancias_iss();
				estancias.setEstancia((pabellon != null ? pabellon
						.getEstancias() : ""));
				estancias.setNivel(empresa.getNivel().equals("") ? -1 : Integer
						.parseInt(empresa.getNivel()));
				estancias.setNro_camas((habitacion != null ? lista_aux.size()
						: -1));
				// log.info("estancia: " + estancias);
				estancias = estancias_issDao.consultarEstancia(estancias);
				codigo_procedimiento = (estancias != null ? estancias
						.getCodigo() : "");
				valor_dia = (int) (estancias != null ? estancias.getValor()
						: 0.0);
			}

			if (!codigo_procedimiento.equals("")) {
				Admision_cama admision_cama = new Admision_cama();
				admision_cama.setCodigo_empresa(codigo_empresa);
				admision_cama.setCodigo_sucursal(codigo_sucursal);
				admision_cama.setNro_ingreso(admision.getNro_ingreso());
				admision_cama.setNro_identificacion(admision
						.getNro_identificacion());
				admision_cama.setTipo_atencion(codigo_atencion);
				admision_cama.setCodigo_pabellon(Integer
						.parseInt(codigo_pabellon));
				admision_cama.setCodigo_habitacion(Integer
						.parseInt(codigo_habitacion));
				admision_cama.setCodigo_cama(Integer.parseInt(admision
						.getCama()));
				admision_cama.setFecha_ingreso(admision.getFecha_ingreso());
				admision_cama.setCodigo_procedimiento(codigo_procedimiento);
				admision_cama.setValor_dia(valor_dia);
				admision_cama.setCreacion_date(admision.getCreacion_date());
				admision_cama.setUltimo_update(admision.getUltimo_update());
				admision_cama.setCreacion_user(admision.getCreacion_user());
				admision_cama.setUltimo_user(admision.getUltimo_user());
				admision_cama.setCancelo_copago("N");
				admision_cama.setCodigo_centro(codigo_centro);
				admision_camaDao.crear(admision_cama);
				if (cama != null) {
					cama.setFecha_ocupacion(admision.getFecha_ingreso());
					cama.setCodigo_paciente(admision.getNro_identificacion());
					cama.setEstado("02");
					cama.setUltimo_update(admision.getUltimo_update());
					cama.setUltimo_user(admision.getUltimo_user());
					camaDao.actualizar(cama);
				}
			}

		}
	}

	private void eliminarEstancia(Map map) throws Exception {
		Admision admision = (Admision) map.get("admision");
		String codigo_empresa = admision.getCodigo_empresa();
		String codigo_sucursal = admision.getCodigo_sucursal();

		Admision_cama admision_cama = new Admision_cama();
		admision_cama.setCodigo_empresa(codigo_empresa);
		admision_cama.setCodigo_sucursal(codigo_sucursal);
		admision_cama.setNro_ingreso(admision.getNro_ingreso());
		admision_cama.setNro_identificacion(admision.getNro_identificacion());
		admision_cama.setCodigo_centro(admision.getCodigo_centro());
		admision_cama = (Admision_cama) admision_camaDao
				.consultar(admision_cama);
		if (admision_cama != null) {
			admision_camaDao.eliminar(admision_cama);
			Cama cama = new Cama();
			cama.setCodigo_empresa(codigo_empresa);
			cama.setCodigo_sucursal(codigo_sucursal);
			cama.setTipo_atencion(admision_cama.getTipo_atencion());
			cama.setCodigo_pabellon(admision_cama.getCodigo_pabellon());
			cama.setCodigo_habitacion(admision_cama.getCodigo_habitacion());
			cama.setCodigo(admision_cama.getCodigo_cama());
			cama.setCodigo_centro(admision.getCodigo_centro());
			cama = camaDao.consultar(cama);
			if (cama != null) {
				cama.setEstado("01");
				cama.setFecha_ocupacion(null);
				cama.setCodigo_paciente("");
				cama.setUltimo_update(admision.getUltimo_update());
				cama.setUltimo_user(admision.getUltimo_user());
				camaDao.actualizar(cama);
			}
		}
	}

	private void actualizarRips(Admision aux2) throws Exception {

		String codigo_administradora = aux2.getCodigo_administradora();
		String id_plan = aux2.getId_plan();

		// Actualizamos los contratos de las consultas de ese paciente //
		Map<String, Object> mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		List<Datos_consulta> lista_datos_cons = datos_consultaDao
				.listarTabla(mapDatos);
		for (Datos_consulta datos_consulta : lista_datos_cons) {
			datos_consulta.setCodigo_administradora(codigo_administradora);
			datos_consulta.setId_plan(id_plan);
			datos_consultaDao.actualizar(datos_consulta);
		}

		// Actualizamos los contratos de los procedimientos de ese paciente //
		mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		List<Datos_procedimiento> lista_datos_pro = datos_procedimientoService
				.listarTabla(mapDatos);
		for (Datos_procedimiento aux : lista_datos_pro) {
			aux.setCodigo_administradora(codigo_administradora);
			aux.setId_plan(id_plan);
			datos_procedimientoService.actualizarRegistro(aux);
		}
		// Actualizamos los contratos de las entregas de medicamentos //
		mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		// mapDatos.put("tipo", "");
		List<Facturacion_medicamento> lista_datos_med = facturacion_medicamentoDao
				.listar(mapDatos);
		for (Facturacion_medicamento aux : lista_datos_med) {
			aux.setCodigo_administradora(codigo_administradora);
			aux.setId_plan(id_plan);
			facturacion_medicamentoDao.actualizar(aux);
		}

		// Actualizamos los contratos de solicitud de medicamento //
		mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		List<Solicitud_medicamento> lista_sol = solicitud_medicamentoDao
				.listar(mapDatos);
		for (Solicitud_medicamento aux : lista_sol) {
			aux.setCodigo_administradora(codigo_administradora);
			aux.setId_plan(id_plan);
			solicitud_medicamentoDao.actualizar(aux);
		}

		// Actualizamos los contratos de receta rips //
		mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		List<Receta_rips> lista_receta = receta_ripsDao.listar(mapDatos);
		for (Receta_rips aux : lista_receta) {
			aux.setCodigo_administradora(codigo_administradora);
			aux.setId_plan(id_plan);
			receta_ripsDao.actualizar(aux);
		}

		// Actualizamos los contratos de los servicios de ese paciente //
		mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		List<Facturacion_servicio> lista_datos_serv = facturacion_servicioDao
				.listar(mapDatos);
		for (Facturacion_servicio aux : lista_datos_serv) {
			aux.setCodigo_administradora(codigo_administradora);
			aux.setId_plan(id_plan);
			facturacion_servicioDao.actualizar(aux);
		}

		// Actualizamos los contratos de las urgencias //
		Urgencias aux_urgencias = new Urgencias();
		aux_urgencias.setCodigo_empresa(aux2.getCodigo_empresa());
		aux_urgencias.setCodigo_sucursal(aux2.getCodigo_sucursal());
		aux_urgencias.setNro_identificacion(aux2.getNro_identificacion());
		aux_urgencias.setNro_ingreso(aux2.getNro_ingreso());
		aux_urgencias = urgenciasDao.consultar(aux_urgencias);
		if (aux_urgencias != null) {
			aux_urgencias.setCodigo_administradora(codigo_administradora);
			aux_urgencias.setId_plan(id_plan);
			urgenciasDao.actualizar(aux_urgencias);
		}
		// Actualizamos los contratos de la hospitalizacion //
		Hospitalizacion aux_hospitalizacion = new Hospitalizacion();
		aux_hospitalizacion.setCodigo_empresa(aux2.getCodigo_empresa());
		aux_hospitalizacion.setCodigo_sucursal(aux2.getCodigo_sucursal());
		aux_hospitalizacion.setNro_identificacion(aux2.getNro_identificacion());
		aux_hospitalizacion.setNro_ingreso(aux2.getNro_ingreso());
		aux_hospitalizacion = (Hospitalizacion) hospitalizacionDao
				.consultar(aux_hospitalizacion);
		if (aux_hospitalizacion != null) {
			aux_hospitalizacion.setCodigo_administradora(codigo_administradora);
			aux_hospitalizacion.setId_plan(id_plan);
			hospitalizacionDao.actualizar(aux_hospitalizacion);
		}
		// Actualizamos los contratos de los recien nacidos //
		Recien_nacido aux_recien_nacido = new Recien_nacido();
		aux_recien_nacido.setCodigo_empresa(aux2.getCodigo_empresa());
		aux_recien_nacido.setCodigo_sucursal(aux2.getCodigo_sucursal());
		aux_recien_nacido.setNro_identificacion(aux2.getNro_identificacion());
		aux_recien_nacido.setNro_ingreso(aux2.getNro_ingreso());
		aux_recien_nacido = recien_nacidoDao.consultar(aux_recien_nacido);
		if (aux_recien_nacido != null) {
			aux_recien_nacido.setCodigo_administradora(codigo_administradora);
			aux_recien_nacido.setId_plan(id_plan);
			recien_nacidoDao.actualizar(aux_recien_nacido);
		}

		// Actualizamos los contratos de receta rips //
		mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("codigo_paciente", aux2.getNro_identificacion());
		List<Orden_servicio> lista_orden = orden_servicioDao.listar(mapDatos);
		for (Orden_servicio aux : lista_orden) {
			aux.setCodigo_administradora(codigo_administradora);
			aux.setId_plan(id_plan);
			orden_servicioDao.actualizar(aux);
		}

		// Actualizamos los contratos de las facturas //
		Facturacion fact = new Facturacion();
		fact.setCodigo_empresa(aux2.getCodigo_empresa());
		fact.setCodigo_sucursal(aux2.getCodigo_sucursal());
		fact.setCodigo_tercero(aux2.getNro_identificacion());
		fact.setNro_ingreso(aux2.getNro_ingreso());
		fact = facturacionService.consultar(fact);
		if (fact != null) {
			fact.setCodigo_administradora(codigo_administradora);
			fact.setId_plan(id_plan);
			facturacionService.actualizar(fact);
		}
	}

	private void eliminarEgresos(Admision aux2) throws Exception {
		// Actualizamos los contratos de las urgencias //
		if (aux2.getUrgencias().equals("N")) {
			Urgencias aux_urgencias = new Urgencias();
			aux_urgencias.setCodigo_empresa(aux2.getCodigo_empresa());
			aux_urgencias.setCodigo_sucursal(aux2.getCodigo_sucursal());
			aux_urgencias.setNro_identificacion(aux2.getNro_identificacion());
			aux_urgencias.setNro_ingreso(aux2.getNro_ingreso());
			aux_urgencias = urgenciasDao.consultar(aux_urgencias);
			if (aux_urgencias != null) {
				urgenciasDao.eliminar(aux_urgencias);
			}
		}

		if (aux2.getHospitalizacion().equals("N")) {
			Hospitalizacion aux_hospitalizacion = new Hospitalizacion();
			aux_hospitalizacion.setCodigo_empresa(aux2.getCodigo_empresa());
			aux_hospitalizacion.setCodigo_sucursal(aux2.getCodigo_sucursal());
			aux_hospitalizacion.setNro_identificacion(aux2
					.getNro_identificacion());
			aux_hospitalizacion.setNro_ingreso(aux2.getNro_ingreso());
			aux_hospitalizacion = (Hospitalizacion) hospitalizacionDao
					.consultar(aux_hospitalizacion);
			if (aux_hospitalizacion != null) {
				hospitalizacionDao.eliminar(aux_hospitalizacion);
			}
		}

		if (aux2.getRecien_nacido().equals("N")) {
			Recien_nacido aux_recien_nacido = new Recien_nacido();
			aux_recien_nacido.setCodigo_empresa(aux2.getCodigo_empresa());
			aux_recien_nacido.setCodigo_sucursal(aux2.getCodigo_sucursal());
			aux_recien_nacido.setNro_identificacion(aux2
					.getNro_identificacion());
			aux_recien_nacido.setNro_ingreso(aux2.getNro_ingreso());
			aux_recien_nacido = recien_nacidoDao.consultar(aux_recien_nacido);
			if (aux_recien_nacido != null) {
				recien_nacidoDao.eliminar(aux_recien_nacido);
			}
		}

	}

	public Admision consultarAdmisionDiasAnteriores(Map<String, Object> map) {
		try {
			return admisionDao.consultarAdmisionDiasAnteriores(map);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite facturar los servicios facturados tales como
	 * Laboratorios, RX, Ecografias, etc.
	 *
	 * @author Luis Miguel Hernandez
	 * @param parametros_empresa
	 *
	 */
	public void guardarFacturacionServiciosOrdenados(
			List<Map<String, Object>> dtt_servicio, Admision admision,
			Manuales_tarifarios manuales_tarifarios, boolean generar)
			throws Exception {

		Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
		configuracion_admision_ingreso.setCodigo_empresa(admision
				.getCodigo_empresa());
		configuracion_admision_ingreso.setCodigo_sucursal(admision
				.getCodigo_sucursal());
		configuracion_admision_ingreso
				.setVia_ingreso(admision.getVia_ingreso());

		configuracion_admision_ingreso = configuracion_admision_ingresoService
				.consultar(configuracion_admision_ingreso);

		if (configuracion_admision_ingreso == null) {
			throw new ValidacionRunTimeException(
					"La via de ingreso "
							+ admision.getVia_ingreso()
							+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
		}

		if (generar
				&& configuracion_admision_ingreso
						.getFac_automatica_particular()) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(admision.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);

			if (paciente == null) {
				throw new ValidacionRunTimeException(
						"El paciente admisionado no existe en la base de datos.");
			}

			if (dtt_servicio.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se encontraron servicios a facturar");
			}

			Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
			datos_procedimiento.setCodigo_empresa(admision.getCodigo_empresa());
			datos_procedimiento.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			datos_procedimiento.setNro_identificacion(admision
					.getNro_identificacion());
			datos_procedimiento.setCodigo_administradora(admision
					.getCodigo_administradora());
			datos_procedimiento.setId_plan(admision.getId_plan());
			datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());

			datos_procedimiento.setCreacion_user(admision.getCreacion_user());
			datos_procedimientoService.eliminarRegistro(datos_procedimiento);

			// Recalculalamos los valores del procedimiento
			// Con el manual que tiene contratado ese servicio
			for (Map<String, Object> detalle_orden_map : dtt_servicio) {
				Detalle_orden detalle_orden = (Detalle_orden) detalle_orden_map
						.get("dtt_orden");

				// Verificamos si existe es un grupo
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("codigo_empresa", detalle_orden.getCodigo_empresa());
				param.put("codigo_sucursal", detalle_orden.getCodigo_sucursal());
				param.put("id_codigo_grupo",
						detalle_orden.getCodigo_procedimiento());
				if (grupos_procedimientosDao.existe(param)) {
					param.put("codigo_grupo", detalle_orden.getCodigo_cups());
					List<Detalle_grupos_procedimientos> detalle_grupos_procedimientos = detalle_grupos_procedimientosDao
							.listar(param);
					for (Detalle_grupos_procedimientos dtt_grupo : detalle_grupos_procedimientos) {
						Detalle_orden detalle_ordenTemp = ServiciosDisponiblesUtils
								.convertirDttGrupoDttOrden(dtt_grupo,
										detalle_orden, manuales_tarifarios);
						guardarServicio(detalle_ordenTemp, manuales_tarifarios,
								admision, paciente, detalle_orden_map,
								configuracion_admision_ingreso);
					}
				} else {
					guardarServicio(detalle_orden, manuales_tarifarios,
							admision, paciente, detalle_orden_map,
							configuracion_admision_ingreso);
				}
			}
			// Verificamos si la factura de manera automatica
			// Verificamos si no hace parte de los que facturan automatico
			// cuando sea particular
			// Que no cree la factura.
			ServiciosDisponiblesUtils.facturacionAutomatica(admision);
		}
	}

	private void guardarServicio(Detalle_orden detalle_orden,
			Manuales_tarifarios manuales_tarifarios, Admision admision,
			Paciente paciente, Map<String, Object> detalle_orden_map,
			Configuracion_admision_ingreso configuracion_admision_ingreso)
			throws Exception {
		// Consultamos procedimiento
		Map<String, Object> procedimiento = Utilidades.getProcedimiento(
				manuales_tarifarios,
				new Long(detalle_orden.getCodigo_procedimiento()),
				ServiciosDisponiblesUtils.getServiceLocator());
		double valor_pcd = (Double) procedimiento.get("valor_pcd");
		double descuento = (Double) procedimiento.get("descuento");
		double incremento = (Double) procedimiento.get("incremento");
		double valor_real = (Double) procedimiento.get("valor_real");

		// Agregamos valores al procedimiento
		detalle_orden.setValor_procedimiento(valor_pcd);
		detalle_orden.setValor_real(valor_real);
		detalle_orden.setDescuento(descuento);
		detalle_orden.setIncremento(incremento);

		// Verificamos si ahi una ecografia, para en la cual la pueda
		// atender el medico de ecografia ginecostetrica
		guardarVerificacionEcografiaGinecostetrica(admision,
				detalle_orden.getCodigo_cups());

		// Si es vacunacion se va a realizar un registro, con respecto al
		// esquema
		crearVacunacion(detalle_orden_map, admision);

		// Generamos el procedimiento
		guardarDatos_procedimiento(detalle_orden, admision, paciente,
				detalle_orden.getCodigo_cups(), detalle_orden_map,
				configuracion_admision_ingreso);

		if (detalle_orden.getCodigo_orden() != null
				&& detalle_orden.getCodigo_orden() > 0
				&& detalle_orden.getConsecutivo() != null
				&& detalle_orden.getCodigo_orden() != null
				&& !detalle_orden.getConsecutivo().trim().isEmpty()) {
			detalle_orden.setRealizado("S");
			detalle_ordenDao.actualizar(detalle_orden);
		}
	}

	private void crearVacunacion(Map<String, Object> detalle_orden_map,
			Admision admision) {
		if (admision.getVia_ingreso().equals(IVias_ingreso.VIA_VACUNACION)) {
			Esquema_vacunacion esquema_vacunacion = (Esquema_vacunacion) detalle_orden_map
					.get("esquema");
			Detalle_orden detalle_orden = (Detalle_orden) detalle_orden_map
					.get("dtt_orden");
			if (esquema_vacunacion != null) {
				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(admision.getCodigo_empresa());
				paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
				paciente.setNro_identificacion(admision.getNro_identificacion());
				paciente = pacienteService.consultar(paciente);

				if (paciente != null) {
					int anio = L2HContraintDate.getAnio(paciente
							.getFecha_nacimiento());
					int mes = L2HContraintDate.getMes(paciente
							.getFecha_nacimiento());

					Vacunas vacunas = new Vacunas();
					vacunas.setCodigo_empresa(admision.getCodigo_empresa());
					vacunas.setCodigo_sucursal(admision.getCodigo_sucursal());
					vacunas.setCodigo_procedimiento(esquema_vacunacion
							.getCodigo_vacuna());
					vacunas = vacunasDao.consultar(vacunas);

					if (vacunas != null) {
						// Agregamos el diagnostico de la vacuna
						detalle_orden_map.put("dx", vacunas.getCodigo_cie());
						// log.info("Diagnostico de vacuna: "
						// + detalle_orden_map.get("dx"));

						// Registramos relacionamos la vacuna con el paciente
						Vacunas_pacientes vacunas_pacientes = new Vacunas_pacientes();
						vacunas_pacientes.setCodigo_empresa(admision
								.getCodigo_empresa());
						vacunas_pacientes.setCodigo_sucursal(admision
								.getCodigo_sucursal());
						vacunas_pacientes
								.setCodigo_procedimiento_vacuna(esquema_vacunacion
										.getCodigo_vacuna());
						vacunas_pacientes.setNro_identificacion(admision
								.getNro_identificacion());
						vacunas_pacientes
								.setId_esquema_vacunacion(esquema_vacunacion
										.getConsecutivo());
						vacunas_pacientes.setDosis(esquema_vacunacion
								.getDosis());

						vacunas_pacientes.setCreacion_date(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						vacunas_pacientes.setUltimo_update(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						vacunas_pacientes.setCreacion_user(admision
								.getCreacion_user());
						vacunas_pacientes.setUltimo_user(admision
								.getUltimo_user());

						vacunas_pacientes.setCodigo_jeringa("");
						vacunas_pacientes.setValor_jeringa(0);
						vacunas_pacientes.setAnio(anio);
						vacunas_pacientes.setMeses(mes + 1);
						vacunas_pacientes
								.setVia_administracion(esquema_vacunacion != null ? esquema_vacunacion
										.getVia_administracion() : "");
						vacunas_pacientes.setDescripcion_edad(vacunas
								.getDescripcion());
						vacunas_pacientes.setFecha_vacunacion(new Timestamp(
								Calendar.getInstance().getTimeInMillis()));
						vacunas_pacientes
								.setRespuesta_4505(esquema_vacunacion != null ? esquema_vacunacion
										.getRespuesta_4505() + ""
										: "");
						vacunas_pacientes
								.setEstado(IConstantes.ESTADO_VACUNA_NUEVA);
						vacunas_pacientes.setObservacion_estado("");
						vacunas_pacientes.setNro_ingreso(admision
								.getNro_ingreso());
						vacunas_pacientesDao.crear(vacunas_pacientes);
					} else {
						throw new ValidacionRunTimeException(
								"Para factuarar la vacuna "
										+ detalle_orden
												.getCodigo_procedimiento()
										+ " necesita un registrar esta vacuna en el esquema de vacunacion, por favor comuniquese con el administrador del sistema");
					}
				} else {
					throw new ValidacionRunTimeException(
							"A la hora de registrar la vacuna no se encuantra el paciente");
				}
			}
			// sino tiene esquema no registra la dosis seleccionada
		}
	}

	/**
	 * Este metodo me permite facturar los servicios facturados tales como
	 * medicamentos, etc.
	 *
	 * @author Dario Perez Campillo
	 * @param parametros_empresa
	 *
	 */
	public void guardarFacturacionServiciosMedicamentos(
			List<Map> dtt_medicamentos, Admision admision,
			Manuales_tarifarios manuales_tarifarios, boolean generar)
			throws Exception {

		Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
		configuracion_admision_ingreso.setCodigo_empresa(admision
				.getCodigo_empresa());
		configuracion_admision_ingreso.setCodigo_sucursal(admision
				.getCodigo_sucursal());
		configuracion_admision_ingreso
				.setVia_ingreso(admision.getVia_ingreso());

		configuracion_admision_ingreso = configuracion_admision_ingresoService
				.consultar(configuracion_admision_ingreso);

		if (configuracion_admision_ingreso == null) {
			throw new ValidacionRunTimeException(
					"La via de ingreso "
							+ admision.getVia_ingreso()
							+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
		}

		if (generar
				&& configuracion_admision_ingreso
						.getFac_automatica_particular()) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(admision.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);

			if (paciente == null) {
				throw new ValidacionRunTimeException(
						"El paciente admisionado no existe en la base de datos.");
			}

			if (dtt_medicamentos.isEmpty()) {
				throw new ValidacionRunTimeException(
						"No se encontraron medicamentos a facturar");
			}

			Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
			facturacion_medicamento.setCodigo_empresa(admision
					.getCodigo_empresa());
			facturacion_medicamento.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			facturacion_medicamento.setNro_ingreso(admision.getNro_ingreso());
			facturacion_medicamento.setNro_identificacion(admision
					.getNro_identificacion());
			facturacion_medicamento.setCodigo_administradora(admision
					.getCodigo_administradora());
			facturacion_medicamento.setId_plan(admision.getId_plan());

			facturacion_medicamentoDao.eliminar(facturacion_medicamento);

			int consecutivo = 1;
			for (Map detalle_medicamentos : dtt_medicamentos) {
				Articulo articulo = (Articulo) detalle_medicamentos
						.get("medicamento");
				double valor = (int) articulo.getPrecio1();
				double valor_real = valor;

				double descuento = 0, incremento = 0;
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(admision.getCodigo_empresa());
				contratos.setCodigo_sucursal(admision.getCodigo_sucursal());
				contratos.setCodigo_administradora(admision
						.getCodigo_administradora());
				contratos.setId_plan(admision.getId_plan());
				contratos = contratosDao.consultar(contratos);
				if (manuales_tarifarios != null) {
					if (manuales_tarifarios.getTarifa_especial().equals("S")) {
						Tarifa_medicamento tarifa = new Tarifa_medicamento();
						tarifa.setCodigo_empresa(contratos.getCodigo_empresa());
						tarifa.setCodigo_sucursal(contratos
								.getCodigo_sucursal());
						tarifa.setCodigo_administradora(contratos
								.getCodigo_administradora());
						tarifa.setId_plan(contratos.getId_plan());
						tarifa.setCodigo_pcd(articulo.getCodigo_articulo());
						tarifa = tarifa_medicamentoDao.consultar(tarifa);
						if (tarifa != null) {
							valor = tarifa.getValor();
							valor_real = valor;
						}
					}
				}

				if (manuales_tarifarios != null) {
					if (articulo.getGrupo1().equals("01")) {
						if (manuales_tarifarios.getTipo_medicamento().equals(
								"01")) {
							descuento = (int) (valor * (manuales_tarifarios
									.getMedicamentos() / 100));
							valor -= descuento;
						} else {
							incremento = (int) (valor * (manuales_tarifarios
									.getMedicamentos() / 100));
							valor += incremento;
						}
					} else if (articulo.getGrupo1().equals("02")) {
						if (manuales_tarifarios.getTipo_servicio().equals("01")) {
							descuento = (int) (valor * (manuales_tarifarios
									.getServicios() / 100));
							valor -= descuento;
						} else {
							incremento = (int) (valor * (manuales_tarifarios
									.getServicios() / 100));
							valor += incremento;
						}
					}
				}

				Integer cantidad = (Integer) detalle_medicamentos
						.get("cantidad_medicamento");

				// creamos factura de medicamento
				Facturacion_medicamento facturacionMedicamento = new Facturacion_medicamento();
				facturacionMedicamento.setCodigo_empresa(admision
						.getCodigo_empresa());
				facturacionMedicamento.setCodigo_sucursal(admision
						.getCodigo_sucursal());
				// facturacionMedicamento.setNro_factura(nro_factura_med);
				facturacionMedicamento.setTipo_identificacion(paciente
						.getTipo_identificacion());
				facturacionMedicamento.setNro_identificacion(paciente
						.getNro_identificacion());
				facturacionMedicamento.setCodigo_administradora(admision
						.getCodigo_administradora());
				facturacionMedicamento.setId_plan(admision.getId_plan());
				facturacionMedicamento
						.setNro_ingreso(admision.getNro_ingreso());
				facturacionMedicamento.setFecha_medicamento(Timestamp
						.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date())));
				facturacionMedicamento.setNumero_autorizacion("");
				facturacionMedicamento.setObservacion("");
				facturacionMedicamento.setTipo("01");
				facturacionMedicamento.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				facturacionMedicamento.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				facturacionMedicamento.setCreacion_user(admision
						.getCreacion_user());
				facturacionMedicamento
						.setUltimo_user(admision.getUltimo_user());
				facturacionMedicamento.setCodigo_solicitud("");
				facturacionMedicamento.setCodigo_receta("");
				facturacionMedicamento.setC_costo("");

				Facturacion_medicamento fact_aux = facturacion_medicamentoDao
						.consultar(facturacionMedicamento);

				if (fact_aux == null) {
					// String nro_factura =
					// consecutivoService.obtenerConsecutivo(
					// facturacionMedicamento.getCodigo_empresa(),
					// facturacionMedicamento.getCodigo_sucursal(),
					// "MEDICAMENTOS-MATERIALES");
					// facturacionMedicamento.setNro_factura(consecutivoService
					// .getZeroFill(nro_factura, 10));
					facturacion_medicamentoDao.crear(facturacionMedicamento);
					// consecutivoService.actualizarConsecutivo(
					// admision.getCodigo_empresa(),
					// admision.getCodigo_sucursal(),
					// "MEDICAMENTOS-MATERIALES", nro_factura);
				} else {
					facturacionMedicamento.setNro_factura(fact_aux
							.getNro_factura());
				}

				Datos_medicamentos datos_medicamentos = new Datos_medicamentos();
				datos_medicamentos.setCodigo_medicamento(articulo
						.getCodigo_articulo());
				datos_medicamentos.setCodigo_empresa(articulo
						.getCodigo_empresa());
				datos_medicamentos.setCodigo_sucursal(articulo
						.getCodigo_sucursal());
				datos_medicamentos.setCantidad_entregada(cantidad);
				datos_medicamentos
						.setReferencia_pyp(articulo != null ? articulo
								.getReferencia() : "");

				/* cargamos existencias */
				Bodega_articulo bodega_articulo = new Bodega_articulo();
				bodega_articulo.setCodigo_empresa(articulo.getCodigo_empresa());
				bodega_articulo.setCodigo_sucursal(articulo
						.getCodigo_sucursal());
				bodega_articulo.setCodigo_bodega("01");
				bodega_articulo.setCodigo_articulo(articulo
						.getCodigo_articulo());
				bodega_articulo = bodega_articuloDao.consultar(bodega_articulo);

				if (bodega_articulo == null) {
					throw new ValidacionRunTimeException("El articulo "
							+ articulo.getNombre1()
							+ " no se encuentra en bodega");
				}

				datos_medicamentos.setCodigo_bodega("01");// bodega por defecto
				datos_medicamentos.setCodigo_lote("");
				datos_medicamentos.setCancelo_copago("");
				datos_medicamentos.setConsecutivo(consecutivo + "");
				datos_medicamentos.setCreacion_date(new Timestamp((new Date())
						.getTime()));
				datos_medicamentos
						.setCreacion_user(admision.getCreacion_user());
				datos_medicamentos.setDescuento(descuento);
				datos_medicamentos.setIncremento(incremento);
				datos_medicamentos.setNro_factura(facturacionMedicamento
						.getNro_factura());
				datos_medicamentos.setUltimo_update(new Timestamp((new Date())
						.getTime()));
				datos_medicamentos.setUltimo_user(admision.getUltimo_user());
				datos_medicamentos.setUnidades(cantidad);
				datos_medicamentos.setValor_real(valor_real);
				datos_medicamentos.setValor_unitario(valor);
				datos_medicamentos
						.setValor_total(datos_medicamentos.getUnidades()
								* datos_medicamentos.getValor_unitario());

				datos_medicamentosDao.crear(datos_medicamentos);
				consecutivo++;

			}

			// Verificamos si la factura de manera automatica
			// Verificamos si no hace parte de los que facturan automatico
			// cuando sea particular
			// Que no cree la factura.
			ServiciosDisponiblesUtils.facturacionAutomatica(admision);
		}
	}

	// private void relacionarConFactura(Admision admision,
	// Facturacion_medicamento facturacionMedicamento) {
	// Facturacion facturacion = new Facturacion();
	// facturacion.setCodigo_empresa(admision.getCodigo_empresa());
	// facturacion.setCodigo_sucursal(admision.getCodigo_sucursal());
	// facturacion.setCodigo_tercero(admision.getNro_identificacion());
	// facturacion.setNro_ingreso(admision.getNro_ingreso());
	// facturacion = facturacionDao.consultar(facturacion);
	// if (facturacion != null) {
	// facturacionMedicamento.setNro_factura(facturacion
	// .getCodigo_documento());
	// }
	// }
	/**
	 * Validamos si se ordeno un ecografia ginecostetrica
	 *
	 * @author Luis Miguel
	 *
	 */
	private void guardarVerificacionEcografiaGinecostetrica(Admision admision,
			String codigo_cups) {
		if (codigo_cups
				.equals(IVias_ingreso.CODIGO_CUPS_ECOGRAFIA_GINECOSTETRICA)) {
			admision.setTipo_adicional_via_ingreso(IVias_ingreso.ECOGRAFIA_GINECOSTETRICA);
			admisionDao.actualizar(admision);
			// log.info("nueva admision para Ecografia Ginecostetrica");
		}
	}

	private void guardarDatos_procedimiento(Detalle_orden detalle_orden,
			Admision admision, Paciente paciente, String codigo_cups,
			Map<String, Object> detalle_orden_map,
			Configuracion_admision_ingreso configuracion_admision_ingreso) {
		// log.info("facturando Procedimiento ordenando ===> "
		// + detalle_orden.getUnidades_realizadas());
		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento
				.setCodigo_empresa(detalle_orden.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(detalle_orden
				.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);

		datos_procedimiento.setTipo_identificacion(paciente
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(paciente
				.getNro_identificacion());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(detalle_orden
				.getCodigo_procedimiento());
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento.setValor_procedimiento(detalle_orden
				.getValor_procedimiento());
		datos_procedimiento.setUnidades(detalle_orden.getUnidades());
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento.setValor_neto(detalle_orden
				.getValor_procedimiento()
				* detalle_orden.getUnidades().intValue());

		datos_procedimiento
				.setAmbito_procedimiento(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
						.getAmbito_realizacion()
						: IDatosProcedimientos.AMBITO_REALIZACION);
		datos_procedimiento
				.setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
		datos_procedimiento
				.setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
		datos_procedimiento
				.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);

		String codigo_dx = (String) detalle_orden_map.get("dx");
		// log.info("Diagnostico de vacuna: " + detalle_orden_map.get("dx"));
		datos_procedimiento
				.setCodigo_diagnostico_principal(codigo_dx != null
						&& !codigo_dx.trim().isEmpty()
						&& codigo_dx.length() == 4 ? codigo_dx : admision
						.getDiagnostico_ingreso());

		datos_procedimiento.setCodigo_diagnostico_relacionado("");
		datos_procedimiento.setComplicacion("");
		datos_procedimiento.setCancelo_copago("N");
		datos_procedimiento.setCodigo_programa("");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(admision.getCreacion_user());
		datos_procedimiento.setUltimo_user(admision.getCreacion_user());
		datos_procedimiento.setValor_real(detalle_orden.getValor_real());
		datos_procedimiento.setDescuento(detalle_orden.getDescuento());
		datos_procedimiento.setIncremento(detalle_orden.getIncremento());
		datos_procedimiento.setRealizado_en("");
		datos_procedimiento.setCodigo_cups(codigo_cups);
		datos_procedimiento.setCodigo_orden(detalle_orden.getCodigo_orden());

		datos_procedimientoService.crear(datos_procedimiento);
	}

	private void guardarDatos_procedimiento(
			Manuales_tarifarios manuales_tarifarios, Map<String, Object> bean,
			Admision admision, Paciente paciente) throws Exception {

		Long id_procedimiento = (Long) bean.get("id_procedimiento");

		Map<String, Object> bean_procedimiento = ServiciosDisponiblesUtils
				.getProcedimiento(manuales_tarifarios, id_procedimiento + "");
		if (bean != null) {
			double unidades = (Double) bean.get("unidades");
			String codigo_cups = (String) bean.get("codigo_cups");
			String tipo_procedimiento = (String) bean.get("tipo_procedimiento");
			String nombre_procedimiento = (String) bean
					.get("nombre_procedimiento");
			double descuento = (Double) bean_procedimiento.get("descuento");
			double incremento = (Double) bean_procedimiento.get("incremento");
			double valor_real = (Double) bean_procedimiento.get("valor_real");
			double valor_procedimiento = (Double) bean_procedimiento
					.get("valor_pcd");

			Detalle_orden detalle_orden = new Detalle_orden();
			detalle_orden.setCodigo_procedimiento(id_procedimiento + "");
			detalle_orden.setValor_procedimiento(valor_procedimiento);
			detalle_orden.setUnidades((int) unidades);
			detalle_orden.setDescuento(descuento);
			detalle_orden.setIncremento(incremento);
			detalle_orden.setValor_real(valor_real);
			detalle_orden.setCodigo_cups(codigo_cups);
			detalle_orden.setRealizado("N");
			detalle_orden.setUnidades_realizadas((int) unidades);
			detalle_orden.setTipo_procedimiento(tipo_procedimiento);
			detalle_orden.setNombre_procedimiento(nombre_procedimiento);

			Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
			datos_procedimiento.setConsecutivo_registro("1");
			datos_procedimiento.setCodigo_procedimiento(id_procedimiento + "");
			datos_procedimiento.setCodigo_empresa(admision.getCodigo_empresa());
			datos_procedimiento.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			datos_procedimiento.setCodigo_registro(null);

			datos_procedimiento.setTipo_identificacion(paciente
					.getTipo_identificacion());
			datos_procedimiento.setNro_identificacion(paciente
					.getNro_identificacion());
			datos_procedimiento.setCodigo_administradora(admision
					.getCodigo_administradora());
			datos_procedimiento.setId_plan(admision.getId_plan());
			datos_procedimiento
					.setCodigo_prestador(admision.getCodigo_medico());
			datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
			datos_procedimiento.setCodigo_procedimiento(detalle_orden
					.getCodigo_procedimiento());

			datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
					.getInstance().getTimeInMillis()));
			datos_procedimiento.setNumero_autorizacion("");
			datos_procedimiento.setValor_procedimiento(valor_procedimiento);
			datos_procedimiento.setUnidades(detalle_orden.getUnidades());
			datos_procedimiento.setCopago(0.0);
			datos_procedimiento.setValor_neto(valor_procedimiento
					* detalle_orden.getUnidades_realizadas().intValue());

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
			datos_procedimiento.setCodigo_programa("");

			datos_procedimiento.setCreacion_date(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			datos_procedimiento.setUltimo_update(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			datos_procedimiento.setCreacion_user(admision.getCreacion_user());
			datos_procedimiento.setUltimo_user(admision.getCreacion_user());
			datos_procedimiento.setValor_real(detalle_orden.getValor_real());
			datos_procedimiento.setDescuento(detalle_orden.getDescuento());
			datos_procedimiento.setIncremento(detalle_orden.getIncremento());
			datos_procedimiento.setRealizado_en("");
			datos_procedimiento.setCodigo_cups(codigo_cups);
			datos_procedimiento.setCodigo_orden(null);
			datos_procedimiento.setCodigo_registro(null);

			Datos_procedimiento datos_procedimiento2 = datos_procedimientoService
					.consultar(datos_procedimiento);

			if (datos_procedimiento2 == null) {
				datos_procedimiento.setCodigo_registro(null);
				datos_procedimientoService.crear(datos_procedimiento);
			}
		}

	}

	private void guardarDatos_procedimientoUrgencia(
			Detalle_orden detalle_orden, Admision admision, Paciente paciente,
			String codigo_cups) {
		// log.info("facturando Procedimiento urgencias ===> "
		// + detalle_orden.getUnidades_realizadas());

		boolean facturar_paquete = false;

		if (admision.getAdmision_parto().equals("S")) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", admision.getCodigo_empresa());
			parametros.put("codigo_sucursal", admision.getCodigo_sucursal());
			parametros.put("nro_identificacion",
					admision.getNro_identificacion());
			parametros.put("codigo_administradora",
					admision.getCodigo_administradora());

			List<Pacientes_contratos> listado_contratos = pacientes_contratosDao
					.listar(parametros);

			Contratos_paquetes contratos_paquetes_aux = null;

			for (Pacientes_contratos pacientes_contratos : listado_contratos) {
				Map<String, Object> parametros_paquetes = new HashMap<String, Object>();
				parametros_paquetes.put("codigo_empresa",
						pacientes_contratos.getCodigo_empresa());
				parametros_paquetes.put("codigo_sucursal",
						pacientes_contratos.getCodigo_sucursal());
				parametros_paquetes.put("id_plan",
						pacientes_contratos.getId_codigo());
				parametros_paquetes.put("codigo_administradora",
						pacientes_contratos.getCodigo_administradora());

				List<Contratos_paquetes> listado_paquetes = contratos_paquetesDao
						.listar(parametros_paquetes);

				for (Contratos_paquetes contratos_paquetes : listado_paquetes) {
					if (contratos_paquetes.getPaquetes_servicios()
							.getVia_ingreso().equals(admision.getVia_ingreso())) {
						contratos_paquetes_aux = contratos_paquetes;
						break;
					}
				}

				if (contratos_paquetes_aux != null) {
					break;
				}

			}

			if (contratos_paquetes_aux != null) {
				if (contratos_paquetes_aux
						.getPaquetes_servicios()
						.getId_procedimiento_principal()
						.equals(new Long(detalle_orden
								.getCodigo_procedimiento()))) {
					facturar_paquete = true;
				} else {
					Map<String, Object> parametros_cups = new HashMap<String, Object>();
					parametros_cups.put("codigo_empresa",
							contratos_paquetes_aux.getCodigo_empresa());
					parametros_cups.put("codigo_sucursal",
							contratos_paquetes_aux.getCodigo_sucursal());
					parametros_cups.put("codigo_administradora",
							contratos_paquetes_aux.getCodigo_administradora());
					parametros_cups.put("id_paquete",
							contratos_paquetes_aux.getId_paquete());
					List<Detalles_paquetes_servicios> listado_detalles = detalles_paquetes_serviciosDao
							.listar(parametros_cups);
					for (Detalles_paquetes_servicios detalles_paquetes_servicios : listado_detalles) {
						if (detalles_paquetes_servicios
								.getCodigo_detalle()
								.equals(detalle_orden.getCodigo_procedimiento())) {
							facturar_paquete = true;
							break;
						}
					}
				}
			}

			if (facturar_paquete) {
				// log.info("Facturando paquete de servicios");
				guardarDatos_procedimiento_paquete(
						contratos_paquetes_aux.getPaquetes_servicios(),
						admision, admision.getPaciente());
			}
		}

		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento
				.setCodigo_empresa(detalle_orden.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(detalle_orden
				.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);

		datos_procedimiento.setTipo_identificacion(paciente
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(paciente
				.getNro_identificacion());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(detalle_orden
				.getCodigo_procedimiento());
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento.setValor_procedimiento(facturar_paquete ? 0.0
				: detalle_orden.getValor_procedimiento());
		datos_procedimiento.setUnidades(detalle_orden.getUnidades_realizadas());
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento.setValor_neto(facturar_paquete ? 0.0
				: detalle_orden.getValor_procedimiento()
						* detalle_orden.getUnidades_realizadas().intValue());

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
		datos_procedimiento.setCodigo_programa("");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(admision.getCreacion_user());
		datos_procedimiento.setUltimo_user(admision.getCreacion_user());
		datos_procedimiento.setValor_real(facturar_paquete ? 0.0
				: detalle_orden.getValor_real());
		datos_procedimiento.setDescuento(facturar_paquete ? 0.0 : detalle_orden
				.getDescuento());
		datos_procedimiento.setIncremento(facturar_paquete ? 0.0
				: detalle_orden.getIncremento());
		datos_procedimiento.setRealizado_en("");
		datos_procedimiento.setCodigo_cups(codigo_cups);
		datos_procedimiento.setCodigo_orden(detalle_orden.getCodigo_orden());

		Datos_procedimiento datos_procedimiento2 = datos_procedimientoService
				.consultar(datos_procedimiento);

		if (datos_procedimiento2 != null) {
			datos_procedimiento.setCodigo_registro(datos_procedimiento2
					.getCodigo_registro());
			datos_procedimientoService.actualizarRegistro(datos_procedimiento);
		} else {
			datos_procedimiento.setCodigo_registro(null);
			datos_procedimientoService.crear(datos_procedimiento);
		}

	}

	private void guardarDatos_procedimiento_paquete(
			Paquetes_servicios paquetes_servicios, Admision admision,
			Paciente paciente) {
		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(paquetes_servicios
				.getId_procedimiento_principal()));
		procedimientos = procedimientoService.consultar(procedimientos);
		// log.info("facturando Procedimiento ordenando ===> "
		// + paquetes_servicios.getId_procedimiento_principal());
		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento.setCodigo_empresa(paquetes_servicios
				.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(paquetes_servicios
				.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);

		datos_procedimiento.setTipo_identificacion(paciente
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(paciente
				.getNro_identificacion());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(paquetes_servicios
				.getId_procedimiento_principal() + "");
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento.setValor_procedimiento(paquetes_servicios
				.getValor());
		datos_procedimiento.setUnidades(1);
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento.setValor_neto(paquetes_servicios.getValor());

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
		datos_procedimiento.setCodigo_programa("");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(admision.getCreacion_user());
		datos_procedimiento.setUltimo_user(admision.getCreacion_user());
		datos_procedimiento.setValor_real(paquetes_servicios.getValor());
		datos_procedimiento.setDescuento(0);
		datos_procedimiento.setIncremento(0);
		datos_procedimiento.setRealizado_en("");
		datos_procedimiento
				.setCodigo_cups(procedimientos != null ? procedimientos
						.getCodigo_cups() : "");
		datos_procedimiento.setCodigo_orden(null);

		Datos_procedimiento datos_procedimiento2 = datos_procedimientoService
				.consultar(datos_procedimiento);

		if (datos_procedimiento2 != null) {
			datos_procedimiento.setCodigo_registro(datos_procedimiento2
					.getCodigo_registro());
			datos_procedimientoService.actualizarRegistro(datos_procedimiento);
		} else {
			datos_procedimiento.setCodigo_registro(null);
			datos_procedimientoService.crear(datos_procedimiento);
		}
	}

	private void guardarDatos_procedimientoOxigenoUrgencia(
			Detalle_orden detalle_orden, Admision admision, Paciente paciente,
			Contratos contratos) {
		// log.info("facturando Procedimiento oxigeno ===> "
		// + detalle_orden.getUnidades_realizadas());
		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento
				.setCodigo_empresa(detalle_orden.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(detalle_orden
				.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);

		datos_procedimiento.setTipo_identificacion(paciente
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(paciente
				.getNro_identificacion());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(detalle_orden
				.getCodigo_procedimiento());
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento
				.setValor_procedimiento(contratos.getValor_oxigeno());
		datos_procedimiento.setUnidades(detalle_orden.getUnidades_realizadas());
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento.setValor_neto(contratos.getValor_oxigeno()
				* detalle_orden.getUnidades_realizadas().intValue());

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
		datos_procedimiento.setCodigo_programa("");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(admision.getCreacion_user());
		datos_procedimiento.setUltimo_user(admision.getCreacion_user());
		datos_procedimiento.setValor_real(detalle_orden.getValor_real());
		datos_procedimiento.setDescuento(detalle_orden.getDescuento());
		datos_procedimiento.setIncremento(detalle_orden.getIncremento());
		datos_procedimiento.setRealizado_en("");
		datos_procedimiento.setCodigo_cups(contratos.getCups_oxigeno());
		datos_procedimiento.setCodigo_orden(detalle_orden.getCodigo_orden());
		datos_procedimiento.setCodigo_registro(null);

		Datos_procedimiento datos_procedimiento2 = datos_procedimientoService
				.consultar(datos_procedimiento);

		if (datos_procedimiento2 != null) {
			datos_procedimiento.setCodigo_registro(datos_procedimiento2
					.getCodigo_registro());
			datos_procedimientoService.actualizarRegistro(datos_procedimiento);
		} else {
			datos_procedimiento.setCodigo_registro(null);
			datos_procedimientoService.crear(datos_procedimiento);
		}

	}

	public void guardarFacturacionProcedimientosUrgencia(
			Map<String, Object> mapa_datos) {
		try {
			Admision admision = (Admision) mapa_datos.get("admision");

			Map<String, Object> mapa_servicios = new HashMap<String, Object>();
			mapa_servicios
					.put("pacientes_contratosDao", pacientes_contratosDao);
			mapa_servicios.put("servicios_contratadosService",
					servicios_contratadosService);
			mapa_servicios.put("servicios_via_ingresoService",
					servicios_via_ingresoService);

			Map<String, Object> mapa_vias_ingreso = ServiciosDisponiblesUtils
					.obtenerMapaViasIngreso(admision.getPaciente(), admision
							.getCodigo_administradora(), admision.getId_plan(),
							admision.getParticular().equalsIgnoreCase("S"));
			Manuales_tarifarios manuales_tarifarios = null;

			if (!mapa_vias_ingreso.containsKey(admision.getVia_ingreso())) {
				throw new ValidacionRunTimeException(
						"No se encontraron manuales tarifarios para este contrato");
			} else {
				// log.info("admision ===> " + admision);
				Map<String, Object> datos_verificacion = (Map<String, Object>) mapa_vias_ingreso
						.get(admision.getVia_ingreso());
				if (admision.getId_plan() == null) {
					admision.setId_plan((String) datos_verificacion
							.get("id_contrato"));
				}
				manuales_tarifarios = new Manuales_tarifarios();
				manuales_tarifarios.setCodigo_empresa(admision
						.getCodigo_empresa());
				manuales_tarifarios.setCodigo_sucursal(admision
						.getCodigo_sucursal());
				manuales_tarifarios.setCodigo_administradora(admision
						.getCodigo_administradora());
				manuales_tarifarios.setId_contrato(admision.getId_plan());
				manuales_tarifarios.setConsecutivo((Long) datos_verificacion
						.get("consecutivo"));
				manuales_tarifarios = manuales_tarifariosDao
						.consultar(manuales_tarifarios);
			}

			if (manuales_tarifarios == null) {
				throw new ValidacionRunTimeException(
						"No se encontraron manuales tarifarios para este contrato");
			}

			List<Detalle_orden> lista_detalles = (List<Detalle_orden>) mapa_datos
					.get("lista_detalles");

			guardarFacturacionProcedimientosUrgencia(lista_detalles, admision,
					manuales_tarifarios, true);

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarFacturacionProcedimientosUrgencia(
			List<Detalle_orden> lista_detalles, Admision admision,
			Manuales_tarifarios manuales_tarifarios, boolean realizarAccion)
			throws Exception {
		if (realizarAccion) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(admision.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);

			if (paciente == null) {
				throw new ValidacionRunTimeException(
						"El paciente admisionado no existe en la base de datos.");
			}

			for (Detalle_orden detalle_orden : lista_detalles) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", detalle_orden.getCodigo_empresa());
				map.put("codigo_sucursal", detalle_orden.getCodigo_sucursal());
				map.put("codigo_cups", detalle_orden.getCodigo_cups());
				// log.info("Detalle orden: " + detalle_orden.getCodigo_cups());

				Procedimientos procedimientos = new Procedimientos();
				procedimientos.setId_procedimiento(new Long(detalle_orden
						.getCodigo_procedimiento()));
				procedimientos = procedimientoService.consultar(procedimientos);
				// String codigo_tipo_procedimiento = procedimiento_iss01 !=
				// null ? procedimiento_iss01.getCodigo_tipo_procedimiento() :
				// "$NO";

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", admision.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", admision.getCodigo_sucursal());
				parametros.put("nro_identificacion",
						admision.getNro_identificacion());
				parametros.put("codigo_servicio_urg",
						ServiciosDisponiblesUtils.CODSER_URGENCIAS);
				parametros.put("codigo_servicio_hos",
						ServiciosDisponiblesUtils.CODSER_HOSPITALIZACION);

				List<Servicios_contratados> listado_servicios = servicios_contratadosService
						.listarServiciosPaciente(parametros);
				Servicios_contratados servicios_contratados = null;

				if (!listado_servicios.isEmpty()) {
					servicios_contratados = listado_servicios.get(0);
				}

				boolean valido = true;

				if (valido) {
					if (servicios_contratados != null) {
						Contratos contratos = new Contratos();
						contratos.setCodigo_empresa(admision
								.getCodigo_empresa());
						contratos.setCodigo_sucursal(admision
								.getCodigo_sucursal());
						contratos.setId_plan(servicios_contratados
								.getId_contrato());
						contratos
								.setCodigo_administradora(servicios_contratados
										.getCodigo_administradora());
						contratos = contratosDao.consultar(contratos);
						// log.info("contratos  ===> " + contratos);
						if (contratos != null) {
							if (detalle_orden.getCodigo_cups().equals(
									contratos.getCups_oxigeno())) {
								guardarDatos_procedimientoOxigenoUrgencia(
										detalle_orden, admision, paciente,
										contratos);
							} else {
								guardarDatos_procedimientoUrgencia(
										detalle_orden, admision, paciente,
										detalle_orden.getCodigo_cups());
							}
						} else {
							guardarDatos_procedimientoUrgencia(detalle_orden,
									admision, paciente,
									detalle_orden.getCodigo_cups());
						}
					} else {
						guardarDatos_procedimientoUrgencia(detalle_orden,
								admision, paciente,
								detalle_orden.getCodigo_cups());
					}
					detalle_ordenDao.actualizar(detalle_orden);
				} else {
					// log.info("No es valido para registro: "
					// + detalle_orden.getCodigo_cups());
				}
			}
		}
	}

	public List<Map<String, Object>> guardarFacturacionAdimisiones(
			Map<String, Object> mapa_datos) {
		try {
			List<Admision> listado_admisiones = (List<Admision>) mapa_datos
					.get("listado_admisiones");
			List<Map<String, Object>> listador_errores = new ArrayList<Map<String, Object>>();
			for (Admision admision : listado_admisiones) {
				Facturacion facturacion = new Facturacion();
				facturacion.setCodigo_empresa(admision.getCodigo_empresa());
				facturacion.setCodigo_sucursal(admision.getCodigo_sucursal());
				facturacion.setCodigo_tercero(admision.getNro_identificacion());
				facturacion.setNro_ingreso(admision.getNro_ingreso());
				facturacion = facturacionService.consultar(facturacion);
				if (facturacion != null) {
					admision.setEstado("2");
					admisionDao.actualizar(admision);
				} else {
					try {
						Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
						impresion_diagnostica.setFinalidad_consulta("10");
						impresion_diagnostica.setCie_principal("Z000");
						impresion_diagnostica.setTipo_principal("1");
						crearConsulta(admision, impresion_diagnostica);
					} catch (Exception e) {
						Map<String, Object> map_contenedor_errores = new HashMap<String, Object>();
						map_contenedor_errores.put(IConstantes.PARAM_EXCEPCION,
								e);
						map_contenedor_errores.put(IConstantes.PARAM_ADMISION,
								admision);
						listador_errores.add(map_contenedor_errores);
					}
				}
			}
			return listador_errores;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearConsulta(Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {
		try {
			Map map = new HashMap();
			map.put("codigo_empresa", admision.getCodigo_empresa());
			map.put("codigo_sucursal", admision.getCodigo_sucursal());
			map.put("nro_identificacion", admision.getNro_identificacion());
			map.put("nro_ingreso", admision.getNro_ingreso());
			map.put("codigo_prestador", admision.getCodigo_medico());
			map.put("codigo_dx", impresion_diagnostica.getCie_principal());
			map.put("creacion_date", admision.getCreacion_date());
			map.put("ultimo_update", admision.getUltimo_update());
			map.put("creacion_user", admision.getCreacion_user());
			map.put("ultimo_user", admision.getUltimo_user());
			map.put("tipo_hc", "");
			map.put("fecha_ingreso", admision.getFecha_ingreso());
			map.put("impresion_diagnostica", impresion_diagnostica);
			map.put("admision", admision);
			map.put("recalculo_factura", "recalcular");
			ServiciosDisponiblesUtils.generarConsulta(map);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void actualizar_estado(Admision admision) {
		admisionDao.actualizar_estado(admision);
	}

	public Long getIdHistoria(Admision admision) {
		return admisionDao.getIdHistoria(admision);
	}

	public boolean consultarContieneServicios(Admision admision) {
		return admisionDao.consultarContieneServicios(admision);
	}

	public Boolean existe(Map<String, Object> parametros) {
		return admisionDao.existe(parametros);
	}

	public boolean verificarServicios(Admision admision) {
		BeanMap beanMap = new BeanMap(admision);
		boolean resultado = false;
		try {
			resultado = datos_consultaDao.existe(beanMap);
			if (!resultado) {
				resultado = datos_procedimientoService.existe(beanMap);
			}
			if (!resultado) {
				resultado = facturacion_medicamentoDao.existe(beanMap);
			}
			if (!resultado) {
				resultado = facturacion_servicioDao.existe(beanMap);
			}
			if (!resultado
					&& admision.getVia_ingreso().equals(
							IVias_ingreso.HOSPITALIZACIONES)) {
				resultado = admision_camaDao.existe(beanMap);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
		return resultado;
	}

	public Map<String, Object> getUltimaAutorizacion(
			Map<String, Object> parametros) {
		log.info("Ejecutando metodo getUltimaAutorizacion() ===> " + parametros);
		try {
			return admisionDao.getUltimaAutorizacion(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Timestamp getFechaUltimoServicio(Admision admision) {
		return admisionDao.getFechaUltimoServicio(admision);
	}

}
