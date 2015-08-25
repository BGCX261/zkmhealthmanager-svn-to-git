/*
 * PlanesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.controller.CargarMultipleAdministradoraAction;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Contratos_medicamentos;
import healthmanager.modelo.bean.Contratos_paquetes;
import healthmanager.modelo.bean.Contratos_procedimientos_ex;
import healthmanager.modelo.bean.Descuentos_laboratorios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.bean.Restriccion_pcd;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.bean.Servicios_procedimientos;
import healthmanager.modelo.bean.Servicios_via_ingreso;
import healthmanager.modelo.bean.Tarifas_procedimientos;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.dao.ContratosDao;
import healthmanager.modelo.dao.Contratos_medicamentosDao;
import healthmanager.modelo.dao.Contratos_paquetesDao;
import healthmanager.modelo.dao.Contratos_procedimientos_exDao;
import healthmanager.modelo.dao.Descuentos_laboratoriosDao;
import healthmanager.modelo.dao.Manuales_tarifariosDao;
import healthmanager.modelo.dao.Restriccion_pcdDao;
import healthmanager.modelo.dao.Servicios_contratadosDao;
import healthmanager.modelo.dao.Servicios_via_ingresoDao;
import healthmanager.modelo.dao.Tarifas_procedimientosDao;
import healthmanager.modelo.dao.Via_ingreso_contratadasDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.util.ServiciosDisponiblesUtils;

@Service("contratosService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContratosService implements Serializable {

	public static Logger log = Logger.getLogger(ContratosService.class);

	@Autowired
	private ContratosDao contratosDao;
	@Autowired
	private Restriccion_pcdDao restriccion_pcdDao;
	@Autowired
	private Tarifas_procedimientosDao tarifas_procedimientosDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Manuales_tarifariosDao manuales_tarifariosDao;
	@Autowired
	private Descuentos_laboratoriosDao descuentos_laboratoriosDao;

	@Autowired
	private Contratos_paquetesDao contratos_paquetesDao;

	@Autowired
	private Contratos_procedimientos_exDao contratos_procedimientos_exDao;

	@Autowired
	private Contratos_medicamentosDao contratos_medicamentosDao;

	@Autowired
	private Servicios_contratadosDao servicios_contratadosDao;

	@Autowired
	private Servicios_via_ingresoDao servicios_via_ingresoDao;

	@Autowired
	private Via_ingreso_contratadasDao via_ingreso_contratadasDao;

	public void crear(Contratos contratos) {
		try {
			if (consultar(contratos) != null) {
				throw new HealthmanagerException(
						"Contratos ya se encuentra en la base de datos");
			}
			contratosDao.crear(contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Contratos contratos) {
		try {
			return contratosDao.actualizar(contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Contratos consultar(Contratos contratos) {
		try {
			return contratosDao.consultar(contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Contratos contratos) {
		try {
			Servicios_contratados servicios_contratados = new Servicios_contratados();
			servicios_contratados.setCodigo_empresa(contratos
					.getCodigo_empresa());
			servicios_contratados.setCodigo_sucursal(contratos
					.getCodigo_sucursal());
			servicios_contratados.setCodigo_administradora(contratos
					.getCodigo_administradora());
			servicios_contratados.setId_contrato(contratos.getId_plan());
			servicios_contratadosDao.eliminar(servicios_contratados);
			return contratosDao.eliminar(contratos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Contratos> listar(Map<String, Object> parameter) {
		try {
			return contratosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listarProcedimientos(
			Map<String, Object> param) {
		try {
			return contratosDao.listarProcedimientos(param);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void guardarContrato(Map<String, Object> datos) {
		try {
			Contratos contratos = (Contratos) datos.get("contratos");
			List<Manuales_tarifarios> manuales_tarifarios = (List<Manuales_tarifarios>) datos
					.get("manuales_tarifarios");

			List<Paquetes_servicios> listado_paquetes = (List<Paquetes_servicios>) datos
					.get("listado_paquetes");

			String accion = (String) datos.get("accion");
			if (accion.equalsIgnoreCase("registrar")) {
				String id_plan = consecutivoService.crearConsecutivo(
						contratos.getCodigo_empresa(),
						contratos.getCodigo_sucursal(),
						IConstantes.ID_CONTRATOS);
				contratos.setId_plan(consecutivoService
						.getZeroFill(id_plan, 10));
				if (consultar(contratos) != null) {
					throw new ValidacionRunTimeException(
							"Contrato nro "
									+ id_plan
									+ " ya se encuentra en la base de datos actualize el consecutivo llamado "
									+ IConstantes.ID_CONTRATOS);
				}
				contratosDao.crear(contratos);
				consecutivoService.actualizarConsecutivo(
						contratos.getCodigo_empresa(),
						contratos.getCodigo_sucursal(),
						IConstantes.ID_CONTRATOS, id_plan);
			} else {
				contratosDao.actualizar(contratos);

				// Eliminamos los procedimientos especificos //
				Restriccion_pcd restriccion_pcd = new Restriccion_pcd();
				restriccion_pcd
						.setCodigo_empresa(contratos.getCodigo_empresa());
				restriccion_pcd.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				restriccion_pcd.setCodigo_administradora(contratos
						.getCodigo_administradora());
				restriccion_pcd.setId_plan(contratos.getId_plan());
				restriccion_pcdDao.eliminar(restriccion_pcd);

				// Eliminamos las tarifas especiales SOAT//
				Tarifas_procedimientos tarifas_procedimientos = new Tarifas_procedimientos();
				tarifas_procedimientos.setCodigo_empresa(contratos
						.getCodigo_empresa());
				tarifas_procedimientos.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				tarifas_procedimientos.setCodigo_administradora(contratos
						.getCodigo_administradora());
				tarifas_procedimientos.setId_plan(contratos.getId_plan());
				tarifas_procedimientos.setConsecutivo_manual(null);
				tarifas_procedimientos.setId_procedimiento(null);
				tarifas_procedimientosDao.eliminar(tarifas_procedimientos);

				Servicios_contratados servicios_contratados = new Servicios_contratados();
				servicios_contratados.setCodigo_empresa(contratos
						.getCodigo_empresa());
				servicios_contratados.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				servicios_contratados.setCodigo_administradora(contratos
						.getCodigo_administradora());
				servicios_contratados.setId_contrato(contratos.getId_plan());
				servicios_contratadosDao.eliminar(servicios_contratados);

				/* eliminamos manuales tarifarios */
				Manuales_tarifarios manuales_tarifarioTemp = new Manuales_tarifarios();
				manuales_tarifarioTemp.setCodigo_empresa(contratos
						.getCodigo_empresa());
				manuales_tarifarioTemp.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				manuales_tarifarioTemp.setCodigo_administradora(contratos
						.getCodigo_administradora());
				manuales_tarifarioTemp.setId_contrato(contratos.getId_plan());
				manuales_tarifariosDao
						.eliminarDeUnContrato(manuales_tarifarioTemp);

				// Descuentos
				Descuentos_laboratorios descuentos_laboratorios = new Descuentos_laboratorios();
				descuentos_laboratorios.setCodigo_empresa(contratos
						.getCodigo_empresa());
				descuentos_laboratorios.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				descuentos_laboratorios.setCodigo_administradora(contratos
						.getCodigo_administradora());
				descuentos_laboratorios.setId_contrato(contratos.getId_plan());
				descuentos_laboratoriosDao.eliminar(descuentos_laboratorios);

				Via_ingreso_contratadas via_ingreso_contratadas = new Via_ingreso_contratadas();
				via_ingreso_contratadas.setCodigo_empresa(contratos
						.getCodigo_empresa());
				via_ingreso_contratadas.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				via_ingreso_contratadas.setCodigo_administradora(contratos
						.getCodigo_administradora());
				via_ingreso_contratadas.setId_plan(contratos.getId_plan());
				via_ingreso_contratadasDao
						.eliminar_contrato(via_ingreso_contratadas);

			}

			Contratos_paquetes contratos_paquetes = new Contratos_paquetes();
			contratos_paquetes.setCodigo_empresa(contratos.getCodigo_empresa());
			contratos_paquetes.setCodigo_sucursal(contratos
					.getCodigo_sucursal());
			contratos_paquetes.setCodigo_administradora(contratos
					.getCodigo_administradora());
			contratos_paquetes.setId_plan(contratos.getId_plan());

			int cantidad = contratos_paquetesDao.eliminar(contratos_paquetes);

			log.debug("Cantidad de paquetes eliminados : " + cantidad);

			for (Paquetes_servicios paquetes_servicios : listado_paquetes) {
				contratos_paquetes = new Contratos_paquetes();
				contratos_paquetes.setCodigo_empresa(paquetes_servicios
						.getCodigo_empresa());
				contratos_paquetes.setCodigo_sucursal(paquetes_servicios
						.getCodigo_sucursal());
				contratos_paquetes.setCodigo_administradora(contratos
						.getCodigo_administradora());
				contratos_paquetes.setId_plan(contratos.getId_plan());
				contratos_paquetes.setId_paquete(paquetes_servicios.getId());
				contratos_paquetes.setCreacion_date(new Timestamp(new Date()
						.getTime()));
				contratos_paquetes.setCreacion_user(contratos.getUltimo_user());

				contratos_paquetesDao.crear(contratos_paquetes);

			}

			/*
			 * Agregamos los manuales tarifarios
			 */

			List<Map<String, Object>> listado_servicios_contratados = new ArrayList<Map<String, Object>>();

			int c_restricciones = 1;
			for (Manuales_tarifarios manuales_tarifarioTemp : manuales_tarifarios) {
				manuales_tarifarioTemp.setCodigo_empresa(contratos
						.getCodigo_empresa());
				manuales_tarifarioTemp.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				manuales_tarifarioTemp.setCodigo_administradora(contratos
						.getCodigo_administradora());
				manuales_tarifarioTemp.setId_contrato(contratos.getId_plan());
				manuales_tarifarioTemp.setCreacion_date(contratos
						.getCreacion_date());
				manuales_tarifarioTemp.setCreacion_user(contratos
						.getCreacion_user());
				manuales_tarifariosDao.crear(manuales_tarifarioTemp);

				/* guardamos los servicios contratados */
				/*
				 */
				List<Servicios_disponibles> listado_servicios_disponibles = manuales_tarifarioTemp
						.getListado_servicios_disponibles();
				for (Servicios_disponibles servicios_disponibles : listado_servicios_disponibles) {
					Servicios_contratados servicios_contratados = ServiciosDisponiblesUtils
							.generarServiciosContratadosPara(
									manuales_tarifarioTemp,
									servicios_disponibles);
					crearServiciosContratados(servicios_contratados);
					Map<String, Object> datos_servicios_contratados = new HashMap<String, Object>();
					datos_servicios_contratados.put("servicios_contratados",
							servicios_contratados);
					datos_servicios_contratados.put("consecutivo_manual",
							manuales_tarifarioTemp.getConsecutivo());
					listado_servicios_contratados
							.add(datos_servicios_contratados);

				}

				/* verificados si hay restriccionaes */
				// Generamos los procedimientos espceficios si los hay //
				if (manuales_tarifarioTemp.getRestriccion()) {
					for (Map<String, Object> map : manuales_tarifarioTemp
							.getLista_procedimiento_especificos()) {
						Restriccion_pcd restriccion_pcd = new Restriccion_pcd();
						restriccion_pcd.setCodigo_empresa(contratos
								.getCodigo_empresa());
						restriccion_pcd.setCodigo_sucursal(contratos
								.getCodigo_sucursal());
						restriccion_pcd.setCodigo_administradora(contratos
								.getCodigo_administradora());
						restriccion_pcd.setId_plan(contratos.getId_plan());
						restriccion_pcd
								.setConsecutivo((c_restricciones++) + "");
						restriccion_pcd.setCodigo_pcd((String) map
								.get("codigo_procedimiento"));
						restriccion_pcd.setCodigo_cups((String) map
								.get("codigo_cups"));
						restriccion_pcd.setRequiere_aut(((String) map
								.get("requiere_aut")).equals("S"));
						restriccion_pcd
								.setConsecutivo_manual(manuales_tarifarioTemp
										.getConsecutivo());
						restriccion_pcdDao.crear(restriccion_pcd);
					}
				}

				// Incluimos descuentos
				if (manuales_tarifarioTemp.getAplica_descuentos().equals("S")) {
					for (Map<String, Object> map : manuales_tarifarioTemp
							.getLista_descuentos()) {
						Descuentos_laboratorios descuentos_laboratorios = new Descuentos_laboratorios();
						descuentos_laboratorios.setCodigo_empresa(contratos
								.getCodigo_empresa());
						descuentos_laboratorios.setCodigo_sucursal(contratos
								.getCodigo_sucursal());
						descuentos_laboratorios
								.setCodigo_administradora(contratos
										.getCodigo_administradora());
						descuentos_laboratorios.setId_contrato(contratos
								.getId_plan());
						descuentos_laboratorios
								.setConsecutivo_manual(manuales_tarifarioTemp
										.getConsecutivo());
						descuentos_laboratorios
								.setCodigo_procedimiento((String) map
										.get("codigo_procedimiento"));
						descuentos_laboratorios
								.setPorcentaje_descuento((Double) map
										.get("descuento"));
						descuentos_laboratorios.setCreacion_date(new Timestamp(
								Calendar.getInstance().getTimeInMillis()));
						descuentos_laboratorios.setUltimo_update(new Timestamp(
								Calendar.getInstance().getTimeInMillis()));
						descuentos_laboratorios.setCreacion_user(contratos
								.getCreacion_user());
						descuentos_laboratorios.setUltimo_user(contratos
								.getCreacion_user());
						descuentos_laboratoriosDao
								.crear(descuentos_laboratorios);
					}
				}

				// Generamos las tarifas especiales //
				if (manuales_tarifarioTemp.getTarifa_especial().equals("S")) {
					for (Map<String, Object> map : manuales_tarifarioTemp
							.getLista_procedimiento_tarifas_especiales()) {

						Tarifas_procedimientos tarifas_procedimientos = new Tarifas_procedimientos();
						tarifas_procedimientos.setCodigo_empresa(contratos
								.getCodigo_empresa());
						tarifas_procedimientos.setCodigo_sucursal(contratos
								.getCodigo_sucursal());
						tarifas_procedimientos
								.setCodigo_administradora(contratos
										.getCodigo_administradora());
						tarifas_procedimientos.setId_plan(contratos
								.getId_plan());
						tarifas_procedimientos.setId_procedimiento((Long) map
								.get("id_procedimiento"));
						tarifas_procedimientos.setValor((Double) map
								.get("valor_particular"));
						tarifas_procedimientos.setCreacion_date(contratos
								.getCreacion_date());
						tarifas_procedimientos.setUltimo_update(contratos
								.getUltimo_update());
						tarifas_procedimientos.setCreacion_user(contratos
								.getCreacion_user());
						tarifas_procedimientos.setUltimo_user(contratos
								.getUltimo_user());
						tarifas_procedimientos.setDescripcion((String) map
								.get("nombre_procedimiento"));
						tarifas_procedimientos
								.setConsecutivo_manual(manuales_tarifarioTemp
										.getConsecutivo());
						if (tarifas_procedimientosDao
								.consultar(tarifas_procedimientos) != null) {
							tarifas_procedimientosDao
									.actualizar(tarifas_procedimientos);
						} else {
							tarifas_procedimientosDao
									.crear(tarifas_procedimientos);
						}
					}

					Contratos_procedimientos_ex contratos_procedimientos_ex = new Contratos_procedimientos_ex();
					contratos_procedimientos_ex.setCodigo_empresa(contratos
							.getCodigo_empresa());
					contratos_procedimientos_ex.setCodigo_sucursal(contratos
							.getCodigo_sucursal());
					contratos_procedimientos_ex
							.setCodigo_administradora(contratos
									.getCodigo_administradora());
					contratos_procedimientos_ex.setId_plan(contratos
							.getId_plan());

					contratos_procedimientos_exDao
							.eliminar(contratos_procedimientos_ex);

					List<Servicios_procedimientos> listado_procedimientos = manuales_tarifarioTemp
							.getListado_procedimientos_excluyentes();

					for (Servicios_procedimientos servicios_procedimientos : listado_procedimientos) {
						contratos_procedimientos_ex = new Contratos_procedimientos_ex();
						contratos_procedimientos_ex.setCodigo_empresa(contratos
								.getCodigo_empresa());
						contratos_procedimientos_ex
								.setCodigo_sucursal(contratos
										.getCodigo_sucursal());
						contratos_procedimientos_ex
								.setCodigo_administradora(contratos
										.getCodigo_administradora());
						contratos_procedimientos_ex.setId_plan(contratos
								.getId_plan());
						contratos_procedimientos_ex
								.setCodigo_unidad(servicios_procedimientos
										.getCodigo_unidad());
						contratos_procedimientos_ex
								.setCodigo_procedimiento(servicios_procedimientos
										.getCodigo_procedimiento());
						contratos_procedimientos_ex
								.setCreacion_date(new Timestamp(new Date()
										.getTime()));
						contratos_procedimientos_ex.setCreacion_user(contratos
								.getCreacion_user());
						contratos_procedimientos_ex
								.setCodigo_cups(servicios_procedimientos
										.getCodigo_cups());
						contratos_procedimientos_exDao
								.crear(contratos_procedimientos_ex);
					}

					Contratos_medicamentos contratos_medicamentos_aux = new Contratos_medicamentos();
					contratos_medicamentos_aux.setCodigo_empresa(contratos
							.getCodigo_empresa());
					contratos_medicamentos_aux.setCodigo_sucursal(contratos
							.getCodigo_sucursal());
					contratos_medicamentos_aux
							.setCodigo_administradora(contratos
									.getCodigo_administradora());
					contratos_medicamentos_aux.setId_plan(contratos
							.getId_plan());

					contratos_medicamentosDao
							.eliminar(contratos_medicamentos_aux);

					List<Contratos_medicamentos> listado_contratos_medicamentos = manuales_tarifarioTemp
							.getListado_medicamentos_contratados();

					for (Contratos_medicamentos contratos_medicamentos : listado_contratos_medicamentos) {
						contratos_medicamentos.setCodigo_empresa(contratos
								.getCodigo_empresa());
						contratos_medicamentos.setCodigo_sucursal(contratos
								.getCodigo_sucursal());
						contratos_medicamentos
								.setCodigo_administradora(contratos
										.getCodigo_administradora());
						contratos_medicamentos.setId_plan(contratos
								.getId_plan());
						contratos_medicamentos.setCreacion_date(new Timestamp(
								new Date().getTime()));
						contratos_medicamentos.setCreacion_user(contratos
								.getCreacion_user());
						contratos_medicamentosDao.crear(contratos_medicamentos);
					}
				}
			}

			if (!listado_servicios_contratados.isEmpty()) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", contratos.getCodigo_empresa());
				parametros.put("codigo_sucursal",
						contratos.getCodigo_sucursal());
				List<Servicios_via_ingreso> listado_servicios_vias = servicios_via_ingresoDao
						.listar(parametros);
				for (Servicios_via_ingreso servicios_via_ingreso : listado_servicios_vias) {
					Object[] informacion = ServiciosDisponiblesUtils
							.verificarServicio_via_ingreso(
									servicios_via_ingreso,
									listado_servicios_contratados);
					if (informacion != null) {
						Elemento elemento = (Elemento) informacion[0];
						Long consecutivo_manual = (Long) informacion[1];
						Via_ingreso_contratadas via_ingreso_contratadas = new Via_ingreso_contratadas();
						via_ingreso_contratadas.setCodigo_empresa(contratos
								.getCodigo_empresa());
						via_ingreso_contratadas.setCodigo_sucursal(contratos
								.getCodigo_sucursal());
						via_ingreso_contratadas
								.setCodigo_administradora(contratos
										.getCodigo_administradora());
						via_ingreso_contratadas.setId_plan(contratos
								.getId_plan());
						via_ingreso_contratadas.setVia_ingreso(elemento
								.getCodigo());
						via_ingreso_contratadas
								.setDescripcion_via_ingreso(elemento
										.getDescripcion());
						via_ingreso_contratadas.setUltimo_user(contratos
								.getUltimo_user());
						via_ingreso_contratadas.setUltimo_update(contratos
								.getUltimo_update());
						via_ingreso_contratadas
								.setConsecutivo_manual(consecutivo_manual);
						if (via_ingreso_contratadasDao
								.consultar_informacion(via_ingreso_contratadas) == null) {
							via_ingreso_contratadasDao
									.crear(via_ingreso_contratadas);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite saber si este contrato, contiene servicios de pyp.
	 * 
	 * @author Luis Miguel
	 * */
	public boolean prestaServiciosPyp(Map<String, Object> params) {
		return contratosDao.prestaServiciosPyp(params);
	}

	/**
	 * Este metodo es para verificar si se repite un servicio
	 * 
	 * @author Luis Miguel
	 * */
	private void crearServiciosContratados(
			Servicios_contratados servicios_contratados) {
		if (servicios_contratadosDao.consultar(servicios_contratados) != null) {
			servicios_contratadosDao.actualizar(servicios_contratados);
		} else {
			servicios_contratadosDao.crear(servicios_contratados);
		}
	}

	@SuppressWarnings("unchecked")
	public void guardarContratoMultiple(Map<String, Object> map) {
		List<Map<String, Object>> administradoras = (List<Map<String, Object>>) map
				.get("administradoras");
		Contratos contratos = (Contratos) map.get("contratos");
		// List<Manuales_tarifarios> manuales_tarifarios =
		// (List<Manuales_tarifarios>) map.get("manuales_tarifarios");
		// log.info("Guardar contrato de forma multiple");
		/* Creamos el contrato para todas estas administradoras */
		for (Map<String, Object> administradora_map : administradoras) {
			Administradora administradora = (Administradora) administradora_map
					.get(CargarMultipleAdministradoraAction.ADMINISTRADORA);
			String codigo_contrato = (String) administradora_map
					.get(CargarMultipleAdministradoraAction.CODIGO_CONTRATO);
			// List<Paquetes_servicios> listado_paquetes =
			// (List<Paquetes_servicios>) map
			// .get("listado_paquetes");
			//
			// //log.info("Administradora: " + administradora.getCodigo());
			contratos.setCodigo_administradora(administradora.getCodigo());
			contratos.setNro_contrato(codigo_contrato);
			guardarContrato(map);
		}
	}

	@SuppressWarnings("unchecked")
	public void guardarAlimentarContrato(Map<String, Object> datos) {
		try {
			List<Contratos> listado_contratos = (List<Contratos>) datos
					.get("listado_contratos");
			List<Manuales_tarifarios> manuales_tarifarios = (List<Manuales_tarifarios>) datos
					.get("manuales_tarifarios");

			List<Paquetes_servicios> listado_paquetes = (List<Paquetes_servicios>) datos
					.get("listado_paquetes");
			for (Contratos contratos : listado_contratos) {
				// Eliminamos los procedimientos especificos //
				Restriccion_pcd restriccion_pcd2 = new Restriccion_pcd();
				restriccion_pcd2.setCodigo_empresa(contratos
						.getCodigo_empresa());
				restriccion_pcd2.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				restriccion_pcd2.setCodigo_administradora(contratos
						.getCodigo_administradora());
				restriccion_pcd2.setId_plan(contratos.getId_plan());
				restriccion_pcdDao.eliminar(restriccion_pcd2);

				// Eliminamos las tarifas especiales SOAT//
				Tarifas_procedimientos tarifas_procedimientos2 = new Tarifas_procedimientos();
				tarifas_procedimientos2.setCodigo_empresa(contratos
						.getCodigo_empresa());
				tarifas_procedimientos2.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				tarifas_procedimientos2.setCodigo_administradora(contratos
						.getCodigo_administradora());
				tarifas_procedimientos2.setId_plan(contratos.getId_plan());
				tarifas_procedimientosDao.eliminar(tarifas_procedimientos2);

				Servicios_contratados servicios_contratados2 = new Servicios_contratados();
				servicios_contratados2.setCodigo_empresa(contratos
						.getCodigo_empresa());
				servicios_contratados2.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				servicios_contratados2.setCodigo_administradora(contratos
						.getCodigo_administradora());
				servicios_contratados2.setId_contrato(contratos.getId_plan());
				servicios_contratadosDao.eliminar(servicios_contratados2);

				/* eliminamos manuales tarifarios */
				Manuales_tarifarios manuales_tarifarioTemp2 = new Manuales_tarifarios();
				manuales_tarifarioTemp2.setCodigo_empresa(contratos
						.getCodigo_empresa());
				manuales_tarifarioTemp2.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				manuales_tarifarioTemp2.setCodigo_administradora(contratos
						.getCodigo_administradora());
				manuales_tarifarioTemp2.setId_contrato(contratos.getId_plan());
				manuales_tarifariosDao
						.eliminarDeUnContrato(manuales_tarifarioTemp2);

				// Descuentos
				Descuentos_laboratorios descuentos_laboratorios2 = new Descuentos_laboratorios();
				descuentos_laboratorios2.setCodigo_empresa(contratos
						.getCodigo_empresa());
				descuentos_laboratorios2.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				descuentos_laboratorios2.setCodigo_administradora(contratos
						.getCodigo_administradora());
				descuentos_laboratorios2.setId_contrato(contratos.getId_plan());
				descuentos_laboratoriosDao.eliminar(descuentos_laboratorios2);

				Contratos_paquetes contratos_paquetes = new Contratos_paquetes();
				contratos_paquetes.setCodigo_empresa(contratos
						.getCodigo_empresa());
				contratos_paquetes.setCodigo_sucursal(contratos
						.getCodigo_sucursal());
				contratos_paquetes.setCodigo_administradora(contratos
						.getCodigo_administradora());
				contratos_paquetes.setId_plan(contratos.getId_plan());

				int cantidad = contratos_paquetesDao
						.eliminar(contratos_paquetes);

				log.debug("Cantidad de paquetes eliminados : " + cantidad);

				for (Paquetes_servicios paquetes_servicios : listado_paquetes) {
					contratos_paquetes = new Contratos_paquetes();
					contratos_paquetes.setCodigo_empresa(paquetes_servicios
							.getCodigo_empresa());
					contratos_paquetes.setCodigo_sucursal(paquetes_servicios
							.getCodigo_sucursal());
					contratos_paquetes.setCodigo_administradora(contratos
							.getCodigo_administradora());
					contratos_paquetes.setId_plan(contratos.getId_plan());
					contratos_paquetes
							.setId_paquete(paquetes_servicios.getId());

					contratos_paquetesDao.crear(contratos_paquetes);

				}

				/*
				 * Agregamos los manuales tarifarios
				 */
				int c_restricciones = 1;
				for (Manuales_tarifarios manuales_tarifarioTemp : manuales_tarifarios) {
					manuales_tarifarioTemp.setCodigo_empresa(contratos
							.getCodigo_empresa());
					manuales_tarifarioTemp.setCodigo_sucursal(contratos
							.getCodigo_sucursal());
					manuales_tarifarioTemp.setCodigo_administradora(contratos
							.getCodigo_administradora());
					manuales_tarifarioTemp.setId_contrato(contratos
							.getId_plan());
					manuales_tarifarioTemp.setCreacion_date(contratos
							.getCreacion_date());
					manuales_tarifarioTemp.setCreacion_user(contratos
							.getCreacion_user());
					manuales_tarifariosDao.crear(manuales_tarifarioTemp);

					/* guardamos los servicios contratados */
					/*
				 */
					List<Servicios_disponibles> listado_servicios_disponibles = manuales_tarifarioTemp
							.getListado_servicios_disponibles();
					for (Servicios_disponibles servicios_disponibles : listado_servicios_disponibles) {
						Servicios_contratados servicios_contratados = ServiciosDisponiblesUtils
								.generarServiciosContratadosPara(
										manuales_tarifarioTemp,
										servicios_disponibles);
						crearServiciosContratados(servicios_contratados);

					}

					/* verificados si hay restriccionaes */
					// Generamos los procedimientos espceficios si los hay //
					if (manuales_tarifarioTemp.getRestriccion()) {
						for (Map<String, Object> map : manuales_tarifarioTemp
								.getLista_procedimiento_especificos()) {
							Restriccion_pcd restriccion_pcd = new Restriccion_pcd();
							restriccion_pcd.setCodigo_empresa(contratos
									.getCodigo_empresa());
							restriccion_pcd.setCodigo_sucursal(contratos
									.getCodigo_sucursal());
							restriccion_pcd.setCodigo_administradora(contratos
									.getCodigo_administradora());
							restriccion_pcd.setId_plan(contratos.getId_plan());
							restriccion_pcd.setConsecutivo((c_restricciones++)
									+ "");
							restriccion_pcd.setCodigo_pcd((String) map
									.get("codigo_procedimiento"));
							restriccion_pcd.setCodigo_cups((String) map
									.get("codigo_cups"));
							restriccion_pcd.setRequiere_aut(((String) map
									.get("requiere_aut")).equals("S"));
							restriccion_pcd
									.setConsecutivo_manual(manuales_tarifarioTemp
											.getConsecutivo());
							restriccion_pcdDao.crear(restriccion_pcd);
						}
					}

					// Incluimos descuentos
					if (manuales_tarifarioTemp.getAplica_descuentos().equals(
							"S")) {
						for (Map<String, Object> map : manuales_tarifarioTemp
								.getLista_descuentos()) {
							Descuentos_laboratorios descuentos_laboratorios = new Descuentos_laboratorios();
							descuentos_laboratorios.setCodigo_empresa(contratos
									.getCodigo_empresa());
							descuentos_laboratorios
									.setCodigo_sucursal(contratos
											.getCodigo_sucursal());
							descuentos_laboratorios
									.setCodigo_administradora(contratos
											.getCodigo_administradora());
							descuentos_laboratorios.setId_contrato(contratos
									.getId_plan());
							descuentos_laboratorios
									.setConsecutivo_manual(manuales_tarifarioTemp
											.getConsecutivo());
							descuentos_laboratorios
									.setCodigo_procedimiento((String) map
											.get("codigo_procedimiento"));
							descuentos_laboratorios
									.setPorcentaje_descuento((Double) map
											.get("descuento"));
							descuentos_laboratorios
									.setCreacion_date(new Timestamp(Calendar
											.getInstance().getTimeInMillis()));
							descuentos_laboratorios
									.setUltimo_update(new Timestamp(Calendar
											.getInstance().getTimeInMillis()));
							descuentos_laboratorios.setCreacion_user(contratos
									.getCreacion_user());
							descuentos_laboratorios.setUltimo_user(contratos
									.getCreacion_user());
							descuentos_laboratoriosDao
									.crear(descuentos_laboratorios);
						}
					}

					// Generamos las tarifas especiales //
					if (manuales_tarifarioTemp.getTarifa_especial().equals("S")) {
						for (Map<String, Object> map : manuales_tarifarioTemp
								.getLista_procedimiento_tarifas_especiales()) {

							Tarifas_procedimientos tarifas_procedimientos = new Tarifas_procedimientos();
							tarifas_procedimientos.setCodigo_empresa(contratos
									.getCodigo_empresa());
							tarifas_procedimientos.setCodigo_sucursal(contratos
									.getCodigo_sucursal());
							tarifas_procedimientos
									.setCodigo_administradora(contratos
											.getCodigo_administradora());
							tarifas_procedimientos.setId_plan(contratos
									.getId_plan());
							tarifas_procedimientos
									.setId_procedimiento((Long) map
											.get("id_procedimiento"));
							tarifas_procedimientos.setValor((Double) map
									.get("valor_particular"));
							tarifas_procedimientos.setCreacion_date(contratos
									.getCreacion_date());
							tarifas_procedimientos.setUltimo_update(contratos
									.getUltimo_update());
							tarifas_procedimientos.setCreacion_user(contratos
									.getCreacion_user());
							tarifas_procedimientos.setUltimo_user(contratos
									.getUltimo_user());
							tarifas_procedimientos.setDescripcion((String) map
									.get("nombre_procedimiento"));
							tarifas_procedimientos
									.setConsecutivo_manual(manuales_tarifarioTemp
											.getConsecutivo());
							tarifas_procedimientosDao
									.crear(tarifas_procedimientos);

						}

						Contratos_procedimientos_ex contratos_procedimientos_ex = new Contratos_procedimientos_ex();
						contratos_procedimientos_ex.setCodigo_empresa(contratos
								.getCodigo_empresa());
						contratos_procedimientos_ex
								.setCodigo_sucursal(contratos
										.getCodigo_sucursal());
						contratos_procedimientos_ex
								.setCodigo_administradora(contratos
										.getCodigo_administradora());
						contratos_procedimientos_ex.setId_plan(contratos
								.getId_plan());

						contratos_procedimientos_exDao
								.eliminar(contratos_procedimientos_ex);

						List<Servicios_procedimientos> listado_procedimientos = manuales_tarifarioTemp
								.getListado_procedimientos_excluyentes();

						for (Servicios_procedimientos servicios_procedimientos : listado_procedimientos) {
							contratos_procedimientos_ex = new Contratos_procedimientos_ex();
							contratos_procedimientos_ex
									.setCodigo_empresa(contratos
											.getCodigo_empresa());
							contratos_procedimientos_ex
									.setCodigo_sucursal(contratos
											.getCodigo_sucursal());
							contratos_procedimientos_ex
									.setCodigo_administradora(contratos
											.getCodigo_administradora());
							contratos_procedimientos_ex.setId_plan(contratos
									.getId_plan());
							contratos_procedimientos_ex
									.setCodigo_unidad(servicios_procedimientos
											.getCodigo_unidad());
							contratos_procedimientos_ex
									.setCodigo_procedimiento(servicios_procedimientos
											.getCodigo_procedimiento());
							contratos_procedimientos_ex
									.setCreacion_date(new Timestamp(new Date()
											.getTime()));
							contratos_procedimientos_ex
									.setCreacion_user(contratos
											.getCreacion_user());
							contratos_procedimientos_ex
									.setCodigo_cups(servicios_procedimientos
											.getCodigo_cups());
							contratos_procedimientos_exDao
									.crear(contratos_procedimientos_ex);
						}

						Contratos_medicamentos contratos_medicamentos_aux = new Contratos_medicamentos();
						contratos_medicamentos_aux.setCodigo_empresa(contratos
								.getCodigo_empresa());
						contratos_medicamentos_aux.setCodigo_sucursal(contratos
								.getCodigo_sucursal());
						contratos_medicamentos_aux
								.setCodigo_administradora(contratos
										.getCodigo_administradora());
						contratos_medicamentos_aux.setId_plan(contratos
								.getId_plan());

						contratos_medicamentosDao
								.eliminar(contratos_medicamentos_aux);

						List<Contratos_medicamentos> listado_contratos_medicamentos = manuales_tarifarioTemp
								.getListado_medicamentos_contratados();

						for (Contratos_medicamentos contratos_medicamentos : listado_contratos_medicamentos) {
							contratos_medicamentos.setCodigo_empresa(contratos
									.getCodigo_empresa());
							contratos_medicamentos.setCodigo_sucursal(contratos
									.getCodigo_sucursal());
							contratos_medicamentos
									.setCodigo_administradora(contratos
											.getCodigo_administradora());
							contratos_medicamentos.setId_plan(contratos
									.getId_plan());
							contratos_medicamentos
									.setCreacion_date(new Timestamp(new Date()
											.getTime()));
							contratos_medicamentos.setCreacion_user(contratos
									.getCreacion_user());
							contratos_medicamentosDao
									.crear(contratos_medicamentos);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer totalResultados(Map<String, Object> parameters) {
		try {
			return contratosDao.totalResultados(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
