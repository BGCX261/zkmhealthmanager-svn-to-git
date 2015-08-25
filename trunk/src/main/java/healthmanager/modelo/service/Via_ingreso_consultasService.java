/*
 * Via_ingreso_consultasServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Via_ingreso_consultas;
import healthmanager.modelo.dao.Via_ingreso_consultasDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("via_ingreso_consultasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Via_ingreso_consultasService implements Serializable{

	@Autowired
	private Via_ingreso_consultasDao via_ingreso_consultasDao;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String codigo_empresa = (String) mapa_datos.get("codigo_empresa");
			String codigo_sucursal = (String) mapa_datos.get("codigo_sucursal");
			List<Via_ingreso_consultas> listado_datos = (List<Via_ingreso_consultas>) mapa_datos
					.get("listado_datos");

			Via_ingreso_consultas via_ingreso_consultas2 = new Via_ingreso_consultas();
			via_ingreso_consultas2.setCodigo_empresa(codigo_empresa);
			via_ingreso_consultas2.setCodigo_sucursal(codigo_sucursal);
			via_ingreso_consultasDao.eliminar(via_ingreso_consultas2);

			for (Via_ingreso_consultas via_ingreso_consultas : listado_datos) {
				via_ingreso_consultasDao.crear(via_ingreso_consultas);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Via_ingreso_consultas via_ingreso_consultas) {
		try {
			if (consultar(via_ingreso_consultas) != null) {
				throw new HealthmanagerException(
						"Via_ingreso_consultas ya se encuentra en la base de datos");
			}
			via_ingreso_consultasDao.crear(via_ingreso_consultas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Via_ingreso_consultas via_ingreso_consultas) {
		try {
			return via_ingreso_consultasDao.actualizar(via_ingreso_consultas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Via_ingreso_consultas consultar(
			Via_ingreso_consultas via_ingreso_consultas) {
		try {
			return via_ingreso_consultasDao.consultar(via_ingreso_consultas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Via_ingreso_consultas via_ingreso_consultas) {
		try {
			return via_ingreso_consultasDao.eliminar(via_ingreso_consultas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Via_ingreso_consultas> listar(Map<String, Object> parameters) {
		try {
			return via_ingreso_consultasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}


	public boolean existe(Map<String, Object> parameters) {
		return via_ingreso_consultasDao.existe(parameters);
	}

}