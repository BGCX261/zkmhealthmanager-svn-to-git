/*
 * Hisc_recien_nacidoService.java
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
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Hisc_recien_nacido;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.Hisc_recien_nacidoDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("hisc_recien_nacidoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Hisc_recien_nacidoService implements Serializable{

	private String limit;

	@Autowired
	private Hisc_recien_nacidoDao hisc_recien_nacidoDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Coordenadas_graficasService coordenadas_graficasService;
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
	private GeneralExtraService generalExtraService;

	@Autowired
	private CitasDao citasDao;

	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;

	public void crear(Hisc_recien_nacido hisc_recien_nacido) {
		try {
			if (consultar(hisc_recien_nacido) != null) {
				throw new HealthmanagerException(
						"Historia clinica ya se encuentra en la base de datos");
			}
			hisc_recien_nacidoDao.crear(hisc_recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarDatos(Map<String, Object> mapaDatos) {
		ArrayList<Coordenadas_graficas> coordenadas = (ArrayList<Coordenadas_graficas>) mapaDatos
				.get("coordenadas");
		Admision admision = (Admision) mapaDatos.get("admision");
		Citas cita = (Citas) mapaDatos.get("cita_seleccionada");
		String accion = (String) mapaDatos.get("accion");
		Map<String, Object> mapRecetas = (Map<String, Object>) mapaDatos
				.get("receta_medica");
		Map<String, Object> mapOrdenServicio = (Map<String, Object>) mapaDatos
				.get("orden_servicio");
		Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) mapaDatos
				.get("impresion_diagnostica");

		Hisc_recien_nacido hisc_recien_nacido = (Hisc_recien_nacido) mapaDatos
				.get("historia_clinica");

		Historia_clinica historia_clinica = new Historia_clinica();
		historia_clinica.setCodigo_empresa(hisc_recien_nacido
				.getCodigo_empresa());
		historia_clinica.setCodigo_sucursal(hisc_recien_nacido
				.getCodigo_sucursal());
		historia_clinica.setFecha_historia(hisc_recien_nacido
				.getFecha_inicial());
		historia_clinica
				.setNro_identificacion(admision.getNro_identificacion());
		historia_clinica.setNro_ingreso(admision.getNro_ingreso());
		historia_clinica.setVia_ingreso(admision.getVia_ingreso());
		historia_clinica.setPrestador(hisc_recien_nacido.getCreacion_user());
		try {
			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				hisc_recien_nacido.setCodigo_historia(historia_clinica
						.getCodigo_historia());

				hisc_recien_nacidoDao.crear(hisc_recien_nacido);

				for (Coordenadas_graficas cg : coordenadas) {
					cg.setCodigo_historia(hisc_recien_nacido
							.getCodigo_historia());
					coordenadas_graficasService.crear(cg);
				}
			} else {
				if (generalExtraService.consultar(historia_clinica) != null) {
					if (consultar(hisc_recien_nacido) == null)
						throw new Exception(
								"El dato que desea actualizar no existe en la base de datos");
					hisc_recien_nacidoDao.actualizar(hisc_recien_nacido);
				}
			}

			List<Detalle_orden> detalle_ordens = (List<Detalle_orden>) mapOrdenServicio
					.get("lista_detalle");
			List<Detalle_receta_rips> detalle_receta_rips = (List<Detalle_receta_rips>) mapOrdenServicio
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
			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) mapaDatos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad.setNro_historia(hisc_recien_nacido
						.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							hisc_recien_nacido.getCodigo_empresa(),
							hisc_recien_nacido.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							hisc_recien_nacido.getCodigo_empresa(),
							hisc_recien_nacido.getCodigo_sucursal(),
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

			admision.setAtendida(true);

			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());

			admision.setCodigo_medico(hisc_recien_nacido.getCreacion_user());
			admisionDao.actualizar(admision);

			if (cita != null) {
				cita.setEstado("2");
				citasDao.actualizar(cita);
			}

			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					hisc_recien_nacido.getCodigo_historia(), mapaDatos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					hisc_recien_nacido.getCreacion_user());
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Hisc_recien_nacido hisc_recien_nacido) {
		try {
			return hisc_recien_nacidoDao.actualizar(hisc_recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Hisc_recien_nacido consultar(Hisc_recien_nacido hisc_recien_nacido) {
		try {
			return hisc_recien_nacidoDao.consultar(hisc_recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Hisc_recien_nacido hisc_recien_nacido) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(hisc_recien_nacido.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_recien_nacido
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(hisc_recien_nacido
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Hisc_recien_nacido> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return hisc_recien_nacidoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer total_registros(Map<String, Object> parameters) {
		try {
			return hisc_recien_nacidoDao.total_registros(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.hisc_recien_nacidoDao.existe(parameters);
	}

}