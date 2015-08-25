package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.LimiteSexoEdadException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Hospitalizacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Recien_nacido;
import healthmanager.modelo.bean.Remision_administradora;
import healthmanager.modelo.bean.Urgencias;
import healthmanager.modelo.dao.AdministradoraDao;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.ContratosDao;
import healthmanager.modelo.dao.Datos_consultaDao;
import healthmanager.modelo.dao.Datos_medicamentosDao;
import healthmanager.modelo.dao.Datos_procedimientoDao;
import healthmanager.modelo.dao.Datos_servicioDao;
import healthmanager.modelo.dao.Facturacion_medicamentoDao;
import healthmanager.modelo.dao.Facturacion_servicioDao;
import healthmanager.modelo.dao.HospitalizacionDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.ProcedimientosDao;
import healthmanager.modelo.dao.Recien_nacidoDao;
import healthmanager.modelo.dao.Remision_administradoraDao;
import healthmanager.modelo.dao.UrgenciasDao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.Util;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Unidad_medida;
import contaweb.modelo.dao.ArticuloDao;
import contaweb.modelo.dao.Detalle_facturaDao;
import contaweb.modelo.dao.FacturacionDao;
import contaweb.modelo.dao.Unidad_medidaDao;

@Service("generacion_ripsService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Generacion_ripsService implements Serializable {

	private static Logger log = Logger.getLogger(Generacion_ripsService.class);

	private StringBuilder sbErrores;

	@Autowired
	private Remision_administradoraDao remision_administradoraDao;
	@Autowired
	private FacturacionDao facturacionDao;
	@Autowired
	private Detalle_facturaDao detalle_facturaDao;
	@Autowired
	private AdministradoraDao administradoraDao;
	@Autowired
	private ContratosDao contratosDao;
	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private UrgenciasDao urgenciasDao;
	@Autowired
	private GeneralExtraService generalExtraService;
	@Autowired
	private HospitalizacionDao hospitalizacionDao;
	@Autowired
	private Recien_nacidoDao recien_nacidoDao;
	@Autowired
	private Datos_consultaDao datos_consultaDao;
	@Autowired
	private ProcedimientosDao procedimientoDao;
	@Autowired
	private Datos_procedimientoDao datos_procedimientoDao;
	@Autowired
	private Datos_medicamentosDao datos_medicamentosDao;
	@Autowired
	private Facturacion_medicamentoDao facturacion_medicamentoDao;
	@Autowired
	private ArticuloDao articuloDao;
	@Autowired
	private Unidad_medidaDao unidad_medidaDao;
	@Autowired
	private Datos_servicioDao datos_servicioDao;
	@Autowired
	private Facturacion_servicioDao facturacion_servicioDao;

	private String rips_generacion;
	private Double porcentaje_progreso;

	public File guardarRipsPorFacturasAdministradora(Map<String, Object> datos) {
		try {
			log.info("Iniciando proceso de la generacion de rips...");
			sbErrores = new StringBuilder();
			String codigo_usuario = (String) datos.get("codigo_usuario");
			Boolean prueba = (Boolean) datos.get("prueba");
			String codigo_administradora = (String) datos
					.get("codigo_administradora");
			String id_plan = (String) datos.get("id_plan");

			String codigo_empresa = (String) datos.get("codigo_empresa");
			String codigo_sucursal = (String) datos.get("codigo_sucursal");
			int longitud_mascara = (Integer) datos.get("longitud_mascara");

			String nro_remision = "000001";

			List<Facturacion> listaAuxAF = new ArrayList<Facturacion>();
			List<Map> listaAF = new ArrayList<Map>();
			List<Map> listaUS = new ArrayList<Map>();
			List<Map> listaAC = new ArrayList<Map>();
			List<Map> listaAP = new ArrayList<Map>();
			List<Map> listaAU = new ArrayList<Map>();
			List<Map> listaAH = new ArrayList<Map>();
			List<Map> listaAN = new ArrayList<Map>();
			List<Map> listaAM = new ArrayList<Map>();
			List<Map> listaAT = new ArrayList<Map>();
			List<Map> listaCT = new ArrayList<Map>();

			if (!prueba) {
				Remision_administradora ra = new Remision_administradora();
				ra.setCodigo_empresa(codigo_empresa);
				ra.setCodigo_sucursal(codigo_sucursal);
				ra.setCodigo_administradora(codigo_administradora);
				ra = (Remision_administradora) remision_administradoraDao
						.consultar(ra);
				if (ra != null) {
					nro_remision = Utilidades.getZeroFill(ra.getNro_remision(),
							6);
					ra.setNro_remision((Integer.parseInt(ra.getNro_remision()) + 1)
							+ "");
					remision_administradoraDao.actualizar(ra);
				} else {
					ra = new Remision_administradora();
					ra.setCodigo_empresa(codigo_empresa);
					ra.setCodigo_sucursal(codigo_sucursal);
					ra.setCodigo_administradora(codigo_administradora);
					ra.setCreacion_date(new Timestamp((new Date()).getTime()));
					ra.setUltimo_update(new Timestamp((new Date()).getTime()));
					ra.setCreacion_user(codigo_usuario);
					ra.setUltimo_user(codigo_usuario);
					ra.setNro_remision("2");
					remision_administradoraDao.crear(ra);
				}
			}
			rips_generacion = "INICIANDO";
			porcentaje_progreso = 0.0;
			generarListaAF(datos, listaAF, listaAuxAF, longitud_mascara);
			generarListaUS(listaAuxAF, listaUS, datos);
			generarListaAU(datos, listaAuxAF, listaAU, longitud_mascara);
			generarListaAH(datos, listaAuxAF, listaAH, longitud_mascara);
			generarListaAN(datos, listaAuxAF, listaAN, longitud_mascara);

			for (int i = 0; i < listaAuxAF.size(); i++) {
				rips_generacion = "RIPS AC-AP-AM-AT";
				Facturacion fac = listaAuxAF.get(i);

				GregorianCalendar gc = new GregorianCalendar();
				gc.setTimeInMillis(fac.getFecha().getTime());

				GregorianCalendar gc2 = new GregorianCalendar();
				gc2.setTimeInMillis(fac.getFecha_inicio().getTime());

				GregorianCalendar gc3 = new GregorianCalendar();
				gc3.setTimeInMillis(fac.getFecha_final().getTime());

				if (gc2.get(Calendar.YEAR) != gc.get(Calendar.YEAR)) {
					gc2.set(Calendar.YEAR, gc.get(Calendar.YEAR));
					gc2.set(Calendar.MONTH, gc.get(Calendar.MONTH));
					gc2.set(Calendar.DAY_OF_MONTH, 1);
					fac.setFecha_inicio(new Timestamp(gc2.getTimeInMillis()));
				}

				if (gc3.get(Calendar.YEAR) != gc.get(Calendar.YEAR)) {
					gc3.set(Calendar.YEAR, gc.get(Calendar.YEAR));
					gc3.set(Calendar.MONTH, gc.get(Calendar.MONTH));
					gc3.set(Calendar.DAY_OF_MONTH,
							gc.getActualMaximum(Calendar.DAY_OF_MONTH));
					fac.setFecha_final(new Timestamp(gc3.getTimeInMillis()));
				}

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("id_factura", fac.getId_factura());
				parametros.put("facturable", true);

				double valor_calculado_factura = Utilidades
						.obtenerValorPrimitivo(detalle_facturaDao
								.totalFacturaClinica(parametros));
				double valor_copago = 0;
				if (fac.getNocopago().equals("N")) {
					valor_copago = fac.getValor_copago();
				}

				double valor_neto = (valor_calculado_factura - valor_copago);

				long valor_factura = Long.parseLong(Utilidades.formatDecimal(
						valor_neto, "#0"));

				List<Map> listaAuxAC = new ArrayList<Map>();
				long resultAC = generarListaAC(datos, fac, listaAuxAC,
						longitud_mascara);

				List<Map> listaAuxAP = new ArrayList<Map>();
				List<Map> listaAuxAT1 = new ArrayList<Map>();
				long resultAP = generarListaAP(datos, fac, listaAuxAP,
						listaAuxAT1, longitud_mascara);

				List<Map> listaAuxAM = new ArrayList<Map>();
				List<Map> listaAuxAT2 = new ArrayList<Map>();
				long resultAM_AT = generarListaAM_AT(datos, fac, listaAuxAM,
						listaAuxAT2, longitud_mascara);

				// List<Map> listaAuxAT2 = new ArrayList<Map>();
				// long resultAT1 = generarListaAM_AT(datos, fac, listaAuxAT2,
				// "02", longitud_mascara);
				List<Map> listaAuxAT3 = new ArrayList<Map>();
				long resultAT = generarListaAT(datos, fac, listaAuxAT3,
						longitud_mascara);

				long total_servicios = resultAC + resultAP + resultAM_AT
						+ resultAT;

				if ((valor_factura != total_servicios)) {
					long diferencia = (valor_factura - total_servicios);
					boolean sw = (valor_factura - total_servicios < 0 ? false
							: true);
					acomodarValores(listaAuxAC, listaAuxAP, listaAuxAT1,
							listaAuxAM, listaAuxAT2, listaAuxAT3, sw,
							diferencia);
				}

				// Adicionamos a las lista principales //
				adicionarListaPrincipal(listaAuxAC, listaAuxAP, listaAuxAT1,
						listaAuxAM, listaAuxAT2, listaAuxAT3, listaAC, listaAP,
						listaAM, listaAT);
				porcentaje_progreso = ((new Double(i + 1)) / new Double(
						listaAuxAF.size())) * new Double(100);
			}

			generarListaCT(datos, listaAF, listaAU, listaAH, listaAN, listaUS,
					listaAC, listaAP, listaAM, listaAT, listaCT, nro_remision,
					codigo_administradora);
			rips_generacion = "FINALIZANDO";
			porcentaje_progreso = 50.0;
			// Generamos el rips //

			return generarRips(datos, listaAF, listaAU, listaAH, listaAN,
					listaUS, listaAC, listaAP, listaAM, listaAT, listaCT,
					nro_remision, codigo_administradora, id_plan);

		} catch (ValidacionRunTimeException e) {
			e.printStackTrace();
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	// listado_facturas
	// Generamos el rips AF en un Mapa //
	private void generarListaAF(Map<String, Object> datos, List<Map> listaAF,
			List<Facturacion> listaAuxAF, int longitud_mascara)
			throws Exception {
		rips_generacion = "RIPS AF";
		Empresa empresa = (Empresa) datos.get("empresa");
		String codigo_empresa = (String) datos.get("codigo_empresa");
		String codigo_sucursal = (String) datos.get("codigo_sucursal");
		String tipo = (String) datos.get("tipo");
		List<Facturacion> listado_facturas = (List<Facturacion>) datos
				.get("listado_facturas");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");
		Contratos contratos = (Contratos) datos.get("contratos");

		String filtro = "";

		for (int i = 0; i < listado_facturas.size(); i++) {
			Facturacion fac = listado_facturas.get(i);

			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(fac.getFecha().getTime());

			GregorianCalendar gc2 = new GregorianCalendar();
			gc2.setTimeInMillis(fac.getFecha_inicio().getTime());

			GregorianCalendar gc3 = new GregorianCalendar();
			gc3.setTimeInMillis(fac.getFecha_final().getTime());

			if (gc2.get(Calendar.YEAR) != gc.get(Calendar.YEAR)) {
				gc2.set(Calendar.YEAR, gc.get(Calendar.YEAR));
				gc2.set(Calendar.MONTH, gc.get(Calendar.MONTH));
				gc2.set(Calendar.DAY_OF_MONTH, 1);
				fac.setFecha_inicio(new Timestamp(gc2.getTimeInMillis()));
			}

			if (gc3.get(Calendar.YEAR) != gc.get(Calendar.YEAR)) {
				gc3.set(Calendar.YEAR, gc.get(Calendar.YEAR));
				gc3.set(Calendar.MONTH, gc.get(Calendar.MONTH));
				gc3.set(Calendar.DAY_OF_MONTH,
						gc.getActualMaximum(Calendar.DAY_OF_MONTH));
				fac.setFecha_final(new Timestamp(gc3.getTimeInMillis()));
			}

			double valor_calculado_factura = Utilidades
					.obtenerValorPrimitivo(fac.getTotal_factura_clinica());
			double valor_copago = (fac.getTipo().equals("IND") ? Integer
					.parseInt(Utilidades.formatDecimal((fac.getValor_copago()),
							"#0")) : Long.parseLong(Utilidades.formatDecimal(0,
					"#0")));
			if (fac.getNocopago().equals("S")) {
				valor_copago = 0;
			}
			double valor_neto = (fac.getTipo().equals("IND") ? Integer
					.parseInt(Utilidades.formatDecimal(
							(valor_calculado_factura - valor_copago), "#0"))
					: Long.parseLong(Utilidades.formatDecimal(
							fac.getValor_total(), "#0")));

			Administradora administradora = fac.getAdministradora();

			// Esta validacion es para las facturas capitadas
			String formato_fecha = Utilidades.formato_fecha(administradora);
			String nro_contrato = (contratos != null ? contratos
					.getNro_contrato() : "");
			if (fac.getTipo().equals(IConstantes.TIPO_FACTURA_CAP)) {
				if (fac.getDescripcion_nro_contrato() != null
						&& !fac.getDescripcion_nro_contrato().trim().isEmpty()) {
					nro_contrato = fac.getDescripcion_nro_contrato();
				}
			}

			Map<String, Object> mapAF = new TreeMap<String, Object>();

			mapAF.put("01-codigo_prestador", codigo_habilitacion);
			mapAF.put("02-nombre_prestador", empresa.getNombre_empresa());
			mapAF.put("03-tipo_id", empresa.getTipo_identificacion());
			mapAF.put("04-nro_documento",
					Utilidades.formatearNit(empresa.getNro_identificacion()));
			mapAF.put("05-nro_factura", Utilidades.getNumeroFactura(
					fac.getCodigo_documento_res(), longitud_mascara));
			mapAF.put("06-fecha", (Utilidades.formatDate(
					(tipo != null ? fac.getFecha_final() : fac.getFecha()),
					formato_fecha)));
			mapAF.put("07-fecha_inicial", (Utilidades.formatDate(
					fac.getFecha_inicio(), formato_fecha)));
			mapAF.put(
					"08-fecha_final",
					(Utilidades.formatDate(fac.getFecha_final(), formato_fecha)));
			mapAF.put("09-codigo_administradora",
					fac.getCodigo_administradora());
			mapAF.put("10-nombre_administradora",
					(administradora != null ? administradora.getNombre() : ""));
			mapAF.put("11-nro_contrato", nro_contrato);
			mapAF.put("12-plan_beneficio", "");
			mapAF.put("13-nro_poliza", fac.getNro_poliza());
			mapAF.put("14-valor_copago", Long.parseLong(Utilidades
					.formatDecimal(valor_copago, "#0")));
			mapAF.put(
					"15-valor_cuota",
					Long.parseLong(Utilidades.formatDecimal(
							fac.getValor_cuota(), "#0")));
			mapAF.put(
					"16-dto_factura",
					Long.parseLong(Utilidades.formatDecimal(
							fac.getDto_factura(), "#0")));
			mapAF.put("17-valor_neto",
					Long.parseLong(Utilidades.formatDecimal(valor_neto, "#0")));

			filtro += "'" + fac.getCodigo_documento_res() + "'";
			if ((i < (listado_facturas.size() - 1))
					&& (listado_facturas.size() > 1)) {
				filtro += ",";
			}

			listaAF.add(mapAF);
			if (tipo == null) {
				listaAuxAF.add(fac);
			}
			porcentaje_progreso = ((new Double(i + 1)) / new Double(
					listaAuxAF.size()))
					* new Double(100);
		}

		if (tipo != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("filtro_factura", filtro);

			List<Facturacion> list_aux = facturacionDao
					.listarFacturacion_rips(parametros);
			if (!list_aux.isEmpty()) {
				listado_facturas = new ArrayList<Facturacion>();
				for (int i = 0; i < list_aux.size(); i++) {
					Facturacion fac_aux = (Facturacion) list_aux.get(i);
					listaAuxAF.add(fac_aux);
				}
			}
		}

	}

	// Generamos el rips US en un Mapa //
	private void generarListaUS(List<Facturacion> listaAuxAF,
			List<Map> listaUS, Map<String, Object> datos) throws Exception {
		rips_generacion = "RIPS DE USUARIOS";
		Boolean continuar_error = (Boolean) datos.get("continuar_error");
		for (int i = 0; i < listaAuxAF.size(); i++) {
			Facturacion fac = listaAuxAF.get(i);
			if (fac.getAdmision() != null) {
				Paciente pct = fac.getAdmision().getPaciente();

				Date fecha_atencion = (fac.getFecha() != null && fac.getFecha()
						.compareTo(Calendar.getInstance().getTime()) <= 0) ? fac
						.getFecha() : Calendar.getInstance().getTime();

				String tipo_identificacion = validarTipoIdentificacion(pct,
						fecha_atencion);
				String documento = validarDocumento(pct.getDocumento());

				if (!buscarUsuario(listaUS, documento, tipo_identificacion)) {
					Map mapUS = new TreeMap();

					mapUS.put("01-tipo_id", tipo_identificacion);
					mapUS.put("02-nro_id", documento);
					mapUS.put("03-codigo_administradora",
							fac.getCodigo_administradora());
					mapUS.put("04-tipo_usuario", pct.getTipo_usuario());
					mapUS.put("05-apellido1",
							Utilidades.formatText(pct.getApellido1()));
					mapUS.put("06-apellido2",
							Utilidades.formatText(pct.getApellido2()));
					mapUS.put("07-nombre1",
							Utilidades.formatText(pct.getNombre1()));
					mapUS.put("08-nombre2",
							Utilidades.formatText(pct.getNombre2()));

					// verificamos fechas
					Date fecha_actual = Calendar.getInstance().getTime();
					boolean fecha_mayor = false;
					if (pct.getFecha_nacimiento().compareTo(fecha_actual) > 0) {
						if (continuar_error) {
							fecha_mayor = true;
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							sbErrores.append(
									"La fecha de nacimiento del paciente "
											+ tipo_identificacion
											+ " "
											+ documento
											+ " es mayor que la fecha actual "
											+ dateFormat.format(pct
													.getFecha_nacimiento())
											+ " > "
											+ dateFormat.format(fecha_actual))
									.append("\n");
						} else {
							throw new ValidacionRunTimeException("");
						}
					}

					// Corregimos con respecto a la edad
					Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(pct
							.getFecha_nacimiento(), Calendar.getInstance()
							.getTime());

					int anios = (Integer) mapa_edades.get("anios");
					int meses = (Integer) mapa_edades.get("meses");
					int dias = (Integer) mapa_edades.get("dias");

					String unidad_medida = "3";// dia
					int edad = dias;
					if (fecha_mayor) {
						edad = 1;
					} else if (dias > 29) {
						if (meses > 11) {
							unidad_medida = "1";// anio
							edad = anios;
						} else {
							unidad_medida = "2";// mes
							edad = meses;
						}
					} else if (dias <= 0) {
						edad = 1;
					}

					// fin de validacion de edad de paciente
					mapUS.put("09-edad", edad);
					mapUS.put("10-unidad_medida", unidad_medida);
					mapUS.put("11-sexo", pct.getSexo());
					mapUS.put("12-dpto", pct.getCodigo_dpto());
					mapUS.put("13-mun", pct.getCodigo_municipio());
					mapUS.put("14-zona", pct.getZona() + "");

					listaUS.add(mapUS);
					porcentaje_progreso = ((new Double(i + 1)) / new Double(
							listaAuxAF.size())) * new Double(100);
				}
			}
		}

	}

	private String validarDocumento(String documento) {
		return documento.trim().replaceAll("[^0-9]", "");
	}

	/**
	 * Este metodo me permite validar el tipo de identificacion puesto que
	 * algunos van en cero.
	 *
	 */
	private String validarTipoIdentificacion(Paciente pct, Date fecha_atencion) {
		if (pct.getTipo_identificacion() == null
				|| pct.getTipo_identificacion().isEmpty()) {
			Map<String, Integer> anios = Util.getEdadYYYYMMDD(
					pct.getFecha_nacimiento(), fecha_atencion);
			int edad = anios.get("anios");
			if (edad >= 18) {
				return "CC";
			} else if (edad <= 5) {
				return "RC";
			} else {
				return "TI";
			}
		}
		return pct.getTipo_identificacion();
	}

	// Generamos el rips AU en un Mapa //
	private void generarListaAU(Map<String, Object> datos,
			List<Facturacion> listaAuxAF, List<Map> listaAU, int longitud)
			throws Exception {
		rips_generacion = "RIPS DE URGENCIAS";
		Boolean continuar_error = (Boolean) datos.get("continuar_error");
		// String tipo = (String) datos.get("tipo");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");

		for (int i = 0; i < listaAuxAF.size(); i++) {
			Facturacion fac = listaAuxAF.get(i);

			String nro_autorizacion = "";
			Admision admision = fac.getAdmision();
			if (admision != null) {
				nro_autorizacion = (admision != null ? admision
						.getNro_autorizacion() : "");

				Paciente pct = fac.getAdmision().getPaciente();

				// Urgencias //
				if (fac.getTiene_urgencia() && fac.getTiene_observacion()) {
					Urgencias urgencias = new Urgencias();
					urgencias.setCodigo_empresa(fac.getCodigo_empresa());
					urgencias.setCodigo_sucursal(fac.getCodigo_sucursal());
					urgencias.setNro_identificacion(fac.getCodigo_tercero());
					urgencias.setNro_ingreso(fac.getNro_ingreso());

					urgencias = urgenciasDao.consultar(urgencias);
					String codigo_dct = urgencias.getNro_factura();
					String codigo_factura = fac.getCodigo_documento_res();
					String sexo_pct = (pct != null ? pct.getSexo() : "");
					String codigo_pct = urgencias.getNro_identificacion();
					String fecha_nac = (pct != null ? new java.text.SimpleDateFormat(
							"dd/MM/yyyy").format(pct.getFecha_nacimiento())
							: "");

					Cie cie = new Cie();
					cie.setCodigo(urgencias.getCodigo_diagnostico_principal());
					cie = generalExtraService.consultar(cie);
					String codigo_dxpp = urgencias
							.getCodigo_diagnostico_principal();
					String sexo_dxpp = (cie != null ? cie.getSexo() : "");
					String limite_inferior_dxpp = (cie != null ? cie
							.getLimite_inferior() : "");
					String limite_superior_dxpp = (cie != null ? cie
							.getLimite_superior() : "");
					try {
						validarDx(limite_inferior_dxpp, limite_superior_dxpp,
								sexo_dxpp, sexo_pct, fecha_nac, codigo_dxpp,
								"Principal", codigo_pct, codigo_dct,
								codigo_factura, "urgencias",
								urgencias.getFecha_ingreso());
					} catch (LimiteSexoEdadException lsex) {
						if (continuar_error) {
							sbErrores.append(lsex.getMessage()).append("\n");
						} else {
							throw new ValidacionRunTimeException(
									lsex.getMessage(), lsex);
						}
					}

					cie = new Cie();
					cie.setCodigo(urgencias.getCodigo_diagnostico1());
					cie = generalExtraService.consultar(cie);
					String codigo_dx1 = urgencias.getCodigo_diagnostico1();
					String sexo_dx1 = (cie != null ? cie.getSexo() : "");
					String limite_inferior_dx1 = (cie != null ? cie
							.getLimite_inferior() : "");
					String limite_superior_dx1 = (cie != null ? cie
							.getLimite_superior() : "");
					try {
						validarDx(limite_inferior_dx1, limite_superior_dx1,
								sexo_dx1, sexo_pct, fecha_nac, codigo_dx1,
								"R. Nro 1", codigo_pct, codigo_dct,
								codigo_factura, "urgencias",
								urgencias.getFecha_ingreso());
					} catch (LimiteSexoEdadException lsex) {
						if (continuar_error) {
							sbErrores.append(lsex.getMessage()).append("\n");
						} else {
							throw new ValidacionRunTimeException(
									lsex.getMessage(), lsex);
						}
					}

					cie = new Cie();
					cie.setCodigo(urgencias.getCodigo_diagnostico2());
					cie = generalExtraService.consultar(cie);
					String codigo_dx2 = urgencias.getCodigo_diagnostico2();
					String sexo_dx2 = (cie != null ? cie.getSexo() : "");
					String limite_inferior_dx2 = (cie != null ? cie
							.getLimite_inferior() : "");
					String limite_superior_dx2 = (cie != null ? cie
							.getLimite_superior() : "");
					try {
						validarDx(limite_inferior_dx2, limite_superior_dx2,
								sexo_dx2, sexo_pct, fecha_nac, codigo_dx2,
								"R. Nro 2", codigo_pct, codigo_dct,
								codigo_factura, "urgencias",
								urgencias.getFecha_ingreso());
					} catch (LimiteSexoEdadException lsex) {
						if (continuar_error) {
							sbErrores.append(lsex.getMessage()).append("\n");
						} else {
							throw new ValidacionRunTimeException(
									lsex.getMessage(), lsex);
						}
					}

					cie = new Cie();
					cie.setCodigo(urgencias.getCodigo_diagnostico3());
					cie = generalExtraService.consultar(cie);
					String codigo_dx3 = urgencias.getCodigo_diagnostico3();
					String sexo_dx3 = (cie != null ? cie.getSexo() : "");
					String limite_inferior_dx3 = (cie != null ? cie
							.getLimite_inferior() : "");
					String limite_superior_dx3 = (cie != null ? cie
							.getLimite_superior() : "");
					try {
						validarDx(limite_inferior_dx3, limite_superior_dx3,
								sexo_dx3, sexo_pct, fecha_nac, codigo_dx3,
								"R. Nro 3", codigo_pct, codigo_dct,
								codigo_factura, "urgencias",
								urgencias.getFecha_ingreso());
					} catch (LimiteSexoEdadException lsex) {
						if (continuar_error) {
							sbErrores.append(lsex.getMessage()).append("\n");
						} else {
							throw new ValidacionRunTimeException(
									lsex.getMessage(), lsex);
						}
					}

					cie = new Cie();
					cie.setCodigo(urgencias.getCausa_muerte());
					cie = generalExtraService.consultar(cie);
					String codigo_dxm = urgencias.getCausa_muerte();
					String sexo_dxm = (cie != null ? cie.getSexo() : "");
					String limite_inferior_dxm = (cie != null ? cie
							.getLimite_inferior() : "");
					String limite_superior_dxm = (cie != null ? cie
							.getLimite_superior() : "");
					try {
						validarDx(limite_inferior_dxm, limite_superior_dxm,
								sexo_dxm, sexo_pct, fecha_nac, codigo_dxm,
								"Causa de Muerte", codigo_pct, codigo_dct,
								codigo_factura, "urgencias",
								urgencias.getFecha_ingreso());
					} catch (LimiteSexoEdadException lsex) {
						if (continuar_error) {
							sbErrores.append(lsex.getMessage()).append("\n");
						} else {
							throw new ValidacionRunTimeException(
									lsex.getMessage(), lsex);
						}
					}

					Administradora administradora = fac.getAdministradora();
					String formato_fecha = Utilidades
							.formato_fecha(administradora);
					Map mapAU = new TreeMap();
					mapAU.put(
							"01-nro_factura",
							Utilidades.getNumeroFactura(
									fac.getCodigo_documento_res(), longitud));
					mapAU.put("02-codigo_prestador", codigo_habilitacion);
					mapAU.put("03-tipo_id",
							(pct != null ? pct.getTipo_identificacion() : ""));
					mapAU.put("04-nro_id", pct.getDocumento());
					mapAU.put("05-fecha_ingreso", (Utilidades.formatDate(
							urgencias.getFecha_ingreso(), formato_fecha)));
					mapAU.put("06-hora_ingreso", (Utilidades.formatDate(
							urgencias.getFecha_ingreso(), "HH:mm")));
					mapAU.put(
							"07-nro_autorizacion",
							(urgencias.getNumero_autorizacion().trim()
									.equals("") ? nro_autorizacion : urgencias
									.getNumero_autorizacion()));
					String causa_externa = urgencias.getCausa_externa();
					if (causa_externa == null || causa_externa.trim().isEmpty()) {
						causa_externa = "13"; // Enfermedad general
					}
					mapAU.put("08-causa_externa", causa_externa);
					mapAU.put("09-dx_principal", validarDx(urgencias
							.getCodigo_diagnostico_principal()));
					mapAU.put("10-dx1",
							validarDx(urgencias.getCodigo_diagnostico1()));
					mapAU.put("11-dx2",
							validarDx(urgencias.getCodigo_diagnostico2()));
					mapAU.put("12-dx3",
							validarDx(urgencias.getCodigo_diagnostico3()));
					mapAU.put("13-destino_salida",
							urgencias.getDestino_salida());
					mapAU.put("14-estado_salida", urgencias.getEstado_salida());
					mapAU.put("15-causa_muerte", urgencias.getCausa_muerte());
					mapAU.put("16-fecha_egreso", (Utilidades.formatDate(
							urgencias.getFecha_egreso(), formato_fecha)));
					mapAU.put("17-hora_egreso", (Utilidades.formatDate(
							urgencias.getFecha_egreso(), "HH:mm")));
					listaAU.add(mapAU);
				}
				porcentaje_progreso = ((new Double(i + 1)) / new Double(
						listaAuxAF.size())) * new Double(100);
			}
		}
	}

	// Generamos el rips AH en un Mapa //
	private void generarListaAH(Map<String, Object> datos,
			List<Facturacion> listaAuxAF, List<Map> listaAH, int longitud)
			throws Exception {
		rips_generacion = "RIPS DE HOSPITALIZACION";
		Boolean continuar_error = (Boolean) datos.get("continuar_error");
		// String tipo = (String) datos.get("tipo");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");

		for (int i = 0; i < listaAuxAF.size(); i++) {
			Facturacion fac = listaAuxAF.get(i);

			String nro_autorizacion = "";
			Admision admision = fac.getAdmision();
			if (admision != null) {
				nro_autorizacion = (admision != null ? admision
						.getNro_autorizacion() : "");

				Paciente pct = fac.getAdmision().getPaciente();

				if (fac.getTiene_hospitalizacion()) {
					// Urgencias //
					try {
						Hospitalizacion hospitalizacion = new Hospitalizacion();
						hospitalizacion.setCodigo_empresa(fac
								.getCodigo_empresa());
						hospitalizacion.setCodigo_sucursal(fac
								.getCodigo_sucursal());
						hospitalizacion.setNro_identificacion(fac
								.getCodigo_tercero());
						hospitalizacion.setNro_ingreso(fac.getNro_ingreso());

						hospitalizacion = (Hospitalizacion) hospitalizacionDao
								.consultar(hospitalizacion);

						String codigo_dct = hospitalizacion.getNro_factura();
						String codigo_factura = fac.getCodigo_documento_res();
						String sexo_pct = (pct != null ? pct.getSexo() : "");
						String codigo_pct = hospitalizacion
								.getNro_identificacion();
						String fecha_nac = (pct != null ? new java.text.SimpleDateFormat(
								"dd/MM/yyyy").format(pct.getFecha_nacimiento())
								: "");

						Cie cie = new Cie();
						cie.setCodigo(hospitalizacion
								.getCodigo_diagnostico_principal());
						cie = generalExtraService.consultar(cie);
						String codigo_dxpp = hospitalizacion
								.getCodigo_diagnostico_principal();
						String sexo_dxpp = (cie != null ? cie.getSexo() : "");
						String limite_inferior_dxpp = (cie != null ? cie
								.getLimite_inferior() : "");
						String limite_superior_dxpp = (cie != null ? cie
								.getLimite_superior() : "");
						try {
							validarDx(limite_inferior_dxpp,
									limite_superior_dxpp, sexo_dxpp, sexo_pct,
									fecha_nac, codigo_dxpp, "Principal",
									codigo_pct, codigo_dct, codigo_factura,
									"hospitalizado",
									hospitalizacion.getFecha_ingreso());
						} catch (LimiteSexoEdadException lsex) {
							if (continuar_error) {
								sbErrores.append(lsex.getMessage())
										.append("\n");
							} else {
								throw new ValidacionRunTimeException(
										lsex.getMessage(), lsex);
							}
						}

						cie = new Cie();
						cie.setCodigo(hospitalizacion.getCodigo_diagnostico1());
						cie = generalExtraService.consultar(cie);
						String codigo_dx1 = hospitalizacion
								.getCodigo_diagnostico1();
						String sexo_dx1 = (cie != null ? cie.getSexo() : "");
						String limite_inferior_dx1 = (cie != null ? cie
								.getLimite_inferior() : "");
						String limite_superior_dx1 = (cie != null ? cie
								.getLimite_superior() : "");
						try {
							validarDx(limite_inferior_dx1, limite_superior_dx1,
									sexo_dx1, sexo_pct, fecha_nac, codigo_dx1,
									"R. Nro 1", codigo_pct, codigo_dct,
									codigo_factura, "hospitalizado",
									hospitalizacion.getFecha_ingreso());
						} catch (LimiteSexoEdadException lsex) {
							if (continuar_error) {
								sbErrores.append(lsex.getMessage())
										.append("\n");
							} else {
								throw new ValidacionRunTimeException(
										lsex.getMessage(), lsex);
							}
						}

						cie = new Cie();
						cie.setCodigo(hospitalizacion.getCodigo_diagnostico2());
						cie = generalExtraService.consultar(cie);
						String codigo_dx2 = hospitalizacion
								.getCodigo_diagnostico2();
						String sexo_dx2 = (cie != null ? cie.getSexo() : "");
						String limite_inferior_dx2 = (cie != null ? cie
								.getLimite_inferior() : "");
						String limite_superior_dx2 = (cie != null ? cie
								.getLimite_superior() : "");
						try {
							validarDx(limite_inferior_dx2, limite_superior_dx2,
									sexo_dx2, sexo_pct, fecha_nac, codigo_dx2,
									"R. Nro 2", codigo_pct, codigo_dct,
									codigo_factura, "hospitalizado",
									hospitalizacion.getFecha_ingreso());
						} catch (LimiteSexoEdadException lsex) {
							if (continuar_error) {
								sbErrores.append(lsex.getMessage())
										.append("\n");
							} else {
								throw new ValidacionRunTimeException(
										lsex.getMessage(), lsex);
							}
						}

						cie = new Cie();
						cie.setCodigo(hospitalizacion.getCodigo_diagnostico3());
						cie = generalExtraService.consultar(cie);
						String codigo_dx3 = hospitalizacion
								.getCodigo_diagnostico3();
						String sexo_dx3 = (cie != null ? cie.getSexo() : "");
						String limite_inferior_dx3 = (cie != null ? cie
								.getLimite_inferior() : "");
						String limite_superior_dx3 = (cie != null ? cie
								.getLimite_superior() : "");
						try {
							validarDx(limite_inferior_dx3, limite_superior_dx3,
									sexo_dx3, sexo_pct, fecha_nac, codigo_dx3,
									"R. Nro 3", codigo_pct, codigo_dct,
									codigo_factura, "hospitalizado",
									hospitalizacion.getFecha_ingreso());
						} catch (LimiteSexoEdadException lsex) {
							if (continuar_error) {
								sbErrores.append(lsex.getMessage())
										.append("\n");
							} else {
								throw new ValidacionRunTimeException(
										lsex.getMessage(), lsex);
							}
						}

						cie = new Cie();
						cie.setCodigo(hospitalizacion.getComplicacion());
						cie = generalExtraService.consultar(cie);
						String codigo_dxc = hospitalizacion.getComplicacion();
						String sexo_dxc = (cie != null ? cie.getSexo() : "");
						String limite_inferior_dxc = (cie != null ? cie
								.getLimite_inferior() : "");
						String limite_superior_dxc = (cie != null ? cie
								.getLimite_superior() : "");
						try {
							validarDx(limite_inferior_dxc, limite_superior_dxc,
									sexo_dxc, sexo_pct, fecha_nac, codigo_dxc,
									"Complicacion", codigo_pct, codigo_dct,
									codigo_factura, "hospitalizado",
									hospitalizacion.getFecha_ingreso());
						} catch (LimiteSexoEdadException lsex) {
							if (continuar_error) {
								sbErrores.append(lsex.getMessage())
										.append("\n");
							} else {
								throw new ValidacionRunTimeException(
										lsex.getMessage(), lsex);
							}
						}

						cie = new Cie();
						cie.setCodigo(hospitalizacion.getCausa_muerte());
						cie = generalExtraService.consultar(cie);
						String codigo_dxm = hospitalizacion.getCausa_muerte();
						String sexo_dxm = (cie != null ? cie.getSexo() : "");
						String limite_inferior_dxm = (cie != null ? cie
								.getLimite_inferior() : "");
						String limite_superior_dxm = (cie != null ? cie
								.getLimite_superior() : "");
						try {
							validarDx(limite_inferior_dxm, limite_superior_dxm,
									sexo_dxm, sexo_pct, fecha_nac, codigo_dxm,
									"Causa de Muerte", codigo_pct, codigo_dct,
									codigo_factura, "hospitalizado",
									hospitalizacion.getFecha_ingreso());
						} catch (LimiteSexoEdadException lsex) {
							if (continuar_error) {
								sbErrores.append(lsex.getMessage())
										.append("\n");
							} else {
								throw new ValidacionRunTimeException(
										lsex.getMessage(), lsex);
							}
						}

						Administradora administradora = fac.getAdministradora();

						String formato_fecha = Utilidades
								.formato_fecha(administradora);

						Map mapAH = new TreeMap();
						mapAH.put(
								"01-nro_factura",
								Utilidades.getNumeroFactura(
										fac.getCodigo_documento_res(), longitud));
						mapAH.put("02-codigo_prestador", codigo_habilitacion);
						mapAH.put("03-tipo_id",
								(pct != null ? pct.getTipo_identificacion()
										: ""));
						mapAH.put("04-nro_id", pct.getDocumento());
						mapAH.put("05-via_ingreso",
								hospitalizacion.getVia_ingreso());
						mapAH.put("06-fecha_ingreso", (Utilidades.formatDate(
								hospitalizacion.getFecha_ingreso(),
								formato_fecha)));
						mapAH.put("07-hora_ingreso", (Utilidades.formatDate(
								hospitalizacion.getFecha_ingreso(), "HH:mm")));
						mapAH.put(
								"08-nro_autorizacion",
								(hospitalizacion.getNumero_autorizacion()
										.trim().equals("") ? nro_autorizacion
										: hospitalizacion
												.getNumero_autorizacion()));
						String causa_externa = hospitalizacion
								.getCausa_externa();
						if (causa_externa == null
								|| causa_externa.trim().isEmpty()) {
							causa_externa = "13"; // Enfermedad general
						}
						mapAH.put("09-causa_externa", causa_externa);
						mapAH.put("10-dx_principal1", validarDx(hospitalizacion
								.getCodigo_diagnostico_principal()));
						mapAH.put("11-dx_principal2", validarDx(hospitalizacion
								.getCodigo_diagnostico_principal()));
						mapAH.put("12-dx1", validarDx(hospitalizacion
								.getCodigo_diagnostico1()));
						mapAH.put("13-dx2", validarDx(hospitalizacion
								.getCodigo_diagnostico2()));
						mapAH.put("14-dx3", validarDx(hospitalizacion
								.getCodigo_diagnostico3()));
						mapAH.put("15-complicacion",
								hospitalizacion.getComplicacion());
						mapAH.put("16-estado_salida",
								hospitalizacion.getEstado_salida());
						mapAH.put("17-causa_muerte",
								hospitalizacion.getCausa_muerte());
						mapAH.put("18-fecha_egreso", (Utilidades.formatDate(
								hospitalizacion.getFecha_egreso(),
								formato_fecha)));
						mapAH.put("19-hora_egreso", (Utilidades.formatDate(
								hospitalizacion.getFecha_egreso(), "HH:mm")));

						listaAH.add(mapAH);
					} catch (LimiteSexoEdadException lsex) {
						if (continuar_error) {
							sbErrores.append(lsex.getMessage()).append("\n");
						} else {
							throw new ValidacionRunTimeException(
									lsex.getMessage(), lsex);
						}
					}
				}
				porcentaje_progreso = ((new Double(i + 1)) / new Double(
						listaAuxAF.size())) * new Double(100);
			}
		}
	}

	// Generamos el rips AN en un Mapa //
	private void generarListaAN(Map<String, Object> datos,
			List<Facturacion> listaAuxAF, List<Map> listaAN,
			int longitud_mascara) throws Exception {
		rips_generacion = "RIPS DE NACIDOS";
		Boolean continuar_error = (Boolean) datos.get("continuar_error");
		// String tipo = (String) datos.get("tipo");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");

		for (int i = 0; i < listaAuxAF.size(); i++) {
			Facturacion fac = listaAuxAF.get(i);
			if (fac.getAdmision() != null) {
				Paciente pct = fac.getAdmision().getPaciente();

				// Recien_nacido //
				if (fac.getTiene_recien_nacido()) {
					Recien_nacido recien_nacido = new Recien_nacido();
					recien_nacido.setCodigo_empresa(fac.getCodigo_empresa());
					recien_nacido.setCodigo_sucursal(fac.getCodigo_sucursal());
					recien_nacido.setNro_ingreso(fac.getNro_ingreso());
					recien_nacido
							.setNro_identificacion(fac.getCodigo_tercero());
					recien_nacido = recien_nacidoDao.consultar(recien_nacido);

					String edad_ges = recien_nacido.getEdad_gestacional();
					if (edad_ges.equals("")) {
						edad_ges = "00";
					} else if (Integer.parseInt(edad_ges) < 10) {
						edad_ges = "0" + edad_ges;
					}

					String codigo_dct = recien_nacido.getNro_factura();
					String codigo_factura = fac.getCodigo_documento_res();
					String sexo_pct = (pct != null ? pct.getSexo() : "");
					String codigo_pct = recien_nacido.getNro_identificacion();
					String fecha_nac = (pct != null ? new java.text.SimpleDateFormat(
							"dd/MM/yyyy").format(pct.getFecha_nacimiento())
							: "");

					Cie cie = new Cie();
					cie.setCodigo(recien_nacido
							.getCodigo_diagnostico_principal());
					cie = generalExtraService.consultar(cie);
					String codigo_dxpp = recien_nacido
							.getCodigo_diagnostico_principal();
					String sexo_dxpp = (cie != null ? cie.getSexo() : "");
					String limite_inferior_dxpp = (cie != null ? cie
							.getLimite_inferior() : "");
					String limite_superior_dxpp = (cie != null ? cie
							.getLimite_superior() : "");
					try {
						validarDx(limite_inferior_dxpp, limite_superior_dxpp,
								sexo_dxpp, sexo_pct, fecha_nac, codigo_dxpp,
								"Principal", codigo_pct, codigo_dct,
								codigo_factura, "recien nacido",
								recien_nacido.getFecha_nacimiento());
					} catch (LimiteSexoEdadException lsex) {
						if (continuar_error) {
							sbErrores.append(lsex.getMessage()).append("\n");
						} else {
							throw new ValidacionRunTimeException(
									lsex.getMessage(), lsex);
						}
					}

					cie = new Cie();
					cie.setCodigo(recien_nacido.getCausa_muerte());
					cie = generalExtraService.consultar(cie);
					String codigo_dxm = recien_nacido.getCausa_muerte();
					String sexo_dxm = (cie != null ? cie.getSexo() : "");
					String limite_inferior_dxm = (cie != null ? cie
							.getLimite_inferior() : "");
					String limite_superior_dxm = (cie != null ? cie
							.getLimite_superior() : "");
					try {
						validarDx(limite_inferior_dxm, limite_superior_dxm,
								sexo_dxm, sexo_pct, fecha_nac, codigo_dxm,
								"Causa de Muerte", codigo_pct, codigo_dct,
								codigo_factura, "recien nacido",
								recien_nacido.getFecha_nacimiento());
					} catch (LimiteSexoEdadException lsex) {
						if (continuar_error) {
							sbErrores.append(lsex.getMessage()).append("\n");
						} else {
							throw new ValidacionRunTimeException(
									lsex.getMessage(), lsex);
						}
					}

					Administradora administradora = fac.getAdministradora();

					String formato_fecha = Utilidades
							.formato_fecha(administradora);

					Map<String, Object> mapAN = new TreeMap<String, Object>();
					mapAN.put("01-nro_factura", Utilidades.getNumeroFactura(
							fac.getCodigo_documento_res(), longitud_mascara));
					mapAN.put("02-codigo_prestador", codigo_habilitacion);
					mapAN.put("03-tipo_id",
							(pct != null ? pct.getTipo_identificacion() : ""));
					mapAN.put("04-nro_id", pct.getDocumento());
					mapAN.put("05-fecha_nacimiento",
							(Utilidades.formatDate(
									recien_nacido.getFecha_nacimiento(),
									formato_fecha)));
					mapAN.put("06-hora_nacimiento", (Utilidades.formatDate(
							recien_nacido.getFecha_nacimiento(), "HH:mm")));
					mapAN.put("07-edad_ges", edad_ges);
					mapAN.put("08-control_prenatal",
							recien_nacido.getControl_prenatal());
					mapAN.put("09-sexo", recien_nacido.getSexo());
					mapAN.put("10-peso", recien_nacido.getPeso());
					mapAN.put("11-dx_principal", validarDx(recien_nacido
							.getCodigo_diagnostico_principal()));
					mapAN.put("12-causa_muerte",
							recien_nacido.getCausa_muerte());
					mapAN.put(
							"13-fecha_muerte",
							recien_nacido.getFecha_muerte() != null ? (Utilidades
									.formatDate(
											recien_nacido.getFecha_muerte(),
											formato_fecha)) : "");
					mapAN.put(
							"14-hora_muerte",
							recien_nacido.getFecha_muerte() != null ? (Utilidades
									.formatDate(
											recien_nacido.getFecha_muerte(),
											"HH:mm")) : "");

					listaAN.add(mapAN);
				}
				porcentaje_progreso = ((new Double(i + 1)) / new Double(
						listaAuxAF.size())) * new Double(100);
			}
		}
	}

	private boolean buscarUsuario(List<Map> listaUS, String id_usuario,
			String tipo_identificacion) {
		boolean existe = false;
		for (int i = 0; i < listaUS.size(); i++) {
			Map map = listaUS.get(i);
			String nro_id = (String) map.get("02-nro_id");
			String tipo_id = (String) map.get("01-tipo_id");
			if (nro_id != null && nro_id.equals(id_usuario)
					&& tipo_id.equals(tipo_identificacion)) {
				existe = true;
				i = listaUS.size();
			}
		}
		return existe;
	}

	private void validarDx(String limite_inferior, String limite_superior,
			String sexo_dx, String sexo_pct, String fecha_nac,
			String codigo_dx, String msg, String codigo_paciente,
			String codigo_dct, String codigo_factura, String archivo,
			Timestamp fecha_atencion) throws LimiteSexoEdadException {
		if (codigo_dx != null && !codigo_dx.trim().equals("")) {
			// Validamos si la edad corresponde al dx con limite inferior//
			if (!limite_inferior.equals("0") && !limite_inferior.equals("000")
					&& !limite_inferior.equals("")) {
				String unidad_medida = limite_inferior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Utilidades.getEdad2(fecha_nac, unidad_medida,
						fecha_atencion);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) < Integer
							.parseInt(limite_inferior.substring(1))) {
						throw new LimiteSexoEdadException("codigo_factura="
								+ codigo_factura + ", El diagnostico " + msg
								+ " " + codigo_dx
								+ " no corresponde con la edad del paciente "
								+ codigo_paciente + "(" + edad
								+ ") del registro de " + archivo + " nro "
								+ codigo_dct);
					}
				}
			}

			// Validamos si la edad corresponde al dx con limite superior//
			if (!limite_superior.equals("599") && !limite_superior.equals("0")
					&& !limite_superior.equals("")) {
				String unidad_medida = limite_superior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Utilidades.getEdad2(fecha_nac, unidad_medida,
						fecha_atencion);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) > Integer
							.parseInt(limite_superior.substring(1))) {
						throw new LimiteSexoEdadException("codigo_factura="
								+ codigo_factura + ", El diagnostico " + msg
								+ " " + codigo_dx
								+ " no corresponde con la edad del paciente "
								+ codigo_paciente + "(" + edad
								+ ") del registro de " + archivo + " nro "
								+ codigo_dct);
					}
				}
			}

			// Validamos si el sexo corresponde al dx //
			if (!sexo_dx.equals("") && !sexo_dx.equals("A")) {
				if (sexo_dx.equals("H")) {
					sexo_dx = "M";
				} else if (sexo_dx.equals("M")) {
					sexo_dx = "F";
				}

				if (!sexo_pct.equals(sexo_dx)) {
					throw new LimiteSexoEdadException("codigo_factura="
							+ codigo_factura + ", El diagnostico " + msg + " "
							+ codigo_dx
							+ " no corresponde con el sexo del paciente "
							+ codigo_paciente + " del registro de " + archivo
							+ " nro " + codigo_dct);
				}
			}
		}
	}

	// Generamos el rips AC en un Mapa //
	private long generarListaAC(Map<String, Object> datos, Facturacion fac,
			List<Map> listaAC, int longitud) throws Exception {
		Boolean continuar_error = (Boolean) datos.get("continuar_error");
		// String tipo = (String) datos.get("tipo");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");

		Contratos contratos = (Contratos) datos.get("contratos");
		List<Detalles_paquetes_servicios> listado_paquetes_servicios = (List<Detalles_paquetes_servicios>) datos
				.get("listado_paquetes_servicios");

		String nro_autorizacion = "";
		Admision admision = fac.getAdmision();
		long result = 0;
		if (admision != null) {
			nro_autorizacion = (admision != null ? admision
					.getNro_autorizacion() : "");

			// cambiamos el calculo del valor de la factura
			double valor_calculado_factura = Utilidades
					.obtenerValorPrimitivo(fac.getTotal_factura_clinica());
			double valor_copago = !fac.getNocopago().equals("N") ? 0 : fac
					.getValor_copago();

			// Datos consulta //
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", fac.getCodigo_empresa());
			parametros.put("codigo_sucursal", fac.getCodigo_sucursal());
			parametros.put("codigo_administradora",
					fac.getCodigo_administradora());
			parametros.put("id_plan", fac.getId_plan());
			parametros.put("nro_identificacion", fac.getCodigo_tercero());
			parametros.put("nro_ingreso", fac.getNro_ingreso());

			List<Datos_consulta> lista_datos_cons = datos_consultaDao
					.listarResultadosRips(parametros);

			for (int idx = 0; idx < lista_datos_cons.size(); idx++) {
				Datos_consulta dc = (Datos_consulta) lista_datos_cons.get(idx);
				try {
					String codigo_registro = dc.getCodigo_registro() + "";
					String codigo_factura = fac.getCodigo_documento_res();
					String sexo_pct = (admision != null ? admision
							.getPaciente().getSexo() : "");
					String codigo_pct = dc.getNro_identificacion();
					String fecha_nac = (admision != null ? new java.text.SimpleDateFormat(
							"dd/MM/yyyy").format(admision.getPaciente()
							.getFecha_nacimiento()) : "");

					double valor_item = dc.getValor_neto();
					if (valor_copago > 0) {
						double dto_copago = (((valor_item * 100) / valor_calculado_factura) / 100)
								* valor_copago;
						valor_item = valor_item - dto_copago;
					}

					String[] pdc = getCodigo_pcd(dc.getProcedimientos(),
							dc.getCodigo_consulta());

					if (pdc[0] != null) {
						String codigo_procedimiento = pdc[6];
						boolean incluir_rips = true;
						if (admision.getAdmision_parto() != null
								&& admision.getAdmision_parto().equals("S")) {
							if (contratos.getIncluir_paquetes().equals("N")) {
								if (listado_paquetes_servicios.isEmpty()) {
									incluir_rips = true;
								} else {
									for (Detalles_paquetes_servicios detalles_paquetes_servicios : listado_paquetes_servicios) {
										if ((codigo_procedimiento
												.equals(detalles_paquetes_servicios
														.getPaquetes_servicios()
														.getId_procedimiento_principal()) || codigo_procedimiento
												.equals(detalles_paquetes_servicios
														.getCodigo_detalle())
												&& dc.getValor_consulta() == 0.0)) {
											incluir_rips = false;
											break;
										}
									}
								}
							} else {
								incluir_rips = true;
							}
						}
						if (incluir_rips) {
							try {
								validarPcd(pdc[4], pdc[5], pdc[3], sexo_pct,
										fecha_nac, dc.getCodigo_consulta(),
										"La consulta", codigo_pct,
										codigo_registro, codigo_factura,
										"consulta", dc.getFecha_consulta(),
										pdc[0]);
							} catch (LimiteSexoEdadException lsex) {
								if (continuar_error) {
									sbErrores.append(lsex.getMessage()).append(
											"\n");
								} else {
									throw new ValidacionRunTimeException(
											lsex.getMessage(), lsex);
								}
							}

							Cie cie = new Cie();
							cie.setCodigo(dc.getCodigo_diagnostico_principal());
							cie = generalExtraService.consultar(cie);
							String codigo_dxpp = dc
									.getCodigo_diagnostico_principal();
							String sexo_dxpp = (cie != null ? cie.getSexo()
									: "");
							String limite_inferior_dxpp = (cie != null ? cie
									.getLimite_inferior() : "");
							String limite_superior_dxpp = (cie != null ? cie
									.getLimite_superior() : "");
							try {
								validarDx(limite_inferior_dxpp,
										limite_superior_dxpp, sexo_dxpp,
										sexo_pct, fecha_nac, codigo_dxpp,
										"Principal", codigo_pct,
										codigo_registro, codigo_factura,
										"consulta", dc.getFecha_consulta());
							} catch (LimiteSexoEdadException lsex) {
								if (continuar_error) {
									sbErrores.append(lsex.getMessage()).append(
											"\n");
								} else {
									throw new ValidacionRunTimeException(
											lsex.getMessage(), lsex);
								}
							}

							cie = new Cie();
							cie.setCodigo(dc.getCodigo_diagnostico1());
							cie = generalExtraService.consultar(cie);
							String codigo_dx1 = dc.getCodigo_diagnostico1();
							String sexo_dx1 = (cie != null ? cie.getSexo() : "");
							String limite_inferior_dx1 = (cie != null ? cie
									.getLimite_inferior() : "");
							String limite_superior_dx1 = (cie != null ? cie
									.getLimite_superior() : "");
							try {
								validarDx(limite_inferior_dx1,
										limite_superior_dx1, sexo_dx1,
										sexo_pct, fecha_nac, codigo_dx1,
										"R. Nro 1", codigo_pct,
										codigo_registro, codigo_factura,
										"consulta", dc.getFecha_consulta());
							} catch (LimiteSexoEdadException lsex) {
								if (continuar_error) {
									sbErrores.append(lsex.getMessage()).append(
											"\n");
								} else {
									throw new ValidacionRunTimeException(
											lsex.getMessage(), lsex);
								}
							}

							cie = new Cie();
							cie.setCodigo(dc.getCodigo_diagnostico2());
							cie = generalExtraService.consultar(cie);
							String codigo_dx2 = dc.getCodigo_diagnostico2();
							String sexo_dx2 = (cie != null ? cie.getSexo() : "");
							String limite_inferior_dx2 = (cie != null ? cie
									.getLimite_inferior() : "");
							String limite_superior_dx2 = (cie != null ? cie
									.getLimite_superior() : "");
							try {
								validarDx(limite_inferior_dx2,
										limite_superior_dx2, sexo_dx2,
										sexo_pct, fecha_nac, codigo_dx2,
										"R. Nro 2", codigo_pct,
										codigo_registro, codigo_factura,
										"consulta", dc.getFecha_consulta());
							} catch (LimiteSexoEdadException lsex) {
								if (continuar_error) {
									sbErrores.append(lsex.getMessage()).append(
											"\n");
								} else {
									throw new ValidacionRunTimeException(
											lsex.getMessage(), lsex);
								}
							}

							cie = new Cie();
							cie.setCodigo(dc.getCodigo_diagnostico3());
							cie = generalExtraService.consultar(cie);
							String codigo_dx3 = dc.getCodigo_diagnostico3();
							String sexo_dx3 = (cie != null ? cie.getSexo() : "");
							String limite_inferior_dx3 = (cie != null ? cie
									.getLimite_inferior() : "");
							String limite_superior_dx3 = (cie != null ? cie
									.getLimite_superior() : "");
							try {
								validarDx(limite_inferior_dx3,
										limite_superior_dx3, sexo_dx3,
										sexo_pct, fecha_nac, codigo_dx3,
										"R. Nro 3", codigo_pct,
										codigo_registro, codigo_factura,
										"consulta", dc.getFecha_consulta());
							} catch (LimiteSexoEdadException lsex) {
								if (continuar_error) {
									sbErrores.append(lsex.getMessage()).append(
											"\n");
								} else {
									throw new ValidacionRunTimeException(
											lsex.getMessage(), lsex);
								}
							}

							if (dc.getFecha_consulta().compareTo(
									fac.getFecha_inicio()) < 0
									|| dc.getFecha_consulta().compareTo(
											fac.getFecha_final()) > 0) {
								dc.setFecha_consulta(fac.getFecha_final());
							}

							Administradora administradora = fac
									.getAdministradora();
							String formato_fecha = Utilidades
									.formato_fecha(administradora);
							Map<String, Object> mapAC = new TreeMap<String, Object>();
							mapAC.put("01-nro_factura", Utilidades
									.getNumeroFactura(
											fac.getCodigo_documento_res(),
											longitud));
							mapAC.put("02-codigo_prestador",
									codigo_habilitacion);
							mapAC.put("03-tipo_id",
									(admision != null ? admision.getPaciente()
											.getTipo_identificacion() : ""));
							mapAC.put("04-nro_id", (admision != null ? admision
									.getPaciente().getDocumento() : ""));
							mapAC.put("05-fecha", (Utilidades.formatDate(
									dc.getFecha_consulta(), formato_fecha)));
							mapAC.put(
									"06-nro_autorizacion",
									(dc.getNumero_autorizacion().trim()
											.equals("") ? nro_autorizacion : dc
											.getNumero_autorizacion()));
							mapAC.put("07-codigo_consulta",
									Utilidades.formatText(pdc[0]));
							mapAC.put("08-finalidad_consulta",
									validarFinalidadConsulta(dc
											.getFinalidad_consulta()));

							String causa_externa = dc.getCausa_externa();
							if (causa_externa == null
									|| causa_externa.trim().isEmpty()) {
								causa_externa = "13"; // Enfermedad general
							}

							mapAC.put("09-causa_externa", causa_externa);
							mapAC.put("10-dx_principal", validarDx(dc
									.getCodigo_diagnostico_principal()));
							mapAC.put("11-dx1",
									validarDx(dc.getCodigo_diagnostico1()));
							mapAC.put("12-dx2",
									validarDx(dc.getCodigo_diagnostico2()));
							mapAC.put("13-dx3",
									validarDx(dc.getCodigo_diagnostico3()));
							mapAC.put("14-tipo_diagnostico",
									dc.getTipo_diagnostico());
							mapAC.put("15-valor_consulta", Long
									.parseLong(Utilidades.formatDecimal(
											valor_item, "#0")));

							mapAC.put(
									"16-valor_cuota",
									Long.parseLong(Utilidades.formatDecimal(
											dc.getValor_cuota(), "#0")));
							mapAC.put("17-valor_neto", Long
									.parseLong(Utilidades.formatDecimal(
											valor_item, "#0")));

							result += (Long) mapAC.get("17-valor_neto");

							listaAC.add(mapAC);
						}
					}
				} catch (LimiteSexoEdadException lsex) {
					if (continuar_error) {
						sbErrores.append(lsex.getMessage()).append("\n");
					} else {
						throw new ValidacionRunTimeException(lsex.getMessage(),
								lsex);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Este metodo se realizo con el fin de intercambiar los valores de la
	 * finalidad ya que habia un error de codigo el cual lo intercambiaron, por
	 * lo que afecta en varios modulos
	 *
	 * Estan intercambiados Atencion del Parto(puerperio) No Aplica
	 *
	 * @author Luis miguel
	 *
	 */
	private String validarFinalidadConsulta(String finalidad) {
		if (finalidad == null) {
			finalidad = "01";
		}
		if (finalidad.equals("01")) {
			return "10";// No Aplica
		} else if (finalidad.equals("10")) {
			return "01";// Atencion del Parto(puerperio)
		}
		return finalidad;
	}

	// Generamos el rips AP en un Mapa //
	private Long generarListaAP(Map<String, Object> datos, Facturacion fac,
			List<Map> listaAP, List<Map> listaAT, int longitud)
			throws Exception {
		Boolean continuar_error = (Boolean) datos.get("continuar_error");
		// String tipo = (String) datos.get("tipo");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");

		Contratos contratos = (Contratos) datos.get("contratos");
		List<Detalles_paquetes_servicios> listado_paquetes_servicios = (List<Detalles_paquetes_servicios>) datos
				.get("listado_paquetes_servicios");

		String nro_autorizacion = "";
		long result = 0;
		Admision admision = fac.getAdmision();
		if (admision != null) {
			nro_autorizacion = (admision != null ? admision
					.getNro_autorizacion() : "");

			double valor_copago = fac.getValor_copago();
			double valor_neto = (Utilidades.obtenerValorPrimitivo(fac
					.getTotal_factura_clinica()) - fac.getValor_copago());

			// Datos procedimiento //
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", fac.getCodigo_empresa());
			parametros.put("codigo_sucursal", fac.getCodigo_sucursal());
			parametros.put("codigo_administradora",
					fac.getCodigo_administradora());
			parametros.put("id_plan", fac.getId_plan());
			parametros.put("nro_identificacion", fac.getCodigo_tercero());
			parametros.put("nro_ingreso", fac.getNro_ingreso());

			List<Datos_procedimiento> lista_datos_pro = datos_procedimientoDao
					.listarResultadosRips(parametros);
			for (int idx = 0; idx < lista_datos_pro.size(); idx++) {
				Datos_procedimiento dp = (Datos_procedimiento) lista_datos_pro
						.get(idx);
				try {
					String codigo_dct = dp.getCodigo_registro() + "";
					String codigo_factura = fac.getCodigo_documento_res();
					String sexo_pct = (admision != null ? admision
							.getPaciente().getSexo() : "");
					String codigo_pct = dp.getNro_identificacion();
					String fecha_nac = (admision != null ? new java.text.SimpleDateFormat(
							"dd/MM/yyyy").format(admision.getPaciente()
							.getFecha_nacimiento()) : "");

					String[] pdc = null;
					if (admision != null) {
						pdc = getCodigo_pcd(dp.getProcedimientos(),
								dp.getCodigo_procedimiento());
					}
					if (pdc != null && pdc[0] != null) {
						String codigo_procedimiento = pdc[6];
						boolean incluir_rips = true;
						if (admision.getAdmision_parto() != null
								&& admision.getAdmision_parto().equals("S")) {
							if (contratos.getIncluir_paquetes().equals("N")) {
								if (listado_paquetes_servicios.isEmpty()) {
									incluir_rips = true;
								} else {
									for (Detalles_paquetes_servicios detalles_paquetes_servicios : listado_paquetes_servicios) {
										if ((codigo_procedimiento
												.equals(detalles_paquetes_servicios
														.getPaquetes_servicios()
														.getId_procedimiento_principal()) || codigo_procedimiento
												.equals(detalles_paquetes_servicios
														.getCodigo_detalle())
												&& dp.getValor_procedimiento() == 0.0)) {
											incluir_rips = false;
											break;
										}
									}
								}
							} else {
								incluir_rips = true;
							}
						}
						if (incluir_rips) {

							if (dp.getFecha_procedimiento().compareTo(
									fac.getFecha_inicio()) < 0
									|| dp.getFecha_procedimiento().compareTo(
											fac.getFecha_final()) > 0) {
								dp.setFecha_procedimiento(fac.getFecha_final());
							}
							try {
								validarPcd(pdc[4], pdc[5], pdc[3], sexo_pct,
										fecha_nac,
										dp.getCodigo_procedimiento(),
										"El procedimiento", codigo_pct,
										codigo_dct, codigo_factura,
										"procedimiento",
										dp.getFecha_procedimiento(), pdc[0]);
							} catch (LimiteSexoEdadException lsex) {
								if (continuar_error) {
									sbErrores.append(lsex.getMessage()).append(
											"\n");
								} else {
									throw new ValidacionRunTimeException(
											lsex.getMessage(), lsex);
								}
							}

							Administradora administradora = fac
									.getAdministradora();

							String formato_fecha = Utilidades
									.formato_fecha(administradora);

							if (pdc[1].trim().equals("")
									|| pdc[1].trim().equals("1")
									|| pdc[1].trim().equals("2")
									|| pdc[1].trim().equals("3")) {
								double valor_item = dp.getValor_neto();
								if (valor_copago > 0) {
									double dto_copago = (((valor_item * 100) / (valor_neto + valor_copago)) / 100)
											* valor_copago;
									valor_item = valor_item - dto_copago;
								}

								Cie cie = new Cie();
								cie.setCodigo(dp
										.getCodigo_diagnostico_principal());
								cie = generalExtraService.consultar(cie);
								String codigo_dxpp = dp
										.getCodigo_diagnostico_principal();
								String sexo_dxpp = (cie != null ? cie.getSexo()
										: "");
								String limite_inferior_dxpp = (cie != null ? cie
										.getLimite_inferior() : "");
								String limite_superior_dxpp = (cie != null ? cie
										.getLimite_superior() : "");
								try {
									validarDx(limite_inferior_dxpp,
											limite_superior_dxpp, sexo_dxpp,
											sexo_pct, fecha_nac, codigo_dxpp,
											"Principal", codigo_pct,
											codigo_dct, codigo_factura,
											"procedimiento",
											dp.getFecha_procedimiento());
								} catch (LimiteSexoEdadException lsex) {
									if (continuar_error) {
										sbErrores.append(lsex.getMessage())
												.append("\n");
									} else {
										throw new ValidacionRunTimeException(
												lsex.getMessage(), lsex);
									}
								}

								cie = new Cie();
								cie.setCodigo(dp
										.getCodigo_diagnostico_relacionado());
								cie = generalExtraService.consultar(cie);
								String codigo_dx1 = dp
										.getCodigo_diagnostico_relacionado();
								String sexo_dx1 = (cie != null ? cie.getSexo()
										: "");
								String limite_inferior_dx1 = (cie != null ? cie
										.getLimite_inferior() : "");
								String limite_superior_dx1 = (cie != null ? cie
										.getLimite_superior() : "");
								try {
									validarDx(limite_inferior_dx1,
											limite_superior_dx1, sexo_dx1,
											sexo_pct, fecha_nac, codigo_dx1,
											"Relacionado", codigo_pct,
											codigo_dct, codigo_factura,
											"procedimiento",
											dp.getFecha_procedimiento());
								} catch (LimiteSexoEdadException lsex) {
									if (continuar_error) {
										sbErrores.append(lsex.getMessage())
												.append("\n");
									} else {
										throw new ValidacionRunTimeException(
												lsex.getMessage(), lsex);
									}
								}

								cie = new Cie();
								cie.setCodigo(dp.getComplicacion());
								cie = generalExtraService.consultar(cie);
								String codigo_dx2 = dp.getComplicacion();
								String sexo_dx2 = (cie != null ? cie.getSexo()
										: "");
								String limite_inferior_dx2 = (cie != null ? cie
										.getLimite_inferior() : "");
								String limite_superior_dx2 = (cie != null ? cie
										.getLimite_superior() : "");
								try {
									validarDx(limite_inferior_dx2,
											limite_superior_dx2, sexo_dx2,
											sexo_pct, fecha_nac, codigo_dx2,
											"Complicacion", codigo_pct,
											codigo_dct, codigo_factura,
											"procedimiento",
											dp.getFecha_procedimiento());
								} catch (LimiteSexoEdadException lsex) {
									if (continuar_error) {
										sbErrores.append(lsex.getMessage())
												.append("\n");
									} else {
										throw new ValidacionRunTimeException(
												lsex.getMessage(), lsex);
									}
								}
								Map mapAP = new TreeMap();
								mapAP.put("01-nro_factura", Utilidades
										.getNumeroFactura(
												fac.getCodigo_documento_res(),
												longitud));
								mapAP.put("02-codigo_prestador",
										codigo_habilitacion);
								mapAP.put("03-tipo_id",
										(admision != null ? admision
												.getPaciente()
												.getTipo_identificacion() : ""));
								mapAP.put("04-nro_id",
										(admision != null ? admision
												.getPaciente().getDocumento()
												: ""));
								mapAP.put("05-fecha", (Utilidades.formatDate(
										dp.getFecha_procedimiento(),
										formato_fecha)));
								mapAP.put("06-nro_autorizacion",
										(dp.getNumero_autorizacion().trim()
												.equals("") ? nro_autorizacion
												: dp.getNumero_autorizacion()));
								mapAP.put("07-codigo_procedimiento",
										Utilidades.formatText(pdc[0]));
								mapAP.put(
										"08-ambito_pcd",
										validarAmbitoProcedimiento(
												dp.getAmbito_procedimiento(),
												admision != null ? admision
														.getVia_ingreso() : ""));
								mapAP.put(
										"09-finalidad_pcd",
										dp.getFinalidad_procedimiento() != null ? dp
												.getFinalidad_procedimiento()
												: "1");
								mapAP.put(
										"10-personal_atiende",
										dp.getPersonal_atiende() != null ? dp
												.getPersonal_atiende() : "2");
								mapAP.put("11-dx_principal", validarDx(dp
										.getCodigo_diagnostico_principal()));
								mapAP.put("12-dx1", validarDx(dp
										.getCodigo_diagnostico_relacionado()));
								mapAP.put("13-complicacion",
										validarDx(dp.getComplicacion()));
								mapAP.put(
										"14-realizacion",
										dp.getForma_realizacion() != null ? dp
												.getForma_realizacion() : "1");
								mapAP.put("15-valor_neto", Long
										.parseLong(Utilidades.formatDecimal(
												valor_item, "#0")));
								result += (Long) mapAP.get("15-valor_neto");

								if (validarProcedimiento(dp.getProcedimientos())) {
									listaAP.add(mapAP);
								}
							} else {
								double valor_item = dp.getValor_procedimiento();
								if (valor_copago > 0) {
									double dto_copago = (((valor_item * 100) / (valor_neto + valor_copago)) / 100)
											* valor_copago;
									valor_item = valor_item - dto_copago;
								}
								String tipo_estancia = "1";
								if (pdc[1].trim().equals("4")) {
									tipo_estancia = "1";
								} else if (pdc[1].trim().equals("5")) {
									tipo_estancia = "2";
								} else if (pdc[1].trim().equals("6")) {
									tipo_estancia = "3";
								} else if (pdc[1].trim().equals("7")) {
									tipo_estancia = "4";
								}
								Map mapAT = new TreeMap();
								mapAT.put("01-nro_factura", Utilidades
										.getNumeroFactura(
												fac.getCodigo_documento_res(),
												longitud));
								mapAT.put("02-codigo_prestador",
										codigo_habilitacion);
								mapAT.put("03-tipo_id",
										(admision != null ? admision
												.getPaciente()
												.getTipo_identificacion() : ""));
								mapAT.put("04-nro_id",
										(admision != null ? admision
												.getPaciente().getDocumento()
												: ""));
								mapAT.put("05-nro_autorizacion",
										(dp.getNumero_autorizacion().trim()
												.equals("") ? nro_autorizacion
												: dp.getNumero_autorizacion()));
								mapAT.put("06-tipo_estancia", tipo_estancia);
								mapAT.put("07-codigo_servicio",
										Utilidades.formatText(pdc[0]));
								mapAT.put("08-nombre_servicio",
										Utilidades.formatText(pdc[2]));
								mapAT.put("09-unidades", dp.getUnidades());
								mapAT.put("10-valor_unit", Long
										.parseLong(Utilidades.formatDecimal(
												valor_item, "#0")));
								mapAT.put("11-valor_total", Long
										.parseLong(Utilidades.formatDecimal(
												valor_item * dp.getUnidades(),
												"#0")));
								result += (Long) mapAT.get("11-valor_total");
								listaAT.add(mapAT);
							}
						}
					}
				} catch (LimiteSexoEdadException lsex) {
					if (continuar_error) {
						sbErrores.append(lsex.getMessage()).append("\n");
					} else {
						throw new ValidacionRunTimeException(lsex.getMessage(),
								lsex);
					}
				}
			}
		}
		return result;
	}

	private boolean validarProcedimiento(Procedimientos procedimientos) {
		if (procedimientos != null && procedimientos.getConsulta() != null) {
			return procedimientos.getConsulta().equalsIgnoreCase("N");
		}
		return true;
	}

	private String validarAmbitoProcedimiento(String ambito_procedimiento,
			String via_ingreso) {
		if (via_ingreso != null) {
			if ((via_ingreso.equals(IVias_ingreso.URGENCIA) || via_ingreso
					.equals(IVias_ingreso.URGENCIA_ODONTOLOGICO))) {
				return IConstantes.AMBITO_REALIZACION_PROCEDIMIENTO_URGENCIAS;
			} else if (via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES)) {
				return IConstantes.AMBITO_REALIZACION_PROCEDIMIENTO_HOSPITALARIO;
			} else {
				return IConstantes.AMBITO_REALIZACION_PROCEDIMIENTO_AMBULATORIO;
			}
		} else if (ambito_procedimiento != null) {
			return ambito_procedimiento;
		} else {
			return IConstantes.AMBITO_REALIZACION_PROCEDIMIENTO_AMBULATORIO;
		}
	}

	// Generamos el rips AC en un Mapa //
	private long generarListaAM_AT(Map<String, Object> datos, Facturacion fac,
			List<Map> listaAuxAM, List<Map> listaAuxAT, int longitud)
			throws Exception {
		Boolean continuar_error = (Boolean) datos.get("continuar_error");
		// String tipo = (String) datos.get("tipo");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");

		Contratos contratos = (Contratos) datos.get("contratos");
		List<Detalles_paquetes_servicios> listado_paquetes_servicios = (List<Detalles_paquetes_servicios>) datos
				.get("listado_paquetes_servicios");

		String nro_autorizacion = "";
		Admision admision = fac.getAdmision();
		long result = 0;
		if (admision != null) {

			nro_autorizacion = (admision != null ? admision
					.getNro_autorizacion() : "");

			double valor_copago = fac.getValor_copago();
			double valor_neto = (Utilidades.obtenerValorPrimitivo(fac
					.getTotal_factura_clinica()) - fac.getValor_copago());

			// Datos medicamentos //
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", fac.getCodigo_empresa());
			parametros.put("codigo_sucursal", fac.getCodigo_sucursal());
			// parametros.put("tipo_reg", tipo_reg);
			parametros.put("codigo_administradora",
					fac.getCodigo_administradora());
			parametros.put("id_plan", fac.getId_plan());
			parametros.put("nro_identificacion", fac.getCodigo_tercero());
			parametros.put("nro_ingreso", fac.getNro_ingreso());

			List<Datos_medicamentos> lista_aux_med = datos_medicamentosDao
					.listar_factura(parametros);

			List<Datos_medicamentos> lista_datos_med = new ArrayList<Datos_medicamentos>();
			for (Datos_medicamentos dm : lista_aux_med) {
				Detalle_factura detalle_factura = new Detalle_factura();
				detalle_factura.setCodigo_empresa(fac.getCodigo_empresa());
				detalle_factura.setCodigo_sucursal(fac.getCodigo_sucursal());
				detalle_factura.setId_factura(fac.getId_factura());
				detalle_factura.setCodigo_articulo(dm.getCodigo_medicamento());
				detalle_factura.setFactura_concepto(dm.getNro_factura());

				boolean fact = false;
				try {
					detalle_factura = detalle_facturaDao
							.consultar_facturable(detalle_factura);
					fact = true;
				} catch (MyBatisSystemException e) {
					if (continuar_error) {
						fact = false;
						sbErrores.append("codigo_factura=")
								.append(fac.getCodigo_documento_res())
								.append(", El detalle ")
								.append(detalle_factura.getCodigo_articulo())
								.append(" esta repetido");
					} else {
						throw new ValidacionRunTimeException("codigo_factura="
								+ fac.getCodigo_documento_res()
								+ ", El detalle "
								+ detalle_factura.getCodigo_articulo()
								+ " esta repetido");
					}

				}
				// System.out.println("detalle_factura medi: "+(detalle_factura!=null?true:false));

				if (fact && detalle_factura != null) {
					fact = detalle_factura.getFacturable();
				}
				if (fact && dm.getUnidades() > 0) {
					lista_datos_med.add(dm);
				}
			}
			// agruparMedicamentosInsumos(lista_datos_med,
			// tipo_reg.equals("01")?"MEDICAMENTOS":"MATERIALES");
			agruparMedicamentosInsumos(lista_datos_med, "MEDICAMENTOS");
			for (int idx = 0; idx < lista_datos_med.size(); idx++) {
				Datos_medicamentos dm = (Datos_medicamentos) lista_datos_med
						.get(idx);

				double valor_item = dm.getValor_unitario();
				if (valor_copago > 0) {
					double dto_copago = (((valor_item * 100) / (valor_neto + valor_copago)) / 100)
							* valor_copago;
					valor_item = valor_item - dto_copago;
				}

				Facturacion_medicamento fm = dm.getFacturacion_medicamento();

				Articulo articulo = dm.getArticulo();

				Unidad_medida unidad_medida = new Unidad_medida();
				unidad_medida.setCodigo_empresa(fm.getCodigo_empresa());
				unidad_medida.setCodigo_sucursal(fm.getCodigo_sucursal());
				unidad_medida.setCodigo((articulo != null ? articulo
						.getUnidad_medida() : ""));
				unidad_medida = (Unidad_medida) unidad_medidaDao
						.consultar(unidad_medida);

				if (articulo != null && admision != null) {
					String codigo_procedimiento = articulo.getCodigo_articulo();
					boolean incluir_rips = true;
					if (admision.getAdmision_parto() != null
							&& admision.getAdmision_parto().equals("S")) {
						if (contratos.getIncluir_paquetes().equals("N")) {
							if (listado_paquetes_servicios.isEmpty()) {
								incluir_rips = true;
							} else {
								for (Detalles_paquetes_servicios detalles_paquetes_servicios : listado_paquetes_servicios) {
									if (codigo_procedimiento
											.equals(detalles_paquetes_servicios
													.getCodigo_detalle())
											&& dm.getValor_total() == 0.0) {
										incluir_rips = false;
										break;
									}
								}
							}
						} else {
							incluir_rips = true;
						}
					}

					if (incluir_rips) {
						if (articulo.getGrupo1().equals("01")) {
							String unidades = dm.getUnidades() + "";
							String valor_unitario = dm.getValor_unitario() + "";
							Map<String, Object> mapAM = new TreeMap<String, Object>();
							mapAM.put("01-nro_factura", Utilidades
									.getNumeroFactura(
											fac.getCodigo_documento_res(),
											longitud));
							mapAM.put("02-codigo_prestador",
									codigo_habilitacion);
							mapAM.put("03-tipo_id",
									(admision != null ? admision.getPaciente()
											.getTipo_identificacion() : ""));
							mapAM.put("04-nro_id", (admision != null ? admision
									.getPaciente().getDocumento() : ""));
							mapAM.put(
									"05-nro_autorizacion",
									(fm.getNumero_autorizacion().trim()
											.equals("") ? nro_autorizacion : fm
											.getNumero_autorizacion()));
							mapAM.put("06-codigo", getCodigoAmRips(articulo));
							mapAM.put("07-tipo", (articulo != null ? (articulo
									.getPos().equals("S") ? "1" : "2") : ""));
							mapAM.put(
									"08-nombre",
									(articulo != null ? (articulo.getNombre1()
											.length() <= 30 ? Utilidades
											.formatText(articulo.getNombre1())
											: Utilidades.formatText(articulo
													.getNombre1().substring(0,
															30))) : ""));
							mapAM.put(
									"09-forma_farmaceutica",
									(articulo != null ? (articulo
											.getUnidad_concentracion().length() <= 20 ? Utilidades
											.formatText(articulo
													.getUnidad_concentracion())
											: Utilidades.formatText(articulo
													.getUnidad_concentracion()
													.substring(0, 20)))
											: ""));
							mapAM.put(
									"10-concentracion",
									(articulo != null ? (articulo
											.getConcentracion().length() <= 20 ? Utilidades
											.formatText(articulo
													.getConcentracion())
											: Utilidades.formatText(articulo
													.getConcentracion()
													.substring(0, 20)))
											: ""));
							mapAM.put(
									"11-unidad_medida",
									(unidad_medida != null ? unidad_medida
											.getNombre() : ""));
							if (unidades.length() > 5
									&& valor_unitario.length() <= 5) {
								mapAM.put("12-unidades", Integer
										.parseInt(Utilidades.formatDecimal(
												valor_item, "#0")));
								mapAM.put("13-valor_unit", dm.getUnidades());
							} else {
								mapAM.put("12-unidades", dm.getUnidades());
								mapAM.put("13-valor_unit", Integer
										.parseInt(Utilidades.formatDecimal(
												valor_item, "#0")));
							}
							mapAM.put(
									"14-valor_total",
									Long.parseLong(Utilidades.formatDecimal(
											valor_item * dm.getUnidades(), "#0")));
							result += (Long) mapAM.get("14-valor_total");
							listaAuxAM.add(mapAM);
						} else {
							Map<String, Object> mapAT = new TreeMap<String, Object>();
							mapAT.put("01-nro_factura", Utilidades
									.getNumeroFactura(
											fac.getCodigo_documento_res(),
											longitud));
							mapAT.put("02-codigo_prestador",
									codigo_habilitacion);
							mapAT.put("03-tipo_id",
									(admision != null ? admision.getPaciente()
											.getTipo_identificacion() : ""));
							mapAT.put("04-nro_id", (admision != null ? admision
									.getPaciente().getDocumento() : ""));
							mapAT.put(
									"05-nro_autorizacion",
									(fm.getNumero_autorizacion().trim()
											.equals("") ? nro_autorizacion : fm
											.getNumero_autorizacion()));
							mapAT.put("06-tipo_estancia", "1");
							mapAT.put("07-codigo_servicio",
									dm.getCodigo_medicamento());
							mapAT.put(
									"08-nombre_servicio",
									(articulo != null ? Utilidades
											.formatText(articulo.getNombre1())
											: ""));
							mapAT.put("09-unidades", dm.getUnidades());
							mapAT.put("10-valor_unit", Long
									.parseLong(Utilidades.formatDecimal(
											valor_item, "#0")));
							mapAT.put(
									"11-valor_total",
									Long.parseLong(Utilidades.formatDecimal(
											valor_item * dm.getUnidades(), "#0")));
							result += (Long) mapAT.get("11-valor_total");
							listaAuxAT.add(mapAT);
						}
					}
				}
			}
		}
		return result;
	}

	// Generamos el rips AC en un Mapa //
	private long generarListaAT(Map<String, Object> datos, Facturacion fac,
			List<Map> listaAT, int longitud) throws Exception {

		// String tipo = (String) datos.get("tipo");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");

		String tipo_reg = "01";

		String nro_autorizacion = "";
		long result = 0;
		Admision admision = fac.getAdmision();
		if (admision != null) {
			nro_autorizacion = (admision != null ? admision
					.getNro_autorizacion() : "");

			double valor_copago = fac.getValor_copago();
			double valor_neto = (Utilidades.obtenerValorPrimitivo(fac
					.getTotal_factura_clinica()) - fac.getValor_copago());

			// Servicios //
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", fac.getCodigo_empresa());
			parametros.put("codigo_sucursal", fac.getCodigo_sucursal());
			parametros.put("tipo_reg", tipo_reg);
			parametros.put("codigo_administradora",
					fac.getCodigo_administradora());
			parametros.put("id_plan", fac.getId_plan());
			parametros.put("nro_identificacion", fac.getCodigo_tercero());
			parametros.put("nro_ingreso", fac.getNro_ingreso());

			List<Datos_servicio> lista_datos_sers = datos_servicioDao
					.listar_factura(parametros);

			for (int idx = 0; idx < lista_datos_sers.size(); idx++) {
				Datos_servicio ds = (Datos_servicio) lista_datos_sers.get(idx);
				double valor_item = ds.getValor_unitario();
				if (valor_copago > 0) {
					double dto_copago = (((valor_item * 100) / (valor_neto + valor_copago)) / 100)
							* valor_copago;
					valor_item = valor_item - dto_copago;
				}
				Facturacion_servicio fm = ds.getFacturacion_servicio();

				Articulo pd = ds.getArticulo();

				String numero_autorizacion = (ds.getNumero_autorizacion() != null
						&& !ds.getNumero_autorizacion().trim().isEmpty() ? ds
						.getNumero_autorizacion() : (fm
						.getNumero_autorizacion() != null
						&& !fm.getNumero_autorizacion().trim().isEmpty() ? fm
						.getNumero_autorizacion() : nro_autorizacion));

				Paciente pct = fac.getAdmision().getPaciente();
				Map<String, Object> mapAT = new TreeMap<String, Object>();
				mapAT.put(
						"01-nro_factura",
						Utilidades.getNumeroFactura(
								fac.getCodigo_documento_res(), longitud));
				mapAT.put("02-codigo_prestador", codigo_habilitacion);
				mapAT.put("03-tipo_id",
						(pct != null ? pct.getTipo_identificacion() : ""));
				mapAT.put("04-nro_id", (admision != null ? admision
						.getPaciente().getDocumento() : ""));
				mapAT.put("05-nro_autorizacion", numero_autorizacion);
				mapAT.put("06-tipo_estancia", "1");
				mapAT.put("07-codigo_servicio", ds.getCodigo_servicio());
				mapAT.put("08-nombre_servicio",
						(pd != null ? Utilidades.formatText(pd.getNombre1())
								: ""));
				mapAT.put("09-unidades", ds.getUnidades());
				mapAT.put("10-valor_unit", Long.parseLong(Utilidades
						.formatDecimal(valor_item, "#0")));
				mapAT.put(
						"11-valor_total",
						Long.parseLong(Utilidades.formatDecimal(
								valor_item * ds.getUnidades(), "#0")));
				result += (Long) mapAT.get("11-valor_total");
				listaAT.add(mapAT);

			}
		}

		return result;
	}

	// Metodo para ajustar la unida que sobre cuando se calcule el valor menos
	// copago distribuido en los servicios //
	private void acomodarValores(List<Map> listaAuxAC, List<Map> listaAuxAP,
			List<Map> listaAuxAT1, List<Map> listaAuxAM, List<Map> listaAuxAT2,
			List<Map> listaAuxAT3, boolean sw, long diferencia) {

		if (!listaAuxAC.isEmpty()) {
			Map aux = listaAuxAC.get(0);
			long valor_aux = ((Long) aux.get("15-valor_consulta")).longValue();
			if (!sw) {
				valor_aux -= (diferencia * -1);
			} else {
				valor_aux += diferencia;
			}
			if (valor_aux > 0) {
				aux.put("15-valor_consulta", valor_aux);
				aux.put("17-valor_neto", valor_aux);
				listaAuxAC.set(0, aux);
			}
		} else if (!listaAuxAP.isEmpty()) {
			Map aux = listaAuxAP.get(0);
			long valor_aux = (Long) aux.get("15-valor_neto");
			if (!sw) {
				valor_aux -= (diferencia * -1);
			} else {
				valor_aux += diferencia;
			}
			if (valor_aux > 0) {
				aux.put("15-valor_neto", valor_aux);
				listaAuxAP.set(0, aux);
			}
		} else if (!listaAuxAT1.isEmpty()) {
			Map aux = listaAuxAT1.get(0);
			int unidades = (Integer) aux.get("09-unidades");
			long valor_aux = ((Long) aux.get("10-valor_unit")).longValue();
			if (!sw) {
				valor_aux -= (diferencia * -1);
			} else {
				valor_aux += diferencia;
			}
			if (valor_aux > 0) {
				aux.put("10-valor_unit", valor_aux);
				aux.put("11-valor_total", (int) (unidades * valor_aux));
				listaAuxAT1.set(0, aux);
			}
		} else if (!listaAuxAM.isEmpty()) {
			Map aux = listaAuxAM.get(0);
			int unidades = (Integer) aux.get("12-unidades");
			long valor_aux = ((Integer) aux.get("13-valor_unit")).longValue();
			if (!sw) {
				valor_aux -= (diferencia * -1);
			} else {
				valor_aux += diferencia;
			}

			if (valor_aux > 0) {
				aux.put("13-valor_unit", valor_aux);
				aux.put("14-valor_total", (int) (unidades * valor_aux));
				listaAuxAM.set(0, aux);
			}
		} else if (!listaAuxAT2.isEmpty()) {
			Map aux = listaAuxAT2.get(0);
			int unidades = (Integer) aux.get("09-unidades");
			long valor_aux = ((Long) aux.get("10-valor_unit")).longValue();
			if (!sw) {
				valor_aux -= (diferencia * -1);
			} else {
				valor_aux += diferencia;
			}
			if (valor_aux > 0) {
				aux.put("10-valor_unit", valor_aux);
				aux.put("11-valor_total", (int) (unidades * valor_aux));
				listaAuxAT2.set(0, aux);
			}
		} else if (!listaAuxAT3.isEmpty()) {
			Map aux = listaAuxAT3.get(0);
			int unidades = (Integer) aux.get("09-unidades");
			long valor_aux = ((Long) aux.get("10-valor_unit")).longValue();
			if (!sw) {
				valor_aux -= (diferencia * -1);
			} else {
				valor_aux += diferencia;
			}
			if (valor_aux > 0) {
				aux.put("10-valor_unit", valor_aux);
				aux.put("11-valor_total", (int) (unidades * valor_aux));
				listaAuxAT3.set(0, aux);
			}
		}
	}

	// Metodo para agregar los servicios a las listas principales de AC,AP,AM y
	// AT//
	private void adicionarListaPrincipal(List<Map> listaAuxAC,
			List<Map> listaAuxAP, List<Map> listaAuxAT1, List<Map> listaAuxAM,
			List<Map> listaAuxAT2, List<Map> listaAuxAT3, List<Map> listaAC,
			List<Map> listaAP, List<Map> listaAM, List<Map> listaAT) {

		if (!listaAuxAC.isEmpty()) {
			for (int i = 0; i < listaAuxAC.size(); i++) {
				Map map = listaAuxAC.get(i);
				listaAC.add(map);
			}
		}

		if (!listaAuxAP.isEmpty()) {
			for (int i = 0; i < listaAuxAP.size(); i++) {
				Map map = listaAuxAP.get(i);
				listaAP.add(map);
			}
		}

		if (!listaAuxAM.isEmpty()) {
			for (int i = 0; i < listaAuxAM.size(); i++) {
				Map map = listaAuxAM.get(i);
				listaAM.add(map);
			}
		}

		if (!listaAuxAT1.isEmpty()) {
			for (int i = 0; i < listaAuxAT1.size(); i++) {
				Map map = listaAuxAT1.get(i);
				listaAT.add(map);
			}
		}

		if (!listaAuxAT2.isEmpty()) {
			for (int i = 0; i < listaAuxAT2.size(); i++) {
				Map map = listaAuxAT2.get(i);
				listaAT.add(map);
			}
		}

		if (!listaAuxAT3.isEmpty()) {
			for (int i = 0; i < listaAuxAT3.size(); i++) {
				Map map = listaAuxAT3.get(i);
				listaAT.add(map);
			}
		}

	}

	// Generamos el rips CT en un Mapa //
	private void generarListaCT(Map<String, Object> datos, List<Map> listaAF,
			List<Map> listaAU, List<Map> listaAH, List<Map> listaAN,
			List<Map> listaUS, List<Map> listaAC, List<Map> listaAP,
			List<Map> listaAM, List<Map> listaAT, List<Map> listaCT,
			String nro_remision, String codigo_admin) throws Exception {

		String codigo_empresa = (String) datos.get("codigo_empresa");
		String codigo_sucursal = (String) datos.get("codigo_sucursal");
		String codigo_habilitacion = (String) datos.get("codigo_habilitacion");

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(codigo_empresa);
		administradora.setCodigo_sucursal(codigo_sucursal);
		administradora.setCodigo(codigo_admin);

		String formato_fecha = Utilidades.formato_fecha(administradora);
		if (!listaAF.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "AF" + nro_remision);
			mapCT.put("04-total", listaAF.size());
			listaCT.add(mapCT);
		}
		if (!listaAC.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "AC" + nro_remision);
			mapCT.put("04-total", listaAC.size());
			listaCT.add(mapCT);
		}
		if (!listaAP.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "AP" + nro_remision);
			mapCT.put("04-total", listaAP.size());
			listaCT.add(mapCT);
		}
		if (!listaAM.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "AM" + nro_remision);
			mapCT.put("04-total", listaAM.size());
			listaCT.add(mapCT);
		}
		if (!listaAT.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "AT" + nro_remision);
			mapCT.put("04-total", listaAT.size());
			listaCT.add(mapCT);
		}
		if (!listaAU.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "AU" + nro_remision);
			mapCT.put("04-total", listaAU.size());
			listaCT.add(mapCT);
		}
		if (!listaAH.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "AH" + nro_remision);
			mapCT.put("04-total", listaAH.size());
			listaCT.add(mapCT);
		}
		if (!listaAN.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "AN" + nro_remision);
			mapCT.put("04-total", listaAN.size());
			listaCT.add(mapCT);
		}
		if (!listaUS.isEmpty()) {
			Map mapCT = new TreeMap();
			mapCT.put("01-codigo_prestador", codigo_habilitacion);
			mapCT.put("02-fecha",
					Utilidades.formatDate(new Date(), formato_fecha));
			mapCT.put("03-remision", "US" + nro_remision);
			mapCT.put("04-total", listaUS.size());
			listaCT.add(mapCT);
		}

	}

	private String[] getCodigo_pcd(Procedimientos pro, String codigo)
			throws LimiteSexoEdadException {

		String result[] = new String[7];

		result[1] = "";
		result[2] = "";
		result[3] = "";
		result[4] = "";
		result[5] = "";
		result[6] = "";

		if (pro != null) {
			if (pro.getCodigo_cups() == null) {
				throw new LimiteSexoEdadException("El procedimiento "
						+ pro.getId_procedimiento() + "-"
						+ pro.getDescripcion() + " no tiene codigo cups");
			} else if (pro.getCodigo_cups().trim().equals("")) {
				throw new LimiteSexoEdadException("El procedimiento "
						+ pro.getId_procedimiento() + "-"
						+ pro.getDescripcion() + " no tiene codigo cups");
			}
			result[0] = pro.getCodigo_cups();
			result[1] = pro.getClasificacion();
			result[2] = pro.getDescripcion();
			result[3] = pro.getSexo();
			result[4] = pro.getLimite_inferior();
			result[5] = pro.getLimite_superior();
			result[6] = pro.getId_procedimiento() + "";

		} else {
			result[0] = codigo;
			// throw new
			// Exception("El procedimiento "+dc.getCodigo_consulta()+" no existe en el manual tarifario SOAT");
		}

		if (result[1] == null) {
			result[1] = "";
		}

		return result;
	}

	private void validarPcd(String limite_inferior, String limite_superior,
			String sexo_pcd, String sexo_pct, String fecha_nac,
			String codigo_pcd, String msg, String codigo_paciente,
			String codigo_dct, String codigo_factura, String archivo,
			Timestamp fecha_atencion, String cups_procedimiento)
			throws LimiteSexoEdadException {
		if (!codigo_pcd.trim().equals("")) {
			// Validamos si la edad corresponde al dx con limite inferior//
			if (!limite_inferior.equals("0") && !limite_inferior.equals("000")
					&& !limite_inferior.equals("")) {
				String unidad_medida = limite_inferior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Utilidades.getEdad2(fecha_nac, unidad_medida,
						fecha_atencion);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) < Integer
							.parseInt(limite_inferior.substring(1))) {
						throw new LimiteSexoEdadException("codigo_factura="
								+ codigo_factura + ", " + msg + " "
								+ cups_procedimiento
								+ " no corresponde con la edad del paciente "
								+ codigo_paciente + " del registro de "
								+ archivo + " nro " + codigo_dct);
					}
				}
			}

			// Validamos si la edad corresponde al dx con limite superior//
			if (!limite_superior.equals("599") && !limite_superior.equals("0")
					&& !limite_superior.equals("")) {
				String unidad_medida = limite_superior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Utilidades.getEdad2(fecha_nac, unidad_medida,
						fecha_atencion);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) > Integer
							.parseInt(limite_superior.substring(1))) {
						throw new LimiteSexoEdadException(
								"codigo_factura="
										+ codigo_factura
										+ ", "
										+ msg
										+ " "
										+ codigo_pcd
										+ " no corresponde con la edad en la que se atendio ("
										+ edad + ") el paciente "
										+ codigo_paciente + " del registro de "
										+ archivo + " nro " + codigo_dct);
					}
				}
			}
			// Validamos si el sexo corresponde al dx //
			if (!sexo_pcd.equals("") && !sexo_pcd.equals("A")) {
				if (sexo_pcd.equals("H")) {
					sexo_pcd = "M";
				} else if (sexo_pcd.equals("M")) {
					sexo_pcd = "F";
				}

				if (!sexo_pct.equals(sexo_pcd)) {
					throw new LimiteSexoEdadException("codigo_factura="
							+ codigo_factura + ", " + msg + " " + codigo_pcd
							+ " no corresponde con el sexo del paciente "
							+ codigo_paciente + " del registro de " + archivo
							+ " nro " + codigo_dct + " factura interna :"
							+ codigo_factura);
				}
			}
		}
	}

	// Agrupamos los medicamentos //
	private void agruparMedicamentosInsumos(List list, String tipo_servicio)
			throws Exception {
		Map general = new HashMap();
		for (int idx = 0; idx < list.size(); idx++) {
			Datos_medicamentos dm = (Datos_medicamentos) list.get(idx);

			Articulo pd = dm.getArticulo();

			int cantidad = dm.getUnidades();
			double total = dm.getValor_total();
			String detalle = (pd != null ? (pd.getNombre1().length() <= 30 ? Utilidades
					.formatText(pd.getNombre1()) : Utilidades.formatText(pd
					.getNombre1().substring(0, 30)))
					: "");
			// String detalle = (pd != null ? (pd.getNombre1()) : "");
			// general.get(fm.getFacturacion()+"_"+tipo_servicio + "_" +
			// detalle)
			if (general.get(tipo_servicio + "_" + detalle) == null) {
				general.put(tipo_servicio + "_" + detalle, dm);
			} else {
				Datos_medicamentos aux = (Datos_medicamentos) general
						.get(tipo_servicio + "_" + detalle);
				int can_aux = aux.getUnidades();
				int vu_aux = (int) aux.getValor_unitario();
				int total_aux = (int) aux.getValor_total();

				can_aux += cantidad;
				total_aux += total;
				if (can_aux > 0) {
					vu_aux = (total_aux / can_aux);
					total_aux = (vu_aux * can_aux);
				}

				aux.setUnidades(can_aux);
				aux.setValor_unitario(vu_aux);
				aux.setValor_total(total_aux);

				/*
				 * aux.put("cantidad", new Integer(can_aux));
				 * aux.put("valor_unitario", new java.math.BigDecimal(vu_aux));
				 * aux.put("total", new java.math.BigDecimal(total_aux));
				 */
				general.put(tipo_servicio + "_" + detalle, aux);
			}
		}
		list.clear();
		Iterator<Object> it = general.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			Datos_medicamentos dm = (Datos_medicamentos) e.getValue();
			list.add(dm);
		}
	}

	// Metodo para generar los rips //
	private File generarRips(Map<String, Object> datos, List<Map> listaAF,
			List<Map> listaAU, List<Map> listaAH, List<Map> listaAN,
			List<Map> listaUS, List<Map> listaAC, List<Map> listaAP,
			List<Map> listaAM, List<Map> listaAT, List<Map> listaCT,
			String nro_remision, String codigo_administradora, String id_plan)
			throws Exception {
		log.info("Generando la exportacion final de los rips");
		ServletContext servletContext = (ServletContext) datos
				.get("servletContext");
		Boolean continuar_error = (Boolean) datos.get("continuar_error");
		String codigo_empresa = (String) datos.get("codigo_empresa");
		String codigo_sucursal = (String) datos.get("codigo_sucursal");

		final int BUFFER = 2048;
		File file = new File(servletContext.getRealPath("") + "/Rips");
		if (!file.exists()) {
			file.mkdir();
		}

		String formato_comprimido = formato_comprimido(codigo_empresa,
				codigo_sucursal, codigo_administradora);

		Map mapBuffer = new HashMap();
		mapBuffer.put("AF", listaAF);
		mapBuffer.put("US", listaUS);
		mapBuffer.put("AC", listaAC);
		mapBuffer.put("AP", listaAP);
		mapBuffer.put("AM", listaAM);
		mapBuffer.put("AT", listaAT);
		mapBuffer.put("AU", listaAU);
		mapBuffer.put("AH", listaAH);
		mapBuffer.put("AN", listaAN);
		mapBuffer.put("CT", listaCT);

		Iterator itBuffer = mapBuffer.entrySet().iterator();
		while (itBuffer.hasNext()) {
			Map.Entry e = (Map.Entry) itBuffer.next();
			List<Map> lista_aux = (List<Map>) e.getValue();
			if (!lista_aux.isEmpty()) {
				StringBuilder textoArchivo = new StringBuilder();
				for (int i = 0; i < lista_aux.size(); i++) {
					Map map = lista_aux.get(i);
					Iterator iterator = map.values().iterator();
					int tam_map = map.size();
					int cont_map = 1;
					while (iterator.hasNext()) {
						textoArchivo.append(iterator.next() + "");
						if (cont_map < tam_map) {
							textoArchivo.append(",");
						}
						cont_map++;
					}
					if (i < (lista_aux.size() - 1)) {
						textoArchivo.append("\r\n");
					}
				}
				DataOutputStream dos = new DataOutputStream(
						new FileOutputStream(servletContext.getRealPath("")
								+ "/Rips/" + e.getKey() + "" + nro_remision
								+ ".TXT"));
				dos.writeBytes(textoArchivo.toString());
				dos.close();
			}
		}

		if (continuar_error && sbErrores != null
				&& !sbErrores.toString().isEmpty()) {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(
					servletContext.getRealPath("") + "/Rips/Errores_"
							+ nro_remision + ".TXT"));
			dos.writeBytes(sbErrores.toString());
			dos.close();
		}

		// Almacenamos en .Zip ///
		BufferedInputStream origin = null;
		File temp = new File(servletContext.getRealPath("") + "/temp");
		if (!temp.exists()) {
			temp.mkdir();
		}

		File file_des = new File(servletContext.getRealPath("") + "/temp/Rips-"
				+ codigo_administradora + "-" + id_plan + "-"
				+ Utilidades.formatDate(new Date(), "yyyyMMddHHmmss") + "."
				+ formato_comprimido);

		FileOutputStream dest = new FileOutputStream(file_des);
		ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(dest));
		byte data[] = new byte[BUFFER];
		String files[] = file.list();
		for (int i = 0; i < files.length; i++) {

			FileInputStream fi = new FileInputStream(
					servletContext.getRealPath("") + "/Rips/" + files[i]);
			origin = new BufferedInputStream(fi, BUFFER);
			// creamos una entrada zip
			ZipEntry entry = new ZipEntry(files[i]);
			// agregamos entradas zip al archivo
			out.putNextEntry(entry);
			int count;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
			File aux = new File(servletContext.getRealPath("") + "/Rips/"
					+ files[i]);
			if (aux.exists()) {
				aux.delete();
			}
		}
		out.close();

		// byte[] fileContent = null;
		ByteArrayOutputStream theBAOS = new ByteArrayOutputStream();
		FileInputStream theFIS = null;
		try {
			theBAOS.reset();
			theFIS = new FileInputStream(file_des);
			@SuppressWarnings("resource")
			BufferedInputStream theBIS = new BufferedInputStream(theFIS);
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = theBIS.read(buffer)) >= 0) {
				theBAOS.write(buffer, 0, bytesRead);
			}
			theBAOS.flush();
			// fileContent = theBAOS.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (theBAOS != null) {
				theBAOS.reset();
			}
			if (theFIS != null) {
				try {
					theFIS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		porcentaje_progreso = 100.0;
		return file_des;

	}

	/**
	 * Este metodo sirve para que en los rips no se vallan los codigos de los
	 * diagnosticos con 3 digitos
	 *
	 */
	public String validarDx(String diagnostico) {
		if (diagnostico != null && !diagnostico.trim().isEmpty()
				&& diagnostico.length() == 3) {
			return diagnostico + "X";
		} else if (diagnostico == null) {
			return "";
		}
		return diagnostico;
	}

	private String getCodigoAmRips(Articulo articulo) {
		if (articulo != null) {
			if (articulo.getReferencia() != null) {
				if (!articulo.getReferencia().isEmpty()) {
					return articulo.getReferencia();
				} else {
					return articulo.getCodigo_articulo();
				}
			} else {
				return articulo.getCodigo_articulo();
			}
		} else {
			return "";
		}
	}

	public String formato_comprimido(String codigo_empresa,
			String codigo_sucursal, String codigo_administradora)
			throws Exception {

		String formato_comprimido = ".zip";

		Administradora admin = new Administradora();
		admin.setCodigo(codigo_administradora);
		admin = administradoraDao.consultar(admin);

		formato_comprimido = (admin != null ? admin.getFormato_rips() : ".zip");

		return formato_comprimido;
	}

	public String getRips_generacion() {
		return rips_generacion != null ? rips_generacion : "";
	}

	public void setRips_generacion(String rips_generacion) {
		this.rips_generacion = rips_generacion;
	}

	public Double getPorcentaje_progreso() {
		return porcentaje_progreso != null ? porcentaje_progreso : 0.0;
	}

	public void setPorcentaje_progreso(Double porcentaje_progreso) {
		this.porcentaje_progreso = porcentaje_progreso;
	}

}
