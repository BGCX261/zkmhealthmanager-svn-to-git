/*
 * Hisc_fisioterapiaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Antecedentes_personales;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Hisc_fisioterapia;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Antecedentes_personalesDao;
import healthmanager.modelo.dao.Hisc_fisioterapiaDao;

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

import com.framework.util.ServiciosDisponiblesUtils;

@Service("hisc_fisioterapiaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Hisc_fisioterapiaService implements Serializable{

	@Autowired
	private Hisc_fisioterapiaDao hisc_fisioterapiaDao;
	@Autowired
	private Antecedentes_personalesDao antecedentes_personalesDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private Receta_ripsService receta_ripsService;
	@Autowired
	private Impresion_diagnosticaService impresion_diagnosticaService;
	@Autowired
	private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;
	@Autowired
	private Remision_internaService remision_internaService;
	@Autowired
	private GeneralExtraService generalExtraService;

	public void crear(Hisc_fisioterapia hisc_fisioterapia) {
		try {
			if (consultar(hisc_fisioterapia) != null) {
				throw new HealthmanagerException(
						"Hisc_fisioterapia ya se encuentra en la base de datos");
			}
			hisc_fisioterapiaDao.crear(hisc_fisioterapia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Hisc_fisioterapia hisc_fisioterapia) {
		try {
			return hisc_fisioterapiaDao.actualizar(hisc_fisioterapia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Hisc_fisioterapia consultar(Hisc_fisioterapia hisc_fisioterapia) {
		try {
			return hisc_fisioterapiaDao.consultar(hisc_fisioterapia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Hisc_fisioterapia hisc_fisioterapia) {
		try {
			return hisc_fisioterapiaDao.eliminar(hisc_fisioterapia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Hisc_fisioterapia> listar(Map<String, Object> parameters) {
		try {
			return hisc_fisioterapiaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return hisc_fisioterapiaDao.existe(parameters);
	}

	public void guardar(Map<String, Object> datos) {
		try {
			Hisc_fisioterapia hisc_fisioterapia = (Hisc_fisioterapia) datos
					.get("histotia_fisioterapia");
			Admision admision = (Admision) datos.get("admision");
			if (admision == null) {
				throw new Exception("No hay admision que actualizar");
			}

			List<Antecedentes_personales> listadoAntecedentes = (List<Antecedentes_personales>) datos
					.get("listadoAntecedentes");

			String accion = (String) datos.get("accion");
			Map<String, Object> mapRecetas = (Map<String, Object>) datos
					.get("receta_medica");

			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");

			remision_interna.setCodigo_paciente(admision
					.getNro_identificacion());
			remision_interna.setCodigo_historia(hisc_fisioterapia
					.getCodigo_historia());
			remision_interna.setFecha_inicio(admision.getFecha_ingreso());

			// creamos la instancia de la historia
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(hisc_fisioterapia
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_fisioterapia
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(admision.getFecha_ingreso());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(hisc_fisioterapia.getCreacion_user());

			// realizamos la accion correspondiente ya sea guardar o actualizar

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				hisc_fisioterapia.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				hisc_fisioterapiaDao.crear(hisc_fisioterapia);
				remision_interna.setCodigo_historia(hisc_fisioterapia
						.getCodigo_historia());

			} else {
				if (generalExtraService.consultar(historia_clinica) != null) {
					if (consultar(hisc_fisioterapia) == null)
						throw new Exception(
								"El dato que desea actualizar no existe en la base de datos");
					hisc_fisioterapiaDao.actualizar(hisc_fisioterapia);
				}
			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
			}

			// creamos impresion diagbnotica
			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) datos
					.get("impresion_diagnostica");

			// creamos receta
			List<Detalle_receta_rips> detalle_receta_rips = (List<Detalle_receta_rips>) mapRecetas
					.get("lista_detalle");

			if (!detalle_receta_rips.isEmpty()) {
				mapRecetas.put("receta_rips",
						receta_ripsService.guardar(mapRecetas));
			} else {
				mapRecetas.put("receta_rips", null);
			}

			// antecedentes personales
			Antecedentes_personales antecedentesAux = new Antecedentes_personales();
			antecedentesAux.setCodigo_empresa(hisc_fisioterapia
					.getCodigo_empresa());
			antecedentesAux.setCodigo_sucursal(hisc_fisioterapia
					.getCodigo_sucursal());
			antecedentesAux.setCodigo_historia(hisc_fisioterapia
					.getCodigo_historia());
			antecedentesAux.setNro_identificacion(hisc_fisioterapia
					.getNro_identificacion());
			antecedentes_personalesDao.eliminar(antecedentesAux);

			for (Antecedentes_personales antePersonales : listadoAntecedentes) {
				antePersonales.setCodigo_historia(hisc_fisioterapia
						.getCodigo_historia());
				antePersonales.setNro_identificacion(hisc_fisioterapia
						.getNro_identificacion());
				antecedentes_personalesDao.crear(antePersonales);
			}

			// actualizamos la admision
			admision.setCodigo_medico(historia_clinica.getPrestador());
			admision.setAtendida(true);
			
			// Jose Ojeda
			admision.setFecha_atencion(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));

			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());
			admisionDao.actualizar(admision);
			
			/* generamos la consulta */
			crearConsulta(hisc_fisioterapia, admision,
					impresion_diagnostica);

			// creamos ficha de epidemiologia y impresion diagnostica
			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					hisc_fisioterapia.getCodigo_historia(), datos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					hisc_fisioterapia.getCreacion_user());

		} catch (Exception e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		}
	}
	
	public void crearConsulta(Hisc_fisioterapia historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {
		Map map = new HashMap();
		map.put("codigo_empresa", historiasConsultas.getCodigo_empresa());
		map.put("codigo_sucursal", historiasConsultas.getCodigo_sucursal());
		map.put("nro_identificacion",
				historiasConsultas.getNro_identificacion());
		map.put("nro_ingreso", historiasConsultas.getNro_ingreso());
		map.put("codigo_prestador", historiasConsultas.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", historiasConsultas.getCreacion_date());
		map.put("ultimo_update", historiasConsultas.getUltimo_update());
		map.put("creacion_user", historiasConsultas.getCreacion_user());
		map.put("ultimo_user", historiasConsultas.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
	
		ServiciosDisponiblesUtils.generarConsulta(map);
	}

}