/*
 * Grupos_etareosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Condiciones_grupos_etareos;
import healthmanager.modelo.bean.Grupos_etareos;
import healthmanager.modelo.dao.Condiciones_grupos_etareosDao;
import healthmanager.modelo.dao.Grupos_etareosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("grupos_etareosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Grupos_etareosService implements Serializable{

	@Autowired
	private Grupos_etareosDao grupos_etareosDao;
	@Autowired
	private Condiciones_grupos_etareosDao condiciones_grupos_etareosDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;

	public void crear(Grupos_etareos grupos_etareos) {
		try {
			if (consultar(grupos_etareos) != null) {
				throw new HealthmanagerException(
						"Grupos_etareos ya se encuentra en la base de datos");
			}
			grupos_etareosDao.crear(grupos_etareos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Grupos_etareos grupos_etareos) {
		try {
			return grupos_etareosDao.actualizar(grupos_etareos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Grupos_etareos consultar(Grupos_etareos grupos_etareos) {
		try {
			return grupos_etareosDao.consultar(grupos_etareos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Grupos_etareos grupos_etareos) {
		try {
			return grupos_etareosDao.eliminar(grupos_etareos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Grupos_etareos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return grupos_etareosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return grupos_etareosDao.existe(parameters);
	}

	public void guardar(Map<String, Object> map) {
		try {
			Grupos_etareos grupos_etareos = (Grupos_etareos) map
					.get("grupos_etareos");
			List<Condiciones_grupos_etareos> list_esq = (List<Condiciones_grupos_etareos>) map
					.get("condiciones");

			if (grupos_etareosDao.consultar(grupos_etareos) != null) {
				grupos_etareosDao.actualizar(grupos_etareos);
			} else {
				// creamos un nuervo grupo etareo
				String id = consecutivoService.crearConsecutivo(
						grupos_etareos.getCodigo_empresa(),
						grupos_etareos.getCodigo_sucursal(),
						IConstantes.ID_GRUPOS_ETARIOS);
				grupos_etareos.setId(id);
				if (consultar(grupos_etareos) != null) {
					throw new HealthmanagerException(
							"Servicio nro "
									+ id
									+ " ya se encuentra en la base de datos actualize el consecutivo del contrato");
				}
				grupos_etareosDao.crear(grupos_etareos);
				consecutivoService.actualizarConsecutivo(
						grupos_etareos.getCodigo_empresa(),
						grupos_etareos.getCodigo_sucursal(),
						IConstantes.ID_GRUPOS_ETARIOS, id);
			}
			Condiciones_grupos_etareos esquema_vacunacion = new Condiciones_grupos_etareos();
			esquema_vacunacion.setCodigo_empresa(grupos_etareos
					.getCodigo_empresa());
			esquema_vacunacion.setCodigo_sucursal(grupos_etareos
					.getCodigo_sucursal());
			esquema_vacunacion.setId_grupo_etareo(grupos_etareos.getId());
			condiciones_grupos_etareosDao.eliminar(esquema_vacunacion);

			int i = 0;
			for (Condiciones_grupos_etareos condiciones_grupos_etareosTemp : list_esq) {
				condiciones_grupos_etareosTemp
						.setId_grupo_etareo(grupos_etareos.getId());
				condiciones_grupos_etareosTemp.setCodigo_empresa(grupos_etareos
						.getCodigo_empresa());
				condiciones_grupos_etareosTemp
						.setCodigo_sucursal(grupos_etareos.getCodigo_sucursal());
				condiciones_grupos_etareosTemp.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				condiciones_grupos_etareosTemp.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				condiciones_grupos_etareosTemp.setCreacion_user(grupos_etareos
						.getCreacion_user());
				condiciones_grupos_etareosTemp.setUltimo_user(grupos_etareos
						.getUltimo_user());
				condiciones_grupos_etareosTemp.setConsecutivo(++i);

				if (condiciones_grupos_etareosTemp.getDescripcion().trim()
						.isEmpty()) {
					throw new ValidacionRunTimeException(
							"El campo descripcion en la fila " + i
									+ " no puede ser vacio.");
				}

				if (condiciones_grupos_etareosDao
						.consultar(condiciones_grupos_etareosTemp) != null) {
					condiciones_grupos_etareosDao
							.actualizar(condiciones_grupos_etareosTemp);
				} else {
					condiciones_grupos_etareosDao
							.crear(condiciones_grupos_etareosTemp);
				}
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}