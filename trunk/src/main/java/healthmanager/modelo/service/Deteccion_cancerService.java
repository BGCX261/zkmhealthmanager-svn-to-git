/*
 * Deteccion_cancerServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Deteccion_cancer;
import healthmanager.modelo.dao.Deteccion_cancerDao;
import healthmanager.modelo.service.ConsecutivoService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("deteccion_cancerService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Deteccion_cancerService implements Serializable{

	private String limit;

	@Autowired
	private Deteccion_cancerDao deteccion_cancerDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Deteccion_cancer guardar(Map<String, Object> datos) {
		try {

			Deteccion_cancer cancer = (Deteccion_cancer) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						cancer.getCodigo_empresa(),
						cancer.getCodigo_sucursal(), "DETECCION_CANCER");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				cancer.setCodigo_historia(codigo_historia);
				if (consultar(cancer) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				deteccion_cancerDao.crear(cancer);
				consecutivoService.actualizarConsecutivo(
						cancer.getCodigo_empresa(),
						cancer.getCodigo_sucursal(), "DETECCION_CANCER",
						cancer.getCodigo_historia());
			} else {
				deteccion_cancerDao.actualizar(cancer);
			}
			return cancer;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Deteccion_cancer deteccion_cancer) {
		try {
			if (consultar(deteccion_cancer) != null) {
				throw new HealthmanagerException(
						"Deteccion_cancer ya se encuentra en la base de datos");
			}
			deteccion_cancerDao.crear(deteccion_cancer);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Deteccion_cancer deteccion_cancer) {
		try {
			return deteccion_cancerDao.actualizar(deteccion_cancer);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Deteccion_cancer consultar(Deteccion_cancer deteccion_cancer) {
		try {
			return deteccion_cancerDao.consultar(deteccion_cancer);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Deteccion_cancer deteccion_cancer) {
		try {
			return deteccion_cancerDao.eliminar(deteccion_cancer);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Deteccion_cancer> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return deteccion_cancerDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}


}
