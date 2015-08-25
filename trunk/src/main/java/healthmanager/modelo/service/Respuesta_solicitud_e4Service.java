/*
 * Respuesta_solicitud_e4ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Respuesta_solicitud_e4;
import healthmanager.modelo.bean.Solicitud_e1;
import healthmanager.modelo.dao.Respuesta_solicitud_e4Dao;
import healthmanager.modelo.dao.Solicitud_e1Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("respuesta_solicitud_e4Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Respuesta_solicitud_e4Service implements Serializable{

	private String limit;

	@Autowired
	private Respuesta_solicitud_e4Dao respuesta_solicitud_e4Dao;
	@Autowired
	private Solicitud_e1Dao solicitudE1Dao;

	public void crear(Respuesta_solicitud_e4 respuesta_solicitud_e4) {
		try {
			if (consultar(respuesta_solicitud_e4) != null) {
				throw new HealthmanagerException(
						"Respuesta_solicitud_e4 ya se encuentra en la base de datos");
			}
			actualizarSolicitud(respuesta_solicitud_e4);
			respuesta_solicitud_e4Dao.crear(respuesta_solicitud_e4);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private void actualizarSolicitud(
			Respuesta_solicitud_e4 respuestaSolicitudE4) {
		System.out.println("Actualizar solicitud");
		Solicitud_e1 solicitudE1 = new Solicitud_e1();
		solicitudE1.setCodigo_empresa(respuestaSolicitudE4.getCodigo_empresa());
		solicitudE1.setCodigo_sucursal(respuestaSolicitudE4
				.getCodigo_sucursal());
		solicitudE1.setNro_identificacion(respuestaSolicitudE4
				.getNro_identificacion());
		solicitudE1 = solicitudE1Dao.consultar(solicitudE1);
		System.out.println("Actualizar solicitud: " + solicitudE1);
		solicitudE1.setEstado_respuesta(respuestaSolicitudE4
				.getEstado_translado());
		solicitudE1Dao.actualizar(solicitudE1);
	}

	public int actualizar(Respuesta_solicitud_e4 respuesta_solicitud_e4) {
		try {
			return respuesta_solicitud_e4Dao.actualizar(respuesta_solicitud_e4);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Respuesta_solicitud_e4 consultar(
			Respuesta_solicitud_e4 respuesta_solicitud_e4) {
		try {
			return respuesta_solicitud_e4Dao.consultar(respuesta_solicitud_e4);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Respuesta_solicitud_e4 respuesta_solicitud_e4) {
		try {
			return respuesta_solicitud_e4Dao.eliminar(respuesta_solicitud_e4);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Respuesta_solicitud_e4> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return respuesta_solicitud_e4Dao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map> listarMap(Map param) {
		param.put("limit", limit);
		return solicitudE1Dao.listarMap(param);
	}
}
