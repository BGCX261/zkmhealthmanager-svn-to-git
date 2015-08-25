/*
 * Control_prenatalServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Control_prenatal;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Control_prenatalDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("control_prenatalService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Control_prenatalService implements Serializable{

	private String limit;

	@Autowired
	private Control_prenatalDao control_prenatalDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Control_prenatal guardar(Map<String, Object> datos) {
		try {

			Control_prenatal prenatal = (Control_prenatal) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						prenatal.getCodigo_empresa(),
						prenatal.getCodigo_sucursal(), "CONTROL_PRENATAL");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				prenatal.setCodigo_historia(codigo_historia);
				if (consultar(prenatal) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				control_prenatalDao.crear(prenatal);
				consecutivoService.actualizarConsecutivo(
						prenatal.getCodigo_empresa(),
						prenatal.getCodigo_sucursal(), "CONTROL_PRENATAL",
						prenatal.getCodigo_historia());
			} else {
				control_prenatalDao.actualizar(prenatal);
			}
			return prenatal;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Control_prenatal control_prenatal) {
		try {
			if (consultar(control_prenatal) != null) {
				throw new HealthmanagerException(
						"Control_prenatal ya se encuentra en la base de datos");
			}
			control_prenatalDao.crear(control_prenatal);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Control_prenatal control_prenatal) {
		try {
			return control_prenatalDao.actualizar(control_prenatal);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Control_prenatal consultar(Control_prenatal control_prenatal) {
		try {
			return control_prenatalDao.consultar(control_prenatal);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Control_prenatal control_prenatal) {
		try {
			return control_prenatalDao.eliminar(control_prenatal);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Control_prenatal> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return control_prenatalDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
