/*
 * AdministradoraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.dao.AdministradoraDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Tercero;
import contaweb.modelo.bean.Tipo_tercero;
import contaweb.modelo.dao.TerceroDao;
import contaweb.modelo.dao.Tipo_terceroDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("administradoraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AdministradoraService implements Serializable{

	private String limit;

	@Autowired
	private AdministradoraDao administradoraDao;
	@Autowired
	private TerceroDao terceroDao;
	@Autowired
	private Tipo_terceroDao tipo_terceroDao;

	public void crear(Administradora administradora) {
		try {
			if (consultar(administradora) != null) {
				throw new ValidacionRunTimeException(
						"Administradora ya se encuentra en la base de datos");
			}
			administradoraDao.crear(administradora);
			guardarTerceroAdministradora(administradora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Administradora administradora) {
		try {
			int result = administradoraDao.actualizar(administradora);
			guardarTerceroAdministradora(administradora);
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Administradora consultar(Administradora administradora) {
		try {
			return administradoraDao.consultar(administradora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Administradora administradora) {
		try {
			return administradoraDao.eliminar(administradora);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Administradora> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return administradoraDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map> listarDesdeContratos(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return administradoraDao.listarDesdeContratos(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Administradora> listarAdministradorasServicio(
			Map<String, Object> params) {
		return administradoraDao.listarAdministradorasServicio(params);
	}

	/**
	 * Este metodo permite generar un tercero de una administradora
	 * 
	 * @param admin
	 *            Administradora.class
	 * @throws Exception
	 * */
	public void guardarTerceroAdministradora(Administradora admin)
			throws Exception {
		if (admin != null) {
			Tipo_tercero tipo = new Tipo_tercero();
			Tercero tercero = new Tercero();
			tercero.setCodigo_empresa(admin.getCodigo_empresa());
			tercero.setCodigo_sucursal(admin.getCodigo_sucursal());
			tercero.setNro_identificacion(admin.getCodigo());
			tercero.setTipo_identificacion("NI");
			tercero.setNombre1(admin.getNombre());
			tercero.setNombre2("");
			tercero.setApellido1("");
			tercero.setApellido2("");
			tercero.setDireccion(admin.getDireccion());
			tercero.setTel_oficina(admin.getTelefono());
			tercero.setTel_res(admin.getTelefono());
			tercero.setFax("");
			tercero.setContacto("");
			tercero.setEmail("");
			tercero.setCodigo_dpto(admin.getCodigo_dpto());
			tercero.setCodigo_municipio(admin.getCodigo_municipio());
			tercero.setZona_venta("");
			tercero.setCodigo_vendedor("");
			tercero.setTipo_contribuyente(admin.getTipo_contribuyente());
			tercero.setObservacion("");
			tercero.setCreacion_date(admin.getCreacion_date());
			tercero.setUltimo_update(admin.getUltimo_update());
			tercero.setCreacion_user(admin.getCreacion_user());
			tercero.setUltimo_user(admin.getUltimo_user());
			tercero.setEmpresa("");
			tercero.setDireccion_empresa("");
			tercero.setTelefono_empresa("");
			tercero.setCargo("");
			tercero.setTiempo_servicio("");
			tercero.setIngresos(0);
			tercero.setPropietario("");
			tercero.setDireccion_propietario("");
			tercero.setValor_arrendamiento(0);
			tercero.setTiempo_habita("");
			tercero.setBanco("");
			tercero.setTarifa_ica(0.0);
			tercero.setTipo("ASEGURADORA");
			tercero.setCuenta_cobrar(admin.getCuenta_cobrar());
			tercero.setCuenta_cobrar2(admin.getCuenta_cobrar2());
			tercero.setCuenta_cobrar3(admin.getCuenta_cobrar3());
			tercero.setCuenta_pagar(admin.getCuenta_pagar());
			tercero.setDv("");
			tercero.setTipo_aseguradora(admin.getTipo_aseguradora());
			tercero.setCuenta_retencion(admin.getCuenta_retencion());

			tercero.setAutoretencion(admin.getAutoretencion());
			tercero.setCuenta_autorete1(admin.getCuenta_autorete1());
			tercero.setCuenta_autorete2(admin.getCuenta_autorete2());
			tercero.setCuenta_reteica(admin.getCuenta_reteica());
			if (terceroDao.consultar(tercero) != null) {
				terceroDao.actualizar(tercero);
			} else {
				terceroDao.crear(tercero);
				tipo.setCodigo_empresa(tercero.getCodigo_empresa());
				tipo.setCodigo_sucursal(tercero.getCodigo_sucursal());
				tipo.setNro_identificacion(tercero.getNro_identificacion());
				tipo.setTipo_tercero("02");
				tipo.setCreacion_date(tercero.getCreacion_date());
				tipo.setUltimo_update(tercero.getUltimo_update());
				tipo.setCreacion_user(tercero.getCreacion_user());
				tipo.setUltimo_user(tercero.getUltimo_user());
				tipo_terceroDao.crear(tipo);
			}
		}
	}
}
