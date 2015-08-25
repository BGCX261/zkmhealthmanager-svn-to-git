/*
 * Servicios_procedimientosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Servicios_procedimientos;
import healthmanager.modelo.dao.Servicios_procedimientosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("servicios_procedimientosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Servicios_procedimientosService implements Serializable{

	@Autowired
	private Servicios_procedimientosDao servicios_procedimientosDao;

	public void guardarDatos(Map<String, Object> mapa_datos) throws Exception {
		try {
			List<Servicios_procedimientos> listado_datos = (List<Servicios_procedimientos>) mapa_datos
					.get("listado_datos");
			Servicios_procedimientos servicios_procedimientos = new Servicios_procedimientos();
			servicios_procedimientos.setCodigo_empresa(listado_datos.get(0)
					.getCodigo_empresa());
			servicios_procedimientos.setCodigo_sucursal(listado_datos.get(0)
					.getCodigo_sucursal());
			servicios_procedimientos.setCodigo_sucursal(listado_datos.get(0)
					.getCodigo_unidad());

			servicios_procedimientosDao.eliminar(servicios_procedimientos);

			for (Servicios_procedimientos servicios_procedimientos2 : listado_datos) {
				servicios_procedimientosDao.crear(servicios_procedimientos2);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Servicios_procedimientos servicios_procedimientos) {
		try {
			if (consultar(servicios_procedimientos) != null) {
				throw new HealthmanagerException(
						"Servicios_procedimientos ya se encuentra en la base de datos");
			}
			servicios_procedimientosDao.crear(servicios_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Servicios_procedimientos servicios_procedimientos) {
		try {
			return servicios_procedimientosDao
					.actualizar(servicios_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Servicios_procedimientos consultar(
			Servicios_procedimientos servicios_procedimientos) {
		try {
			return servicios_procedimientosDao
					.consultar(servicios_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Servicios_procedimientos servicios_procedimientos) {
		try {
			return servicios_procedimientosDao
					.eliminar(servicios_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Servicios_procedimientos> listar(Map<String, Object> parameters) {
		try {
			return servicios_procedimientosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer cantidad_procedimientos(Map<String, Object> parametros) {
		try {
			return servicios_procedimientosDao
					.cantidad_procedimientos(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


	public boolean existe(Map<String, Object> parameters) {
		return servicios_procedimientosDao.existe(parameters);
	}

}