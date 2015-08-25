/*
 * Registro_detalleServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Registro_detalle;
import healthmanager.modelo.dao.Registro_detalleDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("registro_detalleService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Registro_detalleService implements Serializable{

	private String limit;

	@Autowired
	private Registro_detalleDao registro_detalleDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Registro_detalle guardar(Map<String, Object> datos) {
		try {

			Registro_detalle registro_detalle = (Registro_detalle) datos
					.get("codigo_detalle");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_detalle = consecutivoService.crearConsecutivo(
						registro_detalle.getCodigo_empresa(),
						registro_detalle.getCodigo_sucursal(),
						"REGISTRO_DETALLE");
				codigo_detalle = consecutivoService.getZeroFill(codigo_detalle,
						10);
				registro_detalle.setCodigo_detalle(codigo_detalle);
				if (consultar(registro_detalle) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_detalle
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				registro_detalleDao.crear(registro_detalle);
				consecutivoService.actualizarConsecutivo(
						registro_detalle.getCodigo_empresa(),
						registro_detalle.getCodigo_sucursal(),
						"REGISTRO_DETALLE",
						registro_detalle.getCodigo_detalle());
			} else {
				registro_detalleDao.actualizar(registro_detalle);
			}
			return registro_detalle;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Registro_detalle registro_detalle) {
		try {
			if (consultar(registro_detalle) != null) {
				throw new HealthmanagerException(
						"Registro_detalle ya se encuentra en la base de datos");
			}
			registro_detalleDao.crear(registro_detalle);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Registro_detalle registro_detalle) {
		try {
			return registro_detalleDao.actualizar(registro_detalle);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Registro_detalle consultar(Registro_detalle registro_detalle) {
		try {
			return registro_detalleDao.consultar(registro_detalle);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Registro_detalle registro_detalle) {
		try {
			return registro_detalleDao.eliminar(registro_detalle);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Registro_detalle> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return registro_detalleDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
