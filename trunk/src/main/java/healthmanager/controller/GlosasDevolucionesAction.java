package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Datos_consultaService;
import healthmanager.modelo.service.Datos_medicamentosService;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Datos_servicioService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Facturacion_medicamentoService;
import healthmanager.modelo.service.Facturacion_servicioService;
import healthmanager.modelo.service.Manuales_tarifariosService;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesGlosas;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CampoObservacionesPopupMacro;
import com.framework.macros.CampoObservacionesPopupMacro.OnCambioTexto;
import com.framework.macros.VisualizadorRipsMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.macros.macro_row.BandBoxRowMacro.IConfiguracionBandbox;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.LabelAlign;
import com.framework.res.Res;
import com.framework.res.LabelAlign.AlignText;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Nota_contable;
import contaweb.modelo.service.FacturacionClinicaCompraService;
import contaweb.modelo.service.FacturacionService;

public class GlosasDevolucionesAction extends ZKWindow {

	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Toolbarbutton btnLimpiarPaciente;

	@View
	private Listbox lbxNro_ingreso;
	@View
	private Textbox tbxAseguradora;
	@View
	private Datebox dtbxFecha;
	@View
	private Datebox dtbxFecha_ingreso;
	@View
	private Datebox dtbxFecha_egreso;
	@View
	private Textbox tbxNro_poliza;
	@View
	private Textbox tbxObservacion;
	@View
	private Intbox ibxPlazo;
	@View
	private Textbox tbxDescripcion;
	@View
	private Listbox lbxServicios;
	@View
	private Listbox lbxModulo;
	@View
	private Doublebox dbxValor_cuota;
	@View
	private Doublebox dbxSubtotal;
	@View
	private Doublebox dbxDto_factura;
	@View
	private Doublebox dbxValor_copago;
	@View
	private Doublebox dbxValor_total;
	@View
	private Doublebox dbxDto_copago;
	@View
	private Checkbox chbNocopago;
	@View
	private Checkbox chbMediana;
	@View
	private Checkbox chbAgrupar;
	@View
	private Doublebox dbxCop_canc;

	/* Columnas dinamicas */
	@View
	private Listheader res_ac;
	@View
	private Listheader column_obser_respuesta_ac;
	@View
	private Listheader column_glosa_definitiva_ac;
	@View
	private Listheader column_Obseglosa_definitiva_ac;
	@View
	private Listheader listheadFechaRespuesta;
	@View
	private Listheader listheadFechaGlosaDefinitiva;

	@View
	private Caption capHeadTitulo;
	@View
	private Listbox lbx_concepto;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxConceptoAplicar;
	@View
	private Toolbarbutton btnLimpiarConceptoAplicar;

	@View
	private Hbox hboxConceptoGeneral;
	@View
	private Toolbarbutton btGuardar;

	@View
	private Groupbox groupMotivosRespuesta;

	// motivo de gloa grupal
	@View
	private BandBoxRowMacro bandMotivoGlosa;
	@View
	private Datebox dtxFechaMotivoGlosa;
	@View
	private CampoObservacionesPopupMacro tbxobservacionesMotivoGlosa;

	// respuesta de glosa
	@View
	private BandBoxRowMacro bandRespuestaGlosa;
	@View
	private Datebox dtxFechaRespuesta;
	@View
	private CampoObservacionesPopupMacro tbxobservacionesRespuestaGlosa;

	// Respuesta glosa definitiva
	@View
	private BandBoxRowMacro bandRespuestaDefinitivaGlosa;
	@View
	private Datebox dtxFechaRespuestaDefinitiva;
	@View
	private CampoObservacionesPopupMacro tbxobservacionesRespuestaDefinitivaGlosa;

	@View
	private Row rowRespuestaGlosa;
	@View
	private Row rowRespuestaDefinitivaGlosa;

	@View
	private Listheader listheadMotivo;
	@View
	private Listheader listheadFechaMotivo;
	@View
	private Listheader listheadObservacionesMotivo;

	@View
	private Groupbox grpDiferencias;
	@View
	private Groupbox grbAccionesMultiples;

	@View
	private Doublebox dbxvalorGlosaIncial;
	@View
	private Doublebox dbxvalorGlosaDefinitiva;

	@View
	private Space spaceMotivoRespuestaAgrupado;

	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;

	@View
	private Checkbox chkConfigFacturasAuditadas;
	@View
	private Checkbox chkConfigFacturasNoAuditadas;
	@View
	private Checkbox chkConfigFacturasGlosadas;
	@View
	private Checkbox chkConfigFacturasDevoluciones;

	@View
	private Listheader listheadRepuestaFinalGlosa;

	/**
	 * Estados de glosa
	 * */
	public static final String ESTADO_GLOSA = "motivo_glosa";
	public static final String ESTADO_RESPUESTA = "respuestas_glosa";
	public static final String ESTADO_DEVOLUCION = "devoluciones";

	private double t_ac = 0;
	private double t_ap = 0;
	private double t_at = 0;
	private double t_am = 0;

	private long c_ac = 0;
	private long c_ap = 0;
	private long c_at = 0;
	private long c_am = 0;

	/* totales para diferencia */
	@View
	private Label total_ac_g;
	@View
	private Label total_ap_g;
	@View
	private Label total_am_g;
	@View
	private Label total_at_g;
	@View
	private Label total_af_g;
	@View
	private Label total_af_total;

	@View
	private Chart chart;
	@View
	private Label lbDescripcionGlosa;

	@View
	private Row rowPaciente;

	@View
	private Tab tabHospitalizacon;
	@View
	private Tab tabUrgencia;
	@View
	private Tab tabRecienNacidos;
	@View
	private Tab tabDescripcionAgrupada;

	@View
	private Tabpanel tabpanelHospitalizacion;
	@View
	private Tabpanel tabpanelUrgencia;
	@View
	private Tabpanel tabpanelReciennacido;
	@View
	private Tabpanel tabPanelDescripcionAgrupada;

	@View
	private Toolbarbutton btAceptarFactura;

	private Facturacion facturacionAuditada;

	public static final String GLOSA_LEVANTADA = "2";
	private static final String DTT_FACTURA = "DDT";

	/**
	 * Estos son los detalles de la factura
	 * */
	private List<Detalle_factura> lista_datos;

	private final String BANDBOX_GLOSA_INICIAL = "_bGi";
	private final String BANDBOX_RESPUESTA_GLOSA = "_bRg";
	private final String BANDBOX_GLOSA_DEFINITIVA = "_bGd";

	private final String[] idsExcluyentes = new String[] {
			"tbxNro_identificacion", "btnLimpiarPaciente", "tbxTipo",
			"btCancel", "btGuardar", "tbxAccion", "btNew", "lbxFormato",
			"btImprimir", "btImprimir_pre", "btPrintCont", "btCont",
			"btPrintVerCont", "btPrintCop", "lbx_concepto",
			"tbxConceptoAplicar", "dbxSubtotal", "dbxDto_factura",
			"dbxCop_canc", "dbxValor_copago", "dbxValor_total", "dbxDto_copago" };

	private Map<String, Object> datos_seleccion = new HashMap<String, Object>();
	private ENivelFactura nivelFactura;
	private EModeloGlosa modeloGlosa;
	private EventListener<Event> eventListenerConsulta;
	private EventListener<Event> eventListenerProcedimiento;
	private EventListener<Event> eventListenerMedicamento;
	private EventListener<Event> eventListenerServicio;

	/**
	 * 
	 * */
	public enum ENivelFactura {
		GLOSA, DEVOLUCION
	};

	public enum EModeloGlosa {
		INDIVIDUAL, GRUPAL
	};

	@Override
	public void init() throws Exception {
		listarCombos();
		tbxConceptoAplicar.inicializar(null, btnLimpiarConceptoAplicar);
		parametrizarConceptos();
	}

	private void parametrizarConceptos() {
		tbxConceptoAplicar
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Elemento>() {

					@Override
					public void renderListitem(Listitem listitem,
							Elemento registro) {

						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro.getCodigo()
								+ ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getDescripcion() + ""));
						listitem.appendChild(listcell);

						listitem.appendChild(listcell);
					}

					@Override
					public List<Elemento> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {
						parametros.put("tipo", getMotivoConsultaConceptos());
						parametros.put("parametroTodo", valorBusqueda);
						parametros.put("empieza_por", lbx_concepto
								.getSelectedItem().getValue().toString());
						return getServiceLocator().getServicio(
								ElementoService.class).listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Elemento registro) {
						bandbox.setValue(registro.getCodigo() + " - "
								+ registro.getDescripcion());
						return true;

					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbx_concepto, true,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_documento");
		listitem.setLabel("Nro registro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.nro_identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.nombre1||' '||t2.nombre2");
		listitem.setLabel("Nombres");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.apellido1||' '||t2.apellido2");
		listitem.setLabel("Apellidos");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("fecha_servicio");
		listitem.setLabel("Fecha(aaaa-mm-dd)");
		lbxParameter.appendChild(listitem);

		// listitem = new LISTITEM();
		// LISTITEM.SETVALUE("FECHA_SERVICIO");
		// LISTITEM.SETLABEL("GLOSAS");
		// LBXPARAMETER.APPENDCHILD(LISTITEM);
		//
		// LISTITEM = NEW LISTITEM();
		// LISTITEM.SETVALUE("FECHA_SERVICIO");
		// LISTITEM.SETLABEL("DEVOLUCIONES");
		// LBXPARAMETER.APPENDCHILD(LISTITEM);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	/**
	 * Filtros de facturas para glosar
	 * 
	 * */
	public static List<String> getFiltrosFacturacion() {
		List<String> list_in = new ArrayList<String>();
		list_in.add("GEN_CAP");
		list_in.add("GEN_AGR");
		list_in.add("IND");
		list_in.add(IConstantes.TIPO_FACTURA_GLOSA);
		return list_in;
	}

	public void setVisiblePestaniasAlternas(boolean visible) {
		tabHospitalizacon.setVisible(visible);
		tabRecienNacidos.setVisible(visible);
		tabUrgencia.setVisible(visible);
		tabDescripcionAgrupada.setVisible(visible);
	}

	/**
	 * Este metodo me permite buscar las facturas radicadas
	 * 
	 * @author Luis Miguel
	 * */
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("tipo_in", getFiltrosFacturacion());
			parameters.put("post", "N");
			parameters.put("radicado", "S");

			if (dtbxFecha_inicial.getValue() != null
					&& dtbxFecha_final.getValue() != null) {
				parameters.put("fecha_inicio", new Timestamp(dtbxFecha_inicial
						.getValue().getTime()));
				parameters.put("fecha_final", new Timestamp(dtbxFecha_final
						.getValue().getTime()));
				parameters.put("rango_fecha", "_converDate");
			}

			String nivel_busqueda = "";

			if (chkConfigFacturasAuditadas.isChecked()) {
				nivel_busqueda += " facturacion.tipo_glosa = '3' ,";
			}

			if (chkConfigFacturasDevoluciones.isChecked()) {
				nivel_busqueda += " facturacion.tipo_glosa = '2' ,";
			}

			if (chkConfigFacturasGlosadas.isChecked()) {
				nivel_busqueda += " facturacion.tipo_glosa = '1' ,";
			}
			if (chkConfigFacturasNoAuditadas.isChecked()) {
				nivel_busqueda += " (facturacion.tipo_glosa = '' OR facturacion.tipo_glosa IS NULL) ,";
			}

			if (!nivel_busqueda.trim().isEmpty()) {
				nivel_busqueda = nivel_busqueda.substring(0,
						nivel_busqueda.length() - 1);
				parameters.put("nivel_busqueda",
						nivel_busqueda.replace(",", "OR"));
			}

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Facturacion> lista_datos = getServiceLocator()
					.getFacturacionService().listarRegistros(parameters);
			rowsResultado.getChildren().clear();

			for (Facturacion facturacion : lista_datos) {
				rowsResultado.appendChild(crearFilas(facturacion));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {

			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Facturacion objeto) throws Exception {
		Row fila = new Row();

		final Facturacion facturacion = (Facturacion) objeto;

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(facturacion.getCodigo_empresa());
		paciente.setCodigo_sucursal(facturacion.getCodigo_sucursal());
		paciente.setNro_identificacion(facturacion.getCodigo_tercero());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		String nombres_paciente = (paciente != null ? paciente.getNombre1()
				+ " " + paciente.getNombre2() : "");
		String apellidos_paciente = (paciente != null ? paciente.getApellido1()
				+ " " + paciente.getApellido2() : "");

		String codigo_tercero = facturacion.getCodigo_tercero() != null ? facturacion
				.getCodigo_tercero() : "";
		String informacion_paciente = codigo_tercero + " " + nombres_paciente
				+ " " + apellidos_paciente;

		// Contrato
		Contratos contratos = new Contratos();
		contratos.setCodigo_empresa(codigo_empresa);
		contratos.setCodigo_sucursal(codigo_sucursal);
		contratos.setCodigo_administradora(facturacion
				.getCodigo_administradora());
		contratos.setId_plan(facturacion.getId_plan());
		contratos = getServiceLocator().getServicio(ContratosService.class)
				.consultar(contratos);

		String informacion_contrato = contratos != null ? contratos
				.getId_plan() + " - " + contratos.getNombre() : "";

		Hbox hbox = new Hbox();
		Space space = new Space();

		if (facturacion.getEstado_glosa() == null) {
			facturacion.setEstado_glosa("");
		}

		if (facturacion.getTipo_glosa() == null) {
			facturacion.setTipo_glosa("0");
		} else if (facturacion.getTipo_glosa().trim().isEmpty()) {
			facturacion.setTipo_glosa("0");
		}

		String addStyle = "";
		if (facturacion.getEstado_glosa().equals(
				IConstantesGlosas.ESTADO_GLOSA_ACEPTADA)) {
			addStyle = "background-color: #7FEDF5;";
			fila.setTooltiptext("FACTURA ACEPTADA");
		} else if (!facturacion.getTipo_glosa().equals("0")) {
			addStyle = "background-color: #?;".replace("?", facturacion
					.getTipo_glosa().equals("1") ? "AFE3B1" : "ABB7DB");
			fila.setTooltiptext(facturacion.getTipo_glosa().equals("1") ? "Glosa"
					: "Devolucion");
		} else {
			fila.setTooltiptext("NO AUDITADA");
		}

		fila.setStyle("text-align: justify;nowrap:nowrap;" + addStyle);
		fila.appendChild(new Label(facturacion.getNro_atencion() + ""));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(facturacion.getFecha()) + ""));
		fila.appendChild(new Label(facturacion.getNro_ingreso() + ""));
		fila.appendChild(new Label(informacion_paciente + ""));
		fila.appendChild(new Label(informacion_contrato + ""));

		String tipo_factura = getTipoFactura(facturacion.getTipo());
		fila.appendChild(new Label(tipo_factura + ""));

		// fila.appendChild(new Label(apellidos_paciente + ""));
		// fila.appendChild(new Label(nombres_paciente + ""));

		LabelAlign label = new LabelAlign(
				new DecimalFormat("#,##0.##").format(facturacion
						.getValor_pagar_factura()) + "", AlignText.RIGHT, true);
		fila.appendChild(label);
		label = new LabelAlign(new DecimalFormat("#,##0.##").format(facturacion
				.getValor_glosa_inicial()) + "", AlignText.RIGHT, true);
		fila.appendChild(label);
		label = new LabelAlign(new DecimalFormat("#,##0.##").format(facturacion
				.getValor_glosa_aceptada()) + "", AlignText.RIGHT, true);
		fila.appendChild(label);
		label = new LabelAlign(new DecimalFormat("#,##0.##").format(facturacion
				.getValor_total()) + "", AlignText.RIGHT, true);
		fila.appendChild(label);

		// fila.appendChild(new Label(
		// (facturacion.getDelete_date() == null ? facturacion.getEstado()
		// : "ANUL") + ""));

		hbox.appendChild(space);

		final EModeloGlosa modeloGlosa = facturacion.getTipo().equals("IND") ? EModeloGlosa.INDIVIDUAL
				: EModeloGlosa.GRUPAL;
		Image img = new Image();
		img.setSrc("/images/generar.png");
		img.setTooltiptext("Glosar factura");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(facturacion, ENivelFactura.GLOSA, modeloGlosa);
			}
		});
		if (!(facturacion.getTipo_glosa() + "").equals("2")) {
			hbox.appendChild(img);
		}

		img = new Image();
		img.setSrc("/images/devolucion.gif");
		img.setTooltiptext("Devolucion");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(facturacion, ENivelFactura.DEVOLUCION,
						EModeloGlosa.GRUPAL);
			}
		});
		hbox.appendChild(space);
		if (!(facturacion.getTipo_glosa() + "").equals("1")) {
			hbox.appendChild(img);
		}
		if (empresa.getManeja_contabilidad().equals("S")) {
			img = new Image();
			img.setSrc("/images/contabilizar.gif");
			img.setTooltiptext("Contabilizar");
			img.setStyle("cursor: pointer");
			img.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					contabilizarGlosa(
							facturacion.getId_factura(), true);
				}
			});
			hbox.appendChild(img);

			img = new Image();
			img.setSrc("/images/print_ico.gif");
			img.setTooltiptext("Imprimir Contabilizacion");
			img.setStyle("cursor: pointer");
			img.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					imprimirNota_contable(facturacion.getCodigo_comprobante(),facturacion.getId_factura());
				}
			});
			hbox.appendChild(space);
			hbox.appendChild(img);
		}

		fila.appendChild(hbox);

		return fila;
	}

	private String getTipoFactura(String tipo) {
		if (tipo.equals("GEN_CAP")) {
			return "CAPITADA";
		} else if (tipo.equals("GEN_AGR")) {
			return "AGRUPADA";
		} else if (tipo.equals("IND")) {
			return "INDIVUDUAL";
		} else if (tipo.equals(IConstantes.TIPO_FACTURA_GLOSA)) {
			return "EXTERNA";
		}
		return "";
	}

	public void mostrarDatos(Object obj, ENivelFactura nivelFactura,
			EModeloGlosa modeloGlosa) throws Exception {
		facturacionAuditada = (Facturacion) obj;
		this.nivelFactura = nivelFactura;
		this.modeloGlosa = modeloGlosa;
		FormularioUtil.limpiarComponentes(groupboxEditar);
		try {
			List<Admision> admisions = listarAdmisiones(facturacionAuditada,
					true);
			// if(!admisions.isEmpty()){
			if (facturacionAuditada.getFact_glosada() == null) {
				facturacionAuditada.setFact_glosada("N");
			}
			deshabilitarCampos(true);
			boolean visible_respuesta = false;
			boolean visible_confirmacion = false;
			boolean permitir_habilitar_boton_guardar = true;
			if (nivelFactura == ENivelFactura.GLOSA) {
				capHeadTitulo.setLabel("GLOSAR FACTURA NRO "
						+ facturacionAuditada.getCodigo_documento_res());
				// Mostramos linealmente el estado de la glosa
				// Habilitamos las columnas correspondientes
				// Estado 01 - Esta esperando resopuesta
				if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("01")) {
					visible_respuesta = true;
					// Esta esperando confirmacion
				} else if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("02")) {
					visible_confirmacion = true;
					visible_respuesta = true;
				} else if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("00")) {
					permitir_habilitar_boton_guardar = false;
					visible_confirmacion = true;
					visible_respuesta = true;
					permitir_habilitar_boton_guardar = false;
				}
				hboxConceptoGeneral.setVisible(true);
				lbDescripcionGlosa.setValue("Motivo de glosa");
			} else {
				capHeadTitulo.setLabel("REALIZAR DEVOLUCIÓN PARA FACTURA NRO "
						+ facturacionAuditada.getNro_atencion());
				hboxConceptoGeneral.setVisible(false);
				lbx_concepto.setSelectedIndex(0);
				lbDescripcionGlosa.setValue("Motivo de devolucion");

				if (facturacionAuditada.getFact_glosada().equals("S")) {
					permitir_habilitar_boton_guardar = false;
				}
			}

			// habilitamos las pestanias cuando sea una glosa externa
			setVisiblePestaniasAlternas(facturacionAuditada.getTipo().equals(
					IConstantes.TIPO_FACTURA_GLOSA));

			if (modeloGlosa == EModeloGlosa.INDIVIDUAL) {
				// Habilitamos el estado de la glosa
				res_ac.setVisible(visible_respuesta);
				column_obser_respuesta_ac.setVisible(visible_respuesta);
				listheadFechaRespuesta.setVisible(visible_respuesta);

				listheadMotivo.setVisible(true);
				listheadFechaMotivo.setVisible(true);
				listheadObservacionesMotivo.setVisible(true);

				column_glosa_definitiva_ac.setVisible(visible_confirmacion);
				column_Obseglosa_definitiva_ac.setVisible(visible_confirmacion);
				listheadFechaGlosaDefinitiva.setVisible(visible_confirmacion);
				listheadRepuestaFinalGlosa.setVisible(visible_confirmacion);

				grpDiferencias.setVisible(true);
				groupMotivosRespuesta.setVisible(false);
				spaceMotivoRespuestaAgrupado.setVisible(false);

				// habilitamos seleccion
				getFellow("listheaderSeleccion").setVisible(true);
			} else {
				// si el esta de la glosa es no auditada, se nos habilita
				// la accion para marcarlas como aceptadas
				btAceptarFactura.setVisible(facturacionAuditada
						.getEstado_glosa().equals(
								IConstantesGlosas.ESTADO_GLOSA_NO_AUDITADA));

				// deshabilitamos seleccion individual
				getFellow("listheaderSeleccion").setVisible(false);

				groupMotivosRespuesta.setVisible(true);
				spaceMotivoRespuestaAgrupado.setVisible(true);
				bandMotivoGlosa.configurar(getConfiguracionBandbox(
						facturacionAuditada, "concepto_glosa",
						getMotivoConsultaBandBox()));
				bandRespuestaDefinitivaGlosa
						.configurar(getConfiguracionBandbox(
								facturacionAuditada, "respuesta_definitiva",
								ESTADO_GLOSA));
				bandRespuestaGlosa
						.configurar(getConfiguracionBandbox(
								facturacionAuditada, "respueta_glosa",
								ESTADO_RESPUESTA));

				grpDiferencias.setVisible(facturacionAuditada.getTipo().equals(
						IConstantes.TIPO_FACTURA_GLOSA));
				// hboxConceptoGeneral.setVisible(true);
				grbAccionesMultiples.setVisible(false);

				// Motivo glosa
				bandMotivoGlosa.setButtonVisible(true);
				bandMotivoGlosa.setDisabled(false);
				dtxFechaMotivoGlosa.setDisabled(false);
				dtxFechaMotivoGlosa.setButtonVisible(true);
				dtxFechaMotivoGlosa.setReadonly(false);
				if (nivelFactura == ENivelFactura.DEVOLUCION) {
					dbxvalorGlosaIncial.setReadonly(true);
					dbxvalorGlosaIncial
							.setValue((facturacionAuditada.getFact_glosada() + "")
									.equals("S") ? facturacionAuditada
									.getValor_glosa_inicial()
									: facturacionAuditada.getValor_total());
				} else {
					dbxvalorGlosaIncial.setDisabled(false);
					dbxvalorGlosaIncial.setReadonly(false);
					dbxvalorGlosaIncial.setValue(facturacionAuditada
							.getValor_glosa_inicial());

					// fecha respuesta definitiva
					dtxFechaMotivoGlosa.setValue(null);
					dtxFechaRespuesta.setValue(null);
					dtxFechaRespuestaDefinitiva.setValue(null);
				}

				// Ocultamos los items de la vista
				listheadMotivo.setVisible(false);
				listheadFechaMotivo.setVisible(false);
				listheadObservacionesMotivo.setVisible(false);

				res_ac.setVisible(false);
				column_obser_respuesta_ac.setVisible(false);
				listheadFechaRespuesta.setVisible(false);

				column_glosa_definitiva_ac.setVisible(false);
				column_Obseglosa_definitiva_ac.setVisible(false);
				listheadFechaGlosaDefinitiva.setVisible(false);
				listheadRepuestaFinalGlosa.setVisible(false);
				// Fin

				// Respuesta
				bandRespuestaGlosa.setButtonVisible(true);
				bandRespuestaGlosa.setDisabled(false);
				dtxFechaRespuesta.setDisabled(false);
				dtxFechaRespuesta.setReadonly(false);
				dtxFechaRespuesta.setButtonVisible(true);

				// Respuesta definitiva glosa
				bandRespuestaDefinitivaGlosa.setButtonVisible(true);
				bandRespuestaDefinitivaGlosa.setDisabled(false);
				dtxFechaRespuestaDefinitiva.setDisabled(false);
				dtxFechaRespuestaDefinitiva.setReadonly(false);
				dtxFechaRespuestaDefinitiva.setButtonVisible(true);
				dbxvalorGlosaDefinitiva.setDisabled(false);
				dbxvalorGlosaDefinitiva.setReadonly(false);

				dtxFechaMotivoGlosa.setValue(facturacionAuditada
						.getFecha_glosa());
				dtxFechaMotivoGlosa.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								// log.info("cambiar fecha glosa");
								facturacionAuditada
										.setFecha_glosa(new java.sql.Date(
												dtxFechaMotivoGlosa.getValue()
														.getTime()));
							}
						});

				tbxobservacionesMotivoGlosa.setText(facturacionAuditada
						.getObservacion_glosa());
				tbxobservacionesMotivoGlosa
						.setOnCambioTexto(new OnCambioTexto() {
							@Override
							public void texto(String texto) {
								facturacionAuditada.setObservacion_glosa(texto);
							}
						});
				// Cargamos los valores
				setValorBandBox(facturacionAuditada.getConcepto_glosa(),
						bandMotivoGlosa, getMotivoConsultaBandBox());
				tbxobservacionesMotivoGlosa.setReadonly(false);
				// Esta esperando confirmacion
				if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("01")) {
					visible_confirmacion = false;
					visible_respuesta = true;
					dtxFechaRespuesta.setValue(facturacionAuditada
							.getFecha_respuesta_glosa());
					dtxFechaRespuesta.addEventListener(Events.ON_BLUR,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									facturacionAuditada
											.setFecha_respuesta_glosa(new Timestamp(
													dtxFechaRespuesta
															.getValue()
															.getTime()));
								}
							});

					tbxobservacionesRespuestaGlosa.setText(facturacionAuditada
							.getObservacion_respuesta());
					setValorBandBox(facturacionAuditada.getRespueta_glosa(),
							bandRespuestaGlosa, ESTADO_RESPUESTA);
					tbxobservacionesMotivoGlosa.setReadonly(true);
					tbxobservacionesRespuestaGlosa.setReadonly(false);
					tbxobservacionesRespuestaDefinitivaGlosa.setReadonly(true);
					tbxobservacionesRespuestaGlosa
							.setOnCambioTexto(new OnCambioTexto() {
								@Override
								public void texto(String texto) {
									facturacionAuditada
											.setObservacion_respuesta(texto);
								}
							});

				} else if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("02")) {
					visible_confirmacion = true;
					visible_respuesta = true;

					dtxFechaRespuesta.setValue(facturacionAuditada
							.getFecha_respuesta_glosa());
					tbxobservacionesRespuestaGlosa.setText(facturacionAuditada
							.getObservacion_respuesta());

					dtxFechaRespuestaDefinitiva.setValue(facturacionAuditada
							.getFecha_respuesta_definitiva());
					dbxvalorGlosaDefinitiva.setValue(facturacionAuditada
							.getValor_glosa_aceptada());

					dtxFechaRespuestaDefinitiva.addEventListener(
							Events.ON_BLUR, new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									facturacionAuditada
											.setFecha_respuesta_definitiva(new Timestamp(
													dtxFechaRespuestaDefinitiva
															.getValue()
															.getTime()));
								}
							});

					tbxobservacionesRespuestaDefinitivaGlosa
							.setText(facturacionAuditada
									.getObservacion_definitiva_glosa());
					setValorBandBox(facturacionAuditada.getRespueta_glosa(),
							bandRespuestaGlosa, ESTADO_RESPUESTA);
					setValorBandBox(
							facturacionAuditada.getRespuesta_definitiva(),
							bandRespuestaDefinitivaGlosa, ESTADO_GLOSA);

					tbxobservacionesMotivoGlosa.setReadonly(true);
					tbxobservacionesRespuestaGlosa.setReadonly(true);
					tbxobservacionesRespuestaDefinitivaGlosa.setReadonly(false);

					tbxobservacionesRespuestaDefinitivaGlosa
							.setOnCambioTexto(new OnCambioTexto() {
								@Override
								public void texto(String texto) {
									facturacionAuditada
											.setObservacion_definitiva_glosa(texto);
								}
							});
				} else if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("00")) {
					permitir_habilitar_boton_guardar = false;
					visible_confirmacion = true;
					visible_respuesta = true;

					dtxFechaRespuesta.setValue(facturacionAuditada
							.getFecha_respuesta_glosa());
					tbxobservacionesRespuestaGlosa.setText(facturacionAuditada
							.getObservacion_respuesta());
					tbxobservacionesRespuestaDefinitivaGlosa
							.setText(facturacionAuditada
									.getObservacion_definitiva_glosa());

					setValorBandBox(facturacionAuditada.getRespueta_glosa(),
							bandRespuestaGlosa, ESTADO_RESPUESTA);
					setValorBandBox(
							facturacionAuditada.getRespuesta_definitiva(),
							bandRespuestaDefinitivaGlosa, ESTADO_GLOSA);

					dtxFechaRespuestaDefinitiva.setValue(facturacionAuditada
							.getFecha_respuesta_definitiva());
					dbxvalorGlosaDefinitiva.setValue(facturacionAuditada
							.getValor_glosa_aceptada());

					tbxobservacionesMotivoGlosa.setReadonly(true);
					tbxobservacionesRespuestaGlosa.setReadonly(true);
					tbxobservacionesRespuestaDefinitivaGlosa.setReadonly(true);
				}

				// Activacion de campos disponibles
				rowRespuestaDefinitivaGlosa.setVisible(visible_confirmacion);
				rowRespuestaGlosa.setVisible(visible_respuesta);
			}

			// Consultamos pacientes..
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(facturacionAuditada.getCodigo_empresa());
			paciente.setCodigo_sucursal(facturacionAuditada
					.getCodigo_sucursal());
			paciente.setNro_identificacion(facturacionAuditada
					.getCodigo_tercero());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			tbxNro_identificacion.setValue(paciente != null ? paciente
					.getNro_identificacion() : "");
			tbxNomPaciente.setValue(paciente != null ? paciente
					.getNombreCompleto() : "");

			datos_seleccion
					.put("tipo_identificacion",
							(paciente != null ? paciente
									.getTipo_identificacion() : ""));
			datos_seleccion.put("codigo_administradora",
					facturacionAuditada.getCodigo_administradora());
			datos_seleccion.put("id_plan", facturacionAuditada.getId_plan());

			if (!admisions.isEmpty()) {
				listarIngresos(lbxNro_ingreso, admisions, false);

				Utilidades.seleccionarListitem(lbxNro_ingreso,
						facturacionAuditada.getNro_ingreso());
			}

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(facturacionAuditada
					.getCodigo_empresa());
			administradora.setCodigo_sucursal(facturacionAuditada
					.getCodigo_sucursal());
			administradora.setCodigo(facturacionAuditada
					.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			Contratos contratos = new Contratos();
			contratos
					.setCodigo_empresa(facturacionAuditada.getCodigo_empresa());
			contratos.setCodigo_sucursal(facturacionAuditada
					.getCodigo_sucursal());
			contratos.setCodigo_administradora(facturacionAuditada
					.getCodigo_administradora());
			contratos.setId_plan(facturacionAuditada.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(
					contratos);
			tbxAseguradora.setValue((administradora != null ? administradora
					.getNombre() : "")
					+ " - "
					+ (contratos != null ? contratos.getNombre() : ""));

			dtbxFecha.setValue(facturacionAuditada.getFecha());
			dtbxFecha_ingreso.setValue(facturacionAuditada.getFecha_inicio());
			dtbxFecha_egreso.setValue(facturacionAuditada.getFecha_final());

			tbxNro_poliza.setValue(facturacionAuditada.getNro_poliza());
			tbxObservacion.setValue(facturacionAuditada.getObservacion());

			ibxPlazo.setValue(facturacionAuditada.getPlazo());
			tbxDescripcion.setValue(facturacionAuditada.getDescripcion());

			Map paramDetalle_factura = new HashMap();
			paramDetalle_factura.put("id_factura",
					facturacionAuditada.getId_factura());
			lista_datos = getServiceLocator().getDetalle_facturaService()
					.listar(paramDetalle_factura);

			// log.info("Listado: " + lista_datos);

			Admision admision = (Admision) lbxNro_ingreso.getSelectedItem()
					.getValue();

			if (facturacionAuditada.getTipo().equals(
					IConstantes.TIPO_FACTURA_GLOSA)) {
				// facturas externas
				setVisibleInformacionPaciente(false);
				crearFilasManuales(null, facturacionAuditada);
			} else { // facturas internas
				if (admision != null) {
					Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
							.getManuales_tarifarios(admision);
					crearFilasManuales(manuales_tarifarios, facturacionAuditada);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("codigo_empresa",
							facturacionAuditada.getCodigo_empresa());
					map.put("codigo_sucursal",
							facturacionAuditada.getCodigo_sucursal());
					map.put("id_contrato", facturacionAuditada.getId_plan());
					map.put("codigo_administradora",
							facturacionAuditada.getCodigo_administradora());
					List<Manuales_tarifarios> manuales_tarifarios_list = getServiceLocator()
							.getServicio(Manuales_tarifariosService.class)
							.listar(map);
					if (!manuales_tarifarios_list.isEmpty()) {
						crearFilasManuales(manuales_tarifarios_list.get(0),
								facturacionAuditada);
					} else {
						throw new ValidacionRunTimeException(
								"El contrato con el cual se realizo la factura no existe");
					}
				}
				setVisibleInformacionPaciente(true);
			}
			cargarTotalFactura(facturacionAuditada);

			// Mostramos la vista //
			// tbxAccion.setText("modificar");
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			// if(permitir_habilitar_boton_guardar)btGuardar.setDisabled(false);

			bloqueoBotonGuardar(!permitir_habilitar_boton_guardar);
			getFellow("btnAplicarGlosa").setVisible(true);
			getFellow("btnLimpiarSeleccionados").setVisible(true);

			// FormularioUtil.deshabilitarComponentes(groupMotivosRespuesta,
			// false);

			// accionForm(true, tbxAccion.getText());
			// }

			// else{
			// MensajesUtil.mensajeAlerta("Advertencia",
			// "La admision con la que se realizo esta factura no existe..");
			// }
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/**
	 * Este metodo me permite ocultar la infomacion del paciente
	 * */
	private void setVisibleInformacionPaciente(boolean visible) {
		rowPaciente.setVisible(visible);
		tbxAseguradora.setWidth(visible ? "700px" : "500px");
		lbxNro_ingreso.setVisible(false);
		getFellow("cellInformacionIngreso").setVisible(visible);
	}

	public void guardar() {
		if (nivelFactura == ENivelFactura.GLOSA) {
			glosarFact("1");
		} else if (nivelFactura == ENivelFactura.DEVOLUCION) {
			glosarFact("2");
		}
	}

	/**
	 * Este metodo me permite marcar la factura como aceptada
	 * 
	 * @author Luis Miguel
	 * */
	public void aceptarFactura() {
		try {
			Messagebox
					.show(usuarios.getNombres()
							+ " estas seguro(a) que desea marcar la factura como aceptada esta factura?",
							"Informacion", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION, new EventListener<Event>() {
								@Override
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equalsIgnoreCase(event
											.getName())) {
										try {
											facturacionAuditada
													.setEstado_glosa(IConstantesGlosas.ESTADO_GLOSA_ACEPTADA);
											facturacionAuditada
													.setFecha_glosa(new java.sql.Date(
															Calendar.getInstance()
																	.getTimeInMillis()));
											getServiceLocator()
													.getServicio(
															FacturacionService.class)
													.actualizar(
															facturacionAuditada);
											MensajesUtil
													.mensajeInformacion(
															"Informacion",
															"Registro actualizado satisfactoriamente");
										} catch (ValidacionRunTimeException e) {
											MensajesUtil
													.mensajeError(
															e,
															null,
															GlosasDevolucionesAction.this);
										}
									}
								}
							});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	public void glosarFact(final String tipo) {
		if (validarForm(tipo)) {
			Messagebox.show(usuarios.getNombres()
					+ " estas seguro(a) que desea "
					+ (tipo.equals("1") ? "glosar"
							: "realizar la devolucion ha") + " esta factura?",
					"Informacion", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							if ("onYes".equalsIgnoreCase(event.getName())) {
								try {
									glosarFacturaFinal(tipo);
								} catch (ValidacionRunTimeException e) {
									MensajesUtil.mensajeAlerta("Advertencia",
											e.getMessage());
								}
							}
						}
					});
		}
	}

	private boolean validarForm(String tipo) {
		boolean valido = true;
		boolean validate = false;
		int i = 1;
		String msj = "";

		bandMotivoGlosa
				.setStyle("text-transform:uppercase;margin-right:5px;cursor:pointer;background-color:white");
		bandRespuestaGlosa
				.setStyle("text-transform:uppercase;margin-right:5px;cursor:pointer;background-color:white");
		bandRespuestaDefinitivaGlosa
				.setStyle("text-transform:uppercase;margin-right:5px;cursor:pointer;background-color:white");
		dtxFechaMotivoGlosa.setStyle("background-color:white");
		dtxFechaRespuesta.setStyle("background-color:white");
		dtxFechaRespuestaDefinitiva.setStyle("background-color:white");
		tbxobservacionesMotivoGlosa.setStyle("background-color:white");
		tbxobservacionesRespuestaGlosa.setStyle("background-color:white");
		tbxobservacionesRespuestaDefinitivaGlosa
				.setStyle("background-color:white");
		dbxvalorGlosaIncial.setStyle("background-color:white");
		dbxvalorGlosaDefinitiva.setStyle("background-color:white");

		if (facturacionAuditada.getEstado_respuesta_glosa() == null) {
			facturacionAuditada.setEstado_respuesta_glosa("");
		}

		if (modeloGlosa == EModeloGlosa.INDIVIDUAL) {
			for (Detalle_factura detalle_factura : lista_datos) {

				if (detalle_factura.getObservacion_definitiva_glosa() == null) {
					detalle_factura.setObservacion_definitiva_glosa("");
				}

				if (detalle_factura.getEstado_glosa() == null) {
					detalle_factura.setEstado_glosa("");
				}

				if (detalle_factura.getObservacion_glosa() == null) {
					detalle_factura.setObservacion_glosa("");
				}

				if (detalle_factura.getObservacion_respuesta() == null) {
					detalle_factura.setObservacion_respuesta("");
				}

				if (detalle_factura.getRespueta_glosa() == null) {
					detalle_factura.setRespueta_glosa("");
				}

				if (detalle_factura.getRespuesta_definitiva() == null) {
					detalle_factura.setRespuesta_definitiva("");
				}

				if (detalle_factura.getEstado_final_glosa() == null) {
					detalle_factura.setEstado_final_glosa("");
				}

				boolean item_glosado = false;
				if (tipo.equals("3")) {
					item_glosado = !(detalle_factura.getRespuesta_definitiva() + "")
							.trim().isEmpty();
					if ((detalle_factura.getObservacion_definitiva_glosa() + "")
							.trim().isEmpty()) {
						valido = false;
						msj = ("Debe agregar una observacion definitiva en el item de la fila " + i);
						break;
					}
				} else {
					if (facturacionAuditada.getEstado_respuesta_glosa().trim()
							.isEmpty()) {
						item_glosado = !(detalle_factura.getEstado_glosa() + "")
								.trim().isEmpty();
						// log.info("Observaciones glosa: "
						/*
						 * + (detalle_factura.getObservacion_glosa() + "")
						 * .trim());
						 */
						if ((detalle_factura.getObservacion_glosa() + "")
								.trim().isEmpty() && item_glosado) {
							valido = false;
							msj = ("Debe agregar una observacion el motivo en el item de la fila " + i);
							break;
						}
						if (detalle_factura.getFecha_glosa() == null
								&& item_glosado) {
							valido = false;
							msj = ("Debe agregar la fecha del motivo en el item de la fila " + i);
							break;
						}
					} else if (facturacionAuditada.getEstado_respuesta_glosa()
							.equals("01")) {
						item_glosado = !(detalle_factura.getRespueta_glosa() + "")
								.trim().isEmpty();
						if ((detalle_factura.getObservacion_respuesta() + "")
								.trim().isEmpty() && item_glosado) {
							valido = false;
							msj = ("Debe agregar una observacion para respuesta en el item de la fila " + i);
							break;
						}
						if (detalle_factura.getFecha_respuesta_glosa() == null
								&& item_glosado) {
							valido = false;
							msj = ("Debe agregar la fecha para la respuesta de la glosa en el item de la fila " + i);
							break;
						}
					} else if (facturacionAuditada.getEstado_respuesta_glosa()
							.equals("02")) {
						item_glosado = !(detalle_factura
								.getRespuesta_definitiva() + "").trim()
								.isEmpty();
						if ((detalle_factura.getObservacion_definitiva_glosa() + "")
								.trim().isEmpty() && item_glosado) {
							valido = false;
							msj = ("Debe agregar una observacion de la respuesta definitiva en el item de la fila " + i);
							break;
						}
						if (detalle_factura.getFecha_respuesta_definitiva() == null
								&& item_glosado) {
							valido = false;
							msj = ("Debe agregar la fecha para respuesta definitiva de la glosa en el item de la fila " + i);
							break;
						}
						if (detalle_factura.getEstado_final_glosa().trim()
								.isEmpty()
								&& item_glosado) {
							valido = false;
							msj = ("Debe agregar la respuesta definitiva de la glosa en el item de la fila " + i);
							break;
						}
					}
				}
				if (item_glosado) {
					validate = true;
				}
				i++;
			}
			if (!validate && !tipo.equals("3") && valido) {
				if (facturacionAuditada.getEstado_respuesta_glosa().trim()
						.isEmpty()) {
					msj = ("Para realizar esta accion por lo menos, debe glosar un item.");
				} else if (facturacionAuditada.getEstado_respuesta_glosa()
						.equals("01")) {
					msj = ("Para realizar esta accion por lo menos, debe responder un item glosado.");
				} else if (facturacionAuditada.getEstado_respuesta_glosa()
						.equals("02")) {
					msj = ("Para realizar esta accion por lo menos, debe cololar motivo de glosa definitivo.");
				}
				valido = false;
			}
		} else {
			if (facturacionAuditada.getConcepto_glosa() == null) {
				facturacionAuditada.setConcepto_glosa("");
			}
			if (facturacionAuditada.getRespueta_glosa() == null) {
				facturacionAuditada.setRespueta_glosa("");
			}
			if (facturacionAuditada.getRespuesta_definitiva() == null) {
				facturacionAuditada.setRespuesta_definitiva("");
			}
			double valor_total = facturacionAuditada.getValor_total()
					+ facturacionAuditada.getValor_copago();

			msj = "Para realizar esta accion debe diligencial los campos obligatorios";
			if ((facturacionAuditada.getEstado_respuesta_glosa() + "").trim()
					.isEmpty()) {
				if (bandMotivoGlosa.getValue().toString().trim().isEmpty()) {
					valido = false;
					bandMotivoGlosa
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(bandMotivoGlosa);
				}

				if (dtxFechaMotivoGlosa.getValue() == null && valido) {
					valido = false;
					dtxFechaMotivoGlosa
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(dtxFechaMotivoGlosa);
				}

				if (tbxobservacionesMotivoGlosa.getValue().trim().isEmpty()
						&& valido) {
					valido = false;
					tbxobservacionesMotivoGlosa
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(tbxobservacionesMotivoGlosa);
				}

				if (dbxvalorGlosaIncial.getValue() == null && valido) {
					valido = false;
					dbxvalorGlosaIncial
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(dbxvalorGlosaIncial);
				} else if (valor_total < dbxvalorGlosaIncial.getValue()) {
					valido = false;
					msj = "El valor glosado no puede ser mayor que el valor total de la factura";
				}

			} else if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
					.equals("01")) {
				if (bandRespuestaGlosa.getValue().toString().trim().isEmpty()) {
					valido = false;
					bandRespuestaGlosa
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(bandRespuestaGlosa);
				}

				if (dtxFechaRespuesta.getValue() == null && valido) {
					valido = false;
					dtxFechaRespuesta
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(dtxFechaRespuesta);
				}

				if (tbxobservacionesRespuestaGlosa.getValue().trim().isEmpty()
						&& valido) {
					valido = false;
					tbxobservacionesRespuestaGlosa
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(tbxobservacionesRespuestaGlosa);
				}

			} else if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
					.equals("02")) {
				if (bandRespuestaDefinitivaGlosa.getValue().toString().trim()
						.isEmpty()) {
					valido = false;
					bandRespuestaDefinitivaGlosa
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(bandRespuestaDefinitivaGlosa);
				}

				if (dtxFechaRespuestaDefinitiva.getValue() == null && valido) {
					valido = false;
					dtxFechaRespuestaDefinitiva
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(dtxFechaRespuestaDefinitiva);
				}

				if (tbxobservacionesRespuestaDefinitivaGlosa.getValue().trim()
						.isEmpty()
						&& valido) {
					valido = false;
					tbxobservacionesRespuestaDefinitivaGlosa
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(tbxobservacionesRespuestaDefinitivaGlosa);
				}

				if (dbxvalorGlosaDefinitiva.getValue() == null && valido) {
					valido = false;
					dbxvalorGlosaDefinitiva
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(dbxvalorGlosaDefinitiva);
				}
			}
		}

		if (!valido) {
			MensajesUtil.mensajeAlerta("Advertencia", msj);
		}
		return valido;
	}

	private void glosarFacturaFinal(String tipo) throws Exception {
		if (modeloGlosa == EModeloGlosa.GRUPAL) {
			// Fechas
			facturacionAuditada.setFecha_glosa(new java.sql.Date(
					dtxFechaMotivoGlosa.getValue().getTime()));
			if (dtxFechaRespuesta.getValue() != null)
				facturacionAuditada.setFecha_respuesta_glosa(new Timestamp(
						dtxFechaRespuesta.getValue().getTime()));

			if (dtxFechaRespuestaDefinitiva.getValue() != null)
				facturacionAuditada
						.setFecha_respuesta_definitiva(new Timestamp(
								dtxFechaRespuestaDefinitiva.getValue()
										.getTime()));

			// Observaciones
			facturacionAuditada
					.setObservacion_glosa(tbxobservacionesMotivoGlosa
							.getValue());
			facturacionAuditada
					.setObservacion_respuesta(tbxobservacionesRespuestaGlosa
							.getValue());
			facturacionAuditada
					.setObservacion_definitiva_glosa(tbxobservacionesRespuestaDefinitivaGlosa
							.getValue());

			// Valores de glosa
			facturacionAuditada.setValor_glosa_inicial(dbxvalorGlosaIncial
					.getValue());
			facturacionAuditada.setValor_glosa_aceptada(dbxvalorGlosaDefinitiva
					.getValue() != null ? dbxvalorGlosaDefinitiva.getValue()
					: 0);
		}

		Facturacion facturacion = getServiceLocator().getFacturacionService()
				.guardarGlosasFact(facturacionAuditada, lista_datos, tipo,
						modeloGlosa);
		Messagebox.show("? realizada satisfactoriamente".replace("?",
				tipo.equals("1") ? "Factura glosada" : "Devolucion"), "Estado",
				Messagebox.OK, Messagebox.INFORMATION);
		mostrarDatos(facturacion, nivelFactura, modeloGlosa);
		buscarDatos();
		// reset();
		// btnDev.setVisible(false);
		// btnGlosar.setLabel("Guardar");
		// btnAceptar.setVisible(false);
		// facturacionAction.buscarDatos();
	}

	public void cancelar() {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
	}

	public void cargarTotalFactura(Facturacion facturacion) {
		dbxValor_cuota.setValue(facturacion.getValor_cuota());
		dbxSubtotal.setValue(facturacion.getValor_total());
		dbxDto_factura.setValue(facturacion.getDto_factura());

		dbxValor_copago.setValue(facturacion.getValor_copago());

		double valor_factura = facturacion.getValor_total()
				- facturacion.getDto_factura() - facturacion.getValor_copago();

		if (valor_factura < 0) {
			valor_factura = 0;
		}

		dbxValor_total.setValue(valor_factura);
		dbxDto_copago.setValue(facturacion.getDto_copago());

		chbNocopago.setChecked((facturacion.getNocopago().equals("S") ? true
				: false));
		dbxCop_canc.setValue(facturacion.getCop_canc());
	}

	public void crearFilasManuales(Manuales_tarifarios manuales_tarifarios,
			Facturacion facturacion) {
		lbxServicios.getItems().clear();
		t_ac = 0;
		t_ap = 0;
		t_at = 0;
		t_am = 0;

		c_ac = 0;
		c_ap = 0;
		c_at = 0;
		c_am = 0;
		for (int j = 0; j < lista_datos.size(); j++) {
			Detalle_factura detalle_factura = lista_datos.get(j);
			crearFilaDetalle_factura(detalle_factura, j, manuales_tarifarios);
		}
		double total_temp = (t_ac + t_am + t_ap + t_at);
		total_ac_g.setValue(new DecimalFormat("#,##0.##").format(t_ac) + "");
		total_ap_g.setValue(new DecimalFormat("#,##0.##").format(t_ap) + "");
		total_am_g.setValue(new DecimalFormat("#,##0.##").format(t_am) + "");
		total_at_g.setValue(new DecimalFormat("#,##0.##").format(t_at) + "");
		total_af_g.setValue(new DecimalFormat("#,##0.##").format(total_temp)
				+ "");
		total_af_total
				.setValue(new DecimalFormat("#,##0.##")
						.format(facturacion != null ? facturacion
								.getValor_total() : "")
						+ "");

		// Graficamos
		chart.setModel(getModelCategoria(c_ac, c_am, c_ap, c_at,
				lista_datos.size()));
	}

	private ChartModel getModelCategoria(long c_ac, long c_am, long c_ap,
			long c_at, long total) {
		String title = "Detalles(%)";
		CategoryModel model = new SimpleCategoryModel();
		// log.info("@getModelCategoria porcentaje: " + c_ac + " * " + total
		// + "/100" + (c_ac * total / 100d));
		model.setValue("CONSULTA", title, c_ac * 100d / total);
		model.setValue("MEDICAMENTOS", title, c_am * 100d / total);
		model.setValue("PROCEDIMIENTOS", title, c_ap * 100d / total);
		model.setValue("OTROS", title, c_at * 100d / total);
		return model;
	}

	public void crearFilaDetalle_factura(final Detalle_factura detalle_factura,
			int j, Manuales_tarifarios manuales_tarifarios) {

		final Listitem fila = new Listitem();
		fila.setId("fila_" + j);
		fila.setValue(j + "");

		// boolean facturable = detalle_factura.getFacturable();
		String codigo_articulo = detalle_factura.getCodigo_articulo();
		String nombre_articulo = detalle_factura.getDetalle();
		double unidades = detalle_factura.getCantidad();
		double valor_unitario = detalle_factura.getValor_unitario();
		double valor_total = detalle_factura.getValor_total();
		String tipo = detalle_factura.getTipo();

		if (facturacionAuditada.getEstado_respuesta_glosa() == null) {
			facturacionAuditada.setEstado_respuesta_glosa("");
		}

		// En esta parte especificamos
		// que si el item no es glosado
		// nos va ha permitir editar
		boolean item_glosado = false;
		if (detalle_factura.getGlosado() != null) {
			if (detalle_factura.getGlosado().equals("S")) {
				item_glosado = true;
			}
		}

		if (item_glosado) {
			fila.setStyle("background-color: #7FEDF5;");
			fila.setTooltiptext("Item glosado");
		}

		// Verificamo los valores
		EventListener<Event> eventListener = null;
		boolean consultar_procedimiento = false;
		if (tipo.equals("CONSULTA")) {
			t_ac += valor_total;
			c_ac++;
			eventListener = getEventoConsulta();
			consultar_procedimiento = true;
		} else if (tipo.equals("PROCEDIMIENTO")) {
			t_ap += valor_total;
			c_ap++;
			eventListener = getEventoProcedimiento();
			consultar_procedimiento = true;
		} else if (tipo.equals("MEDICAMENTO")) {
			t_am += valor_total;
			c_am++;
			eventListener = getEventoMedicamento();
		} else if (tipo.equals("SERVICIO")) {
			t_at += valor_total;
			c_at++;
			eventListener = getEventoServicio();
		}

		// Columna facturable
		Listcell cell = new Listcell();
		// final Checkbox chbFacturable = new Checkbox();
		// chbFacturable.setId("facturable_" + j);
		// chbFacturable.setHflex("1");
		// chbFacturable.setChecked(facturable);
		// chbFacturable.setDisabled((tipo.equals("MEDICAMENTO")
		// || tipo.equals("MATERIALES/INSUMOS") ? false : true));
		// cell.appendChild(chbFacturable);
		fila.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/info_icon.png");
		toolbarbutton.setTooltiptext("Informacion del detalle");
		toolbarbutton.setAttribute(DTT_FACTURA, detalle_factura);
		if (eventListener != null)
			toolbarbutton.addEventListener(Events.ON_CLICK, eventListener);
		Listcell cellInfo = new Listcell();
		cellInfo.appendChild(toolbarbutton);
		fila.appendChild(cellInfo);

		String codigo_articulo_aux = codigo_articulo;
		if (consultar_procedimiento) {
			Procedimientos procedimiento = new Procedimientos();
			procedimiento.setId_procedimiento(new Long(codigo_articulo));
			procedimiento = getServiceLocator().getProcedimientosService()
					.consultar(procedimiento);
			if (procedimiento != null) {
				codigo_articulo_aux = procedimiento.getCodigo_cups();
			}
		}

		// Columna codigo
		cell = new Listcell();
		Label label = new Label(codigo_articulo_aux);
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Listcell();
		label = new Label(nombre_articulo);
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna cantidad //
		cell = new Listcell();
		label = new Label(Utilidades.formatDecimal(unidades, "#0"));
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna valor unitario //
		cell = new Listcell();
		label = new Label(Utilidades.formatDecimal(valor_unitario, "#,##0"));
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna valor total //
		cell = new Listcell();
		label = new Label(Utilidades.formatDecimal(valor_total, "#,##0"));
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna tipo //
		cell = new Listcell();
		label = new Label(tipo);
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// TODO: Aqui van las adiciones de la columnas
		// Motivo
		BandBoxRowMacro bandBoxRowMacro = getBandBoxRowMacro(detalle_factura,
				"estado_glosa", getMotivoConsultaBandBox());
		bandBoxRowMacro.setHflex("1");
		cell = new Listcell();
		cell.appendChild(bandBoxRowMacro);
		fila.appendChild(cell);
		fila.setAttribute(BANDBOX_GLOSA_INICIAL, bandBoxRowMacro);

		Datebox datebox = new Datebox();
		datebox.setHflex("1");
		datebox.setFormat("yyyy-MM-dd");
		// Evento de adicion de fechas
		datebox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Datebox datebox = (Datebox) arg0.getTarget();
				if (datebox.getValue() != null)
					detalle_factura.setFecha_glosa(new Timestamp(datebox
							.getValue().getTime()));
			}
		});
		datebox.setValue(detalle_factura.getFecha_glosa());

		cell = new Listcell();
		cell.appendChild(datebox);
		fila.appendChild(cell);

		CampoObservacionesPopupMacro textboxObservaciones = new CampoObservacionesPopupMacro(
				this);
		textboxObservaciones.setHflex("1");
		textboxObservaciones.setOnCambioTexto(new OnCambioTexto() {
			@Override
			public void texto(String texto) {
				detalle_factura.setObservacion_glosa(texto);
			}
		});
		cell = new Listcell();
		cell.appendChild(textboxObservaciones);
		fila.appendChild(cell);

		// Mostramos
		if (detalle_factura.getObservacion_glosa() != null) {
			textboxObservaciones.setText(detalle_factura.getObservacion_glosa()
					+ "");
		}

		// desabilitamos dependiendo
		if (facturacionAuditada.getEstado_respuesta_glosa().equals("01")
				|| facturacionAuditada.getEstado_respuesta_glosa().equals("02")
				|| facturacionAuditada.getEstado_respuesta_glosa().equals("00")) {
			bandBoxRowMacro.setReadonly(true);
			bandBoxRowMacro.setButtonVisible(false);
			textboxObservaciones.setDisabled(true);
			datebox.setReadonly(true);
			datebox.setButtonVisible(false);
		}

		// Respuesta
		bandBoxRowMacro = getBandBoxRowMacro(detalle_factura, "respueta_glosa",
				ESTADO_RESPUESTA);
		bandBoxRowMacro.setHflex("1");
		cell = new Listcell();
		cell.appendChild(bandBoxRowMacro);
		fila.appendChild(cell);
		fila.setAttribute(BANDBOX_RESPUESTA_GLOSA, bandBoxRowMacro);

		datebox = new Datebox();
		datebox.setHflex("1");
		datebox.setFormat("yyyy-MM-dd");
		// Evento de adicion de fechas
		datebox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Datebox datebox = (Datebox) arg0.getTarget();
				if (datebox.getValue() != null)
					detalle_factura.setFecha_respuesta_glosa(new Timestamp(
							datebox.getValue().getTime()));
			}
		});
		datebox.setValue(detalle_factura.getFecha_respuesta_glosa());
		cell = new Listcell();
		cell.appendChild(datebox);
		fila.appendChild(cell);

		textboxObservaciones = new CampoObservacionesPopupMacro(this);
		textboxObservaciones.setHflex("1");
		textboxObservaciones.setOnCambioTexto(new OnCambioTexto() {
			@Override
			public void texto(String texto) {
				detalle_factura.setObservacion_respuesta(texto);
			}
		});
		cell = new Listcell();
		cell.appendChild(textboxObservaciones);
		fila.appendChild(cell);

		// Mostramos
		if (detalle_factura.getObservacion_respuesta() != null) {
			textboxObservaciones.setText(detalle_factura
					.getObservacion_respuesta() + "");
		}

		// desabilitamos dependiendo
		if (facturacionAuditada.getEstado_respuesta_glosa().equals("02")
				|| facturacionAuditada.getEstado_respuesta_glosa().equals("00")
				|| !item_glosado) {
			bandBoxRowMacro.setReadonly(true);
			bandBoxRowMacro.setButtonVisible(false);
			textboxObservaciones.setDisabled(true);

			// Eto es para cuando no sea glosado, tambien deshabilite la linea
			if (!item_glosado
					&& (facturacionAuditada.getEstado_respuesta_glosa().equals(
							"02") || facturacionAuditada
							.getEstado_respuesta_glosa().equals("00")))
				fila.setDisabled(true);
			datebox.setReadonly(true);
			datebox.setButtonVisible(false);
		}

		// respuesta definitiva
		bandBoxRowMacro = getBandBoxRowMacro(detalle_factura,
				"respuesta_definitiva", ESTADO_GLOSA);
		bandBoxRowMacro.setHflex("1");
		cell = new Listcell();
		cell.appendChild(bandBoxRowMacro);
		fila.appendChild(cell);
		fila.setAttribute(BANDBOX_GLOSA_DEFINITIVA, bandBoxRowMacro);

		datebox = new Datebox();
		datebox.setHflex("1");
		datebox.setFormat("yyyy-MM-dd");
		// Evento de adicion de fechas
		datebox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Datebox datebox = (Datebox) arg0.getTarget();
				if (datebox.getValue() != null)
					detalle_factura
							.setFecha_respuesta_definitiva(new Timestamp(
									datebox.getValue().getTime()));
			}
		});
		datebox.setValue(detalle_factura.getFecha_respuesta_definitiva());
		cell = new Listcell();
		cell.appendChild(datebox);
		fila.appendChild(cell);

		textboxObservaciones = new CampoObservacionesPopupMacro(this);
		textboxObservaciones.setHflex("1");
		textboxObservaciones.setOnCambioTexto(new OnCambioTexto() {
			@Override
			public void texto(String texto) {
				detalle_factura.setObservacion_definitiva_glosa(texto);
			}
		});
		cell = new Listcell();
		cell.appendChild(textboxObservaciones);
		fila.appendChild(cell);

		// Mostramos
		if (detalle_factura.getObservacion_definitiva_glosa() != null) {
			textboxObservaciones.setText(detalle_factura
					.getObservacion_definitiva_glosa() + "");
		}

		// Respuesta final de glosa
		Listbox listboxRespuestaFinal = getListaRespuestaFinal();
		Res.cargarAutomatica(listboxRespuestaFinal, detalle_factura,
				"estado_final_glosa");
		cell = new Listcell();
		cell.appendChild(listboxRespuestaFinal);
		fila.appendChild(cell);

		if (facturacionAuditada.getEstado_respuesta_glosa().equals("00")
				|| !item_glosado) {
			bandBoxRowMacro.setReadonly(true);
			bandBoxRowMacro.setButtonVisible(false);
			textboxObservaciones.setDisabled(true);
			if (!item_glosado
					&& (facturacionAuditada.getEstado_respuesta_glosa()
							.equals("00")))
				fila.setDisabled(true);
			datebox.setReadonly(true);
			datebox.setButtonVisible(false);
			listboxRespuestaFinal.setDisabled(true);
		}

		lbxServicios.appendChild(fila);
		lbxServicios.setMold("paging");
		lbxServicios.setPageSize(20);

		// chbFacturable.addEventListener("onCheck", new EventListener<Event>()
		// {
		// @Override
		// public void onEvent(Event event) throws Exception {
		//
		// actualizarPagina();
		// calcularTotal();
		// }
		//
		// });
	}

	private EventListener<Event> getEventoServicio() {
		if (eventListenerServicio == null) {
			eventListenerServicio = new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Component component = arg0.getTarget();
					Detalle_factura detalle_factura = (Detalle_factura) component
							.getAttribute(DTT_FACTURA);
					Facturacion_servicio facturacion_servicio = new Facturacion_servicio();
					facturacion_servicio.setCodigo_empresa(detalle_factura
							.getCodigo_empresa());
					facturacion_servicio.setCodigo_sucursal(detalle_factura
							.getCodigo_sucursal());
					facturacion_servicio.setNro_factura(detalle_factura
							.getFactura_concepto());
					facturacion_servicio = getServiceLocator().getServicio(
							Facturacion_servicioService.class).consultar(
							facturacion_servicio);
					if (facturacion_servicio != null) {
						Map<String, Object> parametro = new HashMap<String, Object>();
						parametro.put("codigo_empresa",
								facturacion_servicio.getCodigo_empresa());
						parametro.put("codigo_sucursal",
								facturacion_servicio.getCodigo_sucursal());
						parametro.put("nro_factura",
								facturacion_servicio.getNro_factura());
						List<Datos_servicio> datos_servicios = getServiceLocator()
								.getServicio(Datos_servicioService.class)
								.listar(parametro);

						if (datos_servicios.isEmpty())
							MensajesUtil.mensajeAlerta(
									"Advetencia",
									"El rips asociado a la factura no existe nro de rips de servicio "
											+ detalle_factura
													.getFactura_concepto());

						Datos_servicio datos_serviciosTemp = datos_servicios
								.get(0);
						// mapeo de informacion
						Map<String, Object> mapa_facturacion_servicios = new HashMap<String, Object>();
						mapa_facturacion_servicios.put("nro_factura",
								facturacion_servicio.getNro_factura());
						mapa_facturacion_servicios.put("tipo_identificacion",
								facturacion_servicio.getTipo_identificacion());
						mapa_facturacion_servicios.put("nro_identificacion",
								facturacion_servicio.getNro_identificacion());
						mapa_facturacion_servicios.put("numero_autorizacion",
								facturacion_servicio.getNumero_autorizacion());
						mapa_facturacion_servicios.put("codigo_servicio",
								datos_serviciosTemp.getCodigo_servicio());
						mapa_facturacion_servicios.put("tipo_servicio",
								datos_serviciosTemp.getTipo_servicio());
						mapa_facturacion_servicios.put("nombre_servicio",
								detalle_factura.getDetalle());
						mapa_facturacion_servicios.put("unidades",
								detalle_factura.getCantidad());
						mapa_facturacion_servicios.put("valor_unitario",
								detalle_factura.getValor_unitario());
						mapa_facturacion_servicios.put("valor_total",
								detalle_factura.getValor_total());

						// mostramos informacion de los detalles
						mostrarVisualizador(
								mapa_facturacion_servicios,
								VisualizadorRipsMacro.PARAM_TIPOS_RIPS_SERVICIOS);
					} else {
						MensajesUtil
								.mensajeAlerta(
										"Advetencia",
										"El rips asociado a la factura no existe nro de rips de servicio "
												+ detalle_factura
														.getFactura_concepto());
					}
				}
			};
		}
		return eventListenerServicio;
	}

	private EventListener<Event> getEventoMedicamento() {
		if (eventListenerMedicamento == null) {
			eventListenerMedicamento = new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Component component = arg0.getTarget();
					Detalle_factura detalle_factura = (Detalle_factura) component
							.getAttribute(DTT_FACTURA);
					Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
					facturacion_medicamento.setCodigo_empresa(detalle_factura
							.getCodigo_empresa());
					facturacion_medicamento.setCodigo_sucursal(detalle_factura
							.getCodigo_sucursal());
					facturacion_medicamento.setNro_factura(detalle_factura
							.getFactura_concepto());
					facturacion_medicamento = getServiceLocator().getServicio(
							Facturacion_medicamentoService.class).consultar(
							facturacion_medicamento);
					if (facturacion_medicamento != null) {
						Map<String, Object> parametro = new HashMap<String, Object>();
						parametro.put("codigo_empresa",
								facturacion_medicamento.getCodigo_empresa());
						parametro.put("codigo_sucursal",
								facturacion_medicamento.getCodigo_sucursal());
						parametro.put("nro_factura",
								facturacion_medicamento.getNro_factura());
						List<Datos_medicamentos> datos_medicamentos = getServiceLocator()
								.getServicio(Datos_medicamentosService.class)
								.listar(parametro);

						if (datos_medicamentos.isEmpty())
							MensajesUtil.mensajeAlerta(
									"Advetencia",
									"El rips asociado a la factura no existe nro de rips de medicamento "
											+ detalle_factura
													.getFactura_concepto());

						Datos_medicamentos datos_medicamentosTemp = datos_medicamentos
								.get(0);
						// mapeo de informacion
						Map<String, Object> mapa_facturacion_medicamento = new HashMap<String, Object>();
						mapa_facturacion_medicamento.put("nro_factura",
								facturacion_medicamento.getNro_factura());
						mapa_facturacion_medicamento.put("tipo_identificacion",
								facturacion_medicamento
										.getTipo_identificacion());
						mapa_facturacion_medicamento
								.put("nro_identificacion",
										facturacion_medicamento
												.getNro_identificacion());
						mapa_facturacion_medicamento.put("numero_autorizacion",
								facturacion_medicamento
										.getNumero_autorizacion());
						mapa_facturacion_medicamento.put("codigo_medicamento",
								datos_medicamentosTemp.getCodigo_medicamento());
						mapa_facturacion_medicamento.put("tipo_medicamento",
								datos_medicamentosTemp.getTipo_medicamento());
						mapa_facturacion_medicamento.put("nombre_medicamento",
								detalle_factura.getDetalle());
						mapa_facturacion_medicamento.put("unidades",
								detalle_factura.getCantidad());
						mapa_facturacion_medicamento.put("valor_unitario",
								detalle_factura.getValor_unitario());
						mapa_facturacion_medicamento.put("valor_total",
								detalle_factura.getValor_total());

						mapa_facturacion_medicamento.put("nombre_generico",
								datos_medicamentosTemp.getNombre_generico());
						mapa_facturacion_medicamento.put("forma_farmaceutica",
								datos_medicamentosTemp.getForma_farmaceutica());
						mapa_facturacion_medicamento.put(
								"concentracion_medicamento",
								datos_medicamentosTemp
										.getConcentracion_medicamento());
						mapa_facturacion_medicamento.put("unidad_medida",
								datos_medicamentosTemp.getUnidad_medida());

						// mostramos informacion de los detalles
						mostrarVisualizador(
								mapa_facturacion_medicamento,
								VisualizadorRipsMacro.PARAM_TIPOS_RIPS_MEDICAMENTO);
					} else {
						MensajesUtil
								.mensajeAlerta(
										"Advetencia",
										"El rips asociado a la factura no existe nro de rips de medicamento "
												+ detalle_factura
														.getFactura_concepto());
					}
				}
			};
		}
		return eventListenerMedicamento;
	}

	private EventListener<Event> getEventoProcedimiento() {
		if (eventListenerProcedimiento == null) {
			eventListenerProcedimiento = new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Component component = arg0.getTarget();
					Detalle_factura detalle_factura = (Detalle_factura) component
							.getAttribute(DTT_FACTURA);
					Datos_procedimiento datos_procedimiento = new Datos_procedimiento(
							false);
					datos_procedimiento.setCodigo_empresa(detalle_factura
							.getCodigo_empresa());
					datos_procedimiento.setCodigo_sucursal(detalle_factura
							.getCodigo_sucursal());
					datos_procedimiento
							.setCodigo_registro(Utilidades
									.getValorLong(detalle_factura
											.getFactura_concepto()));
					datos_procedimiento = getServiceLocator().getServicio(
							Datos_procedimientoService.class).consultar(
							datos_procedimiento);
					if (datos_procedimiento != null) {
						mostrarVisualizador(
								datos_procedimiento,
								VisualizadorRipsMacro.PARAM_TIPOS_RIPS_PROCEDIMIENTO);
					} else {
						MensajesUtil
								.mensajeAlerta(
										"Advetencia",
										"El rips asociado a la factura no existe nro de rips de procedimiento "
												+ detalle_factura
														.getFactura_concepto());
					}
				}
			};
		}
		return eventListenerProcedimiento;
	}

	private EventListener<Event> getEventoConsulta() {
		if (eventListenerConsulta == null) {
			eventListenerConsulta = new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					Component component = arg0.getTarget();
					Detalle_factura detalle_factura = (Detalle_factura) component
							.getAttribute(DTT_FACTURA);
					Datos_consulta datos_consulta = new Datos_consulta();
					datos_consulta.setCodigo_empresa(detalle_factura
							.getCodigo_empresa());
					datos_consulta.setCodigo_sucursal(detalle_factura
							.getCodigo_sucursal());
					datos_consulta
							.setCodigo_registro(Utilidades
									.getValorLong(detalle_factura
											.getFactura_concepto()));
					datos_consulta = getServiceLocator().getServicio(
							Datos_consultaService.class).consultar(
							datos_consulta);
					if (datos_consulta != null) {
						mostrarVisualizador(datos_consulta,
								VisualizadorRipsMacro.PARAM_TIPOS_RIPS_CONSULTA);
					} else {
						MensajesUtil
								.mensajeAlerta(
										"Advetencia",
										"El rips asociado a la factura no existe nro de rips de consulta "
												+ detalle_factura
														.getFactura_concepto());
					}
				}
			};
		}
		return eventListenerConsulta;
	}

	private void mostrarVisualizador(Object parametro_rips, String parametro) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put(VisualizadorRipsMacro.PARAM_RIPS, parametro_rips);
		parametros.put(VisualizadorRipsMacro.PARAM_TIPOS_RIPS, parametro);
		VisualizadorRipsMacro visualizadorRipsMacro = (VisualizadorRipsMacro) Executions
				.createComponents("/pages/visualizadorRipsMacro.zul",
						GlosasDevolucionesAction.this, parametros);
		visualizadorRipsMacro.doModal();
	}

	private Listbox getListaRespuestaFinal() {
		Listbox listboxRespuestaFinal = new Listbox();
		listboxRespuestaFinal.setName("respuesta_final_glosa");
		listboxRespuestaFinal.setHflex("1");
		listboxRespuestaFinal.setMold("select");
		Utilidades.listarElementoListbox(listboxRespuestaFinal, true,
				getServiceLocator());
		if (listboxRespuestaFinal.getItemCount() > 0) {
			listboxRespuestaFinal.setSelectedIndex(0);
		}
		return listboxRespuestaFinal;
	}

	private BandBoxRowMacro getBandBoxRowMacro(Detalle_factura detalle_factura,
			String campo, String tipo_concepto) {
		BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro();
		bandBoxRowMacro.configurar(getConfiguracionBandbox(detalle_factura,
				campo, tipo_concepto));
		/**
		 * Consultamos el elemento correspondiente
		 * */
		Object valor = Res.getValor(detalle_factura, campo);
		setValorBandBox(valor, bandBoxRowMacro, tipo_concepto);
		return bandBoxRowMacro;
	}

	private void setValorBandBox(Object valor, BandBoxRowMacro bandBoxRowMacro,
			String tipo_concepto) {
		if (valor != null && !valor.toString().trim().isEmpty()) {
			Elemento elemento = new Elemento();
			elemento.setCodigo(valor.toString());
			elemento.setTipo(tipo_concepto);
			elemento = getServiceLocator().getServicio(ElementoService.class)
					.consultar(elemento);
			bandBoxRowMacro.seleccionarRegistro(elemento, elemento.getCodigo()
					+ " " + elemento.getDescripcion());
		}
	}

	private IConfiguracionBandbox getConfiguracionBandbox(final Object object,
			final String campo, final String tipo_concepto) {
		return new IConfiguracionBandbox<Elemento>() {
			@Override
			public void onSeleccionar(Elemento t, Bandbox bandbox) {
				Res.setValor(object, campo, t.getCodigo());
			}

			@Override
			public String getCabecera(Bandbox bandbox) {
				return "Codigo#100px|Descripcion";
			}

			@Override
			public String getWidthListBox() {
				return "500px";
			}

			@Override
			public String getHeightListBox() {
				return "200px";
			}

			@Override
			public void renderListitem(Listitem listitem, Elemento registro,
					Bandbox bandbox) {
				// //log.info("@renderListitem");
				listitem.appendChild(new Listcell("" + registro.getCodigo()));
				listitem.appendChild(new Listcell(""
						+ registro.getDescripcion()));
			}

			@Override
			public List<Elemento> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros, Bandbox bandbox) {
				// log.info("Listar registros");
				parametros.put("tipo", tipo_concepto);
				parametros.put("parametroTodo", valorBusqueda);
				parametros.put("empieza_por", lbx_concepto.getSelectedItem()
						.getValue().toString());
				return getServiceLocator().getServicio(ElementoService.class)
						.listar(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Elemento registro) {
				Res.setValor(object, campo, registro.getCodigo());
				bandbox.setValue(registro.getCodigo() + " - "
						+ registro.getDescripcion());
				// log.info("Agrego nuevo registro");
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				Res.setValor(object, campo, "");
			}
		};
	}

	public void aplicarSeleccionados() {
		Elemento elemento = tbxConceptoAplicar.getRegistroSeleccionado();
		if (elemento != null) {
			Set<Listitem> listitems = lbxServicios.getSelectedItems();
			if (!listitems.isEmpty()) {
				for (Listitem listitem : listitems) {
					BandBoxRowMacro bandBoxRowMacro = null;
					if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
							.equals("01")) {
						bandBoxRowMacro = (BandBoxRowMacro) listitem
								.getAttribute(BANDBOX_RESPUESTA_GLOSA);
					} else if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
							.equals("01")) {
						bandBoxRowMacro = (BandBoxRowMacro) listitem
								.getAttribute(BANDBOX_GLOSA_DEFINITIVA);
					} else {
						bandBoxRowMacro = (BandBoxRowMacro) listitem
								.getAttribute(BANDBOX_GLOSA_INICIAL);
					}

					if (bandBoxRowMacro != null) {
						bandBoxRowMacro.seleccionarRegistro(
								elemento,
								elemento.getCodigo() + " - "
										+ elemento.getDescripcion());
					}
				}
			} else {
				Notificaciones
						.mostrarNotificacionAlerta(
								"Advertencia",
								"Para realizar esta accion debes por lo menos seleccionar un item",
								3000);
			}
		} else {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"Campo de concepto requerido", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		}
	}

	public void limpiarSeleccionados() {
		Set<Listitem> listitems = lbxServicios.getSelectedItems();
		if (!listitems.isEmpty()) {
			for (Listitem listitem : listitems) {
				BandBoxRowMacro bandBoxRowMacro = null;
				if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("01")) {
					bandBoxRowMacro = (BandBoxRowMacro) listitem
							.getAttribute(BANDBOX_RESPUESTA_GLOSA);
				} else if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("02")) {
					bandBoxRowMacro = (BandBoxRowMacro) listitem
							.getAttribute(BANDBOX_GLOSA_DEFINITIVA);
				} else {
					bandBoxRowMacro = (BandBoxRowMacro) listitem
							.getAttribute(BANDBOX_GLOSA_INICIAL);
				}
				if (bandBoxRowMacro != null) {
					bandBoxRowMacro.limpiarSeleccion(true);
				}
			}
		} else {
			Notificaciones
					.mostrarNotificacionAlerta(
							"Advertencia",
							"Para realizar esta accion debes por lo menos seleccionar un item",
							3000);
		}
	}

	public String getMotivoConsultaConceptos() {
		if (facturacionAuditada != null && nivelFactura != null) {
			if (nivelFactura == ENivelFactura.DEVOLUCION) {
				return ESTADO_DEVOLUCION;
			} else {
				if ((facturacionAuditada.getEstado_respuesta_glosa() + "")
						.equals("01")) {
					return ESTADO_RESPUESTA;
				} else {
					return ESTADO_GLOSA;
				}
			}
		} else {
			return "";
		}
	}

	public String getMotivoConsultaBandBox() {
		if (facturacionAuditada != null && nivelFactura != null) {
			if (nivelFactura == ENivelFactura.DEVOLUCION) {
				return ESTADO_DEVOLUCION;
			} else {
				return ESTADO_GLOSA;
			}
		} else {
			return "";
		}
	}

	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Detalle_factura detalle_factura = lista_datos.get(i);
			Checkbox chbFacturable = (Checkbox) lbxServicios
					.getFellow("facturable_" + i);
			detalle_factura.setFacturable(chbFacturable.isChecked());
		}
	}

	public void calcularTotal() {
		try {
			double subtotal = 0;
			for (int i = 0; i < lista_datos.size(); i++) {
				Detalle_factura detalle_factura = lista_datos.get(i);
				if (detalle_factura.getFacturable()) {
					subtotal += detalle_factura.getValor_total();
				}
			}
			dbxSubtotal.setValue(subtotal);
			calcularCopagoManual();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void calcularCopagoManual() {
		try {
			if (dbxValor_copago.getValue() == null) {
				throw new Exception("Valor de copago no es valido");
			} else if (dbxValor_copago.getValue() < 0) {
				throw new Exception("Valor de copago no es valido");
			}
			dbxValor_total.setValue(dbxSubtotal.getValue()
					- dbxValor_copago.getValue());

			if (dbxValor_copago.getValue() > 0) {
				chbNocopago.setChecked(false);
			} else {
				chbNocopago.setChecked(true);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);

		}
	}

	private void bloqueoBotonGuardar(boolean sw) {

		if (!sw) {
			((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(false);
			((Button) groupboxEditar.getFellow("btGuardar"))
					.setImage("/images/Save16.gif");
		} else {
			((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(true);
			((Button) groupboxEditar.getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}
	}

	public void deshabilitarCampos(boolean sw) {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw,
				idsExcluyentes);
		bloqueoBotonGuardar(sw);
		if (sw) {
			listarIngresos(lbxNro_ingreso, new LinkedList<Admision>(), true);
		}
	}

	public void listarIngresos(Listbox listbox, List<Admision> listaAdmisiones,
			boolean select) {
		listbox.getItems().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("-- --");
			listbox.appendChild(listitem);
		}

		for (Admision a : listaAdmisiones) {
			Administradora admin = new Administradora();
			admin.setCodigo_empresa(a.getCodigo_empresa());
			admin.setCodigo_sucursal(a.getCodigo_sucursal());
			admin.setCodigo(a.getCodigo_administradora());
			admin = getServiceLocator().getAdministradoraService().consultar(
					admin);

			listitem = new Listitem();
			listitem.setValue(a);
			listitem.setLabel(a.getNro_ingreso() + " -- "
					+ (admin != null ? admin.getNombre() : ""));
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public List<Admision> listarAdmisiones(Facturacion facturacion, boolean sw) {
		Map paramAdmision = new HashMap();
		paramAdmision.put("codigo_empresa", facturacion.getCodigo_empresa());
		paramAdmision.put("codigo_sucursal", facturacion.getCodigo_sucursal());
		paramAdmision
				.put("nro_identificacion", facturacion.getCodigo_tercero());
		paramAdmision.put("order", "fecha_ingreso desc");
		if (sw) {
			paramAdmision.put("nro_ingreso", facturacion.getNro_ingreso());
		} else {
			paramAdmision.put("estado", "1");
		}

		List<Admision> listaAdmisiones = getServiceLocator().getServicio(
				AdmisionService.class).listarTabla(paramAdmision);

		return listaAdmisiones;
	}

	public void cargarAdmisiones(Admision aux2) {
		if (aux2 != null) {
			deshabilitarCampos(false);

			datos_seleccion.put("codigo_administradora",
					aux2.getCodigo_administradora());
			datos_seleccion.put("id_plan", aux2.getId_plan());

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(aux2.getCodigo_empresa());
			administradora.setCodigo_sucursal(aux2.getCodigo_sucursal());
			administradora.setCodigo(aux2.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(aux2.getCodigo_empresa());
			contratos.setCodigo_sucursal(aux2.getCodigo_sucursal());
			contratos.setCodigo_administradora(aux2.getCodigo_administradora());
			contratos.setId_plan(aux2.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(
					contratos);
			tbxAseguradora.setValue((administradora != null ? administradora
					.getNombre() : "")
					+ " - "
					+ (contratos != null ? contratos.getNombre() : ""));

			dtbxFecha_ingreso.setValue(aux2.getFecha_ingreso());
			// consultarServiciosFacturas(true);

			dtbxFecha_ingreso.setButtonVisible(false);
			dtbxFecha_ingreso.setReadonly(true);
		}
	}

	public void selectedAdmision(Listitem listitem) throws Exception {
		Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
				.getValue());
		if (admision == null) {
			limpiarDatos();
			deshabilitarCampos(true);
		} else {
			cargarAdmisiones(admision);
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		tbxNro_identificacion.setValue("");
		tbxNro_identificacion.setDisabled(false);
		deshabilitarCampos(true);
		
		tbxDescripcion.setValue("FACTURACION DE PROCEDIMIENTOS Y CONSULTAS");
		ibxPlazo.setValue(30);

		chbNocopago.setChecked(true);
		chbAgrupar.setChecked(true);
		chbMediana.setChecked(false);

		dtbxFecha_ingreso.setButtonVisible(false);
		dtbxFecha_ingreso.setReadonly(true);

		lista_datos.clear();
		try {
			crearFilasManuales(null, null);
			// calcularTotal();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	/* COMPLEMENTO DE RIPS */
	private void mostrarVista(String page, Tabpanel tabpanel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fact", facturacionAuditada);
		Component componente = Executions.createComponents(page, this, params);
		final Window ventana = (Window) componente;
		tabpanel.appendChild(ventana);
	}

	public void cargarPestaniaHospitalizacion() {
		tabpanelHospitalizacion.getChildren().clear();
		mostrarVista("/pages/rips_ah.zul", tabpanelHospitalizacion);
	}

	public void cargarPestaniaUrgencia() {
		tabpanelUrgencia.getChildren().clear();
		mostrarVista("/pages/rips_au.zul", tabpanelUrgencia);
	}

	public void cargarPestaniaRecienNacido() {
		tabpanelReciennacido.getChildren().clear();
		mostrarVista("/pages/rips_an.zul", tabpanelReciennacido);
	}

	public void cargarPestaniaDescripcionAgrupada() {
		tabPanelDescripcionAgrupada.getChildren().clear();
		mostrarVista("/pages/rips_ad.zul", tabPanelDescripcionAgrupada);
	}

	public void imprimirNota_contable(String codigo_comprobante, Long id_factura)
			throws Exception {
		if (id_factura != null) {
			Messagebox.show("El documento no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Nota_contable");
		paramRequest.put("codigo_comprobante", codigo_comprobante);
		paramRequest.put("codigo_documento", id_factura + "");
		paramRequest.put("formato", "pdf");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void contabilizarGlosa(Long id_factura, boolean sw) throws Exception {
		try {
			if (id_factura != null && sw) {
				Messagebox
						.show("El documento no se ha guardado aun",
								"Informacion ..", Messagebox.OK,
								Messagebox.EXCLAMATION);
				return;
			}
			Map<String, Object> param_cont = new HashMap<String, Object>();
			param_cont.put("id_factura", id_factura);

			Nota_contable nota_contable = getServiceLocator().getServicio(
					FacturacionClinicaCompraService.class)
					.guardarContabilizacion(param_cont, sw);
			if (sw && nota_contable != null) {
				Messagebox
						.show("El documento fue contabilizado satisfactoriamente",
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);
			} else if (!sw) {
				Map parametros = new HashMap();
				parametros.put("nota_contable", nota_contable);
				Component componente = Executions.createComponents(
						"/pages/ver_contabilizacion.zul", this, parametros);
				final Window ventana = (Window) componente;
				ventana.setWidth("850px");
				ventana.setHeight("450px");
				ventana.setClosable(true);
				ventana.setMaximizable(true);
				ventana.setTitle("CONTABILIZAcion DEL DOCUMENTO");
				ventana.setMode("modal");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

}
