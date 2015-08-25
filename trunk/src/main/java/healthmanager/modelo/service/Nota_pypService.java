/*
 * Nota_pypServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Nota_pyp;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Nota_pypDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("nota_pypService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Nota_pypService implements Serializable{

	private String limit;

	@Autowired
	private Nota_pypDao nota_pypDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Nota_pyp guardar(Map<String, Object> datos) {
		try {

			Nota_pyp pyp = (Nota_pyp) datos.get("codigo_historia");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						pyp.getCodigo_empresa(), pyp.getCodigo_sucursal(),
						"NOTA_PYP");
				codigo_historia = consecutivoService.getZeroFill(
						codigo_historia, 10);
				pyp.setCodigo_historia(codigo_historia);
				if (consultar(pyp) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				nota_pypDao.crear(pyp);
				consecutivoService.actualizarConsecutivo(
						pyp.getCodigo_empresa(), pyp.getCodigo_sucursal(),
						"NOTA_PYP", pyp.getCodigo_historia());
			} else {
				nota_pypDao.actualizar(pyp);
			}
			return pyp;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Nota_pyp nota_pyp) {
		try {
			if (consultar(nota_pyp) != null) {
				throw new HealthmanagerException(
						"Nota_pyp ya se encuentra en la base de datos");
			}
			nota_pypDao.crear(nota_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Nota_pyp nota_pyp) {
		try {
			return nota_pypDao.actualizar(nota_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nota_pyp consultar(Nota_pyp nota_pyp) {
		try {
			return nota_pypDao.consultar(nota_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Nota_pyp nota_pyp) {
		try {
			return nota_pypDao.eliminar(nota_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Nota_pyp> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return nota_pypDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
