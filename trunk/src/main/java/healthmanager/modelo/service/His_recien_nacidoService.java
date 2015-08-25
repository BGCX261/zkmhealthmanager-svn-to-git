/*
 * His_recien_nacidoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_recien_nacido;
import healthmanager.modelo.dao.His_recien_nacidoDao;
import healthmanager.modelo.service.ConsecutivoService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_recien_nacidoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_recien_nacidoService implements Serializable{

	private String limit;

	@Autowired
	private His_recien_nacidoDao his_recien_nacidoDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public His_recien_nacido guardar(Map<String, Object> datos) {
		try {

			His_recien_nacido nacido = (His_recien_nacido) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						nacido.getCodigo_empresa(),
						nacido.getCodigo_sucursal(), "HIS_RECIEN_NACIDO");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				nacido.setCodigo_historia(codigo_historia);
				if (consultar(nacido) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				his_recien_nacidoDao.crear(nacido);
				consecutivoService.actualizarConsecutivo(
						nacido.getCodigo_empresa(),
						nacido.getCodigo_sucursal(), "HIS_RECIEN_NACIDO",
						nacido.getCodigo_historia());
			} else {
				his_recien_nacidoDao.actualizar(nacido);
			}
			return nacido;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(His_recien_nacido his_recien_nacido) {
		try {
			if (consultar(his_recien_nacido) != null) {
				throw new HealthmanagerException(
						"His_recien_nacido ya se encuentra en la base de datos");
			}
			his_recien_nacidoDao.crear(his_recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(His_recien_nacido his_recien_nacido) {
		try {
			return his_recien_nacidoDao.actualizar(his_recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_recien_nacido consultar(His_recien_nacido his_recien_nacido) {
		try {
			return his_recien_nacidoDao.consultar(his_recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(His_recien_nacido his_recien_nacido) {
		try {
			return his_recien_nacidoDao.eliminar(his_recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_recien_nacido> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return his_recien_nacidoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
