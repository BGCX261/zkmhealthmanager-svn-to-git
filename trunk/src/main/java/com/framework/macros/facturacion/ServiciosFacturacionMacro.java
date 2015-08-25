package com.framework.macros.facturacion;

import healthmanager.controller.Facturacion_ripsAction;
import healthmanager.controller.Facturacion_ripsAction.OnMultiplesContratosAccion;
import healthmanager.controller.Facturacion_ripsAction.VerificarPaquetes;
import healthmanager.controller.GeneralComposer;
import healthmanager.controller.ZKWindow;
import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Admision_cama;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Copago_estrato;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.service.Admision_camaService;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Copago_estratoService;
import healthmanager.modelo.service.Datos_consultaService;
import healthmanager.modelo.service.Datos_medicamentosService;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Datos_servicioService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Facturacion_medicamentoService;
import healthmanager.modelo.service.Facturacion_servicioService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Via_ingreso_contratadasService;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.expression.MapAccessor;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.ITiposServicio;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.macros.macro_row.BandBoxRowMacro.IConfiguracionBandbox;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.CargardorDeDatos;
import com.framework.res.L2HContraintDate;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.ArticuloService;
import contaweb.modelo.service.FacturacionService;

/**
 * Este macro se utiliza para centralizar la manipulacion de servicios del
 * modulo de factura, como adicion, eliminacion, etc.
 * 
 * @author l2
 * */
public class ServiciosFacturacionMacro extends Listbox implements AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3455996419532093625L;

	public static Logger log = Logger
			.getLogger(ServiciosFacturacionMacro.class);

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");

	public static final String DETALLE_NUEVO = "DF_N";
	public static final String DETALLE_FACTURA = "DF";
	public static final String NRO_AUTORIZACION = "_NA";
	public static final String FECHA_REALIZACION = "FEC_REAL";

	public static final String REGISTRO_SELECCIONADO = "REG_SEL";
	public static final String CODIGO_RIPS = "COD_RIPS";

	// private final String MANUAL_TARIFARIO = "MT";
	// private final String CONTRATO = "C";
	private final String TIPO_SERVICIO = "TS";
	private final String MAP_DETALLE_FACTURA = "MDF";

	// private final String LISTBOX_UNIDAD_FUNCIONAL = "LUF";
	public static final String TOOLBAR_BUTTON_COMPLEMENTO = "TBCOMP";

	private final String DOUBLE_BOX_UNIDADES = "DBU";
	public static final String DATE_BOX_REALIZACION = "DBFR";
	private final String DOUBLE_BOX_VALOR_UNITARIO = "DBVU";
	private final String DOUBLE_BOX_VALOR_TOTAL = "DBVT";
	private final String LABEL_NOMBRE_SERVICIO = "LNS";
	private final String ATRIBUTO_TIPO_SERVICIO = "ALTS";
	private final String CELDA_TIPO_SERVICIO = "CLTS";
	private final String TOOLBAR_BUTON_COMPL = "TBC";
	private final String TOOLBAR_BUTON_SELEC = "TBS";
	private final String LISTCELL_SELEC = "LCS";
	private final String BAND_BOX_SERVICIO = "BBS";
	private final String _LISTITEM = "_LI";

	private final String PARAM_UNICA_SELECCION = "PUS_";

	public static final String MAP_SERVICIOS = "MS";
	public static final String MAP_FACT_MEDICAMENTO = "MFM";
	public static final String MAP_FACT_MATERIALES_INSUMO = "MFMI";
	public static final String MAP_FACT_SERVICIOS = "MFS";
	public static final String MAP_SERVICIOS_ELIMINAR = "MSE";

	public static final String PARAM_LISTADO_SERVICIOS = "PLS";
	public static final String PARAM_WINDOW_PADRE = "PWP";
	public static final String PARAM_PACIENTE = "PP";
	public static final String PARAM_AUTORIZACION = "PAN";

	protected static final String ATTRI_CANTIDAD_MAXIMA = "ATCM";
	protected static final String ATTRI_TIPO_DESCRIPCION = "ATTD";
	protected static final String ATTRI_DESCRIPCION = "ATD";

	private static final String ATTRI_NUEVO = "ATN";

	private List<Map<String, Object>> lista_servicios;
	private List<Map<String, Object>> lista_servicios_validados;
	private List<Datos_servicio> lista_datos_servicios;
	private List<Detalle_factura> listado_servicios_eliminado;

	/* unidades funcionales */
	private List<Elemento> listado_vias_ingreso;
	private Elemento elemento_via_ingreso_principal;
	private Configuracion_admision_ingreso configuracion_admision_ingreso;

	private Paciente paciente;
	private Administradora administradora;
	private Prestadores prestadores;
	private Date fecha_ingreso;
	private Date fecha_egreso;
	private Admision admision;
	private Contratos contratos;
	private Via_ingreso_contratadas via_ingreso_contratadas;
	private List<Contratos> listado_contratos_pacientes;
	private GeneralComposer generalComposer;

	private Boolean administradora_particular = false;

	private CargaRapidaInformacionUltimoRegistro informacionUltimoRegistro;

	private OnMultiplesContratosAccion onMultiplesContratosAccion;
	private VerificarPaquetes verificarPaquetes;

	// Eventos centralizados
	private EventListener<Event> eventListenerOnBlurCantidad;
	private EventListener<Event> eventListenerOnBlurUnitario;
	private EventListener<Event> eventListenerOnOkCantidad;
	private EventListener<Event> eventListenerOnOkUnitario;
	private EventListener<Event> eventListenerOnClickCompl;
	private EventListener<Event> eventListenerOnOkCompl;

	@View
	private Toolbarbutton tbbAgregarServicios;
	@View
	private Toolbarbutton tbbAgregarArticulos;
	@View
	private Toolbarbutton tbbQuitar;
	// @View private Toolbarbutton tbbComplementacionMultiple;

	@View
	private Checkbox chkIncluir_paquetes;
	@View
	private Checkbox chbNocopago;
	@View
	private Checkbox chbMediana;
	@View
	private Checkbox chbAgrupar;

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
	private Doublebox dbxCop_canc;
	@View
	private Checkbox chkAplica_descuento;
	@View
	private Intbox intboxPorcentaje;

	private Facturacion_servicio facturacion_servicio;

	private IOnCambio onCambio;

	private PacienteService pacienteService;
	private FacturacionService facturacionService;
	private Copago_estratoService copago_estratoService;
	private Configuracion_admision_ingresoService configuracion_admision_ingresoService;
	private Datos_consultaService datos_consultaService;
	private Datos_procedimientoService datos_procedimientoService;
	private ProcedimientosService procedimientosService;
	private Datos_servicioService datos_servicioService;
	private Admision_camaService admision_camaService;
	private Datos_medicamentosService datos_medicamentosService;
	private Facturacion_servicioService facturacion_servicioService;
	private Facturacion_medicamentoService facturacion_medicamentoService;
	private ContratosService contratosService;
	private Via_ingreso_contratadasService via_ingreso_contratadasService;
	private ElementoService elementoService;
	private ArticuloService articuloService;

	public enum ETIPO_SERVICIO {
		SERVICIO, ARTICULO;

		public static ETIPO_SERVICIO getTipo(Detalle_factura detalle_factura) {
			if (detalle_factura.getTipo().equals(ITiposServicio.MEDICAMENTO)
					|| detalle_factura.getTipo().equals(
							ITiposServicio.MATERIALES_INSUMOS)
					|| detalle_factura.getTipo()
							.equals(ITiposServicio.SERVICIO)) {
				return ARTICULO;
			} else {
				return SERVICIO;
			}
		}
	}

	/**
	 * Diagnostico, fecha de los servicios, centralizado
	 * 
	 * @author Luis Miguel
	 * */
	public class CargaRapidaInformacionUltimoRegistro {
		private String diagnostico_principal;
		private String diagnostico_relacionado;
		private Timestamp fecha;
		private String nro_autorizacion;

		public String getDiagnostico_principal() {
			return diagnostico_principal;
		}

		public void setDiagnostico_principal(String diagnostico_principal) {
			this.diagnostico_principal = diagnostico_principal;
		}

		public String getDiagnostico_relacionado() {
			return diagnostico_relacionado;
		}

		public void setDiagnostico_relacionado(String diagnostico_relacionado) {
			this.diagnostico_relacionado = diagnostico_relacionado;
		}

		public Timestamp getFecha() {
			return fecha;
		}

		public void setFecha(Timestamp fecha) {
			this.fecha = fecha;
		}

		public String getNro_autorizacion() {
			return nro_autorizacion;
		}

		public void setNro_autorizacion(String nro_autorizacion) {
			this.nro_autorizacion = nro_autorizacion;
		}
	}

	public interface IOnCambio {
		void OnCambioContrato(Contratos contratos, boolean agregar);
	}

	@Override
	public void afterCompose() {
		try {
			CargardorDeDatos.initComponents(this);
			informacionUltimoRegistro = new CargaRapidaInformacionUltimoRegistro();
			inicializamosListas();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}

	}

	private void inicializamosListas() {
		lista_servicios = new ArrayList<Map<String, Object>>();
		lista_servicios_validados = new ArrayList<Map<String, Object>>();
		lista_datos_servicios = new ArrayList<Datos_servicio>();
		listado_servicios_eliminado = new ArrayList<Detalle_factura>();
	}

	public void limpiar(boolean todo) {
		lista_servicios.clear();
		lista_servicios_validados.clear();
		listado_servicios_eliminado.clear();
		getItems().clear();
		chbNocopago.setChecked(true);
		chbAgrupar.setChecked(true);
		chbMediana.setChecked(false);
		habilitarAcciones(false);
		calcularTotal();
		informacionUltimoRegistro = new CargaRapidaInformacionUltimoRegistro();
		facturacion_servicio = null;
		if (todo) {
			elemento_via_ingreso_principal = null;
			contratos = null;
			setConfiguracion_admision_ingreso(null);
		}

	}

	public void habilitarServicios(boolean habilitar) {
		for (Listitem listitem : getItems()) {
			BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
					.getAttribute(BAND_BOX_SERVICIO);
			bandBoxRowMacro.setButtonVisible(habilitar);
			bandBoxRowMacro.setReadonly(!habilitar);
			bandBoxRowMacro.setInplace(!habilitar);
		}
	}

	public void habilitarAcciones(boolean estado) {
		tbbAgregarServicios.setVisible(estado);
		tbbAgregarArticulos.setVisible(estado);
		tbbQuitar.setVisible(estado);
		// tbbComplementacionMultiple.setVisible(estado);
	}

	// Metodo para obtener el total facturado //
	public void cargarTotalFactura(Facturacion facturacion) {
		dbxValor_cuota.setValue(facturacion.getValor_cuota());
		dbxSubtotal.setValue(facturacion.getValor_total());
		dbxDto_factura.setValue(facturacion.getDto_factura());

		dbxValor_copago.setValue(facturacion.getValor_copago());
		dbxValor_total.setValue(facturacion.getValor_total()
				- facturacion.getDto_factura() - facturacion.getValor_copago());
		dbxDto_copago.setValue(facturacion.getDto_copago());

		chbNocopago.setChecked((facturacion.getNocopago().equals("S") ? true
				: false));
		dbxCop_canc.setValue(facturacion.getCop_canc());
		calcularTotal();
	}

	public void calcularTotal() {
		try {
			double subtotal = 0;
			for (int i = 0; i < lista_servicios.size(); i++) {
				Map<String, Object> map = lista_servicios.get(i);
				Detalle_factura detalle_factura = (Detalle_factura) map
						.get(DETALLE_FACTURA);
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
			double valor_copago = dbxValor_copago.getValue() != null ? dbxValor_copago
					.getValue() : 0.0;
			if (valor_copago < 0) {
				throw new Exception("Valor de copago no es valido");
			}
			dbxValor_total.setValue(dbxSubtotal.getValue() - valor_copago);

			if (valor_copago > 0) {
				chbNocopago.setChecked(false);
			} else {
				chbNocopago.setChecked(true);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void calcularCopago(boolean checked) {
		try {
			if (checked) {
				dbxValor_copago.setValue(0.0);
				dbxValor_total.setValue(dbxSubtotal.getValue());
				dbxCop_canc.setValue(0.0);
				dbxDto_copago.setValue(0.0);
			} else {
				if (getPaciente() == null) {
					throw new Exception("Debe seleccionar un paciente");
				}

				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(getPaciente().getCodigo_empresa());
				paciente.setCodigo_sucursal(getPaciente().getCodigo_sucursal());
				paciente.setNro_identificacion(getPaciente()
						.getNro_identificacion());
				paciente = pacienteService.consultar(paciente);
				String estrato = (paciente != null ? paciente.getEstrato() : "");
				String regimen = (paciente != null ? paciente.getTipo_usuario()
						: "2");

				Facturacion fac = new Facturacion();
				fac.setCodigo_empresa(getPaciente().getCodigo_empresa());
				fac.setCodigo_sucursal(getPaciente().getCodigo_sucursal());
				fac.setNro_ingreso(getPaciente().getNro_identificacion());
				fac.setCodigo_tercero(getPaciente().getNro_identificacion());
				fac = facturacionService.consultar_informacion(fac);

				Copago_estrato ce = new Copago_estrato();
				ce.setCodigo_empresa(getPaciente().getCodigo_empresa());
				ce.setCodigo_sucursal(getPaciente().getCodigo_sucursal());
				ce.setEstrato(estrato);
				ce = copago_estratoService.consultar(ce);
				if (ce == null) {
					ce = new Copago_estrato();
				}
				double valor_copago = (int) (ce.getPorcentaje() * dbxSubtotal
						.getValue());
				if (ce.getValor_max_contrib() < valor_copago
						&& regimen.equals("1") && ce.getValor_max_contrib() > 0) {
					valor_copago = ce.getValor_max_contrib();
				} else if (ce.getValor_max_sub() < valor_copago
						&& regimen.equals("2") && ce.getValor_max_sub() > 0) {
					valor_copago = ce.getValor_max_sub();
				}

				dbxValor_copago.setValue(valor_copago);
				dbxValor_total.setValue(dbxSubtotal.getValue() - valor_copago);
				if (fac != null) {
					dbxCop_canc.setValue(fac.getCop_canc());
					dbxDto_copago.setValue(fac.getDto_copago());
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void setIncluirPaquetes(boolean incluir) {
		chkIncluir_paquetes.setChecked(incluir);
	}

	public boolean isIncluirPaquetes() {
		return chkIncluir_paquetes.isChecked();
	}

	public void setAgrupar(boolean incluir) {
		chbAgrupar.setChecked(incluir);
	}

	public boolean isAgrupar() {
		return chbAgrupar.isChecked();
	}

	public boolean isMediana() {
		return chbMediana.isChecked();
	}

	public void setNoCopago(boolean copago) {
		chbNocopago.setChecked(copago);
	}

	public boolean isNocopago() {
		return chbNocopago.isChecked();
	}

	/**
	 * Este metodo me permite obtener los servicios en tipo detalle factura
	 * */
	public List<Detalle_factura> getServicios() {
		List<Detalle_factura> detalle_facturas = new ArrayList<Detalle_factura>();
		for (Map<String, Object> servicio : lista_servicios) {
			Detalle_factura detalle_factura = (Detalle_factura) servicio
					.get(DETALLE_FACTURA);
			detalle_facturas.add(detalle_factura);
		}
		return detalle_facturas;
	}
	
	/**
	 * Este metodo me permite obtener los servicios en tipo detalle factura
	 * */
	public List<Map<String, Object>> getLista_servicios() {
		return lista_servicios;
	}

	/**
	 * Este metodo me permite obtener los servicios en tipo detalle factura
	 * */
	public Map<String, Object> getServiciosCompletos() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MAP_SERVICIOS, lista_servicios_validados);
		map.put(MAP_SERVICIOS_ELIMINAR, listado_servicios_eliminado);
		map.put(MAP_FACT_SERVICIOS,
				getFacturacionMedicamentosMateriales(ITiposServicio.SERVICIO));
		return map;
	}

	private Map<String, Object> getFacturacionMedicamentosMateriales(String tipo) {
		if (tipo == ITiposServicio.SERVICIO) {
			if (facturacion_servicio != null) {
				return getMapFacturacionServicio();
			}
		}
		return null;
	}

	private Map<String, Object> getMapFacturacionServicio() {
		Map<String, Object> map_servicios = new HashMap<String, Object>();
		map_servicios.put("facturacion_servicio", facturacion_servicio);
		map_servicios.put("lista_detalle", lista_datos_servicios);
		map_servicios
				.put("accion",
						facturacion_servicio.getNro_factura() == null
								|| facturacion_servicio.getNro_factura()
										.isEmpty() ? "registrar" : "modificar");
		return map_servicios;
	}

	public Map<String, Object> validarForm() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean valida = true;
		boolean confirmacion = false;
		List<String> list_servicios = new ArrayList<String>();
		String mensaje = "Los campos marcados con (*) son obligatorios";
		if (lista_servicios.isEmpty()) {
			mensaje = "Debe crear al menos un registro de servicios";
			valida = false;
		} else {
			// validarGeneracionComplementos();
			int i = 1;
			Map<String, Integer> map_servicios_repetidos = new HashMap<String, Integer>();
			lista_servicios_validados.clear();
			lista_datos_servicios.clear();
			for (Map<String, Object> map_servicios : lista_servicios) {
				Detalle_factura detalle_factura = (Detalle_factura) map_servicios
						.get(DETALLE_FACTURA);
				Object servicio_rips = map_servicios
						.get(ITiposServicio.PARAM_RIPS);

				if (servicio_rips == null) {
					mensaje = "Para realizar esta accion es obligatorio, complementar el detalle en la fila "
							+ i;
					valida = false;
					break;
				} else {
					if (map_servicios
							.containsKey(ITiposServicio.APLICA_ESQUEMA_VACUNACION)) {
						Esquema_vacunacion esquema_vacunacion = (Esquema_vacunacion) map_servicios
								.get(ITiposServicio.PARAM_ESQUEMA_VACUNACION);
						if (esquema_vacunacion == null) {
							mensaje = "Para realizar esta accion es obligatorio, agregar la dosis en la fila "
									+ i;
							valida = false;
							break;
						}
					}

					String key = detalle_factura.getCodigo_articulo();
					if (detalle_factura.getTipo().equals(
							ITiposServicio.PROCEDIMIENTO_MULT)) {
						key = detalle_factura.getCodigo_articulo()
								+ detalle_factura.getDetalle();
					}
					if (map_servicios_repetidos.containsKey(key)) {
						if (!existe(list_servicios, detalle_factura)) {
							list_servicios.add(detalle_factura.getDetalle());
						}
						confirmacion = true;
						mensaje = "Ahi procedimientos repetidos: ";
					} else {
						map_servicios_repetidos.put(key, i);
					}

					if (detalle_factura.getTipo().equals(
							ITiposServicio.SERVICIO)) {
						String numero_autorizacion = (String) map_servicios
								.get(NRO_AUTORIZACION);
						((Datos_servicio) servicio_rips)
								.setNumero_autorizacion(numero_autorizacion);
						lista_datos_servicios
								.add((Datos_servicio) servicio_rips);
					} else {
						lista_servicios_validados.add(map_servicios);
					}
					try {
						alimentar(map_servicios, detalle_factura, i);
					} catch (ValidacionRunTimeException v) {
						mensaje = v.getMessage();
						valida = false;
					} catch (Exception e) {
						MensajesUtil.mensajeError(e, "", generalComposer);
						mensaje = e.getMessage();
						valida = false;
					}
				}
				i++;
			}

			if (lista_datos_servicios.isEmpty()) {
				facturacion_servicio = null;
			}
		}

		double valor_cuota = dbxValor_cuota.getValue() != null ? dbxValor_cuota
				.getValue() : 0.0;

		if (valida && valor_cuota < 0) {
			dbxValor_cuota.setStyle("background-color:#F6BBBE");
			mensaje = "El valor de la cuota no puede ser menor a cero";
			valida = false;
		}
		double valor_copago = dbxValor_copago.getValue() != null ? dbxValor_copago
				.getValue() : 0.0;
		if (valida && valor_copago < 0) {
			dbxValor_copago.setStyle("background-color:#F6BBBE");
			mensaje = "El valor del copago no puede ser menor a cero";
			valida = false;
		}

		if (valida && dbxDto_copago.getValue() < 0) {
			dbxDto_copago.setStyle("background-color:#F6BBBE");
			mensaje = "El valor del descuento del copago no puede ser menor a cero";
			valida = false;
		}

		if (valida && valor_copago > dbxSubtotal.getValue()) {
			dbxValor_copago.setStyle("background-color:#F6BBBE");
			mensaje = "El valor del copago no puede ser mayor que el de la factura";
			valida = false;
		}

		if (valida && dbxDto_copago.getValue() > valor_copago) {
			dbxValor_copago.setStyle("background-color:#F6BBBE");
			mensaje = "El valor del descuento del copago no puede ser mayor que el valor del copago";
			valida = false;
		}

		map.put("valida", valida);
		map.put("mensaje", mensaje);
		map.put("confirmacion", confirmacion);
		map.put("list_servicios", list_servicios);

		return map;
	}

	private boolean existe(List<String> list_servicios,
			Detalle_factura detalle_factura) {
		for (String detalle : list_servicios) {
			if (detalle.equals(detalle_factura.getDetalle())) {
				return true;
			}
		}
		return false;
	}

	private void alimentar(Map<String, Object> mapa_servicios,
			Detalle_factura detalle_factura, int fila) {
		Toolbarbutton toolbarbutton_complememto = (Toolbarbutton) mapa_servicios
				.get(ServiciosFacturacionMacro.TOOLBAR_BUTTON_COMPLEMENTO);
		Datebox datebox_realizacion = (Datebox) toolbarbutton_complememto
				.getAttribute(ServiciosFacturacionMacro.DATE_BOX_REALIZACION);
		Object servicio_rips = mapa_servicios.get(ITiposServicio.PARAM_RIPS);

		if (detalle_factura.getCantidad() <= 0) {
			throw new ValidacionRunTimeException(
					"Para realizar esta accion debe ingresar la cantidad en la fila "
							+ fila);
		}

		// Actualizamos valores cambiantes
		if (servicio_rips instanceof Datos_consulta) {
			Datos_consulta consulta = (Datos_consulta) servicio_rips;
			consulta.setFecha_consulta(datebox_realizacion.getValue() != null ? new Timestamp(
					datebox_realizacion.getValue().getTime()) : null);
			consulta.setCodigo_prestador(prestadores.getNro_identificacion());

			if (consulta.getCodigo_diagnostico_principal() == null
					|| consulta.getCodigo_diagnostico_principal().trim()
							.isEmpty()) {
				throw new ValidacionRunTimeException(
						"El diagnostico es obligatorio para la consulta en la fila "
								+ fila);
			}

			if (consulta.getFecha_consulta() == null) {
				if (informacionUltimoRegistro.getFecha() != null) {
					consulta.setFecha_consulta(informacionUltimoRegistro
							.getFecha());
				}
			}

			if (consulta.getFecha_consulta() == null) {
				throw new ValidacionRunTimeException(
						"La fecha es obligatorio para la consulta en la fila "
								+ fila);
			} else if (!L2HContraintDate.estaDentroRangoYYMMDD(
					consulta.getFecha_consulta(), getFecha_ingreso(),
					getFecha_egreso())) {
				throw new ValidacionRunTimeException(
						"La fecha para la consulta en la fila "
								+ fila
								+ " no puede estar por fuera del rango inicial y final de la factura "
								+ ZKWindow.formatFecha
										.format(getFecha_ingreso())
								+ " hasta "
								+ ZKWindow.formatFecha
										.format(getFecha_egreso()));
			}

		} else if (servicio_rips instanceof Datos_procedimiento) {
			Datos_procedimiento datos_procedimiento = (Datos_procedimiento) servicio_rips;
			datos_procedimiento.setFecha_procedimiento(datebox_realizacion
					.getValue() != null ? new Timestamp(datebox_realizacion
					.getValue().getTime()) : null);
			datos_procedimiento.setCodigo_prestador(prestadores
					.getNro_identificacion());

			Procedimientos procedimientos = (Procedimientos) mapa_servicios
					.get(REGISTRO_SELECCIONADO);

			if (datos_procedimiento.getFecha_procedimiento() == null) {
				if (informacionUltimoRegistro.getFecha() != null) {
					datos_procedimiento
							.setFecha_procedimiento(informacionUltimoRegistro
									.getFecha());
				}
			}

			if (datos_procedimiento.getFecha_procedimiento() == null) {
				throw new ValidacionRunTimeException(
						"La fecha es obligatorio para la procedimiento en la fila "
								+ fila);
			} else if (!L2HContraintDate.estaDentroRangoYYMMDD(
					datos_procedimiento.getFecha_procedimiento(),
					getFecha_ingreso(), getFecha_egreso())) {
				throw new ValidacionRunTimeException(
						"La fecha para la procedimiento en la fila "
								+ fila
								+ " no puede estar por fuera del rango inicial y final de la factura "
								+ ZKWindow.formatFecha
										.format(getFecha_ingreso())
								+ " hasta "
								+ ZKWindow.formatFecha
										.format(getFecha_egreso()));
			}

			if ((datos_procedimiento.getCodigo_diagnostico_principal() == null || datos_procedimiento
					.getCodigo_diagnostico_principal().trim().isEmpty())
					&& procedimientos.getQuirurgico().equals("S")) {
				throw new ValidacionRunTimeException(
						"El diagnostico es obligatorio para el procedimiento en la fila "
								+ fila);
			}

			if ((datos_procedimiento.getAmbito_procedimiento() == null || datos_procedimiento
					.getAmbito_procedimiento().trim().isEmpty())) {
				throw new ValidacionRunTimeException(
						"El ambito del procedimiento es obligatorio para el procedimiento en la fila "
								+ fila);
			}

			if ((datos_procedimiento.getFinalidad_procedimiento() == null || datos_procedimiento
					.getFinalidad_procedimiento().trim().isEmpty())) {
				throw new ValidacionRunTimeException(
						"La finalidad del procedimiento es obligatorio para el procedimiento en la fila "
								+ fila);
			}

			if ((datos_procedimiento.getPersonal_atiende() == null || datos_procedimiento
					.getPersonal_atiende().trim().isEmpty())
					&& procedimientos.getQuirurgico().equals("S")) {
				throw new ValidacionRunTimeException(
						"El personal atiende es obligatorio para el procedimiento en la fila "
								+ fila);
			}

			if ((datos_procedimiento.getForma_realizacion() == null || datos_procedimiento
					.getForma_realizacion().trim().isEmpty())
					&& procedimientos.getQuirurgico().equals("S")) {
				throw new ValidacionRunTimeException(
						"La forma realizacion diagnostico es obligatorio para el procedimiento en la fila "
								+ fila);
			}
		} else if (servicio_rips instanceof Datos_medicamentos
				|| servicio_rips instanceof Datos_servicio) {
			mapa_servicios.put(FECHA_REALIZACION, datebox_realizacion
					.getValue() != null ? new Timestamp(datebox_realizacion
					.getValue().getTime()) : null);
			Date fecha = (Date) mapa_servicios.get(FECHA_REALIZACION);
			if (fecha == null) {
				throw new ValidacionRunTimeException(
						"La fecha es obligatorio para la fila " + fila);
			} else if (!L2HContraintDate.estaDentroRangoYYMMDD(fecha,
					getFecha_ingreso(), getFecha_egreso())) {
				throw new ValidacionRunTimeException(
						"La fecha para la fila "
								+ fila
								+ " no puede estar por fuera del rango inicial y final de la factura "
								+ ZKWindow.formatFecha
										.format(getFecha_ingreso())
								+ " hasta "
								+ ZKWindow.formatFecha
										.format(getFecha_egreso()));
			}
		}
	}

	/**
	 * Este metodo me permite agregar servicios dependiendo el tipo ya sean
	 * servicios (Procedimientos) o articulos (medicamentos, materiales/insumos)
	 * 
	 * @author l2
	 * */
	public void agregarServicio(ETIPO_SERVICIO servicio, boolean mover_scroll) {
		// Verificamos si el paciente tiene unidades funcionales activas
		if (getElemento_via_ingreso_principal() == null) {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"Para agregar servicios debe seleccionar una via de ingreso");
		} else {
			if (getElemento_via_ingreso_principal().getCodigo().isEmpty()) {
				MensajesUtil
						.mensajeAlerta("Advertencia",
								"Para agregar servicios debe seleccionar una via de ingreso");
			} else {

				if (this.configuracion_admision_ingreso == null) {
					Configuracion_admision_ingreso configuracion_admision_ingreso_aux = new Configuracion_admision_ingreso();
					configuracion_admision_ingreso_aux
							.setCodigo_empresa(generalComposer.codigo_empresa);
					configuracion_admision_ingreso_aux
							.setCodigo_sucursal(generalComposer.codigo_sucursal);
					configuracion_admision_ingreso_aux
							.setVia_ingreso(getElemento_via_ingreso_principal()
									.getCodigo());
					configuracion_admision_ingreso_aux = configuracion_admision_ingresoService
							.consultar(configuracion_admision_ingreso_aux);
					setConfiguracion_admision_ingreso(configuracion_admision_ingreso_aux);
				}

				if (this.configuracion_admision_ingreso == null) {
					MensajesUtil
							.mensajeAlerta(
									"Advertencia",
									"Para agregar servicios la via ingreso seleccionada debe tener una configuracion establecida");
				} else {
					// Creamos fila
					Listitem listitem = crearServicio(
							getNuevaDetalle_factura(), servicio, true);
					// Agregamos la fila al componente
					appendChild(listitem);
					// agregamos el foco al list item
					// ((Listbox)listitem.getAttribute(LISTBOX_UNIDAD_FUNCIONAL)).setFocus(true);
					((BandBoxRowMacro) listitem.getAttribute(BAND_BOX_SERVICIO))
							.setFocus(true);

					if (mover_scroll) {// movemos el scroll hasta el componente
										// indicado
						Clients.scrollIntoView(chbNocopago);
					}
				}
			}
		}
	}

	public void consultarServiciosFacturas(boolean sw) {
		if (generalComposer instanceof Facturacion_ripsAction) {
			((Facturacion_ripsAction) generalComposer)
					.consultarServiciosFacturas(sw);
		}
	}

	// Este metodo es para quitar un servcio realizado a la admision
	// este modulo borra los Rips de CONSULTAS, PROCEDIMIENTO, PROCEDIMIENTOS
	// QUIRURGICOS, MEDICAMENTOS,MATERIALES E INSUMOS Y SERVICIOS //
	public void quitarServicio() throws Exception {
		try {
			final Set<Listitem> listitems = getSelectedItems();
			if (listitems.isEmpty()) {
				MensajesUtil.mensajeAlerta("Advertencia",
						"Debe seleccionar un registro para eliminar");
				return;
			}
			Messagebox.show("Esta seguro que deseas eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								List<Listitem> lista_items = getItems();
								for (int i = 0; i < lista_items.size(); i++) {
									Listitem listitem = lista_items.get(i);
									if (listitem.isSelected()) {
										Map<String, Object> map_servicio = listitem
												.getValue();
										Detalle_factura detalle_factura = (Detalle_factura) map_servicio
												.get(DETALLE_FACTURA);
										// colocamos en cola de eliminacion
										if (detalle_factura.getTipo() != null) {
											listado_servicios_eliminado
													.add(detalle_factura);
										}
										// eliminamos de la lista y de la vista
										lista_servicios.remove(map_servicio);
									}
								}
								getItems().removeAll(listitems);
								calcularTotal();
							}
						}
					});

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/**
	 * Este metodo me permite agregar los servicios externos
	 * */
	public void crearServicio(final Detalle_factura detalle_factura,
			ETIPO_SERVICIO servicio) {
		appendChild(crearServicio(detalle_factura, servicio, false));
	}

	private Detalle_factura getNuevaDetalle_factura() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Detalle_factura detalle_factura = new Detalle_factura(true);
		detalle_factura.setUltimo_update(timestamp);
		detalle_factura.setUltimo_user(generalComposer.getUsuarios()
				.getCodigo());
		detalle_factura.setCreacion_date(timestamp);
		detalle_factura.setCreacion_user(generalComposer.getUsuarios()
				.getCodigo());
		detalle_factura.setCodigo_empresa(generalComposer.getSucursal()
				.getCodigo_empresa());
		detalle_factura.setCodigo_sucursal(generalComposer.getSucursal()
				.getCodigo_sucursal());
		detalle_factura.setFactura_concepto("");
		return detalle_factura;
	}

	/**
	 * 
	 * */

	private Listitem crearServicio(final Detalle_factura detalle_factura,
			ETIPO_SERVICIO servicio, boolean nuevo) {
		Listitem listitem = new Listitem();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DETALLE_FACTURA, detalle_factura);
		map.put(DETALLE_NUEVO, nuevo);

		listitem.setValue(map);

		if (!nuevo) {// alimentamos mapa
			alimentarMapa(map);
		} else if (getItems().isEmpty()) {
			// Esto para que cuando se incialice con un nuevo servicio
			// tome la fecha de la factura
			informacionUltimoRegistro
					.setFecha(getFecha_ingreso() != null ? new Timestamp(
							getFecha_ingreso().getTime()) : null);
			informacionUltimoRegistro.setNro_autorizacion(getNroAutorizacion());
		}

		lista_servicios.add(map);

		// String codigo_articulo = detalle_factura.getCodigo_articulo();
		String nombre_articulo = detalle_factura.getDetalle();
		double unidades = detalle_factura.getCantidad();
		double valor_unitario = detalle_factura.getValor_unitario();
		double valor_total = detalle_factura.getValor_total();
		String tipo = detalle_factura.getTipo();

		// Para seleccion
		listitem.appendChild(new Listcell());

		// unidad funcional
		// Listbox listbox = getListBoxUnidadFuncional();
		Listcell cell = new Listcell();
		// cell.appendChild(listbox);
		listitem.appendChild(cell);
		// listitem.setAttribute(LISTBOX_UNIDAD_FUNCIONAL, listbox);

		// para el servicio
		BandBoxRowMacro bandBoxRowMacro = getBandboxMacroServicio(servicio,
				detalle_factura);
		bandBoxRowMacro.setReadonly(!nuevo);
		bandBoxRowMacro.setButtonVisible(nuevo);
		bandBoxRowMacro.setInplace(!nuevo);
		cell = new Listcell();
		cell.appendChild(bandBoxRowMacro);
		// bandBoxRowMacro.setAttribute(LISTBOX_UNIDAD_FUNCIONAL, listbox);
		bandBoxRowMacro.setAttribute(MAP_DETALLE_FACTURA, map);
		listitem.setAttribute(BAND_BOX_SERVICIO, bandBoxRowMacro);
		listitem.appendChild(cell);

		// Columna nombre //
		Label labelNombre = new Label(nombre_articulo);
		bandBoxRowMacro.setAttribute(LABEL_NOMBRE_SERVICIO, labelNombre);
		cell = new Listcell();
		labelNombre.setStyle("font-size:9px");
		cell.appendChild(labelNombre);
		listitem.appendChild(cell);

		// Columna fecha
		cell = new Listcell();
		Datebox datebox_realizacion = new Datebox();
		bandBoxRowMacro.setAttribute(DATE_BOX_REALIZACION, datebox_realizacion);
		datebox_realizacion.setAttribute(MAP_DETALLE_FACTURA, map);
		datebox_realizacion.setAttribute(TIPO_SERVICIO, servicio);
		datebox_realizacion.setAttribute(_LISTITEM, listitem);
		datebox_realizacion.setReadonly(false);
		datebox_realizacion.setHflex("1");
		datebox_realizacion.setStyle("font-size:10px");
		if (nuevo) {
			datebox_realizacion.setValue(informacionUltimoRegistro.getFecha());
		} else {
			Timestamp fecha_realizacion = (Timestamp) map
					.get(FECHA_REALIZACION);
			datebox_realizacion
					.setValue(fecha_realizacion != null ? fecha_realizacion
							: informacionUltimoRegistro.getFecha());
		}
		cell.appendChild(datebox_realizacion);
		listitem.appendChild(cell);

		// Columna cantidad //
		cell = new Listcell();
		Doublebox doublebox_unidades = getDoublebox(unidades > 0 ? unidades
				: null);
		bandBoxRowMacro.setAttribute(DOUBLE_BOX_UNIDADES, doublebox_unidades);
		doublebox_unidades.setAttribute(MAP_DETALLE_FACTURA, map);
		doublebox_unidades.setAttribute(TIPO_SERVICIO, servicio);
		doublebox_unidades.setAttribute(_LISTITEM, listitem);
		doublebox_unidades.setReadonly(false);
		// doublebox_unidades.setMaxlength(1);
		doublebox_unidades.setAttribute(ATTRI_NUEVO, nuevo);
		cargarEventoUnidades(doublebox_unidades); // Cargamos eventos para que
													// actuliza el valor total
		cell.appendChild(doublebox_unidades);
		listitem.appendChild(cell);

		// Columna valor unitario //
		cell = new Listcell();
		Doublebox doublebox_unitario = getDoublebox(valor_unitario > 0 ? valor_unitario
				: null);
		doublebox_unitario.setFormat("#,##0");
		doublebox_unidades.setAttribute(DOUBLE_BOX_VALOR_UNITARIO,
				doublebox_unitario);
		bandBoxRowMacro.setAttribute(DOUBLE_BOX_VALOR_UNITARIO,
				doublebox_unitario);
		cell.appendChild(doublebox_unitario);
		listitem.appendChild(cell);

		// Columna valor total //
		cell = new Listcell();
		Doublebox doublebox_total = getDoublebox(valor_total > 0 ? valor_total
				: null);

		doublebox_total.setFormat("#,##0");
		doublebox_unidades
				.setAttribute(DOUBLE_BOX_VALOR_TOTAL, doublebox_total);
		bandBoxRowMacro.setAttribute(DOUBLE_BOX_VALOR_TOTAL, doublebox_total);
		cell.appendChild(doublebox_total);
		listitem.appendChild(cell);

		// Columna tipo //
		if (tipo != null && tipo.equals(ITiposServicio.PROCEDIMIENTO_MULT)) {
			tipo = ITiposServicio.QUIRURGICO;
		}

		Listcell celda_tipo = new Listcell();
		bandBoxRowMacro.setAttribute(CELDA_TIPO_SERVICIO, celda_tipo);
		listitem.appendChild(celda_tipo);

		if (generalComposer != null
				&& generalComposer.getParametros_empresa()
						.getHabilitar_editar_fac_particular()
				&& administradora_particular) {
			doublebox_unitario.setReadonly(false);
			doublebox_unitario.setAttribute(DOUBLE_BOX_UNIDADES,
					doublebox_unidades);
			doublebox_unitario.setAttribute(DOUBLE_BOX_VALOR_TOTAL,
					doublebox_total);
			doublebox_unitario.setAttribute(MAP_DETALLE_FACTURA, map);
			doublebox_unitario.setAttribute(TIPO_SERVICIO, servicio);
			doublebox_unitario.setAttribute(_LISTITEM, listitem);
			cargarEventoValorUnitario(doublebox_unitario);
		}

		Listcell listcell = new Listcell();
		listitem.appendChild(listcell);

		final Toolbarbutton toolbarbutton_seleccion = new Toolbarbutton();
		bandBoxRowMacro.setAttribute(TOOLBAR_BUTON_SELEC,
				toolbarbutton_seleccion);
		bandBoxRowMacro.setAttribute(LISTCELL_SELEC, listcell);

		if (servicio == ETIPO_SERVICIO.ARTICULO) {
			aplicarFacturable(detalle_factura.getFacturable(),
					toolbarbutton_seleccion);
			listcell.setStyle("font-size:9px;");
			toolbarbutton_seleccion.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (!detalle_factura.getFacturable()) {
								toolbarbutton_seleccion
										.setImage("/images/si_seleccionado.png");
								detalle_factura.setFacturable(true);
								toolbarbutton_seleccion
										.setTooltiptext("Haga click para marcar este detalle como NO facturable");
							} else {
								toolbarbutton_seleccion
										.setImage("/images/no_seleccionado.png");
								detalle_factura.setFacturable(false);
								toolbarbutton_seleccion
										.setTooltiptext("Haga click para marcar este detalle como facturable");
							}
							calcularTotal();
						}
					});
		} else {
			listcell.setValue(true);
			listcell.setStyle("font-size:9px;cursor:pointer;background-color:white;border-style:solid;border-width:1px;");
			listcell.setTooltiptext("Este detalle de factura obligatoriamente es facturable");
			toolbarbutton_seleccion.setImage("/images/si_seleccionado.png");
		}
		listcell.appendChild(toolbarbutton_seleccion);

		// Complementacion de datos
		listcell = new Listcell();
		listitem.appendChild(listcell);
		Toolbarbutton toolbarbutton_complementacion = new Toolbarbutton();

		// Este es cuando la vista de medicamento no tenga complemento
		// toolbarbutton_complementacion.setVisible(!nuevo
		// && servicio == ETIPO_SERVICIO.SERVICIO);

		toolbarbutton_complementacion.setVisible(!nuevo);
		toolbarbutton_complementacion.setImage("/images/multiple.png");
		listcell.appendChild(toolbarbutton_complementacion);

		// Agregamos referencias
		doublebox_unidades.setAttribute(TOOLBAR_BUTON_COMPL,
				toolbarbutton_complementacion);
		bandBoxRowMacro.setAttribute(TOOLBAR_BUTON_COMPL,
				toolbarbutton_complementacion);
		toolbarbutton_complementacion.setAttribute(BAND_BOX_SERVICIO,
				bandBoxRowMacro);
		toolbarbutton_complementacion.setAttribute(CELDA_TIPO_SERVICIO,
				celda_tipo);
		toolbarbutton_complementacion.setAttribute(DATE_BOX_REALIZACION,
				datebox_realizacion);

		// Cargamos evento de complementacion
		map.put(TOOLBAR_BUTTON_COMPLEMENTO, toolbarbutton_complementacion);
		cargarEventoComplementacion(toolbarbutton_complementacion);
		ajustarTipos(celda_tipo, tipo);

		return listitem;
	}

	/**
	 * Este metodo actualiza el mapa de los detalles de cada uno de los
	 * servicios
	 * */
	private void alimentarMapa(Map<String, Object> map) {
		Detalle_factura detalle_factura = (Detalle_factura) map
				.get(DETALLE_FACTURA);
		if (detalle_factura.getTipo().equals(ITiposServicio.CONSULTA)) {
			Datos_consulta datos_consulta = new Datos_consulta(false);
			datos_consulta.setCodigo_empresa(detalle_factura
					.getCodigo_empresa());
			datos_consulta.setCodigo_sucursal(detalle_factura
					.getCodigo_sucursal());
			datos_consulta.setCodigo_registro(Utilidades
					.getValorLong(detalle_factura.getFactura_concepto()));
			datos_consulta = datos_consultaService.consultar(datos_consulta);
			// Actualizamos diagnostico
			if (datos_consulta != null) {
				informacionUltimoRegistro
						.setDiagnostico_principal(datos_consulta
								.getCodigo_diagnostico_principal());
				informacionUltimoRegistro
						.setDiagnostico_relacionado(datos_consulta
								.getCodigo_diagnostico1());
				informacionUltimoRegistro.setFecha(datos_consulta
						.getFecha_consulta());
				informacionUltimoRegistro.setNro_autorizacion(datos_consulta
						.getNumero_autorizacion());
				map.put(FECHA_REALIZACION, datos_consulta.getFecha_consulta());
			}
			map.put(ITiposServicio.PARAM_RIPS, datos_consulta);
		} else if (detalle_factura.getTipo().equals(
				ITiposServicio.PROCEDIMIENTO)
				|| detalle_factura.getTipo().equals(
						ITiposServicio.PROCEDIMIENTO_MULT)) {
			Datos_procedimiento datos_procedimiento = new Datos_procedimiento(
					false);
			datos_procedimiento.setCodigo_empresa(detalle_factura
					.getCodigo_empresa());
			datos_procedimiento.setCodigo_sucursal(detalle_factura
					.getCodigo_sucursal());
			datos_procedimiento.setCodigo_registro(Utilidades
					.getValorLong(detalle_factura.getFactura_concepto()));

			datos_procedimiento = datos_procedimientoService
					.consultar(datos_procedimiento);
			// Actualizamos diagnostico
			if (datos_procedimiento != null) {
				informacionUltimoRegistro
						.setDiagnostico_principal(datos_procedimiento
								.getCodigo_diagnostico_principal());
				informacionUltimoRegistro
						.setDiagnostico_relacionado(datos_procedimiento
								.getCodigo_diagnostico_relacionado());
				informacionUltimoRegistro.setFecha(datos_procedimiento
						.getFecha_procedimiento());
				informacionUltimoRegistro
						.setNro_autorizacion(datos_procedimiento
								.getNumero_autorizacion());
				Procedimientos procedimientos = new Procedimientos();
				procedimientos.setId_procedimiento(new Long(datos_procedimiento
						.getCodigo_procedimiento()));
				procedimientos = procedimientosService
						.consultar(procedimientos);
				map.put(REGISTRO_SELECCIONADO, procedimientos);
				map.put(FECHA_REALIZACION,
						datos_procedimiento.getFecha_procedimiento());
			}
			map.put(ITiposServicio.PARAM_RIPS, datos_procedimiento);
		} else if (detalle_factura.getTipo().equals(ITiposServicio.SERVICIO)) {
			if (facturacion_servicio == null) {
				facturacion_servicio = getFacturacionServicio(detalle_factura);
			}
			Datos_servicio datos_servicio = new Datos_servicio();
			datos_servicio.setCodigo_empresa(detalle_factura
					.getCodigo_empresa());
			datos_servicio.setCodigo_sucursal(detalle_factura
					.getCodigo_sucursal());
			datos_servicio
					.setNro_factura(detalle_factura.getFactura_concepto());
			datos_servicio.setCodigo_servicio(detalle_factura
					.getCodigo_articulo());

			datos_servicio = datos_servicioService.consultar(datos_servicio);

			if (datos_servicio != null) {
				Timestamp fecha_servicio = facturacion_servicio != null ? facturacion_servicio
						.getFecha_servicio() : null;

				map.put(ITiposServicio.PARAM_RIPS, datos_servicio);
				map.put(NRO_AUTORIZACION,
						datos_servicio.getNumero_autorizacion());
				map.put(FECHA_REALIZACION, fecha_servicio);

				informacionUltimoRegistro.setFecha(fecha_servicio);
				informacionUltimoRegistro.setNro_autorizacion(datos_servicio
						.getNumero_autorizacion());
			}
		} else if (detalle_factura.getTipo().equals(ITiposServicio.ESTANCIA)) {
			Admision_cama admision_cama = new Admision_cama();
			admision_cama
					.setCodigo_empresa(detalle_factura.getCodigo_empresa());
			admision_cama.setCodigo_sucursal(detalle_factura
					.getCodigo_sucursal());
			admision_cama.setNro_identificacion(admision
					.getNro_identificacion());
			admision_cama.setNro_ingreso(admision.getNro_ingreso());
			admision_cama = admision_camaService.consultar(admision_cama);
			// Actualizamos diagnostico
			map.put(ITiposServicio.PARAM_RIPS, admision_cama);
			if (admision_cama != null) {
				map.put(FECHA_REALIZACION, admision_cama.getFecha_ingreso());
			}
		} else if (detalle_factura.getTipo().equals(ITiposServicio.MEDICAMENTO)
				|| detalle_factura.getTipo().equals(
						ITiposServicio.MATERIALES_INSUMOS)) {
			Datos_medicamentos datos_medicamentos = new Datos_medicamentos();
			datos_medicamentos.setCodigo_empresa(detalle_factura
					.getCodigo_empresa());
			datos_medicamentos.setCodigo_sucursal(detalle_factura
					.getCodigo_sucursal());
			datos_medicamentos.setCodigo_medicamento(detalle_factura
					.getCodigo_articulo());
			datos_medicamentos.setNro_factura(detalle_factura
					.getFactura_concepto());

			datos_medicamentos = datos_medicamentosService
					.consultar(datos_medicamentos);
			map.put(ITiposServicio.PARAM_RIPS, datos_medicamentos);

			if (datos_medicamentos != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("codigo_empresa",
						datos_medicamentos.getCodigo_empresa());
				params.put("codigo_sucursal",
						datos_medicamentos.getCodigo_sucursal());
				params.put("nro_factura", datos_medicamentos.getNro_factura());
				params.put("nro_identificacion", getAdmision()
						.getNro_identificacion());
				params.put("nro_ingreso", getAdmision().getNro_ingreso());
				params.put("codigo_servicio",
						datos_medicamentos.getCodigo_medicamento());
				Map<String, Object> resultado = datos_medicamentosService
						.getFechaRealizacion(params);
				if (resultado != null) {
					Timestamp fecha_medicamento = (Timestamp) resultado
							.get("fecha");
					String numero_autorizacion = (String) resultado
							.get("numero_autorizacion");
					// alimentar informacion
					map.put(NRO_AUTORIZACION, numero_autorizacion);

					// número de autorizacion
					informacionUltimoRegistro.setFecha(fecha_medicamento);
					informacionUltimoRegistro
							.setNro_autorizacion(numero_autorizacion);
					map.put(FECHA_REALIZACION, fecha_medicamento);
				}

			}
		} else if (detalle_factura.getTipo().equals(ITiposServicio.SERVICIO)) {
			Datos_servicio datos_servicio = new Datos_servicio();
			datos_servicio.setCodigo_empresa(detalle_factura
					.getCodigo_empresa());
			datos_servicio.setCodigo_sucursal(detalle_factura
					.getCodigo_sucursal());
			datos_servicio.setCodigo_servicio(detalle_factura
					.getCodigo_articulo());
			datos_servicio
					.setNro_factura(detalle_factura.getFactura_concepto());

			datos_servicio = datos_servicioService.consultar(datos_servicio);
			map.put(ITiposServicio.PARAM_RIPS, datos_servicio);

			if (datos_servicio != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("codigo_empresa", datos_servicio.getCodigo_empresa());
				params.put("codigo_sucursal",
						datos_servicio.getCodigo_sucursal());
				params.put("nro_factura", datos_servicio.getNro_factura());
				params.put("nro_identificacion", getAdmision()
						.getNro_identificacion());
				params.put("nro_ingreso", getAdmision().getNro_ingreso());
				params.put("codigo_servicio",
						datos_servicio.getCodigo_servicio());
				Map<String, Object> resultado = datos_servicioService
						.getFechaRealizacion(params);
				if (resultado != null) {
					Timestamp fecha_servicio = (Timestamp) resultado
							.get("fecha");
					String numero_autorizacion = (String) resultado
							.get("numero_autorizacion");
					// alimentar informacion
					map.put(NRO_AUTORIZACION, numero_autorizacion);

					// número de autorizacion
					informacionUltimoRegistro.setFecha(fecha_servicio);
					informacionUltimoRegistro
							.setNro_autorizacion(numero_autorizacion);
					map.put(FECHA_REALIZACION, fecha_servicio);
				}

			}
		}
	}

	private Facturacion_servicio getFacturacionServicio(
			Detalle_factura detalle_factura) {
		Facturacion_servicio facturacion_servicio = new Facturacion_servicio();
		facturacion_servicio.setCodigo_empresa(detalle_factura
				.getCodigo_empresa());
		facturacion_servicio.setCodigo_sucursal(detalle_factura
				.getCodigo_sucursal());
		facturacion_servicio.setNro_factura(detalle_factura
				.getFactura_concepto());
		return facturacion_servicioService.consultar(facturacion_servicio);
	}

	/**
	 * Este metodo es utilizado para cargar una facturacion de medicamento desde
	 * una detalle de factuar
	 * */
	@SuppressWarnings("unused")
	private Facturacion_medicamento getFacturacionMedicamento(
			Detalle_factura detalle_factura) {
		Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
		facturacion_medicamento.setCodigo_empresa(detalle_factura
				.getCodigo_empresa());
		facturacion_medicamento.setCodigo_sucursal(detalle_factura
				.getCodigo_sucursal());
		facturacion_medicamento.setNro_factura(detalle_factura
				.getFactura_concepto());
		return facturacion_medicamentoService
				.consultar(facturacion_medicamento);
	}

	private void aplicarFacturable(boolean facturable,
			Toolbarbutton toolbarbutton) {
		if (facturable) {
			toolbarbutton.setImage("/images/si_seleccionado.png");
			toolbarbutton
					.setTooltiptext("Haga click para marcar este detalle como NO facturable");
		} else {
			toolbarbutton.setImage("/images/no_seleccionado.png");
			toolbarbutton
					.setTooltiptext("Haga click para marcar este detalle como facturable");
		}
	}

	/**
	 * Este metodo es utilizado para agregar al componente que tiene las
	 * cantidades del servicio, para que cuando se realice un cambio actualice
	 * el valor total
	 * */
	private void cargarEventoUnidades(Doublebox doublebox_unidades) {
		// inicializamos el evento sino existe
		if (eventListenerOnBlurCantidad == null) {
			eventListenerOnBlurCantidad = new EventListener<Event>() {
				@Override
				public void onEvent(Event evt) throws Exception {
					// validamos las cantidades
					Doublebox dbxCantidad = (Doublebox) evt.getTarget();
					validarCantidadesMaxima(dbxCantidad);

					// ejecutamos accion de verificacion de cantidades
					ejecutarEventoCantidad(dbxCantidad);
				}
			};
		}
		if (eventListenerOnOkCantidad == null) {
			eventListenerOnOkCantidad = new EventListener<Event>() {
				@Override
				public void onEvent(Event evt) throws Exception {
					ejecutarEventoCantidad((Doublebox) evt.getTarget());
					// Habilitamos la complementacion
					Doublebox doublebox_cantidad = (Doublebox) evt.getTarget();
					Toolbarbutton toolbarbutton = (Toolbarbutton) evt
							.getTarget().getAttribute(TOOLBAR_BUTON_COMPL);

					// La verificacion de disable es para cuando es consulta
					// como no permite cambiar la cantidad y directamente se va
					// para complemento
					// no pida la vista de complemento otraves.
					if (!doublebox_cantidad.isReadonly()
							&& evt.getTarget().getAttribute("compl") == null) {
						ETIPO_SERVICIO servicio = (ETIPO_SERVICIO) evt
								.getTarget().getAttribute(TIPO_SERVICIO);
						if (toolbarbutton.isVisible()
								&& servicio != ETIPO_SERVICIO.ARTICULO) {
							abrirComplementacion(toolbarbutton);
						} else {
							// Esto es cuando no ahi datos de complemento
							// agregar, para agregar servicios
							agregarServicio(evt);
						}
						evt.getTarget().setAttribute("compl", "");
					}
					// en el caso que el servicio tenga una complementacion
					// y se necesite agregar otro servicio
					else if (evt.getTarget().getAttribute("added") == null
							&& toolbarbutton.isVisible()) {
						agregarServicio(evt);
						// Esto es para identificar
						// si ya se hizo una adicion
						evt.getTarget().setAttribute("added", "");
					}
				}
			};
		}
		// Cargamos el evento a componente
		doublebox_unidades.addEventListener(Events.ON_BLUR,
				eventListenerOnBlurCantidad);
		doublebox_unidades.addEventListener(Events.ON_OK,
				eventListenerOnOkCantidad);
	}

	@SuppressWarnings("unchecked")
	protected void validarCantidadesMaxima(Doublebox dbxCantidad) {
		if (!(Boolean) dbxCantidad.getAttribute(ATTRI_NUEVO)) {
			Map<String, Object> map = (Map<String, Object>) dbxCantidad
					.getAttribute(MAP_DETALLE_FACTURA);
			Detalle_factura detalle_factura = (Detalle_factura) map
					.get(DETALLE_FACTURA);
			ETIPO_SERVICIO servicio = (ETIPO_SERVICIO) dbxCantidad
					.getAttribute(TIPO_SERVICIO);

			Map<String, Object> map_param = new HashMap<String, Object>();
			map_param.put("id", detalle_factura.getCodigo_articulo());
			String tipo_descripcion = "PROCEDIMIENTO";
			if (servicio == ETIPO_SERVICIO.SERVICIO) {
				map_param.put("tabla", "01");// procedimientos
			} else {
				map_param.put("tabla", "02");// articulos
				tipo_descripcion = "ARTICULO";
			}
			Integer cantidad_maxima = facturacionService
					.getCantidadMaximaServicio(map_param);
			if (cantidad_maxima != null) {
				dbxCantidad
						.setAttribute(ATTRI_CANTIDAD_MAXIMA, cantidad_maxima);
				dbxCantidad.setAttribute(ATTRI_TIPO_DESCRIPCION,
						tipo_descripcion);
				dbxCantidad.setAttribute(ATTRI_DESCRIPCION,
						detalle_factura.getDetalle());

				// cerramos la informacion
				dbxCantidad.setAttribute(ATTRI_NUEVO, false);

				// validar
				FormularioUtil.onAccionValidarCantidadMaxima(cantidad_maxima,
						dbxCantidad, detalle_factura.getDetalle(),
						tipo_descripcion);
			}
		} else {
			Integer cantidad_maxima = (Integer) dbxCantidad
					.getAttribute(ATTRI_CANTIDAD_MAXIMA);
			String tipo_descripcion = (String) dbxCantidad
					.getAttribute(ATTRI_TIPO_DESCRIPCION);
			String descripcion = (String) dbxCantidad
					.getAttribute(ATTRI_DESCRIPCION);
			FormularioUtil.onAccionValidarCantidadMaxima(cantidad_maxima,
					dbxCantidad, descripcion, tipo_descripcion);
		}
	}

	/**
	 * Este metodo es utilizado para agregar al componente que tiene las
	 * cantidades del servicio, para que cuando se realice un cambio actualice
	 * el valor total
	 * */
	private void cargarEventoValorUnitario(Doublebox doublebox_unitario) {
		// inicializamos el evento sino existe
		if (eventListenerOnBlurUnitario == null) {
			eventListenerOnBlurUnitario = new EventListener<Event>() {
				@Override
				public void onEvent(Event evt) throws Exception {
					ejecutarEventoValorUnitario((Doublebox) evt.getTarget());
				}
			};
		}
		if (eventListenerOnOkUnitario == null) {
			eventListenerOnOkUnitario = new EventListener<Event>() {
				@Override
				public void onEvent(Event evt) throws Exception {
					ejecutarEventoValorUnitario((Doublebox) evt.getTarget());
				}
			};
		}
		// Cargamos el evento a componente
		doublebox_unitario.addEventListener(Events.ON_BLUR,
				eventListenerOnBlurUnitario);
		doublebox_unitario.addEventListener(Events.ON_OK,
				eventListenerOnOkUnitario);
	}

	/**
	 * Este metodo me permite agregar otra fila dependiendo de un evento de una
	 * fila especifica<br/>
	 * <h1>Ejemplo:</h1> Si la fila es ARTICULO va agregar otro articulo
	 * */
	private void agregarServicio(Event event) {
		Listitem listitem = (Listitem) event.getTarget()
				.getAttribute(_LISTITEM);
		// Verificamos si el item es el ultimo en la lista
		int index = getIndexOfItem(listitem);
		if (index == getItemCount() - 1) {
			ETIPO_SERVICIO servicio = (ETIPO_SERVICIO) event.getTarget()
					.getAttribute(TIPO_SERVICIO);
			agregarServicio(servicio, true);
		}
	}

	/**
	 * Este metodo me permite ejecutar la accion de cambio de cantidad de manera
	 * centralizada
	 * 
	 * @author luis miguel
	 * */
	@SuppressWarnings("unchecked")
	private void ejecutarEventoCantidad(Doublebox doubleboxcantidad) {
		Doublebox doublebox_valor_unitario = (Doublebox) doubleboxcantidad
				.getAttribute(DOUBLE_BOX_VALOR_UNITARIO);
		Doublebox doublebox_valor_total = (Doublebox) doubleboxcantidad
				.getAttribute(DOUBLE_BOX_VALOR_TOTAL);

		double cantidad = ((Doublebox) doubleboxcantidad).getValue() != null ? ((Doublebox) doubleboxcantidad)
				.getValue() : 0d;
		double valor_unitario = doublebox_valor_unitario.getValue() != null ? doublebox_valor_unitario
				.getValue() : 0d;

		doublebox_valor_total.setValue(cantidad * valor_unitario);

		// Actualizamos detalle de factura
		Map<String, Object> map = (Map<String, Object>) doubleboxcantidad
				.getAttribute(MAP_DETALLE_FACTURA);
		Detalle_factura detalle_factura = (Detalle_factura) map
				.get(DETALLE_FACTURA);
		detalle_factura.setCantidad(cantidad);
		detalle_factura.setValor_unitario(valor_unitario);
		detalle_factura.setValor_total(cantidad * valor_unitario);

		/* actualizamos valores de complemento */
		Object servicio_rips = map.get(ITiposServicio.PARAM_RIPS);
		if (servicio_rips != null) {
			if (servicio_rips instanceof Datos_consulta) {
				Datos_consulta consulta = (Datos_consulta) servicio_rips;
				consulta.setValor_consulta(valor_unitario);
				consulta.setValor_neto(detalle_factura.getValor_total());
			} else if (servicio_rips instanceof Datos_procedimiento) {
				Datos_procedimiento procedimiento = (Datos_procedimiento) servicio_rips;
				procedimiento.setUnidades((int) detalle_factura.getCantidad());
				procedimiento.setValor_procedimiento(detalle_factura
						.getValor_unitario());
				procedimiento.setValor_neto(detalle_factura.getValor_total());
			} else if (servicio_rips instanceof Datos_medicamentos) {
				Datos_medicamentos medicamentos = (Datos_medicamentos) servicio_rips;
				medicamentos.setUnidades((int) detalle_factura.getCantidad());
				medicamentos.setValor_unitario(detalle_factura
						.getValor_unitario());
				medicamentos.setValor_total(detalle_factura.getValor_total());
			} else if (servicio_rips instanceof Datos_servicio) {
				Datos_servicio servicio = (Datos_servicio) servicio_rips;
				servicio.setUnidades((int) detalle_factura.getCantidad());
				servicio.setValor_unitario(detalle_factura.getValor_unitario());
				servicio.setValor_total(detalle_factura.getValor_total());
			}
		}
		// Actualizamos total en la vista
		calcularTotal();
	}

	/**
	 * Este metodo me permite ejecutar la accion de cambio de cantidad de manera
	 * centralizada
	 * 
	 * @author luis miguel
	 * */
	@SuppressWarnings("unchecked")
	private void ejecutarEventoValorUnitario(Doublebox doublebox_unitario) {
		Doublebox doublebox_unidades = (Doublebox) doublebox_unitario
				.getAttribute(DOUBLE_BOX_UNIDADES);
		Doublebox doublebox_valor_total = (Doublebox) doublebox_unitario
				.getAttribute(DOUBLE_BOX_VALOR_TOTAL);

		double cantidad = ((Doublebox) doublebox_unidades).getValue() != null ? ((Doublebox) doublebox_unidades)
				.getValue() : 0d;

		double valor_unitario = doublebox_unitario.getValue() != null ? doublebox_unitario
				.getValue() : 0d;

		doublebox_valor_total.setValue(cantidad * valor_unitario);

		// Actualizamos detalle de factura
		Map<String, Object> map = (Map<String, Object>) doublebox_unitario
				.getAttribute(MAP_DETALLE_FACTURA);
		Detalle_factura detalle_factura = (Detalle_factura) map
				.get(DETALLE_FACTURA);
		detalle_factura.setCantidad(cantidad);
		detalle_factura.setValor_unitario(valor_unitario);
		detalle_factura.setValor_total(cantidad * valor_unitario);

		/* actualizamos valores de complemento */
		Object servicio_rips = map.get(ITiposServicio.PARAM_RIPS);
		if (servicio_rips != null) {
			if (servicio_rips instanceof Datos_consulta) {
				Datos_consulta consulta = (Datos_consulta) servicio_rips;
				consulta.setValor_consulta(detalle_factura.getValor_unitario());
				consulta.setValor_neto(detalle_factura.getValor_total());
			} else if (servicio_rips instanceof Datos_procedimiento) {
				Datos_procedimiento procedimiento = (Datos_procedimiento) servicio_rips;
				procedimiento.setUnidades((int) detalle_factura.getCantidad());
				procedimiento.setValor_procedimiento(detalle_factura
						.getValor_unitario());
				procedimiento.setValor_neto(detalle_factura.getValor_total());
			} else if (servicio_rips instanceof Datos_medicamentos) {
				Datos_medicamentos medicamentos = (Datos_medicamentos) servicio_rips;
				medicamentos.setUnidades((int) detalle_factura.getCantidad());
				medicamentos.setValor_unitario(detalle_factura
						.getValor_unitario());
				medicamentos.setValor_total(detalle_factura.getValor_total());
			} else if (servicio_rips instanceof Datos_servicio) {
				Datos_servicio servicio = (Datos_servicio) servicio_rips;
				servicio.setUnidades((int) detalle_factura.getCantidad());
				servicio.setValor_unitario(detalle_factura.getValor_unitario());
				servicio.setValor_total(detalle_factura.getValor_total());
			}
		}
		// Actualizamos total en la vista
		calcularTotal();
	}

	/**
	 * Este metodo devuelve un doublebox estandar
	 * 
	 * @author Luis Miguel
	 * */
	private Doublebox getDoublebox(Double valor) {
		Doublebox doublebox = new Doublebox(valor);
		doublebox.setFormat("#0");
		doublebox.setHflex("1");
		doublebox.setStyle("font-size:9px;text-align:right");
		doublebox.setReadonly(true);
		doublebox.setInplace(true);
		return doublebox;
	}

	private List<Contratos> getListadoContratos(String via_ingreso) {

		List<Contratos> listado_aux = new ArrayList<Contratos>();
		admision = new Admision();
		admision.setCodigo_empresa(getPaciente().getCodigo_empresa());
		admision.setCodigo_sucursal(getPaciente().getCodigo_sucursal());
		admision.setNro_identificacion(getPaciente().getNro_identificacion());
		admision.setCodigo_administradora(getAdministradora().getCodigo());
		admision.setVia_ingreso(via_ingreso);
		admision.setId_plan(contratos != null ? contratos.getId_plan() : null);
		admision.setParticular((administradora != null && administradora
				.getTipo_aseguradora()
				.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) ? "S"
				: "N");
		List<Via_ingreso_contratadas> listado_vias_ingreso = contratos != null ? ServiciosDisponiblesUtils
				.getListado_via_ingreso_contratadas(admision)
				: ServiciosDisponiblesUtils.getListado_via_ingreso_contratadas(
						admision, listado_contratos_pacientes);

		if (listado_vias_ingreso.size() == 1) {
			Via_ingreso_contratadas via_ingreso_contratadas = listado_vias_ingreso
					.get(0);
			for (Contratos contratos_aux : listado_contratos_pacientes) {
				if (contratos_aux.getId_plan().equals(
						via_ingreso_contratadas.getId_plan())) {
					listado_aux.add(contratos_aux);
					this.contratos = contratos_aux;
				}
			}
			actualizarContrato(contratos, true);
		} else if (!listado_vias_ingreso.isEmpty()) {
			for (Via_ingreso_contratadas via_ingreso_contratadas : listado_vias_ingreso) {
				boolean encontrado = false;
				for (Contratos contratos_aux : listado_contratos_pacientes) {
					if (contratos_aux.getId_plan().equals(
							via_ingreso_contratadas.getId_plan())) {
						listado_aux.add(contratos_aux);
						encontrado = true;
						break;
					}
				}
				if (!encontrado) {
					Contratos contratos_aux = new Contratos();
					contratos_aux.setCodigo_empresa(via_ingreso_contratadas
							.getCodigo_empresa());
					contratos_aux.setCodigo_sucursal(via_ingreso_contratadas
							.getCodigo_sucursal());
					contratos_aux.setId_plan(via_ingreso_contratadas
							.getId_plan());
					contratos_aux
							.setCodigo_administradora(via_ingreso_contratadas
									.getCodigo_administradora());
					contratos_aux = contratosService.consultar(contratos_aux);
					listado_aux.add(contratos_aux);
				}
			}
		}
		return listado_aux;
	}

	public void actualizarContrato(Contratos contratos_aux, boolean agregar) {
		// log.info("ejecutando metodo @actualizarContrato() ===> " + onCambio);
		if (contratos_aux != null) {
			if (onCambio != null) {
				onCambio.OnCambioContrato(contratos_aux, agregar);
			}
			setContratos(contratos_aux);
			Via_ingreso_contratadas via_ingreso_contratadas = new Via_ingreso_contratadas();
			via_ingreso_contratadas.setCodigo_empresa(contratos_aux
					.getCodigo_empresa());
			via_ingreso_contratadas.setCodigo_sucursal(contratos_aux
					.getCodigo_sucursal());
			via_ingreso_contratadas.setId_plan(contratos_aux.getId_plan());
			via_ingreso_contratadas.setCodigo_administradora(contratos_aux
					.getCodigo_administradora());
			via_ingreso_contratadas
					.setVia_ingreso(elemento_via_ingreso_principal.getCodigo());
			via_ingreso_contratadas = via_ingreso_contratadasService
					.consultar_informacion(via_ingreso_contratadas);
			setVia_ingreso_contratadas(via_ingreso_contratadas);
		}
	}

	/**
	 * Este metodo devuelve un bandbox interno dependiendo del tipo de servicio
	 * que se nesecita
	 * 
	 * @param servicio
	 *            -> Es el tipo de servicio por el cual va a consultar
	 * @param detalle_factura2
	 * @param labelDescripcion
	 *            -> Contiene la instancia del componente que lleva el nombre
	 *            del servicio
	 * */
	private BandBoxRowMacro getBandboxMacroServicio(ETIPO_SERVICIO servicio,
			Detalle_factura detalle_factura) {
		BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro();
		bandBoxRowMacro.setReadonly(false);
		bandBoxRowMacro.setHflex("1");
		bandBoxRowMacro.setAttribute(TIPO_SERVICIO, servicio);
		bandBoxRowMacro.configurar(getConfiguracionBandboxServicio());
		if (detalle_factura.getCodigo_articulo() != null
				&& !detalle_factura.getCodigo_articulo().trim().isEmpty()) {
			cargarServicio(detalle_factura, servicio, bandBoxRowMacro);
		}
		return bandBoxRowMacro;
	}

	private void cargarServicio(Detalle_factura detalle_factura,
			ETIPO_SERVICIO servicio, BandBoxRowMacro bandbox) {

		if (servicio == ETIPO_SERVICIO.SERVICIO) {
			if (contratos != null) {
				Procedimientos procedimiento = new Procedimientos();
				procedimiento.setId_procedimiento(new Long(detalle_factura
						.getCodigo_articulo()));
				procedimiento = procedimientosService.consultar(procedimiento);
				if (procedimiento != null) {
					bandbox.seleccionarRegistro(procedimiento,
							procedimiento.getCodigo_cups());
				}
			} else {
				bandbox.setValue(detalle_factura.getCodigo_articulo());
			}
		} else {
			bandbox.setValue(detalle_factura.getCodigo_articulo());
		}
	}

	/**
	 * ARCHIVOS DE CONFIGURACION
	 * 
	 * @param servicio
	 * */
	private IConfiguracionBandbox<Object> getConfiguracionBandboxServicio() {
		return new IConfiguracionBandbox<Object>() {

			@Override
			public void onSeleccionar(Object t, Bandbox bandbox) {
				ETIPO_SERVICIO servicio = (ETIPO_SERVICIO) bandbox
						.getAttribute(TIPO_SERVICIO);

				Doublebox doublebox_cantidad = (Doublebox) bandbox
						.getAttribute(DOUBLE_BOX_UNIDADES);
				Toolbarbutton toolbarbutton_complementacion = (Toolbarbutton) bandbox
						.getAttribute(TOOLBAR_BUTON_COMPL);
				if (bandbox.getAttribute(PARAM_UNICA_SELECCION) != null) {
					bloquearUnico(doublebox_cantidad,
							toolbarbutton_complementacion);
				} else {
					if (doublebox_cantidad != null) {
						doublebox_cantidad.setReadonly(false);
					}
				}

				if (servicio == ETIPO_SERVICIO.ARTICULO) {
					Articulo articulo = (Articulo) t;
					if (articulo.getFacturable()) {
						Toolbarbutton toolbarbutton_seleccion = (Toolbarbutton) bandbox
								.getAttribute(TOOLBAR_BUTON_SELEC);
						Listcell listcell = (Listcell) bandbox
								.getAttribute(LISTCELL_SELEC);

						listcell.setValue(true);
						listcell.setStyle("font-size:9px;cursor:pointer;background-color:white;border-style:solid;border-width:1px;");
						listcell.setTooltiptext("Este detalle de factura obligatoriamente es facturable");
						toolbarbutton_seleccion
								.setImage("/images/si_seleccionado.png");

						Iterable<EventListener<? extends Event>> eventos = toolbarbutton_seleccion
								.getEventListeners(Events.ON_CLICK);

						Iterator<EventListener<? extends Event>> iterator = eventos
								.iterator();

						while (iterator.hasNext()) {
							EventListener<? extends Event> eventListener = iterator
									.next();
							toolbarbutton_seleccion.removeEventListener(
									Events.ON_CLICK, eventListener);
						}

					}
				}

			}

			@Override
			public String getCabecera(Bandbox bandbox) {
				if (((ETIPO_SERVICIO) bandbox.getAttribute(TIPO_SERVICIO)) == ETIPO_SERVICIO.SERVICIO) {
					return "Código cups#100px|Descripcion";
				} else {
					return "Código#100px|Nombre";
				}
			}

			@Override
			public String getWidthListBox() {
				return "500px";
			}

			@Override
			public String getHeightListBox() {
				return "150px";
			}

			@Override
			public void renderListitem(Listitem listitem, Object registro,
					Bandbox bandbox) {
				try {
					if (((ETIPO_SERVICIO) bandbox.getAttribute(TIPO_SERVICIO)) == ETIPO_SERVICIO.SERVICIO) {
						Field fieldCodigoCups = registro.getClass()
								.getDeclaredField("codigo_cups");
						Field fieldDescripcion = registro.getClass()
								.getDeclaredField("descripcion");

						if (!fieldCodigoCups.isAccessible()) {
							fieldCodigoCups.setAccessible(true);
						}

						if (!fieldDescripcion.isAccessible()) {
							fieldDescripcion.setAccessible(true);
						}

						String codigo_cups = (String) fieldCodigoCups
								.get(registro);
						String descripcion = (String) fieldDescripcion
								.get(registro);

						listitem.appendChild(new Listcell("" + codigo_cups));
						listitem.appendChild(new Listcell("" + descripcion));
					} else { // Aplica cuando es articulo
						Articulo articulo = (Articulo) registro;
						listitem.appendChild(new Listcell(""
								+ articulo.getCodigo_articulo()));
						listitem.appendChild(new Listcell(""
								+ articulo.getNombre1()));
					}
				} catch (Exception e) {
					MensajesUtil.mensajeError(e, null,
							ServiciosFacturacionMacro.this);
				}
			}

			@Override
			public List<?> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros, Bandbox bandbox) {
				if (contratos != null) {
					Admision admision = getAdmision();
					if (contratos != null)
						admision.setId_plan(contratos.getId_plan());
					List<String> servicios_contratados = ServiciosDisponiblesUtils
							.getFiltroProcedimientos(admision, true);

					if (((ETIPO_SERVICIO) bandbox.getAttribute(TIPO_SERVICIO)) == ETIPO_SERVICIO.SERVICIO) {
						// parametros.put("nivel_orden", contratos.getNivel());
						parametros.put("paramTodo", "");
						parametros.put("value", valorBusqueda);

						// //log.info("listado_cups_contratados ===> "
						// + servicios_contratados);

						if (!servicios_contratados.isEmpty()) {
							parametros.put("listado_cups_contratados",
									servicios_contratados);
						} else if (admision.getVia_ingreso().equals(
								IVias_ingreso.LABORATORIOS)
								|| admision.getVia_ingreso().equals(
										IVias_ingreso.LABORATORIOS_PYP)) {
							parametros
									.put("codigo_tipo_procedimiento",
											ServiciosDisponiblesUtils.CODSER_LABORATORIO_CLINICO);
							if (admision.getVia_ingreso().equals(
									IVias_ingreso.LABORATORIOS_PYP)) {
								parametros.put("pyp", "S");
							} else {
								parametros.put("pyp", "N");
							}
						} else {
							Elemento elemento = new Elemento();
							elemento.setTipo("via_ingreso");
							elemento.setCodigo(admision.getVia_ingreso());
							elemento = elementoService.consultar(elemento);
							Notificaciones
									.mostrarNotificacionAlerta(
											"Advertencia",
											"No se encontro ningún servicio en la via de ingreso "
													+ (elemento != null ? elemento
															.getDescripcion()
															: admision
																	.getVia_ingreso()),
											30000);
							return new ArrayList<Object>();
						}

						if (onMultiplesContratosAccion != null) {
							if (onMultiplesContratosAccion.laboratorioPyp()) {
								parametros.put("pyp", "S");
								parametros
										.put("tipo_procedimiento",
												ServiciosDisponiblesUtils.TIPO_LABORATORIO_CLINICO);
							}
						}

						procedimientosService.setLimit("limit 25 offset 0");
						return procedimientosService.listar(parametros);

					} else {
						parametros.put("paramTodo", "");
						parametros.put("value", valorBusqueda.toUpperCase());
						parametros.put("codigo_empresa",
								contratos.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								contratos.getCodigo_sucursal());
						parametros.put("grupo1_in", new String[] {
								IConstantes.TIPO_ARTICULO_MEDICAMENTO,
								IConstantes.TIPO_ARTICULO_MATERIALES_INSUMO,
								IConstantes.TIPO_ARTICULO_SERVICIOS });
						articuloService.setLimit("limit 25 offset 0");
						return articuloService.listar(parametros);
					}
				} else {
					throw new ValidacionRunTimeException(
							"No se puede cargar el procedimiento por que el manual tarifario no existe");
				}
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean seleccionarRegistro(Bandbox bandbox, Object registro) {
				try {
					ETIPO_SERVICIO servicio = ((ETIPO_SERVICIO) bandbox
							.getAttribute(TIPO_SERVICIO));
					if (servicio == ETIPO_SERVICIO.SERVICIO) {
						Procedimientos procedimientos = (Procedimientos) registro;
						String codigo_procedimiento = procedimientos
								.getCodigo_cups();
						String limite_inferior = procedimientos
								.getLimite_inferior();
						String limite_superior = procedimientos
								.getLimite_superior();
						String sexo_pcd = procedimientos.getSexo();

						Map<String, Object> mapa_validar = Utilidades
								.validarInformacionLimiteSexo("Procedimiento",
										codigo_procedimiento, limite_inferior,
										limite_superior, sexo_pcd,
										simpleDateFormat.format(paciente
												.getFecha_nacimiento()),
										paciente.getSexo());
						Boolean success = (Boolean) mapa_validar.get("success");
						if (!success) {
							String msg = (String) mapa_validar.get("msg");
							MensajesUtil.mensajeAlerta(
									"Procedimiento no valido", msg);
							return false;
						}
					}

					Map<String, Object> map = (Map<String, Object>) bandbox
							.getAttribute(MAP_DETALLE_FACTURA);

					Detalle_factura detalle_factura = (Detalle_factura) map
							.get(DETALLE_FACTURA);

					map.put(REGISTRO_SELECCIONADO, registro);

					// Extraemos componentes
					Label label_nombre_servicio = (Label) bandbox
							.getAttribute(LABEL_NOMBRE_SERVICIO);
					Doublebox doublebox_cantidad = (Doublebox) bandbox
							.getAttribute(DOUBLE_BOX_UNIDADES);
					Doublebox doublebox_valor_unitario = (Doublebox) bandbox
							.getAttribute(DOUBLE_BOX_VALOR_UNITARIO);
					Listcell celda_tipo_servicio = (Listcell) bandbox
							.getAttribute(CELDA_TIPO_SERVICIO);
					Toolbarbutton toolbarbutton_complementacion = (Toolbarbutton) bandbox
							.getAttribute(TOOLBAR_BUTON_COMPL);
					Toolbarbutton toolbarbutton_facturable = (Toolbarbutton) bandbox
							.getAttribute(TOOLBAR_BUTON_SELEC);
					map.remove(ITiposServicio.PARAM_RIPS);// limpiamos los datos
															// de complemento,
															// por si hacen el
															// cambio de
															// servicio
					String tipo_servicio = "";
					bandbox.removeAttribute(PARAM_UNICA_SELECCION);
					boolean habilitar_complemento = true;
					if (servicio == ETIPO_SERVICIO.SERVICIO) {
						Field fieldCodigoCups = registro.getClass()
								.getDeclaredField("codigo_cups");
						Field fieldDescripcion = registro.getClass()
								.getDeclaredField("descripcion");
						Field fieldIdProcedimiento = registro.getClass()
								.getDeclaredField("id_procedimiento");

						if (!fieldDescripcion.isAccessible()) {
							fieldDescripcion.setAccessible(true);
						}

						if (!fieldIdProcedimiento.isAccessible()) {
							fieldIdProcedimiento.setAccessible(true);
						}

						if (!fieldCodigoCups.isAccessible()) {
							fieldCodigoCups.setAccessible(true);
						}

						String descripcion = (String) fieldDescripcion
								.get(registro);
						Long id_procedimiento = (Long) fieldIdProcedimiento
								.get(registro);
						String codigo_cups = (String) fieldCodigoCups
								.get(registro);

						Map<String, Object> mapProcedimiento = procedimientosService
								.getProcedimiento(via_ingreso_contratadas,
										id_procedimiento);
						double valor_pcd = (Double) mapProcedimiento
								.get("valor_pcd");
						String consulta = (String) mapProcedimiento
								.get("consulta");
						String quirurgico = (String) mapProcedimiento
								.get("quirurgico");

						double descuento = (Double) mapProcedimiento
								.get("descuento");
						detalle_factura.setDescuento(descuento);
						detalle_factura.setCodigo_articulo(id_procedimiento
								+ "");
						detalle_factura.setValor_unitario(valor_pcd);

						label_nombre_servicio.setValue(descripcion + "");
						doublebox_valor_unitario.setValue(valor_pcd);
						bandbox.setValue(codigo_cups);
						int cantidad_maxima = (Integer) mapProcedimiento
								.get("cantidad_maxima");

						// mapeamos la informacion para la validacion para
						// procedimientos
						doublebox_cantidad.setAttribute(ATTRI_CANTIDAD_MAXIMA,
								cantidad_maxima);
						doublebox_cantidad.setAttribute(ATTRI_TIPO_DESCRIPCION,
								"PROCEDIMIENTO");
						doublebox_cantidad.setAttribute(ATTRI_DESCRIPCION,
								descripcion);

						if (!consulta.trim().isEmpty()) {
							if (!quirurgico.trim().isEmpty()
									&& quirurgico.equals("S")) {
								tipo_servicio = ajustarTipos(
										celda_tipo_servicio,
										ITiposServicio.QUIRURGICO);
								alimentarProcedimiento(map, mapProcedimiento,
										detalle_factura,
										via_ingreso_contratadas, true);
								bandbox.setAttribute(PARAM_UNICA_SELECCION,
										PARAM_UNICA_SELECCION);
							} else if (consulta.equals("S")) {
								tipo_servicio = ajustarTipos(
										celda_tipo_servicio,
										ITiposServicio.CONSULTA);
								alimentarConsulta(map, mapProcedimiento,
										detalle_factura,
										via_ingreso_contratadas);
								bandbox.setAttribute(PARAM_UNICA_SELECCION,
										PARAM_UNICA_SELECCION);
							} else {
								tipo_servicio = ajustarTipos(
										celda_tipo_servicio,
										ITiposServicio.PROCEDIMIENTO);
								alimentarProcedimiento(map, mapProcedimiento,
										detalle_factura,
										via_ingreso_contratadas, false);
								doublebox_cantidad.setFocus(true);
							}
						}
						detalle_factura.setFacturable(true);
					} else {
						Articulo articulo = (Articulo) registro;

						Map<String, Object> params = new HashMap<String, Object>();
						params.put("via_ingreso_contratadas",
								via_ingreso_contratadas);
						params.put("codigo_articulo",
								articulo.getCodigo_articulo());
						params.put("grupo", articulo.getGrupo1());

						Map<String, Object> mapArticulo = articuloService
								.getMedicamentos(params);
						Double valor_unitario = (Double) mapArticulo
								.get("valor_unitario");
						int cantidad_maxima = (Integer) mapArticulo
								.get("cantidad_maxima");
						double descuento = (Double) mapArticulo
								.get("descuento");
						detalle_factura.setDescuento(descuento);
						detalle_factura.setCodigo_articulo(articulo
								.getCodigo_articulo());
						bandbox.setValue(articulo.getCodigo_articulo());
						label_nombre_servicio.setValue(articulo.getNombre1()
								+ "");
						doublebox_valor_unitario.setValue(valor_unitario);

						// mapeamos la informacion para la validacion para
						// procedimientos
						doublebox_cantidad.setAttribute(ATTRI_CANTIDAD_MAXIMA,
								cantidad_maxima);
						doublebox_cantidad
								.setAttribute(
										ATTRI_TIPO_DESCRIPCION,
										articulo.getGrupo1().equals("01") ? "MEDICAMENTOS"
												: "MATERIALES E INSUMOS");
						doublebox_cantidad.setAttribute(ATTRI_DESCRIPCION,
								articulo.getNombre1() + "");

						if (articulo.getGrupo1().equals(
								IConstantes.TIPO_ARTICULO_MEDICAMENTO)) {
							tipo_servicio = ajustarTipos(celda_tipo_servicio,
									ITiposServicio.MEDICAMENTO);
							alimentarMedicamentosMaterialesInsumos(map,
									mapArticulo, detalle_factura,
									via_ingreso_contratadas,
									IConstantes.TIPO_ARTICULO_MEDICAMENTO); // 01
																			// -
							// Medicamentos
						} else if (articulo.getGrupo1().equals(
								IConstantes.TIPO_ARTICULO_MATERIALES_INSUMO)) {
							tipo_servicio = ajustarTipos(celda_tipo_servicio,
									ITiposServicio.MATERIALES_INSUMOS);
							alimentarMedicamentosMaterialesInsumos(map,
									mapArticulo, detalle_factura,
									via_ingreso_contratadas,
									IConstantes.TIPO_ARTICULO_MATERIALES_INSUMO); // 01
																					// -
							// Medicamentos
						} else {
							tipo_servicio = ajustarTipos(celda_tipo_servicio,
									ITiposServicio.SERVICIO);
							alimentarServicios(map, mapArticulo,
									detalle_factura, via_ingreso_contratadas);
						}
						// se habilito el complemento para los medicamentos
						habilitar_complemento = true;
						// Verificamos si es facturable
						detalle_factura.setFacturable(articulo.getFacturable());
						aplicarFacturable(articulo.getFacturable(),
								toolbarbutton_facturable);
						doublebox_cantidad.setFocus(true);
					}
					if (tipo_servicio.equals(ITiposServicio.QUIRURGICO)) {
						detalle_factura
								.setTipo(ITiposServicio.PROCEDIMIENTO_MULT);
					} else {
						detalle_factura.setTipo(tipo_servicio);
					}
					detalle_factura
							.setDetalle(label_nombre_servicio.getValue());
					toolbarbutton_complementacion
							.setVisible(habilitar_complemento);
					// listbox_unidad_funcional.setDisabled(true);
				} catch (Exception e) {
					MensajesUtil.mensajeError(e, null,
							ServiciosFacturacionMacro.this);
				}
				return true;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				// Listbox listbox_unidad_funcional = (Listbox)
				// bandbox.getAttribute(LISTBOX_UNIDAD_FUNCIONAL);
				// listbox_unidad_funcional.setDisabled(false);
				Map<String, Object> map = (Map<String, Object>) bandbox
						.getAttribute(MAP_DETALLE_FACTURA);
				Label label_nombre_servicio = (Label) bandbox
						.getAttribute(LABEL_NOMBRE_SERVICIO);
				Doublebox doublebox_cantidad = (Doublebox) bandbox
						.getAttribute(DOUBLE_BOX_UNIDADES);
				Doublebox doublebox_valor_unitario = (Doublebox) bandbox
						.getAttribute(DOUBLE_BOX_VALOR_UNITARIO);
				Listcell celda_tipo_servicio = (Listcell) bandbox
						.getAttribute(CELDA_TIPO_SERVICIO);
				Toolbarbutton toolbarbutton_complementacion = (Toolbarbutton) bandbox
						.getAttribute(TOOLBAR_BUTON_COMPL);
				// Toolbarbutton toolbarbutton_facturable = (Toolbarbutton)
				// bandbox.getAttribute(TOOLBAR_BUTON_SELEC);
				map.remove(ITiposServicio.PARAM_RIPS);// limpiamos los datos de
														// complemento, por si
														// hacen el cambio de
														// servicio
				bandbox.removeAttribute(PARAM_UNICA_SELECCION);

				// limpiamos compoenetes
				label_nombre_servicio.setValue("");
				doublebox_cantidad.setValue(null);
				doublebox_valor_unitario.setValue(null);
				celda_tipo_servicio.setStyle("background-color:none");
				celda_tipo_servicio.setTooltiptext("");
				toolbarbutton_complementacion.setVisible(false);

				// Eliminamos complento para que se vuelva a cargar
				// cuando se seleccione otro procedimiento
				doublebox_cantidad.removeAttribute("compl");

				ejecutarEventoCantidad(doublebox_cantidad);
			}
		};
	}

	private void bloquearUnico(Doublebox doublebox_cantidad,
			Toolbarbutton toolbarbutton_complementacion) {
		doublebox_cantidad.setValue(1);
		doublebox_cantidad.setReadonly(true);
		abrirComplementacion(toolbarbutton_complementacion);
		ejecutarEventoCantidad(doublebox_cantidad);
	}

	protected void alimentarServicios(Map<String, Object> map,
			Map<String, Object> mapArticulo, Detalle_factura detalle_factura,
			Via_ingreso_contratadas via_ingreso_contratadas) {
		Datos_servicio datos_servicio = (Datos_servicio) map
				.get(ITiposServicio.PARAM_RIPS);
		if (datos_servicio == null) {
			datos_servicio = new Datos_servicio();
			map.put(ITiposServicio.PARAM_RIPS, datos_servicio);
		}

		map.put(FECHA_REALIZACION, informacionUltimoRegistro.getFecha());
		map.put(NRO_AUTORIZACION,
				informacionUltimoRegistro.getNro_autorizacion());

		// Verificamos si existe la facturacion de servicios
		if (facturacion_servicio == null) {
			facturacion_servicio = new Facturacion_servicio();
			crearFacturacionServicios(facturacion_servicio, detalle_factura);
		}

		String referencia = (String) mapArticulo.get("referencia");
		double descuento = (Double) mapArticulo.get("descuento");
		double incremento = (Double) mapArticulo.get("incremento");

		// rips de servicios
		datos_servicio.setCodigo_empresa(detalle_factura.getCodigo_empresa());
		datos_servicio.setCodigo_sucursal(detalle_factura.getCodigo_sucursal());
		datos_servicio.setNro_factura("");
		// datos_servicio.setConsecutivo(1 + "");
		datos_servicio.setCodigo_servicio(detalle_factura.getCodigo_articulo());
		datos_servicio.setUnidades((int) detalle_factura.getCantidad());
		datos_servicio.setValor_unitario(detalle_factura.getValor_unitario());
		datos_servicio.setValor_total(detalle_factura.getValor_total());
		datos_servicio.setCancelo_copago("N");
		datos_servicio.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		datos_servicio.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		datos_servicio.setCreacion_user(detalle_factura.getCreacion_user());
		datos_servicio.setUltimo_user(detalle_factura.getUltimo_user());
		datos_servicio.setValor_real(detalle_factura.getValor_real());
		datos_servicio.setDescuento(descuento);
		datos_servicio.setIncremento(incremento);
		datos_servicio.setReferencia_pyp(referencia);
		datos_servicio.setTipo_servicio("01");
	}

	protected void alimentarMedicamentosMaterialesInsumos(
			Map<String, Object> map, Map<String, Object> mapArticulo,
			Detalle_factura detalle_factura,
			Via_ingreso_contratadas via_ingreso_contratadas, String tipo) {

		Datos_medicamentos datos_medicamentos = (Datos_medicamentos) map
				.get(ITiposServicio.PARAM_RIPS);
		if (datos_medicamentos == null) {
			datos_medicamentos = new Datos_medicamentos();
			map.put(ITiposServicio.PARAM_RIPS, datos_medicamentos);
		}

		map.put(FECHA_REALIZACION, informacionUltimoRegistro.getFecha());
		map.put(NRO_AUTORIZACION,
				informacionUltimoRegistro.getNro_autorizacion());

		// Facturacion medicamentos
		if (tipo.equals("01")) {
			Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
			crearFacturacion(tipo, "FACTURACION DE MEDICAMENTOS",
					facturacion_medicamento, detalle_factura);
		} else {
			Facturacion_medicamento facturacion_materias_insumos = new Facturacion_medicamento();
			crearFacturacion(tipo, "FACTURACION DE MATERIALES/INSUMOS",
					facturacion_materias_insumos, detalle_factura);
		}

		String referencia = (String) mapArticulo.get("referencia");
		String unidad_medida = (String) mapArticulo.get("unidad_medida");
		String concentracion = (String) mapArticulo.get("concentracion");
		String nombre1 = (String) mapArticulo.get("nombre1");
		String pos = (String) mapArticulo.get("pos");
		String bodega = (String) mapArticulo.get("bodega");
		double descuento = (Double) mapArticulo.get("descuento");
		double incremento = (Double) mapArticulo.get("incremento");

		// datos medicamentos
		datos_medicamentos.setCodigo_empresa(detalle_factura
				.getCodigo_empresa());
		datos_medicamentos.setCodigo_sucursal(detalle_factura
				.getCodigo_sucursal());
		datos_medicamentos
				.setNro_factura(detalle_factura.getFactura_concepto());
		// datos_medicamentos.setConsecutivo(1 + "");
		datos_medicamentos.setCodigo_bodega(bodega);
		datos_medicamentos.setCodigo_medicamento(detalle_factura
				.getCodigo_articulo());
		datos_medicamentos.setCodigo_lote("");
		datos_medicamentos.setUnidades((int) detalle_factura.getCantidad());
		datos_medicamentos.setValor_unitario(detalle_factura
				.getValor_unitario());
		datos_medicamentos.setValor_total(detalle_factura.getValor_total());
		datos_medicamentos.setCancelo_copago("N");
		datos_medicamentos.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_medicamentos.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_medicamentos.setCreacion_user(detalle_factura.getCreacion_user());
		datos_medicamentos.setUltimo_user(detalle_factura.getUltimo_user());
		datos_medicamentos.setValor_real(detalle_factura.getValor_real());
		datos_medicamentos.setDescuento(descuento);
		datos_medicamentos.setIncremento(incremento);
		datos_medicamentos.setCantidad_entregada((int) detalle_factura
				.getCantidad());
		datos_medicamentos.setReferencia_pyp(referencia != null ? referencia
				: "");

		datos_medicamentos.setTipo_medicamento(pos.equals("S") ? "1" : "2");
		datos_medicamentos.setNombre_generico(nombre1);
		datos_medicamentos.setForma_farmaceutica("");
		datos_medicamentos.setConcentracion_medicamento(concentracion);
		datos_medicamentos.setUnidad_medida(unidad_medida);
	}

	private void crearFacturacionServicios(
			Facturacion_servicio facturacion_servicio,
			Detalle_factura detalle_factura) {
		facturacion_servicio.setCodigo_empresa(detalle_factura
				.getCodigo_empresa());
		facturacion_servicio.setCodigo_sucursal(detalle_factura
				.getCodigo_sucursal());
		facturacion_servicio.setTipo_identificacion(getPaciente()
				.getTipo_identificacion());
		facturacion_servicio.setNro_identificacion(getPaciente()
				.getNro_identificacion());
		facturacion_servicio.setCodigo_administradora(via_ingreso_contratadas
				.getCodigo_administradora());
		facturacion_servicio.setId_plan(via_ingreso_contratadas.getId_plan());
		facturacion_servicio.setNro_ingreso("");
		facturacion_servicio.setFecha_servicio(informacionUltimoRegistro
				.getFecha());
		facturacion_servicio.setNumero_autorizacion(getNroAutorizacion());
		facturacion_servicio
				.setObservacion("FACTURA DE SERVICIO POR HOJA DE GASTO");
		facturacion_servicio.setTipo("01");
		facturacion_servicio.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_servicio.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_servicio.setCreacion_user(detalle_factura
				.getCreacion_user());
		facturacion_servicio.setUltimo_user(detalle_factura.getUltimo_user());
	}

	private void crearFacturacion(String tipo, String observaciones,
			Facturacion_medicamento facturacion_medicamento,
			Detalle_factura detalle_factura) {
		facturacion_medicamento.setCodigo_empresa(detalle_factura
				.getCodigo_empresa());
		facturacion_medicamento.setCodigo_sucursal(detalle_factura
				.getCodigo_sucursal());
		facturacion_medicamento.setNro_factura("");
		facturacion_medicamento.setTipo_identificacion(getPaciente()
				.getTipo_identificacion());
		facturacion_medicamento.setNro_identificacion(getPaciente()
				.getNro_identificacion());
		facturacion_medicamento
				.setCodigo_administradora(via_ingreso_contratadas
						.getCodigo_administradora());
		facturacion_medicamento
				.setId_plan(via_ingreso_contratadas.getId_plan());
		facturacion_medicamento.setNro_ingreso("");
		facturacion_medicamento.setFecha_medicamento(informacionUltimoRegistro
				.getFecha());
		facturacion_medicamento.setNumero_autorizacion(getNroAutorizacion());
		facturacion_medicamento.setObservacion("" + observaciones);
		facturacion_medicamento.setTipo(tipo); // 01 tipo medicamento
		facturacion_medicamento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_medicamento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_medicamento.setCreacion_user(detalle_factura
				.getCreacion_user());
		facturacion_medicamento
				.setUltimo_user(detalle_factura.getUltimo_user());
		facturacion_medicamento.setCodigo_solicitud("");
		facturacion_medicamento.setCodigo_receta("");
		facturacion_medicamento.setC_costo("");
	}

	protected void alimentarProcedimiento(Map<String, Object> map,
			Map<String, Object> mapProcedimiento,
			Detalle_factura detalle_factura,
			Via_ingreso_contratadas via_ingreso_contratadas,
			boolean es_quirurgico) {
		Datos_procedimiento datos_procedimiento = (Datos_procedimiento) map
				.get(ITiposServicio.PARAM_RIPS);
		Procedimientos procedimientos = (Procedimientos) map
				.get(REGISTRO_SELECCIONADO);
		if (datos_procedimiento == null) {
			datos_procedimiento = new Datos_procedimiento();
			datos_procedimiento
					.setCodigo_diagnostico_principal(informacionUltimoRegistro
							.getDiagnostico_principal());
			datos_procedimiento
					.setCodigo_diagnostico_relacionado(informacionUltimoRegistro
							.getDiagnostico_relacionado());
			map.put(ITiposServicio.PARAM_RIPS, datos_procedimiento);
		}

		String codigo_cups = (String) mapProcedimiento.get("codigo_cups");
		double valor_real = (Double) mapProcedimiento.get("valor_real");
		double descuento = (Double) mapProcedimiento.get("descuento");
		double incremento = (Double) mapProcedimiento.get("incremento");

		datos_procedimiento.setCodigo_registro(Utilidades
				.getValorLong(detalle_factura.getFactura_concepto()));
		datos_procedimiento.setValor_real(valor_real);
		datos_procedimiento.setDescuento(descuento);
		datos_procedimiento.setIncremento(incremento);
		datos_procedimiento.setCodigo_procedimiento(detalle_factura
				.getCodigo_articulo());
		datos_procedimiento
				.setCreacion_date(detalle_factura.getCreacion_date());
		datos_procedimiento
				.setCreacion_user(detalle_factura.getCreacion_user());
		datos_procedimiento
				.setUltimo_update(detalle_factura.getUltimo_update());
		datos_procedimiento.setUltimo_user(detalle_factura.getUltimo_user());
		datos_procedimiento.setCodigo_empresa(detalle_factura
				.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(detalle_factura
				.getCodigo_sucursal());
		datos_procedimiento.setNro_identificacion(getPaciente()
				.getNro_identificacion());
		datos_procedimiento.setTipo_identificacion(getPaciente()
				.getTipo_identificacion());
		datos_procedimiento.setId_plan(via_ingreso_contratadas.getId_plan());
		datos_procedimiento.setCodigo_administradora(via_ingreso_contratadas
				.getCodigo_administradora());
		datos_procedimiento.setValor_procedimiento(detalle_factura
				.getValor_unitario());
		datos_procedimiento.setNumero_autorizacion(getNroAutorizacion());
		datos_procedimiento.setCancelo_copago("N");
		datos_procedimiento.setCodigo_cups(codigo_cups);
		datos_procedimiento.setCodigo_registro(Utilidades
				.getValorLong(detalle_factura.getFactura_concepto()));
		datos_procedimiento.setEs_quirurgico(es_quirurgico ? "S" : "N");
		datos_procedimiento
				.setAmbito_procedimiento(configuracion_admision_ingreso
						.getAmbito_realizacion());
		datos_procedimiento.setFinalidad_procedimiento(procedimientos
				.getFinalidad_procedimiento());
		datos_procedimiento
				.setPersonal_atiende(prestadores != null ? prestadores
						.getTipo_medico() : "");
		datos_procedimiento
				.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
	}

	protected void alimentarConsulta(Map<String, Object> map,
			Map<String, Object> mapProcedimiento,
			Detalle_factura detalle_factura,
			Via_ingreso_contratadas via_ingreso_contratadas) {
		Datos_consulta datos_consulta = (Datos_consulta) map
				.get(ITiposServicio.PARAM_RIPS);
		if (datos_consulta == null) {
			datos_consulta = new Datos_consulta();
			datos_consulta
					.setCodigo_diagnostico_principal(informacionUltimoRegistro
							.getDiagnostico_principal());
			datos_consulta.setCodigo_diagnostico1(informacionUltimoRegistro
					.getDiagnostico_relacionado());
			map.put(ITiposServicio.PARAM_RIPS, datos_consulta);
		}

		double valor_real = (Double) mapProcedimiento.get("valor_real");
		double descuento = (Double) mapProcedimiento.get("descuento");
		double incremento = (Double) mapProcedimiento.get("incremento");

		datos_consulta.setValor_real(valor_real);
		datos_consulta.setDescuento(descuento);
		datos_consulta.setIncremento(incremento);
		datos_consulta.setCodigo_consulta(detalle_factura.getCodigo_articulo());
		datos_consulta.setCreacion_date(detalle_factura.getCreacion_date());
		datos_consulta.setCreacion_user(detalle_factura.getCreacion_user());
		datos_consulta.setUltimo_update(detalle_factura.getUltimo_update());
		datos_consulta.setUltimo_user(detalle_factura.getUltimo_user());
		datos_consulta.setCodigo_empresa(detalle_factura.getCodigo_empresa());
		datos_consulta.setCodigo_sucursal(detalle_factura.getCodigo_sucursal());
		datos_consulta.setNro_identificacion(getPaciente()
				.getNro_identificacion());
		datos_consulta.setTipo_identificacion(getPaciente()
				.getTipo_identificacion());
		datos_consulta.setId_plan(via_ingreso_contratadas.getId_plan());
		datos_consulta.setCodigo_administradora(via_ingreso_contratadas
				.getCodigo_administradora());
		datos_consulta.setValor_consulta(detalle_factura.getValor_unitario());
		datos_consulta.setNumero_autorizacion(getNroAutorizacion());
		datos_consulta.setCancelo_copago("N");
		datos_consulta.setCodigo_registro(Utilidades
				.getValorLong(detalle_factura.getFactura_concepto()));
	}

	private String getNroAutorizacion() {
		String nro_autorizacion = "";
		if (getGeneralComposer() instanceof Facturacion_ripsAction) {
			nro_autorizacion = ((Facturacion_ripsAction) getGeneralComposer())
					.getNroAutorizacion();
		}
		return nro_autorizacion;
	}

	/*
	 * TODO complementacion COMPLEMENTACION DE DATOS PARA SERVICIOS
	 */
	private void cargarEventoComplementacion(
			Toolbarbutton toolbarbutton_complementacion) {
		if (eventListenerOnClickCompl == null) {
			eventListenerOnClickCompl = new EventListener<Event>() {
				@Override
				public void onEvent(Event evt) throws Exception {
					abrirComplementacion((Toolbarbutton) evt.getTarget());
				}
			};
		}
		if (eventListenerOnOkCompl == null) {
			eventListenerOnOkCompl = new EventListener<Event>() {
				@Override
				public void onEvent(Event evt) throws Exception {
					abrirComplementacion((Toolbarbutton) evt.getTarget());
				}
			};
		}
		toolbarbutton_complementacion.addEventListener(Events.ON_CLICK,
				eventListenerOnClickCompl);
		toolbarbutton_complementacion.addEventListener(Events.ON_OK,
				eventListenerOnOkCompl);
	}

	/**
	 * Este evento se utiliza para la complementacion de datos dependiendo del
	 * servicio
	 * */
	@SuppressWarnings("unchecked")
	protected Window abrirComplementacion(Toolbarbutton target) {
		Listcell celda_tipo_servicio = (Listcell) target
				.getAttribute(CELDA_TIPO_SERVICIO);
		String tipo_servicio = (String) celda_tipo_servicio
				.getAttribute(ATRIBUTO_TIPO_SERVICIO);
		Datebox datebox_realizacion = (Datebox) target
				.getAttribute(DATE_BOX_REALIZACION);
		if (tipo_servicio != null && !tipo_servicio.trim().isEmpty()) {
			String pagina = "/pages/complemento/";
			final BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) target
					.getAttribute(BAND_BOX_SERVICIO);
			// Listbox listbox_via_ingreso = (Listbox)
			// bandBoxRowMacro.getAttribute(LISTBOX_UNIDAD_FUNCIONAL);
			Map<String, Object> map = (Map<String, Object>) bandBoxRowMacro
					.getAttribute(MAP_DETALLE_FACTURA);
			// Manuales_tarifarios manuales_tarifarios = (Manuales_tarifarios)
			// listbox_via_ingreso.getAttribute(MANUAL_TARIFARIO);
			Doublebox doublebox_valor_total = (Doublebox) bandBoxRowMacro
					.getAttribute(DOUBLE_BOX_VALOR_TOTAL);

			// inicializamos parametros
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(ITiposServicio.PARAM_VIA_INGRESO,
					elemento_via_ingreso_principal.getCodigo());
			params.put(ITiposServicio.PARAM_PRESTADOR, prestadores);
			params.put(ITiposServicio.PARAM_ADMINISTRADORA, administradora);
			params.put(ITiposServicio.PARAM_PACIENTES, paciente);
			params.put(ITiposServicio.PARAM_DIAGNOSTICO,
					informacionUltimoRegistro);
			params.put(ITiposServicio.PARAM_VIA_INGRESO_CONTRATADA,
					via_ingreso_contratadas);
			params.put(ITiposServicio.PARAM_PROCEDIMIENTO,
					bandBoxRowMacro.getRegistroSeleccionado());
			params.put(ITiposServicio.PARAM_ADMISION, admision);
			params.put(ITiposServicio.PARAM_VALOR_TOTAL, doublebox_valor_total);
			params.put(ITiposServicio.PARAM_MAP_DETALLE, map);
			params.put(ITiposServicio.PARAM_CONFIG_ADMISION,
					configuracion_admision_ingreso);

			Timestamp fecha_realizacion = datebox_realizacion.getValue() != null ? new Timestamp(
					datebox_realizacion.getValue().getTime())
					: informacionUltimoRegistro.getFecha();

			// cargamos servicios dependiendo la seleccion
			if (tipo_servicio.equals(ITiposServicio.CONSULTA)) {
				pagina += "consultas.zul";
				Datos_consulta datos_consulta = (Datos_consulta) map
						.get(ITiposServicio.PARAM_RIPS);
				if (datos_consulta == null) {
					datos_consulta = new Datos_consulta();
					datos_consulta
							.setCodigo_diagnostico_principal(informacionUltimoRegistro
									.getDiagnostico_principal());
					datos_consulta
							.setCodigo_diagnostico1(informacionUltimoRegistro
									.getDiagnostico_relacionado());
					datos_consulta.setFecha_consulta(fecha_realizacion);
					datos_consulta
							.setNumero_autorizacion(informacionUltimoRegistro
									.getNro_autorizacion());
					map.put(ITiposServicio.PARAM_RIPS, datos_consulta);
				} else {
					datos_consulta.setFecha_consulta(fecha_realizacion);
				}
				params.put(ITiposServicio.PARAM_RIPS, datos_consulta);
			} else if (tipo_servicio.equals(ITiposServicio.PROCEDIMIENTO)) {
				pagina += "procedimientos.zul";
				Datos_procedimiento datos_procedimiento = (Datos_procedimiento) map
						.get(ITiposServicio.PARAM_RIPS);
				Procedimientos procedimientos = (Procedimientos) map
						.get(REGISTRO_SELECCIONADO);
				if (datos_procedimiento == null) {
					datos_procedimiento = new Datos_procedimiento();
					datos_procedimiento
							.setCodigo_diagnostico_principal(informacionUltimoRegistro
									.getDiagnostico_principal());
					datos_procedimiento
							.setCodigo_diagnostico_relacionado(informacionUltimoRegistro
									.getDiagnostico_relacionado());
					datos_procedimiento
							.setFecha_procedimiento(fecha_realizacion);
					datos_procedimiento
							.setNumero_autorizacion(informacionUltimoRegistro
									.getNro_autorizacion());
					datos_procedimiento
							.setAmbito_procedimiento(configuracion_admision_ingreso
									.getAmbito_realizacion());
					datos_procedimiento
							.setFinalidad_procedimiento(procedimientos
									.getFinalidad_procedimiento());
					datos_procedimiento
							.setPersonal_atiende(prestadores != null ? prestadores
									.getTipo_medico() : "");
					datos_procedimiento
							.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
					map.put(ITiposServicio.PARAM_RIPS, datos_procedimiento);
				} else {
					datos_procedimiento
							.setFecha_procedimiento(fecha_realizacion);
				}
				params.put(ITiposServicio.PARAM_RIPS, datos_procedimiento);

			} else if (tipo_servicio.equals(ITiposServicio.QUIRURGICO)
					|| tipo_servicio.equals(ITiposServicio.PROCEDIMIENTO_MULT)) {
				pagina += "quirurgicos.zul";
				Procedimientos procedimientos = (Procedimientos) map
						.get(REGISTRO_SELECCIONADO);
				Datos_procedimiento datos_procedimiento = (Datos_procedimiento) map
						.get(ITiposServicio.PARAM_RIPS);
				if (datos_procedimiento == null) {
					datos_procedimiento = new Datos_procedimiento();
					datos_procedimiento
							.setCodigo_diagnostico_principal(informacionUltimoRegistro
									.getDiagnostico_principal());
					datos_procedimiento
							.setCodigo_diagnostico_relacionado(informacionUltimoRegistro
									.getDiagnostico_relacionado());
					datos_procedimiento
							.setFecha_procedimiento(fecha_realizacion);
					datos_procedimiento
							.setNumero_autorizacion(informacionUltimoRegistro
									.getNro_autorizacion());
					datos_procedimiento
							.setAmbito_procedimiento(configuracion_admision_ingreso
									.getAmbito_realizacion());
					datos_procedimiento
							.setFinalidad_procedimiento(procedimientos
									.getFinalidad_procedimiento());
					datos_procedimiento
							.setPersonal_atiende(prestadores != null ? prestadores
									.getTipo_medico() : "");
					datos_procedimiento
							.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
					map.put(ITiposServicio.PARAM_RIPS, datos_procedimiento);
				} else {
					datos_procedimiento
							.setFecha_procedimiento(fecha_realizacion);
				}
				params.put(ITiposServicio.PARAM_RIPS, datos_procedimiento);
			} else if (tipo_servicio.equals(ITiposServicio.ESTANCIA)) {
				pagina += "estancias.zul";
				Admision_cama admision_cama = (Admision_cama) map
						.get(ITiposServicio.PARAM_RIPS);
				if (admision_cama == null) {
					admision_cama = new Admision_cama();
					map.put(ITiposServicio.PARAM_RIPS, admision_cama);
				}
				params.put(ITiposServicio.PARAM_RIPS, admision_cama);
			} else if (tipo_servicio.equals(ITiposServicio.MEDICAMENTO)
					|| tipo_servicio.equals(ITiposServicio.MATERIALES_INSUMOS)
					|| tipo_servicio.equals(ITiposServicio.SERVICIO)) {
				pagina += "medicamentos_insumos_servicios.zul";
				if (tipo_servicio.equals(ITiposServicio.SERVICIO)) {
					Datos_servicio datos_servicio = (Datos_servicio) map
							.get(ITiposServicio.PARAM_RIPS);
					if (datos_servicio == null) {
						datos_servicio = new Datos_servicio();
						map.put(ITiposServicio.PARAM_RIPS, datos_servicio);
						map.put(NRO_AUTORIZACION,
								informacionUltimoRegistro.getNro_autorizacion());
						map.put(FECHA_REALIZACION, fecha_realizacion);
					} else {
						map.put(FECHA_REALIZACION, fecha_realizacion);
					}
					params.put(ITiposServicio.PARAM_RIPS, map);
				} else {
					Datos_medicamentos datos_medicamentos = (Datos_medicamentos) map
							.get(ITiposServicio.PARAM_RIPS);
					if (datos_medicamentos == null) {
						datos_medicamentos = new Datos_medicamentos();
						map.put(ITiposServicio.PARAM_RIPS, datos_medicamentos);
						map.put(NRO_AUTORIZACION,
								informacionUltimoRegistro.getNro_autorizacion());
						map.put(FECHA_REALIZACION, fecha_realizacion);
					} else {
						map.put(FECHA_REALIZACION, fecha_realizacion);
					}
					params.put(ITiposServicio.PARAM_RIPS, map);
				}
			}

			params.put("COMPONENTE_MACRO", this);

			// ventana externa
			Window window = (Window) Executions.createComponents(pagina,
					getParent(), params);
			window.addEventListener(Events.ON_CANCEL,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event evt) throws Exception {
							evt.getTarget().detach();
							Doublebox doublebox_cantidad = (Doublebox) bandBoxRowMacro
									.getAttribute(DOUBLE_BOX_UNIDADES);
							doublebox_cantidad.setFocus(true);
							doublebox_cantidad.focus();
						}
					});

			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							Doublebox doublebox_cantidad = (Doublebox) bandBoxRowMacro
									.getAttribute(DOUBLE_BOX_UNIDADES);
							doublebox_cantidad.setFocus(true);
							doublebox_cantidad.focus();
						}
					});
			window.doModal();
			return window;
		}
		return null;
	}

	public List<Elemento> getListado_vias_ingreso() {
		return listado_vias_ingreso;
	}

	public void setListado_vias_ingreso(List<Elemento> listado_vias_ingreso) {
		this.listado_vias_ingreso = listado_vias_ingreso;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Administradora getAdministradora() {
		return administradora;
	}

	public void setAdministradora(Administradora administradora) {
		this.administradora = administradora;
	}

	public Prestadores getPrestadores() {
		return prestadores;
	}

	public void setPrestadores(Prestadores prestadores) {
		this.prestadores = prestadores;
	}

	public Date getFecha_ingreso() {
		if (generalComposer instanceof Facturacion_ripsAction) {
			return ((Facturacion_ripsAction) generalComposer).getFechaIngreso();
		} else {
			return fecha_ingreso;
		}
	}

	public void setFecha_ingreso(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	public Date getFecha_egreso() {
		if (generalComposer instanceof Facturacion_ripsAction) {
			return ((Facturacion_ripsAction) generalComposer).getFechaEgreso();
		} else {
			return fecha_egreso;
		}
	}

	public void setFecha_egreso(Date fecha_egreso) {
		this.fecha_egreso = fecha_egreso;
	}

	public Admision getAdmision() {
		return admision;
	}

	public void setAdmision(Admision admision) {
		this.admision = admision;
	}

	public GeneralComposer getGeneralComposer() {
		return generalComposer;
	}

	public void setGeneralComposer(GeneralComposer generalComposer) {
		this.generalComposer = generalComposer;
	}

	public String getTipoContrato() {
		return contratos != null ? contratos.getTipo_facturacion() : "";
	}

	public void cargarDatos(Paciente paciente, Administradora administradora,
			Elemento via_ingreso, Prestadores prestadores, Date fecha,
			Date fecha_ingreso, Date fecha_egreso, Admision admision,
			Contratos contratos_aux) {
		setElemento_via_ingreso_principal(via_ingreso);

		setPaciente(paciente);
		setAdministradora(administradora);
		setPrestadores(prestadores);
		setFecha_ingreso(fecha_ingreso);
		setFecha_egreso(fecha_egreso);
		setAdmision(admision);

		// Actualizamos manuales tarifarios
		if (via_ingreso != null) {
			if (contratos_aux == null) {
				List<Contratos> listado_contratos = getListadoContratos(via_ingreso
						.getCodigo());

				if (onMultiplesContratosAccion != null)
					onMultiplesContratosAccion
							.realizarAccionMultiplesContratos(listado_contratos);
				if (listado_contratos.size() == 1) {
					setContratos(listado_contratos.get(0));
					verificarPaquetes.verificarPaquetes(contratos);
				} else {
					habilitarAcciones(false);
					throw new ValidacionRunTimeException(
							"Esta via de ingreso se presenta repetida en "
									+ listado_contratos.size()
									+ " contrato(s). Seleccionar el contrato con el que se desea guardar");
				}
			} else {
				actualizarContrato(contratos_aux, true);
				verificarPaquetes.verificarPaquetes(contratos_aux);
			}
		}

	}

	public void aplicarViaIngreso() throws Exception {
		// log.info("ejecutando metodo @aplicarViaIngreso() ");
		if (elemento_via_ingreso_principal == null)
			this.contratos = null;
		if (elemento_via_ingreso_principal != null && getPaciente() != null) {
			List<Contratos> listado_contratos = getListadoContratos(elemento_via_ingreso_principal
					.getCodigo());
			if (listado_contratos.size() == 1) {
				habilitarAcciones(true);
				verificarPaquetes.verificarPaquetes(contratos);
			} else {
				habilitarAcciones(false);
				if (onMultiplesContratosAccion != null)
					onMultiplesContratosAccion
							.realizarAccionMultiplesContratos(listado_contratos);
				throw new ValidacionRunTimeException(
						"Esta via de ingreso se presenta repetida en "
								+ listado_contratos.size()
								+ " contrato(s). Seleccionar el contrato con el que se desea guardar");
			}
		}
	}

	public void limpiarCargaDatos() throws Exception {
		cargarDatos(null, null, null, null, null, null, null, null, null);
	}

	public void onComplementarInformacion() {
		Set<Listitem> listado_servicios = getSelectedItems();
		if (listado_servicios.isEmpty()) {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"Para realizar esta accion debe seleccionar por lo menos un item");
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(PARAM_LISTADO_SERVICIOS, listado_servicios);
			params.put(PARAM_AUTORIZACION, getNroAutorizacion());
			params.put(PARAM_WINDOW_PADRE, generalComposer);
			params.put(PARAM_PACIENTE, paciente);
			Window window = (Window) Executions.createComponents(
					"/pages/complemento/complementacion_multiple.zul",
					getParent(), params);
			window.doModal();
		}
	}

	public void onCalcularDescuento() {
		if (chkAplica_descuento.isChecked()) {
			intboxPorcentaje.setDisabled(false);
			Integer porcentaje = intboxPorcentaje.getValue() != null ? intboxPorcentaje
					.getValue() : 0;
			double descuento = (dbxValor_total.getValue() * porcentaje) / 100;
			dbxDto_factura.setValue(descuento);
		} else {
			dbxDto_factura.setValue(0.0);
			intboxPorcentaje.setDisabled(true);
		}
	}

	public double getValorTotal() {
		return dbxSubtotal.getValue();
	}

	public double getValor_cuota() {
		return dbxValor_cuota.getValue() != null ? dbxValor_cuota.getValue()
				: 0.0;
	}

	public double getValor_copago() {
		return dbxValor_copago.getValue() != null ? dbxValor_copago.getValue()
				: 0.0;
	}

	public double getDto_factura() {
		return dbxDto_factura.getValue() != null ? dbxDto_factura.getValue()
				: 0.0;
	}

	public double getDto_copago() {
		return dbxDto_copago.getValue() != null ? dbxDto_copago.getValue()
				: 0.0;
	}

	public double getCop_canc() {
		return dbxCop_canc.getValue() != null ? dbxCop_canc.getValue() : 0.0;
	}

	public Elemento getElemento_via_ingreso_principal() {
		return elemento_via_ingreso_principal;
	}

	public void setElemento_via_ingreso_principal(
			Elemento elemento_via_ingreso_principal) {
		this.elemento_via_ingreso_principal = elemento_via_ingreso_principal;
	}

	public IOnCambio getOnCambio() {
		return onCambio;
	}

	public void setOnCambio(IOnCambio onCambio) {
		this.onCambio = onCambio;
	}

	public OnMultiplesContratosAccion getOnMultiplesContratosAccion() {
		return onMultiplesContratosAccion;
	}

	public void setOnMultiplesContratosAccion(
			OnMultiplesContratosAccion onMultiplesContratosAccion) {
		this.onMultiplesContratosAccion = onMultiplesContratosAccion;
	}

	public Configuracion_admision_ingreso getConfiguracion_admision_ingreso() {
		return configuracion_admision_ingreso;
	}

	public void setConfiguracion_admision_ingreso(
			Configuracion_admision_ingreso configuracion_admision_ingreso) {
		this.configuracion_admision_ingreso = configuracion_admision_ingreso;
	}

	public void setContratos(Contratos contratos) {
		this.contratos = contratos;
	}

	public Contratos getContratos() {
		return this.contratos;
	}

	public void setValorTotal(double valor_total) {
		dbxValor_total.setValue(valor_total);
		;
	}

	public void setValor_cuota(double valor_cuota) {
		dbxValor_cuota.setValue(valor_cuota);
	}

	public void setValor_copago(double valor_copago) {
		dbxValor_copago.setValue(valor_copago);
	}

	public void setNocopago(boolean nocapago) {
		chbNocopago.setChecked(nocapago);
	}

	public void setDto_factura(double dto_factura) {
		dbxDto_factura.setValue(dto_factura);
	}

	public void setDto_copago(double dto_copago) {
		dbxDto_copago.setValue(dto_copago);
	}

	public void setCop_canc(double cop_canc) {
		dbxCop_canc.setValue(cop_canc);
	}

	public Boolean getAdministradora_particular() {
		return administradora_particular;
	}

	public void setAdministradora_particular(Boolean administradora_particular) {
		this.administradora_particular = administradora_particular;
	}

	public void setAplicaDescuento(boolean aplica_descuento) {
		chkAplica_descuento.setChecked(aplica_descuento);
	}

	public boolean getAplicaDescuento() {
		return chkAplica_descuento.isChecked();
	}

	public void calcularPorcentajeApartir(boolean aplica_descuento) {
		if (aplica_descuento) {
			double descuento = dbxDto_factura.getValue() != null ? dbxDto_factura
					.getValue() : 0D;
			Integer porcentaje = new Double(
					(descuento / dbxValor_total.getValue()) * 100).intValue();
			intboxPorcentaje.setValue(porcentaje);
		}
	}

	private String ajustarTipos(Listcell listcell, String tipo) {
		if (tipo != null) {
			if (tipo.equals(ITiposServicio.QUIRURGICO)) {
				listcell.setStyle("text-align:center;font-weight:bold;background-color:"
						+ ITiposServicio.COLOR_QUIRURGICO);
				listcell.setTooltiptext(ITiposServicio.QUIRURGICO);
				listcell.setLabel("P-QX");
			} else if (tipo.equals(ITiposServicio.CONSULTA)) {
				listcell.setStyle("text-align:center;font-weight:bold;background-color:"
						+ ITiposServicio.COLOR_CONSULTA);
				listcell.setTooltiptext(ITiposServicio.CONSULTA);
				listcell.setLabel("CONS");
			} else if (tipo.equals(ITiposServicio.PROCEDIMIENTO)) {
				listcell.setStyle("text-align:center;font-weight:bold;background-color:"
						+ ITiposServicio.COLOR_PROCEDIMIENTO);
				listcell.setTooltiptext(ITiposServicio.PROCEDIMIENTO);
				listcell.setLabel("PROC");
			} else if (tipo.equals(ITiposServicio.MEDICAMENTO)) {
				listcell.setStyle("text-align:center;font-weight:bold;background-color:"
						+ ITiposServicio.COLOR_MEDICAMENTO);
				listcell.setTooltiptext(ITiposServicio.MEDICAMENTO);
				listcell.setLabel("MEDI");
			} else if (tipo.equals(ITiposServicio.MATERIALES_INSUMOS)) {
				listcell.setStyle("text-align:center;font-weight:bold;background-color:"
						+ ITiposServicio.COLOR_MATERIALES_INSUMOS);
				listcell.setTooltiptext(ITiposServicio.MATERIALES_INSUMOS);
				listcell.setLabel("MA-IN");
			} else if (tipo.equals(ITiposServicio.SERVICIO)) {
				listcell.setStyle("text-align:center;font-weight:bold;background-color:"
						+ ITiposServicio.COLOR_SERVICIO);
				listcell.setTooltiptext(ITiposServicio.SERVICIO);
				listcell.setLabel("SERV");
			} else if (tipo.equals(ITiposServicio.ESTANCIA)) {
				listcell.setStyle("text-align:center;font-weight:bold;background-color:"
						+ ITiposServicio.COLOR_ESTANCIA);
				listcell.setTooltiptext(ITiposServicio.ESTANCIA);
				listcell.setLabel("EST");
			}

		} else {
			tipo = "";
		}
		listcell.setAttribute(ATRIBUTO_TIPO_SERVICIO, tipo);
		return tipo;
	}

	public List<Contratos> getListado_contratos_pacientes() {
		return listado_contratos_pacientes;
	}

	public void setListado_contratos_pacientes(
			List<Contratos> listado_contratos_pacientes) {
		this.listado_contratos_pacientes = listado_contratos_pacientes;
	}

	public Via_ingreso_contratadas getVia_ingreso_contratadas() {
		return via_ingreso_contratadas;
	}

	public void setVia_ingreso_contratadas(
			Via_ingreso_contratadas via_ingreso_contratadas) {
		this.via_ingreso_contratadas = via_ingreso_contratadas;
	}

	public PacienteService getPacienteService() {
		return pacienteService;
	}

	public void setPacienteService(PacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	public FacturacionService getFacturacionService() {
		return facturacionService;
	}

	public void setFacturacionService(FacturacionService facturacionService) {
		this.facturacionService = facturacionService;
	}

	public Copago_estratoService getCopago_estratoService() {
		return copago_estratoService;
	}

	public void setCopago_estratoService(
			Copago_estratoService copago_estratoService) {
		this.copago_estratoService = copago_estratoService;
	}

	public Configuracion_admision_ingresoService getConfiguracion_admision_ingresoService() {
		return configuracion_admision_ingresoService;
	}

	public void setConfiguracion_admision_ingresoService(
			Configuracion_admision_ingresoService configuracion_admision_ingresoService) {
		this.configuracion_admision_ingresoService = configuracion_admision_ingresoService;
	}

	public Datos_consultaService getDatos_consultaService() {
		return datos_consultaService;
	}

	public void setDatos_consultaService(
			Datos_consultaService datos_consultaService) {
		this.datos_consultaService = datos_consultaService;
	}

	public Datos_procedimientoService getDatos_procedimientoService() {
		return datos_procedimientoService;
	}

	public void setDatos_procedimientoService(
			Datos_procedimientoService datos_procedimientoService) {
		this.datos_procedimientoService = datos_procedimientoService;
	}

	public ProcedimientosService getProcedimientosService() {
		return procedimientosService;
	}

	public void setProcedimientosService(
			ProcedimientosService procedimientosService) {
		this.procedimientosService = procedimientosService;
	}

	public Datos_servicioService getDatos_servicioService() {
		return datos_servicioService;
	}

	public void setDatos_servicioService(
			Datos_servicioService datos_servicioService) {
		this.datos_servicioService = datos_servicioService;
	}

	public Admision_camaService getAdmision_camaService() {
		return admision_camaService;
	}

	public void setAdmision_camaService(
			Admision_camaService admision_camaService) {
		this.admision_camaService = admision_camaService;
	}

	public Datos_medicamentosService getDatos_medicamentosService() {
		return datos_medicamentosService;
	}

	public void setDatos_medicamentosService(
			Datos_medicamentosService datos_medicamentosService) {
		this.datos_medicamentosService = datos_medicamentosService;
	}

	public Facturacion_servicioService getFacturacion_servicioService() {
		return facturacion_servicioService;
	}

	public void setFacturacion_servicioService(
			Facturacion_servicioService facturacion_servicioService) {
		this.facturacion_servicioService = facturacion_servicioService;
	}

	public Facturacion_medicamentoService getFacturacion_medicamentoService() {
		return facturacion_medicamentoService;
	}

	public void setFacturacion_medicamentoService(
			Facturacion_medicamentoService facturacion_medicamentoService) {
		this.facturacion_medicamentoService = facturacion_medicamentoService;
	}

	public ContratosService getContratosService() {
		return contratosService;
	}

	public void setContratosService(ContratosService contratosService) {
		this.contratosService = contratosService;
	}

	public ElementoService getElementoService() {
		return elementoService;
	}

	public void setElementoService(ElementoService elementoService) {
		this.elementoService = elementoService;
	}

	public ArticuloService getArticuloService() {
		return articuloService;
	}

	public void setArticuloService(ArticuloService articuloService) {
		this.articuloService = articuloService;
	}

	public Via_ingreso_contratadasService getVia_ingreso_contratadasService() {
		return via_ingreso_contratadasService;
	}

	public void setVia_ingreso_contratadasService(
			Via_ingreso_contratadasService via_ingreso_contratadasService) {
		this.via_ingreso_contratadasService = via_ingreso_contratadasService;
	}

	public VerificarPaquetes getVerificarPaquetes() {
		return verificarPaquetes;
	}

	public void setVerificarPaquetes(VerificarPaquetes verificarPaquetes) {
		this.verificarPaquetes = verificarPaquetes;
	}

}
