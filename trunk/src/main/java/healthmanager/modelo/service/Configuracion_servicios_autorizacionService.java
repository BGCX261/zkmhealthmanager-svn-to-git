/*
 * Configuracion_servicios_autorizacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Configuracion_autorizacion_tipo_usuario;
import healthmanager.modelo.bean.Configuracion_autorizacion_via_ingreso;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Detalle_config_servicios_autorizacion;
import healthmanager.modelo.dao.Configuracion_autorizacion_tipo_usuarioDao;
import healthmanager.modelo.dao.Configuracion_autorizacion_via_ingresoDao;
import healthmanager.modelo.dao.Configuracion_servicios_autorizacionDao;
import healthmanager.modelo.dao.Detalle_config_servicios_autorizacionDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("configuracion_servicios_autorizacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Configuracion_servicios_autorizacionService implements Serializable{

	@Autowired
	private Configuracion_servicios_autorizacionDao configuracion_servicios_autorizacionDao;
	@Autowired
	private Detalle_config_servicios_autorizacionDao detalle_config_servicios_autorizacionDao;
	@Autowired
	private Configuracion_autorizacion_tipo_usuarioDao configuracion_autorizacion_tipo_usuarioDao;
	@Autowired
	private Configuracion_autorizacion_via_ingresoDao configuracion_autorizacion_via_ingresoDao;

	private String limit;

	public static final String PARAM_CONFIGURACION = "_cf";
	public static final String PARAM_DETALLE_CONFIGURACION = "_Dcf";
	public static final String PARAM_ACCION = "_ac";
	public static final String PARAM_CONFIGURACION_VIA_INGRESO = "_cfvi";
	public static final String PARAM_CONFIGURACION_TIPO_USUARIO = "_cftu";

	public void crear(
			Configuracion_servicios_autorizacion configuracion_servicios_autorizacion) {
		try {
			// if(consultar(configuracion_servicios_autorizacion)!=null){
			// throw new
			// HealthmanagerException("Configuracion_servicios_autorizacion ya se encuentra en la base de datos");
			// }
			configuracion_servicios_autorizacionDao
					.crear(configuracion_servicios_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Configuracion_servicios_autorizacion configuracion_servicios_autorizacion) {
		try {
			return configuracion_servicios_autorizacionDao
					.actualizar(configuracion_servicios_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Configuracion_servicios_autorizacion consultar(
			Configuracion_servicios_autorizacion configuracion_servicios_autorizacion) {
		try {
			return configuracion_servicios_autorizacionDao
					.consultar(configuracion_servicios_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Configuracion_servicios_autorizacion configuracion_servicios_autorizacion) {
		try {
			return configuracion_servicios_autorizacionDao
					.eliminar(configuracion_servicios_autorizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Configuracion_servicios_autorizacion> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return configuracion_servicios_autorizacionDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return configuracion_servicios_autorizacionDao.existe(parameters);
	}

	public void guardar(Map<String, Object> map) {
		try {
			Configuracion_servicios_autorizacion configuracion_servicios_autorizacion = (Configuracion_servicios_autorizacion) map
					.get(PARAM_CONFIGURACION);
			List<Detalle_config_servicios_autorizacion> dtt_configs = (List<Detalle_config_servicios_autorizacion>) map
					.get(PARAM_DETALLE_CONFIGURACION);
			List<Configuracion_autorizacion_tipo_usuario> config_auto_tipo = (List<Configuracion_autorizacion_tipo_usuario>) map
					.get(PARAM_CONFIGURACION_TIPO_USUARIO);
			List<Configuracion_autorizacion_via_ingreso> config_via_ingresos = (List<Configuracion_autorizacion_via_ingreso>) map
					.get(PARAM_CONFIGURACION_VIA_INGRESO);
			String accion = (String) map.get(PARAM_ACCION);

			if (dtt_configs.isEmpty()) {
				throw new ValidacionRunTimeException(
						"Para realizar esta opcion es de caracter obligatorio seleccionar un detalle del arbol de servicios");
			}

			if (accion.equalsIgnoreCase("registrar")) {
				crear(configuracion_servicios_autorizacion);
			} else {
				int result = actualizar(configuracion_servicios_autorizacion);
				if (result == 0) {
					throw new ValidacionRunTimeException(
							"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
				}

				// Eliminamos detalles
				Detalle_config_servicios_autorizacion dtt_config_eliminar = new Detalle_config_servicios_autorizacion();
				dtt_config_eliminar
						.setId_configuracion(configuracion_servicios_autorizacion
								.getId());
				detalle_config_servicios_autorizacionDao
						.eliminar(dtt_config_eliminar);

				Configuracion_autorizacion_tipo_usuario config_tipo_usuario = new Configuracion_autorizacion_tipo_usuario();
				config_tipo_usuario
						.setId_configuracion(configuracion_servicios_autorizacion
								.getId());
				configuracion_autorizacion_tipo_usuarioDao
						.eliminar(config_tipo_usuario);

				Configuracion_autorizacion_via_ingreso config_via_ingreso = new Configuracion_autorizacion_via_ingreso();
				config_via_ingreso
						.setId_configuracion(configuracion_servicios_autorizacion
								.getId());
				configuracion_autorizacion_via_ingresoDao
						.eliminar(config_via_ingreso);
			}

			for (Detalle_config_servicios_autorizacion dtt_config : dtt_configs) {
				dtt_config
						.setId_configuracion(configuracion_servicios_autorizacion
								.getId());
				detalle_config_servicios_autorizacionDao.crear(dtt_config);
			}

			for (Configuracion_autorizacion_tipo_usuario configuracion_autorizacion_tipo_usuario : config_auto_tipo) {
				configuracion_autorizacion_tipo_usuario
						.setId_configuracion(configuracion_servicios_autorizacion
								.getId());
				if (configuracion_autorizacion_tipo_usuarioDao
						.consultar(configuracion_autorizacion_tipo_usuario) == null) {
					configuracion_autorizacion_tipo_usuarioDao
							.crear(configuracion_autorizacion_tipo_usuario);
				}
			}

			for (Configuracion_autorizacion_via_ingreso configuracion_autorizacion_via_ingreso : config_via_ingresos) {
				configuracion_autorizacion_via_ingreso
						.setId_configuracion(configuracion_servicios_autorizacion
								.getId());
				if (configuracion_autorizacion_via_ingresoDao
						.consultar(configuracion_autorizacion_via_ingreso) == null) {
					configuracion_autorizacion_via_ingresoDao
							.crear(configuracion_autorizacion_via_ingreso);
				}
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<String> listarServiciosPermitidos(Map<String, Object> parametros) {  
		return configuracion_servicios_autorizacionDao.listarServiciosPermitidos(parametros); 
	}

}