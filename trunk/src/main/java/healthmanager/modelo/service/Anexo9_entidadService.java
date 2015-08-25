/*
 * Anexo9_entidadServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.dao.Anexo9_entidadDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("anexo9_entidadService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Anexo9_entidadService implements Serializable{

	private String limit;

	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public void guardar(Anexo9_entidad anexo9_entidad) {
		try {
			String consecutivo = consecutivoService.crearConsecutivo(
					anexo9_entidad.getCodigo_empresa(),
					anexo9_entidad.getCodigo_sucursal(),
					IConstantes.CONS_ANEXO_9);
			String codigo_anexo = consecutivoService.getZeroFill(consecutivo,
					10);
			anexo9_entidad.setCodigo(codigo_anexo);
			anexo9_entidadDao.crear(anexo9_entidad);
			consecutivoService.actualizarConsecutivo(
					anexo9_entidad.getCodigo_empresa(),
					anexo9_entidad.getCodigo_sucursal(),
					IConstantes.CONS_ANEXO_9, codigo_anexo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Anexo9_entidad anexo9_entidad) {
		try {
			if (consultar(anexo9_entidad) != null) {
				throw new HealthmanagerException(
						"Anexo9_entidad ya se encuentra en la base de datos");
			}
			anexo9_entidadDao.crear(anexo9_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Anexo9_entidad anexo9_entidad) {
		try {
			return anexo9_entidadDao.actualizar(anexo9_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Anexo9_entidad consultar(Anexo9_entidad anexo9_entidad) {
		try {
			return anexo9_entidadDao.consultar(anexo9_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Anexo9_entidad anexo9_entidad) {
		try {
			return anexo9_entidadDao.eliminar(anexo9_entidad);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Anexo9_entidad> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return anexo9_entidadDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
