/*
 * FurtranServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Furtran;
import healthmanager.modelo.dao.FurtranDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("furtranService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class FurtranService implements Serializable{

	@Autowired
	private FurtranDao furtranDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;

	public Furtran guardar(Map<String, Object> datos) {
		try {

			Furtran furtran = (Furtran) datos.get("furtran");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String consecutivo = consecutivoService.crearConsecutivo(
						furtran.getCodigo_empresa(),
						furtran.getCodigo_sucursal(), IConstantes.CONS_FURTRAN);
				consecutivo = consecutivoService.getZeroFill(consecutivo, 10);

				furtran.setConsecutivo(consecutivo);

				if (consultar(furtran) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ consecutivo
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				furtranDao.crear(furtran);
				consecutivoService.actualizarConsecutivo(
						furtran.getCodigo_empresa(),
						furtran.getCodigo_sucursal(), IConstantes.CONS_FURTRAN,
						furtran.getConsecutivo());
				// //log.info("5: "+codigo_historia);

			} else {
				//
				// if (consultar(furtran) == null)
				// throw new
				// Exception("El dato que desea actualizar no existe en la base de datos");

				furtranDao.actualizar(furtran);
			}

			return furtran;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Furtran furtran) {
		try {
			if (consultar(furtran) != null) {
				throw new HealthmanagerException(
						"Furtran ya se encuentra en la base de datos");
			}
			furtranDao.crear(furtran);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Furtran furtran) {
		try {
			return furtranDao.actualizar(furtran);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Furtran consultar(Furtran furtran) {
		try {
			return furtranDao.consultar(furtran);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Furtran furtran) {
		try {
			return furtranDao.eliminar(furtran);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Furtran> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return furtranDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return furtranDao.existe(parameters);
	}

	public Furtran consultarPorParametros(Furtran furtran) {
		return furtranDao.consultarPorParametros(furtran);
	}

}