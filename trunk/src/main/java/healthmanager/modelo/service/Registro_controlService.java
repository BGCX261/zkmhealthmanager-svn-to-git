/*
 * Registro_controlServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Registro_control;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Registro_controlDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("registro_controlService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Registro_controlService implements Serializable{

	private String limit;

	@Autowired
	private Registro_controlDao registro_controlDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Registro_control guardar(Map<String, Object> datos) {
		try {
			Registro_control registro_control = (Registro_control) datos
					.get("codigo_registro");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_registro = consecutivoService.crearConsecutivo(
						registro_control.getCodigo_empresa(),
						registro_control.getCodigo_sucursal(),
						"REGISTRO_CONTROL");
				codigo_registro = consecutivoService.getZeroFill(
						codigo_registro, 10);
				registro_control.setCodigo_registro(codigo_registro);
				if (consultar(registro_control) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_registro
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				registro_controlDao.crear(registro_control);
				consecutivoService.actualizarConsecutivo(
						registro_control.getCodigo_empresa(),
						registro_control.getCodigo_sucursal(),
						"REGISTRO_CONTROL",
						registro_control.getCodigo_registro());
			} else {
				registro_controlDao.actualizar(registro_control);
			}
			return registro_control;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Registro_control registro_control) {
		try {
			if (consultar(registro_control) != null) {
				throw new HealthmanagerException(
						"Registro_control ya se encuentra en la base de datos");
			}
			registro_controlDao.crear(registro_control);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Registro_control registro_control) {
		try {
			return registro_controlDao.actualizar(registro_control);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Registro_control consultar(Registro_control registro_control) {
		try {
			return registro_controlDao.consultar(registro_control);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Registro_control registro_control) {
		try {
			return registro_controlDao.eliminar(registro_control);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Registro_control> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return registro_controlDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public void setLimit(String limit) {
		this.limit = limit;
	}

}
