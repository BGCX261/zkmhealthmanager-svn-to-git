/*
 * His_atencion_embarazadaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.His_atencion_embarazada;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.dao.His_atencion_embarazadaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_atencion_embarazadaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_atencion_embarazadaService implements Serializable{

	private String limit;

	@Autowired
	private His_atencion_embarazadaDao his_atencion_embarazadaDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public His_atencion_embarazada guardar(Map<String, Object> datos) {
		try {
			His_atencion_embarazada atencion_embarazada = (His_atencion_embarazada) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						atencion_embarazada.getCodigo_empresa(),
						atencion_embarazada.getCodigo_sucursal(),
						"HIS_ATENCION_EMBARAZADA");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				atencion_embarazada.setCodigo_historia(codigo_historia);
				if (consultar(atencion_embarazada) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				his_atencion_embarazadaDao.crear(atencion_embarazada);
				consecutivoService.actualizarConsecutivo(
						atencion_embarazada.getCodigo_empresa(),
						atencion_embarazada.getCodigo_sucursal(),
						"HIS_ATENCION_EMBARAZADA",
						atencion_embarazada.getCodigo_historia());
			} else {
				his_atencion_embarazadaDao.actualizar(atencion_embarazada);
			}
			return atencion_embarazada;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(His_atencion_embarazada his_atencion_embarazada) {
		try {
			if (consultar(his_atencion_embarazada) != null) {
				throw new HealthmanagerException(
						"His_atencion_embarazada ya se encuentra en la base de datos");
			}
			his_atencion_embarazadaDao.crear(his_atencion_embarazada);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(His_atencion_embarazada his_atencion_embarazada) {
		try {
			return his_atencion_embarazadaDao
					.actualizar(his_atencion_embarazada);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_atencion_embarazada consultar(
			His_atencion_embarazada his_atencion_embarazada) {
		try {
			return his_atencion_embarazadaDao
					.consultar(his_atencion_embarazada);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(His_atencion_embarazada his_atencion_embarazada) {
		try {
			return his_atencion_embarazadaDao.eliminar(his_atencion_embarazada);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_atencion_embarazada> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return his_atencion_embarazadaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {

	}

}
