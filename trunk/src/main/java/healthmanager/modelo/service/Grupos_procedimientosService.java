/*
 * Grupos_procedimientosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;
import healthmanager.modelo.bean.Grupos_procedimientos;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.dao.Detalle_grupos_procedimientosDao;
import healthmanager.modelo.dao.Grupos_procedimientosDao;
import healthmanager.modelo.dao.ProcedimientosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("grupos_procedimientosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Grupos_procedimientosService implements Serializable{

	@Autowired
	private Grupos_procedimientosDao grupos_procedimientosDao;
	@Autowired
	private Detalle_grupos_procedimientosDao detalle_grupos_procedimientosDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private ProcedimientosDao procedimientoDao;

	public void crear(Grupos_procedimientos grupos_procedimientos) {
		try {
			if (consultar(grupos_procedimientos) != null) {
				throw new HealthmanagerException(
						"Grupos_procedimientos ya se encuentra en la base de datos");
			}
			grupos_procedimientosDao.crear(grupos_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Grupos_procedimientos grupos_procedimientos) {
		try {
			return grupos_procedimientosDao.actualizar(grupos_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Grupos_procedimientos consultar(
			Grupos_procedimientos grupos_procedimientos) {
		try {
			return grupos_procedimientosDao.consultar(grupos_procedimientos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Grupos_procedimientos grupos_procedimientos) {
		try {
			Procedimientos procedimiento = new Procedimientos();
			procedimiento.setId_procedimiento(new Long(grupos_procedimientos
					.getId_codigo_grupo()));
			int result = grupos_procedimientosDao
					.eliminar(grupos_procedimientos);
			procedimientoDao.eliminar(procedimiento);
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Grupos_procedimientos> listar(Map<String, Object> parameters) {
		try {
			return grupos_procedimientosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return grupos_procedimientosDao.existe(parameters);
	}

	public void guardar(Map<String, Object> map) {
		try {
			String CONSECUTIVO_GRUPO_PROCEDIMIENTO = "GRUPOS_PROCEDIMIENTO";
			Grupos_procedimientos grupos_procedimientos = (Grupos_procedimientos) map
					.get("grupos_pro");
			String accion = (String) map.get("accion");
			List<Detalle_grupos_procedimientos> detalle_grupos_procedimientos = (List<Detalle_grupos_procedimientos>) map
					.get("dtt_grupos");
			String tipo_procedimiento = (String) map.get("tipo_procedimiento");
			String sexo = (String) map.get("sexo");
			int frecuencia_ordenamiento = (Integer) map
					.get("frecuencia_ordenamiento");
			String nivel = (String) map.get("nivel");

			Double valor_iss01_defecto = 0d;
			Double porcentaje_soat_defecto = 0d;
			Double valor_iss04_defecto = 0d;
			for (Detalle_grupos_procedimientos dtt_d_pro : detalle_grupos_procedimientos) {
				Procedimientos procedimientos = new Procedimientos();
				procedimientos.setId_procedimiento(new Long(dtt_d_pro
						.getId_procedimiento()));
				procedimientos = procedimientoDao.consultar(procedimientos);
				if (procedimientos != null) {
					valor_iss01_defecto += procedimientos
							.getValoriss01_defecto();
					porcentaje_soat_defecto += procedimientos
							.getPorcentaje_defecto();
					valor_iss04_defecto += procedimientos
							.getValoriss04_defecto();
				}
			}

			if (accion.equalsIgnoreCase("registrar")) {
				// Creamos en el modulo de los procedimientos
				Procedimientos procedimiento = getProcedimiento(
						grupos_procedimientos, tipo_procedimiento, sexo,
						frecuencia_ordenamiento, nivel);
				procedimiento.setPorcentaje_defecto(porcentaje_soat_defecto);
				procedimiento.setValoriss01_defecto(valor_iss01_defecto);
				procedimiento.setValoriss04_defecto(valor_iss04_defecto);

				String codigo_procedimiento = consecutivoService
						.crearConsecutivo(
								grupos_procedimientos.getCodigo_empresa(),
								grupos_procedimientos.getCodigo_sucursal(),
								CONSECUTIVO_GRUPO_PROCEDIMIENTO);
				DecimalFormat decimalFormat = new DecimalFormat("G00000");
				String codigo_estandar = decimalFormat.format(Integer
						.parseInt(codigo_procedimiento));
				// Registramos los procedimientos
				procedimiento.setCodigo_cups(codigo_estandar);
				procedimiento.setCodigo_soat(codigo_estandar);
				procedimientoDao.crear(procedimiento);
				consecutivoService.actualizarConsecutivo(
						grupos_procedimientos.getCodigo_empresa(),
						grupos_procedimientos.getCodigo_sucursal(),
						CONSECUTIVO_GRUPO_PROCEDIMIENTO, codigo_procedimiento);

				grupos_procedimientos.setId_codigo_grupo(procedimiento
						.getId_procedimiento() + "");
				grupos_procedimientosDao.crear(grupos_procedimientos);
			} else {
				grupos_procedimientosDao.actualizar(grupos_procedimientos);

				// Creamos en el modulo de los procedimientos
				Procedimientos procedimiento = getProcedimiento(
						grupos_procedimientos, tipo_procedimiento, sexo,
						frecuencia_ordenamiento, nivel);
				procedimiento.setPorcentaje_defecto(porcentaje_soat_defecto);
				procedimiento.setValoriss01_defecto(valor_iss01_defecto);
				procedimiento.setValoriss04_defecto(valor_iss04_defecto);

				// Actualizamo los procedimientos
				procedimientoDao.actualizar(procedimiento);

				// Limpiamos los procedimientos que esten trabajando como
				// detalle de grupo
				Detalle_grupos_procedimientos dtt_eliminar = new Detalle_grupos_procedimientos();
				dtt_eliminar.setCodigo_empresa(grupos_procedimientos
						.getCodigo_empresa());
				dtt_eliminar.setCodigo_sucursal(grupos_procedimientos
						.getCodigo_sucursal());
				dtt_eliminar.setCodigo_grupo(grupos_procedimientos
						.getId_codigo_grupo());
				detalle_grupos_procedimientosDao.eliminar(dtt_eliminar);
			}

			// Registramos nuestro detalles del grupo
			int i = 0;
			for (Detalle_grupos_procedimientos dtt_temp : detalle_grupos_procedimientos) {
				dtt_temp.setCodigo_grupo(grupos_procedimientos
						.getId_codigo_grupo());
				dtt_temp.setConsecutivo(++i + "");
				detalle_grupos_procedimientosDao.crear(dtt_temp);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private Procedimientos getProcedimiento(
			Grupos_procedimientos grupos_procedimientos,
			String tipo_procedimiento, String sexo,
			int frecuencia_ordenamiento, String nivel) {
		Procedimientos procedimiento = new Procedimientos();

		if (grupos_procedimientos.getId_codigo_grupo() != null
				&& !grupos_procedimientos.getId_codigo_grupo().trim().isEmpty()) {
			procedimiento.setId_procedimiento(new Long(grupos_procedimientos
					.getId_codigo_grupo()));
			procedimiento = procedimientoDao.consultar(procedimiento);
		} else {
			procedimiento.setConsulta("N");
			procedimiento.setAut_medi_especialista("S");
			procedimiento.setAut_medi_general("S");
			procedimiento.setLimite_inferior("0");
			procedimiento.setLimite_superior("599");
			procedimiento.setQuirurgico("N");
			procedimiento.setPyp("N");
			procedimiento.setCobra_copago("N");
			procedimiento.setNivel("1");
			procedimiento.setEditable(true);
			procedimiento.setEs_grupo("S");
		}

		procedimiento.setDescripcion(grupos_procedimientos.getDescipcion());
		procedimiento.setCreacion_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		procedimiento.setUltimo_update(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		procedimiento.setDelete_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		procedimiento
				.setCreacion_user(grupos_procedimientos.getCreacion_user());
		procedimiento.setUltimo_user(grupos_procedimientos.getUltimo_user());
		procedimiento.setCodigo_contabilidad("20");
		procedimiento.setSexo(sexo);

		procedimiento.setFrecuencia_orden(frecuencia_ordenamiento);
		return procedimiento;
	}

}