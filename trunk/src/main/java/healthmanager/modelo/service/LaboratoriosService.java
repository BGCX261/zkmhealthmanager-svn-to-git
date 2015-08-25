/*
 * LaboratoriosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Laboratorios;
import healthmanager.modelo.bean.Laboratorios_respuestas;
import healthmanager.modelo.dao.LaboratoriosDao;
import healthmanager.modelo.dao.Laboratorios_respuestasDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("laboratoriosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LaboratoriosService implements Serializable{

	@Autowired
	private LaboratoriosDao laboratoriosDao;
	@Autowired
	private Laboratorios_respuestasDao laboratorios_respuestasDao;
	private String limit;

	public void crear(Laboratorios laboratorios) {
		try {
			if (consultar(laboratorios) != null) {
				throw new HealthmanagerException(
						"Laboratorios ya se encuentra en la base de datos");
			}
			laboratoriosDao.crear(laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Laboratorios laboratorios) {
		try {
			return laboratoriosDao.actualizar(laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Laboratorios consultar(Laboratorios laboratorios) {
		try {
			return laboratoriosDao.consultar(laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Laboratorios laboratorios) {
		try {
			return laboratoriosDao.eliminar(laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Laboratorios> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return laboratoriosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return laboratoriosDao.existe(parameters);
	}

	public void guardar(Map<String, Object> map) {
		try {
			Laboratorios vacunas = (Laboratorios) map.get("lab");
			List<Laboratorios_respuestas> list_esq = (List<Laboratorios_respuestas>) map
					.get("labres");

			if (laboratoriosDao.consultar(vacunas) != null) {
				laboratoriosDao.actualizar(vacunas);
			} else {
				laboratoriosDao.crear(vacunas);
			}
			Laboratorios_respuestas laboratorios_respuestas = new Laboratorios_respuestas();
			laboratorios_respuestas.setCodigo_empresa(vacunas
					.getCodigo_empresa());
			laboratorios_respuestas.setCodigo_sucursal(vacunas
					.getCodigo_sucursal());
			laboratorios_respuestas.setCodigo_procedimiento(vacunas
					.getCodigo_procedimiento());
			laboratorios_respuestasDao.eliminar(laboratorios_respuestas);

			int i = 0;
			for (Laboratorios_respuestas laboratorios_respuestasTemp : list_esq) {
				laboratorios_respuestasTemp.setCodigo_procedimiento(vacunas
						.getCodigo_procedimiento());
				laboratorios_respuestasTemp.setCodigo_empresa(vacunas
						.getCodigo_empresa());
				laboratorios_respuestasTemp.setCodigo_sucursal(vacunas
						.getCodigo_sucursal());
				laboratorios_respuestasTemp.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				laboratorios_respuestasTemp.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				laboratorios_respuestasTemp.setCreacion_user(vacunas
						.getCreacion_user());
				laboratorios_respuestasTemp.setUltimo_user(vacunas
						.getUltimo_user());

				if (laboratorios_respuestasTemp.getDescripcion().trim()
						.isEmpty()) {
					throw new ValidacionRunTimeException(
							"El campo descripcion en la fila " + i
									+ " no puede ser vacio.");
				}

				if (laboratorios_respuestasTemp.getCodigo_respuesta().trim()
						.isEmpty()) {
					throw new ValidacionRunTimeException(
							"El campo codigo de respuesta en la fila " + i
									+ " no puede ser vacio.");
				}

				if (laboratorios_respuestasDao
						.consultar(laboratorios_respuestasTemp) != null) {
					laboratorios_respuestasDao
							.actualizar(laboratorios_respuestasTemp);
				} else {
					laboratorios_respuestasDao
							.crear(laboratorios_respuestasTemp);
				}
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}