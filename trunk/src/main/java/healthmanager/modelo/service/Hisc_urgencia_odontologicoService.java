/*
 * Hisc_urgencia_odontologicoServiceImpl.java
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
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Hisc_urgencia_odontologico;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Hisc_urgencia_odontologicoDao;

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

@Service("hisc_urgencia_odontologicoService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Hisc_urgencia_odontologicoService implements Serializable {

	private String limit;

	@Autowired
	private Hisc_urgencia_odontologicoDao hisc_urgencia_odontologicoDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private Impresion_diagnosticaService impresion_diagnosticaService;
	@Autowired
	private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;

	@Autowired
	private GeneralExtraService generalExtraService;
	@Autowired
	private Datos_procedimientoService datos_procedimientoService;

	public void crear(Hisc_urgencia_odontologico hisc_urgencia_odontologico) {
		try {
			if (consultar(hisc_urgencia_odontologico) != null) {
				throw new HealthmanagerException(
						"Hisc_urgencia_odontologico ya se encuentra en la base de datos");
			}
			hisc_urgencia_odontologicoDao.crear(hisc_urgencia_odontologico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarDatos(Map<String, Object> datos) {
		try {
			Hisc_urgencia_odontologico hisc_urgencia_odontologico = (Hisc_urgencia_odontologico) datos
					.get("hisc_urgencia_odontologico");
			Admision admision = (Admision) datos.get("admision");
			String accion = (String) datos.get("accion");
			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) datos
					.get("impresion_diagnostica");
			List<Datos_procedimiento> datos_procedimientos = (List<Datos_procedimiento>) datos
					.get("datos_procedimiento");

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(hisc_urgencia_odontologico
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_urgencia_odontologico
					.getCodigo_sucursal());
			historia_clinica.setFecha_historia(hisc_urgencia_odontologico
					.getFecha_inicial());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setPrestador(hisc_urgencia_odontologico
					.getCreacion_user());

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				hisc_urgencia_odontologico.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				hisc_urgencia_odontologicoDao.crear(hisc_urgencia_odontologico);

			} else {
				if (consultar(hisc_urgencia_odontologico) == null)
					throw new Exception(
							"El dato que desea actualizar no existe en la base de datos");
				hisc_urgencia_odontologicoDao
						.actualizar(hisc_urgencia_odontologico);
			}

			// cargamos procedimientos realizados
			for (Datos_procedimiento datos_procedimiento : datos_procedimientos) {
				// para que pueda consultar
				if (datos_procedimiento.getCodigo_registro() == null) {
					datos_procedimiento.setCodigo_registro(-1L);
				}

				// verificamos que exista para que no se repitan
				if (datos_procedimientoService.existe(datos_procedimiento)) {
					datos_procedimiento.setValor_neto(datos_procedimiento
							.getValor_procedimiento()
							* datos_procedimiento.getUnidades().intValue());
					datos_procedimientoService
							.actualizarRegistro(datos_procedimiento);
				} else {
					datos_procedimiento.setCopago(0.0);
					datos_procedimiento.setValor_neto(datos_procedimiento
							.getValor_procedimiento()
							* datos_procedimiento.getUnidades().intValue());
					datos_procedimientoService.crear(datos_procedimiento);
				}
			}

			// actualuzamos informacion de admision
			admision.setCodigo_medico(historia_clinica.getPrestador());
			admision.setAtendida(true);
			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());

			admision.setFecha_atencion(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			admisionDao.actualizar(admision);

			/* generamos la consulta */
			crearConsulta(hisc_urgencia_odontologico, admision,
					impresion_diagnostica);

			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					hisc_urgencia_odontologico.getCodigo_historia(), datos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					hisc_urgencia_odontologico.getCreacion_user());

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Hisc_urgencia_odontologico hisc_urgencia_odontologico) {
		try {
			return hisc_urgencia_odontologicoDao
					.actualizar(hisc_urgencia_odontologico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Hisc_urgencia_odontologico consultar(
			Hisc_urgencia_odontologico hisc_urgencia_odontologico) {
		try {
			return hisc_urgencia_odontologicoDao
					.consultar(hisc_urgencia_odontologico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Hisc_urgencia_odontologico hisc_urgencia_odontologico) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(hisc_urgencia_odontologico.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_urgencia_odontologico
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(hisc_urgencia_odontologico
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Hisc_urgencia_odontologico> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return hisc_urgencia_odontologicoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.hisc_urgencia_odontologicoDao.existe(parameters);
	}

	public void crearConsulta(
			Hisc_urgencia_odontologico hisc_urgencia_odontologico,
			Admision admision, Impresion_diagnostica impresion_diagnostica)
			throws Exception {
		Map map = new HashMap();
		map.put("codigo_empresa",
				hisc_urgencia_odontologico.getCodigo_empresa());
		map.put("codigo_sucursal",
				hisc_urgencia_odontologico.getCodigo_sucursal());
		map.put("nro_identificacion",
				hisc_urgencia_odontologico.getIdentificacion());
		map.put("nro_ingreso", admision.getNro_ingreso());
		map.put("codigo_prestador",
				hisc_urgencia_odontologico.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", hisc_urgencia_odontologico.getCreacion_date());
		map.put("ultimo_update", hisc_urgencia_odontologico.getUltimo_update());
		map.put("creacion_user", hisc_urgencia_odontologico.getCreacion_user());
		map.put("ultimo_user", hisc_urgencia_odontologico.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);

		ServiciosDisponiblesUtils.generarConsulta(map);
	}

}
