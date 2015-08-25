/*
 * Seguimiento_control_pqtServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Control_convivientes_seguimiento;
import healthmanager.modelo.bean.Hisc_consulta_externa;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Seguimiento_control_pqt;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Control_convivientes_seguimientoDao;
import healthmanager.modelo.dao.Seguimiento_control_pqtDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

@Service("seguimiento_control_pqtService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Seguimiento_control_pqtService implements Serializable{

	@Autowired
	private Seguimiento_control_pqtDao seguimiento_control_pqtDao;
	@Autowired
	private Control_convivientes_seguimientoDao control_convivientes_seguimientoDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;
	@Autowired
	private Hisc_consulta_externaService hisc_consulta_externaService;
	@Autowired
	private Impresion_diagnosticaService impresion_diagnosticaService;

	private Manuales_tarifarios manuales_tarifarios;
	private Admision admision;
	@Autowired
	private AdmisionDao admisionDao;

	private Impresion_diagnostica impresion_diagnostica;

	public void crear(Seguimiento_control_pqt seguimiento_control_pqt) {
		try {
			String codigo_ficha = consecutivoService.crearConsecutivo(
					seguimiento_control_pqt.getCodigo_empresa(),
					seguimiento_control_pqt.getCodigo_sucursal(),
					IConstantes.CONS_SEGUIMIENTO_CONTROL_PQT);
			seguimiento_control_pqt.setCodigo_ficha(consecutivoService
					.getZeroFill(codigo_ficha, 10));
			if (consultar(seguimiento_control_pqt) != null) {
				throw new HealthmanagerException(
						"Codigo de ficha ya existe actualizar consecutivo "
								+ IConstantes.CONS_SEGUIMIENTO_CONTROL_PQT);
			}
			seguimiento_control_pqtDao.crear(seguimiento_control_pqt);
			consecutivoService.actualizarConsecutivo(
					seguimiento_control_pqt.getCodigo_empresa(),
					seguimiento_control_pqt.getCodigo_sucursal(),
					IConstantes.CONS_SEGUIMIENTO_CONTROL_PQT,
					seguimiento_control_pqt.getCodigo_ficha());
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Seguimiento_control_pqt guardarDatos(Map<String, Object> datos) {

		try {
			Seguimiento_control_pqt seguimiento_control_pqt = (Seguimiento_control_pqt) datos
					.get("seguimiento_control_pqt");
			List<Control_convivientes_seguimiento> lista_detalle = (List<Control_convivientes_seguimiento>) datos
					.get("lista_detalle");
			String accion = (String) datos.get("accion");
			admision = (Admision) datos.get("admision");

			if (accion.equalsIgnoreCase("registrar")) {
				crear(seguimiento_control_pqt);

				if (admision != null
						&& admision.getVia_ingreso().equals(
								IVias_ingreso.CONTROL_LEPRA)) {
					crearConsultaTratamiento(admision);

				} else {
					if (impresion_diagnostica != null) {
						crearConsultaHistoria(admision, impresion_diagnostica);
					}

				}

			} else {
				seguimiento_control_pqtDao.actualizar(seguimiento_control_pqt);

				Control_convivientes_seguimiento control_convivientes_seguimientoAux = new Control_convivientes_seguimiento();
				control_convivientes_seguimientoAux
						.setCodigo_empresa(seguimiento_control_pqt
								.getCodigo_empresa());
				control_convivientes_seguimientoAux
						.setCodigo_sucursal(seguimiento_control_pqt
								.getCodigo_sucursal());
				control_convivientes_seguimientoAux
						.setCodigo_ficha(seguimiento_control_pqt
								.getCodigo_ficha());
				control_convivientes_seguimientoDao
						.eliminar(control_convivientes_seguimientoAux);

			}

			for (Control_convivientes_seguimiento control_convivientes_seguimiento : lista_detalle) {
				Control_convivientes_seguimiento control_convivientes_seguimientoAux = new Control_convivientes_seguimiento();
				control_convivientes_seguimientoAux
						.setCodigo_empresa(control_convivientes_seguimiento
								.getCodigo_empresa());
				control_convivientes_seguimientoAux
						.setCodigo_sucursal(control_convivientes_seguimiento
								.getCodigo_sucursal());
				control_convivientes_seguimientoAux
						.setCodigo_ficha(control_convivientes_seguimiento
								.getCodigo_ficha());
				control_convivientes_seguimientoAux
						.setCodigo_conviviente(control_convivientes_seguimiento
								.getCodigo_conviviente());

				control_convivientes_seguimiento
						.setCodigo_ficha(seguimiento_control_pqt
								.getCodigo_ficha());
				//log.info("control_convivientes_seguimiento: "
						//+ control_convivientes_seguimiento);
				if (control_convivientes_seguimientoDao
						.consultar(control_convivientes_seguimientoAux) != null) {
					throw new HealthmanagerException(
							"Control "
									+ control_convivientes_seguimiento
											.getCodigo_conviviente()
									+ "  ya se encuentra registrado");
				}
				control_convivientes_seguimientoDao
						.crear(control_convivientes_seguimiento);
			}

			seguimiento_control_pqt = consultar(seguimiento_control_pqt);
			if (admision != null
					&& admision.getVia_ingreso().equalsIgnoreCase(
							IVias_ingreso.CONTROL_LEPRA)) {
				admision.setCodigo_medico(seguimiento_control_pqt
						.getCreacion_user());
				admision.setAtendida(true);
				admisionDao.actualizar(admision);
			}

			return seguimiento_control_pqt;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearConsultaHistoria(Admision admision,
			Impresion_diagnostica impresion_diagnostica) throws Exception {
		Hisc_consulta_externa hisc_consulta_externa = new Hisc_consulta_externa();
		hisc_consulta_externa.setCodigo_empresa(admision.getCodigo_empresa());
		hisc_consulta_externa.setCodigo_sucursal(admision.getCodigo_sucursal());
		hisc_consulta_externa.setNro_ingreso(admision.getNro_ingreso());
		hisc_consulta_externa.setNro_identificacion(admision
				.getNro_identificacion());

		hisc_consulta_externa = hisc_consulta_externaService
				.consultar(hisc_consulta_externa);

		if (hisc_consulta_externa != null) {
			impresion_diagnostica.setCodigo_empresa(hisc_consulta_externa
					.getCodigo_empresa());
			impresion_diagnostica.setCodigo_sucursal(hisc_consulta_externa
					.getCodigo_sucursal());
			impresion_diagnostica.setCodigo_historia(hisc_consulta_externa
					.getCodigo_historia());

			impresion_diagnostica = impresion_diagnosticaService
					.consultar(impresion_diagnostica);

			if (impresion_diagnostica != null) {

			}

		}

		Seguimiento_control_pqt seguimiento_control_pqt = new Seguimiento_control_pqt();
		Map map = new HashMap();
		map.put("codigo_empresa", seguimiento_control_pqt.getCodigo_empresa());
		map.put("codigo_sucursal", seguimiento_control_pqt.getCodigo_sucursal());
		map.put("nro_identificacion",
				seguimiento_control_pqt.getNro_identificacion());
		map.put("nro_ingreso", seguimiento_control_pqt.getNro_ingreso());
		map.put("codigo_prestador", seguimiento_control_pqt.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", seguimiento_control_pqt.getCreacion_date());
		map.put("ultimo_update", seguimiento_control_pqt.getUltimo_update());
		map.put("creacion_user", seguimiento_control_pqt.getCreacion_user());
		map.put("ultimo_user", seguimiento_control_pqt.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);
	}

	public void crearConsultaTratamiento(Admision admision) throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(admision.getCodigo_empresa());
		impresion_diagnostica.setCodigo_sucursal(admision.getCodigo_sucursal());
		impresion_diagnostica.setFinalidad_consulta("01");
		impresion_diagnostica.setTipo_principal("1");

		Map map = new HashMap();
		map.put("codigo_empresa", admision.getCodigo_empresa());
		map.put("codigo_sucursal", admision.getCodigo_sucursal());
		map.put("nro_identificacion", admision.getNro_identificacion());
		map.put("nro_ingreso", admision.getNro_ingreso());
		map.put("codigo_prestador", admision.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", admision.getCreacion_date());
		map.put("ultimo_update", admision.getUltimo_update());
		map.put("creacion_user", admision.getCreacion_user());
		map.put("ultimo_user", admision.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		ServiciosDisponiblesUtils.generarConsulta(map);

		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
				.getManuales_tarifarios(admision);

		Map<String, Object> mapServicios = Utilidades.getProcedimiento(
				manuales_tarifarios, new Long(
						IConstantes.PROCEDIMIENTO_TRATAMIENTO_990201),
				ServiciosDisponiblesUtils.getServiceLocator());
		ServiciosDisponiblesUtils.generarDatosProcedimientos(admision,
				IConstantes.PROCEDIMIENTO_TRATAMIENTO_990201, mapServicios,
				manuales_tarifarios);

		Map<String, Object> mapServicios1 = Utilidades.getProcedimiento(
				manuales_tarifarios, new Long(
						IConstantes.PROCEDIMIENTO_TRATAMIENTO_992990),
				ServiciosDisponiblesUtils.getServiceLocator());
		ServiciosDisponiblesUtils.generarDatosProcedimientos(admision,
				IConstantes.PROCEDIMIENTO_TRATAMIENTO_992990, mapServicios1,
				manuales_tarifarios);

	}

	public int actualizar(Seguimiento_control_pqt seguimiento_control_pqt) {
		try {
			return seguimiento_control_pqtDao
					.actualizar(seguimiento_control_pqt);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Seguimiento_control_pqt consultar(
			Seguimiento_control_pqt seguimiento_control_pqt) {
		try {
			return seguimiento_control_pqtDao
					.consultar(seguimiento_control_pqt);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Seguimiento_control_pqt seguimiento_control_pqt) {
		try {
			return seguimiento_control_pqtDao.eliminar(seguimiento_control_pqt);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Seguimiento_control_pqt> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return seguimiento_control_pqtDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarCobroConsulta(String procedimiento) {
		try {
			Map<String, Object> mapServicios = Utilidades.getProcedimiento(
					manuales_tarifarios, new Long(procedimiento),
					ServiciosDisponiblesUtils.getServiceLocator());
			ServiciosDisponiblesUtils.generarDatosProcedimientos(admision,
					procedimiento, mapServicios, manuales_tarifarios);
		} catch (Exception e) {
			//  block
			e.printStackTrace();
		}

	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return seguimiento_control_pqtDao.existe(parameters);
	}

	public boolean existe_fecha_fin_tratamiento(Map<String, Object> parameters) {
		return seguimiento_control_pqtDao
				.existe_fecha_fin_tratamiento(parameters);
	}

}