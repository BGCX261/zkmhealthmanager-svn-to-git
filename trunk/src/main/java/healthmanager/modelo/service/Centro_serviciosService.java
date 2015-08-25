/*
 * Centro_serviciosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_servicios;
import healthmanager.modelo.bean.Servicios_via_ingreso;
import healthmanager.modelo.dao.Centro_serviciosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("centro_serviciosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Centro_serviciosService implements Serializable{

	@Autowired
	private Centro_serviciosDao centro_serviciosDao;
	private String limit;

	public void crear(Centro_servicios centro_servicios) {
		try {
			if (consultar(centro_servicios) != null) {
				throw new HealthmanagerException(
						"Centro_servicios ya se encuentra en la base de datos");
			}
			centro_serviciosDao.crear(centro_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardar(Map<String, Object> map) {
		Map<String, Centro_atencion> mapa_centros = (Map<String, Centro_atencion>) map
				.get("centros_salud");
		Map<String, Servicios_via_ingreso> mapa_servicios = (Map<String, Servicios_via_ingreso>) map
				.get("servicios");
		String codigo_usuario = (String) map.get("codigo_usuario");

		for (String codigo_centro : mapa_centros.keySet()) {
			Centro_atencion centro_atencion = mapa_centros.get(codigo_centro);

			Centro_servicios centro_servicios = new Centro_servicios();
			centro_servicios.setCodigo_empresa(centro_atencion
					.getCodigo_empresa());
			centro_servicios.setCodigo_sucursal(centro_atencion
					.getCodigo_sucursal());
			centro_servicios.setCodigo_centro(centro_atencion
					.getCodigo_centro());
			centro_serviciosDao.eliminar(centro_servicios);

			for (String codigo_servicio : mapa_servicios.keySet()) {
				Servicios_via_ingreso servicios_via_ingreso = mapa_servicios
						.get(codigo_servicio);
				centro_servicios = new Centro_servicios();
				centro_servicios.setCodigo_empresa(centro_atencion
						.getCodigo_empresa());
				centro_servicios.setCodigo_sucursal(centro_atencion
						.getCodigo_sucursal());
				centro_servicios.setCodigo_centro(centro_atencion
						.getCodigo_centro());
				centro_servicios.setServicio_via_ingreso(servicios_via_ingreso
						.getCodigo_registro());
				centro_servicios.setUltimo_update(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				centro_servicios.setUltimo_user(codigo_usuario);
				centro_servicios.setCreacion_user(codigo_usuario);
				centro_servicios.setCreacion_date(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				centro_serviciosDao.crear(centro_servicios);
			}
		}
	}

	public int actualizar(Centro_servicios centro_servicios) {
		try {
			return centro_serviciosDao.actualizar(centro_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Centro_servicios consultar(Centro_servicios centro_servicios) {
		try {
			return centro_serviciosDao.consultar(centro_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Centro_servicios centro_servicios) {
		try {
			return centro_serviciosDao.eliminar(centro_servicios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Centro_servicios> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return centro_serviciosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return centro_serviciosDao.existe(parameters);
	}

}