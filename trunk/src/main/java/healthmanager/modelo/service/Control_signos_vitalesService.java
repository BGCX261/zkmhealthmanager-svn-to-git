/*
 * Control_signos_vitalesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Control_signos_vitales;
import healthmanager.modelo.dao.Control_signos_vitalesDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("control_signos_vitalesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Control_signos_vitalesService implements Serializable{

	@Autowired
	private Control_signos_vitalesDao control_signos_vitalesDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;

	public void guardar(Map<String, Object> datos) {
		try {
			Control_signos_vitales control_signos_vitales = (Control_signos_vitales) datos
					.get("control_signos_vitales");
			//String accion = (String) datos.get("accion");

			List<Control_signos_vitales> listado_componentes_control = (List<Control_signos_vitales>) datos
					.get("listado_componentes_control");

			if (listado_componentes_control != null) {
				for (Control_signos_vitales control_signos : listado_componentes_control) {
					if (control_signos.getConsecutivo() == null) {
						String codigo_historia = consecutivoService
								.crearConsecutivo(control_signos_vitales
										.getCodigo_empresa(),
										control_signos_vitales
												.getCodigo_sucursal(),
										IConstantes.CONS_CONTROL_SIGNOS);

						codigo_historia = consecutivoService.getZeroFill(
								codigo_historia, 10);

						control_signos.setConsecutivo(codigo_historia);

						control_signos_vitalesDao.crear(control_signos);

						consecutivoService.actualizarConsecutivo(
								control_signos.getCodigo_empresa(),
								control_signos.getCodigo_sucursal(),
								IConstantes.CONS_CONTROL_SIGNOS,
								control_signos.getConsecutivo());
					} else {

						control_signos_vitalesDao.actualizar(control_signos);

					}
				}
			}

		} catch (Exception e) {

			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Control_signos_vitales control_signos_vitales) {
		try {
			if (consultar(control_signos_vitales) != null) {
				throw new HealthmanagerException(
						"Control_signos_vitales ya se encuentra en la base de datos");
			}
			control_signos_vitalesDao.crear(control_signos_vitales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Control_signos_vitales control_signos_vitales) {
		try {
			return control_signos_vitalesDao.actualizar(control_signos_vitales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Control_signos_vitales consultar(
			Control_signos_vitales control_signos_vitales) {
		try {
			return control_signos_vitalesDao.consultar(control_signos_vitales);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Map<String, Object> parameters) {
		try {
			return control_signos_vitalesDao.eliminar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Control_signos_vitales> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return control_signos_vitalesDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return control_signos_vitalesDao.existe(parameters);
	}

}