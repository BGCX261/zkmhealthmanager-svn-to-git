/*
 * Alteracion_jovenServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Alteracion_joven;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Presultados_paraclinicos;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Alteracion_jovenDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.PrestadoresDao;
import healthmanager.modelo.dao.Presultados_paraclinicosDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

@Service("alteracion_jovenService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Alteracion_jovenService implements Serializable{


	@Autowired
	private Alteracion_jovenDao alteracion_jovenDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Presultados_paraclinicosDao presultados_paraclinicosDao;
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
	private Agudeza_visualService agudeza_visualService;

	@Autowired
	private Remision_internaService remision_internaService;

	@Autowired
	private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;

	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;

	@Autowired
	private GeneralExtraService generalExtraService;
	// manuel
        
        @Autowired
        private PrestadoresDao prestadoresDao;

	@Autowired
	private CitasDao citasDao;

	public boolean cobrar_agudeza;

	public void guardarDatos(Map<String, Object> datos) {
		try {
			Alteracion_joven alteracion_joven = (Alteracion_joven) datos
					.get("historia_clinica");
			Admision admision = (Admision) datos.get("admision");
			Citas cita = (Citas) datos.get("cita_seleccionada");

			if (admision == null) {
				throw new Exception("No hay admision que actualizar");
			}

			Integer anio = (Integer) datos.get("anio");

			List<Presultados_paraclinicos> listado_paraclinicos = (List<Presultados_paraclinicos>) datos
					.get("listado_paraclinicos");

			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);

			String accion = (String) datos.get("accion");
			Map<String, Object> mapRecetas = (Map<String, Object>) datos
					.get("receta_medica");
			Map<String, Object> mapOrdenServicio = (Map<String, Object>) datos
					.get("orden_servicio");

			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");

			Agudeza_visual agudeza_visual = (Agudeza_visual) datos
					.get("agudeza_visual");

			agudeza_visual.setCodigo_historia(alteracion_joven
					.getCodigo_historia());
			agudeza_visual.setIdentificacion(admision.getPaciente()
					.getNro_identificacion());

			cobrar_agudeza = (Boolean) datos.get("cobrar_agudeza");

			if (cobrar_agudeza) {
				Map<String, Object> mapServicios = Utilidades.getProcedimiento(
						manuales_tarifarios, new Long(
								IConstantes.PROCEDIMIENTO_AGUDEZA_VISUAL),
						ServiciosDisponiblesUtils.getServiceLocator());
				ServiciosDisponiblesUtils.generarDatosProcedimientos(admision,
						IConstantes.PROCEDIMIENTO_AGUDEZA_VISUAL, mapServicios,
						manuales_tarifarios);
			}

			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna.setCodigo_historia(alteracion_joven
					.getCodigo_historia());
			remision_interna.setFecha_inicio(alteracion_joven
					.getFecha_inicial());

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(admision.getCodigo_empresa());
			prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
			prestadores.setNro_identificacion(admision.getCodigo_medico());
			prestadores = prestadoresDao.consultar(prestadores);

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(alteracion_joven
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(alteracion_joven
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(alteracion_joven
					.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(alteracion_joven.getCreacion_user());

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				alteracion_joven.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				alteracion_jovenDao.crear(alteracion_joven);
				remision_interna.setCodigo_historia(alteracion_joven
						.getCodigo_historia());
				agudeza_visual.setCodigo_historia(alteracion_joven
						.getCodigo_historia());

			} else {

				if (generalExtraService.consultar(historia_clinica) != null) {
					if (consultar(alteracion_joven) == null)
						throw new Exception(
								"El dato que desea actualizar no existe en la base de datos");
					alteracion_jovenDao.actualizar(alteracion_joven);
				}

			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
			}

			if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
				if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
					if (agudeza_visualService.consultar(agudeza_visual) == null) {
						agudeza_visualService.crear(agudeza_visual);
					} else {
						agudeza_visualService.actualizar(agudeza_visual);
					}

				}
			}

			for (Presultados_paraclinicos presultados_paraclinicos : listado_paraclinicos) {
				String consecutivo = consecutivoService.crearConsecutivo(
						presultados_paraclinicos.getCodigo_empresa(),
						presultados_paraclinicos.getCodigo_sucursal(),
						IConstantes.CONS_PRESULTADOS_PARACLINICOS);
				String consecutivo_p = consecutivoService.getZeroFill(
						consecutivo, 10);
				presultados_paraclinicos.setConsecutivo(consecutivo_p);
				if (presultados_paraclinicosDao
						.consultar(presultados_paraclinicos) != null) {
					throw new HealthmanagerException(
							"El resultado paraclinico ya se encuentra en la base de datos");
				}
				presultados_paraclinicosDao.crear(presultados_paraclinicos);
				consecutivoService.actualizarConsecutivo(
						presultados_paraclinicos.getCodigo_empresa(),
						presultados_paraclinicos.getCodigo_sucursal(),
						IConstantes.CONS_PRESULTADOS_PARACLINICOS,
						consecutivo_p);
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

			if (cita != null) {
				cita.setEstado("2");
				citasDao.actualizar(cita);
			}
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

			/* generamos la consulta */
			crearConsulta(alteracion_joven, admision, impresion_diagnostica);

			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					alteracion_joven.getCodigo_historia(), datos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					alteracion_joven.getCreacion_user());

			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) datos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad.setNro_historia(alteracion_joven
						.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							alteracion_joven.getCodigo_empresa(),
							alteracion_joven.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							alteracion_joven.getCodigo_empresa(),
							alteracion_joven.getCodigo_sucursal(),
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

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

		Alteracion_joven alteracion_joven = (Alteracion_joven) historiasConsultas;
		Map map = new HashMap();
		map.put("codigo_empresa", alteracion_joven.getCodigo_empresa());
		map.put("codigo_sucursal", alteracion_joven.getCodigo_sucursal());
		map.put("nro_identificacion", alteracion_joven.getIdentificacion());
		map.put("nro_ingreso", alteracion_joven.getNro_ingreso());
		map.put("codigo_prestador", alteracion_joven.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", alteracion_joven.getCreacion_date());
		map.put("ultimo_update", alteracion_joven.getUltimo_update());
		map.put("creacion_user", alteracion_joven.getCreacion_user());
		map.put("ultimo_user", alteracion_joven.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}

	public void crear(Alteracion_joven alteracion_joven) {
		try {
			if (consultar(alteracion_joven) != null) {
				throw new HealthmanagerException(
						"Alteracion_joven ya se encuentra en la base de datos");
			}
			alteracion_jovenDao.crear(alteracion_joven);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Alteracion_joven alteracion_joven) {
		try {
			return alteracion_jovenDao.actualizar(alteracion_joven);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Alteracion_joven consultar(Alteracion_joven alteracion_joven) {
		try {
			return alteracion_jovenDao.consultar(alteracion_joven);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Alteracion_joven consultar_historia(Alteracion_joven alteracion_joven) {
		try {
			return alteracion_jovenDao.consultar_historia(alteracion_joven);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Alteracion_joven alteracion_joven) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(alteracion_joven.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(alteracion_joven
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(alteracion_joven
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Alteracion_joven> listar(Map<String, Object> parameter) {
		try {
			return alteracion_jovenDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parameters) {
		try {
			return alteracion_jovenDao.total_registros(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.alteracion_jovenDao.existe(parameters);
	}

}
