/*
 * Horarios_medicoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Horarios_medico;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.Detalles_horariosDao;
import healthmanager.modelo.dao.Horarios_medicoDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.res.Res;
import com.framework.res.ZKSimpleCalendarEvent;
import com.framework.util.secuencia_hora.DetallesHorariosModel;

@Service("horarios_medicoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Horarios_medicoService implements Serializable{
	
	private String limit;

	@Autowired
	private Horarios_medicoDao horarios_medicoDao;
	@Autowired
	private Detalles_horariosDao detallesHorariosDao;
	@Autowired
	private CitasDao citasDao;
	
	public void crear(Horarios_medico horarios_medico) {
		try {
			if (consultar(horarios_medico) != null) {
				throw new HealthmanagerException(
						"Horarios_medico ya se encuentra en la base de datos");
			}
			horarios_medicoDao.crear(horarios_medico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Horarios_medico horarios_medico) {
		try {
			return horarios_medicoDao.actualizar(horarios_medico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Horarios_medico consultar(Horarios_medico horarios_medico) {
		try {
			return horarios_medicoDao.consultar(horarios_medico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Horarios_medico horarios_medico) {
		try {
			Detalles_horarios detalles_horarios = new Detalles_horarios();
			detalles_horarios.setCodigo_empresa(horarios_medico
					.getCodigo_empresa());
			detalles_horarios.setCodigo_sucursal(horarios_medico
					.getCodigo_sucursal());
			detalles_horarios.setCodigo_medico(horarios_medico
					.getCodigo_medico());
			detallesHorariosDao.eliminar_horario(detalles_horarios);
			return horarios_medicoDao.eliminar(horarios_medico);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Horarios_medico> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return horarios_medicoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void guardarHorarioCitas(Map datos) {
		try {
			String accion_form = (String) datos.get("accion_form");
			Horarios_medico horarios = (Horarios_medico) datos.get("horarios");
			List<Detalles_horarios> detalles = (List<Detalles_horarios>) datos
					.get("detalles");
			if (accion_form.equalsIgnoreCase("registrar")) {
				crear(horarios);
			} else {
				actualizar(horarios);
			}

			/* Eliminamos los detalles */
			// Detalles_horarios detalles_horarios = new Detalles_horarios();
			// detalles_horarios.setCodigo_empresa(horarios.getCodigo_empresa());
			// detalles_horarios.setCodigo_sucursal(horario.getClass());
			// detalles_horarios.setCodigo_medico(codigo_medico);

			/* guardamos los horarios */
			// Cuando sea registrar agregamos los detalles del horario, mientras
			// tanto no
			if (accion_form.equalsIgnoreCase("registrar"))
				for (Detalles_horarios detalle : detalles) {
					detalle.setCodigo_medico(horarios.getCodigo_medico());
					detalle.setCodigo_empresa(horarios.getCodigo_empresa());
					detalle.setCodigo_sucursal(horarios.getCodigo_sucursal());
					detalle.setCreacion_user(horarios.getCreacion_user());
					detalle.setMotivo_consulta("1");
					detalle.setCodigo_centro(detalle.getCodigo_centro());
					detalle.setCodigo_rol(detalle.getCodigo_rol());

					detalle.setConsecutivo(detalle.getConsecutivo());
					detalle.setCodigo_programa_excepcion(detalle
							.getCodigo_programa_excepcion());

					if (detalle.getConsecutivo() != null) {
						detallesHorariosDao.actualizar(detalle);
					} else {
						detallesHorariosDao.crear(detalle);
					}
				}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminarSecuenciaHoarios(
			List<Detalles_horarios> listado_detalles,
			DetallesHorariosModel<Detalles_horarios> model) {
		try {
			int contador = 0;
			for (Detalles_horarios detalles_horarios : listado_detalles) {
				detallesHorariosDao.eliminar(detalles_horarios);
				model.remove(detalles_horarios.getFecha_inicial().getTime());
				contador++;

			}
			return contador;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminarDetallesPorDia(String codigo_empresa,
			String codigo_sucursal, String codigo_prestador,
			List<Detalles_horarios> listado_eventos) {
		try {
			int eliminar = 0;
			for (Detalles_horarios zkSimpleCalendarEvent : listado_eventos) {
				if (zkSimpleCalendarEvent.getConsecutivo() != null) {
					Detalles_horarios detalles_horarios = new Detalles_horarios();
					detalles_horarios.setCodigo_empresa(codigo_empresa);
					detalles_horarios.setCodigo_sucursal(codigo_sucursal);
					detalles_horarios.setCodigo_medico(codigo_prestador);
					detalles_horarios.setConsecutivo(zkSimpleCalendarEvent
							.getConsecutivo());
					detallesHorariosDao.eliminar(detalles_horarios);
					eliminar++;
				}
			}
			return eliminar;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarTrasladoCitas(Map<String, Object> datos) {
		try {

			Prestadores prestadores = (Prestadores) datos.get("prestadores");
			Prestadores prestadores_traslado = (Prestadores) datos.get("prestadores_traslado");
			List<Detalles_horarios> listado_detalles_aux = (List<Detalles_horarios>) datos.get("listado_detalles_aux");
			String creacion_user = (String) datos.get("creacion_user");
			String codigo_centro_taslado = (String) datos.get("codigo_centro_trasalado");
			
			Horarios_medico horarios_medico = new Horarios_medico();
			horarios_medico.setCodigo_empresa(prestadores.getCodigo_empresa());
			horarios_medico.setCodigo_sucursal(prestadores.getCodigo_sucursal());
			horarios_medico.setCodigo_medico(prestadores_traslado.getNro_identificacion());
			//Crea el horario medico de destino en caso de que el prestador no lo tenga
			if (horarios_medicoDao.consultar(horarios_medico) == null) {
				horarios_medico.setCreacion_date(new Timestamp(new Date().getTime()));
				horarios_medico.setCreacion_user(creacion_user);
				horarios_medico.setCodigo_rol("");
				horarios_medicoDao.crear(horarios_medico);
			}
			//log.info(">>>>>det1>"+listado_detalles_aux.size());
			for (Detalles_horarios detalles_horarios : listado_detalles_aux) {
				//Se listan las citas en el rango de horario de los detalles de horario para ser actualizadas
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", detalles_horarios.getCodigo_empresa());
				parametros.put("codigo_sucursal", detalles_horarios.getCodigo_sucursal());
				parametros.put("codigo_prestador_unico", detalles_horarios.getCodigo_medico());
				parametros.put("codigo_detalle_horario", detalles_horarios.getConsecutivo());
				List<Citas> listado_citas = citasDao.listar(parametros);
				//log.info(">>>>>cit2>"+listado_citas.size());
				//Consulta del detalle de horario del prestador de traslado cual coincide
				// con la hora del detalle de origen
				parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", detalles_horarios.getCodigo_empresa());
				parametros.put("codigo_sucursal", detalles_horarios.getCodigo_sucursal());
				parametros.put("codigo_medico", prestadores_traslado.getNro_identificacion());
				parametros.put("codigo_centro", codigo_centro_taslado);
				parametros.put("fecha_hora", detalles_horarios.getFecha_inicial());
				List<Detalles_horarios> lista_auxiliar = detallesHorariosDao.listar_por_hora(parametros);
				//log.info(">>>>>det2>"+lista_auxiliar.size());
				//Si hay detalles horario que coincidan con la hora inicial de la cita
				if (!lista_auxiliar.isEmpty()) {
					for (Detalles_horarios detalles_horarios_aux : lista_auxiliar) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						String fecha_ini1 = sdf.format(detalles_horarios.getFecha_inicial());
						String fecha_ini2 = sdf.format(detalles_horarios_aux.getFecha_inicial());
						String fecha_fin1 = sdf.format(detalles_horarios.getFecha_final());
						String fecha_fin2 = sdf.format(detalles_horarios_aux.getFecha_final());

						//log.info(">>>>>>"+detalles_horarios.getConsecutivo()+">"+fecha_ini1+" - "+fecha_fin1);
						//log.info(">>>>>>"+detalles_horarios_aux.getConsecutivo()+">"+fecha_ini2+" - "+fecha_fin2);
						
						if(fecha_ini2.equals(fecha_ini1) &&	fecha_fin2.equals(fecha_fin1)){
							//Consulta si el prestador de traslado tiene citas asignadas en los detalles de horario
							parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", detalles_horarios.getCodigo_empresa());
							parametros.put("codigo_sucursal", detalles_horarios.getCodigo_sucursal());
							parametros.put("codigo_prestador_unico", detalles_horarios_aux.getCodigo_medico());
							parametros.put("codigo_detalle_horario", detalles_horarios_aux.getConsecutivo());
							parametros.put("estado", '1');
							List<Citas> listado_prestador_traslado = citasDao.listar(parametros);
							
							if (!listado_prestador_traslado.isEmpty()) {
								Citas citas_aux = listado_prestador_traslado.get(0);
								throw new Exception(
										"No se puede hacer el traslado de horarios. Verifique que el prestador de traslado tenga los cupos disponibles para que se pueda completar la operacion. Cita ocupada : "
												+ ZKSimpleCalendarEvent.simpleDateFormat
														.format(citas_aux
																.getFecha_cita()));
							}
						}else{
							throw new Exception("Los rangos de horario de los prestadores no coinciden!!!");
						}
					}
					
					for (Detalles_horarios detalles_horarios_aux : lista_auxiliar) {
						detallesHorariosDao.eliminar(detalles_horarios_aux);
					}
					
					//Se clona el detalle de horario de el prestador de origen al destino
					Detalles_horarios detalles_horarios_clon = Res.clonar(detalles_horarios);
					detalles_horarios_clon.setCodigo_medico(prestadores_traslado.getNro_identificacion());
					detalles_horarios_clon.setCodigo_centro(codigo_centro_taslado);
					detalles_horarios_clon.setCreacion_date(new Timestamp(new Date().getTime()));
					detalles_horarios_clon.setCreacion_user(creacion_user);
					detalles_horarios_clon.setConsecutivo(null);
					detallesHorariosDao.crear(detalles_horarios_clon);
					//Se reasigna la cita al prestador destino y con el nuevo detalle de horario
					for (Citas citas_aux : listado_citas) {
						citas_aux.setCodigo_prestador(prestadores_traslado.getNro_identificacion());
						citas_aux.setCodigo_detalle_horario(detalles_horarios_clon.getConsecutivo());
						citasDao.actualizar(citas_aux);
					}
				} else {
					//Si no hay detalles horario que coincidan con la hora inicial de la cita
					//clona el detalle del prestador de origen
					Detalles_horarios detalles_horarios_clon = Res.clonar(detalles_horarios);
					detalles_horarios_clon.setCodigo_medico(prestadores_traslado.getNro_identificacion());
					detalles_horarios_clon.setCreacion_date(new Timestamp(new Date().getTime()));
					detalles_horarios_clon.setCreacion_user(creacion_user);
					detalles_horarios_clon.setConsecutivo(null);
					detallesHorariosDao.crear(detalles_horarios_clon);
					//Se reasigna la cita al prestador destino y con el nuevo detalle de horario
					for (Citas citas_aux : listado_citas) {
						citas_aux.setCodigo_prestador(prestadores_traslado.getNro_identificacion());
						citas_aux.setCodigo_detalle_horario(detalles_horarios_clon.getConsecutivo());
						citasDao.actualizar(citas_aux);
					}
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
