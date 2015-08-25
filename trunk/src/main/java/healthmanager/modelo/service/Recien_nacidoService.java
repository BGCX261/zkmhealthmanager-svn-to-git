/*
 * Recien_nacidoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Recien_nacido;
import healthmanager.modelo.dao.Recien_nacidoDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.FacturacionService;

@Service("recien_nacidoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Recien_nacidoService implements Serializable{

	private String limit;

	@Autowired
	private Recien_nacidoDao recien_nacidoDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	@Autowired
	private FacturacionService facturacionService;

	public void crear(Recien_nacido recien_nacido) {
		try {
			String nro_factura = consecutivoService.getZeroFill(
					consecutivoService.crearConsecutivo(
							recien_nacido.getCodigo_empresa(),
							recien_nacido.getCodigo_sucursal(),
							"RECIEN_NACIDOS"), 10);
			recien_nacido.setNro_factura(nro_factura);
			if (consultar(recien_nacido) != null) {
				throw new HealthmanagerException(
						"Recien_nacido ya se encuentra en la base de datos");
			}
			recien_nacidoDao.crear(recien_nacido);
			consecutivoService.actualizarConsecutivo(
					recien_nacido.getCodigo_empresa(),
					recien_nacido.getCodigo_sucursal(), "RECIEN_NACIDOS",
					nro_factura);

			Facturacion facturacion = new Facturacion();
			facturacion.setCodigo_empresa(recien_nacido.getCodigo_empresa());
			facturacion.setCodigo_sucursal(recien_nacido.getCodigo_sucursal());
			facturacion
					.setCodigo_tercero(recien_nacido.getNro_identificacion());
			facturacion.setNro_ingreso(recien_nacido.getNro_ingreso());
			// //log.info("facturacion antes: "+facturacion);
			facturacion = facturacionService.consultar(facturacion);
			if (facturacion != null) {
				recien_nacidoDao.actualizar(recien_nacido);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Recien_nacido recien_nacido) {
		try {
			return recien_nacidoDao.actualizar(recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Recien_nacido consultar(Recien_nacido recien_nacido) {
		try {
			return recien_nacidoDao.consultar(recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Recien_nacido recien_nacido) {
		try {
			return recien_nacidoDao.eliminar(recien_nacido);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Recien_nacido> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return recien_nacidoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.recien_nacidoDao.existe(parameters);
	}

}
