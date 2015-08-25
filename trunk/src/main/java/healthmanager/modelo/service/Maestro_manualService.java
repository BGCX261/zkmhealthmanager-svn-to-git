/*
 * Maestro_manualServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_procedimientos;
import healthmanager.modelo.dao.Maestro_manualDao;
import healthmanager.modelo.dao.Manuales_procedimientosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("maestro_manualService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Maestro_manualService implements Serializable{

	@Autowired
	private Maestro_manualDao maestro_manualDao;

	@Autowired
	private Manuales_procedimientosDao manuales_procedimientosDao;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String accion = (String) mapa_datos.get("accion");
			Maestro_manual maestro_manual = (Maestro_manual) mapa_datos
					.get("maestro_manual");
			Map<Long, Object> mapa_datos_procedimientos = (Map<Long, Object>) mapa_datos
					.get("mapa_datos_procedimientos");
			if (accion.equalsIgnoreCase("registrar")) {
				maestro_manualDao.crear(maestro_manual);
			} else {
				maestro_manualDao.actualizar(maestro_manual);
				Manuales_procedimientos manuales_procedimientos = new Manuales_procedimientos();
				manuales_procedimientos.setId_manual(maestro_manual
						.getId_manual());
				manuales_procedimientosDao
						.eliminar_manual(manuales_procedimientos);
			}

			for (Long id_procedimiento : mapa_datos_procedimientos.keySet()) {
				Map<String, Object> pcd = (Map<String, Object>) mapa_datos_procedimientos
						.get(id_procedimiento);
				Manuales_procedimientos manuales_procedimientos = new Manuales_procedimientos();
				String codigo_manual = (String) pcd.get("codigo_manual");
				String grupo_uvr = (String) pcd.get("grupo_uvr");
				Double valor = (Double) pcd.get("valor");
				String nro_cuenta = (String) pcd.get("nro_cuenta");
				manuales_procedimientos.setCodigo_manual(codigo_manual);
				manuales_procedimientos.setCreacion_date(new Timestamp(
						new Date().getTime()));
				manuales_procedimientos.setCreacion_user(maestro_manual
						.getCreacion_user());
				manuales_procedimientos
						.setGrupo_uvr(grupo_uvr != null ? grupo_uvr : "");
				manuales_procedimientos.setId_manual(maestro_manual
						.getId_manual());
				manuales_procedimientos.setId_procedimiento(id_procedimiento);
				manuales_procedimientos.setValor(valor != null ? valor : 0.0);
				manuales_procedimientos.setNro_cuenta_contable(nro_cuenta);
				manuales_procedimientosDao.crear(manuales_procedimientos);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Maestro_manual maestro_manual) {
		try {
			if (consultar(maestro_manual) != null) {
				throw new HealthmanagerException(
						"Maestro_manual ya se encuentra en la base de datos");
			}
			maestro_manualDao.crear(maestro_manual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Maestro_manual maestro_manual) {
		try {
			return maestro_manualDao.actualizar(maestro_manual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Maestro_manual consultar(Maestro_manual maestro_manual) {
		try {
			return maestro_manualDao.consultar(maestro_manual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Maestro_manual maestro_manual) {
		try {
			return maestro_manualDao.eliminar(maestro_manual);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Maestro_manual> listar(Map<String, Object> parameters) {
		try {
			return maestro_manualDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return maestro_manualDao.existe(parameters);
	}

}