/*
 * Alteracion_menorServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Alteracion_menor;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Alteracion_menorDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("alteracion_menorService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Alteracion_menorService implements Serializable{

	private String limit;

	@Autowired
	private Alteracion_menorDao alteracion_menorDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Alteracion_menor guardar(Map<String, Object> datos) {
		try {

			Alteracion_menor menor = (Alteracion_menor) datos
					.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						menor.getCodigo_empresa(), menor.getCodigo_sucursal(),
						"ALTERACION_MENOR");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				menor.setCodigo_historia(codigo_historia);
				if (consultar(menor) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				alteracion_menorDao.crear(menor);
				consecutivoService.actualizarConsecutivo(
						menor.getCodigo_empresa(), menor.getCodigo_sucursal(),
						"ALTERACION_MENOR", menor.getCodigo_historia());
			} else {
				alteracion_menorDao.actualizar(menor);
			}
			return menor;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Alteracion_menor alteracion_menor) {
		try {
			if (consultar(alteracion_menor) != null) {
				throw new HealthmanagerException(
						"Alteracion_menor ya se encuentra en la base de datos");
			}
			alteracion_menorDao.crear(alteracion_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Alteracion_menor alteracion_menor) {
		try {
			return alteracion_menorDao.actualizar(alteracion_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Alteracion_menor consultar(Alteracion_menor alteracion_menor) {
		try {
			return alteracion_menorDao.consultar(alteracion_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Alteracion_menor alteracion_menor) {
		try {
			return alteracion_menorDao.eliminar(alteracion_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Alteracion_menor> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return alteracion_menorDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public void setLimit(String limit) {
		this.limit = limit;
	}

}
