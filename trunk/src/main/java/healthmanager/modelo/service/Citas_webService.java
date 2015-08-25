/*
 * CitasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Citas_web;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.dao.Citas_webDao;
import healthmanager.modelo.dao.PacienteDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("citas_webService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Citas_webService implements Serializable{

	private String limit;

	@Autowired
	private Citas_webDao citas_webDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private PacienteDao pacienteDao;

	public Citas_web guardarCita(Citas_web citas_web, String dir, String tel) {
		try {
			String codigo_cita = consecutivoService.crearConsecutivo(
					citas_web.getCodigo_empresa(),
					citas_web.getCodigo_sucursal(), "CITAS");
			citas_web.setCodigo_cita(consecutivoService.getZeroFill(
					codigo_cita, 10));
			if (consultar(citas_web) != null) {
				throw new HealthmanagerException("Cita " + codigo_cita
						+ " ya se encuentra en la base de datos");
			}

			// Map map = new HashMap();
			// map.put("codigo_empresa", citas_web.getCodigo_empresa());
			// map.put("codigo_sucursal",citas_web.getCodigo_sucursal());
			// map.put("nro_identificacion", citas_web.getNro_identificacion());
			// map.put("anio",Integer.parseInt(new
			// SimpleDateFormat("yyyy").format(citas_web.getFecha_cita())));
			// setLimit("limit 1 offset 0");
			// List<Citas_web> lista_citas = listar(map);
			// if(citas_web.getTipo_cita().equals("1") &&
			// !lista_citas.isEmpty()){
			// throw new
			// HealthmanagerException("Debe seleccionar en el tipo de cita REPETIDO EN EL año");
			// }else if(citas_web.getTipo_cita().equals("2") &&
			// lista_citas.isEmpty()){
			// throw new
			// HealthmanagerException("Debe seleccionar en el tipo de cita PRIMERA VEZ");
			// }

			citas_webDao.crear(citas_web);
			consecutivoService.actualizarConsecutivo(
					citas_web.getCodigo_empresa(),
					citas_web.getCodigo_sucursal(), "CITAS", codigo_cita);

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(citas_web.getCodigo_empresa());
			paciente.setCodigo_sucursal(citas_web.getCodigo_sucursal());
			paciente.setNro_identificacion(citas_web.getNro_identificacion());
			paciente = pacienteDao.consultar(paciente);
			if (paciente == null) {
				throw new HealthmanagerException("Paciente "
						+ citas_web.getNro_identificacion()
						+ " no se encuentra en la base de datos");
			}
			paciente.setDireccion(dir.toUpperCase().trim());
			paciente.setTel_res(tel.toUpperCase().trim());
			pacienteDao.actualizar(paciente);

			return citas_web;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Citas_web citas_web) {
		try {
			if (consultar(citas_web) != null) {
				throw new HealthmanagerException(
						"Citas ya se encuentra en la base de datos");
			}
			citas_webDao.crear(citas_web);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Citas_web citas_web) {
		try {
			return citas_webDao.actualizar(citas_web);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Citas_web consultar(Citas_web citas_web) {
		try {
			return citas_webDao.consultar(citas_web);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Citas_web citas_web) {
		try {
			return citas_webDao.eliminar(citas_web);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> map) {
		try {
			return citas_webDao.existe(map);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Citas_web> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return citas_webDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}


	public int obtenerCitasAseguradora(Map params) {
		return citas_webDao.obtenerCitasAseguradora(params);
	}

}
