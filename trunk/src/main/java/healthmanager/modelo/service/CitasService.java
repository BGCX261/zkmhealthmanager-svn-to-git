/*
 * CitasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Oportunidad_citas;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.PacienteDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("citasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CitasService implements Serializable{

	private String limit;

	@Autowired
	private CitasDao citasDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private PacienteDao pacienteDao;

	public Citas guardarCita(Citas citas, String dir, String tel) {
		try {
			String codigo_cita = consecutivoService.crearConsecutivo(
					citas.getCodigo_empresa(), citas.getCodigo_sucursal(),
					"CITAS");
			citas.setCodigo_cita(consecutivoService
					.getZeroFill(codigo_cita, 10));
			if (consultar(citas) != null) {
				throw new HealthmanagerException("Cita " + codigo_cita
						+ " ya se encuentra en la base de datos");
			}

			// Map map = new HashMap();
			// map.put("codigo_empresa", citas.getCodigo_empresa());
			// map.put("codigo_sucursal",citas.getCodigo_sucursal());
			// map.put("nro_identificacion", citas.getNro_identificacion());
			// map.put("anio",Integer.parseInt(new
			// SimpleDateFormat("yyyy").format(citas.getFecha_cita())));
			// map.put("tipo_consulta",citas.getTipo_consulta());
			// setLimit("limit 1 offset 0");
			// List<Citas> lista_citas = listar(map);
			// if(citas.getTipo_cita().equals("1") && !lista_citas.isEmpty()){
			// //throw new
			// HealthmanagerException("Debe seleccionar en el tipo de cita REPETIDO EN EL año");
			// }else if(citas.getTipo_cita().equals("2") &&
			// lista_citas.isEmpty()){
			//
			// //throw new
			// HealthmanagerException("Debe seleccionar en el tipo de cita PRIMERA VEZ");
			// }

			citasDao.crear(citas);
			consecutivoService.actualizarConsecutivo(citas.getCodigo_empresa(),
					citas.getCodigo_sucursal(), "CITAS", codigo_cita);

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(citas.getCodigo_empresa());
			paciente.setCodigo_sucursal(citas.getCodigo_sucursal());
			paciente.setNro_identificacion(citas.getNro_identificacion());
			paciente = pacienteDao.consultar(paciente);
			if (paciente == null) {
				throw new HealthmanagerException("Paciente "
						+ citas.getNro_identificacion()
						+ " no se encuentra en la base de datos");
			}
			paciente.setDireccion(dir.toUpperCase().trim());
			paciente.setTel_res(tel.toUpperCase().trim());
			pacienteDao.actualizar(paciente);

			return citas;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Citas citas) {
		try {
			if (consultar(citas) != null) {
				throw new HealthmanagerException(
						"Citas ya se encuentra en la base de datos");
			}
			citasDao.crear(citas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Citas citas) {
		try {
			return citasDao.actualizar(citas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Citas consultar(Citas citas) {
		try {
			return citasDao.consultar(citas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Citas consultar_citas(Map parameter) {
		try {
			return citasDao.consultar_citas(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Citas citas) {
		try {
			return citasDao.eliminar(citas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> map) {
		try {
			return citasDao.existe(map);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Citas> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return citasDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listar_oportunidad(Map parameter) {
		try {
			parameter.put("limit", limit);
			return citasDao.listar_oportunidad(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> listar_oportunidad_pacientes(Map parameter) {
		try {
			parameter.put("limit", limit);
			return citasDao.listar_oportunidad_pacientes(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Oportunidad_citas> listar_oportunidad_consulta(Map parameter) {
		try {
			parameter.put("limit", limit);
			return citasDao.listar_oportunidad_consulta(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public int obtenerCitasAseguradora(Map params) {
		return citasDao.obtenerCitasAseguradora(params);
	}

	public int getCitas(Map<String, Object> parameter) {
		return citasDao.getCitas(parameter);
	}

}
