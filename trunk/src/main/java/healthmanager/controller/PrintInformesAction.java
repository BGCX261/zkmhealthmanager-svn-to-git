package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Config_carnets;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Modulo_firmas;
import healthmanager.modelo.bean.Muestra_citologia_vaginal;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Tipo_procedimiento;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.Anexo9_entidadService;
import healthmanager.modelo.service.Antecedentes_personalesService;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.Config_carnetsService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Datos_consultaService;
import healthmanager.modelo.service.Datos_medicamentosService;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Datos_servicioService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Grupos_quirurgicosService;
import healthmanager.modelo.service.Hisc_urgenciaService;
import healthmanager.modelo.service.Modulo_firmasService;
import healthmanager.modelo.service.Muestra_citologia_vaginalService;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.Orden_servicioService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.ReportService;
import healthmanager.modelo.service.ResolucionService;
import healthmanager.modelo.service.Tipo_procedimientoService;
import healthmanager.modelo.service.UsuariosService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IFichas_epidemiologicas;
import com.framework.constantes.IFirmasConstantes;
import com.framework.constantes.INivelesTriage;
import com.framework.helper.DataSourceReport;
import com.framework.macros.GlasgowMacro;
import com.framework.res.Res;
import com.framework.util.ConvertidorXmlToMap;
import com.framework.util.MensajesUtil;
import com.framework.util.Num_letter;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Presentacion_articulo;
import contaweb.modelo.service.ArticuloService;
import contaweb.modelo.service.FacturacionService;
import contaweb.modelo.service.Presentacion_articuloService;
import healthmanager.modelo.service.GeneralExtraService;

public class PrintInformesAction extends GeneralComposer {

	private static Logger log = Logger.getLogger(PrintInformesAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@WireVariable
	private ResolucionService resolucionService;
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private DepartamentosService departamentosService;
	@WireVariable
	private MunicipiosService municipiosService;
	@WireVariable
	private ReportService reportService;
	@WireVariable
	private Anexo9_entidadService anexo9_entidadService;
	@WireVariable
	private UsuariosService usuariosService;
	@WireVariable
	private PrestadoresService prestadoresService;
	@WireVariable
	private ProcedimientosService procedimientosService;
	@WireVariable
	private CitasService citasService;
	@WireVariable
	private Afiliaciones_meService afiliaciones_meService;
	@WireVariable
	private AdmisionService admisionService;
	@WireVariable
	private AdministradoraService administradoraService;
	@WireVariable
	private ContratosService contratosService;
	@WireVariable
	private contaweb.modelo.service.ReportService reportContawebService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private Config_carnetsService config_carnetsService;
	@WireVariable
	private Centro_atencionService centro_atencionService;
	@WireVariable
	private ArticuloService articuloService;
	@WireVariable
	private Orden_servicioService orden_servicioService;
	@WireVariable
	private Datos_servicioService datos_servicioService;
	@WireVariable
	private Datos_consultaService datos_consultaService;
	@WireVariable
	private Datos_procedimientoService datos_procedimientoService;
	@WireVariable
	private Datos_medicamentosService datos_medicamentosService;
	@WireVariable
	private FacturacionService facturacionService;
	@WireVariable
	private Grupos_quirurgicosService grupos_quirurgicosService;
	@WireVariable
	private Tipo_procedimientoService tipo_procedimientoService;
	@WireVariable
	private Presentacion_articuloService presentacion_articuloService;
	@WireVariable
	private Modulo_firmasService modulo_firmasService;
	@WireVariable
	private Muestra_citologia_vaginalService muestra_citologia_vaginalService;
	@WireVariable
	private Antecedentes_personalesService antecedentes_personalesService;
	@WireVariable
	private Hisc_urgenciaService hisc_urgenciaService;
	@WireVariable
	private GeneralExtraService generalExtraService;

	private Window form;
	private Jasperreport report;

	private Map<String, Object> parameters;
	private Map paramRequest;

	private Resolucion resolucion;

	// private Usuarios usuarios;
	public void initPrintInformes() throws Exception {
		// HttpServletRequest request = (HttpServletRequest)
		// Executions.getCurrent().getNativeRequest();
		// String name_report = request.getParameter("name_report");
		try {
			paramRequest = Executions.getCurrent().getArg();
			String name_report = (String) paramRequest.get("name_report");

			Method method = this.getClass().getMethod("imprimir" + name_report,
					new Class[] {});
			method.invoke(this, new Object[] {});
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			ErrorMessage(e);
		}
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formPrintInformes");
		report = (Jasperreport) form.getFellow("report");
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		loadComponents();
		report.setSrc(null);

		// Obtenemos el logo y la resolucion de la ie //
		resolucion = new Resolucion();
		resolucion.setCodigo_empresa(getEmpresa().getCodigo_empresa());
		resolucion = resolucionService.consultar(resolucion);

		Elemento elemento = new Elemento();
		elemento.setCodigo(getEmpresa().getTipo_identificacion());
		elemento.setTipo("tipo_id");
		elemento = elementoService.consultar(elemento);
		String tipo_id = (elemento != null ? elemento.getDescripcion() : "");

		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo(getEmpresa().getCodigo_empresa());
		departamentos = departamentosService.consultar(departamentos);
		String dpto = (departamentos != null ? departamentos.getNombre() : "");

		Municipios municipios = new Municipios();
		municipios.setCoddep(getEmpresa().getCodigo_dpto());
		municipios.setCodigo(getEmpresa().getCodigo_municipio());
		municipios = municipiosService.consultar(municipios);
		String mun = (municipios != null ? municipios.getNombre() : "");

		String nombre_empresa = getEmpresa().getNombre_empresa();
		String id = tipo_id + "-" + getEmpresa().getNro_identificacion();
		String identificador = getEmpresa().getIdentificador();
		String gerente = getEmpresa().getGerente();
		String direccion = getEmpresa().getDireccion();
		String telefono = getEmpresa().getTelefonos();
		String codigo_empresa = getEmpresa().getCodigo_empresa();
		String codigo_sucursal = getSucursal().getCodigo_sucursal();

		parameters = new HashMap();
		parameters.put("empresa", nombre_empresa);
		parameters.put("gerente", gerente);
		parameters.put("id", id);
		parameters.put("identificador", identificador);
		parameters.put("direccion", direccion);
		parameters.put("telefono", telefono);
		parameters.put("dir_empresa", direccion);
		parameters.put("tel_empresa", telefono);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("resolucion",
				(resolucion != null ? resolucion.getResolucion() : ""));
		parameters.put("dpto", dpto + " - " + mun);
		parameters.put("codigo_habilitacion", getEmpresa()
				.getCodigo_habilitacion());
		parameters.put(JRParameter.REPORT_LOCALE, IConstantes.locale);
		parameters.put("email_sucursal",
				getSucursal().getEmail() != null ? getSucursal().getEmail()
						.toLowerCase() : "");

		try {
			if (resolucion != null) {
				if (resolucion.getLogo() != null) {
					InputStream imagen = new ByteArrayInputStream(
							resolucion.getLogo());
					parameters.put("imagen", imagen);
				}
			} else {
				InputStream imagen = new FileInputStream(
						request.getContextPath() + "/images/empresa.gif");
				parameters.put("imagen", imagen);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void imprimirAnexo9Remision() throws Exception {
		String codigo = (String) paramRequest.get("codigo");
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");

		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();
		parameters.put("codigo", codigo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo", codigo);
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		List<Map> data1 = reportService.getReport(map, "anexo9Remisiones");
		/* cargamos imagen del reporte */
		String SUBREPORT_DIR = context.getRealPath("/report/img/Anexo9.jpg");
		File file = new File(SUBREPORT_DIR);
		parameters.put("imagen_fondo", file);

		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setCodigo(codigo);

		anexo9_entidad = anexo9_entidadService.consultar(anexo9_entidad);

		if (anexo9_entidad != null && anexo9_entidad.getCodigo_medico() != null) {
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(anexo9_entidad.getCodigo_medico());

			usuarios = usuariosService.consultar(usuarios);

			if (usuarios != null) {
				parameters.put("nombre_med", usuarios.getNombres());
				parameters.put("apellido_med", usuarios.getApellidos());
				try {
					if (usuarios.getFirma() != null) {
						InputStream firma_medico = new ByteArrayInputStream(
								usuarios.getFirma());
						parameters.put("firma_medico", firma_medico);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

		}

		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(data1);
		report.setSrc("/report/anexo9.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	public void imprimirAnexo10Remision() throws Exception {
		Long codigo = (Long) paramRequest.get("codigo");
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");

		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();
		parameters.put("codigo", codigo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo", codigo);
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		List<Map> data1 = reportService.getReport(map, "anexo10CR");
		/* cargamos imagen del reporte */
		String SUBREPORT_DIR = context.getRealPath("/report/img/Anexo10.jpg");
		File file = new File(SUBREPORT_DIR);
		parameters.put("imagen", file);

		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(data1);
		report.setSrc("/report/Anexo10.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	public void imprimirAnexo2() throws Exception {
		String nro_documento = (String) paramRequest.get("nro_documento");
		String nro_ingreso = (String) paramRequest.get("nro_ingreso");
		String formato = (String) paramRequest.get("formato");

		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();
		parameters.put("nro_ingreso", nro_ingreso);
		parameters.put("nro_documento", nro_documento);

		List<Map> data = reportService.getReport(parameters, "anexo2");

		/* cargamos imagen del reporte */
		String SUBREPORT_DIR = context.getRealPath("/report/img/Anexo2.JPG");
		File file = new File(SUBREPORT_DIR);
		parameters.put("imagen", file);

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/anexo2.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirAnexo1() throws Exception {
		String codigo = (String) paramRequest.get("codigo");
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");

		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();
		parameters.put("codigo", codigo);

		Map map = new HashMap();
		map.put("codigo", codigo);
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		List<Map> data1 = reportService.getReport(map, "anexo1");
		/* cargamos imagen del reporte */
		String SUBREPORT_DIR = context.getRealPath("/report/img/Anexo1.jpg");
		File file = new File(SUBREPORT_DIR);
		parameters.put("imagen", file);

		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(data1);
		report.setSrc("/report/anexo1.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	public void imprimirAnexo3() throws Exception {
		String codigo = (String) paramRequest.get("codigo");
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();
		parameters.put("codigo", codigo);
		List<Map> data1 = reportService.getReport(parameters, "anexo3Report");

		for (Map map : data1) {
			int z_posProcedimientosOrden = 1;
			for (int i = 1; i <= 20; i++) {
				String codigo_cups = map.get("codigo_cups" + i) + "";
				String cantidad = map.get("cantidad" + i) + "";
				if (cantidad.equals("0")) {
					cantidad = "";
				}
				// log.info("cups: " + codigo_cups);
				if (codigo_cups != null) {
					if (!codigo_cups.trim().isEmpty()) {
						Procedimientos procediemientoCaja = new Procedimientos();
						procediemientoCaja.setCodigo_cups(codigo_cups);
						procediemientoCaja = procedimientosService
								.consultar(procediemientoCaja);
						String nombre = procediemientoCaja != null ? procediemientoCaja
								.getDescripcion() : "";
						map.put("codigo_cups" + (z_posProcedimientosOrden),
								codigo_cups);
						map.put("cups" + (z_posProcedimientosOrden), nombre);
						map.put("cantidad" + (z_posProcedimientosOrden++),
								cantidad);
					}
				}
			}
		}
		/* cargamos imagen del reporte */
		String SUBREPORT_DIR = context.getRealPath("/report/img/Anexo3.JPG");
		File file = new File(SUBREPORT_DIR);
		parameters.put("imagen", file);

		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(data1);
		report.setSrc("/report/Anexo3.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	public void imprimirNegacion() throws Exception {
		String codigo = (String) paramRequest.get("codigo");
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();
		parameters.put("codigo", codigo);
		List<Map> data1 = reportService.getReport(parameters,
				"negacionSQLReport");

		/* cargamos imagen del reporte */
		String SUBREPORT_DIR = context.getRealPath("/report/img/negacion.JPG");
		File file = new File(SUBREPORT_DIR);
		parameters.put("imagen", file);
		parameters.put("nombre_emp", "");

		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(data1);
		report.setSrc("/report/negacion.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	public void imprimirAnexo4() throws Exception {
		String codigo = (String) paramRequest.get("codigo");
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();
		parameters.put("codigo", codigo);
		List<Map> data1 = reportService.getReport(parameters, "anexo4Report");

		/* cargamos imagen del reporte */
		String SUBREPORT_DIR = context.getRealPath("/report/img/Anexo4.JPG");
		File file = new File(SUBREPORT_DIR);
		parameters.put("imagen", file);

		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(data1);
		report.setSrc("/report/Anexo4.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	public void imprimirRecordatorioCitas() throws Exception {
		String codigo = (String) paramRequest.get("codigo_cita");
		parameters.put("codigo_cita", codigo);
		List<Map> data1 = reportService.getReport(parameters,
				"recordatorioCitasModel");

		if (resolucion.getLogo() != null) {
			// byte[] imagen = Utilidades.getImagenAplicandoFade(
			// new ByteArrayInputStream(resolucion.getLogo()), 1f);
			// log.info(":: Logo: " + resolucion.getLogo());
			// log.info(":: imagen: " + imagen);
			parameters.put("imagen",
					new ByteArrayInputStream(resolucion.getLogo()));
		}
		parameters.put("nombre_centro",
				centro_atencion_session.getNombre_centro());
		parameters.put("direccion", centro_atencion_session.getDireccion());
		parameters.put("telefono", centro_atencion_session.getTelefonos());

		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(data1);
		report.setSrc("/report/recordatorio_citas_report.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	public void imprimirAutorizacion() throws Exception {
		parameters.putAll(paramRequest);

		String reporte = (String) paramRequest.get("reporte");
		List<Map> data1 = reportService.getReport(parameters, "anexo4Report");

		// log.info("parameters: " + parameters);
		if (validarContenido(data1, "No se han encontraron registros")) {
			for (Map map : data1) {
				byte[] firma = (byte[]) map.get("firma");
				if (firma != null) {
					map.put("firma", new ByteArrayInputStream(firma));
				}
			}
			DataSourceReport dataSource1 = new DataSourceReport();
			dataSource1.loadReport(data1);
			report.setSrc(reporte);
			report.setParameters(parameters);
			report.setDatasource(dataSource1);
			report.setType("pdf");
		}
	}

	public void imprimirAfiliadosSinCotizaciones() throws Exception {
		parameters.putAll(paramRequest);
		List<Map> data1 = reportService.getReport(parameters,
				"afiliadosSinAportesReport");
		if (validarContenido(data1, "No se han encontraron registros")) {
			DataSourceReport dataSource1 = new DataSourceReport();
			dataSource1.loadReport(data1);
			report.setSrc("/report/AfiliadosSinAportes.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource1);
			report.setType("pdf");
		}
	}

	public void imprimirAutorizacionesSimples() throws Exception {
		String codigo = (String) paramRequest.get("codigo");
		// HttpServletRequest request = (HttpServletRequest) Executions
		// .getCurrent().getNativeRequest();
		// ServletContext context = request.getSession().getServletContext();
		parameters.put("codigo", codigo);
		List<Map> data1 = reportService.getReport(parameters, "anexo4Report");

		List<Map> maps = new ArrayList<Map>();

		double valor_total = 0d;

		for (Map map : data1) {

			String nro_autorizacion = (String) map.get("numero_autorizacion");
			String prestador = (String) map.get("nombre_empresa");

			/* datos del paciente */
			String tipo_id = (String) map.get("tipo_identificacion");
			String id = (String) map.get("nro_identificacion");
			String nombre1 = (String) map.get("nombre1");
			String nombre2 = (String) map.get("nombre2");
			String apellido1 = (String) map.get("apellido1");
			String apellido2 = (String) map.get("apellido2");

			String tipo_afiliado = "*";
			String telefono_prestador = (String) map.get("telefonos");
			String direccion_prestador = (String) map.get("direccion_empresa");
			String estado = (String) map.get("estado");

			String nombre_medico = (String) map.get("nombre_reporta");

			String diagnostico = (String) map.get("cie_p");
			Cie cie = new Cie();
			cie.setCodigo(diagnostico);
			cie = generalExtraService.consultar(cie);

			String diagnostico_cie = "*";
			if (cie != null) {
				diagnostico_cie = cie.getCodigo() + " " + cie.getNombre();
			}

			Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
			afiliaciones_me.setCodigo_empresa(codigo_empresa);
			afiliaciones_me.setCodigo_sucursal(codigo_sucursal);
			afiliaciones_me.setNro_identificacion_afiliado(id);
			afiliaciones_me = afiliaciones_meService.consultar(afiliaciones_me);

			if (afiliaciones_me != null) {
				tipo_afiliado = afiliaciones_me.getTipo_afiliado().equals("C") ? "COTIZANTE"
						: (afiliaciones_me.getTipo_afiliado().equals("B") ? "BENEFICIARIO"
								: "ADICIONAL");
			}

			String afiliado = tipo_id + " " + id + " " + apellido1 + " "
					+ apellido2 + " " + nombre1 + " " + nombre2;
			String fecha = (String) map.get("fecha_simple");

			/* datos de paciente y prestador */
			parameters.put("prestador", prestador.toUpperCase());
			parameters.put("nro_autorizacion", nro_autorizacion);
			parameters.put("fecha", fecha);
			parameters.put("afiliado", afiliado.toUpperCase());
			parameters.put("tipo_afiliado", tipo_afiliado);
			parameters.put("direccion_prestador",
					direccion_prestador.toUpperCase());
			parameters.put("telefono_prestador", telefono_prestador);
			parameters.put("diagnostico", diagnostico_cie.toUpperCase());
			parameters.put("estado", estado);
			parameters.put("nombre_medico", nombre_medico);

			// log.info("Nombre: " + nombre_medico);
			for (int i = 1; i <= 20; i++) {
				String codigo_cups = map.get("codigo_cups" + i) + "";
				String cantidad = map.get("cantidad" + i) + "";
				String nombre = map.get("cups" + i) + "";
				String valor = map.get("valor" + i) + "";

				if (!cantidad.trim().isEmpty()) {
					Map<String, Object> map_detalle = new HashMap<String, Object>();

					/* datos del detalle */
					map_detalle.put("codigo", codigo_cups);
					map_detalle.put("descripcion", nombre);
					map_detalle.put("cantidad", cantidad);
					map_detalle.put("valor_uni", valor);

					/* verificamos valores del detalle */
					if (valor.matches("[0-9.]*$")
							&& cantidad.matches("[0-9.]*$")) {
						double valor_temp = Double.parseDouble(valor);
						double cantidad_temp = Double.parseDouble(cantidad);
						double subtotal = (cantidad_temp * valor_temp);
						valor_total += subtotal;
						map_detalle.put("total", "" + subtotal);
						// log.info("Total del detalle: " + subtotal);
					} else {
						map_detalle.put("total", valor);
					}
					maps.add(map_detalle);
				}
			}
			parameters.put("valor_total", "" + valor_total);
			break; // solo es permitido para uno
		}

		/* cargamos imagen del reporte */
		// String SUBREPORT_DIR = context.getRealPath("/report/img/Anexo4.JPG");
		// File file = new File(SUBREPORT_DIR);
		// parameters.put("imagen", file);
		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(maps);
		report.setSrc("/report/autorizaciones_simple.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	public void imprimirHistoria_clinica_uci() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);

		List<Map> data1 = reportService.getReport(parameters,
				"historia_clinica_uci1Model");
		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport(data1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_clinica_uci2Model");
		DataSourceReport dataSource2 = new DataSourceReport();
		dataSource2.loadReport(data2);

		parameters.put("dataSource1", dataSource1);
		parameters.put("dataSource2", dataSource2);
		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/Historia_uci.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);
	}

	public void imprimirEvolucion_medica() throws Exception {
		String codigo_evolucion = (String) paramRequest.get("codigo_evolucion");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_evolucion", codigo_evolucion);

		List<Map> data = reportService.getReport(parameters,
				"evolucion_medicaModel");
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Evolucion_medica.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirCarnets() throws Exception {
		// String filtro = (String) paramRequest.get("filtro");
		String formato = (String) paramRequest.get("formato");
		boolean print_back = (Boolean) paramRequest.get("prin_back");

		Config_carnets config_carnets = new Config_carnets();
		config_carnets.setCodigo_empresa(getEmpresa().getCodigo_empresa());
		config_carnets.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
		config_carnets.setTipo("01");
		config_carnets = config_carnetsService.consultar(config_carnets);

		parameters.put("filtro", paramRequest.get("filtro"));
		parameters.put("nombre_empresa", ""
				+ getEmpresa().getNombre_empresa().toUpperCase());
		parameters.put("resolucion",
				resolucion != null ? resolucion.getResolucion() : "");
		parameters.put("nit", getEmpresa().getTipo_identificacion() + " "
				+ getEmpresa().getNro_identificacion());
		if (config_carnets != null) {
			// fondo = config_carnets.getFondo() != null ? new
			// ByteArrayInputStream(config_carnets.getFondo()) : null;
			// logo = config_carnets.getLogo() != null ? new
			// ByteArrayInputStream(config_carnets.getLogo()) : null;
			parameters
					.put("imagen_fondo",
							config_carnets.getFondo() != null ? new ByteArrayInputStream(
									config_carnets.getFondo()) : null);
			parameters
					.put("imagen_logo",
							config_carnets.getLogo() != null ? new ByteArrayInputStream(
									config_carnets.getLogo()) : null);
			parameters
					.put("imgen_fondo",
							config_carnets.getImagen_atras() != null ? new ByteArrayInputStream(
									config_carnets.getImagen_atras()) : null);
			// logo = fondo;
		}

		List<Map> data = reportService.getReport(parameters, "carnetsModel");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		// report.setSrc("/report/carnets.jasper");
		report.setSrc(print_back ? "/report/carnets_atras.jasper"
				: "/report/carnets_no.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirOrden_servicio() throws Exception {
		Long id = (Long) paramRequest.get("id");
		String formato = (String) paramRequest.get("formato");

		// parameters.put("id", id);
		List<Map> data = reportService.getReport(paramRequest,
				"orden_servicioModel");
		for (int i = 0; i < data.size(); i++) {
			Map map = data.get(i);

			String nro_identificacion = (String) map.get("nro_identificacion");
			String nro_ingreso = (String) map.get("nro_ingreso");

			Admision admision = new Admision();
			admision.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			admision.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			admision.setNro_ingreso(nro_ingreso);
			admision.setNro_identificacion(nro_identificacion);
			admision = admisionService.consultar(admision);

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			contratos.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			contratos.setCodigo_administradora((String) map
					.get("codigo_administradora"));
			contratos.setId_plan((String) map.get("id_plan"));
			contratos = contratosService.consultar(contratos);

			String nombre_procedimiento = "";
			String codigo_procedimiento_aux = (String) map
					.get("codigo_procedimiento");

			Procedimientos proc = new Procedimientos();
			proc.setId_procedimiento(new Long((String) map
					.get("codigo_procedimiento")));
			// proc.setCodigo_cups((String)
			// map.get("codigo_procedimiento"));
			proc = procedimientosService.consultar(proc);
			nombre_procedimiento = (proc != null ? proc.getDescripcion() : "");
			if (proc != null) {
				codigo_procedimiento_aux = proc.getCodigo_cups();
			}

			map.put("nombre_procedimiento", nombre_procedimiento);
			map.put("codigo_procedimiento", codigo_procedimiento_aux);
			data.set(i, map);
		}

		Orden_servicio orden_servicio = new Orden_servicio();
		orden_servicio.setId(id);

		orden_servicio = orden_servicioService.consultar(orden_servicio);

		if (orden_servicio != null
				&& orden_servicio.getCodigo_ordenador() != null) {
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(orden_servicio.getCodigo_ordenador());

			usuarios = usuariosService.consultar(usuarios);

			if (usuarios != null) {
				parameters.put("nombre_med", usuarios.getNombres());
				parameters.put("apellido_med", usuarios.getApellidos());
				parameters
						.put("registro_medico", usuarios.getRegistro_medico());
				try {
					if (usuarios.getFirma() != null) {
						InputStream firma_medico = new ByteArrayInputStream(
								usuarios.getFirma());
						parameters.put("firma_medico", firma_medico);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Orden_servicio.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirNota_enfermeria() throws Exception {
		String codigo_nota = (String) paramRequest.get("codigo_nota");
		String tipo = (String) paramRequest.get("tipo");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_nota", codigo_nota);
		parameters.put("tipo", tipo);

		List<Map> data = reportService.getReport(parameters,
				"nota_enfermeriaModel");
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Nota_enfermeria.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirNotas_enfermeria() throws Exception {
		String nro_ingreso = (String) paramRequest.get("nro_ingreso");
		String nro_documento = (String) paramRequest.get("nro_documento");
		String codigo_centro = (String) paramRequest.get("codigo_centro");
		String codigo_nota_in = (String) paramRequest.get("codigo_nota_in");
		String formato = (String) paramRequest.get("formato");

		parameters.put("nro_documento", nro_documento);
		parameters.put("nro_ingreso", nro_ingreso);
		parameters.put("codigo_centro", codigo_centro);
		parameters.put("codigo_nota_in", codigo_nota_in);

		List<Map> data = reportService.getReport(parameters,
				"notas_enfermeriaModel");
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/notas_enfermeria.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirRegistro_medicamentos() throws Exception {
		String nro_ingreso = (String) paramRequest.get("nro_ingreso");
		String nro_documento = (String) paramRequest.get("nro_documento");
		String formato = (String) paramRequest.get("formato");

		parameters.put("nro_documento", nro_documento);
		parameters.put("nro_ingreso", nro_ingreso);

		List<Map> data = reportService.getReport(parameters,
				"registro_medicamentosModel");

		Map<String, Articulo> mapa_articulos = new HashMap<String, Articulo>();

		for (int i = 0; i < data.size(); i++) {
			Map map = data.get(i);
			String codigo_empresa = (String) map.get("codigo_empresa");
			String codigo_sucursal = (String) map.get("codigo_sucursal");
			String codigo_articulo = (String) map.get("codigo_articulo");
			if (mapa_articulos.containsKey(codigo_empresa + "_"
					+ codigo_sucursal + "_" + codigo_articulo)) {
				map.put("nombre_medicamento",
						mapa_articulos
								.get(codigo_empresa + "_" + codigo_sucursal
										+ "_" + codigo_articulo).getNombre1()
								.trim());
			} else {
				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(codigo_empresa);
				articulo.setCodigo_sucursal(codigo_sucursal);
				articulo.setCodigo_articulo(codigo_articulo);
				articulo = articuloService.consultar(articulo);
				if (articulo != null) {
					mapa_articulos.put(codigo_empresa + "_" + codigo_sucursal
							+ "_" + codigo_articulo, articulo);
				}
				map.put("nombre_medicamento", articulo != null ? articulo
						.getNombre1().trim() : "");
			}
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/registro_medicamentos.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

		if (data.isEmpty()) {
			this.setAttribute("LISTADO_REGISTROS_VACIO",
					"LISTADO_REGISTROS_VACIO");
		}

	}

	public void imprimirHoja_gastos() throws Exception {
		String nro_ingreso = (String) paramRequest.get("nro_ingreso");
		String nro_identificacion = (String) paramRequest
				.get("nro_identificacion");
		String codigo_administradora = (String) paramRequest
				.get("codigo_administradora");

		parameters.put("nro_ingreso", nro_ingreso);
		parameters.put("nro_identificacion", nro_identificacion);
		parameters.put("codigo_administradora", codigo_administradora);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);

		List<Map<String, Object>> listado_medicamentos = (List<Map<String, Object>>) datos_medicamentosService
				.medicamentos_hoja_gasto(parameters);

		List<Map<String, Object>> listado_servicios = (List<Map<String, Object>>) datos_servicioService
				.servicios_hoja_gastos(parameters);

		List<Map<String, Object>> listado_datos = new ArrayList<Map<String, Object>>();

		int contador = 0;

		while (contador < listado_medicamentos.size()
				|| contador < listado_servicios.size()) {
			Map<String, Object> datos_registro = new HashMap<String, Object>();
			if (contador < listado_medicamentos.size()) {
				Map<String, Object> mapa_medicamentos = listado_medicamentos
						.get(contador);
				String codigo_medicamento = (String) mapa_medicamentos
						.get("codigo_medicamento");
				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(codigo_empresa);
				articulo.setCodigo_sucursal(codigo_sucursal);
				articulo.setCodigo_articulo(codigo_medicamento);
				articulo = articuloService.consultar(articulo);

				String nom_articulo = (articulo != null ? articulo.getNombre1()
						: "").trim();

				datos_registro.put("codigo_empresa", codigo_empresa);
				datos_registro.put("codigo_sucursal", codigo_sucursal);
				datos_registro.put("codigo_administradora",
						codigo_administradora);
				datos_registro.put("nro_ingreso", nro_ingreso);
				datos_registro.put("nro_identificacion", nro_identificacion);
				datos_registro.put("cantidad_medicamento",
						mapa_medicamentos.get("unidades"));
				datos_registro.put("nombre_medicamento", nom_articulo);
				datos_registro.put("tipo", mapa_medicamentos.get("tipo"));
				datos_registro.put("nombre1_pct",
						mapa_medicamentos.get("nombre1_pct"));
				datos_registro.put("nombre2_pct",
						mapa_medicamentos.get("nombre2_pct"));
				datos_registro.put("apellido1_pct",
						mapa_medicamentos.get("apellido1_pct"));
				datos_registro.put("apellido2_pct",
						mapa_medicamentos.get("apellido2_pct"));
				datos_registro.put("fecha_atencion",
						mapa_medicamentos.get("fecha_atencion"));
				datos_registro.put("fecha_ingreso",
						mapa_medicamentos.get("fecha_ingreso"));
				datos_registro.put("diagnostico_ingreso",
						mapa_medicamentos.get("diagnostico_ingreso"));
				datos_registro.put("nombre_diagnostico",
						mapa_medicamentos.get("nombre_diagnostico"));
				datos_registro.put("codigo_medico",
						mapa_medicamentos.get("codigo_medico"));
				datos_registro.put("nombres_medico",
						mapa_medicamentos.get("nombres_medico"));
				datos_registro.put("apellidos_medico",
						mapa_medicamentos.get("apellidos_medico"));
				datos_registro.put("urgencias",
						mapa_medicamentos.get("urgencias"));
				datos_registro.put("hospitalizacion",
						mapa_medicamentos.get("hospitalizacion"));
				datos_registro.put("admision_parto",
						mapa_medicamentos.get("admision_parto"));
				datos_registro.put("codigo_administradora",
						mapa_medicamentos.get("codigo_administradora"));
				datos_registro.put("nombre_administradora",
						mapa_medicamentos.get("nombre_administradora"));
				datos_registro.put("nombre_centro",
						mapa_medicamentos.get("nombre_centro"));
				datos_registro.put("codigo_historia",
						mapa_medicamentos.get("codigo_historia"));
			} else {
				datos_registro.put("cantidad_medicamento", "");
				datos_registro.put("nombre_medicamento", "");
			}
			if (contador < listado_servicios.size()) {
				Map<String, Object> mapa_medicamentos = listado_servicios
						.get(contador);

				String codigo_servicio = (String) mapa_medicamentos
						.get("codigo_servicio");

				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(codigo_empresa);
				articulo.setCodigo_sucursal(codigo_sucursal);
				articulo.setCodigo_articulo(codigo_servicio);
				articulo = articuloService.consultar(articulo);

				String nom_articulo = (articulo != null ? articulo.getNombre1()
						: "").trim();

				datos_registro.put("codigo_empresa", codigo_empresa);
				datos_registro.put("codigo_sucursal", codigo_sucursal);
				datos_registro.put("codigo_administradora",
						codigo_administradora);
				datos_registro.put("nro_ingreso", nro_ingreso);
				datos_registro.put("nro_identificacion", nro_identificacion);
				datos_registro.put("cantidad_dispositivo",
						mapa_medicamentos.get("unidades"));
				datos_registro.put("nombre_dispositivo", nom_articulo);
				datos_registro.put("tipo", mapa_medicamentos.get("tipo"));
				datos_registro.put("nombre1_pct",
						mapa_medicamentos.get("nombre1_pct"));
				datos_registro.put("nombre2_pct",
						mapa_medicamentos.get("nombre2_pct"));
				datos_registro.put("apellido1_pct",
						mapa_medicamentos.get("apellido1_pct"));
				datos_registro.put("apellido2_pct",
						mapa_medicamentos.get("apellido2_pct"));
				datos_registro.put("fecha_atencion",
						mapa_medicamentos.get("fecha_atencion"));
				datos_registro.put("fecha_ingreso",
						mapa_medicamentos.get("fecha_ingreso"));
				datos_registro.put("diagnostico_ingreso",
						mapa_medicamentos.get("diagnostico_ingreso"));
				datos_registro.put("nombre_diagnostico",
						mapa_medicamentos.get("nombre_diagnostico"));
				datos_registro.put("codigo_medico",
						mapa_medicamentos.get("codigo_medico"));
				datos_registro.put("nombres_medico",
						mapa_medicamentos.get("nombres_medico"));
				datos_registro.put("apellidos_medico",
						mapa_medicamentos.get("apellidos_medico"));
				datos_registro.put("urgencias",
						mapa_medicamentos.get("urgencias"));
				datos_registro.put("hospitalizacion",
						mapa_medicamentos.get("hospitalizacion"));
				datos_registro.put("admision_parto",
						mapa_medicamentos.get("admision_parto"));
				datos_registro.put("codigo_administradora",
						mapa_medicamentos.get("codigo_administradora"));
				datos_registro.put("nombre_administradora",
						mapa_medicamentos.get("nombre_administradora"));
				datos_registro.put("nombre_centro",
						mapa_medicamentos.get("nombre_centro"));
				datos_registro.put("codigo_historia",
						mapa_medicamentos.get("codigo_historia"));
			} else {
				datos_registro.put("cantidad_dispositivo", "");
				datos_registro.put("nombre_dispositivo", "");
			}
			listado_datos.add(datos_registro);
			contador++;
		}

		if (!listado_datos.isEmpty()) {
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(listado_datos);
			report.setSrc("/report/Hoja_de_gasto.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");
		}

	}

	public void imprimirReceta_rips2() throws Exception {
		parameters.remove("firma_medico");
		String codigo_receta = (String) paramRequest.get("codigo_receta");
		String formato = (String) paramRequest.get("formato");
		String usuario_imprimio = (String) paramRequest.get("usuario_imprimio");

		parameters.put("codigo_receta", codigo_receta);
		parameters.put("usuario_imprimio", usuario_imprimio);

		List<Map> data = reportService
				.getReport(
						parameters,
						parametros_empresa.getTipo_reporte_receta()
								.equals("01") ? "receta_ripsModel"
								: parametros_empresa.getTipo_reporte_receta()
										.equals("2") ? "prescripcionMedicaModel"
										: "receta_rips2Model");

		String edad_paciente = null;
		String codigo_medico = null;

		for (int i = 0; i < data.size(); i++) {
			Map map = data.get(i);
			Timestamp fecha_nacimiento = (Timestamp) map
					.get("fecha_nacimiento");
			if (edad_paciente == null) {
				Map<String, Integer> mapa_edades = Util
						.getEdadYYYYMMDD(fecha_nacimiento);

				Integer anios = mapa_edades.get("anios");
				Integer meses = mapa_edades.get("meses");
				Integer dias = mapa_edades.get("dias");

				if (anios.intValue() == 0 && meses.intValue() == 0) {
					edad_paciente = dias + (dias == 1 ? " día" : " días");
				} else if (anios.intValue() == 0) {
					edad_paciente = meses
							+ (meses == 1 ? " mes (" : " meses (")
							+ (dias + (dias == 1 ? " día" : " días")) + ")";
				} else {
					int current_meses = meses.intValue()
							- (anios.intValue() * 12);
					edad_paciente = anios
							+ (anios == 1 ? " año" : " años")
							+ (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ? " mes "
									: " meses"))
									: "");
				}
			}

			map.put("edad_paciente", edad_paciente);

			if (codigo_medico == null) {
				codigo_medico = (String) map.get("codigo_medico");
			}

			Articulo art = new Articulo();
			art.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			art.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			art.setCodigo_articulo((String) map.get("codigo_articulo"));
			art = articuloService.consultar(art);

			String articulo = (art != null ? art.getNombre1() : "").trim();

			if (parametros_empresa.getTipo_reporte_receta().equals("02")) {
				BigDecimal cantidad_recetada = (BigDecimal) map
						.get("cantidad_recetada");
				if (cantidad_recetada != null) {
					String cantidad_palabras_string = Num_letter
							.convertirLetras(cantidad_recetada.intValue());
					DecimalFormat decimalFormat = new DecimalFormat("#.##");
					String cantidad_string = decimalFormat
							.format(cantidad_recetada);
					map.put("cantidad", cantidad_palabras_string + "("
							+ cantidad_string + ")");
				}

				byte[] firma_medico = (byte[]) map.get("firma_medico");
				if (firma_medico != null) {
					map.put("firma_medico", new ByteArrayInputStream(
							firma_medico));
				}
				map.put("nombre_medicamento", articulo);
				map.put("forma", art != null ? art.getUnidad_concentracion()
						: "");
				map.put("via_administracion", art != null ? art.getVia() : "");
				map.put("via_administracion", art != null ? art.getVia() : "");
			} else {
				map.put("articulo", articulo);
			}
		}

		if (codigo_medico != null) {
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(codigo_medico);

			usuarios = usuariosService.consultar(usuarios);

			if (usuarios != null) {
				try {
					if (usuarios.getFirma() != null) {
						InputStream firma_medico = new ByteArrayInputStream(
								usuarios.getFirma());
						parameters.put("firma_medico", firma_medico);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/"
				+ (parametros_empresa.getTipo_reporte_receta().equals("01") ? "Receta_rips.jasper"
						: parametros_empresa.getTipo_reporte_receta().equals(
								"02") ? "prescripcion_medica.jasper"
								: "Receta_rips2.jasper"));
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	/*
	 * public void imprimirReceta_rips() throws Exception {
	 * parameters.remove("firma_medico"); String codigo_receta = (String)
	 * paramRequest.get("codigo_receta"); String formato = (String)
	 * paramRequest.get("formato"); String usuario_imprimio = (String)
	 * paramRequest.get("usuario_imprimio");
	 * 
	 * parameters.put("codigo_receta", codigo_receta);
	 * parameters.put("usuario_imprimio", usuario_imprimio);
	 * 
	 * List<Map> data = reportService .getReport( parameters,
	 * parametros_empresa.getTipo_reporte_receta() .equals("01") ?
	 * "receta_ripsModel" : "prescripcionMedicaModel");
	 * 
	 * String edad_paciente = null; String codigo_medico = null;
	 * 
	 * for (int i = 0; i < data.size(); i++) { Map map = data.get(i); Timestamp
	 * fecha_nacimiento = (Timestamp) map .get("fecha_nacimiento"); if
	 * (edad_paciente == null) { Map<String, Integer> mapa_edades = Util
	 * .getEdadYYYYMMDD(fecha_nacimiento);
	 * 
	 * Integer anios = mapa_edades.get("anios"); Integer meses =
	 * mapa_edades.get("meses"); Integer dias = mapa_edades.get("dias");
	 * 
	 * if (anios.intValue() == 0 && meses.intValue() == 0) { edad_paciente =
	 * dias + (dias == 1 ? " día" : " días"); } else if (anios.intValue() == 0)
	 * { edad_paciente = meses + (meses == 1 ? " mes (" : " meses (") + (dias +
	 * (dias == 1 ? " día" : " días")) + ")"; } else { int current_meses =
	 * meses.intValue() - (anios.intValue() * 12); edad_paciente = anios +
	 * (anios == 1 ? " año" : " años") + (current_meses != 0 ? (" y " +
	 * current_meses + (current_meses == 1 ? " mes " : " meses")) : ""); } }
	 * 
	 * map.put("edad_paciente", edad_paciente);
	 * 
	 * if (codigo_medico == null) codigo_medico = (String)
	 * map.get("codigo_medico");
	 * 
	 * Articulo art = new Articulo();
	 * art.setCodigo_empresa(getEmpresa().getCodigo_empresa());
	 * art.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
	 * art.setCodigo_articulo((String) map.get("codigo_articulo")); art =
	 * articuloService.consultar(art);
	 * 
	 * String articulo = (art != null ? art.getNombre1() : "").trim();
	 * 
	 * if (parametros_empresa.getTipo_reporte_receta().equals("02")) {
	 * BigDecimal cantidad_recetada = (BigDecimal) map
	 * .get("cantidad_recetada"); if (cantidad_recetada != null) { String
	 * cantidad_palabras_string = Num_letter
	 * .convertirLetras(cantidad_recetada.intValue()); DecimalFormat
	 * decimalFormat = new DecimalFormat("#.##"); String cantidad_string =
	 * decimalFormat .format(cantidad_recetada); map.put("cantidad",
	 * cantidad_palabras_string + "(" + cantidad_string + ")"); }
	 * 
	 * byte[] firma_medico = (byte[]) map.get("firma_medico"); if (firma_medico
	 * != null) map.put("firma_medico", new ByteArrayInputStream(
	 * firma_medico)); map.put("nombre_medicamento", articulo); map.put("forma",
	 * art != null ? art.getUnidad_concentracion() : "");
	 * map.put("via_administracion", art != null ? art.getVia() : "");
	 * map.put("via_administracion", art != null ? art.getVia() : ""); } else {
	 * map.put("articulo", articulo); } }
	 * 
	 * if (codigo_medico != null) { Usuarios usuarios = new Usuarios();
	 * usuarios.setCodigo_empresa(codigo_empresa);
	 * usuarios.setCodigo_sucursal(codigo_sucursal);
	 * usuarios.setCodigo(codigo_medico);
	 * 
	 * usuarios = usuariosService.consultar( usuarios);
	 * 
	 * if (usuarios != null) { try { if (usuarios.getFirma() != null) {
	 * InputStream firma_medico = new ByteArrayInputStream(
	 * usuarios.getFirma()); parameters.put("firma_medico", firma_medico); } }
	 * catch (Exception e) { log.error(e.getMessage(), e); } }
	 * 
	 * }
	 * 
	 * DataSourceReport dataSource = new DataSourceReport();
	 * dataSource.loadReport(data);
	 * 
	 * report.setSrc("/report/" +
	 * (parametros_empresa.getTipo_reporte_receta().equals("01") ?
	 * "Receta_rips.jasper" : "prescripcion_medica.jasper"));
	 * report.setParameters(parameters); report.setDatasource(dataSource);
	 * report.setType(formato);
	 * 
	 * }
	 */
	public void imprimirConsulta_externa_hc() throws Exception {
		String codigo = (String) paramRequest.get("codigo");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo", codigo);

		List<Map> data = reportService.getReport(parameters,
				"consulta_externa_hcModel");
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Consulta_externa.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirAgendaCitas() throws Exception {
		Date fecha_cita = (Date) paramRequest.get("fecha_cita");
		String formato = (String) paramRequest.get("formato");
		String codigo_prestador = (String) paramRequest.get("codigo_prestador");
		String codigo_centro = (String) paramRequest.get("codigo_centro");
		String nombre_tipo_consulta = (String) paramRequest
				.get("nombre_tipo_consulta");
		String prestador = (String) paramRequest.get("prestador");

		List<Elemento> listado_vias = elementoService.listar("via_ingreso");
		Map<String, String> vias = new HashMap<String, String>();
		for (Elemento elemento : listado_vias) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}

		parameters.put("fecha_cita", fecha_cita);
		parameters.put("nombre_tipo_consulta", nombre_tipo_consulta);
		parameters.put("prestador", prestador);
		parameters.put("codigo_prestador_unico", codigo_prestador);
		parameters.put("codigo_centro", codigo_centro);

		List<Citas> lista_datos = citasService.listar(parameters);
		// log.info("lista_datos>>>>>>>>" + lista_datos);

		List<Map> data = new LinkedList<Map>();
		for (Citas citas : lista_datos) {

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(citas.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);
			Elemento elementoEstado = citas.getElementoEstado();

			Admision admision = new Admision();
			admision.setCodigo_empresa(citas.getCodigo_empresa());
			admision.setCodigo_sucursal(citas.getCodigo_sucursal());
			admision.setNro_identificacion(citas.getNro_identificacion());
			admision.setCodigo_cita(citas.getCodigo_cita());
			admision = admisionService.consultar(admision);

			String estado = citas.getEstado();
			String estado_admision = (admision != null ? admision.getEstado()
					: "");
			String estado_cita = (elementoEstado != null ? elementoEstado
					.getDescripcion() : "");
			if (estado_admision.equals("1")) {
				estado_cita = "Sala de espera";
				estado = "6";
			}
			if (estado.equals("1")) {
				estado_cita = "Por cumplir";
			} else if (estado.equals("4")) {
				estado_cita = "Por cumplir";
			} else if (estado.equals("5")) {
				estado_cita = "Cancelada";
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", citas.getCodigo_empresa());
			map.put("codigo_sucursal", citas.getCodigo_sucursal());
			map.put("codigo_cita", citas.getCodigo_cita());
			map.put("nro_identificacion", citas.getNro_identificacion());
			map.put("codigo_prestador", citas.getCodigo_prestador());
			map.put("fecha_cita", citas.getFecha_cita());
			map.put("hora", citas.getHora());
			map.put("estado", estado);
			map.put("tipo_consulta", citas.getTipo_consulta());

			map.put("motivo_consulta_hc", vias.get(citas.getTipo_consulta()));
			map.put("estado_cita", estado_cita);
			map.put("apellido1", (paciente != null ? paciente.getApellido1()
					: ""));
			map.put("apellido2", (paciente != null ? paciente.getApellido2()
					: ""));
			map.put("nombre1", (paciente != null ? paciente.getNombre1() : ""));

			data.add(map);
		}
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/AgendaCitas.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirAgendaCitasPrestador() throws Exception {
		Date fecha_cita = (Date) paramRequest.get("fecha_cita");
		String formato = (String) paramRequest.get("formato");
		String codigo_prestador = (String) paramRequest.get("codigo_prestador");
		String codigo_centro = (String) paramRequest.get("codigo_centro");
		String nombre_tipo_consulta = (String) paramRequest
				.get("nombre_tipo_consulta");
		String prestador = (String) paramRequest.get("prestador");

		List<Elemento> listado_vias = elementoService.listar("via_ingreso");
		Map<String, String> vias = new HashMap<String, String>();
		for (Elemento elemento : listado_vias) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}

		parameters.put("fecha_cita", fecha_cita);
		parameters.put("nombre_tipo_consulta", nombre_tipo_consulta);
		parameters.put("prestador", prestador);
		parameters.put("codigo_prestador_unico", codigo_prestador);
		parameters.put("codigo_centro", codigo_centro);

		List<Citas> lista_datos = citasService.listar(parameters);

		// log.info("lista_datos>>>>>>>>" + lista_datos);
		List<Map> data = new LinkedList<Map>();
		for (Citas citas : lista_datos) {

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(citas.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);
			Elemento elementoEstado = citas.getElementoEstado();

			Admision admision = new Admision();
			admision.setCodigo_empresa(citas.getCodigo_empresa());
			admision.setCodigo_sucursal(citas.getCodigo_sucursal());
			admision.setNro_identificacion(citas.getNro_identificacion());
			admision.setCodigo_cita(citas.getCodigo_cita());
			admision = admisionService.consultar(admision);

			String estado = citas.getEstado();
			String estado_admision = (admision != null ? admision.getEstado()
					: "");
			String estado_cita = (elementoEstado != null ? elementoEstado
					.getDescripcion() : "");
			if (estado_admision.equals("1")) {
				estado_cita = "Sala de espera";
				estado = "6";
			}
			if (estado.equals("1")) {
				estado_cita = "Por cumplir";
			} else if (estado.equals("4")) {
				estado_cita = "Por cumplir";
			} else if (estado.equals("5")) {
				estado_cita = "Cancelada";
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", citas.getCodigo_empresa());
			map.put("codigo_sucursal", citas.getCodigo_sucursal());
			map.put("codigo_cita", citas.getCodigo_cita());
			map.put("nro_identificacion", citas.getNro_identificacion());
			map.put("codigo_prestador", citas.getCodigo_prestador());
			map.put("fecha_cita", citas.getFecha_cita());
			map.put("hora", citas.getHora());
			map.put("estado", estado);
			map.put("tipo_consulta", citas.getTipo_consulta());

			map.put("motivo_consulta_hc", vias.get(citas.getTipo_consulta()));
			map.put("estado_cita", estado_cita);
			map.put("apellido1", (paciente != null ? paciente.getApellido1()
					: ""));
			map.put("apellido2", (paciente != null ? paciente.getApellido2()
					: ""));
			map.put("nombre1", (paciente != null ? paciente.getNombre1() : ""));

			data.add(map);
		}
		DataSourceReport dataSource = new DataSourceReport();
		// log.info("Data source>>>>>>>>" + dataSource);
		dataSource.loadReport(data);

		report.setSrc("/report/AgendaCitas.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirRecibo_caja_particular() throws Exception {
		Long id_recibo = (Long) paramRequest.get("id_recibo");
		String formato = (String) paramRequest.get("formato");
		String codigo_usuario = "";

		parameters.put("id_recibo", id_recibo);

		List<Map> data = reportService
				.getReport(parameters, "recibo_cajaModel");
		
		for (Map map : data) {
			String creacion_user = (String)map.get("creacion_user");
			codigo_usuario = creacion_user;
		}
		
		Usuarios user = new Usuarios();
		user.setCodigo(codigo_usuario);
		user.setCodigo_empresa(codigo_empresa);
		user.setCodigo_sucursal(codigo_sucursal);
		user = usuariosService.consultar(user);

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		parameters.put("usuario_creador", user != null ? (user.getCodigo()
				+ " " + user.getNombres() + " " + user.getApellidos())
				: codigo_usuario);
		parameters.put("nombre_reporte", "RECIBO DE CAJA PARA PACIENTES PARTICULARES");

		report.setSrc("/report/recibo_caja_particular.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirNota_contable() throws Exception {
		String codigo_documento = (String) paramRequest.get("codigo_documento");
		String codigo_comprobante = (String) paramRequest
				.get("codigo_comprobante");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_documento", codigo_documento);
		parameters.put("codigo_comprobante", codigo_comprobante);

		List<Map> data = reportContawebService.getReport(parameters,
				"nota_contableModel");
		for (Map map : data) {
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			usuarios.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			usuarios.setCodigo((String) map.get("creacion_user"));
			usuarios = usuariosService.consultar(usuarios);

			map.put("elaboro", (usuarios != null ? usuarios.getNombres() + " "
					+ usuarios.getApellidos() : ""));

		}
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Nota_contable.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirReporte_consumo() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String identificacion = (String) paramRequest.get("identificacion");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		Date fecha_final = (Date) paramRequest.get("fecha_final");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("fecha_final", fecha_final);
		// log.info(codigo_empresa + " - " + codigo_sucursal + " - "
		// + identificacion + " - " + fecha_inicial + " - " + fecha_final);

		List<Map> data = reportService.getReport(parameters,
				"reporte_consumoModel");

		for (int i = 0; i < data.size(); i++) {
			Map map = data.get(i);

			Articulo art = new Articulo();
			art.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			art.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			art.setCodigo_articulo((String) map.get("codigo_articulo"));
			art = articuloService.consultar(art);

			String articulo = (art != null ? art.getNombre1() : "");

			map.put("articulo", articulo);
			// log.info("ARTICULO:" + articulo);
			data.set(i, map);

		}
		/*
		 * agregado por Luis Miguel
		 */
		parameters.put(JRParameter.REPORT_LOCALE, IConstantes.locale);

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info(data);

		report.setSrc("/report/Reporte_consumo.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirReporte_oportunidad() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		Date fecha_final = (Date) paramRequest.get("fecha_final");
		String formato = (String) paramRequest.get("formato");
		String filtro = (String) paramRequest.get("filtro");

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("fecha_final", fecha_final);
		// log.info(codigo_empresa + " - " + codigo_sucursal + " - "
		// + fecha_inicial + " - " + fecha_final);

		List<Map> data = reportService.getReport(parameters,
				"reporte_oportunidadModel");
		List<Map> dataFilter = new ArrayList<Map>();
		for (int i = 0; i < data.size(); i++) {
			Map map = data.get(i);

			Articulo art = new Articulo();
			art.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			art.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			art.setCodigo_articulo((String) map.get("codigo_articulo"));
			art = articuloService.consultar(art);

			boolean add = false;
			if (filtro.equals("1")) {
				if (art != null) {
					if (art.getPos().equals("S")) {
						add = true;
					}
				}
			} else if (filtro.equals("2")) {
				if (art != null) {
					if (!art.getPos().equals("S")) {
						add = true;
					}
				}
			} else {
				add = true;
			}

			String articulo = (art != null ? art.getNombre1() : "");

			map.put("articulo", articulo);
			// //log.info(articulo);
			// log.info("ARTICULO:" + articulo);
			data.set(i, map);

			if (add) {
				dataFilter.add(map);
			}
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(dataFilter);
		// log.info(dataFilter);

		report.setSrc("/report/Reporte_medicamentos.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	/**
	 * Este metodo me permite generar un reporte de consolidado de consultas y
	 * morbilidad
	 *
	 */
	public void imprimirConsolidadoYMorbilidad() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);

		List<Map> data1 = reportService.getReport(parameters,
				"reporte_gruposetariosModel");
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data1);

		report.setSrc("/report/reporte_consolidado_morbilidad.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	/**
	 * Este metodo me permite generar un reporte de consolidado de consultas y
	 * morbilidad
	 *
	 */
	public void imprimirConsolidadoYProcedimientos() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);

		// modificar el model
		List<Map> data1 = reportService.getReport(parameters,
				"reporte_gruposetarios_procedimientosModel");
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data1);

		report.setSrc("/report/reporte_consolidado_morbilidad.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	/**
	 * Este metodo me permite generar un reporte de consolidado de consultas y
	 * morbilidad
	 *
	 */
	public void imprimirValorFacturado() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);

		List<Map> data1 = reportService.getReport(parameters,
				"reporte_valorFacturadoModel");

		List<Map<String, Object>> datos_finales = new ArrayList<Map<String, Object>>();
		for (Map mapResultado : data1) {
			Map<String, Object> map = facturacionService
					.getValorTotalYRecuperado(mapResultado);
			if (map != null) {
				Double valor_facturado = (Double) map.get("valor_total");
				Double valor_recuperado = (Double) map.get("valor_recibo");
				String nro_factura = (String) map.get("codigo_documento");
				String fecha_factura = (String) map.get("fecha");

				mapResultado.put("valor_factura",
						valor_facturado != null ? valor_facturado : 0d);
				mapResultado.put("valor_recuperado",
						valor_recuperado != null ? valor_recuperado : 0d);
				mapResultado.put("nro_factura",
						nro_factura != null ? nro_factura : "");
				mapResultado.put("fecha_fact",
						fecha_factura != null ? fecha_factura : "");

				// Verificamos que existan los valores
				datos_finales.add(mapResultado);
			}
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(datos_finales);

		report.setSrc("/report/facturado.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	/**
	 * Este metodo me permite generar un reporte de consolidado de consultas y
	 * morbilidad
	 *
	 */
	public void imprimirReporteResLaboratorioFueraRango() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);

		List<Map> data1 = reportService.getReport(parameters,
				"resultado_laboratoriosModel");
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data1);

		report.setSrc("/report/resultado_fuera_rango_laboratorio.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirHistoria_clinica() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);

		List<Map> data1 = reportService.getReport(parameters,
				"historia_clinica1Model");
		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);
		// log.info("DATAs1: " + Source1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_clinica2Model");
		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);
		// log.info("DATA2: " + data2);
		// log.info("DATAs2: " + Source2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_clinica3Model");
		DataSourceReport Source3 = new DataSourceReport();
		Source3.loadReport(data3);
		// log.info("DATA3: " + data3);
		// log.info("DATAs3: " + Source3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_clinica4Model");
		DataSourceReport Source4 = new DataSourceReport();
		Source4.loadReport(data4);
		// log.info("DATA4: " + data4);
		// log.info("DATAs4: " + Source4);

		parameters.put("dataSource1", Source1);
		parameters.put("dataSource2", Source2);
		parameters.put("dataSource3", Source3);
		parameters.put("dataSource4", Source4);
		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/Historia_clinica_ce.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	public void imprimirReporte_afiliacion() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String identificacion = (String) paramRequest.get("identificacion");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		String formato = (String) paramRequest.get("formato");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_inicial);

		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		String numero = Num_letter.convertirLetras((int) dia);

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("numero", numero);
		// log.info(codigo_empresa + " - " + codigo_sucursal + " - "
		// + fecha_inicial + " - " + numero);

		List<Map> data = reportService.getReport(parameters,
				"reporte_afiliacionModel");

		parameters.putAll(paramRequest); // actualizamos parametros

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info(data);

		report.setSrc("/report/Reporte_afiliacion.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirAdmision() throws Exception {
		String nro_ingreso = (String) paramRequest.get("nro_ingreso");
		String nro_identificacion = (String) paramRequest
				.get("nro_identificacion");

		parameters.put("nro_ingreso", nro_ingreso);
		parameters.put("nro_identificacion", nro_identificacion);

		List<Map> data = reportService.getReport(parameters,
				"reporteAdmisionModel");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Admision.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType("pdf");
	}

	public void imprimirRedPrestadores() throws Exception {
		Timestamp fech_init = (Timestamp) paramRequest.get("fech_init");
		Timestamp fech_end = (Timestamp) paramRequest.get("fech_end");
		String cerrado = (String) paramRequest.get("cerrado");
		String rango = (String) paramRequest.get("rango");

		parameters.put("fech_init", fech_init);
		parameters.put("fech_end", fech_end);
		parameters.put("cerrado", cerrado);
		parameters.put("rango", rango);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		parameters.put("nameAdmin", "Prestador ");
		parameters.put("fecha", dateFormat.format(fech_init) + " hasta "
				+ dateFormat.format(fech_end));

		List<Map> data = reportService.getReport(parameters,
				"red_prestadoresModel");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/red_prestadores.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType("pdf");
	}

	public void imprimirReporte_autorizacion_traslado() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String identificacion = (String) paramRequest.get("identificacion");
		String eps = (String) paramRequest.get("eps");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("eps", eps);
		parameters.put("fecha_inicial", fecha_inicial);
		// log.info(identificacion + " - " + fecha_inicial + " - " + eps);

		List<Map> data = reportService.getReport(parameters,
				"reporte_autorizacion_trasladoModel");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		parameters.put("firma", getFirmaUsusario());
		parameters.put("nombre_firma",
				usuarios.getNombres() + " " + usuarios.getApellidos());
		parameters.put("id_firma", usuarios.getCodigo());

		report.setSrc("/report/Reporte_autorizacion_traslado.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirResolucion1552() throws Exception {
		boolean rips = (Boolean) paramRequest.get("rip");
		boolean repor = (Boolean) paramRequest.get("report");
		// String in = (String) paramRequest.get("in");
		// String mesAnio = (String) paramRequest.get("mes_anio");
		//
		// log.info("" + paramRequest.get("contentin"));

		parameters.putAll(paramRequest);

		List<Map> data = reportService.getReport(parameters,
				"resolucion1552Model");

		if (validarContenido(data, "No se encontraron registros")) {
			// archivo plano
			if (rips) {
				File file = new File(Executions.getCurrent().getDesktop()
						.getWebApp().getRealPath("")
						+ "/Files/Resolucion1552");
				if (!file.exists()) {
					file.mkdirs();
				}

				file = new File(file.getAbsolutePath() + "/Temp/Resolucion1552");
				if (!file.exists()) {
					file.mkdirs();
				}

				file = new File(file.getAbsolutePath() + "/"
						+ getEmpresa().getCodigo_empresa() + ""
						+ getSucursal().getCodigo_sucursal());
				if (!file.exists()) {
					file.mkdir();
				}

				File fileZip = new File(file.getAbsolutePath() + File.separator
						+ "ZIP");
				if (!fileZip.exists()) {
					fileZip.mkdir();
				}

				file = new File(file.getAbsolutePath() + File.separator
						+ "plain");
				if (!file.exists()) {
					file.mkdir();
				}

				// inicalizamos file
				StringBuilder stringBuilder = new StringBuilder();
				for (Map map : data) {
					Number total_citas = (Number) map.get("total_citas");
					Number realizacion = (Number) map.get("realizacion");
					Number solicitud_pac = (Number) map.get("solicitud_pac");
					Number dos_uno = (Number) map.get("dos_uno");
					Number tres_uno = (Number) map.get("tres_uno");
					Number min_del4 = (Number) map.get("min_del4");
					Number min_del5 = (Number) map.get("min_del5");
					Number max_del4 = (Number) map.get("max_del4");
					Number max_del5 = (Number) map.get("max_del5");
					Number horas_contradas_disponibles = (Number) map
							.get("horas_contradas_disponibles");

					stringBuilder.append(total_citas + ",");
					stringBuilder.append(realizacion + ",");
					stringBuilder.append(solicitud_pac + ",");
					stringBuilder.append(dos_uno + ",");
					stringBuilder.append(tres_uno + ",");
					stringBuilder.append(min_del4 + ",");
					stringBuilder.append(min_del5 + ",");
					stringBuilder.append(max_del4 + ",");
					stringBuilder.append(max_del5 + ",");
					stringBuilder.append(horas_contradas_disponibles);
					stringBuilder.append("\r\n");
				}

				String nameFile = file.getAbsolutePath() + File.separator
						+ "Resolucion1552" + ".TXT";
				DataOutputStream dos = new DataOutputStream(
						new FileOutputStream(nameFile));
				dos.writeBytes(stringBuilder.toString());
				dos.close();
				// fin inicalizacion file
				final int BUFFER = 2048;
				final String formato_comprimido = ".zip";

				String time = new SimpleDateFormat("ddMMyyyyhhmm")
						.format(Calendar.getInstance().getTime());
				String nameOfFileZip = fileZip.getAbsolutePath()
						+ File.separator
						+ "Archivos-"
						+ getEmpresa().getNombre_empresa().replaceAll(
								"^[0-9a-zA-Z ]", "") + "-" + (time)
						+ formato_comprimido;

				BufferedInputStream origin = null;
				FileOutputStream dest = new FileOutputStream(nameOfFileZip);
				ZipOutputStream out = new ZipOutputStream(
						new BufferedOutputStream(dest));
				byte databyte[] = new byte[BUFFER];
				String files[] = file.list();
				for (int i = 0; i < files.length; i++) {
					FileInputStream fi = new FileInputStream(
							file.getAbsolutePath() + File.separator + files[i]);
					origin = new BufferedInputStream(fi, BUFFER);
					// creamos una entrada zip
					ZipEntry entry = new ZipEntry(files[i]);
					// agregamos entradas zip al archivo
					out.putNextEntry(entry);
					int count;
					while ((count = origin.read(databyte, 0, BUFFER)) != -1) {
						out.write(databyte, 0, count);
					}
					origin.close();
					File aux = new File(file.getAbsolutePath() + File.separator
							+ files[i]);
					if (aux.exists()) {
						aux.delete();
					}
				}
				out.close();

				FileInputStream archivo = new FileInputStream(nameOfFileZip);
				int longitud = archivo.available();
				byte[] datos = new byte[longitud];
				archivo.read(datos);
				archivo.close();
				File del = new File(nameOfFileZip);
				Filedownload.save(del, "application/zip");
			}

			// reporte
			if (repor) {
				DataSourceReport dataSource = new DataSourceReport();
				dataSource.loadReport(data);
				// log.info(data);

				report.setSrc("/report/resolucion_1552.jasper");
				report.setParameters(parameters);
				report.setDatasource(dataSource);
				report.setType("pdf");
			}
		}
	}

	public void imprimirProcedimiento_multiple() throws Exception {
		String nro_factura = (String) paramRequest.get("nro_factura");
		String formato = (String) paramRequest.get("formato");

		parameters.put("nro_factura", nro_factura);

		List<Map> listAux = reportService.getReport(parameters,
				"procedimiento_multipleModel");

		List<Map> data = new LinkedList<Map>();

		for (Map map : listAux) {
			String codigo_administradora = (String) map
					.get("codigo_administradora");
			String id_plan = (String) map.get("id_plan");
			String codigo_procedimiento = (String) map
					.get("codigo_procedimiento");
			String nro_identificacion = (String) map.get("nro_identificacion");
			String nro_ingreso = (String) map.get("nro_ingreso");

			String procedimiento = "";

			Admision admision = new Admision();
			admision.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			admision.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			admision.setNro_ingreso(nro_ingreso);
			admision.setNro_identificacion(nro_identificacion);
			admision = admisionService.consultar(admision);

			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			contratos.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			contratos.setCodigo_administradora(codigo_administradora);
			contratos.setId_plan(id_plan);
			contratos = contratosService.consultar(contratos);
			String manual = (manuales_tarifarios != null ? manuales_tarifarios
					.getMaestro_manual().getTipo_manual() : "");
			Map result = procedimientosService.getProcedimiento(
					manuales_tarifarios, new Long(codigo_procedimiento));
			procedimiento = (String) result.get("nombre_procedimiento");

			String cobra_cirujano = (String) map.get("cobra_cirujano");
			String cobra_anestesiologo = (String) map
					.get("cobra_anestesiologo");
			String cobra_ayudante = (String) map.get("cobra_ayudante");
			String cobra_sala = (String) map.get("cobra_sala");
			String cobra_materiales = (String) map.get("cobra_materiales");
			String cobra_perfusionista = (String) map
					.get("cobra_perfusionista");
			String grupo = (String) map.get("grupo");

			BigDecimal valor_cirujano = (BigDecimal) map.get("valor_cirujano");
			BigDecimal valor_anestesiologo = (BigDecimal) map
					.get("valor_anestesiologo");
			BigDecimal valor_ayudante = (BigDecimal) map.get("valor_ayudante");
			BigDecimal valor_sala = (BigDecimal) map.get("valor_sala");
			BigDecimal valor_materiales = (BigDecimal) map
					.get("valor_materiales");
			BigDecimal valor_perfusionista = (BigDecimal) map
					.get("valor_perfusionista");

			map.put("procedimiento", procedimiento);

			data.add(map);

			if (cobra_cirujano.equals("S")) {
				Map mapBean = grupos_quirurgicosService.getNomGrupoCirugia(
						manual, grupo, "GR01", "39756", map);
				mapBean.put("medico", (String) map.get("prestador"));
				mapBean.put("valor_procedimiento", valor_cirujano);
				data.add(mapBean);
			}
			if (cobra_anestesiologo.equals("S")) {
				Map mapBean = grupos_quirurgicosService.getNomGrupoCirugia(
						manual, grupo, "GR02", "38564", map);
				mapBean.put("medico", (String) map.get("anestesiologo"));
				mapBean.put("valor_procedimiento", valor_anestesiologo);
				data.add(mapBean);
			}
			if (cobra_ayudante.equals("S")) {
				Map mapBean = grupos_quirurgicosService.getNomGrupoCirugia(
						manual, grupo, "GR03", "39456", map);
				mapBean.put("medico", (String) map.get("ayudante"));
				mapBean.put("valor_procedimiento", valor_ayudante);
				data.add(mapBean);
			}
			if (cobra_sala.equals("S")) {
				Map mapBean = grupos_quirurgicosService.getNomGrupoCirugia(
						manual, grupo, "GR04", "", map);
				mapBean.put("valor_procedimiento", valor_sala);
				data.add(mapBean);
			}
			if (cobra_materiales.equals("S")) {
				Map mapBean = grupos_quirurgicosService.getNomGrupoCirugia(
						manual, grupo, "GR05", "41205", map);
				mapBean.put("valor_procedimiento", valor_materiales);
				data.add(mapBean);
			}
			if (cobra_perfusionista.equals("S")) {
				Map mapBean = grupos_quirurgicosService.getNomGrupoCirugia(
						manual, grupo, "GR06", "", map);
				mapBean.put("medico", (String) map.get("prestador"));
				mapBean.put("valor_procedimiento", valor_perfusionista);
				data.add(mapBean);
			}

		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// //log.info(data);

		report.setSrc("/report/Procedimiento_multiple.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirFacturacion_servicio() throws Exception {
		String nro_factura = (String) paramRequest.get("nro_factura");

		String formato = (String) paramRequest.get("formato");

		parameters.put("nro_factura", nro_factura);

		List<Map> data = reportService.getReport(parameters,
				"facturacion_servicioModel");

		for (Map map : data) {
			Articulo articulo = new Articulo();
			articulo.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			articulo.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			articulo.setCodigo_articulo((String) map.get("codigo_servicio"));
			articulo = articuloService.consultar(articulo);
			map.put("servicio", (articulo != null ? articulo.getNombre1() : ""));
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Facturacion_servicio.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirRespuestaSolicitudIndividual() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);

		List<Map> data = reportService.getReport(parameters,
				"reporte_revisionComiteIndividual");

		// consultamos la informacion del articulo
		for (Map map : data) {
			String articulo = (String) map.get("codigo_medicamento");
			if (articulo != null) {
				if (!articulo.trim().isEmpty()) {
					Articulo articuloTemp = new Articulo();
					articuloTemp.setCodigo_empresa(getEmpresa()
							.getCodigo_empresa());
					articuloTemp.setCodigo_sucursal(getSucursal()
							.getCodigo_sucursal());
					articuloTemp.setCodigo_articulo(articulo);
					articuloTemp = articuloService.consultar(articuloTemp);
					if (articuloTemp != null) {
						map.put("medicamento", articuloTemp.getNombre1());
						map.put("presentacion", articuloTemp.getPresentacion());
						map.put("consentracion",
								articuloTemp.getConcentracion());
						map.put("via", articuloTemp.getVia());
					}
				}

			}
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info(data);

		report.setSrc("/report/respuetsaComite.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirFacturacion_rips() throws Exception {
		String codigo_comprobante = (String) paramRequest
				.get("codigo_comprobante");
		Long id_factura = (Long) paramRequest.get("id_factura");
		String agrupar = (String) paramRequest.get("agrupar");
		String mediana = (String) paramRequest.get("mediana");
		String incluir_paquete = paramRequest.containsKey("incluir_paquete") ? (String) paramRequest
				.get("incluir_paquete") : "S";

		String codigo_empresa = getEmpresa().getCodigo_empresa();
		String codigo_sucursal = getSucursal().getCodigo_sucursal();

		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_comprobante", codigo_comprobante);
		parameters.put("id_factura", id_factura);
		parameters.put("email_sucursal",
				getSucursal().getEmail() != null ? getSucursal().getEmail()
						.toLowerCase() : "");
		if (incluir_paquete.equalsIgnoreCase("N")) {
			parameters.put("valor_total_cero", "valor_total_cero");
		}

		List<Map> dataAux = reportContawebService.getReport(parameters,
				"facturacion_ripsModel");
		double valor_total = 0;
		for (Map map : dataAux) {
			String tipo_servicio = (String) map.get("tipo_servicio");
			double total = (Integer) map.get("total");
			boolean facturable = (Boolean) map.get("facturable");
			boolean fact = true;
			if (tipo_servicio.equalsIgnoreCase("MEDICAMENTO")
					|| tipo_servicio.equalsIgnoreCase("MATERIALES/INSUMOS")) {
				if (!facturable) {
					fact = false;
				}
			}
			if (fact) {
				valor_total += total;
			}
		}
		valor_total = Integer.parseInt(Utilidades.formatDecimal(valor_total,
				"#0"));
		List<Map> data = new LinkedList<Map>();

		Map<String, Admision> mapa_admisiones = new HashMap<String, Admision>();
		Map<String, Usuarios> mapa_usuarios = new HashMap<String, Usuarios>();

		byte[] firma_usuario_creador_firma = null;

		for (Map bean : dataAux) {
			String codigo_documento_res = (String) bean.get("codigo_documento");
			String nro_atencion = (String) bean.get("nro_atencion");
			Timestamp fecha = (Timestamp) bean.get("fecha");
			Timestamp fecha_inicio = (Timestamp) bean.get("fecha_inicio");
			Timestamp fecha_final = (Timestamp) bean.get("fecha_final");
			Timestamp fecha_vencimiento = (Timestamp) bean
					.get("fecha_vencimiento");
			String codigo_administradora = (String) bean
					.get("codigo_administradora");
			String id_plan = (String) bean.get("id_plan");
			int plazo = (Integer) bean.get("plazo");
			String codigo_tercero = (String) bean.get("codigo_tercero");
			String ultimo_user = (String) bean.get("ultimo_user");
			String nro_poliza = (String) bean.get("nro_poliza");
			// double valor_total = (Double)bean.get("valor_total");
			double dto_factura = (Double) bean.get("dto_factura");
			double dto_copago = (Double) bean.get("dto_copago");
			double valor_copago = (Double) bean.get("valor_copago");
			String tipo_servicio = (String) bean.get("tipo_servicio");
			String codigo_articulo = (String) bean.get("codigo_articulo");
			String detalle = (String) bean.get("detalle");
			int cantidad = (Integer) bean.get("cantidad");
			double valor_unitario = (Integer) bean.get("valor_unitario");
			double total = (Integer) bean.get("total");
			String tipo = (String) bean.get("tipo");
			String nro_ingreso = (String) bean.get("nro_ingreso");
			String descripcion = (String) bean.get("descripcion");
			String factura_concepto = (String) bean.get("factura_concepto");
			String consecutivo = (String) bean.get("consecutivo");
			double valor_cuota = bean.get("valor_cuota") != null ? (Double) bean
					.get("valor_cuota") : 0d;
			Integer forma_pago = (Integer) bean.get("forma_pago");
			String nocopago = (String) bean.get("nocopago");
			String estado = (String) bean.get("estado");

			if (cantidad > 0) {
				Usuarios usuarios = new Usuarios();
				if (mapa_admisiones.containsKey(codigo_empresa
						+ codigo_sucursal + ultimo_user)) {
					usuarios = mapa_usuarios.get(codigo_empresa
							+ codigo_sucursal + ultimo_user);
				} else {
					usuarios.setCodigo_empresa(codigo_empresa);
					usuarios.setCodigo_sucursal(codigo_sucursal);
					usuarios.setCodigo(ultimo_user);
					usuarios = usuariosService.consultar(usuarios);
					mapa_usuarios.put(codigo_empresa + codigo_sucursal
							+ ultimo_user, usuarios);
				}

				if (firma_usuario_creador_firma == null && usuarios != null) {
					firma_usuario_creador_firma = usuarios.getFirma();
				}

				Administradora admin = new Administradora();
				admin.setCodigo_empresa(codigo_empresa);
				admin.setCodigo_sucursal(codigo_sucursal);
				admin.setCodigo(codigo_administradora);
				admin = administradoraService.consultar(admin);

				Admision admision = new Admision();

				if (mapa_admisiones.containsKey(codigo_empresa + "_"
						+ codigo_sucursal + "_" + codigo_tercero + "_"
						+ nro_ingreso)) {
					admision = mapa_admisiones.get(codigo_empresa + "_"
							+ codigo_sucursal + "_" + codigo_tercero + "_"
							+ nro_ingreso);
				} else {
					admision.setCodigo_empresa(codigo_empresa);
					admision.setCodigo_sucursal(codigo_sucursal);
					admision.setNro_identificacion(codigo_tercero);
					admision.setNro_ingreso(nro_ingreso);
					admision = admisionService.consultar(admision);
					mapa_admisiones.put(codigo_empresa + "_" + codigo_sucursal
							+ "_" + codigo_tercero + "_" + nro_ingreso,
							admision);
				}

				Centro_atencion centro_atencion = new Centro_atencion();
				centro_atencion.setCodigo_empresa(codigo_empresa);
				centro_atencion.setCodigo_sucursal(codigo_sucursal);
				centro_atencion.setCodigo_centro(admision.getCodigo_centro());
				centro_atencion = centro_atencionService
						.consultar(centro_atencion);
				String nombre_centro = centro_atencion != null ? centro_atencion
						.getNombre_centro() : "";

				Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
						.getManuales_tarifarios(admision);

				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(codigo_empresa);
				contratos.setCodigo_sucursal(codigo_sucursal);
				contratos.setCodigo_administradora(codigo_administradora);
				contratos.setId_plan(id_plan);
				contratos = contratosService.consultar(contratos);

				Maestro_manual maestro_manual = manuales_tarifarios
						.getMaestro_manual();
				nombre_centro += contratos != null ? " | PLAN: "
						+ contratos.getNombre() : "";
				nombre_centro += maestro_manual != null ? " - Manual Tarifario: "
						+ maestro_manual.getManual()
						: "";
				String tipo_procedimiento = "";

				Map<String, Object> parametro = new HashMap<String, Object>();
				parametro.put("codigo_empresa", codigo_empresa);
				parametro.put("codigo_sucursal", codigo_sucursal);
				parametro.put("nro_identificacion", codigo_tercero);
				parametro.put("nro_ingreso", nro_ingreso);
				parametro.put("codigo_servicio", codigo_articulo);

				Date date = fecha;
				String numero_autorizacion = admision != null ? admision
						.getNro_autorizacion() : "";
				String codigo_servicio = null;
				Map<String, Object> resultado = null;
				if (tipo_servicio.equals("CONSULTA")
						|| tipo_servicio.equals("PROCEDIMIENTO")
						|| tipo_servicio.equals("PROCEDIMIENTO MULT")) {
					parametro.put("codigo_registro",
							Long.parseLong(factura_concepto));

					Map pcd = procedimientosService.getProcedimiento(
							manuales_tarifarios, new Long(codigo_articulo));
					tipo_procedimiento = (agrupar != null ? (String) pcd
							.get("tipo_procedimiento") : "");
					codigo_servicio = (String) pcd.get("codigo_cups");
					// Consultamos por fecha de realizacion
					if (tipo_servicio.equals("CONSULTA")) {
						resultado = datos_consultaService
								.getFechaRealizacion(parametro);
					} else {
						resultado = datos_procedimientoService
								.getFechaRealizacion(parametro);
					}
				} else if (tipo_servicio.equals("MEDICAMENTO")
						|| tipo_servicio.equals("MATERIALES/INSUMOS")
						|| tipo_servicio.equals("SERVICIO")) {
					parametro.put("nro_factura", factura_concepto);

					Articulo articulo = new Articulo();
					articulo.setCodigo_empresa(codigo_empresa);
					articulo.setCodigo_sucursal(codigo_sucursal);
					articulo.setCodigo_articulo(codigo_articulo);
					articulo = articuloService.consultar(articulo);
					tipo_procedimiento = (articulo != null ? articulo
							.getCodigo_unidad_funcional() : "");

					// Consultamos por fecha de realizacion
					if (tipo_servicio.equals("MEDICAMENTO")
							|| tipo_servicio.equals("MATERIALES/INSUMOS")) {
						resultado = datos_medicamentosService
								.getFechaRealizacion(parametro);
					} else if (tipo_servicio.equals("SERVICIO")) {
						resultado = datos_servicioService
								.getFechaRealizacion(parametro);
					}
				}

				if (codigo_servicio == null) {
					codigo_servicio = codigo_articulo;
				}

				// Cargamos la informacion
				if (resultado != null) {
					Timestamp dateTemp = (Timestamp) resultado.get("fecha");
					String numero_autorizacion_temp = (String) resultado
							.get("numero_autorizacion");
					String nro_autorizacion_fact = (String) resultado
							.get("nro_autorizacion_fact");
					if (dateTemp != null) {
						date = dateTemp;
					}
					if (numero_autorizacion_temp != null
							&& !numero_autorizacion_temp.trim().isEmpty()) {
						numero_autorizacion = numero_autorizacion_temp;
					} else if (nro_autorizacion_fact != null
							&& !nro_autorizacion_fact.trim().isEmpty()) {
						numero_autorizacion = nro_autorizacion_fact;
					}
				}

				String nombre_tipo_procedimiento = "";
				Tipo_procedimiento tipo_pro = new Tipo_procedimiento();
				tipo_pro.setCodigo_empresa(codigo_empresa);
				tipo_pro.setCodigo(tipo_procedimiento);
				tipo_pro = tipo_procedimientoService.consultar(tipo_pro);
				nombre_tipo_procedimiento = (tipo_pro != null ? tipo_pro
						.getNombre() : "OTROS SERVICIOS");

				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(codigo_empresa);
				paciente.setCodigo_sucursal(codigo_sucursal);
				paciente.setNro_identificacion(codigo_tercero);
				paciente = pacienteService.consultar(paciente);

				String tipo_afiliado = "";

				if (paciente != null) {
					if (paciente.getTipo_usuario().equals("2")
							|| paciente.getTipo_usuario().equals("6")) {
						tipo_afiliado = "tipo_afiliado_subsidiado";
					} else if (paciente.getTipo_usuario().equals("1")
							|| paciente.getTipo_usuario().equals("4")
							|| paciente.getTipo_usuario().equals("10")) {
						tipo_afiliado = "tipo_afiliado";
					} else {
						tipo_afiliado = "tipo_afiliado_vinculado";
					}
					tipo_afiliado = Utilidades.getDescripcionElemento(
							paciente.getTipo_afiliado(), tipo_afiliado,
							elementoService);
				}

				// se actualiza la via de ingreso
				if (admision != null
						&& admision.getElemento_via_ingreso() != null) {
					descripcion = admision.getElemento_via_ingreso()
							.getDescripcion();
				}

				Map map = new HashMap();
				map.put("codigo_documento", Utilidades.getNumeroFactura(
						codigo_documento_res, getParametros_empresa()
								.getNumero_mascara_factura()));
				map.put("nro_atencion", nro_atencion);
				map.put("fecha", fecha);
				map.put("fecha_inicio", fecha_inicio);
				map.put("fecha_final", fecha_final);
				map.put("fecha_vencimiento", fecha_vencimiento);
				map.put("nit_admin", (admin != null ? admin.getNit() : ""));
				map.put("nom_admin", (admin != null ? admin.getNombre() : ""));
				map.put("dir_admin",
						(admin != null ? admin.getDireccion() : ""));
				map.put("ciud_admin",
						(admin != null ? Utilidades.getNombreMunicipio(
								admin.getCodigo_dpto(),
								admin.getCodigo_municipio(), municipiosService)
								: ""));
				map.put("tel_admin", (admin != null ? admin.getTelefono() : ""));
				map.put("plazo", new Integer(plazo));
				map.put("nro_identificacion",
						(paciente != null ? paciente.getDocumento() : ""));
				map.put("tipo_identificacion",
						(paciente != null ? paciente.getTipo_identificacion()
								: ""));
				map.put("paciente",
						(paciente != null ? paciente.getNombre1() + " "
								+ paciente.getNombre2() + " "
								+ paciente.getApellido1() + " "
								+ paciente.getApellido2() : ""));
				map.put("sexo",
						(paciente != null ? Utilidades.getNombreElemento(
								paciente.getSexo(), "sexo", elementoService)
								: ""));
				map.put("edad",
						(paciente != null ? (Util.getEdad(
								new java.text.SimpleDateFormat("dd/MM/yyyy")
										.format(paciente.getFecha_nacimiento()),
								paciente.getUnidad_medidad(), true))
								: ""));
				map.put("user", (usuarios != null) ? usuarios.getNombres()
						+ " " + usuarios.getApellidos() : "");
				map.put("regimen",
						(paciente != null ? Utilidades.getNombreElemento(
								paciente.getTipo_usuario(), "tipo_usuario",
								elementoService) : ""));
				map.put("nro_contrato",
						(contratos != null ? contratos.getNro_contrato() : ""));
				map.put("nro_poliza", nro_poliza);

				if (nocopago.equals("S")) {
					valor_copago = 0d;
				}
				map.put("valor_total", new java.math.BigDecimal(valor_total));
				map.put("dto_factura", new java.math.BigDecimal(dto_factura));
				map.put("dto_copago", new java.math.BigDecimal(dto_copago));
				map.put("valor_copago", new java.math.BigDecimal(valor_copago));
				map.put("consecutivo", consecutivo);
				map.put("factura_concepto", factura_concepto);
				map.put("codigo_articulo", codigo_servicio);
				map.put("detalle", detalle);
				map.put("cantidad", new Integer(cantidad));
				map.put("valor_unitario", new java.math.BigDecimal(
						valor_unitario));
				map.put("total", new java.math.BigDecimal(total));
				map.put("tipo", tipo);
				map.put("tipo_servicio", tipo_servicio);
				map.put("tipo_procedimiento", tipo_procedimiento);
				map.put("nombre_tipo_procedimiento", nombre_tipo_procedimiento);
				map.put("descripcion", descripcion);
				map.put("dir_paciente",
						(paciente != null ? paciente.getDireccion() : ""));
				map.put("tel_res", (paciente != null ? paciente.getTel_res()
						: ""));
				map.put("estrato", (paciente != null ? paciente.getEstrato()
						: ""));
				map.put("nro_autorizacion", numero_autorizacion);
				map.put("tipo_atencion",
						(admision != null ? Utilidades.getNombreElemento(
								admision.getTipo_atencion(), "tipo_atencion",
								elementoService) : ""));
				map.put("fecha_detalle", date != null ? date : fecha);
				map.put("valor_cuota", valor_cuota);
				map.put("forma_pago", forma_pago);
				map.put("centro_atencion", nombre_centro);
				map.put("nocopago", nocopago);
				map.put("estado", estado);
				map.put("tipo_afiliado", tipo_afiliado);

				if (admision.getAdmision_parto().equals("S") && total == 0.0) {
					if (incluir_paquete.equals("S")) {
						data.add(map);
					}
				} else {
					data.add(map);
				}
			}
		}

		if (agrupar != null) {
			List lista_med = new LinkedList();
			List lista_otros = new LinkedList();
			Utilidades.agrupar(data);
			Utilidades.dividirListas(data, lista_med, lista_otros);
			Utilidades.ordenarPcd(data, codigo_empresa, lista_otros,
					tipo_procedimientoService);
			Utilidades.ordenarMedicamento_materiales(data, codigo_empresa,
					lista_med, tipo_procedimientoService);
			Utilidades.unirListas(data, lista_med, lista_otros);
		}

		// Cargamos las firmas
		parameters.put("firma_gerente", getFirmaGenrente());
		parameters.put("firma_realiza", getFirma(firma_usuario_creador_firma));
		parameters.put("email_financiera", getEmpresa()
				.getEmail_departamento_financiero());

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		if (parametros_empresa.getTipo_reporte_facturacion().equals(
				IConstantes.TIPO_REPORTE_FACTURA_ESE_CARTAGENA)) {
			report.setSrc("/report/facturacion_rips3.jasper");
		} else {
			report.setSrc("/report/Facturacion_rips" + mediana + ".jasper");
		}

		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirPrefactura() throws Exception {
		String codigo_comprobante = (String) paramRequest
				.get("codigo_comprobante");
		String agrupar = (String) paramRequest.get("agrupar");
		Facturacion facturacion = (Facturacion) paramRequest.get("facturacion");
		List<Detalle_factura> lista_detalle = (List<Detalle_factura>) paramRequest
				.get("lista_detalle");

		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_comprobante", codigo_comprobante);

		String codigo_empresa = getEmpresa().getCodigo_empresa();
		String codigo_sucursal = getSucursal().getCodigo_sucursal();
		Long id_factura = facturacion.getId_factura();
		String codigo_administradora = facturacion.getCodigo_administradora();
		String nro_ingreso = facturacion.getNro_ingreso();
		String codigo_tercero = facturacion.getCodigo_tercero();
		Timestamp fecha_inicio = facturacion.getFecha_inicio();
		Timestamp fecha_final = facturacion.getFecha_final();
		String id_plan = facturacion.getId_plan();
		String descripcion = facturacion.getDescripcion();
		String tipo = facturacion.getTipo();
		double valor_total = facturacion.getValor_total();
		double valor_copago = facturacion.getValor_copago();
		double dto_factura = facturacion.getDto_factura();
		double dto_copago = facturacion.getDto_copago();

		Administradora admin = new Administradora();
		admin.setCodigo_empresa(codigo_empresa);
		admin.setCodigo_sucursal(codigo_sucursal);
		admin.setCodigo(codigo_administradora);
		admin = administradoraService.consultar(admin);

		Contratos contratos = new Contratos();
		contratos.setCodigo_empresa(codigo_empresa);
		contratos.setCodigo_sucursal(codigo_sucursal);
		contratos.setCodigo_administradora(codigo_administradora);
		contratos.setId_plan(id_plan);
		contratos = contratosService.consultar(contratos);

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(codigo_tercero);
		paciente = pacienteService.consultar(paciente);

		Admision admision = new Admision();
		admision.setCodigo_empresa(codigo_empresa);
		admision.setCodigo_sucursal(codigo_sucursal);
		admision.setNro_identificacion(codigo_tercero);
		admision.setNro_ingreso(nro_ingreso);
		admision = admisionService.consultar(admision);

		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
				.getManuales_tarifarios(admision);

		valor_total = 0;
		for (Detalle_factura detalle_factura : lista_detalle) {
			String tipo_servicio = detalle_factura.getTipo();
			/*
			 * String factura_concepto = detalle_factura.getFactura_concepto();
			 * String codigo_articulo = detalle_factura.getCodigo_articulo();
			 */
			double total = detalle_factura.getValor_total();
			String facturable = (detalle_factura.getFacturable() ? "" : null);
			double cantidad = detalle_factura.getCantidad();
			// String facturable = request.getParameter("c_0" + i);
			boolean fact = true;
			if (tipo_servicio.equalsIgnoreCase("MEDICAMENTO")
					|| tipo_servicio.equalsIgnoreCase("MATERIALES/INSUMOS")) {
				if (facturable == null || cantidad <= 0) {
					fact = false;
				}
			}
			if (fact) {
				valor_total += total;
			}
		}

		valor_total = Integer.parseInt(Utilidades.formatDecimal(valor_total,
				"#0"));

		List<Map> data = new LinkedList<Map>();

		int i = 1;
		for (Detalle_factura detalle_factura : lista_detalle) {
			String tipo_servicio = detalle_factura.getTipo();
			String factura_concepto = detalle_factura.getFactura_concepto();
			String codigo_articulo = detalle_factura.getCodigo_articulo();
			String detalle = detalle_factura.getDetalle();
			int cantidad = (int) detalle_factura.getCantidad();
			double valor_unitario = detalle_factura.getValor_unitario();
			double total = detalle_factura.getValor_total();
			String tipo_procedimiento = "";

			if (tipo_servicio.equals("CONSULTA")
					|| tipo_servicio.equals("PROCEDIMIENTO")
					|| tipo_servicio.equals("PROCEDIMIENTO MULT")) {
				Map<String, Object> pcd = procedimientosService
						.getProcedimiento(manuales_tarifarios, new Long(
								codigo_articulo));
				tipo_procedimiento = (agrupar != null ? (String) pcd
						.get("tipo_procedimiento") : "");
			} else if (tipo_servicio.equals("MEDICAMENTO")
					|| tipo_servicio.equals("MATERIALES/INSUMOS")
					|| tipo_servicio.equals("SERVICIO")) {
				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(codigo_empresa);
				articulo.setCodigo_sucursal(codigo_sucursal);
				articulo.setCodigo_articulo(codigo_articulo);
				articulo = articuloService.consultar(articulo);
				tipo_procedimiento = (articulo != null ? articulo
						.getCodigo_unidad_funcional() : "");
			}
			String facturable = (detalle_factura.getFacturable() ? "" : null);

			boolean fact = true;

			if (tipo_servicio.equalsIgnoreCase("MEDICAMENTO")
					|| tipo_servicio.equalsIgnoreCase("MATERIALES/INSUMOS")) {
				if (facturable == null || cantidad <= 0) {
					fact = false;
				}
			}

			if (fact) {
				String nombre_tipo_procedimiento = "";
				Tipo_procedimiento tipo_pro = new Tipo_procedimiento();
				tipo_pro.setCodigo_empresa(codigo_empresa);
				tipo_pro.setCodigo(tipo_procedimiento);
				tipo_pro = tipo_procedimientoService.consultar(tipo_pro);
				nombre_tipo_procedimiento = (tipo_pro != null ? tipo_pro
						.getNombre() : "OTROS SERVICIOS");

				Map map = new HashMap();
				map.put("id_factura", id_factura);
				map.put("fecha_inicio", fecha_inicio);
				map.put("fecha_final", fecha_final);
				map.put("nom_admin", (admin != null ? admin.getNombre() : ""));
				map.put("nro_identificacion",
						(paciente != null ? paciente.getDocumento()
								: codigo_tercero));
				map.put("paciente",
						(paciente != null ? paciente.getNombre1() + " "
								+ paciente.getNombre2() + " "
								+ paciente.getApellido1() + " "
								+ paciente.getApellido2() : ""));
				map.put("edad",
						(paciente != null ? (Util.getEdad(
								new java.text.SimpleDateFormat("dd/MM/yyyy")
										.format(paciente.getFecha_nacimiento()),
								paciente.getUnidad_medidad(), true))
								: ""));
				map.put("user", (usuarios != null) ? usuarios.getNombres()
						+ " " + usuarios.getApellidos() : "");
				map.put("nro_contrato",
						(contratos != null ? contratos.getNro_contrato() : ""));
				map.put("valor_total", new java.math.BigDecimal(valor_total));
				map.put("dto_factura", new java.math.BigDecimal(dto_factura));
				map.put("dto_copago", new java.math.BigDecimal(dto_copago));
				map.put("valor_copago", new java.math.BigDecimal(valor_copago));
				map.put("consecutivo", i + "");
				map.put("factura_concepto", factura_concepto);
				map.put("codigo_articulo", codigo_articulo);
				map.put("detalle", detalle);
				map.put("cantidad", new Integer(cantidad));
				map.put("valor_unitario", new java.math.BigDecimal(
						valor_unitario));
				map.put("total", new java.math.BigDecimal(total));
				map.put("tipo", tipo);
				map.put("tipo_servicio", tipo_servicio);
				map.put("nombre_tipo_procedimiento", nombre_tipo_procedimiento);
				map.put("tipo_procedimiento", tipo_procedimiento);
				map.put("descripcion", descripcion);
				map.put("dir_paciente",
						(paciente != null ? paciente.getDireccion() : ""));
				map.put("tel_res", (paciente != null ? paciente.getTel_res()
						: ""));
				map.put("estrato", (paciente != null ? paciente.getEstrato()
						: ""));
				map.put("nro_autorizacion",
						(admision != null ? admision.getNro_autorizacion() : ""));
				map.put("tipo_atencion",
						(admision != null ? Utilidades.getNombreElemento(
								admision.getTipo_atencion(), "tipo_atencion",
								elementoService) : ""));
				data.add(map);
			}
		}

		if (agrupar != null) {
			List lista_med = new LinkedList();
			List lista_otros = new LinkedList();
			Utilidades.agrupar(data);
			Utilidades.dividirListas(data, lista_med, lista_otros);
			Utilidades.ordenarPcd(data, codigo_empresa, lista_otros,
					tipo_procedimientoService);
			Utilidades.ordenarMedicamento_materiales(data, codigo_empresa,
					lista_med, tipo_procedimientoService);
			Utilidades.unirListas(data, lista_med, lista_otros);
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Prefactura.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirSolicitudCTC() throws Exception {
		String nro_identificacion = (String) paramRequest
				.get("nro_identificacion");
		String codigo = (String) paramRequest.get("codigo");
		String formato = (String) paramRequest.get("formato");

		// HttpServletRequest request = (HttpServletRequest) Executions
		// .getCurrent().getNativeRequest();
		// ServletContext context = request.getSession().getServletContext();
		Map param = new HashMap();
		param.put("codigo_empresa", getEmpresa().getCodigo_empresa());
		param.put("codigo_sucursal", getSucursal().getCodigo_sucursal());
		param.put("nro_identificacion", nro_identificacion);
		param.put("codigo", codigo);

		parameters.put("nro_identificacion", nro_identificacion);
		parameters.put("codigo", codigo);
		// String SUBREPORT_DIR = context
		// .getRealPath("/report/img/fondo_ctc2.jpg");
		// File file = new File(SUBREPORT_DIR);
		// parameters.put("fondo", new FileInputStream(file));

		List<Map> data = reportService.getReport(param,
				"reporte_solicitudCTCModel");

		// consultamos la informacion del articulo
		for (Map map : data) {
			String codigo_medico = (String) map.get("codigo_medico");
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(codigo_medico);
			usuarios = usuariosService.consultar(usuarios);
			if (usuarios != null) {
				if (usuarios.getFirma() != null) {
					InputStream imagen = new ByteArrayInputStream(
							usuarios.getFirma());
					map.put("firma_medico", imagen);
				}
			}

			for (int i = 1; i <= 3; i++) {
				String articulo = (String) map.get("codigo_med" + i);
				if (articulo != null) {
					if (!articulo.trim().isEmpty()) {
						Articulo articuloTemp = new Articulo();
						articuloTemp.setCodigo_empresa(getEmpresa()
								.getCodigo_empresa());
						articuloTemp.setCodigo_sucursal(getSucursal()
								.getCodigo_sucursal());
						articuloTemp.setCodigo_articulo(articulo);
						articuloTemp = articuloService.consultar(articuloTemp);
						if (articuloTemp != null) {
							map.put("nom_medicamento" + i,
									articuloTemp.getNombre1());

							map.put("consentracion" + i,
									articuloTemp.getConcentracion());
							//
							Presentacion_articulo presentacion_articulo = new Presentacion_articulo();
							presentacion_articulo
									.setCodigo_empresa(articuloTemp
											.getCodigo_empresa());
							presentacion_articulo
									.setCodigo_sucursal(articuloTemp
											.getCodigo_sucursal());
							presentacion_articulo.setCodigo(articuloTemp
									.getPresentacion());
							presentacion_articulo = presentacion_articuloService
									.consultar(presentacion_articulo);

							map.put("presentacion" + i,
									presentacion_articulo != null ? presentacion_articulo
											.getNombre() : "");

							// Elemento elemento = new Elemento();
							// elemento.setCodigo(articuloTemp.getVia());
							// elemento.setTipo("via_administracion_receta");
							// elemento =
							// getServiceLocator().getServicio(ElementoService.class).consultar(elemento);
							// map.put("via" + i, elemento != null ?
							// elemento.getDescripcion() : "");
						}
					}
				}
			}
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info(data);

		report.setSrc("/report/reporte_ctc.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirFacturacion_cap() throws Exception {
		if (getParametros_empresa().getTipo_reporte_facturacion().equals(
				IConstantes.TIPO_REPORTE_FACTURA_ESE_CARTAGENA)) {
			imprimirFacturacion_capESE();
		} else {
			String formato = (String) paramRequest.get("formato");
			parameters.put("codigo_habilitacion", getEmpresa()
					.getCodigo_habilitacion());
			parameters.putAll(paramRequest);

			List<Map> data = reportContawebService.getReport(parameters,
					"factura_cap_agrupadaModel");
			boolean hab_pref_venta = getParametros_empresa()
					.getHabilitar_prefijo_venta();

			for (Map map : data) {
				String codigo_documento = (String) map.get("codigo_documento");
				String prefijo_venta = (String) map.get("prefijo_venta");
				if (hab_pref_venta && prefijo_venta != null) {
					codigo_documento = prefijo_venta
							+ "-"
							+ Utilidades.getNumeroFactura(codigo_documento,
									getParametros_empresa()
											.getNumero_mascara_factura());
				} else {
					codigo_documento = Utilidades.getNumeroFactura(
							codigo_documento, getParametros_empresa()
									.getNumero_mascara_factura());
				}
				map.put("codigo_documento", codigo_documento);

			}

			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);
			// log.info(data);

			report.setSrc("/report/Facturacion_cap.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType(formato);
		}
	}

	public void imprimirFacturacion_capESE() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.put("codigo_habilitacion", getEmpresa()
				.getCodigo_habilitacion());
		parameters.putAll(paramRequest);

		List<Map> data = reportContawebService.getReport(parameters,
				"factura_cap_agrupadaESEModel");

		if (validarContenido(data, "No se encontraron registros")) {
			for (Map map : data) {
				map.put("codigo_documento_aux", Utilidades.getNumeroFactura(
						map.get("codigo_documento_aux") + "",
						getParametros_empresa().getNumero_mascara_factura()));
			}

			parameters.put("aviso_facturacion_capitada", getEmpresa()
					.getAviso_facturacion_capitada() != null ? getEmpresa()
					.getAviso_facturacion_capitada() : "");
			parameters.put("telefono_dpto_financiero", getEmpresa()
					.getTelefono_dpto_financiero() != null ? getEmpresa()
					.getTelefono_dpto_financiero() : "");
			parameters.put("email_financiera", getEmpresa()
					.getEmail_departamento_financiero() != null ? getEmpresa()
					.getEmail_departamento_financiero() : "");
			parameters.put("firma_gerente", getFirmaGenrente());

			// log.error("Param: " + parameters);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/facturacion_capitada_ese.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType(formato);
		}
	}

	public void imprimirReporte_beneficiario25() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String identificacion = (String) paramRequest.get("identificacion");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		Date date_vencimiento = (Date) paramRequest.get("date_vencimiento");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("date_vencimiento", date_vencimiento);
		// log.info(codigo_empresa + " - " + codigo_sucursal + " - "
		// + fecha_inicial);

		List<Map> data = reportService.getReport(parameters,
				"reporte_beneficiario25Model");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		parameters.put("firma", getFirmaGenrente());
		parameters.put("nombre_firma", getEmpresa().getGerente());
		parameters.put("telefonos",
				getEmpresa().getTelefonos() != null ? getEmpresa()
						.getTelefonos() : "");

		report.setSrc("/report/Reporte_beneficiario25.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirReporte_beneficiario18() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String identificacion = (String) paramRequest.get("identificacion");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		Date date_vencimiento = (Date) paramRequest.get("date_vencimiento");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("date_vencimiento", date_vencimiento);
		// log.info(codigo_empresa + " - " + codigo_sucursal + " - "
		// + fecha_inicial);

		List<Map> data = reportService.getReport(parameters,
				"reporte_beneficiario18Model");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		parameters.put("firma", getFirmaGenrente());
		parameters.put("nombre_firma", getEmpresa().getGerente());

		report.setSrc("/report/Reporte_beneficiario18.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirReporte_retiro_afiliacion() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String identificacion = (String) paramRequest.get("identificacion");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		Date fecha_recibido = (Date) paramRequest.get("fecha_recibido");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("fecha_recibido", fecha_recibido);

		List<Map> data = reportService.getReport(parameters,
				"reporte_retiro_afiliacionModel");

		if (validarContenido(data, "No se encontraron registros")) {
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", getEmpresa().getCodigo_empresa());
			map.put("codigo_sucursal", getSucursal().getCodigo_sucursal());
			map.put("codigo_reporte", IFirmasConstantes.RETIRO_AFILIACION);
			Modulo_firmas modulo_firmas = modulo_firmasService
					.consultarFirma(map);

			parameters
					.put("firma",
							modulo_firmas != null
									&& modulo_firmas.getFirma() != null ? new ByteArrayInputStream(
									modulo_firmas.getFirma()) : null);
			parameters.put("nombre_firma",
					modulo_firmas != null ? modulo_firmas.getNombre_firma()
							: "");
			parameters.put("telefonos",
					getEmpresa().getTelefonos() != null ? getEmpresa()
							.getTelefonos() : "");

			report.setSrc("/report/Reporte_retiro_afiliacion.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType(formato);
		}
	}

	public void imprimirReporte_traslado() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);
		List<Map> data = reportService.getReport(parameters,
				"reporte_trasladoModel");
		if (validarContenido(data, "No se encontraron registros")) {
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			parameters.put("firma", getFirmaGenrente());
			parameters.put("nombre_firma", getEmpresa().getGerente());
			parameters.put("telefonos",
					getEmpresa().getTelefonos() != null ? getEmpresa()
							.getTelefonos() : "");

			report.setSrc("/report/Reporte_traslado.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType(formato);
		}
	}

	public void imprimirReporte_conbeneficiario() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String identificacion = (String) paramRequest.get("identificacion");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		String formato = (String) paramRequest.get("formato");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_inicial);

		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		String numero = Num_letter.convertirLetras((int) dia);

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("numero", numero);
		// log.info(codigo_empresa + " - " + codigo_sucursal + " - "
		// + fecha_inicial + " - " + numero);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", getEmpresa().getCodigo_empresa());
		map.put("codigo_sucursal", getSucursal().getCodigo_sucursal());
		map.put("codigo_reporte", IFirmasConstantes.CERTIFICADO_BENEFICIARIO);
		Modulo_firmas modulo_firmas = modulo_firmasService.consultarFirma(map);

		parameters
				.put("firma",
						modulo_firmas != null
								&& modulo_firmas.getFirma() != null ? new ByteArrayInputStream(
								modulo_firmas.getFirma()) : null);
		parameters.put("nombre_firma",
				modulo_firmas != null ? modulo_firmas.getNombre_firma() : "");
		parameters.put("telefonos",
				getEmpresa().getTelefonos() != null ? getEmpresa()
						.getTelefonos() : "");

		List<Map> data = reportService.getReport(parameters,
				"reporte_conbeneficiarioModel");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info(data);

		report.setSrc("/report/Reporte_conbeneficiario.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirTotalProcedimientoOrdenadosPorOdontolog()
			throws Exception {
		// log.info("@imprimirTotalProcedimientoOrdenadosPorOdontolog total_procedimientos");
		parameters.putAll(paramRequest);
		List<Map> data = reportService.getReport(parameters,
				"reporte_procedimiento_ordenadosModel");
		/* Esta local es para que trabaje con el lenguage español */
		parameters.put(JRParameter.REPORT_LOCALE, IConstantes.locale);

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info("datos: " + data);

		report.setSrc("/report/total_proc_odonto.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType("pdf");
	}

	public void imprimirTotalPacientesPorEdadMesYAnio() throws Exception {
		// log.info("@imprimirTotalPacientesPorEdadMesYAnio total_paciente por rangos de edades");
		parameters.putAll(paramRequest);
		List<Map> data = reportService.getReport(parameters,
				"reporte_pacientes_edad_sexoModel");
		/* Esta local es para que trabaje con el lenguage español */
		parameters.put(JRParameter.REPORT_LOCALE, IConstantes.locale);

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info("datos: " + data);

		report.setSrc("/report/reportetotal_pac_aten_edades.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType("pdf");
	}

	public void imprimirPacienteActividadesOdontologia() throws Exception {
		String meses_anio = (String) paramRequest.get("meses_anio");
		Integer modo_orden = (Integer) paramRequest.get("modo_orden");
		parameters.putAll(paramRequest);
		List<Map> data = reportService.getReport(parameters,
				"pacientes_actividad_odontologoModel");

		if (modo_orden.intValue() != 3) {
			long cantidad_pacientes = 0;
			long cantidad_actividades = 0;
			for (Map map : data) {
				String tipo = (String) map.get("tipo");
				long cantidad = (Long) map.get("cantidad");
				if (tipo.equals("PAC")) {
					cantidad_pacientes += cantidad;
				} else {
					cantidad_actividades += cantidad;
				}
			}

			/* Agregamos parametros */
			parameters.put("meses_anio", meses_anio);
			parameters.put("cantidad_pacientes", cantidad_pacientes + "");
			parameters.put("cantidad_actividades", cantidad_actividades + "");
		}

		parameters.put(JRParameter.REPORT_LOCALE, IConstantes.locale);

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info(data);

		report.setSrc("/report/"
				+ (modo_orden.intValue() == 1 ? "paciente_actividades_realizadasReport.jasper"
						: "pacientes_acitividades_odontologos_simple.jasper"));
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType("pdf");
	}

	public void imprimirReporte_ibc() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String identificacion = (String) paramRequest.get("identificacion");
		Date fecha_inicial = (Date) paramRequest.get("fecha_inicial");
		String formato = (String) paramRequest.get("formato");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_inicial);

		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		String numero = Num_letter.convertirLetras((int) dia);

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("numero", numero);
		parameters.putAll(paramRequest);

		List<Map> data = reportService
				.getReport(parameters, "reporte_ibcModel");

		if (data.isEmpty()) {
			paramRequest.put("retorno_vacio", true);
			paramRequest.put("mensaje_vacio",
					"No existe registro para este paciente");
		} else {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", getEmpresa().getCodigo_empresa());
			map.put("codigo_sucursal", getSucursal().getCodigo_sucursal());
			map.put("codigo_reporte", IFirmasConstantes.IBC);
			Modulo_firmas modulo_firmas = modulo_firmasService
					.consultarFirma(map);

			parameters
					.put("firma",
							modulo_firmas != null
									&& modulo_firmas.getFirma() != null ? new ByteArrayInputStream(
									modulo_firmas.getFirma()) : null);
			parameters.put("nombre_firma",
					modulo_firmas != null ? modulo_firmas.getNombre_firma()
							: "");
			parameters.put("telefonos",
					getEmpresa().getTelefonos() != null ? getEmpresa()
							.getTelefonos() : "");

			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);
			// log.info(data);

			report.setSrc("/report/Reporte_ibc.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType(formato);
		}
	}

	public void imprimirConsolidado_caja() throws Exception {
		String codigo_empresa = (String) paramRequest.get("codigo_empresa");
		String codigo_sucursal = (String) paramRequest.get("codigo_sucursal");
		String[] tipo = (String[]) paramRequest.get("tipos");
		Timestamp fecha_inicial = (Timestamp) paramRequest.get("fecha_inicial");
		Timestamp fecha_final = (Timestamp) paramRequest.get("fecha_final");

		String codigo_administradora = (String) paramRequest
				.get("codigo_administradora");
		String id_plan = (String) paramRequest.get("id_plan");
		String codigo_usuario = (String) paramRequest.get("codigo_usuario");
		String agrupar_turnos = (String) paramRequest.get("agrupar_turnos");

		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("tipos", tipo);
		parameters.put("fecha_inicial", fecha_inicial);
		parameters.put("fecha_final", fecha_final);
		if (!codigo_administradora.isEmpty()) {
			parameters.put("codigo_administradora", codigo_administradora);
		}
		if (!id_plan.isEmpty()) {
			parameters.put("id_plan", id_plan);
		}
		if (!codigo_usuario.isEmpty()) {
			parameters.put("codigo_usuario", codigo_usuario);
		}
		if (agrupar_turnos != null) {
			parameters.put("agrupar_turnos", "");
		}

		List<Map> listaAux = reportContawebService.getReport(parameters,
				"consolidado_cajaModel");

		List<Map> data = new LinkedList<Map>();
		for (Map bean : listaAux) {
			String codigo_cajero = (String) bean.get("codigo_cajero");

			// //log.info("codigo cajero: "+codigo_cajero);
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(codigo_cajero);
			usuarios = usuariosService.consultar(usuarios);

			Map map = new HashMap();
			double copago = (Double) bean.get("copago");
			double cant_copago = (Double) bean.get("cant_copago");
			if (copago > 0) {
				copago = copago / cant_copago;
			}

			map.put("documento", bean.get("codigo_documento"));
			map.put("recibo", bean.get("codigo_recibo"));
			map.put("cliente", bean.get("tercero"));
			map.put("fechaRec", bean.get("creacion_date"));
			map.put("valorRec", (Double) (bean.get("valor_recibo")));
			map.put("valorDes", (Double) (bean.get("dto")));
			map.put("valorEfe", (Double) (bean.get("efectivo")));
			map.put("valorTarj", (Double) (bean.get("valor_tarjeta")));
			map.put("valorCheq", (Double) (bean.get("valor_cheque")));
			map.put("descripcion", bean.get("detalle"));
			map.put("cantidad", (Integer) bean.get("cantidad"));
			map.put("copago", (Double) (copago));
			map.put("valor", (Double) (bean.get("valor_unitario")));
			map.put("descuento", (Double) (bean.get("descuento")));
			map.put("iva", (Double) (bean.get("iva")));
			map.put("importe",
					(Double) (copago != 0 ? (Double) bean.get("importe") : 0));
			map.put("importe2",
					(Double) (copago == 0 ? (Double) bean.get("importe2") : 0));
			map.put("cajero", (usuarios != null ? usuarios.getNombres() + " "
					+ usuarios.getApellidos() : ""));
			map.put("codigo_cajero", codigo_cajero);
			map.put("delete_date", bean.get("delete_date"));

			data.add(map);
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);
		// log.info(data);

		report.setSrc("/report/Consolidado_caja_paciente"
				+ (agrupar_turnos != null ? "_user" : "") + ".jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirOrden_servicio2() throws Exception {
		String codigo_orden = (String) paramRequest.get("codigo_orden");
		String formato = (String) paramRequest.get("formato");

		parameters.put("codigo_orden", codigo_orden);

		List<Map> data = reportService.getReport(parameters,
				"orden_servicio2Model");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/Orden_servicio2.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirEcografiaGinecostetrica() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);

		List<Map> data = reportService.getReport(parameters,
				"ecografiaGinecostetricaModel");

		for (Map map : data) {
			byte[] bs = (byte[]) map.get("firma");
			if (bs != null) {
				map.put("firma", new ByteArrayInputStream(bs));
			}
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/ecografia_ginecostetrica.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirConsentimiento_informado() throws Exception {
		parameters.remove("firma_medico");
		String formato = (String) paramRequest.get("formato");
		Admision admision = (Admision) paramRequest.get("admision");
		Timestamp fecha_consentimiento = (Timestamp) paramRequest
				.get("fecha_consentimiento");
		Calendar calendar_date = Calendar.getInstance();
		calendar_date
				.setTime(fecha_consentimiento != null ? fecha_consentimiento
						: new Date());
		String aceptar = (String) paramRequest.get("aceptar");
		String codigo_medico = (String) paramRequest.get("codigo_medico");
		String nombre_procedimiento = (String) paramRequest
				.get("nombre_procedimiento");

		List<Map> data = new ArrayList<Map>();

		if (codigo_medico != null) {
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(codigo_medico);

			usuarios = usuariosService.consultar(usuarios);

			if (usuarios != null) {
				try {
					if (usuarios.getFirma() != null) {
						InputStream firma_medico = new ByteArrayInputStream(
								usuarios.getFirma());
						parameters.put("firma_medico", firma_medico);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(codigo_medico);

			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores != null) {
				parameters.put("nombre_medico", prestadores.getNombres() + " "
						+ prestadores.getApellidos());
				parameters.put("registro_medico",
						prestadores.getRegistro_medico());

			}
		}

		parameters.put("nombre_procedimiento", nombre_procedimiento);
		parameters.put("nombre_completo", admision.getPaciente()
				.getNombreCompleto());
		parameters.put("aceptar", aceptar);
		parameters.put("dias_reporte", calendar_date.get(Calendar.DAY_OF_MONTH)
				+ "");
		parameters.put("meses_reporte",
				Res.getNombreMes(calendar_date.get(Calendar.MONTH)));
		parameters.put("anios_reporte", calendar_date.get(Calendar.YEAR) + "");

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/consentimiento_informado.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirRemisionInterna() throws Exception {
		parameters.remove("firma_medico");
		Long codigo_historia = (Long) paramRequest.get("codigo_historia");
		String formato = (String) paramRequest.get("formato");
		String codigo_medico = (String) paramRequest.get("codigo_medico");

		parameters.put("codigo_historia", codigo_historia);

		Usuarios usuarios = new Usuarios();

		if (codigo_medico != null) {
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(codigo_medico);

			usuarios = usuariosService.consultar(usuarios);

			if (usuarios != null) {
				try {
					if (usuarios.getFirma() != null) {
						InputStream firma_medico = new ByteArrayInputStream(
								usuarios.getFirma());
						parameters.put("firma_medico", firma_medico);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(codigo_medico);

			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores != null) {
				parameters.put("nombre_medico", prestadores.getNombres() + " "
						+ prestadores.getApellidos());
				parameters.put("registro_medico",
						prestadores.getRegistro_medico());

			}

		}

		List<Map> data = reportService.getReport(parameters,
				"remision_internaModel");

		String edad_paciente = null;

		for (int i = 0; i < data.size(); i++) {
			Map map = data.get(i);
			Timestamp fecha_nacimiento = (Timestamp) map
					.get("fecha_nacimiento");
			if (edad_paciente == null) {
				Map<String, Integer> mapa_edades = Util
						.getEdadYYYYMMDD(fecha_nacimiento);

				Integer anios = mapa_edades.get("anios");
				Integer meses = mapa_edades.get("meses");
				Integer dias = mapa_edades.get("dias");

				if (anios.intValue() == 0 && meses.intValue() == 0) {
					edad_paciente = dias + (dias == 1 ? " día" : " días");
				} else if (anios.intValue() == 0) {
					edad_paciente = meses
							+ (meses == 1 ? " mes (" : " meses (")
							+ (dias + (dias == 1 ? " día" : " días")) + ")";
				} else {
					int current_meses = meses.intValue()
							- (anios.intValue() * 12);
					edad_paciente = anios
							+ (anios == 1 ? " año" : " años")
							+ (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ? " mes "
									: " meses"))
									: "");
				}
			}

			map.put("edad_paciente", edad_paciente);
		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/remisiones_internas.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirCitologia() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		String codigo_historia = (String) paramRequest.get("codigo_historia");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("codigo_historia", codigo_historia);

		List<Map> data1 = reportService
				.getReport(parameters, "citologia1Model");
		DataSourceReport source1 = new DataSourceReport();
		source1.loadReport(data1);

		List<Map> data2 = reportService
				.getReport(parameters, "citologia2Model");
		DataSourceReport source2 = new DataSourceReport();
		source2.loadReport(data2);

		// log.info("Data source>>>>>>" + source1);
		parameters.put("dataSource1", source1);
		parameters.put("dataSource2", source2);
		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		Muestra_citologia_vaginal muestra_citologia_vaginal = new Muestra_citologia_vaginal();
		muestra_citologia_vaginal.setCodigo_empresa(codigo_empresa);
		muestra_citologia_vaginal.setCodigo_sucursal(codigo_sucursal);
		muestra_citologia_vaginal.setCodigo_historia(codigo_historia);

		muestra_citologia_vaginal = muestra_citologia_vaginalService
				.consultar(muestra_citologia_vaginal);

		Usuarios usuarios = new Usuarios();
		usuarios.setCodigo_empresa(codigo_empresa);
		usuarios.setCodigo_sucursal(codigo_sucursal);
		usuarios.setCodigo(muestra_citologia_vaginal.getCodigo_prestador());

		usuarios = usuariosService.consultar(usuarios);

		if (usuarios != null) {
			parameters.put("nombre_med", usuarios.getNombres());
			parameters.put("apellido_med", usuarios.getApellidos());
			parameters.put("registro_medico", usuarios.getRegistro_medico());
			try {
				if (usuarios.getFirma() != null) {
					InputStream firma_medico = new ByteArrayInputStream(
							usuarios.getFirma());
					parameters.put("firma_medico", firma_medico);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		report.setSrc("/report/reporte_macro_citologia.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType("pdf");

	}

	public void imprimirConsentimiento_inf_odon() throws Exception {
		parameters.remove("firma_medico");
		Long codigo_historia = (Long) paramRequest.get("codigo_historia");
		String codigo_empresa = (String) parameters.get("codigo_empresa");
		String codigo_sucursal = (String) parameters.get("codigo_sucursal");
		String formato = (String) paramRequest.get("formato");
		String codigo_medico = (String) paramRequest.get("codigo_medico");

		parameters.put("nro_historia", codigo_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		// log.info("codigo_historia>>>>>>>>>>" + codigo_historia);
		// log.info("codigo_empresa>>>>>>>>>>" + codigo_empresa);
		// log.info("codigo_sucursal>>>>>>>>>>" + codigo_sucursal);
		// log.info("parametros>>>>>>>>>" + parameters);

		Usuarios usuarios = new Usuarios();

		if (codigo_medico != null) {
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(codigo_medico);

			usuarios = usuariosService.consultar(usuarios);

			if (usuarios != null) {
				try {
					if (usuarios.getFirma() != null) {
						InputStream firma_medico = new ByteArrayInputStream(
								usuarios.getFirma());
						parameters.put("firma_medico", firma_medico);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(codigo_medico);

			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores != null) {
				parameters.put("nombre_medico", prestadores.getNombres() + " "
						+ prestadores.getApellidos());
				parameters.put("registro_medico",
						prestadores.getRegistro_medico());
			}
		}

		List<Map> data = reportService.getReport(parameters,
				"consentimiento_inf_odonModel");
		// log.info("data>>>>>>>>>>>>>>>" + data);

		// String edad_paciente = null;

		/*
		 * for (int i = 0; i < data.size(); i++) { Map map = data.get(i);
		 * Timestamp fecha_nacimiento = (Timestamp) map
		 * .get("fecha_nacimiento"); if (edad_paciente == null) { Map<String,
		 * Integer> mapa_edades = Util .getEdadYYYYMMDD(fecha_nacimiento);
		 * 
		 * Integer anios = mapa_edades.get("anios"); Integer meses =
		 * mapa_edades.get("meses"); Integer dias = mapa_edades.get("dias");
		 * 
		 * if (anios.intValue() == 0 && meses.intValue() == 0) { edad_paciente =
		 * dias + (dias == 1 ? " día" : " días"); } else if (anios.intValue() ==
		 * 0) { edad_paciente = meses + (meses == 1 ? " mes (" : " meses (") +
		 * (dias + (dias == 1 ? " día" : " días")) + ")"; } else { int
		 * current_meses = meses.intValue() - (anios.intValue() * 12);
		 * edad_paciente = anios + (anios == 1 ? " año" : " años") +
		 * (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ?
		 * " mes " : " meses")) : ""); } }
		 * 
		 * map.put("edad_paciente", edad_paciente); }
		 */
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/consentimiento_inf_odon.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirGlosarEps() throws Exception {
		// log.info("@imprimirGlosarIps");
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);

		List<Map> data = reportContawebService.getReport(parameters,
				"glosasepsModel");

		parameters.put(JRParameter.REPORT_LOCALE, IConstantes.locale);

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/glosas_ips.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirConsolidadoGlosasTotalEps() throws Exception {
		// log.info("@imprimirConsolidadoGlosasTotalIps");
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);

		List<Map> data = reportContawebService.getReport(parameters,
				"consolidadoTotalEpsModel");

		parameters.put(JRParameter.REPORT_LOCALE, IConstantes.locale);

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/consolidado_glosas_ips.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirAgendaCitasCAPS() throws Exception {

		String formato = (String) paramRequest.get("formato");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		parameters.put("nombre_caps", (String) paramRequest.get("nombre_caps"));

		List<Citas> lista_citas = (List<Citas>) paramRequest.get("lista_citas");

		List<Map> data = new LinkedList<Map>();
		for (Citas citas : lista_citas) {
			Elemento elementoEstado = citas.getElementoEstado();

			Elemento elementoTipo_consulta = new Elemento();
			elementoTipo_consulta.setCodigo(citas.getTipo_consulta());
			elementoTipo_consulta.setTipo("via_ingreso");
			elementoTipo_consulta = elementoService
					.consultar(elementoTipo_consulta);

			Admision admision = new Admision();
			admision.setCodigo_empresa(citas.getCodigo_empresa());
			admision.setCodigo_sucursal(citas.getCodigo_sucursal());
			admision.setNro_identificacion(citas.getNro_identificacion());
			admision.setCodigo_cita(citas.getCodigo_cita());
			admision = admisionService.consultar(admision);

			String estado = citas.getEstado();
			String estado_admision = (admision != null ? admision.getEstado()
					: "");
			String estado_cita = (elementoEstado != null ? elementoEstado
					.getDescripcion() : "");
			if (estado_admision.equals("1")) {
				estado_cita = "Sala de espera";
				estado = "6";
			}
			if (estado.equals("1")) {
				estado_cita = "Por cumplir";
			} else if (estado.equals("4")) {
				estado_cita = "Por cumplir";
			} else if (estado.equals("5")) {
				estado_cita = "Cancelada";
			}

			Map map = new HashMap();
			map.put("codigo_empresa", citas.getCodigo_empresa());
			map.put("codigo_sucursal", citas.getCodigo_sucursal());
			map.put("codigo_cita", citas.getCodigo_cita());
			map.put("nro_identificacion", citas.getNro_identificacion());
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(citas.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);
			map.put("paciente", paciente != null ? paciente.getNombreCompleto()
					: "");
			map.put("codigo_prestador",
					citas.getPrestadores() != null ? citas
							.getCodigo_prestador() : "");
			map.put("prestador", citas.getPrestadores() != null ? citas
					.getPrestadores().getNombres()
					+ " "
					+ citas.getPrestadores().getApellidos() : "");
			map.put("fecha_cita",
					simpleDateFormat.format(citas.getFecha_cita()));
			map.put("hora", citas.getHora());
			map.put("via_ingreso",
					(elementoTipo_consulta != null ? elementoTipo_consulta
							.getDescripcion() : ""));
			map.put("estado", estado_cita);

			data.add(map);
		}
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/agenda_citas_caps1.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirFurtran() throws Exception {

		String empresa = (String) paramRequest.get("empresa");
		String sucursal = (String) paramRequest.get("sucursal");
		String consecutivo = (String) paramRequest.get("consecutivo");

		// String formato = (String) paramRequest.get("formato");
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();

		Map map = new HashMap();
		map.put("codigo_empresa", empresa);
		map.put("codigo_sucursal", sucursal);
		map.put("consecutivo", consecutivo);

		List<Map> data = reportService.getReport(map, "furtranModel");

		// log.info("data" + data);

		/* cargamos imagen del reporte */
		String SUBREPORT_DIR = context.getRealPath("/report/img/furtran.png");
		File file = new File(SUBREPORT_DIR);
		parameters.put("imagen", file);

		// log.info("imagen" + file);
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/furtran.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType("pdf");

	}

	public void imprimirFurips() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		String empresa = (String) paramRequest.get("empresa");
		String sucursal = (String) paramRequest.get("sucursal");
		String codigo_currips = (String) paramRequest.get("codigo_currips");

		String formato = (String) paramRequest.get("formato");

		// String formato = (String) paramRequest.get("formato");
		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report");

		parameters.put("codigo_empresa", empresa);
		parameters.put("codigo_sucursal", sucursal);
		parameters.put("codigo_currips", codigo_currips);

		/* Falta agregar las imagenes a cada subreporte */
		List<Map> data1 = reportService.getReport(parameters, "furips1Model");
		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);
		// log.info("DATAs1: " + Source1);

		List<Map> data2 = reportService.getReport(parameters, "furips2Model");

		for (Map map : data2) {
			String ix_nro_documento_medico = (String) map
					.get("ix_nro_documento_medico");
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(empresa);
			prestadores.setCodigo_sucursal(sucursal);
			prestadores.setNro_identificacion(ix_nro_documento_medico);
			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores != null) {
				String[] apellidos = Utilidades.separarApellido(prestadores
						.getApellidos());
				String[] nombres = Utilidades.separarNombre(prestadores
						.getNombres());

				map.put("ix_nro_registro_medico",
						"" + prestadores.getRegistro_medico());
				map.put("ix_primer_apellido_medico", "" + apellidos[0]);
				map.put("ix_segundo_apellido_medico", "" + apellidos[1]);
				map.put("ix_primer_nombre_medico", "" + nombres[0]);
				map.put("ix_segundo_nombre_medico", "" + nombres[1]);
				map.put("ix_tipo_documento_medico",
						"" + prestadores.getTipo_identificacion());
			} else {
				map.put("ix_nro_registro_medico", "");
				map.put("ix_primer_apellido_medico", "");
				map.put("ix_segundo_apellido_medico", "");
				map.put("ix_primer_nombre_medico", "");
				map.put("ix_segundo_nombre_medico", "");
				map.put("ix_tipo_documento_medico", "");
			}
		}

		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);
		// //log.info("DATA2: " + data2);
		// //log.info("DATAs2: " + Source2);

		parameters.put("dataSource1", Source1);
		parameters.put("dataSource2", Source2);
		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR);

		// log.info("Directorio: " + SUBREPORT_DIR);
		InputStream imagen = new FileInputStream(SUBREPORT_DIR
				+ "/img/furips1.png");
		parameters.put("imagen1", imagen);

		imagen = new FileInputStream(SUBREPORT_DIR + "/img/furips2.png");
		parameters.put("imagen2", imagen);

		// log.info("Parametros: " + parameters);
		// log.info("Cargar reporte");
		report.setSrc("/report/furips.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		// report.setDatasource(Source1);
		report.setType(formato);
	}

	public void imprimirHistoria_clinica_consulta_externa() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("codigo_historia", nro_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);

		List<Map> data1 = reportService.getReport(parameters,
				"historia_consulta_externaModel");
		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);
		// log.info("DATAs1: " + Source1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_consulta_externa2Model");
		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);
		// log.info("DATA2: " + data2);
		// log.info("DATAs1: " + Source2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_consulta_externa3Model");
		DataSourceReport Source3 = new DataSourceReport();
		Source3.loadReport(data3);
		// log.info("DATA3: " + data3);
		// log.info("DATAs1: " + Source3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_consulta_externa4Model");
		DataSourceReport Source4 = new DataSourceReport();
		Source4.loadReport(data4);
		// log.info("DATA4: " + data4);
		// log.info("DATAs1: " + Source4);

		List<Map> data5 = reportService.getReport(parameters,
				"historia_consulta_externa5Model");
		for (Map map : data5) {
			if (map.get("firma") != null) {
				byte[] firma = (byte[]) map.get("firma");
				InputStream aux = new ByteArrayInputStream(firma);
				map.put("firma", aux);
			}
		}
		DataSourceReport Source5 = new DataSourceReport();
		Source5.loadReport(data5);
		// log.info("DATA5: " + data5);
		// log.info("DATAs1: " + Source5);
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);
		// log.info("DATAs1: " + Source1);

		List<Map<String, Object>> listado_antecedentes = antecedentes_personalesService
				.listar_reporte(parameters);

		StringBuilder nombre_antecedente1 = new StringBuilder();
		StringBuilder valor_antecedente1 = new StringBuilder();

		StringBuilder nombre_antecedente2 = new StringBuilder();
		StringBuilder valor_antecedente2 = new StringBuilder();

		for (int i = 0; i < 17; i++) {
			Map<String, Object> mapa_datos = listado_antecedentes.get(i);
			nombre_antecedente1.append(mapa_datos.get("antecedente")).append(
					"\n");
			String respuesta = (String) mapa_datos.get("respuesta");
			if (respuesta != null && respuesta.equals("S")) {
				valor_antecedente1.append("SI. ")
						.append(mapa_datos.get("observacion")).append("\n");
			} else {
				valor_antecedente1.append("NO.").append("\n");
			}
		}

		for (int i = 17; i < listado_antecedentes.size(); i++) {
			Map<String, Object> mapa_datos = listado_antecedentes.get(i);
			nombre_antecedente2.append(mapa_datos.get("antecedente")).append(
					"\n");
			String respuesta = (String) mapa_datos.get("respuesta");
			if (respuesta != null && respuesta.equals("S")) {
				valor_antecedente2.append("SI. ")
						.append(mapa_datos.get("observacion")).append("\n");
			} else {
				valor_antecedente2.append("NO.").append("\n");
			}
		}

		parameters.put("nombre_antecedente1", nombre_antecedente1.toString());
		parameters.put("nombre_antecedente2", nombre_antecedente2.toString());
		parameters.put("valor_antecedente1", valor_antecedente1.toString());
		parameters.put("valor_antecedente2", valor_antecedente2.toString());

		// log.info("Data source>>>>>>" + Source1);
		parameters.put("dataSource1", Source1);
		parameters.put("dataSource2", Source2);
		parameters.put("dataSource3", Source3);
		parameters.put("dataSource4", Source4);
		parameters.put("dataSource5", Source5);
		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/reporte_macro_consulta_externa.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	public void imprimirHistoria_clinica_odontologica() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String formato = (String) paramRequest.get("formato");
		Map<String, Object> mapDientes = (Map<String, Object>) paramRequest
				.get("mapDientes");
		String porcentaje_manchado = (String) paramRequest
				.get("porcentaje_manchado");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("mapDientes", mapDientes);
		parameters.put("porcentaje_manchado", porcentaje_manchado);

		// log.info("parameters odontologia: " + parameters);
		List<Map> data1 = reportService.getReport(parameters,
				"historia_odontologia1Model");
		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);
		// log.info("DATAs1: " + Source1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_odontologia2Model");
		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);
		// log.info("DATA2: " + data2);
		// log.info("DATAs2: " + Source2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_odontologia3Model");
		DataSourceReport Source3 = new DataSourceReport();
		Source3.loadReport(data3);
		// log.info("DATA3: " + data3);
		// log.info("DATAs3: " + Source3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_odontologia4Model");
		DataSourceReport Source4 = new DataSourceReport();
		Source4.loadReport(data4);
		// log.info("DATA4: " + data4);
		// log.info("DATAs4: " + Source4);

		List<Map> data5 = reportService.getReport(parameters,
				"historia_odontologia5Model");
		for (Map map : data5) {
			if (map.get("firma") != null) {
				byte[] firma = (byte[]) map.get("firma");
				InputStream aux = new ByteArrayInputStream(firma);
				map.put("firma", aux);
			}
		}
		DataSourceReport Source5 = new DataSourceReport();
		Source5.loadReport(data5);
		// log.info("DATA5: " + data5);
		// //log.info("DATAs4: " + Source4);

		parameters.put("dataSource1", Source1);
		parameters.put("dataSource2", Source2);
		parameters.put("dataSource3", Source3);
		parameters.put("dataSource4", Source4);
		parameters.put("dataSource5", Source5);
		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/Historia_odontologia.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	public void imprimirHistoria_clinica_urgencia() throws Exception {
		imprimirHistoria_clinica_urgencia("URGENCIAS");
	}

	public void imprimirHistoria_clinica_hospitalizacion() throws Exception {
		imprimirHistoria_clinica_urgencia("HOSPITALIZAcion");
	}

	public void imprimirHistoria_clinica_urgencia(String titulo)
			throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("codigo_historia", nro_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("titulo", titulo);

		List<Map> data1 = reportService.getReport(parameters,
				"historia_urgenciaModel");
		DataSourceReport source1 = new DataSourceReport();
		source1.loadReport(data1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_urgencia2Model");
		DataSourceReport source2 = new DataSourceReport();
		source2.loadReport(data2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_urgencia3Model");
		DataSourceReport source3 = new DataSourceReport();
		source3.loadReport(data3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_urgencia4Model");
		for (Map map : data4) {
			if (map.get("firma") != null) {
				byte[] firma = (byte[]) map.get("firma");
				InputStream aux = new ByteArrayInputStream(firma);
				map.put("firma", aux);
			}
		}
		DataSourceReport source4 = new DataSourceReport();
		source4.loadReport(data4);

		List<Map<String, Object>> listado_antecedentes = antecedentes_personalesService
				.listar_reporte(parameters);

		StringBuilder nombre_antecedente1 = new StringBuilder();
		StringBuilder valor_antecedente1 = new StringBuilder();

		StringBuilder nombre_antecedente2 = new StringBuilder();
		StringBuilder valor_antecedente2 = new StringBuilder();

		for (int i = 0; i < 16; i++) {
			Map<String, Object> mapa_datos = listado_antecedentes.get(i);
			nombre_antecedente1.append(mapa_datos.get("antecedente")).append(
					"\n");
			String respuesta = (String) mapa_datos.get("respuesta");
			if (respuesta != null && respuesta.equals("S")) {
				valor_antecedente1
						.append("SI ")
						.append(mapa_datos.get("observacion") != null ? mapa_datos
								.get("observacion") : "").append("\n");
			} else {
				valor_antecedente1.append("NO").append("\n");
			}
		}

		for (int i = 16; i < listado_antecedentes.size(); i++) {
			Map<String, Object> mapa_datos = listado_antecedentes.get(i);
			nombre_antecedente2.append(mapa_datos.get("antecedente")).append(
					"\n");
			String respuesta = (String) mapa_datos.get("respuesta");
			if (respuesta != null && respuesta.equals("S")) {
				valor_antecedente2
						.append("SI ")
						.append(mapa_datos.get("observacion") != null ? mapa_datos
								.get("observacion") : "").append("\n");
			} else {
				valor_antecedente2.append("NO").append("\n");
			}
		}

		parameters.put("nombre_antecedente1", nombre_antecedente1.toString());
		parameters.put("nombre_antecedente2", nombre_antecedente2.toString());
		parameters.put("valor_antecedente1", valor_antecedente1.toString());
		parameters.put("valor_antecedente2", valor_antecedente2.toString());

		Hisc_urgencia urgencia = new Hisc_urgencia();
		urgencia.setCodigo_empresa(codigo_empresa);
		urgencia.setCodigo_sucursal(codigo_sucursal);
		urgencia.setCodigo_historia(nro_historia);
		urgencia = hisc_urgenciaService.consultar(urgencia);

		String no_disponible = "no tiene";

		if (urgencia != null) {
			if (urgencia.getAnte_fam_hipertension() != null
					&& !urgencia.getAnte_fam_hipertension().isEmpty()) {
				parameters.put("ante_fam_hipertension",
						obtenerDatosXml(urgencia.getAnte_fam_hipertension()));
			} else {
				parameters.put("ante_fam_hipertension", no_disponible);
			}
			if (urgencia.getAnte_fam_obesos() != null
					&& !urgencia.getAnte_fam_obesos().isEmpty()) {
				parameters.put("ante_fam_obesos",
						obtenerDatosXml(urgencia.getAnte_fam_obesos()));
			} else {
				parameters.put("ante_fam_obesos", no_disponible);
			}
			if (urgencia.getAnte_fam_ecv() != null
					&& !urgencia.getAnte_fam_ecv().isEmpty()) {
				parameters.put("ante_fam_ecv",
						obtenerDatosXml(urgencia.getAnte_fam_ecv()));
			} else {
				parameters.put("ante_fam_ecv", no_disponible);
			}
			if (urgencia.getAnte_fam_diabetes() != null
					&& !urgencia.getAnte_fam_diabetes().isEmpty()) {
				parameters.put("ante_fam_diabetes",
						obtenerDatosXml(urgencia.getAnte_fam_diabetes()));
			} else {
				parameters.put("ante_fam_diabetes", no_disponible);
			}
			if (urgencia.getAnte_fam_enf_coronaria() != null
					&& !urgencia.getAnte_fam_enf_coronaria().isEmpty()) {
				parameters.put("ante_fam_enf_coronaria",
						obtenerDatosXml(urgencia.getAnte_fam_enf_coronaria()));
			} else {
				parameters.put("ante_fam_enf_coronaria", no_disponible);
			}
			if (urgencia.getAnte_fam_enf_mental() != null
					&& !urgencia.getAnte_fam_enf_mental().isEmpty()) {
				parameters.put("ante_fam_enf_mental",
						obtenerDatosXml(urgencia.getAnte_fam_enf_mental()));
			} else {
				parameters.put("ante_fam_enf_mental", no_disponible);
			}
			if (urgencia.getAnte_fam_muerte_im_acv() != null
					&& !urgencia.getAnte_fam_muerte_im_acv().isEmpty()) {
				parameters.put("ante_fam_muerte_im_acv",
						obtenerDatosXml(urgencia.getAnte_fam_muerte_im_acv()));
			} else {
				parameters.put("ante_fam_muerte_im_acv", no_disponible);
			}
			if (urgencia.getAnte_fam_cancer() != null
					&& !urgencia.getAnte_fam_cancer().isEmpty()) {
				parameters.put("ante_fam_cancer",
						obtenerDatosXml(urgencia.getAnte_fam_cancer()));
			} else {
				parameters.put("ante_fam_cancer", no_disponible);
			}
			if (urgencia.getAnte_fam_dislipidemia() != null
					&& !urgencia.getAnte_fam_dislipidemia().isEmpty()) {
				parameters.put("ante_fam_dislipidemia",
						obtenerDatosXml(urgencia.getAnte_fam_dislipidemia()));
			} else {
				parameters.put("ante_fam_dislipidemia", no_disponible);
			}
			if (urgencia.getAnte_fam_hematologia() != null
					&& !urgencia.getAnte_fam_hematologia().isEmpty()) {
				parameters.put("ante_fam_hematologia",
						obtenerDatosXml(urgencia.getAnte_fam_hematologia()));
			} else {
				parameters.put("ante_fam_hematologia", no_disponible);
			}
			if (urgencia.getAnte_fam_alergicos() != null
					&& !urgencia.getAnte_fam_alergicos().isEmpty()) {
				parameters.put("ante_fam_alergicos",
						obtenerDatosXml(urgencia.getAnte_fam_alergicos()));
			} else {
				parameters.put("ante_fam_alergicos", no_disponible);
			}
			if (urgencia.getAnte_fam_infecciosa_vih() != null
					&& !urgencia.getAnte_fam_infecciosa_vih().isEmpty()) {
				parameters.put("ante_fam_infecciosa_vih",
						obtenerDatosXml(urgencia.getAnte_fam_infecciosa_vih()));
			} else {
				parameters.put("ante_fam_infecciosa_vih", no_disponible);
			}
			if (urgencia.getAnte_fam_infecciosa_sifilis() != null
					&& !urgencia.getAnte_fam_infecciosa_sifilis().isEmpty()) {
				parameters.put("ante_fam_infecciosa_sifilis",
						obtenerDatosXml(urgencia
								.getAnte_fam_infecciosa_sifilis()));
			} else {
				parameters.put("ante_fam_infecciosa_sifilis", no_disponible);
			}
			if (urgencia.getAnte_fam_infecciosa_hepatitisb() != null
					&& !urgencia.getAnte_fam_infecciosa_hepatitisb().isEmpty()) {
				parameters.put("ante_fam_infecciosa_hepatitisb",
						obtenerDatosXml(urgencia
								.getAnte_fam_infecciosa_hepatitisb()));
			} else {
				parameters.put("ante_fam_infecciosa_hepatitisb", no_disponible);
			}
			if (urgencia.getAnte_fam_infecciosa_tuberculosis() != null
					&& !urgencia.getAnte_fam_infecciosa_tuberculosis()
							.isEmpty()) {
				parameters.put("ante_fam_infecciosa_tuberculosis",
						obtenerDatosXml(urgencia
								.getAnte_fam_infecciosa_tuberculosis()));
			} else {
				parameters.put("ante_fam_infecciosa_tuberculosis",
						no_disponible);
			}
			if (urgencia.getAnte_fam_infecciosa_lepra() != null
					&& !urgencia.getAnte_fam_infecciosa_lepra().isEmpty()) {
				parameters
						.put("ante_fam_infecciosa_lepra",
								obtenerDatosXml(urgencia
										.getAnte_fam_infecciosa_lepra()));
			} else {
				parameters.put("ante_fam_infecciosa_lepra", no_disponible);
			}
			if (urgencia.getAnte_fam_nefropatias() != null
					&& !urgencia.getAnte_fam_nefropatias().isEmpty()) {
				parameters.put("ante_fam_nefropatias",
						obtenerDatosXml(urgencia.getAnte_fam_nefropatias()));
			} else {
				parameters.put("ante_fam_nefropatias", no_disponible);
			}
			if (urgencia.getAnte_fam_asma() != null
					&& !urgencia.getAnte_fam_asma().isEmpty()) {
				parameters.put("ante_fam_asma",
						obtenerDatosXml(urgencia.getAnte_fam_asma()));
			} else {
				parameters.put("ante_fam_asma", no_disponible);
			}
			if (urgencia.getAnte_fam_otros() != null
					&& !urgencia.getAnte_fam_otros().isEmpty()) {
				parameters.put("ante_fam_otros",
						obtenerDatosXml(urgencia.getAnte_fam_otros()));
			} else {
				parameters.put("ante_fam_otros", no_disponible);
			}
		}

		parameters.put("dataSource1", source1);
		parameters.put("dataSource2", source2);
		parameters.put("dataSource3", source3);
		parameters.put("dataSource4", source4);
		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/reporte_macro_urgencia.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	private String obtenerDatosXml(String xml) {
		String resultado = "";
		Map mapa = ConvertidorXmlToMap.convertirToMap(xml);
		Iterator entries = mapa.entrySet().iterator();
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			Object value = thisEntry.getValue();
			if (!(key.equals("otros") && value.equals("otros"))) {
				resultado += value + ", ";
			}
		}

		if (!resultado.isEmpty()) {
			resultado = resultado.substring(0, resultado.length() - 2);
		}
		return resultado.replaceAll("_", " ");
	}

	public void imprimirEpicrisis_ese() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("codigo_historia", nro_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);

		List<Map> data1 = reportService.getReport(parameters,
				"epicrisis_eseModel");
		DataSourceReport source1 = new DataSourceReport();
		source1.loadReport(data1);

		List<Map> data2 = reportService.getReport(parameters,
				"epicrisis_ese3Model");
		DataSourceReport source2 = new DataSourceReport();
		source2.loadReport(data2);

		// log.info("Data source>>>>>>" + source1);
		parameters.put("dataSource1", source1);
		parameters.put("dataSource2", source2);
		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/reporte_macro_epicrisis_ese.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	public void imprimirAnexo3_entidad() throws Exception {
		// HttpServletRequest request = (HttpServletRequest) Executions
		// .getCurrent().getNativeRequest();
		String formato = (String) paramRequest.get("formato");

		String codigo_orden = (String) paramRequest.get("codigo_orden");
		// log.info("codigo_orden en la impresion ===> " + codigo_orden);

		parameters.put("codigo_orden", codigo_orden);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);

		List<Map> data1 = reportService.getReport(parameters,
				"anexo3_entidad2Model");

		// log.info("Datos de la consulta ===> " + data1);
		DataSourceReport source1 = new DataSourceReport();
		source1.loadReport(data1);

		report.setSrc("/report/reporte_anexo3_nuevo.jasper");
		report.setParameters(parameters);
		report.setDatasource(source1);
		report.setType(formato);

	}

	public void imprimirAuditoriaPacientes() throws Exception {
		String formato = (String) paramRequest.get("formato");

		List<Map<String, Object>> list = (List<Map<String, Object>>) paramRequest
				.get("listado");
		DataSourceReport source1 = new DataSourceReport();
		source1.loadReport(list);

		report.setSrc("/report/auditoria_pacientes.jasper");
		report.setParameters(parameters);
		report.setDatasource(source1);
		report.setType(formato);

	}

	public void imprimirListado_admisiones() {
		String formato = (String) paramRequest.get("formato");
		parameters.put("nombre_reporte",
				(String) paramRequest.get("nombre_reporte"));
		List<Admision> listado_admisiones = (List<Admision>) paramRequest
				.get("listado_admisiones");

		List<Elemento> listado_vias = elementoService.listar("via_ingreso");
		Map<String, String> vias = new HashMap<String, String>();
		for (Elemento elemento : listado_vias) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}

		Elemento elemento = new Elemento();

		List<Map> data = new LinkedList<Map>();
		for (Admision admision : listado_admisiones) {
			admision = admisionService.consultar(admision);
			elemento.setCodigo(admision.getMarca_admision());
			elemento.setTipo("marca_admision");
			elemento = elementoService.consultar(elemento);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", admision.getCodigo_empresa());
			map.put("codigo_sucursal", admision.getCodigo_sucursal());
			map.put("fecha_ingreso", admision.getFecha_ingreso());
			map.put("nro_identificacion", admision.getNro_identificacion());
			map.put("paciente", admision.getPaciente().getNombreCompleto());
			map.put("ingreso", vias.get(admision.getVia_ingreso()));
			map.put("administradora", admision.getAdministradora().getNombre());
			map.put("facturado", admision.getEstado().equals("2") ? "S" : "N");
			map.put("atentido", admision.getAtendida() ? "S" : "N");
			map.put("marca", elemento.getDescripcion());
			data.add(map);
		}
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/listado_admisiones.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	public void imprimirListado_citas() {
		String formato = (String) paramRequest.get("formato");
		parameters.put("nombre_reporte",
				(String) paramRequest.get("nombre_reporte"));
		List<Citas> listado_citas = (List<Citas>) paramRequest
				.get("listado_citas");

		List<Elemento> listado_vias = elementoService.listar("via_ingreso");
		Map<String, String> vias = new HashMap<String, String>();
		for (Elemento elemento : listado_vias) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}

		List<Elemento> listado_estados = elementoService.listar("estado_cita");
		Map<String, String> estados = new HashMap<String, String>();
		for (Elemento elemento : listado_estados) {
			estados.put(elemento.getCodigo(), elemento.getDescripcion());
		}

		List<Map> data = new LinkedList<Map>();
		for (Citas cita : listado_citas) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(cita.getCodigo_empresa());
			paciente.setCodigo_sucursal(cita.getCodigo_sucursal());
			paciente.setNro_identificacion(cita.getNro_identificacion());
			paciente = pacienteService.consultar(paciente);

			Prestadores prestador = new Prestadores();
			prestador.setCodigo_empresa(cita.getCodigo_empresa());
			prestador.setCodigo_sucursal(cita.getCodigo_sucursal());
			prestador.setNro_identificacion(cita.getCodigo_prestador());
			prestador = prestadoresService.consultar(prestador);

			Map map = new HashMap();
			map.put("codigo_empresa", cita.getCodigo_empresa());
			map.put("codigo_sucursal", cita.getCodigo_sucursal());
			map.put("fecha_cita", cita.getFecha_cita());
			map.put("nro_identificacion", cita.getNro_identificacion());
			map.put("paciente", paciente != null ? paciente.getNombreCompleto()
					: "");
			map.put("prestador", prestador != null ? prestador.getNombres()
					+ " " + prestador.getApellidos() : "");
			map.put("via", vias.get(cita.getTipo_consulta()));
			map.put("estado", estados.get(cita.getEstado()));
			data.add(map);
		}
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/listado_citas.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);
	}

	public void imprimirListado_variables2193() {
		String formato = (String) paramRequest.get("formato");
		parameters.put("nombre_reporte",
				(String) paramRequest.get("nombre_reporte"));
		Map<String, List<Map<String, Object>>> mapa_variables = (Map<String, List<Map<String, Object>>>) paramRequest
				.get("listado_variables");

		List<Elemento> listado_variables = elementoService
				.listar("variable_2193");
		Map<String, String> variables = new HashMap<String, String>();
		for (Elemento elemento : listado_variables) {
			variables.put(elemento.getCodigo(), elemento.getDescripcion());
		}
		List<Map> data = new LinkedList<Map>();

		for (Map.Entry<String, List<Map<String, Object>>> entry : mapa_variables
				.entrySet()) {
			Map map = new HashMap();
			map.put("variable2193", variables.get(entry.getKey()));
			List<Map<String, Object>> lista = mapa_variables
					.get(entry.getKey());
			Long total = 0l;
			for (Map<String, Object> map2 : lista) {
				String tipo = (String) map2.get("tipo_usuario");
				Long cantidad = (Long) map2.get("cant");
				if (tipo.equalsIgnoreCase("-1")) {
					map.put("codigo_0", 0l);
					map.put("codigo_1", 0l);
					map.put("codigo_2", 0l);
					map.put("codigo_3", 0l);
					total = cantidad;
					break;
				} else if (tipo.equalsIgnoreCase("1")) {
					total += cantidad;
					map.put("codigo_2", cantidad);
				} else if (tipo.equalsIgnoreCase("2")
						|| tipo.equalsIgnoreCase("6")) {
					total += cantidad;
					Long cant = (Long) (map.get("codigo_1") != null ? map
							.get("codigo_1") : 0l);
					map.put("codigo_1", cantidad + cant);
				} else if (tipo.equalsIgnoreCase("5")
						|| tipo.equalsIgnoreCase("3")) {
					total += cantidad;
					Long cant = (Long) (map.get("codigo_3") != null ? map
							.get("codigo_3") : 0l);
					map.put("codigo_3", cantidad + cant);
				} else if (tipo.equalsIgnoreCase("7")
						|| tipo.equalsIgnoreCase("8")) {
					total += cantidad;
					Long cant = (Long) (map.get("codigo_0") != null ? map
							.get("codigo_0") : 0l);
					map.put("codigo_0", cantidad + cant);
				}
			}

			map.put("codigo_4", total);
			data.add(map);

		}

		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/listado_variables2193.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType(formato);

	}

	/*
	 * Historias PYP
	 */
	public void imprimirHistoria_pyp_planificacion_familiar() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String sexo = (String) paramRequest.get("sexo");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("codigo_historia", nro_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("sexo", sexo);

		// log.info("sexo" + sexo);
		List<Map> data1 = reportService.getReport(parameters,
				"historia_pyp_planificacion_familiarModel");
		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_pyp_planificacion_familiar2Model");
		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);
		// log.info("DATA2: " + data2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_pyp_planificacion_familiar3Model");
		DataSourceReport Source3 = new DataSourceReport();
		Source3.loadReport(data3);
		// log.info("DATA3: " + data3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_pyp_planificacion_familiar4Model");
		DataSourceReport Source4 = new DataSourceReport();
		Source4.loadReport(data4);
		// log.info("DATA4: " + data4);

		parameters.put("dataSource1", Source1);
		parameters.put("dataSource2", Source2);
		parameters.put("dataSource3", Source3);
		parameters.put("dataSource4", Source4);

		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/historia_pyp_planificacion_familiar.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	public void imprimirHistoria_pyp_adulto_mayor() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String sexo = (String) paramRequest.get("sexo");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("codigo_historia", nro_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("sexo", sexo);

		// log.info("sexo" + sexo);
		List<Map> data1 = reportService.getReport(parameters,
				"historia_pyp_adulto_mayorModel");

		// log.info("data1" + data1);
		for (Map map : data1) {
			if (map.get("cardio_mas") != null) {
				// cardio_fem
				String cardio_mas = (String) map.get("cardio_mas");
				String mas = Utilidades.mostrarXml(
						ConvertidorXmlToMap.convertirToMap(cardio_mas), ";");
				map.put("cardio_mas", mas);
			}
			if (map.get("cardio_fem") != null) {
				String cardio_mas = (String) map.get("cardio_fem");
				String fem = Utilidades.mostrarXml(
						ConvertidorXmlToMap.convertirToMap(cardio_mas), ";");
				map.put("cardio_fem", fem);
			}
			// log.info("---------");
		}

		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_pyp_adulto_mayor2Model");
		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);
		// log.info("DATA2: " + data2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_pyp_adulto_mayor3Model");
		DataSourceReport Source3 = new DataSourceReport();
		Source3.loadReport(data3);
		// log.info("DATA3: " + data3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_pyp_adulto_mayor4Model");
		DataSourceReport Source4 = new DataSourceReport();
		Source4.loadReport(data4);
		// log.info("DATA4: " + data4);

		parameters.put("dataSource1", Source1);

		parameters.put("dataSource2", Source2);
		parameters.put("dataSource3", Source3);
		parameters.put("dataSource4", Source4);

		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/historia_pyp_adulto_mayor.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	public void imprimirHistoria_pyp_hipertenso() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String sexo = (String) paramRequest.get("sexo");
		String formato = (String) paramRequest.get("formato");

		Integer colesterol = (Integer) paramRequest.get("colesterol");
		Integer hdl = (Integer) paramRequest.get("hdl");
		String tabaquismo = (String) paramRequest.get("tabaquismo");
		Integer edad = (Integer) paramRequest.get("edad");
		Double sistolica = (Double) paramRequest.get("sistolica");

		Integer puntos = (Integer) paramRequest.get("puntos");
		String riesgo = (String) paramRequest.get("riesgo");
		Integer porcentaje = (Integer) paramRequest.get("porcentaje");

		String codigo_medico = (String) paramRequest.get("codigo_medico");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("codigo_historia", nro_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("sexo", sexo);
		parameters.put("edad", edad);
		parameters.put("sistolica", sistolica);
		parameters.put("tabaquismo", tabaquismo);
		parameters.put("puntos", puntos);
		parameters.put("riesgo", riesgo);
		parameters.put("porcentaje", porcentaje);
		parameters.put("colesterol", colesterol);
		parameters.put("hdl", hdl);
		parameters.put("codigo_medico", codigo_medico);

		// log.info("sexo" + sexo);
		List<Map> data1 = reportService.getReport(parameters,
				"historia_pyp_hipertensoModel");
		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_pyp_hipertenso2Model");

		for (Map map : data2) {

			// log.info("---------" + map.get("mdant_nombre"));
			if (map.get("mdant_nombre") != null) {
				String mdant_nombre = (String) map.get("mdant_nombre");

				String nombre1 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_nombre, 0);
				// log.info("---------" + nombre1);
				map.put("mdant_nombre1", nombre1);
				String nombre2 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_nombre, 1);
				// log.info("---------" + nombre2);
				map.put("mdant_nombre2", nombre2);
				String nombre3 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_nombre, 2);
				map.put("mdant_nombre3", nombre3);
				String nombre4 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_nombre, 3);
				map.put("mdant_nombre4", nombre4);
				String nombre5 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_nombre, 4);
				map.put("mdant_nombre5", nombre5);

			}

			if (map.get("mdant_presentacion") != null) {
				String mdant_presentacion = (String) map
						.get("mdant_presentacion");

				String presentacion1 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_presentacion, 0);
				map.put("mdant_presentacion1", presentacion1);
				String presentacion2 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_presentacion, 1);
				map.put("mdant_presentacion2", presentacion2);
				String presentacion3 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_presentacion, 2);
				map.put("mdant_presentacion3", presentacion3);
				String presentacion4 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_presentacion, 3);
				map.put("mdant_presentacion4", presentacion4);
				String presentacion5 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_presentacion, 4);
				map.put("mdant_presentacion5", presentacion5);

			}

			if (map.get("mdant_dosis") != null) {
				String mdant_dosis = (String) map.get("mdant_dosis");

				String dosis1 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_dosis, 0);
				map.put("mdant_dosis1", dosis1);
				String dosis2 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_dosis, 1);
				map.put("mdant_dosis2", dosis2);
				String dosis3 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_dosis, 2);
				map.put("mdant_dosis3", dosis3);
				String dosis4 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_dosis, 3);
				map.put("mdant_dosis4", dosis4);
				String dosis5 = Utilidades.setValoresMedicamentosAnteriores(
						mdant_dosis, 4);
				map.put("mdant_dosis5", dosis5);

			}

			if (map.get("mdant_uso_hasta") != null) {
				String mdant_uso_hasta = (String) map.get("mdant_uso_hasta");

				String uso_hasta1 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_uso_hasta, 0);
				map.put("mdant_uso_hasta1", uso_hasta1);
				String uso_hasta2 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_uso_hasta, 1);
				map.put("mdant_uso_hasta2", uso_hasta2);
				String uso_hasta3 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_uso_hasta, 2);
				map.put("mdant_uso_hasta3", uso_hasta3);
				String uso_hasta4 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_uso_hasta, 3);
				map.put("mdant_uso_hasta4", uso_hasta4);
				String uso_hasta5 = Utilidades
						.setValoresMedicamentosAnteriores(mdant_uso_hasta, 4);
				map.put("mdant_uso_hasta5", uso_hasta5);

			}

			if (map.get("mdant_causa_suspension") != null) {
				String mdant_causa_suspension = (String) map
						.get("mdant_causa_suspension");

				String causa_suspension1 = Utilidades
						.setValoresMedicamentosAnteriores(
								mdant_causa_suspension, 0);
				map.put("mdant_causa_suspension1", causa_suspension1);
				String causa_suspension2 = Utilidades
						.setValoresMedicamentosAnteriores(
								mdant_causa_suspension, 1);
				map.put("mdant_causa_suspension2", causa_suspension2);
				String causa_suspension3 = Utilidades
						.setValoresMedicamentosAnteriores(
								mdant_causa_suspension, 2);
				map.put("mdant_causa_suspension3", causa_suspension3);
				String causa_suspension4 = Utilidades
						.setValoresMedicamentosAnteriores(
								mdant_causa_suspension, 3);
				map.put("mdant_causa_suspension4", causa_suspension4);
				String causa_suspension5 = Utilidades
						.setValoresMedicamentosAnteriores(
								mdant_causa_suspension, 4);
				map.put("mdant_causa_suspension5", causa_suspension5);

			}

			if (map.get("mdact_nombre") != null) {
				String mdact_nombre = (String) map.get("mdact_nombre");

				String nombre1 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_nombre, 0);
				map.put("mdact_nombre1", nombre1);
				String nombre2 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_nombre, 1);
				map.put("mdact_nombre2", nombre2);
				String nombre3 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_nombre, 2);
				map.put("mdact_nombre3", nombre3);
				String nombre4 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_nombre, 3);
				map.put("mdact_nombre4", nombre4);
				String nombre5 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_nombre, 4);
				map.put("mdact_nombre5", nombre5);

			}

			if (map.get("mdact_presentacion") != null) {
				String mdact_presentacion = (String) map
						.get("mdact_presentacion");

				String presentacion1 = Utilidades
						.setValoresMedicamentosAnteriores(mdact_presentacion, 0);
				map.put("mdact_presentacion1", presentacion1);
				String presentacion2 = Utilidades
						.setValoresMedicamentosAnteriores(mdact_presentacion, 1);
				map.put("mdact_presentacion2", presentacion2);
				String presentacion3 = Utilidades
						.setValoresMedicamentosAnteriores(mdact_presentacion, 2);
				map.put("mdact_presentacion3", presentacion3);
				String presentacion4 = Utilidades
						.setValoresMedicamentosAnteriores(mdact_presentacion, 3);
				map.put("mdact_presentacion4", presentacion4);
				String presentacion5 = Utilidades
						.setValoresMedicamentosAnteriores(mdact_presentacion, 4);
				map.put("mdact_presentacion5", presentacion5);

			}

			if (map.get("mdact_dosis") != null) {
				String mdact_dosis = (String) map.get("mdact_dosis");

				String dosis1 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_dosis, 0);
				map.put("mdact_dosis1", dosis1);
				String dosis2 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_dosis, 1);
				map.put("mdact_dosis2", dosis2);
				String dosis3 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_dosis, 2);
				map.put("mdact_dosis3", dosis3);
				String dosis4 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_dosis, 3);
				map.put("mdact_dosis4", dosis4);
				String dosis5 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_dosis, 4);
				map.put("mdact_dosis5", dosis5);

			}

			if (map.get("mdact_fecha_inicio") != null) {
				String mdact_fecha_inicio = (String) map
						.get("mdact_fecha_inicio");

				String inicio1 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_fecha_inicio, 0);
				map.put("mdact_fecha_inicio1", inicio1);
				String inicio2 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_fecha_inicio, 1);
				map.put("mdact_fecha_inicio2", inicio2);
				String inicio3 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_fecha_inicio, 2);
				map.put("mdact_fecha_inicio3", inicio3);
				String inicio4 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_fecha_inicio, 3);
				map.put("mdact_fecha_inicio4", inicio4);
				String inicio5 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_fecha_inicio, 4);
				map.put("mdact_fecha_inicio5", inicio5);

			}

			if (map.get("mdact_reaccion") != null) {
				String mdact_reaccion = (String) map.get("mdact_reaccion");

				String reaccion1 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_reaccion, 0);
				map.put("mdact_reaccion1", reaccion1);
				String reaccion2 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_reaccion, 1);
				map.put("mdact_reaccion2", reaccion2);
				String reaccion3 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_reaccion, 2);
				map.put("mdact_reaccion3", reaccion3);
				String reaccion4 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_reaccion, 3);
				map.put("mdact_reaccion4", reaccion4);
				String reaccion5 = Utilidades.setValoresMedicamentosAnteriores(
						mdact_reaccion, 4);
				map.put("mdact_reaccion5", reaccion5);

			}

		}

		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_pyp_hipertenso3Model");
		DataSourceReport Source3 = new DataSourceReport();
		Source3.loadReport(data3);
		// log.info("DATA3: " + data3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_pyp_hipertenso4Model");
		DataSourceReport Source4 = new DataSourceReport();
		Source4.loadReport(data4);
		// log.info("DATA4: " + data4);

		List<Map> data5 = reportService.getReport(parameters,
				"historia_pyp_hipertenso5Model");

		DataSourceReport Source5 = new DataSourceReport();
		Source5.loadReport(data5);
		// log.info("DATA5: " + data5);

		parameters.put("dataSource1", Source1);
		parameters.put("dataSource2", Source2);
		parameters.put("dataSource3", Source3);
		parameters.put("dataSource4", Source4);
		parameters.put("dataSource5", Source5);
		// log.info("dataSource5: " + Source5);

		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/historia_pyp_hipertenso.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	public void imprimirHistoria_pyp_embarazo() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String sexo = (String) paramRequest.get("sexo");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("codigo_historia", nro_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("sexo", sexo);

		// log.info("sexo" + sexo);

		/*
		 * InputStream img_uterina = (InputStream)
		 * paramRequest.get("img_uterina");
		 * 
		 * if(img_uterina != null){ parameters.put("img_uterina", img_uterina);
		 * 
		 * //log.info("---------"+img_uterina); }
		 * 
		 * InputStream img_materna= (InputStream)
		 * paramRequest.get("img_materna");
		 * 
		 * if(img_materna != null){ parameters.put("img_materna", img_materna);
		 * 
		 * //log.info("---------"+img_materna); }
		 * 
		 * 
		 * InputStream img_diastolica= (InputStream)
		 * paramRequest.get("img_diastolica");
		 * 
		 * if(img_diastolica != null){ parameters.put("img_diastolica",
		 * img_diastolica);
		 * 
		 * //log.info("---------"+img_diastolica); }
		 */
		List<Map> data1 = reportService.getReport(parameters,
				"historia_pyp_embarazoModel");
		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_pyp_embarazo2Model");
		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);
		// log.info("DATA2: " + data2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_pyp_embarazo3Model");
		DataSourceReport Source3 = new DataSourceReport();
		Source3.loadReport(data3);
		// log.info("DATA3: " + data3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_pyp_embarazo4Model");
		DataSourceReport Source4 = new DataSourceReport();
		Source4.loadReport(data4);
		// log.info("DATA4: " + data4);

		parameters.put("dataSource1", Source1);
		parameters.put("dataSource2", Source2);
		parameters.put("dataSource3", Source3);
		parameters.put("dataSource4", Source4);

		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/historia_pyp_embarazo.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	public void imprimirHistoria_pyp_menor_2meses() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		String sexo = (String) paramRequest.get("sexo");
		String formato = (String) paramRequest.get("formato");

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath("/report/");

		parameters.put("nro_historia", nro_historia);
		parameters.put("codigo_historia", nro_historia);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("sexo", sexo);

		// log.info("sexo" + sexo);
		List<Map> data1 = reportService.getReport(parameters,
				"historia_pyp_menor_2mesesModel");
		DataSourceReport Source1 = new DataSourceReport();
		Source1.loadReport(data1);
		// log.info("DATA1: " + data1);

		List<Map> data2 = reportService.getReport(parameters,
				"historia_pyp_menor_2meses2Model");
		DataSourceReport Source2 = new DataSourceReport();
		Source2.loadReport(data2);
		// log.info("DATA2: " + data2);

		List<Map> data3 = reportService.getReport(parameters,
				"historia_pyp_menor_2meses3Model");
		DataSourceReport Source3 = new DataSourceReport();
		Source3.loadReport(data3);
		// log.info("DATA3: " + data3);

		List<Map> data4 = reportService.getReport(parameters,
				"historia_pyp_menor_2meses4Model");
		DataSourceReport Source4 = new DataSourceReport();
		Source4.loadReport(data4);
		// log.info("DATA4: " + data4);

		parameters.put("dataSource1", Source1);
		parameters.put("dataSource2", Source2);
		parameters.put("dataSource3", Source3);
		parameters.put("dataSource4", Source4);

		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/historia_pyp_menor_2meses.jasper");
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType(formato);

	}

	/*
	 * Fichas Epidemiologicas
	 */
	public void imprimirFicha_epidemiologia() throws Exception {

		String empresa = (String) paramRequest.get("empresa");
		String sucursal = (String) paramRequest.get("sucursal");
		String codigo_ficha = (String) paramRequest.get("codigo_ficha");
		String ficha_epidemiologia = (String) paramRequest
				.get("ficha_epidemiologia");

		// String formato = (String) paramRequest.get("formato");
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		ServletContext context = request.getSession().getServletContext();

		Map map = new HashMap();
		map.put("codigo_empresa", empresa);
		map.put("codigo_sucursal", sucursal);
		map.put("codigo_ficha", codigo_ficha);

		if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.ACCIDENTE_OFIDICO)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n1Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n1.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n1.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.BAJO_PESO_AL_NACER)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.ENFERMEDAD_CHAGAS)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n3Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n3.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n3.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.DENGUE)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n4Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n4.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n4.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.ESAVI)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.FIEBRE_AMARILLA)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n6Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n6.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n6.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.HEPATITIS_B)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n7Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n7.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n7.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.ENFERMEDAD_POR_ALIMENTOS)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n8Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n8.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n8.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.INTOXICACION)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n9Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n9.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n9.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.SALUD_PUBLICA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.LEISHMANIASIS_CUTANEA)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n11Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n11.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n11.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.LEPRA)) {
			/*
			 * List<Map> data = reportService.getReport(map,
			 * "ficha_epidemiologia_n12Model");
			 * 
			 * //log.info("data" + data);
			 * 
			 * // cargamos imagen del reporte String SUBREPORT_DIR =
			 * context.getRealPath("/report/img/ficha_n12.png"); File file = new
			 * File(SUBREPORT_DIR); parameters.put("imagen", file);
			 * 
			 * //log.info("imagen" + file);
			 * 
			 * DataSourceReport dataSource = new DataSourceReport();
			 * dataSource.loadReport(data);
			 * 
			 * report.setSrc("/report/ficha_epidemiologia_n12.jasper");
			 * report.setParameters(parameters);
			 * report.setDatasource(dataSource); report.setType("pdf");
			 */
		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.LEPTOSPIROSIS)) {

		} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.MALARIA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.MALARIA_COMPLICADA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.MENINGITIS)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.PARAISIS_FLACIDA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.TOSFERINA)) {

		} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.LEUCEMIA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.DIARREICA_AGUDA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.AGRESIONES_POR_ANIMALES)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.VIGILANCIA_CENTINELA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.RABIA_HUMANA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.RABIA_ANIMAL)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.TUBERCULOSIS_PULMONAR)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.MORTALIDAD_MATERNA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.LESIONES_POR_POLVORA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.ANOMALIAS_CONGENITAS)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.EXPOSICION_FLUOR)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.MORBILIDAD_MATERNA_EXTREMA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.MORTALIDAD_DESNUTRICION)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.TUBERCULOSIS_FARMACORRESISTENTE)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.VIOLENCIA_INTRAFAMILIAR)) {

		} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.RUBEOLA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.SARAMPION)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.SIFILIS_CONGENITA)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.SIFILIS_GESTIONAL)) {

			List<Map> data = reportService.getReport(map,
					"ficha_epidemiologia_n39Model");

			// log.info("data" + data);
			// cargamos imagen del reporte
			String SUBREPORT_DIR = context
					.getRealPath("/report/img/ficha_n39.png");
			File file = new File(SUBREPORT_DIR);
			parameters.put("imagen", file);

			// log.info("imagen" + file);
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/ficha_epidemiologia_n39.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.TETANOS_ACCIDENTAL)) {

		} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.VIH)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.NOTIFICACION_SALUD_MENTAL)) {

		} else if (ficha_epidemiologia
				.equals(IFichas_epidemiologicas.NOTIFICACION_CONSUMO_SPA)) {

		}

	}

	/*
	 * Fin Fichas Epidemiologicas
	 */
	public void imprimirRedPrestadores2() {
		parameters.put("nameAdmin",
				"" + usuarios.getNombres() + " " + usuarios.getApellidos());
		List<Map> data = reportService.getReport(parameters,
				"red_prestadores2Model");
		DataSourceReport dataSource = new DataSourceReport();
		dataSource.loadReport(data);

		report.setSrc("/report/redprestadores_2.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource);
		report.setType("pdf");
	}

	/**
	 * Este metodo me sirve para imprimir todas las historias
	 *
	 */
	public void imprimirHistoria() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		String dir_reporte = (String) paramRequest.get("dir_reporte");
		String nombre_reporte = (String) paramRequest.get("nombre_reporte");
		Map<String, Object> data = (Map<String, Object>) paramRequest
				.get("data");
		Long nro_historia = (Long) paramRequest.get("nro_historia");
		Integer nro_subreporte = (Integer) paramRequest.get("nro_subreport");

		String directorio_completo = "/report/" + dir_reporte;

		ServletContext context = request.getSession().getServletContext();
		String SUBREPORT_DIR = context.getRealPath(directorio_completo);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(data);

		// Agregamos sub report
		for (int i = 0; i < nro_subreporte; i++) {
			DataSourceReport dataSource1 = new DataSourceReport();
			dataSource1.loadReport(list);
			parameters.put("dataSource" + (i + 1), dataSource1);
		}

		parameters.put("nro_historia", nro_historia);

		parameters.put("SUBREPORT_DIR", SUBREPORT_DIR + "/");

		report.setSrc("/report/" + dir_reporte + nombre_reporte);
		report.setParameters(parameters);
		report.setDatasource(new JREmptyDataSource());
		report.setType("pdf");
	}

	public void imprimirReporteTrazabilidadPaciente() {
		parameters.putAll(paramRequest);
		List<Map> data = reportService.getReport(parameters,
				"reporte_trazabilidadModel");
		if (validarContenido(data, "No se encontro ningún registro!")) {
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);
			report.setSrc("/report/reporte_trazabilidad.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");
		}
	}

	public void imprimirReporteAfiliados() throws Exception {
		String formato = (String) paramRequest.get("formato");
		parameters.putAll(paramRequest);
		List<Map> data = reportService.getReport(parameters,
				"reporte_afiliadosModel");
		if (validarContenido(data, "No se encontraron registros")) {
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			report.setSrc("/report/reporte_afiliados.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType(formato);
		}
	}

	public void imprimirPagare() throws Exception {
		try {
			String codigo_comprobante = (String) paramRequest
					.get("codigo_comprobante");
			String codigo_documento = (String) paramRequest
					.get("codigo_documento");

			parameters.put("codigo_comprobante", codigo_comprobante);
			parameters.put("codigo_documento", codigo_documento);

			List<Map> data = reportContawebService.getReport(parameters,
					"pagareModel");
			for (Map map : data) {
				String codigo_tercero = (String) map.get("codigo_tercero");

				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(codigo_empresa);
				paciente.setCodigo_sucursal(codigo_sucursal);
				paciente.setNro_identificacion(codigo_tercero);
				paciente = pacienteService.consultar(paciente);
				if (paciente != null) {
					map.put("tercero", paciente.getNombreCompleto());
				}
			}
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			// //log.info("data: "+data);
			report.setSrc("/report/Pagare.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	/*
	 * Reporte de la Caja<br/> Muestra las autorizaciones realizadas por cada
	 * uno de los usuarios.
	 */
	public void imprimirAutorizacionesRealizadas() throws Exception {
		List<Map> data1 = reportService.getReport(paramRequest,
				"autorizaciones_realizadasModel");
		if (validarContenido(data1, "No se encontraron registros")) {
			DataSourceReport dataSource1 = new DataSourceReport();
			dataSource1.loadReport(data1);
			report.setSrc("/report/reporte_autorizaciones_realizadas.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource1);
			report.setType("pdf");
		}
	}

	public void imprimirCertificadoIncapacidadCaja() throws Exception {
		DataSourceReport dataSource1 = new DataSourceReport();
		dataSource1.loadReport((List<Map<String, Object>>) paramRequest
				.get("list"));
		report.setSrc("/report/reporte_certificado_incapacidad.jasper");
		report.setParameters(parameters);
		report.setDatasource(dataSource1);
		report.setType("pdf");
	}

	// metodo para imprimir el reporte de la relacion de los rips
	public void imprimirRelacionRips() throws Exception {
		try {
			List<Facturacion> listado_facturas = (List<Facturacion>) paramRequest
					.get("listado_facturas");
			List<Map> data = new ArrayList<Map>();

			for (Facturacion facturacion : listado_facturas) {
				Map<String, Object> map = new HashMap<String, Object>();
				Admision admision = facturacion.getAdmision();
				if (admision != null) {
					Paciente paciente = facturacion.getAdmision().getPaciente();
					map.put("nro_atencion", facturacion.getNro_atencion());
					map.put("codigo_documento",
							facturacion.getCodigo_documento_res() != null ? facturacion
									.getCodigo_documento_res() : "");
					map.put("nombre_paciente", paciente.getNombreCompleto());
				} else {
					map.put("nro_atencion", "");
					map.put("codigo_documento",
							facturacion.getCodigo_documento_res() != null ? facturacion
									.getCodigo_documento_res() : "");
					map.put("nombre_paciente", "FACTURA CAPITADA");
				}
				map.put("fecha", facturacion.getFecha());
				map.put("valor_total", facturacion.getValor_total());
				data.add(map);
			}

			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			// //log.info("data: "+data);
			parameters
					.put("nombre_reporte", paramRequest.get("nombre_reporte"));
			parameters.put("valor_total", paramRequest.get("valor_total"));
			parameters.put("periodo_empresa",
					paramRequest.get("periodo_empresa"));
			parameters
					.put("nombre_empresa", paramRequest.get("nombre_empresa"));

			report.setSrc("/report/reporte_relacion_rips.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void imprimirReporteTriage() throws Exception {
		log.info("Ejecutando metodo imprimirReporteTriage()");
		try {
			String codigo_historia = (String) paramRequest
					.get("codigo_historia");
			parameters.put("codigo_historia", codigo_historia);

			List<Map> data = reportService.getReport(parameters,
					"reporte_triageModel");
			for (Map map : data) {

				String codigo_medico = (String) map.get("codigo_medico");
				String nivel_triage = (String) map.get("nivel_triage");
				String admitido = (String) map.get("admitido");
				String admitido_si = (String) map.get("admitido_si");
				String descripcion_triage = "";
				String nivel_triage_aux = "";

				if (nivel_triage.equals("1")) {
					descripcion_triage = INivelesTriage.VARIABLE_NIVEL1;
					nivel_triage_aux = "NIVEL I";
				} else if (nivel_triage.equals("2")) {
					descripcion_triage = INivelesTriage.VARIABLE_NIVEL2;
					nivel_triage_aux = "NIVEL II";
				} else if (nivel_triage.equals("3")) {
					descripcion_triage = INivelesTriage.VARIABLE_NIVEL3;
					nivel_triage_aux = "NIVEL III";
				} else if (nivel_triage.equals("4")) {
					descripcion_triage = INivelesTriage.VARIABLE_NIVEL4;
					nivel_triage_aux = "NIVEL IV";
				}
				Usuarios usuarios = new Usuarios();
				usuarios.setCodigo_empresa(codigo_empresa);
				usuarios.setCodigo_sucursal(codigo_sucursal);
				usuarios.setCodigo(codigo_medico);
				usuarios = usuariosService.consultar(usuarios);
				if (usuarios != null) {
					map.put("firma_medico", getFirma(usuarios.getFirma()));
				} else {
					map.put("firma_medico", null);
				}
				map.put("nivel_triage", nivel_triage_aux);
				map.put("descripcion_triage", descripcion_triage);
				if (admitido.equals("S")) {
					map.put("admitido_si", Utilidades.getDescripcionElemento(
							admitido_si, "admision_triage", elementoService));
				}

				String escala_glasgow = (String) map.get("escala_glasgow");
				if (escala_glasgow.equals("S")) {
					Integer respuesta_motora = (Integer) map
							.get("respuesta_motora");
					Integer respuesta_verbal = (Integer) map
							.get("respuesta_verbal");
					Integer apertura_ocular = (Integer) map
							.get("apertura_ocular");
					int total_puntuacion = respuesta_motora + respuesta_verbal
							+ apertura_ocular;
					map.put("puntuacion_total", total_puntuacion + "");
					map.put("valoracion_total", GlasgowMacro
							.validarTotalPuntuacion(total_puntuacion));
				}

			}
			DataSourceReport dataSource = new DataSourceReport();
			dataSource.loadReport(data);

			// //log.info("data: "+data);
			report.setSrc("/report/reporte_triage.jasper");
			report.setParameters(parameters);
			report.setDatasource(dataSource);
			report.setType("pdf");
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void ErrorMessage(Throwable throwable) {
		MensajesUtil.mensajeError(throwable, "", this);
	}

	/**
	 * Este metodo verifica si la consulta del reporte tiene trae algun
	 * contenido
	 *
	 */
	private boolean validarContenido(List<?> list, final String msj) {
		if (list.isEmpty()) {
			addEventListener("onCreate", new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {
					detach();
					MensajesUtil.mensajeAlerta("Advertencia", "" + msj);
				}
			});
		}
		return !list.isEmpty();
	}

	/**
	 * Este metodo me devuelve la firma del gerente
	 *
	 */
	private ByteArrayInputStream getFirmaGenrente() {
		return getFirma(getEmpresa().getFirma());
	}

	private ByteArrayInputStream getFirmaUsusario() {
		return getFirma(usuarios.getFirma());
	}

	private ByteArrayInputStream getFirma(byte[] firma) {
		if (firma != null) {
			return new ByteArrayInputStream(firma);
		}
		return null;
	}

	@Override
	public void init() throws Exception {
		initPrintInformes();
	}

}
