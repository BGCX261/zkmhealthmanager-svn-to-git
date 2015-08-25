/*
 * Agudeza_visualServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Agudeza_visualDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("agudeza_visualService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Agudeza_visualService implements Serializable{

	@Autowired
	private Agudeza_visualDao agudeza_visualDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Agudeza_visual guardar(Map<String, Object> datos) {
		try {

			Agudeza_visual visual = (Agudeza_visual) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						visual.getCodigo_empresa(),
						visual.getCodigo_sucursal(), "AGUDEZA_VISUAL");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				visual.setCodigo_historia(Long.valueOf(codigo_historia));
				if (consultar(visual) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				agudeza_visualDao.crear(visual);
				consecutivoService.actualizarConsecutivo(
						visual.getCodigo_empresa(),
						visual.getCodigo_sucursal(), "AGUDEZA_VISUAL",
						visual.getCodigo_historia() + "");
			} else {
				agudeza_visualDao.actualizar(visual);
			}
			return visual;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Agudeza_visual agudeza_visual) {
		try {
			if (consultar(agudeza_visual) != null) {
				throw new HealthmanagerException(
						"Agudeza_visual ya se encuentra en la base de datos");
			}
			agudeza_visualDao.crear(agudeza_visual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Agudeza_visual agudeza_visual) {
		try {
			return agudeza_visualDao.actualizar(agudeza_visual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Agudeza_visual consultar(Agudeza_visual agudeza_visual) {
		try {
			return agudeza_visualDao.consultar(agudeza_visual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Agudeza_visual consultarAnio(Agudeza_visual agudeza_visual) {
		try {
			return agudeza_visualDao.consultarAnio(agudeza_visual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Agudeza_visual agudeza_visual) {
		try {
			return agudeza_visualDao.eliminar(agudeza_visual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Agudeza_visual> listar(Map<String, Object> parameter) {
		try {
			return agudeza_visualDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
