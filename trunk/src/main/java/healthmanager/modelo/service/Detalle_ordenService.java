/*
 * Detalle_ordenServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Detalle_ordenDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_ordenService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_ordenService implements Serializable{

	private String limit;

	@Autowired
	private Detalle_ordenDao detalle_ordenDao;
	@Autowired
	private AdmisionDao admisionDao;

	public void crear(Detalle_orden detalle_orden) {
		try {
			if (consultar(detalle_orden) != null) {
				throw new HealthmanagerException(
						"Detalle_orden ya se encuentra en la base de datos");
			}
			detalle_ordenDao.crear(detalle_orden);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_orden detalle_orden) {
		try {
			return detalle_ordenDao.actualizar(detalle_orden);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_orden consultar(Detalle_orden detalle_orden) {
		try {
			return detalle_ordenDao.consultar(detalle_orden);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_orden detalle_orden) {
		try {
			return detalle_ordenDao.eliminar(detalle_orden);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_orden> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_ordenDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listarLaboratoriosRegistrado(
			Map<String, Object> param) {
		param.put("limit", limit);
		return detalle_ordenDao.listarLaboratoriosRegistrado(param);
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void actualizarEstado(List<Map<String, Object>> listadoLaboratorios,
			Admision admision) {
		admision.setAtendida(true);
		admisionDao.actualizar(admision);

		for (Map<String, Object> map : listadoLaboratorios) {
			String codigo_empresa = (String) map.get("codigo_empresa");
			String codigo_sucursal = (String) map.get("codigo_sucursal");
			Long codigo_orden = (Long) map.get("codigo_orden");
			String consecutivo = (String) map.get("consecutivo");

			Detalle_orden detalle_orden = new Detalle_orden();
			detalle_orden.setCodigo_empresa(codigo_empresa);
			detalle_orden.setCodigo_sucursal(codigo_sucursal);
			detalle_orden.setCodigo_orden(codigo_orden);
			detalle_orden.setConsecutivo(consecutivo);
			detalle_orden = detalle_ordenDao.consultar(detalle_orden);

			if (detalle_orden != null) {
				detalle_orden.setRealizado("S");
				detalle_ordenDao.actualizar(detalle_orden);
			}
		}
	}

	public List<Detalle_orden> listarParametrizado(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_ordenDao.listarParametrizado(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
