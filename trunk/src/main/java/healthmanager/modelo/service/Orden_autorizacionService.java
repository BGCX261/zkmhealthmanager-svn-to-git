/*
 * Orden_autorizacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Detalle_anexo3;
import healthmanager.modelo.bean.Detalle_anexo4;
import healthmanager.modelo.bean.Detalle_orden_autorizacion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_autorizacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plan_tratamiento;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.dao.Anexo3_entidadDao;
import healthmanager.modelo.dao.Anexo4_entidadDao;
import healthmanager.modelo.dao.Detalle_anexo3Dao;
import healthmanager.modelo.dao.Detalle_anexo4Dao;
import healthmanager.modelo.dao.Detalle_orden_autorizacionDao;
import healthmanager.modelo.dao.ElementoDao;
import healthmanager.modelo.dao.Orden_autorizacionDao;
import healthmanager.modelo.dao.ProcedimientosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantesAutorizaciones;

@Service("orden_autorizacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Orden_autorizacionService implements Serializable{

	public static Logger log = Logger
			.getLogger(Orden_autorizacionService.class);
	
	public static final String PARAM_PLAN_TRATAMIENTO = "PPT";
	public static final String PARAM_ADMISION = "PA";
	public static final String PARAM_SUCURSAL = "PS";
	public static final String PARAM_IMPRESION_DIAGNOSTICA = "PID";
	public static final String PARAM_USUARIO = "PU";
	public static final String PARAM_ACCION = "PAON";
	public static final String PARAM_ROL_USUARIO = "RUL"; 

	@Autowired
	private Orden_autorizacionDao orden_autorizacionDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Detalle_orden_autorizacionDao detalle_orden_autorizacionDao;
	@Autowired
	private Anexo4_entidadDao anexo4_entidadDao;
	@Autowired
	private Detalle_anexo4Dao detalle_anexo4Dao;
	@Autowired
	private Anexo3_entidadDao anexo3_entidadDao;
	@Autowired
	private Detalle_anexo3Dao detalle_anexo3Dao;
	@Autowired
	private ElementoDao elementoDao;
	@Autowired private ProcedimientosDao procedimientosDao;
	@Autowired
	private PacienteService pacienteService;
	@Autowired
	private AdministradoraService administradoraService;

	private String limit;

	public void crear(Orden_autorizacion orden_autorizacion) {
		try {
			if (consultar(orden_autorizacion) != null) {
				throw new HealthmanagerException(
						"Orden_autorizacion ya se encuentra en la base de datos");
			}
			orden_autorizacionDao.crear(orden_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Orden_autorizacion orden_autorizacion) {
		try {
			return orden_autorizacionDao.actualizar(orden_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Orden_autorizacion consultar(Orden_autorizacion orden_autorizacion) {
		try {
			return orden_autorizacionDao.consultar(orden_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Orden_autorizacion orden_autorizacion) {
		try {
			return orden_autorizacionDao.eliminar(orden_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Orden_autorizacion> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return orden_autorizacionDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return orden_autorizacionDao.existe(parameters);
	}

	public void guardarOrden(Map<String, Object> mapDatos,
			Impresion_diagnostica impresion_diagnostica, Admision admision) {
		if (mapDatos != null && impresion_diagnostica != null
				&& admision != null) {
			// Actualizamos datos
			Orden_autorizacion autorizacion = (Orden_autorizacion) mapDatos
					.get("orden");
			autorizacion.setCodigo_cie_principal(impresion_diagnostica
					.getCie_principal());
			autorizacion.setCodigo_cie1(impresion_diagnostica
					.getCie_relacionado1());
			autorizacion.setCodigo_cie2(impresion_diagnostica
					.getCie_relacionado2());
			autorizacion.setCodigo_prestador(admision
					.getCodigo_administradora());
			autorizacion
					.setNro_identificacion(admision.getNro_identificacion());
			autorizacion.setUbicacion("1");

//			// Solicitamos registro
//			mapDatos.put("accion", "registrar");

			// guardamos autorizacion
			guardarOrden(mapDatos);
		}
	}

	/**
	 * Este metodo me permite crear la orden, una autorizacion y una solicitud
	 * de manera automatica
	 * */
	public Orden_autorizacion guardarOrden(Map<String, Object> obtenerDatos) {
		try {
			Orden_autorizacion autorizacion = (Orden_autorizacion) obtenerDatos
					.get("orden");
			List<Detalle_orden_autorizacion> autorizacions = (List<Detalle_orden_autorizacion>) obtenerDatos
					.get("detalle");
			Usuarios usuarios = (Usuarios) obtenerDatos.get("usuario");
			String rol = (String) obtenerDatos.get("rol");
			String accion = (String) obtenerDatos.get("accion");
			if (accion.equalsIgnoreCase("registrar")) {
				String codigo = consecutivoService
						.crearConsecutivo(autorizacion.getCodigo_empresa(),
								autorizacion.getCodigo_sucursal(),
								"ORDEN_AUTORIZACION");
				autorizacion.setCodigo_orden(codigo);
				orden_autorizacionDao.crear(autorizacion);
				consecutivoService.actualizarConsecutivo(
						autorizacion.getCodigo_empresa(),
						autorizacion.getCodigo_sucursal(),
						"ORDEN_AUTORIZACION", codigo);
			} else {
				orden_autorizacionDao.actualizar(autorizacion);
			}

			// Generar tercero en la autorizacion
			guardarTerceroAdministradora(autorizacion);
			guardarTerceroPaciente(autorizacion);

			// Limpiamos detalles
			Detalle_orden_autorizacion detalle_orden_autorizacion = new Detalle_orden_autorizacion();
			detalle_orden_autorizacion.setCodigo_empresa(autorizacion
					.getCodigo_empresa());
			detalle_orden_autorizacion.setCodigo_sucursal(autorizacion
					.getCodigo_sucursal());
			detalle_orden_autorizacion.setCodigo_orden(autorizacion
					.getCodigo_orden());
			detalle_orden_autorizacionDao.eliminar(detalle_orden_autorizacion);

			// Limpiamos anexo 4
//			Anexo4_entidad anexo4_entidad = new Anexo4_entidad();
//			anexo4_entidad.setCodigo_empresa(autorizacion.getCodigo_empresa());
//			anexo4_entidad
//					.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
//			anexo4_entidad.setCodigo_solicitud(autorizacion.getCodigo_orden());
//			anexo4_entidadDao.eliminarParametro(anexo4_entidad);

			// Limpiamos anexo 3
			Anexo3_entidad anexo_3entidad = new Anexo3_entidad();
			anexo_3entidad.setCodigo_empresa(autorizacion.getCodigo_empresa());
			anexo_3entidad
					.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
			anexo_3entidad.setNumero_solicitud(autorizacion.getCodigo_orden());
			anexo3_entidadDao.eliminarParametro(anexo_3entidad);

			Elemento elemento = new Elemento();
			elemento.setCodigo(rol);
			elemento.setTipo("rol");
			elemento = elementoDao.consultar(elemento);

			// Guardamos servicios
			int consecutivo = 0;
			List<Detalle_orden_autorizacion> detalle_orden_autorizacionsNoAutorizados = new ArrayList<Detalle_orden_autorizacion>();
			List<Detalle_orden_autorizacion> detalle_orden_autorizacionsAutorizados = new ArrayList<Detalle_orden_autorizacion>();
			for (Detalle_orden_autorizacion detalle_orden_autorizacionTemp : autorizacions) {
				detalle_orden_autorizacionTemp.setCodigo_orden(autorizacion
						.getCodigo_orden());
				detalle_orden_autorizacionTemp.setConsecutivo(consecutivo++
						+ "");
				detalle_orden_autorizacionDao
						.crear(detalle_orden_autorizacionTemp);
				// Aqui verificamos los servicios que son autorizados y los que
				// no
				if (detalle_orden_autorizacionTemp.getAutorizado().equals("S")) {
					detalle_orden_autorizacionsAutorizados
							.add(detalle_orden_autorizacionTemp);
				} else {
					detalle_orden_autorizacionsNoAutorizados
							.add(detalle_orden_autorizacionTemp);
				}
			}

			String prestador_asignado = (String) obtenerDatos
					.get("prestador_asignado");
			/* Creamos autorizaciones anexo 4 */
			if (!detalle_orden_autorizacionsAutorizados.isEmpty()) {

				// reorganizamos lista para guardar detalles
				// clasificado
				Map<String, List<Detalle_orden_autorizacion>> mapServicios = new HashMap<String, List<Detalle_orden_autorizacion>>();
				for (Detalle_orden_autorizacion detalle_orden_autorizacionTemp : detalle_orden_autorizacionsAutorizados) {
					String llave = detalle_orden_autorizacionTemp
							.getTipo_servicio()
							+ "-"
							+ detalle_orden_autorizacionTemp.getEstado_cobro();
					if (mapServicios.containsKey(llave)) {
						List<Detalle_orden_autorizacion> detalle_orden_autorizacions = mapServicios
								.get(llave);
						detalle_orden_autorizacions
								.add(detalle_orden_autorizacionTemp);
					} else {
						List<Detalle_orden_autorizacion> list = new ArrayList<Detalle_orden_autorizacion>();
						list.add(detalle_orden_autorizacionTemp);
						mapServicios.put(llave, list);
					}
				}
				// Creamos nuestras autorizaciones por grupos ejemplo:
				// Laboratorio, Rx, etc
				Iterator it = mapServicios.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry e = (Map.Entry) it.next();
					String llave = e.getKey().toString();
					String servicio = llave.replaceAll("[-].+", "");
					String estado_cobro = llave.replaceAll(".+[-]", "");

					guardarAnexo4(autorizacion,
							(List<Detalle_orden_autorizacion>) e.getValue(),
							Long.parseLong(servicio), estado_cobro,
							prestador_asignado, usuarios, elemento);
				}
			}
			
			// Limpiamos las ordenes 
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", autorizacion.getCodigo_empresa());
			params.put("codigo_sucursal", autorizacion.getCodigo_sucursal());
			params.put("codigo_solicitud", autorizacion.getCodigo_orden());
			params.put("ordenes_sin_servicios", "ordenes_sin_servicios");
			anexo4_entidadDao.eliminarParametro(params);

			/* creamos solicitud anexo 3 */
			if (!detalle_orden_autorizacionsNoAutorizados.isEmpty()) {
				guardarAnexo3(autorizacion,
						detalle_orden_autorizacionsNoAutorizados, usuarios,
						elemento);
			}
			return autorizacion;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private void guardarTerceroPaciente(Orden_autorizacion autorizacion)
			throws Exception {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(autorizacion.getCodigo_empresa());
		paciente.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
		paciente.setNro_identificacion(autorizacion.getNro_identificacion());
		paciente = pacienteService.consultar(paciente);
		if (paciente != null) {
			pacienteService.guardarTercero(paciente);
		}
	}

	private void guardarTerceroAdministradora(Orden_autorizacion autorizacion)
			throws Exception {
		Administradora admin = new Administradora();
		admin.setCodigo_empresa(autorizacion.getCodigo_empresa());
		admin.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
		admin.setCodigo(autorizacion.getCodigo_prestador());
		admin = administradoraService.consultar(admin);
		if (admin != null) {
			administradoraService.guardarTerceroAdministradora(admin);
		}
	}

	private void guardarAnexo3(
			Orden_autorizacion autorizacion,
			List<Detalle_orden_autorizacion> detalle_orden_autorizacionsAutorizados,
			Usuarios usuarios, Elemento elementoRol) {
		String codigo = consecutivoService.crearConsecutivo(
				autorizacion.getCodigo_empresa(),
				autorizacion.getCodigo_sucursal(), "ANEXO_3");

		Anexo3_entidad anexo3_entidad = new Anexo3_entidad();
		anexo3_entidad.setCodigo_empresa(autorizacion.getCodigo_empresa());
		anexo3_entidad.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
		anexo3_entidad.setCodigo(codigo);
		anexo3_entidad.setNumero_solicitud(autorizacion.getCodigo_orden());
		anexo3_entidad.setFecha(autorizacion.getFecha());
		anexo3_entidad.setCodigo_administradora(autorizacion
				.getCodigo_prestador());
		anexo3_entidad.setCodigo_paciente(autorizacion.getNro_identificacion());
		anexo3_entidad.setCobertura("");
		anexo3_entidad.setOrigen_general("");
		anexo3_entidad.setOrigen_profesional("");
		anexo3_entidad.setOrigen_trabajo("");
		anexo3_entidad.setOrigen_transito("");
		anexo3_entidad.setOrigen_evento("");
		anexo3_entidad.setTipo_servicio("");
		anexo3_entidad.setPrioridad("");
		anexo3_entidad.setUbicacion(autorizacion.getUbicacion());
		anexo3_entidad.setServicio(autorizacion.getServicio());
		anexo3_entidad.setCama(autorizacion.getCama());
		anexo3_entidad.setGuia_atencion("");
		anexo3_entidad.setJustificacion("");
		anexo3_entidad.setCie_p(autorizacion.getCodigo_cie_principal());
		anexo3_entidad.setCie_1(autorizacion.getCodigo_cie1());
		anexo3_entidad.setCie_2(autorizacion.getCodigo_cie2());
		anexo3_entidad.setNro_identificacion_reporta("" + usuarios.getCodigo());
		anexo3_entidad.setNombre_reporta(usuarios != null ? usuarios
				.getNombres() + " " + usuarios.getApellidos() : "");
		anexo3_entidad.setCargo_reporta(elementoRol != null ? elementoRol
				.getDescripcion() : "");
		anexo3_entidad.setTel_reporta(usuarios.getTel_res() + "");
		anexo3_entidad.setCel_reporta(usuarios.getCelular() + "");
		anexo3_entidad.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		anexo3_entidad.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		anexo3_entidad.setCreacion_user(autorizacion.getCreacion_user());
		anexo3_entidad.setUltimo_user(autorizacion.getCreacion_user());
		anexo3_entidad.setAutorizado("N");
		anexo3_entidad.setCodigo_ips("");
		anexo3_entidad.setCons_ips("");
		anexo3_entidad.setEntidad("");
		anexo3_entidad.setLeido("N");
		anexo3_entidad.setLeido_rechazado("");
		anexo3_entidad.setNeed_autorizacion("");
		anexo3_entidad.setCodigo_receta("");
		anexo3_entidad.setTipo_anexo("");
		anexo3_entidad
				.setEstado(IConstantesAutorizaciones.ESTADO_ANEXO3_ACTIVO);
		anexo3_entidad.setTipo_servicio("");
		
		if(!detalle_orden_autorizacionsAutorizados.isEmpty()){
			Detalle_orden_autorizacion detalle_orden_autorizacion = detalle_orden_autorizacionsAutorizados.get(0);
			// alimentamos informacion de anexo
			anexo3_entidad.setTipo_cirugia(detalle_orden_autorizacion.getTipo_quirurgico());
			anexo3_entidad.setObservaciones(detalle_orden_autorizacion.getObservaciones());
		}

		anexo3_entidadDao.crear(anexo3_entidad);
		consecutivoService.actualizarConsecutivo(
				autorizacion.getCodigo_empresa(),
				autorizacion.getCodigo_sucursal(), "ANEXO_3", codigo);

		/* creamos detalles */
		int consecutivo = 0;
		for (Detalle_orden_autorizacion detalle_orden_autorizacion : detalle_orden_autorizacionsAutorizados) {
			Detalle_anexo3 detalle_anexo3 = new Detalle_anexo3();
			detalle_anexo3.setCodigo_empresa(detalle_orden_autorizacion
					.getCodigo_empresa());
			detalle_anexo3.setCodigo_sucursal(detalle_orden_autorizacion
					.getCodigo_sucursal());
			detalle_anexo3.setCodigo_orden(anexo3_entidad.getCodigo());
			detalle_anexo3.setConsecutivo(consecutivo++ + "");
			detalle_anexo3.setCodigo_procedimiento(detalle_orden_autorizacion
					.getCodigo_procedimiento());
			detalle_anexo3.setValor_procedimiento(detalle_orden_autorizacion
					.getValor_procedimiento());
			detalle_anexo3
					.setUnidades(detalle_orden_autorizacion.getUnidades());
			detalle_anexo3.setValor_real(detalle_orden_autorizacion
					.getValor_real());
			detalle_anexo3.setDescuento(detalle_orden_autorizacion
					.getDescuento());
			detalle_anexo3.setIncremento(detalle_orden_autorizacion
					.getIncremento());
			detalle_anexo3.setCodigo_cups(detalle_orden_autorizacion
					.getCodigo_cups());
			detalle_anexo3.setNombre_pcd(detalle_orden_autorizacion
					.getNombre_procedimiento());
			detalle_anexo3
					.setEstado_autorizacion(IConstantesAutorizaciones.ESTADO_AUTORIZACION_SOLICITUD_NO_AUTORIZADO);
			detalle_anexo3.setTipo_servicio(detalle_orden_autorizacion
					.getTipo_servicio());
			detalle_anexo3.setTipo_procedimiento("");  
			detalle_anexo3.setCodigo_quien_autoriza("");
			detalle_anexo3.setEstado_cobro(detalle_orden_autorizacion
					.getEstado_cobro());
			detalle_anexo3.setManual_tarifario(detalle_orden_autorizacion
					.getManual_tarifario());
			// Agreamos el tipo de procesimiento y el estado de cobro
			detalle_anexo3Dao.crear(detalle_anexo3);
		}
	}

	/**
	 * Este metodo me permite crear el anexo 4
	 * 
	 * @param usuarios
	 * @param solicitar_prestador
	 * @param string
	 * */
	public void guardarAnexo4(
			Orden_autorizacion autorizacion,
			List<Detalle_orden_autorizacion> detalle_orden_autorizacionsAutorizados,
			Long tipo_procedimiento, String estado_cobro,
			String prestador_asignado, Usuarios usuarios, Elemento elementoRol) {

		/* cargamos el consecutivo */
		Anexo4_entidad anexo4_entidad = new Anexo4_entidad();
		anexo4_entidad.setCodigo_empresa(autorizacion.getCodigo_empresa());
		anexo4_entidad.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
		anexo4_entidad.setCodigo_solicitud(autorizacion.getCodigo_orden());
		anexo4_entidad.setTipo_servicio(tipo_procedimiento);
		anexo4_entidad.setEstado_cobro(estado_cobro);
		anexo4_entidad = anexo4_entidadDao.consultarParametrizado(anexo4_entidad);
		
		boolean registrar = false;
		if(anexo4_entidad == null){
			registrar = true;
			anexo4_entidad = new Anexo4_entidad();
			anexo4_entidad.setCodigo_empresa(autorizacion.getCodigo_empresa());
			anexo4_entidad.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
			anexo4_entidad.setCodigo_solicitud(autorizacion.getCodigo_orden());
			anexo4_entidad.setTipo_servicio(tipo_procedimiento);
			anexo4_entidad.setEstado_cobro(estado_cobro);
			anexo4_entidad.setGuia_atencion("");
			anexo4_entidad.setPorcentaje_valor(0);
			anexo4_entidad.setSemanas_afiliacion(0);
			anexo4_entidad.setReclamo_bono("");
			anexo4_entidad.setConcepto_moderadora("");
			anexo4_entidad.setValor_moderadora(0);
			anexo4_entidad.setPorcentaje_moderadora(0);
			anexo4_entidad.setValor_max_moderadora(0);
			anexo4_entidad.setConcepto_copago("");
			anexo4_entidad.setValor_copago(0);
			anexo4_entidad.setPorcentaje_copago(0);
			anexo4_entidad.setValor_max_copago(0);
			anexo4_entidad.setConcepto_recuperacion("");
			anexo4_entidad.setValor_recuperacion(0);
			anexo4_entidad.setPorcentaje_recuperacion(0);
			anexo4_entidad.setValor_max_recuperacion(0);
			anexo4_entidad.setConcepto_otro("");
			anexo4_entidad.setValor_otro(0);
			anexo4_entidad.setPorcentaje_otro(0);
			anexo4_entidad.setValor_max_otro(0);
			
			anexo4_entidad.setEntidad("");
			anexo4_entidad.setLeido("N");
			anexo4_entidad.setEstado("");
			anexo4_entidad.setAnexo_constituye("");
			anexo4_entidad.setNro_identificacion_paciente(autorizacion
					.getNro_identificacion());
			anexo4_entidad.setPrestador_asignado(prestador_asignado);
			anexo4_entidad.setCreacion_user(usuarios.getCreacion_user());
			anexo4_entidad.setCreacion_date(new Timestamp(new GregorianCalendar()
			.getTimeInMillis()));
		}
		
		anexo4_entidad.setUbicacion(autorizacion.getUbicacion());
		anexo4_entidad.setServicio(autorizacion.getServicio());
		anexo4_entidad.setCama(autorizacion.getCama());
		anexo4_entidad.setFecha(autorizacion.getFecha());
		anexo4_entidad.setCodigo_prestador(autorizacion.getCodigo_prestador());
		anexo4_entidad.setNro_identificacion_reporta("" + usuarios.getCodigo());
		anexo4_entidad.setNombre_reporta(usuarios != null ? (usuarios
				.getApellidos() + " " + usuarios.getNombres()) : "");
		anexo4_entidad.setCargo_reporta(elementoRol != null ? elementoRol
				.getDescripcion() : "");
		anexo4_entidad.setTel_reporta((usuarios != null ? usuarios.getTel_res()
				: ""));
		anexo4_entidad.setCel_reporta((usuarios != null ? usuarios.getCelular()
				: ""));
		anexo4_entidad.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		anexo4_entidad.setUltimo_user(usuarios.getUltimo_user());
		
		if(!detalle_orden_autorizacionsAutorizados.isEmpty()){
			Detalle_orden_autorizacion detalle_orden_autorizacion = detalle_orden_autorizacionsAutorizados.get(0);
			// alimentamos informacion de anexo
			anexo4_entidad.setTipo_cirugia(detalle_orden_autorizacion.getTipo_quirurgico());
            anexo4_entidad.setObservaciones(detalle_orden_autorizacion.getObservaciones());
		}
		
		if(registrar){
			// En el caso de crear la orden
			String codigo = consecutivoService.crearConsecutivo(
					autorizacion.getCodigo_empresa(),
					autorizacion.getCodigo_sucursal(), "ANEXO_4");
			anexo4_entidad.setCodigo(codigo);
			anexo4_entidadDao.crear(anexo4_entidad);
			consecutivoService.actualizarConsecutivo(
					autorizacion.getCodigo_empresa(),
					autorizacion.getCodigo_sucursal(), "ANEXO_4", codigo);
		}else{
			anexo4_entidadDao.actualizar(anexo4_entidad);
			
			Detalle_anexo4 detalle_anexo4 = new Detalle_anexo4();
			detalle_anexo4.setCodigo_empresa(autorizacion.getCodigo_empresa());
			detalle_anexo4
					.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
			detalle_anexo4.setCodigo_orden(anexo4_entidad.getCodigo());
			detalle_anexo4Dao.eliminarParametrizado(detalle_anexo4); 
		}
		

		/* Creamos los detalles del anexo 4 */
		int consecutivo = 0;
		for (Detalle_orden_autorizacion detalle_orden_autorizacion : detalle_orden_autorizacionsAutorizados) {
			Detalle_anexo4 detalle_anexo4 = new Detalle_anexo4();
			detalle_anexo4.setCodigo_empresa(autorizacion.getCodigo_empresa());
			detalle_anexo4
					.setCodigo_sucursal(autorizacion.getCodigo_sucursal());
			detalle_anexo4.setCodigo_orden(anexo4_entidad.getCodigo());
			detalle_anexo4.setConsecutivo(consecutivo++ + "");
			detalle_anexo4.setCodigo_procedimiento(detalle_orden_autorizacion
					.getCodigo_procedimiento());
			detalle_anexo4.setValor_procedimiento(detalle_orden_autorizacion
					.getValor_procedimiento());
			detalle_anexo4
					.setUnidades(detalle_orden_autorizacion.getUnidades());
			detalle_anexo4.setValor_real(detalle_orden_autorizacion
					.getValor_real());
			detalle_anexo4.setDescuento(detalle_orden_autorizacion
					.getDescuento());
			detalle_anexo4.setIncremento(detalle_orden_autorizacion
					.getIncremento());
			detalle_anexo4.setCodigo_cups(detalle_orden_autorizacion
					.getCodigo_cups());
			detalle_anexo4.setNombre_pcd(detalle_orden_autorizacion
					.getNombre_procedimiento());
			detalle_anexo4.setManual_tarifario(detalle_orden_autorizacion
					.getManual_tarifario());
			detalle_anexo4Dao.crear(detalle_anexo4);
		}
	}

	public void guardarAnulacionAutorizacion(Orden_autorizacion orden_autorizacion,
			Usuarios usuarios) {
		// Anulamos la orden
		orden_autorizacion.setDelete_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		orden_autorizacion.setDelete_user(usuarios.getCodigo());
		orden_autorizacion.setEstado(IConstantesAutorizaciones.ESTADO_ANULADO);
		orden_autorizacionDao.actualizar(orden_autorizacion);
		// log.info("Ordenes actualizada: " + actualizados);

		// Anulamos la Autorizacion
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("estado_anulado", IConstantesAutorizaciones.ESTADO_ANULADO);
		map.put("codigo_empresa", orden_autorizacion.getCodigo_empresa());
		map.put("codigo_sucursal", orden_autorizacion.getCodigo_sucursal());
		map.put("codigo_solicitud", orden_autorizacion.getCodigo_orden());
		anexo4_entidadDao.anularAutorizacion(map);
		// log.info("Ordenes actualizada: " + actualizados);

		// Anulamos la Solicitud
		map = new HashMap<String, Object>();
		map.put("estado_anulado", IConstantesAutorizaciones.ESTADO_ANULADO);
		map.put("codigo_empresa", orden_autorizacion.getCodigo_empresa());
		map.put("codigo_sucursal", orden_autorizacion.getCodigo_sucursal());
		map.put("numero_solicitud", orden_autorizacion.getCodigo_orden());
		anexo3_entidadDao.anularAutorizacion(map);
		// log.info("Ordenes actualizada: " + actualizados);
	}

	public void guardarOrdenDesdePlanTratamiento( 
			Map<String, Object> parametros_guardado) {
		Admision admision = (Admision) parametros_guardado.get(PARAM_ADMISION);
		Sucursal sucursal = (Sucursal) parametros_guardado.get(PARAM_SUCURSAL);
		List<Map<String, Object>> plan_tratamiento = (List<Map<String,Object>>) parametros_guardado.get(PARAM_PLAN_TRATAMIENTO); 
		Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) parametros_guardado.get(PARAM_IMPRESION_DIAGNOSTICA);
		Usuarios usuario = (Usuarios) parametros_guardado.get(PARAM_USUARIO);
		String rol_usuario = (String) parametros_guardado.get(PARAM_ROL_USUARIO);
		
		Orden_autorizacion orden_autorizacion = new Orden_autorizacion();
		orden_autorizacion.setEstado(IConstantesAutorizaciones.ESTADO_AUTORIZACION);
        orden_autorizacion.setTipo(IConstantesAutorizaciones.TIPO_ORDEN_MEDICO);
        orden_autorizacion.setCodigo_empresa(admision.getCodigo_empresa());
		orden_autorizacion.setCodigo_sucursal(admision.getCodigo_sucursal());
		orden_autorizacion.setCodigo_prestador(sucursal.getCodigo_administradora_relacion() != null ? sucursal.getCodigo_administradora_relacion() : ""); 
		orden_autorizacion.setNro_identificacion(admision.getNro_identificacion()); 
		
		orden_autorizacion.setUbicacion(IConstantesAutorizaciones.UBICACION_ANEXO3_CONSULTA_EXTERNA);
		orden_autorizacion.setServicio(""); 
		orden_autorizacion.setCama("");
		orden_autorizacion.setCodigo_persona_autoriza(usuario.getCodigo());
		orden_autorizacion.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		orden_autorizacion.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		orden_autorizacion.setCreacion_user(usuario.getCodigo());
		orden_autorizacion.setUltimo_user(usuario.getCodigo());
		orden_autorizacion.setFecha(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		orden_autorizacion.setCodigo_cie_principal(impresion_diagnostica.getCie_principal());
		orden_autorizacion.setCodigo_cie1(impresion_diagnostica.getCie_relacionado1());
		orden_autorizacion.setCodigo_cie2(impresion_diagnostica.getCie_relacionado2());
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orden", orden_autorizacion);
		map.put("detalle", getDetalle_orden_autorizacions(plan_tratamiento, sucursal));
		map.put("usuario", usuario);   
		map.put("rol", rol_usuario); 
		map.put("prestador_asignado", "S");
		map.put("accion", "registrar") ; // solo se va hacer cuando se carga el plan de tratamiento
		guardarOrden(map);
	}

	private List<Detalle_orden_autorizacion> getDetalle_orden_autorizacions(
			List<Map<String, Object>> plan_tratamiento, Sucursal sucursal) { 
		List<Detalle_orden_autorizacion> dtt_orden_autorizacion = new ArrayList<Detalle_orden_autorizacion>();
		for (Map<String, Object> map_plan_tratamiento : plan_tratamiento) {
			int cantidades_realizadas = (Integer) map_plan_tratamiento
					.get("cantidad");
			Plan_tratamiento plan_tratamientoTemp = (Plan_tratamiento) map_plan_tratamiento
					.get("plan");
			
			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(plan_tratamientoTemp.getCodigo_procedimiento())); 
			procedimientos = procedimientosDao.consultar(procedimientos);
			
			Detalle_orden_autorizacion detalle_orden = new Detalle_orden_autorizacion();
			detalle_orden.setCodigo_empresa(plan_tratamientoTemp.getCodigo_empresa());
			detalle_orden.setCodigo_sucursal(plan_tratamientoTemp.getCodigo_sucursal());
			detalle_orden.setCodigo_procedimiento(plan_tratamientoTemp.getCodigo_procedimiento());
			detalle_orden.setValor_procedimiento(plan_tratamientoTemp.getValor_procedimiento()); 
			detalle_orden.setDescuento(plan_tratamientoTemp.getDescuento());
			detalle_orden.setIncremento(plan_tratamientoTemp.getIncremento());
			detalle_orden.setValor_real(plan_tratamientoTemp.getValor_real());
			detalle_orden.setUnidades(cantidades_realizadas); 
			detalle_orden.setCodigo_cups(plan_tratamientoTemp.getCodigo_cups());
			detalle_orden.setNombre_procedimiento(procedimientos != null ? procedimientos.getDescripcion() : "");
			detalle_orden.setManual_tarifario("");
			detalle_orden.setAutorizado("S");
			detalle_orden.setTipo_servicio(sucursal.getId_configuracion_orden_odontologia()); 
			detalle_orden.setEstado_cobro(IConstantesAutorizaciones.ESTADO_COBRO_MEDICINA_GENERAL);
			detalle_orden.setObservaciones("");  
			detalle_orden.setTipo_quirurgico("");  
			dtt_orden_autorizacion.add(detalle_orden);
		}
		return dtt_orden_autorizacion;
	}

}