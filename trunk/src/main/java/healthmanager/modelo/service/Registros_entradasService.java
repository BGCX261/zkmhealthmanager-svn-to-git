/*
 * Nota_pypServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Registros_entradas;
import healthmanager.modelo.dao.Registros_entradasDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("registros_entradasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Registros_entradasService implements Serializable{

	@Autowired
	private Registros_entradasDao registros_entradasDao;

	public void crear(Registros_entradas registros_entradas) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_usuario",
					registros_entradas.getCodigo_usuario());
			parametros.put("codigo_empresa",
					registros_entradas.getCodigo_empresa());
			parametros.put("codigo_sucursal",
					registros_entradas.getCodigo_sucursal());
			parametros.put("tipo", registros_entradas.getTipo());
			parametros.put("salidas_incorrectas", "salidas_incorrectas");

			List<Registros_entradas> listado = listar(parametros);

			for (Registros_entradas registros_entradas_aux : listado) {
				if (registros_entradas_aux.getFecha_egreso() == null)
					registros_entradas_aux.setFecha_egreso(new Timestamp(
							new Date().getTime()));
				registros_entradas_aux.setEstado("2");
				actualizar(registros_entradas_aux);
			}
			registros_entradasDao.crear(registros_entradas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Registros_entradas registros_entradas) {
		try {
			return registros_entradasDao.actualizar(registros_entradas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Registros_entradas consultar(Registros_entradas registros_entradas) {
		try {
			return registros_entradasDao.consultar(registros_entradas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Registros_entradas registros_entradas) {
		try {
			return registros_entradasDao.eliminar(registros_entradas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Registros_entradas> listar(Map<String, Object> parameters) {
		try {
			return registros_entradasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public List<Long> listarIds(Map<String, Object> parameters){
		try {
			return registros_entradasDao.listarIds(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public long cantidad_entradas(Map<String, Object> parameters) {
		try {
			return registros_entradasDao.cantidad_entradas(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
