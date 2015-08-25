/*
 * Notas_enfermeriaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Notas_enfermeria;
import healthmanager.modelo.dao.Notas_enfermeriaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("notas_enfermeriaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Notas_enfermeriaService implements Serializable{

	@Autowired
	private Notas_enfermeriaDao notas_enfermeriaDao;

	@Autowired
	private ConsecutivoService consecutivoService;

	private String limit;

	public void crear(Notas_enfermeria notas_enfermeria) {
		try {
			if (consultar(notas_enfermeria) != null) {
				throw new HealthmanagerException(
						"Notas_enfermeria ya se encuentra en la base de datos");
			}
			notas_enfermeria.setFecha_nota(new Timestamp(new Date().getTime()));
			notas_enfermeriaDao.crear(notas_enfermeria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			Notas_enfermeria notas_enfermeria = (Notas_enfermeria) mapa_datos
					.get("notas_enfermeria");
			String accion = (String) mapa_datos.get("accion");
			if (accion.equalsIgnoreCase("registrar")) {
				String consecutivo = consecutivoService.crearConsecutivo(
						notas_enfermeria.getCodigo_empresa(),
						notas_enfermeria.getCodigo_sucursal(),
						IConstantes.CONS_NOTAS_ENFERMERIA);
				String codigo_nota = consecutivoService.getZeroFill(
						consecutivo, 10);
				notas_enfermeria.setCodigo_nota(codigo_nota);
				notas_enfermeria.setFecha_nota(new Timestamp(new Date()
						.getTime()));
				notas_enfermeriaDao.crear(notas_enfermeria);

				consecutivoService.actualizarConsecutivo(
						notas_enfermeria.getCodigo_empresa(),
						notas_enfermeria.getCodigo_sucursal(),
						IConstantes.CONS_NOTAS_ENFERMERIA, codigo_nota);

			} else {
				notas_enfermeriaDao.actualizar(notas_enfermeria);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Notas_enfermeria notas_enfermeria) {
		try {
			return notas_enfermeriaDao.actualizar(notas_enfermeria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Notas_enfermeria consultar(Notas_enfermeria notas_enfermeria) {
		try {
			return notas_enfermeriaDao.consultar(notas_enfermeria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Notas_enfermeria notas_enfermeria) {
		try {
			return notas_enfermeriaDao.eliminar(notas_enfermeria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Notas_enfermeria> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return notas_enfermeriaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return notas_enfermeriaDao.existe(parameters);
	}

}