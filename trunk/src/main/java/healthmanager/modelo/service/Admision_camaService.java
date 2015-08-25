/*
 * Admision_camaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Admision_cama;
import healthmanager.modelo.bean.Cama;
import healthmanager.modelo.bean.Hospitalizacion;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Admision_camaDao;
import healthmanager.modelo.dao.CamaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("admision_camaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Admision_camaService implements Serializable{

	@Autowired
	private AdmisionDao admisionDao;

	@Autowired
	private CamaDao camaDao;

	@Autowired
	private Admision_camaDao admision_camaDao;

	public void crear(Admision_cama admision_cama) {
		try {
			if (consultar(admision_cama) != null) {
				throw new HealthmanagerException(
						"Admision_cama ya se encuentra en la base de datos");
			}
			admision_camaDao.crear(admision_cama);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Admision_cama admision_cama) {
		try {
			return admision_camaDao.actualizar(admision_cama);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Admision_cama consultar(Admision_cama admision_cama) {
		try {
			return admision_camaDao.consultar(admision_cama);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Admision_cama admision_cama) {
		try {
			return admision_camaDao.eliminar(admision_cama);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Admision_cama> listar(Map<String, Object> parameter) {
		try {
			return admision_camaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarLiberacionCamaOcupada(Hospitalizacion hospitalizacion) {
		// Actualizamos la cama //
		Admision_cama admision_cama = new Admision_cama();
		admision_cama.setCodigo_empresa(hospitalizacion.getCodigo_empresa());
		admision_cama.setCodigo_sucursal(hospitalizacion.getCodigo_sucursal());
		admision_cama.setNro_ingreso(hospitalizacion.getNro_ingreso());
		admision_cama.setNro_identificacion(hospitalizacion
				.getNro_identificacion());
		admision_cama = admision_camaDao.consultar(admision_cama);
		if (admision_cama != null) {
			admision_cama.setFacturacion(hospitalizacion.getNro_factura());
			admision_cama.setFecha_egreso(hospitalizacion.getFecha_egreso());
			admision_camaDao.actualizar(admision_cama);

			Admision admision = new Admision();
			admision.setCodigo_empresa(admision_cama.getCodigo_empresa());
			admision.setCodigo_sucursal(admision_cama.getCodigo_sucursal());
			admision.setNro_identificacion(admision_cama
					.getNro_identificacion());
			admision.setNro_ingreso(admision_cama.getNro_ingreso());
			admision = admisionDao.consultar(admision);

			// Liberamos la cama nuevamente //
			Cama cama = new Cama();
			cama.setCodigo_empresa(admision_cama.getCodigo_empresa());
			cama.setCodigo_sucursal(admision_cama.getCodigo_sucursal());
			cama.setTipo_atencion(admision_cama.getTipo_atencion());
			cama.setCodigo_pabellon(admision_cama.getCodigo_pabellon());
			cama.setCodigo_habitacion(admision_cama.getCodigo_habitacion());
			cama.setCodigo(admision_cama.getCodigo_cama());
			cama.setCodigo_centro(admision.getCodigo_centro());
			cama = camaDao.consultar(cama);
			if (cama != null) {
				cama.setEstado("01");
				cama.setFecha_ocupacion(null);
				cama.setCodigo_paciente("");
				cama.setUltimo_update(hospitalizacion.getUltimo_update());
				cama.setUltimo_user(hospitalizacion.getUltimo_user());
				camaDao.actualizar(cama);
			}
		}

	}

	public boolean existe(Object parameters) {
		return this.admision_camaDao.existe(parameters);
	}

}
