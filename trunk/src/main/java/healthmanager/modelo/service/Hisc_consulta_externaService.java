/*
 * Hisc_consulta_externaServiceImpl.java
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
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Antecedentes_personales;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Hisc_consulta_externa;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.bean.Revision_sistema;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.Antecedentes_personalesDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.Hisc_consulta_externaDao;
import healthmanager.modelo.dao.Revision_sistemaDao;
import healthmanager.modelo.dao.SigvitalesDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.util.ServiciosDisponiblesUtils;

@Service("hisc_consulta_externaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Hisc_consulta_externaService implements Serializable{

	// private static Logger log = Logger
	// .getLogger(Hisc_consulta_externaService.class);

	@Autowired
	private Hisc_consulta_externaDao hisc_consulta_externaDao;
	@Autowired
	private Antecedentes_personalesDao antecedentes_personalesDao;
	@Autowired
	private Revision_sistemaDao revision_sistemaDao;
	@Autowired
	private SigvitalesDao sigvitalesDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private AdmisionDao admisionDao;

	@Autowired
	private Receta_ripsService receta_ripsService;
	@Autowired
	private Orden_servicioService orden_servicioService;

	@Autowired
	private Impresion_diagnosticaService impresion_diagnosticaService;
	@Autowired
	private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;
	@Autowired
	private Remision_internaService remision_internaService;
	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;
	@Autowired
	private CitasDao citasDao;
	@Autowired
	private GeneralExtraService generalExtraService;
	@Autowired
	private Orden_autorizacionService orden_autorizacionService;

	public void crear(Hisc_consulta_externa hisc_consulta_externa) {
		try {
			if (consultar(hisc_consulta_externa) != null) {
				throw new HealthmanagerException(
						"Hisc_consulta_externa ya se encuentra en la base de datos");
			}
			hisc_consulta_externaDao.crear(hisc_consulta_externa);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void guardarDatos(Map<String, Object> datos) {
		try {
			Hisc_consulta_externa hisc_consulta_externa = (Hisc_consulta_externa) datos
					.get("hisc_consulta_externa");
			Admision admision = (Admision) datos.get("admision");
			if (admision == null) {
				throw new Exception("No hay admision que actualizar");
			}
			Sigvitales sigvitales = (Sigvitales) datos.get("sigvitales");
			List<Antecedentes_personales> listadoAntecedentes = (List<Antecedentes_personales>) datos
					.get("listadoAntecedentes");
			List<Revision_sistema> listadoRevisiones = (List<Revision_sistema>) datos
					.get("listadoRevisiones");
			String accion = (String) datos.get("accion");
			Map<String, Object> mapRecetas = (Map<String, Object>) datos
					.get("receta_medica");
			Map<String, Object> mapOrdenServicio = (Map<String, Object>) datos
					.get("orden_servicio");

			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");
			Citas cita = (Citas) datos.get("cita_seleccionada");
			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna.setCodigo_historia(hisc_consulta_externa
					.getCodigo_historia());
			remision_interna.setFecha_inicio(hisc_consulta_externa
					.getFecha_ingreso());

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(hisc_consulta_externa
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_consulta_externa
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(hisc_consulta_externa
					.getFecha_ingreso());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(hisc_consulta_externa
					.getCreacion_user());

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				hisc_consulta_externa.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				hisc_consulta_externaDao.crear(hisc_consulta_externa);
				remision_interna.setCodigo_historia(hisc_consulta_externa
						.getCodigo_historia());
			} else {

				if (generalExtraService.consultar(historia_clinica) != null) {
					if (consultar(hisc_consulta_externa) == null)
						throw new Exception(
								"El dato que desea actualizar no existe en la base de datos");
					hisc_consulta_externaDao.actualizar(hisc_consulta_externa);
				}

			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
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

			sigvitales.setCodigo_historia(hisc_consulta_externa
					.getCodigo_historia());
			sigvitales.setNro_identificacion(hisc_consulta_externa
					.getNro_identificacion());
			sigvitales.setNro_identificacion(admision.getNro_identificacion());
			sigvitales.setCreacion_date(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			if (sigvitalesDao.consultar(sigvitales) != null) {
				sigvitalesDao.actualizar(sigvitales);
			} else {
				sigvitalesDao.crear(sigvitales);
			}

			Antecedentes_personales antecedentesAux = new Antecedentes_personales();
			antecedentesAux.setCodigo_empresa(hisc_consulta_externa
					.getCodigo_empresa());
			antecedentesAux.setCodigo_sucursal(hisc_consulta_externa
					.getCodigo_sucursal());
			antecedentesAux.setCodigo_historia(hisc_consulta_externa
					.getCodigo_historia());
			antecedentesAux.setNro_identificacion(hisc_consulta_externa
					.getNro_identificacion());
			antecedentes_personalesDao.eliminar(antecedentesAux);

			for (Antecedentes_personales antePersonales : listadoAntecedentes) {
				antePersonales.setCodigo_historia(hisc_consulta_externa
						.getCodigo_historia());
				antePersonales.setNro_identificacion(hisc_consulta_externa
						.getNro_identificacion());
				antecedentes_personalesDao.crear(antePersonales);
			}

			Revision_sistema revision_sistemaAux = new Revision_sistema();
			revision_sistemaAux.setCodigo_empresa(hisc_consulta_externa
					.getCodigo_empresa());
			revision_sistemaAux.setCodigo_sucursal(hisc_consulta_externa
					.getCodigo_sucursal());
			revision_sistemaAux.setCodigo_historia(hisc_consulta_externa
					.getCodigo_historia());
			revision_sistemaAux.setNro_identificacion(hisc_consulta_externa
					.getNro_identificacion());
			revision_sistemaDao.eliminar(revision_sistemaAux);

			for (Revision_sistema revision_sistema : listadoRevisiones) {
				revision_sistema.setCodigo_historia(hisc_consulta_externa
						.getCodigo_historia());
				revision_sistema.setNro_identificacion(hisc_consulta_externa
						.getNro_identificacion());
				revision_sistemaDao.crear(revision_sistema);
			}

			if (cita != null) {
				cita.setEstado("2");
				citasDao.actualizar(cita);
			}
			admision.setCodigo_medico(historia_clinica.getPrestador());
			admision.setAtendida(true);
			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());

			// Jose Ojeda
			admision.setFecha_atencion(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));

			admisionDao.actualizar(admision);

			// Creamos autorizacion
			Map<String, Object> mapDatos = (Map<String, Object>) datos
					.get("autorizacion");
			if (mapDatos != null) {
				orden_autorizacionService.guardarOrden(mapDatos,
						impresion_diagnostica, admision);
			}

			/* generamos la consulta */
			crearConsulta(hisc_consulta_externa, admision,
					impresion_diagnostica);

			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					hisc_consulta_externa.getCodigo_historia(), datos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					hisc_consulta_externa.getCreacion_user());

			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) datos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad.setNro_historia(hisc_consulta_externa
						.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							hisc_consulta_externa.getCodigo_empresa(),
							hisc_consulta_externa.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							hisc_consulta_externa.getCodigo_empresa(),
							hisc_consulta_externa.getCodigo_sucursal(),
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

		Hisc_consulta_externa hisc_consulta_externa = (Hisc_consulta_externa) historiasConsultas;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", hisc_consulta_externa.getCodigo_empresa());
		map.put("codigo_sucursal", hisc_consulta_externa.getCodigo_sucursal());
		map.put("nro_identificacion",
				hisc_consulta_externa.getNro_identificacion());
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
		map.put("admision", admision);

		ServiciosDisponiblesUtils.generarConsulta(map);
	}

	public int actualizar(Hisc_consulta_externa hisc_consulta_externa) {
		try {
			return hisc_consulta_externaDao.actualizar(hisc_consulta_externa);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Hisc_consulta_externa consultar(
			Hisc_consulta_externa hisc_consulta_externa) {
		try {
			return hisc_consulta_externaDao.consultar(hisc_consulta_externa);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Hisc_consulta_externa hisc_consulta_externa,
			String delete_user) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(hisc_consulta_externa.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_consulta_externa
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(hisc_consulta_externa
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Hisc_consulta_externa> listar(Map<String, Object> parameter) {
		try {
			return hisc_consulta_externaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parameters) {

		try {
			return hisc_consulta_externaDao.total_registros(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}

	}

	public boolean existe(Map<String, Object> parameters) {
		return this.hisc_consulta_externaDao.existe(parameters);
	}

}
