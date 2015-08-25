/*
 * Anexo3_entidadServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.controller.ChangePrestadorServicioAction.Change;
import healthmanager.controller.ZKWindow;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Detalle_anexo3;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_orden_autorizacion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Negacion;
import healthmanager.modelo.bean.Orden_autorizacion;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.dao.Anexo3_entidadDao;
import healthmanager.modelo.dao.Detalle_anexo3Dao;
import healthmanager.modelo.dao.ElementoDao;
import healthmanager.modelo.dao.Frecuencia_ordenamientoDao;
import healthmanager.modelo.dao.NegacionDao;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.Main;
import com.framework.res.Res;

@Service("anexo3_entidadService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Anexo3_entidadService implements Serializable{

	private String limit;

	@Autowired
	private Anexo3_entidadDao anexo3_entidadDao;
	@Autowired
	private Frecuencia_ordenamientoDao frecuencia_ordenamientoDao;
	@Autowired
	private Detalle_anexo3Dao detalle_anexo3Dao;
	@Autowired
	private NegacionDao negacionDao;
	@Autowired
	private Orden_autorizacionService orden_autorizacionService;
	@Autowired
	private ElementoDao elementoDao;

	@Autowired
	private ConsecutivoService consecutivoService;

	public static Logger log = Logger.getLogger(ZKWindow.class);

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			Anexo3_entidad anexo3_entidad = (Anexo3_entidad) mapa_datos
					.get("anexo3_entidad");
			String accion = (String) mapa_datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String consecutivo = consecutivoService.crearConsecutivo(
						anexo3_entidad.getCodigo_empresa(),
						anexo3_entidad.getCodigo_sucursal(),
						IConstantes.CONS_ANEXO_3_ENTIDAD);
				String codigo = consecutivoService.getZeroFill(consecutivo, 10);
				anexo3_entidad.setCodigo(codigo);
				anexo3_entidadDao.crear(anexo3_entidad);
				consecutivoService.actualizarConsecutivo(
						anexo3_entidad.getCodigo_empresa(),
						anexo3_entidad.getCodigo_sucursal(),
						IConstantes.CONS_ANEXO_3_ENTIDAD, codigo);
			} else {
				if (anexo3_entidadDao.consultar(anexo3_entidad) != null) {
					anexo3_entidadDao.actualizar(anexo3_entidad);
				}
			}

			List<Detalle_orden> lista_detalle = (List<Detalle_orden>) mapa_datos
					.get("lista_detalle");

			Detalle_anexo3 detalle_anexo3_aux = new Detalle_anexo3();
			detalle_anexo3_aux.setCodigo_empresa(anexo3_entidad
					.getCodigo_empresa());
			detalle_anexo3_aux.setCodigo_sucursal(anexo3_entidad
					.getCodigo_sucursal());
			detalle_anexo3_aux.setCodigo_orden(anexo3_entidad.getCodigo());

			detalle_anexo3Dao.eliminar(detalle_anexo3_aux);

			if (lista_detalle != null && !lista_detalle.isEmpty()) {
				int consecutivo = 1;
				for (Detalle_orden detalle_orden : lista_detalle) {
					Detalle_anexo3 detalle_anexo3 = new Detalle_anexo3();
					detalle_anexo3.setCodigo_empresa(anexo3_entidad
							.getCodigo_empresa());
					detalle_anexo3.setCodigo_sucursal(anexo3_entidad
							.getCodigo_sucursal());
					detalle_anexo3.setCodigo_orden(anexo3_entidad.getCodigo());
					detalle_anexo3.setCodigo_cups(detalle_orden
							.getCodigo_cups());
					detalle_anexo3.setCodigo_procedimiento(detalle_orden
							.getCodigo_procedimiento());
					detalle_anexo3.setCodigo_quien_autoriza(anexo3_entidad
							.getNro_identificacion_reporta());
					detalle_anexo3.setDescuento(detalle_orden.getDescuento());
					detalle_anexo3.setEstado_autorizacion("");
					detalle_anexo3.setEstado_cobro("");
					detalle_anexo3.setIncremento(detalle_orden.getIncremento());
					detalle_anexo3.setNombre_pcd(detalle_orden
							.getNombre_procedimiento());
					detalle_anexo3.setTipo_procedimiento(detalle_orden
							.getTipo_procedimiento());
					detalle_anexo3.setUnidades(detalle_orden.getUnidades());
					detalle_anexo3.setValor_procedimiento(detalle_orden
							.getValor_procedimiento());
					detalle_anexo3.setValor_real(detalle_orden.getValor_real());
					detalle_anexo3.setConsecutivo(consecutivo + "");
					detalle_anexo3Dao.crear(detalle_anexo3);
					consecutivo++;
				}
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Anexo3_entidad anexo3_entidad) {
		try {
			if (consultar(anexo3_entidad) != null) {
				throw new HealthmanagerException(
						"Anexo3_entidad ya se encuentra en la base de datos");
			}
			anexo3_entidadDao.crear(anexo3_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Anexo3_entidad anexo3_entidad) {
		try {
			return anexo3_entidadDao.actualizar(anexo3_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Anexo3_entidad consultar(Anexo3_entidad anexo3_entidad) {
		try {
			return anexo3_entidadDao.consultar(anexo3_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Anexo3_entidad anexo3_entidad) {
		try {
			return anexo3_entidadDao.eliminar(anexo3_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Anexo3_entidad> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return anexo3_entidadDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void guardarNuevosAnexos(Map<String, Object> mapContent,
			List<Map<String, Object>> procedimientosSeQuedan,
			ServiceLocatorWeb serviceLocator, Sucursal sucursal,
			Usuarios usuarios, Anexo3_entidad anexo_3) {
		try {

			/* */
			List<Anexo4_entidad> list = guardarOrdenEliminarAnexo(
					procedimientosSeQuedan, serviceLocator, anexo_3, usuarios);
			Iterator it = mapContent.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Change change = (Change) e.getValue();
				String codigo_prestador = (String) e.getKey();
				Anexo3_entidad anexo3Entidad = change.anexo3Entidad;

				anexo3Entidad.setCodigo_ips(codigo_prestador);
				anexo3Entidad
						.setNeed_autorizacion(change.needAutorizacion ? "S"
								: "N");
				Res.reOrder(change.procedimientos, anexo3Entidad);

				String codigo = Main.obtenerConsecutivo(serviceLocator,
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "ANEXO_3");
				String numero_solicitud = Main.obtenerNro_solicitud(
						serviceLocator, sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "NRO_ANEXO_3");
				DecimalFormat decimalFormat = new DecimalFormat("0000000000");
				numero_solicitud = decimalFormat.format(Double
						.parseDouble(numero_solicitud));

				anexo3Entidad.setCodigo(codigo);
				anexo3Entidad.setNumero_solicitud(numero_solicitud);
				crear(anexo3Entidad);
				Main.actualizarConsecutivo(serviceLocator,
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "ANEXO_3", codigo);
				Main.actualizarConsecutivo(serviceLocator,
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "NRO_ANEXO_3",
						numero_solicitud);

				Anexo4_entidad anexo4_entidadGeneral = Res.generarAnexo4Para(
						anexo3Entidad, "01", serviceLocator, usuarios);
				Anexo4_entidad anexo4_entidadPyp = Res.generarAnexo4Para(
						anexo3Entidad, "02", serviceLocator, usuarios);
				Anexo4_entidad anexo4_entidad = Res.generarAnexo4Para(
						anexo3Entidad, "03", serviceLocator, usuarios);

				/* */
				if (anexo4_entidadGeneral != null)
					anexo4_entidadGeneral.setAnexo_constituye(Res.getAnexoTipo(
							list, "01"));
				if (anexo4_entidadPyp != null)
					anexo4_entidadPyp.setAnexo_constituye(Res.getAnexoTipo(
							list, "02"));
				if (anexo4_entidad != null)
					anexo4_entidad.setAnexo_constituye(Res.getAnexoTipo(list,
							"03"));

				/* actualizamos, los anexos */
				serviceLocator.getAnexo4EntidadService().actualizar(
						anexo4_entidadGeneral);
				serviceLocator.getAnexo4EntidadService().actualizar(
						anexo4_entidadPyp);
				serviceLocator.getAnexo4EntidadService().actualizar(
						anexo4_entidad);

			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private List<Anexo4_entidad> guardarOrdenEliminarAnexo(
			List<Map<String, Object>> maps, ServiceLocatorWeb serviceLocator,
			Anexo3_entidad anexo_3, Usuarios usuarios) throws Exception {
		List<Anexo4_entidad> anexo4_entidads = eliminarLosAnexo4Anteriores(
				serviceLocator, anexo_3);
		if (maps.isEmpty()) {
			serviceLocator.getAnexo3EntidadService().eliminar(anexo_3);
		} else {
			Res.reOrder(maps, anexo_3);
			serviceLocator.getAnexo3EntidadService().actualizar(anexo_3);
			Anexo4_entidad anexo4_entidadGeneral = Res.generarAnexo4Para(
					anexo_3, "01", serviceLocator, usuarios);
			Anexo4_entidad anexo4_entidadPyp = Res.generarAnexo4Para(anexo_3,
					"02", serviceLocator, usuarios);
			Anexo4_entidad anexo4_entidad = Res.generarAnexo4Para(anexo_3,
					"03", serviceLocator, usuarios);

			/* */
			if (anexo4_entidadGeneral != null)
				anexo4_entidadGeneral.setAnexo_constituye(Res.getAnexoTipo(
						anexo4_entidads, "01"));
			if (anexo4_entidadPyp != null)
				anexo4_entidadPyp.setAnexo_constituye(Res.getAnexoTipo(
						anexo4_entidads, "02"));
			if (anexo4_entidad != null)
				anexo4_entidad.setAnexo_constituye(Res.getAnexoTipo(
						anexo4_entidads, "03"));

			/* actualizamos, los anexos */
			serviceLocator.getAnexo4EntidadService().actualizar(
					anexo4_entidadGeneral);
			serviceLocator.getAnexo4EntidadService().actualizar(
					anexo4_entidadPyp);
			serviceLocator.getAnexo4EntidadService().actualizar(anexo4_entidad);
		}
		return anexo4_entidads;
	}

	private List<Anexo4_entidad> eliminarLosAnexo4Anteriores(
			ServiceLocatorWeb serviceLocator, Anexo3_entidad anexo_3) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", anexo_3.getCodigo_empresa());
		map.put("codigo_sucursal", anexo_3.getCodigo_sucursal());
		map.put("codigo_solicitud", anexo_3.getCodigo());
		List<Anexo4_entidad> anexo4_entidads = serviceLocator
				.getAnexo4EntidadService().listar(map);
		for (Anexo4_entidad anexo4_entidad : anexo4_entidads) {
			anexo4_entidad.setEstado("02"); // cancelado
			serviceLocator.getAnexo4EntidadService().actualizar(anexo4_entidad);
		}
		return anexo4_entidads;
	}

	/**
	 * Este metodo me permite, guardar una autorizacion de los procedimentos.
	 * 
	 * @author Luis Miguel
	 * */
	public void guardarAutorizacion(Map<String, Object> map) {
		try {
			// Cargamos parametros
			List<Map<String, Object>> list = (List<Map<String, Object>>) map
					.get(IConstantesAutorizaciones.PARAM_DETALLE_SERVICIO);
			Anexo3_entidad anexo3_entidad = (Anexo3_entidad) map
					.get(IConstantesAutorizaciones.PARAM_ANEXO_3);
			Usuarios usuarios = (Usuarios) map
					.get(IConstantesAutorizaciones.PARAM_USUARIO);
			String rol = (String) map.get(IConstantesAutorizaciones.PARAM_ROL);
			// Paciente paciente = (Paciente)
			// map.get(IConstantesAutorizaciones.PARAM_PACIENTE);

			// Cambiamos el estado de autorizacion
			anexo3_entidad
					.setEstado(IConstantesAutorizaciones.ESTADO_ANEXO3_AUDITADO);
			anexo3_entidadDao.actualizar(anexo3_entidad);

			Elemento elemento = new Elemento();
			elemento.setCodigo(rol);
			elemento.setTipo("rol");
			elemento = elementoDao.consultar(elemento);

			Map<String, List<Detalle_anexo3>> mapServicios = new HashMap<String, List<Detalle_anexo3>>();
			for (Map<String, Object> detalles : list) {
				Detalle_anexo3 detalle_temp = (Detalle_anexo3) detalles
						.get(IConstantesAutorizaciones.PARAM_DETALLE_SERVICIO);
				detalle_anexo3Dao.actualizar(detalle_temp);
				if (detalle_temp
						.getEstado_autorizacion()
						.equals(IConstantesAutorizaciones.ESTADO_AUTORIZACION_SOLICITUD_AUTORIZADO)) {
					String llave = detalle_temp.getTipo_servicio() + "-"
							+ detalle_temp.getEstado_cobro();
					if (mapServicios.containsKey(llave)) {
						List<Detalle_anexo3> detalle_orden_autorizacions = mapServicios
								.get(llave);
						detalle_orden_autorizacions.add(detalle_temp);
					} else {
						List<Detalle_anexo3> listTem = new ArrayList<Detalle_anexo3>();
						listTem.add(detalle_temp);
						mapServicios.put(llave, listTem);
					}
				} else {
					Negacion negacion = (Negacion) detalles
							.get(IConstantesAutorizaciones.PARAM_NEGACION);
					if (negacion != null) {
						// Cargamos datos en negacion
						negacion.setNro_solicitud(anexo3_entidad.getCodigo());
						negacion.setNombre(detalle_temp.getNombre_pcd());
						negacion.setCodigo_servicio(detalle_temp
								.getCodigo_procedimiento());

						// Actualizamos y creamos negacion de servicio
						negacionDao.crear(negacion);
					} else {
						throw new ValidacionRunTimeException(
								"Falta negacion para detalle de servicio no autorizado");
					}
				}
			}

			// Creamos autorizaciones de procedimiento
			Orden_autorizacion autorizacion = new Orden_autorizacion();
			autorizacion.setCodigo_empresa(anexo3_entidad.getCodigo_empresa());
			autorizacion
					.setCodigo_sucursal(anexo3_entidad.getCodigo_sucursal());
			autorizacion.setCodigo_orden(anexo3_entidad.getNumero_solicitud());
			autorizacion = orden_autorizacionService.consultar(autorizacion);

			if (autorizacion != null) {
				Iterator it = mapServicios.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry e = (Map.Entry) it.next();
					String llave = e.getKey().toString();
					String servicio = llave.replaceAll("[-].+", "");
					String estado_cobro = llave.replaceAll(".+[-]", "");

					if (!servicio.trim().isEmpty()
							&& servicio.matches("[0-9]*$")) {
						List<Detalle_orden_autorizacion> detalle_orden_autorizacions = Res
								.convertirListaDetalleAnexo3ADetalleOrden(
										(List<Detalle_anexo3>) e.getValue(),
										anexo3_entidad);
						orden_autorizacionService.guardarAnexo4(autorizacion,
								detalle_orden_autorizacions,
								Long.parseLong(servicio), estado_cobro, "N",
								usuarios, elemento);
					} else {
						throw new ValidacionRunTimeException(
								"El tipo de servicion no es validado ("
										+ servicio + ")");
					}
				}
			} else {
				throw new ValidacionRunTimeException(
						"Para realizar esta accion debe haber una orden");
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
