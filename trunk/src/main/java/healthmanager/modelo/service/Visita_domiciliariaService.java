/*
 * Visita_domiciliariaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.bean.Visita_domiciliaria;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Visita_domiciliariaDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.interfaces.IDatosFichaEpidemiologia;
import com.framework.util.ServiciosDisponiblesUtils;

@Service("visita_domiciliariaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Visita_domiciliariaService implements Serializable{

	@Autowired
	private Visita_domiciliariaDao visita_domiciliariaDao;
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
	private GeneralExtraService generalExtraService;

	public Visita_domiciliaria guardar(Map<String, Object> datos) {
		try {

			Visita_domiciliaria visita_domiciliaria = (Visita_domiciliaria) datos
					.get("visita_domiciliaria");
			Admision admision = (Admision) datos.get("admision");
			String accion = (String) datos.get("accion");

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(visita_domiciliaria
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(visita_domiciliaria
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(visita_domiciliaria
					.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(visita_domiciliaria
					.getCreacion_user());

			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");

			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna.setCodigo_historia(visita_domiciliaria
					.getCodigo_historia());
			remision_interna.setFecha_inicio(visita_domiciliaria
					.getFecha_inicial());

			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) datos
					.get("impresion_diagnostica");
			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());
			admisionDao.actualizar(admision);

			visita_domiciliaria.setCie_principal(impresion_diagnostica
					.getCie_principal());
			visita_domiciliaria.setTipo_principal(impresion_diagnostica
					.getTipo_principal());
			visita_domiciliaria.setCie_relacionado1(impresion_diagnostica
					.getCie_relacionado1());
			visita_domiciliaria.setTipo_relacionado1(impresion_diagnostica
					.getTipo_relacionado1());
			visita_domiciliaria.setCie_relacionado2(impresion_diagnostica
					.getCie_relacionado2());
			visita_domiciliaria.setTipo_relacionado2(impresion_diagnostica
					.getTipo_relacionado2());
			visita_domiciliaria.setCie_relacionado3(impresion_diagnostica
					.getCie_relacionado3());
			visita_domiciliaria.setTipo_relacionado3(impresion_diagnostica
					.getTipo_relacionado3());
			visita_domiciliaria.setFinalidad_consulta(impresion_diagnostica
					.getFinalidad_consulta());
			visita_domiciliaria.setCausas_externas(impresion_diagnostica
					.getCausas_externas());
			visita_domiciliaria.setCodigo_consulta_pyp(impresion_diagnostica
					.getCodigo_consulta_pyp());
			visita_domiciliaria.setNro_ingreso(admision.getNro_ingreso());

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				visita_domiciliaria.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				visita_domiciliariaDao.crear(visita_domiciliaria);
				remision_interna.setCodigo_historia(visita_domiciliaria
						.getCodigo_historia());
			} else {
				visita_domiciliariaDao.actualizar(visita_domiciliaria);
			}

			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
			}

			admision.setCodigo_medico(historia_clinica.getPrestador());
			admision.setAtendida(true);
			//log.info("crear consulta");
			crearConsulta(visita_domiciliaria, admision, impresion_diagnostica);
			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());
			admisionDao.actualizar(admision);

			if (impresion_diagnostica != null) {
				List<IDatosFichaEpidemiologia> listado_fichas = impresion_diagnostica
						.getListado_fichas();
				for (IDatosFichaEpidemiologia iDatosFichaEpidemiologia : listado_fichas) {
					iDatosFichaEpidemiologia
							.setCodigo_historia(visita_domiciliaria
									.getCodigo_historia());
					datos.put("datos_ficha_epidemiologia",
							iDatosFichaEpidemiologia);

					ficha_epidemiologia_nnService.guardar(datos);
				}
			}

			return visita_domiciliaria;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Visita_domiciliaria visita_domiciliaria) {
		try {
			if (consultar(visita_domiciliaria) != null) {
				throw new HealthmanagerException(
						"Visita_domiciliaria ya se encuentra en la base de datos");
			}
			visita_domiciliariaDao.crear(visita_domiciliaria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Visita_domiciliaria visita_domiciliaria) {
		try {
			return visita_domiciliariaDao.actualizar(visita_domiciliaria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Visita_domiciliaria consultar(Visita_domiciliaria visita_domiciliaria) {
		try {
			return visita_domiciliariaDao.consultar(visita_domiciliaria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Visita_domiciliaria consultar_historia(
			Visita_domiciliaria visita_domiciliaria) {
		try {
			return visita_domiciliariaDao
					.consultar_historia(visita_domiciliaria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Visita_domiciliaria visita_domiciliaria) {
		try {
			return visita_domiciliariaDao.eliminar(visita_domiciliaria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Visita_domiciliaria> listar(Map<String, Object> parameter) {
		try {
			return visita_domiciliariaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

		Visita_domiciliaria visita_domiciliaria = (Visita_domiciliaria) historiasConsultas;
		Map map = new HashMap();
		map.put("codigo_empresa", visita_domiciliaria.getCodigo_empresa());
		map.put("codigo_sucursal", visita_domiciliaria.getCodigo_sucursal());
		map.put("nro_identificacion", visita_domiciliaria.getIdentificacion());
		map.put("nro_ingreso", visita_domiciliaria.getNro_ingreso());
		map.put("codigo_prestador", visita_domiciliaria.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", visita_domiciliaria.getCreacion_date());
		map.put("ultimo_update", visita_domiciliaria.getUltimo_update());
		map.put("creacion_user", visita_domiciliaria.getCreacion_user());
		map.put("ultimo_user", visita_domiciliaria.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}
}
