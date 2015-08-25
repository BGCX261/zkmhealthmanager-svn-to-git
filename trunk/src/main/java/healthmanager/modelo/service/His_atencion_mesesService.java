/*
 * His_atencion_mesesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_atencion_meses;
import healthmanager.modelo.dao.His_atencion_mesesDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_atencion_mesesService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_atencion_mesesService implements Serializable{

	private String limit;

	@Autowired
	private His_atencion_mesesDao his_atencion_mesesDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public His_atencion_meses guardar(Map<String, Object> datos) {
		try {

			His_atencion_meses atencion_meses = (His_atencion_meses) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						atencion_meses.getCodigo_empresa(),
						atencion_meses.getCodigo_sucursal(),
						"HIS_ATENCION_MESES");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				atencion_meses.setCodigo_historia(codigo_historia);
				if (consultar(atencion_meses) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				his_atencion_mesesDao.crear(atencion_meses);
				consecutivoService.actualizarConsecutivo(
						atencion_meses.getCodigo_empresa(),
						atencion_meses.getCodigo_sucursal(),
						"HIS_ATENCION_MESES",
						atencion_meses.getCodigo_historia());
			} else {
				his_atencion_mesesDao.actualizar(atencion_meses);
			}
			return atencion_meses;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(His_atencion_meses his_atencion_meses) {
		try {
			if (consultar(his_atencion_meses) != null) {
				throw new HealthmanagerException(
						"His_atencion_meses ya se encuentra en la base de datos");
			}
			his_atencion_mesesDao.crear(his_atencion_meses);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(His_atencion_meses his_atencion_meses) {
		try {
			return his_atencion_mesesDao.actualizar(his_atencion_meses);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_atencion_meses consultar(His_atencion_meses his_atencion_meses) {
		try {
			return his_atencion_mesesDao.consultar(his_atencion_meses);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(His_atencion_meses his_atencion_meses) {
		try {
			return his_atencion_mesesDao.eliminar(his_atencion_meses);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_atencion_meses> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return his_atencion_mesesDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
