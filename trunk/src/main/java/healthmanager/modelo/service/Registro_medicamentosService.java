/*
 * Registro_medicamentosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Registro_medicamentos;
import healthmanager.modelo.dao.Registro_medicamentosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("registro_medicamentosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Registro_medicamentosService implements Serializable{

	@Autowired
	private Registro_medicamentosDao registro_medicamentosDao;
	private String limit;

	@Autowired
	private ConsecutivoService consecutivoService;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			Registro_medicamentos registro_medicamentos = (Registro_medicamentos) mapa_datos
					.get("registro_medicamentos");
			String accion = (String) mapa_datos.get("accion");
			if (accion.equalsIgnoreCase("registrar")) {
				String consecutivo = consecutivoService.crearConsecutivo(
						registro_medicamentos.getCodigo_empresa(),
						registro_medicamentos.getCodigo_sucursal(),
						IConstantes.CONS_REGISTRO_MEDICAMENTOS);
				String codigo_registro = consecutivoService.getZeroFill(
						consecutivo, 10);
				registro_medicamentos.setCodigo_registro(codigo_registro);
				registro_medicamentos.setFecha_registro(new Timestamp(
						new Date().getTime()));
				registro_medicamentosDao.crear(registro_medicamentos);

				consecutivoService
						.actualizarConsecutivo(
								registro_medicamentos.getCodigo_empresa(),
								registro_medicamentos.getCodigo_sucursal(),
								IConstantes.CONS_REGISTRO_MEDICAMENTOS,
								codigo_registro);

			} else {
				registro_medicamentosDao.actualizar(registro_medicamentos);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Registro_medicamentos registro_medicamentos) {
		try {
			if (consultar(registro_medicamentos) != null) {
				throw new HealthmanagerException(
						"Registro_medicamentos ya se encuentra en la base de datos");
			}
			registro_medicamentosDao.crear(registro_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Registro_medicamentos registro_medicamentos) {
		try {
			return registro_medicamentosDao.actualizar(registro_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Registro_medicamentos consultar(
			Registro_medicamentos registro_medicamentos) {
		try {
			return registro_medicamentosDao.consultar(registro_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Registro_medicamentos registro_medicamentos) {
		try {
			return registro_medicamentosDao.eliminar(registro_medicamentos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Registro_medicamentos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return registro_medicamentosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return registro_medicamentosDao.existe(parameters);
	}

}