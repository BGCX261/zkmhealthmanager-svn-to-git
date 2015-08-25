/*
 * Control_menorServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Control_menor;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Control_menorDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("control_menorService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Control_menorService implements Serializable{

	private String limit;

	@Autowired
	private Control_menorDao control_menorDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Control_menor guardar(Map<String, Object> datos) {
		try {

			Control_menor pyp = (Control_menor) datos.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						pyp.getCodigo_empresa(), pyp.getCodigo_sucursal(),
						"CONTROL_MENOR");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				pyp.setCodigo_historia(codigo_historia);
				if (consultar(pyp) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				control_menorDao.crear(pyp);
				consecutivoService.actualizarConsecutivo(
						pyp.getCodigo_empresa(), pyp.getCodigo_sucursal(),
						"CONTROL_MENOR", pyp.getCodigo_historia());
			} else {
				control_menorDao.actualizar(pyp);
			}
			return pyp;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Control_menor control_menor) {
		try {
			if (consultar(control_menor) != null) {
				throw new HealthmanagerException(
						"Control_menor ya se encuentra en la base de datos");
			}
			control_menorDao.crear(control_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Control_menor control_menor) {
		try {
			return control_menorDao.actualizar(control_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Control_menor consultar(Control_menor control_menor) {
		try {
			return control_menorDao.consultar(control_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Control_menor control_menor) {
		try {
			return control_menorDao.eliminar(control_menor);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Control_menor> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return control_menorDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
