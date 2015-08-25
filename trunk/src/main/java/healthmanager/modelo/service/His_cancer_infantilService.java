/*
 * His_cancer_infantilServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_cancer_infantil;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.His_cancer_infantilDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_cancer_infantilService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_cancer_infantilService implements Serializable{

	private String limit;

	@Autowired
	private His_cancer_infantilDao his_cancer_infantilDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public His_cancer_infantil guardar(Map<String, Object> datos) {
		try {

			His_cancer_infantil cancer_infantil = (His_cancer_infantil) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						cancer_infantil.getCodigo_empresa(),
						cancer_infantil.getCodigo_sucursal(),
						"HIS_CANCER_INFANTIL");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				cancer_infantil.setCodigo_historia(codigo_historia);
				if (consultar(cancer_infantil) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				his_cancer_infantilDao.crear(cancer_infantil);
				consecutivoService.actualizarConsecutivo(
						cancer_infantil.getCodigo_empresa(),
						cancer_infantil.getCodigo_sucursal(),
						"HIS_CANCER_INFANTIL",
						cancer_infantil.getCodigo_historia());
			} else {
				his_cancer_infantilDao.actualizar(cancer_infantil);
			}
			return cancer_infantil;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(His_cancer_infantil his_cancer_infantil) {
		try {
			if (consultar(his_cancer_infantil) != null) {
				throw new HealthmanagerException(
						"His_cancer_infantil ya se encuentra en la base de datos");
			}
			his_cancer_infantilDao.crear(his_cancer_infantil);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(His_cancer_infantil his_cancer_infantil) {
		try {
			return his_cancer_infantilDao.actualizar(his_cancer_infantil);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_cancer_infantil consultar(His_cancer_infantil his_cancer_infantil) {
		try {
			return his_cancer_infantilDao.consultar(his_cancer_infantil);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(His_cancer_infantil his_cancer_infantil) {
		try {
			return his_cancer_infantilDao.eliminar(his_cancer_infantil);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_cancer_infantil> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return his_cancer_infantilDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
