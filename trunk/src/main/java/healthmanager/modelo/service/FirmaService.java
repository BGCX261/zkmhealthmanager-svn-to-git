/*
 * FirmaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Firma;
import healthmanager.modelo.dao.FirmaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("firmaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class FirmaService implements Serializable{

	private String limit;

	@Autowired
	private FirmaDao firmaDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Firma guardar(Map<String, Object> datos) {
		try {

			Firma firma = (Firma) datos.get("codigo_firma");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_firma = consecutivoService.crearConsecutivo(
						firma.getCodigo_empresa(), firma.getCodigo_sucursal(),
						"FIRMA");
				codigo_firma = consecutivoService.getZeroFill(codigo_firma, 10);
				firma.setCodigo_firma(codigo_firma);
				if (consultar(firma) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_firma
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				firmaDao.crear(firma);
				consecutivoService.actualizarConsecutivo(
						firma.getCodigo_empresa(), firma.getCodigo_sucursal(),
						"FIRMA", firma.getCodigo_firma());
			} else {
				firmaDao.actualizar(firma);
			}
			return firma;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Firma firma) {
		try {
			if (consultar(firma) != null) {
				throw new HealthmanagerException(
						"Firma ya se encuentra en la base de datos");
			}
			firmaDao.crear(firma);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Firma firma) {
		try {
			return firmaDao.actualizar(firma);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Firma consultar(Firma firma) {
		try {
			return firmaDao.consultar(firma);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Firma firma) {
		try {
			return firmaDao.eliminar(firma);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Firma> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return firmaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
	
}
