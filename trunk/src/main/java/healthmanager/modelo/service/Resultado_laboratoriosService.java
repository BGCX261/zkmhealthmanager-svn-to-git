/*
 * Resultado_laboratoriosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resultado_laboratorios;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Datos_procedimientoDao;
import healthmanager.modelo.dao.Detalle_ordenDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.Resultado_laboratoriosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.IVias_ingreso;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.dao.Detalle_facturaDao;
import contaweb.modelo.dao.FacturacionDao;
import contaweb.modelo.service.FacturacionService;

@Service("resultado_laboratoriosService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Resultado_laboratoriosService implements Serializable {

	public static Logger log = Logger
			.getLogger(Resultado_laboratoriosService.class);

	@Autowired
	private Resultado_laboratoriosDao resultado_laboratoriosDao;
	@Autowired
	private Detalle_ordenDao detalle_ordenDao;
	@Autowired
	private FacturacionDao facturacionDao;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private Datos_procedimientoDao datos_procedimientoDao;
	@Autowired
	private Detalle_facturaDao detalle_facturaDao;
	@Autowired
	private FacturacionService facturacionService;

	public void crear(Resultado_laboratorios resultado_laboratorios) {
		try {
			if (consultar(resultado_laboratorios) != null) {
				throw new HealthmanagerException(
						"Resultado_laboratorios ya se encuentra en la base de datos");
			}
			resultado_laboratoriosDao.crear(resultado_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crearImportado(Resultado_laboratorios resultado_laboratorios) {
		log.info("ejecutando metodo @crearImportado()");
		try {
			resultado_laboratoriosDao.crear(resultado_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Resultado_laboratorios resultado_laboratorios) {
		try {
			return resultado_laboratoriosDao.actualizar(resultado_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Resultado_laboratorios consultar(
			Resultado_laboratorios resultado_laboratorios) {
		try {
			return resultado_laboratoriosDao.consultar(resultado_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean consultarExiste(Resultado_laboratorios resultado_laboratorios) {
		try {
			return resultado_laboratoriosDao
					.consultarExiste(resultado_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Resultado_laboratorios resultado_laboratorios) {
		try {
			return resultado_laboratoriosDao.eliminar(resultado_laboratorios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Resultado_laboratorios> listar(Map<String, Object> parameters) {
		try {
			return resultado_laboratoriosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return resultado_laboratoriosDao.existe(parameters);
	}

	public void guardarDatos(Map<String, Object> map) {
		try {
			Orden_servicio orden_servicio = (Orden_servicio) map
					.get("orden_servicio");
			Detalle_orden detalle_orden = (Detalle_orden) map.get("detalle");
			Resultado_laboratorios resultado = (Resultado_laboratorios) map
					.get("resultado");
			Usuarios usuarios = (Usuarios) map.get("user");
			detalle_ordenDao.actualizar(detalle_orden);
			if (resultado_laboratoriosDao.consultar(resultado) != null) {
				resultado_laboratoriosDao.actualizar(resultado);
			} else {
				resultado_laboratoriosDao.crear(resultado);
			}
			guardarFacturacionLaboratorio(orden_servicio, detalle_orden,
					usuarios);
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite facturar los procedimientos, que se realizaron
	 * 
	 * @author Luis Miguel
	 * @param usuarios
	 * */
	private void guardarFacturacionLaboratorio(Orden_servicio orden_servicio,
			Detalle_orden detalle_orden, Usuarios usuarios) {

		Admision admision = new Admision();
		admision.setCodigo_empresa(orden_servicio.getCodigo_empresa());
		admision.setCodigo_sucursal(orden_servicio.getCodigo_sucursal());
		admision.setNro_ingreso(orden_servicio.getNro_ingreso());
		admision.setNro_identificacion(orden_servicio.getCodigo_paciente());
		admision = admisionDao.consultar(admision);

		if (admision == null) {
			throw new ValidacionRunTimeException(
					"La admision del paciente no existe");
		}

		Facturacion facturacion = new Facturacion();
		facturacion.setCodigo_empresa(admision.getCodigo_empresa());
		facturacion.setCodigo_sucursal(admision.getCodigo_sucursal());
		facturacion.setNro_ingreso(admision.getNro_ingreso());
		facturacion.setCodigo_tercero(admision.getNro_identificacion());
		facturacion = facturacionDao.consultar_informacion(facturacion);

		if (facturacion != null) {
			/* cargamos procedimiento ha la facturacion */
			guardarFactura(detalle_orden, admision, usuarios, facturacion);
		} else {
			/* creamos la factura */
			guardarDatosProcedimientos(detalle_orden, admision, usuarios);
		}
	}

	private void guardarDatosProcedimientos(Detalle_orden detalle_orden,
			Admision admision, Usuarios usuarios) {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(admision.getCodigo_empresa());
		paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
		paciente.setNro_identificacion(admision.getNro_identificacion());
		paciente = pacienteDao.consultar(paciente);

		/*
		 * Ambito de realizacion del procedimiento 1 = ambulatorio 2 =
		 * hospitalario 3 = En urgencias
		 */

		if (paciente == null)
			throw new ValidacionRunTimeException(
					"El paciente admisionado no existe en la base de datos.");

		/* cargamos procedimiento a factuar */
		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento
				.setCodigo_empresa(detalle_orden.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(detalle_orden
				.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);
		datos_procedimiento.setCodigo_cups(detalle_orden.getCodigo_cups());

		datos_procedimiento.setTipo_identificacion(paciente
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(paciente
				.getNro_identificacion());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(detalle_orden
				.getCodigo_procedimiento());
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento.setValor_procedimiento(detalle_orden
				.getValor_procedimiento());
		datos_procedimiento.setUnidades(detalle_orden.getUnidades());
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento.setValor_neto(detalle_orden
				.getValor_procedimiento()
				* detalle_orden.getUnidades().intValue());

		String ambito_realizacion = IDatosProcedimientos.AMBITO_REALIZACION;

		if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)) {
			ambito_realizacion = "3";
		} else if (admision.getVia_ingreso().equals(
				IVias_ingreso.HOSPITALIZACIONES)) {
			ambito_realizacion = "2";
		}

		String PERSONAL_ATIENDE_OTRO = "5";

		datos_procedimiento.setAmbito_procedimiento(ambito_realizacion);
		datos_procedimiento
				.setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
		datos_procedimiento.setPersonal_atiende(PERSONAL_ATIENDE_OTRO);
		datos_procedimiento
				.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
		datos_procedimiento.setCodigo_diagnostico_principal(admision
				.getDiagnostico_ingreso());
		datos_procedimiento.setCodigo_diagnostico_relacionado("");
		datos_procedimiento.setComplicacion("");
		datos_procedimiento.setCancelo_copago("N");
		datos_procedimiento.setCodigo_programa("");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(usuarios.getCreacion_user());
		datos_procedimiento.setUltimo_user(usuarios.getCreacion_user());
		datos_procedimiento.setValor_real(detalle_orden.getValor_real());
		datos_procedimiento.setDescuento(detalle_orden.getDescuento());
		datos_procedimiento.setIncremento(detalle_orden.getIncremento());
		datos_procedimiento.setRealizado_en("");
		datos_procedimientoDao.crear(datos_procedimiento);
	}

	private void guardarFactura(Detalle_orden detalle_orden, Admision admision,
			Usuarios usuarios, Facturacion facturacion) {
		if (facturacion.getRadicado().equals("N")) {
			Map paramDetalle_factura = new HashMap();
			paramDetalle_factura.put("codigo_empresa",
					facturacion.getCodigo_empresa());
			paramDetalle_factura.put("codigo_sucursal",
					facturacion.getCodigo_sucursal());
			paramDetalle_factura.put("codigo_comprobante",
					facturacion.getCodigo_comprobante());

		} else {
			throw new ValidacionRunTimeException("Factura ya radicada");
		}
	}

	public boolean resultadosFueraRango(Map<String, Object> map) {
		return resultado_laboratoriosDao.resultadosFueraRango(map);
	}

}