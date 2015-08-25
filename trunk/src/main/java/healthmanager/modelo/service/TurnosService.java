/*
 * TurnosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Detalle_turno;
import healthmanager.modelo.bean.Turnos;
import healthmanager.modelo.dao.Detalle_turnoDao;
import healthmanager.modelo.dao.TurnosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("turnosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class TurnosService implements Serializable{

	public static Logger log = Logger.getLogger(TurnosService.class);

	@Autowired
	private TurnosDao turnosDao;
	@Autowired
	private Detalle_turnoDao detalle_turnoDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;

	public void crear(Turnos turnos) {
		try {
			if (consultar(turnos) != null) {
				throw new HealthmanagerException(
						"Turnos ya se encuentra en la base de datos");
			}
			turnosDao.crear(turnos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Turnos turnos) {
		try {
			return turnosDao.actualizar(turnos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Turnos consultar(Turnos turnos) {
		try {
			return turnosDao.consultar(turnos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Turnos turnos) {
		try {
			return turnosDao.eliminar(turnos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Turnos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return turnosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return turnosDao.existe(parameters);
	}

	public void guardar(Map<String, Object> map) {
		try {
			Turnos turno = (Turnos) map.get("turnos");
			List<Detalle_turno> list_dtt_turno = (List<Detalle_turno>) map
					.get("detalle_turno");

			if (turnosDao.consultar(turno) != null) {
				turnosDao.actualizar(turno);
			} else {
				turnosDao.crear(turno);
			}

			Detalle_turno detalle_turno = new Detalle_turno();
			detalle_turno.setCodigo_empresa(turno.getCodigo_empresa());
			detalle_turno.setCodigo_sucursal(turno.getCodigo_sucursal());
			detalle_turno.setCodigo_centro_salud(turno
					.getCodigo_centro_atencion());
			detalle_turnoDao.eliminar(detalle_turno);

			int i = 0;
			for (Detalle_turno detalle_turnoTemp : list_dtt_turno) {
				detalle_turnoTemp.setCodigo_centro_salud(turno
						.getCodigo_centro_atencion());
				detalle_turnoTemp.setCodigo_empresa(turno.getCodigo_empresa());
				detalle_turnoTemp
						.setCodigo_sucursal(turno.getCodigo_sucursal());
				detalle_turnoTemp.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				detalle_turnoTemp.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				detalle_turnoTemp.setCreacion_user(turno.getCreacion_user());
				detalle_turnoTemp.setUltimo_user(turno.getUltimo_user());
				detalle_turnoTemp.setConsecutivo((++i) + "");

				if (detalle_turnoTemp.getVia_ingreso().trim().isEmpty()) {
					throw new ValidacionRunTimeException(
							"El campo descripcion en la fila " + i
									+ " no puede ser vacio.");
				}

				if (detalle_turnoTemp.getPatron_dias() == null
						|| detalle_turnoTemp.getPatron_dias().trim().isEmpty()) {
					throw new ValidacionRunTimeException(
							"Debe seleccionar los días que aplique este turno, en la fila # "
									+ i);
				}

				if (detalle_turnoDao.consultar(detalle_turnoTemp) != null) {
					detalle_turnoDao.actualizar(detalle_turnoTemp);
				} else {
					detalle_turnoDao.crear(detalle_turnoTemp);
				}
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarMultiple(Map<String, Object> map) {
		try {
			// log.info("Mapa: " + map);
			List<Centro_atencion> centro_atencions = (List<Centro_atencion>) map
					.get("lisCentros");
			for (Centro_atencion centro_atencion : centro_atencions) {
				Turnos turnos = (Turnos) map.get("turnos");
				turnos.setCodigo_centro_atencion(centro_atencion
						.getCodigo_centro());
				guardar(map);
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}