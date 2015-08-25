/*
 * Detalle_plantilla_pypServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_plantilla_pyp;
import healthmanager.modelo.dao.Detalle_plantilla_pypDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_plantilla_pypService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_plantilla_pypService implements Serializable{

	@Autowired
	private Detalle_plantilla_pypDao detalle_plantilla_pypDao;

	public void crear(Detalle_plantilla_pyp detalle_plantilla_pyp) {
		try {
			if (consultar(detalle_plantilla_pyp) != null) {
				throw new HealthmanagerException(
						"Detalle_plantilla_pyp ya se encuentra en la base de datos");
			}
			detalle_plantilla_pypDao.crear(detalle_plantilla_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_plantilla_pyp detalle_plantilla_pyp) {
		try {
			return detalle_plantilla_pypDao.actualizar(detalle_plantilla_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_plantilla_pyp consultar(
			Detalle_plantilla_pyp detalle_plantilla_pyp) {
		try {
			return detalle_plantilla_pypDao.consultar(detalle_plantilla_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_plantilla_pyp detalle_plantilla_pyp) {
		try {
			return detalle_plantilla_pypDao.eliminar(detalle_plantilla_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_plantilla_pyp> listar(Map<String, Object> parameters) {
		try {
			return detalle_plantilla_pypDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_plantilla_pypDao.existe(parameters);
	}

}