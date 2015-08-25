/*
 * Modulo_firmasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.controller.Modulo_firmasAction;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Modulo_firmas;
import healthmanager.modelo.bean.Modulo_firmas_reportes;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.dao.ElementoDao;
import healthmanager.modelo.dao.Modulo_firmasDao;
import healthmanager.modelo.dao.Modulo_firmas_reportesDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("modulo_firmasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Modulo_firmasService implements Serializable{

	@Autowired
	private Modulo_firmasDao modulo_firmasDao;
	@Autowired
	private Modulo_firmas_reportesDao modulo_firmas_reportesDao;
	@Autowired
	private ElementoDao elementoDao;
	private String limit;

	public void crear(Modulo_firmas modulo_firmas) {
		try {
			if (consultar(modulo_firmas) != null) {
				throw new ValidacionRunTimeException(
						"Modulo_firmas ya se encuentra en la base de datos");
			}
			modulo_firmasDao.crear(modulo_firmas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Modulo_firmas modulo_firmas) {
		try {
			return modulo_firmasDao.actualizar(modulo_firmas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Modulo_firmas consultar(Modulo_firmas modulo_firmas) {
		try {
			return modulo_firmasDao.consultar(modulo_firmas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Modulo_firmas modulo_firmas) {
		try {
			return modulo_firmasDao.eliminar(modulo_firmas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Modulo_firmas> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return modulo_firmasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return modulo_firmasDao.existe(parameters);
	}

	public Modulo_firmas consultarID(Modulo_firmas modulo_firmas) {
		return modulo_firmasDao.consultarID(modulo_firmas);
	}

	public Modulo_firmas guardar(Map<String, Object> parametros) {
		try {
			List<Map<String, Object>> lista_datos_reportes = (List<Map<String, Object>>) parametros
					.get("lista_datos_reportes");
			Modulo_firmas modulo_firmas = (Modulo_firmas) parametros
					.get("modulo_firmas");
			String accion = (String) parametros.get("accion");
			Usuarios usuarios = (Usuarios) parametros.get("usuario");
			if (accion.equalsIgnoreCase("registrar")) {
				crear(modulo_firmas);
				modulo_firmas = consultar(modulo_firmas);
			} else {
				int result = actualizar(modulo_firmas);
				if (result == 0) {
					throw new Exception(
							"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
				}
			}

			Modulo_firmas_reportes firmas_reportesWhere = new Modulo_firmas_reportes();
			firmas_reportesWhere.setCodigo_empresa(modulo_firmas
					.getCodigo_empresa());
			firmas_reportesWhere.setCodigo_sucursal(modulo_firmas
					.getCodigo_sucursal());
			firmas_reportesWhere.setId_modulo_firma(modulo_firmas.getId());
			modulo_firmas_reportesDao.eliminar(firmas_reportesWhere);

			int i = 1;
			for (Map<String, Object> elementoMap : lista_datos_reportes) {
				Elemento elemento = (Elemento) elementoMap
						.get(Modulo_firmasAction.REPORTE);

				Modulo_firmas_reportes firmas_reportes = new Modulo_firmas_reportes();
				firmas_reportes.setCodigo_empresa(modulo_firmas
						.getCodigo_empresa());
				firmas_reportes.setCodigo_sucursal(modulo_firmas
						.getCodigo_sucursal());
				firmas_reportes.setCodigo_reporte(elemento.getCodigo());
				firmas_reportes.setId_modulo_firma(modulo_firmas.getId());
				firmas_reportes.setCreacion_user(usuarios.getCodigo());
				firmas_reportes.setUltimo_update(new Timestamp(System
						.currentTimeMillis()));
				firmas_reportes.setUltimo_user(usuarios.getCodigo());

				if (modulo_firmas_reportesDao.consultar(firmas_reportes) != null) {
					modulo_firmas_reportesDao.actualizar(firmas_reportes);
				} else {

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("codigo_empresa",
							firmas_reportes.getCodigo_empresa());
					params.put("codigo_sucursal",
							firmas_reportes.getCodigo_sucursal());
					params.put("codigo_reporte",
							firmas_reportes.getCodigo_reporte());

					if (modulo_firmas_reportesDao.existe(params)) {
						Elemento elementoReporte = new Elemento();
						elementoReporte.setCodigo(firmas_reportes
								.getCodigo_reporte());
						elementoReporte
								.setTipo(IConstantes.ELEMENTO_TIPO_REPORTE_CON_FIRMA);
						elementoReporte = elementoDao
								.consultar(elementoReporte);
						throw new ValidacionRunTimeException(
								"El reporte "
										+ (elementoReporte != null ? elementoReporte
												.getDescripcion() : "")
										+ " en la posicion " + i
										+ " ya tiene relacionada una firma!!");
					} else {
						modulo_firmas_reportesDao.crear(firmas_reportes);
					}
				}
				++i;
			}
			return modulo_firmas;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Modulo_firmas consultarFirma(Map<String, Object> map) {
		return modulo_firmasDao.consultarFirma(map);
	}
}