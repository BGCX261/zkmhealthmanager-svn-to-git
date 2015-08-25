/*
 * Detalles_horariosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.Detalles_horariosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalles_horariosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalles_horariosService implements Serializable{

	private String limit;

	@Autowired
	private Detalles_horariosDao detalles_horariosDao;
	@Autowired
	private CitasDao citasDao;

	public void crear(Detalles_horarios detalles_horarios) {
		try {
			if (consultar(detalles_horarios) != null) {
				throw new HealthmanagerException(
						"Detalles_horarios ya se encuentra en la base de datos");
			}
			detalles_horariosDao.crear(detalles_horarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalles_horarios detalles_horarios) {
		try {
			return detalles_horariosDao.actualizar(detalles_horarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalles_horarios consultar(Detalles_horarios detalles_horarios) {
		try {
			return detalles_horariosDao.consultar(detalles_horarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalles_horarios consultarUltimoDisponible(
			Map<String, Object> parametros) {
		try {
			return detalles_horariosDao.consultarUltimoDisponible(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalles_horarios detalles_horarios) {
		try {
			return detalles_horariosDao.eliminar(detalles_horarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminarValidandoCitas(Map parameter) {
		try {
			Integer i = 0;
			List<Detalles_horarios> lista = listar(parameter);
			for (Detalles_horarios dh : lista) {
				Citas cita = new Citas();
				cita.setCodigo_empresa(dh.getCodigo_empresa());
				cita.setCodigo_sucursal(dh.getCodigo_sucursal());
				cita.setCodigo_detalle_horario(dh.getConsecutivo());
				cita.setEstado("1");
				cita = citasDao.consultar(cita);
				if (cita != null) {
					throw new Exception(
							"Los detalles horario seleccionado contienen citas asignadas,debe eliminarlas o trasladarlas antes de realizar esta operacion!!!");
				}
			}
			for (Detalles_horarios dh : lista) {
				Citas cita = new Citas();
				cita.setCodigo_empresa(dh.getCodigo_empresa());
				cita.setCodigo_sucursal(dh.getCodigo_sucursal());
				cita.setCodigo_detalle_horario(dh.getConsecutivo());
				cita.setEstado("5");
				cita = citasDao.consultar(cita);
				if (cita != null) {
					cita.setCodigo_detalle_horario(-1);
					citasDao.actualizar(cita);
				}
				i += detalles_horariosDao.eliminar(dh);
			}
			return i;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalles_horarios> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalles_horariosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * Este listar es el que se utiiza para buscar todas las citas desde el detalle de horarios sin hacerle join con la tabla prestadores
	 * @param parametros
	 * @return
	 */
	public List<Detalles_horarios> listar_registros(Map<String, Object> parametros){
		try {
			parametros.put("limit", limit);
			return detalles_horariosDao.listar_registros(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalles_horarios> listar_normal(Map<String, Object> parametros) {
		try {
			return detalles_horariosDao.listar_normal(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map<String, Object>> getViasIngresoAsignadas(
			Map<String, Object> map) {
		return this.detalles_horariosDao.getViasIngresoAsignadas(map);
	}

}
