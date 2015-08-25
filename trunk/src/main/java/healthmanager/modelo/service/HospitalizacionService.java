/*
 * HospitalizacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Hospitalizacion;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.HospitalizacionDao;
import healthmanager.modelo.dao.PacienteDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.FacturacionService;

@Service("hospitalizacionService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class HospitalizacionService implements Serializable{

	private String limit;

	@Autowired
	private HospitalizacionDao hospitalizacionDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private Admision_camaService admision_camaService;

	@Autowired
	private FacturacionService facturacionService;

	public void crear(Hospitalizacion hospitalizacion) {
		try {
			String nro_factura = consecutivoService.getZeroFill(
					consecutivoService.crearConsecutivo(
							hospitalizacion.getCodigo_empresa(),
							hospitalizacion.getCodigo_sucursal(), "HOSP"), 10);
			hospitalizacion.setNro_factura(nro_factura);
			if (consultar(hospitalizacion) != null) {
				throw new HealthmanagerException(
						"Hospitalizacion ya se encuentra en la base de datos");
			}
			hospitalizacionDao.crear(hospitalizacion);
			consecutivoService.actualizarConsecutivo(
					hospitalizacion.getCodigo_empresa(),
					hospitalizacion.getCodigo_sucursal(), "HOSP", nro_factura);

			Facturacion facturacion = new Facturacion();
			facturacion.setCodigo_empresa(hospitalizacion.getCodigo_empresa());
			facturacion
					.setCodigo_sucursal(hospitalizacion.getCodigo_sucursal());
			facturacion.setCodigo_tercero(hospitalizacion
					.getNro_identificacion());
			facturacion.setNro_ingreso(hospitalizacion.getNro_ingreso());
			// //log.info("facturacion antes: "+facturacion);

			admision_camaService.guardarLiberacionCamaOcupada(hospitalizacion);

			facturacion = facturacionService.consultar(facturacion);
			if (facturacion != null) {
				hospitalizacionDao.actualizar(hospitalizacion);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Hospitalizacion hospitalizacion) {
		try {
			admision_camaService.guardarLiberacionCamaOcupada(hospitalizacion);
			return hospitalizacionDao.actualizar(hospitalizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Hospitalizacion consultar(Hospitalizacion hospitalizacion) {
		try {
			return hospitalizacionDao.consultar(hospitalizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Hospitalizacion hospitalizacion) {
		try {
			admision_camaService.guardarLiberacionCamaOcupada(hospitalizacion);
			return hospitalizacionDao.eliminar(hospitalizacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Hospitalizacion> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return hospitalizacionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	

}
