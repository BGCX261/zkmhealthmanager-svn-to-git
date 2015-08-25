/*
 * Centro_costoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.C_costo_via_ingreso;
import contaweb.modelo.bean.Centro_costo;
import contaweb.modelo.dao.C_costo_via_ingresoDao;
import contaweb.modelo.dao.Centro_costoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("centro_costoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Centro_costoService implements Serializable{

	private String limit;

	@Autowired
	private Centro_costoDao centro_costoDao;
	@Autowired
	private C_costo_via_ingresoDao c_costo_via_ingresoDao;

	public void crear(Centro_costo centro_costo,
			List<C_costo_via_ingreso> lista_datos) {
		try {
			if (consultar(centro_costo) != null) {
				throw new HealthmanagerException(
						"Centro_costo ya se encuentra en la base de datos");
			}
			centro_costoDao.crear(centro_costo);

			for (C_costo_via_ingreso c_costo_via_ingreso : lista_datos) {
				c_costo_via_ingreso.setCodigo_cc(centro_costo.getCodigo());
				// c_costo_via_ingreso.setCodigo_centro(centro_costo.getCodigo_centro());
				C_costo_via_ingreso aux = c_costo_via_ingresoDao
						.consultar(c_costo_via_ingreso);
				if (aux != null) {
					throw new HealthmanagerException("Via de ingreso "
							+ c_costo_via_ingreso.getVia_ingreso()
							+ " y centro de atencion "
							+ centro_costo.getCodigo_centro()
							+ " ya se encuentra asignado al centro de costo "
							+ aux.getCodigo_cc());
				}
				aux = c_costo_via_ingresoDao
						.consultarUnico(c_costo_via_ingreso);
				if (aux != null) {
					throw new HealthmanagerException("Via de ingreso "
							+ c_costo_via_ingreso.getVia_ingreso()
							+ " y centro de atencion "
							+ centro_costo.getCodigo_centro()
							+ " ya se encuentra asignado al centro de costo "
							+ aux.getCodigo_cc());
				}
				c_costo_via_ingresoDao.crear(c_costo_via_ingreso);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Centro_costo centro_costo,
			List<C_costo_via_ingreso> lista_datos) {
		try {
			int result = centro_costoDao.actualizar(centro_costo);

			C_costo_via_ingreso c_costo_via_ingresoAux = new C_costo_via_ingreso();
			c_costo_via_ingresoAux.setCodigo_empresa(centro_costo
					.getCodigo_empresa());
			c_costo_via_ingresoAux.setCodigo_sucursal(centro_costo
					.getCodigo_sucursal());
			c_costo_via_ingresoAux.setCodigo_cc(centro_costo.getCodigo());
			c_costo_via_ingresoDao.eliminar(c_costo_via_ingresoAux);

			for (C_costo_via_ingreso c_costo_via_ingreso : lista_datos) {
				c_costo_via_ingreso.setCodigo_cc(centro_costo.getCodigo());
				// c_costo_via_ingreso.setCodigo_centro(centro_costo.getCodigo_centro());
				C_costo_via_ingreso aux = c_costo_via_ingresoDao
						.consultar(c_costo_via_ingreso);
				if (aux != null) {
					throw new HealthmanagerException("Via de ingreso "
							+ c_costo_via_ingreso.getVia_ingreso()
							+ " y centro de atencion "
							+ centro_costo.getCodigo_centro()
							+ " ya se encuentra asignado al centro de costo "
							+ aux.getCodigo_cc());
				}
				aux = c_costo_via_ingresoDao
						.consultarUnico(c_costo_via_ingreso);
				if (aux != null) {
					throw new HealthmanagerException("Via de ingreso "
							+ c_costo_via_ingreso.getVia_ingreso()
							+ " y centro de atencion "
							+ centro_costo.getCodigo_centro()
							+ " ya se encuentra asignado al centro de costo "
							+ aux.getCodigo_cc());
				}
				c_costo_via_ingresoDao.crear(c_costo_via_ingreso);
			}

			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Centro_costo consultar(Centro_costo centro_costo) {
		try {
			return centro_costoDao.consultar(centro_costo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Centro_costo consultarVia_ingreso(Map<String, Object> parameter) {
		try {
			return centro_costoDao.consultarVia_ingreso(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Centro_costo centro_costo) {
		try {
			return centro_costoDao.eliminar(centro_costo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Centro_costo> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return centro_costoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
