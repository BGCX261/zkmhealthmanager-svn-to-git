/*
 * Facturacion_servicioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.dao.Datos_servicioDao;
import healthmanager.modelo.dao.Facturacion_servicioDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.service.ConsecutivoService;
import contaweb.modelo.service.FacturacionService;

@Service("facturacion_servicioService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Facturacion_servicioService implements Serializable{

	private String limit;

	@Autowired
	private Facturacion_servicioDao facturacion_servicioDao;
	@Autowired
	private Datos_servicioDao datos_servicioDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	@Autowired
	private FacturacionService facturacionService;

	public void crear(Facturacion_servicio facturacion_servicio) {
		try {
			if (consultar(facturacion_servicio) != null) {
				throw new HealthmanagerException(
						"Facturacion_servicio ya se encuentra en la base de datos");
			}
			facturacion_servicioDao.crear(facturacion_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public synchronized Facturacion_servicio guardar(Map<String, Object> datos,
			boolean fact_aut) {
		try {
			Facturacion_servicio facturacion_servicio = (Facturacion_servicio) datos
					.get("facturacion_servicio");
			List<Datos_servicio> lista_detalle = (List<Datos_servicio>) datos
					.get("lista_detalle");
			String accion = (String) datos.get("accion");
			if (accion.equalsIgnoreCase("registrar")) {
				// String nro_factura = consecutivoService.obtenerConsecutivo(
				// facturacion_servicio.getCodigo_empresa(),
				// facturacion_servicio.getCodigo_sucursal(), "SERVICIOS");
				// facturacion_servicio.setNro_factura(consecutivoService.getZeroFill(nro_factura,
				// 10));
				// if (consultar(facturacion_servicio) != null) {
				// throw new HealthmanagerException(
				// "Servicio nro "
				// + nro_factura
				// +
				// " ya se encuentra en la base de datos actualize el consecutivo del servicio");
				// }
				facturacion_servicioDao.crear(facturacion_servicio);
				// consecutivoService.actualizarConsecutivo(
				// facturacion_servicio.getCodigo_empresa(),
				// facturacion_servicio.getCodigo_sucursal(), "SERVICIOS",
				// facturacion_servicio.getNro_factura());
			} else {
				facturacion_servicioDao.actualizar(facturacion_servicio);
				Datos_servicio datos_servicioAux = new Datos_servicio();
				datos_servicioAux.setCodigo_empresa(facturacion_servicio
						.getCodigo_empresa());
				datos_servicioAux.setCodigo_sucursal(facturacion_servicio
						.getCodigo_sucursal());
				datos_servicioAux.setNro_factura(facturacion_servicio
						.getNro_factura());
				datos_servicioDao.eliminar(datos_servicioAux);
			}

			int i = 1;
			for (Datos_servicio datos_servicio : lista_detalle) {
				datos_servicio.setConsecutivo(i++ + "");
				datos_servicio.setNro_factura(facturacion_servicio
						.getNro_factura());
				
				// actualizamos el numero de autorizacion a datos servicio
				if((datos_servicio.getNumero_autorizacion() == null
						|| datos_servicio.getNumero_autorizacion().trim().isEmpty())
						&& facturacion_servicio.getNumero_autorizacion() != null
						&& !facturacion_servicio.getNumero_autorizacion().trim().isEmpty()){
					datos_servicio.setNumero_autorizacion(facturacion_servicio.getNumero_autorizacion());  
				}
				datos_servicioDao.crear(datos_servicio);
			}

			if (fact_aut) {
				facturacionService.actualizarFacturaAutomatico(
						facturacion_servicio.getCodigo_empresa(),
						facturacion_servicio.getCodigo_sucursal(),
						facturacion_servicio.getNro_ingreso(),
						facturacion_servicio.getNro_identificacion());
			}

			return facturacion_servicio;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Facturacion_servicio facturacion_servicio) {
		try {
			return facturacion_servicioDao.actualizar(facturacion_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Facturacion_servicio consultar(
			Facturacion_servicio facturacion_servicio) {
		try {
			return facturacion_servicioDao.consultar(facturacion_servicio);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminarRegistro(Facturacion_servicio facturacion_servicio) {
		try {
			int result = facturacion_servicioDao.eliminar(facturacion_servicio);
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminarActualizarFactura(
			Facturacion_servicio facturacion_servicio) {
		try {
			int result = facturacion_servicioDao.eliminar(facturacion_servicio);
			facturacionService.actualizarFacturaAutomatico(
					facturacion_servicio.getCodigo_empresa(),
					facturacion_servicio.getCodigo_sucursal(),
					facturacion_servicio.getNro_ingreso(),
					facturacion_servicio.getNro_identificacion());
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Facturacion_servicio> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return facturacion_servicioDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Object parameters) {
		return this.facturacion_servicioDao.existe(parameters);
	}

}
