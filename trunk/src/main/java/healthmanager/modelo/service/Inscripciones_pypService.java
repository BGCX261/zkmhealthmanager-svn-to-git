/*
 * Inscripciones_pypServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Inscripciones_pyp;
import healthmanager.modelo.dao.Inscripciones_pypDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("inscripciones_pypService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Inscripciones_pypService implements Serializable{

	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Inscripciones_pypDao inscripciones_pypDao;

	private String limit;

	public void crear(Inscripciones_pyp inscripciones_pyp) {
		try {
			if (consultar(inscripciones_pyp) != null) {
				throw new HealthmanagerException(
						"Inscripciones_pyp ya se encuentra en la base de datos");
			}
			inscripciones_pypDao.crear(inscripciones_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarDatos(Inscripciones_pyp insc) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", insc.getCodigo_empresa());
			param.put("codigo_sucursal", insc.getCodigo_sucursal());
			param.put("nro_identificacion", insc.getNro_identificacion());
			param.put("id_codigo", insc.getId_codigo());

			if (listar(param).size() == 0) {

			} else {
				inscripciones_pypDao.actualizar(insc);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Inscripciones_pyp inscripciones_pyp) {
		try {
			return inscripciones_pypDao.actualizar(inscripciones_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Inscripciones_pyp consultar(Inscripciones_pyp inscripciones_pyp) {
		try {
			return inscripciones_pypDao.consultar(inscripciones_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Inscripciones_pyp inscripciones_pyp) {
		try {
			return inscripciones_pypDao.eliminar(inscripciones_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Inscripciones_pyp> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return inscripciones_pypDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return inscripciones_pypDao.existe(parameters);
	}

}