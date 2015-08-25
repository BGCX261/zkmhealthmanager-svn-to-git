/*
 * His_partoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_parto;
import healthmanager.modelo.dao.His_partoDao;
import healthmanager.modelo.service.ConsecutivoService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_partoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_partoService implements Serializable{

	private String limit;

	@Autowired
	private His_partoDao his_partoDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public His_parto guardar(Map<String, Object> datos) {
		try {

			His_parto parto = (His_parto) datos.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						parto.getCodigo_empresa(), parto.getCodigo_sucursal(),
						"HIS_PARTO");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				parto.setCodigo_historia(codigo_historia);
				if (consultar(parto) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				his_partoDao.crear(parto);
				consecutivoService.actualizarConsecutivo(
						parto.getCodigo_empresa(), parto.getCodigo_sucursal(),
						"HIS_PARTO", parto.getCodigo_historia());
			} else {
				his_partoDao.actualizar(parto);
			}
			return parto;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(His_parto his_parto) {
		try {
			if (consultar(his_parto) != null) {
				throw new HealthmanagerException(
						"His_parto ya se encuentra en la base de datos");
			}
			his_partoDao.crear(his_parto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(His_parto his_parto) {
		try {
			return his_partoDao.actualizar(his_parto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_parto consultar(His_parto his_parto) {
		try {
			return his_partoDao.consultar(his_parto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(His_parto his_parto) {
		try {
			return his_partoDao.eliminar(his_parto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_parto> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return his_partoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
