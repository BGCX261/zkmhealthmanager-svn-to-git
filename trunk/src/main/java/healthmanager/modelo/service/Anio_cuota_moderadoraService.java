/*
 * Anio_cuota_moderadoraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anio_cuota_moderadora;
import healthmanager.modelo.bean.Salario_anual;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.dao.Anio_cuota_moderadoraDao;
import healthmanager.modelo.dao.Salario_anualDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("anio_cuota_moderadoraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Anio_cuota_moderadoraService implements Serializable{

	private String limit;

	@Autowired
	private Anio_cuota_moderadoraDao anio_cuota_moderadoraDao;
	@Autowired
	private Salario_anualDao salario_anualDao;

	public void crear(Anio_cuota_moderadora anio_cuota_moderadora) {
		try {
			if (consultar(anio_cuota_moderadora) != null) {
				throw new HealthmanagerException(
						"Anio_cuota_moderadora ya se encuentra en la base de datos");
			}
			anio_cuota_moderadoraDao.crear(anio_cuota_moderadora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Anio_cuota_moderadora anio_cuota_moderadora) {
		try {
			return anio_cuota_moderadoraDao.actualizar(anio_cuota_moderadora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Anio_cuota_moderadora consultar(
			Anio_cuota_moderadora anio_cuota_moderadora) {
		try {
			return anio_cuota_moderadoraDao.consultar(anio_cuota_moderadora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Anio_cuota_moderadora anio_cuota_moderadora) {
		try {
			return anio_cuota_moderadoraDao.eliminar(anio_cuota_moderadora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Anio_cuota_moderadora> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return anio_cuota_moderadoraDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void guardar(Map<String, Object> map) {
		List<Object> lista_detalle = (List<Object>) map.get("listado_detalle");
		Usuarios usuarios = (Usuarios) map.get("user");
		for (Object object : lista_detalle) {
			if (object instanceof Anio_cuota_moderadora) {
				Anio_cuota_moderadora anio_cuota_moderadora = (Anio_cuota_moderadora) object;
				if (anio_cuota_moderadoraDao.consultar(anio_cuota_moderadora) != null) {
					anio_cuota_moderadora.setUltimo_update(new Timestamp(
							Calendar.getInstance().getTimeInMillis()));
					anio_cuota_moderadora.setUltimo_user(usuarios.getCodigo());
					anio_cuota_moderadoraDao.actualizar(anio_cuota_moderadora);
				} else {
					anio_cuota_moderadoraDao.crear(anio_cuota_moderadora);
				}
			} else if (object instanceof Salario_anual) {
				Salario_anual salario_anual = (Salario_anual) object;
				if (salario_anualDao.consultar(salario_anual) != null) {
					salario_anualDao.actualizar(salario_anual);
				} else {
					salario_anualDao.crear(salario_anual);
				}
			}
		}
	}

}
