/*
 * Plan_tratamientoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Plan_tratamiento;
import healthmanager.modelo.dao.Plan_tratamientoDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("plan_tratamientoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Plan_tratamientoService implements Serializable{

	@Autowired
	private Plan_tratamientoDao plan_tratamientoDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public void crear(Plan_tratamiento plan_tratamiento) {
		try {
			// String consecutivo = consecutivoService.obtenerConsecutivo(
			// plan_tratamiento.getCodigo_empresa(),
			// plan_tratamiento.getCodigo_sucursal(),
			// IConsecutivos.CONS_PLAN_TRATAMIENTO);
			// plan_tratamiento.setConsecutivo(consecutivo);
			//
			if (consultar(plan_tratamiento) != null) {
				throw new HealthmanagerException(
						"Plan_tratamiento ya se encuentra en la base de datos");
			}

			// Creamos los planes de tratamiento
			plan_tratamientoDao.crear(plan_tratamiento);

			// actualizamos los consecutivos
			// consecutivoService.actualizarConsecutivo(plan_tratamiento.getCodigo_empresa(),
			// plan_tratamiento.getCodigo_sucursal(),
			// IConsecutivos.CONS_PLAN_TRATAMIENTO, consecutivo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Plan_tratamiento plan_tratamiento) {
		try {
			return plan_tratamientoDao.actualizar(plan_tratamiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Plan_tratamiento consultar(Plan_tratamiento plan_tratamiento) {
		try {
			return plan_tratamientoDao.consultar(plan_tratamiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Plan_tratamiento plan_tratamiento) {
		try {
			return plan_tratamientoDao.eliminar(plan_tratamiento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Plan_tratamiento> listar(Map<String, Object> parameters) {
		try {
			return plan_tratamientoDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return plan_tratamientoDao.existe(parameters);
	}

	public boolean planTratamientoPendiente(Map<String, Object> param) {
		return plan_tratamientoDao.planTratamientoPendiente(param);
	}

}