/*
 * Plantilla_pypServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Detalle_plantilla_pyp;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.dao.Detalle_plantilla_pypDao;
import healthmanager.modelo.dao.Plantilla_pypDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("plantilla_pypService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Plantilla_pypService implements Serializable{

	@Autowired
	private Plantilla_pypDao plantilla_pypDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Detalle_plantilla_pypDao detalle_plantilla_pypDao;

	public void crear(Plantilla_pyp plantilla_pyp) {
		try {
			if (consultar(plantilla_pyp) != null) {
				throw new HealthmanagerException(
						"Plantilla_pyp ya se encuentra en la base de datos");
			}
			plantilla_pypDao.crear(plantilla_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Plantilla_pyp plantilla_pyp) {
		try {
			return plantilla_pypDao.actualizar(plantilla_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Plantilla_pyp consultar(Plantilla_pyp plantilla_pyp) {
		try {
			return plantilla_pypDao.consultar(plantilla_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Plantilla_pyp plantilla_pyp) {
		try {
			return plantilla_pypDao.eliminar(plantilla_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Plantilla_pyp> listar(Map<String, Object> parameter) {
		try {
			return plantilla_pypDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listarMetasPyp(Map<String, Object> map) {
		return plantilla_pypDao.listarMetasPyp(map);
	}

	public void guardar(Map<String, Object> map) {
		try {
			Plantilla_pyp plantilla_pyp = (Plantilla_pyp) map
					.get("plantilla_pyp");
			String accion = (String) map.get("accion");
			List<Detalle_plantilla_pyp> plantilla_pyps = (List<Detalle_plantilla_pyp>) map
					.get("validaciones");
			if (accion.equalsIgnoreCase("registrar")) {
				plantilla_pypDao.crear(plantilla_pyp);
			} else {
				plantilla_pypDao.actualizar(plantilla_pyp);
			}

			// Actualizamos las restricciones
			Detalle_plantilla_pyp detalle_plantilla_pyp = new Detalle_plantilla_pyp();
			detalle_plantilla_pyp.setArea_intervencion(plantilla_pyp
					.getArea_intervencion());
			detalle_plantilla_pyp.setId_actividad(plantilla_pyp.getId());
			detalle_plantilla_pypDao.eliminar(detalle_plantilla_pyp);

			// Registramos los detalles de la plantilla
			if (plantilla_pyp.getValidaciones().equals("S")) {
				int i = 0;
				for (Detalle_plantilla_pyp detalle_plantilla_pypTemp : plantilla_pyps) {
					detalle_plantilla_pypTemp
							.setArea_intervencion(plantilla_pyp
									.getArea_intervencion());
					detalle_plantilla_pypTemp.setId_actividad(plantilla_pyp
							.getId());
					detalle_plantilla_pypTemp.setConsecutivo(++i + "");
					detalle_plantilla_pypTemp.setMaximo(0);

					if (detalle_plantilla_pypTemp.getUnidad_med_edad_final()
							.trim().isEmpty()) {
						throw new ValidacionRunTimeException(
								"El campo unidad de medida para la edad inicial en la fila "
										+ i + " es de caracter obligatorio.");
					}

					if (detalle_plantilla_pypTemp.getUnidad_med_edad_inicial()
							.trim().isEmpty()) {
						throw new ValidacionRunTimeException(
								"El campo unidad de medida para la edad final en la fila "
										+ i + " no puede ser vacio.");
					}

					if (detalle_plantilla_pypTemp.getUnidad_tiempo().trim()
							.isEmpty()) {
						throw new ValidacionRunTimeException(
								"El campo unidad de medida para la frecuencia en la fila "
										+ i + " no puede ser vacio.");
					}

					if (detalle_plantilla_pypDao
							.consultar(detalle_plantilla_pypTemp) != null) {
						detalle_plantilla_pypDao
								.actualizar(detalle_plantilla_pypTemp);
					} else {
						detalle_plantilla_pypDao
								.crear(detalle_plantilla_pypTemp);
					}
				}
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}

	}

	public boolean existe(Map<String, Object> param) {
		return detalle_plantilla_pypDao.existe(param);
	}

}
