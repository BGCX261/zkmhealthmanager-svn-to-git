/*
 * PsicologiaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Psicologia;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.PsicologiaDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.util.ServiciosDisponiblesUtils;

@Service("psicologiaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PsicologiaService implements Serializable{

	@Autowired
	private PsicologiaDao psicologiaDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private AdmisionDao admisionDao;
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

	public Psicologia guardar(Map<String, Object> datos) {
		try {

			Psicologia psicologia = (Psicologia) datos.get("psicologia");
			String accion = (String) datos.get("accion");
			Admision admision = (Admision) datos.get("admision");
			Citas cita = (Citas) datos.get("cita_seleccionada");

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(psicologia.getCodigo_empresa());
			historia_clinica
					.setCodigo_sucursal(psicologia.getCodigo_sucursal());
			historia_clinica.setFecha_historia(psicologia.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(psicologia.getCreacion_user());

			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");

			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna
					.setCodigo_historia(psicologia.getCodigo_historia());
			remision_interna.setFecha_inicio(psicologia.getFecha_inicial());

			//log.info("accion" + accion);
			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				psicologia.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				psicologiaDao.crear(psicologia);

				remision_interna.setCodigo_historia(psicologia
						.getCodigo_historia());

			} else {

				if (consultar(psicologia) == null)
					throw new Exception(
							"El dato que desea actualizar no existe en la base de datos");

				psicologiaDao.actualizar(psicologia);
			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
			}

			admision.setCodigo_medico(historia_clinica.getPrestador());
			admision.setAtendida(true);

			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) datos
					.get("impresion_diagnostica");
			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());
			admisionDao.actualizar(admision);
			if (cita != null) {
				cita.setEstado("2");
				citasDao.actualizar(cita);
			}
			crearConsulta(psicologia, admision, impresion_diagnostica);
			FichaEpidemiologiaModel
					.guardarDatosImpresionDiagnostica(
							psicologia.getCodigo_historia(), datos,
							ficha_epidemiologia_nnService,
							impresion_diagnosticaService,
							psicologia.getCreacion_user());

			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) datos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad.setNro_historia(psicologia.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							psicologia.getCodigo_empresa(),
							psicologia.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							psicologia.getCodigo_empresa(),
							psicologia.getCodigo_sucursal(),
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

			return psicologia;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Psicologia psicologia) {
		try {
			if (consultar(psicologia) != null) {
				throw new HealthmanagerException(
						"Psicologia ya se encuentra en la base de datos");
			}
			psicologiaDao.crear(psicologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Psicologia psicologia) {
		try {
			return psicologiaDao.actualizar(psicologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Psicologia consultar(Psicologia psicologia) {
		try {
			return psicologiaDao.consultar(psicologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Psicologia consultar_historia(Psicologia psicologia) {
		try {
			return psicologiaDao.consultar_historia(psicologia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Psicologia psicologia) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(psicologia.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(psicologia
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(psicologia
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Psicologia> listar(Map<String, Object> parameter) {
		try {
			return psicologiaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parameters) {
		try {
			return psicologiaDao.total_registros(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

		Psicologia psicologia = (Psicologia) historiasConsultas;
		Map map = new HashMap();
		map.put("codigo_empresa", psicologia.getCodigo_empresa());
		map.put("codigo_sucursal", psicologia.getCodigo_sucursal());
		map.put("nro_identificacion", psicologia.getIdentificacion());
		map.put("nro_ingreso", psicologia.getNro_ingreso());
		map.put("codigo_prestador", psicologia.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", psicologia.getCreacion_date());
		map.put("ultimo_update", psicologia.getUltimo_update());
		map.put("creacion_user", psicologia.getCreacion_user());
		map.put("ultimo_user", psicologia.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}

}
