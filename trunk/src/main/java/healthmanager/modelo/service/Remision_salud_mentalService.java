/*
 * Remision_salud_mentalServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.service.ConsecutivoService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Remision_salud_mental;
import healthmanager.modelo.dao.Remision_salud_mentalDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("remision_salud_mentalService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Remision_salud_mentalService implements Serializable{

	private String limit;

	@Autowired
	private Remision_salud_mentalDao remision_salud_mentalDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Remision_salud_mental guardar(Map<String, Object> datos) {
		try {

			Remision_salud_mental mayor = (Remision_salud_mental) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						mayor.getCodigo_empresa(), mayor.getCodigo_sucursal(),
						"REMISION_SALUD_MENTAL");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				mayor.setCodigo_historia(codigo_historia);
				if (consultar(mayor) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				remision_salud_mentalDao.crear(mayor);
				consecutivoService.actualizarConsecutivo(
						mayor.getCodigo_empresa(), mayor.getCodigo_sucursal(),
						"REMISION_SALUD_MENTAL", mayor.getCodigo_historia());
			} else {
				remision_salud_mentalDao.actualizar(mayor);
			}
			return mayor;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Remision_salud_mental remision_salud_mental) {
		try {
			if (consultar(remision_salud_mental) != null) {
				throw new HealthmanagerException(
						"Remision_salud_mental ya se encuentra en la base de datos");
			}
			remision_salud_mentalDao.crear(remision_salud_mental);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Remision_salud_mental remision_salud_mental) {
		try {
			return remision_salud_mentalDao.actualizar(remision_salud_mental);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Remision_salud_mental consultar(
			Remision_salud_mental remision_salud_mental) {
		try {
			return remision_salud_mentalDao.consultar(remision_salud_mental);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Remision_salud_mental remision_salud_mental) {
		try {
			return remision_salud_mentalDao.eliminar(remision_salud_mental);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Remision_salud_mental> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return remision_salud_mentalDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
