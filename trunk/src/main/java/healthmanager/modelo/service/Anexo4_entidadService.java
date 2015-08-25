/*
 * Anexo4_entidadServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.controller.SeleccionarPrestadorAction;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Detalle_anexo4;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.dao.Anexo4_entidadDao;
import healthmanager.modelo.dao.Detalle_anexo4Dao;
import healthmanager.modelo.dao.Manuales_tarifariosDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.Res;
import com.framework.util.Utilidades;

@Service("anexo4_entidadService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Anexo4_entidadService implements Serializable{

//	private static Logger log = Logger.getLogger(Anexo4_entidadService.class);

	private String limit;

	private static final String PARAM_MANUAL_TARIFARIO = "PMT";

	@Autowired
	private Anexo4_entidadDao anexo4_entidadDao;
	@Autowired
	private Detalle_anexo4Dao detalle_anexo4Dao;
	@Autowired
	private Manuales_tarifariosDao manuales_tarifariosDao;
	@Autowired
	private Servicios_contratadosService servicios_contratadosService;
	@Autowired
	private ConsecutivoService consecutivoService;

	public void crear(Anexo4_entidad anexo4_entidad) {
		try {
			if (consultar(anexo4_entidad) != null) {
				throw new HealthmanagerException(
						"Anexo4_entidad ya se encuentra en la base de datos");
			}
			anexo4_entidadDao.crear(anexo4_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Anexo4_entidad anexo4_entidad) {
		try {
			return anexo4_entidadDao.actualizar(anexo4_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Anexo4_entidad consultar(Anexo4_entidad anexo4_entidad) {
		try {
			return anexo4_entidadDao.consultar(anexo4_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Anexo4_entidad anexo4_entidad) {
		try {
			return anexo4_entidadDao.eliminar(anexo4_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Anexo4_entidad> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return anexo4_entidadDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setAnexo4_entidadDao(Anexo4_entidadDao anexo4_entidadDao) {
		this.anexo4_entidadDao = anexo4_entidadDao;
	}

	public Anexo4_entidadDao getAnexo4_entidadDao() {
		return this.anexo4_entidadDao;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
	
	/**
	 * Este metodo me permite guardar las ordenes por prestador
	 * */
	public List<Map<String, Object>> guardarCambioPrestadorMultiple(Map<String, Object> map){
		Anexo4_entidad anexo4_entidad = (Anexo4_entidad) map.get("anexo4");
		Configuracion_servicios_autorizacion config = (Configuracion_servicios_autorizacion) map
				.get("config");
		ServiceLocatorWeb serviceLocator = (ServiceLocatorWeb) map.get("servicelocator");
		List<Map<String, Object>> listado_prestadores = (List<Map<String,Object>>) map.get("listado_prestadores");
		List<Map<String, Object>> respuesta = new ArrayList<Map<String,Object>>();
		int posicion = 1;
		for (Map<String, Object> map_prestadores : listado_prestadores) {
			Administradora administradora = (Administradora) map_prestadores.get(SeleccionarPrestadorAction.PARAM_ADMINISTRADORA); 
			Map<String, Object> mapa_crear_nueva_orden = new HashMap<String, Object>();
			if(posicion++ == 1){
				anexo4_entidad.setCodigo_prestador(administradora.getCodigo());
				mapa_crear_nueva_orden.put("anexo4", anexo4_entidad);
				mapa_crear_nueva_orden.put("accion", "modificar");
				mapa_crear_nueva_orden.put("accion_detalle", "registrar");
				
				Detalle_anexo4 detalle_anexo4 = new Detalle_anexo4();
				detalle_anexo4.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
				detalle_anexo4.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
				detalle_anexo4.setCodigo_orden(anexo4_entidad.getCodigo());
				detalle_anexo4Dao.eliminar(detalle_anexo4);
			}else{
				Anexo4_entidad anexo4_entidad_nuevo = Res.clonar(anexo4_entidad);
				anexo4_entidad_nuevo.setCodigo_prestador(administradora.getCodigo());
				mapa_crear_nueva_orden.put("anexo4", anexo4_entidad_nuevo);
				mapa_crear_nueva_orden.put("accion", "registrar");
				mapa_crear_nueva_orden.put("accion_detalle", "registrar");
			}
			mapa_crear_nueva_orden.put("config", config);
			mapa_crear_nueva_orden.put("administradora", administradora);
			mapa_crear_nueva_orden.put("servicelocator", serviceLocator);
			mapa_crear_nueva_orden.put("detalles", map_prestadores
					.get(SeleccionarPrestadorAction.PARAM_LISTADO_ANEXO4));  
			
			// guardamos prestador
			guardarCambioPrestador(mapa_crear_nueva_orden); 
			// alimentamos la respuesta
			respuesta.add(mapa_crear_nueva_orden); 
		}
		return respuesta;
	}
	

	public void guardarCambioPrestador(Map<String, Object> map) {
		try {
			// Obtenemos parametros
			Anexo4_entidad anexo4_entidad = (Anexo4_entidad) map.get("anexo4");
			Configuracion_servicios_autorizacion config = (Configuracion_servicios_autorizacion) map
					.get("config");
			Administradora administradora = (Administradora) map
					.get("administradora");
			ServiceLocatorWeb serviceLocator = (ServiceLocatorWeb) map
					.get("servicelocator");
			String accion = (String) map.get("accion");
			String accion_detalle = (String) map.get("accion_detalle"); 
			List<Detalle_anexo4> detalle_anexo4s = (List<Detalle_anexo4>) map.get("detalles"); 

			// actualizamos informaicion
			anexo4_entidad.setPrestador_asignado("S");
			
			if(accion.equalsIgnoreCase("registrar")){
				String codigo = consecutivoService.crearConsecutivo(
						anexo4_entidad.getCodigo_empresa(),
						anexo4_entidad.getCodigo_sucursal(), "ANEXO_4");
				anexo4_entidad.setCodigo(codigo);
				anexo4_entidadDao.crear(anexo4_entidad);
				consecutivoService.actualizarConsecutivo(
						anexo4_entidad.getCodigo_empresa(),
						anexo4_entidad.getCodigo_sucursal(), "ANEXO_4", codigo);
			}else{
				actualizar(anexo4_entidad);
			}

			// Obetenemos el manual con el cual va a costar esa orden

			Map<String, Object> map_consultar_contratos = new HashMap<String, Object>();
			map_consultar_contratos.put("codigo_empresa",
					administradora.getCodigo_empresa());
			map_consultar_contratos.put("codigo_sucursal",
					administradora.getCodigo_sucursal());
			map_consultar_contratos.put("codigo_administradora",
					administradora.getCodigo());
			map_consultar_contratos.put("id_configuracion", config.getId());
			map_consultar_contratos.put("mostar_solo_activos", "activos");
//			log.info("Mapa contrato: " + map_consultar_contratos);
			List<Map<String, Object>> list_contratos = servicios_contratadosService
					.getContratosValidos(map_consultar_contratos);

			if (!list_contratos.isEmpty()) {
				// obtenemos los de detalles de la autorizacion
//				Map<String, Object> mapDetalleautorizacion = new HashMap<String, Object>();
//				mapDetalleautorizacion.put("codigo_empresa",
//						anexo4_entidad.getCodigo_empresa());
//				mapDetalleautorizacion.put("codigo_sucursal",
//						anexo4_entidad.getCodigo_sucursal());
//				mapDetalleautorizacion.put("codigo_orden",
//						anexo4_entidad.getCodigo());
//				List<Detalle_anexo4> detalle_anexo4s = detalle_anexo4Dao
//						.listar(mapDetalleautorizacion);
				if (!detalle_anexo4s.isEmpty()) {
					for (Detalle_anexo4 detalle_anexo4 : detalle_anexo4s) {
						Map<String, Object> mapProcedimiento = Utilidades
								.getProcedimiento(
										getManualTarifario(
												list_contratos,
												detalle_anexo4
														.getCodigo_procedimiento()),
										new Long(detalle_anexo4
												.getCodigo_procedimiento()),
										serviceLocator);
						// log.info("Procedimiento: " + mapProcedimiento);
						double valor_pcd = (Double) mapProcedimiento
								.get("valor_pcd");
						// log.info("Valor procedimiento: " + valor_pcd);
						if (valor_pcd > 0 || config.isMostrar_tipo_pcd()) {
							detalle_anexo4.setValor_procedimiento(valor_pcd);
							detalle_anexo4
									.setDescuento((Double) mapProcedimiento
											.get("descuento"));
							detalle_anexo4
									.setIncremento((Double) mapProcedimiento
											.get("incremento"));
							detalle_anexo4
									.setValor_real((Double) mapProcedimiento
											.get("valor_real"));
							detalle_anexo4
									.setNombre_pcd((String) mapProcedimiento
											.get("nombre_procedimiento"));
							if(accion_detalle.equalsIgnoreCase("registrar")){
								detalle_anexo4.setCodigo_orden(anexo4_entidad.getCodigo()); 
								detalle_anexo4Dao.crear(detalle_anexo4);
							}else{ 
								detalle_anexo4Dao.actualizar(detalle_anexo4);
							}
						} else {
							throw new ValidacionRunTimeException(
									"No se autorizar el procedimiento "
											+ detalle_anexo4
													.getCodigo_procedimiento()
											+ " "
											+ detalle_anexo4.getNombre_pcd()
											+ " por el valor esta en cero");
						}
					}
				} else {
					throw new ValidacionRunTimeException(
							"Esta autorizacion no tiene servicios a autorizar");
				}
			} else {
				throw new ValidacionRunTimeException(
						"El prestador "
								+ (administradora.getCodigo() + " " + administradora
										.getNombre())
								+ " no tiene contratado el servicio de "
								+ config.getNombre());
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private Manuales_tarifarios getManualTarifario(
			List<Map<String, Object>> list_contratos,
			String codigo_procedimiento) {
		if (list_contratos.size() == 1) {
			return getManualTarifario(list_contratos.get(0));
		} else {
			// TODO pendiente por validar multiple
			return getManualTarifario(list_contratos.get(0));
		}
	}

	/**
	 * Este metodo me permite devolver los manuales permitidos para ese
	 * prestador
	 * 
	 * @author Luis Miguel
	 * */
	private Manuales_tarifarios getManualTarifario(
			Map<String, Object> map_contrato_manual) {
		Manuales_tarifarios manuales_tarifarios = (Manuales_tarifarios) map_contrato_manual
				.get(PARAM_MANUAL_TARIFARIO);
		if (manuales_tarifarios != null) {
			return manuales_tarifarios;
		} else {
			String id_contrato = (String) map_contrato_manual
					.get("id_contrato");
			manuales_tarifarios = new Manuales_tarifarios();
			manuales_tarifarios.setCodigo_empresa((String) map_contrato_manual
					.get("codigo_empresa"));
			manuales_tarifarios.setCodigo_sucursal((String) map_contrato_manual
					.get("codigo_sucursal"));
			manuales_tarifarios.setId_contrato(id_contrato);
			manuales_tarifarios.setConsecutivo((Long) map_contrato_manual
					.get("consecutivo_mt"));
			manuales_tarifarios
					.setCodigo_administradora((String) map_contrato_manual
							.get("codigo_administradora"));
			manuales_tarifarios = manuales_tarifariosDao
					.consultar(manuales_tarifarios);
			if (manuales_tarifarios != null) {
				map_contrato_manual.put(PARAM_MANUAL_TARIFARIO,
						manuales_tarifarios);
				return manuales_tarifarios;
			} else {
				throw new ValidacionRunTimeException(
						"Manual tarifario no encontrado id contrato: "
								+ id_contrato);
			}
		}
	}
	
	public List<Anexo4_entidad> listarResultados(Map<String, Object> parametros){
		return anexo4_entidadDao.listarResultados(parametros);
	}
	
	public Integer totalResultados(Map<String, Object> parameters){
		return anexo4_entidadDao.totalResultados(parameters);
	}
}
